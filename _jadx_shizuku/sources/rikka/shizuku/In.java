package rikka.shizuku;

import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class In implements Predicate {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ In(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Ln ln = (Ln) obj;
        switch (this.a) {
            case 0:
                return ln.a == ((T3) this.b);
            case 1:
                return ln.a == ((Mn) this.b);
            default:
                return ln.a == ((C0719wn) this.b);
        }
    }
}
