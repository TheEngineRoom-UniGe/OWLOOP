package it.emarolab.owloop.articleExamples.example2;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.articleExamples.exampleDescriptors.DefSubClassDesc;
import it.emarolab.owloop.articleExamples.exampleDescriptors.TypeIndividualDesc;
import it.emarolab.owloop.core.Axiom.Descriptor.*;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Restrictions;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.LinkIndividualDesc;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import java.util.Set;

/**
 * An example to illustrate how to 'build()' method of a descriptor.
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
public class useDescriptorBuildMethod {

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

        LinkIndividualDesc robotIndividual_Desc = new LinkIndividualDesc("Robot1", ontoRef);
        // To read knowledge (only) specific to the property "isIn"
        robotIndividual_Desc.addObject("isIn", true);
        robotIndividual_Desc.readAxioms();
        // Print
        System.out.println(robotIndividual_Desc);
        // Get knowledge from internal state of the Descriptor
        OWLNamedIndividual robotLocation = robotIndividual_Desc.getIndividualFromObjectProperty("isIn");

        // robotLocation = "Corridor1"
        TypeIndividualDesc locationIndividual_Desc = new TypeIndividualDesc(robotLocation, ontoRef);
        locationIndividual_Desc.readAxioms();
        // Print
        System.out.println(locationIndividual_Desc);

        // Descriptor.buildTypes() gets the Type/s (i.e., Class/s) of the Individual "Corridor1" as a set of grounded Descriptors
        Set<DefSubClassDesc> setOfClass_Descs = locationIndividual_Desc.buildTypes();

        for(DefSubClassDesc locationClass_Desc : setOfClass_Descs) {

            // Descriptor.buildSubClasses() gets the subClass/s of a Class as a set of grounded Descriptors
            Set<DefSubClassDesc> setOfSubClass_Descs = locationClass_Desc.buildSubClasses();
            // Find the root Class (It has max. 1 subClass, i.e., owl:Nothing)
            if(setOfSubClass_Descs.size() == 1) {

                System.out.println("'" + locationClass_Desc.getGroundInstanceName() + "' is the root Class among: ");
                for(DefSubClassDesc ClassType : setOfClass_Descs) {

                    System.out.println("\t\t\t'" + ClassType.getGroundInstanceName() + "'");
                }
                System.out.print("\nTherefore '" + locationIndividual_Desc.getGroundInstanceName() + "'" + " is of Type " + "'" + locationClass_Desc.getGroundInstanceName() + "' \n");
                Restrictions restrictions = locationClass_Desc.getEquivalentRestrictions();
                for(SemanticRestriction rest : restrictions) {

                    if(rest instanceof SemanticRestriction.ClassRestrictedOnExactObject) {

                        System.out.println("\n" + "'" + locationClass_Desc.getGroundInstanceName() + "'" + " is defined with Exact Cardinality EquivalentRestriction " + "'" + rest + "'");
                    }
                    else if(rest instanceof SemanticRestriction.ClassRestrictedOnMinObject) {

                        System.out.println("\n" + "'" + locationClass_Desc.getGroundInstanceName() + "'" + " is defined with Min Cardinality EquivalentRestriction " + "'" + rest + "'");
                    }
                }
            }
        }
    }
}
