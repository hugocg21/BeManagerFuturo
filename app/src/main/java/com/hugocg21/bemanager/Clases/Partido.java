package com.hugocg21.bemanager.Clases;

public class Partido {
    String dia, horaInic, local_visitante, pabellon, rival;

    public Partido(String dia, String horaInic, String local_visitante, String pabellon, String rival) {
        this.dia = dia;
        this.horaInic = horaInic;
        this.local_visitante = local_visitante;
        this.pabellon = pabellon;
        this.rival = rival;
    }

    public Partido() {
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraInic() {
        return horaInic;
    }

    public void setHoraInic(String horaInic) {
        this.horaInic = horaInic;
    }

    public String getLocal_visitante() {
        return local_visitante;
    }

    public void setLocal_visitante(String local_visitante) {
        this.local_visitante = local_visitante;
    }

    public String getPabellon() {
        return pabellon;
    }

    public void setPabellon(String pabellon) {
        this.pabellon = pabellon;
    }

    public String getRival() {
        return rival;
    }

    public void setRival(String rival) {
        this.rival = rival;
    }
}
