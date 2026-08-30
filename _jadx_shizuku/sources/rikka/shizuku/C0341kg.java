package rikka.shizuku;

import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: rikka.shizuku.kg, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0341kg implements Enumeration {
    public C0355l a;
    public AbstractC0633u b;

    public final AbstractC0633u a() {
        try {
            return this.a.e();
        } catch (IOException e) {
            throw new C0602t("malformed ASN.1: " + e, e);
        }
    }

    @Override // java.util.Enumeration
    public final boolean hasMoreElements() {
        return this.b != null;
    }

    @Override // java.util.Enumeration
    public final Object nextElement() {
        AbstractC0633u abstractC0633u = this.b;
        if (abstractC0633u == null) {
            throw new NoSuchElementException();
        }
        this.b = a();
        return abstractC0633u;
    }
}
