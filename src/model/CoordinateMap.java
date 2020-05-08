package model;

public class CoordinateMap implements Coordinate {

    private int x;
    private int y;

    public CoordinateMap(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX () {
        return this.x;
    }

    public int getY () {
        return this.y;
    }
}
