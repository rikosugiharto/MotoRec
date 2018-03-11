package id.ac.sttgarut.motorec.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import id.ac.sttgarut.motorec.R;
import id.ac.sttgarut.motorec.adapter.SparePartAdapter;
import id.ac.sttgarut.motorec.db.SparePartDAO;
import id.ac.sttgarut.motorec.model.SparePart;

public class SparePartList extends Fragment implements OnItemClickListener,
        OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "list_sparepart";

    Activity activity;
    ListView sparePartListView;
    ArrayList<SparePart> spareParts;

    SparePartAdapter sparePartListAdapter;
    SparePartDAO sparePartDAO;

    private GetTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sparePartDAO = new SparePartDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sparepart_list, container,
                false);
        findViewsById(view);

        task = new GetTask(activity);
        task.execute((Void) null);

        sparePartListView.setOnItemClickListener(this);
        sparePartListView.setOnItemLongClickListener(this);
        return view;
    }

    private void findViewsById(View view) {
        sparePartListView = (ListView) view.findViewById(R.id.list_kar);
    }

    @Override
    public void onResume() {
        /*getActivity().setTitle(R.string.app_name);
        getActivity().getActionBar().setTitle(R.string.app_name);*/
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> list, View arg1, int position,
                            long arg3) {
        SparePart sparePart = (SparePart) list.getItemAtPosition(position);

        if (sparePart != null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedSparePart", sparePart);
            CustomDialogFragment customDialogFragment = new CustomDialogFragment();
            customDialogFragment.setArguments(arguments);
            customDialogFragment.show(getFragmentManager(),
                    CustomDialogFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long arg3) {
        SparePart sparePart = (SparePart) parent.getItemAtPosition(position);

        //AsyncTask menghapus data dari database
        sparePartDAO.delete(sparePart);
        sparePartListAdapter.remove(sparePart);
        return true;
    }

    public class GetTask extends AsyncTask<Void, Void, ArrayList<SparePart>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<SparePart> doInBackground(Void... arg0) {
            ArrayList<SparePart> karyawanList = sparePartDAO.getSparePart();
            return karyawanList;
        }

        @Override
        protected void onPostExecute(ArrayList<SparePart> karList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                Log.d("karyawan", karList.toString());
                spareParts = karList;
                if (karList != null) {
                    if (karList.size() != 0) {
                        sparePartListAdapter = new SparePartAdapter(activity,
                                karList);
                        sparePartListView.setAdapter(sparePartListAdapter);
                    } else {
                        Toast.makeText(activity, "Records SparePart Tidak Ada",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    }

    /*
     * This method is invoked from MainActivity onFinishDialog() method. It is
     * called from CustomEmpDialogFragment when an employee record is updated.
     * This is used for communicating between fragments.
     */
    public void updateView() {
        task = new GetTask(activity);
        task.execute((Void) null);
    }

}