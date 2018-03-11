package id.ac.sttgarut.motorec.fragment;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.sttgarut.motorec.R;
import id.ac.sttgarut.motorec.db.SparePartDAO;
import id.ac.sttgarut.motorec.model.SparePart;

public class SparePartAdd extends Fragment implements OnClickListener {

    //referensi form
    private EditText namaEtxt;
    private EditText batasPakaiEtxt;
    private EditText tgllahirEtxt;
    private Button addButton;
    private Button resetButton;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;

    SparePart sparePart = null;
    private SparePartDAO sparePartDAO;
    private AddTask task;

    public static final String ARG_ITEM_ID = "add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sparePartDAO = new SparePartDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sparepart_add, container,
                false);

        findViewsById(rootView);

        setListeners();

        if (savedInstanceState != null) {
            dateCalendar = Calendar.getInstance();
            if (savedInstanceState.getLong("dateCalendar") != 0)
                dateCalendar.setTime(new Date(savedInstanceState
                        .getLong("dateCalendar")));
        }

        return rootView;
    }

    private void setListeners() {
        /*tgllahirEtxt.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(),
                new OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateCalendar = Calendar.getInstance();
                        dateCalendar.set(year, monthOfYear, dayOfMonth);
                        tgllahirEtxt.setText(formatter.format(dateCalendar
                                .getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));*/

        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    protected void resetAllFields() {
        namaEtxt.setText("");
        batasPakaiEtxt.setText("");
        tgllahirEtxt.setText("");
    }

    private void setSparePart() {
        sparePart = new SparePart();
        sparePart.setNama(namaEtxt.getText().toString());
        sparePart.setBataspakai(Integer.parseInt(batasPakaiEtxt.getText()
                .toString()));
        /*if (dateCalendar != null)
            bengkel.setTanggalLahir(dateCalendar.getTime());*/
    }

    @Override
    public void onResume() {
        /*getActivity().setTitle(R.string.add_kar);
        getActivity().getActionBar().setTitle(R.string.add_kar);*/
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (dateCalendar != null)
            outState.putLong("dateCalendar", dateCalendar.getTime().getTime());
    }

    private void findViewsById(View rootView) {
        namaEtxt = (EditText) rootView.findViewById(R.id.ed_nama);
        batasPakaiEtxt = (EditText) rootView.findViewById(R.id.ed_bataspakai);
        /*tgllahirEtxt = (EditText) rootView.findViewById(R.id.edit_txt_tgllahir);
        tgllahirEtxt.setInputType(InputType.TYPE_NULL);*/

        addButton = (Button) rootView.findViewById(R.id.button_add_sparepart);
        resetButton = (Button) rootView.findViewById(R.id.button_reset);
    }

    @Override
    public void onClick(View view) {
        if (view == tgllahirEtxt) {
            datePickerDialog.show();
        } else if (view == addButton) {
            setSparePart();

            task = new AddTask(getActivity());
            task.execute((Void) null);
        } else if (view == resetButton) {
            resetAllFields();
        }
    }

    public class AddTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = sparePartDAO.save(sparePart);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakRef.get(), "Simpan SparePart",
                            Toast.LENGTH_LONG).show();
            }
        }
    }
}