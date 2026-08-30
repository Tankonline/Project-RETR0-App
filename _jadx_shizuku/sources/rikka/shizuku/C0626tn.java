package rikka.shizuku;

import android.graphics.Matrix;
import android.graphics.Path;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.tn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0626tn {
    public float a;
    public float b;
    public float c;
    public float d;
    public float e;
    public final ArrayList f = new ArrayList();
    public final ArrayList g = new ArrayList();

    public C0626tn() {
        d(0.0f, 270.0f, 0.0f);
    }

    public final void a(float f) {
        float f2 = this.d;
        if (f2 == f) {
            return;
        }
        float f3 = ((f - f2) + 360.0f) % 360.0f;
        if (f3 > 180.0f) {
            return;
        }
        float f4 = this.b;
        float f5 = this.c;
        C0503pn c0503pn = new C0503pn(f4, f5, f4, f5);
        c0503pn.f = this.d;
        c0503pn.g = f3;
        this.g.add(new C0441nn(c0503pn));
        this.d = f;
    }

    public final void b(Matrix matrix, Path path) {
        ArrayList arrayList = this.f;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((AbstractC0564rn) arrayList.get(i)).a(matrix, path);
        }
    }

    public final void c(float f, float f2) {
        C0534qn c0534qn = new C0534qn();
        c0534qn.b = f;
        c0534qn.c = f2;
        this.f.add(c0534qn);
        C0472on c0472on = new C0472on(c0534qn, this.b, this.c);
        float fB = c0472on.b() + 270.0f;
        float fB2 = c0472on.b() + 270.0f;
        a(fB);
        this.g.add(c0472on);
        this.d = fB2;
        this.b = f;
        this.c = f2;
    }

    public final void d(float f, float f2, float f3) {
        this.a = f;
        this.b = 0.0f;
        this.c = f;
        this.d = f2;
        this.e = (f2 + f3) % 360.0f;
        this.f.clear();
        this.g.clear();
    }
}
