package rikka.shizuku;

import android.os.Bundle;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class Ra extends InputConnectionWrapper {
    public final EditText a;
    public final Re b;

    public Ra(EditText editText, InputConnection inputConnection, EditorInfo editorInfo) {
        Re re = new Re();
        super(inputConnection, false);
        this.a = editText;
        this.b = re;
        if (Ma.j != null) {
            Ma maA = Ma.a();
            if (maA.b() != 1 || editorInfo == null) {
                return;
            }
            if (editorInfo.extras == null) {
                editorInfo.extras = new Bundle();
            }
            Ia ia = maA.e;
            ia.getClass();
            Bundle bundle = editorInfo.extras;
            Bi bi = (Bi) ia.c.a;
            int iA = bi.a(4);
            bundle.putInt("android.support.text.emoji.emojiCompat_metadataVersion", iA != 0 ? ((ByteBuffer) bi.d).getInt(iA + bi.a) : 0);
            Bundle bundle2 = editorInfo.extras;
            ia.a.getClass();
            bundle2.putBoolean("android.support.text.emoji.emojiCompat_replaceAll", false);
        }
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingText(int i, int i2) {
        Editable editableText = this.a.getEditableText();
        this.b.getClass();
        return Re.d(this, editableText, i, i2, false) || super.deleteSurroundingText(i, i2);
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public final boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        Editable editableText = this.a.getEditableText();
        this.b.getClass();
        return Re.d(this, editableText, i, i2, true) || super.deleteSurroundingTextInCodePoints(i, i2);
    }
}
