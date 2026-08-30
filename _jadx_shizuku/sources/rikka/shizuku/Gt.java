package rikka.shizuku;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsAnimation$Callback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class Gt extends WindowInsetsAnimation$Callback {
    public final C0796z7 a;
    public List b;
    public ArrayList c;
    public final HashMap d;

    public Gt(C0796z7 c0796z7) {
        super(0);
        this.d = new HashMap();
        this.a = c0796z7;
    }

    public final Jt a(WindowInsetsAnimation windowInsetsAnimation) {
        Jt jt = (Jt) this.d.get(windowInsetsAnimation);
        if (jt == null) {
            jt = new Jt(0, null, 0L);
            if (Build.VERSION.SDK_INT >= 30) {
                jt.a = new Ht(windowInsetsAnimation);
            }
            this.d.put(windowInsetsAnimation, jt);
        }
        return jt;
    }

    public final void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
        C0796z7 c0796z7 = this.a;
        a(windowInsetsAnimation);
        ((View) c0796z7.e).setTranslationY(0.0f);
        this.d.remove(windowInsetsAnimation);
    }

    public final void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
        C0796z7 c0796z7 = this.a;
        a(windowInsetsAnimation);
        View view = (View) c0796z7.e;
        int[] iArr = (int[]) c0796z7.f;
        view.getLocationOnScreen(iArr);
        c0796z7.c = iArr[1];
    }

    public final WindowInsets onProgress(WindowInsets windowInsets, List list) {
        ArrayList arrayList = this.c;
        if (arrayList == null) {
            ArrayList arrayList2 = new ArrayList(list.size());
            this.c = arrayList2;
            this.b = Collections.unmodifiableList(arrayList2);
        } else {
            arrayList.clear();
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            WindowInsetsAnimation windowInsetsAnimationL = X.l(list.get(size));
            Jt jtA = a(windowInsetsAnimationL);
            jtA.a.d(windowInsetsAnimationL.getFraction());
            this.c.add(jtA);
        }
        C0796z7 c0796z7 = this.a;
        au auVarE = au.e(null, windowInsets);
        c0796z7.a(auVarE, this.b);
        return auVarE.d();
    }

    public final WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
        C0796z7 c0796z7 = this.a;
        a(windowInsetsAnimation);
        C0587sf c0587sfC = C0587sf.c(bounds.getLowerBound());
        C0587sf c0587sfC2 = C0587sf.c(bounds.getUpperBound());
        View view = (View) c0796z7.e;
        int[] iArr = (int[]) c0796z7.f;
        view.getLocationOnScreen(iArr);
        int i = c0796z7.c - iArr[1];
        c0796z7.d = i;
        view.setTranslationY(i);
        X.o();
        return X.j(c0587sfC.d(), c0587sfC2.d());
    }
}
