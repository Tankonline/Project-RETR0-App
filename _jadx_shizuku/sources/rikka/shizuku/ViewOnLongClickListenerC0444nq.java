package rikka.shizuku;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.nq, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnLongClickListenerC0444nq implements View.OnLongClickListener, View.OnHoverListener, View.OnAttachStateChangeListener {
    public static ViewOnLongClickListenerC0444nq k;
    public static ViewOnLongClickListenerC0444nq l;
    public final View a;
    public final CharSequence b;
    public final int c;
    public final RunnableC0413mq d;
    public final RunnableC0413mq e;
    public int f;
    public int g;
    public C0475oq h;
    public boolean i;
    public boolean j;

    /* JADX WARN: Type inference failed for: r0v0, types: [rikka.shizuku.mq] */
    /* JADX WARN: Type inference failed for: r0v1, types: [rikka.shizuku.mq] */
    public ViewOnLongClickListenerC0444nq(View view, CharSequence charSequence) {
        final int i = 0;
        this.d = new Runnable(this) { // from class: rikka.shizuku.mq
            public final /* synthetic */ ViewOnLongClickListenerC0444nq b;

            {
                this.b = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        this.b.c(false);
                        break;
                    default:
                        this.b.a();
                        break;
                }
            }
        };
        final int i2 = 1;
        this.e = new Runnable(this) { // from class: rikka.shizuku.mq
            public final /* synthetic */ ViewOnLongClickListenerC0444nq b;

            {
                this.b = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        this.b.c(false);
                        break;
                    default:
                        this.b.a();
                        break;
                }
            }
        };
        this.a = view;
        this.b = charSequence;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(view.getContext());
        Method method = Is.a;
        this.c = Build.VERSION.SDK_INT >= 28 ? G9.g(viewConfiguration) : viewConfiguration.getScaledTouchSlop() / 2;
        this.j = true;
        view.setOnLongClickListener(this);
        view.setOnHoverListener(this);
    }

    public static void b(ViewOnLongClickListenerC0444nq viewOnLongClickListenerC0444nq) {
        ViewOnLongClickListenerC0444nq viewOnLongClickListenerC0444nq2 = k;
        if (viewOnLongClickListenerC0444nq2 != null) {
            viewOnLongClickListenerC0444nq2.a.removeCallbacks(viewOnLongClickListenerC0444nq2.d);
        }
        k = viewOnLongClickListenerC0444nq;
        if (viewOnLongClickListenerC0444nq != null) {
            viewOnLongClickListenerC0444nq.a.postDelayed(viewOnLongClickListenerC0444nq.d, ViewConfiguration.getLongPressTimeout());
        }
    }

    public final void a() {
        ViewOnLongClickListenerC0444nq viewOnLongClickListenerC0444nq = l;
        View view = this.a;
        if (viewOnLongClickListenerC0444nq == this) {
            l = null;
            C0475oq c0475oq = this.h;
            if (c0475oq != null) {
                View view2 = c0475oq.b;
                if (view2.getParent() != null) {
                    ((WindowManager) c0475oq.a.getSystemService("window")).removeView(view2);
                }
                this.h = null;
                this.j = true;
                view.removeOnAttachStateChangeListener(this);
            } else {
                Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
            }
        }
        if (k == this) {
            b(null);
        }
        view.removeCallbacks(this.e);
    }

    public final void c(boolean z) {
        int height;
        int i;
        int i2;
        int i3;
        long longPressTimeout;
        long j;
        long j2;
        WeakHashMap weakHashMap = Hs.a;
        View view = this.a;
        if (view.isAttachedToWindow()) {
            b(null);
            ViewOnLongClickListenerC0444nq viewOnLongClickListenerC0444nq = l;
            if (viewOnLongClickListenerC0444nq != null) {
                viewOnLongClickListenerC0444nq.a();
            }
            l = this;
            this.i = z;
            C0475oq c0475oq = new C0475oq(view.getContext());
            this.h = c0475oq;
            int width = this.f;
            int i4 = this.g;
            boolean z2 = this.i;
            View view2 = c0475oq.b;
            ViewParent parent = view2.getParent();
            Context context = c0475oq.a;
            if (parent != null && view2.getParent() != null) {
                ((WindowManager) context.getSystemService("window")).removeView(view2);
            }
            c0475oq.c.setText(this.b);
            WindowManager.LayoutParams layoutParams = c0475oq.d;
            layoutParams.token = view.getApplicationWindowToken();
            int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen._0_resource_name_obfuscated_res_0x7f070359);
            if (view.getWidth() < dimensionPixelOffset) {
                width = view.getWidth() / 2;
            }
            if (view.getHeight() >= dimensionPixelOffset) {
                int dimensionPixelOffset2 = context.getResources().getDimensionPixelOffset(R.dimen._0_resource_name_obfuscated_res_0x7f070358);
                height = i4 + dimensionPixelOffset2;
                i = i4 - dimensionPixelOffset2;
            } else {
                height = view.getHeight();
                i = 0;
            }
            layoutParams.gravity = 49;
            int dimensionPixelOffset3 = context.getResources().getDimensionPixelOffset(z2 ? R.dimen._0_resource_name_obfuscated_res_0x7f07035c : R.dimen._0_resource_name_obfuscated_res_0x7f07035b);
            View rootView = view.getRootView();
            ViewGroup.LayoutParams layoutParams2 = rootView.getLayoutParams();
            if (!(layoutParams2 instanceof WindowManager.LayoutParams) || ((WindowManager.LayoutParams) layoutParams2).type != 2) {
                for (Context context2 = view.getContext(); context2 instanceof ContextWrapper; context2 = ((ContextWrapper) context2).getBaseContext()) {
                    if (context2 instanceof Activity) {
                        rootView = ((Activity) context2).getWindow().getDecorView();
                        break;
                    }
                }
            }
            if (rootView == null) {
                Log.e("TooltipPopup", "Cannot find app view");
                i3 = 1;
            } else {
                Rect rect = c0475oq.e;
                rootView.getWindowVisibleDisplayFrame(rect);
                if (rect.left >= 0 || rect.top >= 0) {
                    i2 = 0;
                    i3 = 1;
                } else {
                    Resources resources = context.getResources();
                    i3 = 1;
                    int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
                    int dimensionPixelSize = identifier != 0 ? resources.getDimensionPixelSize(identifier) : 0;
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    i2 = 0;
                    rect.set(0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels);
                }
                int[] iArr = c0475oq.g;
                rootView.getLocationOnScreen(iArr);
                int[] iArr2 = c0475oq.f;
                view.getLocationOnScreen(iArr2);
                int i5 = iArr2[i2] - iArr[i2];
                iArr2[i2] = i5;
                iArr2[i3] = iArr2[i3] - iArr[i3];
                layoutParams.x = (i5 + width) - (rootView.getWidth() / 2);
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i2, i2);
                view2.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                int measuredHeight = view2.getMeasuredHeight();
                int i6 = iArr2[i3];
                int i7 = ((i6 + i) - dimensionPixelOffset3) - measuredHeight;
                int i8 = i6 + height + dimensionPixelOffset3;
                if (z2) {
                    if (i7 >= 0) {
                        layoutParams.y = i7;
                    } else {
                        layoutParams.y = i8;
                    }
                } else if (measuredHeight + i8 <= rect.height()) {
                    layoutParams.y = i8;
                } else {
                    layoutParams.y = i7;
                }
            }
            ((WindowManager) context.getSystemService("window")).addView(view2, layoutParams);
            view.addOnAttachStateChangeListener(this);
            if (this.i) {
                j2 = 2500;
            } else {
                if ((view.getWindowSystemUiVisibility() & 1) == i3) {
                    longPressTimeout = ViewConfiguration.getLongPressTimeout();
                    j = 3000;
                } else {
                    longPressTimeout = ViewConfiguration.getLongPressTimeout();
                    j = 15000;
                }
                j2 = j - longPressTimeout;
            }
            RunnableC0413mq runnableC0413mq = this.e;
            view.removeCallbacks(runnableC0413mq);
            view.postDelayed(runnableC0413mq, j2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0066  */
    @Override // android.view.View.OnHoverListener
    public final boolean onHover(View view, MotionEvent motionEvent) {
        if (this.h == null || !this.i) {
            View view2 = this.a;
            AccessibilityManager accessibilityManager = (AccessibilityManager) view2.getContext().getSystemService("accessibility");
            if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled()) {
                int action = motionEvent.getAction();
                if (action != 7) {
                    if (action == 10) {
                        this.j = true;
                        a();
                        return false;
                    }
                } else if (view2.isEnabled() && this.h == null) {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    if (this.j) {
                        this.f = x;
                        this.g = y;
                        this.j = false;
                        b(this);
                    } else {
                        int iAbs = Math.abs(x - this.f);
                        int i = this.c;
                        if (iAbs > i || Math.abs(y - this.g) > i) {
                            this.f = x;
                            this.g = y;
                            this.j = false;
                            b(this);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        this.f = view.getWidth() / 2;
        this.g = view.getHeight() / 2;
        c(true);
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        a();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }
}
