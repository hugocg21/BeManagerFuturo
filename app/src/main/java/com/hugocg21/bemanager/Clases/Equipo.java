package com.hugocg21.bemanager.Clases;

public class Equipo {
    private String nombreEquipo, categoriaEquipo, sedeEquipo;
    private int numJugadoresEquipo;

    public Equipo(String nombreEquipo, String categoriaEquipo, String sedeEquipo, int numJugadoresEquipo) {
        this.nombreEquipo = nombreEquipo;
        this.categoriaEquipo = categoriaEquipo;
        this.sedeEquipo = sedeEquipo;
        this.numJugadoresEquipo = numJugadoresEquipo;
    }

    public Equipo() {

    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getCategoriaEquipo() {
        return categoriaEquipo;
    }

    public void setCategoriaEquipo(String categoriaEquipo) {
        this.categoriaEquipo = categoriaEquipo;
    }

    public String getSedeEquipo() {
        return sedeEquipo;
    }

    public void setSedeEquipo(String sedeEquipo) {
        this.sedeEquipo = sedeEquipo;
    }

    public int getNumJugadoresEquipo() {
        return numJugadoresEquipo;
    }

    public void setNumJugadoresEquipo(int numJugadoresEquipo) {
        this.numJugadoresEquipo = numJugadoresEquipo;
    }
}
