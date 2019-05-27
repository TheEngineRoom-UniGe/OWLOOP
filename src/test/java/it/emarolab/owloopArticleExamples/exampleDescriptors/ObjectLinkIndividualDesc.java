package it.emarolab.owloopArticleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.FullObjectPropertyDesc;

import java.util.List;

/**
 * A 'simple' Individual Descriptor which implements 1 {@link IndividualExpression}s.
 * <ul>
 * <li><b>{@link IndividualExpression.ObjectLink}</b>:   to describe an ObjectProperty and Individuals related via that ObjectProperty, for an Individual.</li>
 * </ul>
 * <p>
 *     Doing build() with this Descriptor returns another descriptor of type {@link FullObjectPropertyDesc}.
 * </p>
 * See {@link FullIndividualDesc} for an example of a 'compound' Individual Descriptor that implements all IndividualExpressions.
 */
public class ObjectLinkIndividualDesc
        extends IndividualGround
        implements IndividualExpression.ObjectLink<FullObjectPropertyDesc> {

    private DescriptorEntitySet.ObjectLinksSet objectLinks = new DescriptorEntitySet.ObjectLinksSet();

    // constructors for IndividualGround
    public ObjectLinkIndividualDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }

    // implementations for Axiom.descriptor
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ObjectLink.super.readExpressionAxioms();
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ObjectLink.super.writeExpressionAxioms();
        return r;
    }


    // implementations for IndividualExpression.ObjectLink
    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDesc getNewObjectIndividual(DescriptorEntitySet.ObjectLinks instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance.getExpression(), ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectLinksSet getObjectExpressionAxioms() {
        return objectLinks;
    }

    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorGround<?> which considers only the ground
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊨ " + objectLinks +
                NL + "}" + NL;
    }
}