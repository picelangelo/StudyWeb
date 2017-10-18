package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

public class Berechtigung extends Model<Berechtigung, Long> {

    private Fragebogen fragebogen;
    private boolean darfBearbeiten;

    public Berechtigung() {

    }

    public Berechtigung(Fragebogen fragebogen, boolean darfBearbeiten) {
        this.fragebogen = Ensurer.ensureNotNull(fragebogen);
        this.darfBearbeiten = darfBearbeiten;
    }

    public Berechtigung(Long primaryKey, Long version, Fragebogen fragebogen, boolean darfBearbeiten) {
        super(primaryKey, version);
        this.fragebogen = Ensurer.ensureNotNull(fragebogen);
        this.darfBearbeiten = darfBearbeiten;
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
}
