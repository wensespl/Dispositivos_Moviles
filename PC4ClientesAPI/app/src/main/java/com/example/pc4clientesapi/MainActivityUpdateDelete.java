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
import com.example.pc4clientesapi.Models.Cliente;
import com.example.pc4clientesapi.Models.ClienteData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityUpdateDelete extends AppCompatActivity {
    Button btnupdateweb, btndeleteweb, btncancelar;
    EditText txtnombrenew, txtdninew, txttelefononew, txtcorreonew;
    Spinner snptipoclientenew;
    String idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_update_delete);

        txtnombrenew = (EditText) findViewById(R.id.txtnombrenew);
        txtdninew = (EditText) findViewById(R.id.txtdninew);
        txttelefononew = (EditText) findViewById(R.id.txttelefononew);
        txtcorreonew = (EditText) findViewById(R.id.txtcorreonew);
        snptipoclientenew = (Spinner) findViewById(R.id.spntipoclientenew);

        Intent clienteintent = getIntent();
        Bundle b = clienteintent.getExtras();
        if (b!= null){
            idCliente = (String) b.get("idcliente");
            String nombre = (String) b.get("nombre");
            String dni = (String) b.get("dni");
            String telefono = (String) b.get("telefono");
            String correo = (String) b.get("correo");

            String tipocliente = (String) b.get("tipocliente");
            int spnpos = 0;
            switch (tipocliente){
                case "Ocasional":
                    spnpos = 0;
                    break;
                case "Regular":
                    spnpos = 1;
                    break;
                case "Frecuente":
                    spnpos = 2;
                    break;
                case "Embajador":
                    spnpos = 3;
                    break;
            }

            txtnombrenew.setText(nombre);
            txtdninew.setText(dni);
            txttelefononew.setText(telefono);
            txtcorreonew.setText(correo);
            snptipoclientenew.setSelection(spnpos);
        }

        btncancelar = (Button) findViewById(R.id.btncancelar);
        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(MainActivityUpdateDelete.this, MainActivity.class);
                startActivity(goHome);
                finish();
            }
        });

        btnupdateweb = (Button) findViewById(R.id.btnactualizarweb);
        btnupdateweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombrenew = txtnombrenew.getText().toString();
                String dninew = txtdninew.getText().toString();
                String telefononew = txttelefononew.getText().toString();
                String correonew = txtcorreonew.getText().toString();

                int itempos = snptipoclientenew.getSelectedItemPosition();
                String tipoClientenew = "";

                switch (itempos){
                    case 0:
                        tipoClientenew = "64961e89d4f0026577601d33";
                        break;
                    case 1:
                        tipoClientenew = "64961e89d4f0026577601d34";
                        break;
                    case 2:
                        tipoClientenew = "64961e89d4f0026577601d35";
                        break;
                    case 3:
                        tipoClientenew = "64961e89d4f0026577601d36";
                        break;
                }

                ClienteAPI clienteAPI = RetrofitCliente.getInstance().create(ClienteAPI.class);
                final ClienteData cliente = new ClienteData(nombrenew,dninew,telefononew,correonew,"A",tipoClientenew);
                Call<Cliente> call = clienteAPI.updateClienteById(idCliente,cliente);
                call.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(MainActivityUpdateDelete.this,"OK ACTUALIZADO",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivityUpdateDelete.this,"OK ACTUALIZADO 2",Toast.LENGTH_LONG).show();
                        }
                        Intent goHome = new Intent(MainActivityUpdateDelete.this, MainActivity.class);
                        startActivity(goHome);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {
                        Toast.makeText(MainActivityUpdateDelete.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btndeleteweb = (Button) findViewById(R.id.btneliminarweb);
        btndeleteweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClienteAPI clienteAPI = RetrofitCliente.getInstance().create(ClienteAPI.class);
                Call<Object> call = clienteAPI.deleteClienteById(idCliente);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(MainActivityUpdateDelete.this,"OK ELIMINADO",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivityUpdateDelete.this,"OK ELIMINADO 2",Toast.LENGTH_LONG).show();
                        }
                        Intent goHome = new Intent(MainActivityUpdateDelete.this, MainActivity.class);
                        startActivity(goHome);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Toast.makeText(MainActivityUpdateDelete.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}