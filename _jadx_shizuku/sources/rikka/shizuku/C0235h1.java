package rikka.shizuku;

import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Base64;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.GCMParameterSpec;
import javax.net.ssl.SSLContext;

/* JADX INFO: renamed from: rikka.shizuku.h1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0235h1 {
    public static final byte[] f = {0, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 48, 33, 48, 9, 6, 5, 43, 14, 3, 2, 26, 5, 0, 4, 20};
    public final Object a;
    public final Object b;
    public final Object c;
    public final Object d;
    public final Object e;

    public C0235h1(ScrollView scrollView, TextInputLayout textInputLayout, TextInputLayout textInputLayout2, TextView textView, LinearLayout linearLayout) {
        this.a = scrollView;
        this.b = textInputLayout;
        this.c = textInputLayout2;
        this.d = textView;
        this.e = linearLayout;
    }

    public C0235h1(Ys ys) throws IllegalBlockSizeException, IOException, KeyStoreException, CertificateException, InvalidAlgorithmParameterException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeyException, C0777yj, NoSuchProviderException, ShortBufferException {
        byte[] bArrDoFinal;
        RSAPrivateKey rSAPrivateKey;
        K1 k1;
        byte[] bArr;
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        Key key = keyStore.getKey("_adbkey_encryption_key_", null);
        if (key == null) {
            KeyGenParameterSpec keyGenParameterSpecBuild = new KeyGenParameterSpec.Builder("_adbkey_encryption_key_", 3).setBlockModes("GCM").setEncryptionPaddings("NoPadding").setKeySize(256).build();
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
            keyGenerator.init(keyGenParameterSpecBuild);
            key = keyGenerator.generateKey();
        }
        if (key != null) {
            byte[] bArr2 = new byte[16];
            AbstractC0484p4.g0("adbkey".getBytes(P5.a), bArr2, 0, 14);
            SharedPreferences sharedPreferences = (SharedPreferences) ys.b;
            byte[] bArrDecode = !sharedPreferences.contains("adbkey") ? null : Base64.decode(sharedPreferences.getString("adbkey", null), 2);
            if (bArrDecode != null) {
                try {
                    if (bArrDecode.length < 28) {
                        bArrDoFinal = null;
                    } else {
                        GCMParameterSpec gCMParameterSpec = new GCMParameterSpec(128, bArrDecode, 0, 12);
                        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                        cipher.init(2, key, gCMParameterSpec);
                        cipher.updateAAD(bArr2);
                        bArrDoFinal = cipher.doFinal(bArrDecode, 12, bArrDecode.length - 12);
                    }
                    rSAPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bArrDoFinal));
                } catch (Exception unused) {
                    rSAPrivateKey = null;
                }
            } else {
                rSAPrivateKey = null;
            }
            if (rSAPrivateKey == null) {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4));
                rSAPrivateKey = (RSAPrivateKey) keyPairGenerator.generateKeyPair().getPrivate();
                byte[] encoded = rSAPrivateKey.getEncoded();
                if (encoded.length > 2147483619) {
                    bArr = null;
                } else {
                    byte[] bArr3 = new byte[encoded.length + 28];
                    Cipher cipher2 = Cipher.getInstance("AES/GCM/NoPadding");
                    cipher2.init(1, key);
                    cipher2.updateAAD(bArr2);
                    cipher2.doFinal(encoded, 0, encoded.length, bArr3, 12);
                    bArr = bArr3;
                    System.arraycopy(cipher2.getIV(), 0, bArr, 0, 12);
                }
                if (bArr != null) {
                    SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                    editorEdit.putString("adbkey", new String(Base64.encode(bArr, 2), P5.a));
                    editorEdit.apply();
                }
            }
            this.a = rSAPrivateKey;
            RSAPublicKey rSAPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(rSAPrivateKey.getModulus(), RSAKeyGenParameterSpec.F4));
            this.b = rSAPublicKey;
            C0808zj c0808zj = new C0808zj();
            String strE = Wo.e("SHA256withRSA");
            C0510q c0510q = (C0510q) C0581s9.a.get(strE);
            if (c0510q != null) {
                if (C0581s9.b.contains(c0510q)) {
                    k1 = new K1();
                    k1.a = c0510q;
                } else {
                    HashMap map = C0581s9.c;
                    k1 = map.containsKey(strE) ? new K1(c0510q, (InterfaceC0202g) map.get(strE)) : new K1(c0510q, C0766y8.a);
                }
                try {
                    Signature signatureA = c0808zj.a(k1);
                    signatureA.initSign(rSAPrivateKey);
                    C0073bo c0073bo = new C0073bo();
                    c0073bo.a = signatureA;
                    lu luVar = new lu();
                    BigInteger bigInteger = BigInteger.ONE;
                    Date date = new Date(0L);
                    Date date2 = new Date(2461449600000L);
                    Locale locale = Locale.ROOT;
                    lu luVar2 = new lu();
                    C0197fp c0197fpI = C0197fp.i(rSAPublicKey.getEncoded());
                    Qp qp = new Qp(date);
                    Qp qp2 = new Qp(date2);
                    C0077bs c0077bs = new C0077bs();
                    c0077bs.a = new H4(true, 0, (InterfaceC0202g) new C0386m(2L), 1);
                    c0077bs.b = new C0386m(bigInteger);
                    c0077bs.d = luVar;
                    c0077bs.e = qp;
                    c0077bs.f = qp2;
                    c0077bs.g = luVar2;
                    c0077bs.h = c0197fpI;
                    Fb fb = new Fb();
                    fb.a = new Hashtable();
                    Vector vector = new Vector();
                    c0077bs.c = k1;
                    if (!vector.isEmpty()) {
                        int size = vector.size();
                        Db[] dbArr = new Db[size];
                        for (int i = 0; i != vector.size(); i++) {
                            dbArr[i] = (Db) fb.a.get(vector.elementAt(i));
                        }
                        Eb eb = new Eb();
                        eb.a = new Hashtable();
                        eb.b = new Vector();
                        for (int i2 = 0; i2 != size; i2++) {
                            Db db = dbArr[i2];
                            eb.b.addElement(db.a);
                            eb.a.put(db.a, db);
                        }
                        c0077bs.i = eb;
                        Db db2 = (Db) eb.a.get(Db.e);
                        if (db2 != null && db2.b) {
                            c0077bs.j = true;
                        }
                    }
                    try {
                        C0659up c0659upA = c0077bs.a();
                        c0659upA.d().l(c0073bo);
                        c0073bo.close();
                        try {
                            H5 h5B = Hn.b(c0659upA, k1, signatureA.sign());
                            h5B.b.getClass();
                            this.c = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(h5B.g()));
                            ((RSAPrivateKey) this.a).toString();
                            final int i3 = 0;
                            this.d = AbstractC0046as.H(new InterfaceC0124dd(this) { // from class: rikka.shizuku.e1
                                public final /* synthetic */ C0235h1 b;

                                {
                                    this.b = this;
                                }

                                @Override // rikka.shizuku.InterfaceC0124dd
                                public final Object a() throws NoSuchAlgorithmException, KeyManagementException {
                                    switch (i3) {
                                        case 0:
                                            RSAPublicKey rSAPublicKey2 = (RSAPublicKey) this.b.b;
                                            BigInteger bigInteger2 = BigInteger.ZERO;
                                            BigInteger bit = bigInteger2.setBit(32);
                                            BigInteger bigIntegerNegate = rSAPublicKey2.getModulus().remainder(bit).modInverse(bit).negate();
                                            BigInteger bigIntegerModPow = bigInteger2.setBit(2048).modPow(BigInteger.valueOf(2L), rSAPublicKey2.getModulus());
                                            ByteBuffer byteBufferOrder = ByteBuffer.allocate(524).order(ByteOrder.LITTLE_ENDIAN);
                                            byteBufferOrder.putInt(64);
                                            byteBufferOrder.putInt(bigIntegerNegate.intValue());
                                            int[] iArrY = X8.Y(rSAPublicKey2.getModulus());
                                            for (int i4 = 0; i4 < 64; i4++) {
                                                byteBufferOrder.putInt(iArrY[i4]);
                                            }
                                            int[] iArrY2 = X8.Y(bigIntegerModPow);
                                            for (int i5 = 0; i5 < 64; i5++) {
                                                byteBufferOrder.putInt(iArrY2[i5]);
                                            }
                                            byteBufferOrder.putInt(rSAPublicKey2.getPublicExponent().intValue());
                                            byte[] bArrEncode = Base64.encode(byteBufferOrder.array(), 2);
                                            byte[] bytes = " shizuku\u0000".getBytes(P5.a);
                                            byte[] bArr4 = new byte[bArrEncode.length + bytes.length];
                                            AbstractC0484p4.g0(bArrEncode, bArr4, 0, 14);
                                            AbstractC0484p4.g0(bytes, bArr4, bArrEncode.length, 12);
                                            return bArr4;
                                        default:
                                            SSLContext sSLContext = SSLContext.getInstance("TLSv1.3");
                                            C0235h1 c0235h1 = this.b;
                                            c0235h1.getClass();
                                            sSLContext.init(new C0173f1[]{new C0173f1(c0235h1)}, new C0204g1[]{new C0204g1()}, new SecureRandom());
                                            return sSLContext;
                                    }
                                }
                            });
                            final int i4 = 1;
                            this.e = AbstractC0046as.H(new InterfaceC0124dd(this) { // from class: rikka.shizuku.e1
                                public final /* synthetic */ C0235h1 b;

                                {
                                    this.b = this;
                                }

                                @Override // rikka.shizuku.InterfaceC0124dd
                                public final Object a() throws NoSuchAlgorithmException, KeyManagementException {
                                    switch (i4) {
                                        case 0:
                                            RSAPublicKey rSAPublicKey2 = (RSAPublicKey) this.b.b;
                                            BigInteger bigInteger2 = BigInteger.ZERO;
                                            BigInteger bit = bigInteger2.setBit(32);
                                            BigInteger bigIntegerNegate = rSAPublicKey2.getModulus().remainder(bit).modInverse(bit).negate();
                                            BigInteger bigIntegerModPow = bigInteger2.setBit(2048).modPow(BigInteger.valueOf(2L), rSAPublicKey2.getModulus());
                                            ByteBuffer byteBufferOrder = ByteBuffer.allocate(524).order(ByteOrder.LITTLE_ENDIAN);
                                            byteBufferOrder.putInt(64);
                                            byteBufferOrder.putInt(bigIntegerNegate.intValue());
                                            int[] iArrY = X8.Y(rSAPublicKey2.getModulus());
                                            for (int i5 = 0; i5 < 64; i5++) {
                                                byteBufferOrder.putInt(iArrY[i5]);
                                            }
                                            int[] iArrY2 = X8.Y(bigIntegerModPow);
                                            for (int i6 = 0; i6 < 64; i6++) {
                                                byteBufferOrder.putInt(iArrY2[i6]);
                                            }
                                            byteBufferOrder.putInt(rSAPublicKey2.getPublicExponent().intValue());
                                            byte[] bArrEncode = Base64.encode(byteBufferOrder.array(), 2);
                                            byte[] bytes = " shizuku\u0000".getBytes(P5.a);
                                            byte[] bArr4 = new byte[bArrEncode.length + bytes.length];
                                            AbstractC0484p4.g0(bArrEncode, bArr4, 0, 14);
                                            AbstractC0484p4.g0(bytes, bArr4, bArrEncode.length, 12);
                                            return bArr4;
                                        default:
                                            SSLContext sSLContext = SSLContext.getInstance("TLSv1.3");
                                            C0235h1 c0235h1 = this.b;
                                            c0235h1.getClass();
                                            sSLContext.init(new C0173f1[]{new C0173f1(c0235h1)}, new C0204g1[]{new C0204g1()}, new SecureRandom());
                                            return sSLContext;
                                    }
                                }
                            });
                            return;
                        } catch (SignatureException e) {
                            C0347km c0347km = new C0347km("exception obtaining signature: " + e.getMessage());
                            c0347km.a = e;
                            throw c0347km;
                        }
                    } catch (IOException unused2) {
                        throw new IllegalArgumentException("cannot produce certificate signature");
                    }
                } catch (GeneralSecurityException e2) {
                    C0777yj c0777yj = new C0777yj("cannot create signer: " + e2.getMessage());
                    c0777yj.a = e2;
                    throw c0777yj;
                }
            }
            throw new IllegalArgumentException("Unknown signature type requested: ".concat(strE));
        }
        throw new IllegalStateException("Failed to generate encryption key with AndroidKeyManager.");
    }
}
