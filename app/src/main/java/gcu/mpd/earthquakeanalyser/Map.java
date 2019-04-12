package gcu.mpd.earthquakeanalyser;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

// Name                 Narinder Kaur
// Student ID           S1516125
// Programme of Study   Bsc Hons Computing

public class Map extends Fragment implements OnMapReadyCallback {

    ArrayList<String> location = new ArrayList<String>();
    ArrayList<String> loc = new ArrayList<String>();
    ArrayList<String> latlo = new ArrayList<String>();
    ArrayList<String> latlo2 = new ArrayList<String>();
    ArrayList<String> latitude = new ArrayList<String>();
    ArrayList<String> longitude = new ArrayList<String>();

    SupportMapFragment mapFragment;
    public Map () {

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FrameLayout v = (FrameLayout) inflater.inflate(R.layout.map_fragment, container, false);
        mapFragment= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment== null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft= fm.beginTransaction();
            mapFragment= SupportMapFragment.newInstance();
            ft.replace(R.id.mapf, mapFragment).commit();

            ArrayList<String> array = getArguments().getStringArrayList("info");
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
        }

        //callback
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println(latitude);
        for (int i = 0; i<latitude.size(); i++) {
            double lat = Double.parseDouble(latitude.get(i));
            double lon = Double.parseDouble(longitude.get(i));
            LatLng sydney = new LatLng(lat, lon);
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title(loc.get(i)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        }
    }
}
