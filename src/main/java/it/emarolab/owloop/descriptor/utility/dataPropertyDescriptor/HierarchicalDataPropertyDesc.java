package it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DataPropertyGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;

/**
 * This is an example of a 'compound' DataProperty Descriptor which implements 2 {@link DataPropertyExpression} interfaces:
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

    /* Constructors from class: DataPropertyGround */

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

    /* Overriding methods in class: DataPropertyGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Sub.super.readExpressionAxioms();
        r.addAll( DataPropertyExpression.Super.super.readExpressionAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Sub.super.writeExpressionAxioms();
        r.addAll( DataPropertyExpression.Super.super.writeExpressionAxioms());
        return r;
    }

    /* Overriding methods in classes: DataProperty and DataPropertyExpression */


    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public HierarchicalDataPropertyDesc getNewSubDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new HierarchicalDataPropertyDesc( instance, ontology);
    }
    // It returns subDataProperties from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.DataProperties getSubDataProperties() {
        return subDataProperties;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public HierarchicalDataPropertyDesc getNewSuperDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new HierarchicalDataPropertyDesc( instance, ontology);
    }
    // It returns superDataProperties from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.DataProperties getSuperDataProperties() {
        return superDataProperties;
    }

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t⊃ " +        subDataProperties + "\n" +
                "\t\t⊂ " +        superDataProperties + "\n" +
                "}" + "\n";
    }
}
