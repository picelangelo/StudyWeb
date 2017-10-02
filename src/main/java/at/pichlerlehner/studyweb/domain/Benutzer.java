package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

import java.util.List;

public class Benutzer extends Model<Benutzer, Long> {
    private String email;
    private String password;
    private String vorname;
    private String nachname;
    private List<Fragebogen> frageboegenErstellt;
    private List<Berechtigung> berechtigungsList;

    public Benutzer(String email, String password, String vorname, String nachname, List<Fragebogen> frageboegenErstellt, List<Berechtigung> berechtigungsList) {
        this.email = Ensurer.ensureNotBlank(email);
        this.password = Ensurer.ensureNotBlank(password);
        this.vorname = Ensurer.ensureNotBlank(vorname);
        this.nachname = Ensurer.ensureNotBlank(nachname);
        Ensurer.ensureNotNull(frageboegenErstellt);
        frageboegenErstellt.forEach(Ensurer::ensureNotNull);
        this.frageboegenErstellt = frageboegenErstellt;
        Ensurer.ensureNotNull(berechtigungsList);
        berechtigungsList.forEach(Ensurer::ensureNotNull);
        this.berechtigungsList = berechtigungsList;
    }

    public Benutzer(Long primaryKey, Long version, String email, String password, String vorname, String nachname, List<Fragebogen> frageboegenErstellt, List<Berechtigung> berechtigungsList) {
        super(primaryKey, version);
        this.email = Ensurer.ensureNotBlank(email);
        this.password = Ensurer.ensureNotBlank(password);
        this.vorname = Ensurer.ensureNotBlank(vorname);
        this.nachname = Ensurer.ensureNotBlank(nachname);
        Ensurer.ensureNotNull(frageboegenErstellt);
        frageboegenErstellt.forEach(Ensurer::ensureNotNull);
        this.frageboegenErstellt = frageboegenErstellt;
        Ensurer.ensureNotNull(berechtigungsList);
        berechtigungsList.forEach(Ensurer::ensureNotNull);
        this.berechtigungsList = berechtigungsList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Ensurer.ensureNotBlank(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Ensurer.ensureNotBlank(password);
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = Ensurer.ensureNotBlank(vorname);
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = Ensurer.ensureNotBlank(nachname);
    }

    public List<Fragebogen> getFrageboegenErstellt() {
        return frageboegenErstellt;
    }

    public void setFrageboegenErstellt(List<Fragebogen> frageboegenErstellt) {
        Ensurer.ensureNotNull(frageboegenErstellt);
        frageboegenErstellt.forEach(Ensurer::ensureNotNull);
        this.frageboegenErstellt = frageboegenErstellt;
    }

    public List<Berechtigung> getBerechtigungsList() {
        return berechtigungsList;
    }

    public void setBerechtigungsList(List<Berechtigung> berechtigungsList) {
        Ensurer.ensureNotNull(berechtigungsList);
        berechtigungsList.forEach(Ensurer::ensureNotNull);
        this.berechtigungsList = berechtigungsList;
    }
}
