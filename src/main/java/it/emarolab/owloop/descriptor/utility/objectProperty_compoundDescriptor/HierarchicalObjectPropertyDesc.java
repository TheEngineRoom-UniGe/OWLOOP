package it.emarolab.owloop.descriptor.utility.objectProperty_compoundDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorBase.ObjectPropertyDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorAxioms;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;

/**
 * A basic implementation for an object property with sub and super properties.
 * <p>
 *     This is an example of how use the {@link Descriptor}s for implement
 *     a object property that is synchronised w.r.t. its {@link Super} and {@link Sub} properties.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorAxioms.ObjectLinks} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link ObjectPropertyDescriptorBase} in order
 *     to automatically manage an {@link ObjectInstance} ground.
 *     <br>
 *     You may want to use this class (see also {@link DefinitionObjectPropertyDesc}
 *     and {@link DomainObjectPropertyDesc} as well as other classes in the
 *     {@link it.emarolab.owloop.descriptor.utility} package) as templates to build a specific
 *     {@link ObjectPropertyExpression} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for object properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.objectProperty_compoundDescriptor.HierarchicalObjectPropertyDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class HierarchicalObjectPropertyDesc
        extends ObjectPropertyDescriptorBase
        implements ObjectPropertyExpression.Sub<HierarchicalObjectPropertyDesc>,
        ObjectPropertyExpression.Super<HierarchicalObjectPropertyDesc>{

    private DescriptorAxioms.ObjectLinks subProperties = new DescriptorAxioms.ObjectLinks();
    private DescriptorAxioms.ObjectLinks superProperties = new DescriptorAxioms.ObjectLinks();

    // constructors for ObjectPropertyDescriptorBase

    public HierarchicalObjectPropertyDesc(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public HierarchicalObjectPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public HierarchicalObjectPropertyDesc(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public HierarchicalObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public HierarchicalObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public HierarchicalObjectPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public HierarchicalObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public HierarchicalObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = ObjectPropertyExpression.Sub.super.readSemantic();
        r.addAll( ObjectPropertyExpression.Super.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = ObjectPropertyExpression.Sub.super.writeSemantic();
        r.addAll( ObjectPropertyExpression.Super.super.writeSemantic());
        return r;
    }



    // implementations for ObjectPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public HierarchicalObjectPropertyDesc getNewSubObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new HierarchicalObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.ObjectLinks getSubObjectProperty() {
        return subProperties;
    }



    // implementations for ObjectPropertyExpression.Super

    @Override  //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public HierarchicalObjectPropertyDesc getNewSuperObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new HierarchicalObjectPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.ObjectLinks getSuperObjectProperty() {
        return superProperties;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorBase<?> which considers only the ground

    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊃ " + subProperties +
                "," + NL + "\t⊂ " + superProperties +
                NL + "}";
    }
}
