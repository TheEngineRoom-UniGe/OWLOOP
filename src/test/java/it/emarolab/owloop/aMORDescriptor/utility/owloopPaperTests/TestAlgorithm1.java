package it.emarolab.owloop.aMORDescriptor.utility.owloopPaperTests;

import it.emarolab.owloop.aMORDescriptor.utility.individual.MORFullIndividual;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAlgorithm1 {

    private static MORFullIndividual individual;

    @Before // called a before every @Test
    public void setUp() {

        individual = new MORFullIndividual(

                "Robot1", // the ground instance name
                "owloopTest", // ontology reference name
                "src/test/resources/owloopTestOntology.owl", // the ontology file path
                "http://www.semanticweb.org/yusha_temporary/owloopTestOntology" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() {

        individual.saveOntology( "src/test/resources/owloopTestOntology.owl");
    }

    @Test
    public void Algorithm1() {

        String pose = "Corridor1";

        MORFullIndividual d = individual;
        d.readSemantic();
        d.addObject("isIn",pose,true); //singleton True means with replacement
        d.writeSemantic();
        assertSemantic();
    }

    private void assertSemantic(){//         asserts that the state of the java representation is equal to the state of the ontology

        assertEquals( individual.getTypeIndividual(), individual.queryTypeIndividual()); //checks whether readSemantic() works properly
        assertEquals( individual.getObjectSemantics(), individual.queryObject());
    }
}