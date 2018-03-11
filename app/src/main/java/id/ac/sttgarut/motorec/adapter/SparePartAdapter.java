package id.ac.sttgarut.motorec.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import id.ac.sttgarut.motorec.R;
import id.ac.sttgarut.motorec.model.SparePart;

public class SparePartAdapter extends ArrayAdapter<SparePart> {

    private Context context;
    List<SparePart> spareParts;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public SparePartAdapter(Context context, List<SparePart> spareParts) {
        super(context, R.layout.sparepart_item, spareParts);
        this.context = context;
        this.spareParts = spareParts;
    }

    private class ViewHolder {
        TextView idTxt;
        TextView namaTxt;
        TextView tgllahirTxt;
        TextView gajiTxt;
    }

    @Override
    public int getCount() {
        return spareParts.size();
    }

    @Override
    public SparePart getItem(int position) {
        return spareParts.get(position);
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
            convertView = inflater.inflate(R.layout.sparepart_item, null);
            holder = new ViewHolder();

            holder.idTxt = (TextView) convertView
                    .findViewById(R.id.txt_id);
            holder.namaTxt = (TextView) convertView
                    .findViewById(R.id.txt_nama);
            holder.gajiTxt = (TextView) convertView
                    .findViewById(R.id.txt_bataspakai);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SparePart sparePart = (SparePart) getItem(position);
        holder.idTxt.setText(sparePart.getId() + "");
        holder.namaTxt.setText(sparePart.getNama());
        holder.gajiTxt.setText(sparePart.getBataspakai() + "");

        //holder.tgllahirTxt.setText(formatter.format(sparePart.getTanggalLahir()));

        return convertView;
    }

    @Override
    public void add(SparePart sparePart) {
        spareParts.add(sparePart);
        notifyDataSetChanged();
        super.add(sparePart);
    }

    @Override
    public void remove(SparePart sparePart) {
        spareParts.remove(sparePart);
        notifyDataSetChanged();
        super.remove(sparePart);
    }

}