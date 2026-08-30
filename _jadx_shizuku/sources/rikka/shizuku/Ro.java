package rikka.shizuku;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: classes.dex */
public final class Ro extends AbstractC0599sr {
    public static final /* synthetic */ AtomicReferenceFieldUpdater J = AtomicReferenceFieldUpdater.newUpdater(Ro.class, Object.class, "_state$volatile");
    public int I;
    private volatile /* synthetic */ Object _state$volatile;

    public Ro(Object obj) {
        this._state$volatile = obj;
    }
}
