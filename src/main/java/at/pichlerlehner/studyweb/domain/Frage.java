package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

import java.util.List;

public class Frage extends Model<Frage, Long> {
    private String Frage;
    private List<Antwort> antworten;
    private boolean isMultipleChoice;
    private Beantwortet beantwortet;

    public Frage(String frage, List<Antwort> antworten, boolean isMultipleChoice, Beantwortet beantwortet) {
        Frage = Ensurer.ensureNotBlank(frage);
        Ensurer.ensureNotNull(antworten);
        antworten.forEach(Ensurer::ensureNotNull);
        this.antworten = antworten;
        this.isMultipleChoice = isMultipleChoice;
        this.beantwortet = Ensurer.ensureNotNull(beantwortet);
    }

    public Frage(Long primaryKey, Long version, String frage, List<Antwort> antworten, boolean isMultipleChoice, Beantwortet beantwortet) {
        super(primaryKey, version);
        Frage = Ensurer.ensureNotBlank(frage);
        Ensurer.ensureNotNull(antworten);
        antworten.forEach(Ensurer::ensureNotNull);
        this.antworten = antworten;
        this.isMultipleChoice = isMultipleChoice;
        this.beantwortet = Ensurer.ensureNotNull(beantwortet);
    }

    public String getFrage() {
        return Frage;
    }

    public void setFrage(String frage) {
        Frage = frage;
    }

    public List<Antwort> getAntworten() {
        return antworten;
    }

    public void setAntworten(List<Antwort> antworten) {
        Ensurer.ensureNotNull(antworten);
        antworten.forEach(Ensurer::ensureNotNull);
        this.antworten = antworten;
    }

    public boolean isMultipleChoice() {
        return isMultipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        isMultipleChoice = multipleChoice;
    }

    public Beantwortet getBeantwortet() {
        return beantwortet;
    }

    public void setBeantwortet(Beantwortet beantwortet) {
        this.beantwortet = Ensurer.ensureNotNull(beantwortet);
    }
}
