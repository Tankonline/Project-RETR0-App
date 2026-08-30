package rikka.shizuku;

import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
public final class Td implements GenericArrayType, Serializable {
    public final Type a;

    public Td(Type type) {
        this.a = X8.i(type);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof GenericArrayType) && X8.s(this, (GenericArrayType) obj);
    }

    @Override // java.lang.reflect.GenericArrayType
    public final Type getGenericComponentType() {
        return this.a;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return X8.a0(this.a) + "[]";
    }
}
