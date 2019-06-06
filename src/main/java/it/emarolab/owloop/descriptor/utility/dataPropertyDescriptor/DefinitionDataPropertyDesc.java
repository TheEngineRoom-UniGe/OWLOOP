package it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DataPropertyGround;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;


/**
 * This is an example of a 'compound' DataProperty Descriptor which implements 2 {@link DataPropertyExpression}s.
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

    // constructors for DataPropertyGround

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

    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Disjoint.super.readExpressionAxioms();
        r.addAll( DataPropertyExpression.Equivalent.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Disjoint.super.writeExpressionAxioms();
        r.addAll( DataPropertyExpression.Equivalent.super.writeExpressionAxioms());
        return r;
    }

    // implementations for DataPropertyExpression.Disjoint

    @Override  // called during build...()you can change the returning type to any implementations of DataPropertyExpression
    public DefinitionDataPropertyDesc getNewDisjointDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new DefinitionDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataProperties getDisjointDataProperties() {
        return disjointDataProperties;
    }

    // implementations for DataPropertyExpression.Equivalent

    @Override // called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public DefinitionDataPropertyDesc getNewEquivalentDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new DefinitionDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataProperties getEquivalentDataProperties() {
        return equivalentDataProperties;
    }

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointDataProperties +
                "," + NL + "\t≡ " + equivalentDataProperties +
                NL + "}";
    }
}
