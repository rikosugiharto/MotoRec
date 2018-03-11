package id.ac.sttgarut.motorec.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
 
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
 
import id.ac.sttgarut.motorec.activity.MainActivity;
import id.ac.sttgarut.motorec.R;
import id.ac.sttgarut.motorec.db.SparePartDAO;
import id.ac.sttgarut.motorec.model.SparePart;

public class CustomDialogFragment extends DialogFragment {

    //referensi form
    private EditText namaEtxt;
    private EditText batasPakaiEtxt;
    private EditText tgllahirEtxt;
    private LinearLayout submitLayout;
 
    private SparePart sparePart;
 
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);
    
    SparePartDAO sparePartDAO;
    
    public static final String ARG_ITEM_ID = "emp_dialog_fragment";
 
    public interface DialogFragmentListener {
        void onFinishDialog();
    }
 
    public CustomDialogFragment() {
 
    }
 
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	sparePartDAO = new SparePartDAO(getActivity());
 
        Bundle bundle = this.getArguments();
        sparePart = bundle.getParcelable("selectedSparePart");
 
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
 
        View customDialogView = inflater.inflate(R.layout.sparepart_add, null);
        builder.setView(customDialogView);
 
        namaEtxt = (EditText) customDialogView.findViewById(R.id.ed_nama);
        batasPakaiEtxt = (EditText) customDialogView.findViewById(R.id.ed_bataspakai);
        //tgllahirEtxt = (EditText) customDialogView.findViewById(R.id.edit_txt_tgllahir);
        submitLayout = (LinearLayout) customDialogView
                .findViewById(R.id.layout_submit);
        submitLayout.setVisibility(View.GONE);
 
        setValue();
 
        builder.setTitle(R.string.update_spar);
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
                        sparePart.setNama(namaEtxt.getText().toString());
                        sparePart.setBataspakai(Integer.parseInt(batasPakaiEtxt
                                .getText().toString()));
                        long result = sparePartDAO.update(sparePart);
                        if (result > 0) {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.onFinishDialog();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Unable to update employee",
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
        if (sparePart != null) {
        	namaEtxt.setText(sparePart.getNama());
        	batasPakaiEtxt.setText(sparePart.getBataspakai() + "");
        	//tgllahirEtxt.setText(formatter.format(bengkel.getTanggalLahir()));
        }
    }
}