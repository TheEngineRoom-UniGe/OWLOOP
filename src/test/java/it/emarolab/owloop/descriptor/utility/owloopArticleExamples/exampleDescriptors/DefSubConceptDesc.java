package it.emarolab.owloop.descriptor.utility.owloopArticleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ConceptGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.utility.conceptDescriptor.FullConceptDescriptor;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.List;

/**
 * This is an example of a 'compound' Concept Descriptor which implements 2 {@link ConceptExpression}s.
 * <ul>
 * <li><b>{@link ConceptExpression.Definition}</b>:  to describe the definition of a Class..</li>
 * <li><b>{@link ConceptExpression.Sub}</b>:         to describe that a Class subsumes another Class.</li>
 * </ul>
 * <p>
 *     Doing build() with this Descriptor returns another descriptor of type {@link FullConceptDescriptor}.
 * </p>
 * See {@link FullConceptDescriptor} for an example of a 'compound' Concept Descriptor that implements all ConceptExpressions.
 */
public class DefSubConceptDesc
        extends ConceptGround
        implements ConceptExpression.Definition,
        ConceptExpression.Sub<FullConceptDescriptor>{

    private DescriptorEntitySet.Restrictions restrictions = new DescriptorEntitySet.Restrictions();
    private DescriptorEntitySet.Concepts subConcept = new DescriptorEntitySet.Concepts();

    // constructors for ConceptGround
    public DefSubConceptDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }

    // implementations for Axiom.descriptor
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = Definition.super.readExpressionAxioms(); // call this before Sub or Super !!!
        r.addAll( Sub.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = Sub.super.writeExpressionAxioms();
        r.addAll( Definition.super.writeExpressionAxioms()); // call this before Sub or Super !!!
        return r;
    }

    // implementations for ConceptExpression.Definition
    @Override
    public DescriptorEntitySet.Restrictions getDefinitionConcept() {
        return restrictions;
    }

    // you cannot build Definition (based on Restrictions)


    // implementations for ConceptExpression.Super
    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDescriptor getNewSubConcept(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDescriptor( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getSubConcept() {
        return subConcept;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorGround<?> which considers only the ground
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t= " + restrictions +
                "," + NL + "\t⊃ " + subConcept +
                NL + "}" + NL;
    }
}