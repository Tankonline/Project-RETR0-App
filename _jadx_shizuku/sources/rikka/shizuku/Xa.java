package rikka.shizuku;

import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.SparseArray;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public final class Xa extends AbstractC0046as {
    public final TextView i;
    public final Ta j;
    public boolean k = true;

    public Xa(TextView textView) {
        this.i = textView;
        this.j = new Ta(textView);
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final boolean E() {
        return this.k;
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final void S(boolean z) {
        if (z) {
            TextView textView = this.i;
            textView.setTransformationMethod(Z(textView.getTransformationMethod()));
        }
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final void T(boolean z) {
        this.k = z;
        TextView textView = this.i;
        textView.setTransformationMethod(Z(textView.getTransformationMethod()));
        textView.setFilters(w(textView.getFilters()));
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final TransformationMethod Z(TransformationMethod transformationMethod) {
        if (this.k) {
            return ((transformationMethod instanceof C0060bb) || (transformationMethod instanceof PasswordTransformationMethod)) ? transformationMethod : new C0060bb(transformationMethod);
        }
        return transformationMethod instanceof C0060bb ? ((C0060bb) transformationMethod).a : transformationMethod;
    }

    @Override // rikka.shizuku.AbstractC0046as
    public final InputFilter[] w(InputFilter[] inputFilterArr) {
        if (!this.k) {
            SparseArray sparseArray = new SparseArray(1);
            for (int i = 0; i < inputFilterArr.length; i++) {
                InputFilter inputFilter = inputFilterArr[i];
                if (inputFilter instanceof Ta) {
                    sparseArray.put(i, inputFilter);
                }
            }
            if (sparseArray.size() == 0) {
                return inputFilterArr;
            }
            int length = inputFilterArr.length;
            InputFilter[] inputFilterArr2 = new InputFilter[inputFilterArr.length - sparseArray.size()];
            int i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                if (sparseArray.indexOfKey(i3) < 0) {
                    inputFilterArr2[i2] = inputFilterArr[i3];
                    i2++;
                }
            }
            return inputFilterArr2;
        }
        int length2 = inputFilterArr.length;
        int i4 = 0;
        while (true) {
            Ta ta = this.j;
            if (i4 >= length2) {
                InputFilter[] inputFilterArr3 = new InputFilter[inputFilterArr.length + 1];
                System.arraycopy(inputFilterArr, 0, inputFilterArr3, 0, length2);
                inputFilterArr3[length2] = ta;
                return inputFilterArr3;
            }
            if (inputFilterArr[i4] == ta) {
                return inputFilterArr;
            }
            i4++;
        }
    }
}
