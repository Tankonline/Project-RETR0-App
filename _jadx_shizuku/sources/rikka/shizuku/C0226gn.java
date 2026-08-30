package rikka.shizuku;

import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;
import androidx.recyclerview.widget.RecyclerView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import moe.shizuku.manager.receiver.BootCompleteReceiver;
import moe.shizuku.manager.settings.IntegerSimpleMenuPreference;
import moe.shizuku.privileged.api.R;
import rikka.widget.borderview.BorderRecyclerView;

/* JADX INFO: renamed from: rikka.shizuku.gn, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0226gn extends AbstractC0284ik {
    public ListPreference e0;
    public IntegerSimpleMenuPreference f0;
    public TwoStatePreference g0;
    public TwoStatePreference h0;
    public Preference i0;
    public Preference j0;
    public TwoStatePreference k0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r14v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r14v8, types: [android.text.Spanned] */
    /* JADX WARN: Type inference failed for: r7v19, types: [java.lang.StringBuilder] */
    @Override // rikka.shizuku.AbstractC0284ik
    public final void N() {
        String string;
        int iIndexOf;
        String displayName;
        ?? A;
        String displayScript;
        final int i = 0;
        final int i2 = 1;
        final Context contextH = H();
        C0438nk c0438nk = this.X;
        c0438nk.g = 1;
        c0438nk.f = "settings";
        c0438nk.c = null;
        c0438nk.getClass();
        c0438nk.c = null;
        Context contextH2 = H();
        c0438nk.e = true;
        C0407mk c0407mk = new C0407mk(contextH2, c0438nk);
        XmlResourceParser xml = contextH2.getResources().getXml(R.xml._0_resource_name_obfuscated_res_0x7f140004);
        try {
            PreferenceGroup preferenceGroupC = c0407mk.c(xml);
            xml.close();
            PreferenceScreen preferenceScreen = (PreferenceScreen) preferenceGroupC;
            preferenceScreen.l(c0438nk);
            SharedPreferences.Editor editor = c0438nk.d;
            if (editor != null) {
                editor.apply();
            }
            c0438nk.e = false;
            C0438nk c0438nk2 = this.X;
            PreferenceScreen preferenceScreen2 = c0438nk2.h;
            if (preferenceScreen != preferenceScreen2) {
                if (preferenceScreen2 != null) {
                    preferenceScreen2.p();
                }
                c0438nk2.h = preferenceScreen;
                this.Z = true;
                if (this.a0) {
                    F1 f1 = this.c0;
                    if (!f1.hasMessages(1)) {
                        f1.obtainMessage(1).sendToTarget();
                    }
                }
            }
            this.e0 = (ListPreference) M("language");
            this.f0 = (IntegerSimpleMenuPreference) M("night_mode");
            this.g0 = (TwoStatePreference) M("black_night_theme");
            this.h0 = (TwoStatePreference) M("start_on_boot");
            this.i0 = M("translation");
            this.j0 = M("translation_contributors");
            this.k0 = (TwoStatePreference) M("use_system_color");
            final ComponentName componentName = new ComponentName(contextH.getPackageName(), BootCompleteReceiver.class.getName());
            TwoStatePreference twoStatePreference = this.h0;
            if (twoStatePreference == null) {
                twoStatePreference = null;
            }
            int componentEnabledSetting = contextH.getPackageManager().getComponentEnabledSetting(componentName);
            twoStatePreference.B(componentEnabledSetting == 0 || componentEnabledSetting == 1);
            TwoStatePreference twoStatePreference2 = this.h0;
            if (twoStatePreference2 == null) {
                twoStatePreference2 = null;
            }
            twoStatePreference2.e = new InterfaceC0069bk() { // from class: rikka.shizuku.en
                @Override // rikka.shizuku.InterfaceC0069bk
                public final boolean a(Serializable serializable) {
                    AbstractActivityC0051b2 abstractActivityC0051b2G;
                    switch (i) {
                        case 0:
                            if (!(serializable instanceof Boolean)) {
                                return false;
                            }
                            Context context = contextH;
                            PackageManager packageManager = context.getPackageManager();
                            boolean zBooleanValue = ((Boolean) serializable).booleanValue();
                            ComponentName componentName2 = (ComponentName) componentName;
                            int componentEnabledSetting2 = packageManager.getComponentEnabledSetting(componentName2);
                            int i3 = zBooleanValue ? 1 : 2;
                            if (i3 != componentEnabledSetting2) {
                                packageManager.setComponentEnabledSetting(componentName2, i3, 1);
                            }
                            int componentEnabledSetting3 = context.getPackageManager().getComponentEnabledSetting(componentName2);
                            return Boolean.valueOf(componentEnabledSetting3 == 0 || componentEnabledSetting3 == 1).equals(serializable);
                        default:
                            if ((contextH.getResources().getConfiguration().uiMode & 32) <= 0 || (abstractActivityC0051b2G = ((C0226gn) componentName).g()) == null) {
                                return true;
                            }
                            abstractActivityC0051b2G.recreate();
                            return true;
                    }
                }
            };
            ListPreference listPreference = this.e0;
            (listPreference == null ? null : listPreference).e = new InterfaceC0069bk(this) { // from class: rikka.shizuku.fn
                public final /* synthetic */ C0226gn b;

                {
                    this.b = this;
                }

                @Override // rikka.shizuku.InterfaceC0069bk
                public final boolean a(Serializable serializable) {
                    AbstractActivityC0051b2 abstractActivityC0051b2G;
                    switch (i) {
                        case 0:
                            if (serializable instanceof String) {
                                C0281ih.b = "SYSTEM".equals(serializable) ? C0281ih.c : Locale.forLanguageTag((String) serializable);
                                AbstractActivityC0051b2 abstractActivityC0051b2G2 = this.b.g();
                                if (abstractActivityC0051b2G2 != null) {
                                    abstractActivityC0051b2G2.recreate();
                                }
                            }
                            break;
                        case 1:
                            if (serializable instanceof Integer) {
                                int iJ = AbstractC0750xn.j();
                                if (serializable == null || iJ != ((Number) serializable).intValue()) {
                                    AbstractC0667v2.m(((Number) serializable).intValue());
                                    AbstractActivityC0051b2 abstractActivityC0051b2G3 = this.b.g();
                                    if (abstractActivityC0051b2G3 != null) {
                                        abstractActivityC0051b2G3.recreate();
                                    }
                                    break;
                                }
                            }
                            break;
                        default:
                            if ((serializable instanceof Boolean) && !Boolean.valueOf(Hn.i()).equals(serializable) && (abstractActivityC0051b2G = this.b.g()) != null) {
                                abstractActivityC0051b2G.recreate();
                                break;
                            }
                            break;
                    }
                    return true;
                }
            };
            String[] strArr = AbstractC0046as.f;
            String[] strArr2 = AbstractC0046as.g;
            if (listPreference == null) {
                listPreference = null;
            }
            listPreference.C(strArr2);
            ListPreference listPreference2 = this.e0;
            (listPreference2 == null ? null : listPreference2).U = strArr;
            if (listPreference2 == null) {
                listPreference2 = null;
            }
            String str = listPreference2.V;
            int iI0 = AbstractC0484p4.i0(strArr, str);
            Locale localeI = AbstractC0750xn.i();
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < 43; i3++) {
                String str2 = strArr2[i3];
                if (i3 == 0) {
                    arrayList.add(m().getString(R.string._0_resource_name_obfuscated_res_0x7f110069));
                } else {
                    Locale localeForLanguageTag = Locale.forLanguageTag(str2.toString());
                    String displayScript2 = !TextUtils.isEmpty(localeForLanguageTag.getScript()) ? localeForLanguageTag.getDisplayScript(localeForLanguageTag) : localeForLanguageTag.getDisplayName(localeForLanguageTag);
                    if (TextUtils.isEmpty(localeForLanguageTag.getScript())) {
                        displayName = localeForLanguageTag.getDisplayName(localeI);
                    } else {
                        displayScript = localeForLanguageTag.getDisplayScript(localeI);
                    }
                    if (i3 != iI0) {
                        A = displayName;
                        A = displayScript;
                        A = AbstractC0688vn.A(displayScript2 + "<br><small>" + A + "<small>", 0);
                    }
                    A = displayName;
                    A = displayScript;
                    arrayList.add(A);
                }
            }
            ListPreference listPreference3 = this.e0;
            if (listPreference3 == null) {
                listPreference3 = null;
            }
            listPreference3.C((CharSequence[]) arrayList.toArray(new CharSequence[0]));
            ListPreference listPreference4 = this.e0;
            if (listPreference4 == null) {
                listPreference4 = null;
            }
            if (TextUtils.isEmpty(str) || "SYSTEM".equals(str)) {
                string = m().getString(R.string._0_resource_name_obfuscated_res_0x7f110069);
            } else if (iI0 != -1) {
                CharSequence charSequence = (CharSequence) arrayList.get(iI0);
                if (!(charSequence instanceof String)) {
                    char[] cArr = {'\n'};
                    if (!(charSequence instanceof String)) {
                        int iR = AbstractC0135dp.r(charSequence);
                        if (iR < 0) {
                            iIndexOf = -1;
                            break;
                        }
                        int i4 = 0;
                        while (true) {
                            if (cArr[0] == charSequence.charAt(i4)) {
                                iIndexOf = i4;
                                break;
                            }
                            if (i4 == iR) {
                                iIndexOf = -1;
                                break;
                            }
                            i4++;
                        }
                    } else {
                        iIndexOf = ((String) charSequence).indexOf(cArr[0], 0);
                    }
                } else {
                    iIndexOf = ((String) charSequence).indexOf(10, 0);
                }
                string = iIndexOf == -1 ? charSequence.toString() : charSequence.subSequence(0, iIndexOf).toString();
            } else {
                string = "";
            }
            listPreference4.y(string);
            IntegerSimpleMenuPreference integerSimpleMenuPreference = this.f0;
            if (integerSimpleMenuPreference == null) {
                integerSimpleMenuPreference = null;
            }
            integerSimpleMenuPreference.B(AbstractC0750xn.j());
            IntegerSimpleMenuPreference integerSimpleMenuPreference2 = this.f0;
            if (integerSimpleMenuPreference2 == null) {
                integerSimpleMenuPreference2 = null;
            }
            integerSimpleMenuPreference2.e = new InterfaceC0069bk(this) { // from class: rikka.shizuku.fn
                public final /* synthetic */ C0226gn b;

                {
                    this.b = this;
                }

                @Override // rikka.shizuku.InterfaceC0069bk
                public final boolean a(Serializable serializable) {
                    AbstractActivityC0051b2 abstractActivityC0051b2G;
                    switch (i2) {
                        case 0:
                            if (serializable instanceof String) {
                                C0281ih.b = "SYSTEM".equals(serializable) ? C0281ih.c : Locale.forLanguageTag((String) serializable);
                                AbstractActivityC0051b2 abstractActivityC0051b2G2 = this.b.g();
                                if (abstractActivityC0051b2G2 != null) {
                                    abstractActivityC0051b2G2.recreate();
                                }
                            }
                            break;
                        case 1:
                            if (serializable instanceof Integer) {
                                int iJ = AbstractC0750xn.j();
                                if (serializable == null || iJ != ((Number) serializable).intValue()) {
                                    AbstractC0667v2.m(((Number) serializable).intValue());
                                    AbstractActivityC0051b2 abstractActivityC0051b2G3 = this.b.g();
                                    if (abstractActivityC0051b2G3 != null) {
                                        abstractActivityC0051b2G3.recreate();
                                    }
                                    break;
                                }
                            }
                            break;
                        default:
                            if ((serializable instanceof Boolean) && !Boolean.valueOf(Hn.i()).equals(serializable) && (abstractActivityC0051b2G = this.b.g()) != null) {
                                abstractActivityC0051b2G.recreate();
                                break;
                            }
                            break;
                    }
                    return true;
                }
            };
            if (AbstractC0750xn.j() != 1) {
                TwoStatePreference twoStatePreference3 = this.g0;
                if (twoStatePreference3 == null) {
                    twoStatePreference3 = null;
                }
                twoStatePreference3.B(AbstractC0750xn.a.getBoolean("black_night_theme", ((UiModeManager) contextH.getSystemService(UiModeManager.class)).getCurrentModeType() == 6));
                TwoStatePreference twoStatePreference4 = this.g0;
                if (twoStatePreference4 == null) {
                    twoStatePreference4 = null;
                }
                twoStatePreference4.e = new InterfaceC0069bk() { // from class: rikka.shizuku.en
                    @Override // rikka.shizuku.InterfaceC0069bk
                    public final boolean a(Serializable serializable) {
                        AbstractActivityC0051b2 abstractActivityC0051b2G;
                        switch (i2) {
                            case 0:
                                if (!(serializable instanceof Boolean)) {
                                    return false;
                                }
                                Context context = contextH;
                                PackageManager packageManager = context.getPackageManager();
                                boolean zBooleanValue = ((Boolean) serializable).booleanValue();
                                ComponentName componentName2 = (ComponentName) this;
                                int componentEnabledSetting2 = packageManager.getComponentEnabledSetting(componentName2);
                                int i5 = zBooleanValue ? 1 : 2;
                                if (i5 != componentEnabledSetting2) {
                                    packageManager.setComponentEnabledSetting(componentName2, i5, 1);
                                }
                                int componentEnabledSetting3 = context.getPackageManager().getComponentEnabledSetting(componentName2);
                                return Boolean.valueOf(componentEnabledSetting3 == 0 || componentEnabledSetting3 == 1).equals(serializable);
                            default:
                                if ((contextH.getResources().getConfiguration().uiMode & 32) <= 0 || (abstractActivityC0051b2G = ((C0226gn) this).g()) == null) {
                                    return true;
                                }
                                abstractActivityC0051b2G.recreate();
                                return true;
                        }
                    }
                };
            } else {
                TwoStatePreference twoStatePreference5 = this.g0;
                if (twoStatePreference5 == null) {
                    twoStatePreference5 = null;
                }
                if (twoStatePreference5.w) {
                    twoStatePreference5.w = false;
                    C0376lk c0376lk = twoStatePreference5.G;
                    if (c0376lk != null) {
                        Handler handler = c0376lk.h;
                        RunnableC0607t4 runnableC0607t4 = c0376lk.i;
                        handler.removeCallbacks(runnableC0607t4);
                        handler.post(runnableC0607t4);
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= 31) {
                TwoStatePreference twoStatePreference6 = this.k0;
                if (twoStatePreference6 == null) {
                    twoStatePreference6 = null;
                }
                final int i5 = 2;
                twoStatePreference6.e = new InterfaceC0069bk(this) { // from class: rikka.shizuku.fn
                    public final /* synthetic */ C0226gn b;

                    {
                        this.b = this;
                    }

                    @Override // rikka.shizuku.InterfaceC0069bk
                    public final boolean a(Serializable serializable) {
                        AbstractActivityC0051b2 abstractActivityC0051b2G;
                        switch (i5) {
                            case 0:
                                if (serializable instanceof String) {
                                    C0281ih.b = "SYSTEM".equals(serializable) ? C0281ih.c : Locale.forLanguageTag((String) serializable);
                                    AbstractActivityC0051b2 abstractActivityC0051b2G2 = this.b.g();
                                    if (abstractActivityC0051b2G2 != null) {
                                        abstractActivityC0051b2G2.recreate();
                                    }
                                }
                                break;
                            case 1:
                                if (serializable instanceof Integer) {
                                    int iJ = AbstractC0750xn.j();
                                    if (serializable == null || iJ != ((Number) serializable).intValue()) {
                                        AbstractC0667v2.m(((Number) serializable).intValue());
                                        AbstractActivityC0051b2 abstractActivityC0051b2G3 = this.b.g();
                                        if (abstractActivityC0051b2G3 != null) {
                                            abstractActivityC0051b2G3.recreate();
                                        }
                                        break;
                                    }
                                }
                                break;
                            default:
                                if ((serializable instanceof Boolean) && !Boolean.valueOf(Hn.i()).equals(serializable) && (abstractActivityC0051b2G = this.b.g()) != null) {
                                    abstractActivityC0051b2G.recreate();
                                    break;
                                }
                                break;
                        }
                        return true;
                    }
                };
            } else {
                TwoStatePreference twoStatePreference7 = this.k0;
                if (twoStatePreference7 == null) {
                    twoStatePreference7 = null;
                }
                if (twoStatePreference7.w) {
                    twoStatePreference7.w = false;
                    C0376lk c0376lk2 = twoStatePreference7.G;
                    if (c0376lk2 != null) {
                        Handler handler2 = c0376lk2.h;
                        RunnableC0607t4 runnableC0607t5 = c0376lk2.i;
                        handler2.removeCallbacks(runnableC0607t5);
                        handler2.post(runnableC0607t5);
                    }
                }
            }
            Preference preference = this.i0;
            if (preference == null) {
                preference = null;
            }
            preference.y(contextH.getString(R.string._0_resource_name_obfuscated_res_0x7f11011c, contextH.getString(R.string._0_resource_name_obfuscated_res_0x7f110032)));
            Preference preference2 = this.i0;
            if (preference2 == null) {
                preference2 = null;
            }
            preference2.f = new C0610t7(6, contextH);
            String string2 = AbstractC0688vn.A(contextH.getString(R.string._0_resource_name_obfuscated_res_0x7f11012f), 0).toString();
            for (int i6 = 0; i6 < string2.length(); i6++) {
                char cCharAt = string2.charAt(i6);
                if (!Character.isWhitespace(cCharAt) && !Character.isSpaceChar(cCharAt)) {
                    Preference preference3 = this.j0;
                    (preference3 == null ? null : preference3).y(string2);
                    return;
                }
            }
            Preference preference4 = this.j0;
            Preference preference5 = preference4 == null ? null : preference4;
            if (preference5.w) {
                preference5.w = false;
                C0376lk c0376lk3 = preference5.G;
                if (c0376lk3 != null) {
                    Handler handler3 = c0376lk3.h;
                    RunnableC0607t4 runnableC0607t6 = c0376lk3.i;
                    handler3.removeCallbacks(runnableC0607t6);
                    handler3.post(runnableC0607t6);
                }
            }
        } catch (Throwable th) {
            xml.close();
            throw th;
        }
    }

    @Override // rikka.shizuku.AbstractC0284ik
    public final RecyclerView O(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        BorderRecyclerView borderRecyclerView = (BorderRecyclerView) super.O(layoutInflater, viewGroup, bundle);
        X8.t(borderRecyclerView);
        X8.e(borderRecyclerView, 0.0f, (5 & 2) != 0 ? 0.0f : 8.0f, 0.0f, 8.0f);
        ViewGroup.LayoutParams layoutParams = borderRecyclerView.getLayoutParams();
        if (layoutParams instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
            int dimension = (int) borderRecyclerView.getContext().getResources().getDimension(R.dimen._0_resource_name_obfuscated_res_0x7f07033f);
            layoutParams2.rightMargin = dimension;
            layoutParams2.leftMargin = dimension;
        }
        return borderRecyclerView;
    }
}
