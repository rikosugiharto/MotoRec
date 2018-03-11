package id.ac.sttgarut.motorec.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PROGRAMMING on 1/13/2018.
 */

public class SparePart implements Parcelable {

    private int id;
    private String nama;
    private int bataspakai;

    public SparePart() {
        super();
    }

    private SparePart(Parcel in) {
        super();
        this.id = in.readInt();
        this.nama = in.readString();
        this.bataspakai = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getBataspakai() {
        return bataspakai;
    }

    public void setBataspakai(int bataspakai) {
        this.bataspakai = bataspakai;
    }

    @Override
    public String toString() {
        return "Sparepart [id=" + id + ", nama=" + nama + ", batas_pakai="
                + bataspakai + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SparePart other = (SparePart) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getNama());
        parcel.writeInt(getBataspakai());
    }

    public static final Creator<SparePart> CREATOR = new Creator<SparePart>() {
        public SparePart createFromParcel(Parcel in) {
            return new SparePart(in);
        }

        public SparePart[] newArray(int size) {
            return new SparePart[size];
        }
    };

}
