package rikka.shizuku;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Tk implements InterfaceC0681vg {
    public final /* synthetic */ int a;
    public final Object b;

    public /* synthetic */ Tk(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // rikka.shizuku.InterfaceC0681vg
    public final void a(InterfaceC0743xg interfaceC0743xg, EnumC0557rg enumC0557rg) {
        View view;
        switch (this.a) {
            case 0:
                if (enumC0557rg != EnumC0557rg.ON_CREATE) {
                    throw new AssertionError("Next event must be ON_CREATE");
                }
                interfaceC0743xg.e().f(this);
                Bm bm = (Bm) this.b;
                Bundle bundleG = bm.b().g("androidx.savedstate.Restarter");
                if (bundleG == null) {
                    return;
                }
                ArrayList<String> stringArrayList = bundleG.getStringArrayList("classes_to_restore");
                if (stringArrayList == null) {
                    throw new IllegalStateException("SavedState with restored state for the component \"androidx.savedstate.Restarter\" must contain list of strings by the key \"classes_to_restore\"");
                }
                int size = stringArrayList.size();
                int i = 0;
                while (i < size) {
                    String str = stringArrayList.get(i);
                    i++;
                    String str2 = str;
                    try {
                        Class<? extends U> clsAsSubclass = Class.forName(str2, false, Tk.class.getClassLoader()).asSubclass(InterfaceC0749xm.class);
                        try {
                            Constructor declaredConstructor = clsAsSubclass.getDeclaredConstructor(null);
                            declaredConstructor.setAccessible(true);
                            try {
                                ((C0465og) ((InterfaceC0749xm) declaredConstructor.newInstance(null))).getClass();
                                if (!(bm instanceof InterfaceC0047at)) {
                                    throw new IllegalStateException(("Internal error: OnRecreation should be registered only on components that implement ViewModelStoreOwner. Received owner: " + bm).toString());
                                }
                                Zs zsD = ((InterfaceC0047at) bm).d();
                                R2 r2B = bm.b();
                                zsD.getClass();
                                LinkedHashMap linkedHashMap = zsD.a;
                                Iterator it = new HashSet(linkedHashMap.keySet()).iterator();
                                while (it.hasNext()) {
                                    Vs vs = (Vs) linkedHashMap.get((String) it.next());
                                    if (vs != null) {
                                        bm.e();
                                    }
                                }
                                if (!new HashSet(linkedHashMap.keySet()).isEmpty()) {
                                    r2B.W();
                                }
                            } catch (Exception e) {
                                throw new RuntimeException("Failed to instantiate " + str2, e);
                            }
                        } catch (NoSuchMethodException e2) {
                            throw new IllegalStateException("Class " + clsAsSubclass.getSimpleName() + " must have default constructor in order to be automatically recreated", e2);
                        }
                    } catch (ClassNotFoundException e3) {
                        throw new RuntimeException(AbstractC0535qo.d("Class ", str2, " wasn't found"), e3);
                    }
                }
                return;
            case 1:
                if (enumC0557rg != EnumC0557rg.ON_CREATE || Build.VERSION.SDK_INT < 33) {
                    return;
                }
                C0622tj c0622tj = ((T6) this.b).g;
                c0622tj.e = P6.a((T6) interfaceC0743xg);
                c0622tj.b(c0622tj.g);
                return;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                new HashMap();
                Id[] idArr = (Id[]) this.b;
                if (idArr.length > 0) {
                    Id id = idArr[0];
                    throw null;
                }
                if (idArr.length <= 0) {
                    return;
                }
                Id id2 = idArr[0];
                throw null;
            case 3:
                if (enumC0557rg != EnumC0557rg.ON_STOP || (view = ((Ac) this.b).G) == null) {
                    return;
                }
                view.cancelPendingInputEvents();
                return;
            default:
                if (enumC0557rg != EnumC0557rg.ON_CREATE) {
                    throw new IllegalStateException(("Next event must be ON_CREATE, it was " + enumC0557rg).toString());
                }
                interfaceC0743xg.e().f(this);
                C0687vm c0687vm = (C0687vm) this.b;
                if (c0687vm.b) {
                    return;
                }
                Bundle bundleG2 = c0687vm.a.g("androidx.lifecycle.internal.SavedStateHandlesProvider");
                Bundle bundleF = AbstractC0046as.f((Hj[]) Arrays.copyOf(new Hj[0], 0));
                Bundle bundle = c0687vm.c;
                if (bundle != null) {
                    bundleF.putAll(bundle);
                }
                if (bundleG2 != null) {
                    bundleF.putAll(bundleG2);
                }
                c0687vm.c = bundleF;
                c0687vm.b = true;
                c0687vm.b();
                return;
        }
    }
}
