package it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DataPropertyGround;
import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;


/**
 * A basic implementation for a data property with equivalent and disjoint properties.
 * <p>
 *     This is an example of how use the {@link Axiom.Descriptor}s for implement
 *     a data property that is synchronised w.r.t. its {@link Disjoint} and {@link Equivalent} properties.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorEntitySet.DataLinks} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()} methods.
 *     All its constructions are based on {@link DataPropertyGround} in order
 *     to automatically manage a grounding {@link DataGroundInstance}.
 *     <br>
 *     You may want to use this class (see also {@link HierarchicalDataPropertyDesc}
 *     and {@link DomainDataPropertyDesc}, as well as other classes in the
 *     {@link it.emarolab.owloop.descriptor.utility} package) as templates to build a specific
 *     {@link DataPropertyExpression} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for data properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor.DefinitionDataPropertyDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class DefinitionDataPropertyDesc
        extends DataPropertyGround
        implements DataPropertyExpression.Disjoint<DefinitionDataPropertyDesc>,
        DataPropertyExpression.Equivalent<DefinitionDataPropertyDesc> {

    private DescriptorEntitySet.DataLinks disjointProperties = new DescriptorEntitySet.DataLinks();
    private DescriptorEntitySet.DataLinks equivalentProperties = new DescriptorEntitySet.DataLinks();


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
    public DescriptorEntitySet.DataLinks getDisjointDataProperty() {
        return disjointProperties;
    }



    // implementations for DataPropertyExpression.Equivalent

    @Override // called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public DefinitionDataPropertyDesc getNewEquivalentDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new DefinitionDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinks getEquivalentDataProperty() {
        return equivalentProperties;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorGround<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointProperties +
                "," + NL + "\t≡ " + equivalentProperties +
                NL + "}";
    }
}
