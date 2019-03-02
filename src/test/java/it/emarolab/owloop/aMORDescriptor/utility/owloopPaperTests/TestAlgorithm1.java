package it.emarolab.owloop.aMORDescriptor.utility.owloopPaperTests;

import it.emarolab.owloop.aMORDescriptor.utility.individual.MORFullIndividual;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAlgorithm1 {

    private static MORFullIndividual d;

    @Before // called a before every @Test
    public void setUp() {

        d = new MORFullIndividual(

                "Robot1", // the ground instance name
                "owloopTest", // ontology reference name
                "src/test/resources/owloopTestOntology.owl", // the ontology file path
                "http://www.semanticweb.org/yusha_temporary/owloopTestOntology" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() {

        d.saveOntology( "src/test/resources/owloopTestOntology.owl");
    }

    @Test
    public void algorithm1() {

        String pose = "Corridor1";

        d.readSemantic();
        d.addTypeIndividual("Robot");
        d.addObject("isIn",pose);
        d.writeSemantic();

        assertSemantic();
    }

    private void assertSemantic(){// asserts that the state of the java representation is equal to the state of the ontology

        assertEquals(d.getTypeIndividual(), d.queryTypeIndividual());
        assertEquals(d.getObjectSemantics(), d.queryObject());
    }
}