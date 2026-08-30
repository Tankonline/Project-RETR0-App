package rikka.shizuku;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.ActionBarContextView;
import java.util.WeakHashMap;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.q0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnAttachStateChangeListenerC0511q0 implements View.OnAttachStateChangeListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ ViewOnAttachStateChangeListenerC0511q0(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        AccessibilityManager accessibilityManager;
        Object obj = this.b;
        switch (this.a) {
            case 0:
                ActionBarContextView actionBarContextView = (ActionBarContextView) obj;
                if (actionBarContextView.getRootWindowInsets() != null) {
                    ActionBarContextView.a(actionBarContextView, actionBarContextView.getRootWindowInsets());
                }
                break;
            case 1:
                view.addOnLayoutChangeListener((ViewOnLayoutChangeListenerC0270i5) obj);
                break;
            case 3:
                C0460ob c0460ob = (C0460ob) obj;
                if (c0460ob.r != null && (accessibilityManager = c0460ob.q) != null) {
                    WeakHashMap weakHashMap = Hs.a;
                    if (c0460ob.isAttachedToWindow()) {
                        accessibilityManager.addTouchExplorationStateChangeListener(new T(c0460ob.r));
                    }
                    break;
                }
                break;
            case 4:
                View view2 = (View) obj;
                view2.removeOnAttachStateChangeListener(this);
                WeakHashMap weakHashMap2 = Hs.a;
                AbstractC0755xs.c(view2);
                break;
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        AccessibilityManager accessibilityManager;
        switch (this.a) {
            case 0:
                break;
            case 1:
                view.removeOnLayoutChangeListener((ViewOnLayoutChangeListenerC0270i5) this.b);
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                G5 g5 = (G5) this.b;
                ViewTreeObserver viewTreeObserver = g5.x;
                if (viewTreeObserver != null) {
                    if (!viewTreeObserver.isAlive()) {
                        g5.x = view.getViewTreeObserver();
                    }
                    g5.x.removeGlobalOnLayoutListener(g5.i);
                }
                view.removeOnAttachStateChangeListener(this);
                break;
            case 3:
                C0460ob c0460ob = (C0460ob) this.b;
                C0610t7 c0610t7 = c0460ob.r;
                if (c0610t7 != null && (accessibilityManager = c0460ob.q) != null) {
                    accessibilityManager.removeTouchExplorationStateChangeListener(new T(c0610t7));
                    break;
                }
                break;
            case 4:
                break;
            default:
                No no = (No) this.b;
                ViewTreeObserver viewTreeObserver2 = no.o;
                if (viewTreeObserver2 != null) {
                    if (!viewTreeObserver2.isAlive()) {
                        no.o = view.getViewTreeObserver();
                    }
                    no.o.removeGlobalOnLayoutListener(no.i);
                }
                view.removeOnAttachStateChangeListener(this);
                break;
        }
    }

    private final void a(View view) {
    }

    private final void b(View view) {
    }

    private final void c(View view) {
    }

    private final void d(View view) {
    }
}
