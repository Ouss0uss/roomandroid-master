package calculatrice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OperationsTest {
    public Operations op;
    @Before
    public void setUp() throws Exception {

        op = new Operations() ;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addition() {
        assertEquals((Character)'+', op.lireSymbole());
    }

    @Test
    public void testCalculer() throws Exception {
        assertEquals(new Long(4),
                op.addition(new Long(1), new Long(3)));
    }

    @Test
    public void valeursExactementEgales() {
        final double lResultat = Operations.soustraire(5, 2.5);
        assertEquals(2.5, lResultat, 0);
    }

    @Test
    public void valeursAvecUneLegereDifference() {
        final double lResultat = Operations.soustraire(71.19, 98);
        assertEquals(-26.81, lResultat, 0.005);
    }

    @Test
    public void valeursAvecUneDifferenceTropImportante() {
        final double lResultat = Operations.soustraire(71.19, 98);
        assertEquals(-26.81, lResultat, 0);     }



}