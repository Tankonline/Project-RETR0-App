package rikka.shizuku;

import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.icu.util.TimeZone;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class Nh extends AbstractC0224gl {
    public final /* synthetic */ com.google.android.material.datepicker.c a;
    public final /* synthetic */ MaterialButton b;
    public final /* synthetic */ Oh c;

    public Nh(Oh oh, com.google.android.material.datepicker.c cVar, MaterialButton materialButton) {
        this.c = oh;
        this.a = cVar;
        this.b = materialButton;
    }

    @Override // rikka.shizuku.AbstractC0224gl
    public final void a(RecyclerView recyclerView, int i) {
        if (i == 0) {
            recyclerView.announceForAccessibility(this.b.getText());
        }
    }

    @Override // rikka.shizuku.AbstractC0224gl
    public final void b(RecyclerView recyclerView, int i, int i2) {
        int iN0;
        Oh oh = this.c;
        if (i < 0) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) oh.d0.n;
            View viewP0 = linearLayoutManager.P0(0, linearLayoutManager.v(), false, true);
            iN0 = viewP0 == null ? -1 : AbstractC0162el.G(viewP0);
        } else {
            iN0 = ((LinearLayoutManager) oh.d0.n).N0();
        }
        C0608t5 c0608t5 = this.a.d;
        Calendar calendarA = Zr.a(c0608t5.a.a);
        calendarA.add(2, iN0);
        oh.Z = new Ei(calendarA);
        Calendar calendarA2 = Zr.a(c0608t5.a.a);
        calendarA2.add(2, iN0);
        calendarA2.set(5, 1);
        Calendar calendarA3 = Zr.a(calendarA2);
        calendarA3.get(2);
        calendarA3.get(1);
        calendarA3.getMaximum(7);
        calendarA3.getActualMaximum(5);
        calendarA3.getTimeInMillis();
        long timeInMillis = calendarA3.getTimeInMillis();
        Locale locale = Locale.getDefault();
        AtomicReference atomicReference = Zr.a;
        DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton("yMMMM", locale);
        instanceForSkeleton.setTimeZone(TimeZone.getTimeZone("UTC"));
        instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
        this.b.setText(instanceForSkeleton.format(new Date(timeInMillis)));
    }
}
