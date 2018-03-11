package id.ac.sttgarut.motorec.model;


import java.util.List;

public class SnapSparePart extends Snap {

    private List<SparePart> mList;

    public SnapSparePart(int gravity, String text, List<SparePart> list) {
        mGravity = gravity;
        mText = text;
        mList = list;
    }

    public List<SparePart> getApps(){
        return mList;
    }

}
