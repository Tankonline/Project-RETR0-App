package rikka.shizuku;

import android.os.Bundle;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.material.chip.Chip;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class Cb extends Ys {
    public final /* synthetic */ C0024a6 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Cb(C0024a6 c0024a6) {
        super(3);
        this.d = c0024a6;
    }

    @Override // rikka.shizuku.Ys
    public final boolean A(int i, int i2, Bundle bundle) {
        int i3;
        int i4;
        C0024a6 c0024a6 = this.d;
        Chip chip = c0024a6.i;
        if (i == -1) {
            WeakHashMap weakHashMap = Hs.a;
            return chip.performAccessibilityAction(i2, bundle);
        }
        if (i2 == 1) {
            Chip chip2 = c0024a6.i;
            if ((!chip2.isFocused() && !chip2.requestFocus()) || (i3 = c0024a6.l) == i) {
                return false;
            }
            if (i3 != Integer.MIN_VALUE) {
                c0024a6.l = Integer.MIN_VALUE;
                if (i3 == 1) {
                    Chip chip3 = c0024a6.m;
                    chip3.l = false;
                    chip3.refreshDrawableState();
                }
                c0024a6.k(i3, 8);
            }
            if (i == Integer.MIN_VALUE) {
                return false;
            }
            c0024a6.l = i;
            if (i == 1) {
                Chip chip4 = c0024a6.m;
                chip4.l = true;
                chip4.refreshDrawableState();
            }
            c0024a6.k(i, 8);
            return true;
        }
        if (i2 == 2) {
            if (c0024a6.l != i) {
                return false;
            }
            c0024a6.l = Integer.MIN_VALUE;
            if (i == 1) {
                Chip chip5 = c0024a6.m;
                chip5.l = false;
                chip5.refreshDrawableState();
            }
            c0024a6.k(i, 8);
            return true;
        }
        if (i2 == 64) {
            AccessibilityManager accessibilityManager = c0024a6.h;
            if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled() || (i4 = c0024a6.k) == i) {
                return false;
            }
            if (i4 != Integer.MIN_VALUE) {
                c0024a6.k = Integer.MIN_VALUE;
                c0024a6.i.invalidate();
                c0024a6.k(i4, 65536);
            }
            c0024a6.k = i;
            chip.invalidate();
            c0024a6.k(i, 32768);
            return true;
        }
        if (i2 == 128) {
            if (c0024a6.k != i) {
                return false;
            }
            c0024a6.k = Integer.MIN_VALUE;
            chip.invalidate();
            c0024a6.k(i, 65536);
            return true;
        }
        if (i2 == 16) {
            Chip chip6 = c0024a6.m;
            if (i == 0) {
                return chip6.performClick();
            }
            if (i == 1) {
                chip6.playSoundEffect(0);
            }
        }
        return false;
    }

    @Override // rikka.shizuku.Ys
    public final C0141e0 p(int i) {
        return new C0141e0(AccessibilityNodeInfo.obtain(this.d.j(i).a));
    }

    @Override // rikka.shizuku.Ys
    public final C0141e0 q(int i) {
        C0024a6 c0024a6 = this.d;
        int i2 = i == 2 ? c0024a6.k : c0024a6.l;
        if (i2 == Integer.MIN_VALUE) {
            return null;
        }
        return p(i2);
    }
}
