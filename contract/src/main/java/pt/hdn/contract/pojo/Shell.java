package pt.hdn.contract.pojo;

import pt.hdn.contract.annotations.SchemaType;

public class Shell {

    private @SchemaType int type;

    public @SchemaType int getType() {
        return type;
    }

    public void setType(@SchemaType int type) {
        this.type = type;
    }
}
