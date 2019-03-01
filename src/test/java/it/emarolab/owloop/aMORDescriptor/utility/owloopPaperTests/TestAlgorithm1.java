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
    public void algorithm1() {

        String pose = "Corridor1";

        MORFullIndividual d = individual;
        d.readSemantic();

        d.addTypeIndividual("Robot");
        d.addObject("isIn",pose);
        d.writeSemantic();
        assertSemantic();
    }

    private void assertSemantic(){//         asserts that the state of the java representation is equal to the state of the ontology

        assertEquals( individual.getDisjointIndividual(), individual.queryDisjointIndividual());
        assertEquals( individual.getEquivalentIndividual(), individual.queryEquivalentIndividual());

        assertEquals( individual.getTypeIndividual(), individual.queryTypeIndividual());
        System.out.println(individual.getTypeIndividual());
        System.out.println(individual.queryTypeIndividual());

        assertEquals( individual.getObjectSemantics(), individual.queryObject());
        System.out.println("==========");
        System.out.println(individual.getObjectSemantics());
        System.out.println(individual.queryObject());

        assertEquals( individual.getDataSemantics(), individual.queryDataIndividual());
    }
}