package it.emarolab.owloop.aMORDescriptor.utility.owloopPaperTests;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORIndividual;
import it.emarolab.owloop.aMORDescriptor.utility.MORIndividualBase;
import it.emarolab.owloop.aMORDescriptor.utility.concept.MORFullConcept;
import it.emarolab.owloop.aMORDescriptor.utility.dataProperty.MORFullDataProperty;
import it.emarolab.owloop.aMORDescriptor.utility.individual.MORDefinitionIndividual;
import it.emarolab.owloop.aMORDescriptor.utility.individual.MORLinkIndividual;
import it.emarolab.owloop.aMORDescriptor.utility.individual.MORTypeIndividual;
import it.emarolab.owloop.aMORDescriptor.utility.objectProperty.MORFullObjectProperty;
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

    // implementations for Semantic.Descriptor
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