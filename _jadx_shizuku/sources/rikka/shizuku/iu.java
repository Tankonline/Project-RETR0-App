package rikka.shizuku;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* JADX INFO: loaded from: classes.dex */
public final class iu {
    public static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(iu.class, Object.class, "lastScheduledTask$volatile");
    public static final /* synthetic */ AtomicIntegerFieldUpdater c = AtomicIntegerFieldUpdater.newUpdater(iu.class, "producerIndex$volatile");
    public static final /* synthetic */ AtomicIntegerFieldUpdater d = AtomicIntegerFieldUpdater.newUpdater(iu.class, "consumerIndex$volatile");
    public static final /* synthetic */ AtomicIntegerFieldUpdater e = AtomicIntegerFieldUpdater.newUpdater(iu.class, "blockingTasksInBuffer$volatile");
    public final AtomicReferenceArray a = new AtomicReferenceArray(128);
    private volatile /* synthetic */ int blockingTasksInBuffer$volatile;
    private volatile /* synthetic */ int consumerIndex$volatile;
    private volatile /* synthetic */ Object lastScheduledTask$volatile;
    private volatile /* synthetic */ int producerIndex$volatile;

    public final AbstractRunnableC0721wp a() {
        AbstractRunnableC0721wp abstractRunnableC0721wp;
        while (true) {
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = d;
            int i = atomicIntegerFieldUpdater.get(this);
            if (i - c.get(this) == 0) {
                return null;
            }
            int i2 = i & 127;
            if (atomicIntegerFieldUpdater.compareAndSet(this, i, i + 1) && (abstractRunnableC0721wp = (AbstractRunnableC0721wp) this.a.getAndSet(i2, null)) != null) {
                if (abstractRunnableC0721wp.b) {
                    e.decrementAndGet(this);
                }
                return abstractRunnableC0721wp;
            }
        }
    }

    public final AbstractRunnableC0721wp b(int i, boolean z) {
        int i2 = i & 127;
        AtomicReferenceArray atomicReferenceArray = this.a;
        AbstractRunnableC0721wp abstractRunnableC0721wp = (AbstractRunnableC0721wp) atomicReferenceArray.get(i2);
        if (abstractRunnableC0721wp != null && abstractRunnableC0721wp.b == z) {
            while (!atomicReferenceArray.compareAndSet(i2, abstractRunnableC0721wp, null)) {
                if (atomicReferenceArray.get(i2) != abstractRunnableC0721wp) {
                }
            }
            if (z) {
                e.decrementAndGet(this);
            }
            return abstractRunnableC0721wp;
        }
        return null;
    }
}
