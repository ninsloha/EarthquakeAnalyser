package gcu.mpd.earthquakeanalyser;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class Adapter extends ArrayAdapter <EarthquakeAnalyser> implements Filterable {


    private final int layoutResource;
    private final LayoutInflater layoutInflater;

    private ArrayList<String> earthquakeAnalyserItems;
    private ArrayList<String>backupItems;

    public Adapter(Context context, int resource, ArrayList<String> earthquakeAnalyserItems) {
        super(context, resource);

        ArrayList<String> desc = earthquakeAnalyserItems;

        backupItems = new ArrayList<>(earthquakeAnalyserItems);
        System.out.println("backup"+backupItems);

        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);


    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.content_main, parent, false);
            ListView list = (ListView) convertView.findViewById(R.id.ListView);
        }
        /*final Context context = parent.getContext();
        layoutInflater = LayoutInflater.from(context);*/

        TextView location = convertView.findViewById(R.id.location);
        TextView LatLong = convertView.findViewById(R.id.LatLong);



        String eqa = backupItems.get (position);

        location.setText(backupItems.get(position));
       // LatLong.setText(eqa.getLatlong());




        return convertView;


    }


    @Override
    public int getCount() {return earthquakeAnalyserItems.size();}

    @Override
    public Filter getFilter() {return theSearch;}


    private Filter theSearch = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<String> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                filteredList.addAll(backupItems);


            } else {

                String filterPattern = constraint.toString().toLowerCase().trim();

                for (String item : backupItems) {
                    // String searchAllItems = item.getTitle()+" "+ item.getMagnitude()+
                    if (item.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

            }


            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            earthquakeAnalyserItems.clear();
            earthquakeAnalyserItems.addAll( (ArrayList) results.values);
            notifyDataSetChanged();
        }

    };
}

