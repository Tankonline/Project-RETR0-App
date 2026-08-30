package rikka.shizuku;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class Va {
    public static final ThreadLocal d = new ThreadLocal();
    public final int a;
    public final Xn b;
    public volatile int c = 0;

    public Va(Xn xn, int i) {
        this.b = xn;
        this.a = i;
    }

    public final int a(int i) {
        Ai aiB = b();
        int iA = aiB.a(16);
        if (iA == 0) {
            return 0;
        }
        ByteBuffer byteBuffer = (ByteBuffer) aiB.d;
        int i2 = iA + aiB.a;
        return byteBuffer.getInt((i * 4) + byteBuffer.getInt(i2) + i2 + 4);
    }

    public final Ai b() {
        ThreadLocal threadLocal = d;
        Ai ai = (Ai) threadLocal.get();
        if (ai == null) {
            ai = new Ai();
            threadLocal.set(ai);
        }
        Bi bi = (Bi) this.b.a;
        int iA = bi.a(6);
        if (iA != 0) {
            int i = iA + bi.a;
            int i2 = (this.a * 4) + ((ByteBuffer) bi.d).getInt(i) + i + 4;
            int i3 = ((ByteBuffer) bi.d).getInt(i2) + i2;
            ByteBuffer byteBuffer = (ByteBuffer) bi.d;
            ai.d = byteBuffer;
            if (byteBuffer != null) {
                ai.a = i3;
                int i4 = i3 - byteBuffer.getInt(i3);
                ai.b = i4;
                ai.c = ((ByteBuffer) ai.d).getShort(i4);
                return ai;
            }
            ai.a = 0;
            ai.b = 0;
            ai.c = 0;
        }
        return ai;
    }

    public final String toString() {
        int i;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", id:");
        Ai aiB = b();
        int iA = aiB.a(4);
        sb.append(Integer.toHexString(iA != 0 ? ((ByteBuffer) aiB.d).getInt(iA + aiB.a) : 0));
        sb.append(", codepoints:");
        Ai aiB2 = b();
        int iA2 = aiB2.a(16);
        if (iA2 != 0) {
            int i2 = iA2 + aiB2.a;
            i = ((ByteBuffer) aiB2.d).getInt(((ByteBuffer) aiB2.d).getInt(i2) + i2);
        } else {
            i = 0;
        }
        for (int i3 = 0; i3 < i; i3++) {
            sb.append(Integer.toHexString(a(i3)));
            sb.append(" ");
        }
        return sb.toString();
    }
}
