package it.emarolab.owloop.articleExamples.example1;

import com.clarkparsia.owlapi.explanation.ExplanationGenerator;
import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.ReasonerExplanator;
import it.emarolab.owloop.articleExamples.exampleDescriptors.CorridorClassDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.LocationClassDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.ObjectLinkIndividualDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.RoomClassDesc;
import it.emarolab.owloop.core.Axiom.Descriptor.*;
import it.emarolab.owloop.descriptor.utility.classDescriptor.RestrictionClassDesc;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.LinkIndividualDesc;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.RestrictionIndividualDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.DomainRangeObjectPropertyDesc;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import uk.ac.manchester.cs.owl.explanation.ordering.ExplanationTree;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplInteger;

public class addAxioms {

    public static void main(String[] args) {

        OWLReferences ontoRef;

        // Disable printing of amor logs
        OntologyReference.activateAMORlogging( false);

        // Create a new ontology file associated to Pellet reasoner
        ontoRef = OntologyReference.newOWLReferencesCreatedWithPellet(
                "robotAtHomeOnto", // ontology reference name.
                "src/test/resources/robotAtHomeOntology.owl", // file path.
                "http://www.semanticweb.org/emaroLab/robotAtHomeOntology", // IRI.
                true // true, synchronizes the reasoner manually. Else, automatic.
        );

        // Add Class Expression Axioms to the ontology
        LocationClassDesc locationClass_Desc = new LocationClassDesc(ontoRef);
        CorridorClassDesc corridorClass_Desc = new CorridorClassDesc(ontoRef);
        RoomClassDesc roomClass_Desc = new RoomClassDesc(ontoRef);

        // Add Individual Expression Axioms (i.e., Assertions) to the ontology
        LinkIndividualDesc corridorIndividual_Desc = new LinkIndividualDesc("Corridor1", ontoRef);
        corridorIndividual_Desc.addObject("isLinkedTo", "Room1");
        corridorIndividual_Desc.addObject("isLinkedTo", "Room2");
        corridorIndividual_Desc.writeAxiomsReasonReadAxioms();

        LinkIndividualDesc robotIndividual_Desc = new LinkIndividualDesc( "Robot1", ontoRef);
        robotIndividual_Desc.addObject("isIn", getRobotPosition());
        robotIndividual_Desc.writeAxiomsReasonReadAxioms();

        LinkIndividualDesc roomIndividual_Desc = new LinkIndividualDesc("Room1", ontoRef);
        roomIndividual_Desc.addData("hasTemperature", 25);
        roomIndividual_Desc.writeAxioms();

        // Add ObjectProperty Expression Axioms to the Ontology
        DomainRangeObjectPropertyDesc isLinkedTo_Desc = new DomainRangeObjectPropertyDesc( "isLinkedTo", ontoRef);
        isLinkedTo_Desc.addDomainClassRestriction( "CORRIDOR");
        isLinkedTo_Desc.addRangeClassRestriction( "ROOM");
        isLinkedTo_Desc.writeAxiomsReasonReadAxioms();

        DomainRangeObjectPropertyDesc isIn_Desc = new DomainRangeObjectPropertyDesc( "isIn", ontoRef);
        isIn_Desc.addDomainClassRestriction( "ROBOT");
        isIn_Desc.addRangeClassRestriction( "LOCATION");
        isIn_Desc.writeAxiomsReasonReadAxioms();

        // Add some more details into the ontology
        // ROBOT concept disjoint with DOOR and LOCATION concepts
        RestrictionClassDesc robotClass_Desc = new RestrictionClassDesc( "ROBOT", ontoRef);
        robotClass_Desc.addDisjointClass( "LOCATION");
        robotClass_Desc.addDisjointClass( "DOOR");
        robotClass_Desc.writeAxiomsReasonReadAxioms();

        // saveOntology() works for ontologyReference instantiated with the method newOWLReferencesCreatedWithPellet()
        ontoRef.saveOntology();

        // Check implicit knowledge
        FullIndividualDesc corridorIndividual_FullDesc = new FullIndividualDesc("Corridor1", ontoRef);
        corridorIndividual_FullDesc.readAxioms();
        System.out.println(corridorIndividual_FullDesc);
    }

    private static String getRobotPosition() {

        // ... Consider that this method does some computation
        // ... and returns the position of the robot at home
        return "Corridor1";
    }
}