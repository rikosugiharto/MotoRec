package id.ac.sttgarut.motorec.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.ac.sttgarut.motorec.R;
import id.ac.sttgarut.motorec.model.Bengkel;

public class AdapterBengkel extends RecyclerView.Adapter<AdapterBengkel.ViewHolder> {

    private List<Bengkel> mApps;
    private boolean mHorizontal;
    private boolean mPager;

    public AdapterBengkel(boolean horizontal, boolean pager, List<Bengkel> apps) {
        mHorizontal = horizontal;
        mApps = apps;
        mPager = pager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mPager) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_pager, parent, false));
        } else {
            return mHorizontal ? new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_bengkel, parent, false)) :
                    new ViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.adapter_vertical, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bengkel app = mApps.get(position);
        holder.imageView.setImageResource(R.drawable.bengkel);
        holder.nameTextView.setText(app.getNama());
        holder.alamatTextView.setText(app.getAlamat());
        holder.ratingTextView.setText(String.valueOf(4.6f));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView nameTextView;
        public TextView alamatTextView;
        public TextView ratingTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            alamatTextView = (TextView) itemView.findViewById(R.id.alamatTextView);
            ratingTextView = (TextView) itemView.findViewById(R.id.ratingTextView);
        }

        @Override
        public void onClick(View v) {
            Log.d("App", mApps.get(getAdapterPosition()).getNama());
        }
    }

}

