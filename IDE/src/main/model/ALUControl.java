package main.model;

/**
 * Created by gonchub on 24/03/14.
 */
public class ALUControl {

    private static Integer maxPosNumber = 127;
    private static Integer maxNegNumber = -128;
    private boolean overflow;
    private boolean precisionLost;
    private boolean zero;
    private boolean negative;

    public boolean checkSumOverflow(Float op1, Float op2) {
        return (op1 + op2 > maxPosNumber || op1 + op2 < maxNegNumber);
    }

    public Float addTwoNumbers(Float op1, Float op2) {
        Float result = op1 + op2;
        this.setOverflow(this.checkSumOverflow(op1, op2));
        this.setNegative(result < 0);
        this.setZero(result == 0);
        //TODO: check precisionLost after converting Float to 8bit computer float.
        return result;
    }

    public boolean isOverflow() {
        return overflow;
    }

    public void setOverflow(boolean overflow) {
        this.overflow = overflow;
    }

    public boolean isPrecisionLost() {
        return precisionLost;
    }

    public void setPrecisionLost(boolean precisionLost) {
        this.precisionLost = precisionLost;
    }

    public boolean isZero() {
        return zero;
    }

    public void setZero(boolean zero) {
        this.zero = zero;
    }

    public boolean isNegative() {
        return negative;
    }

    public void setNegative(boolean negative) {
        this.negative = negative;
    }
}
