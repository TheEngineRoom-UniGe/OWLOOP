package it.emarolab.owloop.articleExamples.example3;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.core.Axiom.Descriptor.*;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.LinkIndividualDesc;
import org.junit.Before;
import org.junit.Test;

/**
 * An example to illustrate how to remove axioms from an ontology.
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
public class RemoveAxioms {

    public static void main(String[] args) {
        OWLReferences ontoRef;

        // Disables printing of amor logs
        OntologyReference.activateAMORlogging(false);

        // Create reference to an existing ontology file associated with the Pellet reasoner
        ontoRef = OntologyReference.newOWLReferenceFromFileWithPellet(
                "robotAtHomeOnto", //ontology reference name.
                "src/test/resources/robotAtHomeOntology.owl", //file path.
                "http://www.semanticweb.org/emaroLab/robotAtHomeOntology", //IRI.
                true //synchronize the reasoner manually.
        );

        LinkIndividualDesc robot_Desc1 = new LinkIndividualDesc( "Robot1", ontoRef);
        robot_Desc1.readAxioms();
        System.out.println(robot_Desc1); // Print
        // Remove the association between the ground-individual and entity-set-individuals, associated via object-property "isIn". (in the descriptor's internal state)
        robot_Desc1.removeObject("isIn");
        // Synchronize knowledge between descriptor's internal state and the ontology
        robot_Desc1.writeAxiomsReasonReadAxioms();
        System.out.println(robot_Desc1); // Print
        // Save the in-memory ontology to a .owl file in a specific file-path
        ontoRef.saveOntology(ontoRef.getFilePath());
    }
}