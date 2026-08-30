package rikka.shizuku;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.ob, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0460ob extends LinearLayout {
    public final TextInputLayout a;
    public final FrameLayout b;
    public final CheckableImageButton c;
    public final ColorStateList d;
    public final PorterDuff.Mode e;
    public final CheckableImageButton f;
    public final C0429nb g;
    public int h;
    public final LinkedHashSet i;
    public final ColorStateList j;
    public final PorterDuff.Mode k;
    public final int l;
    public final CharSequence m;
    public final C0606t3 n;
    public boolean o;
    public EditText p;
    public final AccessibilityManager q;
    public C0610t7 r;
    public final C0367lb s;

    public C0460ob(TextInputLayout textInputLayout, K3 k3) {
        CharSequence text;
        super(textInputLayout.getContext());
        this.h = 0;
        this.i = new LinkedHashSet();
        this.s = new C0367lb(this);
        C0398mb c0398mb = new C0398mb(this);
        this.q = (AccessibilityManager) getContext().getSystemService("accessibility");
        this.a = textInputLayout;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388613));
        FrameLayout frameLayout = new FrameLayout(getContext());
        this.b = frameLayout;
        frameLayout.setVisibility(8);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        CheckableImageButton checkableImageButtonA = a(this, layoutInflaterFrom, R.id._0_resource_name_obfuscated_res_0x7f0901d5);
        this.c = checkableImageButtonA;
        CheckableImageButton checkableImageButtonA2 = a(frameLayout, layoutInflaterFrom, R.id._0_resource_name_obfuscated_res_0x7f0901d4);
        this.f = checkableImageButtonA2;
        this.g = new C0429nb(this, k3);
        C0606t3 c0606t3 = new C0606t3(getContext(), null);
        this.n = c0606t3;
        TypedArray typedArray = (TypedArray) k3.a;
        if (typedArray.hasValue(38)) {
            this.d = X8.x(getContext(), k3, 38);
        }
        if (typedArray.hasValue(39)) {
            this.e = AbstractC0750xn.q(typedArray.getInt(39, -1), null);
        }
        if (typedArray.hasValue(37)) {
            checkableImageButtonA.setImageDrawable(k3.i(37));
            k();
            AbstractC0599sr.a(textInputLayout, checkableImageButtonA, this.d, this.e);
        }
        checkableImageButtonA.setContentDescription(getResources().getText(R.string._0_resource_name_obfuscated_res_0x7f110061));
        WeakHashMap weakHashMap = Hs.a;
        checkableImageButtonA.setImportantForAccessibility(2);
        checkableImageButtonA.setClickable(false);
        checkableImageButtonA.f = false;
        checkableImageButtonA.setFocusable(false);
        if (!typedArray.hasValue(53)) {
            if (typedArray.hasValue(32)) {
                this.j = X8.x(getContext(), k3, 32);
            }
            if (typedArray.hasValue(33)) {
                this.k = AbstractC0750xn.q(typedArray.getInt(33, -1), null);
            }
        }
        if (typedArray.hasValue(30)) {
            g(typedArray.getInt(30, 0));
            if (typedArray.hasValue(27) && checkableImageButtonA2.getContentDescription() != (text = typedArray.getText(27))) {
                checkableImageButtonA2.setContentDescription(text);
            }
            boolean z = typedArray.getBoolean(26, true);
            if (checkableImageButtonA2.e != z) {
                checkableImageButtonA2.e = z;
                checkableImageButtonA2.sendAccessibilityEvent(0);
            }
        } else if (typedArray.hasValue(53)) {
            if (typedArray.hasValue(54)) {
                this.j = X8.x(getContext(), k3, 54);
            }
            if (typedArray.hasValue(55)) {
                this.k = AbstractC0750xn.q(typedArray.getInt(55, -1), null);
            }
            g(typedArray.getBoolean(53, false) ? 1 : 0);
            CharSequence text2 = typedArray.getText(51);
            if (checkableImageButtonA2.getContentDescription() != text2) {
                checkableImageButtonA2.setContentDescription(text2);
            }
        }
        int dimensionPixelSize = typedArray.getDimensionPixelSize(29, getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f0702de));
        if (dimensionPixelSize < 0) {
            throw new IllegalArgumentException("endIconSize cannot be less than 0");
        }
        if (dimensionPixelSize != this.l) {
            this.l = dimensionPixelSize;
            checkableImageButtonA2.setMinimumWidth(dimensionPixelSize);
            checkableImageButtonA2.setMinimumHeight(dimensionPixelSize);
            checkableImageButtonA.setMinimumWidth(dimensionPixelSize);
            checkableImageButtonA.setMinimumHeight(dimensionPixelSize);
        }
        if (typedArray.hasValue(31)) {
            ImageView.ScaleType scaleTypeH = AbstractC0599sr.h(typedArray.getInt(31, -1));
            checkableImageButtonA2.setScaleType(scaleTypeH);
            checkableImageButtonA.setScaleType(scaleTypeH);
        }
        c0606t3.setVisibility(8);
        c0606t3.setId(R.id._0_resource_name_obfuscated_res_0x7f0901dc);
        c0606t3.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 80.0f));
        c0606t3.setAccessibilityLiveRegion(1);
        c0606t3.setTextAppearance(typedArray.getResourceId(72, 0));
        if (typedArray.hasValue(73)) {
            c0606t3.setTextColor(k3.h(73));
        }
        CharSequence text3 = typedArray.getText(71);
        this.m = TextUtils.isEmpty(text3) ? null : text3;
        c0606t3.setText(text3);
        m();
        frameLayout.addView(checkableImageButtonA2);
        addView(c0606t3);
        addView(frameLayout);
        addView(checkableImageButtonA);
        textInputLayout.d0.add(c0398mb);
        if (textInputLayout.d != null) {
            c0398mb.a(textInputLayout);
        }
        addOnAttachStateChangeListener(new ViewOnAttachStateChangeListenerC0511q0(3, this));
    }

    public final CheckableImageButton a(ViewGroup viewGroup, LayoutInflater layoutInflater, int i) {
        CheckableImageButton checkableImageButton = (CheckableImageButton) layoutInflater.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0037, viewGroup, false);
        checkableImageButton.setId(i);
        if (X8.I(getContext())) {
            ((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams()).setMarginStart(0);
        }
        return checkableImageButton;
    }

    public final AbstractC0491pb b() {
        AbstractC0491pb c0333k8;
        int i = this.h;
        C0429nb c0429nb = this.g;
        SparseArray sparseArray = c0429nb.a;
        AbstractC0491pb abstractC0491pb = (AbstractC0491pb) sparseArray.get(i);
        if (abstractC0491pb != null) {
            return abstractC0491pb;
        }
        C0460ob c0460ob = c0429nb.b;
        if (i == -1) {
            c0333k8 = new C0333k8(c0460ob, 0);
        } else if (i == 0) {
            c0333k8 = new C0333k8(c0460ob, 1);
        } else if (i == 1) {
            c0333k8 = new Oj(c0460ob, c0429nb.d);
        } else if (i == 2) {
            c0333k8 = new C0393m6(c0460ob);
        } else {
            if (i != 3) {
                throw new IllegalArgumentException(AbstractC0535qo.b(i, "Invalid end icon mode: "));
            }
            c0333k8 = new C0644ua(c0460ob);
        }
        sparseArray.append(i, c0333k8);
        return c0333k8;
    }

    public final int c() {
        int marginStart;
        if (d() || e()) {
            CheckableImageButton checkableImageButton = this.f;
            marginStart = ((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams()).getMarginStart() + checkableImageButton.getMeasuredWidth();
        } else {
            marginStart = 0;
        }
        WeakHashMap weakHashMap = Hs.a;
        return this.n.getPaddingEnd() + getPaddingEnd() + marginStart;
    }

    public final boolean d() {
        return this.b.getVisibility() == 0 && this.f.getVisibility() == 0;
    }

    public final boolean e() {
        return this.c.getVisibility() == 0;
    }

    public final void f(boolean z) {
        boolean z2;
        boolean zIsActivated;
        boolean z3;
        AbstractC0491pb abstractC0491pbB = b();
        boolean zK = abstractC0491pbB.k();
        boolean z4 = true;
        CheckableImageButton checkableImageButton = this.f;
        if (!zK || (z3 = checkableImageButton.d) == abstractC0491pbB.l()) {
            z2 = false;
        } else {
            checkableImageButton.setChecked(!z3);
            z2 = true;
        }
        if (!(abstractC0491pbB instanceof C0644ua) || (zIsActivated = checkableImageButton.isActivated()) == abstractC0491pbB.j()) {
            z4 = z2;
        } else {
            checkableImageButton.setActivated(!zIsActivated);
        }
        if (z || z4) {
            AbstractC0599sr.D(this.a, checkableImageButton, this.j);
        }
    }

    public final void g(int i) {
        PorterDuff.Mode mode = this.k;
        ColorStateList colorStateList = this.j;
        if (this.h == i) {
            return;
        }
        AbstractC0491pb abstractC0491pbB = b();
        C0610t7 c0610t7 = this.r;
        AccessibilityManager accessibilityManager = this.q;
        if (c0610t7 != null && accessibilityManager != null) {
            accessibilityManager.removeTouchExplorationStateChangeListener(new T(c0610t7));
        }
        this.r = null;
        abstractC0491pbB.s();
        this.h = i;
        Iterator it = this.i.iterator();
        if (it.hasNext()) {
            it.next().getClass();
            throw new ClassCastException();
        }
        h(i != 0);
        AbstractC0491pb abstractC0491pbB2 = b();
        int iD = this.g.c;
        if (iD == 0) {
            iD = abstractC0491pbB2.d();
        }
        Drawable drawableV = iD != 0 ? AbstractC0744xh.v(getContext(), iD) : null;
        CheckableImageButton checkableImageButton = this.f;
        checkableImageButton.setImageDrawable(drawableV);
        TextInputLayout textInputLayout = this.a;
        if (drawableV != null) {
            AbstractC0599sr.a(textInputLayout, checkableImageButton, colorStateList, mode);
            AbstractC0599sr.D(textInputLayout, checkableImageButton, colorStateList);
        }
        int iC = abstractC0491pbB2.c();
        CharSequence text = iC != 0 ? getResources().getText(iC) : null;
        if (checkableImageButton.getContentDescription() != text) {
            checkableImageButton.setContentDescription(text);
        }
        boolean zK = abstractC0491pbB2.k();
        if (checkableImageButton.e != zK) {
            checkableImageButton.e = zK;
            checkableImageButton.sendAccessibilityEvent(0);
        }
        if (!abstractC0491pbB2.i(textInputLayout.O)) {
            throw new IllegalStateException("The current box background mode " + textInputLayout.O + " is not supported by the end icon mode " + i);
        }
        abstractC0491pbB2.r();
        C0610t7 c0610t7H = abstractC0491pbB2.h();
        this.r = c0610t7H;
        if (c0610t7H != null && accessibilityManager != null) {
            WeakHashMap weakHashMap = Hs.a;
            if (isAttachedToWindow()) {
                accessibilityManager.addTouchExplorationStateChangeListener(new T(this.r));
            }
        }
        checkableImageButton.setOnClickListener(abstractC0491pbB2.f());
        AbstractC0599sr.G(checkableImageButton);
        EditText editText = this.p;
        if (editText != null) {
            abstractC0491pbB2.m(editText);
            i(abstractC0491pbB2);
        }
        AbstractC0599sr.a(textInputLayout, checkableImageButton, colorStateList, mode);
        f(true);
    }

    public final void h(boolean z) {
        if (d() != z) {
            this.f.setVisibility(z ? 0 : 8);
            j();
            l();
            this.a.v();
        }
    }

    public final void i(AbstractC0491pb abstractC0491pb) {
        if (this.p == null) {
            return;
        }
        if (abstractC0491pb.e() != null) {
            this.p.setOnFocusChangeListener(abstractC0491pb.e());
        }
        if (abstractC0491pb.g() != null) {
            this.f.setOnFocusChangeListener(abstractC0491pb.g());
        }
    }

    public final void j() {
        this.b.setVisibility((this.f.getVisibility() != 0 || e()) ? 8 : 0);
        setVisibility((d() || e() || ((this.m == null || this.o) ? '\b' : (char) 0) == 0) ? 0 : 8);
    }

    public final void k() {
        CheckableImageButton checkableImageButton = this.c;
        Drawable drawable = checkableImageButton.getDrawable();
        TextInputLayout textInputLayout = this.a;
        checkableImageButton.setVisibility((drawable != null && textInputLayout.j.q && textInputLayout.r()) ? 0 : 8);
        j();
        l();
        if (this.h != 0) {
            return;
        }
        textInputLayout.v();
    }

    public final void l() {
        int paddingEnd;
        TextInputLayout textInputLayout = this.a;
        if (textInputLayout.d == null) {
            return;
        }
        if (d() || e()) {
            paddingEnd = 0;
        } else {
            EditText editText = textInputLayout.d;
            WeakHashMap weakHashMap = Hs.a;
            paddingEnd = editText.getPaddingEnd();
        }
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f07025c);
        int paddingTop = textInputLayout.d.getPaddingTop();
        int paddingBottom = textInputLayout.d.getPaddingBottom();
        WeakHashMap weakHashMap2 = Hs.a;
        this.n.setPaddingRelative(dimensionPixelSize, paddingTop, paddingEnd, paddingBottom);
    }

    public final void m() {
        C0606t3 c0606t3 = this.n;
        int visibility = c0606t3.getVisibility();
        int i = (this.m == null || this.o) ? 8 : 0;
        if (visibility != i) {
            b().p(i == 0);
        }
        j();
        c0606t3.setVisibility(i);
        this.a.v();
    }
}
