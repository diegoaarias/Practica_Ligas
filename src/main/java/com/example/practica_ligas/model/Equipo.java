package com.example.practica_ligas.model;

public class Equipo {
    public int intRank;
    public String strTeam;
    public String strTeamBadge;
    public int intPoints;
    public String strForm;

    public int getIntRank() {
        return intRank;
    }

    public void setIntRank(int intRank) {
        this.intRank = intRank;
    }

    public String getStrTeam() {
        return strTeam;
    }

    public void setStrTeam(String strTeam) {
        this.strTeam = strTeam;
    }

    public String getStrTeamBadge() {
        return strTeamBadge;
    }

    public void setStrTeamBadge(String strTeamBadge) {
        this.strTeamBadge = strTeamBadge;
    }

    public int getIntPoints() {
        return intPoints;
    }

    public void setIntPoints(int intPoints) {
        this.intPoints = intPoints;
    }

    public String getStrForm() {
        return strForm;
    }

    public void setStrForm(String strForm) {
        this.strForm = strForm;
    }

    public Equipo() {
    }

    public Equipo(int intRank, String strTeam, String strTeamBadge, int intPoints, String strForm) {
        this.intRank = intRank;
        this.strTeam = strTeam;
        this.strTeamBadge = strTeamBadge;
        this.intPoints = intPoints;
        this.strForm = strForm;
    }

    @Override
    public String toString() {
        return intRank+". "+strTeam;
    }
}
