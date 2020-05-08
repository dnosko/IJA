package model;

public class BusMap implements Bus {

    private Line line;
    private int startTime;
    private int endTime;

    public BusMap (Line line, int startTime, int endTime) {
        this.line = line;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Line getLine() {
        return this.line;
    }

    @Override
    public int getStartTime() {
        return this.startTime;
    }

    @Override
    public int getEndTime() {
        return this.endTime;
    }
}
