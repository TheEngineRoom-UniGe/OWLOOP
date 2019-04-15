package it.emarolab.owloop.descriptor.utility.dataProperty_compoundDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorBase.DataPropertyDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorAxioms;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;

/**
 * A basic implementation for a data property with sub and super properties.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a data property that is synchronised w.r.t. its {@link Super} and {@link Sub} properties.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorAxioms.DataLinks} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link DataPropertyDescriptorBase} in order
 *     to automatically manage a grounding {@link DataInstance}.
 *     <br>
 *     You may want to use this class (see also {@link DefinitionDataPropertyDesc}
 *     and {@link DomainDataPropertyDesc} as well as other classes in the
 *     {@link it.emarolab.owloop.descriptor.utility} package) as templates to build a specific
 *     {@link DataPropertyExpression} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for data properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.dataProperty_compoundDescriptor.HierarchicalDataPropertyDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class HierarchicalDataPropertyDesc
        extends DataPropertyDescriptorBase
        implements DataPropertyExpression.Sub<HierarchicalDataPropertyDesc>,
        DataPropertyExpression.Super<HierarchicalDataPropertyDesc>{

    private DescriptorAxioms.DataLinks subProperties = new DescriptorAxioms.DataLinks();
    private DescriptorAxioms.DataLinks superProperties = new DescriptorAxioms.DataLinks();

    // constructors for DataPropertyDescriptorBase

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



    // implementations for Semantic.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = DataPropertyExpression.Sub.super.readSemantic();
        r.addAll( DataPropertyExpression.Super.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = DataPropertyExpression.Sub.super.writeSemantic();
        r.addAll( DataPropertyExpression.Super.super.writeSemantic());
        return r;
    }



    // implementations for DataPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public HierarchicalDataPropertyDesc getNewSubDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new HierarchicalDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.DataLinks getSubDataProperty() {
        return subProperties;
    }



    // implementations for DataPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public HierarchicalDataPropertyDesc getNewSuperDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new HierarchicalDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.DataLinks getSuperDataProperty() {
        return superProperties;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorBase<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊃ " + subProperties +
                "," + NL + "\t⊂ " + superProperties +
                NL + "}";
    }
}
