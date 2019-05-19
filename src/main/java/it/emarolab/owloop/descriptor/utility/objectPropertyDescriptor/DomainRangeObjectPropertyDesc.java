package it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ObjectPropertyGround;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;

/**
 * This is an example of a 'compound' ObjectProperty Descriptor which implements 2 {@link ObjectPropertyExpression}s.
 * <ul>
 * <li><b>{@link ObjectPropertyExpression.Domain}</b>:       to describe the domain restrictions of an ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Range}</b>:        to describe the range restrictions of an ObjectProperty.</li>
 * </ul>
 * See {@link FullObjectPropertyDescriptor} for an example of a 'compound' Individual Descriptor that implements all ObjectPropertyExpressions.
 */
public class DomainRangeObjectPropertyDesc
        extends ObjectPropertyGround
        implements ObjectPropertyExpression.Domain,
        ObjectPropertyExpression.Range {

    private DescriptorEntitySet.Restrictions domainRestriction = new DescriptorEntitySet.Restrictions();
    private DescriptorEntitySet.Restrictions rangeRestriction = new DescriptorEntitySet.Restrictions();

    // constructors for ObjectPropertyGround

    public DomainRangeObjectPropertyDesc(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DomainRangeObjectPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DomainRangeObjectPropertyDesc(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public DomainRangeObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DomainRangeObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DomainRangeObjectPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DomainRangeObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DomainRangeObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Domain.super.readExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Range.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Domain.super.writeExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Range.super.writeExpressionAxioms());
        return r;
    }

    // implementations for ObjectPropertyExpression.Domain

    @Override
    public DescriptorEntitySet.Restrictions getDomainObjectProperty() {
        return domainRestriction;
    }

    // implementations for ObjectPropertyExpression.Range

    @Override
    public DescriptorEntitySet.Restrictions getRangeObjectProperty() {
        return rangeRestriction;
    }

    public String toString() {
        return "FullObjectPropertyDescriptor{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t→ " + domainRestriction +
                "," + NL + "\t← " + rangeRestriction +
                NL + "}";
    }
}

