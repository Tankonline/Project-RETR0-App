package rikka.shizuku;

import androidx.appcompat.widget.ActionMenuView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/* JADX INFO: renamed from: rikka.shizuku.k5, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0330k5 implements InterfaceC0652ui {
    public boolean a;
    public final Object b;

    public /* synthetic */ C0330k5(Object obj) {
        this.b = obj;
    }

    public boolean a() {
        return this.a;
    }

    @Override // rikka.shizuku.InterfaceC0652ui
    public void b(MenuC0129di menuC0129di, boolean z) {
        C0 c0;
        if (this.a) {
            return;
        }
        this.a = true;
        C0260hq c0260hq = (C0260hq) this.b;
        ActionMenuView actionMenuView = c0260hq.q.a.a;
        if (actionMenuView != null && (c0 = actionMenuView.t) != null) {
            c0.e();
            C0758y0 c0758y0 = c0.r;
            if (c0758y0 != null && c0758y0.b()) {
                c0758y0.i.dismiss();
            }
        }
        c0260hq.r.onPanelClosed(108, menuC0129di);
        this.a = false;
    }

    @Override // rikka.shizuku.InterfaceC0652ui
    public boolean c(MenuC0129di menuC0129di) {
        ((C0260hq) this.b).r.onMenuOpened(108, menuC0129di);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0036  */
    public boolean d(CharSequence charSequence, int i) {
        if (charSequence == null || i < 0 || charSequence.length() - i < 0) {
            throw new IllegalArgumentException();
        }
        if (((Me) this.b) == null) {
            return a();
        }
        char c = 0;
        c = 2;
        for (int i2 = 0; i2 < i && c == 2; i2++) {
            byte directionality = Character.getDirectionality(charSequence.charAt(i2));
            C0330k5 c0330k5 = Dp.a;
            if (directionality == 0) {
                c = 1;
                continue;
            } else if (directionality != 1 && directionality != 2) {
                switch (directionality) {
                    case 14:
                    case 15:
                        c = 1;
                        continue;
                    case 16:
                    case 17:
                        break;
                    default:
                        c = 2;
                        continue;
                }
            }
        }
        if (c == 0) {
            return true;
        }
        if (c != 1) {
            return a();
        }
        return false;
    }

    public C0330k5(Me me2, boolean z) {
        this(me2);
        this.a = z;
    }

    public C0330k5(BottomSheetBehavior bottomSheetBehavior, boolean z) {
        this.b = bottomSheetBehavior;
        this.a = z;
    }
}
