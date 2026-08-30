package defpackage;

import android.util.Log;
import androidx.profileinstaller.ProfileInstallReceiver;
import java.io.Serializable;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: assets/rish_shizuku.dex */
public final class w implements y {
    public final /* synthetic */ ProfileInstallReceiver a;

    public w(ProfileInstallReceiver profileInstallReceiver) {
        this.a = profileInstallReceiver;
    }

    @Override // defpackage.y
    public final void a() {
        Log.d("ProfileInstaller", "DIAGNOSTIC_PROFILE_IS_COMPRESSED");
    }

    @Override // defpackage.y
    public final void b(int i, Serializable serializable) {
        String str;
        switch (i) {
            case 1:
                str = "RESULT_INSTALL_SUCCESS";
                break;
            case BulletSpan.STANDARD_GAP_WIDTH /* 2 */:
                str = "RESULT_ALREADY_INSTALLED";
                break;
            case 3:
                str = "RESULT_UNSUPPORTED_ART_VERSION";
                break;
            case 4:
                str = "RESULT_NOT_WRITABLE";
                break;
            case BulletSpan.STANDARD_BULLET_RADIUS /* 5 */:
                str = "RESULT_DESIRED_FORMAT_UNSUPPORTED";
                break;
            case 6:
                str = "RESULT_BASELINE_PROFILE_NOT_FOUND";
                break;
            case 7:
                str = "RESULT_IO_EXCEPTION";
                break;
            case 8:
                str = "RESULT_PARSE_EXCEPTION";
                break;
            case 9:
            default:
                str = "";
                break;
            case 10:
                str = "RESULT_INSTALL_SKIP_FILE_SUCCESS";
                break;
            case 11:
                str = "RESULT_DELETE_SKIP_FILE_SUCCESS";
                break;
        }
        if (i == 6 || i == 7 || i == 8) {
            Log.e("ProfileInstaller", str, (Throwable) serializable);
        } else {
            Log.d("ProfileInstaller", str);
        }
        this.a.setResultCode(i);
    }
}
