package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final double EPSILON = 1e-10;
    private double x;
    private double y;

    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point) {
        this.x = point.x;
        this.y = point.y;
    }

    public FunctionPoint() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }
    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FunctionPoint that = (FunctionPoint) o;
        return Math.abs(this.x - that.x) < EPSILON &&
                Math.abs(this.y - that.y) < EPSILON;
    }

    @Override
    public int hashCode() {
        long xBits = Double.doubleToLongBits(x);
        long yBits = Double.doubleToLongBits(y);

        int xHash = (int)(xBits ^ (xBits >>> 32));
        int yHash = (int)(yBits ^ (yBits >>> 32));

        return xHash ^ yHash;
    }

    @Override
    public Object clone() {
        return new FunctionPoint(this.x, this.y);
    }
}
