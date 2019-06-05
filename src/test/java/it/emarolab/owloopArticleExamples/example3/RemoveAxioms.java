package it.emarolab.owloopArticleExamples.example3;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import it.emarolab.owloopArticleExamples.exampleDescriptors.ObjectLinkIndividualDesc;
import org.junit.Before;
import org.junit.Test;

public class RemoveAxioms {

    private OWLReferences ontoRef;

    @Before
    public void beforeTest() {

        // Disables printing of amor logs
        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( false);

        ontoRef = OWLReferencesInterface.OWLReferencesContainer.newOWLReferenceFromFileWithPellet(
                "robotAtHomeOnto", // ontology reference name
                "src/test/resources/robotAtHomeOntology.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/robotAtHomeOntology", // the ontology IRI path
                true // if (true) you must synchronize the reasoner manually. Else, it synchronizes itself.
        );
    }

    @Test
    public void removeStuff() {

        /* THE CODE TO DEBUG WITH LUCA */

        // robot individual
        FullIndividualDesc robot_Desc = new FullIndividualDesc( "Robot1", ontoRef);
        robot_Desc.readExpressionAxioms();
        System.out.println(robot_Desc);

        // corridor individual
        FullIndividualDesc corridor_Desc = new FullIndividualDesc( "Corridor1", ontoRef);
        corridor_Desc.readExpressionAxioms();
        System.out.println(corridor_Desc);

        // room individual
        FullIndividualDesc room1_Desc = new FullIndividualDesc("Room1", ontoRef);
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

/* THE CODE IN GITHUB WIKI */

//        // robot individual
//        ObjectLinkIndividualDesc robot_Desc1 = new ObjectLinkIndividualDesc( "Robot1", ontoRef);
//
//        // synchronize axioms from the Ontology to the internal state of the Descriptor
//        robot_Desc1.readExpressionAxioms();
//
//        // print the Descriptor
//        System.out.println(robot_Desc1);
//
//        // remove Object Property associated to the Individual Descriptor (i.e., from Descriptor's internal state)
//        robot_Desc1.removeObject("isIn");
//
//        // synchronize axioms from the internal state of the Descriptor and Ontology
//        robot_Desc1.writeExpressionAxioms();
//
//        // synchronize reasoner of the ontology, so that its axioms are updated with inferences based on latest assertions
//        ontoRef.synchronizeReasoner();
//
//        // save the current state of the ontology
//        robot_Desc1.saveOntology(ontoRef.getFilePath());
//
//        // A new Descriptor associated to the same individual as before
//        ObjectLinkIndividualDesc robot_Desc2 = new ObjectLinkIndividualDesc( "Robot1", ontoRef);
//
//        // synchronize axioms from the Ontology to the internal state of the Descriptor
//        robot_Desc2.readExpressionAxioms();
//
//        // print the Descriptor
//        System.out.println(robot_Desc2);