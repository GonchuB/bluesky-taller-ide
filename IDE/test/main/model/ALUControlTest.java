package main.model;

import junit.framework.Assert;
import junit.framework.TestCase;
import main.apis.FPConversionAPI;

/**
 * Created by GonchuB on 29/03/2014.
 */
public class ALUControlTest extends TestCase {

    private ALUControl aluControl;

    public void setUp() throws Exception {
        super.setUp();

        aluControl = new ALUControl();
    }

    public void testCheckSumOverflowInt() throws Exception {
        Integer op11 = 50;
        Integer op12 = 50;

        Integer op21 = -50;
        Integer op22 = -50;

        Integer op31 = 100;
        Integer op32 = 28;

        Integer op41 = -100;
        Integer op42 = -29;

        Assert.assertFalse(aluControl.checkSumOverflow(op11, op12));
        Assert.assertFalse(aluControl.checkSumOverflow(op21, op22));
        Assert.assertTrue(aluControl.checkSumOverflow(op31, op32));
        Assert.assertTrue(aluControl.checkSumOverflow(op41, op42));
    }

    public void testAddTwoNumbersInt() throws Exception {
        Integer zero1 = 5;
        Integer zero2 = -5;

        Integer negative1 = -5;
        Integer negative2 = -10;

        Integer overflow1 = 100;
        Integer overflow2 = 100;

        aluControl.addTwoNumbers(zero1, zero2);
        Assert.assertTrue(aluControl.isZero());
        Assert.assertFalse(aluControl.isNegative());
        Assert.assertFalse(aluControl.isPrecisionLost());
        Assert.assertFalse(aluControl.isOverflow());

        aluControl.addTwoNumbers(negative1, negative2);
        Assert.assertTrue(aluControl.isNegative());
        Assert.assertFalse(aluControl.isZero());
        Assert.assertFalse(aluControl.isPrecisionLost());
        Assert.assertFalse(aluControl.isOverflow());

        aluControl.addTwoNumbers(overflow1, overflow2);
        Assert.assertTrue(aluControl.isOverflow());
        Assert.assertFalse(aluControl.isNegative());
        Assert.assertFalse(aluControl.isZero());
        Assert.assertFalse(aluControl.isPrecisionLost());
    }

    public void testCheckSumOverflowFloat() throws Exception {
        Float op11 = 1.0f;
        Float op12 = 2.0f;

        Float op21 = -1.0f;
        Float op22 = -2.0f;

        Float op31 = new Float(0.999 * Math.pow(10, 3));
        Float op32 = new Float(0.001 * Math.pow(10, 3));

        Float op41 = new Float(-0.999 * Math.pow(10, 3));
        Float op42 = new Float(-0.001 * Math.pow(10, 3));

        Assert.assertFalse(aluControl.checkSumOverflow(op11, op12));
        Assert.assertFalse(aluControl.checkSumOverflow(op21, op22));
        Assert.assertTrue(aluControl.checkSumOverflow(op31, op32));
        Assert.assertTrue(aluControl.checkSumOverflow(op41, op42));
    }

    public void testCheckSumPrecisionLostFloat() throws Exception {
        Float notLost0 = 0.125f;
        Float notLost1 = 0.0625f;
        Float lost0 = 0.03125f;
        Float lost1 = 0.015625f;

        Assert.assertFalse(aluControl.checkSumPrecisionLost(notLost0, 0.0f));
        Assert.assertFalse(aluControl.checkSumPrecisionLost(notLost1, 0.0f));
        Assert.assertTrue(aluControl.checkSumPrecisionLost(lost0, 0.0f));
        Assert.assertTrue(aluControl.checkSumPrecisionLost(lost1, 0.0f));
    }

