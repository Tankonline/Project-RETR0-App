package rikka.shizuku;

import androidx.appcompat.widget.ActionBarContextView;

/* JADX INFO: renamed from: rikka.shizuku.h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0233h implements InterfaceC0170et {
    public static final InterfaceC0202g[] d = new InterfaceC0202g[0];
    public boolean a;
    public int b;
    public Object c;

    public C0233h() {
        this(10);
    }

    public static InterfaceC0202g[] e(InterfaceC0202g[] interfaceC0202gArr) {
        return interfaceC0202gArr.length < 1 ? d : (InterfaceC0202g[]) interfaceC0202gArr.clone();
    }

    @Override // rikka.shizuku.InterfaceC0170et
    public void a() {
        if (this.a) {
            return;
        }
        ActionBarContextView actionBarContextView = (ActionBarContextView) this.c;
        actionBarContextView.f = null;
        super/*android.view.View*/.setVisibility(this.b);
    }

    @Override // rikka.shizuku.InterfaceC0170et
    public void b() {
        this.a = true;
    }

    @Override // rikka.shizuku.InterfaceC0170et
    public void c() {
        super/*android.view.View*/.setVisibility(0);
        this.a = false;
    }

    public void d(InterfaceC0202g interfaceC0202g) {
        if (interfaceC0202g == null) {
            throw new NullPointerException("'element' cannot be null");
        }
        InterfaceC0202g[] interfaceC0202gArr = (InterfaceC0202g[]) this.c;
        int length = interfaceC0202gArr.length;
        int i = this.b + 1;
        if (this.a | (i > length)) {
            InterfaceC0202g[] interfaceC0202gArr2 = new InterfaceC0202g[Math.max(interfaceC0202gArr.length, (i >> 1) + i)];
            System.arraycopy((InterfaceC0202g[]) this.c, 0, interfaceC0202gArr2, 0, this.b);
            this.c = interfaceC0202gArr2;
            this.a = false;
        }
        ((InterfaceC0202g[]) this.c)[this.b] = interfaceC0202g;
        this.b = i;
    }

    public InterfaceC0202g f(int i) {
        if (i < this.b) {
            return ((InterfaceC0202g[]) this.c)[i];
        }
        throw new ArrayIndexOutOfBoundsException(i + " >= " + this.b);
    }

    public InterfaceC0202g[] g() {
        int i = this.b;
        if (i == 0) {
            return d;
        }
        InterfaceC0202g[] interfaceC0202gArr = (InterfaceC0202g[]) this.c;
        if (interfaceC0202gArr.length == i) {
            this.a = true;
            return interfaceC0202gArr;
        }
        InterfaceC0202g[] interfaceC0202gArr2 = new InterfaceC0202g[i];
        System.arraycopy(interfaceC0202gArr, 0, interfaceC0202gArr2, 0, i);
        return interfaceC0202gArr2;
    }

    public C0233h(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("'initialCapacity' must not be negative");
        }
        this.c = i == 0 ? d : new InterfaceC0202g[i];
        this.b = 0;
        this.a = false;
    }
}
