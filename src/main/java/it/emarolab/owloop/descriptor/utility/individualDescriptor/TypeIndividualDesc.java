package it.emarolab.owloop.descriptor.utility.individualDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.utility.conceptDescriptor.FullConceptDesc;
import it.emarolab.owloop.descriptor.utility.conceptDescriptor.HierarchicalConceptDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * This is an example of a 'simple' Individual Descriptor which implements 1 {@link IndividualExpression} interfaces:
 * <ul>
 * <li><b>{@link IndividualExpression.Type}</b>: to describe the Type/s (i.e., class/es) of an Individual.</li>
 * </ul>
 * See {@link FullIndividualDesc} for an example of a 'compound' Individual Descriptor that implements all IndividualExpressions.
 */
public class TypeIndividualDesc
        extends IndividualGround
        implements IndividualExpression.Type<HierarchicalConceptDesc>{

    private DescriptorEntitySet.Concepts concepts = new DescriptorEntitySet.Concepts();

    /* Constructors from class: IndividualGround */

    public TypeIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public TypeIndividualDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public TypeIndividualDesc(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public TypeIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public TypeIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public TypeIndividualDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public TypeIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public TypeIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    /* Overriding methods in class: IndividualGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        return IndividualExpression.Type.super.readExpressionAxioms();
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        return IndividualExpression.Type.super.writeExpressionAxioms();
    }

    /* Overriding methods in classes: Individual and IndividualExpression */


    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public HierarchicalConceptDesc getNewIndividualType(OWLClass instance, OWLReferences ontology) {
        return new HierarchicalConceptDesc( instance, ontology);
    }
    // It returns concepts from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Concepts getIndividualTypes() {
        return concepts;
    }

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t ∈ " + concepts +
                NL + "}";
    }
}
