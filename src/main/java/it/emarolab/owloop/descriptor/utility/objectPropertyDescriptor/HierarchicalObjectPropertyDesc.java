package it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ObjectPropertyGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;

/**
 * This is an example of a 'compound' ObjectProperty Descriptor which implements 2 {@link ObjectPropertyExpression}s.
 * <ul>
 * <li><b>{@link ObjectPropertyExpression.Sub}</b>:          to describe that an ObjectProperty subsumes another ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Super}</b>:        to describe that an ObjectProperty super-sumes another ObjectProperty.</li>
 * </ul>
 * See {@link FullObjectPropertyDesc} for an example of a 'compound' Individual Descriptor that implements all ObjectPropertyExpressions.
 */
public class HierarchicalObjectPropertyDesc
        extends ObjectPropertyGround
        implements ObjectPropertyExpression.Sub<HierarchicalObjectPropertyDesc>,
        ObjectPropertyExpression.Super<HierarchicalObjectPropertyDesc>{

    private DescriptorEntitySet.ObjectProperties subObjectProperties = new DescriptorEntitySet.ObjectProperties();
    private DescriptorEntitySet.ObjectProperties superObjectProperties = new DescriptorEntitySet.ObjectProperties();

    // constructors for ObjectPropertyGround

    public HierarchicalObjectPropertyDesc(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public HierarchicalObjectPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public HierarchicalObjectPropertyDesc(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public HierarchicalObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public HierarchicalObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public HierarchicalObjectPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public HierarchicalObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public HierarchicalObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Sub.super.readExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Super.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Sub.super.writeExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Super.super.writeExpressionAxioms());
        return r;
    }

    // implementations for ObjectPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public HierarchicalObjectPropertyDesc getNewSubObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new HierarchicalObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectProperties getSubObjectProperties() {
        return subObjectProperties;
    }

    // implementations for ObjectPropertyExpression.Super

    @Override  //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public HierarchicalObjectPropertyDesc getNewSuperObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new HierarchicalObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectProperties getSuperObjectProperties() {
        return superObjectProperties;
    }

    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊃ " + subObjectProperties +
                "," + NL + "\t⊂ " + superObjectProperties +
                NL + "}";
    }
}
