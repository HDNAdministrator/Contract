package pt.hdn.contract.pojo;

import pt.hdn.contract.annotations.SchemaType;

public abstract class Shell {

    private @SchemaType int type;

    public abstract boolean isValid();

    public @SchemaType int getType() {
        return type;
    }

    public void setType(@SchemaType int type) {
        this.type = type;
    }
}
