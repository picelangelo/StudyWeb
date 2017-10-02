package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

import java.util.List;

public class Fragebogen extends Model<Fragebogen, Long> {
    private List<Frage> fragen;

    public Fragebogen(List<Frage> fragen) {
        Ensurer.ensureNotNull(fragen);
        fragen.forEach(Ensurer::ensureNotNull);
        this.fragen = fragen;
    }

    public Fragebogen(Long primaryKey, Long version, List<Frage> fragen) {
        super(primaryKey, version);
        Ensurer.ensureNotNull(fragen);
        fragen.forEach(Ensurer::ensureNotNull);
        this.fragen = fragen;
    }

    public List<Frage> getFragen() {
        return fragen;
    }

    public void setFragen(List<Frage> fragen) {
        Ensurer.ensureNotNull(fragen);
        fragen.forEach(Ensurer::ensureNotNull);
        this.fragen = fragen;
    }
}
