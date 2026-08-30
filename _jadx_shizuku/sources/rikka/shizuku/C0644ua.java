package rikka.shizuku;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.ua, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0644ua extends AbstractC0491pb {
    public final int e;
    public final int f;
    public final TimeInterpolator g;
    public AutoCompleteTextView h;
    public final Y0 i;
    public final ViewOnFocusChangeListenerC0300j6 j;
    public final C0610t7 k;
    public boolean l;
    public boolean m;
    public boolean n;
    public long o;
    public AccessibilityManager p;
    public ValueAnimator q;
    public ValueAnimator r;

    public C0644ua(C0460ob c0460ob) {
        super(c0460ob);
        this.i = new Y0(3, this);
        this.j = new ViewOnFocusChangeListenerC0300j6(this, 1);
        this.k = new C0610t7(2, this);
        this.o = Long.MAX_VALUE;
        this.f = AbstractC0046as.O(c0460ob.getContext(), R.attr._0_resource_name_obfuscated_res_0x7f040344, 67);
        this.e = AbstractC0046as.O(c0460ob.getContext(), R.attr._0_resource_name_obfuscated_res_0x7f040344, 50);
        this.g = AbstractC0046as.P(c0460ob.getContext(), R.attr._0_resource_name_obfuscated_res_0x7f04034d, V1.a);
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void a() {
        if (this.p.isTouchExplorationEnabled() && X8.H(this.h) && !this.d.hasFocus()) {
            this.h.dismissDropDown();
        }
        this.h.post(new G0(7, this));
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final int c() {
        return R.string._0_resource_name_obfuscated_res_0x7f110063;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final int d() {
        return R.drawable._0_resource_name_obfuscated_res_0x7f0800fd;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final View.OnFocusChangeListener e() {
        return this.j;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final View.OnClickListener f() {
        return this.i;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final C0610t7 h() {
        return this.k;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final boolean i(int i) {
        return i != 0;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final boolean j() {
        return this.l;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final boolean l() {
        return this.n;
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void m(EditText editText) {
        if (!(editText instanceof AutoCompleteTextView)) {
            throw new RuntimeException("EditText needs to be an AutoCompleteTextView if an Exposed Dropdown Menu is being used.");
        }
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) editText;
        this.h = autoCompleteTextView;
        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() { // from class: rikka.shizuku.sa
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                C0644ua c0644ua = this.a;
                c0644ua.getClass();
                if (motionEvent.getAction() == 1) {
                    long jCurrentTimeMillis = System.currentTimeMillis() - c0644ua.o;
                    if (jCurrentTimeMillis < 0 || jCurrentTimeMillis > 300) {
                        c0644ua.m = false;
                    }
                    c0644ua.u();
                    c0644ua.m = true;
                    c0644ua.o = System.currentTimeMillis();
                }
                return false;
            }
        });
        this.h.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() { // from class: rikka.shizuku.ta
            @Override // android.widget.AutoCompleteTextView.OnDismissListener
            public final void onDismiss() {
                C0644ua c0644ua = this.a;
                c0644ua.m = true;
                c0644ua.o = System.currentTimeMillis();
                c0644ua.t(false);
            }
        });
        this.h.setThreshold(0);
        TextInputLayout textInputLayout = this.a;
        C0460ob c0460ob = textInputLayout.c;
        CheckableImageButton checkableImageButton = c0460ob.c;
        checkableImageButton.setImageDrawable(null);
        c0460ob.k();
        AbstractC0599sr.a(c0460ob.a, checkableImageButton, c0460ob.d, c0460ob.e);
        if (!X8.H(editText) && this.p.isTouchExplorationEnabled()) {
            WeakHashMap weakHashMap = Hs.a;
            this.d.setImportantForAccessibility(2);
        }
        textInputLayout.c.h(true);
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void n(C0141e0 c0141e0) {
        if (!X8.H(this.h)) {
            c0141e0.i(Spinner.class.getName());
        }
        if (Build.VERSION.SDK_INT >= 26 ? c0141e0.a.isShowingHintText() : c0141e0.e(4)) {
            c0141e0.k(null);
        }
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void o(AccessibilityEvent accessibilityEvent) {
        if (!this.p.isEnabled() || X8.H(this.h)) {
            return;
        }
        boolean z = (accessibilityEvent.getEventType() == 32768 || accessibilityEvent.getEventType() == 8) && this.n && !this.h.isPopupShowing();
        if (accessibilityEvent.getEventType() == 1 || z) {
            u();
            this.m = true;
            this.o = System.currentTimeMillis();
        }
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void r() {
        int i = 0;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        TimeInterpolator timeInterpolator = this.g;
        valueAnimatorOfFloat.setInterpolator(timeInterpolator);
        valueAnimatorOfFloat.setDuration(this.f);
        valueAnimatorOfFloat.addUpdateListener(new C0551ra(i, this));
        this.r = valueAnimatorOfFloat;
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat2.setInterpolator(timeInterpolator);
        valueAnimatorOfFloat2.setDuration(this.e);
        valueAnimatorOfFloat2.addUpdateListener(new C0551ra(i, this));
        this.q = valueAnimatorOfFloat2;
        valueAnimatorOfFloat2.addListener(new C0603t0(2, this));
        this.p = (AccessibilityManager) this.c.getSystemService("accessibility");
    }

    @Override // rikka.shizuku.AbstractC0491pb
    public final void s() {
        AutoCompleteTextView autoCompleteTextView = this.h;
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setOnTouchListener(null);
            this.h.setOnDismissListener(null);
        }
    }

    public final void t(boolean z) {
        if (this.n != z) {
            this.n = z;
            this.r.cancel();
            this.q.start();
        }
    }

    public final void u() {
        if (this.h == null) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - this.o;
        if (jCurrentTimeMillis < 0 || jCurrentTimeMillis > 300) {
            this.m = false;
        }
        if (this.m) {
            this.m = false;
            return;
        }
        t(!this.n);
        if (!this.n) {
            this.h.dismissDropDown();
        } else {
            this.h.requestFocus();
            this.h.showDropDown();
        }
    }
}
