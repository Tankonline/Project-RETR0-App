package androidx.appcompat.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;
import rikka.shizuku.AbstractC0688vn;
import rikka.shizuku.C0144e3;
import rikka.shizuku.C0389m2;
import rikka.shizuku.C0481p1;
import rikka.shizuku.C0521qa;
import rikka.shizuku.Di;
import rikka.shizuku.Eg;
import rikka.shizuku.Hs;
import rikka.shizuku.Im;
import rikka.shizuku.InterfaceC0547r6;
import rikka.shizuku.Jm;
import rikka.shizuku.K3;
import rikka.shizuku.Km;
import rikka.shizuku.Lm;
import rikka.shizuku.Mm;
import rikka.shizuku.Nk;
import rikka.shizuku.Nm;
import rikka.shizuku.Om;
import rikka.shizuku.RunnableC0607t4;
import rikka.shizuku.ViewOnClickListenerC0572s0;
import rikka.shizuku.ViewOnLayoutChangeListenerC0270i5;

/* JADX INFO: loaded from: classes.dex */
public class SearchView extends Eg implements InterfaceC0547r6 {
    public static final Di P;
    public final Rect A;
    public final int[] B;
    public final int[] C;
    public final ImageView D;
    public final Drawable E;
    public final CharSequence F;
    public final boolean G;
    public boolean H;
    public final CharSequence I;
    public boolean J;
    public final int K;
    public boolean L;
    public int M;
    public final Im N;
    public final Im O;
    public final SearchAutoComplete p;
    public final View q;
    public final View r;
    public final View s;
    public final ImageView t;
    public final ImageView u;
    public final ImageView v;
    public final ImageView w;
    public final View x;
    public Om y;
    public final Rect z;

    public class SearchAutoComplete extends C0389m2 {
        public int e;
        public SearchView f;
        public boolean g;
        public final RunnableC0607t4 h;

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.h = new RunnableC0607t4(12, this);
            this.e = getThreshold();
        }

        public final void a(boolean z) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            RunnableC0607t4 runnableC0607t4 = this.h;
            if (!z) {
                this.g = false;
                removeCallbacks(runnableC0607t4);
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else {
                if (!inputMethodManager.isActive(this)) {
                    this.g = true;
                    return;
                }
                this.g = false;
                removeCallbacks(runnableC0607t4);
                inputMethodManager.showSoftInput(this, 0);
            }
        }

        @Override // android.widget.AutoCompleteTextView
        public final boolean enoughToFilter() {
            return this.e <= 0 || super.enoughToFilter();
        }

