package it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ObjectPropertyDescriptorGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;


/**
 * A basic implementation for an object property with equivalent, disjoint and inverse properties.
 * <p>
 *     This is an example of how use the {@link Descriptor}s for implement
 *     an object property that is synchronised w.r.t. its {@link Disjoint},
 *     {@link Equivalent} and {@link Inverse} properties.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorEntitySet.ObjectLinks} for the
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
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.DefinitionObjectPropertyDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class DefinitionObjectPropertyDesc
        extends ObjectPropertyDescriptorGround
        implements ObjectPropertyExpression.Disjoint<DefinitionObjectPropertyDesc>,
        ObjectPropertyExpression.Equivalent<DefinitionObjectPropertyDesc>,
        ObjectPropertyExpression.Inverse<DefinitionObjectPropertyDesc> {

    private DescriptorEntitySet.ObjectLinks disjointProperties = new DescriptorEntitySet.ObjectLinks();
    private DescriptorEntitySet.ObjectLinks equivalentProperties = new DescriptorEntitySet.ObjectLinks();
    private DescriptorEntitySet.ObjectLinks inverseProperties = new DescriptorEntitySet.ObjectLinks();


    // constructors for ObjectPropertyDescriptorGround

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



    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.readExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Equivalent.super.readExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Inverse.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.writeExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Equivalent.super.writeExpressionAxioms());
        r.addAll( ObjectPropertyExpression.Inverse.super.writeExpressionAxioms());
        return r;
    }


    // implementations for ObjectPropertyExpression.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public DefinitionObjectPropertyDesc getNewDisjointObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new DefinitionObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectLinks getDisjointObjectProperty() {
        return disjointProperties;
    }



    // implementations for ObjectPropertyExpression.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public DefinitionObjectPropertyDesc getNewEquivalentObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new DefinitionObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectLinks getEquivalentObjectProperty() {
        return equivalentProperties;
    }



    // implementations for ObjectPropertyExpression.Inverse

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public DefinitionObjectPropertyDesc getNewInverseObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new DefinitionObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectLinks getInverseObjectProperty() {
        return inverseProperties;
    }



    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorGround<?> which considers only the ground

    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointProperties +
                "," + NL + "\t≡ " + equivalentProperties +
                "," + NL + "\t↔ " + inverseProperties +
                NL + "}";
    }

}
