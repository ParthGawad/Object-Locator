package com.example.objectlocatorappcapstoneproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.List;
import java.util.Locale;
import io.realm.Realm;
import io.realm.RealmResults;

public class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.ViewHolder> {
    Context context;
    RealmResults<Note> notesList;
    FusedLocationProviderClient fusedLocationProviderClient;
    public String loc2;
    RecyclerCardAdapter(Context context,RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_cards,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.tv1.setText(note.getText_name());
        holder.tv2.setText(note.getText_location());

        holder.del_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    note.deleteFromRealm();
                    realm.commitTransaction();
                    Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        holder.view_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String locate = (String) holder.tv2.getText();
                    Uri uri1 = Uri.parse("https://www.google.co.in/maps/search/" + locate);
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, uri1);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        holder.set_loc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                try {
                    String l1 = (String) holder.tv1.getText();
                    String l2 = (String) holder.tv2.getText();

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    note.deleteFromRealm();
                    realm.commitTransaction();

                    Intent intent3 = new Intent(context, MainActivity2.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent3.putExtra("name", l1);
                    intent3.putExtra("location", l2);
                    context.startActivity(intent3);
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        });

        holder.s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String n1 = (String) holder.tv1.getText();
                    String la1 = (String) holder.tv2.getText();

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plane");
                    share.putExtra(Intent.EXTRA_TEXT, "Name : " + n1 + "\nLocation : " + la1);
                    share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(share);
                }catch(Exception e) {
                    throw new RuntimeException(e);
                }
             }
        });

        holder.loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String loc1 = (String) holder.tv2.getText();
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
                    getnewlocation();
                    if(loc1.equals(loc2)) {
                        Toast.makeText(context,"You are close to your object",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context,"You are far away from your object",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            @SuppressLint("MissingPermission")
            private void getnewlocation() {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null) {
                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                            List<Address> addressList = null;
                            try {
                                addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                loc2 = addressList.get(0).getAddressLine(0).toString();
                            }catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2;
        AppCompatButton del_obj,view_loc,set_loc;
        ImageView s1,loc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            s1 = itemView.findViewById(R.id.share);
            loc = itemView.findViewById(R.id.loc);
            tv1 = itemView.findViewById(R.id.text_name);
            tv2 = itemView.findViewById(R.id.text_loc);
            del_obj = itemView.findViewById(R.id.del_object);
            view_loc = itemView.findViewById(R.id.view_loc);
            set_loc = itemView.findViewById(R.id.set_loc);
        }
    }
}