package id.ac.sttgarut.motorec.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import id.ac.sttgarut.motorec.R;
import id.ac.sttgarut.motorec.db.SparePartDAO;
import id.ac.sttgarut.motorec.fragment.BengkelList;
import id.ac.sttgarut.motorec.fragment.CustomDialogFragment;
import id.ac.sttgarut.motorec.fragment.MainFragment;
import id.ac.sttgarut.motorec.fragment.BengkelAdd;
import id.ac.sttgarut.motorec.fragment.SparePartAdd;
import id.ac.sttgarut.motorec.fragment.SparePartList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CustomDialogFragment.DialogFragmentListener {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Fragment contentFragment;
    private SparePartList sparePartListFragment;
    private BengkelAdd bengkelAddFragment;
    private SparePartAdd sparePartAddFragment;
    SparePartDAO sparePartDAO;
    protected LocationManager locationManager;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 10; // dalam Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 60000; // dalam Milliseconds
    public final static int TAG_PERMISSION_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sparePartDAO = new SparePartDAO(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();

		/*
         * This is called when orientation is changed.
         */
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(BengkelAdd.ARG_ITEM_ID)) {
                    if (fragmentManager
                            .findFragmentByTag(BengkelAdd.ARG_ITEM_ID) != null) {
                        setFragmentTitle(R.string.add_beng);
                        contentFragment = fragmentManager
                                .findFragmentByTag(BengkelAdd.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(SparePartList.ARG_ITEM_ID) != null) {
                sparePartListFragment = (SparePartList) fragmentManager
                        .findFragmentByTag(SparePartList.ARG_ITEM_ID);
                contentFragment = sparePartListFragment;
            }
        } else {
            MainFragment mainFragment = new MainFragment();
            setFragmentTitle(R.string.app_name);
            switchContent(mainFragment, MainFragment.ARG_ITEM_ID);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MINIMUM_TIME_BETWEEN_UPDATES,
                        MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                        new MyLocationListener()
                );
            }
        }
    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "Deteksi Lokasi Baru \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            //switchToMap();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(MainActivity.this, "Status provider berubah",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(MainActivity.this,
                    "Provider dinonaktifkan oleh user, GPS off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(MainActivity.this,
                    "Provider diaktifkan oleh user, GPS on",
                    Toast.LENGTH_LONG).show();
        }

    }

    protected void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
        //getActionBar().setTitle(resourseId);

    }

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_main, fragment, tag);

            if (!(fragment instanceof BengkelAdd)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    protected void showCurrentLocation() {
        if(!CheckPermission.checkPermission(MainActivity.this)) {
            CheckPermission.requestPermission(MainActivity.this,TAG_PERMISSION_CODE);

        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            String message = String.format(
                    "Lokasi saat ini \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(this, message,
                    Toast.LENGTH_LONG).show();
        }

    }

    public static class CheckPermission {

        //  CHECK FOR LOCATION PERMISSION
        public static boolean checkPermission(Activity activity){
            int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
            if (result == PackageManager.PERMISSION_GRANTED){

                return true;

            } else {

                return false;

            }
        }

        //REQUEST FOR PERMISSSION
        public static void requestPermission(Activity activity, final int code){

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_FINE_LOCATION)){

                Toast.makeText(activity,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

            } else {

                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},code);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/
        switch (item.getItemId()) {
            case R.id.action_location:
                showCurrentLocation();
                return true;
            case R.id.action_add_sparepart:
                setFragmentTitle(R.string.add_spar);
                sparePartAddFragment = new SparePartAdd();
                switchContent(sparePartAddFragment, SparePartAdd.ARG_ITEM_ID);
                /*sparePartListFragment = new SparePartList();
                switchContent(sparePartListFragment, SparePartList.ARG_ITEM_ID);*/

                return true;
            case R.id.action_add_bengkel:
                setFragmentTitle(R.string.add_beng);
                bengkelAddFragment = new BengkelAdd();
                switchContent(bengkelAddFragment, BengkelAdd.ARG_ITEM_ID);
                /*sparePartListFragment = new SparePartList();
                switchContent(sparePartListFragment, SparePartList.ARG_ITEM_ID);*/

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int id){
        Fragment fragment = null;
        switch (id){
            case R.id.nav_dashboard:
                MainFragment mainFragment = new MainFragment();
                setFragmentTitle(R.string.app_name);
                switchContent(mainFragment, MainFragment.ARG_ITEM_ID);
                break;
            case R.id.nav_map:
                //fragment = new MapsActivity();
                startActivity(new Intent(this, MapsActivity.class));
                break;
            case R.id.nav_sparepart:
                fragment = new SparePartList();
                setFragmentTitle(R.string.list_spar);
                switchContent(fragment, SparePartList.ARG_ITEM_ID);
                break;
            case R.id.nav_bengkel:
                fragment = new BengkelList();
                setFragmentTitle(R.string.list_beng);
                switchContent(fragment, SparePartList.ARG_ITEM_ID);
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    @Override
    public void onFinishDialog() {
        if (sparePartListFragment != null) {
            sparePartListFragment.updateView();
        }
    }


}
