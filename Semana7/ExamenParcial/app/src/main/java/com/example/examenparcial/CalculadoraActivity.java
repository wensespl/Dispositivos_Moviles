package com.example.examenparcial;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class CalculadoraActivity extends AppCompatActivity {

    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        resultado = (TextView) findViewById(R.id.resultado);
    }

    public void addNumero(View view) {
        String numero = ((TextView) view).getText().toString();
        resultado.setText(resultado.getText() + numero);
    }

    public void addOperacion(View view) {
        String operacion = ((TextView) view).getText().toString();
        resultado.setText(resultado.getText() + operacion);
    }

    public void borrar(View view) {
        resultado.setText("");
    }

    public void calcular(View view) {
        String entrada = resultado.getText().toString();
        String[] tokens = entrada.split("(?<=[-+*/])|(?=[-+*/])");

        float result = Float.parseFloat(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            if (Objects.equals(tokens[i], "+")) {
                result += Float.parseFloat(tokens[i+1]);
            } else if (Objects.equals(tokens[i], "-")) {
                result -= Float.parseFloat(tokens[i+1]);
            } else if (Objects.equals(tokens[i], "*")) {
                result *= Float.parseFloat(tokens[i+1]);
            } else if (Objects.equals(tokens[i], "/")) {
                result /= Float.parseFloat(tokens[i+1]);
            }
        }

        resultado.setText(""+result);
    }

    public void regresar(View view) {
        finish();
    }
}