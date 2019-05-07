package it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorBase.DataPropertyDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorBaseInterface.DescriptorEntitySet;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;
import java.util.Set;

/**
 * This is an example of a 'compound' DataProperty Descriptor as it implements more than one {@link DataPropertyExpression}.
 * Axioms in this descriptor's internal state (OWLOOP representation) gets synchronized wrt the axioms in the OWL representation.
 * {@link FullDataPropertyDesc} can synchronize all the axioms, that are based on the following DataPropertyExpressions:
 *
 * <ul>
 * <li><b>{@link DataPropertyExpression.Equivalent}</b>:   to describe that a DataProperty is equivalent to another DataProperty.</li>
 * <li><b>{@link DataPropertyExpression.Disjoint}</b>:     to describe that a DataProperty is disjoint to another DataProperty.</li>
 * <li><b>{@link DataPropertyExpression.Sub}</b>:          to describe that a DataProperty is subsumes another DataProperty.</li>
 * <li><b>{@link DataPropertyExpression.Super}</b>:        to describe that a DataProperty is super-sumes another DataProperty.</li>
 * <li><b>{@link DataPropertyExpression.Domain}</b>:       to describe the domain restrictions of a DataProperty.</li>
 * <li><b>{@link DataPropertyExpression.Range}</b>:        to describe the range restrictions of a DataProperty.</li>
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
public class FullDataPropertyDesc
        extends DataPropertyDescriptorBase
        implements DataPropertyExpression.Disjoint<FullDataPropertyDesc>,
        DataPropertyExpression.Equivalent<FullDataPropertyDesc>,
        DataPropertyExpression.Sub<FullDataPropertyDesc>,
        DataPropertyExpression.Super<FullDataPropertyDesc>,
        DataPropertyExpression.Domain,
        DataPropertyExpression.Range{

    private DescriptorEntitySet.DataLinks disjointProperties = new DescriptorEntitySet.DataLinks();
    private DescriptorEntitySet.DataLinks equivalentProperties = new DescriptorEntitySet.DataLinks();
    private DescriptorEntitySet.DataLinks subProperties = new DescriptorEntitySet.DataLinks();
    private DescriptorEntitySet.DataLinks superProperties = new DescriptorEntitySet.DataLinks();
    private DescriptorEntitySet.Restrictions domainRestriction = new DescriptorEntitySet.Restrictions();
    private DescriptorEntitySet.Restrictions rangeRestriction = new DescriptorEntitySet.Restrictions();

    // Constructors from the abstract class: DataPropertyDescriptorBase

    public FullDataPropertyDesc(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public FullDataPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public FullDataPropertyDesc(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public FullDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public FullDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public FullDataPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public FullDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public FullDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // Implementation of readExpressionAxioms()

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Disjoint.super.readExpressionAxioms();
        r.addAll( DataPropertyExpression.Equivalent.super.readExpressionAxioms());
        r.addAll( DataPropertyExpression.Range.super.readExpressionAxioms());
        r.addAll( DataPropertyExpression.Domain.super.readExpressionAxioms());
        r.addAll( DataPropertyExpression.Sub.super.readExpressionAxioms());
        r.addAll( DataPropertyExpression.Super.super.readExpressionAxioms());
        return r;
    }

    // Implementation of writeExpressionAxioms()

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Disjoint.super.writeExpressionAxioms();
        r.addAll( DataPropertyExpression.Equivalent.super.writeExpressionAxioms());
        r.addAll( DataPropertyExpression.Range.super.writeExpressionAxioms());
        r.addAll( DataPropertyExpression.Domain.super.writeExpressionAxioms());
        r.addAll( DataPropertyExpression.Sub.super.writeExpressionAxioms());
        r.addAll( DataPropertyExpression.Super.super.writeExpressionAxioms());
        return r;
    }

    // Implementations for: DataPropertyExpression.Disjoint

    @Override // you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewDisjointDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinks getDisjointDataProperty() {
        return disjointProperties;
    }

    // Implementations for: DataPropertyExpression.Equivalent

    @Override // returns a set with elements of the same type of getNewDisjointDataProperty()
    public Set<FullDataPropertyDesc> buildEquivalentDataProperty() {
        return DataPropertyExpression.Equivalent.super.buildEquivalentDataProperty();
    }

    @Override // you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewEquivalentDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinks getEquivalentDataProperty() {
        return equivalentProperties;
    }

    // Implementations for: DataPropertyExpression.Domain

    @Override
    public DescriptorEntitySet.Restrictions getDomainDataProperty() {
        return domainRestriction;
    }

    // Implementations for: DataPropertyExpression.Range

    @Override
    public DescriptorEntitySet.Restrictions getRangeDataProperty() {
        return rangeRestriction;
    }

    // Implementations for: DataPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewSubDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinks getSubDataProperty() {
        return subProperties;
    }

    // Implementations for: DataPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewSuperDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinks getSuperDataProperty() {
        return superProperties;
    }

    // Implementations for: standard object interface

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointProperties +
                "," + NL + "\t≡ " + equivalentProperties +
                "," + NL + "\t→ " + domainRestriction +
                "," + NL + "\t← " + rangeRestriction +
                "," + NL + "\t⊃ " + subProperties +
                "," + NL + "\t⊂ " + superProperties +
                NL + "}";
    }
}
