package it.emarolab.owloop.descriptor.utility.objectProperty_compoundDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorBase.ObjectPropertyDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorAxioms;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;


/**
 * A basic implementation for an object property with equivalent, disjoint and inverse properties.
 * <p>
 *     This is an example of how use the {@link Descriptor}s for implement
 *     an object property that is synchronised w.r.t. its {@link Disjoint},
 *     {@link Equivalent} and {@link Inverse} properties.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorAxioms.ObjectLinks} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link ObjectPropertyDescriptorBase} in order
 *     to automatically manage an {@link ObjectInstance} ground.
 *     <br>
 *     You may want to use this class (see also {@link DefinitionObjectPropertyDesc}
 *     and {@link HierarchicalObjectPropertyDesc},as well as other classes in the
 *     {@link it.emarolab.owloop.descriptor.utility} package) as templates to build a specific
 *     {@link ObjectPropertyExpression} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for object properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.objectProperty_compoundDescriptor.DefinitionObjectPropertyDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class DefinitionObjectPropertyDesc
        extends ObjectPropertyDescriptorBase
        implements ObjectPropertyExpression.Disjoint<DefinitionObjectPropertyDesc>,
        ObjectPropertyExpression.Equivalent<DefinitionObjectPropertyDesc>,
        ObjectPropertyExpression.Inverse<DefinitionObjectPropertyDesc> {

    private DescriptorAxioms.ObjectLinks disjointProperties = new DescriptorAxioms.ObjectLinks();
    private DescriptorAxioms.ObjectLinks equivalentProperties = new DescriptorAxioms.ObjectLinks();
    private DescriptorAxioms.ObjectLinks inverseProperties = new DescriptorAxioms.ObjectLinks();


    // constructors for ObjectPropertyDescriptorBase

    public DefinitionObjectPropertyDesc(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DefinitionObjectPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DefinitionObjectPropertyDesc(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public DefinitionObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DefinitionObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DefinitionObjectPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DefinitionObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DefinitionObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.readSemantic();
        r.addAll( ObjectPropertyExpression.Equivalent.super.readSemantic());
        r.addAll( ObjectPropertyExpression.Inverse.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.writeSemantic();
        r.addAll( ObjectPropertyExpression.Equivalent.super.writeSemantic());
        r.addAll( ObjectPropertyExpression.Inverse.super.writeSemantic());
        return r;
    }


    // implementations for ObjectPropertyExpression.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public DefinitionObjectPropertyDesc getNewDisjointObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new DefinitionObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.ObjectLinks getDisjointObjectProperty() {
        return disjointProperties;
    }



    // implementations for ObjectPropertyExpression.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public DefinitionObjectPropertyDesc getNewEquivalentObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new DefinitionObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.ObjectLinks getEquivalentObjectProperty() {
        return equivalentProperties;
    }



    // implementations for ObjectPropertyExpression.Inverse

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public DefinitionObjectPropertyDesc getNewInverseObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new DefinitionObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.ObjectLinks getInverseObjectProperty() {
        return inverseProperties;
    }



    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorBase<?> which considers only the ground

    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointProperties +
                "," + NL + "\t≡ " + equivalentProperties +
                "," + NL + "\t↔ " + inverseProperties +
                NL + "}";
    }

}
