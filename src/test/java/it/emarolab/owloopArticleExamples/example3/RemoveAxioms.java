package it.emarolab.owloopArticleExamples.example3;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.owloop.core.Individual;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDescriptor;
import it.emarolab.owloopArticleExamples.exampleDescriptors.ObjectLinkIndividualDesc;
import org.junit.Before;
import org.junit.Test;

public class RemoveAxioms {

    private OWLReferences ontoRef;

    @Before
    public void beforeTest() {

        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( false);

        ontoRef = OWLReferencesInterface.OWLReferencesContainer.newOWLReferenceFromFileWithPellet(
                "robotAtHomeOnto", // ontology reference name
                "src/test/resources/robotAtHomeOntology.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/robotAtHomeOntology", // the ontology IRI path
                true
        );
    }

    @Test
    public void removeStuff() {

        // robot individual
        FullIndividualDescriptor robot_Desc = new FullIndividualDescriptor( "Robot1", ontoRef);
        robot_Desc.readExpressionAxioms();
        System.out.println(robot_Desc);

        ObjectLinkIndividualDesc test = new ObjectLinkIndividualDesc("dsfads", ontoRef);



        // corridor individual
        FullIndividualDescriptor corridor_Desc = new FullIndividualDescriptor( "Corridor1", ontoRef);
        corridor_Desc.readExpressionAxioms();
        System.out.println(corridor_Desc);

        // room individual
        FullIndividualDescriptor room1_Desc = new FullIndividualDescriptor("Room1", ontoRef);
        room1_Desc.readExpressionAxioms();
        System.out.println(room1_Desc);

        // Step 1 - add something to the onto by hand (add using Protege)

        // Step 2 - add something to the onto by code
//        robot_Desc.addObject("hasAtr2","Stupid");
//        robot_Desc.addObject("isIn", "Corridor1");
//
//        robot_Desc.writeExpressionAxioms();
//        ontoRef.synchronizeReasoner();
//        robot_Desc.saveOntology(ontoRef.getFilePath());

        // step 3 - try removing stuff added from the code
//        robot_Desc.removeObject("hasAtr");
//        robot_Desc.removeObject("isIn");
//        robot_Desc.removeObject("hasAtr2");

        robot_Desc.writeExpressionAxioms();
        ontoRef.synchronizeReasoner();
        robot_Desc.saveOntology(ontoRef.getFilePath());
    }

}