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

    private static ObjLinkIndividualDesc individual;

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

        individual = new ObjLinkIndividualDesc("Robot1", ontoref);
    }

    @AfterClass // called after all @Test-s
    public static void save() {

        // individual.saveOntology( "src/test/resources/owloopTestOntology.owl");
    }

    @Test
    public void Algorithm2() {

        //          p: ObjLinkIndividualDesc -> ground: individual, expression: objectProperty (build: ??ObjectProperty).
        // R=[r,r,..]: TypeIndividualDesc    -> ground: individual, expression: classOf        (build: DefSubClassDesc),
        // T=[t,t,..]: DefSubClassDesc       -> ground: class,      expression: subclass       (build: ??ClassDescr).
        //                                                                      definition     (build: CANNOT).

        // setting the knowledge in the ontology
        String ISIN_PROPERTY = "isIn";
        ObjLinkIndividualDesc d = individual;
        d.addObject( ISIN_PROPERTY, "Room1"); // just care this object property from the individual d while readSematic
        d.writeSemantic();

        // get knowledge form the ontology
        OWLNamedIndividual robotPlace = d.getObject(ISIN_PROPERTY); // ...
        TypeIndividualDesc p = new TypeIndividualDesc(robotPlace, ontoref); // ... manually "build"
        p.readSemantic();
        Set<DefSubClassDesc> R = p.buildTypeIndividual();
        for( DefSubClassDesc r : R){
            Set<MORFullConcept> T = r.buildSubConcept();
            if( T.size() <= 1) { // owl:Nothing is always there
                System.out.print("'" + ontoref.getOWLObjectName(p.getInstance()) + "'" + " is of Type " + "'" + ontoref.getOWLObjectName(r.getInstance()) + "'");

                MORAxioms.Restrictions restrictions = r.getDefinitionConcept();
                for( SemanticRestriction rest : restrictions){
                    if( rest instanceof SemanticRestriction.ClassRestrictedOnExactObject){
                        System.out.println( "\n" + "'" + ontoref.getOWLObjectName(r.getInstance()) + "'" + " is defined with Exact Cardinality Restriction " + "'" + rest + "'");
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
        d1.readSemantic();
        System.out.println( " 11 " + d1);

        MORFullIndividual d2 = new MORFullIndividual("Robot1", ontoref);
        d2.addObject( "hasProp1");
        d2.addObject( "hasProp2");
        d2.readSemantic();
        System.out.println( " 22 " + d2);
*/