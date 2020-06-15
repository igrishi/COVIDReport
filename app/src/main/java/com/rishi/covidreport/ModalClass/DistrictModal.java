package com.rishi.covidreport.ModalClass;

public class DistrictModal {
    String name;
    int confirmed,active,recovered,death;

    public DistrictModal(){}
    public DistrictModal(String name, int confirmed, int active, int recovered, int death) {
        this.name = name;
        this.confirmed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.death = death;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }
}
