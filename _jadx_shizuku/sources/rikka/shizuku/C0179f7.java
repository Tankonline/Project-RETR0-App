package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.f7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0179f7 implements InterfaceC0344kj {
    public final int a;
    public final int b;
    public final Object c;

    public C0179f7(Context context, XmlResourceParser xmlResourceParser) {
        this.c = new ArrayList();
        this.b = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), Fk.g);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == 0) {
                this.a = typedArrayObtainStyledAttributes.getResourceId(index, this.a);
            } else if (index == 1) {
                int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.b);
                this.b = resourceId;
                String resourceTypeName = context.getResources().getResourceTypeName(resourceId);
                context.getResources().getResourceName(resourceId);
                if ("layout".equals(resourceTypeName)) {
                    new C0394m7().b((ConstraintLayout) LayoutInflater.from(context).inflate(resourceId, (ViewGroup) null));
                }
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // rikka.shizuku.InterfaceC0344kj
    public au f(View view, au auVar) {
        int i = auVar.a.f(519).b;
        View view2 = (View) this.c;
        int i2 = this.a;
        if (i2 >= 0) {
            view2.getLayoutParams().height = i2 + i;
            view2.setLayoutParams(view2.getLayoutParams());
        }
        view2.setPadding(view2.getPaddingLeft(), this.b + i, view2.getPaddingRight(), view2.getPaddingBottom());
        return auVar;
    }

    public C0179f7(View view, int i, int i2) {
        this.a = i;
        this.c = view;
        this.b = i2;
    }
}
