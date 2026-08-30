package rikka.shizuku;

import java.util.HashMap;

/* JADX INFO: renamed from: rikka.shizuku.zi, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0807zi {
    public static final HashMap a;

    static {
        HashMap map = new HashMap();
        a = map;
        map.put(Ej.p, "MD2");
        map.put(Ej.q, "MD4");
        map.put(Ej.r, "MD5");
        map.put(InterfaceC0191fj.a, "SHA-1");
        map.put(Qi.d, "SHA-224");
        map.put(Qi.a, "SHA-256");
        map.put(Qi.b, "SHA-384");
        map.put(Qi.c, "SHA-512");
        map.put(Qi.e, "SHA-512(224)");
        map.put(Qi.f, "SHA-512(256)");
        map.put(InterfaceC0814zp.b, "RIPEMD-128");
        map.put(InterfaceC0814zp.a, "RIPEMD-160");
        map.put(InterfaceC0814zp.c, "RIPEMD-128");
        map.put(We.b, "RIPEMD-128");
        map.put(We.a, "RIPEMD-160");
        map.put(InterfaceC0273i8.a, "GOST3411");
        map.put(Ed.a, "Tiger");
        map.put(We.c, "Whirlpool");
        map.put(Qi.g, "SHA3-224");
        map.put(Qi.h, "SHA3-256");
        map.put(Qi.i, "SHA3-384");
        map.put(Qi.j, "SHA3-512");
        map.put(Qi.k, "SHAKE128");
        map.put(Qi.l, "SHAKE256");
        map.put(Dd.a, "SM3");
    }
}
