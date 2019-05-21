package it.emarolab.owloop.descriptor.utility.owloopArticleExamples.exampleOne;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.DomainRangeObjectPropertyDesc;
import it.emarolab.owloop.descriptor.utility.owloopArticleExamples.exampleDescriptors.CorridorConceptDesc;
import it.emarolab.owloop.descriptor.utility.owloopArticleExamples.exampleDescriptors.LocationConceptDesc;
import it.emarolab.owloop.descriptor.utility.owloopArticleExamples.exampleDescriptors.ObjectLinkIndividualDesc;
import it.emarolab.owloop.descriptor.utility.owloopArticleExamples.exampleDescriptors.RoomConceptDesc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConstructOntology {

    private OWLReferences ontoRef;

    @Before
    public void beforeTest() {

        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( false);

        // newOWLReferencesCreatedWithPellet() creates a new ontology
        ontoRef = OWLReferencesInterface.OWLReferencesContainer.newOWLReferencesCreatedWithPellet(
                "robotAtHomeOnto", // ontology reference name
                "src/test/resources/robotAtHomeOntology.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/robotAtHomeOntology", // the ontology IRI path
                true
        );
    }

    @Test
    public void constructOntology() {

        // Add Classes (and their descriptions) to the ontology
        LocationConceptDesc locationConcept_Desc = new LocationConceptDesc( ontoRef);
        CorridorConceptDesc corridorConcept_Desc = new CorridorConceptDesc( ontoRef);
        RoomConceptDesc roomConcept_Desc = new RoomConceptDesc( ontoRef);

        // Add Individuals (and their assertions) to the ontology
        ObjectLinkIndividualDesc corridorIndividual_Desc = new ObjectLinkIndividualDesc( "Corridor1", ontoRef);
        ObjectLinkIndividualDesc robotIndividual_Desc = new ObjectLinkIndividualDesc( "Robot1", ontoRef);

        corridorIndividual_Desc.addObject( "isLinkedTo", "Room1");
        corridorIndividual_Desc.addObject( "isLinkedTo", "Room2");
        corridorIndividual_Desc.writeExpressionAxioms();

        robotIndividual_Desc.addObject( "isIn", getRobotPosition()); // consider that the assertion is made based on some computation
        robotIndividual_Desc.writeExpressionAxioms();

        // Add/Modify ObjectProperty (and their descriptions) to the ontology
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

        // ... consider that this method does some computation and returns the robot's position
        return "Corridor1";
    }

    @After
    public void afterTest() {

        ontoRef.saveOntology();
    }

}