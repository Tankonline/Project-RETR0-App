package rikka.shizuku;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: rikka.shizuku.w, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0695w implements Enumeration {
    public int a = 0;
    public final /* synthetic */ AbstractC0726x b;

    public C0695w(AbstractC0726x abstractC0726x) {
        this.b = abstractC0726x;
    }

    @Override // java.util.Enumeration
    public final boolean hasMoreElements() {
        return this.a < this.b.a.length;
    }

    @Override // java.util.Enumeration
    public final Object nextElement() {
        int i = this.a;
        InterfaceC0202g[] interfaceC0202gArr = this.b.a;
        if (i >= interfaceC0202gArr.length) {
            throw new NoSuchElementException();
        }
        this.a = i + 1;
        return interfaceC0202gArr[i];
    }
}
