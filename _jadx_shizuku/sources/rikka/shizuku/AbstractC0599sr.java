package rikka.shizuku;

import android.R;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ParceledListSlice;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ParagraphStyle;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ImageView;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/* JADX INFO: renamed from: rikka.shizuku.sr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0599sr {
    public static Field A = null;
    public static boolean B = false;
    public static Class C = null;
    public static boolean D = false;
    public static Field E = null;
    public static boolean F = false;
    public static Field G = null;
    public static boolean H = false;
    public static Di n = null;
    public static boolean w = false;
    public static Method x = null;
    public static boolean y = false;
    public static Field z;
    public static final float[][] a = {new float[]{0.401288f, 0.650173f, -0.051461f}, new float[]{-0.250268f, 1.204414f, 0.045854f}, new float[]{-0.002079f, 0.048952f, 0.953127f}};
    public static final float[][] b = {new float[]{1.8620678f, -1.0112547f, 0.14918678f}, new float[]{0.38752654f, 0.62144744f, -0.00897398f}, new float[]{-0.0158415f, -0.03412294f, 1.0499644f}};
    public static final float[] c = {95.047f, 100.0f, 108.883f};
    public static final float[][] d = {new float[]{0.41233894f, 0.35762063f, 0.18051042f}, new float[]{0.2126f, 0.7152f, 0.0722f}, new float[]{0.01932141f, 0.11916382f, 0.9503448f}};
    public static final String[] e = {"standard", "accelerate", "decelerate", "linear"};
    public static final C0589sh f = new C0589sh(1, "COMPLETING_ALREADY");
    public static final C0589sh g = new C0589sh(1, "COMPLETING_WAITING_CHILDREN");
    public static final C0589sh h = new C0589sh(1, "COMPLETING_RETRY");
    public static final C0589sh i = new C0589sh(1, "TOO_LATE_TO_CANCEL");
    public static final C0589sh j = new C0589sh(1, "SEALED");
    public static final C0091cb k = new C0091cb(false);
    public static final C0091cb l = new C0091cb(true);
    public static final Di m = new Di(null, null, null);
    public static final Se o = new Se();
    public static final Te p = new Te();
    public static final Aa q = new Aa();
    public static final C0589sh r = new C0589sh(1, "NO_THREAD_ELEMENTS");
    public static final F6 s = new F6(5);
    public static final F6 t = new F6(6);
    public static final F6 u = new F6(7);
    public static final int[] v = {R.attr.stateListAnimator};

    public static float A(int i2) {
        float f2 = i2 / 255.0f;
        return (f2 <= 0.04045f ? f2 / 12.92f : (float) Math.pow((f2 + 0.055f) / 1.055f, 2.4000000953674316d)) * 100.0f;
    }

    public static void D(TextInputLayout textInputLayout, CheckableImageButton checkableImageButton, ColorStateList colorStateList) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (checkableImageButton.getDrawable() == null || colorStateList == null || !colorStateList.isStateful()) {
            return;
        }
        int[] drawableState = textInputLayout.getDrawableState();
        int[] drawableState2 = checkableImageButton.getDrawableState();
        int length = drawableState.length;
        int[] iArrCopyOf = Arrays.copyOf(drawableState, drawableState.length + drawableState2.length);
        System.arraycopy(drawableState2, 0, iArrCopyOf, length, drawableState2.length);
        int colorForState = colorStateList.getColorForState(iArrCopyOf, colorStateList.getDefaultColor());
        Drawable drawableMutate = drawable.mutate();
        drawableMutate.setTintList(ColorStateList.valueOf(colorForState));
        checkableImageButton.setImageDrawable(drawableMutate);
    }

    public static final void E(V7 v7, Object obj) {
        if (obj == r) {
            return;
        }
        if (!(obj instanceof Pp)) {
            AbstractC0535qo.h(v7.l(null, t));
            throw null;
        }
        Pp pp = (Pp) obj;
        Mp[] mpArr = pp.b;
        int length = mpArr.length - 1;
        if (length < 0) {
            return;
        }
        Mp mp = mpArr[length];
        Object obj2 = pp.a[length];
        throw null;
    }

    public static void F(View view, float f2) {
        int integer = view.getResources().getInteger(moe.shizuku.privileged.api.R.integer._0_resource_name_obfuscated_res_0x7f0a0002);
        StateListAnimator stateListAnimator = new StateListAnimator();
        long j2 = integer;
        stateListAnimator.addState(new int[]{R.attr.state_enabled, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f040463, -2130969700}, ObjectAnimator.ofFloat(view, "elevation", 0.0f).setDuration(j2));
        stateListAnimator.addState(new int[]{R.attr.state_enabled}, ObjectAnimator.ofFloat(view, "elevation", f2).setDuration(j2));
        stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(view, "elevation", 0.0f).setDuration(0L));
        view.setStateListAnimator(stateListAnimator);
    }

    public static void G(CheckableImageButton checkableImageButton) {
        WeakHashMap weakHashMap = Hs.a;
        boolean zHasOnClickListeners = checkableImageButton.hasOnClickListeners();
        checkableImageButton.setFocusable(zHasOnClickListeners);
        checkableImageButton.setClickable(zHasOnClickListeners);
        checkableImageButton.f = zHasOnClickListeners;
        checkableImageButton.setLongClickable(false);
        checkableImageButton.setImportantForAccessibility(zHasOnClickListeners ? 1 : 2);
    }

    public static void H(View view, Xh xh) {
        Ga ga = xh.a.b;
        if (ga == null || !ga.a) {
            return;
        }
        float fE = 0.0f;
        for (ViewParent parent = view.getParent(); parent instanceof View; parent = parent.getParent()) {
            WeakHashMap weakHashMap = Hs.a;
            fE += AbstractC0817zs.e((View) parent);
        }
        Wh wh = xh.a;
        if (wh.l != fE) {
            wh.l = fE;
            xh.p();
        }
    }

    public static void I(Drawable drawable, int i2) {
        drawable.setTint(i2);
    }

    public static void J(Drawable drawable, ColorStateList colorStateList) {
        drawable.setTintList(colorStateList);
    }

    public static void K(Drawable drawable, PorterDuff.Mode mode) {
        drawable.setTintMode(mode);
    }

    public static final void L(String str, X1 x1) {
        String parent = new File(str).getParent();
        Map map = Z1.a;
        Object obj = map.get(parent);
        Object obj2 = obj;
        if (obj == null) {
            Y1 y1 = new Y1(parent);
            y1.startWatching();
            map.put(parent, y1);
            obj2 = y1;
        }
        ((Y1) obj2).a.add(x1);
    }

    public static final void M(X1 x1) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : Z1.a.entrySet()) {
            String str = (String) entry.getKey();
            Y1 y1 = (Y1) entry.getValue();
            y1.a.remove(x1);
            if (y1.a.isEmpty()) {
                arrayList.add(str);
            }
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            Y1 y2 = (Y1) Z1.a.remove((String) obj);
            if (y2 != null) {
                y2.stopWatching();
            }
        }
    }

    public static int N(Context context, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.style.Animation.Activity, new int[]{i2});
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, -1);
        typedArrayObtainStyledAttributes.recycle();
        return resourceId;
    }

    public static final Object O(V7 v7, Object obj) {
        if (obj == null) {
            obj = v7.l(0, s);
        }
        if (obj == 0) {
            return r;
        }
        if (obj instanceof Integer) {
            return v7.l(new Pp(v7, ((Number) obj).intValue()), u);
        }
        AbstractC0535qo.h(obj);
        throw null;
    }

    public static final Object P(W7 w7, F3 f3, G3 g3) throws Throwable {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
        InterfaceC0187ff interfaceC0187ff;
        w7.getClass();
        V7 v7 = g3.b;
        V7 v7B = v7.b(w7);
        Ef ef = (Ef) v7B.g(Fe.a);
        if (ef != null && !ef.a()) {
            throw ef.c();
        }
        if (v7B == v7) {
            Em em = new Em(v7B, g3);
            return Hn.m(em, em, f3);
        }
        Aa aa = Aa.a;
        if (AbstractC0046as.c(v7B.g(aa), v7.g(aa))) {
            Gr gr = new Gr(v7B, g3);
            V7 v8 = gr.c;
            Object objO = O(v8, null);
            try {
                return Hn.m(gr, gr, f3);
            } finally {
                E(v8, objO);
            }
        }
        C0028aa c0028aa = new C0028aa(v7B, g3);
        try {
            X8.U(Pe.b, AbstractC0744xh.C(AbstractC0744xh.m(c0028aa, c0028aa, f3)));
            do {
                atomicIntegerFieldUpdater = C0028aa.e;
                int i2 = atomicIntegerFieldUpdater.get(c0028aa);
                if (i2 != 0) {
                    if (i2 != 2) {
                        throw new IllegalStateException("Already suspended");
                    }
                    Object obj = Kf.a.get(c0028aa);
                    C0218gf c0218gf = obj instanceof C0218gf ? (C0218gf) obj : null;
                    if (c0218gf != null && (interfaceC0187ff = c0218gf.a) != null) {
                        obj = interfaceC0187ff;
                    }
                    if (obj instanceof J6) {
                        throw ((J6) obj).a;
                    }
                    return obj;
                }
            } while (!atomicIntegerFieldUpdater.compareAndSet(c0028aa, 0, 1));
            return EnumC0057b8.a;
        } catch (Throwable th) {
            th = th;
            if (th instanceof Y9) {
                th = ((Y9) th).a;
            }
            c0028aa.h(new C0071bm(th));
            throw th;
        }
    }

    public static float Q() {
        return ((float) Math.pow((((double) 50.0f) + 16.0d) / 116.0d, 3.0d)) * 100.0f;
    }

    public static void a(TextInputLayout textInputLayout, CheckableImageButton checkableImageButton, ColorStateList colorStateList, PorterDuff.Mode mode) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (drawable != null) {
            drawable = drawable.mutate();
            if (colorStateList == null || !colorStateList.isStateful()) {
                drawable.setTintList(colorStateList);
            } else {
                int[] drawableState = textInputLayout.getDrawableState();
                int[] drawableState2 = checkableImageButton.getDrawableState();
                int length = drawableState.length;
                int[] iArrCopyOf = Arrays.copyOf(drawableState, drawableState.length + drawableState2.length);
                System.arraycopy(drawableState2, 0, iArrCopyOf, length, drawableState2.length);
                drawable.setTintList(ColorStateList.valueOf(colorStateList.getColorForState(iArrCopyOf, colorStateList.getDefaultColor())));
            }
            if (mode != null) {
                drawable.setTintMode(mode);
            }
        }
        if (checkableImageButton.getDrawable() != drawable) {
            checkableImageButton.setImageDrawable(drawable);
        }
    }

    public static void b(Object obj) {
        if (obj == null || y(2, obj)) {
            return;
        }
        ClassCastException classCastException = new ClassCastException(obj.getClass().getName().concat(" cannot be cast to kotlin.jvm.functions.Function2"));
        String name = AbstractC0599sr.class.getName();
        StackTraceElement[] stackTrace = classCastException.getStackTrace();
        int length = stackTrace.length;
        int i2 = -1;
        for (int i3 = 0; i3 < length; i3++) {
            if (name.equals(stackTrace[i3].getClassName())) {
                i2 = i3;
            }
        }
        classCastException.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i2 + 1, length));
        throw classCastException;
    }

    public static final void c(View view) {
        Os os = new Os(view, null);
        Sm sm = new Sm();
        sm.d = AbstractC0744xh.m(sm, sm, os);
        while (sm.hasNext()) {
            View view2 = (View) sm.next();
            Xj xj = (Xj) view2.getTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090158);
            if (xj == null) {
                xj = new Xj();
                view2.setTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f090158, xj);
            }
            ArrayList arrayList = xj.a;
            int iA0 = AbstractC0702w6.a0(arrayList);
            if (-1 < iA0) {
                arrayList.get(iA0).getClass();
                throw new ClassCastException();
            }
        }
    }

    public static final void d(Closeable closeable, Throwable th) {
        try {
            closeable.close();
        } catch (Throwable th2) {
            AbstractC0744xh.a(th, th2);
        }
    }

    public static int e(C0408ml c0408ml, Ja ja, View view, View view2, AbstractC0162el abstractC0162el, boolean z2) {
        if (abstractC0162el.v() == 0 || c0408ml.b() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z2) {
            return Math.abs(AbstractC0162el.G(view) - AbstractC0162el.G(view2)) + 1;
        }
        return Math.min(ja.l(), ja.b(view2) - ja.e(view));
    }

    public static int f(C0408ml c0408ml, Ja ja, View view, View view2, AbstractC0162el abstractC0162el, boolean z2, boolean z3) {
        if (abstractC0162el.v() == 0 || c0408ml.b() == 0 || view == null || view2 == null) {
            return 0;
        }
        int iMax = z3 ? Math.max(0, (c0408ml.b() - Math.max(AbstractC0162el.G(view), AbstractC0162el.G(view2))) - 1) : Math.max(0, Math.min(AbstractC0162el.G(view), AbstractC0162el.G(view2)));
        if (z2) {
            return Math.round((iMax * (Math.abs(ja.b(view2) - ja.e(view)) / (Math.abs(AbstractC0162el.G(view) - AbstractC0162el.G(view2)) + 1))) + (ja.k() - ja.e(view)));
        }
        return iMax;
    }

    public static int g(C0408ml c0408ml, Ja ja, View view, View view2, AbstractC0162el abstractC0162el, boolean z2) {
        if (abstractC0162el.v() == 0 || c0408ml.b() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z2) {
            return c0408ml.b();
        }
        return (int) (((ja.b(view2) - ja.e(view)) / (Math.abs(AbstractC0162el.G(view) - AbstractC0162el.G(view2)) + 1)) * c0408ml.b());
    }

    public static ImageView.ScaleType h(int i2) {
        if (i2 == 0) {
            return ImageView.ScaleType.FIT_XY;
        }
        if (i2 == 1) {
            return ImageView.ScaleType.FIT_START;
        }
        if (i2 == 2) {
            return ImageView.ScaleType.FIT_CENTER;
        }
        if (i2 == 3) {
            return ImageView.ScaleType.FIT_END;
        }
        if (i2 != 5) {
            return i2 != 6 ? ImageView.ScaleType.CENTER : ImageView.ScaleType.CENTER_INSIDE;
        }
        return ImageView.ScaleType.CENTER_CROP;
    }

    public static AbstractC0046as i(int i2) {
        if (i2 != 0) {
            return i2 != 1 ? new C0256hm() : new C0457o8();
        }
        return new C0256hm();
    }

    public static boolean j(View view, KeyEvent keyEvent) {
        ArrayList arrayList;
        int size;
        int iIndexOfKey;
        WeakHashMap weakHashMap = Hs.a;
        if (Build.VERSION.SDK_INT >= 28) {
            return false;
        }
        ArrayList arrayList2 = Gs.d;
        Gs gs = (Gs) view.getTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901c8);
        WeakReference weakReference = null;
        if (gs == null) {
            gs = new Gs();
            gs.a = null;
            gs.b = null;
            gs.c = null;
            view.setTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901c8, gs);
        }
        WeakReference weakReference2 = gs.c;
        if (weakReference2 != null && weakReference2.get() == keyEvent) {
            return false;
        }
        gs.c = new WeakReference(keyEvent);
        if (gs.b == null) {
            gs.b = new SparseArray();
        }
        SparseArray sparseArray = gs.b;
        if (keyEvent.getAction() == 1 && (iIndexOfKey = sparseArray.indexOfKey(keyEvent.getKeyCode())) >= 0) {
            weakReference = (WeakReference) sparseArray.valueAt(iIndexOfKey);
            sparseArray.removeAt(iIndexOfKey);
        }
        if (weakReference == null) {
            weakReference = (WeakReference) sparseArray.get(keyEvent.getKeyCode());
        }
        if (weakReference == null) {
            return false;
        }
        View view2 = (View) weakReference.get();
        if (view2 == null || !view2.isAttachedToWindow() || (arrayList = (ArrayList) view2.getTag(moe.shizuku.privileged.api.R.id._0_resource_name_obfuscated_res_0x7f0901c9)) == null || (size = arrayList.size() - 1) < 0) {
            return true;
        }
        arrayList.get(size).getClass();
        throw new ClassCastException();
    }

    public static boolean k(InterfaceC0065bg interfaceC0065bg, View view, Window.Callback callback, KeyEvent keyEvent) {
        DialogInterface.OnKeyListener onKeyListener;
        boolean zBooleanValue = false;
        if (interfaceC0065bg != null) {
            if (Build.VERSION.SDK_INT >= 28) {
                return interfaceC0065bg.c(keyEvent);
            }
            if (callback instanceof Activity) {
                Activity activity = (Activity) callback;
                activity.onUserInteraction();
                Window window = activity.getWindow();
                if (window.hasFeature(8)) {
                    ActionBar actionBar = activity.getActionBar();
                    if (keyEvent.getKeyCode() == 82 && actionBar != null) {
                        if (!w) {
                            try {
                                x = actionBar.getClass().getMethod("onMenuKeyEvent", KeyEvent.class);
                            } catch (NoSuchMethodException unused) {
                            }
                            w = true;
                        }
                        Method method = x;
                        if (method != null) {
                            try {
                                Object objInvoke = method.invoke(actionBar, keyEvent);
                                if (objInvoke != null) {
                                    zBooleanValue = ((Boolean) objInvoke).booleanValue();
                                }
                            } catch (IllegalAccessException | InvocationTargetException unused2) {
                            }
                        }
                        if (zBooleanValue) {
                            return true;
                        }
                    }
                }
                if (window.superDispatchKeyEvent(keyEvent)) {
                    return true;
                }
                View decorView = window.getDecorView();
                if (Hs.c(decorView, keyEvent)) {
                    return true;
                }
                return keyEvent.dispatch(activity, decorView != null ? decorView.getKeyDispatcherState() : null, activity);
            }
            if (callback instanceof Dialog) {
                Dialog dialog = (Dialog) callback;
                if (!y) {
                    try {
                        Field declaredField = Dialog.class.getDeclaredField("mOnKeyListener");
                        z = declaredField;
                        declaredField.setAccessible(true);
                    } catch (NoSuchFieldException unused3) {
                    }
                    y = true;
                }
                Field field = z;
                if (field != null) {
                    try {
                        onKeyListener = (DialogInterface.OnKeyListener) field.get(dialog);
                    } catch (IllegalAccessException unused4) {
                        onKeyListener = null;
                    }
                } else {
                    onKeyListener = null;
                }
                if (onKeyListener != null && onKeyListener.onKey(dialog, keyEvent.getKeyCode(), keyEvent)) {
                    return true;
                }
                Window window2 = dialog.getWindow();
                if (window2.superDispatchKeyEvent(keyEvent)) {
                    return true;
                }
                View decorView2 = window2.getDecorView();
                if (Hs.c(decorView2, keyEvent)) {
                    return true;
                }
                return keyEvent.dispatch(dialog, decorView2 != null ? decorView2.getKeyDispatcherState() : null, dialog);
            }
            if ((view != null && Hs.c(view, keyEvent)) || interfaceC0065bg.c(keyEvent)) {
                return true;
            }
        }
        return false;
    }

    public static final void l(Bm bm) {
        InterfaceC0780ym interfaceC0780ym;
        EnumC0588sg enumC0588sg = bm.e().c;
        if (enumC0588sg != EnumC0588sg.b && enumC0588sg != EnumC0588sg.c) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        Am am = (Am) bm.b().b;
        synchronized (am.c) {
            Iterator it = am.d.entrySet().iterator();
            do {
                interfaceC0780ym = null;
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it.next();
                String str = (String) entry.getKey();
                InterfaceC0780ym interfaceC0780ym2 = (InterfaceC0780ym) entry.getValue();
                if (AbstractC0046as.c(str, "androidx.lifecycle.internal.SavedStateHandlesProvider")) {
                    interfaceC0780ym = interfaceC0780ym2;
                }
            } while (interfaceC0780ym == null);
        }
        if (interfaceC0780ym == null) {
            C0687vm c0687vm = new C0687vm(bm.b(), (InterfaceC0047at) bm);
            bm.b().S("androidx.lifecycle.internal.SavedStateHandlesProvider", c0687vm);
            bm.e().a(new Tk(4, c0687vm));
        }
    }

    public static Spanned m(int i2, String str) {
        Nj nj = new Nj();
        nj.a = nj;
        nj.b = nj;
        nj.c = nj;
        nj.d = nj;
        nj.e = nj;
        nj.i = true;
        int i3 = 0;
        nj.j = false;
        nj.k = false;
        nj.l = true;
        nj.m = true;
        nj.n = false;
        nj.o = true;
        nj.p = false;
        nj.q = true;
        HashMap map = new HashMap();
        nj.r = map;
        Boolean bool = Boolean.TRUE;
        map.put("http://xml.org/sax/features/namespaces", bool);
        Boolean bool2 = Boolean.FALSE;
        map.put("http://xml.org/sax/features/namespace-prefixes", bool2);
        map.put("http://xml.org/sax/features/external-general-entities", bool2);
        map.put("http://xml.org/sax/features/external-parameter-entities", bool2);
        map.put("http://xml.org/sax/features/is-standalone", bool2);
        map.put("http://xml.org/sax/features/lexical-handler/parameter-entities", bool2);
        map.put("http://xml.org/sax/features/resolve-dtd-uris", bool);
        map.put("http://xml.org/sax/features/string-interning", bool);
        map.put("http://xml.org/sax/features/use-attributes2", bool2);
        map.put("http://xml.org/sax/features/use-locator2", bool2);
        map.put("http://xml.org/sax/features/use-entity-resolver2", bool2);
        map.put("http://xml.org/sax/features/validation", bool2);
        map.put("http://xml.org/sax/features/xmlns-uris", bool2);
        map.put("http://xml.org/sax/features/xmlns-uris", bool2);
        map.put("http://xml.org/sax/features/xml-1.1", bool2);
        map.put("http://www.ccil.org/~cowan/tagsoup/features/ignore-bogons", bool2);
        map.put("http://www.ccil.org/~cowan/tagsoup/features/bogons-empty", bool2);
        map.put("http://www.ccil.org/~cowan/tagsoup/features/root-bogons", bool);
        map.put("http://www.ccil.org/~cowan/tagsoup/features/default-attributes", bool);
        map.put("http://www.ccil.org/~cowan/tagsoup/features/translate-colons", bool2);
        map.put("http://www.ccil.org/~cowan/tagsoup/features/restart-elements", bool);
        map.put("http://www.ccil.org/~cowan/tagsoup/features/ignorable-whitespace", bool2);
        map.put("http://www.ccil.org/~cowan/tagsoup/features/cdata-elements", bool);
        nj.s = null;
        nj.t = null;
        nj.u = false;
        nj.v = null;
        nj.w = null;
        nj.x = null;
        nj.y = null;
        nj.z = null;
        nj.A = null;
        nj.B = null;
        nj.C = true;
        try {
            try {
                nj.setProperty("http://www.ccil.org/~cowan/tagsoup/properties/schema", Be.a);
                Ue ue = new Ue();
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                ue.a = spannableStringBuilder;
                nj.a = ue;
                try {
                    nj.parse(new InputSource(new StringReader(str)));
                    Object[] spans = spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ParagraphStyle.class);
                    for (int i4 = 0; i4 < spans.length; i4++) {
                        int spanStart = spannableStringBuilder.getSpanStart(spans[i4]);
                        int spanEnd = spannableStringBuilder.getSpanEnd(spans[i4]);
                        int i5 = spanEnd - 2;
                        if (i5 >= 0 && spannableStringBuilder.charAt(spanEnd - 1) == '\n' && spannableStringBuilder.charAt(i5) == '\n') {
                            spanEnd--;
                        }
                        if (spanEnd == spanStart) {
                            spannableStringBuilder.removeSpan(spans[i4]);
                        } else {
                            spannableStringBuilder.setSpan(spans[i4], spanStart, spanEnd, 51);
                        }
                    }
                    if ((i2 & 512) <= 0) {
                        return spannableStringBuilder;
                    }
                    int length = spannableStringBuilder.length();
                    while (true) {
                        int i6 = length - 1;
                        if (i6 < 0 || !Character.isWhitespace(spannableStringBuilder.charAt(i6))) {
                            break;
                        }
                        length = i6;
                    }
                    Spanned spanned = (Spanned) spannableStringBuilder.subSequence(0, length);
                    int length2 = spanned.length();
                    while (i3 < length2 && Character.isWhitespace(spanned.charAt(i3))) {
                        i3++;
                    }
                    return (Spanned) spanned.subSequence(i3, spanned.length());
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                } catch (SAXException e3) {
                    throw new RuntimeException(e3);
                }
            } catch (SAXNotSupportedException e4) {
                e = e4;
                throw new RuntimeException(e);
            }
        } catch (SAXNotRecognizedException | SAXNotSupportedException e5) {
            e = e5;
            throw new RuntimeException(e);
        }
    }

    public static ApplicationInfo n(int i2, String str) {
        try {
            return Build.VERSION.SDK_INT >= 33 ? AbstractC0134dn.c.a().getApplicationInfo(str, 0L, i2) : AbstractC0134dn.c.a().getApplicationInfo(str, (int) 0, i2);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static ColorStateList o(Context context, int i2) {
        ColorStateList colorStateListA;
        ColorStateList colorStateList;
        Ul ul;
        Resources resources = context.getResources();
        Resources.Theme theme = context.getTheme();
        Vl vl = new Vl(resources, theme);
        synchronized (Xl.c) {
            try {
                SparseArray sparseArray = (SparseArray) Xl.b.get(vl);
                colorStateListA = null;
                if (sparseArray == null || sparseArray.size() <= 0 || (ul = (Ul) sparseArray.get(i2)) == null) {
                    colorStateList = null;
                } else {
                    if (ul.b.equals(resources.getConfiguration())) {
                        if (theme != null || ul.c != 0) {
                            if (theme == null || ul.c != theme.hashCode()) {
                            }
                        }
                        colorStateList = ul.a;
                    }
                    sparseArray.remove(i2);
                    colorStateList = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (colorStateList != null) {
            return colorStateList;
        }
        ThreadLocal threadLocal = Xl.a;
        TypedValue typedValue = (TypedValue) threadLocal.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            threadLocal.set(typedValue);
        }
        resources.getValue(i2, typedValue, true);
        int i3 = typedValue.type;
        if (i3 < 28 || i3 > 31) {
            try {
                colorStateListA = D6.a(resources, resources.getXml(i2), theme);
            } catch (Exception e2) {
                Log.w("ResourcesCompat", "Failed to inflate ColorStateList, leaving it to the framework", e2);
            }
        }
        if (colorStateListA == null) {
            return resources.getColorStateList(i2, theme);
        }
        synchronized (Xl.c) {
            try {
                WeakHashMap weakHashMap = Xl.b;
                SparseArray sparseArray2 = (SparseArray) weakHashMap.get(vl);
                if (sparseArray2 == null) {
                    sparseArray2 = new SparseArray();
                    weakHashMap.put(vl, sparseArray2);
                }
                sparseArray2.append(i2, new Ul(colorStateListA, vl.a.getConfiguration(), theme));
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return colorStateListA;
    }

    public static void p(ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        if (it.hasNext()) {
            it.next().getClass();
            throw new ClassCastException();
        }
    }

    public static List q(int i2, long j2) {
        try {
            ParceledListSlice installedPackages = Build.VERSION.SDK_INT >= 33 ? AbstractC0134dn.c.a().getInstalledPackages(j2, i2) : AbstractC0134dn.c.a().getInstalledPackages((int) j2, i2);
            if (installedPackages != null && installedPackages.getList() != null) {
                return installedPackages.getList();
            }
        } catch (Throwable unused) {
        }
        return Collections.EMPTY_LIST;
    }

    public static PackageInfo r(String str, long j2, int i2) {
        try {
            return Build.VERSION.SDK_INT >= 33 ? AbstractC0134dn.c.a().getPackageInfo(str, j2, i2) : AbstractC0134dn.c.a().getPackageInfo(str, (int) j2, i2);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static ArrayList s(int i2) {
        ArrayList arrayList = new ArrayList();
        try {
            String[] packagesForUid = AbstractC0134dn.c.a().getPackagesForUid(i2);
            if (packagesForUid != null) {
                for (String str : packagesForUid) {
                    if (str != null) {
                        arrayList.add(str);
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return arrayList;
    }

    public static final C0718wm t(InterfaceC0047at interfaceC0047at) {
        return (C0718wm) new Xn(interfaceC0047at.d(), new C0656um(), interfaceC0047at instanceof InterfaceC0155ee ? ((InterfaceC0155ee) interfaceC0047at).a() : C0149e8.b).p(AbstractC0593sl.a(C0718wm.class), "androidx.lifecycle.internal.SavedStateHandlesVM");
    }

    public static final void u(V7 v7, Throwable th) {
        Throwable runtimeException;
        Iterator it = X7.a.iterator();
        while (it.hasNext()) {
            try {
                ((P1) it.next()).d(th);
            } catch (Throwable th2) {
                if (th == th2) {
                    runtimeException = th;
                } else {
                    runtimeException = new RuntimeException("Exception while trying to handle coroutine exception", th2);
                    AbstractC0744xh.a(runtimeException, th);
                }
                Thread threadCurrentThread = Thread.currentThread();
                threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, runtimeException);
            }
        }
        try {
            AbstractC0744xh.a(th, new P9(v7));
        } catch (Throwable unused) {
        }
        Thread threadCurrentThread2 = Thread.currentThread();
        threadCurrentThread2.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread2, th);
    }

    public static final int v(C0391m4 c0391m4, Object obj, int i2) {
        int i3 = c0391m4.c;
        if (i3 == 0) {
            return -1;
        }
        try {
            int iD = AbstractC0744xh.d(i3, i2, c0391m4.a);
            if (iD < 0 || AbstractC0046as.c(obj, c0391m4.b[iD])) {
                return iD;
            }
            int i4 = iD + 1;
            while (i4 < i3 && c0391m4.a[i4] == i2) {
                if (AbstractC0046as.c(obj, c0391m4.b[i4])) {
                    return i4;
                }
                i4++;
            }
            for (int i5 = iD - 1; i5 >= 0 && c0391m4.a[i5] == i2; i5--) {
                if (AbstractC0046as.c(obj, c0391m4.b[i5])) {
                    return i5;
                }
            }
            return ~i4;
        } catch (IndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    public static int w(float f2) {
        if (f2 < 1.0f) {
            return -16777216;
        }
        if (f2 > 99.0f) {
            return -1;
        }
        float f3 = (f2 + 16.0f) / 116.0f;
        float f4 = f2 > 8.0f ? f3 * f3 * f3 : f2 / 903.2963f;
        float f5 = f3 * f3 * f3;
        boolean z2 = f5 > 0.008856452f;
        float f6 = z2 ? f5 : ((f3 * 116.0f) - 16.0f) / 903.2963f;
        if (!z2) {
            f5 = ((f3 * 116.0f) - 16.0f) / 903.2963f;
        }
        float[] fArr = c;
        return E6.a(f6 * fArr[0], f4 * fArr[1], f5 * fArr[2]);
    }

    public static boolean x(MotionEvent motionEvent, int i2) {
        return (motionEvent.getSource() & i2) == i2;
    }

    public static boolean y(int i2, Object obj) {
        int iB;
        if (obj instanceof Ad) {
            if (obj instanceof Cd) {
                iB = ((Cd) obj).b();
            } else if (obj instanceof InterfaceC0124dd) {
                iB = 0;
            } else if (obj instanceof InterfaceC0462od) {
                iB = 1;
            } else if (obj instanceof InterfaceC0585sd) {
                iB = 2;
            } else {
                iB = obj instanceof InterfaceC0678vd ? 5 : -1;
            }
            if (iB == i2) {
                return true;
            }
        }
        return false;
    }

    public static Mo z(InterfaceC0026a8 interfaceC0026a8, InterfaceC0585sd interfaceC0585sd, int i2) {
        V7 v7 = ExecutorC0243h9.b;
        if ((i2 & 1) != 0) {
            v7 = C0152eb.a;
        }
        V7 v7U = X8.u(interfaceC0026a8.f(), v7, true);
        C0550r9 c0550r9 = AbstractC0090ca.a;
        if (v7U != c0550r9 && v7U.g(Aa.a) == null) {
            v7U = v7U.b(c0550r9);
        }
        Mo mo = new Mo(v7U, true);
        int iJ = AbstractC0535qo.j(1);
        Pe pe = Pe.b;
        if (iJ == 0) {
            try {
                X8.U(pe, AbstractC0744xh.C(AbstractC0744xh.m(mo, mo, interfaceC0585sd)));
                return mo;
            } catch (Throwable th) {
                th = th;
                if (th instanceof Y9) {
                    th = ((Y9) th).a;
                }
                mo.h(new C0071bm(th));
                throw th;
            }
        }
        if (iJ != 1) {
            if (iJ == 2) {
                AbstractC0744xh.C(AbstractC0744xh.m(mo, mo, interfaceC0585sd)).h(pe);
            } else {
                if (iJ != 3) {
                    throw new K6();
                }
                try {
                    V7 v8 = mo.c;
                    Object objO = O(v8, null);
                    try {
                        b(interfaceC0585sd);
                        Object objF = interfaceC0585sd.f(mo, mo);
                        E(v8, objO);
                        if (objF != EnumC0057b8.a) {
                            mo.h(objF);
                        }
                    } catch (Throwable th2) {
                        E(v8, objO);
                        throw th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (th instanceof Y9) {
                        th = ((Y9) th).a;
                    }
                    mo.h(new C0071bm(th));
                }
            }
        }
        return mo;
    }

    public abstract void B(Throwable th);

    public abstract void C(Xn xn);
}
