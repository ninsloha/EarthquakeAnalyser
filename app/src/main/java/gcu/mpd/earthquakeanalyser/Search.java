package gcu.mpd.earthquakeanalyser;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Name                 Narinder Kaur
// Student ID           S1516125
// Programme of Study   Bsc Hons Computing


public class Search extends Fragment {

    Button datepicker;
    Calendar c;
    DatePickerDialog dpd;
    TextView txtView;
    EditText minInput;
    EditText maxInput;
    EditText search;
    Button Update;
    LinearLayout minLayout;
    LinearLayout maxLayout;
    LinearLayout searchLayout;
    DatePicker datePicker;

    public Search(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout rl = (RelativeLayout) inflater.inflate(R.layout.search_fragment, container, false);



                /* for (EarthquakeAnalyser currentEarthquakeAnalyser: ){
                    final EarthquakeAnalyser eqa = currentEarthquakeAnalyser;

                    Date date = null;
                    try {

                        date = dateFormat.parse(currentEarthquakeAnalyser.getPubDate());
                        System.out.println(date.toString());

                    }catch (ParseException e){
                        e.printStackTrace();

                    }

                    if(date.after (dateStart) && date.before(dateEnd)){

                        filtered.add(eqa);
                    }
                }

                System.out.println(filtered.size());
                if (filtered.size() != 0){
                    adapter = Adapter (MainActivity.this, filtered);
                    ListView.setAdapter(adapter);
                }
}
            }
        });
*/
        return rl;
    }






}

