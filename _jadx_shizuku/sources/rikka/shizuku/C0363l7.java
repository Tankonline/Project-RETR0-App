package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.l7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0363l7 {
    public static final SparseIntArray m;
    public float a;
    public float b;
    public float c;
    public float d;
    public float e;
    public float f;
    public float g;
    public float h;
    public float i;
    public float j;
    public boolean k;
    public float l;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        m = sparseIntArray;
        sparseIntArray.append(6, 1);
        sparseIntArray.append(7, 2);
        sparseIntArray.append(8, 3);
        sparseIntArray.append(4, 4);
        sparseIntArray.append(5, 5);
        sparseIntArray.append(0, 6);
        sparseIntArray.append(1, 7);
        sparseIntArray.append(2, 8);
        sparseIntArray.append(3, 9);
        sparseIntArray.append(9, 10);
        sparseIntArray.append(10, 11);
    }

    public final void a(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Fk.h);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            switch (m.get(index)) {
                case 1:
                    this.a = typedArrayObtainStyledAttributes.getFloat(index, this.a);
                    break;
                case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                    this.b = typedArrayObtainStyledAttributes.getFloat(index, this.b);
                    break;
                case 3:
                    this.c = typedArrayObtainStyledAttributes.getFloat(index, this.c);
                    break;
                case 4:
                    this.d = typedArrayObtainStyledAttributes.getFloat(index, this.d);
                    break;
                case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                    this.e = typedArrayObtainStyledAttributes.getFloat(index, this.e);
                    break;
                case 6:
                    this.f = typedArrayObtainStyledAttributes.getDimension(index, this.f);
                    break;
                case 7:
                    this.g = typedArrayObtainStyledAttributes.getDimension(index, this.g);
                    break;
                case 8:
                    this.h = typedArrayObtainStyledAttributes.getDimension(index, this.h);
                    break;
                case 9:
                    this.i = typedArrayObtainStyledAttributes.getDimension(index, this.i);
                    break;
                case 10:
                    this.j = typedArrayObtainStyledAttributes.getDimension(index, this.j);
                    break;
                case 11:
                    this.k = true;
                    this.l = typedArrayObtainStyledAttributes.getDimension(index, this.l);
                    break;
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
