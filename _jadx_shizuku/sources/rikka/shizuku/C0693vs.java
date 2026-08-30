package rikka.shizuku;

import android.text.TextUtils;
import android.view.View;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.vs, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0693vs extends AbstractC0690vp {
    public final /* synthetic */ int e;

    public C0693vs(int i, Class cls, int i2, int i3, int i4) {
        this.e = i4;
        this.a = i;
        this.d = cls;
        this.c = i2;
        this.b = i3;
    }

    @Override // rikka.shizuku.AbstractC0690vp
    public final Object b(View view) {
        switch (this.e) {
            case 0:
                return Boolean.valueOf(Cs.c(view));
            case 1:
                return Cs.a(view);
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return Es.b(view);
            default:
                return Boolean.valueOf(Cs.b(view));
        }
    }

    @Override // rikka.shizuku.AbstractC0690vp
    public final void c(View view, Object obj) {
        switch (this.e) {
            case 0:
                Cs.f(view, ((Boolean) obj).booleanValue());
                break;
            case 1:
                Cs.e(view, (CharSequence) obj);
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                Es.c(view, (CharSequence) obj);
                break;
            default:
                Cs.d(view, ((Boolean) obj).booleanValue());
                break;
        }
    }

    @Override // rikka.shizuku.AbstractC0690vp
    public final boolean e(Object obj, Object obj2) {
        switch (this.e) {
            case 0:
                Boolean bool = (Boolean) obj;
                Boolean bool2 = (Boolean) obj2;
                return !((bool != null && bool.booleanValue()) == (bool2 != null && bool2.booleanValue()));
            case 1:
                return !TextUtils.equals((CharSequence) obj, (CharSequence) obj2);
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return !TextUtils.equals((CharSequence) obj, (CharSequence) obj2);
            default:
                Boolean bool3 = (Boolean) obj;
                Boolean bool4 = (Boolean) obj2;
                return !((bool3 != null && bool3.booleanValue()) == (bool4 != null && bool4.booleanValue()));
        }
    }
}
