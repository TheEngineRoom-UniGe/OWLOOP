package it.emarolab.owloop.articleExamples.drugOrderExample;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.owloop.descriptor.utility.classDescriptor.FullClassDesc;
import it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor.FullDataPropertyDesc;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.FullObjectPropertyDesc;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * This example is taken from Owlready paper: https://www.archives-ouvertes.fr/hal-01592746/document
 * To compare difference in the number of lines of code written with OWL-API and OWLOOP-API
 */
public class drugOrder {

    private OWLReferences ontoRef;

    @Before
    public void beforeTest() {

        // Disables printing of amor logs
        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( false);

        // To create a new ontologyReference. The ontology file need not be pre-existing.
        ontoRef = OWLReferencesInterface.OWLReferencesContainer.newOWLReferenceFromFileWithPellet(
                "drugOntology", // ontology reference name
                "src/test/resources/drug.owl", // the ontology file path
                "http://www.semanticweb.org/yusha/ontologies/2019/drug", // the ontology IRI path
                true // if (true) you must synchronize the reasoner manually. Else, it synchronizes itself.
        );
    }

    @Test
    public void orderDrugs() {

        final int[] total_cost = {0};
        FullIndividualDesc order_indivDesc = new FullIndividualDesc("order", ontoRef);
        order_indivDesc.readExpressionAxioms();
        Iterable<OWLNamedIndividual> drugs_indivSet = order_indivDesc.getIndividualsFromObjectProperty("hasDrug");
        drugs_indivSet.forEach(drug_indiv -> {
            FullIndividualDesc drug_indivDesc = new FullIndividualDesc(drug_indiv, ontoRef);
            drug_indivDesc.readExpressionAxioms();
            OWLLiteral value = drug_indivDesc.getLiteralFromDataProperty("hasPrice");
            total_cost[0] += value.parseInteger();
        });
        System.out.println(total_cost[0]);

        FullClassDesc ORDER = new FullClassDesc("ORDER", ontoRef);
        FullIndividualDesc order = new FullIndividualDesc("order", ontoRef);
        FullObjectPropertyDesc hasDrug = new FullObjectPropertyDesc("hasDrug", ontoRef);
        FullDataPropertyDesc hasPrice = new FullDataPropertyDesc("hasPrice", ontoRef);
    }
}
