package id.ac.sttgarut.motorec.db;

/**
 * Created by PROGRAMMING on 1/13/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "motorecdb";
    private static final int DATABASE_VERSION = 1;

    public static final String TABEL_SPAREPART = "sparepart";
    public static final String TABEL_BENGKEL = "bengkel";
    public static final String TABEL_JENIS_BENGKEL = "jenisbengkel";

    public static final String ID  			 = "id";
    public static final String NAMA 		 = "nama";
    public static final String ALAMAT 		 = "alamat";
    public static final String BATAS_PAKAI 		 = "bataspakai";
    public static final String JENIS 		 = "jenisbengkelid";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LONG = "long";

    /*public static final String CREATE_TABEL_KARYAWAN = "CREATE TABLE "
            + TABEL_KARYAWAN + "(" + ID + " INTEGER PRIMARY KEY, "
            + NAMA + " TEXT, " + GAJI + " DOUBLE, "
            + TANGGAL_LAHIR + " DATE, " + BAGIAN + " INT, " + "FOREIGN KEY(" + BAGIAN + ") REFERENCES "
            + TABEL_BAGIAN + "(id) " + ")";*/

    public static final String CREATE_TABEL_SPAREPART = "CREATE TABLE "
            + TABEL_SPAREPART+ "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAMA + " TEXT, "
            + BATAS_PAKAI + " INTEGER " + ")";

    public static final String CREATE_TABEL_BENGKEL = "CREATE TABLE "
            + TABEL_BENGKEL + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAMA+ " VARCHAR(255), "
            + ALAMAT+ " VARCHAR(255), "
            + COLUMN_LONG+ " VARCHAR(50));"
            + COLUMN_LAT+ " VARCHAR(50), "
            + JENIS + " INT, " + "FOREIGN KEY(" + JENIS + ") REFERENCES "
            + TABEL_JENIS_BENGKEL + "(id) " + ")";

    public static final String CREATE_TABEL_JENIS_BENGKEL = "CREATE TABLE "
            + TABEL_JENIS_BENGKEL+ "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAMA + " TEXT " + ")";

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABEL_SPAREPART);
        db.execSQL(CREATE_TABEL_JENIS_BENGKEL);
        db.execSQL(CREATE_TABEL_BENGKEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
