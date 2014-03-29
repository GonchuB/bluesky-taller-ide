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

    public void testAddTwoNumbers1() throws Exception {

    }
}
