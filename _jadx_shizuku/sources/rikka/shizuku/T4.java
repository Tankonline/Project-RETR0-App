package rikka.shizuku;

import android.os.Build;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.materialswitch.MaterialSwitch;
import java.util.ArrayList;
import java.util.List;
import moe.shizuku.privileged.api.R;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public abstract class T4 extends Yk {
    public final ArrayList d;
    public final InterfaceC0242h8 e = k();

    public T4(ArrayList arrayList) {
        this.d = arrayList;
    }

    @Override // rikka.shizuku.Yk
    public final int a() {
        return this.d.size();
    }

    @Override // rikka.shizuku.Yk
    public final int c(int i) {
        Object obj = this.d.get(i);
        int iA = this.e.a(this, i);
        if (iA >= 0) {
            return iA;
        }
        throw new IllegalStateException("Can't find Creator for " + obj.getClass() + ", position: " + i);
    }

    @Override // rikka.shizuku.Yk
    public final void d(AbstractC0470ol abstractC0470ol, int i) {
        X4 x4 = (X4) abstractC0470ol;
        Object obj = this.d.get(i);
        x4.v = this;
        x4.u = obj;
        x4.q();
    }

    @Override // rikka.shizuku.Yk
    public final void e(AbstractC0470ol abstractC0470ol, int i, List list) {
        X4 x4 = (X4) abstractC0470ol;
        if (list.isEmpty()) {
            Object obj = this.d.get(i);
            x4.v = this;
            x4.u = obj;
            x4.q();
            return;
        }
        Object obj2 = this.d.get(i);
        x4.v = this;
        x4.u = obj2;
        x4.r();
    }

    @Override // rikka.shizuku.Yk
    public final AbstractC0470ol f(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        switch (this.e.b(i).a) {
            case 0:
                MaterialCardView materialCardView = (MaterialCardView) Ys.x(layoutInflaterFrom, viewGroup).b;
                View viewInflate = layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c003c, (ViewGroup) materialCardView, false);
                materialCardView.addView(viewInflate);
                int i2 = android.R.id.button1;
                MaterialButton materialButton = (MaterialButton) AbstractC0750xn.h(viewInflate, android.R.id.button1);
                if (materialButton != null) {
                    i2 = android.R.id.icon;
                    if (((ImageView) AbstractC0750xn.h(viewInflate, android.R.id.icon)) != null) {
                        i2 = android.R.id.text1;
                        if (((TextView) AbstractC0750xn.h(viewInflate, android.R.id.text1)) != null) {
                            i2 = android.R.id.title;
                            if (((TextView) AbstractC0750xn.h(viewInflate, android.R.id.title)) != null) {
                                C0790z1 c0790z1 = new C0790z1(materialCardView, 0);
                                materialButton.setOnClickListener(new X0(2));
                                return c0790z1;
                            }
                        }
                    }
                }
                throw new NullPointerException("Missing required view with ID: ".concat(viewInflate.getResources().getResourceName(i2)));
            case 1:
                View viewInflate2 = layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0021, viewGroup, false);
                int i3 = android.R.id.icon;
                ImageView imageView = (ImageView) AbstractC0750xn.h(viewInflate2, android.R.id.icon);
                if (imageView != null) {
                    i3 = R.id._0_resource_name_obfuscated_res_0x7f090166;
                    TextView textView = (TextView) AbstractC0750xn.h(viewInflate2, R.id._0_resource_name_obfuscated_res_0x7f090166);
                    if (textView != null) {
                        i3 = android.R.id.summary;
                        TextView textView2 = (TextView) AbstractC0750xn.h(viewInflate2, android.R.id.summary);
                        if (textView2 != null) {
                            i3 = R.id._0_resource_name_obfuscated_res_0x7f0901b8;
                            MaterialSwitch materialSwitch = (MaterialSwitch) AbstractC0750xn.h(viewInflate2, R.id._0_resource_name_obfuscated_res_0x7f0901b8);
                            if (materialSwitch != null) {
                                i3 = android.R.id.title;
                                TextView textView3 = (TextView) AbstractC0750xn.h(viewInflate2, android.R.id.title);
                                if (textView3 != null) {
                                    return new S3(new K2((LinearLayout) viewInflate2, imageView, textView, textView2, materialSwitch, textView3));
                                }
                            }
                        }
                    }
                }
                throw new NullPointerException("Missing required view with ID: ".concat(viewInflate2.getResources().getResourceName(i3)));
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                View viewInflate3 = layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0020, viewGroup, false);
                if (viewInflate3 != null) {
                    return new C0790z1((LinearLayout) viewInflate3, 1);
                }
                throw new NullPointerException("rootView");
            case 3:
                MaterialCardView materialCardView2 = (MaterialCardView) Ys.x(layoutInflaterFrom, viewGroup).b;
                View viewInflate4 = layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c003e, (ViewGroup) materialCardView2, false);
                materialCardView2.addView(viewInflate4);
                int i4 = android.R.id.icon;
                if (((ImageView) AbstractC0750xn.h(viewInflate4, android.R.id.icon)) != null) {
                    i4 = android.R.id.text1;
                    if (((TextView) AbstractC0750xn.h(viewInflate4, android.R.id.text1)) != null) {
                        i4 = android.R.id.text2;
                        if (((TextView) AbstractC0750xn.h(viewInflate4, android.R.id.text2)) != null) {
                            C0790z1 c0790z2 = new C0790z1(materialCardView2, 2);
                            materialCardView2.setOnClickListener(new X0(3));
                            return c0790z2;
                        }
                    }
                }
                throw new NullPointerException("Missing required view with ID: ".concat(viewInflate4.getResources().getResourceName(i4)));
            case 4:
                MaterialCardView materialCardView3 = (MaterialCardView) Ys.x(layoutInflaterFrom, viewGroup).b;
                View viewInflate5 = layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c003f, (ViewGroup) materialCardView3, false);
                materialCardView3.addView(viewInflate5);
                int i5 = android.R.id.icon;
                if (((ImageView) AbstractC0750xn.h(viewInflate5, android.R.id.icon)) != null) {
                    i5 = android.R.id.text1;
                    TextView textView4 = (TextView) AbstractC0750xn.h(viewInflate5, android.R.id.text1);
                    if (textView4 != null) {
                        i5 = android.R.id.text2;
                        TextView textView5 = (TextView) AbstractC0750xn.h(viewInflate5, android.R.id.text2);
                        if (textView5 != null) {
                            return new ViewOnClickListenerC0806zh(new R2(textView4, textView5, 18, false), materialCardView3);
                        }
                    }
                }
                throw new NullPointerException("Missing required view with ID: ".concat(viewInflate5.getResources().getResourceName(i5)));
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                MaterialCardView materialCardView4 = (MaterialCardView) Ys.x(layoutInflaterFrom, viewGroup).b;
                View viewInflate6 = layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0040, (ViewGroup) materialCardView4, false);
                materialCardView4.addView(viewInflate6);
                int i6 = android.R.id.icon;
                ImageView imageView2 = (ImageView) AbstractC0750xn.h(viewInflate6, android.R.id.icon);
                if (imageView2 != null) {
                    i6 = android.R.id.text1;
                    TextView textView6 = (TextView) AbstractC0750xn.h(viewInflate6, android.R.id.text1);
                    if (textView6 != null) {
                        i6 = android.R.id.text2;
                        TextView textView7 = (TextView) AbstractC0750xn.h(viewInflate6, android.R.id.text2);
                        if (textView7 != null) {
                            return new Ym(new K3(imageView2, textView6, textView7), materialCardView4, (byte) 0);
                        }
                    }
                }
                throw new NullPointerException("Missing required view with ID: ".concat(viewInflate6.getResources().getResourceName(i6)));
            case 6:
                MaterialCardView materialCardView5 = (MaterialCardView) Ys.x(layoutInflaterFrom, viewGroup).b;
                View viewInflate7 = layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0041, (ViewGroup) materialCardView5, false);
                materialCardView5.addView(viewInflate7);
                int i7 = android.R.id.button1;
                MaterialButton materialButton2 = (MaterialButton) AbstractC0750xn.h(viewInflate7, android.R.id.button1);
                if (materialButton2 != null) {
                    i7 = android.R.id.icon;
                    if (((ImageView) AbstractC0750xn.h(viewInflate7, android.R.id.icon)) != null) {
                        i7 = android.R.id.text1;
                        TextView textView8 = (TextView) AbstractC0750xn.h(viewInflate7, android.R.id.text1);
                        if (textView8 != null) {
                            C0790z1 c0790z3 = new C0790z1(materialCardView5, 3);
                            materialButton2.setOnClickListener(new X0(5));
                            textView8.setMovementMethod(LinkMovementMethod.getInstance());
                            textView8.setText(AbstractC0688vn.A(materialCardView5.getContext().getString(R.string._0_resource_name_obfuscated_res_0x7f11006f, AbstractC0494pe.a.a()), 512));
                            return c0790z3;
                        }
                    }
                }
                throw new NullPointerException("Missing required view with ID: ".concat(viewInflate7.getResources().getResourceName(i7)));
            case 7:
                MaterialCardView materialCardView6 = (MaterialCardView) Ys.x(layoutInflaterFrom, viewGroup).b;
                View viewInflate8 = layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0042, (ViewGroup) materialCardView6, false);
                materialCardView6.addView(viewInflate8);
                int i8 = android.R.id.button1;
                MaterialButton materialButton3 = (MaterialButton) AbstractC0750xn.h(viewInflate8, android.R.id.button1);
                if (materialButton3 != null) {
                    i8 = android.R.id.button2;
                    MaterialButton materialButton4 = (MaterialButton) AbstractC0750xn.h(viewInflate8, android.R.id.button2);
                    if (materialButton4 != null) {
                        i8 = android.R.id.icon;
                        if (((ImageView) AbstractC0750xn.h(viewInflate8, android.R.id.icon)) != null) {
                            i8 = android.R.id.text1;
                            TextView textView9 = (TextView) AbstractC0750xn.h(viewInflate8, android.R.id.text1);
                            if (textView9 != null) {
                                i8 = android.R.id.title;
                                if (((TextView) AbstractC0750xn.h(viewInflate8, android.R.id.title)) != null) {
                                    return new Ym(new K3(materialButton3, materialButton4, textView9), materialCardView6);
                                }
                            }
                        }
                    }
                }
                throw new NullPointerException("Missing required view with ID: ".concat(viewInflate8.getResources().getResourceName(i8)));
            case 8:
                MaterialCardView materialCardView7 = (MaterialCardView) Ys.x(layoutInflaterFrom, viewGroup).b;
                View viewInflate9 = layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0043, (ViewGroup) materialCardView7, false);
                materialCardView7.addView(viewInflate9);
                int i9 = android.R.id.button1;
                MaterialButton materialButton5 = (MaterialButton) AbstractC0750xn.h(viewInflate9, android.R.id.button1);
                if (materialButton5 != null) {
                    i9 = android.R.id.button2;
                    MaterialButton materialButton6 = (MaterialButton) AbstractC0750xn.h(viewInflate9, android.R.id.button2);
                    if (materialButton6 != null) {
                        i9 = android.R.id.button3;
                        MaterialButton materialButton7 = (MaterialButton) AbstractC0750xn.h(viewInflate9, android.R.id.button3);
                        if (materialButton7 != null) {
                            i9 = android.R.id.icon;
                            if (((ImageView) AbstractC0750xn.h(viewInflate9, android.R.id.icon)) != null) {
                                i9 = android.R.id.text1;
                                TextView textView10 = (TextView) AbstractC0750xn.h(viewInflate9, android.R.id.text1);
                                if (textView10 != null) {
                                    C0790z1 c0790z4 = new C0790z1(materialCardView7, 4);
                                    materialButton5.setOnClickListener(new X0(c0790z4, 6));
                                    if (Build.VERSION.SDK_INT < 30) {
                                        textView10.setText(AbstractC0688vn.A(materialCardView7.getContext().getString(R.string._0_resource_name_obfuscated_res_0x7f110087), 512));
                                        materialButton6.setVisibility(8);
                                        materialButton7.setVisibility(8);
                                        return c0790z4;
                                    }
                                    materialButton7.setOnClickListener(new X0(7));
                                    materialButton6.setOnClickListener(new X0(c0790z4, 8));
                                    textView10.setMovementMethod(LinkMovementMethod.getInstance());
                                    textView10.setText(AbstractC0688vn.A(materialCardView7.getContext().getString(R.string._0_resource_name_obfuscated_res_0x7f110086), 512));
                                    return c0790z4;
                                }
                            }
                        }
                    }
                }
                throw new NullPointerException("Missing required view with ID: ".concat(viewInflate9.getResources().getResourceName(i9)));
            default:
                MaterialCardView materialCardView8 = (MaterialCardView) Ys.x(layoutInflaterFrom, viewGroup).b;
                View viewInflate10 = layoutInflaterFrom.inflate(R.layout._0_resource_name_obfuscated_res_0x7f0c0044, (ViewGroup) materialCardView8, false);
                materialCardView8.addView(viewInflate10);
                int i10 = android.R.id.icon;
                if (((ImageView) AbstractC0750xn.h(viewInflate10, android.R.id.icon)) != null) {
                    i10 = android.R.id.text1;
                    if (((TextView) AbstractC0750xn.h(viewInflate10, android.R.id.text1)) != null) {
                        i10 = android.R.id.text2;
                        TextView textView11 = (TextView) AbstractC0750xn.h(viewInflate10, android.R.id.text2);
                        if (textView11 != null) {
                            return new S3(new C0741xe(textView11), materialCardView8);
                        }
                    }
                }
                throw new NullPointerException("Missing required view with ID: ".concat(viewInflate10.getResources().getResourceName(i10)));
        }
    }

    @Override // rikka.shizuku.Yk
    public final void g(AbstractC0470ol abstractC0470ol) {
    }

    @Override // rikka.shizuku.Yk
    public final void h(AbstractC0470ol abstractC0470ol) {
    }

    @Override // rikka.shizuku.Yk
    public final void i(AbstractC0470ol abstractC0470ol) {
        X4 x4 = (X4) abstractC0470ol;
        x4.s();
        x4.u = null;
        x4.v = null;
    }

    public abstract InterfaceC0242h8 k();
}
