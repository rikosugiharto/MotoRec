package id.ac.sttgarut.motorec.db;

/**
 * Created by PROGRAMMING on 1/13/2018.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Space;

import id.ac.sttgarut.motorec.model.SparePart;

public class SparePartDAO extends MotoRecDBDAO {
    public static final String TABLE_SPAREPART = "sparepart spt";
    public static final String SPAREPART_ID = "spt.id";
    public static final String SPAREPART_NAMA = "spt.nama";
    public static final String SPAREPART_BATAS_PAKAI = "spt.bataspakai";

    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID
            + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public SparePartDAO(Context context) {
        super(context);
    }

    public long save(SparePart sparepart) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAMA, sparepart.getNama());
        //Log.d("tgllahir", karyawan.getTanggalLahir().getTime() + "");
        //values.put(DataBaseHelper.TANGGAL_LAHIR, formatter.format(karyawan.getTanggalLahir()));
        values.put(DataBaseHelper.BATAS_PAKAI, sparepart.getBataspakai());
        //values.put(DataBaseHelper.BAGIAN, karyawan.getBagian().getId());

        return database.insert(DataBaseHelper.TABEL_SPAREPART, null, values);
    }

    public long update(SparePart sparepart) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NAMA, sparepart.getNama());
        //values.put(DataBaseHelper.TANGGAL_LAHIR, formatter.format(karyawan.getTanggalLahir()));
        values.put(DataBaseHelper.BATAS_PAKAI, sparepart.getBataspakai());
        //values.put(DataBaseHelper.BAGIAN, karyawan.getBagian().getId());

        long result = database.update(DataBaseHelper.TABEL_SPAREPART, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(sparepart.getId()) });
        Log.d("Hasil Update :", "=" + result);
        return result;

    }

    public int delete(SparePart karyawan) {
        return database.delete(DataBaseHelper.TABEL_SPAREPART, WHERE_ID_EQUALS,
                new String[] { karyawan.getId() + "" });
    }

    public ArrayList<SparePart> getSparePart() {
        ArrayList<SparePart> karyawans = new ArrayList<SparePart>();
        String query = "SELECT " + SPAREPART_ID + ","
                + SPAREPART_NAMA + "," + SPAREPART_BATAS_PAKAI
                + " FROM "
                + TABLE_SPAREPART;

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
            SparePart sparepart = new SparePart();
            sparepart.setId(cursor.getInt(0));
            sparepart.setNama(cursor.getString(1));
            /*try {
                karyawan.setTanggalLahir(formatter.parse(cursor.getString(2)));
            } catch (ParseException e) {
                karyawan.setTanggalLahir(null);
            }*/
            sparepart.setBataspakai(cursor.getInt(2));

            /*Bagian bagian = new Bagian();
            bagian.setId(cursor.getInt(4));
            bagian.setNama(cursor.getString(5));

            karyawan.setBagian(bagian);*/

            karyawans.add(sparepart);
        }
        return karyawans;
    }

	//Mengambil satu record karyawan dengan id yang diberikan
	public SparePart getSparePart(long id) {
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
