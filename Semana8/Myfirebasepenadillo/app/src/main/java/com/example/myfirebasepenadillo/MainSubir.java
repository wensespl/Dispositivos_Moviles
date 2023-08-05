package com.example.myfirebasepenadillo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class MainSubir extends AppCompatActivity {
    private Button btnseleccionar,btnsubir;
    private ImageView imgsubir;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    private static final int PICK_IMAGE_CODE = 71;
    private Uri filepath;
    Contacto contacto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_subir);

        btnseleccionar = (Button) findViewById(R.id.btnseleccionar);
        btnsubir = (Button) findViewById(R.id.btnsubir);
        imgsubir = ( ImageView) findViewById(R.id.imgsubir);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();//FirebaseStorage.getInstance().getReference(MainActivity.actual.getCodigo());

        contacto = new Contacto();
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null){
            String nombre =(String) b.get("nombre");
            String alias =(String) b.get("alias");
            String codigo =(String) b.get("codigo");
            String key =(String) b.get("key");
            contacto.setNombre(nombre);
            contacto.setAlias(alias);
            contacto.setCodigo(codigo);
            contacto.setKey(key);
        }

        btnseleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Seleccione imagen"),PICK_IMAGE_CODE);
            }
        });

        btnsubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filepath!=null){
                    StorageReference ref = storageReference.child("images/"+contacto.getKey());
                    ref.putFile(filepath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            btnsubir.setText("Subido");
                            //Intent intent = new Intent(MainSubir.this,ListarFirebase.class);
                            //startActivity(intent);
                            finish();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progreso = 100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount();
                            btnsubir.setText("Subiendo ..." + (int)progreso);
                        }
                    });
                    Toast.makeText(getApplicationContext(),"Subiendo...",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_CODE && resultCode== RESULT_OK && data!=null){
            filepath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                imgsubir.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}