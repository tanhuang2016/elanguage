package com.util;

import java.lang.reflect.Field;

public class ColumnRecord {
    private Field field;

    private int index;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
