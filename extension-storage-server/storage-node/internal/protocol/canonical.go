package protocol

import (
	"crypto/ed25519"
	"encoding/json"
	"errors"
	"time"
)

func DecodeAndVerify(data []byte, nodeID string, key ed25519.PublicKey, now time.Time) (Command, error) {
	if len(data) == 0 || len(data) > MaxEnvelopeBytes {
		return Command{}, errors.New("invalid envelope size")
	}
	var c Command
	if json.Unmarshal(data, &c) != nil {
		return c, errors.New("invalid envelope")
	}
	if err := Verify(c, nodeID, key, now); err != nil {
		return c, err
	}
	return c, nil
}
func (c Command) SignatureText() string { return c.EncodedSignature() }
