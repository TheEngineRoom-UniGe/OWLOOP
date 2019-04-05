package it.emarolab.owloop.aMORDescriptor.utility.owloopPaperTests;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.utility.concept.MORFullConcept;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestAlgorithm2 {

    private static ObjLinkIndividualDesc d1;

    private OWLReferences ontoref;

    @Before // called a before every @Test
    public void setUp() {

        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( false);

        //OWLReferences
        ontoref = OWLReferencesInterface.OWLReferencesContainer.newOWLReferenceFromFileWithPellet(
                "owloopTest", // ontology reference name
                "src/test/resources/owloopTestOntology.owl", // the ontology file path
                "http://www.semanticweb.org/yusha_temporary/owloopTestOntology", // the ontology IRI path
                true
        );

    d1 = new ObjLinkIndividualDesc("Robot1", ontoref);           // Initialize a DESC with ground as Robot1
    }

    @AfterClass // called after all @Test-s
    public static void save() {

        // d1.saveOntology( "src/test/resources/owloopTestOntology.owl");
    }

    @Test
    public void Algorithm2() {

        //          p: ObjLinkIndividualDesc -> ground: d1,          expression: objectProperty (build: ??ObjectProperty).
        // R=[r,r,..]: TypeIndividualDesc    -> ground: d1,          expression: classOf        (build: DefSubClassDesc).
        // T=[t,t,..]: DefSubClassDesc       -> ground: class,      expression: subclass       (build: ??ClassDescr).
        //                                                                      definition     (build: CANNOT).

        // Assuming that knowledge of the Robot's position is saved in the ontology after running Algo.1
        // Get knowledge form the ontology
        d1.addObject("isIn");                                        // To read knowledge specific to the property "isIn"
        d1.readSemantic();                                                   // READ
        OWLNamedIndividual robotPlace = d1.getObject("isIn");    // getObject("isIn")

        TypeIndividualDesc d2 = new TypeIndividualDesc(robotPlace, ontoref); // Initialize a DESC with ground as Corridor1
        d2.readSemantic();                                                   // READ
        Set<DefSubClassDesc> DT = d2.buildTypeIndividual();                   // BUILD Type of an Individual --> CORRIDOR,LOCATION,Top
        for( DefSubClassDesc d3 : DT ){

            Set<MORFullConcept> DS = d3.buildSubConcept();                    // BUILD Sub of a Concept --> we have the sub-classes of all the 3 above
            if( DS.size() <= 1 ) { // owl:Nothing is always there            // If less than or equal to 1

                System.out.print("'" + d2.getInstanceName() + "'" + " is of Type " + "'" + d3.getInstanceName() + "'"); // PRINT
                MORAxioms.Restrictions restrictions = d3.getDefinitionConcept(); //GET DEFINITION
                for( SemanticRestriction rest : restrictions ){

                    if( rest instanceof SemanticRestriction.ClassRestrictedOnExactObject ){

                        System.out.println( "\n" + "'" + d3.getInstanceName() + "'" + " is defined with Exact Cardinality Restriction " + "'" + rest + "'"); //PRINT
                    }
                    else if( rest instanceof SemanticRestriction.ClassRestrictedOnMinObject ){

                        System.out.println( "\n" + "'" + d3.getInstanceName() + "'" + " is defined with Min Cardinality Restriction " + "'" + rest + "'"); //PRINT
                    }
                }
            }
        }
    }
}

/*
//      Regarding the use of readSemantic()

        d1.addObject( "hasProp1", "X");
        d1.addObject( "hasProp2", "X");
        d1.writeSemantic();

        MORFullIndividual d1 = new MORFullIndividual("Robot1", ontoref);
        d1.readSemantic();          //at this point the descriptor reads all knowledge
        System.out.println( " 11 " + d1);

        MORFullIndividual d2 = new MORFullIndividual("Robot1", ontoref);
        d2.addObject( "hasProp1");
        d2.addObject( "hasProp2");
        d2.readSemantic();          //at this point the descriptor reads only particular knowledge
        System.out.println( " 22 " + d2);
*/