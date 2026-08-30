package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.ColorStateList;
import android.view.View;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import java.util.ArrayList;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.t0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0603t0 extends AnimatorListenerAdapter {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ C0603t0(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        switch (this.a) {
            case 0:
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.b;
                actionBarOverlayLayout.v = null;
                actionBarOverlayLayout.j = false;
                break;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                ((InterfaceC0170et) this.b).b();
                break;
            default:
                super.onAnimationCancel(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        switch (this.a) {
            case 0:
                ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) this.b;
                actionBarOverlayLayout.v = null;
                actionBarOverlayLayout.j = false;
                break;
            case 1:
                U1 u1 = (U1) this.b;
                ArrayList arrayList = new ArrayList(u1.e);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ColorStateList colorStateList = ((Rh) arrayList.get(i)).b.n;
                    if (colorStateList != null) {
                        u1.setTintList(colorStateList);
                    }
                }
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                C0644ua c0644ua = (C0644ua) this.b;
                c0644ua.q();
                c0644ua.r.start();
                break;
            case 3:
                ((HideBottomViewOnScrollBehavior) this.b).h = null;
                break;
            case 4:
                ((AbstractC0722wq) this.b).m();
                animator.removeListener(this);
                break;
            default:
                ((InterfaceC0170et) this.b).a();
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        switch (this.a) {
            case 1:
                U1 u1 = (U1) this.b;
                ArrayList arrayList = new ArrayList(u1.e);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    Th th = ((Rh) arrayList.get(i)).b;
                    ColorStateList colorStateList = th.n;
                    if (colorStateList != null) {
                        u1.setTint(colorStateList.getColorForState(th.r, colorStateList.getDefaultColor()));
                    }
                }
                break;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                ((InterfaceC0170et) this.b).c();
                break;
            default:
                super.onAnimationStart(animator);
                break;
        }
    }

    public C0603t0(InterfaceC0170et interfaceC0170et, View view) {
        this.a = 5;
        this.b = interfaceC0170et;
    }
}
