package org.bsuir.tofi.credit_assistant.structure;

/**
 * Created by 1 on 19.10.2016.
 */
public class Fullname {
    private String name;
    private String secondName;

    public Fullname(String name, String secondName) {
        this.name = name;
        this.secondName = secondName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
