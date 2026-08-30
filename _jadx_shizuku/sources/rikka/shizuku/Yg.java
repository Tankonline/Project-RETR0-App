package rikka.shizuku;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;

/* JADX INFO: loaded from: classes.dex */
public final class Yg implements View.OnTouchListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ Yg(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        Q2 q2;
        switch (this.a) {
            case 0:
                int action = motionEvent.getAction();
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                Zg zg = (Zg) this.b;
                if (action == 0 && (q2 = zg.z) != null && q2.isShowing() && x >= 0 && x < zg.z.getWidth() && y >= 0 && y < zg.z.getHeight()) {
                    zg.v.postDelayed(zg.r, 250L);
                    return false;
                }
                if (action != 1) {
                    return false;
                }
                zg.v.removeCallbacks(zg.r);
                return false;
            default:
                if (((Checkable) view).isChecked()) {
                    return ((GestureDetector) this.b).onTouchEvent(motionEvent);
                }
                return false;
        }
    }
}
