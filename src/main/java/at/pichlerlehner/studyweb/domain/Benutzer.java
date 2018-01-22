package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

public class Benutzer extends Model<Benutzer, Long> {
    private String email;
    private String password;
    private String vorname;
    private String nachname;

    public Benutzer() {

    }

    public Benutzer(String email, String password, String vorname, String nachname) {
        this.email = Ensurer.ensureNotBlank(email);
        this.password = Ensurer.ensureNotBlank(password);
        this.vorname = Ensurer.ensureNotBlank(vorname);
        this.nachname = Ensurer.ensureNotBlank(nachname);
    }

    public Benutzer(Long primaryKey, Long version, String email, String password, String vorname, String nachname) {
        super(primaryKey, version);
        this.email = Ensurer.ensureNotBlank(email);
        this.password = Ensurer.ensureNotBlank(password);
        this.vorname = Ensurer.ensureNotBlank(vorname);
        this.nachname = Ensurer.ensureNotBlank(nachname);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Ensurer.ensureNotBlank(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Ensurer.ensureNotBlank(password);
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = Ensurer.ensureNotBlank(vorname);
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = Ensurer.ensureNotBlank(nachname);
    }

}
