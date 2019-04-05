package it.emarolab.owloop.aMORDescriptor.utility.owloopPaperTests;

import it.emarolab.owloop.aMORDescriptor.utility.individual.MORFullIndividual;
import it.emarolab.owloop.aMORDescriptor.utility.objectProperty.MORFullObjectProperty;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

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

        String pos = getRobotPosition();

        MORFullIndividual d = individual;
        d.addObject("isIn",pos,true); //singleton True means with replacement
        d.writeSemantic();
    }

    private String getRobotPosition() {
        return "Corridor1";
    }
}