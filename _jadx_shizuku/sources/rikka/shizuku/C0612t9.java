package rikka.shizuku;

import java.util.HashMap;

/* JADX INFO: renamed from: rikka.shizuku.t9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0612t9 {
    public static final HashMap a;
    public static final HashMap b;

    static {
        HashMap map = new HashMap();
        a = map;
        HashMap map2 = new HashMap();
        b = map2;
        map.put(Ej.g, "RSASSA-PSS");
        map.put(InterfaceC0706wa.a, "ED25519");
        map.put(InterfaceC0706wa.b, "ED448");
        map.put(new C0510q("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
        map.put(Ej.k, "SHA224WITHRSA");
        map.put(Ej.h, "SHA256WITHRSA");
        map.put(Ej.i, "SHA384WITHRSA");
        map.put(Ej.j, "SHA512WITHRSA");
        map.put(InterfaceC0546r5.a, "SHAKE128WITHRSAPSS");
        map.put(InterfaceC0546r5.b, "SHAKE256WITHRSAPSS");
        map.put(InterfaceC0273i8.c, "GOST3411WITHGOST3410");
        map.put(InterfaceC0273i8.d, "GOST3411WITHECGOST3410");
        map.put(InterfaceC0163em.c, "GOST3411-2012-256WITHECGOST3410-2012-256");
        map.put(InterfaceC0163em.d, "GOST3411-2012-512WITHECGOST3410-2012-512");
        map.put(J4.a, "SHA1WITHPLAIN-ECDSA");
        map.put(J4.b, "SHA224WITHPLAIN-ECDSA");
        map.put(J4.c, "SHA256WITHPLAIN-ECDSA");
        map.put(J4.d, "SHA384WITHPLAIN-ECDSA");
        map.put(J4.e, "SHA512WITHPLAIN-ECDSA");
        map.put(J4.g, "SHA3-224WITHPLAIN-ECDSA");
        map.put(J4.h, "SHA3-256WITHPLAIN-ECDSA");
        map.put(J4.i, "SHA3-384WITHPLAIN-ECDSA");
        map.put(J4.j, "SHA3-512WITHPLAIN-ECDSA");
        map.put(J4.f, "RIPEMD160WITHPLAIN-ECDSA");
        map.put(InterfaceC0675va.a, "SHA1WITHCVC-ECDSA");
        map.put(InterfaceC0675va.b, "SHA224WITHCVC-ECDSA");
        map.put(InterfaceC0675va.c, "SHA256WITHCVC-ECDSA");
        map.put(InterfaceC0675va.d, "SHA384WITHCVC-ECDSA");
        map.put(InterfaceC0675va.e, "SHA512WITHCVC-ECDSA");
        map.put(Af.a, "XMSS");
        map.put(Af.b, "XMSSMT");
        map.put(InterfaceC0814zp.e, "RIPEMD128WITHRSA");
        map.put(InterfaceC0814zp.d, "RIPEMD160WITHRSA");
        map.put(InterfaceC0814zp.f, "RIPEMD256WITHRSA");
        map.put(new C0510q("1.2.840.113549.1.1.4"), "MD5WITHRSA");
        map.put(new C0510q("1.2.840.113549.1.1.2"), "MD2WITHRSA");
        map.put(new C0510q("1.2.840.10040.4.3"), "SHA1WITHDSA");
        map.put(ou.a, "SHA1WITHECDSA");
        map.put(ou.b, "SHA224WITHECDSA");
        map.put(ou.c, "SHA256WITHECDSA");
        map.put(ou.d, "SHA384WITHECDSA");
        map.put(ou.e, "SHA512WITHECDSA");
        map.put(InterfaceC0546r5.c, "SHAKE128WITHECDSA");
        map.put(InterfaceC0546r5.d, "SHAKE256WITHECDSA");
        map.put(InterfaceC0191fj.c, "SHA1WITHRSA");
        map.put(InterfaceC0191fj.b, "SHA1WITHDSA");
        map.put(Qi.v, "SHA224WITHDSA");
        map.put(Qi.w, "SHA256WITHDSA");
        map2.put(InterfaceC0191fj.a, "SHA1");
        map2.put(Qi.d, "SHA224");
        map2.put(Qi.a, "SHA256");
        map2.put(Qi.b, "SHA384");
        map2.put(Qi.c, "SHA512");
        map2.put(Qi.g, "SHA3-224");
        map2.put(Qi.h, "SHA3-256");
        map2.put(Qi.i, "SHA3-384");
        map2.put(Qi.j, "SHA3-512");
        map2.put(InterfaceC0814zp.b, "RIPEMD128");
        map2.put(InterfaceC0814zp.a, "RIPEMD160");
        map2.put(InterfaceC0814zp.c, "RIPEMD256");
    }

    public static String a(C0510q c0510q) {
        String str = (String) b.get(c0510q);
        return str != null ? str : c0510q.a;
    }
}
