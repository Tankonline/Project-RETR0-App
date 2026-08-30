package rikka.shizuku;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class Gi {
    public final C0104co a = new C0104co(0);
    public final C0104co b = new C0104co(0);

    public static Gi a(ArrayList arrayList) {
        Gi gi = new Gi();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Animator animator = (Animator) arrayList.get(i);
            if (!(animator instanceof ObjectAnimator)) {
                throw new IllegalArgumentException("Animator must be an ObjectAnimator: " + animator);
            }
            ObjectAnimator objectAnimator = (ObjectAnimator) animator;
            gi.b.put(objectAnimator.getPropertyName(), objectAnimator.getValues());
            String propertyName = objectAnimator.getPropertyName();
            long startDelay = objectAnimator.getStartDelay();
            long duration = objectAnimator.getDuration();
            TimeInterpolator interpolator = objectAnimator.getInterpolator();
            if ((interpolator instanceof AccelerateDecelerateInterpolator) || interpolator == null) {
                interpolator = V1.b;
            } else if (interpolator instanceof AccelerateInterpolator) {
                interpolator = V1.c;
            } else if (interpolator instanceof DecelerateInterpolator) {
                interpolator = V1.d;
            }
            Hi hi = new Hi();
            hi.d = 0;
            hi.e = 1;
            hi.a = startDelay;
            hi.b = duration;
            hi.c = interpolator;
            hi.d = objectAnimator.getRepeatCount();
            hi.e = objectAnimator.getRepeatMode();
            gi.a.put(propertyName, hi);
        }
        return gi;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Gi) {
            return this.a.equals(((Gi) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return "\n" + Gi.class.getName() + '{' + Integer.toHexString(System.identityHashCode(this)) + " timings: " + this.a + "}\n";
    }
}
