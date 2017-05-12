package com.mousebelly.app.userapp.userprofile;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.test.mock.MockPackageManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mousebelly.app.userapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.roger.catloadinglibrary.CatLoadingView;

import java.io.IOException;
import java.util.List;

import static com.mousebelly.app.userapp.userprofile.GetUserProfileData.userprofileUserBean;


public class UserprofileMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE_PERMISSION = 2;
    static EditText StreetName1;
    CatLoadingView mView;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    UserprofileGPSTracker gps;
    double latitude;
    double longitude;
    EditText StreetName, ZipCode, StateName, CityName, Country;
    String AddStreetName, AddZipCode, AddStateName, AddCityName, AddCountry;
    TextView addph, addcnf, addpass, adderrmail, adderruser;
    Button ok, cancle;
    LatLng latLng;
    MarkerOptions markerOptions;
    String StreetCheck = "Ok";
    String zipCheck = "Ok";
    String StateCheck = "Ok";
    String CityCheck = "Ok";
    String CountryCheck = "Ok";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofileactivity_maps);
        mView = new CatLoadingView();
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Location();

        final Dialog dialog = new Dialog(UserprofileMapsActivity.this);
        dialog.setContentView(R.layout.userprofileaddress_dialog);
        dialog.setTitle("Enter Address");

        UserprofileMapsActivity.StreetName1 = StreetName = (EditText) dialog.findViewById(R.id.streetname);
        ZipCode = (EditText) dialog.findViewById(R.id.zipcode);
        StateName = (EditText) dialog.findViewById(R.id.statename);
        CityName = (EditText) dialog.findViewById(R.id.cityname);
        Country = (EditText) dialog.findViewById(R.id.country);
        cancle = (Button) dialog.findViewById(R.id.cancle);
        ok = (Button) dialog.findViewById(R.id.ok);


        addph = (TextView) dialog.findViewById(R.id.addphn);
        addcnf = (TextView) dialog.findViewById(R.id.addcnf);
        addpass = (TextView) dialog.findViewById(R.id.addpass);
        adderrmail = (TextView) dialog.findViewById(R.id.adderroremail);
        adderruser = (TextView) dialog.findViewById(R.id.adderrusername);

        String Strnm = userprofileUserBean.getAddressStreet();
        String zipcd = userprofileUserBean.getAddressZipcode();
        String Statenm = userprofileUserBean.getAddressState();
        String Citynm = userprofileUserBean.getAddressCity();
        String Countrynm = userprofileUserBean.getAddressCountry();

        StreetName.setText(Strnm);
        AddStreetName = StreetName.getText().toString();

        ZipCode.setText(zipcd);
        AddZipCode = ZipCode.getText().toString();
        StateName.setText(Statenm);
        AddStateName = StateName.getText().toString();

        CityName.setText(Citynm);
        AddCityName = CityName.getText().toString();
        Country.setText(Countrynm);
        AddCountry = Country.getText().toString();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StreetName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        AddStreetName = StreetName.getText().toString();

                        //System.out.println();
                        StreetCheck = userprofileUserBean.validateAddressStreet(StreetName.getText().toString());
                        adderruser.setVisibility(View.VISIBLE);
                        adderruser.setText(StreetCheck);
                        if (StreetCheck.equals("Ok")) {
                            adderruser.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                ZipCode.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        AddZipCode = ZipCode.getText().toString();

                        zipCheck = userprofileUserBean.validateAddressZipcode(ZipCode.getText().toString());
                        adderrmail.setVisibility(View.VISIBLE);
                        adderrmail.setText(zipCheck);
                        if (zipCheck.equals("Ok")) {
                            adderrmail.setVisibility(View.GONE);
                        }
                    }
                });

                StateName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        AddStateName = StateName.getText().toString();

                        StateCheck = userprofileUserBean.validateAddressState(StateName.getText().toString());
                        addpass.setVisibility(View.VISIBLE);
                        addpass.setText(StateCheck);
                        if (StateCheck.equals("Ok")) {
                            addpass.setVisibility(View.GONE);
                        }
                    }
                });

                CityName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        AddCityName = CityName.getText().toString();

                        System.out.println(AddCityName);
                        CityCheck = userprofileUserBean.validateAddressCity(AddCityName);

                        if (CityCheck.equals("Ok")) {
                            addcnf.setVisibility(View.GONE);
                        } else {
                            addcnf.setVisibility(View.VISIBLE);
                            addcnf.setText(CityCheck);
                        }
                    }
                });

                Country.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        AddCountry = Country.getText().toString();

                        CountryCheck = userprofileUserBean.validateAddressCountry(Country.getText().toString());
                        addph.setVisibility(View.VISIBLE);
                        addph.setText(CountryCheck);
                        if (CountryCheck.equals("Ok")) {
                            addph.setVisibility(View.GONE);
                        }

                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String MainAddress = AddStreetName + " " + AddZipCode + " " + AddStateName + " " + AddCityName + " " + AddCountry;


                        if (MainAddress != null && !MainAddress.equals("")) {
                            new GeocoderTask().execute(MainAddress);
                        }
                        dialog.dismiss();
                    }
                });

                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }

        });


        //System.out.println("This is Address"+StreetName.getText().toString()+ZipCode.getText().toString().equals("")+StateName.getText().toString()+CityName.getText().toString()+Country.getText().toString());
        Button photo = (Button) findViewById(R.id.nextphoto);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(UserprofileMapsActivity.StreetName1);
                System.out.println(UserprofileMapsActivity.StreetName1.getText());

                if (StreetName.getText().toString().equals("") || StreetName.getText().toString() == null || !StreetCheck.equals("Ok") ||
                        ZipCode.getText().toString().equals("") || ZipCode.getText().toString() == null || !zipCheck.equals("Ok") ||
                        StateName.getText().toString().equals("") || StateName.getText().toString() == null || !StateCheck.equals("Ok") ||
                        CityName.getText().toString().equals("") || CityName.getText().toString() == null || !CityCheck.equals("Ok") ||
                        Country.getText().toString().equals("") || Country.getText().toString() == null || !CountryCheck.equals("Ok")) {
                    Toast.makeText(UserprofileMapsActivity.this, "One or more are fields are not properly filled", Toast.LENGTH_SHORT).show();
                } else {
                    UserprofileMainActivity obj1 = new UserprofileMainActivity();
                    obj1.photo();
                }
            }
        });

    }


    public void Location() {

        gps = new UserprofileGPSTracker(UserprofileMapsActivity.this);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            //   userprofileUserBean.setLatitude(String.valueOf(latitude));
            //   userprofileUserBean.setLongitude(String.valueOf(longitude));
        } else {
            gps.showSettingsAlert();
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in India").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14.0f));


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng ll = marker.getPosition();
                //   userprofileUserBean.setLatitude(String.valueOf(ll.latitude));
                //   userprofileUserBean.setLongitude(String.valueOf(ll.longitude));

            }
        });

        // An AsyncTask class for accessing the GeoCoding Web Service


    }

    class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mView.show(getSupportFragmentManager(), "");

        }

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            mView.dismiss();

            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map
            mMap.clear();

            // Adding Markers on Google Map for each matching address
            for (int i = 0; i < addresses.size(); i++) {

                Address address = addresses.get(i);

                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());

                String addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());

                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.draggable(true);
                markerOptions.title(addressText);
                mMap.addMarker(markerOptions);


                mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {

                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {

                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        LatLng ll = marker.getPosition();
                        //  userprofileUserBean.setLatitude(String.valueOf(ll.latitude));
                        //   userprofileUserBean.setLongitude(String.valueOf(ll.longitude));

                    }
                });

                // Locate the first location
                if (i == 0)
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }
}
