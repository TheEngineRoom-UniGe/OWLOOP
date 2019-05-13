package it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ObjectPropertyDescriptorGround;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;

/**
 * A basic implementation for an object property with domain and range restrictions.
 * <p>
 *     This is an example of how use the {@link Descriptor}s for implement
 *     an object property that is synchronised w.r.t. its {@link Domain} and {@link Range} restrictions.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorEntitySet.Restrictions} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()} methods.
 *     All its constructions are based on {@link ObjectPropertyDescriptorGround} in order
 *     to automatically manage an {@link ObjectGroundInstance} ground.
 *     <br>
 *     You may want to use this class (see also {@link DefinitionObjectPropertyDesc}
 *     and {@link HierarchicalObjectPropertyDesc},as well as other classes in the
 *     {@link it.emarolab.owloop.descriptor.utility} package) as templates to build a specific
 *     {@link ObjectPropertyExpression} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for object properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.DomainObjectPropertyDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class DomainObjectPropertyDesc
        extends ObjectPropertyDescriptorGround
        implements ObjectPropertyExpression.Domain, ObjectPropertyExpression.Range {

    private DescriptorEntitySet.Restrictions domainRestriction = new DescriptorEntitySet.Restrictions();
    private DescriptorEntitySet.Restrictions rangeRestriction = new DescriptorEntitySet.Restrictions();


    // constructors for ObjectPropertyDescriptorGround

    public DomainObjectPropertyDesc(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DomainObjectPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DomainObjectPropertyDesc(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public DomainObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DomainObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DomainObjectPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DomainObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DomainObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Domain.super.readExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Range.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Domain.super.writeExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Range.super.writeExpressionAxioms());
        return r;
    }

    

    // implementations for ObjectPropertyExpression.Domain
    @Override
    public DescriptorEntitySet.Restrictions getDomainObjectProperty() {
        return domainRestriction;
    }

    

    // implementations for ObjectPropertyExpression.Range
    @Override
    public DescriptorEntitySet.Restrictions getRangeObjectProperty() {
        return rangeRestriction;
    }



    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorGround<?> which considers only the ground

    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t→ " + domainRestriction +
                "," + NL + "\t← " + rangeRestriction +
                NL + "}";
    }
}

