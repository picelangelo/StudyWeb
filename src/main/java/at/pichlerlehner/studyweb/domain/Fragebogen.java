package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

import java.util.List;

public class Fragebogen extends Model<Fragebogen, Long> {
    private Benutzer ersteller;

    public Fragebogen() {}

    public Fragebogen(Benutzer ersteller) {
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
}
