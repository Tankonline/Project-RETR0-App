package rikka.shizuku;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/* JADX INFO: loaded from: classes.dex */
public final class Vd implements WildcardType, Serializable {
    public final Type a;
    public final Type b;

    public Vd(Type[] typeArr, Type[] typeArr2) {
        AbstractC0744xh.g(typeArr2.length <= 1);
        AbstractC0744xh.g(typeArr.length == 1);
        if (typeArr2.length != 1) {
            X8.k(typeArr[0]);
            this.b = null;
            this.a = X8.i(typeArr[0]);
        } else {
            X8.k(typeArr2[0]);
            AbstractC0744xh.g(typeArr[0] == Object.class);
            this.b = X8.i(typeArr2[0]);
            this.a = Object.class;
        }
    }

    public final boolean equals(Object obj) {
        return (obj instanceof WildcardType) && X8.s(this, (WildcardType) obj);
    }

    @Override // java.lang.reflect.WildcardType
    public final Type[] getLowerBounds() {
        Type type = this.b;
        return type != null ? new Type[]{type} : X8.i;
    }

    @Override // java.lang.reflect.WildcardType
    public final Type[] getUpperBounds() {
        return new Type[]{this.a};
    }

    public final int hashCode() {
        Type type = this.b;
        return (type != null ? type.hashCode() + 31 : 1) ^ (this.a.hashCode() + 31);
    }

    public final String toString() {
        Type type = this.b;
        if (type != null) {
            return "? super " + X8.a0(type);
        }
        Type type2 = this.a;
        if (type2 == Object.class) {
            return "?";
        }
        return "? extends " + X8.a0(type2);
    }
}
