package rikka.shizuku;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.s7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class C0579s7 implements InterfaceC0253hj {
    public static final C0579s7 b = new C0579s7(19);
    public static final C0579s7 c = new C0579s7(20);
    public static final C0579s7 d = new C0579s7(21);
    public static final C0579s7 e = new C0579s7(22);
    public static final C0579s7 f = new C0579s7(23);
    public final /* synthetic */ int a;

    public /* synthetic */ C0579s7(int i) {
        this.a = i;
    }

    @Override // rikka.shizuku.InterfaceC0253hj
    public Object d() {
        switch (this.a) {
            case 0:
                return new Pg(true);
            case 1:
                return new LinkedHashMap();
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                return new TreeMap();
            case 3:
                return new ConcurrentHashMap();
            case 4:
                return new ConcurrentSkipListMap();
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                return new ArrayList();
            case 6:
                return new LinkedHashSet();
            case 7:
                return new TreeSet();
            default:
                return new ArrayDeque();
        }
    }
}
