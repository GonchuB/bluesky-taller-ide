package main.model;

import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Created by GonchuB on 27/03/2014.
 */
public class TraductorASMtoMAQTest extends TestCase {
    //TODO - Actualizar test con nuevas firmas y objetos
    private TraductorASMtoMAQ traductor;
    private Compilador compilador;

    public void setUp() throws Exception {
        super.setUp();
        traductor = new TraductorASMtoMAQ();
        compilador = new Compilador();
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

        String asmLine1 = "ldi r0,1 ;Load";
        String asmLine2 = "add r2,r0,r1 ;Add";

        // Tests para nuevas instrucciones.
        //TODO: que carajo es RRR? Dos parametros?
        String asmLine3 = "rrr r1,r3 ;Rrr";
        String asmLine4 = "jpz r1 ;Jump";
        String asmLine5 = "stp ;Stop";
        String asmLine6 = "mulf r2,r0,r1 ;Mul";
        String asmLine7 = "jnc r2,r0,r1 ;JumpC";
        String asmLine8 = "cmp r2,r0 ;Cmp";

        Assert.assertEquals("2001 Load", traductor.traducirLineaALenguajeMaquina(asmLine1));
        Assert.assertEquals("5201 Add", traductor.traducirLineaALenguajeMaquina(asmLine2));

        Assert.assertEquals("A103 Rrr", traductor.traducirLineaALenguajeMaquina(asmLine3));
        Assert.assertEquals("B1 Jump", traductor.traducirLineaALenguajeMaquina(asmLine4));
        Assert.assertEquals("C000 Stop", traductor.traducirLineaALenguajeMaquina(asmLine5));
        Assert.assertEquals("D201 Mul", traductor.traducirLineaALenguajeMaquina(asmLine6));
        // FIXME: Este test falla, mete un 0 adelante E0201. Mal la traduccion creo.
        Assert.assertEquals("E201 JumpC", traductor.traducirLineaALenguajeMaquina(asmLine7));
        Assert.assertEquals("F20 Cmp", traductor.traducirLineaALenguajeMaquina(asmLine8));
    }

    public void testChequearSyntaxisDeLinea() throws Exception {

        // Param tests.
        String asmLine1 = "ldi r0,1";
        String asmLine2 = "ldi 0,1";
        String asmLine3 = "ldi r0,r1";
        String asmLine4 = "ldi r0,1,2";

        // Operation tests.
        String asmLine5 = "ldi r0,1";
        String asmLine6 = "asd 1,2,3";

        // Comment tests.
        String asmLine7 = "ldi r0,1 ;Valid";
        String asmLine8 = "ldi r1,2 Invalid";

        Integer lineNo = 0;
        String typeRegex = "^(r([0-9]|1[0-5])),([0-9]|[0-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$";
        String countError = "Error de sintaxis - Linea " + lineNo + " - Numero de parametros incorrectos";
        String typeError = "Error de sintaxis - Linea " + lineNo + " - Tipo de parametros incorrectos. Se esperaba: " + typeRegex;
        String nonExistantError = "Error de syntaxis - Linea " + lineNo + " - Operación desconocida";
        String invalidCommentError = "Error de syntaxis - Linea " + lineNo + " - Exceso de caracteres en linea, posible falta de caracter comentario ';'";

        Assert.assertNull(compilador.chequearSyntaxisDeLineaASM(asmLine1, lineNo));
        Assert.assertEquals(typeError, compilador.chequearSyntaxisDeLineaASM(asmLine2, lineNo));
        Assert.assertEquals(typeError, compilador.chequearSyntaxisDeLineaASM(asmLine3, lineNo));
        Assert.assertEquals(countError, compilador.chequearSyntaxisDeLineaASM(asmLine4, lineNo));

        Assert.assertNull(compilador.chequearSyntaxisDeLineaASM(asmLine5, lineNo));
        Assert.assertEquals(nonExistantError, compilador.chequearSyntaxisDeLineaASM(asmLine6, lineNo));

        Assert.assertNull(compilador.chequearSyntaxisDeLineaASM(asmLine7, lineNo));
        Assert.assertEquals(invalidCommentError, compilador.chequearSyntaxisDeLineaASM(asmLine8, lineNo));
    }

    public void testValidarParametrosOperacion() throws Exception {
        String asmLine1 = "ldi r0,1";
        String asmLine2 = "ldi 0,1";
        String asmLine3 = "ldi r0,r1";
        String asmLine4 = "ldi r0,1,2";

        Integer lineNo = 0;
        String typeRegex = "^(r([0-9]|1[0-5])),([0-9]|[0-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$";
        String countError = "Error de sintaxis - Linea " + lineNo + " - Numero de parametros incorrectos";
        String typeError = "Error de sintaxis - Linea " + lineNo + " - Tipo de parametros incorrectos. Se esperaba: " + typeRegex;

        Assert.assertNull(compilador.validarParametrosOperacionASM(0, asmLine1.split("\\s+")[0], asmLine1.split("\\s+")[1]));
        Assert.assertEquals(typeError, compilador.validarParametrosOperacionASM(0, asmLine2.split("\\s+")[0], asmLine2.split("\\s+")[1]));
        Assert.assertEquals(typeError, compilador.validarParametrosOperacionASM(0, asmLine3.split("\\s+")[0], asmLine3.split("\\s+")[1]));
        Assert.assertEquals(countError, compilador.validarParametrosOperacionASM(0, asmLine4.split("\\s+")[0], asmLine4.split("\\s+")[1]));
    }

    public void testValidarOperacion() throws Exception {
        String asmLine1 = "ldi r0,r1";
        String asmLine2 = "asd 1,2,3";

        Integer lineNo = 0;
        String nonExistantError = "Error de syntaxis - Linea " + lineNo + " - Operación desconocida";

        Assert.assertNull(compilador.validarOperacionASM(lineNo, asmLine1.split("\\s+")[0]));
        Assert.assertEquals(nonExistantError, compilador.validarOperacionASM(lineNo, asmLine2.split("\\s+")[0]));
    }

    public void testValidarComentarios() throws Exception {
        String asmLine1 = "ldi r0,r1 ;Valid";
        String asmLine2 = "asd 1,2,3 Invalid";

        Integer lineNo = 0;
        String invalidCommentError = "Error de syntaxis - Linea " + lineNo + " - Exceso de caracteres en linea, posible falta de caracter comentario ';'";

        Assert.assertNull(compilador.validarComentariosASM(lineNo, asmLine1.split("\\s+")[2]));
        Assert.assertEquals(invalidCommentError, compilador.validarComentariosASM(lineNo, asmLine2.split("\\s+")[2]));
    }
}
