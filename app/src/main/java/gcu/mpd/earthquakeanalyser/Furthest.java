package gcu.mpd.earthquakeanalyser;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;


public class Furthest extends Fragment {


    ArrayList<String> location = new ArrayList<String>();
    ArrayList<String> loc = new ArrayList<String>();
    ArrayList<String> latlo = new ArrayList<String>();
    ArrayList<String> latlo2 = new ArrayList<String>();
    ArrayList<String> latitude = new ArrayList<String>();
    ArrayList<String> longitude = new ArrayList<String>();
    ArrayList<Double> lat = new ArrayList<>();
    ArrayList<Double> lon = new ArrayList<>();

    public Furthest(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pff = inflater.inflate(R.layout.furthest_fragment, container, false);

        ArrayList<String> array = getArguments().getStringArrayList("furthest");
        for (int i = 0; i<array.size(); i++) {

            String getLatLo[] = array.get(i).split(";");
            location.add(getLatLo[1]);
            String[] getLocation = location.get(i).split(":");
            loc.add(getLocation[1]);
            latlo.add(getLatLo[2]);
            String getLatLo2[] = latlo.get(i).split(":");
            latlo2.add(getLatLo2[1]);
            String getLaLo3[] = latlo2.get(i).split(",");
            latitude.add(getLaLo3[0]);
            longitude.add(getLaLo3[1]);
        }

        System.out.println(latitude);
        System.out.println(longitude);

        for (int i = 0; i<latitude.size(); i++) {
            lat.add(Double.parseDouble(latitude.get(i)));
            lon.add(Double.parseDouble(longitude.get(i)));
        }

        System.out.println("in double"+lat);
        System.out.println("in double"+lon);

        double furWest = lat.get(1);
        double furSouth = lat.get(1);
        double furNorth = lon.get(1);
        double furEast = lon.get(1);
        int positionEast = 0;
        int positionWest = 0;
        int positionNorth = 0;
        int positionSouth = 0;

        for (int k = 0; k<lat.size(); k++){
            if (lat.get(k) > furNorth) {
                furNorth = lat.get(k);
                positionNorth = k;
            }
            if (lat.get(k) < furSouth) {
                furSouth = lat.get(k);
                positionSouth = k;
            }
            if (lon.get(k) > furEast) {
                furEast = lon.get(k);
                positionEast = k;
            }
            if (lon.get(k) < furWest) {
                furWest = lon.get(k);
                positionWest = k;
            }
        }

        System.out.println("West"+location.get(positionWest));
        System.out.println("East"+location.get(positionEast));
        System.out.println("North"+location.get(positionNorth));
        System.out.println("South"+location.get(positionSouth));

        TextView east = (TextView) pff.findViewById(R.id.east);
        east.setText(location.get(positionEast));

        TextView west = (TextView) pff.findViewById(R.id.west);
        west.setText(location.get(positionWest));

        TextView south = (TextView) pff.findViewById(R.id.south);
        south.setText(location.get(positionSouth));

        TextView north = (TextView) pff.findViewById(R.id.north);
        north.setText(location.get(positionNorth));

        return pff;
    }
}

