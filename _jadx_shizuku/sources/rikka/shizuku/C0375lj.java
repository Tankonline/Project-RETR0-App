package rikka.shizuku;

/* JADX INFO: renamed from: rikka.shizuku.lj, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0375lj extends AbstractC0127dg implements InterfaceC0462od {
    public final /* synthetic */ int b;
    public final /* synthetic */ C0622tj c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0375lj(C0622tj c0622tj, int i) {
        super(1);
        this.b = i;
        this.c = c0622tj;
    }

    /* JADX WARN: Code duplicated, block: B:48:0x0108  */
    /* JADX WARN: Code duplicated, block: B:50:0x0111  */
    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r1v6 java.lang.Object, still in use, count: 2, list:
          (r1v6 java.lang.Object) from 0x0102: PHI (r1 I:??) = (r1v2 java.lang.Object), (r1v6 java.lang.Object) binds: [B:45:0x0101, B:63:0x0102] A[DONT_GENERATE, DONT_INLINE]
          (r1v6 java.lang.Object) from 0x00fa: CHECK_CAST (rikka.shizuku.Ic) (r1v6 java.lang.Object)
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
    @Override // rikka.shizuku.InterfaceC0462od
    public final java.lang.Object g(java.lang.Object r11) {
        /*
            Method dump skipped, instruction units count: 298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: rikka.shizuku.C0375lj.g(java.lang.Object):java.lang.Object");
    }
}
