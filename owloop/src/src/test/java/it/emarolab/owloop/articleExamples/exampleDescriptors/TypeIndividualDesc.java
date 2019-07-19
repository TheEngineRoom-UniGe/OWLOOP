package it.emarolab.owloop.articleExamples.exampleDescriptors;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * A 'simple' Individual Descriptor which implements 1 {@link IndividualExpression} interfaces:
 * <ul>
 * <li><b>{@link IndividualExpression.Type}</b>:         to describe the Type/s (i.e., class/es) of an Individual.</li>
 * </ul>
 * <p>
 *     Doing build() with this Descriptor returns another descriptor of type {@link DefSubConceptDesc}.
 * </p>
 *
 * See {@link FullIndividualDesc} for an example of a 'compound' Individual Descriptor that implements all IndividualExpressions.
 */
public class TypeIndividualDesc
        extends IndividualGround
        implements IndividualExpression.Type<DefSubConceptDesc> {

    private DescriptorEntitySet.Concepts concepts = new DescriptorEntitySet.Concepts();

    /* Constructors from class: IndividualGround */

    public TypeIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }

    /* Overriding methods in class: IndividualGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = Type.super.readExpressionAxioms();
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = Type.super.writeExpressionAxioms();
        return r;
    }

    /* Overriding methods in classes: Individual and IndividualExpression */


    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public DefSubConceptDesc getNewIndividualType(OWLClass instance, OWLReferences ontology) {
        return new DefSubConceptDesc( instance, ontology);
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
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t∈ " + concepts + "\n" +
                "}" + "\n";
    }
}