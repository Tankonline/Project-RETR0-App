package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public final class Qr {
    public volatile Object a;

    public final String toString() {
        StringBuilder sb = new StringBuilder("UnsafeLazy{value=");
        sb.append(this.a == AbstractC0046as.d ? "(uninitialized)" : this.a.toString());
        sb.append('}');
        return sb.toString();
    }
}
