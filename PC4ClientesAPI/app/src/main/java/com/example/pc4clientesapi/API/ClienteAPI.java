package com.example.pc4clientesapi.API;


import com.example.pc4clientesapi.Models.Cliente;
import com.example.pc4clientesapi.Models.ClienteData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClienteAPI {
    @GET("clientes")
    Observable<List<Cliente>> getClientes();

    @POST("clientes")
    Call<ClienteData> addCliente(@Body ClienteData cliente);

    @GET("clientes/{idCliente}")
    Observable<Cliente> getClienteById(@Path("idCliente") String idCliente);

    @PUT("clientes/{idCliente}")
    Call<Cliente> updateClienteById(@Path("idCliente") String idCliente, @Body ClienteData cliente);

    @DELETE("clientes/{idCliente}")
    Call<Object> deleteClienteById(@Path("idCliente") String idCliente);
}
