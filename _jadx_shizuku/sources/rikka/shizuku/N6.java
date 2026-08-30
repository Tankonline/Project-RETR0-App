package rikka.shizuku;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class N6 {
    public final HashMap a = new HashMap();
    public final HashMap b = new HashMap();
    public final HashMap c = new HashMap();
    public ArrayList d = new ArrayList();
    public final transient HashMap e = new HashMap();
    public final HashMap f = new HashMap();
    public final Bundle g = new Bundle();
    public final /* synthetic */ AbstractActivityC0051b2 h;

    public N6(AbstractActivityC0051b2 abstractActivityC0051b2) {
        this.h = abstractActivityC0051b2;
    }

    public final boolean a(int i, int i2, Intent intent) {
        String str = (String) this.a.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        Q0 q0 = (Q0) this.e.get(str);
        if (q0 != null) {
            L0 l0 = q0.a;
            if (this.d.contains(str)) {
                l0.b(q0.b.L(i2, intent));
                this.d.remove(str);
                return true;
            }
        }
        this.f.remove(str);
        this.g.putParcelable(str, new K0(i2, intent));
        return true;
    }

    public final void b(int i, AbstractC0046as abstractC0046as) {
        Bundle bundleExtra;
        int i2;
        AbstractActivityC0051b2 abstractActivityC0051b2 = this.h;
        AbstractC0599sr abstractC0599srZ = abstractC0046as.z(abstractActivityC0051b2);
        if (abstractC0599srZ != null) {
            new Handler(Looper.getMainLooper()).post(new RunnableC0670v5(this, i, abstractC0599srZ));
            return;
        }
        Intent intentN = abstractC0046as.n();
        if (intentN.getExtras() != null && intentN.getExtras().getClassLoader() == null) {
            intentN.setExtrasClassLoader(abstractActivityC0051b2.getClassLoader());
        }
        if (intentN.hasExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE")) {
            bundleExtra = intentN.getBundleExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
            intentN.removeExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
        } else {
            bundleExtra = null;
        }
        Bundle bundle = bundleExtra;
        if ("androidx.activity.result.contract.action.REQUEST_PERMISSIONS".equals(intentN.getAction())) {
            String[] stringArrayExtra = intentN.getStringArrayExtra("androidx.activity.result.contract.extra.PERMISSIONS");
            if (stringArrayExtra == null) {
                stringArrayExtra = new String[0];
            }
            HashSet hashSet = new HashSet();
            for (int i3 = 0; i3 < stringArrayExtra.length; i3++) {
                if (TextUtils.isEmpty(stringArrayExtra[i3])) {
                    throw new IllegalArgumentException("Permission request for permissions " + Arrays.toString(stringArrayExtra) + " must not contain null or empty values");
                }
                if (Build.VERSION.SDK_INT < 33 && TextUtils.equals(stringArrayExtra[i3], "android.permission.POST_NOTIFICATIONS")) {
                    hashSet.add(Integer.valueOf(i3));
                }
            }
            int size = hashSet.size();
            String[] strArr = size > 0 ? new String[stringArrayExtra.length - size] : stringArrayExtra;
            if (size > 0) {
                if (size == stringArrayExtra.length) {
                    return;
                }
                int i4 = 0;
                for (int i5 = 0; i5 < stringArrayExtra.length; i5++) {
                    if (!hashSet.contains(Integer.valueOf(i5))) {
                        strArr[i4] = stringArrayExtra[i5];
                        i4++;
                    }
                }
            }
            abstractActivityC0051b2.requestPermissions(stringArrayExtra, i);
            return;
        }
        if (!"androidx.activity.result.contract.action.INTENT_SENDER_REQUEST".equals(intentN.getAction())) {
            abstractActivityC0051b2.startActivityForResult(intentN, i, bundle);
            return;
        }
        C0742xf c0742xf = (C0742xf) intentN.getParcelableExtra("androidx.activity.result.contract.extra.INTENT_SENDER_REQUEST");
        try {
            i2 = i;
            try {
                abstractActivityC0051b2.startIntentSenderForResult(c0742xf.a, i2, c0742xf.b, c0742xf.c, c0742xf.d, 0, bundle);
            } catch (IntentSender.SendIntentException e) {
                e = e;
                new Handler(Looper.getMainLooper()).post(new RunnableC0390m3(this, i2, e));
            }
        } catch (IntentSender.SendIntentException e2) {
            e = e2;
            i2 = i;
        }
    }

    public final P0 c(String str, AbstractC0046as abstractC0046as, L0 l0) {
        d(str);
        this.e.put(str, new Q0(l0, abstractC0046as));
        HashMap map = this.f;
        if (map.containsKey(str)) {
            Object obj = map.get(str);
            map.remove(str);
            l0.b(obj);
        }
        Bundle bundle = this.g;
        K0 k0 = (K0) bundle.getParcelable(str);
        if (k0 != null) {
            bundle.remove(str);
            l0.b(abstractC0046as.L(k0.a, k0.b));
        }
        return new P0(this, str, abstractC0046as);
    }

    public final void d(String str) {
        HashMap map = this.b;
        if (((Integer) map.get(str)) != null) {
            return;
        }
        O o = Sk.a;
        int iNextInt = Sk.a.a().nextInt(2147418112);
        while (true) {
            int i = iNextInt + 65536;
            HashMap map2 = this.a;
            if (!map2.containsKey(Integer.valueOf(i))) {
                map2.put(Integer.valueOf(i), str);
                map.put(str, Integer.valueOf(i));
                return;
            } else {
                O o2 = Sk.a;
                iNextInt = Sk.a.a().nextInt(2147418112);
            }
        }
    }

    public final void e(String str) {
        Integer num;
        if (!this.d.contains(str) && (num = (Integer) this.b.remove(str)) != null) {
            this.a.remove(num);
        }
        this.e.remove(str);
        HashMap map = this.f;
        if (map.containsKey(str)) {
            StringBuilder sbE = AbstractC0535qo.e("Dropping pending result for request ", str, ": ");
            sbE.append(map.get(str));
            Log.w("ActivityResultRegistry", sbE.toString());
            map.remove(str);
        }
        Bundle bundle = this.g;
        if (bundle.containsKey(str)) {
            StringBuilder sbE2 = AbstractC0535qo.e("Dropping pending result for request ", str, ": ");
            sbE2.append(bundle.getParcelable(str));
            Log.w("ActivityResultRegistry", sbE2.toString());
            bundle.remove(str);
        }
        HashMap map2 = this.c;
        R0 r0 = (R0) map2.get(str);
        if (r0 != null) {
            ArrayList arrayList = r0.b;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                r0.a.f((InterfaceC0681vg) obj);
            }
            arrayList.clear();
            map2.remove(str);
        }
    }
}
