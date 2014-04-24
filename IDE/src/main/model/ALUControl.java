package main.model;

import main.apis.FPConversionAPI;

/**
 * Created by gonchub on 24/03/14.
 */
public class ALUControl {

    public static Integer maxPosIntNumber = 127;
    public static Float maxPosFloatNumber = FPConversionAPI.binaryToFloat("01111011");
    public static Integer maxNegIntNumber = -128;
    public static Float maxNegFloatNumber = FPConversionAPI.binaryToFloat("11111011");
    private boolean overflow;
    private boolean precisionLost;
    private boolean zero;
    private boolean negative;

    private Float roundToSignificantFigures(Float num, int n) {
        if (num == 0) {
            return 0.0f;
        }

        final double d = Math.ceil(Math.log10(num < 0 ? -num : num));
        final int power = n - (int) d;

        final double magnitude = Math.pow(10, power);
        final long shifted = Math.round(num * magnitude);
        return new Float(shifted / magnitude);
    }

    public boolean checkSumPrecisionLost(Float op1, Float op2) {
        return FPConversionAPI.isPrecisionLostInConversion(op1 + op2);
    }

    public boolean checkSumOverflow(Float op1, Float op2) {
        return (op1 + op2 > maxPosFloatNumber || op1 + op2 < maxNegFloatNumber);
    }

    public boolean checkMulOverflow(Float op1, Float op2) {
        return (op1 * op2 > maxPosFloatNumber || op1 * op2 < maxNegFloatNumber);
    }

    public boolean checkMulPrecisionLost(Float op1, Float op2) {
        return FPConversionAPI.isPrecisionLostInConversion(op1 * op2);
    }

    public Float addTwoNumbers(Float op1, Float op2) {
        Float result = op1 + op2;
        this.setOverflow(this.checkSumOverflow(op1, op2));
        this.setNegative(result < 0.0f);
        this.setZero(result == 0.0f);
        this.setPrecisionLost(checkSumPrecisionLost(op1, op2));
        return result;
    }

    public Float mulTwoNumbers(Float op1, Float op2) {
        Float result = op1 * op2;
        this.setOverflow(this.checkMulOverflow(op1, op2));
        this.setNegative(result < 0.0f);
        this.setZero(result == 0.0f);
        this.setPrecisionLost(checkMulPrecisionLost(op1, op2));
        return result;
    }

    public boolean checkSumOverflow(Integer op1, Integer op2) {
        return (op1 + op2 > maxPosIntNumber || op1 + op2 < maxNegIntNumber);
    }

    public Integer addTwoNumbers(Integer op1, Integer op2) {
        Integer result = op1 + op2;
        this.setOverflow(this.checkSumOverflow(op1, op2));
        this.setNegative(result < 0);
        this.setZero(result == 0);
        this.setPrecisionLost(false);
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
