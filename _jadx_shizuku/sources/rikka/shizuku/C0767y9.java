package rikka.shizuku;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.y9, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0767y9 extends C {
    public final boolean c;
    public boolean d;
    public R2 e;

    public C0767y9(C0751xo c0751xo, boolean z) {
        super(1, c0751xo);
        this.c = z;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0027  */
    /* JADX WARN: Code duplicated, block: B:80:0x00e5 A[Catch: RuntimeException -> 0x00eb, TRY_LEAVE, TryCatch #2 {RuntimeException -> 0x00eb, blocks: (B:78:0x00df, B:80:0x00e5), top: B:91:0x00df }] */
    public final R2 f(Context context) {
        int i;
        R2 r2;
        Animator animatorLoadAnimator;
        int iN;
        if (this.d) {
            return this.e;
        }
        C0751xo c0751xo = (C0751xo) this.b;
        Ac ac = c0751xo.c;
        boolean z = c0751xo.a == 2;
        C0801zc c0801zc = ac.J;
        int i2 = c0801zc == null ? 0 : c0801zc.f;
        if (this.c) {
            if (z) {
                if (c0801zc == null) {
                    i = 0;
                } else {
                    i = c0801zc.d;
                }
            } else if (c0801zc == null) {
                i = 0;
            } else {
                i = c0801zc.e;
            }
        } else if (z) {
            if (c0801zc == null) {
                i = 0;
            } else {
                i = c0801zc.b;
            }
        } else if (c0801zc == null) {
            i = 0;
        } else {
            i = c0801zc.c;
        }
        ac.J(0, 0, 0, 0);
        ViewGroup viewGroup = ac.F;
        R2 r3 = null;
        if (viewGroup != null && viewGroup.getTag(R.id._0_resource_name_obfuscated_res_0x7f090201) != null) {
            ac.F.setTag(R.id._0_resource_name_obfuscated_res_0x7f090201, null);
        }
        ViewGroup viewGroup2 = ac.F;
        if (viewGroup2 == null || viewGroup2.getLayoutTransition() == null) {
            if (i == 0 && i2 != 0) {
                if (i2 == 4097) {
                    iN = z ? R.animator._0_resource_name_obfuscated_res_0x7f020008 : R.animator._0_resource_name_obfuscated_res_0x7f020009;
                } else if (i2 == 8194) {
                    iN = z ? R.animator._0_resource_name_obfuscated_res_0x7f020004 : R.animator._0_resource_name_obfuscated_res_0x7f020005;
                } else if (i2 == 8197) {
                    iN = z ? AbstractC0599sr.N(context, android.R.attr.activityCloseEnterAnimation) : AbstractC0599sr.N(context, android.R.attr.activityCloseExitAnimation);
                } else if (i2 == 4099) {
                    iN = z ? R.animator._0_resource_name_obfuscated_res_0x7f020006 : R.animator._0_resource_name_obfuscated_res_0x7f020007;
                } else if (i2 != 4100) {
                    iN = -1;
                } else {
                    iN = z ? AbstractC0599sr.N(context, android.R.attr.activityOpenEnterAnimation) : AbstractC0599sr.N(context, android.R.attr.activityOpenExitAnimation);
                }
                i = iN;
            }
            if (i != 0) {
                boolean zEquals = "anim".equals(context.getResources().getResourceTypeName(i));
                if (zEquals) {
                    try {
                        Animation animationLoadAnimation = AnimationUtils.loadAnimation(context, i);
                        if (animationLoadAnimation != null) {
                            r2 = new R2(animationLoadAnimation);
                            r3 = r2;
                        }
                    } catch (Resources.NotFoundException e) {
                        throw e;
                    } catch (RuntimeException unused) {
                        try {
                            animatorLoadAnimator = AnimatorInflater.loadAnimator(context, i);
                            if (animatorLoadAnimator != null) {
                                r2 = new R2(animatorLoadAnimator);
                                r3 = r2;
                            }
                        } catch (RuntimeException e2) {
                            if (zEquals) {
                                throw e2;
                            }
                            Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(context, i);
                            if (animationLoadAnimation2 != null) {
                                r3 = new R2(animationLoadAnimation2);
                            }
                        }
                    }
                } else {
                    animatorLoadAnimator = AnimatorInflater.loadAnimator(context, i);
                    if (animatorLoadAnimator != null) {
                        r2 = new R2(animatorLoadAnimator);
                        r3 = r2;
                    }
                }
            }
        }
        this.e = r3;
        this.d = true;
        return r3;
    }
}
