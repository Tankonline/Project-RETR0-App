package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.mj, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0406mj extends AbstractC0127dg implements InterfaceC0124dd {
    public final /* synthetic */ int b;
    public final /* synthetic */ C0622tj c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0406mj(C0622tj c0622tj, int i) {
        super(0);
        this.b = i;
        this.c = c0622tj;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r3v4 java.lang.Object, still in use, count: 2, list:
          (r3v4 java.lang.Object) from 0x0031: PHI (r3 I:??) = (r3v2 java.lang.Object), (r3v4 java.lang.Object) binds: [B:14:0x0030, B:24:0x0031] A[DONT_GENERATE, DONT_INLINE]
          (r3v4 java.lang.Object) from 0x0029: CHECK_CAST (rikka.shizuku.Ic) (r3v4 java.lang.Object)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:93)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:132)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:67)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:50)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:96)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:36)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:44)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    @Override // rikka.shizuku.InterfaceC0124dd
    public final java.lang.Object a() {
        /*
            r5 = this;
            int r0 = r5.b
            switch(r0) {
                case 0: goto L3e;
                case 1: goto Ld;
                default: goto L5;
            }
        L5:
            rikka.shizuku.tj r0 = r5.c
            r0.a()
            rikka.shizuku.Pe r0 = rikka.shizuku.Pe.b
            return r0
        Ld:
            rikka.shizuku.tj r0 = r5.c
            rikka.shizuku.Ic r1 = r0.c
            r2 = 0
            if (r1 != 0) goto L34
            rikka.shizuku.c4 r1 = r0.b
            int r3 = r1.size()
            java.util.ListIterator r1 = r1.listIterator(r3)
        L1e:
            boolean r3 = r1.hasPrevious()
            if (r3 == 0) goto L30
            java.lang.Object r3 = r1.previous()
            r4 = r3
            rikka.shizuku.Ic r4 = (rikka.shizuku.Ic) r4
            boolean r4 = r4.a
            if (r4 == 0) goto L1e
            goto L31
        L30:
            r3 = r2
        L31:
            r1 = r3
            rikka.shizuku.Ic r1 = (rikka.shizuku.Ic) r1
        L34:
            r0.c = r2
            if (r1 == 0) goto L3b
            r1.a()
        L3b:
            rikka.shizuku.Pe r0 = rikka.shizuku.Pe.b
            return r0
        L3e:
            rikka.shizuku.tj r0 = r5.c
            r0.a()
            rikka.shizuku.Pe r0 = rikka.shizuku.Pe.b
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: rikka.shizuku.C0406mj.a():java.lang.Object");
    }
}
