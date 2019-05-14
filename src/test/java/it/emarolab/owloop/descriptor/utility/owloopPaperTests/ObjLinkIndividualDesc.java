package it.emarolab.owloop.descriptor.utility.owloopPaperTests;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.FullObjectPropertyDescriptor;

import java.util.List;

public class ObjLinkIndividualDesc
        extends IndividualGround
        implements IndividualExpression.ObjectLink<FullObjectPropertyDescriptor> {

    private DescriptorEntitySet.ObjectLinksSet objectLinks = new DescriptorEntitySet.ObjectLinksSet();

    // constructors for IndividualGround
    public ObjLinkIndividualDesc(String instanceName, OWLReferences onto) {
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
    public FullObjectPropertyDescriptor getNewObjectIndividual(DescriptorEntitySet.ObjectLinks instance, OWLReferences ontology) {
        return new FullObjectPropertyDescriptor( instance.getExpression(), ontology);
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