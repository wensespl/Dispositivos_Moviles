package com.example.pc3app;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

public class MyAdaptador extends BaseAdapter {
    StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    StorageReference ref = storageReference.child("images");
    Context context;
    public MyAdaptador(Context c){
        context = c;
    }

    @Override
    public int getCount() {
        return MainActivity.jugadores.size();
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.jugadores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View fila;
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
            fila = inflater.inflate(R.layout.misfilas,null);
        }else {
            fila = convertView;
        }

        TextView txtminombre = (TextView) fila.findViewById(R.id.txtminombre);
        TextView txtmipuntaje = (TextView) fila.findViewById(R.id.txtmipuntaje);
        ImageView imgImagen = (ImageView) fila.findViewById(R.id.imgimagen);

        txtminombre.setText(MainActivity.jugadores.get(position).getNombre());
        txtmipuntaje.setText(""+MainActivity.jugadores.get(position).getPuntaje());
        mostrarimagen(imgImagen,MainActivity.jugadores.get(position).getKey());

        return fila;
    }
    private void mostrarimagen(final ImageView imageView, final String key) {
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for(StorageReference item:listResult.getItems()){
                    if(item.getName().equals(key)){
                        item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(context)
                                        .load(uri)
                                        .override(75,75)
                                        .error(R.drawable.playeravatar)
                                        .into(imageView);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("XXX",e.toString());
                            }
                        });
                        break;
                    }
                }
            }
        });
    }
}
