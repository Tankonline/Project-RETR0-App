package rikka.shizuku;

import android.graphics.Canvas;
import android.graphics.Matrix;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.mn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0410mn extends AbstractC0595sn {
    public final /* synthetic */ ArrayList c;
    public final /* synthetic */ Matrix d;

    public C0410mn(ArrayList arrayList, Matrix matrix) {
        this.c = arrayList;
        this.d = matrix;
    }

    @Override // rikka.shizuku.AbstractC0595sn
    public final void a(Matrix matrix, C0287in c0287in, int i, Canvas canvas) {
        ArrayList arrayList = this.c;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((AbstractC0595sn) obj).a(this.d, c0287in, i, canvas);
        }
    }
}
