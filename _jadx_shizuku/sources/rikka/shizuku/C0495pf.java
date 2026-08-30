package rikka.shizuku;

import android.content.ClipDescription;
import android.net.Uri;
import android.view.inputmethod.InputContentInfo;

/* JADX INFO: renamed from: rikka.shizuku.pf, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0495pf implements InterfaceC0526qf {
    public final InputContentInfo a;

    public C0495pf(Object obj) {
        this.a = (InputContentInfo) obj;
    }

    @Override // rikka.shizuku.InterfaceC0526qf
    public final void a() {
        this.a.requestPermission();
    }

    @Override // rikka.shizuku.InterfaceC0526qf
    public final Uri b() {
        return this.a.getLinkUri();
    }

    @Override // rikka.shizuku.InterfaceC0526qf
    public final ClipDescription c() {
        return this.a.getDescription();
    }

    @Override // rikka.shizuku.InterfaceC0526qf
    public final Object d() {
        return this.a;
    }

    @Override // rikka.shizuku.InterfaceC0526qf
    public final Uri e() {
        return this.a.getContentUri();
    }

    public C0495pf(Uri uri, ClipDescription clipDescription, Uri uri2) {
        this.a = new InputContentInfo(uri, clipDescription, uri2);
    }
}
