package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

public class Antwort extends Model<Antwort, Long> {
    private String antwort;
    private boolean isCorrect;

    public Antwort() {

    }

    public Antwort(String antwort, boolean isCorrect) {
        this.antwort = Ensurer.ensureNotBlank(antwort);
        this.isCorrect = isCorrect;
    }

    public Antwort(Long primaryKey, Long version, String antwort, boolean isCorrect) {
        super(primaryKey, version);
        this.antwort = Ensurer.ensureNotBlank(antwort);
        this.isCorrect = isCorrect;
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
}
