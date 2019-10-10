package it.emarolab.owloop.articleExamples.example1;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.articleExamples.exampleDescriptors.CorridorClassDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.LocationClassDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.RoomClassDesc;
import it.emarolab.owloop.core.Axiom.Descriptor.*;
import it.emarolab.owloop.descriptor.utility.classDescriptor.RestrictionClassDesc;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.LinkIndividualDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.DomainRangeObjectPropertyDesc;

/**
 * An example to illustrate how to add axioms to an ontology.
 *
 * <p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         08/09/19 <br>
 * </small></div>
 */
public class addAxioms {

    public static void main(String[] args) {

        // TODO [Aesthetic] Change OWLReferences to OWLReference in AMOR
        OWLReferences ontoRef;

        // Disable printing of amor logs
        OntologyReference.activateAMORlogging(false);

        // TODO [Aesthetic] Change newOWLReferences...() to newOWLReference...() in AMOR
        // Create reference to a new ontology file associated with the Pellet reasoner
        ontoRef = OntologyReference.newOWLReferencesCreatedWithPellet(
                "robotAtHomeOnto", //ontology reference name.
                "src/test/resources/robotAtHomeOntology.owl", //file path.
                "http://www.semanticweb.org/emaroLab/robotAtHomeOntology", //IRI.
                true //synchronize the reasoner manually.
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
        robotIndividual_Desc.addObject("isIn", getRobotLocation());
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

        // Save the in-memory ontology to a .owl file in file-path provided during instantiation of ontoRef
        ontoRef.saveOntology();

        // Check implicit knowledge
        FullIndividualDesc corridorIndividual_FullDesc = new FullIndividualDesc("Corridor1", ontoRef);
        corridorIndividual_FullDesc.readAxioms();
        System.out.println(corridorIndividual_FullDesc);
    }

    private static String getRobotLocation() {

        // ... Consider that this method does some computation
        // ... and returns the location of the robot at home
        return "Corridor1";
    }
}

// TODO [Make developer's life easy] We can find-out whether something is wrong in the program by checking amorLOG for [[!!! ERROR !!!]]