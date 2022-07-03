package model;

public class Seat {
    private int id;
    private boolean empty;

    public Seat(int id, boolean empty) {
        this.id = id;
        this.empty = empty;
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


