package model;

public class Positions {
    private int positon_id;
    private String name;

    public Positions() {
    }

    public Positions(int positon_id, String name) {
        this.positon_id = positon_id;
        this.name = name;
    }

    public int getPositon_id() {
        return positon_id;
    }

    public void setPositon_id(int positon_id) {
        this.positon_id = positon_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Positions{" +
                "positon_id=" + positon_id +
                ", name='" + name + '\'' +
                '}';
    }
}
