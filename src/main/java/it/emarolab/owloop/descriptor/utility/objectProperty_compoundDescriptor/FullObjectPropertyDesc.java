package it.emarolab.owloop.descriptor.utility.objectProperty_compoundDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorBase.ObjectPropertyDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorAxioms;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;

/**
 * The implementation of all the semantic features of an object property.
 * <p>
 *     This is an example of how use the {@link Descriptor}s for implement
 *     an object property that is synchronised w.r.t. all interfaces of {@link ObjectPropertyExpression}.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorAxioms.ObjectLinks}
 *     and {@link DescriptorAxioms.Restrictions} for the
 *     respective descriptions, as well as call all interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link ObjectPropertyDescriptorBase} in order
 *     to automatically manage an {@link ObjectInstance} ground.
 *     <br>
 *     In order to optimise the synchronissation efficiency (i.e.: minimize
 *     reasoning calls) use this object only if it is necessary.
 *     Otherwise is recommended to use an had hoc {@link Descriptor}.
 *     You may want to use this class, see also {@link HierarchicalObjectPropertyDesc},
 *     {@link DefinitionObjectPropertyDesc} and {@link DomainObjectPropertyDesc}
 *     (generally, all the classes in the {@link it.emarolab.owloop.descriptor.utility} package)
 *     as templates for doing that.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.objectProperty_compoundDescriptor.FullObjectPropertyDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class FullObjectPropertyDesc
        extends ObjectPropertyDescriptorBase
        implements ObjectPropertyExpression.Disjoint<FullObjectPropertyDesc>,
        ObjectPropertyExpression.Equivalent<FullObjectPropertyDesc>,
        ObjectPropertyExpression.Inverse<FullObjectPropertyDesc>,
        ObjectPropertyExpression.Sub<FullObjectPropertyDesc>,
        ObjectPropertyExpression.Super<FullObjectPropertyDesc>,
        ObjectPropertyExpression.Domain,
        ObjectPropertyExpression.Range{

    private DescriptorAxioms.ObjectLinks disjointProperties = new DescriptorAxioms.ObjectLinks();
    private DescriptorAxioms.ObjectLinks equivalentProperties = new DescriptorAxioms.ObjectLinks();
    private DescriptorAxioms.Restrictions domainRestriction = new DescriptorAxioms.Restrictions();
    private DescriptorAxioms.Restrictions rangeRestriction = new DescriptorAxioms.Restrictions();
    private DescriptorAxioms.ObjectLinks subProperties = new DescriptorAxioms.ObjectLinks();
    private DescriptorAxioms.ObjectLinks superProperties = new DescriptorAxioms.ObjectLinks();
    private DescriptorAxioms.ObjectLinks inverseProperties = new DescriptorAxioms.ObjectLinks();


    // constructors for DataPropertyDescriptorBase

    public FullObjectPropertyDesc(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public FullObjectPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public FullObjectPropertyDesc(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public FullObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public FullObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public FullObjectPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public FullObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public FullObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }




    // implementations for Semantic.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.readSemantic();
        r.addAll( ObjectPropertyExpression.Equivalent.super.readSemantic());
        r.addAll( ObjectPropertyExpression.Range.super.readSemantic());
        r.addAll( ObjectPropertyExpression.Domain.super.readSemantic());
        r.addAll( ObjectPropertyExpression.Sub.super.readSemantic());
        r.addAll( ObjectPropertyExpression.Super.super.readSemantic());
        r.addAll( ObjectPropertyExpression.Inverse.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.writeSemantic();
        r.addAll( ObjectPropertyExpression.Equivalent.super.writeSemantic());
        r.addAll( ObjectPropertyExpression.Range.super.writeSemantic());
        r.addAll( ObjectPropertyExpression.Domain.super.writeSemantic());
        r.addAll( ObjectPropertyExpression.Sub.super.writeSemantic());
        r.addAll( ObjectPropertyExpression.Super.super.writeSemantic());
        r.addAll( ObjectPropertyExpression.Inverse.super.writeSemantic());
        return r;
    }


    // implementations for ObjectPropertyExpression.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDesc getNewDisjointObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.ObjectLinks getDisjointObjectProperty() {
        return disjointProperties;
    }



    // implementations for ObjectPropertyExpression.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDesc getNewEquivalentObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.ObjectLinks getEquivalentObjectProperty() {
        return equivalentProperties;
    }




    // implementations for ObjectPropertyExpression.Domain
    @Override
    public DescriptorAxioms.Restrictions getDomainObjectProperty() {
        return domainRestriction;
    }



    // implementations for ObjectPropertyExpression.Range
    @Override
    public DescriptorAxioms.Restrictions getRangeObjectProperty() {
        return rangeRestriction;
    }




    // implementations for ObjectPropertyExpression.Sub

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDesc getNewSubObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.ObjectLinks getSubObjectProperty() {
        return subProperties;
    }



    // implementations for ObjectPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDesc getNewSuperObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.ObjectLinks getSuperObjectProperty() {
        return superProperties;
    }




    // implementations for ObjectPropertyExpression.Inverse

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDesc getNewInverseObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.ObjectLinks getInverseObjectProperty() {
        return inverseProperties;
    }



    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorBase<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointProperties +
                "," + NL + "\t≡ " + equivalentProperties +
                "," + NL + "\t→ " + domainRestriction +
                "," + NL + "\t← " + rangeRestriction +
                "," + NL + "\t⊃ " + subProperties +
                "," + NL + "\t⊂ " + superProperties +
                "," + NL + "\t↔ " + inverseProperties +
                NL + "}";
    }
}
