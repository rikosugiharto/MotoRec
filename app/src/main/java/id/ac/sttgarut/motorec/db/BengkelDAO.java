package id.ac.sttgarut.motorec.db;

/**
 * Created by PROGRAMMING on 1/13/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import id.ac.sttgarut.motorec.model.Bengkel;
import id.ac.sttgarut.motorec.model.SparePart;

public class BengkelDAO extends MotoRecDBDAO {
    public static final String TABLE_BENGKEL = "bengkel bkl";
    public static final String BENGKEL_ID = "bkl.id";
    public static final String BENGKEL_NAMA = "bkl.nama";
    public static final String BENGKEL_ALAMAT = "bkl.alamat";

    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID
            + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public BengkelDAO(Context context) {
        super(context);
    }

    public long save(Bengkel bengkel) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAMA, bengkel.getNama());
        //Log.d("tgllahir", karyawan.getTanggalLahir().getTime() + "");
        //values.put(DataBaseHelper.TANGGAL_LAHIR, formatter.format(karyawan.getTanggalLahir()));
        values.put(DataBaseHelper.ALAMAT, bengkel.getAlamat());
        //values.put(DataBaseHelper.BAGIAN, karyawan.getBagian().getId());

        return database.insert(DataBaseHelper.TABEL_BENGKEL, null, values);
    }

    public long update(Bengkel bengkel) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAMA, bengkel.getNama());
        //values.put(DataBaseHelper.TANGGAL_LAHIR, formatter.format(karyawan.getTanggalLahir()));
        values.put(DataBaseHelper.BATAS_PAKAI, bengkel.getAlamat());
        //values.put(DataBaseHelper.BAGIAN, karyawan.getBagian().getId());

        long result = database.update(DataBaseHelper.TABEL_SPAREPART, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(bengkel.getId()) });
        Log.d("Hasil Update :", "=" + result);
        return result;

    }

    public int delete(Bengkel bengkel) {
        return database.delete(DataBaseHelper.TABEL_BENGKEL, WHERE_ID_EQUALS,
                new String[] { bengkel.getId() + "" });
    }

    public ArrayList<Bengkel> getBengkel() {
        ArrayList<Bengkel> bengkels = new ArrayList<Bengkel>();
        String query = "SELECT " + BENGKEL_ID + ","
                + BENGKEL_NAMA + "," + BENGKEL_ALAMAT
                + " FROM "
                + TABLE_BENGKEL;

        // Building query using INNER JOIN keyword
		/*String query = "SELECT " + EMPLOYEE_ID_WITH_PREFIX + ","
		+ EMPLOYEE_NAME_WITH_PREFIX + "," + DataBaseHelper.EMPLOYEE_DOB
		+ "," + DataBaseHelper.EMPLOYEE_SALARY + ","
		+ DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + ","
		+ DEPT_NAME_WITH_PREFIX + " FROM "
		+ DataBaseHelper.EMPLOYEE_TABLE + " emp INNER JOIN "
		+ DataBaseHelper.DEPARTMENT_TABLE + " dept ON emp."
		+ DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + " = dept."
		+ DataBaseHelper.ID_COLUMN;*/

        Log.d("query", query);
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Bengkel bengkel = new Bengkel();
            bengkel.setId(cursor.getInt(0));
            bengkel.setNama(cursor.getString(1));
            /*try {
                karyawan.setTanggalLahir(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                karyawan.setTanggalLahir(null);
            }*/
            bengkel.setAlamat(cursor.getString(2));
            //bengkel.setAlamat(cursor.getInt(2));

            /*Bagian bagian = new Bagian();
            bagian.setId(cursor.getInt(4));
            bagian.setNama(cursor.getString(5));

            karyawan.setBagian(bagian);*/

            bengkels.add(bengkel);
        }
        return bengkels;
    }

	//Mengambil satu record karyawan dengan id yang diberikan
	public SparePart getBengkel(long id) {
		SparePart sparePart = null;

		String sql = "SELECT * FROM " + DataBaseHelper.TABEL_SPAREPART
				+ " WHERE " + DataBaseHelper.ID + " = ?";

		Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

		if (cursor.moveToNext()) {
			sparePart = new SparePart();
			sparePart.setId(cursor.getInt(0));
			sparePart.setNama(cursor.getString(1));
			/*try {
				sparePart.setTanggalLahir(formatter.parse(cursor.getString(2)));
			} catch (ParseException e) {
				sparePart.setTanggalLahir(null);
			}*/
			sparePart.setBataspakai(cursor.getInt(2));
		}
		return sparePart;
	}
}
