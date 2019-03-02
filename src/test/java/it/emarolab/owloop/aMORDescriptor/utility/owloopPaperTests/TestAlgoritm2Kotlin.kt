package it.emarolab.owloop.aMORDescriptor.utility.owloopPaperTests

import it.emarolab.amor.owlInterface.OWLReferences
import it.emarolab.amor.owlInterface.OWLReferencesInterface
import it.emarolab.amor.owlInterface.SemanticRestriction
import org.junit.After
import org.junit.Before
import org.junit.Test

class TestAlgorithm2Kotlin {

    private lateinit var ontoref: OWLReferences
    private lateinit var d: ObjLinkIndividualDesc

    @Before // called a before every @Test
    fun setUp() {

        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole(false)

        //OWLReferences
        ontoref = OWLReferencesInterface.OWLReferencesContainer.newOWLReferenceFromFileWithPellet(
                "owloopTest", // ontology reference name
                "src/test/resources/owloopTestOntology.owl", // the ontology file path
                "http://www.semanticweb.org/yusha_temporary/owloopTestOntology", // the ontology IRI path
                true
        )

        d = ObjLinkIndividualDesc("Robot1", ontoref)
    }

    @After // called after all @Test-s
    fun save() {

        // d.saveOntology( "src/test/resources/owloopTestOntology.owl");
    }

    @Test
    fun algorithm2() {

        //          p: ObjLinkIndividualDesc -> ground: d, expression: objectProperty (build: ??ObjectProperty).
        // R=[r,r,..]: TypeIndividualDesc    -> ground: d, expression: classOf        (build: DefSubClassDesc),
        // T=[t,t,..]: DefSubClassDesc       -> ground: class,      expression: subclass       (build: ??ClassDescr).
        //                                                                      definition     (build: CANNOT).

        // setting the knowledge in the ontology
        d.addObject("isIn", "Room1") // just care this object property from the d d while readSematic
        d.writeSemantic()

        // get knowledge form the ontology
        val robotPlace = d.getObject("isIn")
        val p = TypeIndividualDesc(robotPlace, ontoref) // ... manually "build"
        p.readSemantic()
        val R = p.buildTypeIndividual()
        for (r in R) {

            val T = r.buildSubConcept()
            if (T.size <= 1) { // owl:Nothing is always there

                print("'" + ontoref.getOWLObjectName(p.instance) + "'" + " is of Type " + "'" + ontoref.getOWLObjectName(r.instance) + "'")
                val restrictions = r.definitionConcept
                for (restriction in restrictions) {

                    if (restriction is SemanticRestriction.ClassRestrictedOnExactObject) {

                        println("\n" + "'" + ontoref.getOWLObjectName(r.instance) + "'" + " is defined with Exact Cardinality Restriction " + "'" + restriction + "'")
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