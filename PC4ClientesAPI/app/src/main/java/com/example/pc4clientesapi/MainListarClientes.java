package com.example.pc4clientesapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pc4clientesapi.API.ClienteAPI;
import com.example.pc4clientesapi.API.RetrofitCliente;
import com.example.pc4clientesapi.Models.Cliente;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainListarClientes extends AppCompatActivity {
    ClienteAPI clienteAPI;
    ListView lvcontactoweb;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_listar_clientes);

        lvcontactoweb = (ListView) findViewById(R.id.lvcontactoweb);

        /*Conexion*/
        Retrofit retrofit = RetrofitCliente.getInstance();
        clienteAPI = retrofit.create(ClienteAPI.class);

        /*Cargar los datos del API*/
        cargardatos();
    }
    private void cargardatos(){
        compositeDisposable.add(clienteAPI.getClientes().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Cliente>>() {
            @Override
            public void accept(List<Cliente> clientes) throws Exception {
                mostrardatos(clientes);
            }
        }));
    }
    private void mostrardatos(List<Cliente> clientes){
        MiAdaptadorCliente miAdaptadorCliente = new MiAdaptadorCliente(this,clientes);
        lvcontactoweb.setAdapter(miAdaptadorCliente);
        lvcontactoweb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),clientes.get(position).getIdCliente(),Toast.LENGTH_LONG).show();
                Intent goUpdateDelete = new Intent(getApplicationContext(), MainActivityUpdateDelete.class);
                goUpdateDelete.putExtra("idcliente", clientes.get(position).getIdCliente());
                goUpdateDelete.putExtra("nombre", clientes.get(position).getNombre());
                goUpdateDelete.putExtra("dni", clientes.get(position).getDni());
                goUpdateDelete.putExtra("telefono", clientes.get(position).getTelefono());
                goUpdateDelete.putExtra("correo", clientes.get(position).getCorreo());
                goUpdateDelete.putExtra("tipocliente", clientes.get(position).getTipoCliente().getNombre());
                startActivity(goUpdateDelete);
                finish();
            }
        });
    }

    @Override
    protected void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }
}