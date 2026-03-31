package com.store.model;

public class Domain {
    private int id;
    private String end;

    public Domain(int id, String end) {
        this.id = id;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
