package it.emarolab.owloop.descriptor.utility.dataProperty_compoundDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorBase.DataPropertyDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorAxioms;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;

/**
 * A basic implementation for a data property with domain and range restrictions.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a data property that is synchronised w.r.t. its {@link Domain} and {@link Range} restrictions.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorAxioms.Restrictions} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link DataPropertyDescriptorBase} in order
 *     to automatically manage a grounding {@link DataInstance}.
 *     <br>
 *     You may want to use this class (see also {@link DefinitionDataPropertyDesc}
 *     and {@link HierarchicalDataPropertyDesc}, as well as other classes in the
 *     {@link it.emarolab.owloop.descriptor.utility} package) as templates to build a specific
 *     {@link DataPropertyExpression} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for data properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.dataProperty_compoundDescriptor.DomainDataPropertyDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class DomainDataPropertyDesc
        extends DataPropertyDescriptorBase
        implements DataPropertyExpression.Domain, DataPropertyExpression.Range {

    private DescriptorAxioms.Restrictions domainRestriction = new DescriptorAxioms.Restrictions();
    private DescriptorAxioms.Restrictions rangeRestriction = new DescriptorAxioms.Restrictions();


    // constructors for DataPropertyDescriptorBase

    public DomainDataPropertyDesc(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DomainDataPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DomainDataPropertyDesc(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public DomainDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DomainDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DomainDataPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DomainDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DomainDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = DataPropertyExpression.Domain.super.readSemantic();
        r.addAll( DataPropertyExpression.Range.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = DataPropertyExpression.Range.super.writeSemantic();
        r.addAll( DataPropertyExpression.Domain.super.writeSemantic());
        return r;
    }



    // implementations for DataPropertyExpression.Domain
    @Override
    public DescriptorAxioms.Restrictions getDomainDataProperty() {
        return domainRestriction;
    }



    // implementations for DataPropertyExpression.Range
    @Override
    public DescriptorAxioms.Restrictions getRangeDataProperty() {
        return rangeRestriction;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorBase<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t→ " + domainRestriction +
                "," + NL + "\t← " + rangeRestriction +
                NL + "}";
    }
}

