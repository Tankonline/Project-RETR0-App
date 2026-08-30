package rikka.shizuku;

import android.os.FileObserver;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class Y1 extends FileObserver {
    public final LinkedHashSet a;

    public Y1(String str) {
        super(str, 512);
        this.a = new LinkedHashSet();
    }

    @Override // android.os.FileObserver
    public final void onEvent(int i, String str) {
        Map map = Z1.a;
        StringBuilder sb = new StringBuilder();
        if ((i & 1) == 1) {
            sb.append("ACCESS | ");
        }
        if ((i & 2) == 2) {
            sb.append("MODIFY | ");
        }
        if ((i & 4) == 4) {
            sb.append("ATTRIB | ");
        }
        if ((i & 8) == 8) {
            sb.append("CLOSE_WRITE | ");
        }
        if ((i & 16) == 16) {
            sb.append("CLOSE_NOWRITE | ");
        }
        if ((i & 32) == 32) {
            sb.append("OPEN | ");
        }
        if ((i & 64) == 64) {
            sb.append("MOVED_FROM | ");
        }
        if ((i & 128) == 128) {
            sb.append("MOVED_TO | ");
        }
        if ((i & 256) == 256) {
            sb.append("CREATE | ");
        }
        if ((i & 512) == 512) {
            sb.append("DELETE | ");
        }
        if ((i & 1024) == 1024) {
            sb.append("DELETE_SELF | ");
        }
        if ((i & 2048) == 2048) {
            sb.append("MOVE_SELF | ");
        }
        int i2 = i & 32768;
        if (i2 == 32768) {
            sb.append("IN_IGNORED | ");
        }
        if ((i & 1073741824) == 1073741824) {
            sb.append("IN_ISDIR | ");
        }
        int i3 = 0;
        if (sb.length() > 0) {
            sb.substring(0, sb.length() - 3);
        }
        if (i2 == 0 && str != null && str.equals("base.apk")) {
            super.stopWatching();
            ArrayList arrayList = new ArrayList(this.a);
            int size = arrayList.size();
            while (i3 < size) {
                Object obj = arrayList.get(i3);
                i3++;
                ((X1) obj).a();
            }
        }
    }
}
