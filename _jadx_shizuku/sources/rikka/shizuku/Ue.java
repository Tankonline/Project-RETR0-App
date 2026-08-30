package rikka.shizuku;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import moe.shizuku.privileged.api.R;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import rikka.html.text.style.BulletSpan;

/* JADX INFO: loaded from: classes.dex */
public final class Ue implements ContentHandler {
    public static final float[] b = {1.5f, 1.4f, 1.3f, 1.2f, 1.1f, 1.0f};
    public static Pattern c;
    public static Pattern d;
    public static Pattern e;
    public static Pattern f;
    public SpannableStringBuilder a;

    static {
        HashMap map = new HashMap();
        map.put("darkgray", -5658199);
        map.put("gray", -8355712);
        map.put("lightgray", -2894893);
        map.put("darkgrey", -5658199);
        map.put("grey", -8355712);
        map.put("lightgrey", -2894893);
        map.put("green", -16744448);
    }

    public static void a(Editable editable, int i) {
        int length = editable.length();
        if (length == 0) {
            return;
        }
        int i2 = 0;
        for (int i3 = length - 1; i3 >= 0 && editable.charAt(i3) == '\n'; i3--) {
            i2++;
        }
        while (i2 < i) {
            editable.append("\n");
            i2++;
        }
    }

    public static void b(Editable editable, Class cls, ParcelableSpan parcelableSpan) {
        editable.length();
        Object objF = f(editable, cls);
        if (objF != null) {
            g(editable, objF, parcelableSpan);
        }
    }

    public static void c(Editable editable) {
        Oe oe = (Oe) f(editable, Oe.class);
        if (oe != null) {
            a(editable, oe.a);
            editable.removeSpan(oe);
        }
        Ce ce = (Ce) f(editable, Ce.class);
        if (ce != null) {
            g(editable, ce, new AlignmentSpan.Standard(ce.a));
        }
    }

    public static void d(SpannableStringBuilder spannableStringBuilder) {
        Qe qe = (Qe) f(spannableStringBuilder, Qe.class);
        if (qe != null) {
            g(spannableStringBuilder, qe, new StrikethroughSpan());
        }
        De de = (De) f(spannableStringBuilder, De.class);
        if (de != null) {
            g(spannableStringBuilder, de, new BackgroundColorSpan(de.a));
        }
        Je je = (Je) f(spannableStringBuilder, Je.class);
        if (je != null) {
            g(spannableStringBuilder, je, new ForegroundColorSpan(je.a));
        }
    }

    public static Object f(Spanned spanned, Class cls) {
        Object[] spans = spanned.getSpans(0, spanned.length(), cls);
        if (spans.length == 0) {
            return null;
        }
        return spans[spans.length - 1];
    }

    public static void g(Spannable spannable, Object obj, Object... objArr) {
        int spanStart = spannable.getSpanStart(obj);
        spannable.removeSpan(obj);
        int length = spannable.length();
        if (spanStart != length) {
            for (Object obj2 : objArr) {
                spannable.setSpan(obj2, spanStart, length, 33);
            }
        }
    }

    public static void h(Editable editable, Object obj) {
        int length = editable.length();
        editable.setSpan(obj, length, length, 17);
    }

    public static void i(Editable editable, Attributes attributes, int i) {
        editable.length();
        if (i > 0) {
            a(editable, i);
            Oe oe = new Oe();
            oe.a = i;
            int length = editable.length();
            editable.setSpan(oe, length, length, 17);
        }
        String value = attributes.getValue("", "style");
        if (value != null) {
            if (c == null) {
                c = Pattern.compile("(?:\\s+|\\A)text-align\\s*:\\s*(\\S*)\\b");
            }
            Matcher matcher = c.matcher(value);
            if (matcher.find()) {
                String strGroup = matcher.group(1);
                if (strGroup.equalsIgnoreCase("start")) {
                    Object ce = new Ce(Layout.Alignment.ALIGN_NORMAL);
                    int length2 = editable.length();
                    editable.setSpan(ce, length2, length2, 17);
                } else if (strGroup.equalsIgnoreCase("center")) {
                    Object ce2 = new Ce(Layout.Alignment.ALIGN_CENTER);
                    int length3 = editable.length();
                    editable.setSpan(ce2, length3, length3, 17);
                } else if (strGroup.equalsIgnoreCase("end")) {
                    Object ce3 = new Ce(Layout.Alignment.ALIGN_OPPOSITE);
                    int length4 = editable.length();
                    editable.setSpan(ce3, length4, length4, 17);
                }
            }
        }
    }

