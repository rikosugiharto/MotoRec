package id.ac.sttgarut.motorec.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import id.ac.sttgarut.motorec.R;
import id.ac.sttgarut.motorec.adapter.Adapter;
import id.ac.sttgarut.motorec.adapter.AdapterBengkel;
import id.ac.sttgarut.motorec.adapter.BengkelAdapter;
import id.ac.sttgarut.motorec.adapter.BengkelSnapAdapter;
import id.ac.sttgarut.motorec.adapter.SnapAdapter;
import id.ac.sttgarut.motorec.adapter.SparePartAdapter;
import id.ac.sttgarut.motorec.db.BengkelDAO;
import id.ac.sttgarut.motorec.db.SparePartDAO;
import id.ac.sttgarut.motorec.model.Bengkel;
import id.ac.sttgarut.motorec.model.SnapBengkel;
import id.ac.sttgarut.motorec.model.SnapSparePart;
import id.ac.sttgarut.motorec.model.SparePart;

public class MainFragment extends Fragment {

    public static final String ARG_ITEM_ID = "list_karyawan";

    Activity activity;
    Activity activity2;
    ListView sparePartListView;
    ArrayList<SparePart> spareParts;
    ArrayList<Bengkel> bengkels;

    SparePartAdapter sparePartListAdapter;
    BengkelAdapter bengkelListAdapter;
    SparePartDAO sparePartDAO;
    BengkelDAO bengkelDAO;

    private GetSparePart task;
    private GetBengkel task2;

    private RecyclerView mRecyclerView;
    private boolean mHorizontal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sparePartDAO = new SparePartDAO(activity);
        activity2 = getActivity();
        bengkelDAO = new BengkelDAO(activity2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container,
                false);
        findViewsById(view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.setHasFixedSize(true);

        /*if (savedInstanceState == null) {
            mHorizontal = true;
        } else {
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }*/
        mHorizontal = true;

        task = new GetSparePart(activity);
        task.execute((Void) null);

        /*bengkelListView.setOnItemClickListener(this);
        bengkelListView.setOnItemLongClickListener(this);*/
        return view;
    }

    private void findViewsById(View view) {
        sparePartListView = (ListView) view.findViewById(R.id.list_kar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onResume() {
        /*getActivity().setTitle(R.string.app_name);
        getActivity().getActionBar().setTitle(R.string.app_name);*/
        super.onResume();
    }

    /*@Override
    public void onItemClick(AdapterView<?> list, View arg1, int position,
                            long arg3) {
        SparePart bengkel = (SparePart) list.getItemAtPosition(position);

        if (bengkel != null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedKaryawan", bengkel);
            CustomDialogFragment customDialogFragment = new CustomDialogFragment();
            customDialogFragment.setArguments(arguments);
            customDialogFragment.show(getFragmentManager(),
                    CustomDialogFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long arg3) {
        SparePart bengkel = (SparePart) parent.getItemAtPosition(position);

        //AsyncTask menghapus data dari database
        bengkelDAO.delete(bengkel);
        bengkelListAdapter.remove(bengkel);
        return true;
    }*/

    public class GetSparePart extends AsyncTask<Void, Void, ArrayList<SparePart>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetSparePart(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<SparePart> doInBackground(Void... arg0) {
            ArrayList<SparePart> sparePartList = sparePartDAO.getSparePart();
            return sparePartList;
        }

        @Override
        protected void onPostExecute(ArrayList<SparePart> spareList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                Log.d("karyawan", spareList.toString());
                spareParts = spareList;
                task2 = new GetBengkel(activity2);
                task2.execute((Void) null);
            }
        }
    }

    public class GetBengkel extends AsyncTask<Void, Void, ArrayList<Bengkel>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetBengkel(Activity context) {
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
                bengkels = bengkelList;
                BengkelSnapAdapter snapAdapter = new BengkelSnapAdapter();
                if (spareParts != null) {
                    if (spareParts.size() != 0) {
                        sparePartListAdapter = new SparePartAdapter(activity,
                                spareParts);
                        //SnapAdapter snapAdapter = new SnapAdapter();
                        if (mHorizontal) {
                            snapAdapter.addSnap(new SnapSparePart(Gravity.CENTER_HORIZONTAL, "SparePart", spareParts));
                            mRecyclerView.setAdapter(snapAdapter);
                            //mRecyclerView.setAdapter(snapAdapter);
                        } else {
                            Adapter adapter = new Adapter(false, false, spareParts);
                            mRecyclerView.setAdapter(adapter);
                            new GravitySnapHelper(Gravity.TOP, false, new GravitySnapHelper.SnapListener() {
                                @Override
                                public void onSnap(int position) {
                                    Log.d("Snapped", position + "");
                                }
                            }).attachToRecyclerView(mRecyclerView);
                        }
                    } else {
                        Toast.makeText(activity, "Records SparePart Tidak Ada",
                                Toast.LENGTH_LONG).show();
                    }
                }
                if (bengkels != null) {
                    if (bengkelList.size() != 0) {
                        bengkelListAdapter = new BengkelAdapter(activity,
                                bengkels);
                        //BengkelSnapAdapter snapAdapter = new BengkelSnapAdapter();
                        if (mHorizontal) {
                            snapAdapter.addSnap(new SnapBengkel(Gravity.CENTER_HORIZONTAL, "Bengkel", bengkels));
                        } else {
                            AdapterBengkel adapter = new AdapterBengkel(false, false, bengkels);
                            mRecyclerView.setAdapter(adapter);
                            new GravitySnapHelper(Gravity.TOP, false, new GravitySnapHelper.SnapListener() {
                                @Override
                                public void onSnap(int position) {
                                    Log.d("Snapped", position + "");
                                }
                            }).attachToRecyclerView(mRecyclerView);
                        }
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
        task = new GetSparePart(activity);
        task.execute((Void) null);
    }

}