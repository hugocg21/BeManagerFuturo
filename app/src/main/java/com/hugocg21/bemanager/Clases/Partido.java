package com.hugocg21.bemanager.Clases;

public class Partido {
    String rivalPartido, rolPartido, sedePartido, fechaPartido, horaPartido;

    public Partido(String rivalPartido, String rolPartido, String sedePartido, String fechaPartido, String horaPartido) {
        this.rivalPartido = rivalPartido;
        this.rolPartido = rolPartido;
        this.sedePartido = sedePartido;
        this.fechaPartido = fechaPartido;
        this.horaPartido = horaPartido;
    }

    public Partido() {
    }

    public String getRivalPartido() {
        return rivalPartido;
    }

    public void setRivalPartido(String rivalPartido) {
        this.rivalPartido = rivalPartido;
    }

    public String getRolPartido() {
        return rolPartido;
    }

    public void setRolPartido(String rolPartido) {
        this.rolPartido = rolPartido;
    }

    public String getSedePartido() {
        return sedePartido;
    }

    public void setSedePartido(String sedePartido) {
        this.sedePartido = sedePartido;
    }

    public String getFechaPartido() {
        return fechaPartido;
    }

    public void setFechaPartido(String fechaPartido) {
        this.fechaPartido = fechaPartido;
    }

    public String getHoraPartido() {
        return horaPartido;
    }

    public void setHoraPartido(String horaPartido) {
        this.horaPartido = horaPartido;
    }
}
