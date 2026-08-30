package rikka.shizuku;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: renamed from: rikka.shizuku.ul, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0655ul extends AbstractC0046as {
    @Override // rikka.shizuku.AbstractC0046as
    public final boolean G(Class cls) {
        return false;
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final Method r(Class cls, Field field) {
        throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final Constructor s(Class cls) {
        throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final String[] y(Class cls) {
        throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
    }
}
