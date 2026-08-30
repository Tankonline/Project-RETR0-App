package rikka.shizuku;

import android.os.Build;
import android.view.View;
import java.nio.ByteBuffer;

/* JADX INFO: renamed from: rikka.shizuku.vp, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0690vp {
    public int a;
    public int b;
    public int c;
    public Object d;

    public AbstractC0690vp() {
        if (Te.a == null) {
            Te.a = new Te();
        }
    }

    public int a(int i) {
        if (i < this.c) {
            return ((ByteBuffer) this.d).getShort(this.b + i);
        }
        return 0;
    }

    public abstract Object b(View view);

    public abstract void c(View view, Object obj);

    public void d(View view, Object obj) {
        Object tag;
        S s;
        if (Build.VERSION.SDK_INT >= this.b) {
            c(view, obj);
            return;
        }
        if (Build.VERSION.SDK_INT >= this.b) {
            tag = b(view);
        } else {
            tag = view.getTag(this.a);
            if (!((Class) this.d).isInstance(tag)) {
                tag = null;
            }
        }
        if (e(tag, obj)) {
            View.AccessibilityDelegate accessibilityDelegateD = Hs.d(view);
            if (accessibilityDelegateD == null) {
                s = null;
            } else {
                s = accessibilityDelegateD instanceof Q ? ((Q) accessibilityDelegateD).a : new S(accessibilityDelegateD);
            }
            if (s == null) {
                s = new S();
            }
            Hs.m(view, s);
            view.setTag(this.a, obj);
            Hs.h(view, this.c);
        }
    }

    public abstract boolean e(Object obj, Object obj2);
}
