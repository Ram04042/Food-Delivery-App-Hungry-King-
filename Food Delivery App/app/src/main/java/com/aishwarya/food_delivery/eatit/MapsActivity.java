package com.aishwarya.food_delivery.eatit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.aishwarya.food_delivery.eatit.Common.Common;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions markeropt;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);




//








        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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



        String stringgaddress = Common.currentRequest.getAddress();

//        Log.d("KEK", stringgaddress);

        Geocoder geocoder =new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try
        {
            list = geocoder.getFromLocationName(stringgaddress,1);
            Log.d("debug", "onMapReady: "+list);


        }catch (IOException e){
            Log.e("Debug", "displayLocation: IOException"+e.getMessage());
        }


        if(list.size()>0)
        {
            Address address = list.get(0);

            Log.d("debug", "onCreate: "+address.getLatitude());

            LatLng orderLocation = new LatLng(address.getLatitude(),address.getLongitude());

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.box);
            bitmap = Common.scaleBitmap(bitmap, 70,70);

            MarkerOptions markerOptions =  new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                    .title("Order of "+Common.currentRequest.getPhone())
                    .position(orderLocation);
            mMap.addMarker(markerOptions);
        }



        // Add a marker in Sydney and move the camera
        final String edtPhone ="085249169576";

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Shipper");

        LatLng test = new LatLng(0.0,0.0);
        final Marker newmarker = mMap.addMarker(new MarkerOptions()
                .position(test).title("Staff"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(test));





        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                LatLng stafflocn = new LatLng(
                        dataSnapshot.child(edtPhone).child("lat").getValue(double.class),
                        dataSnapshot.child(edtPhone).child("lon").getValue(double.class)
                );
//                markeropt=new MarkerOptions().position(stafflocn).title("Staff");
//                marker = mMap.addMarker(markeropt);
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(stafflocn));
                newmarker.setPosition(stafflocn);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(stafflocn));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                           }
        });




    }
}
