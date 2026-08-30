package rikka.shizuku;

import android.view.ViewGroup;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public abstract class Aq {
    public static final C0638u4 a;
    public static final ThreadLocal b;
    public static final ArrayList c;

    static {
        C0638u4 c0638u4 = new C0638u4();
        c0638u4.C = new ArrayList();
        c0638u4.D = true;
        c0638u4.F = false;
        c0638u4.G = 0;
        c0638u4.D = false;
        c0638u4.L(new Hb(2));
        c0638u4.L(new O5());
        c0638u4.L(new Hb(1));
        a = c0638u4;
        b = new ThreadLocal();
        c = new ArrayList();
    }

    public static void a(ViewGroup viewGroup, AbstractC0722wq abstractC0722wq) {
        ArrayList arrayList = c;
        if (arrayList.contains(viewGroup) || !viewGroup.isLaidOut()) {
            return;
        }
        arrayList.add(viewGroup);
        if (abstractC0722wq == null) {
            abstractC0722wq = a;
        }
        AbstractC0722wq abstractC0722wqClone = abstractC0722wq.clone();
        ArrayList arrayList2 = (ArrayList) b().get(viewGroup);
        if (arrayList2 != null && arrayList2.size() > 0) {
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                ((AbstractC0722wq) obj).x(viewGroup);
            }
        }
        if (abstractC0722wqClone != null) {
            abstractC0722wqClone.h(viewGroup, true);
        }
        if (viewGroup.getTag(R.id._0_resource_name_obfuscated_res_0x7f0901ea) != null) {
            throw new ClassCastException();
        }
        viewGroup.setTag(R.id._0_resource_name_obfuscated_res_0x7f0901ea, null);
        ViewTreeObserverOnPreDrawListenerC0815zq viewTreeObserverOnPreDrawListenerC0815zq = new ViewTreeObserverOnPreDrawListenerC0815zq();
        viewTreeObserverOnPreDrawListenerC0815zq.a = abstractC0722wqClone;
        viewTreeObserverOnPreDrawListenerC0815zq.b = viewGroup;
        viewGroup.addOnAttachStateChangeListener(viewTreeObserverOnPreDrawListenerC0815zq);
        viewGroup.getViewTreeObserver().addOnPreDrawListener(viewTreeObserverOnPreDrawListenerC0815zq);
    }

    public static C0298j4 b() {
        C0298j4 c0298j4;
        ThreadLocal threadLocal = b;
        WeakReference weakReference = (WeakReference) threadLocal.get();
        if (weakReference != null && (c0298j4 = (C0298j4) weakReference.get()) != null) {
            return c0298j4;
        }
        C0298j4 c0298j5 = new C0298j4(0);
        threadLocal.set(new WeakReference(c0298j5));
        return c0298j5;
    }
}
