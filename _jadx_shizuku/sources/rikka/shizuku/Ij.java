package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Ij {
    public final byte a;
    public final byte b;
    public final int c;

    public Ij(byte b, byte b2, int i) {
        this.a = b;
        this.b = b2;
        this.c = i;
    }

    public final String a() {
        return "version=" + ((int) this.a) + ", type=" + ((int) this.b) + ", payload=" + this.c;
    }

    public final String toString() {
        return AbstractC0535qo.d("PairingPacketHeader(", a(), ")");
    }
}
