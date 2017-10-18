package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

import java.util.List;

public class Fragebogen extends Model<Fragebogen, Long> {
    private List<Frage> fragen;
    private Benutzer ersteller;

    public Fragebogen() {}

    public Fragebogen(List<Frage> fragen, Benutzer ersteller) {
        Ensurer.ensureNotNull(fragen);
        fragen.forEach(Ensurer::ensureNotNull);
        this.fragen = fragen;
    }

    public Fragebogen(Long primaryKey, Long version, List<Frage> fragen, Benutzer ersteller) {
        super(primaryKey, version);
        Ensurer.ensureNotNull(fragen);
        fragen.forEach(Ensurer::ensureNotNull);
        this.fragen = fragen;
        this.ersteller = Ensurer.ensureNotNull(ersteller);
    }

    public List<Frage> getFragen() {
        return fragen;
    }

    public void setFragen(List<Frage> fragen) {
        Ensurer.ensureNotNull(fragen);
        fragen.forEach(Ensurer::ensureNotNull);
        this.fragen = fragen;
        this.ersteller = Ensurer.ensureNotNull(ersteller);
    }

    public Benutzer getErsteller() {
        return ersteller;
    }

    public void setErsteller(Benutzer ersteller) {
        this.ersteller = Ensurer.ensureNotNull(ersteller);
    }
}
