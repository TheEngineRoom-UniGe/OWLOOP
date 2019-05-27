package it.emarolab.owloop.descriptor.utility.conceptDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ConceptGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.List;


/**
 * This is an example of a 'compound' Concept Descriptor that implements 2 ClassExpressions (aka {@link ConceptExpression}):
 * <ul>
 * <li><b>{@link ConceptExpression.Sub}</b>:         to describe that a Class subsumes another Class.</li>
 * <li><b>{@link ConceptExpression.Super}</b>:       to describe that a Class is a super-class of another Class.</li>
 * </ul>
 *
 * See {@link FullConceptDesc} for an example of a 'compound' Concept Descriptor that implements all ClassExpressions (aka {@link ConceptExpression}).
 */
public class HierarchicalConceptDesc
        extends ConceptGround
        implements ConceptExpression.Sub<HierarchicalConceptDesc>,
        ConceptExpression.Super<HierarchicalConceptDesc>{

    private DescriptorEntitySet.Concepts subConcept = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Concepts superConcept = new DescriptorEntitySet.Concepts();

    // constructors for ConceptGround

    public HierarchicalConceptDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public HierarchicalConceptDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public HierarchicalConceptDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public HierarchicalConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public HierarchicalConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public HierarchicalConceptDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public HierarchicalConceptDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public HierarchicalConceptDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ConceptExpression.Sub.super.readExpressionAxioms();
        r.addAll( ConceptExpression.Super.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ConceptExpression.Sub.super.writeExpressionAxioms();
        r.addAll( ConceptExpression.Super.super.writeExpressionAxioms());
        return r;
    }



    // implementations for ConceptExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of ConceptExpression
    public HierarchicalConceptDesc getSubConceptDescriptor(OWLClass instance, OWLReferences ontology) {
        return new HierarchicalConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getSubConcepts() {
        return subConcept;
    }



    // implementations for ConceptExpression.Sub

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public HierarchicalConceptDesc getSuperConceptDescriptor(OWLClass instance, OWLReferences ontology) {
        return new HierarchicalConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getSuperConcepts() {
        return superConcept;
    }


    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊃ " + subConcept +
                "," + NL + "\t⊂ " + superConcept +
                NL + "}";
    }
}
