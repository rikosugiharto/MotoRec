package id.ac.sttgarut.motorec.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

import id.ac.sttgarut.motorec.R;
import id.ac.sttgarut.motorec.activity.MainActivity;
import id.ac.sttgarut.motorec.db.BengkelDAO;
import id.ac.sttgarut.motorec.db.SparePartDAO;
import id.ac.sttgarut.motorec.model.Bengkel;

public class BengkelCustomDialogFragment extends DialogFragment {

    //referensi form
    private EditText namaEtxt;
    private EditText alamatPakaiEtxt;
    private EditText tgllahirEtxt;
    private LinearLayout submitLayout;

    private Bengkel bengkel;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    BengkelDAO bengkelDAO;

    public static final String ARG_ITEM_ID = "emp_dialog_fragment";

    public interface DialogFragmentListener {
        void onFinishDialog();
    }

    public BengkelCustomDialogFragment() {
 
    }
 
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	bengkelDAO = new BengkelDAO(getActivity());
 
        Bundle bundle = this.getArguments();
        bengkel = bundle.getParcelable("selectedBengkel");
 
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
 
        View customDialogView = inflater.inflate(R.layout.bengkel_add, null);
        builder.setView(customDialogView);
 
        namaEtxt = (EditText) customDialogView.findViewById(R.id.ed_nama);
        alamatPakaiEtxt = (EditText) customDialogView.findViewById(R.id.ed_alamat);
        //tgllahirEtxt = (EditText) customDialogView.findViewById(R.id.edit_txt_tgllahir);
        submitLayout = (LinearLayout) customDialogView
                .findViewById(R.id.layout_submit);
        submitLayout.setVisibility(View.GONE);
 
        setValue();
 
        builder.setTitle(R.string.update_beng);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.update,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*try {
                        	bengkel.setTanggalLahir(formatter.parse(tgllahirEtxt.getText().toString()));
                        } catch (ParseException e) {
                            Toast.makeText(getActivity(),
                                    "Format data tidak valid!",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }*/
                        bengkel.setNama(namaEtxt.getText().toString());
                        bengkel.setAlamat(alamatPakaiEtxt.getText().toString());
                        long result = bengkelDAO.update(bengkel);
                        if (result > 0) {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.onFinishDialog();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Tidak dapat memperbarui bengkel",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
 
        AlertDialog alertDialog = builder.create();
 
        return alertDialog;
    }
 
    private void setValue() {
        if (bengkel != null) {
        	namaEtxt.setText(bengkel.getNama());
        	alamatPakaiEtxt.setText(bengkel.getAlamat() + "");
        	//tgllahirEtxt.setText(formatter.format(bengkel.getTanggalLahir()));
        }
    }
}