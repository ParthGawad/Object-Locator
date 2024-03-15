package com.example.objectlocatorappcapstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.List;
import java.util.Locale;
import io.realm.Realm;

public class MainActivity2 extends AppCompatActivity {
EditText nametext,loctext;
AppCompatButton locbtn, createbtn,set_loc;
ImageButton back;
private final static int REQUEST_CODE= 100;
public String loca = " ";
FusedLocationProviderClient fusedLocationProviderClient;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        back = findViewById(R.id.backto1);
        nametext = findViewById(R.id.nametext);
        loctext = findViewById(R.id.loctext);
        locbtn = findViewById(R.id.loc);
        createbtn = findViewById(R.id.create);
        set_loc = findViewById(R.id.set_loc);

        Intent intent3 = getIntent();
        String l1 = intent3.getStringExtra("name");
        String l2 = intent3.getStringExtra("location");
        nametext.setText(l1);
        loctext.setText(l2);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent2);
            }
        });


        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NameObj = " ";
                NameObj =  nametext.getText().toString();
                String Location1 = loctext.getText().toString();

                if(NameObj.isEmpty()) {
                    Toast toast = new Toast(getApplicationContext());
                    View view1 = getLayoutInflater().inflate(R.layout.customtoast_create_object,findViewById(R.id.ViewContainer));
                    toast.setView(view1);
                    TextView txt = view1.findViewById(R.id.txt);
                    txt.setText("Enter the Name of the Object");
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,300);
                    toast.show();
                }

                if(Location1.isEmpty()) {
                    Toast toast = new Toast(getApplicationContext());
                    View view1 = getLayoutInflater().inflate(R.layout.customtoast_create_object,findViewById(R.id.ViewContainer));
                    toast.setView(view1);
                    TextView txt = view1.findViewById(R.id.txt);
                    txt.setText("Tap location button to get location");
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,300);
                    toast.show();
                }

                if(!(NameObj.isEmpty() || Location1.isEmpty())) {
                    realm.beginTransaction();
                    Note note = realm.createObject(Note.class);
                    note.setText_name(NameObj);
                    note.setText_location(Location1);
                    realm.commitTransaction();

                    Toast toast = new Toast(getApplicationContext());
                    View view1 = getLayoutInflater().inflate(R.layout.customtoast_create_object, findViewById(R.id.ViewContainer));
                    toast.setView(view1);
                    TextView txt = view1.findViewById(R.id.txt);
                    txt.setText("Object Created");
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 300);
                    toast.show();
                    finish();
                }
            }
        });

        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getLastLocation();
            }
        });
    }
    public void getLastLocation() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null) {
                                Geocoder geocoder = new Geocoder(MainActivity2.this, Locale.getDefault());
                                List<Address> addressList = null;
                                try {
                                    addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    loca = addressList.get(0).getAddressLine(0).toString();
                                    loctext.setText(loca);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
        }else {
            askPermission();
        }
    }

    public void askPermission() {
        ActivityCompat.requestPermissions(MainActivity2.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this,"Request Required",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}