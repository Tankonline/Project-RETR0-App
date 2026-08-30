package rikka.shizuku;

import java.util.concurrent.CancellationException;

/* JADX INFO: loaded from: classes.dex */
public final class Ff extends CancellationException {
    public final transient Kf a;

    public Ff(String str, Throwable th, Kf kf) {
        super(str);
        this.a = kf;
        if (th != null) {
            initCause(th);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ff)) {
            return false;
        }
        Ff ff = (Ff) obj;
        if (!AbstractC0046as.c(ff.getMessage(), getMessage())) {
            return false;
        }
        Object obj2 = ff.a;
        if (obj2 == null) {
            obj2 = C0068bj.b;
        }
        Object obj3 = this.a;
        if (obj3 == null) {
            obj3 = C0068bj.b;
        }
        return obj2.equals(obj3) && AbstractC0046as.c(ff.getCause(), getCause());
    }

    @Override // java.lang.Throwable
    public final Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }

    public final int hashCode() {
        int iHashCode = getMessage().hashCode() * 31;
        Object obj = this.a;
        if (obj == null) {
            obj = C0068bj.b;
        }
        int iHashCode2 = (obj.hashCode() + iHashCode) * 31;
        Throwable cause = getCause();
        return iHashCode2 + (cause != null ? cause.hashCode() : 0);
    }

    @Override // java.lang.Throwable
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("; job=");
        Object obj = this.a;
        if (obj == null) {
            obj = C0068bj.b;
        }
        sb.append(obj);
        return sb.toString();
    }
}