        @Override // rikka.shizuku.C0389m2, android.widget.TextView, android.view.View
        public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.g) {
                RunnableC0607t4 runnableC0607t4 = this.h;
                removeCallbacks(runnableC0607t4);
                post(runnableC0607t4);
            }
            return inputConnectionOnCreateInputConnection;
        }

        @Override // android.view.View
        public final void onFinishInflate() {
            int i;
            super.onFinishInflate();
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            Configuration configuration = getResources().getConfiguration();
            int i2 = configuration.screenWidthDp;
            int i3 = configuration.screenHeightDp;
            if (i2 < 960 || i3 < 720 || configuration.orientation != 2) {
                i = (i2 >= 600 || (i2 >= 640 && i3 >= 480)) ? 192 : 160;
            } else {
                i = 256;
            }
            setMinWidth((int) TypedValue.applyDimension(1, i, displayMetrics));
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public final void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            SearchView searchView = this.f;
            searchView.q(searchView.H);
            searchView.post(searchView.N);
            if (searchView.p.hasFocus()) {
                searchView.m();
            }
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public final boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (i == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.f.clearFocus();
                        a(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(i, keyEvent);
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public final void onWindowFocusChanged(boolean z) {
            super.onWindowFocusChanged(z);
            if (z && this.f.hasFocus() && getVisibility() == 0) {
                this.g = true;
                Context context = getContext();
                Di di = SearchView.P;
                if (context.getResources().getConfiguration().orientation == 2) {
                    if (Build.VERSION.SDK_INT >= 29) {
                        Mm.b(this, 1);
                        if (enoughToFilter()) {
                            showDropDown();
                            return;
                        }
                        return;
                    }
                    Di di2 = SearchView.P;
                    di2.getClass();
                    Di.a();
                    Method method = di2.c;
                    if (method != null) {
                        try {
                            method.invoke(this, Boolean.TRUE);
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        }

        @Override // android.widget.AutoCompleteTextView
        public final void performCompletion() {
        }

        @Override // android.widget.AutoCompleteTextView
        public final void replaceText(CharSequence charSequence) {
        }

        @Override // android.widget.AutoCompleteTextView
        public final void setThreshold(int i) {
            super.setThreshold(i);
            this.e = i;
        }
    }

    static {
        Di di = null;
        if (Build.VERSION.SDK_INT < 29) {
            Di di2 = new Di();
            di2.a = null;
            di2.b = null;
            di2.c = null;
            Di.a();
            try {
                Method declaredMethod = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", null);
                di2.a = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            try {
                Method declaredMethod2 = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", null);
                di2.b = declaredMethod2;
                declaredMethod2.setAccessible(true);
            } catch (NoSuchMethodException unused2) {
            }
            try {
                Method method = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
                di2.c = method;
                method.setAccessible(true);
            } catch (NoSuchMethodException unused3) {
            }
            di = di2;
        }
        P = di;
    }

    public SearchView(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void clearFocus() {
        this.J = true;
        super.clearFocus();
        SearchAutoComplete searchAutoComplete = this.p;
        searchAutoComplete.clearFocus();
        searchAutoComplete.a(false);
        this.J = false;
    }

    @Override // rikka.shizuku.InterfaceC0547r6
    public final void d() {
        if (this.L) {
            return;
        }
        this.L = true;
        SearchAutoComplete searchAutoComplete = this.p;
        int imeOptions = searchAutoComplete.getImeOptions();
        this.M = imeOptions;
        searchAutoComplete.setImeOptions(imeOptions | 33554432);
        searchAutoComplete.setText("");
        q(false);
        searchAutoComplete.requestFocus();
        searchAutoComplete.a(true);
    }

    @Override // rikka.shizuku.InterfaceC0547r6
    public final void f() {
        SearchAutoComplete searchAutoComplete = this.p;
        searchAutoComplete.setText("");
        searchAutoComplete.setSelection(searchAutoComplete.length());
        clearFocus();
        q(true);
        searchAutoComplete.setImeOptions(this.M);
        this.L = false;
    }

    public final void m() {
        int i = Build.VERSION.SDK_INT;
        SearchAutoComplete searchAutoComplete = this.p;
        if (i >= 29) {
            Mm.a(searchAutoComplete);
            return;
        }
        Di di = P;
        di.getClass();
        Di.a();
        Method method = di.a;
        if (method != null) {
            try {
                method.invoke(searchAutoComplete, null);
            } catch (Exception unused) {
            }
        }
        di.getClass();
        Di.a();
        Method method2 = di.b;
        if (method2 != null) {
            try {
                method2.invoke(searchAutoComplete, null);
            } catch (Exception unused2) {
            }
        }
    }

    public final void n() {
        boolean zIsEmpty = TextUtils.isEmpty(this.p.getText());
        int i = (!zIsEmpty || (this.G && !this.L)) ? 0 : 8;
        ImageView imageView = this.v;
        imageView.setVisibility(i);
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            drawable.setState(!zIsEmpty ? ViewGroup.ENABLED_STATE_SET : ViewGroup.EMPTY_STATE_SET);
        }
    }

    public final void o() {
        int[] iArr = this.p.hasFocus() ? ViewGroup.FOCUSED_STATE_SET : ViewGroup.EMPTY_STATE_SET;
        Drawable background = this.r.getBackground();
        if (background != null) {
            background.setState(iArr);
        }
        Drawable background2 = this.s.getBackground();
        if (background2 != null) {
            background2.setState(iArr);
        }
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        removeCallbacks(this.N);
        post(this.O);
        super.onDetachedFromWindow();
    }

    @Override // rikka.shizuku.Eg, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            int[] iArr = this.B;
            SearchAutoComplete searchAutoComplete = this.p;
            searchAutoComplete.getLocationInWindow(iArr);
            int[] iArr2 = this.C;
            getLocationInWindow(iArr2);
            int i5 = iArr[1] - iArr2[1];
            int i6 = iArr[0] - iArr2[0];
            int width = searchAutoComplete.getWidth() + i6;
            int height = searchAutoComplete.getHeight() + i5;
            Rect rect = this.z;
            rect.set(i6, i5, width, height);
            int i7 = rect.left;
            int i8 = rect.right;
            int i9 = i4 - i2;
            Rect rect2 = this.A;
            rect2.set(i7, 0, i8, i9);
            Om om = this.y;
            if (om == null) {
                Om om2 = new Om(rect2, rect, searchAutoComplete);
                this.y = om2;
                setTouchDelegate(om2);
            } else {
                om.b.set(rect2);
                Rect rect3 = om.d;
                rect3.set(rect2);
                int i10 = -om.e;
                rect3.inset(i10, i10);
                om.c.set(rect);
            }
        }
    }

    @Override // rikka.shizuku.Eg, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        if (this.H) {
            super.onMeasure(i, i2);
            return;
        }
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode == Integer.MIN_VALUE) {
            int i4 = this.K;
            size = i4 > 0 ? Math.min(i4, size) : Math.min(getContext().getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070037), size);
        } else if (mode == 0) {
            size = this.K;
            if (size <= 0) {
                size = getContext().getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070037);
            }
        } else if (mode == 1073741824 && (i3 = this.K) > 0) {
            size = Math.min(i3, size);
        }
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode2 == Integer.MIN_VALUE) {
            size2 = Math.min(getContext().getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070036), size2);
        } else if (mode2 == 0) {
            size2 = getContext().getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f070036);
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Nm)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Nm nm = (Nm) parcelable;
        super.onRestoreInstanceState(nm.a);
        q(nm.c);
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        Nm nm = new Nm(super.onSaveInstanceState());
        nm.c = this.H;
        return nm;
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        post(this.N);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void p() {
        Drawable drawable;
        CharSequence charSequence;
        CharSequence charSequence2 = this.I;
        CharSequence charSequence3 = charSequence2;
        if (charSequence2 == null) {
            charSequence = this.F;
        }
        if (charSequence3 == null) {
            charSequence3 = charSequence;
            charSequence3 = "";
        }
        charSequence3 = charSequence;
        boolean z = this.G;
        SearchAutoComplete searchAutoComplete = this.p;
        CharSequence charSequence4 = charSequence3;
        if (z && (drawable = this.E) != null) {
            charSequence4 = charSequence3;
            int textSize = (int) (((double) searchAutoComplete.getTextSize()) * 1.25d);
            drawable.setBounds(0, 0, textSize, textSize);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
            spannableStringBuilder.setSpan(new ImageSpan(drawable), 1, 2, 33);
            spannableStringBuilder.append(charSequence3);
            charSequence4 = spannableStringBuilder;
        }
        charSequence4 = charSequence3;
        searchAutoComplete.setHint(charSequence4);
    }

    public final void q(boolean z) {
        this.H = z;
        int i = z ? 0 : 8;
        TextUtils.isEmpty(this.p.getText());
        this.t.setVisibility(i);
        this.u.setVisibility(8);
        this.q.setVisibility(z ? 8 : 0);
        ImageView imageView = this.D;
        imageView.setVisibility((imageView.getDrawable() == null || this.G) ? 8 : 0);
        n();
        this.w.setVisibility(8);
        this.s.setVisibility(8);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean requestFocus(int i, Rect rect) {
        if (this.J || !isFocusable()) {
            return false;
        }
        if (this.H) {
            return super.requestFocus(i, rect);
        }
        boolean zRequestFocus = this.p.requestFocus(i, rect);
        if (zRequestFocus) {
            q(false);
        }
        return zRequestFocus;
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr._0_resource_name_obfuscated_res_0x7f040416);
    }

    public SearchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.z = new Rect();
        this.A = new Rect();
        this.B = new int[2];
        this.C = new int[2];
        this.N = new Im(this, 0);
        this.O = new Im(this, 1);
        new WeakHashMap();
        ViewOnClickListenerC0572s0 viewOnClickListenerC0572s0 = new ViewOnClickListenerC0572s0(4, this);
        Km km = new Km(0, this);
        Lm lm = new Lm(this);
        int i2 = 2;
        C0144e3 c0144e3 = new C0144e3(i2, this);
        C0521qa c0521qa = new C0521qa(i2, this);
        C0481p1 c0481p1 = new C0481p1(1, this);
        int[] iArr = Nk.u;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        K3 k3 = new K3(context, typedArrayObtainStyledAttributes);
        Hs.l(this, context, iArr, attributeSet, typedArrayObtainStyledAttributes, i);
        LayoutInflater.from(context).inflate(typedArrayObtainStyledAttributes.getResourceId(19, R.layout._0_resource_name_obfuscated_res_0x7f0c0019), (ViewGroup) this, true);
        SearchAutoComplete searchAutoComplete = (SearchAutoComplete) findViewById(R.id._0_resource_name_obfuscated_res_0x7f090182);
        this.p = searchAutoComplete;
        searchAutoComplete.f = this;
        this.q = findViewById(R.id._0_resource_name_obfuscated_res_0x7f09017e);
        View viewFindViewById = findViewById(R.id._0_resource_name_obfuscated_res_0x7f090181);
        this.r = viewFindViewById;
        View viewFindViewById2 = findViewById(R.id._0_resource_name_obfuscated_res_0x7f0901b3);
        this.s = viewFindViewById2;
        ImageView imageView = (ImageView) findViewById(R.id._0_resource_name_obfuscated_res_0x7f09017c);
        this.t = imageView;
        ImageView imageView2 = (ImageView) findViewById(R.id._0_resource_name_obfuscated_res_0x7f09017f);
        this.u = imageView2;
        ImageView imageView3 = (ImageView) findViewById(R.id._0_resource_name_obfuscated_res_0x7f09017d);
        this.v = imageView3;
        ImageView imageView4 = (ImageView) findViewById(R.id._0_resource_name_obfuscated_res_0x7f090183);
        this.w = imageView4;
        ImageView imageView5 = (ImageView) findViewById(R.id._0_resource_name_obfuscated_res_0x7f090180);
        this.D = imageView5;
        viewFindViewById.setBackground(k3.i(20));
        viewFindViewById2.setBackground(k3.i(25));
        imageView.setImageDrawable(k3.i(23));
        imageView2.setImageDrawable(k3.i(15));
        imageView3.setImageDrawable(k3.i(12));
        imageView4.setImageDrawable(k3.i(28));
        imageView5.setImageDrawable(k3.i(23));
        this.E = k3.i(22);
        AbstractC0688vn.y(imageView, getResources().getString(R.string._0_resource_name_obfuscated_res_0x7f110015));
        typedArrayObtainStyledAttributes.getResourceId(26, R.layout._0_resource_name_obfuscated_res_0x7f0c0018);
        typedArrayObtainStyledAttributes.getResourceId(13, 0);
        imageView.setOnClickListener(viewOnClickListenerC0572s0);
        imageView3.setOnClickListener(viewOnClickListenerC0572s0);
        imageView2.setOnClickListener(viewOnClickListenerC0572s0);
        imageView4.setOnClickListener(viewOnClickListenerC0572s0);
        searchAutoComplete.setOnClickListener(viewOnClickListenerC0572s0);
        searchAutoComplete.addTextChangedListener(c0481p1);
        searchAutoComplete.setOnEditorActionListener(lm);
        searchAutoComplete.setOnItemClickListener(c0144e3);
        searchAutoComplete.setOnItemSelectedListener(c0521qa);
        searchAutoComplete.setOnKeyListener(km);
        searchAutoComplete.setOnFocusChangeListener(new Jm(this));
        boolean z = typedArrayObtainStyledAttributes.getBoolean(18, true);
        if (this.G != z) {
            this.G = z;
            q(z);
            p();
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, -1);
        if (dimensionPixelSize != -1) {
            this.K = dimensionPixelSize;
            requestLayout();
        }
        this.F = typedArrayObtainStyledAttributes.getText(14);
        this.I = typedArrayObtainStyledAttributes.getText(21);
        int i3 = typedArrayObtainStyledAttributes.getInt(6, -1);
        if (i3 != -1) {
            searchAutoComplete.setImeOptions(i3);
        }
        int i4 = typedArrayObtainStyledAttributes.getInt(5, -1);
        if (i4 != -1) {
            searchAutoComplete.setInputType(i4);
        }
        setFocusable(typedArrayObtainStyledAttributes.getBoolean(1, true));
        k3.o();
        Intent intent = new Intent("android.speech.action.WEB_SEARCH");
        intent.addFlags(268435456);
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        new Intent("android.speech.action.RECOGNIZE_SPEECH").addFlags(268435456);
        View viewFindViewById3 = findViewById(searchAutoComplete.getDropDownAnchor());
        this.x = viewFindViewById3;
        if (viewFindViewById3 != null) {
            viewFindViewById3.addOnLayoutChangeListener(new ViewOnLayoutChangeListenerC0270i5(2, this));
        }
        q(this.G);
        p();
    }
}
