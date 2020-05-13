package model;

public class GlobalData {
    private int confirmedCases;
    private int recoveredCases ;
    private int deaths ;
    private String date;

    @Override
    public String toString() {
        return "GlobalData{" +
                "confirmedCases=" + confirmedCases +
                ", recoveredCases=" + recoveredCases +
                ", deaths=" + deaths +
                ", date='" + date + '\'' +
                '}';
    }

    public GlobalData(int confirmedCases, int recoveredCases, int deaths, String date) {
        this.confirmedCases = confirmedCases;
        this.recoveredCases = recoveredCases;
        this.deaths = deaths;
        this.date = date;
    }

    public GlobalData() {
    }

    public int getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(int confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public int getRecoveredCases() {
        return recoveredCases;
    }

    public void setRecoveredCases(int recoveredCases) {
        this.recoveredCases = recoveredCases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
