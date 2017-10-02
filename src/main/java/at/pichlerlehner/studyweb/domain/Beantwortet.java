package at.pichlerlehner.studyweb.domain;

public class Beantwortet extends Model<Beantwortet, Long> {
    private Benutzer benutzer;
    private int anzahlRichtig;
    private int anzahlFalsch;
}
