package com.example.modulofotovoltaico;

import java.io.Serializable;
import java.util.ArrayList;

public class Modulo implements Serializable {
    private ArrayList<Sensor> sensores;
    private String nombre;  // Igual a como aparece la red BT

    public Modulo(String nombre, ArrayList<Sensor> sensores){
        this.nombre = nombre;
        this.sensores = sensores;
    }

    public ArrayList<Sensor> getSensores() {
        return sensores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setSensores(ArrayList<Sensor> sensores) {
        this.sensores = sensores;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
