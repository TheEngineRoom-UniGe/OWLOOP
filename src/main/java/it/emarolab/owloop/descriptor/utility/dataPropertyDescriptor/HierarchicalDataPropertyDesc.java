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
 * <li><b>{@link DataPropertyExpression.Sub}</b>:          to describe that a DataProperty is subsumes another DataProperty.</li>
 * <li><b>{@link DataPropertyExpression.Super}</b>:        to describe that a DataProperty is super-sumes another DataProperty.</li>
 * </ul>
 * See {@link FullDataPropertyDesc} for an example of a 'compound' DataProperty Descriptor that implements all DataPropertyExpressions.
 */
public class HierarchicalDataPropertyDesc
        extends DataPropertyGround
        implements DataPropertyExpression.Sub<HierarchicalDataPropertyDesc>,
        DataPropertyExpression.Super<HierarchicalDataPropertyDesc>{

    private DescriptorEntitySet.DataProperties subDataProperties = new DescriptorEntitySet.DataProperties();
    private DescriptorEntitySet.DataProperties superDataProperties = new DescriptorEntitySet.DataProperties();

    // constructors for DataPropertyGround

    public HierarchicalDataPropertyDesc(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public HierarchicalDataPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public HierarchicalDataPropertyDesc(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public HierarchicalDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public HierarchicalDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public HierarchicalDataPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public HierarchicalDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public HierarchicalDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Sub.super.readExpressionAxioms();
        r.addAll( DataPropertyExpression.Super.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Sub.super.writeExpressionAxioms();
        r.addAll( DataPropertyExpression.Super.super.writeExpressionAxioms());
        return r;
    }

    // implementations for DataPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public HierarchicalDataPropertyDesc getNewSubDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new HierarchicalDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataProperties getSubDataProperties() {
        return subDataProperties;
    }

    // implementations for DataPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public HierarchicalDataPropertyDesc getNewSuperDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new HierarchicalDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataProperties getSuperDataProperties() {
        return superDataProperties;
    }

    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorGround<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊃ " + subDataProperties +
                "," + NL + "\t⊂ " + superDataProperties +
                NL + "}";
    }
}
