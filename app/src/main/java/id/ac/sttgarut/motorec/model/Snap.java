package id.ac.sttgarut.motorec.model;

/**
 * Created by Programming on 3/11/2018.
 */

public abstract class Snap {

    protected int mGravity;
    protected String mText;

    public String getText(){
        return mText;
    }

    public int getGravity(){
        return mGravity;
    }
}
