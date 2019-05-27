package it.emarolab.owloopArticleExamples.exampleDescriptors;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * A 'simple' Individual Descriptor which implements 1 {@link IndividualExpression}s.
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

    private DescriptorEntitySet.Concepts individualTypes = new DescriptorEntitySet.Concepts();

    // constructors for IndividualGround
    public TypeIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }

    // implementations for Axiom.descriptor
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = Type.super.readExpressionAxioms();
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = Type.super.writeExpressionAxioms();
        return r;
    }


    // implementations for IndividualExpression.Type
    @Override //called during build...() you can change the returning type to any implementations of ConceptExpression
    public DefSubConceptDesc getNewTypeIndividual(OWLClass instance, OWLReferences ontology) {
        return new DefSubConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getTypeIndividual() {
        return individualTypes;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorGround<?> which considers only the ground
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t∈ " + individualTypes +
                NL + "}" + NL;
    }
}