package rikka.shizuku;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import java.lang.reflect.Method;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class V9 {
    public final Context a;
    public final W9 b;
    public VelocityTracker c;
    public float d;
    public int e = -1;
    public int f = -1;
    public int g = -1;
    public final int[] h = {Integer.MAX_VALUE, 0};

    public V9(Context context, W9 w9) {
        this.a = context;
        this.b = w9;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x007e  */
    /* JADX WARN: Code duplicated, block: B:41:0x00a7  */
    /* JADX WARN: Code duplicated, block: B:85:0x016f  */
    public final void a(MotionEvent motionEvent, int i) {
        int i2;
        int i3;
        int i4;
        int scaledMinimumFlingVelocity;
        int scaledMaximumFlingVelocity;
        boolean z;
        float f;
        float yVelocity;
        long j;
        float fSqrt;
        float f2;
        int i5;
        int source = motionEvent.getSource();
        int deviceId = motionEvent.getDeviceId();
        int i6 = this.f;
        int[] iArr = this.h;
        if (i6 == source && this.g == deviceId && this.e == i) {
            z = false;
            i2 = 1;
            i3 = 20;
            i4 = 0;
        } else {
            Context context = this.a;
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            int deviceId2 = motionEvent.getDeviceId();
            i2 = 1;
            int source2 = motionEvent.getSource();
            i3 = 20;
            int i7 = Build.VERSION.SDK_INT;
            i4 = 0;
            if (i7 >= 34) {
                Method method = Is.a;
                scaledMinimumFlingVelocity = AbstractC0111d0.f(viewConfiguration, deviceId2, i, source2);
            } else {
                Method method2 = Is.a;
                InputDevice device = InputDevice.getDevice(deviceId2);
                if (device == null || device.getMotionRange(i, source2) == null) {
                    scaledMinimumFlingVelocity = Integer.MAX_VALUE;
                } else {
                    Resources resources = context.getResources();
                    int identifier = (source2 == 4194304 && i == 26) ? resources.getIdentifier("config_viewMinRotaryEncoderFlingVelocity", "dimen", "android") : -1;
                    if (identifier == -1) {
                        scaledMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
                    } else if (identifier == 0 || (scaledMinimumFlingVelocity = resources.getDimensionPixelSize(identifier)) < 0) {
                        scaledMinimumFlingVelocity = Integer.MAX_VALUE;
                    }
                }
            }
            iArr[0] = scaledMinimumFlingVelocity;
            int deviceId3 = motionEvent.getDeviceId();
            int source3 = motionEvent.getSource();
            if (i7 >= 34) {
                scaledMaximumFlingVelocity = AbstractC0111d0.e(viewConfiguration, deviceId3, i, source3);
            } else {
                InputDevice device2 = InputDevice.getDevice(deviceId3);
                if ((device2 == null || device2.getMotionRange(i, source3) == null) ? false : true) {
                    Resources resources2 = context.getResources();
                    int identifier2 = (source3 == 4194304 && i == 26) ? resources2.getIdentifier("config_viewMaxRotaryEncoderFlingVelocity", "dimen", "android") : -1;
                    if (identifier2 == -1) {
                        scaledMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
                    } else if (identifier2 == 0 || (scaledMaximumFlingVelocity = resources2.getDimensionPixelSize(identifier2)) < 0) {
                        scaledMaximumFlingVelocity = Integer.MIN_VALUE;
                    }
                } else {
                    scaledMaximumFlingVelocity = Integer.MIN_VALUE;
                }
            }
            iArr[1] = scaledMaximumFlingVelocity;
            this.f = source;
            this.g = deviceId;
            this.e = i;
            z = true;
        }
        if (iArr[i4] == Integer.MAX_VALUE) {
            VelocityTracker velocityTracker = this.c;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.c = null;
                return;
            }
            return;
        }
        if (this.c == null) {
            this.c = VelocityTracker.obtain();
        }
        VelocityTracker velocityTracker2 = this.c;
        Map map = AbstractC0446ns.a;
        velocityTracker2.addMovement(motionEvent);
        float f3 = 0.0f;
        if (Build.VERSION.SDK_INT < 34 && motionEvent.getSource() == 4194304) {
            Map map2 = AbstractC0446ns.a;
            if (!map2.containsKey(velocityTracker2)) {
                map2.put(velocityTracker2, new C0477os());
            }
            C0477os c0477os = (C0477os) map2.get(velocityTracker2);
            c0477os.getClass();
            long eventTime = motionEvent.getEventTime();
            int i8 = c0477os.d;
            long[] jArr = c0477os.b;
            if (i8 != 0 && eventTime - jArr[c0477os.e] > 40) {
                c0477os.d = i4;
                c0477os.c = 0.0f;
            }
            int i9 = (c0477os.e + 1) % 20;
            c0477os.e = i9;
            int i10 = c0477os.d;
            if (i10 != i3) {
                c0477os.d = i10 + 1;
            }
            c0477os.a[i9] = motionEvent.getAxisValue(26);
            jArr[c0477os.e] = eventTime;
        }
        int i11 = 1000;
        velocityTracker2.computeCurrentVelocity(1000, Float.MAX_VALUE);
        C0477os c0477os2 = (C0477os) AbstractC0446ns.a.get(velocityTracker2);
        if (c0477os2 != null) {
            int i12 = c0477os2.d;
            if (i12 < 2) {
                fSqrt = 0.0f;
                f = 0.0f;
            } else {
                int i13 = c0477os2.e;
                int i14 = ((i13 + 20) - (i12 - 1)) % 20;
                long[] jArr2 = c0477os2.b;
                long j2 = jArr2[i13];
                while (true) {
                    j = jArr2[i14];
                    if (j2 - j <= 100) {
                        break;
                    }
                    c0477os2.d--;
                    i14 = (i14 + 1) % 20;
                }
                int i15 = c0477os2.d;
                if (i15 < 2) {
                    fSqrt = 0.0f;
                    f = 0.0f;
                } else {
                    float[] fArr = c0477os2.a;
                    if (i15 == 2) {
                        int i16 = (i14 + 1) % 20;
                        long j3 = jArr2[i16];
                        if (j == j3) {
                            fSqrt = 0.0f;
                            f = 0.0f;
                        } else {
                            fSqrt = fArr[i16] / (j3 - j);
                            f = 0.0f;
                        }
                    } else {
                        float fAbs = 0.0f;
                        int i17 = 0;
                        int i18 = 0;
                        while (true) {
                            if (i17 >= c0477os2.d - 1) {
                                break;
                            }
                            int i19 = i17 + i14;
                            long j4 = jArr2[i19 % 20];
                            int i20 = (i19 + 1) % 20;
                            if (jArr2[i20] == j4) {
                                f2 = f3;
                                i5 = i2;
                            } else {
                                i18++;
                                f2 = f3;
                                float fSqrt2 = (fAbs < f3 ? -1.0f : 1.0f) * ((float) Math.sqrt(Math.abs(fAbs) * 2.0f));
                                float f4 = fArr[i20] / (jArr2[i20] - j4);
                                fAbs += Math.abs(f4) * (f4 - fSqrt2);
                                i5 = i2;
                                if (i18 == i5) {
                                    fAbs *= 0.5f;
                                }
                            }
                            i17 += i5;
                            i2 = i5;
                            f3 = f2;
                        }
                        f = f3;
                        fSqrt = (fAbs < f ? -1.0f : 1.0f) * ((float) Math.sqrt(Math.abs(fAbs) * 2.0f));
                        i11 = 1000;
                    }
                }
            }
            float f5 = fSqrt * i11;
            c0477os2.c = f5;
            if (f5 < (-Math.abs((float) r4))) {
                c0477os2.c = -Math.abs(Float.MAX_VALUE);
            } else if (c0477os2.c > Math.abs((float) r4)) {
                c0477os2.c = Math.abs((float) r4);
            }
        } else {
            f = 0.0f;
        }
        if (Build.VERSION.SDK_INT >= 34) {
            yVelocity = AbstractC0111d0.b(velocityTracker2, i);
        } else if (i == 0) {
            yVelocity = velocityTracker2.getXVelocity();
        } else if (i == 1) {
            yVelocity = velocityTracker2.getYVelocity();
        } else {
            C0477os c0477os3 = (C0477os) AbstractC0446ns.a.get(velocityTracker2);
            yVelocity = (c0477os3 == null || i != 26) ? f : c0477os3.c;
        }
        W9 w9 = this.b;
        float fL = w9.l() * yVelocity;
        float fSignum = Math.signum(fL);
        if (z || (fSignum != Math.signum(this.d) && fSignum != f)) {
            w9.m();
        }
        if (Math.abs(fL) < iArr[0]) {
            return;
        }
        int i21 = iArr[1];
        float fMax = Math.max(-i21, Math.min(fL, i21));
        this.d = w9.b(fMax) ? fMax : f;
    }
}
