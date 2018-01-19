package org.scilab.forge.jlatexmath;


/**
 * Created by shihaojun on 17-8-30.
 */

public class EqualFillFactory {
    private static final Atom leftUp = SymbolAtom.get("leftharpoonup");
    private static final Atom leftDown = SymbolAtom.get("leftharpoondown");
    private static final Atom rightUp = SymbolAtom.get("rightharpoonup");
    private static final Atom rightDown = SymbolAtom.get("rightharpoondown");

    public static final int EQUAL = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    public static Box createFillBox(int tag, float width, TeXEnvironment env) {
        if (width < 0) {
            return new StrutBox(0, 0, 0, 0);
        }
        Box minus = new SmashedAtom(XLeftRightArrowFactory.MINUS, "").createBox(env);
        Box kern = new SpaceAtom(TeXConstants.UNIT_MU, -4f, 0, 0)
                .createBox(env);
        float swidth = minus.getWidth();
        width *= swidth;
        float mwidth = swidth + kern.getWidth();
        swidth += kern.getWidth();
        HorizontalBox hbox1 = new HorizontalBox();
        HorizontalBox hbox2 = new HorizontalBox();
        float w = 0;
        for (; w < width - swidth - mwidth; w += mwidth) {

            hbox1.add(minus);
            hbox1.add(kern);

            hbox2.add(minus);
            hbox2.add(kern);
        }
        float sf = (width - swidth - w) / minus.getWidth();
        hbox1.add(new SpaceAtom(TeXConstants.UNIT_MU, -2f * sf, 0, 0)
                .createBox(env));
        hbox1.add(new ScaleAtom(XLeftRightArrowFactory.MINUS, sf, 1).createBox(env));

        hbox2.add(new SpaceAtom(TeXConstants.UNIT_MU, -2f * sf, 0, 0)
                .createBox(env));
        hbox2.add(new ScaleAtom(XLeftRightArrowFactory.MINUS, sf, 1).createBox(env));

        Box arr1 = leftUp.createBox(env);
        Box arr2 = rightDown.createBox(env);
        Box arr3 = rightUp.createBox(env);
        Box arr4 = leftDown.createBox(env);

        switch (tag) {
            case EQUAL:
                hbox1.add(kern);
                hbox1.add(minus);
                hbox2.add(kern);
                hbox2.add(minus);
                break;
            case LEFT:
                hbox1.add(0, new SpaceAtom(TeXConstants.UNIT_MU, -3.5f, 0, 0)
                        .createBox(env));
                hbox1.add(0, arr1);
                hbox2.add(new SpaceAtom(TeXConstants.UNIT_MU, -2f * sf - 2f, 0, 0)
                        .createBox(env));
                hbox2.add(arr2);
                break;
            case RIGHT:
                hbox1.add(new SpaceAtom(TeXConstants.UNIT_MU, -2f * sf - 2f, 0, 0)
                        .createBox(env));
                hbox1.add(arr3);
                hbox2.add(0, new SpaceAtom(TeXConstants.UNIT_MU, -3.5f, 0, 0)
                        .createBox(env));
                hbox2.add(0, arr4);
                break;
            default:
                break;
        }
        hbox1.setDepth(arr1.getDepth()/2);
        hbox1.setHeight(arr1.getHeight());
        hbox2.setDepth(arr1.getDepth()/2);
        hbox2.setHeight(arr1.getHeight());
        VerticalBox vbox = new VerticalBox();
        vbox.add(hbox1);
        vbox.add(hbox2);
        vbox.setHeight(arr1.getHeight()+arr2.getHeight()+arr2.getDepth());
        vbox.setDepth(arr1.getDepth()*4);
        return vbox;
    }

    public static Box createEqualFill(float width, TeXEnvironment env) {
        if (width < 0) {
            return new StrutBox(0, 0, 0, 0);
        }
        Box equalBox = getEquealTex(width).createBox(env);
        return equalBox;
    }

    private static Atom getEquealTex(float width) {
        StringBuffer buffer = new StringBuffer("\\substack{");
        int length = (int) (width + 0.5);
        buffer.append('=');
        length--;
        while (length > 0) {
            buffer.append("\\!");
            buffer.append('=');
            length--;
        }
        buffer.append('}');
        return new TeXFormula(new String(buffer)).root;
    }

}
