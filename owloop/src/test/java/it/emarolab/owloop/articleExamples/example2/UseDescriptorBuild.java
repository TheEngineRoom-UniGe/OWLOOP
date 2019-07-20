package it.emarolab.owloop.articleExamples.example2;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.articleExamples.exampleDescriptors.DefSubConceptDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.ObjectLinkIndividualDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.TypeIndividualDesc;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.Set;

public class UseDescriptorBuild {

    private OWLReferences ontoRef;

    @Before
    public void beforeTest() {

        // Disables printing of amor logs
        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( false);

        // Ontology reference, newOWLReferencesCreatedWithPellet() allows to create a new ontology which does not exist
        ontoRef = OWLReferencesInterface.OWLReferencesContainer.newOWLReferenceFromFileWithPellet(
                "robotAtHomeOnto", // ontology reference name
                "src/test/resources/robotAtHomeOntology.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/robotAtHomeOntology", // the ontology IRI path
                true // if (true) you must synchronize the reasoner manually. Else, it synchronizes itself.
        );
    }

    @Test
    public void useDescriptorBuilding() {

        // Considering that the Robot's position is saved in the ontology after running Example1

        ObjectLinkIndividualDesc d1 = new ObjectLinkIndividualDesc( "Robot1", ontoRef);
        d1.addObject( "isIn",true); // to be able to read knowledge (only) specific to the property "isIn"
        d1.readExpressionAxioms(); // read all axioms (related to this descriptor) from the ontology
        System.out.println( d1); // printing to check the axioms represented by this descriptor
        OWLNamedIndividual robotPosition = d1.getObject( "isIn"); // get knowledge from the internal state of the Descriptor

        TypeIndividualDesc d2 = new TypeIndividualDesc( robotPosition, ontoRef); // here, robotPosition = "Corridor1"
        d2.readExpressionAxioms();

        System.out.println( d2); // printing
        Set<DefSubConceptDesc> setOfConceptTypes = d2.buildTypeIndividual(); // (Descriptor.buildTypeIndividual()) here, gets the Type/s (i.e., Concept/s) of the Individual "Corridor1", as grounded Descriptors

        for( DefSubConceptDesc d3 : setOfConceptTypes ){

            Set<DefSubConceptDesc> setOfSubConcepts = d3.buildSubConcept(); // (Descriptor.buildSubConcept()) here, gets the subConcept/s of a Concept, as grounded Descriptors

            if( setOfSubConcepts.size() == 1 ) { // to find the root Concept, because it has max. 1 subConcept, i.e., owl:Nothing

                System.out.println( "'" + d3.getGroundInstanceName() + "' is the root Concept among: "); // printing

                for( DefSubConceptDesc conceptType : setOfConceptTypes ){

                    System.out.println( "\t\t\t'" + conceptType.getGroundInstanceName() + "'"); // printing
                }

                System.out.print( "\n'" + d2.getGroundInstanceName() + "'" + " is of Type " + "'" + d3.getGroundInstanceName() + "' \n"); // printing
                DescriptorEntitySet.Restrictions restrictions = d3.getRestrictionConcepts();

                for( SemanticRestriction rest : restrictions ){

                    if( rest instanceof SemanticRestriction.ClassRestrictedOnExactObject ){

                        System.out.println( "\n" + "'" + d3.getGroundInstanceName() + "'" + " is defined with Exact Cardinality Restriction " + "'" + rest + "'"); // printing
                    }

                    else if( rest instanceof SemanticRestriction.ClassRestrictedOnMinObject ){

                        System.out.println( "\n" + "'" + d3.getGroundInstanceName() + "'" + " is defined with Min Cardinality Restriction " + "'" + rest + "'"); // printing
                    }
                }
            }
        }
    }
}

/*
//      Regarding the use of readExpressionAxioms()


        d1.addObject( "hasProp1", "X");
        d1.addObject( "hasProp2", "X");
        d1.writeExpressionAxioms();

        FullIndividualDesc d1 = new FullIndividualDesc("Robot1", ontoref);
        d1.readExpressionAxioms();          //at this point the descriptor reads all knowledge
        System.out.println( " 11 " + d1);

        FullIndividualDesc d2 = new FullIndividualDesc("Robot1", ontoref);
        d2.addObject( "hasProp1");
        d2.addObject( "hasProp2");
        d2.readExpressionAxioms();          //at this point the descriptor reads only particular knowledge
        System.out.println( " 22 " + d2);
*/


/*
We need ..OWLRefCreated.. to make an ontology which does not exist
We need ..OWLRefFromFile.. to interact with an ontology which already exists
From the ..OWLRefCreated.. file we can write/save in both ways (ontoref.save(), descriptor.save(path)) and CAN-READ in both ways (same descriptor, new descriptor).
From the ..OWLRefFromFile.. we can write/save in one way (descriptor.save(path)) and CAN-READ in both ways (same descriptor, new descriptor).
SynchronizeReasoner() to be in sync with the latest state of the ontology (else the descriptor reads the state of the ontology, when it was instantiated)
readExpressionAxioms(), when called for the first time, reads all (asserted and inferred) axioms because when the descriptor is initialized, syncOnto() is called.
With newOWLReferenceFromFileWithPellet(), decriptor.saveOntology(path), IS WORKING
With newOWLReferenceFromFileWithPellet(), ontoRef.saveOntology(path), IS WORKING
Its nice to (sync and save) after writing something to the ontology.
*/