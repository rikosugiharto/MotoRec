package id.ac.sttgarut.motorec.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import id.ac.sttgarut.motorec.R;
import id.ac.sttgarut.motorec.model.SparePart;
import id.ac.sttgarut.motorec.model.Bengkel;

public class BengkelAdapter extends ArrayAdapter<Bengkel> {

    private Context context;
    List<Bengkel> bengkel;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public BengkelAdapter(Context context, List<Bengkel> bengkels) {
        super(context, R.layout.sparepart_item, bengkels);
        this.context = context;
        this.bengkel = bengkels;
    }

    private class ViewHolder {
        TextView idTxt;
        TextView namaTxt;
        //TextView tgllahirTxt;
        TextView alamatTxt;
    }

    @Override
    public int getCount() {
        return bengkel.size();
    }

    @Override
    public Bengkel getItem(int position) {
        return bengkel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bengkel_item, null);
            holder = new ViewHolder();

            holder.idTxt = (TextView) convertView
                    .findViewById(R.id.txt_id);
            holder.namaTxt = (TextView) convertView
                    .findViewById(R.id.txt_nama);
            holder.alamatTxt = (TextView) convertView
                    .findViewById(R.id.txt_alamat);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Bengkel bengkel = (Bengkel) getItem(position);
        holder.idTxt.setText(bengkel.getId() + "");
        holder.namaTxt.setText(bengkel.getNama());
        holder.alamatTxt.setText(bengkel.getAlamat() + "");

        //holder.tgllahirTxt.setText(formatter.format(sparePart.getTanggalLahir()));

        return convertView;
    }

    @Override
    public void add(Bengkel bengkel) {
        this.bengkel.add(bengkel);
        notifyDataSetChanged();
        super.add(bengkel);
    }

    @Override
    public void remove(Bengkel bengkel) {
        this.bengkel.remove(bengkel);
        notifyDataSetChanged();
        super.remove(bengkel);
    }

}