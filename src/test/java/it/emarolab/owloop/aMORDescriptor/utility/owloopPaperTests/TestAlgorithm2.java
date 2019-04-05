package it.emarolab.owloop.aMORDescriptor.utility.owloopPaperTests;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.utility.concept.MORFullConcept;
import it.emarolab.owloop.aMORDescriptor.utility.individual.MORFullIndividual;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestAlgorithm2 {

    private static ObjLinkIndividualDesc d;

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

        d = new ObjLinkIndividualDesc("Robot1", ontoref);
    }

    @AfterClass // called after all @Test-s
    public static void save() {

        // d.saveOntology( "src/test/resources/owloopTestOntology.owl");
    }

    @Test
    public void Algorithm2() {

        //          p: ObjLinkIndividualDesc -> ground: d,          expression: objectProperty (build: ??ObjectProperty).
        // R=[r,r,..]: TypeIndividualDesc    -> ground: d,          expression: classOf        (build: DefSubClassDesc),
        // T=[t,t,..]: DefSubClassDesc       -> ground: class,      expression: subclass       (build: ??ClassDescr).
        //                                                                      definition     (build: CANNOT).

        // Assuming that knowledge of the Robot's position is saved in the ontology after running Algo.1
        // Get knowledge form the ontology
        d.readSemantic();
        OWLNamedIndividual robotPlace = d.getObject("isIn"); // ...
        TypeIndividualDesc p = new TypeIndividualDesc(robotPlace, ontoref); // ... manually "build"
        p.readSemantic();
        Set<DefSubClassDesc> R = p.buildTypeIndividual();
        for( DefSubClassDesc r : R ){

            Set<MORFullConcept> T = r.buildSubConcept();
            if( T.size() <= 1 ) { // owl:Nothing is always there

                System.out.print("'" + p.getInstanceName() + "'" + " is of Type " + "'" + r.getInstanceName() + "'");
                MORAxioms.Restrictions restrictions = r.getDefinitionConcept();
                for( SemanticRestriction rest : restrictions ){

                    if( rest instanceof SemanticRestriction.ClassRestrictedOnExactObject ){

                        System.out.println( "\n" + "'" + r.getInstanceName() + "'" + " is defined with Exact Cardinality Restriction " + "'" + rest + "'");
                    }
                    else if( rest instanceof SemanticRestriction.ClassRestrictedOnMinObject ){

                        System.out.println( "\n" + "'" + r.getInstanceName() + "'" + " is defined with Min Cardinality Restriction " + "'" + rest + "'");
                    }
                }
            }
        }
    }
}

/*
//      Regarding the use of readSemantic()

        d.addObject( "hasProp1", "X");
        d.addObject( "hasProp2", "X");
        d.writeSemantic();

        MORFullIndividual d1 = new MORFullIndividual("Robot1", ontoref);
        d1.readSemantic();          //at this point the descriptor reads all knowledge
        System.out.println( " 11 " + d1);

        MORFullIndividual d2 = new MORFullIndividual("Robot1", ontoref);
        d2.addObject( "hasProp1");
        d2.addObject( "hasProp2");
        d2.readSemantic();          //at this point the descriptor reads only particular knowledge
        System.out.println( " 22 " + d2);
*/