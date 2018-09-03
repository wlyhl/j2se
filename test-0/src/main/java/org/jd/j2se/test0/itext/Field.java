package org.jd.j2se.test0.itext;

/**
 * Created by cuijiandong on 2018/7/11.
 */
public class Field {
    String name;
    String desc;
    String getter, setter;

    public Field(String name, String desc) {
        this.name = name;
        this.desc = desc;
        String upperName = name.substring(0, 1).toUpperCase() + name.substring(1);
        setter = "set" + upperName;
        getter = "get" + upperName;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getGetter() {
        return getter;
    }

    public String getSetter() {
        return setter;
    }
}