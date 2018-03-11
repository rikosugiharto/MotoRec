package id.ac.sttgarut.motorec.model;


import java.util.List;

public class SnapBengkel extends Snap {

    private List<Bengkel> mList;

    public SnapBengkel(int gravity, String text, List<Bengkel> list) {
        mGravity = gravity;
        mText = text;
        mList = list;
    }

    public List<Bengkel> getApps(){
        return mList;
    }

}
