package rikka.shizuku;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import java.net.ConnectException;
import javax.net.ssl.SSLProtocolException;
import moe.shizuku.manager.starter.StarterActivity;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.o1, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class C0450o1 implements InterfaceC0462od {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;

    public /* synthetic */ C0450o1(Object obj, int i, Object obj2) {
        this.a = i;
        this.b = obj;
        this.c = obj2;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x005b  */
    /* JADX WARN: Code duplicated, block: B:26:0x0063  */
    /* JADX WARN: Code duplicated, block: B:29:0x0072  */
    /* JADX WARN: Code duplicated, block: B:30:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:32:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:34:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:35:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:37:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:38:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:41:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:43:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:44:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:46:0x00ca  */
    @Override // rikka.shizuku.InterfaceC0462od
    public final Object g(Object obj) {
        CharSequence charSequenceSubSequence;
        boolean zS;
        StarterActivity starterActivity;
        Throwable th;
        int i = R.string._0_resource_name_obfuscated_res_0x7f110041;
        Pe pe = Pe.b;
        Object obj2 = this.c;
        Object obj3 = this.b;
        int i2 = 1;
        switch (this.a) {
            case 0:
                Throwable th2 = (Throwable) obj;
                C0573s1 c0573s1 = (C0573s1) obj3;
                if (th2 != null) {
                    Context context = (Context) obj2;
                    if (th2 instanceof ConnectException) {
                        C0235h1 c0235h1 = c0573s1.m0;
                        ((TextInputLayout) (c0235h1 != null ? c0235h1 : null).c).l(context.getString(R.string._0_resource_name_obfuscated_res_0x7f110041));
                    } else if (th2 instanceof C0112d1) {
                        C0235h1 c0235h2 = c0573s1.m0;
                        ((TextInputLayout) (c0235h2 != null ? c0235h2 : null).b).l(context.getString(R.string._0_resource_name_obfuscated_res_0x7f110102));
                    } else if (th2 instanceof C0266i1) {
                        Toast toastMakeText = Toast.makeText(context, context.getString(R.string._0_resource_name_obfuscated_res_0x7f11001e), 1);
                        toastMakeText.setGravity(17, 0, 0);
                        toastMakeText.show();
                    }
                } else {
                    c0573s1.M(true, false);
                }
                break;
            case 1:
                Integer num = (Integer) obj;
                J1 j1 = (J1) obj2;
                C0573s1 c0573s2 = (C0573s1) obj3;
                if (num == null || num.intValue() != -1) {
                    j1.setTitle(R.string._0_resource_name_obfuscated_res_0x7f110056);
                    C0235h1 c0235h3 = c0573s2.m0;
                    if (c0235h3 == null) {
                        c0235h3 = null;
                    }
                    ((TextView) c0235h3.d).setVisibility(8);
                    C0235h1 c0235h4 = c0573s2.m0;
                    if (c0235h4 == null) {
                        c0235h4 = null;
                    }
                    ((TextInputLayout) c0235h4.b).setVisibility(0);
                    C0235h1 c0235h5 = c0573s2.m0;
                    ((TextInputLayout) (c0235h5 != null ? c0235h5 : null).c).d.setText(num.toString());
                    j1.d(-1).setVisibility(0);
                    j1.d(-3).setVisibility(8);
                } else {
                    j1.setTitle(R.string._0_resource_name_obfuscated_res_0x7f110053);
                    C0235h1 c0235h6 = c0573s2.m0;
                    if (c0235h6 == null) {
                        c0235h6 = null;
                    }
                    ((TextView) c0235h6.d).setVisibility(0);
                    C0235h1 c0235h7 = c0573s2.m0;
                    if (c0235h7 == null) {
                        c0235h7 = null;
                    }
                    ((TextInputLayout) c0235h7.b).setVisibility(8);
                    C0235h1 c0235h8 = c0573s2.m0;
                    ((TextInputLayout) (c0235h8 != null ? c0235h8 : null).c).d.setText(num.toString());
                    j1.d(-1).setVisibility(8);
                    j1.d(-3).setVisibility(0);
                }
                break;
            default:
                Rl rl = (Rl) obj;
                int i3 = StarterActivity.E;
                CharSequence charSequence = (CharSequence) rl.b;
                int length = charSequence.length() - 1;
                int i4 = 0;
                boolean z = false;
                while (i4 <= length) {
                    char cCharAt = charSequence.charAt(!z ? i4 : length);
                    boolean z2 = Character.isWhitespace(cCharAt) || Character.isSpaceChar(cCharAt);
                    if (z) {
                        if (!z2) {
                            charSequenceSubSequence = charSequence.subSequence(i4, length + 1);
                            if (charSequenceSubSequence instanceof String) {
                                zS = ((String) charSequenceSubSequence).endsWith("info: shizuku_starter exit with 0");
                            } else {
                                zS = AbstractC0135dp.s(charSequenceSubSequence, charSequenceSubSequence.length() - 33, "info: shizuku_starter exit with 0", 0, 33);
                            }
                            starterActivity = (StarterActivity) obj3;
                            if (zS) {
                                Xn xn = starterActivity.D;
                                Us us = (Us) xn.getValue();
                                StringBuilder sb = us.b;
                                sb.append("");
                                sb.append('\n');
                                us.d(null);
                                Us us2 = (Us) xn.getValue();
                                StringBuilder sb2 = us2.b;
                                sb2.append("Waiting for service...");
                                sb2.append('\n');
                                us2.d(null);
                                Nn.b(new Pl(i2, starterActivity), false, null);
                            } else if (rl.a == 2) {
                                th = rl.c;
                                if (th instanceof C0266i1) {
                                    i = R.string._0_resource_name_obfuscated_res_0x7f11001e;
                                } else if (th instanceof C0130dj) {
                                    i = R.string._0_resource_name_obfuscated_res_0x7f110121;
                                } else if (!(th instanceof ConnectException)) {
                                    if (th instanceof SSLProtocolException) {
                                        i = R.string._0_resource_name_obfuscated_res_0x7f11001f;
                                    } else {
                                        i = 0;
                                    }
                                }
                                if (i != 0) {
                                    Ch ch = new Ch(starterActivity);
                                    E1 e1 = (E1) ch.b;
                                    e1.f = e1.a.getText(i);
                                    ch.g(android.R.string.ok, null);
                                    ch.f();
                                }
                            }
                            ((C0741xe) obj2).a.setText(charSequenceSubSequence);
                        } else {
                            length--;
                        }
                        break;
                    } else if (z2) {
                        i4++;
                    } else {
                        z = true;
                    }
                }
                charSequenceSubSequence = charSequence.subSequence(i4, length + 1);
                if (charSequenceSubSequence instanceof String) {
                    zS = ((String) charSequenceSubSequence).endsWith("info: shizuku_starter exit with 0");
                } else {
                    zS = AbstractC0135dp.s(charSequenceSubSequence, charSequenceSubSequence.length() - 33, "info: shizuku_starter exit with 0", 0, 33);
                }
                starterActivity = (StarterActivity) obj3;
                if (zS) {
                    Xn xn2 = starterActivity.D;
                    Us us3 = (Us) xn2.getValue();
                    StringBuilder sb3 = us3.b;
                    sb3.append("");
                    sb3.append('\n');
                    us3.d(null);
                    Us us4 = (Us) xn2.getValue();
                    StringBuilder sb4 = us4.b;
                    sb4.append("Waiting for service...");
                    sb4.append('\n');
                    us4.d(null);
                    Nn.b(new Pl(i2, starterActivity), false, null);
                } else if (rl.a == 2) {
                    th = rl.c;
                    if (th instanceof C0266i1) {
                        i = R.string._0_resource_name_obfuscated_res_0x7f11001e;
                    } else if (th instanceof C0130dj) {
                        i = R.string._0_resource_name_obfuscated_res_0x7f110121;
                    } else if (!(th instanceof ConnectException)) {
                        if (th instanceof SSLProtocolException) {
                            i = R.string._0_resource_name_obfuscated_res_0x7f11001f;
                        } else {
                            i = 0;
                        }
                    }
                    if (i != 0) {
                        Ch ch2 = new Ch(starterActivity);
                        E1 e2 = (E1) ch2.b;
                        e2.f = e2.a.getText(i);
                        ch2.g(android.R.string.ok, null);
                        ch2.f();
                    }
                }
                ((C0741xe) obj2).a.setText(charSequenceSubSequence);
                break;
        }
        return pe;
    }

    public /* synthetic */ C0450o1(J1 j1, C0573s1 c0573s1) {
        this.a = 1;
        this.c = j1;
        this.b = c0573s1;
    }
}
