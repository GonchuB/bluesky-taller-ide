package main.model;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by GonchuB on 29/03/2014.
 */
public class ALUControlTest extends TestCase {

    private ALUControl aluControl;

    public void setUp() throws Exception {
        super.setUp();

        aluControl = new ALUControl();
    }

    public void testCheckSumOverflow() throws Exception {
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

    public void testAddTwoNumbers() throws Exception {
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

    public void testCheckSumOverflow1() throws Exception {
        Float op11 = 50.0f;
        Float op12 = 50.0f;

        Float op21 = -50.0f;
        Float op22 = -50.0f;

        Float op31 = new Float(0.999 * Math.pow(10, 3));
        Float op32 = new Float(0.001 * Math.pow(10, 3));

        Float op41 = new Float(-0.999 * Math.pow(10, 3));
        Float op42 = new Float(-0.001 * Math.pow(10, 3));

        Assert.assertFalse(aluControl.checkSumOverflow(op11, op12));
        Assert.assertFalse(aluControl.checkSumOverflow(op21, op22));
        Assert.assertTrue(aluControl.checkSumOverflow(op31, op32));
        Assert.assertTrue(aluControl.checkSumOverflow(op41, op42));
    }

    public void testCheckSumPrecisionLost() throws Exception {
        Float notLost0 = 999.9f;
        Float notLost1 = 99.99f;
        Float notLost2 = 9.999f;
        Float notLost3 = 0.9999f;
        Float notLost4 = 0.09999f;
        Float lost0 = 999.91f;
        Float lost1 = 99.991f;
        Float lost2 = 9.9991f;
        Float lost3 = 0.99991f;
        Float lost4 = 0.099991f;

        Assert.assertFalse(aluControl.checkSumPrecisionLost(notLost0, 0.0f));
        Assert.assertFalse(aluControl.checkSumPrecisionLost(notLost1, 0.0f));
        Assert.assertFalse(aluControl.checkSumPrecisionLost(notLost2, 0.0f));
        Assert.assertFalse(aluControl.checkSumPrecisionLost(notLost3, 0.0f));
        Assert.assertFalse(aluControl.checkSumPrecisionLost(notLost4, 0.0f));
        Assert.assertTrue(aluControl.checkSumPrecisionLost(lost0, 0.0f));
        Assert.assertTrue(aluControl.checkSumPrecisionLost(lost1, 0.0f));
        Assert.assertTrue(aluControl.checkSumPrecisionLost(lost2, 0.0f));
        Assert.assertTrue(aluControl.checkSumPrecisionLost(lost3, 0.0f));
        Assert.assertTrue(aluControl.checkSumPrecisionLost(lost4, 0.0f));
    }

    public void testAddTwoNumbers1() throws Exception {
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
        Assert.assertFalse(aluControl.isOverflow());

        aluControl.addTwoNumbers(overflow1, overflow2);
        Assert.assertTrue(aluControl.isOverflow());
        Assert.assertFalse(aluControl.isNegative());
        Assert.assertFalse(aluControl.isZero());
        Assert.assertFalse(aluControl.isPrecisionLost());

        aluControl.addTwoNumbers(precision1, precision2);
        Assert.assertTrue(aluControl.isPrecisionLost());
        Assert.assertFalse(aluControl.isOverflow());
        Assert.assertFalse(aluControl.isNegative());
        Assert.assertFalse(aluControl.isZero());
    }
}
