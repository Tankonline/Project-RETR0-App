package rikka.shizuku;

import java.util.ArrayList;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public class M9 implements K9 {
    public final AbstractC0725wt d;
    public int f;
    public int g;
    public AbstractC0725wt a = null;
    public boolean b = false;
    public boolean c = false;
    public int e = 1;
    public int h = 1;
    public X9 i = null;
    public boolean j = false;
    public final ArrayList k = new ArrayList();
    public final ArrayList l = new ArrayList();

    public M9(AbstractC0725wt abstractC0725wt) {
        this.d = abstractC0725wt;
    }

    @Override // rikka.shizuku.K9
    public final void a(K9 k9) {
        ArrayList arrayList = this.l;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            if (!((M9) obj).j) {
                return;
            }
        }
        this.c = true;
        AbstractC0725wt abstractC0725wt = this.a;
        if (abstractC0725wt != null) {
            abstractC0725wt.a(this);
        }
        if (this.b) {
            this.d.a(this);
            return;
        }
        int size2 = arrayList.size();
        M9 m9 = null;
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList.get(i3);
            i3++;
            M9 m10 = (M9) obj2;
            if (!(m10 instanceof X9)) {
                i++;
                m9 = m10;
            }
        }
        if (m9 != null && i == 1 && m9.j) {
            X9 x9 = this.i;
            if (x9 != null) {
                if (!x9.j) {
                    return;
                } else {
                    this.f = this.h * x9.g;
                }
            }
            d(m9.g + this.f);
        }
        AbstractC0725wt abstractC0725wt2 = this.a;
        if (abstractC0725wt2 != null) {
            abstractC0725wt2.a(this);
        }
    }

    public final void b(AbstractC0725wt abstractC0725wt) {
        this.k.add(abstractC0725wt);
        if (this.j) {
            abstractC0725wt.a(abstractC0725wt);
        }
    }

    public final void c() {
        this.l.clear();
        this.k.clear();
        this.j = false;
        this.g = 0;
        this.c = false;
        this.b = false;
    }

    public void d(int i) {
        if (this.j) {
            return;
        }
        this.j = true;
        this.g = i;
        ArrayList arrayList = this.k;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            K9 k9 = (K9) obj;
            k9.a(k9);
        }
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.d.b.W);
        sb.append(":");
        switch (this.e) {
            case 1:
                str = "UNKNOWN";
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                str = "HORIZONTAL_DIMENSION";
                break;
            case 3:
                str = "VERTICAL_DIMENSION";
                break;
            case 4:
                str = "LEFT";
                break;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                str = "RIGHT";
                break;
            case 6:
                str = "TOP";
                break;
            case 7:
                str = "BOTTOM";
                break;
            case 8:
                str = "BASELINE";
                break;
            default:
                str = "null";
                break;
        }
        sb.append(str);
        sb.append("(");
        sb.append(this.j ? Integer.valueOf(this.g) : "unresolved");
        sb.append(") <t=");
        sb.append(this.l.size());
        sb.append(":d=");
        sb.append(this.k.size());
        sb.append(">");
        return sb.toString();
    }
}
