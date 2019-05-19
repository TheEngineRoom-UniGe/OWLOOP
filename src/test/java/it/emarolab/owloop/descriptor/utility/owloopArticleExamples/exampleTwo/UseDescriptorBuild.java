package it.emarolab.owloop.descriptor.utility.owloopArticleExamples.exampleTwo;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.utility.conceptDescriptor.FullConceptDescriptor;
import it.emarolab.owloop.descriptor.utility.owloopArticleExamples.exampleDescriptors.DefSubConceptDesc;
import it.emarolab.owloop.descriptor.utility.owloopArticleExamples.exampleDescriptors.TypeIndividualDesc;
import it.emarolab.owloop.descriptor.utility.owloopArticleExamples.exampleDescriptors.ObjectLinkIndividualDesc;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.Set;

public class UseDescriptorBuild {

    private OWLReferences ontoRef;

    @Before
    public void beforeTest() {

        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( false);

        // Ontology reference, newOWLReferencesCreatedWithPellet() allows to create a new ontology which does not exist
        ontoRef = OWLReferencesInterface.OWLReferencesContainer.newOWLReferenceFromFileWithPellet(
                "robotAtHomeOnto", // ontology reference name
                "src/test/resources/robotAtHomeOntology.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/robotAtHomeOntology", // the ontology IRI path
                true
        );
    }

    @Test
    public void useDescriptorBuilding() {

        // Considering that the Robot's position is saved in the ontology after running ExampleOne

        ObjectLinkIndividualDesc d1 = new ObjectLinkIndividualDesc( "Robot1", ontoRef); // Initialize a DESC with ground as Robot1
        d1.addObject( "isIn",true);                              // To read knowledge specific to the property "isIn"
        d1.readExpressionAxioms();                                               // READ
        System.out.println( d1);                                                 // PRINT
        OWLNamedIndividual robotPosition = d1.getObject( "isIn");    // getObject("isIn")

        TypeIndividualDesc d2 = new TypeIndividualDesc( robotPosition, ontoRef); // Initialize a DESC with ground as Corridor1
        d2.readExpressionAxioms();                                               // READ

        System.out.println( d2);                                                 // PRINT
        Set<DefSubConceptDesc> setOfTypes = d2.buildTypeIndividual();            // BUILD Type of an Individual --> CORRIDOR,LOCATION,Top
        for( DefSubConceptDesc d3 : setOfTypes ){

            Set<FullConceptDescriptor> setOfSubTypes = d3.buildSubConcept();     // BUILD Sub of a Concept --> we have the sub-classes of all the 3 above

            if( setOfSubTypes.size() <= 1 ) {                                    // If less than or equal to 1 (owl:Nothing is always there )

                System.out.print("'" + d2.getInstanceName() + "'" + " is of Type " + "'" + d3.getInstanceName() + "' \n"); // PRINT
                DescriptorEntitySet.Restrictions restrictions = d3.getDefinitionConcept(); //GET DEFINITION
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
//      Regarding the use of readExpressionAxioms()


        d1.addObject( "hasProp1", "X");
        d1.addObject( "hasProp2", "X");
        d1.writeExpressionAxioms();

        FullIndividualDescriptor d1 = new FullIndividualDescriptor("Robot1", ontoref);
        d1.readExpressionAxioms();          //at this point the descriptor reads all knowledge
        System.out.println( " 11 " + d1);

        FullIndividualDescriptor d2 = new FullIndividualDescriptor("Robot1", ontoref);
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
*/