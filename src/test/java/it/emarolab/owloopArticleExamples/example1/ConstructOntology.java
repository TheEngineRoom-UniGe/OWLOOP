package it.emarolab.owloopArticleExamples.example1;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.DomainRangeObjectPropertyDesc;
import it.emarolab.owloopArticleExamples.exampleDescriptors.CorridorConceptDesc;
import it.emarolab.owloopArticleExamples.exampleDescriptors.LocationConceptDesc;
import it.emarolab.owloopArticleExamples.exampleDescriptors.ObjectLinkIndividualDesc;
import it.emarolab.owloopArticleExamples.exampleDescriptors.RoomConceptDesc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConstructOntology {

    private OWLReferences ontoRef;

    @Before
    public void beforeTest() {

        // Disables printing of amor logs
        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( false);

        // To create a new ontologyReference. The ontology file need not be pre-existing.
        ontoRef = OWLReferencesInterface.OWLReferencesContainer.newOWLReferencesCreatedWithPellet(
                "robotAtHomeOnto", // ontology reference name
                "src/test/resources/robotAtHomeOntology.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/robotAtHomeOntology", // the ontology IRI path
                true // if (true) you must synchronize the reasoner manually. Else, it synchronizes itself.
        );
    }

    @Test
    public void constructOntology() {

        // Add Class Expression Axioms to the ontology
        LocationConceptDesc locationConcept_Desc = new LocationConceptDesc( ontoRef);
        CorridorConceptDesc corridorConcept_Desc = new CorridorConceptDesc( ontoRef);
        RoomConceptDesc roomConcept_Desc = new RoomConceptDesc( ontoRef);

        // Add Individual Expression Axioms (i.e., Assertions) to the ontology
        ObjectLinkIndividualDesc corridorIndividual_Desc = new ObjectLinkIndividualDesc( "Corridor1", ontoRef); // Instantiate ObjectLinkIndividualDesc with ground as "Corridor1" and ontology reference as ontoRef
        ObjectLinkIndividualDesc robotIndividual_Desc = new ObjectLinkIndividualDesc( "Robot1", ontoRef);

        corridorIndividual_Desc.addObject( "isLinkedTo", "Room1");
        corridorIndividual_Desc.addObject( "isLinkedTo", "Room2");
        corridorIndividual_Desc.writeExpressionAxioms(); // write all axioms (represented in this descriptor) to the ontology

        robotIndividual_Desc.addObject( "isIn", getRobotPosition()); // consider that we get robot's position after some computation
        robotIndividual_Desc.writeExpressionAxioms();

        // Adding ObjectProperty Expression Axioms to the Ontology
        DomainRangeObjectPropertyDesc hasDoor_Desc = new DomainRangeObjectPropertyDesc( "hasDoor", ontoRef);
        hasDoor_Desc.addDomainClassRestriction( "LOCATION");
        hasDoor_Desc.addRangeClassRestriction( "DOOR");
        hasDoor_Desc.writeExpressionAxioms();


        DomainRangeObjectPropertyDesc isLinkedTo_Desc = new DomainRangeObjectPropertyDesc( "isLinkedTo", ontoRef);
        isLinkedTo_Desc.addDomainClassRestriction( "CORRIDOR");
        isLinkedTo_Desc.addRangeClassRestriction( "ROOM");
        isLinkedTo_Desc.writeExpressionAxioms();

        DomainRangeObjectPropertyDesc isIn_Desc = new DomainRangeObjectPropertyDesc( "isIn", ontoRef);
        isIn_Desc.addDomainClassRestriction( "ROBOT");
        isIn_Desc.addRangeClassRestriction( "LOCATION");
        isIn_Desc.writeExpressionAxioms();
    }

    private String getRobotPosition() {

        // ... consider that this method does some computation and finally returns the robot's position
        // ...
        return "Corridor1";
    }

    @After
    public void afterTest() {

        // saveOntology() works for ontologyReference instantiated with the method newOWLReferencesCreatedWithPellet()
        ontoRef.saveOntology();
    }
}