package rikka.shizuku;

import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.HashMap;
import moe.shizuku.manager.shell.ShellTutorialActivity;
import moe.shizuku.manager.starter.StarterActivity;
import moe.shizuku.privileged.api.R;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Y0 implements View.OnClickListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ Y0(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) throws Exception {
        int i = -1;
        Object obj = this.b;
        switch (this.a) {
            case 0:
                int i2 = SystemProperties.getInt("service.adb.tcp.port", -1);
                if (i2 == -1) {
                    i2 = SystemProperties.getInt("persist.adb.tcp.port", -1);
                }
                ((C0050b1) obj).Q(i2);
                return;
            case 1:
                C0573s1 c0573s1 = (C0573s1) obj;
                Context context = view.getContext();
                try {
                    C0235h1 c0235h1 = c0573s1.m0;
                    if (c0235h1 == null) {
                        c0235h1 = null;
                    }
                    i = Integer.parseInt(((TextInputLayout) c0235h1.c).d.getText().toString());
                    break;
                } catch (Exception unused) {
                }
                if (i > 65535 || i < 1) {
                    C0235h1 c0235h2 = c0573s1.m0;
                    if (c0235h2 == null) {
                        c0235h2 = null;
                    }
                    ((TextInputLayout) c0235h2.c).setVisibility(0);
                    C0235h1 c0235h3 = c0573s1.m0;
                    ((TextInputLayout) (c0235h3 != null ? c0235h3 : null).c).l(context.getString(R.string._0_resource_name_obfuscated_res_0x7f110052));
                    return;
                }
                C0235h1 c0235h4 = c0573s1.m0;
                if (c0235h4 == null) {
                    c0235h4 = null;
                }
                String string = ((TextInputLayout) c0235h4.b).d.getText().toString();
                Ts ts = (Ts) c0573s1.n0.getValue();
                ts.getClass();
                Ld ld = Ld.a;
                C0550r9 c0550r9 = AbstractC0090ca.a;
                ExecutorC0243h9 executorC0243h9 = ExecutorC0243h9.b;
                AbstractC0599sr.z(ld, new Ps(ts, i, string, null), 2);
                return;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                C0393m6 c0393m6 = (C0393m6) obj;
                EditText editText = c0393m6.i;
                if (editText == null) {
                    return;
                }
                Editable text = editText.getText();
                if (text != null) {
                    text.clear();
                }
                c0393m6.q();
                return;
            case 3:
                ((C0644ua) obj).u();
                return;
            case 4:
                ((Uh) obj).Q();
                throw null;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                Oj oj = (Oj) obj;
                EditText editText2 = oj.f;
                if (editText2 == null) {
                    return;
                }
                int selectionEnd = editText2.getSelectionEnd();
                EditText editText3 = oj.f;
                if (editText3 == null || !(editText3.getTransformationMethod() instanceof PasswordTransformationMethod)) {
                    oj.f.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    oj.f.setTransformationMethod(null);
                }
                if (selectionEnd >= 0) {
                    oj.f.setSelection(selectionEnd);
                }
                oj.q();
                return;
            case 6:
                O0 o0 = ((ShellTutorialActivity) obj).D;
                N6 n6 = o0.s;
                HashMap map = n6.b;
                String str = o0.q;
                Integer num = (Integer) map.get(str);
                M0 m0 = o0.r;
                if (num == null) {
                    throw new IllegalStateException("Attempting to launch an unregistered ActivityResultLauncher with contract " + m0 + " and input null. You must ensure the ActivityResultLauncher is registered before calling launch().");
                }
                n6.d.add(str);
                try {
                    n6.b(num.intValue(), m0);
                    return;
                } catch (Exception e) {
                    n6.d.remove(str);
                    throw e;
                }
            default:
                ((Ym) obj).getClass();
                Context context2 = view.getContext();
                Intent intent = new Intent(context2, (Class<?>) StarterActivity.class);
                intent.putExtra("moe.shizuku.manager.extra.IS_ROOT", true);
                context2.startActivity(intent);
                return;
        }
    }
}
