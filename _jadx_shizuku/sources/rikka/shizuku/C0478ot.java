package rikka.shizuku;

import android.graphics.Matrix;
import android.os.Build;
import android.view.View;

/* JADX INFO: renamed from: rikka.shizuku.ot, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0478ot extends An {
    public static boolean e = true;
    public static boolean f = true;
    public static boolean g = true;
    public static boolean h = true;

    @Override // rikka.shizuku.An
    public void q(View view, int i) {
        if (Build.VERSION.SDK_INT == 28) {
            super.q(view, i);
        } else if (h) {
            try {
                AbstractC0447nt.a(view, i);
            } catch (NoSuchMethodError unused) {
                h = false;
            }
        }
    }

    public void v(View view, int i, int i2, int i3, int i4) {
        if (g) {
            try {
                AbstractC0416mt.a(view, i, i2, i3, i4);
            } catch (NoSuchMethodError unused) {
                g = false;
            }
        }
    }

    public void w(View view, Matrix matrix) {
        if (e) {
            try {
                AbstractC0385lt.b(view, matrix);
            } catch (NoSuchMethodError unused) {
                e = false;
            }
        }
    }

    public void x(View view, Matrix matrix) {
        if (f) {
            try {
                AbstractC0385lt.c(view, matrix);
            } catch (NoSuchMethodError unused) {
                f = false;
            }
        }
    }
}
