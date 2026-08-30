package rikka.shizuku;

/* JADX INFO: loaded from: classes.dex */
public class Yq extends Lq {
    @Override // rikka.shizuku.Lq
    public final Object b(Vf vf) {
        throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?\nSee " + "https://github.com/google/gson/blob/main/Troubleshooting.md#".concat("java-lang-class-unsupported"));
    }

    @Override // rikka.shizuku.Lq
    public final void c(Wf wf, Object obj) {
        throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + ((Class) obj).getName() + ". Forgot to register a type adapter?\nSee " + "https://github.com/google/gson/blob/main/Troubleshooting.md#".concat("java-lang-class-unsupported"));
    }
}
