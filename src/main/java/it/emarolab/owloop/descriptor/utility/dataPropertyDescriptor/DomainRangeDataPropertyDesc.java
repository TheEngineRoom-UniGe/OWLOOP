package it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DataPropertyGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;

/**
 * This is an example of a 'compound' DataProperty Descriptor which implements 2 {@link DataPropertyExpression}s.
 * <ul>
 * <li><b>{@link DataPropertyExpression.Domain}</b>:       to describe the domain restrictions of a DataProperty.</li>
 * <li><b>{@link DataPropertyExpression.Range}</b>:        to describe the range restrictions of a DataProperty.</li>
 * </ul>
 * See {@link FullDataPropertyDescriptor} for an example of a 'compound' DataProperty Descriptor that implements all DataPropertyExpressions.
 */
public class DomainRangeDataPropertyDesc
        extends DataPropertyGround
        implements DataPropertyExpression.Domain,
        DataPropertyExpression.Range {

    private DescriptorEntitySet.Restrictions domainRestriction = new DescriptorEntitySet.Restrictions();
    private DescriptorEntitySet.Restrictions rangeRestriction = new DescriptorEntitySet.Restrictions();

    // constructors for DataPropertyGround

    public DomainRangeDataPropertyDesc(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DomainRangeDataPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DomainRangeDataPropertyDesc(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public DomainRangeDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DomainRangeDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DomainRangeDataPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DomainRangeDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DomainRangeDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Domain.super.readExpressionAxioms();
        r.addAll( DataPropertyExpression.Range.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Range.super.writeExpressionAxioms();
        r.addAll( DataPropertyExpression.Domain.super.writeExpressionAxioms());
        return r;
    }

    // implementations for DataPropertyExpression.Domain
    @Override
    public DescriptorEntitySet.Restrictions getDomainDataProperty() {
        return domainRestriction;
    }

    // implementations for DataPropertyExpression.Range
    @Override
    public DescriptorEntitySet.Restrictions getRangeDataProperty() {
        return rangeRestriction;
    }

    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorGround<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDescriptor{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t→ " + domainRestriction +
                "," + NL + "\t← " + rangeRestriction +
                NL + "}";
    }
}

