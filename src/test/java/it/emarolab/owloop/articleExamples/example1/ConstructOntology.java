package it.emarolab.owloop.articleExamples.example1;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.articleExamples.exampleDescriptors.*;
import it.emarolab.owloop.articleExamples.exampleDescriptors.RoomClassDesc;
import it.emarolab.owloop.descriptor.utility.classDescriptor.RestrictionClassDesc;
import it.emarolab.owloop.core.Axiom.Descriptor.*;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.RestrictionIndividualDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.DomainRangeObjectPropertyDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.LocationClassDesc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * An example to show how to add axioms, i.e, classes, objectProperties, dataProperties and individuals, to an ontology by using descriptors.
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
public class ConstructOntology {
    // TODO: The robot-at-home ontology is inconsistent. solve it
    private OWLReferences ontoRef;

    @Before
    public void beforeTest() {

        // Disables printing of amor logs
        OntologyReference.activateAMORlogging( false);

        // Creates a new ontology file associated to Pellet reasoner
        ontoRef = OntologyReference.newOWLReferencesCreatedWithPellet(
                "robotAtHomeOnto", // ontology reference name.
                "src/test/resources/robotAtHomeOntology.owl", // file path.
                "http://www.semanticweb.org/emaroLab/robotAtHomeOntology", // IRI.
                true // true, synchronizes the reasoner manually. Else, automatic.
        );
    }

    @Test
    public void constructOntology() {

        // Add Class Expression Axioms to the ontology
        LocationClassDesc locationConcept_Desc = new LocationClassDesc(ontoRef);
        CorridorClassDesc corridorConcept_Desc = new CorridorClassDesc(ontoRef);
        RoomClassDesc roomConcept_Desc = new RoomClassDesc(ontoRef);

        // Add Individual Expression Axioms (i.e., Assertions) to the ontology
        ObjectLinkIndividualDesc corridorIndividual_Desc = new ObjectLinkIndividualDesc("Corridor1", ontoRef);
        corridorIndividual_Desc.addObject("isLinkedTo", "Room1");
        corridorIndividual_Desc.addObject("isLinkedTo", "Room2");
        corridorIndividual_Desc.writeAxiomsReasonReadAxioms();
//        assertEquals(corridorIndividual_Desc.getObjectProperties(), corridorIndividual_Desc.queryObjectProperties());

        ObjectLinkIndividualDesc robotIndividual_Desc = new ObjectLinkIndividualDesc( "Robot1", ontoRef);
        robotIndividual_Desc.addObject( "isIn", getRobotPosition());
        robotIndividual_Desc.writeAxiomsReasonReadAxioms();
//        assertEquals(robotIndividual_Desc.getObjectProperties(), robotIndividual_Desc.queryObjectProperties());

        // Adding ObjectProperty Expression Axioms to the Ontology
        DomainRangeObjectPropertyDesc hasDoor_Desc = new DomainRangeObjectPropertyDesc( "hasDoor", ontoRef);
        hasDoor_Desc.addDomainClassRestriction( "LOCATION");
        hasDoor_Desc.addRangeClassRestriction( "DOOR");
        hasDoor_Desc.writeAxiomsReasonReadAxioms();
//        assertEquals(hasDoor_Desc.getDomainRestrictions(), hasDoor_Desc.queryDomainRestrictions());
//        assertEquals(hasDoor_Desc.getRangeRestrictions(), hasDoor_Desc.queryRangeRestrictions());

        DomainRangeObjectPropertyDesc isLinkedTo_Desc = new DomainRangeObjectPropertyDesc( "isLinkedTo", ontoRef);
        isLinkedTo_Desc.addDomainClassRestriction( "CORRIDOR");
        isLinkedTo_Desc.addRangeClassRestriction( "ROOM");
        isLinkedTo_Desc.writeAxiomsReasonReadAxioms();
//        assertEquals(isLinkedTo_Desc.getDomainRestrictions(), isLinkedTo_Desc.queryDomainRestrictions());
//        assertEquals(isLinkedTo_Desc.getRangeRestrictions(), isLinkedTo_Desc.queryRangeRestrictions());

        DomainRangeObjectPropertyDesc isIn_Desc = new DomainRangeObjectPropertyDesc( "isIn", ontoRef);
        isIn_Desc.addDomainClassRestriction( "ROBOT");
        isIn_Desc.addRangeClassRestriction( "LOCATION");
        isIn_Desc.writeAxiomsReasonReadAxioms();
//        assertEquals(isIn_Desc.getDomainRestrictions(), isIn_Desc.queryDomainRestrictions());
//        assertEquals(isIn_Desc.getRangeRestrictions(), isIn_Desc.queryRangeRestrictions());

        // Adding some more details into the ontology

        // ROBOT concept disjoint with DOOR and LOCATION concepts
        RestrictionClassDesc robotConcept_Desc = new RestrictionClassDesc( "ROBOT", ontoRef);
        robotConcept_Desc.addDisjointClass( "LOCATION");
        robotConcept_Desc.addDisjointClass( "DOOR");
        robotConcept_Desc.writeAxiomsReasonReadAxioms();
//        assertEquals(robotConcept_Desc.getDisjointClasses(), robotConcept_Desc.queryDisjointClasses());

        // All individuals are different from each other
        RestrictionIndividualDesc corridorIndividualRestriction_Desc = new RestrictionIndividualDesc( "Corridor1", ontoRef);
        corridorIndividualRestriction_Desc.addDisjointIndividual( "Robot1");
        corridorIndividualRestriction_Desc.addDisjointIndividual( "Room1");
        corridorIndividualRestriction_Desc.addDisjointIndividual( "Room2");
        corridorIndividualRestriction_Desc.writeAxiomsReasonReadAxioms();
//        assertEquals(corridorIndividualRestriction_Desc.getDisjointIndividuals(), corridorIndividualRestriction_Desc.queryDisjointIndividuals());
    }

    private String getRobotPosition() {

        // ... consider that this method does some heavy computation and finally returns the robot's position
        // ...
        return "Corridor1";
    }

    @After
    public void afterTest() {

        // saveOntology() works for ontologyReference instantiated with the method newOWLReferencesCreatedWithPellet()
        ontoRef.saveOntology();
    }
}

// TODO: what happens when ontology is inconsistent (do we say something in aMORlogs or even apart from that)