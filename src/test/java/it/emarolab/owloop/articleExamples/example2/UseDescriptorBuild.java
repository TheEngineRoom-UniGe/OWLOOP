package it.emarolab.owloop.articleExamples.example2;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.articleExamples.exampleDescriptors.DefSubClassDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.ObjectLinkIndividualDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.TypeIndividualDesc;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Restrictions;
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
        OWLNamedIndividual robotPosition = d1.getIndividualFromObjectProperty( "isIn"); // get knowledge from the internal state of the Descriptor

        TypeIndividualDesc d2 = new TypeIndividualDesc( robotPosition, ontoRef); // here, robotPosition = "Corridor1"
        d2.readExpressionAxioms();

        System.out.println( d2); // printing
        Set<DefSubClassDesc> setOfConceptTypes = d2.buildTypes(); // (Descriptor.buildTypes()) here, gets the Type/s (i.e., Class/s) of the Individual "Corridor1", as grounded Descriptors

        for( DefSubClassDesc d3 : setOfConceptTypes ){

            Set<DefSubClassDesc> setOfSubConcepts = d3.buildSubClasses(); // (Descriptor.buildSubClasses()) here, gets the subConcept/s of a Class, as grounded Descriptors

            if( setOfSubConcepts.size() == 1 ) { // to find the root Class, because it has max. 1 subConcept, i.e., owl:Nothing

                System.out.println( "'" + d3.getGroundInstanceName() + "' is the root Class among: "); // printing

                for( DefSubClassDesc conceptType : setOfConceptTypes ){

                    System.out.println( "\t\t\t'" + conceptType.getGroundInstanceName() + "'"); // printing
                }

                System.out.print( "\n'" + d2.getGroundInstanceName() + "'" + " is of Type " + "'" + d3.getGroundInstanceName() + "' \n"); // printing
                Restrictions restrictions = d3.getEquivalentRestrictions();

                for( SemanticRestriction rest : restrictions ){

                    if( rest instanceof SemanticRestriction.ClassRestrictedOnExactObject ){

                        System.out.println( "\n" + "'" + d3.getGroundInstanceName() + "'" + " is defined with Exact Cardinality EquivalentRestriction " + "'" + rest + "'"); // printing
                    }

                    else if( rest instanceof SemanticRestriction.ClassRestrictedOnMinObject ){

                        System.out.println( "\n" + "'" + d3.getGroundInstanceName() + "'" + " is defined with Min Cardinality EquivalentRestriction " + "'" + rest + "'"); // printing
                    }
                }
            }
        }
    }
}