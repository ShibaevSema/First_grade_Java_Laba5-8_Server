package semashibaev.ifmo.server.structCollection.substruct;

public class Coordinates {
    private long x; //Значение поля должно быть больше -112
    private Long y; //Значение поля должно быть больше -259
    public Coordinates(long x, long y) throws Exception {
        this.setX(x);
        this.setY(y);
    }

    public long getX() {
        return x;
    }

    private void setX(long x) throws Exception {
        if (x <= -112)
            throw new Exception("**Invalid x coord (must be > -112)");
        this.x = x;
    }

    public long getY() {
        return y;
    }

    private void setY(long y) throws Exception {
        if (y <= -259)
            throw new Exception("**Invalid y coord (must be > -259)");
        this.y = y;
    }

    @Override
    public String toString() {
        return x + "," + y ;
    }

}


