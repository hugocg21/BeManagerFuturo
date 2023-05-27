package com.hugocg21.bemanager.Clases;

public class Jugador {
    String nombreJugador, apellidosJugador, equipoJugador, posicionJugador, dorsalJugador;

    public Jugador(String nombreJugador, String apellidosJugador, String equipoJugador, String posicionJugador, String dorsalJugador) {
        this.nombreJugador = nombreJugador;
        this.apellidosJugador = apellidosJugador;
        this.equipoJugador = equipoJugador;
        this.posicionJugador = posicionJugador;
        this.dorsalJugador = dorsalJugador;
    }

    public Jugador() {

    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getApellidosJugador() {
        return apellidosJugador;
    }

    public void setApellidosJugador(String apellidosJugador) {
        this.apellidosJugador = apellidosJugador;
    }

    public String getEquipoJugador() {
        return equipoJugador;
    }

    public void setEquipoJugador(String equipoJugador) {
        this.equipoJugador = equipoJugador;
    }

    public String getPosicionJugador() {
        return posicionJugador;
    }

    public void setPosicionJugador(String posicionJugador) {
        this.posicionJugador = posicionJugador;
    }

    public String getDorsalJugador() {
        return dorsalJugador;
    }

    public void setDorsalJugador(String dorsalJugador) {
        this.dorsalJugador = dorsalJugador;
    }
}
