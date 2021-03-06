package main.model;

import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Created by GonchuB on 27/03/2014.
 */
public class TraductorASMtoMAQTest extends TestCase {
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
        Assert.assertEquals("1", traductor.registerParamToString(regParam3, opCode3)); //Esto falla, pero porq el 0 se agrega en un metodo aparte
        Assert.assertEquals("F", traductor.registerParamToString(regParam4, opCode4));
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

        String asmStr = "str r1,r2 ;Str";
        String asmLoadMem = "ldm r0,5 ;LoadMem";
        String asmLoadImm = "ldi r0,1 ;LoadImm";
        String asmStm = "stm r0,4 ;StoreMem";
        String asmCpy = "cpy r0,r1 ;Cpy";
        String asmAddInt = "add r2,r0,r1 ;Add";
        String asmAddFp = "addf r2,r0,r1 ;AddFp";
        String asmOr = "oor r2,r0,r1 ;Or";
        String asmAnd = "and r2,r0,r1 ;And";
        String asmXor = "xor r2,r0,r1 ;Xor";

        // Tests para nuevas instrucciones.
        String asmRrr = "rrr r1,r3 ;Rrr";
        String asmJump = "jpz r1,10 ;Jump";
        String asmStop = "stp ;Stop";
        String asmMul = "mulf r2,r0,r1 ;Mul";
        String asmJumpC = "jnc 250 ;JumpC";
        String asmCmp = "cmp r2,r0 ;Cmp";

        Assert.assertEquals("0012 Str", traductor.traducirLineaALenguajeMaquina(asmStr));
        Assert.assertEquals("1005 LoadMem", traductor.traducirLineaALenguajeMaquina(asmLoadMem));
        Assert.assertEquals("2001 LoadImm", traductor.traducirLineaALenguajeMaquina(asmLoadImm));
        Assert.assertEquals("3004 StoreMem", traductor.traducirLineaALenguajeMaquina(asmStm));
        Assert.assertEquals("4001 Cpy", traductor.traducirLineaALenguajeMaquina(asmCpy));
        Assert.assertEquals("5201 Add", traductor.traducirLineaALenguajeMaquina(asmAddInt));
        Assert.assertEquals("6201 AddFp", traductor.traducirLineaALenguajeMaquina(asmAddFp));
        Assert.assertEquals("7201 Or", traductor.traducirLineaALenguajeMaquina(asmOr));
        Assert.assertEquals("8201 And", traductor.traducirLineaALenguajeMaquina(asmAnd));
        Assert.assertEquals("9201 Xor", traductor.traducirLineaALenguajeMaquina(asmXor));

        // Tests para nuevas instrucciones.
        Assert.assertEquals("A103 Rrr", traductor.traducirLineaALenguajeMaquina(asmRrr));
        Assert.assertEquals("B10A Jump", traductor.traducirLineaALenguajeMaquina(asmJump));
        Assert.assertEquals("C000 Stop", traductor.traducirLineaALenguajeMaquina(asmStop));
        Assert.assertEquals("D201 Mul", traductor.traducirLineaALenguajeMaquina(asmMul));
        Assert.assertEquals("E0FA JumpC", traductor.traducirLineaALenguajeMaquina(asmJumpC));
        Assert.assertEquals("F20 Cmp", traductor.traducirLineaALenguajeMaquina(asmCmp));
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
