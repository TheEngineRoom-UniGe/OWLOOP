package it.emarolab.owloop.descriptor.utility.owloopPaperTests;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorAxioms;
import it.emarolab.owloop.descriptor.construction.descriptorBase.ConceptDescriptorBase;
import it.emarolab.owloop.descriptor.utility.concept_compoundDescriptor.FullConceptDesc;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.List;

public class DefSubClassDesc
        extends ConceptDescriptorBase
        implements ConceptExpression.Define, ConceptExpression.Sub<FullConceptDesc>{

    private DescriptorAxioms.Restrictions restrictions = new DescriptorAxioms.Restrictions();
    private DescriptorAxioms.Concepts subConcept = new DescriptorAxioms.Concepts();

    // constructors for ConceptDescriptorBase
    public DefSubClassDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }

    // implementations for Semantic.descriptor
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

    // implementations for ConceptExpression.Define
    @Override
    public DescriptorAxioms.Restrictions getDefinitionConcept() {
        return restrictions;
    }

    // you cannot build Define (based on Restrictions)


    // implementations for ConceptExpression.Super
    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewSubConcept(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Concepts getSubConcept() {
        return subConcept;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorBase<?> which considers only the ground
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t= " + restrictions +
                "," + NL + "\t⊃ " + subConcept +
                NL + "}" + NL;
    }
}