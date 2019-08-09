package it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DataPropertyGround;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;


/**
 * This is an example of a 'compound' DataProperty Descriptor which implements 2 {@link DataPropertyExpression} interfaces:
 *
 * <ul>
 * <li><b>{@link DataPropertyExpression.Equivalent}</b>:   to describe that a DataProperty is equivalent to another DataProperty.</li>
 * <li><b>{@link DataPropertyExpression.Disjoint}</b>:     to describe that a DataProperty is disjoint to another DataProperty.</li>
 * </ul>
 *
 * See {@link FullDataPropertyDesc} for an example of a 'compound' DataProperty Descriptor that implements all DataPropertyExpressions.
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
public class RestrictionDataPropertyDesc
        extends DataPropertyGround
        implements DataPropertyExpression.Disjoint<RestrictionDataPropertyDesc>,
        DataPropertyExpression.Equivalent<RestrictionDataPropertyDesc> {

    private DescriptorEntitySet.DataProperties disjointDataProperties = new DescriptorEntitySet.DataProperties();
    private DescriptorEntitySet.DataProperties equivalentDataProperties = new DescriptorEntitySet.DataProperties();

    /* Constructors from class: DataPropertyGround */

    public RestrictionDataPropertyDesc(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public RestrictionDataPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public RestrictionDataPropertyDesc(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public RestrictionDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public RestrictionDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public RestrictionDataPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public RestrictionDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public RestrictionDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
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
    public RestrictionDataPropertyDesc getNewDisjointDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new RestrictionDataPropertyDesc( instance, ontology);
    }
    // It returns disjointDataProperties from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.DataProperties getDisjointDataProperties() {
        return disjointDataProperties;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public RestrictionDataPropertyDesc getNewEquivalentDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new RestrictionDataPropertyDesc( instance, ontology);
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
