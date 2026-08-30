package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.t, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0602t extends IllegalStateException {
    public final /* synthetic */ int a;
    public Exception b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0602t(int i, String str) {
        super(str);
        this.a = i;
    }

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        switch (this.a) {
            case 0:
                break;
            case 1:
                break;
        }
        return this.b;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0602t(String str, Exception exc) {
        super(str);
        this.a = 0;
        this.b = exc;
    }
}
