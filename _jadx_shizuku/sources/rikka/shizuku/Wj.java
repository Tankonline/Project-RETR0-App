package rikka.shizuku;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/* JADX INFO: loaded from: classes.dex */
public final class Wj extends O {
    @Override // rikka.shizuku.O
    public final Random a() {
        return ThreadLocalRandom.current();
    }
}
