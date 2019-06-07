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
 * This is an example of a 'compound' Concept Descriptor as it implements more than one ClassExpression (aka {@link ConceptExpression}).
 * Axioms in this descriptor's internal state (i.e., OWLOOP representation) can be synchronized to/from an OWL ontology.
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
 * <p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
 * </small></div>
 */
public class FullConceptDesc
        extends ConceptGround
        implements ConceptExpression.Definition,
        ConceptExpression.Disjoint<FullConceptDesc>,
        ConceptExpression.Equivalent<FullConceptDesc>,
        ConceptExpression.Sub<FullConceptDesc>,
        ConceptExpression.Super<FullConceptDesc>,
        ConceptExpression.Instance<LinkIndividualDesc> {

    private DescriptorEntitySet.Restrictions conceptRestrictions = new DescriptorEntitySet.Restrictions();
    private DescriptorEntitySet.Concepts disjointConcepts = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Concepts equivalentConcepts = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Concepts subConcepts = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Concepts superConcepts = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Individuals individuals = new DescriptorEntitySet.Individuals();

    /* Constructors from class: ConceptGround */

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

    /* Overriding methods in class: ConceptGround */


    // To read axioms from an ontology
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
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ConceptExpression.Disjoint.super.writeExpressionAxioms();
        r.addAll( ConceptExpression.Equivalent.super.writeExpressionAxioms());
        r.addAll( Definition.super.writeExpressionAxioms()); // call this before Sub or Super !!!
        r.addAll( ConceptExpression.Sub.super.writeExpressionAxioms());
        r.addAll( ConceptExpression.Super.super.writeExpressionAxioms());
        r.addAll( Instance.super.writeExpressionAxioms());
        return r;
    }

    /* Overriding methods in classes: Concept and ConceptExpression */


    // It returns conceptRestrictions from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Restrictions getDefinitionConcepts() {
        return conceptRestrictions;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullConceptDesc getDisjointConceptDescriptor(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }
    // It returns disjointConcepts from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Concepts getDisjointConcepts() {
        return disjointConcepts;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullConceptDesc getEquivalentConceptDescriptor(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }
    // It returns equivalentConcepts from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Concepts getEquivalentConcepts() {
        return equivalentConcepts;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullConceptDesc getSubConceptDescriptor(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }
    // It returns subConcepts from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Concepts getSubConcepts() {
        return subConcepts;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullConceptDesc getSuperConceptDescriptor(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }
    // It returns superConcepts from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Concepts getSuperConcepts() {
        return superConcepts;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public LinkIndividualDesc getIndividualDescriptor(OWLNamedIndividual instance, OWLReferences ontology) {
        return new LinkIndividualDesc( instance, ontology);
    }
    // It returns Individuals from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Individuals getIndividualInstances() {
        return individuals;
    }

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t≠ " + disjointConcepts + "\n" +
                "\t\t≡ " + equivalentConcepts + "\n" +
                "\t\t⇐ " + individuals + "\n" +
                "\t\t≐ " + conceptRestrictions + "\n" +
                "\t\t⊃ " + subConcepts + "\n" +
                "\t\t⊂ " + superConcepts + "\n" +
                "}" + "\n";
    }
}
