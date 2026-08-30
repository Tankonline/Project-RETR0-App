package rikka.shizuku;

import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.BitSet;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/* JADX INFO: renamed from: rikka.shizuku.rr, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0568rr {
    public static final C0261hr A;
    public static final C0583sb B;
    public static final C0261hr a = new C0261hr(Class.class, new Yq().a(), 0);
    public static final C0261hr b = new C0261hr(BitSet.class, new C0291ir().a(), 0);
    public static final C0383lr c;
    public static final C0321jr d;
    public static final C0321jr e;
    public static final C0321jr f;
    public static final C0321jr g;
    public static final C0261hr h;
    public static final C0261hr i;
    public static final C0261hr j;
    public static final Pq k;
    public static final C0321jr l;
    public static final Uq m;
    public static final Vq n;
    public static final Wq o;
    public static final C0261hr p;
    public static final C0261hr q;
    public static final C0261hr r;
    public static final C0261hr s;
    public static final C0261hr t;
    public static final C0261hr u;
    public static final C0261hr v;
    public static final C0261hr w;
    public static final C0160ej x;
    public static final C0261hr y;
    public static final Qf z;

    static {
        C0352kr c0352kr = new C0352kr();
        c = new C0383lr();
        d = new C0321jr(Boolean.TYPE, Boolean.class, c0352kr);
        e = new C0321jr(Byte.TYPE, Byte.class, new C0414mr());
        f = new C0321jr(Short.TYPE, Short.class, new C0445nr());
        g = new C0321jr(Integer.TYPE, Integer.class, new C0476or());
        h = new C0261hr(AtomicInteger.class, new C0507pr().a(), 0);
        i = new C0261hr(AtomicBoolean.class, new C0538qr().a(), 0);
        j = new C0261hr(AtomicIntegerArray.class, new Oq().a(), 0);
        k = new Pq();
        new Qq();
        new Rq();
        l = new C0321jr(Character.TYPE, Character.class, new Sq());
        Tq tq = new Tq();
        m = new Uq();
        n = new Vq();
        o = new Wq();
        p = new C0261hr(String.class, tq, 0);
        q = new C0261hr(StringBuilder.class, new Xq(), 0);
        r = new C0261hr(StringBuffer.class, new Zq(), 0);
        s = new C0261hr(URL.class, new C0045ar(), 0);
        t = new C0261hr(URI.class, new C0076br(), 0);
        u = new C0261hr(InetAddress.class, new C0107cr(), 1);
        v = new C0261hr(UUID.class, new C0137dr(), 0);
        w = new C0261hr(Currency.class, new C0168er().a(), 0);
        x = new C0160ej(2, new C0199fr());
        y = new C0261hr(Locale.class, new C0230gr(), 0);
        Qf qf = Qf.a;
        z = qf;
        A = new C0261hr(Pf.class, qf, 1);
        B = C0614tb.d;
    }
}
