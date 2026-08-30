package rikka.shizuku;

import android.content.Intent;
import java.util.ArrayList;
import java.util.Iterator;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class M0 extends AbstractC0046as {
    public final /* synthetic */ int i;

    public /* synthetic */ M0(int i) {
        this.i = i;
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final Object L(int i, Intent intent) {
        switch (this.i) {
            case 0:
                if (i != -1) {
                    intent = null;
                }
                if (intent != null) {
                    return intent.getData();
                }
                return null;
            case 1:
                C0245hb c0245hb = C0245hb.a;
                if (i != -1 || intent == null) {
                    return c0245hb;
                }
                String[] stringArrayExtra = intent.getStringArrayExtra("androidx.activity.result.contract.extra.PERMISSIONS");
                int[] intArrayExtra = intent.getIntArrayExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS");
                if (intArrayExtra == null || stringArrayExtra == null) {
                    return c0245hb;
                }
                ArrayList arrayList = new ArrayList(intArrayExtra.length);
                for (int i2 : intArrayExtra) {
                    arrayList.add(Boolean.valueOf(i2 == 0));
                }
                ArrayList arrayList2 = new ArrayList();
                for (String str : stringArrayExtra) {
                    if (str != null) {
                        arrayList2.add(str);
                    }
                }
                Iterator it = arrayList2.iterator();
                Iterator it2 = arrayList.iterator();
                ArrayList arrayList3 = new ArrayList(Math.min(AbstractC0733x6.b0(arrayList2), AbstractC0733x6.b0(arrayList)));
                while (it.hasNext() && it2.hasNext()) {
                    arrayList3.add(new Hj(it.next(), it2.next()));
                }
                return Bh.S(arrayList3);
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return new K0(i, intent);
            default:
                return new K0(i, intent);
        }
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final Intent n() {
        switch (this.i) {
            case 0:
                return new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
            case 1:
                return new Intent("androidx.activity.result.contract.action.REQUEST_PERMISSIONS").putExtra("androidx.activity.result.contract.extra.PERMISSIONS", (String[]) null);
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return null;
            default:
                new Intent("androidx.activity.result.contract.action.INTENT_SENDER_REQUEST");
                throw null;
        }
    }

    @Override // rikka.shizuku.AbstractC0046as
    public AbstractC0599sr z(AbstractActivityC0051b2 abstractActivityC0051b2) {
        switch (this.i) {
            case 0:
                return null;
            case 1:
                throw null;
            default:
                return super.z(abstractActivityC0051b2);
        }
    }
}
