package rikka.shizuku;

import com.google.android.material.internal.CheckableImageButton;

/* JADX INFO: renamed from: rikka.shizuku.k8, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0333k8 extends AbstractC0491pb {
    public final /* synthetic */ int e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0333k8(C0460ob c0460ob, int i) {
        super(c0460ob);
        this.e = i;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public void r() {
        switch (this.e) {
            case 0:
                C0460ob c0460ob = this.b;
                c0460ob.getClass();
                CheckableImageButton checkableImageButton = c0460ob.f;
                checkableImageButton.setOnLongClickListener(null);
                AbstractC0599sr.G(checkableImageButton);
                break;
        }
    }
}
