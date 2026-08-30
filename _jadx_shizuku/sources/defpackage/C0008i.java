package defpackage;

import java.util.AbstractSet;
import java.util.Iterator;

/* JADX INFO: renamed from: i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class C0008i extends AbstractSet {
    public final /* synthetic */ C0013n a;

    public C0008i(C0013n c0013n) {
        this.a = c0013n;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new C0011l(this.a);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.c;
    }
}
