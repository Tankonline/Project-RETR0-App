package rikka.shizuku;

import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public final class Os extends AbstractC0040am implements InterfaceC0585sd, Cd {
    public final int b;
    public int c;
    public /* synthetic */ Object d;
    public final /* synthetic */ View e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Os(View view, G7 g7) {
        super(g7);
        this.e = view;
        this.b = 2;
    }

    @Override // rikka.shizuku.Cd
    public final int b() {
        return this.b;
    }

    @Override // rikka.shizuku.InterfaceC0585sd
    public final Object f(Object obj, Object obj2) {
        return ((Os) i((Sm) obj, (G7) obj2)).j(Pe.b);
    }

    @Override // rikka.shizuku.Q4
    public final G7 i(Object obj, G7 g7) {
        Os os = new Os(this.e, g7);
        os.d = obj;
        return os;
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        Object obj2;
        EnumC0057b8 enumC0057b8 = EnumC0057b8.a;
        int i = this.c;
        View view = this.e;
        if (i == 0) {
            AbstractC0046as.V(obj);
            Sm sm = (Sm) this.d;
            this.d = sm;
            this.c = 1;
            sm.b = view;
            sm.a = 3;
            sm.d = this;
            return enumC0057b8;
        }
        Pe pe = Pe.b;
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            AbstractC0046as.V(obj);
            return pe;
        }
        Sm sm2 = (Sm) this.d;
        AbstractC0046as.V(obj);
        if (view instanceof ViewGroup) {
            this.d = null;
            this.c = 2;
            sm2.getClass();
            Fq fq = new Fq(new K(1, (ViewGroup) view));
            if (fq.b.hasNext()) {
                sm2.c = fq;
                sm2.a = 2;
                sm2.d = this;
                obj2 = enumC0057b8;
            } else {
                obj2 = pe;
            }
            if (obj2 != enumC0057b8) {
                obj2 = pe;
            }
            if (obj2 == enumC0057b8) {
                return enumC0057b8;
            }
        }
        return pe;
    }

    @Override // rikka.shizuku.Q4
    public final String toString() {
        if (this.a != null) {
            return super.toString();
        }
        AbstractC0593sl.a.getClass();
        return C0624tl.a(this);
    }
}