    @Override // org.xml.sax.ContentHandler
    public final void characters(char[] cArr, int i, int i2) {
        char cCharAt;
        StringBuilder sb = new StringBuilder();
        int i3 = 0;
        while (true) {
            SpannableStringBuilder spannableStringBuilder = this.a;
            if (i3 >= i2) {
                spannableStringBuilder.append((CharSequence) sb);
                return;
            }
            char c2 = cArr[i3 + i];
            if (c2 == ' ' || c2 == '\n') {
                int length = sb.length();
                if (length == 0) {
                    int length2 = spannableStringBuilder.length();
                    cCharAt = length2 == 0 ? '\n' : spannableStringBuilder.charAt(length2 - 1);
                } else {
                    cCharAt = sb.charAt(length - 1);
                }
                if (cCharAt != ' ' && cCharAt != '\n') {
                    sb.append(' ');
                }
            } else {
                sb.append(c2);
            }
            i3++;
        }
    }

    public final int e(String str) {
        try {
            return Color.parseColor(str);
        } catch (IllegalArgumentException unused) {
            return 0;
        }
    }

    @Override // org.xml.sax.ContentHandler
    public final void endElement(String str, String str2, String str3) {
        String str4;
        boolean zEqualsIgnoreCase = str2.equalsIgnoreCase("br");
        SpannableStringBuilder spannableStringBuilder = this.a;
        if (zEqualsIgnoreCase) {
            spannableStringBuilder.append('\n');
            return;
        }
        if (str2.equalsIgnoreCase("p")) {
            d(spannableStringBuilder);
            c(spannableStringBuilder);
            return;
        }
        if (str2.equalsIgnoreCase("ul")) {
            c(spannableStringBuilder);
            return;
        }
        if (str2.equalsIgnoreCase("li")) {
            He he = (He) f(spannableStringBuilder, He.class);
            if (he == null) {
                return;
            }
            boolean z = he.b;
            int i = he.a;
            BulletSpan bulletSpan = z ? new BulletSpan(i, 0) : new BulletSpan(i);
            d(spannableStringBuilder);
            c(spannableStringBuilder);
            b(spannableStringBuilder, He.class, bulletSpan);
            return;
        }
        if (str2.equalsIgnoreCase("div")) {
            c(spannableStringBuilder);
            return;
        }
        if (str2.equalsIgnoreCase("span")) {
            d(spannableStringBuilder);
            return;
        }
        if (str2.equalsIgnoreCase("strong")) {
            b(spannableStringBuilder, Ge.class, new StyleSpan(1));
            return;
        }
        if (str2.equalsIgnoreCase("b")) {
            b(spannableStringBuilder, Ge.class, new StyleSpan(1));
            return;
        }
        if (str2.equalsIgnoreCase("em")) {
            b(spannableStringBuilder, Me.class, new StyleSpan(2));
            return;
        }
        if (str2.equalsIgnoreCase("cite")) {
            b(spannableStringBuilder, Me.class, new StyleSpan(2));
            return;
        }
        if (str2.equalsIgnoreCase("dfn")) {
            b(spannableStringBuilder, Me.class, new StyleSpan(2));
            return;
        }
        if (str2.equalsIgnoreCase("i")) {
            b(spannableStringBuilder, Me.class, new StyleSpan(2));
            return;
        }
        if (str2.equalsIgnoreCase("big")) {
            b(spannableStringBuilder, Ee.class, new RelativeSizeSpan(1.25f));
            return;
        }
        if (str2.equalsIgnoreCase("small")) {
            b(spannableStringBuilder, Pe.class, new RelativeSizeSpan(0.8f));
            return;
        }
        if (str2.equalsIgnoreCase("font")) {
            Ie ie = (Ie) f(spannableStringBuilder, Ie.class);
            if (ie != null) {
                g(spannableStringBuilder, ie, new TypefaceSpan(ie.a));
            }
            Je je = (Je) f(spannableStringBuilder, Je.class);
            if (je != null) {
                g(spannableStringBuilder, je, new ForegroundColorSpan(je.a));
                return;
            }
            return;
        }
        if (str2.equalsIgnoreCase("blockquote")) {
            c(spannableStringBuilder);
            b(spannableStringBuilder, Fe.class, new QuoteSpan());
            return;
        }
        if (str2.equalsIgnoreCase("tt")) {
            b(spannableStringBuilder, Ne.class, new TypefaceSpan("monospace"));
            return;
        }
        if (str2.equalsIgnoreCase("a")) {
            Le le = (Le) f(spannableStringBuilder, Le.class);
            if (le == null || (str4 = le.a) == null) {
                return;
            }
            g(spannableStringBuilder, le, new URLSpan(str4));
            return;
        }
        if (str2.equalsIgnoreCase("u")) {
            b(spannableStringBuilder, Te.class, new UnderlineSpan());
            return;
        }
        if (str2.equalsIgnoreCase("del")) {
            b(spannableStringBuilder, Qe.class, new StrikethroughSpan());
            return;
        }
        if (str2.equalsIgnoreCase("s")) {
            b(spannableStringBuilder, Qe.class, new StrikethroughSpan());
            return;
        }
        if (str2.equalsIgnoreCase("strike")) {
            b(spannableStringBuilder, Qe.class, new StrikethroughSpan());
            return;
        }
        if (str2.equalsIgnoreCase("sup")) {
            b(spannableStringBuilder, Se.class, new SuperscriptSpan());
            return;
        }
        if (str2.equalsIgnoreCase("sub")) {
            b(spannableStringBuilder, Re.class, new SubscriptSpan());
            return;
        }
        if (str2.length() != 2 || Character.toLowerCase(str2.charAt(0)) != 'h' || str2.charAt(1) < '1' || str2.charAt(1) > '6') {
            return;
        }
        Ke ke = (Ke) f(spannableStringBuilder, Ke.class);
        if (ke != null) {
            g(spannableStringBuilder, ke, new RelativeSizeSpan(b[ke.a]), new StyleSpan(1));
        }
        c(spannableStringBuilder);
    }

