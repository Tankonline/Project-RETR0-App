package rikka.shizuku;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.SwitchPreference;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: rikka.shizuku.mk, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0407mk {
    public static final Class[] e = {Context.class, AttributeSet.class};
    public static final HashMap f = new HashMap();
    public final Context a;
    public final C0438nk c;
    public final Object[] b = new Object[2];
    public final String[] d = {Preference.class.getPackage().getName() + ".", SwitchPreference.class.getPackage().getName() + "."};

    public C0407mk(Context context, C0438nk c0438nk) {
        this.a = context;
        this.c = c0438nk;
    }

    public final Preference a(String str, String[] strArr, AttributeSet attributeSet) throws ClassNotFoundException {
        Class<?> cls;
        HashMap map = f;
        Constructor<?> constructor = (Constructor) map.get(str);
        if (constructor == null) {
            try {
                try {
                    ClassLoader classLoader = this.a.getClassLoader();
                    if (strArr == null || strArr.length == 0) {
                        cls = Class.forName(str, false, classLoader);
                    } else {
                        cls = null;
                        ClassNotFoundException e2 = null;
                        for (String str2 : strArr) {
                            try {
                                cls = Class.forName(str2 + str, false, classLoader);
                                break;
                            } catch (ClassNotFoundException e3) {
                                e2 = e3;
                            }
                        }
                        if (cls == null) {
                            if (e2 != null) {
                                throw e2;
                            }
                            throw new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
                        }
                    }
                    constructor = cls.getConstructor(e);
                    constructor.setAccessible(true);
                    map.put(str, constructor);
                } catch (Exception e4) {
                    InflateException inflateException = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
                    inflateException.initCause(e4);
                    throw inflateException;
                }
            } catch (ClassNotFoundException e5) {
                throw e5;
            }
        }
        Object[] objArr = this.b;
        objArr[1] = attributeSet;
        return (Preference) constructor.newInstance(objArr);
    }

    public final Preference b(String str, AttributeSet attributeSet) {
        try {
            return -1 == str.indexOf(46) ? a(str, this.d, attributeSet) : a(str, null, attributeSet);
        } catch (InflateException e2) {
            throw e2;
        } catch (ClassNotFoundException e3) {
            InflateException inflateException = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class (not found)" + str);
            inflateException.initCause(e3);
            throw inflateException;
        } catch (Exception e4) {
            InflateException inflateException2 = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
            inflateException2.initCause(e4);
            throw inflateException2;
        }
    }

    public final PreferenceGroup c(XmlResourceParser xmlResourceParser) {
        int next;
        PreferenceGroup preferenceGroup;
        synchronized (this.b) {
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParser);
            this.b[0] = this.a;
            do {
                try {
                    try {
                        next = xmlResourceParser.next();
                        if (next == 2) {
                            break;
                        }
                    } catch (InflateException e2) {
                        throw e2;
                    }
                } catch (IOException e3) {
                    InflateException inflateException = new InflateException(xmlResourceParser.getPositionDescription() + ": " + e3.getMessage());
                    inflateException.initCause(e3);
                    throw inflateException;
                } catch (XmlPullParserException e4) {
                    InflateException inflateException2 = new InflateException(e4.getMessage());
                    inflateException2.initCause(e4);
                    throw inflateException2;
                }
            } while (next != 1);
            if (next != 2) {
                throw new InflateException(xmlResourceParser.getPositionDescription() + ": No start tag found!");
            }
            preferenceGroup = (PreferenceGroup) b(xmlResourceParser.getName(), attributeSetAsAttributeSet);
            preferenceGroup.l(this.c);
            d(xmlResourceParser, preferenceGroup, attributeSetAsAttributeSet);
        }
        return preferenceGroup;
    }

    public final void d(XmlResourceParser xmlResourceParser, Preference preference, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        long jLongValue;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if ((next == 3 && xmlResourceParser.getDepth() <= depth) || next == 1) {
                return;
            }
            if (next == 2) {
                String name = xmlResourceParser.getName();
                if ("intent".equals(name)) {
                    try {
                        preference.m = Intent.parseIntent(this.a.getResources(), xmlResourceParser, attributeSet);
                    } catch (IOException e2) {
                        XmlPullParserException xmlPullParserException = new XmlPullParserException("Error parsing preference");
                        xmlPullParserException.initCause(e2);
                        throw xmlPullParserException;
                    }
                } else if ("extra".equals(name)) {
                    Resources resources = this.a.getResources();
                    if (preference.o == null) {
                        preference.o = new Bundle();
                    }
                    resources.parseBundleExtra("extra", attributeSet, preference.o);
                    try {
                        int depth2 = xmlResourceParser.getDepth();
                        while (true) {
                            int next2 = xmlResourceParser.next();
                            if (next2 == 1 || (next2 == 3 && xmlResourceParser.getDepth() <= depth2)) {
                                break;
                            }
                        }
                    } catch (IOException e3) {
                        XmlPullParserException xmlPullParserException2 = new XmlPullParserException("Error parsing preference");
                        xmlPullParserException2.initCause(e3);
                        throw xmlPullParserException2;
                    }
                } else {
                    Preference preferenceB = b(name, attributeSet);
                    PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
                    if (!preferenceGroup.O.contains(preferenceB)) {
                        if (preferenceB.l != null) {
                            PreferenceGroup preferenceGroup2 = preferenceGroup;
                            while (true) {
                                PreferenceGroup preferenceGroup3 = preferenceGroup2.I;
                                if (preferenceGroup3 == null) {
                                    break;
                                } else {
                                    preferenceGroup2 = preferenceGroup3;
                                }
                            }
                            String str = preferenceB.l;
                            if (preferenceGroup2.B(str) != null) {
                                Log.e("PreferenceGroup", "Found duplicated key: \"" + str + "\". This can cause unintended behaviour, please use unique keys for every preference.");
                            }
                        }
                        int i = preferenceB.g;
                        if (i == Integer.MAX_VALUE) {
                            if (preferenceGroup.P) {
                                int i2 = preferenceGroup.Q;
                                preferenceGroup.Q = i2 + 1;
                                if (i2 != i) {
                                    preferenceB.g = i2;
                                    C0376lk c0376lk = preferenceB.G;
                                    if (c0376lk != null) {
                                        Handler handler = c0376lk.h;
                                        RunnableC0607t4 runnableC0607t4 = c0376lk.i;
                                        handler.removeCallbacks(runnableC0607t4);
                                        handler.post(runnableC0607t4);
                                    }
                                }
                            }
                            if (preferenceB instanceof PreferenceGroup) {
                                ((PreferenceGroup) preferenceB).P = preferenceGroup.P;
                            }
                        }
                        int iBinarySearch = Collections.binarySearch(preferenceGroup.O, preferenceB);
                        if (iBinarySearch < 0) {
                            iBinarySearch = (iBinarySearch * (-1)) - 1;
                        }
                        boolean z = preferenceGroup.z();
                        if (preferenceB.v == z) {
                            preferenceB.v = !z;
                            preferenceB.j(preferenceB.z());
                            preferenceB.i();
                        }
                        synchronized (preferenceGroup) {
                            preferenceGroup.O.add(iBinarySearch, preferenceB);
                        }
                        C0438nk c0438nk = preferenceGroup.b;
                        String str2 = preferenceB.l;
                        if (str2 == null || !preferenceGroup.N.containsKey(str2)) {
                            synchronized (c0438nk) {
                                jLongValue = c0438nk.b;
                                c0438nk.b = 1 + jLongValue;
                            }
                        } else {
                            jLongValue = ((Long) preferenceGroup.N.get(str2)).longValue();
                            preferenceGroup.N.remove(str2);
                        }
                        preferenceB.c = jLongValue;
                        preferenceB.d = true;
                        try {
                            preferenceB.l(c0438nk);
                            preferenceB.d = false;
                            if (preferenceB.I != null) {
                                throw new IllegalStateException("This preference already has a parent. You must remove the existing parent before assigning a new one.");
                            }
                            preferenceB.I = preferenceGroup;
                            if (preferenceGroup.R) {
                                preferenceB.k();
                            }
                            C0376lk c0376lk2 = preferenceGroup.G;
                            if (c0376lk2 != null) {
                                Handler handler2 = c0376lk2.h;
                                RunnableC0607t4 runnableC0607t5 = c0376lk2.i;
                                handler2.removeCallbacks(runnableC0607t5);
                                handler2.post(runnableC0607t5);
                            }
                        } catch (Throwable th) {
                            preferenceB.d = false;
                            throw th;
                        }
                    }
                    d(xmlResourceParser, preferenceB, attributeSet);
                }
            }
        }
    }
}
