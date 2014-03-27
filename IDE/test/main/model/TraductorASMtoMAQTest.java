package main.model;

import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Created by GonchuB on 27/03/2014.
 */
public class TraductorASMtoMAQTest extends TestCase {

    private TraductorASMtoMAQ traductor;

    public void setUp() throws Exception {
        super.setUp();
        traductor = new TraductorASMtoMAQ();
    }

    public void testLineNumberToBytes() throws Exception {
        String t0 = traductor.lineNumberToBytes(0);
        String t1 = traductor.lineNumberToBytes(1);
        String t2 = traductor.lineNumberToBytes(5);
        String t3 = traductor.lineNumberToBytes(127);

        Assert.assertEquals("00", t0);
        Assert.assertEquals("02", t1);
        Assert.assertEquals("0A", t2);
        Assert.assertEquals("FE", t3);
    }

    public void testIsRegisterParam() throws Exception {
        String reg = "r0";
        String notReg = "00";

        Assert.assertTrue(traductor.isRegisterParam(reg));
        Assert.assertFalse(traductor.isRegisterParam(notReg));
    }

    public void testRegisterParamToString() throws Exception {
        String regParam1 = "r0";
        String opCode1 = "0";

        String regParam2 = "r15";
        String opCode2 = "1";

        String regParam3 = "r1";
        String opCode3 = "4";

        String regParam4 = "r15";
        String opCode4 = "4";

        Assert.assertEquals("0", traductor.registerParamToString(regParam1, opCode1));
        Assert.assertEquals("F", traductor.registerParamToString(regParam2, opCode2));
        Assert.assertEquals("01", traductor.registerParamToString(regParam3, opCode3));
        Assert.assertEquals("0F", traductor.registerParamToString(regParam4, opCode4));
    }

    public void testImmediateParamToString() throws Exception {
        String immParam1 = "0";
        String opCode1 = "0";

        String immParam2 = "15";
        String opCode2 = "1";

        String immParam3 = "1";
        String opCode3 = "4";

        String immParam4 = "15";
        String opCode4 = "4";

        String immParam5 = "16";
        String opCode5 = "2";

        Assert.assertEquals("00", traductor.immediateParamToString(immParam1, opCode1));
        Assert.assertEquals("0F", traductor.immediateParamToString(immParam2, opCode2));
        Assert.assertEquals("01", traductor.immediateParamToString(immParam3, opCode3));
        Assert.assertEquals("0F", traductor.immediateParamToString(immParam4, opCode4));
        Assert.assertEquals("10", traductor.immediateParamToString(immParam5, opCode5));
    }

    public void testTraducirLineaALenguajeMaquina() throws Exception {

        //FIXME: mas test cases para mas operaciones, sinceramente me dio paja abrir el enunciado ahora.

        String asmLine1 = "ldi r0,1 ;Load";
        String asmLine2 = "add r2,r0,r1 ;Add";

        Assert.assertEquals("2001 Load", traductor.traducirLineaALenguajeMaquina(asmLine1));
        Assert.assertEquals("5201 Add", traductor.traducirLineaALenguajeMaquina(asmLine2));
    }

    public void testChequearSyntaxisDeLinea() throws Exception {

    }

    public void testValidarParametrosOperacion() throws Exception {
        String asmLine1 = "ldi r0,1";
        String asmLine2 = "ldi 0,1";
        String asmLine3 = "ldi r0,r1";
        String asmLine4 = "ldi r0,1,2";

        Integer lineNo = 0;
        String typeRegex = "^r[0-15],[0-9a-fA-F]{1,2}$";
        String countError = "Error de sintaxis - Linea " + lineNo + " - Numero de parametros incorrectos";
        String typeError = "Error de sintaxis - Linea " + lineNo + " - Tipo de parametros incorrectos. Se esperaba: " + typeRegex;

        Assert.assertNull(traductor.validarParametrosOperacion(0, asmLine1.split("\\s+")[0], asmLine1.split("\\s+")[1]));
        Assert.assertEquals(typeError, traductor.validarParametrosOperacion(0, asmLine2.split("\\s+")[0], asmLine2.split("\\s+")[1]));
        Assert.assertEquals(typeError, traductor.validarParametrosOperacion(0, asmLine3.split("\\s+")[0], asmLine3.split("\\s+")[1]));
        Assert.assertEquals(countError, traductor.validarParametrosOperacion(0, asmLine4.split("\\s+")[0], asmLine4.split("\\s+")[1]));
    }

    public void testValidarOperacion() throws Exception {
        String asmLine1 = "ldi r0,r1";
        String asmLine2 = "asd 1,2,3";

        Integer lineNo = 0;
        String nonExistantError = "Error de syntaxis - Linea " + lineNo + " - Operación desconocida";

        Assert.assertNull(traductor.validarOperacion(lineNo, asmLine1.split("\\s+")[0]));
        Assert.assertEquals(nonExistantError, traductor.validarOperacion(lineNo, asmLine2.split("\\s+")[0]));
    }

    public void testValidarComentarios() throws Exception {
        String asmLine1 = "ldi r0,r1 ;Valid";
        String asmLine2 = "asd 1,2,3 Invalid";

        Integer lineNo = 0;
        String invalidCommentError = "Error de syntaxis - Linea " + lineNo + " - Exceso de caracteres en linea, posible falta de caracter comentario ';'";

        Assert.assertNull(traductor.validarComentarios(lineNo, asmLine1.split("\\s+")[2]));
        Assert.assertEquals(invalidCommentError, traductor.validarComentarios(lineNo, asmLine2.split("\\s+")[2]));
    }
}
