package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public class Yj {
    public final /* synthetic */ int a;
    public final Object[] b;
    public int c;

    public Yj(int i) {
        this.a = 0;
        if (i <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
        }
        this.b = new Object[i];
    }

    public Object a() {
        switch (this.a) {
            case 0:
                int i = this.c;
                if (i <= 0) {
                    return null;
                }
                int i2 = i - 1;
                Object[] objArr = this.b;
                Object obj = objArr[i2];
                objArr[i2] = null;
                this.c = i - 1;
                return obj;
            default:
                int i3 = this.c;
                if (i3 <= 0) {
                    return null;
                }
                int i4 = i3 - 1;
                Object[] objArr2 = this.b;
                Object obj2 = objArr2[i4];
                objArr2[i4] = null;
                this.c = i3 - 1;
                return obj2;
        }
    }

    public void b(C0360l4 c0360l4) {
        int i = this.c;
        Object[] objArr = this.b;
        if (i < objArr.length) {
            objArr[i] = c0360l4;
            this.c = i + 1;
        }
    }

    public boolean c(Object obj) {
        Object[] objArr;
        boolean z;
        int i = this.c;
        int i2 = 0;
        while (true) {
            objArr = this.b;
            if (i2 >= i) {
                z = false;
                break;
            }
            if (objArr[i2] == obj) {
                z = true;
                break;
            }
            i2++;
        }
        if (z) {
            throw new IllegalStateException("Already in the pool!");
        }
        int i3 = this.c;
        if (i3 >= objArr.length) {
            return false;
        }
        objArr[i3] = obj;
        this.c = i3 + 1;
        return true;
    }

    public Yj() {
        this.a = 1;
        this.b = new Object[256];
    }
}
