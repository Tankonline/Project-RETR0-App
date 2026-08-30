package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.s3, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0575s3 extends Ys {
    public final /* synthetic */ C0606t3 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0575s3(C0606t3 c0606t3) {
        super(8, c0606t3);
        this.d = c0606t3;
    }

    @Override // rikka.shizuku.Ys
    public final void B(int i) {
        super/*android.widget.TextView*/.setFirstBaselineToTopHeight(i);
    }

    @Override // rikka.shizuku.Ys
    public final void C(int i) {
        super/*android.widget.TextView*/.setLastBaselineToBottomHeight(i);
    }
}
