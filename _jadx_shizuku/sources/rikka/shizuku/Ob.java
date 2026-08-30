package rikka.shizuku;

import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public final class Ob extends O {
    public final Nb b = new Nb();

    @Override // rikka.shizuku.O
    public final Random a() {
        return (Random) this.b.get();
    }
}
