package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.am, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0040am extends Q4 {
    public AbstractC0040am(G7 g7) {
        super(g7);
        if (g7 != null && g7.e() != C0152eb.a) {
            throw new IllegalArgumentException("Coroutines with restricted suspension must have EmptyCoroutineContext");
        }
    }

    @Override // rikka.shizuku.G7
    public final V7 e() {
        return C0152eb.a;
    }
}
