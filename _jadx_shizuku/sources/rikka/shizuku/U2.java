package rikka.shizuku;

import android.app.Activity;
import android.content.ClipData;
import android.os.Build;
import android.text.Selection;
import android.text.Spannable;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public abstract class U2 {
    public static boolean a(DragEvent dragEvent, TextView textView, Activity activity) {
        InterfaceC0765y7 ys;
        activity.requestDragAndDropPermissions(dragEvent);
        int offsetForPosition = textView.getOffsetForPosition(dragEvent.getX(), dragEvent.getY());
        textView.beginBatchEdit();
        try {
            Selection.setSelection((Spannable) textView.getText(), offsetForPosition);
            ClipData clipData = dragEvent.getClipData();
            if (Build.VERSION.SDK_INT >= 31) {
                ys = new Ys(clipData, 3);
            } else {
                C0796z7 c0796z7 = new C0796z7();
                c0796z7.b = clipData;
                c0796z7.c = 3;
                ys = c0796z7;
            }
            Hs.i(textView, ys.d());
            return true;
        } finally {
            textView.endBatchEdit();
        }
    }

    public static boolean b(DragEvent dragEvent, View view, Activity activity) {
        InterfaceC0765y7 ys;
        activity.requestDragAndDropPermissions(dragEvent);
        ClipData clipData = dragEvent.getClipData();
        if (Build.VERSION.SDK_INT >= 31) {
            ys = new Ys(clipData, 3);
        } else {
            C0796z7 c0796z7 = new C0796z7();
            c0796z7.b = clipData;
            c0796z7.c = 3;
            ys = c0796z7;
        }
        Hs.i(view, ys.d());
        return true;
    }
}
