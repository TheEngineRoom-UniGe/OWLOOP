package it.emarolab.owloop.aMORDescriptor.utility.owloopPaperTests;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORConcept;
import it.emarolab.owloop.aMORDescriptor.utility.MORConceptBase;
import it.emarolab.owloop.aMORDescriptor.utility.concept.MORFullConcept;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.List;

public class DefSubClassDesc
        extends MORConceptBase
        implements MORConcept.Define, MORConcept.Sub<MORFullConcept>{

    private MORAxioms.Restrictions restrictions = new MORAxioms.Restrictions();
    private MORAxioms.Concepts subConcept = new MORAxioms.Concepts();

    // constructors for MORConceptBase
    public DefSubClassDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }

    // implementations for Semantic.Descriptor
    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = Define.super.readSemantic(); // call this before Sub or Super !!!
        r.addAll( Sub.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = Sub.super.writeSemantic();
        r.addAll( Define.super.writeSemantic()); // call this before Sub or Super !!!
        return r;
    }

    // implementations for MORConcept.Define
    @Override
    public MORAxioms.Restrictions getDefinitionConcept() {
        return restrictions;
    }

    // you cannot build Define (based on Restrictions)


    // implementations for MORConcept.Super
    @Override // called during build...() you can change the returning type to any implementations of MORConcept
    public MORFullConcept getNewSubConcept(OWLClass instance, OWLReferences ontology) {
        return new MORFullConcept( instance, ontology);
    }

    @Override
    public MORAxioms.Concepts getSubConcept() {
        return subConcept;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t= " + restrictions +
                "," + NL + "\t⊃ " + subConcept +
                NL + "}" + NL;
    }
}