    public final void j(SpannableStringBuilder spannableStringBuilder, Attributes attributes) {
        int iE;
        int iE2;
        String value = attributes.getValue("", "style");
        if (value != null) {
            if (d == null) {
                d = Pattern.compile("(?:\\s+|\\A)color\\s*:\\s*(\\S*)\\b");
            }
            Matcher matcher = d.matcher(value);
            if (matcher.find() && (iE2 = e(matcher.group(1))) != -1) {
                h(spannableStringBuilder, new Je(iE2 | (-16777216)));
            }
            if (e == null) {
                e = Pattern.compile("(?:\\s+|\\A)background(?:-color)?\\s*:\\s*(\\S*)\\b");
            }
            Matcher matcher2 = e.matcher(value);
            if (matcher2.find() && (iE = e(matcher2.group(1))) != -1) {
                De de = new De();
                de.a = iE | (-16777216);
                h(spannableStringBuilder, de);
            }
            if (f == null) {
                f = Pattern.compile("(?:\\s+|\\A)text-decoration\\s*:\\s*(\\S*)\\b");
            }
            Matcher matcher3 = f.matcher(value);
            if (matcher3.find() && matcher3.group(1).equalsIgnoreCase("line-through")) {
                h(spannableStringBuilder, new Qe());
            }
        }
    }

