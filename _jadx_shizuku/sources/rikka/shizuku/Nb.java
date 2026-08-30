package rikka.shizuku;

import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public final class Nb extends ThreadLocal {
    @Override // java.lang.ThreadLocal
    public final Object initialValue() {
        return new Random();
    }
}
