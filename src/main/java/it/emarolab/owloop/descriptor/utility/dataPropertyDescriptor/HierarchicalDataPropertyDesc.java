package it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.core.Axiom;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DataPropertyGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;

/**
 * A basic implementation for a data property with sub and super properties.
 * <p>
 *     This is an example of how use the {@link Axiom.Descriptor}s for implement
 *     a data property that is synchronised w.r.t. its {@link Super} and {@link Sub} properties.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorEntitySet.DataLinks} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()} methods.
 *     All its constructions are based on {@link DataPropertyGround} in order
 *     to automatically manage a grounding {@link DataGroundInstance}.
 *     <br>
 *     You may want to use this class (see also {@link DefinitionDataPropertyDesc}
 *     and {@link DomainDataPropertyDesc} as well as other classes in the
 *     {@link it.emarolab.owloop.descriptor.utility} package) as templates to build a specific
 *     {@link DataPropertyExpression} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for data properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor.HierarchicalDataPropertyDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class HierarchicalDataPropertyDesc
        extends DataPropertyGround
        implements DataPropertyExpression.Sub<HierarchicalDataPropertyDesc>,
        DataPropertyExpression.Super<HierarchicalDataPropertyDesc>{

    private DescriptorEntitySet.DataLinks subProperties = new DescriptorEntitySet.DataLinks();
    private DescriptorEntitySet.DataLinks superProperties = new DescriptorEntitySet.DataLinks();

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
    public DescriptorEntitySet.DataLinks getSubDataProperty() {
        return subProperties;
    }



    // implementations for DataPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public HierarchicalDataPropertyDesc getNewSuperDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new HierarchicalDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinks getSuperDataProperty() {
        return superProperties;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorGround<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊃ " + subProperties +
                "," + NL + "\t⊂ " + superProperties +
                NL + "}";
    }
}
