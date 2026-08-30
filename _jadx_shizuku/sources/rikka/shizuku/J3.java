package rikka.shizuku;

import android.content.Context;
import java.util.concurrent.ConcurrentLinkedQueue;

/* JADX INFO: loaded from: classes.dex */
public final class J3 {
    public final int a;
    public final boolean b;
    public final Context c;
    public final ConcurrentLinkedQueue d = new ConcurrentLinkedQueue();

    public J3(int i, Context context, boolean z) {
        this.a = i;
        this.b = z;
        this.c = context;
    }
}
