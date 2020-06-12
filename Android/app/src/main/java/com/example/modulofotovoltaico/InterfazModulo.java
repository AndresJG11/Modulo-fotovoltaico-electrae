package com.example.modulofotovoltaico;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;

public class InterfazModulo extends AppCompatActivity{

    private String nombreModulo;

    private GridView gridData;
    private GridAdapter gridAdapter;
    private ArrayList<Sensor> dataSensores;
    public String strData[] = {"NaN", "NaN", "NaN", "NaN", "NaN", "NaN", "NaN"};

    private Bluetooth BT;
    private GraphView graphView;

    private ArrayList<LineGraphSeries> serieDatos = new ArrayList<>();
    private ArrayList<LineGraphSeries> serieDatosGraph = new ArrayList<>();

    private double xActual = 0.0;
    private double xAnt = 0.0;
    private double delayTime = 1.0; // Frecuencia de envio en segundos

    private Modulo modulo;



    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        modulo = (Modulo)getIntent().getExtras().getSerializable("Modulo");
        setContentView(R.layout.modulo_panel);
        Toast.makeText(getApplicationContext(),"Conectando...",Toast.LENGTH_SHORT).show();
        init();

        BT = new Bluetooth(nombreModulo);
        /*
        if(!BT.isConnected){
            Toast.makeText(getApplicationContext(), "Imposible conectar, revisa la conexion e ingresa de nuevo", Toast.LENGTH_LONG).show();
        }*/

    }

    @Override
    protected void onPause() {
        BT.disconnect();
        super.onPause();
        finish();
    }


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Bluetooth.SUCCESS_CONNECT:
                    Bluetooth.connectedThread = new Bluetooth.ConnectedThread((BluetoothSocket) msg.obj);
                    Toast.makeText(getApplicationContext(), "Conectado", Toast.LENGTH_LONG).show();
                    Bluetooth.connectedThread.start();
                    break;
                case Bluetooth.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;

                    String strIncom = new String(readBuf, 0, msg.arg1);

                    // Se obtienen los datos separados por comas
                    strData = strIncom.split(",");
                    // Se actualiza el valor de x y se grafican los datos recibidos
                    xActual = xAnt + delayTime;
                    xAnt = xActual;
                    // Grafica los datos en orden de llegada
                    int j = 0;
                    for(LineGraphSeries serie:serieDatos){
                        double yValue = Double.parseDouble(strData[j]);
                        serie.appendData(new DataPoint(xActual,yValue),true,100);
                        j++;
                    }

                    // Actualiza los TextView con los datos recibidos
                    actualizarSensores();
                    break;
            }
        }

        public boolean isFloatNumber(String num) {
            try {
                Double.parseDouble(num);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }
    };

    void init() {
        Bluetooth.gethandler(mHandler);

        //dataSensores = prepareDataSet();
        dataSensores = modulo.getSensores();
        nombreModulo = modulo.getNombre();
        gridData = (GridView) findViewById(R.id.gridData);

        gridAdapter = new GridAdapter(this, dataSensores);

        gridData.setAdapter(gridAdapter);

        graphView = (GraphView) findViewById(R.id.graph);

        for(int i = 0;i<dataSensores.size();i++){
            LineGraphSeries serie = new LineGraphSeries();
            serie.setColor(dataSensores.get(i).getColor());
            serie.setThickness(5);
            // serie.setDrawDataPoints(true);
            // serie.setDataPointsRadius(10);
            serieDatos.add(serie);
            //graphView.addSeries(serie);
        }

        // activate horizontal zooming and scrolling
        graphView.getViewport().setScalable(true);

        // activate horizontal scrolling
        graphView.getViewport().setScrollable(true);

        // activate horizontal and vertical zooming and scrolling
        graphView.getViewport().setScalableY(true);

        // activate vertical scrolling
        graphView.getViewport().setScrollableY(true);

        gridData.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                // Obtiene la serie seleccionada
                LineGraphSeries serieSelected = serieDatos.get(position);

                // Busca entre las series graficadas para eliminar o agregar al plot
                if(serieDatosGraph.contains(serieSelected)){
                    gridAdapter.setEnfasis(position,false);
                    serieDatosGraph.remove(serieSelected);
                    graphView.removeSeries(serieSelected);
                } else {
                    gridAdapter.setEnfasis(position,true);
                    graphView.addSeries(serieSelected);
                    serieDatosGraph.add(serieSelected);
                }
            }
        });
    }

    public void actualizarSensores() {
        // Se actualiza cada elemento del grid con cada dato nuevo
        for (int i = 0; i < dataSensores.size(); i++) {
            gridAdapter.setMedida(i, strData[i]);
        }
        gridData.setAdapter(gridAdapter);
    }

}
