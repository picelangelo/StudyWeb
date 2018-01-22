package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

public class Frage extends Model<Frage, Long> {
    private String Frage;
    private boolean isMultipleChoice;
    private Fragebogen fragebogen;

    public Frage() {

    }

    public Frage(String frage, boolean isMultipleChoice, Fragebogen fragebogen) {
        Frage = Ensurer.ensureNotBlank(frage);
        this.isMultipleChoice = isMultipleChoice;
        this.fragebogen = Ensurer.ensureNotNull(fragebogen);
    }

    public Frage(Long primaryKey, Long version, String frage, boolean isMultipleChoice, Fragebogen fragebogen) {
        super(primaryKey, version);
        Frage = Ensurer.ensureNotBlank(frage);
        this.isMultipleChoice = isMultipleChoice;
        this.fragebogen = Ensurer.ensureNotNull(fragebogen);
    }

    public String getFrage() {
        return Frage;
    }

    public void setFrage(String frage) {
        Frage = frage;
    }

    public boolean isMultipleChoice() {
        return isMultipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        isMultipleChoice = multipleChoice;
    }

    public Fragebogen getFragebogen() {
        return fragebogen;
    }

    public void setFragebogen(Fragebogen fragebogen) {
        this.fragebogen = Ensurer.ensureNotNull(fragebogen);
    }
}
