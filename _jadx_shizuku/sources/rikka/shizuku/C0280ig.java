package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.ig, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0280ig extends Number {
    public final String a;

    public C0280ig(String str) {
        this.a = str;
    }

    @Override // java.lang.Number
    public final double doubleValue() {
        return Double.parseDouble(this.a);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C0280ig) {
            return this.a.equals(((C0280ig) obj).a);
        }
        return false;
    }

    @Override // java.lang.Number
    public final float floatValue() {
        return Float.parseFloat(this.a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // java.lang.Number
    public final int intValue() {
        String str = this.a;
        try {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                return (int) Long.parseLong(str);
            }
        } catch (NumberFormatException unused2) {
            return X8.N(str).intValue();
        }
    }

    @Override // java.lang.Number
    public final long longValue() {
        String str = this.a;
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return X8.N(str).longValue();
        }
    }

    public final String toString() {
        return this.a;
    }
}
