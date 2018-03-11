package id.ac.sttgarut.motorec.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PROGRAMMING on 1/13/2018.
 */

public class Bengkel implements Parcelable {

    private int id;
    private String nama;
    private String alamat;

    public Bengkel() {
        super();
    }

    private Bengkel(Parcel in) {
        super();
        this.id = in.readInt();
        this.nama = in.readString();
        this.alamat = in.readString();
        //this.alamat = in.readInt();
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public String toString() {
        return "Bengkel [id=" + id + ", nama=" + nama + ", alamat="
                + alamat + "]";
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
        Bengkel other = (Bengkel) obj;
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
        parcel.writeString(getAlamat());
        //parcel.writeInt(getAlamat());
    }

    public static final Creator<Bengkel> CREATOR = new Creator<Bengkel>() {
        public Bengkel createFromParcel(Parcel in) {
            return new Bengkel(in);
        }

        public Bengkel[] newArray(int size) {
            return new Bengkel[size];
        }
    };

}
