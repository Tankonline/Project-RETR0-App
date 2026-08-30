package rikka.shizuku;

import android.content.ClipData;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContentInfo;
import android.view.View;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: renamed from: rikka.shizuku.z7, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0796z7 implements InterfaceC0765y7, A7 {
    public final /* synthetic */ int a;
    public Object b;
    public int c;
    public int d;
    public Object e;
    public Cloneable f;

    public /* synthetic */ C0796z7() {
        this.a = 0;
    }

    public void a(au auVar, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Jt jt = (Jt) it.next();
            if ((jt.a.c() & 8) != 0) {
                ((View) this.e).setTranslationY(V1.c(this.d, 0, jt.a.b()));
                return;
            }
        }
    }

    @Override // rikka.shizuku.A7
    public ClipData c() {
        return (ClipData) this.b;
    }

    @Override // rikka.shizuku.InterfaceC0765y7
    public B7 d() {
        return new B7(new C0796z7(this));
    }

    @Override // rikka.shizuku.A7
    public int e() {
        return this.d;
    }

    @Override // rikka.shizuku.A7
    public ContentInfo g() {
        return null;
    }

    @Override // rikka.shizuku.InterfaceC0765y7
    public void h(Bundle bundle) {
        this.f = bundle;
    }

    @Override // rikka.shizuku.InterfaceC0765y7
    public void i(Uri uri) {
        this.e = uri;
    }

    @Override // rikka.shizuku.A7
    public int j() {
        return this.c;
    }

    @Override // rikka.shizuku.InterfaceC0765y7
    public void o(int i) {
        this.d = i;
    }

    public String toString() {
        String strValueOf;
        String str;
        switch (this.a) {
            case 1:
                StringBuilder sb = new StringBuilder("ContentInfoCompat{clip=");
                sb.append(((ClipData) this.b).getDescription());
                sb.append(", source=");
                int i = this.c;
                if (i == 0) {
                    strValueOf = "SOURCE_APP";
                } else if (i == 1) {
                    strValueOf = "SOURCE_CLIPBOARD";
                } else if (i == 2) {
                    strValueOf = "SOURCE_INPUT_METHOD";
                } else if (i == 3) {
                    strValueOf = "SOURCE_DRAG_AND_DROP";
                } else if (i != 4) {
                    strValueOf = i != 5 ? String.valueOf(i) : "SOURCE_PROCESS_TEXT";
                } else {
                    strValueOf = "SOURCE_AUTOFILL";
                }
                sb.append(strValueOf);
                sb.append(", flags=");
                int i2 = this.d;
                sb.append((i2 & 1) != 0 ? "FLAG_CONVERT_TO_PLAIN_TEXT" : String.valueOf(i2));
                Uri uri = (Uri) this.e;
                if (uri == null) {
                    str = "";
                } else {
                    str = ", hasLinkUri(" + uri.toString().length() + ")";
                }
                sb.append(str);
                sb.append(((Bundle) this.f) != null ? ", hasExtras" : "");
                sb.append("}");
                return sb.toString();
            default:
                return super.toString();
        }
    }

    public C0796z7(C0796z7 c0796z7) {
        this.a = 1;
        ClipData clipData = (ClipData) c0796z7.b;
        clipData.getClass();
        this.b = clipData;
        int i = c0796z7.c;
        if (i < 0) {
            Locale locale = Locale.US;
            throw new IllegalArgumentException("source is out of range of [0, 5] (too low)");
        }
        if (i > 5) {
            Locale locale2 = Locale.US;
            throw new IllegalArgumentException("source is out of range of [0, 5] (too high)");
        }
        this.c = i;
        int i2 = c0796z7.d;
        if ((i2 & 1) == i2) {
            this.d = i2;
            this.e = (Uri) c0796z7.e;
            this.f = (Bundle) c0796z7.f;
        } else {
            throw new IllegalArgumentException("Requested flags 0x" + Integer.toHexString(i2) + ", but only 0x" + Integer.toHexString(1) + " are allowed");
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [int[], java.lang.Cloneable] */
    public C0796z7(View view) {
        this.a = 2;
        this.f = new int[2];
        this.e = view;
    }
}