    @Override // org.xml.sax.ContentHandler
    public final void startElement(String str, String str2, String str3, Attributes attributes) {
        int iE;
        float fFloatValue;
        if (str2.equalsIgnoreCase("br")) {
            return;
        }
        boolean zEqualsIgnoreCase = str2.equalsIgnoreCase("p");
        SpannableStringBuilder spannableStringBuilder = this.a;
        if (zEqualsIgnoreCase) {
            i(spannableStringBuilder, attributes, 2);
            j(spannableStringBuilder, attributes);
            return;
        }
        if (str2.equalsIgnoreCase("ul")) {
            i(spannableStringBuilder, attributes, 2);
            return;
        }
        int i = -1;
        if (str2.equalsIgnoreCase("li")) {
            String value = attributes.getValue("", "gap");
            boolean z = !TextUtils.isEmpty(attributes.getValue("", "color"));
            if (TextUtils.isEmpty(value)) {
                i = 2;
            } else if (TextUtils.isEmpty(value)) {
                i = 0;
            } else {
                try {
                    fFloatValue = (value.endsWith("px") || value.endsWith("dp") || value.endsWith("sp")) ? Float.valueOf(value.substring(0, value.length() - 2)).floatValue() : Float.valueOf(value).floatValue();
                } catch (NumberFormatException unused) {
                    fFloatValue = 0.0f;
                }
                if (value.endsWith("px")) {
                    i = (int) fFloatValue;
                } else if (value.endsWith("dp") || value.endsWith("sp")) {
                    throw null;
                }
            }
            i(spannableStringBuilder, attributes, 2);
            He he = new He();
            he.a = i;
            he.b = z;
            int length = spannableStringBuilder.length();
            spannableStringBuilder.setSpan(he, length, length, 17);
            j(spannableStringBuilder, attributes);
            return;
        }
        if (str2.equalsIgnoreCase("div")) {
            i(spannableStringBuilder, attributes, 2);
            return;
        }
        if (str2.equalsIgnoreCase("span")) {
            j(spannableStringBuilder, attributes);
            return;
        }
        if (str2.equalsIgnoreCase("strong")) {
            h(spannableStringBuilder, new Ge());
            return;
        }
        if (str2.equalsIgnoreCase("b")) {
            h(spannableStringBuilder, new Ge());
            return;
        }
        if (str2.equalsIgnoreCase("em")) {
            h(spannableStringBuilder, new Me());
            return;
        }
        if (str2.equalsIgnoreCase("cite")) {
            h(spannableStringBuilder, new Me());
            return;
        }
        if (str2.equalsIgnoreCase("dfn")) {
            h(spannableStringBuilder, new Me());
            return;
        }
        if (str2.equalsIgnoreCase("i")) {
            h(spannableStringBuilder, new Me());
            return;
        }
        if (str2.equalsIgnoreCase("big")) {
            h(spannableStringBuilder, new Ee());
            return;
        }
        if (str2.equalsIgnoreCase("small")) {
            h(spannableStringBuilder, new Pe(0));
            return;
        }
        if (str2.equalsIgnoreCase("font")) {
            String value2 = attributes.getValue("", "color");
            String value3 = attributes.getValue("", "face");
            if (!TextUtils.isEmpty(value2) && (iE = e(value2)) != -1) {
                h(spannableStringBuilder, new Je(iE | (-16777216)));
            }
            if (TextUtils.isEmpty(value3)) {
                return;
            }
            Ie ie = new Ie();
            ie.a = value3;
            h(spannableStringBuilder, ie);
            return;
        }
        if (str2.equalsIgnoreCase("blockquote")) {
            i(spannableStringBuilder, attributes, 2);
            Object fe = new Fe();
            int length2 = spannableStringBuilder.length();
            spannableStringBuilder.setSpan(fe, length2, length2, 17);
            return;
        }
        if (str2.equalsIgnoreCase("tt")) {
            h(spannableStringBuilder, new Ne());
            return;
        }
        if (str2.equalsIgnoreCase("a")) {
            String value4 = attributes.getValue("", "href");
            Le le = new Le();
            le.a = value4;
            h(spannableStringBuilder, le);
            return;
        }
        if (str2.equalsIgnoreCase("u")) {
            h(spannableStringBuilder, new Te());
            return;
        }
        if (str2.equalsIgnoreCase("del")) {
            h(spannableStringBuilder, new Qe());
            return;
        }
        if (str2.equalsIgnoreCase("s")) {
            h(spannableStringBuilder, new Qe());
            return;
        }
        if (str2.equalsIgnoreCase("strike")) {
            h(spannableStringBuilder, new Qe());
            return;
        }
        if (str2.equalsIgnoreCase("sup")) {
            h(spannableStringBuilder, new Se());
            return;
        }
        if (str2.equalsIgnoreCase("sub")) {
            h(spannableStringBuilder, new Re());
            return;
        }
        if (str2.length() == 2 && Character.toLowerCase(str2.charAt(0)) == 'h' && str2.charAt(1) >= '1' && str2.charAt(1) <= '6') {
            int iCharAt = str2.charAt(1) - '1';
            i(spannableStringBuilder, attributes, 2);
            Ke ke = new Ke();
            ke.a = iCharAt;
            int length3 = spannableStringBuilder.length();
            spannableStringBuilder.setSpan(ke, length3, length3, 17);
            return;
        }
        if (str2.equalsIgnoreCase("img")) {
            String value5 = attributes.getValue("", "src");
            Drawable drawable = Resources.getSystem().getDrawable(R.drawable._0_resource_name_obfuscated_res_0x7f08012e);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            int length4 = spannableStringBuilder.length();
            spannableStringBuilder.append("￼");
            spannableStringBuilder.setSpan(new ImageSpan(drawable, value5), length4, spannableStringBuilder.length(), 33);
        }
    }

    @Override // org.xml.sax.ContentHandler
    public final void endDocument() {
    }

    @Override // org.xml.sax.ContentHandler
    public final void startDocument() {
    }

    @Override // org.xml.sax.ContentHandler
    public final void endPrefixMapping(String str) {
    }

    @Override // org.xml.sax.ContentHandler
    public final void setDocumentLocator(Locator locator) {
    }

    @Override // org.xml.sax.ContentHandler
    public final void skippedEntity(String str) {
    }

    @Override // org.xml.sax.ContentHandler
    public final void processingInstruction(String str, String str2) {
    }

    @Override // org.xml.sax.ContentHandler
    public final void startPrefixMapping(String str, String str2) {
    }

    @Override // org.xml.sax.ContentHandler
    public final void ignorableWhitespace(char[] cArr, int i, int i2) {
    }
}
