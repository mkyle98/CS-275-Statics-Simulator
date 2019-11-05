package com.jacobwunder.libstatics;

public class UpdatePayload {
    public UpdatePayload(String type, Object value) {
        this.type = type;
        this.value = value;
    }
    public String type;
    public Object value;

    @Override
    public String toString() {
        return "UpdatePayload{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
