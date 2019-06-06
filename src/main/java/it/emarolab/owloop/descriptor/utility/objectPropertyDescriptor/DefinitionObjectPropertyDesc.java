package it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ObjectPropertyGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;


/**
 * This is an example of a 'compound' ObjectProperty Descriptor which implements 3 {@link ObjectPropertyExpression}s.
 * <ul>
 * <li><b>{@link ObjectPropertyExpression.Equivalent}</b>:   to describe that an ObjectProperty is equivalent to another ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Disjoint}</b>:     to describe that an ObjectProperty is disjoint to another ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Inverse}</b>:      to describe that an ObjectProperty has another inverse ObjectProperty.</li>
 * </ul>
 * See {@link FullObjectPropertyDesc} for an example of a 'compound' Individual Descriptor that implements all ObjectPropertyExpressions.
 */
public class DefinitionObjectPropertyDesc
        extends ObjectPropertyGround
        implements ObjectPropertyExpression.Disjoint<DefinitionObjectPropertyDesc>,
        ObjectPropertyExpression.Equivalent<DefinitionObjectPropertyDesc>,
        ObjectPropertyExpression.Inverse<DefinitionObjectPropertyDesc> {

    private DescriptorEntitySet.ObjectProperties disjointObjectProperties = new DescriptorEntitySet.ObjectProperties();
    private DescriptorEntitySet.ObjectProperties equivalentObjectProperties = new DescriptorEntitySet.ObjectProperties();
    private DescriptorEntitySet.ObjectProperties inverseObjectProperties = new DescriptorEntitySet.ObjectProperties();

    // constructors for ObjectPropertyGround

    public DefinitionObjectPropertyDesc(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DefinitionObjectPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DefinitionObjectPropertyDesc(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public DefinitionObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DefinitionObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DefinitionObjectPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DefinitionObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DefinitionObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.readExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Equivalent.super.readExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Inverse.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.writeExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Equivalent.super.writeExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Inverse.super.writeExpressionAxioms());
        return r;
    }

    // implementations for ObjectPropertyExpression.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public DefinitionObjectPropertyDesc getNewDisjointObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new DefinitionObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectProperties getDisjointObjectProperties() {
        return disjointObjectProperties;
    }

    // implementations for ObjectPropertyExpression.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public DefinitionObjectPropertyDesc getNewEquivalentObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new DefinitionObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectProperties getEquivalentObjectProperties() {
        return equivalentObjectProperties;
    }

    // implementations for ObjectPropertyExpression.Inverse

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public DefinitionObjectPropertyDesc getNewInverseObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new DefinitionObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectProperties getInverseObjectProperties() {
        return inverseObjectProperties;
    }

    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointObjectProperties +
                "," + NL + "\t≡ " + equivalentObjectProperties +
                "," + NL + "\t↔ " + inverseObjectProperties +
                NL + "}";
    }

}
