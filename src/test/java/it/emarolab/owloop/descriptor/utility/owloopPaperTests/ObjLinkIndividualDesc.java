package it.emarolab.owloop.descriptor.utility.owloopPaperTests;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.MORAxioms;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.MORIndividual;
import it.emarolab.owloop.descriptor.construction.descriptorBase.MORIndividualBase;
import it.emarolab.owloop.descriptor.utility.objectProperty.MORFullObjectProperty;

import java.util.List;

public class ObjLinkIndividualDesc
        extends MORIndividualBase
        implements MORIndividual.ObjectLink<MORFullObjectProperty> {

    private MORAxioms.ObjectSemantics objectLinks = new MORAxioms.ObjectSemantics();

    // constructors for MORIndividualBase
    public ObjLinkIndividualDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }

    // implementations for Semantic.descriptor
    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = ObjectLink.super.readSemantic();
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = ObjectLink.super.writeSemantic();
        return r;
    }


    // implementations for MORIndividual.ObjectLink
    @Override //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORFullObjectProperty getNewObjectIndividual(MORAxioms.ObjectSemantic instance, OWLReferences ontology) {
        return new MORFullObjectProperty( instance.getSemantic(), ontology);
    }

    @Override
    public MORAxioms.ObjectSemantics getObjectSemantics() {
        return objectLinks;
    }



    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊨ " + objectLinks +
                NL + "}" + NL;
    }
}