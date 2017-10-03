package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

public class Beantwortet extends Model<Beantwortet, Long> {
    private Benutzer benutzer;
    private int anzahlRichtig;
    private int anzahlFalsch;

    public Beantwortet(Benutzer benutzer, int anzahlRichtig, int anzahlFalsch) {
        this.benutzer = Ensurer.ensureNotNull(benutzer);
        this.anzahlRichtig = Ensurer.ensureNotNegative(anzahlRichtig);
        this.anzahlFalsch = Ensurer.ensureNotNegative(anzahlFalsch);
    }

    public Beantwortet(Long primaryKey, Long version, Benutzer benutzer, int anzahlRichtig, int anzahlFalsch) {
        super(primaryKey, version);
        this.benutzer = Ensurer.ensureNotNull(benutzer);
        this.anzahlRichtig = Ensurer.ensureNotNegative(anzahlRichtig);
        this.anzahlFalsch = Ensurer.ensureNotNegative(anzahlFalsch);
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = Ensurer.ensureNotNull(benutzer);
    }

    public int getAnzahlRichtig() {
        return anzahlRichtig;
    }

    public void setAnzahlRichtig(int anzahlRichtig) {
        this.anzahlRichtig = Ensurer.ensureNotNegative(anzahlRichtig);
    }

    public int getAnzahlFalsch() {
        return anzahlFalsch;
    }

    public void setAnzahlFalsch(int anzahlFalsch) {
        this.anzahlFalsch = Ensurer.ensureNotNegative(anzahlFalsch);
    }
}
