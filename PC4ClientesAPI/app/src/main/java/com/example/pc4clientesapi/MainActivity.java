package com.example.pc4clientesapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pc4clientesapi.API.ClienteAPI;
import com.example.pc4clientesapi.API.RetrofitCliente;
import com.example.pc4clientesapi.Models.ClienteData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btnlistarweb, btnagregarweb;
    EditText txtnombre, txtdni, txttelefono, txtcorreo;
    Spinner snptipocliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txtdni = (EditText) findViewById(R.id.txtdni);
        txttelefono = (EditText) findViewById(R.id.txttelefono);
        txtcorreo = (EditText) findViewById(R.id.txtcorreo);
        snptipocliente = (Spinner) findViewById(R.id.spntipocliente);

        btnlistarweb = (Button) findViewById(R.id.btnlistarweb);
        btnlistarweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainListarClientes.class);
                startActivity(intent);
            }
        });

        btnagregarweb = (Button) findViewById(R.id.btnagregarweb);
        btnagregarweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtnombre.getText().toString();
                String dni = txtdni.getText().toString();
                String telefono = txttelefono.getText().toString();
                String correo = txtcorreo.getText().toString();

                int itempos = snptipocliente.getSelectedItemPosition();
                String tipoCliente = "";

                switch (itempos){
                    case 0:
                        tipoCliente = "64961e89d4f0026577601d33";
                        break;
                    case 1:
                        tipoCliente = "64961e89d4f0026577601d34";
                        break;
                    case 2:
                        tipoCliente = "64961e89d4f0026577601d35";
                        break;
                    case 3:
                        tipoCliente = "64961e89d4f0026577601d36";
                        break;
                }

                ClienteAPI clienteAPI = RetrofitCliente.getInstance().create(ClienteAPI.class);
                final ClienteData cliente = new ClienteData(nombre,dni,telefono,correo,"A",tipoCliente);
                Call<ClienteData> call = clienteAPI.addCliente(cliente);
                call.enqueue(new Callback<ClienteData>() {
                    @Override
                    public void onResponse(Call<ClienteData> call, Response<ClienteData> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"OK2",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ClienteData> call, Throwable t) {
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

                txtnombre.setText("");
                txtdni.setText("");
                txttelefono.setText("");
                txtcorreo.setText("");
                snptipocliente.setSelection(0);
            }
        });
    }
}