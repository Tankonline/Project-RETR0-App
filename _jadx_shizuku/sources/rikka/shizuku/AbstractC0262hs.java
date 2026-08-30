package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.hs, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0262hs extends AbstractC0231gs {
    public Pj[] a;
    public String b;
    public int c;

    public AbstractC0262hs() {
        this.a = null;
        this.c = 0;
    }

    public Pj[] getPathData() {
        return this.a;
    }

    public String getPathName() {
        return this.b;
    }

    public void setPathData(Pj[] pjArr) {
        if (!AbstractC0046as.g(this.a, pjArr)) {
            this.a = AbstractC0046as.q(pjArr);
            return;
        }
        Pj[] pjArr2 = this.a;
        for (int i = 0; i < pjArr.length; i++) {
            pjArr2[i].a = pjArr[i].a;
            int i2 = 0;
            while (true) {
                float[] fArr = pjArr[i].b;
                if (i2 < fArr.length) {
                    pjArr2[i].b[i2] = fArr[i2];
                    i2++;
                }
            }
        }
    }

    public AbstractC0262hs(AbstractC0262hs abstractC0262hs) {
        this.a = null;
        this.c = 0;
        this.b = abstractC0262hs.b;
        this.a = AbstractC0046as.q(abstractC0262hs.a);
    }
}
