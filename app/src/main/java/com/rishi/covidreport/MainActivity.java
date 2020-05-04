package com.rishi.covidreport;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 123;
    private String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private DrawerLayout mDrawerLayout;
    private FragmentTransaction mFragmentTransaction;
    private MyLocation myLocation;
    private Earth mEarth;
    private Graph graph;
    public static String District = "District";
    public static String State = "State";
    private RelativeLayout r1;
    private RelativeLayout r2;
    private LocationManager locationmanager;
    private LocationListener locationListener;
    private RelativeLayout loader_layout;
    private ProgressBar mProgressBar;
    private TextView loading_text;
    private NavigationView nav_view;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLocation = new MyLocation();
        mEarth = new Earth();
        graph = new Graph();
        locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        Toolbar toolbar1 = findViewById(R.id.toolbar_o);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        loader_layout = findViewById(R.id.loading_layout);
        mProgressBar = findViewById(R.id.progess_bar);
        loading_text = findViewById(R.id.loading_text);
        r1 = findViewById(R.id.home_layout);
        r2 = findViewById(R.id.other_layout);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        nav_view = findViewById(R.id.nav_view);
        nav_view.getMenu().getItem(0).setChecked(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationclicklistener();
        bottomnavigationlistener();
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.fragment, myLocation);
        mFragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean check = checkgoogleplayservices();
        if (check) {
            requestpermission();
        }
    }

    private void navigationclicklistener() {
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction nav_transition;
                int id = item.getItemId();
                Log.d(TAG, "navigationclicklistener: navigation item clicked");
                switch (id) {
                    case R.id.home:
                        item.setChecked(true);
                        nav_view.getMenu().getItem(2).setChecked(false);
                        nav_view.getMenu().getItem(3).setChecked(false);
                        nav_view.getMenu().getItem(4).setChecked(false);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        r1.setVisibility(View.VISIBLE);
                        r2.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.donation:
                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.pmcares.gov.in/en/web/contribution/donate_india"));
                        startActivity(intent);
                        break;
                    case R.id.prevention:
                        item.setChecked(true);
                        nav_view.getMenu().getItem(0).setChecked(false);
                        nav_view.getMenu().getItem(3).setChecked(false);
                        nav_view.getMenu().getItem(4).setChecked(false);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        r1.setVisibility(View.INVISIBLE);
                        r2.setVisibility(View.VISIBLE);
                        nav_transition = getSupportFragmentManager().beginTransaction();
                        nav_transition.replace(R.id.other_fragment, new SympandPrecau());
                        nav_transition.commit();
                        break;
                    case R.id.helpline:
                        item.setChecked(true);
                        nav_view.getMenu().getItem(2).setChecked(false);
                        nav_view.getMenu().getItem(0).setChecked(false);
                        nav_view.getMenu().getItem(4).setChecked(false);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        r1.setVisibility(View.INVISIBLE);
                        r2.setVisibility(View.VISIBLE);
                        nav_transition = getSupportFragmentManager().beginTransaction();
                        nav_transition.replace(R.id.other_fragment, new Helpline());
                        nav_transition.commit();
                        break;
                    case R.id.faq:
                        item.setChecked(true);
                        nav_view.getMenu().getItem(2).setChecked(false);
                        nav_view.getMenu().getItem(0).setChecked(false);
                        nav_view.getMenu().getItem(3).setChecked(false);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        r1.setVisibility(View.INVISIBLE);
                        r2.setVisibility(View.VISIBLE);
                        nav_transition = getSupportFragmentManager().beginTransaction();
                        nav_transition.replace(R.id.other_fragment, new FAQ());
                        nav_transition.commit();
                        break;
                    default:
                        Log.d(TAG, "onNavigationItemSelected: " + "nothing selected");

                }
                return true;
            }
        });

    }

    private void bottomnavigationlistener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Log.d(TAG, "onNavigationItemSelected: bottom navigation clicked");
                switch (id) {
                    case R.id.location:
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(R.id.fragment, myLocation);
                        mFragmentTransaction.commit();
                        Log.d(TAG, "onNavigationItemSelected: " + "starting my location data");
                        break;
                    case R.id.earth:
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(R.id.fragment, mEarth);
                        mFragmentTransaction.commit();
                        Log.d(TAG, "onNavigationItemSelected: " + "starting earth data");
                        break;
                    case R.id.graph:
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        mFragmentTransaction.replace(R.id.fragment, graph);
                        mFragmentTransaction.commit();
                        Log.d(TAG, "onNavigationItemSelected: " + "starting graph data");
                        break;
                    default:
                        Log.d(TAG, "onNavigationItemSelected: nothing clicked");
                }
                return true;
            }
        });
    }

    private boolean checkgoogleplayservices() {
        Log.d(TAG, "checking google play services");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            //everything is fine google play service is present along with correct version and the user can make request
            Log.d(TAG, "Google play service is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but can be resolved eg.older version present so user will be directed to google play to update it
            Log.d(TAG, "error occured but can be resolved");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(MainActivity.this, "You can't make map request", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void requestpermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
            return;
        }
        if (!locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alertdialog();
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
            loading_text.setVisibility(View.VISIBLE);
            userlocationlm();
        }
    }

    private void alertdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Location Permission")
                .setMessage("Your GPS seems to be disabled, please enable it")
                .setCancelable(false)
                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void userlocationlm() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged: user location accessed");
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                Geocoder geocoder = new Geocoder(MainActivity.this);
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                    if (addresses.size() != 0) {
                        District = addresses.get(0).getSubAdminArea();
                        State = addresses.get(0).getAdminArea();
                        myLocation.setslocationdata(District, State);
                        covid19districtdata(District, State);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d(TAG, "onStatusChanged: " + "status changed: " + status);
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d(TAG, "onProviderEnabled: Provider Enabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d(TAG, "onProviderDisabled: provider Disabled");
            }
        };
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1000, locationListener);
    }

    //for requesting permission using fused location provider
   /* private void userlocation() {
        Log.d(TAG, "userlocation: accessing user location");
        FusedLocationProviderClient locationprovider = LocationServices.getFusedLocationProviderClient(this);
        locationprovider.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    Log.d(TAG, "onComplete: User lat/lng: " + lat + " " + lng);
                    Geocoder geocoder = new Geocoder(MainActivity.this);
                    try {
                        List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                        if (addresses.size() != 0) {
                            District = addresses.get(0).getSubAdminArea();
                            State = addresses.get(0).getAdminArea();
                            Log.d(TAG, "onComplete: location accessed");
                            Log.d(TAG, "onComplete: " + "District is: " + District + " State is: " + State);

                            //this will not give nullpointer exception as mylocation instantes are already instaniated
                            //ie. district_name and state_name are already instanited and we are setting text on it
                            //while in previous one we where setting data without any instantiation
                            myLocation.setslocationdata(District, State);
                            covid19data(District, State);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Error occured" + e.toString());
            }
        });
    }*/

    private void covid19districtdata(final String district, final String state) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String state_vise_url = "https://api.covid19india.org/state_district_wise.json";
        asyncHttpClient.get(state_vise_url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONObject state_data = response.getJSONObject(state).getJSONObject("districtData")
                                    .getJSONObject(district);

                            Log.d(TAG, "onSuccess: getting state stats");
                            myLocation.updatingdata(state_data);
                            covid19statedata(state);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d(TAG, "onFailure: Something went wrong" + throwable.toString());
                    }
                }
        );
    }

    private void covid19statedata(final String state) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String statedataurl = "https://api.covid19india.org/data.json";
        asyncHttpClient.get(statedataurl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String state_name;
                    JSONArray states = response.getJSONArray("statewise");
                    int total_data = states.length();
                    JSONObject country_stats = states.getJSONObject(0);
                    myLocation.updatecountrydata(country_stats);
                    for (int i = 0; i < total_data; ++i) {
                        state_name = states.getJSONObject(i).getString("state");
                        Log.d(TAG, "onSuccess: " + state_name);
                        if (state_name.equals(state)) {
                            myLocation.updatingstatedata(states.getJSONObject(i));
                            break;
                        }
                    }
                    dailydata();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d(TAG, "onFailure: something went wrong" + throwable.getMessage());
            }
        });
    }

    private void dailydata() {
        String dailt_url = "https://api.covid19india.org/states_daily.json";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(dailt_url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String statecode = MyLocation.statecode;
                try {
                    int length = response.getJSONArray("states_daily").length();
                    JSONObject json = response.getJSONArray("states_daily").getJSONObject(length - 3);
                    Log.d(TAG, "onSuccess: daily data" + statecode);
                    myLocation.newcases(json, statecode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d(TAG, "onFailure: something went wrong" + throwable.toString());
            }
        });
        globedata();
    }

    private void globedata() {
        String globe_url = "https://coronavirus-19-api.herokuapp.com/countries";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(globe_url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                mEarth.fromJSON(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                if (bytesWritten == totalSize) {
                    mProgressBar.setVisibility(View.GONE);
                    loading_text.setVisibility(View.GONE);
                    loader_layout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: " + "permission Granted");
                    userlocationlm();
                }
            } else {
                Toast.makeText(this, "Permission: Denied", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Something went Wrong", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationListener != null) {
            Log.d(TAG, "onPause: location permission removed");
            locationmanager.removeUpdates(locationListener);
        }
    }
}
