package it.emarolab.owloop.descriptor.utility.owloopPaperTests.exampleOne;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.owloop.descriptor.utility.owloopPaperTests.exampleDescriptors.ObjectLinkIndividualDesc;
import org.apache.jena.base.Sys;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class ConstructOntology {

//    private static OWLReferences ontoRef;

    private OWLReferences ontorefToWrite;
    private OWLReferences ontorefToRead;

    @Before
    public void beforeTest() {

        System.out.println("Entered beforeTest");

        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( false);

//        //OWLReferences
//        ontoRef = OWLReferencesInterface.OWLReferencesContainer.newOWLReferencesCreatedWithPellet(
//                "robotAtHomeOnto", // ontology reference name
//                "src/test/resources/robotAtHomeOntology.owl", // the ontology file path
//                "http://www.semanticweb.org/yusha_temporary/robotAtHomeOntology", // the ontology IRI path
//                true
//        );

        //OWLReferences
        ontorefToWrite = OWLReferencesInterface.OWLReferencesContainer.newOWLReferencesCreatedWithPellet(
                "owloopTestDeleteWrite", // ontology reference name
                "src/test/resources/owloopTestOntologyDelete.owl", // the ontology file path
                "http://www.semanticweb.org/yusha_temporary/owloopTestOntologyDelete", // the ontology IRI path
                true
        );

        System.out.println("Exit beforeTest");

    }

    @Test
    public void constructOntology() {

        System.out.println("Entered WRITE");

        // Add stuff to the descriptor
        ObjectLinkIndividualDesc robotIndividual = new ObjectLinkIndividualDesc("Robot1", ontorefToWrite);
        System.out.println("========At initialization");
        System.out.println(robotIndividual);

        robotIndividual.addObject("isIn","Corridor1");
        robotIndividual.addObject("isAlsoIn","Room1");
        System.out.println("========At addObject");
        System.out.println(robotIndividual);

        // synchronise descriptor with ontology
        robotIndividual.writeExpressionAxioms();
        System.out.println("========At writeExpAxioms");
        System.out.println(robotIndividual);

        ontorefToWrite.saveOntology();

         // update the latest state of the ontology
        ObjectLinkIndividualDesc robotDeleterDesc = new ObjectLinkIndividualDesc("Robot1", ontorefToWrite);
        ontorefToWrite.synchronizeReasoner();
        robotDeleterDesc.readExpressionAxioms();
        System.out.println(robotDeleterDesc);
        robotDeleterDesc.removeObject("isAlsoIn","Room1");
        robotDeleterDesc.writeExpressionAxioms();
        ontorefToWrite.synchronizeReasoner();
        robotDeleterDesc.readExpressionAxioms();
        System.out.println(robotDeleterDesc);
        ontorefToWrite.saveOntology();
        robotDeleterDesc.saveOntology(robotDeleterDesc.getInstanceName());


//        // Add Classes (and their descriptions) to the ontology
//        LocationConceptDesc locationConcept_Desc = new LocationConceptDesc( ontoRef);
//        CorridorConceptDesc corridorConcept_Desc = new CorridorConceptDesc( ontoRef);
//        RoomConceptDesc roomConcept_Desc = new RoomConceptDesc( ontoRef);
//
//        // Add Individuals (and their assertions) to the ontology
//        ObjectLinkIndividualDesc corridorIndividual_Desc = new ObjectLinkIndividualDesc( "Corridor1", ontoRef);
//        ObjectLinkIndividualDesc robotIndividual_Desc = new ObjectLinkIndividualDesc( "Robot1", ontoRef);
//
//        corridorIndividual_Desc.addObject( "isLinkedTo", "Room1");
//        corridorIndividual_Desc.addObject( "isLinkedTo", "Room2");
//        corridorIndividual_Desc.writeExpressionAxioms();
//
//        robotIndividual_Desc.addObject( "isIn", getRobotPosition()); // consider that the assertion is made based on some computation
//        robotIndividual_Desc.writeExpressionAxioms();
//
//        // Add/Modify ObjectProperty (and their descriptions) to the ontology
//        DomainRangeObjectPropertyDesc isLinkedTo_Desc = new DomainRangeObjectPropertyDesc( "isLinkedTo", ontoRef);
//
//        isLinkedTo_Desc.addDomainClassRestriction( "LOCATION");
//        isLinkedTo_Desc.addRangeClassRestriction( "LOCATION");
//        isLinkedTo_Desc.writeExpressionAxioms();
//
//        DomainRangeObjectPropertyDesc isIn_Desc = new DomainRangeObjectPropertyDesc( "isIn", ontoRef);
//        isIn_Desc.addDomainClassRestriction( "ROBOT");
//        isIn_Desc.addRangeClassRestriction( "LOCATION");
//        isIn_Desc.writeExpressionAxioms();

        System.out.println("Exit Construct");

    }

    private void localTest() {

        System.out.println("Entered READ");

        // reading with a new descriptor and it works
        ObjectLinkIndividualDesc newRobotIndividual1 = new ObjectLinkIndividualDesc("Robot1", ontorefToRead);
        System.out.println("========At initialization");
        System.out.println(newRobotIndividual1);

        System.out.println("========With readExpAxioms");
        newRobotIndividual1.readExpressionAxioms();
        System.out.println(newRobotIndividual1);

        // Once the file is created, can we write then read then delete?

        // Writing
            // with the descriptor that created the file
        System.out.println("========Writing with the descriptor that created the file");
        newRobotIndividual1.addObject("isAtTheSameTimeIn","Room1"); // add to descriptor
        newRobotIndividual1.writeExpressionAxioms(); // sync descriptor with onto
        System.out.println(newRobotIndividual1);
        newRobotIndividual1.saveOntology(ontorefToRead.getFilePath());

            // with a new descriptor
        System.out.println("========Writing with a NEW descriptor");
        ObjectLinkIndividualDesc newRobotIndividual2 = new ObjectLinkIndividualDesc("Robot1", ontorefToRead);
        newRobotIndividual2.readExpressionAxioms();
        newRobotIndividual2.addObject("isAlsoAtTheSameTimeIn","Car"); // add to descriptor
        newRobotIndividual2.writeExpressionAxioms(); // sync descriptor with onto
        System.out.println(newRobotIndividual2);
        newRobotIndividual2.saveOntology(ontorefToRead.getFilePath());

//        ontorefToRead.synchronizeReasoner();

        // Reading
            // read with same old descriptor
        System.out.println("========Reading with the descriptor that created the file");
        newRobotIndividual1.readExpressionAxioms();
        System.out.println(newRobotIndividual1); // todo (Comment) only syncs specific properties
            // read with a new descriptor
        System.out.println("========Reading with a new descriptor");
        newRobotIndividual2.readExpressionAxioms();
        System.out.println(newRobotIndividual2); // todo (Comment) only syncs specific properties


        System.out.println("+++++EK AKHRI++++");
        //new
        ObjectLinkIndividualDesc akhri = new ObjectLinkIndividualDesc("Robot1", ontorefToRead);
        //read
        akhri.readExpressionAxioms();
        //print
        System.out.println(akhri);

        // Deleting
        ontorefToRead.synchronizeReasoner(); // update the latest state of the ontology
        ObjectLinkIndividualDesc robotDeleterDesc = new ObjectLinkIndividualDesc("Robot1", ontorefToRead);
        robotDeleterDesc.readExpressionAxioms();
        System.out.println(robotDeleterDesc);
        robotDeleterDesc.removeObject("isNowIn","Himalaya");
        robotDeleterDesc.removeObject("isIn", "Corridor1");
        robotDeleterDesc.removeObject("isAtTheSameTimeIn");
        robotDeleterDesc.writeExpressionAxioms();
        ontorefToRead.synchronizeReasoner();
        robotDeleterDesc.readExpressionAxioms();
        System.out.println(robotDeleterDesc);
        robotDeleterDesc.saveOntology(ontorefToRead.getFilePath());

        // TODO (Notes)
        /*
        We need ..OWLRefCreated.. to make an ontology which does not exist
        We need ..OWLRefFromFile.. to interact with an ontology which already exists
        From the ..OWLRefCreated.. file we can write/save in both ways (ontoref.save(), descriptor.save(path)) and CAN-READ in both ways (same descriptor, new descriptor).
        From the ..OWLRefFromFile.. we can write/save in one way (descriptor.save(path)) and CAN-READ in both ways (same descriptor, new descriptor).
        SynchronizeReasoner() to be in sync with the latest state of the ontology (else the descriptor reads the state of the ontology, when it was instantiated)


         */
    }

    private String getRobotPosition() {

        // ... consider that this method does some computation and returns the robot's position
        return "Corridor1";
    }

    @After
    public void afterTest() {

        System.out.println("Entered afterTest");

        //OWLReferences
        ontorefToRead = OWLReferencesInterface.OWLReferencesContainer.newOWLReferenceFromFileWithPellet(
                "owloopTestDeleteRead", // ontology reference name
                "src/test/resources/owloopTestOntologyDelete.owl", // the ontology file path
                "http://www.semanticweb.org/yusha_temporary/owloopTestOntologyDelete", // the ontology IRI path
                true
        );

//        localTest();

//        ontoRef.saveOntology();
    }

}
