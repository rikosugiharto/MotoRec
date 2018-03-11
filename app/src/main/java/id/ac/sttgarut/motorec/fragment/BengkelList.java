package id.ac.sttgarut.motorec.fragment;

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

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import id.ac.sttgarut.motorec.R;
import id.ac.sttgarut.motorec.adapter.BengkelAdapter;
import id.ac.sttgarut.motorec.db.BengkelDAO;
import id.ac.sttgarut.motorec.model.Bengkel;
import id.ac.sttgarut.motorec.model.SparePart;

public class BengkelList extends Fragment implements OnItemClickListener,
        OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "list_bengkel";

    Activity activity;
    ListView bengkelListView;
    ArrayList<Bengkel> bengkel;

    BengkelAdapter bengkelListAdapter;
    BengkelDAO bengkelDAO;

    private GetTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        bengkelDAO = new BengkelDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bengkel_list, container,
                false);
        findViewsById(view);

        task = new GetTask(activity);
        task.execute((Void) null);

        bengkelListView.setOnItemClickListener(this);
        bengkelListView.setOnItemLongClickListener(this);
        return view;
    }

    private void findViewsById(View view) {
        bengkelListView = (ListView) view.findViewById(R.id.list_kar);
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
        Bengkel bengkel = (Bengkel) list.getItemAtPosition(position);

        if (bengkel != null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedBengkel", bengkel);
            BengkelCustomDialogFragment customDialogFragment = new BengkelCustomDialogFragment();
            customDialogFragment.setArguments(arguments);
            customDialogFragment.show(getFragmentManager(),
                    CustomDialogFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long arg3) {
        Bengkel bengkel = (Bengkel) parent.getItemAtPosition(position);

        //AsyncTask menghapus data dari database
        bengkelDAO.delete(bengkel);
        bengkelListAdapter.remove(bengkel);
        return true;
    }

    public class GetTask extends AsyncTask<Void, Void, ArrayList<Bengkel>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Bengkel> doInBackground(Void... arg0) {
            ArrayList<Bengkel> bengkelList = bengkelDAO.getBengkel();
            return bengkelList;
        }

        @Override
        protected void onPostExecute(ArrayList<Bengkel> bengkelList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                Log.d("karyawan", bengkelList.toString());
                bengkel = bengkelList;
                if (bengkelList != null) {
                    if (bengkelList.size() != 0) {
                        bengkelListAdapter = new BengkelAdapter(activity,
                                bengkelList);
                        bengkelListView.setAdapter(bengkelListAdapter);
                    } else {
                        Toast.makeText(activity, "Records Bengkel Tidak Ada",
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