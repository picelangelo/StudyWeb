package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

import java.util.List;


public class Fragebogen extends Model<Fragebogen, Long> {
    private Benutzer ersteller;
    private String bezeichnung;


    public Fragebogen() {}

    public Fragebogen(String bezeichnung, Benutzer ersteller) {
        this.bezeichnung = Ensurer.ensureNotBlank(bezeichnung);
        this.ersteller = Ensurer.ensureNotNull(ersteller);
    }

    public Fragebogen(Long primaryKey, Long version, Benutzer ersteller) {
        super(primaryKey, version);
        this.ersteller = Ensurer.ensureNotNull(ersteller);
    }

    public Benutzer getErsteller() {
        return ersteller;
    }

    public void setErsteller(Benutzer ersteller) {
        this.ersteller = Ensurer.ensureNotNull(ersteller);
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

}
