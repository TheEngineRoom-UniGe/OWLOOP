package it.emarolab.owloop.articleExamples.example1;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.articleExamples.exampleDescriptors.*;
import it.emarolab.owloop.articleExamples.exampleDescriptors.RoomClassDesc;
import it.emarolab.owloop.descriptor.utility.classDescriptor.RestrictionClassDesc;
import it.emarolab.owloop.descriptor.utility.helperFunctions.OntologyReference;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.RestrictionIndividualDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.DomainRangeObjectPropertyDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.LocationClassDesc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    private OWLReferences ontoRef;

    @Before
    public void beforeTest() {

        // Disables printing of amor logs
        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( false);

        // To create a new ontologyReference. The ontology file need not be pre-existing.
        ontoRef = OntologyReference.newOWLReferencesCreatedWithPellet(
                "robotAtHomeOnto", // ontology reference name
                "src/test/resources/robotAtHomeOntology.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/robotAtHomeOntology", // the ontology IRI path
                true // if (true) you must synchronize the reasoner manually. Else, it synchronizes itself.
        );
    }

    @Test
    public void constructOntology() {

        // Add Class Expression Axioms to the ontology
        LocationClassDesc locationConcept_Desc = new LocationClassDesc( ontoRef);
        CorridorClassDesc corridorConcept_Desc = new CorridorClassDesc( ontoRef);
        RoomClassDesc roomConcept_Desc = new RoomClassDesc( ontoRef);

        System.out.println(locationConcept_Desc);

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

        // Adding some more details into the ontology

        // ROBOT concept disjoint with DOOR and LOCATION concepts
        RestrictionClassDesc robotConcept_Desc = new RestrictionClassDesc( "ROBOT", ontoRef);

        robotConcept_Desc.addDisjointClass( "LOCATION");
        robotConcept_Desc.addDisjointClass( "DOOR");
        robotConcept_Desc.writeExpressionAxioms();

        // All individuals are different from each other
        RestrictionIndividualDesc corridorIndividualRestriction_Desc = new RestrictionIndividualDesc( "Corridor1", ontoRef);

        corridorIndividualRestriction_Desc.addDisjointIndividual( "Robot1");
        corridorIndividualRestriction_Desc.addDisjointIndividual( "Room1");
        corridorIndividualRestriction_Desc.addDisjointIndividual( "Room2");
        corridorIndividualRestriction_Desc.writeExpressionAxioms();

        // This piece of code, is to show the error when we construct an inconsistent ontology
//        FullClassDesc robotDesc = new FullClassDesc( "ROBOT", ontoRef);
//        robotDesc.reason();
//        robotDesc.readExpressionAxioms(); // does this work? (because the ontology is inconsistent)
//        DescriptorEntitySet.Individuals a = robotDesc.queryIndividuals();
//        System.out.println(a); // What is inside this?

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