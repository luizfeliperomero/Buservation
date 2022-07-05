package model;

import java.sql.Timestamp;

public class Seat {
    private int id;
    private boolean empty;
    private String owner;

    Timestamp timestamp;




    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public Seat(int id, boolean empty) {
        this.id = id;
        this.empty = true;
    }

    public Seat(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

}


