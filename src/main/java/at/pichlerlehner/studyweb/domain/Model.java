package at.pichlerlehner.studyweb.domain;

import at.pichlerlehner.studyweb.foundation.Ensurer;

public abstract class Model<DOMAIN_TYPE extends Model, ID extends Number> {

    private ID primaryKey;
    private Long version;

    protected Model(ID primaryKey, Long version) {
        this.primaryKey = Ensurer.ensureNotNull(primaryKey);
        this.version = Ensurer.ensureNotNull(version);
    }

    Model() {
        this.version = 0L;
    }

    public ID getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ID primaryKey) {
        this.primaryKey = Ensurer.ensureNotNull(primaryKey);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = Ensurer.ensureNotNull(version);
    }

    public boolean isNew() {
        return primaryKey == null;
    }

}
