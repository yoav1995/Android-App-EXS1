package com.example.exs1.Fregmants;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.exs1.Adapters.HighscoreAdapter;
import com.example.exs1.Interfaces.CoordinateCallBack;
import com.example.exs1.Interfaces.HighScoreCallBack;
import com.example.exs1.Models.HighScore;
import com.example.exs1.R;
import com.example.exs1.Utilities.DataManager;
import com.example.exs1.Utilities.MySP;
import com.example.exs1.Utilities.SignalGen;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;


public class MapFragment extends Fragment {
    private HighScoreCallBack highScoreCallBack;
    private CoordinateCallBack coordinateCallBack;
    private LatLng latLng;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_fregmant, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        HighscoreAdapter highscoreAdapter=new HighscoreAdapter(DataManager.getHighScores());
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view.getContext());
        Dexter.withContext(view.getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).
                withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getCurrentLocationMap(view.getContext());
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.cancelPermissionRequest();
                    }
                }).check();
    }

    public void zoomOnRecord(double latitude, double longitude) {
        supportMapFragment.getMapAsync(googleMap -> {
            LatLng user = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions()
                    .position(user)
                    .title("User Marker"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user, 15));
        });
    }

    private void getCurrentLocationMap(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> supportMapFragment.getMapAsync(googleMap -> {
            if (location != null) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Current Location");
                googleMap.addMarker(markerOptions);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                handleHighScores(location.getLatitude(),location.getLongitude());
                //String json = new Gson().toJson(latLng);
               // MySP.getInstance().putString("MapFragment", json);
            } else {
                SignalGen.getInstance().toast("Permission Denied", Toast.LENGTH_SHORT);
            }
        }));
    }
    private void handleHighScores(double lat, double lng) {
        ArrayList<HighScore> highscores=DataManager.getHighScores();
        highscores.get(highscores.size()-1).setLat(lat).setLng(lng);
        String highScoreJson=new Gson().toJson(highscores);
        MySP.getInstance().putString("usersDetails",highScoreJson);
        Log.d("foofoo", "handleHighScores: "+" "+highscores.toString());
    }
}