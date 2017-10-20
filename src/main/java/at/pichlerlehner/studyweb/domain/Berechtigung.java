package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

public class Berechtigung extends Model<Berechtigung, Long> {

    private Fragebogen fragebogen;
    private boolean darfBearbeiten;
    private Benutzer benutzer;


    public Berechtigung() {

    }

    public Berechtigung(Fragebogen fragebogen, boolean darfBearbeiten, Benutzer benutzer) {
        this.fragebogen = Ensurer.ensureNotNull(fragebogen);
        this.darfBearbeiten = darfBearbeiten;
        this.benutzer = Ensurer.ensureNotNull(benutzer);
    }

    public Berechtigung(Long primaryKey, Long version, Fragebogen fragebogen, boolean darfBearbeiten, Benutzer benutzer) {
        super(primaryKey, version);
        this.fragebogen = Ensurer.ensureNotNull(fragebogen);
        this.darfBearbeiten = darfBearbeiten;
        this.benutzer = Ensurer.ensureNotNull(benutzer);
    }

    public Fragebogen getFragebogen() {
        return fragebogen;
    }

    public void setFragebogen(Fragebogen fragebogen) {
        this.fragebogen = Ensurer.ensureNotNull(fragebogen);
    }

    public boolean isDarfBearbeiten() {
        return darfBearbeiten;
    }

    public void setDarfBearbeiten(boolean darfBearbeiten) {
        this.darfBearbeiten = darfBearbeiten;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = Ensurer.ensureNotNull(benutzer);
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }
}
