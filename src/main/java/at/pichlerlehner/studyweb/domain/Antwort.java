package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

public class Antwort extends Model<Antwort, Long> {
    private String antwort;
    private boolean isCorrect;
    private Frage frage;

    public Antwort() {
    }

    public Antwort(String antwort, boolean isCorrect, Frage frage) {
        this.antwort = Ensurer.ensureNotBlank(antwort);
        this.isCorrect = isCorrect;
        this.frage = Ensurer.ensureNotNull(frage);
    }

    public Antwort(Long primaryKey, Long version, String antwort, boolean isCorrect, Frage frage) {
        super(primaryKey, version);
        this.antwort = Ensurer.ensureNotBlank(antwort);
        this.isCorrect = isCorrect;
        this.frage = Ensurer.ensureNotNull(frage);
    }

    public String getAntwort() {
        return antwort;
    }

    public void setAntwort(String antwort) {
        this.antwort = antwort;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Frage getFrage() {
        return this.frage;
    }

    public void setFrage(Frage frage) {
        this.frage = Ensurer.ensureNotNull(frage);
    }
}
