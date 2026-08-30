package rikka.shizuku;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.preference.Preference;
import moe.shizuku.privileged.api.R;

/* JADX INFO: renamed from: rikka.shizuku.dk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class ViewOnCreateContextMenuListenerC0131dk implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
    public final Preference a;

    public ViewOnCreateContextMenuListenerC0131dk(Preference preference) {
        this.a = preference;
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        Preference preference = this.a;
        CharSequence charSequenceG = preference.g();
        if (!preference.C || TextUtils.isEmpty(charSequenceG)) {
            return;
        }
        contextMenu.setHeaderTitle(charSequenceG);
        contextMenu.add(0, 0, 0, R.string._0_resource_name_obfuscated_res_0x7f110046).setOnMenuItemClickListener(this);
    }

    @Override // android.view.MenuItem.OnMenuItemClickListener
    public final boolean onMenuItemClick(MenuItem menuItem) {
        Preference preference = this.a;
        ClipboardManager clipboardManager = (ClipboardManager) preference.a.getSystemService("clipboard");
        CharSequence charSequenceG = preference.g();
        clipboardManager.setPrimaryClip(ClipData.newPlainText("Preference", charSequenceG));
        Context context = preference.a;
        Toast.makeText(context, context.getString(R.string._0_resource_name_obfuscated_res_0x7f11010d, charSequenceG), 0).show();
        return true;
    }
}
