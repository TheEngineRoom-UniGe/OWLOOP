package it.emarolab.owloop.descriptor.utility.owloopPaperTests;

import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAlgorithm1 {

    private static FullIndividualDesc individual;

    @Before // called a before every @Test
    public void setUp() {

        individual = new FullIndividualDesc(

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

        String pos = getRobotPosition();

        FullIndividualDesc d = individual;
        d.addObject("isIn",pos,true); //singleton True means with replacement
        d.writeSemantic();
    }

    private String getRobotPosition() {
        return "Corridor1";
    }
}