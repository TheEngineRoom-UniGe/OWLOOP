package it.emarolab.owloop.descriptor.utility.conceptDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ConceptDescriptorGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.LinkIndividualDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * This is an example of a 'compound' Concept Descriptor as it implements more than one ClassExpression (aka {@link ConceptExpression}).
 * Axioms in this descriptor's internal state (OWLOOP representation) gets synchronized wrt the axioms in the OWL representation.
 * {@link FullConceptDesc} can synchronize all the axioms, that are based on the following ClassExpressions:
 *
 * <ul>
 * <li><b>{@link ConceptExpression.Equivalent}</b>:  to describe that a Class is equivalent to another Class.</li>
 * <li><b>{@link ConceptExpression.Disjoint}</b>:    to describe that a Class is disjoint to another Class.</li>
 * <li><b>{@link ConceptExpression.Sub}</b>:         to describe that a Class subsumes another Class.</li>
 * <li><b>{@link ConceptExpression.Super}</b>:       to describe that a Class is a super-class of another Class.</li>
 * <li><b>{@link ConceptExpression.Instance}</b>:    to describe an Individual of a Class.</li>
 * <li><b>{@link ConceptExpression.Definition}</b>:  to describe the definition of a Class..</li>
 * </ul>
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
 * </small></div>
 */
public class FullConceptDesc
        extends ConceptDescriptorGround
        implements ConceptExpression.Definition,
        ConceptExpression.Disjoint<FullConceptDesc>,
        ConceptExpression.Equivalent<FullConceptDesc>,
        ConceptExpression.Sub<FullConceptDesc>,
        ConceptExpression.Super<FullConceptDesc>,
        ConceptExpression.Instance<LinkIndividualDesc> {

    private DescriptorEntitySet.Restrictions restrictions = new DescriptorEntitySet.Restrictions();
    private DescriptorEntitySet.Concepts disjointConcept = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Concepts equivalentConcept = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Concepts subConcept = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Concepts superConcept = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Individuals classifiedIndividual = new DescriptorEntitySet.Individuals();

    // Constructors from the abstract class: ConceptDescriptorGround

    public FullConceptDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public FullConceptDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public FullConceptDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public FullConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public FullConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public FullConceptDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public FullConceptDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public FullConceptDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // Implementation of readExpressionAxioms()

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ConceptExpression.Disjoint.super.readExpressionAxioms();
        r.addAll( ConceptExpression.Equivalent.super.readExpressionAxioms());
        r.addAll( Definition.super.readExpressionAxioms()); // call this before Sub or Super !!!
        r.addAll( ConceptExpression.Sub.super.readExpressionAxioms());
        r.addAll( ConceptExpression.Super.super.readExpressionAxioms());
        r.addAll( Instance.super.readExpressionAxioms());
        return r;
    }

    // Implementation of writeExpressionAxioms()

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ConceptExpression.Disjoint.super.writeExpressionAxioms();
        r.addAll( ConceptExpression.Equivalent.super.writeExpressionAxioms());
        r.addAll( ConceptExpression.Sub.super.writeExpressionAxioms());
        r.addAll( ConceptExpression.Super.super.writeExpressionAxioms());
        r.addAll( Definition.super.writeExpressionAxioms()); // call this before Sub or Super !!!
        r.addAll( Instance.super.writeExpressionAxioms());
        return r;
    }

    // Implementations for: ConceptExpression.Disjoint

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewDisjointConcept(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getDisjointConcept() {
        return disjointConcept;
    }

    // Implementations for: ConceptExpression.Equivalent

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewEquivalentConcept(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getEquivalentConcept() {
        return equivalentConcept;
    }

    // Implementations for: ConceptExpression.Definition

    @Override
    public DescriptorEntitySet.Restrictions getDefinitionConcept() {
        return restrictions;
    }

    // Implementations for: ConceptExpression.Super

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewSubConcept(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getSubConcept() {
        return subConcept;
    }

    // Implementations for: ConceptExpression.Super

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewSuperConcept(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getSuperConcept() {
        return superConcept;
    }

    // Implementations for: ConceptExpression.Classifier

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public LinkIndividualDesc getNewIndividualInstance(OWLNamedIndividual instance, OWLReferences ontology) {
        return new LinkIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Individuals getIndividualInstance() {
        return classifiedIndividual;
    }

    // Implementations for: standard object interface

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointConcept +
                "," + NL + "\t≡ " + equivalentConcept +
                "," + NL + "\t⇐ " + classifiedIndividual +
                "," + NL + "\t= " + restrictions +
                "," + NL + "\t⊃ " + subConcept +
                "," + NL + "\t⊂ " + superConcept +
                NL + "}";
    }
}
