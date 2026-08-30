package rikka.shizuku;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.spec.PSSParameterSpec;
import java.util.HashMap;

/* JADX INFO: renamed from: rikka.shizuku.zj, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0808zj {
    public static final HashMap a;
    public static final C0612t9 b;

    static {
        HashMap map = new HashMap();
        a = map;
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        HashMap map4 = new HashMap();
        HashMap map5 = new HashMap();
        b = new C0612t9();
        map.put(InterfaceC0191fj.a, "SHA1");
        map.put(Qi.d, "SHA224");
        map.put(Qi.a, "SHA256");
        map.put(Qi.b, "SHA384");
        map.put(Qi.c, "SHA512");
        map.put(InterfaceC0814zp.b, "RIPEMD128");
        map.put(InterfaceC0814zp.a, "RIPEMD160");
        map.put(InterfaceC0814zp.c, "RIPEMD256");
        map2.put(Ej.a, "RSA/ECB/PKCS1Padding");
        map2.put(InterfaceC0273i8.b, "ECGOST3410");
        C0510q c0510q = Ej.w;
        map3.put(c0510q, "DESEDEWrap");
        map3.put(Ej.x, "RC2Wrap");
        C0510q c0510q2 = Qi.q;
        map3.put(c0510q2, "AESWrap");
        C0510q c0510q3 = Qi.s;
        map3.put(c0510q3, "AESWrap");
        C0510q c0510q4 = Qi.u;
        map3.put(c0510q4, "AESWrap");
        C0510q c0510q5 = Ri.a;
        map3.put(c0510q5, "CamelliaWrap");
        C0510q c0510q6 = Ri.b;
        map3.put(c0510q6, "CamelliaWrap");
        C0510q c0510q7 = Ri.c;
        map3.put(c0510q7, "CamelliaWrap");
        C0510q c0510q8 = Zf.a;
        map3.put(c0510q8, "SEEDWrap");
        C0510q c0510q9 = Ej.n;
        map3.put(c0510q9, "DESede");
        map5.put(c0510q, 192);
        map5.put(c0510q2, 128);
        map5.put(c0510q3, 192);
        map5.put(c0510q4, 256);
        map5.put(c0510q5, 128);
        map5.put(c0510q6, 192);
        map5.put(c0510q7, 256);
        map5.put(c0510q8, 128);
        map5.put(c0510q9, 192);
        map4.put(Qi.o, "AES");
        map4.put(Qi.p, "AES");
        map4.put(Qi.r, "AES");
        map4.put(Qi.t, "AES");
        map4.put(c0510q9, "DESede");
        map4.put(Ej.o, "RC2");
    }

    /* JADX WARN: Code duplicated, block: B:75:0x0195 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public final Signature a(K1 k1) {
        String string;
        Signature signature;
        AbstractC0726x abstractC0726xT;
        MessageDigest messageDigest;
        String string2;
        C0766y8 c0766y8;
        StringBuilder sb;
        String strA;
        b.getClass();
        InterfaceC0202g interfaceC0202g = k1.b;
        C0510q c0510q = k1.a;
        if (interfaceC0202g == null || (c0766y8 = C0766y8.a) == interfaceC0202g || c0766y8.i(interfaceC0202g.d()) || !c0510q.n(Ej.g)) {
            HashMap map = C0612t9.a;
            string = map.containsKey(c0510q) ? (String) map.get(c0510q) : c0510q.a;
        } else {
            Pk pkI = Pk.i(interfaceC0202g);
            K1 k2 = pkI.b;
            boolean zN = k2.a.n(Ej.f);
            K1 k3 = pkI.a;
            if (zN) {
                C0510q c0510q2 = K1.i(k2.b).a;
                boolean zN2 = c0510q2.n(k3.a);
                C0510q c0510q3 = k3.a;
                if (zN2) {
                    string = C0612t9.a(c0510q3) + "WITHRSAANDMGF1";
                } else {
                    sb = new StringBuilder();
                    sb.append(C0612t9.a(c0510q3));
                    sb.append("WITHRSAANDMGF1USING");
                    strA = C0612t9.a(c0510q2);
                }
            } else {
                sb = new StringBuilder();
                sb.append(C0612t9.a(k3.a));
                sb.append("WITHRSAAND");
                strA = k2.a.a;
            }
            sb.append(strA);
            string = sb.toString();
        }
        try {
            signature = Signature.getInstance(string);
        } catch (NoSuchAlgorithmException e) {
            if (!string.endsWith("WITHRSAANDMGF1")) {
                throw e;
            }
            signature = Signature.getInstance(string.substring(0, string.indexOf(87)) + "WITHRSASSA-PSS");
        }
        if (c0510q.n(Ej.g) && (abstractC0726xT = AbstractC0726x.t(k1.b)) != null && abstractC0726xT.size() != 0) {
            Pk pkI2 = Pk.i(abstractC0726xT);
            if (pkI2.b.a.n(Ej.f)) {
                K1 k1I = K1.i(pkI2.b.b);
                K1 k4 = pkI2.a;
                boolean zEquals = k4.equals(k1I);
                C0510q c0510q4 = k4.a;
                if (zEquals) {
                    try {
                        boolean zN3 = c0510q4.n(Qi.n);
                        InterfaceC0202g interfaceC0202g2 = k4.b;
                        if (zN3) {
                            StringBuilder sb2 = new StringBuilder("SHAKE256-");
                            C0386m c0386mR = C0386m.r(interfaceC0202g2);
                            c0386mR.getClass();
                            sb2.append(new BigInteger(c0386mR.a));
                            string2 = sb2.toString();
                        } else if (c0510q4.n(Qi.m)) {
                            StringBuilder sb3 = new StringBuilder("SHAKE128-");
                            C0386m c0386mR2 = C0386m.r(interfaceC0202g2);
                            c0386mR2.getClass();
                            sb3.append(new BigInteger(c0386mR2.a));
                            string2 = sb3.toString();
                        } else {
                            string2 = (String) AbstractC0807zi.a.get(c0510q4);
                            if (string2 == null) {
                                string2 = c0510q4.a;
                            }
                        }
                        messageDigest = MessageDigest.getInstance(string2);
                    } catch (NoSuchAlgorithmException e2) {
                        HashMap map2 = a;
                        if (map2.get(c0510q4) == null) {
                            throw e2;
                        }
                        messageDigest = MessageDigest.getInstance((String) map2.get(c0510q4));
                    }
                    C0386m c0386m = pkI2.c;
                    c0386m.getClass();
                    if (new BigInteger(c0386m.a).intValue() != messageDigest.getDigestLength()) {
                        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("PSS");
                        algorithmParameters.init(abstractC0726xT.g());
                        signature.setParameter(algorithmParameters.getParameterSpec(PSSParameterSpec.class));
                    }
                } else {
                    AlgorithmParameters algorithmParameters2 = AlgorithmParameters.getInstance("PSS");
                    algorithmParameters2.init(abstractC0726xT.g());
                    signature.setParameter(algorithmParameters2.getParameterSpec(PSSParameterSpec.class));
                }
            } else {
                try {
                    AlgorithmParameters algorithmParameters3 = AlgorithmParameters.getInstance("PSS");
                    algorithmParameters3.init(abstractC0726xT.g());
                    signature.setParameter(algorithmParameters3.getParameterSpec(PSSParameterSpec.class));
                } catch (IOException e3) {
                    throw new GeneralSecurityException("unable to process PSS parameters: " + e3.getMessage());
                }
            }
        }
        return signature;
    }
}
