package it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DataPropertyGround;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;


/**
 * This is an example of a 'compound' DataProperty Descriptor which implements 2 {@link DataPropertyExpression} interfaces:
 * <ul>
 * <li><b>{@link DataPropertyExpression.Equivalent}</b>:   to describe that a DataProperty is equivalent to another DataProperty.</li>
 * <li><b>{@link DataPropertyExpression.Disjoint}</b>:     to describe that a DataProperty is disjoint to another DataProperty.</li>
 * </ul>
 * See {@link FullDataPropertyDesc} for an example of a 'compound' DataProperty Descriptor that implements all DataPropertyExpressions.
 */
public class DefinitionDataPropertyDesc
        extends DataPropertyGround
        implements DataPropertyExpression.Disjoint<DefinitionDataPropertyDesc>,
        DataPropertyExpression.Equivalent<DefinitionDataPropertyDesc> {

    private DescriptorEntitySet.DataProperties disjointDataProperties = new DescriptorEntitySet.DataProperties();
    private DescriptorEntitySet.DataProperties equivalentDataProperties = new DescriptorEntitySet.DataProperties();

    /* Constructors from class: DataPropertyGround */

    public DefinitionDataPropertyDesc(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DefinitionDataPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DefinitionDataPropertyDesc(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public DefinitionDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DefinitionDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DefinitionDataPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DefinitionDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DefinitionDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    /* Overriding methods in class: DataPropertyGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Disjoint.super.readExpressionAxioms();
        r.addAll( DataPropertyExpression.Equivalent.super.readExpressionAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Disjoint.super.writeExpressionAxioms();
        r.addAll( DataPropertyExpression.Equivalent.super.writeExpressionAxioms());
        return r;
    }

    /* Overriding methods in classes: DataProperty and DataPropertyExpression */


    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public DefinitionDataPropertyDesc getNewDisjointDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new DefinitionDataPropertyDesc( instance, ontology);
    }
    // It returns disjointDataProperties from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.DataProperties getDisjointDataProperties() {
        return disjointDataProperties;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public DefinitionDataPropertyDesc getNewEquivalentDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new DefinitionDataPropertyDesc( instance, ontology);
    }
    // It returns equivalentDataProperties from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.DataProperties getEquivalentDataProperties() {
        return equivalentDataProperties;
    }

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t≠ " +        disjointDataProperties + "\n" +
                "\t\t≡ " +        equivalentDataProperties + "\n" +
                "}" + "\n";
    }
}
