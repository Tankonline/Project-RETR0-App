package rikka.shizuku;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextPaint;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.AbsSeekBar;
import android.widget.EditText;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class R2 implements InterfaceC0100ck, InterfaceC0344kj {
    public static final int[] d = {R.attr.indeterminateDrawable, R.attr.progressDrawable};
    public static final C0589sh e = new C0589sh(0, "UserServiceRecord");
    public final /* synthetic */ int a;
    public Object b;
    public Object c;

    public /* synthetic */ R2(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public static int D(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < i; i5++) {
            i3++;
            if (i3 == i2) {
                i4++;
                i3 = 0;
            } else if (i3 > i2) {
                i4++;
                i3 = 1;
            }
        }
        return i3 + 1 > i2 ? i4 + 1 : i4;
    }

    public static boolean i(Editable editable, KeyEvent keyEvent, boolean z) {
        Ar[] arArr;
        if (KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState())) {
            int selectionStart = Selection.getSelectionStart(editable);
            int selectionEnd = Selection.getSelectionEnd(editable);
            if (selectionStart != -1 && selectionEnd != -1 && selectionStart == selectionEnd && (arArr = (Ar[]) editable.getSpans(selectionStart, selectionEnd, Ar.class)) != null && arArr.length > 0) {
                for (Ar ar : arArr) {
                    int spanStart = editable.getSpanStart(ar);
                    int spanEnd = editable.getSpanEnd(ar);
                    if ((z && spanStart == selectionStart) || ((!z && spanEnd == selectionStart) || (selectionStart > spanStart && selectionStart < spanEnd))) {
                        editable.delete(spanStart, spanEnd);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public View A(int i, int i2, int i3, int i4) {
        View viewU;
        C0101cl c0101cl = (C0101cl) this.b;
        int iD = c0101cl.d();
        int iC = c0101cl.c();
        int i5 = i2 > i ? 1 : -1;
        View view = null;
        while (i != i2) {
            switch (c0101cl.a) {
                case 0:
                    viewU = c0101cl.b.u(i);
                    break;
                default:
                    viewU = c0101cl.b.u(i);
                    break;
            }
            int iB = c0101cl.b(viewU);
            int iA = c0101cl.a(viewU);
            C0631ts c0631ts = (C0631ts) this.c;
            c0631ts.b = iD;
            c0631ts.c = iC;
            c0631ts.d = iB;
            c0631ts.e = iA;
            if (i3 != 0) {
                c0631ts.a = i3;
                if (c0631ts.a()) {
                    return viewU;
                }
            }
            if (i4 != 0) {
                c0631ts.a = i4;
                if (c0631ts.a()) {
                    view = viewU;
                }
            }
            i += i5;
        }
        return view;
    }

    public InputFilter[] B(InputFilter[] inputFilterArr) {
        return ((AbstractC0046as) ((Ys) this.c).b).w(inputFilterArr);
    }

    public KeyListener C(KeyListener keyListener) {
        if (keyListener instanceof NumberKeyListener) {
            return keyListener;
        }
        ((R2) ((Ys) this.c).b).getClass();
        if (keyListener instanceof Ua) {
            return keyListener;
        }
        if (keyListener == null) {
            return null;
        }
        return keyListener instanceof NumberKeyListener ? keyListener : new Ua(keyListener);
    }

    public boolean E(CharSequence charSequence, int i, int i2, Va va) {
        if (va.c == 0) {
            C0212g9 c0212g9 = (C0212g9) this.c;
            Ai aiB = va.b();
            int iA = aiB.a(8);
            if (iA != 0) {
                ((ByteBuffer) aiB.d).getShort(iA + aiB.a);
            }
            c0212g9.getClass();
            ThreadLocal threadLocal = C0212g9.b;
            if (threadLocal.get() == null) {
                threadLocal.set(new StringBuilder());
            }
            StringBuilder sb = (StringBuilder) threadLocal.get();
            sb.setLength(0);
            while (i < i2) {
                sb.append(charSequence.charAt(i));
                i++;
            }
            TextPaint textPaint = c0212g9.a;
            String string = sb.toString();
            int i3 = Gj.a;
            va.c = textPaint.hasGlyph(string) ? 2 : 1;
        }
        return va.c == 2;
    }

    public void F() {
        ((SparseIntArray) this.b).clear();
    }

    public boolean G(View view) {
        C0101cl c0101cl = (C0101cl) this.b;
        int iD = c0101cl.d();
        int iC = c0101cl.c();
        int iB = c0101cl.b(view);
        int iA = c0101cl.a(view);
        C0631ts c0631ts = (C0631ts) this.c;
        c0631ts.b = iD;
        c0631ts.c = iC;
        c0631ts.d = iB;
        c0631ts.e = iA;
        c0631ts.a = 24579;
        return c0631ts.a();
    }

    public void H(AttributeSet attributeSet, int i) {
        switch (this.a) {
            case 0:
                AbsSeekBar absSeekBar = (AbsSeekBar) this.b;
                K3 k3N = K3.n(absSeekBar.getContext(), attributeSet, d, i);
                Drawable drawableJ = k3N.j(0);
                if (drawableJ != null) {
                    if (drawableJ instanceof AnimationDrawable) {
                        AnimationDrawable animationDrawable = (AnimationDrawable) drawableJ;
                        int numberOfFrames = animationDrawable.getNumberOfFrames();
                        AnimationDrawable animationDrawable2 = new AnimationDrawable();
                        animationDrawable2.setOneShot(animationDrawable.isOneShot());
                        for (int i2 = 0; i2 < numberOfFrames; i2++) {
                            Drawable drawableZ = Z(animationDrawable.getFrame(i2), true);
                            drawableZ.setLevel(10000);
                            animationDrawable2.addFrame(drawableZ, animationDrawable.getDuration(i2));
                        }
                        animationDrawable2.setLevel(10000);
                        drawableJ = animationDrawable2;
                    }
                    absSeekBar.setIndeterminateDrawable(drawableJ);
                }
                Drawable drawableJ2 = k3N.j(1);
                if (drawableJ2 != null) {
                    absSeekBar.setProgressDrawable(Z(drawableJ2, false));
                }
                k3N.o();
                return;
            case 4:
                TypedArray typedArrayObtainStyledAttributes = ((EditText) this.b).getContext().obtainStyledAttributes(attributeSet, Nk.i, i, 0);
                try {
                    boolean z = true;
                    if (typedArrayObtainStyledAttributes.hasValue(14)) {
                        z = typedArrayObtainStyledAttributes.getBoolean(14, true);
                        break;
                    }
                    typedArrayObtainStyledAttributes.recycle();
                    C0029ab c0029ab = (C0029ab) ((R2) ((Ys) this.c).b).c;
                    if (c0029ab.c != z) {
                        if (c0029ab.b != null) {
                            Ma maA = Ma.a();
                            Za za = c0029ab.b;
                            maA.getClass();
                            AbstractC0046as.j("initCallback cannot be null", za);
                            ReentrantReadWriteLock reentrantReadWriteLock = maA.a;
                            reentrantReadWriteLock.writeLock().lock();
                            try {
                                maA.b.remove(za);
                                reentrantReadWriteLock.writeLock().unlock();
                            } catch (Throwable th) {
                                reentrantReadWriteLock.writeLock().unlock();
                                throw th;
                            }
                        }
                        c0029ab.c = z;
                        if (z) {
                            C0029ab.a(c0029ab.a, Ma.a().b());
                            return;
                        }
                        return;
                    }
                    return;
                } catch (Throwable th2) {
                    typedArrayObtainStyledAttributes.recycle();
                    throw th2;
                }
            default:
                TypedArray typedArrayObtainStyledAttributes2 = ((TextView) this.b).getContext().obtainStyledAttributes(attributeSet, Nk.i, i, 0);
                try {
                    boolean z2 = true;
                    if (typedArrayObtainStyledAttributes2.hasValue(14)) {
                        z2 = typedArrayObtainStyledAttributes2.getBoolean(14, true);
                        break;
                    }
                    typedArrayObtainStyledAttributes2.recycle();
                    ((AbstractC0046as) ((Ys) this.c).b).T(z2);
                    return;
                } catch (Throwable th3) {
                    typedArrayObtainStyledAttributes2.recycle();
                    throw th3;
                }
        }
    }

    public void I(int i, int i2) {
        int[] iArr = (int[]) this.b;
        if (iArr == null || i >= iArr.length) {
            return;
        }
        int i3 = i + i2;
        x(i3);
        int[] iArr2 = (int[]) this.b;
        System.arraycopy(iArr2, i, iArr2, i3, (iArr2.length - i) - i2);
        Arrays.fill((int[]) this.b, i, i3, -1);
        ArrayList arrayList = (ArrayList) this.c;
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Jo jo = (Jo) ((ArrayList) this.c).get(size);
            int i4 = jo.a;
            if (i4 >= i) {
                jo.a = i4 + i2;
            }
        }
    }

    public void J(int i, int i2) {
        int[] iArr = (int[]) this.b;
        if (iArr == null || i >= iArr.length) {
            return;
        }
        int i3 = i + i2;
        x(i3);
        int[] iArr2 = (int[]) this.b;
        System.arraycopy(iArr2, i3, iArr2, i, (iArr2.length - i) - i2);
        int[] iArr3 = (int[]) this.b;
        Arrays.fill(iArr3, iArr3.length - i2, iArr3.length, -1);
        ArrayList arrayList = (ArrayList) this.c;
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Jo jo = (Jo) ((ArrayList) this.c).get(size);
            int i4 = jo.a;
            if (i4 >= i) {
                if (i4 < i3) {
                    ((ArrayList) this.c).remove(size);
                } else {
                    jo.a = i4 - i2;
                }
            }
        }
    }

    public Ra K(InputConnection inputConnection, EditorInfo editorInfo) {
        Ys ys = (Ys) this.c;
        if (inputConnection == null) {
            ys.getClass();
            inputConnection = null;
        } else {
            R2 r2 = (R2) ys.b;
            r2.getClass();
            if (!(inputConnection instanceof Ra)) {
                inputConnection = new Ra((EditText) r2.b, inputConnection, editorInfo);
            }
        }
        return (Ra) inputConnection;
    }

    public void L(F0 f0) {
        Xn xn = (Xn) this.b;
        ((ActionMode.Callback) xn.a).onDestroyActionMode(xn.j(f0));
        I2 i2 = (I2) this.c;
        if (i2.w != null) {
            i2.l.getDecorView().removeCallbacks(i2.x);
        }
        if (i2.v != null) {
            C0109ct c0109ct = i2.y;
            if (c0109ct != null) {
                c0109ct.b();
            }
            C0109ct c0109ctA = Hs.a(i2.v);
            c0109ctA.a(0.0f);
            i2.y = c0109ctA;
            c0109ctA.d(new C0760y2(2, this));
        }
        i2.u = null;
        ViewGroup viewGroup = i2.A;
        WeakHashMap weakHashMap = Hs.a;
        AbstractC0755xs.c(viewGroup);
        i2.K();
    }

    public boolean M(F0 f0, MenuC0129di menuC0129di) {
        ViewGroup viewGroup = ((I2) this.c).A;
        WeakHashMap weakHashMap = Hs.a;
        AbstractC0755xs.c(viewGroup);
        Xn xn = (Xn) this.b;
        C0289ip c0289ipJ = xn.j(f0);
        C0104co c0104co = (C0104co) xn.d;
        Menu menuC0776yi = (Menu) c0104co.get(menuC0129di);
        if (menuC0776yi == null) {
            menuC0776yi = new MenuC0776yi((Context) xn.b, menuC0129di);
            c0104co.put(menuC0129di, menuC0776yi);
        }
        return ((ActionMode.Callback) xn.a).onPrepareActionMode(c0289ipJ, menuC0776yi);
    }

    public void N(C0430nc c0430nc) {
        int i = c0430nc.b;
        Ml ml = (Ml) this.c;
        Hm hm = (Hm) this.b;
        if (i == 0) {
            ml.execute(new A0(hm, c0430nc.a, 4, false));
        } else {
            ml.execute(new RunnableC0670v5(i, 0, hm));
        }
    }

    /* JADX WARN: Code duplicated, block: B:60:0x00ed  */
    public void O(Context context, XmlResourceParser xmlResourceParser) {
        C0394m7 c0394m7 = new C0394m7();
        int attributeCount = xmlResourceParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            if ("id".equals(xmlResourceParser.getAttributeName(i))) {
                String attributeValue = xmlResourceParser.getAttributeValue(i);
                int identifier = attributeValue.contains("/") ? context.getResources().getIdentifier(attributeValue.substring(attributeValue.indexOf(47) + 1), "id", context.getPackageName()) : -1;
                if (identifier == -1) {
                    if (attributeValue.length() > 1) {
                        identifier = Integer.parseInt(attributeValue.substring(1));
                    } else {
                        Log.e("ConstraintLayoutStates", "error in parsing id");
                    }
                }
                try {
                    int eventType = xmlResourceParser.getEventType();
                    C0241h7 c0241h7D = null;
                    while (eventType != 1) {
                        if (eventType == 0) {
                            xmlResourceParser.getName();
                        } else if (eventType == 2) {
                            switch (xmlResourceParser.getName()) {
                                case "Constraint":
                                    c0241h7D = C0394m7.d(context, Xml.asAttributeSet(xmlResourceParser));
                                    break;
                                case "Guideline":
                                    c0241h7D = C0394m7.d(context, Xml.asAttributeSet(xmlResourceParser));
                                    c0241h7D.d.a = true;
                                    break;
                                case "Barrier":
                                    c0241h7D = C0394m7.d(context, Xml.asAttributeSet(xmlResourceParser));
                                    c0241h7D.d.c0 = 1;
                                    break;
                                case "PropertySet":
                                    if (c0241h7D == null) {
                                        throw new RuntimeException("XML parser error must be within a Constraint " + xmlResourceParser.getLineNumber());
                                    }
                                    c0241h7D.b.a(context, Xml.asAttributeSet(xmlResourceParser));
                                    break;
                                    break;
                                case "Transform":
                                    if (c0241h7D == null) {
                                        throw new RuntimeException("XML parser error must be within a Constraint " + xmlResourceParser.getLineNumber());
                                    }
                                    c0241h7D.e.a(context, Xml.asAttributeSet(xmlResourceParser));
                                    break;
                                    break;
                                case "Layout":
                                    if (c0241h7D == null) {
                                        throw new RuntimeException("XML parser error must be within a Constraint " + xmlResourceParser.getLineNumber());
                                    }
                                    c0241h7D.d.a(context, Xml.asAttributeSet(xmlResourceParser));
                                    break;
                                    break;
                                case "Motion":
                                    if (c0241h7D == null) {
                                        throw new RuntimeException("XML parser error must be within a Constraint " + xmlResourceParser.getLineNumber());
                                    }
                                    c0241h7D.c.a(context, Xml.asAttributeSet(xmlResourceParser));
                                    break;
                                    break;
                                case "CustomAttribute":
                                    if (c0241h7D == null) {
                                        throw new RuntimeException("XML parser error must be within a Constraint " + xmlResourceParser.getLineNumber());
                                    }
                                    C0025a7.a(context, xmlResourceParser, c0241h7D.f);
                                    break;
                                    break;
                            }
                        } else if (eventType != 3) {
                            continue;
                        } else {
                            String name = xmlResourceParser.getName();
                            if ("ConstraintSet".equals(name)) {
                                ((SparseArray) this.c).put(identifier, c0394m7);
                                return;
                            } else if (name.equalsIgnoreCase("Constraint")) {
                                c0394m7.c.put(Integer.valueOf(c0241h7D.a), c0241h7D);
                                c0241h7D = null;
                            }
                        }
                        eventType = xmlResourceParser.next();
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                } catch (XmlPullParserException e3) {
                    e3.printStackTrace();
                }
                ((SparseArray) this.c).put(identifier, c0394m7);
                return;
            }
        }
    }

    public void P() {
        ((Am) this.b).a();
    }

    public void Q(Bundle bundle) {
        Am am = (Am) this.b;
        Bundle bundleF = AbstractC0046as.f((Hj[]) Arrays.copyOf(new Hj[0], 0));
        Bundle bundle2 = am.f;
        if (bundle2 != null) {
            bundleF.putAll(bundle2);
        }
        synchronized (am.c) {
            for (Map.Entry entry : am.d.entrySet()) {
                bundleF.putBundle((String) entry.getKey(), ((InterfaceC0780ym) entry.getValue()).a());
            }
        }
        if (bundleF.isEmpty()) {
            return;
        }
        bundle.putBundle("androidx.lifecycle.BundlableSavedStateRegistry.key", bundleF);
    }

    public Yi R(AbstractC0470ol abstractC0470ol, int i) {
        Ns ns;
        Yi yi;
        C0104co c0104co = (C0104co) this.b;
        int iD = c0104co.d(abstractC0470ol);
        if (iD >= 0 && (ns = (Ns) c0104co.i(iD)) != null) {
            int i2 = ns.a;
            if ((i2 & i) != 0) {
                int i3 = i2 & (~i);
                ns.a = i3;
                if (i == 4) {
                    yi = ns.b;
                } else {
                    if (i != 8) {
                        throw new IllegalArgumentException("Must provide flag PRE or POST");
                    }
                    yi = ns.c;
                }
                if ((i3 & 12) == 0) {
                    c0104co.g(iD);
                    ns.a = 0;
                    ns.b = null;
                    ns.c = null;
                    Ns.d.c(ns);
                }
                return yi;
            }
        }
        return null;
    }

    public void S(String str, InterfaceC0780ym interfaceC0780ym) {
        Am am = (Am) this.b;
        synchronized (am.c) {
            if (am.d.containsKey(str)) {
                throw new IllegalArgumentException("SavedStateProvider with the given key is already registered");
            }
            am.d.put(str, interfaceC0780ym);
        }
    }

    public void T(AbstractC0470ol abstractC0470ol) {
        Ns ns = (Ns) ((C0104co) this.b).get(abstractC0470ol);
        if (ns == null) {
            return;
        }
        ns.a &= -2;
    }

    public void U(AbstractC0470ol abstractC0470ol) {
        C0620th c0620th = (C0620th) this.c;
        for (int iE = c0620th.e() - 1; iE >= 0; iE--) {
            if (abstractC0470ol == c0620th.f(iE)) {
                Object[] objArr = c0620th.c;
                Object obj = objArr[iE];
                Object obj2 = AbstractC0744xh.g;
                if (obj == obj2) {
                    break;
                }
                objArr[iE] = obj2;
                c0620th.a = true;
                break;
            }
        }
        Ns ns = (Ns) ((C0104co) this.b).remove(abstractC0470ol);
        if (ns != null) {
            ns.a = 0;
            ns.b = null;
            ns.c = null;
            Ns.d.c(ns);
        }
    }

    public C0455o6 V(int i, int i2) {
        C0455o6 c0455o6Y = y(i, i2);
        if (c0455o6Y != null) {
            return c0455o6Y;
        }
        C0589sh c0589sh = e;
        Locale locale = Locale.ENGLISH;
        c0589sh.b(5, "Caller (uid " + i + ", pid " + i2 + ") is not an attached client");
        throw new IllegalStateException("Not an attached client");
    }

    public void W() {
        if (!((Am) this.b).h) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        C0327k2 c0327k2 = (C0327k2) this.c;
        if (c0327k2 == null) {
            c0327k2 = new C0327k2(this);
        }
        this.c = c0327k2;
        try {
            C0465og.class.getDeclaredConstructor(null);
            C0327k2 c0327k3 = (C0327k2) this.c;
            if (c0327k3 != null) {
                ((LinkedHashSet) c0327k3.b).add(C0465og.class.getName());
            }
        } catch (NoSuchMethodException e2) {
            throw new IllegalArgumentException("Class " + C0465og.class.getSimpleName() + " must have default constructor in order to be automatically recreated", e2);
        }
    }

    public void X(boolean z) {
        ((AbstractC0046as) ((Ys) this.c).b).S(z);
    }

    public void Y(int i, int i2, int i3, int i4) {
        AbstractC0794z5 abstractC0794z5 = (AbstractC0794z5) this.c;
        abstractC0794z5.d.set(i, i2, i3, i4);
        Rect rect = abstractC0794z5.c;
        super/*android.view.View*/.setPadding(i + rect.left, i2 + rect.top, i3 + rect.right, i4 + rect.bottom);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Drawable Z(Drawable drawable, boolean z) {
        if (drawable instanceof ju) {
            ((ku) ((ju) drawable)).getClass();
        } else {
            if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                Drawable[] drawableArr = new Drawable[numberOfLayers];
                for (int i = 0; i < numberOfLayers; i++) {
                    int id = layerDrawable.getId(i);
                    drawableArr[i] = Z(layerDrawable.getDrawable(i), id == 16908301 || id == 16908303);
                }
                LayerDrawable layerDrawable2 = new LayerDrawable(drawableArr);
                for (int i2 = 0; i2 < numberOfLayers; i2++) {
                    layerDrawable2.setId(i2, layerDrawable.getId(i2));
                    layerDrawable2.setLayerGravity(i2, layerDrawable.getLayerGravity(i2));
                    layerDrawable2.setLayerWidth(i2, layerDrawable.getLayerWidth(i2));
                    layerDrawable2.setLayerHeight(i2, layerDrawable.getLayerHeight(i2));
                    layerDrawable2.setLayerInsetLeft(i2, layerDrawable.getLayerInsetLeft(i2));
                    layerDrawable2.setLayerInsetRight(i2, layerDrawable.getLayerInsetRight(i2));
                    layerDrawable2.setLayerInsetTop(i2, layerDrawable.getLayerInsetTop(i2));
                    layerDrawable2.setLayerInsetBottom(i2, layerDrawable.getLayerInsetBottom(i2));
                    layerDrawable2.setLayerInsetStart(i2, layerDrawable.getLayerInsetStart(i2));
                    layerDrawable2.setLayerInsetEnd(i2, layerDrawable.getLayerInsetEnd(i2));
                }
                return layerDrawable2;
            }
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (((Bitmap) this.c) == null) {
                    this.c = bitmap;
                }
                ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null));
                shapeDrawable.getPaint().setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP));
                shapeDrawable.getPaint().setColorFilter(bitmapDrawable.getPaint().getColorFilter());
                return z ? new ClipDrawable(shapeDrawable, 3, 1) : shapeDrawable;
            }
        }
        return drawable;
    }

    public C0455o6 a(int i, int i2, Ye ye, String str, int i3) {
        final C0455o6 c0455o6 = new C0455o6(i, i2, ye, str, i3);
        Pn pnD0 = ((Rn) this.b).d0(i);
        if (pnD0 != null && (pnD0.b & 2) != 0) {
            c0455o6.f = true;
        }
        try {
            ye.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: rikka.shizuku.n6
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    ((List) this.a.c).remove(c0455o6);
                }
            }, 0);
            ((List) this.c).add(c0455o6);
            return c0455o6;
        } catch (RemoteException e2) {
            e.c(e2, "addClient: linkToDeath failed", new Object[0]);
            return null;
        }
    }

    public void b(C0510q c0510q, String str) {
        ((C0700w4) this.b).getClass();
        AbstractC0633u abstractC0633uY = AbstractC0744xh.Y(c0510q, str);
        Vector vector = (Vector) this.c;
        Ok ok = new Ok();
        C0233h c0233h = new C0233h(2);
        c0233h.d(c0510q);
        c0233h.d(abstractC0633uY);
        D8 d8 = new D8(c0233h);
        d8.c = -1;
        E8 e8 = new E8(d8);
        e8.c = -1;
        ok.a = e8;
        vector.addElement(ok);
    }

    @Override // rikka.shizuku.InterfaceC0100ck
    public void c(Preference preference) {
        ((PreferenceGroup) this.b).S = Integer.MAX_VALUE;
        C0376lk c0376lk = (C0376lk) this.c;
        Handler handler = c0376lk.h;
        RunnableC0607t4 runnableC0607t4 = c0376lk.i;
        handler.removeCallbacks(runnableC0607t4);
        handler.post(runnableC0607t4);
    }

    public void d(AbstractC0470ol abstractC0470ol, Yi yi) {
        C0104co c0104co = (C0104co) this.b;
        Ns nsA = (Ns) c0104co.get(abstractC0470ol);
        if (nsA == null) {
            nsA = Ns.a();
            c0104co.put(abstractC0470ol, nsA);
        }
        nsA.c = yi;
        nsA.a |= 8;
    }

    public void e() {
        int[] iArr = (int[]) this.b;
        if (iArr != null) {
            Arrays.fill(iArr, -1);
        }
        this.c = null;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0089  */
    @Override // rikka.shizuku.InterfaceC0344kj
    public au f(View view, au auVar) {
        boolean z;
        C0232gt c0232gt = (C0232gt) this.c;
        int i = c0232gt.a;
        C0330k5 c0330k5 = (C0330k5) this.b;
        Wt wt = auVar.a;
        C0587sf c0587sfF = wt.f(519);
        C0587sf c0587sfF2 = wt.f(32);
        int i2 = c0587sfF.b;
        BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) c0330k5.b;
        bottomSheetBehavior.w = i2;
        boolean zM = AbstractC0750xn.m(view);
        int paddingBottom = view.getPaddingBottom();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        boolean z2 = bottomSheetBehavior.o;
        if (z2) {
            int iA = auVar.a();
            bottomSheetBehavior.v = iA;
            paddingBottom = iA + c0232gt.c;
        }
        int i3 = c0232gt.b;
        boolean z3 = bottomSheetBehavior.p;
        int i4 = c0587sfF.a;
        if (z3) {
            paddingLeft = (zM ? i3 : i) + i4;
        }
        boolean z4 = bottomSheetBehavior.q;
        int i5 = c0587sfF.c;
        if (z4) {
            if (!zM) {
                i = i3;
            }
            paddingRight = i + i5;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        boolean z5 = true;
        if (!bottomSheetBehavior.s || marginLayoutParams.leftMargin == i4) {
            z = false;
        } else {
            marginLayoutParams.leftMargin = i4;
            z = true;
        }
        if (bottomSheetBehavior.t && marginLayoutParams.rightMargin != i5) {
            marginLayoutParams.rightMargin = i5;
            z = true;
        }
        if (bottomSheetBehavior.u) {
            int i6 = marginLayoutParams.topMargin;
            int i7 = c0587sfF.b;
            if (i6 != i7) {
                marginLayoutParams.topMargin = i7;
            } else {
                z5 = z;
            }
        } else {
            z5 = z;
        }
        if (z5) {
            view.setLayoutParams(marginLayoutParams);
        }
        view.setPadding(paddingLeft, view.getPaddingTop(), paddingRight, paddingBottom);
        boolean z6 = c0330k5.a;
        if (z6) {
            bottomSheetBehavior.m = c0587sfF2.d;
        }
        if (!z2 && !z6) {
            return auVar;
        }
        bottomSheetBehavior.J();
        return auVar;
    }

    public Bundle g(String str) {
        Am am = (Am) this.b;
        if (!am.g) {
            throw new IllegalStateException("You can 'consumeRestoredStateForKey' only after the corresponding component has moved to the 'CREATED' state");
        }
        Bundle bundle = am.f;
        if (bundle == null) {
            return null;
        }
        Bundle bundleF = bundle.containsKey(str) ? X8.F(str, bundle) : null;
        bundle.remove(str);
        if (bundle.isEmpty()) {
            am.f = null;
        }
        return bundleF;
    }

    public byte[] h(int i, String str) throws IOException {
        if (i >= 0) {
            int i2 = 1;
            if (1 <= str.length() - i) {
                if ((i & 1) != 0) {
                    throw new IOException("a hexadecimal encoding must have an even number of characters");
                }
                int i3 = i >>> 1;
                byte[] bArr = new byte[i3];
                for (int i4 = 0; i4 < i3; i4++) {
                    char cCharAt = str.charAt(i2);
                    byte[] bArr2 = (byte[]) this.c;
                    i2 += 2;
                    int i5 = bArr2[str.charAt(i2 + 1)] | (bArr2[cCharAt] << 4);
                    if (i5 < 0) {
                        throw new IOException("invalid characters encountered in Hex string");
                    }
                    bArr[i4] = (byte) i5;
                }
                return bArr;
            }
        }
        throw new IndexOutOfBoundsException("invalid offset and/or length specified");
    }

    public void j(Ac ac, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.j(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void k(Ac ac, boolean z) {
        Qc qc = (Qc) this.b;
        AbstractActivityC0051b2 abstractActivityC0051b2 = qc.w.j;
        Ac ac2 = qc.y;
        if (ac2 != null) {
            ac2.l().o.k(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void l(Ac ac, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.l(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void m(Ac ac, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.m(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void n(Ac ac, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.n(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void o(Ac ac, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.o(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void p(Ac ac, boolean z) {
        Qc qc = (Qc) this.b;
        AbstractActivityC0051b2 abstractActivityC0051b2 = qc.w.j;
        Ac ac2 = qc.y;
        if (ac2 != null) {
            ac2.l().o.p(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void q(Ac ac, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.q(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void r(Ac ac, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.r(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void s(Ac ac, Bundle bundle, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.s(ac, bundle, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void t(Ac ac, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.t(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public String toString() {
        switch (this.a) {
            case 26:
                return "Bounds{lower=" + ((C0587sf) this.b) + " upper=" + ((C0587sf) this.c) + "}";
            default:
                return super.toString();
        }
    }

    public void u(Ac ac, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.u(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void v(Ac ac, View view, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.v(ac, view, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void w(Ac ac, boolean z) {
        Ac ac2 = ((Qc) this.b).y;
        if (ac2 != null) {
            ac2.l().o.w(ac, true);
        }
        Iterator it = ((CopyOnWriteArrayList) this.c).iterator();
        if (it.hasNext()) {
            if (it.next() != null) {
                throw new ClassCastException();
            }
            if (!z) {
                throw null;
            }
            throw null;
        }
    }

    public void x(int i) {
        int[] iArr = (int[]) this.b;
        if (iArr == null) {
            int[] iArr2 = new int[Math.max(i, 10) + 1];
            this.b = iArr2;
            Arrays.fill(iArr2, -1);
        } else if (i >= iArr.length) {
            int length = iArr.length;
            while (length <= i) {
                length *= 2;
            }
            int[] iArr3 = new int[length];
            this.b = iArr3;
            System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            int[] iArr4 = (int[]) this.b;
            Arrays.fill(iArr4, iArr.length, iArr4.length, -1);
        }
    }

    public C0455o6 y(int i, int i2) {
        for (C0455o6 c0455o6 : (List) this.c) {
            if (c0455o6.b == i2 && c0455o6.a == i) {
                return c0455o6;
            }
        }
        return null;
    }

    public ArrayList z(int i) {
        ArrayList arrayList;
        synchronized (this) {
            try {
                arrayList = new ArrayList();
                for (C0455o6 c0455o6 : (List) this.c) {
                    if (c0455o6.a == i) {
                        arrayList.add(c0455o6);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public /* synthetic */ R2(int i, boolean z) {
        this.a = i;
    }

    public /* synthetic */ R2(Object obj, int i, Object obj2) {
        this.a = i;
        this.c = obj;
        this.b = obj2;
    }

    public /* synthetic */ R2(Object obj, Object obj2, int i, boolean z) {
        this.a = i;
        this.b = obj;
        this.c = obj2;
    }

    public R2(Rn rn) {
        this.a = 1;
        this.c = Collections.synchronizedList(new ArrayList());
        this.b = rn;
    }

    public R2(Am am) {
        this.a = 21;
        this.b = am;
        this.c = new R2(20, am);
    }

    public R2(Qc qc) {
        this.a = 14;
        this.b = qc;
        this.c = new CopyOnWriteArrayList();
    }

    public R2(TextView textView) {
        this.a = 5;
        this.b = textView;
        this.c = new Ys(textView);
    }

    public R2(EditText editText, int i) {
        this.a = i;
        switch (i) {
            case 11:
                this.b = editText;
                C0029ab c0029ab = new C0029ab(editText);
                this.c = c0029ab;
                editText.addTextChangedListener(c0029ab);
                if (Qa.b == null) {
                    synchronized (Qa.a) {
                        try {
                            if (Qa.b == null) {
                                Qa qa = new Qa();
                                try {
                                    Qa.c = Class.forName("android.text.DynamicLayout$ChangeWatcher", false, Qa.class.getClassLoader());
                                    break;
                                } catch (Throwable unused) {
                                }
                                Qa.b = qa;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                        break;
                    }
                }
                editText.setEditableFactory(Qa.b);
                return;
            default:
                this.b = editText;
                this.c = new Ys(editText);
                return;
        }
    }

    public R2(Context context) {
        this.a = 7;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(AbstractC0046as.Q(context, moe.shizuku.privileged.api.R.attr._0_resource_name_obfuscated_res_0x7f0402ff, Oh.class.getCanonicalName()).data, Lk.p);
        Ee.c(context, typedArrayObtainStyledAttributes.getResourceId(4, 0));
        Ee.c(context, typedArrayObtainStyledAttributes.getResourceId(2, 0));
        Ee.c(context, typedArrayObtainStyledAttributes.getResourceId(3, 0));
        Ee.c(context, typedArrayObtainStyledAttributes.getResourceId(5, 0));
        ColorStateList colorStateListW = X8.w(context, typedArrayObtainStyledAttributes, 7);
        this.b = Ee.c(context, typedArrayObtainStyledAttributes.getResourceId(9, 0));
        Ee.c(context, typedArrayObtainStyledAttributes.getResourceId(8, 0));
        this.c = Ee.c(context, typedArrayObtainStyledAttributes.getResourceId(10, 0));
        new Paint().setColor(colorStateListW.getDefaultColor());
        typedArrayObtainStyledAttributes.recycle();
    }

    public R2(Xn xn, Pe pe, C0212g9 c0212g9) {
        this.a = 12;
        this.b = xn;
        this.c = c0212g9;
    }

    public R2(C0101cl c0101cl) {
        this.a = 23;
        this.b = c0101cl;
        C0631ts c0631ts = new C0631ts();
        c0631ts.a = 0;
        this.c = c0631ts;
    }

    public R2(Animation animation) {
        this.a = 13;
        this.b = animation;
        this.c = null;
    }

    public R2(Animator animator) {
        this.a = 13;
        this.b = null;
        AnimatorSet animatorSet = new AnimatorSet();
        this.c = animatorSet;
        animatorSet.play(animator);
    }

    public R2(ArrayList arrayList, ArrayList arrayList2) {
        this.a = 15;
        int size = arrayList.size();
        this.b = new int[size];
        this.c = new float[size];
        for (int i = 0; i < size; i++) {
            ((int[]) this.b)[i] = ((Integer) arrayList.get(i)).intValue();
            ((float[]) this.c)[i] = ((Float) arrayList2.get(i)).floatValue();
        }
    }

    public R2(int i, int i2) {
        this.a = 15;
        this.b = new int[]{i, i2};
        this.c = new float[]{0.0f, 1.0f};
    }

    public R2(int i, int i2, int i3) {
        this.a = 15;
        this.b = new int[]{i, i2, i3};
        this.c = new float[]{0.0f, 0.5f, 1.0f};
    }

    public R2(AbstractC0794z5 abstractC0794z5) {
        this.a = 9;
        this.c = abstractC0794z5;
    }

    public R2(int i) {
        int i2 = 0;
        this.a = i;
        switch (i) {
            case 16:
                this.b = new SparseIntArray();
                this.c = new SparseIntArray();
                break;
            case 17:
                this.b = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
                this.c = new byte[128];
                int i3 = 0;
                while (true) {
                    byte[] bArr = (byte[]) this.c;
                    if (i3 >= bArr.length) {
                        while (true) {
                            byte[] bArr2 = (byte[]) this.b;
                            if (i2 >= bArr2.length) {
                                bArr[65] = bArr[97];
                                bArr[66] = bArr[98];
                                bArr[67] = bArr[99];
                                bArr[68] = bArr[100];
                                bArr[69] = bArr[101];
                                bArr[70] = bArr[102];
                            } else {
                                bArr[bArr2[i2]] = (byte) i2;
                                i2++;
                            }
                        }
                    } else {
                        bArr[i3] = -1;
                        i3++;
                    }
                    break;
                }
                break;
            case 24:
                this.b = new C0104co(0);
                this.c = new C0620th();
                break;
            default:
                this.b = new Rect();
                this.c = new Rect();
                break;
        }
    }
}
