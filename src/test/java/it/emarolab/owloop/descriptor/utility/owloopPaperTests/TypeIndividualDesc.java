package it.emarolab.owloop.descriptor.utility.owloopPaperTests;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.MORAxioms;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.MORIndividual;
import it.emarolab.owloop.descriptor.construction.descriptorBase.MORIndividualBase;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

public class TypeIndividualDesc
        extends MORIndividualBase
        implements MORIndividual.Type<DefSubClassDesc> {

    private MORAxioms.Concepts individualTypes = new MORAxioms.Concepts();

    // constructors for MORIndividualBase
    public TypeIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }

    // implementations for Semantic.descriptor
    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = Type.super.readSemantic();
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = Type.super.writeSemantic();
        return r;
    }


    // implementations for MORIndividual.Type
    @Override //called during build...() you can change the returning type to any implementations of MORConcept
    public DefSubClassDesc getNewTypeIndividual(OWLClass instance, OWLReferences ontology) {
        return new DefSubClassDesc( instance, ontology);
    }

    @Override
    public MORAxioms.Concepts getTypeIndividual() {
        return individualTypes;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t∈ " + individualTypes +
                NL + "}" + NL;
    }
}