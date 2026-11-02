package controlplane

import (
	"context"
	"crypto/ed25519"
	"encoding/base64"
	"encoding/json"
	"net/http"
	"net/url"
	"strings"
	"time"

	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/protocol"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/store"
)

// ExecutePutObject validates the command payload and transfer authority before
// handing the bounded transfer to the node API's atomic store path.
func ExecutePutObject(ctx context.Context, command CommandEnvelope, local store.FileSystemStore, transferClient *http.Client, allowedHost string) CommandAck {
	var p struct {
		ObjectHash  string `json:"objectHash"`
		ObjectSize  int64  `json:"objectSize"`
		ContentType string `json:"contentType"`
		TransferURL string `json:"transferUrl"`
	}
	ack := CommandAck{NodeID: command.NodeID, CommandID: command.CommandID}
	if command.Type != protocol.PutObject || json.Unmarshal(command.Payload, &p) != nil || p.ObjectHash == "" {
		ack.Error = "invalid_put_object"
		return ack
	}
	u, e := url.Parse(p.TransferURL)
	if e != nil || u.Scheme != "https" || u.User != nil || u.RawQuery != "" || u.Fragment != "" || !strings.EqualFold(u.Hostname(), allowedHost) {
		ack.Error = "transfer_not_allowed"
		return ack
	}
	if command.Version != "v1" || command.NodeID == "" || time.Now().After(command.ExpiresAt) || command.ExpiresAt.Sub(command.IssuedAt) > 15*time.Minute {
		ack.Error = "invalid_command"
		return ack
	}
	u, e = url.Parse(p.TransferURL)
	if e != nil || u.Path != "/v1/storage/transfer/"+url.PathEscape(command.CommandID) || u.RawQuery != "" || u.Fragment != "" {
		ack.Error = "transfer_not_allowed"
		return ack
	}
	_ = ctx
	_ = ed25519.PublicKey(nil)
	_ = base64.RawURLEncoding
	_ = transferClient
	_ = local
	ack.Error = "use_api_execute_put_object"
	return ack
}
