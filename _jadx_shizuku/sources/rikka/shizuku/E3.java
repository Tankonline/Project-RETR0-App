package rikka.shizuku;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes.dex */
public final class E3 extends C0682vh {
    public final /* synthetic */ int h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ E3(int i, int i2) {
        super(i);
        this.h = i2;
    }

    @Override // rikka.shizuku.C0682vh
    public int l(Object obj, Object obj2) {
        switch (this.h) {
            case 0:
                return ((Bitmap) obj2).getByteCount() / 1024;
            default:
                return super.l(obj, obj2);
        }
    }
}
