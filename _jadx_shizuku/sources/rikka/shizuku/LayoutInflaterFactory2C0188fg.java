package rikka.shizuku;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: renamed from: rikka.shizuku.fg, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class LayoutInflaterFactory2C0188fg implements LayoutInflater.Factory2 {
    public final AbstractC0667v2 a;
    public final ArrayList b = new ArrayList();
    public final ArrayList c = new ArrayList();

    public LayoutInflaterFactory2C0188fg(AbstractC0667v2 abstractC0667v2) {
        this.a = abstractC0667v2;
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        View viewA = null;
        AbstractC0667v2 abstractC0667v2 = this.a;
        View viewB = abstractC0667v2 != null ? abstractC0667v2.b(str, context, attributeSet) : null;
        int i = 0;
        if (viewB == null) {
            ArrayList arrayList = this.b;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ((Mb) obj).getClass();
                if (str.equals(MaterialButton.class.getName())) {
                    viewB = new Kb(context, attributeSet, 0);
                } else if (str.equals(Zh.class.getName())) {
                    viewB = new Lb(context, attributeSet);
                } else {
                    viewB = str.equals(C0513q2.class.getName()) ? new Ib(context, attributeSet) : null;
                }
                if (viewB != null) {
                    break;
                }
            }
        }
        if (viewB == null) {
            Class[] clsArr = AbstractC0219gg.a;
            if (Objects.equals(str, "view")) {
                str = attributeSet.getAttributeValue(null, "class");
            }
            try {
                if (-1 != str.indexOf(46)) {
                    viewA = AbstractC0219gg.a(context, str, attributeSet, null);
                } else {
                    String[] strArr = AbstractC0219gg.b;
                    for (int i3 = 0; i3 < 3; i3++) {
                        View viewA2 = AbstractC0219gg.a(context, str, attributeSet, strArr[i3]);
                        if (viewA2 != null) {
                            viewA = viewA2;
                            break;
                        }
                    }
                }
            } catch (Exception unused) {
            }
            viewB = viewA;
        }
        if (viewB != null) {
            ArrayList arrayList2 = this.c;
            int size2 = arrayList2.size();
            while (i < size2) {
                Object obj2 = arrayList2.get(i);
                i++;
                ((InterfaceC0157eg) obj2).a(viewB, context, attributeSet);
            }
        }
        return viewB;
    }
}
