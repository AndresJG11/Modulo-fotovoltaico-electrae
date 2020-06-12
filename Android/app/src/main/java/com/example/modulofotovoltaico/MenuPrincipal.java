package com.example.modulofotovoltaico;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {

    Button btnPanel;
    Button btnControlador;
    Button btnInversor;

    private Modulo moduloPanel;
    String nombrePanel = "HC-06";

    private Modulo moduloControlador;
    String nombreControlador = "HC-06";

    private Modulo moduloInversor;
    String nombreInversor = "HC-06";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu_principal);

        initButton();

        ArrayList<Sensor> sensoresPanel = getSensoresPanel();
        moduloPanel = new Modulo(nombrePanel,sensoresPanel);

        ArrayList<Sensor> sensoresControlador = getSensoresControlador();
        moduloControlador = new Modulo(nombreControlador,sensoresControlador);

        ArrayList<Sensor> sensoresInversor= getSensoresInversor();
        moduloInversor = new Modulo(nombreInversor,sensoresInversor);
    }


    private ArrayList<Sensor> getSensoresPanel() {
        // Se declaran los sensores ubicados en el modulo del panel solar
        ArrayList<Sensor> sensores = new ArrayList<>();
        Sensor sensor;


        // Temperatura
        sensor = new Sensor("Temperatura");
        sensor.setMedida("NaN");
        sensor.setColor("#708AE4"); // Color del BackGround en Hexa
        sensores.add(sensor);


        // Humedad
        sensor = new Sensor("Humedad");
        sensor.setMedida("NaN");
        sensor.setColor("#9770E4");
        sensores.add(sensor);

        // Luz UV
        sensor = new Sensor("Luz UV");
        sensor.setMedida("NaN");
        sensor.setColor("#379EBF");
        sensores.add(sensor);

        // Luz IR
        sensor = new Sensor("Luz IR");
        sensor.setMedida("NaN");
        sensor.setColor("#DBE470");
        sensores.add(sensor);

        // Voltaje
        sensor = new Sensor("Voltaje");
        sensor.setMedida("NaN");
        sensor.setColor("#E49E70");
        sensores.add(sensor);

        // Corriente
        sensor = new Sensor("Corriente");
        sensor.setMedida("NaN");
        sensor.setColor("#E470A5");
        sensores.add(sensor);

        // Potencia
        sensor = new Sensor("Potencia");
        sensor.setMedida("NaN");
        sensor.setColor("#6DBF37");
        sensores.add(sensor);

        return sensores;
    }

    private ArrayList<Sensor> getSensoresControlador(){
        // Se declaran los sensores ubicados en el modulo del controlador de carga
        ArrayList<Sensor> sensores = new ArrayList<>();
        Sensor sensor;

        // Voltaje
        sensor = new Sensor("Voltaje");
        sensor.setMedida("NaN");
        sensor.setColor("#E49E70");
        sensores.add(sensor);

        // Corriente
        sensor = new Sensor("Corriente");
        sensor.setMedida("NaN");
        sensor.setColor("#E470A5");
        sensores.add(sensor);

        // Potencia
        sensor = new Sensor("Potencia");
        sensor.setMedida("NaN");
        sensor.setColor("#6DBF37");
        sensores.add(sensor);

        return sensores;
    }

    private ArrayList<Sensor> getSensoresInversor(){
        // Se declaran los sensores ubicados en el modulo del inversor
        ArrayList<Sensor> sensores = new ArrayList<>();
        Sensor sensor;

        // Voltaje
        sensor = new Sensor("Voltaje");
        sensor.setMedida("NaN");
        sensor.setColor("#E49E70");
        sensores.add(sensor);

        // Corriente
        sensor = new Sensor("Corriente");
        sensor.setMedida("NaN");
        sensor.setColor("#E470A5");
        sensores.add(sensor);

        // Potencia
        sensor = new Sensor("Potencia");
        sensor.setMedida("NaN");
        sensor.setColor("#6DBF37");
        sensores.add(sensor);

        // Frecuencia
        sensor = new Sensor("Frecuencia");
        sensor.setMedida("NaN");
        sensor.setColor("#9770E4");
        sensores.add(sensor);

        return sensores;
    }

    private void initButton() {
        btnPanel = findViewById(R.id.btnPanel);
        btnPanel.setOnClickListener(this);

        btnControlador = findViewById(R.id.btnControlador);
        btnControlador.setOnClickListener(this);

        btnInversor = findViewById(R.id.btnInversor);
        btnInversor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        System.gc();
        switch (v.getId()) {
            case R.id.btnPanel:
                Intent intent = new Intent (v.getContext(), InterfazModulo.class);
                intent.putExtra("Modulo",moduloPanel);
                startActivity(intent);
                break;
            case R.id.btnControlador:
                Intent intentControlador = new Intent (v.getContext(), InterfazModulo.class);
                intentControlador.putExtra("Modulo",moduloControlador);
                startActivity(intentControlador);
                //Toast.makeText(getApplicationContext(),"Controlador", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnInversor:
                Intent intentInversor = new Intent (v.getContext(), InterfazModulo.class);
                intentInversor.putExtra("Modulo",moduloInversor);
                startActivity(intentInversor);
                //Toast.makeText(getApplicationContext(),"Inversor", Toast.LENGTH_SHORT).show();
                break;

        }

    }
}
