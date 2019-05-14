package it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ObjectPropertyGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;

/**
 * This is an example of a 'compound' ObjectProperty Descriptor as it implements more than one {@link ObjectPropertyExpression}.
 * Axioms in this descriptor's internal state (i.e., OWLOOP representation) can be synchronized to/from an OWL ontology.
 * {@link FullObjectPropertyDescriptor} can synchronize all the axioms, that are based on the following ObjectPropertyExpressions:
 *
 * <ul>
 * <li><b>{@link ObjectPropertyExpression.Equivalent}</b>:   to describe that an ObjectProperty is equivalent to another ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Disjoint}</b>:     to describe that an ObjectProperty is disjoint to another ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Sub}</b>:          to describe that an ObjectProperty subsumes another ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Super}</b>:        to describe that an ObjectProperty super-sumes another ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Domain}</b>:       to describe the domain restrictions of an ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Range}</b>:        to describe the range restrictions of an ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Inverse}</b>:      to describe that an ObjectProperty has another inverse ObjectProperty.</li>
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
public class FullObjectPropertyDescriptor
        extends ObjectPropertyGround
        implements ObjectPropertyExpression.Disjoint<FullObjectPropertyDescriptor>,
        ObjectPropertyExpression.Equivalent<FullObjectPropertyDescriptor>,
        ObjectPropertyExpression.Inverse<FullObjectPropertyDescriptor>,
        ObjectPropertyExpression.Sub<FullObjectPropertyDescriptor>,
        ObjectPropertyExpression.Super<FullObjectPropertyDescriptor>,
        ObjectPropertyExpression.Domain,
        ObjectPropertyExpression.Range{

    private DescriptorEntitySet.ObjectProperties disjointProperties = new DescriptorEntitySet.ObjectProperties();
    private DescriptorEntitySet.ObjectProperties equivalentProperties = new DescriptorEntitySet.ObjectProperties();
    private DescriptorEntitySet.ObjectProperties inverseProperties = new DescriptorEntitySet.ObjectProperties();
    private DescriptorEntitySet.ObjectProperties subProperties = new DescriptorEntitySet.ObjectProperties();
    private DescriptorEntitySet.ObjectProperties superProperties = new DescriptorEntitySet.ObjectProperties();
    private DescriptorEntitySet.Restrictions domainRestriction = new DescriptorEntitySet.Restrictions();
    private DescriptorEntitySet.Restrictions rangeRestriction = new DescriptorEntitySet.Restrictions();

    // constructors for DataPropertyGround

    public FullObjectPropertyDescriptor(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public FullObjectPropertyDescriptor(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public FullObjectPropertyDescriptor(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public FullObjectPropertyDescriptor(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public FullObjectPropertyDescriptor(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public FullObjectPropertyDescriptor(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public FullObjectPropertyDescriptor(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public FullObjectPropertyDescriptor(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // Implementation of readExpressionAxioms()

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.readExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Equivalent.super.readExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Range.super.readExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Domain.super.readExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Sub.super.readExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Super.super.readExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Inverse.super.readExpressionAxioms());
        return r;
    }

    // Implementation of writeExpressionAxioms()

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.writeExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Equivalent.super.writeExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Range.super.writeExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Domain.super.writeExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Sub.super.writeExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Super.super.writeExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Inverse.super.writeExpressionAxioms());
        return r;
    }

    // implementations for: ObjectPropertyExpression.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDescriptor getNewDisjointObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDescriptor( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectProperties getDisjointObjectProperty() {
        return disjointProperties;
    }

    // implementations for: ObjectPropertyExpression.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDescriptor getNewEquivalentObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDescriptor( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectProperties getEquivalentObjectProperty() {
        return equivalentProperties;
    }

    // implementations for: ObjectPropertyExpression.Domain

    @Override
    public DescriptorEntitySet.Restrictions getDomainObjectProperty() {
        return domainRestriction;
    }

    // implementations for: ObjectPropertyExpression.Range

    @Override
    public DescriptorEntitySet.Restrictions getRangeObjectProperty() {
        return rangeRestriction;
    }

    // implementations for: ObjectPropertyExpression.Sub

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDescriptor getNewSubObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDescriptor( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectProperties getSubObjectProperty() {
        return subProperties;
    }

    // implementations for: ObjectPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDescriptor getNewSuperObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDescriptor( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectProperties getSuperObjectProperty() {
        return superProperties;
    }

    // implementations for: ObjectPropertyExpression.Inverse

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDescriptor getNewInverseObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDescriptor( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectProperties getInverseObjectProperty() {
        return inverseProperties;
    }

    // implementations for: standard object interface

    @Override
    public String toString() {
        return "FullObjectPropertyDescriptor{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointProperties +
                "," + NL + "\t≡ " + equivalentProperties +
                "," + NL + "\t→ " + domainRestriction +
                "," + NL + "\t← " + rangeRestriction +
                "," + NL + "\t⊃ " + subProperties +
                "," + NL + "\t⊂ " + superProperties +
                "," + NL + "\t↔ " + inverseProperties +
                NL + "}";
    }
}