    public void testAddTwoNumbersFloat() throws Exception {
        Float zero1 = 5.0f;
        Float zero2 = -5.0f;

        Float negative1 = -5.0f;
        Float negative2 = -10.0f;

        Float overflow1 = 500.0f;
        Float overflow2 = 500.0f;

        Float precision1 = 99.99f;
        Float precision2 = 0.009f;

        aluControl.addTwoNumbers(zero1, zero2);
        Assert.assertTrue(aluControl.isZero());
        Assert.assertFalse(aluControl.isNegative());
        Assert.assertFalse(aluControl.isPrecisionLost());
        Assert.assertFalse(aluControl.isOverflow());

        aluControl.addTwoNumbers(negative1, negative2);
        Assert.assertTrue(aluControl.isNegative());
        Assert.assertFalse(aluControl.isZero());
        Assert.assertFalse(aluControl.isPrecisionLost());
        Assert.assertTrue(aluControl.isOverflow());

        aluControl.addTwoNumbers(overflow1, overflow2);
        Assert.assertTrue(aluControl.isOverflow());
        Assert.assertFalse(aluControl.isNegative());
        Assert.assertFalse(aluControl.isZero());
        Assert.assertTrue(aluControl.isPrecisionLost());

        aluControl.addTwoNumbers(precision1, precision2);
        Assert.assertTrue(aluControl.isPrecisionLost());
        Assert.assertTrue(aluControl.isOverflow());
        Assert.assertFalse(aluControl.isNegative());
        Assert.assertFalse(aluControl.isZero());
    }

    public void testCheckMulOverflow() throws Exception {
        Float op11 = 2.0f;
        Float op12 = 2.0f;

        Float op21 = 3.0f;
        Float op22 = 2.5f;

        Float op31 = 7.55f;
        Float op32 = 1.0f;

        Assert.assertFalse(aluControl.checkMulOverflow(op11, op12));
        Assert.assertFalse(aluControl.checkMulOverflow(op21, op22));
        Assert.assertTrue(aluControl.checkMulOverflow(op31, op32));
    }

    public void testCheckMulPrecisionLost() throws Exception {
        Float notLost0 = 0.125f;
        Float notLost1 = 0.0625f;
        Float lost0 = 0.03125f;
        Float lost1 = 0.015625f;

        Assert.assertFalse(aluControl.checkMulPrecisionLost(notLost0, 1.0f));
        Assert.assertFalse(aluControl.checkMulPrecisionLost(notLost1, 1.0f));
        Assert.assertTrue(aluControl.checkMulPrecisionLost(lost0, 1.0f));
        Assert.assertTrue(aluControl.checkMulPrecisionLost(lost1, 1.0f));
    }

    public void testMulTwoNumbersFloat() throws Exception {
        Float zero1 = 5.0f;
        Float zero2 = 0.0f;

        Float negative1 = -8f;
        Float negative2 = 1.0f;

        Float overflow1 = 500.0f;
        Float overflow2 = 500.0f;

        Float precisionLost1 = 0.03125f;
        Float precisionLost2 = 1.0f;

        Float negativePrecisionLost1 = 0.03125f;
        Float negativePrecisionLost2 = -1.0f;

        aluControl.mulTwoNumbers(zero1, zero2);
        Assert.assertTrue(aluControl.isZero());
        Assert.assertFalse(aluControl.isNegative());
        Assert.assertFalse(aluControl.isPrecisionLost());
        Assert.assertFalse(aluControl.isOverflow());

        aluControl.mulTwoNumbers(negative1, negative2);
        Assert.assertTrue(aluControl.isNegative());
        Assert.assertTrue(aluControl.isOverflow());
        Assert.assertFalse(aluControl.isZero());
        Assert.assertFalse(aluControl.isPrecisionLost());

        aluControl.mulTwoNumbers(overflow1, overflow2);
        Assert.assertTrue(aluControl.isOverflow());
        Assert.assertFalse(aluControl.isNegative());
        Assert.assertFalse(aluControl.isZero());
        Assert.assertTrue(aluControl.isPrecisionLost());

        aluControl.mulTwoNumbers(precisionLost1, precisionLost2);
        Assert.assertTrue(aluControl.isPrecisionLost());
        Assert.assertFalse(aluControl.isOverflow());
        Assert.assertFalse(aluControl.isNegative());
        Assert.assertFalse(aluControl.isZero());

        aluControl.mulTwoNumbers(negativePrecisionLost1, negativePrecisionLost2);
        Assert.assertTrue(aluControl.isPrecisionLost());
        Assert.assertTrue(aluControl.isNegative());
        Assert.assertFalse(aluControl.isOverflow());
        Assert.assertFalse(aluControl.isZero());
    }

}
