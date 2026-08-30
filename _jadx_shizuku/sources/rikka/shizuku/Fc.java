package rikka.shizuku;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentContainerView;

/* JADX INFO: loaded from: classes.dex */
public final class Fc implements LayoutInflater.Factory2 {
    public final Qc a;

    public Fc(Qc qc) {
        this.a = qc;
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        boolean zIsAssignableFrom;
        Xc xcG;
        boolean zEquals = FragmentContainerView.class.getName().equals(str);
        Qc qc = this.a;
        if (zEquals) {
            return new FragmentContainerView(context, attributeSet, qc);
        }
        if ("fragment".equals(str)) {
            String attributeValue = attributeSet.getAttributeValue(null, "class");
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Ik.a);
            if (attributeValue == null) {
                attributeValue = typedArrayObtainStyledAttributes.getString(0);
            }
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(1, -1);
            String string = typedArrayObtainStyledAttributes.getString(2);
            typedArrayObtainStyledAttributes.recycle();
            if (attributeValue != null) {
                try {
                    zIsAssignableFrom = Ac.class.isAssignableFrom(Kc.b(context.getClassLoader(), attributeValue));
                } catch (ClassNotFoundException unused) {
                    zIsAssignableFrom = false;
                }
                if (zIsAssignableFrom) {
                    int id = view != null ? view.getId() : 0;
                    if (id == -1 && resourceId == -1 && string == null) {
                        throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + attributeValue);
                    }
                    Ac acB = resourceId != -1 ? qc.B(resourceId) : null;
                    if (acB == null && string != null) {
                        acB = qc.C(string);
                    }
                    if (acB == null && id != -1) {
                        acB = qc.B(id);
                    }
                    if (acB == null) {
                        Kc kcG = qc.G();
                        context.getClassLoader();
                        acB = kcG.a(attributeValue);
                        acB.n = true;
                        acB.x = resourceId != 0 ? resourceId : id;
                        acB.y = id;
                        acB.z = string;
                        acB.o = true;
                        acB.t = qc;
                        Cc cc = qc.w;
                        acB.u = cc;
                        AbstractActivityC0051b2 abstractActivityC0051b2 = cc.j;
                        acB.E = true;
                        if ((cc != null ? cc.i : null) != null) {
                            acB.E = true;
                        }
                        xcG = qc.a(acB);
                        if (Qc.J(2)) {
                            Log.v("FragmentManager", "Fragment " + acB + " has been inflated via the <fragment> tag: id=0x" + Integer.toHexString(resourceId));
                        }
                    } else {
                        if (acB.o) {
                            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + attributeValue);
                        }
                        acB.o = true;
                        acB.t = qc;
                        Cc cc2 = qc.w;
                        acB.u = cc2;
                        AbstractActivityC0051b2 abstractActivityC0051b3 = cc2.j;
                        acB.E = true;
                        if ((cc2 != null ? cc2.i : null) != null) {
                            acB.E = true;
                        }
                        xcG = qc.g(acB);
                        if (Qc.J(2)) {
                            Log.v("FragmentManager", "Retained Fragment " + acB + " has been re-attached via the <fragment> tag: id=0x" + Integer.toHexString(resourceId));
                        }
                    }
                    ViewGroup viewGroup = (ViewGroup) view;
                    Zc zc = AbstractC0031ad.a;
                    AbstractC0031ad.b(new Vc(acB, "Attempting to use <fragment> tag to add fragment " + acB + " to container " + viewGroup));
                    AbstractC0031ad.a(acB).getClass();
                    acB.F = viewGroup;
                    xcG.k();
                    xcG.j();
                    View view2 = acB.G;
                    if (view2 == null) {
                        throw new IllegalStateException(AbstractC0535qo.d("Fragment ", attributeValue, " did not create a view."));
                    }
                    if (resourceId != 0) {
                        view2.setId(resourceId);
                    }
                    if (acB.G.getTag() == null) {
                        acB.G.setTag(string);
                    }
                    acB.G.addOnAttachStateChangeListener(new Ec(this, xcG));
                    return acB.G;
                }
            }
        }
        return null;
    }
}
