package id.ac.sttgarut.motorec.adapter;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;

import id.ac.sttgarut.motorec.R;
import id.ac.sttgarut.motorec.model.Snap;
import id.ac.sttgarut.motorec.model.SnapBengkel;
import id.ac.sttgarut.motorec.model.SnapSparePart;

public class BengkelSnapAdapter extends RecyclerView.Adapter<BengkelSnapAdapter.ViewHolder> implements GravitySnapHelper.SnapListener {

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    private ArrayList<Snap> mSnaps;
    // Disable touch detection for parent recyclerView if we use vertical nested recyclerViews
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        }
    };

    public BengkelSnapAdapter() {
        mSnaps = new ArrayList<>();
    }

    public void addSnap(Snap snap) {
        mSnaps.add(snap);
    }

    @Override
    public int getItemViewType(int position) {
        /*SnapBengkel snap = mSnaps.get(position);
        switch (snap.getGravity()) {
            case Gravity.CENTER_VERTICAL:
                return VERTICAL;
            case Gravity.CENTER_HORIZONTAL:
                return HORIZONTAL;
            case Gravity.START:
                return HORIZONTAL;
            case Gravity.TOP:
                return VERTICAL;
            case Gravity.END:
                return HORIZONTAL;
            case Gravity.BOTTOM:
                return VERTICAL;
        }*/
        return HORIZONTAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = viewType == VERTICAL ? LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_snap_vertical, parent, false)
                : LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_snap, parent, false);

        if (viewType == VERTICAL) {
            view.findViewById(R.id.recyclerView).setOnTouchListener(mTouchListener);
        }

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object snap = mSnaps.get(position);
        if(snap instanceof SnapSparePart)
        {
            SnapSparePart sparePart = (SnapSparePart) snap;
            holder.snapTextView.setText(sparePart.getText());
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), sparePart.getGravity() == Gravity.CENTER_HORIZONTAL ?
                    LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL, false));
            new LinearSnapHelper().attachToRecyclerView(holder.recyclerView);
            holder.recyclerView.setAdapter(new Adapter(sparePart.getGravity() == Gravity.START
                    || sparePart.getGravity() == Gravity.END
                    || sparePart.getGravity() == Gravity.CENTER_HORIZONTAL,
                    sparePart.getGravity() == Gravity.CENTER, sparePart.getApps()));
        } else
        {
            SnapBengkel bengkel = (SnapBengkel) snap;
            holder.snapTextView.setText(bengkel.getText());
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), bengkel.getGravity() == Gravity.CENTER_HORIZONTAL ?
                    LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL, false));
            new LinearSnapHelper().attachToRecyclerView(holder.recyclerView);
            holder.recyclerView.setAdapter(new AdapterBengkel(bengkel.getGravity() == Gravity.START
                    || bengkel.getGravity() == Gravity.END
                    || bengkel.getGravity() == Gravity.CENTER_HORIZONTAL,
                    bengkel.getGravity() == Gravity.CENTER, bengkel.getApps()));
        }
        /*SnapBengkel snap = mSnaps.get(position);
        holder.snapTextView.setText(snap.getText());

        if (snap.getGravity() == Gravity.START || snap.getGravity() == Gravity.END) {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            new GravitySnapHelper(snap.getGravity(), false, this).attachToRecyclerView(holder.recyclerView);
        } else if (snap.getGravity() == Gravity.CENTER_HORIZONTAL ||
                snap.getGravity() == Gravity.CENTER_VERTICAL) {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), snap.getGravity() == Gravity.CENTER_HORIZONTAL ?
                    LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL, false));
            new LinearSnapHelper().attachToRecyclerView(holder.recyclerView);
        } else if (snap.getGravity() == Gravity.CENTER) { // Pager snap
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            new GravityPagerSnapHelper(Gravity.START).attachToRecyclerView(holder.recyclerView);
        } else { // Top / Bottom
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext()));
            new GravitySnapHelper(snap.getGravity()).attachToRecyclerView(holder.recyclerView);
        }


        holder.recyclerView.setAdapter(new AdapterBengkel(snap.getGravity() == Gravity.START
                || snap.getGravity() == Gravity.END
                || snap.getGravity() == Gravity.CENTER_HORIZONTAL,
                snap.getGravity() == Gravity.CENTER, snap.getApps()));*/
    }

    @Override
    public int getItemCount() {
        return mSnaps.size();
    }

    @Override
    public void onSnap(int position) {
        Log.d("Snapped: ", position + "");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView snapTextView;
        public RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            snapTextView = (TextView) itemView.findViewById(R.id.snapTextView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }

    }
}

