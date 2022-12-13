package com.asuni.assignment.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.asuni.assignment.R;
import com.asuni.assignment.db.entity.LocModel;
import com.asuni.assignment.viewmodels.ViewModal;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.android.SphericalUtil;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Geocoder geocoder;

    EditText searchEditText;
    ListView searchResultList;
    ProgressBar searchProgress;

    public Address currentSelect;

    RelativeLayout mapToolBar;

    Button saveBTN;

    private ViewModal viewmodal;

    LocModel locModel1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locModel1 = (LocModel) getIntent().getSerializableExtra("model");


        viewmodal = ViewModelProviders.of( this ).get( ViewModal.class );

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        (findViewById(R.id.backBTN)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void myMoveCamera1(Address address) {

        map.clear();

        mapToolBar.setVisibility(View.VISIBLE);

        LatLng sydney = new LatLng( address.getLatitude(), address.getLongitude() );

        map.addMarker(new MarkerOptions().position(sydney).title( address.getAddressLine(0) ).visible(true) );

        map.animateCamera(CameraUpdateFactory.zoomTo(20));

        CameraUpdate center = CameraUpdateFactory.newLatLng( sydney );

        map.moveCamera(center);

        map.animateCamera( CameraUpdateFactory.newLatLng( sydney ) );

    }

    private void myMoveCamera1() {

        map.clear();

        mapToolBar.setVisibility(View.VISIBLE);

        LatLng sydney = new LatLng( Double.parseDouble( locModel1.getLat() ),Double.parseDouble( locModel1.getLog() ) );

        map.addMarker(new MarkerOptions().position(sydney).title( locModel1.getAddress() ).visible(true) );

        map.animateCamera(CameraUpdateFactory.zoomTo(20));

        CameraUpdate center = CameraUpdateFactory.newLatLng( sydney );

        map.moveCamera(center);

        map.animateCamera( CameraUpdateFactory.newLatLng( sydney ) );

    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        this.map = mMap;

        geocoder = new Geocoder( getApplicationContext() );

        LatLng sydney = new LatLng(28.679079, 77.391029);
        map.animateCamera( CameraUpdateFactory.newLatLng( sydney ) );
        map.animateCamera(CameraUpdateFactory.zoomTo(10));


        boolean b = getIntent().getBooleanExtra("flag",false);

        if( b )
            drawMarker();
        else
            searchList();

        LatLng center = mMap.getCameraPosition().target;

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {

            }
        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                LatLng center = mMap.getCameraPosition().target;
            }
        });

        mMap.setOnCameraMoveCanceledListener(new GoogleMap.OnCameraMoveCanceledListener() {
            @Override
            public void onCameraMoveCanceled() {

            }
        });
    }

    private class AsyncTaskExample extends AsyncTask<String, String, Boolean> {

        List<String> asStringList ;
        List<Address> addressList ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            asStringList = new ArrayList<>();

        }

        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                addressList = geocoder.getFromLocationName( strings[0] , 5);
                if( addressList.size() != 0 ) {

                    for (Address address : addressList)
                        if( address.getAddressLine(0).trim() == null || address.getAddressLine(0).trim() =="" )
                            asStringList.add("no Name");
                        else
                            asStringList.add( address.getAddressLine(0).trim() );
                    return  true;
                }else
                    return false;

            } catch (Exception e) {
//            Toast.makeText( getApplicationContext() , "Location Not found.", Toast.LENGTH_SHORT).show();
            }

            return  false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);

            ArrayAdapter<String> arr = new ArrayAdapter<String>( getApplicationContext() , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, asStringList );
            searchResultList.setAdapter(arr);
            searchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    myMoveCamera1( addressList.get(i) );
                    currentSelect = addressList.get(i);
                }
            });

            if( b ){
                searchResultList.setVisibility(View.VISIBLE);
            }else
                searchResultList.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    searchProgress.setVisibility(View.GONE);
                }
            },2000);
        }
    }

    @SuppressLint("MissingInflatedId")
    public void searchList() {

        ((TextView) findViewById(R.id.headerTitle) ).setText( getApplicationContext().getString (R.string.search_place ));

        Snackbar snackbar = Snackbar.make(  findViewById(android.R.id.content) , "", Snackbar.LENGTH_INDEFINITE);

        View customSnackView = getLayoutInflater().inflate(R.layout.search_shape, null);

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);

        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

        snackbarLayout.setPadding(0, 0, 0, 0);

        this.searchResultList = (customSnackView.findViewById(R.id.searchResultList));
        this.searchEditText = (customSnackView.findViewById(R.id.searchEditText));
        this.searchProgress = (customSnackView.findViewById(R.id.searchProgress));
        this.mapToolBar = (customSnackView.findViewById(R.id.mapToolBar));


        if( locModel1 != null ){

            searchEditText.setText( locModel1.getAddress() );
            myMoveCamera1(  );

        }

        searchEditText.setBackgroundResource(R.drawable.edit_text_share);

        searchEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence cs, int s, int b, int c) {

                mapToolBar.setVisibility(View.GONE);

                if( cs.toString().trim().equals("")){

                    searchProgress.setVisibility(View.GONE);
                    searchResultList.setVisibility(View.GONE);

                } else{
                    searchProgress.setVisibility(View.VISIBLE);
                    AsyncTaskExample asyncTask=new AsyncTaskExample();
                    asyncTask.execute( cs.toString().trim() );
                }
            }
            public void afterTextChanged(Editable editable) { }
            public void beforeTextChanged(CharSequence cs, int i, int j, int k) { }
        });

        this.saveBTN = customSnackView.findViewById(R.id.saveBTN);

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocModel locModel = new LocModel();

                Double distance=0.0;

                int size =  Home.adapter1.getItemCount();

                for( LocModel locModel1 : Home.adapter1.getCurrentList() ){
                    if( locModel1.getPrioriry() == 0 ){

                        distance = distance(Double.parseDouble( locModel1.getLat() ),currentSelect.getLatitude(),Double.parseDouble( locModel1.getLog() ),currentSelect.getLongitude() );

                        DecimalFormat df = new DecimalFormat("#.##");
                        distance = Double.valueOf( df.format(distance) );

                    }
                }


                if( size <= 0 ) {

                    locModel.setPrioriry( 0 );
                    locModel.setDistance( 0 );

                } else {

                    locModel.setDistance( distance );
                    locModel.setPrioriry( size );

                }

                if( currentSelect.getLocality()==null || currentSelect.getLocality().trim().equals(""))
                    locModel.setName( String.valueOf( currentSelect.getAdminArea()) );
                else
                    locModel.setName( String.valueOf( currentSelect.getLocality() ) );

                locModel.setLat( String.valueOf( currentSelect.getLatitude() ) );
                locModel.setLog( String.valueOf( currentSelect.getLongitude() ) );
                locModel.setAddress( String.valueOf( currentSelect.getAddressLine(0) ) );


                if( locModel1 != null ) {

                    locModel.setId( locModel1.getId() );
                    viewmodal.update( locModel );

                } else
                    viewmodal.insert( locModel );


                Toast.makeText(getApplicationContext() , "Place Locaiton Saved.",Toast.LENGTH_SHORT).show();

                finish();


            }
        });

        snackbarLayout.addView(customSnackView, 0);

        snackbar.show();

    }




    public static double distance(double lat1,double lat2, double lon1, double lon2)
    {

        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        double r = 6371;

        return(c * r);
    }


    public void drawMarker(){

        ((TextView) findViewById(R.id.headerTitle) ).setText( getApplicationContext().getString (R.string.routing ));

        List<LocModel> list = Home.adapter1.getCurrentList();

        for( LocModel model : list ){

            LatLng latLng = new LatLng( Double.parseDouble( model.getLat() ),Double.parseDouble( model.getLog() ) );
            map.addMarker( new MarkerOptions().position(latLng).title( model.getAddress() ));

        }

        LocModel model11 = list.get(0) ;

        for( int i=1; i < list.size(); i++ ) {


            String origin = model11.getLat()+","+model11.getLog();
            String destination = list.get(i).getLat()+","+list.get(i).getLog();

            rootDraw( origin  , destination );

            model11 = list.get(i);

        }


        if( !list.isEmpty()) {
            int mid = (int) list.size() / 2;
            LatLng fullRoutFor = new LatLng(Double.parseDouble(list.get(mid).getLat()), Double.parseDouble(list.get(mid).getLog()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(fullRoutFor, 5));
        }

        map.getUiSettings().setZoomControlsEnabled(true);



    }

    public void rootDraw( String origin , String destination ){


        List<LatLng> path = new ArrayList();

        GeoApiContext context = new GeoApiContext.Builder().apiKey(getString(R.string.api_kay)).build();

        DirectionsApiRequest req = DirectionsApi.getDirections(context, origin, destination);

        try {

            DirectionsResult res = req.await();

            if (res.routes != null && res.routes.length > 0) {

                DirectionsRoute route = res.routes[0];

                if (route.legs !=null) {

                    for(int i=0; i< route.legs.length; i++) {

                        DirectionsLeg leg = route.legs[i];

                        if (leg.steps != null) {

                            for (int j=0; j<leg.steps.length;j++){

                                DirectionsStep step = leg.steps[j];

                                if (step.steps != null && step.steps.length >0) {

                                    for (int k=0; k<step.steps.length;k++){
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }

                                    }

                                } else {

                                    EncodedPolyline points = step.polyline;

                                    if (points != null) {

                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }

                                    }

                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString() , Toast.LENGTH_SHORT).show();
        }

        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
            map.addPolyline(opts);
        }


    }


}
