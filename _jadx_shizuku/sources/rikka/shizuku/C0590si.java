package rikka.shizuku;

import android.content.Context;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import androidx.appcompat.view.menu.ListMenuItemView;
import java.util.ArrayList;

/* JADX INFO: renamed from: rikka.shizuku.si, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0590si extends C0490pa {
    public final int m;
    public final int n;
    public C0621ti o;
    public C0221gi p;

    public C0590si(Context context, boolean z) {
        super(context, z);
        if (1 == AbstractC0559ri.a(context.getResources().getConfiguration())) {
            this.m = 21;
            this.n = 22;
        } else {
            this.m = 22;
            this.n = 21;
        }
    }

    @Override // rikka.shizuku.C0490pa, android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        C0036ai c0036ai;
        int headersCount;
        Ys ys;
        Ys ys2;
        int iPointToPosition;
        int i;
        if (this.o != null) {
            ListAdapter adapter = getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                headersCount = headerViewListAdapter.getHeadersCount();
                c0036ai = (C0036ai) headerViewListAdapter.getWrappedAdapter();
            } else {
                c0036ai = (C0036ai) adapter;
                headersCount = 0;
            }
            C0221gi c0221giB = (motionEvent.getAction() == 10 || (iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY())) == -1 || (i = iPointToPosition - headersCount) < 0 || i >= c0036ai.getCount()) ? null : c0036ai.getItem(i);
            C0221gi c0221gi = this.p;
            if (c0221gi != c0221giB) {
                MenuC0129di menuC0129di = c0036ai.a;
                if (c0221gi != null && (ys2 = this.o.C) != null) {
                    ((G5) ys2.b).f.removeCallbacksAndMessages(menuC0129di);
                }
                this.p = c0221giB;
                if (c0221giB != null && (ys = this.o.C) != null) {
                    G5 g5 = (G5) ys.b;
                    g5.f.removeCallbacksAndMessages(null);
                    ArrayList arrayList = g5.h;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            i2 = -1;
                            break;
                        }
                        if (menuC0129di == ((F5) arrayList.get(i2)).b) {
                            break;
                        }
                        i2++;
                    }
                    if (i2 != -1) {
                        int i3 = i2 + 1;
                        g5.f.postAtTime(new E5(ys, i3 < arrayList.size() ? (F5) arrayList.get(i3) : null, c0221giB, menuC0129di), menuC0129di, SystemClock.uptimeMillis() + 200);
                    }
                }
            }
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        ListMenuItemView listMenuItemView = (ListMenuItemView) getSelectedView();
        if (listMenuItemView != null && i == this.m) {
            if (listMenuItemView.isEnabled() && listMenuItemView.a.hasSubMenu()) {
                performItemClick(listMenuItemView, getSelectedItemPosition(), getSelectedItemId());
            }
            return true;
        }
        if (listMenuItemView == null || i != this.n) {
            return super.onKeyDown(i, keyEvent);
        }
        setSelection(-1);
        ListAdapter adapter = getAdapter();
        (adapter instanceof HeaderViewListAdapter ? (C0036ai) ((HeaderViewListAdapter) adapter).getWrappedAdapter() : (C0036ai) adapter).a.c(false);
        return true;
    }
}
