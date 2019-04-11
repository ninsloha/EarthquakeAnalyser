package gcu.mpd.earthquakeanalyser;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//
// Name                 Narinder Kaur
// Student ID           S1516125
// Programme of Study   Bsc Hons Computing


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener/*implements OnClickListener*/ {
    private TextView rawDataDisplay;
    private Button startButton;
    private String result;
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";


    ListView ListView;
    ArrayList<String> titles;
    ArrayList<String> links;
    ArrayAdapter<String> adapter;
    DatePickerDialog datepicker;
    DatePickerDialog datepicker1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView = (ListView) findViewById(R.id.ListView);

        titles = new ArrayList<String>();
        links = new ArrayList<String>();


        new ProcessInBackground().execute();
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loading RSS Feed...please wait...");
            progressDialog.show();
        }

        @Override
       protected Exception doInBackground(Integer... params) {

            try {
                URL urlSource = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                factory.setNamespaceAware(false);


                //factory features
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(getInputStream(urlSource), "UTF_8");

                boolean insideItem = false;

                EarthquakeAnalyser earthquake = new EarthquakeAnalyser();

                // Returns the type of current event: START_TAG, END_TAG, START_DOCUMENT, END_DOCUMENT etc..
                int eventType = xpp.getEventType(); //loop control

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    //if we are at a START_TAG (opening tag)
                    if (eventType == XmlPullParser.START_TAG) {
                        //if the tag is called "item"
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        }
                        //if the tag is called "title"
                        else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem) {
                                // extract the text between <title> and </title>
                                titles.add(xpp.nextText());
                                for (int i = 0; i < titles.size(); i++) {
                                    String[] separated = titles.get(i).split(";");
                                    earthquake.setLocation(separated[0]);
                                    earthquake.setLatlong(separated[2]);
                                    earthquake.setPubDate(separated[1]);
                                    earthquake.setDepth(separated[3]);
                                    earthquake.setMagnitude(separated[4]);

                                    System.out.println(earthquake.getPubDate());
                                    System.out.println("This works great!" + earthquake.getPubDate());
                                    System.out.println(earthquake.getLatlong());
                                    System.out.println(earthquake.getDepth());
                                    System.out.println(earthquake.getMagnitude());

                                }

                            }
                        }
                        //if the tag is called "link"
                        else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {
                                // extract the text between <link> and </link>
                                links.add(xpp.nextText());
                            }
                        }
                    }
                    //if we are at an END_TAG and the END_TAG is called "item"
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }

                    eventType = xpp.next(); //move to next element
                }


            } catch (MalformedURLException e) {
                exception = e;
            } catch (XmlPullParserException e) {
                exception = e;
            } catch (IOException e) {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, titles);

            ListView.setAdapter(adapter);
            System.out.println("In ADAPTER");

            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.RSS_feed) {

        } else if (id == R.id.Map) {
            fragment = new Map();
            Bundle info = new Bundle();
            info.putStringArrayList("info", titles);
            fragment.setArguments(info);

        } else if (id == R.id.Search) {
            Calendar c = Calendar.getInstance();
             final int year = c.get(Calendar.YEAR);
             final int month = c.get(Calendar.MONTH);
             final int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
             final Date dateStart = new Date();
             final Date dateEnd = new Date();
            datepicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                            Log.d("Date", "Year=" + i + "Month" + ( + i1) + "day=" + dayOfMonth);

                            dateStart.setYear(year-1900);
                            dateStart.setMonth(month);
                            dateStart.setDate(dayOfMonth);
                            dateStart.setHours(0);
                            dateStart.setMinutes(0);
                            dateStart.setSeconds(0);

                    datepicker1 = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                            Log.d("Date", "Year=" + i + "Month" + ( + i1) + "day=" + dayOfMonth);



                            dateStart.setYear(year-1900);
                            dateStart.setMonth(month);
                            dateStart.setDate(dayOfMonth);
                            dateStart.setHours(0);
                            dateStart.setMinutes(0);
                            dateStart.setSeconds(0);
                            /*
                            for loop.....

                            if(date.after (dateStart) && date.before(dateEnd)){

                        filtered.add(eqa);
                    }
                }

                System.out.println(filtered.size());
                if (filtered.size() != 0){
                    adapter = Adapter (MainActivity.this, filtered);
                    ListView.setAdapter(adapter);
                }*/

                        }
                    }, year, month, dayOfMonth);
                    datepicker1.show();

                }
            }, year, month, dayOfMonth);
            datepicker.show();

        } else if (id == R.id.Settings) {
            fragment = new Furthest();
            Bundle infoFurthest = new Bundle();
            infoFurthest.putStringArrayList("furthest", titles);
            fragment.setArguments(infoFurthest);
        }

        if (fragment != null){

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();


            ft.replace(R.id.fragemnt, fragment, "Fragment");

            ft.addToBackStack("Fragment");

            ft.commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
