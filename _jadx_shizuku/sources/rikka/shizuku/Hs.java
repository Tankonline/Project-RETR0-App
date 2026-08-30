package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public abstract class Hs {
    public static WeakHashMap a = null;
    public static Field b = null;
    public static boolean c = false;
    public static final int[] d = {R.id._0_resource_name_obfuscated_res_0x7f090010, R.id._0_resource_name_obfuscated_res_0x7f090011, R.id._0_resource_name_obfuscated_res_0x7f09001c, R.id._0_resource_name_obfuscated_res_0x7f090027, R.id._0_resource_name_obfuscated_res_0x7f09002a, R.id._0_resource_name_obfuscated_res_0x7f09002b, R.id._0_resource_name_obfuscated_res_0x7f09002c, R.id._0_resource_name_obfuscated_res_0x7f09002d, R.id._0_resource_name_obfuscated_res_0x7f09002e, R.id._0_resource_name_obfuscated_res_0x7f09002f, R.id._0_resource_name_obfuscated_res_0x7f090012, R.id._0_resource_name_obfuscated_res_0x7f090013, R.id._0_resource_name_obfuscated_res_0x7f090014, R.id._0_resource_name_obfuscated_res_0x7f090015, R.id._0_resource_name_obfuscated_res_0x7f090016, R.id._0_resource_name_obfuscated_res_0x7f090017, R.id._0_resource_name_obfuscated_res_0x7f090018, R.id._0_resource_name_obfuscated_res_0x7f090019, R.id._0_resource_name_obfuscated_res_0x7f09001a, R.id._0_resource_name_obfuscated_res_0x7f09001b, R.id._0_resource_name_obfuscated_res_0x7f09001d, R.id._0_resource_name_obfuscated_res_0x7f09001e, R.id._0_resource_name_obfuscated_res_0x7f09001f, R.id._0_resource_name_obfuscated_res_0x7f090020, R.id._0_resource_name_obfuscated_res_0x7f090021, R.id._0_resource_name_obfuscated_res_0x7f090022, R.id._0_resource_name_obfuscated_res_0x7f090023, R.id._0_resource_name_obfuscated_res_0x7f090024, R.id._0_resource_name_obfuscated_res_0x7f090025, R.id._0_resource_name_obfuscated_res_0x7f090026, R.id._0_resource_name_obfuscated_res_0x7f090028, R.id._0_resource_name_obfuscated_res_0x7f090029};
    public static final C0662us e = new C0662us();
    public static final ViewTreeObserverOnGlobalLayoutListenerC0724ws f = new ViewTreeObserverOnGlobalLayoutListenerC0724ws();

    public static C0109ct a(View view) {
        if (a == null) {
            a = new WeakHashMap();
        }
        C0109ct c0109ct = (C0109ct) a.get(view);
        if (c0109ct != null) {
            return c0109ct;
        }
        C0109ct c0109ct2 = new C0109ct(view);
        a.put(view, c0109ct2);
        return c0109ct2;
    }

    public static void b(View view, au auVar) {
        int i = Build.VERSION.SDK_INT;
        WindowInsets windowInsetsD = auVar.d();
        if (windowInsetsD != null) {
            WindowInsets windowInsetsA = i >= 30 ? Es.a(view, windowInsetsD) : AbstractC0755xs.a(view, windowInsetsD);
            if (windowInsetsA.equals(windowInsetsD)) {
                return;
            }
            au.e(view, windowInsetsA);
        }
    }

    public static boolean c(View view, KeyEvent keyEvent) {
        if (Build.VERSION.SDK_INT >= 28) {
            return false;
        }
        ArrayList arrayList = Gs.d;
        Gs gs = (Gs) view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901c8);
        if (gs == null) {
            gs = new Gs();
            gs.a = null;
            gs.b = null;
            gs.c = null;
            view.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901c8, gs);
        }
        if (keyEvent.getAction() == 0) {
            WeakHashMap weakHashMap = gs.a;
            if (weakHashMap != null) {
                weakHashMap.clear();
            }
            ArrayList arrayList2 = Gs.d;
            if (!arrayList2.isEmpty()) {
                synchronized (arrayList2) {
                    try {
                        if (gs.a == null) {
                            gs.a = new WeakHashMap();
                        }
                        for (int size = arrayList2.size() - 1; size >= 0; size--) {
                            ArrayList arrayList3 = Gs.d;
                            View view2 = (View) ((WeakReference) arrayList3.get(size)).get();
                            if (view2 == null) {
                                arrayList3.remove(size);
                            } else {
                                gs.a.put(view2, Boolean.TRUE);
                                for (ViewParent parent = view2.getParent(); parent instanceof View; parent = parent.getParent()) {
                                    gs.a.put((View) parent, Boolean.TRUE);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
        View viewA = gs.a(view);
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (viewA != null && !KeyEvent.isModifierKey(keyCode)) {
                if (gs.b == null) {
                    gs.b = new SparseArray();
                }
                gs.b.put(keyCode, new WeakReference(viewA));
            }
        }
        return viewA != null;
    }

    public static View.AccessibilityDelegate d(View view) {
        if (Build.VERSION.SDK_INT >= 29) {
            return Ds.a(view);
        }
        if (c) {
            return null;
        }
        if (b == null) {
            try {
                Field declaredField = View.class.getDeclaredField("mAccessibilityDelegate");
                b = declaredField;
                declaredField.setAccessible(true);
            } catch (Throwable unused) {
                c = true;
                return null;
            }
        }
        try {
            Object obj = b.get(view);
            if (obj instanceof View.AccessibilityDelegate) {
                return (View.AccessibilityDelegate) obj;
            }
            return null;
        } catch (Throwable unused2) {
            c = true;
            return null;
        }
    }

    public static CharSequence e(View view) {
        Object tag;
        if (Build.VERSION.SDK_INT >= 28) {
            tag = Cs.a(view);
        } else {
            tag = view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901bd);
            if (!CharSequence.class.isInstance(tag)) {
                tag = null;
            }
        }
        return (CharSequence) tag;
    }

    public static ArrayList f(View view) {
        ArrayList arrayList = (ArrayList) view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901ba);
        if (arrayList != null) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        view.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901ba, arrayList2);
        return arrayList2;
    }

    public static String[] g(M2 m2) {
        return Build.VERSION.SDK_INT >= 31 ? Fs.a(m2) : (String[]) m2.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901c1);
    }

    public static void h(View view, int i) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled()) {
            boolean z = e(view) != null && view.isShown() && view.getWindowVisibility() == 0;
            if (view.getAccessibilityLiveRegion() != 0 || z) {
                AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain();
                accessibilityEventObtain.setEventType(z ? 32 : 2048);
                accessibilityEventObtain.setContentChangeTypes(i);
                if (z) {
                    accessibilityEventObtain.getText().add(e(view));
                    if (view.getImportantForAccessibility() == 0) {
                        view.setImportantForAccessibility(1);
                    }
                }
                view.sendAccessibilityEventUnchecked(accessibilityEventObtain);
                return;
            }
            if (i != 32) {
                if (view.getParent() != null) {
                    try {
                        view.getParent().notifySubtreeAccessibilityStateChanged(view, view, i);
                        return;
                    } catch (AbstractMethodError e2) {
                        Log.e("ViewCompat", view.getParent().getClass().getSimpleName().concat(" does not fully implement ViewParent"), e2);
                        return;
                    }
                }
                return;
            }
            AccessibilityEvent accessibilityEventObtain2 = AccessibilityEvent.obtain();
            view.onInitializeAccessibilityEvent(accessibilityEventObtain2);
            accessibilityEventObtain2.setEventType(32);
            accessibilityEventObtain2.setContentChangeTypes(i);
            accessibilityEventObtain2.setSource(view);
            view.onPopulateAccessibilityEvent(accessibilityEventObtain2);
            accessibilityEventObtain2.getText().add(e(view));
            accessibilityManager.sendAccessibilityEvent(accessibilityEventObtain2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static B7 i(View view, B7 b7) {
        if (Log.isLoggable("ViewCompat", 3)) {
            Objects.toString(b7);
            view.getClass();
            view.getId();
        }
        if (Build.VERSION.SDK_INT >= 31) {
            return Fs.b(view, b7);
        }
        Jp jp = (Jp) view.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901c0);
        InterfaceC0715wj interfaceC0715wj = e;
        if (jp == null) {
            if (view instanceof InterfaceC0715wj) {
                interfaceC0715wj = (InterfaceC0715wj) view;
            }
            return interfaceC0715wj.a(b7);
        }
        B7 b7A = Jp.a(view, b7);
        if (b7A == null) {
            return null;
        }
        if (view instanceof InterfaceC0715wj) {
            interfaceC0715wj = (InterfaceC0715wj) view;
        }
        return interfaceC0715wj.a(b7A);
    }

    public static void j(View view, int i) {
        ArrayList arrayListF = f(view);
        for (int i2 = 0; i2 < arrayListF.size(); i2++) {
            if (((C0018a0) arrayListF.get(i2)).a() == i) {
                arrayListF.remove(i2);
                return;
            }
        }
    }

    public static void k(View view, C0018a0 c0018a0, InterfaceC0449o0 interfaceC0449o0) {
        S s;
        C0018a0 c0018a1 = new C0018a0(null, c0018a0.b, null, interfaceC0449o0, c0018a0.c);
        View.AccessibilityDelegate accessibilityDelegateD = d(view);
        if (accessibilityDelegateD == null) {
            s = null;
        } else {
            s = accessibilityDelegateD instanceof Q ? ((Q) accessibilityDelegateD).a : new S(accessibilityDelegateD);
        }
        if (s == null) {
            s = new S();
        }
        m(view, s);
        j(view, c0018a1.a());
        f(view).add(c0018a1);
        h(view, 0);
    }

    public static void l(View view, Context context, int[] iArr, AttributeSet attributeSet, TypedArray typedArray, int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            Ds.b(view, context, iArr, attributeSet, typedArray, i, 0);
        }
    }

    public static void m(View view, S s) {
        if (s == null && (d(view) instanceof Q)) {
            s = new S();
        }
        if (view.getImportantForAccessibility() == 0) {
            view.setImportantForAccessibility(1);
        }
        view.setAccessibilityDelegate(s == null ? null : s.b);
    }

    public static void n(View view, CharSequence charSequence) {
        new C0693vs(R.id._0_resource_name_obfuscated_res_0x7f0901bd, CharSequence.class, 8, 28, 1).d(view, charSequence);
        ViewTreeObserverOnGlobalLayoutListenerC0724ws viewTreeObserverOnGlobalLayoutListenerC0724ws = f;
        if (charSequence == null) {
            viewTreeObserverOnGlobalLayoutListenerC0724ws.a.remove(view);
            view.removeOnAttachStateChangeListener(viewTreeObserverOnGlobalLayoutListenerC0724ws);
            view.getViewTreeObserver().removeOnGlobalLayoutListener(viewTreeObserverOnGlobalLayoutListenerC0724ws);
        } else {
            viewTreeObserverOnGlobalLayoutListenerC0724ws.a.put(view, Boolean.valueOf(view.isShown() && view.getWindowVisibility() == 0));
            view.addOnAttachStateChangeListener(viewTreeObserverOnGlobalLayoutListenerC0724ws);
            if (view.isAttachedToWindow()) {
                view.getViewTreeObserver().addOnGlobalLayoutListener(viewTreeObserverOnGlobalLayoutListenerC0724ws);
            }
        }
    }
}
