package rikka.shizuku;

import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: renamed from: rikka.shizuku.s0, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnClickListenerC0572s0 implements View.OnClickListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ ViewOnClickListenerC0572s0(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Message messageObtain;
        Message message;
        Message message2;
        Message message3;
        switch (this.a) {
            case 0:
                ((F0) this.b).b();
                break;
            case 1:
                H1 h1 = (H1) this.b;
                if (view == h1.i && (message3 = h1.k) != null) {
                    messageObtain = Message.obtain(message3);
                } else if (view != h1.l || (message2 = h1.n) == null) {
                    messageObtain = (view != h1.o || (message = h1.q) == null) ? null : Message.obtain(message);
                } else {
                    messageObtain = Message.obtain(message2);
                }
                if (messageObtain != null) {
                    messageObtain.sendToTarget();
                }
                h1.F.obtainMessage(1, h1.b).sendToTarget();
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                Oh oh = (Oh) this.b;
                int i = oh.a0;
                if (i == 2) {
                    oh.N(1);
                } else if (i == 1) {
                    oh.N(2);
                }
                break;
            case 3:
                ((Preference) this.b).u(view);
                break;
            case 4:
                SearchView searchView = (SearchView) this.b;
                ImageView imageView = searchView.t;
                SearchView.SearchAutoComplete searchAutoComplete = searchView.p;
                if (view == imageView) {
                    searchView.q(false);
                    searchAutoComplete.requestFocus();
                    searchAutoComplete.a(true);
                    break;
                } else if (view == searchView.v) {
                    if (!TextUtils.isEmpty(searchAutoComplete.getText())) {
                        searchAutoComplete.setText("");
                        searchAutoComplete.requestFocus();
                        searchAutoComplete.a(true);
                    } else if (searchView.G) {
                        searchView.clearFocus();
                        searchView.q(true);
                    }
                    break;
                } else if (view != searchView.u) {
                    if (view != searchView.w && view == searchAutoComplete) {
                        searchView.m();
                    }
                    break;
                } else {
                    Editable text = searchAutoComplete.getText();
                    if (text != null && TextUtils.getTrimmedLength(text) > 0) {
                        searchAutoComplete.a(false);
                        searchAutoComplete.dismissDropDown();
                        break;
                    }
                }
                break;
            default:
                C0136dq c0136dq = ((Toolbar) this.b).M;
                C0221gi c0221gi = c0136dq == null ? null : c0136dq.b;
                if (c0221gi != null) {
                    c0221gi.collapseActionView();
                }
                break;
        }
    }
}
