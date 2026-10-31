package auth

import (
	"crypto/ed25519"
	"crypto/rand"
	"crypto/sha256"
	"encoding/base64"
	"encoding/hex"
	"encoding/json"
	"errors"
	"os"
	"path/filepath"
)

type Identity struct {
	NodeID     string `json:"nodeId"`
	PrivateKey string `json:"privateKey"`
	PublicKey  string `json:"publicKey"`
}

func LoadOrCreateIdentity(path string) (Identity, error) {
	if data, err := os.ReadFile(path); err == nil {
		var id Identity
		if json.Unmarshal(data, &id) == nil && id.NodeID != "" {
			if _, e := id.Private(); e == nil {
				return id, nil
			}
		}
	}
	pub, priv, err := ed25519.GenerateKey(rand.Reader)
	if err != nil {
		return Identity{}, err
	}
	nodeID, err := NewNodeID()
	if err != nil {
		return Identity{}, err
	}
	id := Identity{NodeID: nodeID, PrivateKey: base64.RawStdEncoding.EncodeToString(priv), PublicKey: base64.RawStdEncoding.EncodeToString(pub)}
	if err = os.MkdirAll(filepath.Dir(path), 0750); err != nil {
		return Identity{}, err
	}
	data, _ := json.MarshalIndent(id, "", "  ")
	if err = os.WriteFile(path, data, 0600); err != nil {
		return Identity{}, err
	}
	return id, nil
}
func (i Identity) Private() (ed25519.PrivateKey, error) {
	b, e := base64.RawStdEncoding.DecodeString(i.PrivateKey)
	if e != nil || len(b) != ed25519.PrivateKeySize {
		return nil, errors.New("invalid private key")
	}
	return ed25519.PrivateKey(b), nil
}
func (i Identity) Public() (ed25519.PublicKey, error) {
	b, e := base64.RawStdEncoding.DecodeString(i.PublicKey)
	if e != nil || len(b) != ed25519.PublicKeySize {
		return nil, errors.New("invalid public key")
	}
	return ed25519.PublicKey(b), nil
}
func (i Identity) Fingerprint() string {
	p, e := i.Public()
	if e != nil {
		return ""
	}
	s := sha256.Sum256(p)
	return hex.EncodeToString(s[:])
}
