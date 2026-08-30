package rikka.shizuku;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.ImageView;
import java.util.concurrent.CancellationException;
import moe.shizuku.privileged.api.R;

/* JADX INFO: loaded from: classes.dex */
public final class G3 extends AbstractC0443np implements InterfaceC0585sd {
    public int e;
    public final /* synthetic */ ImageView f;
    public final /* synthetic */ ApplicationInfo g;
    public final /* synthetic */ int h;
    public final /* synthetic */ Context i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public G3(ImageView imageView, ApplicationInfo applicationInfo, int i, Context context, G7 g7) {
        super(g7);
        this.f = imageView;
        this.g = applicationInfo;
        this.h = i;
        this.i = context;
    }

    @Override // rikka.shizuku.InterfaceC0585sd
    public final Object f(Object obj, Object obj2) {
        return ((G3) i((InterfaceC0026a8) obj, (G7) obj2)).j(Pe.b);
    }

    @Override // rikka.shizuku.Q4
    public final G7 i(Object obj, G7 g7) {
        return new G3(this.f, this.g, this.h, this.i, g7);
    }

    @Override // rikka.shizuku.Q4
    public final Object j(Object obj) throws Throwable {
        Bitmap bitmap;
        EnumC0057b8 enumC0057b8 = EnumC0057b8.a;
        int i = this.e;
        Pe pe = Pe.b;
        ImageView imageView = this.f;
        try {
            if (i == 0) {
                AbstractC0046as.V(obj);
                int measuredWidth = imageView.getMeasuredWidth();
                Context context = this.i;
                if (measuredWidth <= 0) {
                    measuredWidth = context.getResources().getDimensionPixelSize(R.dimen._0_resource_name_obfuscated_res_0x7f07006d);
                }
                int i2 = measuredWidth;
                H3 h3 = H3.a;
                Bitmap bitmapA = H3.a(this.g.packageName, this.h, i2);
                if (bitmapA != null) {
                    imageView.setImageBitmap(bitmapA);
                    return pe;
                }
                Ab ab = H3.c;
                F3 f3 = new F3(context, this.g, this.h, i2, null);
                this.e = 1;
                obj = AbstractC0599sr.P(ab, f3, this);
                if (obj == enumC0057b8) {
                    return enumC0057b8;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                AbstractC0046as.V(obj);
            }
            bitmap = (Bitmap) obj;
        } catch (CancellationException unused) {
        } catch (Throwable unused2) {
            bitmap = null;
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else if (Build.VERSION.SDK_INT >= 26) {
            imageView.setImageResource(R.drawable._0_resource_name_obfuscated_res_0x7f0800a7);
        } else {
            imageView.setImageDrawable(null);
        }
        return pe;
    }
}
