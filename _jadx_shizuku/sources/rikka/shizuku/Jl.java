package rikka.shizuku;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class Jl extends Fragment {
    public static final /* synthetic */ int b = 0;
    public Ys a;

    public final void a(EnumC0557rg enumC0557rg) {
        if (Build.VERSION.SDK_INT < 29) {
            AbstractC0744xh.n(getActivity(), enumC0557rg);
        }
    }

    @Override // android.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        a(EnumC0557rg.ON_CREATE);
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        a(EnumC0557rg.ON_DESTROY);
        this.a = null;
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        a(EnumC0557rg.ON_PAUSE);
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        Ys ys = this.a;
        if (ys != null) {
            ((C0654uk) ys.b).a();
        }
        a(EnumC0557rg.ON_RESUME);
    }

    @Override // android.app.Fragment
    public final void onStart() {
        super.onStart();
        Ys ys = this.a;
        if (ys != null) {
            C0654uk c0654uk = (C0654uk) ys.b;
            int i = c0654uk.a + 1;
            c0654uk.a = i;
            if (i == 1 && c0654uk.d) {
                c0654uk.f.d(EnumC0557rg.ON_START);
                c0654uk.d = false;
            }
        }
        a(EnumC0557rg.ON_START);
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        a(EnumC0557rg.ON_STOP);
    }
}
