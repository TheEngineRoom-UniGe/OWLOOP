package it.emarolab.owloop.descriptor.utility.conceptDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ConceptGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.LinkIndividualDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * This is an example of a 'compound' Concept Descriptor that implements 4 ClassExpressions (aka {@link ConceptExpression}):
 * <ul>
 * <li><b>{@link ConceptExpression.Equivalent}</b>:  to describe that a Class is equivalent to another Class.</li>
 * <li><b>{@link ConceptExpression.Disjoint}</b>:    to describe that a Class is disjoint to another Class.</li>
 * <li><b>{@link ConceptExpression.Instance}</b>:    to describe an Individual of a Class.</li>
 * <li><b>{@link ConceptExpression.Definition}</b>:  to describe the definition of a Class..</li>
 * </ul>
 *
 *  See {@link FullConceptDesc} for an example of a 'compound' Concept Descriptor that implements all ClassExpressions (aka {@link ConceptExpression}).
 */
public class DefinitionConceptDesc
        extends ConceptGround
        implements ConceptExpression.Definition,
        ConceptExpression.Disjoint<DefinitionConceptDesc>,
        ConceptExpression.Equivalent<DefinitionConceptDesc>,
        ConceptExpression.Instance<LinkIndividualDesc> {

    private DescriptorEntitySet.Concepts disjointConcepts = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Concepts equivalentConcepts = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Restrictions restrictions = new DescriptorEntitySet.Restrictions();
    private DescriptorEntitySet.Individuals individuals = new DescriptorEntitySet.Individuals();

    // Constructors from class: ConceptGround
    
    public DefinitionConceptDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DefinitionConceptDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DefinitionConceptDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public DefinitionConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DefinitionConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DefinitionConceptDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DefinitionConceptDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DefinitionConceptDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // Overriding methods in class: ConceptGround


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ConceptExpression.Disjoint.super.readExpressionAxioms();
        r.addAll( ConceptExpression.Equivalent.super.readExpressionAxioms());
        r.addAll( Definition.super.readExpressionAxioms());
        r.addAll( Instance.super.readExpressionAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ConceptExpression.Disjoint.super.writeExpressionAxioms();
        r.addAll( ConceptExpression.Equivalent.super.writeExpressionAxioms());
        r.addAll( Definition.super.writeExpressionAxioms());
        r.addAll( Instance.super.writeExpressionAxioms());
        return r;
    }

    // Overriding methods in classes: Concept and ConceptExpression


    @Override
    public DescriptorEntitySet.Restrictions getDefinitionConcepts() {
        return restrictions;
    }

    @Override
    public DefinitionConceptDesc getDisjointConceptDescriptor(OWLClass instance, OWLReferences ontology) {
        return new DefinitionConceptDesc( instance, ontology);
    }
    @Override
    public DescriptorEntitySet.Concepts getDisjointConcepts() {
        return disjointConcepts;
    }

    @Override
    public DefinitionConceptDesc getEquivalentConceptDescriptor(OWLClass instance, OWLReferences ontology) {
        return new DefinitionConceptDesc( instance, ontology);
    }
    @Override
    public DescriptorEntitySet.Concepts getEquivalentConcepts() {
        return equivalentConcepts;
    }

    @Override
    public LinkIndividualDesc getIndividualDescriptor(OWLNamedIndividual instance, OWLReferences ontology) {
        return new LinkIndividualDesc( instance, ontology);
    }
    @Override
    public DescriptorEntitySet.Individuals getIndividualInstances() {
        return individuals;
    }

    // Overriding method in class: Object


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointConcepts +
                "," + NL + "\t≡ " + equivalentConcepts +
                "," + NL + "\t⇐ " + individuals +
                "," + NL + "\t= " + restrictions +
                NL + "}";
    }
}

// todo: (i) rename entitySet objects properly (ii) rename the methods related to those variables properly (iii) modification in toString() (iv) fix spaces and comments.