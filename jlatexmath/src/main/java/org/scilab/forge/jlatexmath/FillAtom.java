package org.scilab.forge.jlatexmath;

/**
 * Created by shihaojun on 17-8-30.
 */

public class FillAtom extends Atom {

    private float width;
    private String tag;

    public FillAtom(float width, String tag) {

        this.width = width;
        this.tag = tag;
    }

    @Override
    public Box createBox(TeXEnvironment env) {
        if (tag.equals("\\leftarrowfill")) {
            return XLeftRightArrowFactory.createFillBox(true, env, width);
        } else if (tag.equals("\\rightarrowfill")) {
            return XLeftRightArrowFactory.createFillBox(false, env, width);
        } else if (tag.equals("\\equalfill")) {
            return EqualFillFactory.createFillBox(EqualFillFactory.EQUAL, width, env);
        } else if (tag.equals("\\reversiblefill")) {
            return EqualFillFactory.createFillBox(EqualFillFactory.LEFT, width, env);
        }
        return new StrutBox(0, 0, 0, 0);
    }

}
