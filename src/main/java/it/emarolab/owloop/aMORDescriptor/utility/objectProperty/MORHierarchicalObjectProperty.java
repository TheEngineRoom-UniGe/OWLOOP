package it.emarolab.owloop.aMORDescriptor.utility.objectProperty;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORObjectProperty;
import it.emarolab.owloop.aMORDescriptor.utility.MORObjectPropertyBase;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;

/**
 * A basic implementation for an object property with sub and super properties.
 * <p>
 *     This is an example of how use the {@link Descriptor}s for implement
 *     a object property that is synchronised w.r.t. its {@link Super} and {@link Sub} properties.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.ObjectLinks} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link MORObjectPropertyBase} in order
 *     to automatically manage an {@link ObjectInstance} ground.
 *     <br>
 *     You may want to use this class (see also {@link MORDefinitionObjectProperty}
 *     and {@link MORDomainObjectProperty} as well as other classes in the
 *     {@link it.emarolab.owloop.aMORDescriptor.utility} package) as templates to build a specific
 *     {@link MORObjectProperty} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for object properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.objectProperty.MORHierarchicalObjectProperty <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORHierarchicalObjectProperty
        extends MORObjectPropertyBase
        implements MORObjectProperty.Sub<MORHierarchicalObjectProperty>,
        MORObjectProperty.Super<MORHierarchicalObjectProperty>{

    private MORAxioms.ObjectLinks subProperties = new MORAxioms.ObjectLinks();
    private MORAxioms.ObjectLinks superProperties = new MORAxioms.ObjectLinks();

    // constructors for MORObjectPropertyBase

    public MORHierarchicalObjectProperty(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORHierarchicalObjectProperty(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORHierarchicalObjectProperty(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORHierarchicalObjectProperty(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORHierarchicalObjectProperty(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORHierarchicalObjectProperty(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORHierarchicalObjectProperty(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORHierarchicalObjectProperty(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORObjectProperty.Sub.super.readSemantic();
        r.addAll( MORObjectProperty.Super.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORObjectProperty.Sub.super.writeSemantic();
        r.addAll( MORObjectProperty.Super.super.writeSemantic());
        return r;
    }



    // implementations for MORObjectProperty.Super

    @Override //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORHierarchicalObjectProperty getNewSubObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new MORHierarchicalObjectProperty( instance, ontology);
    }

    @Override
    public MORAxioms.ObjectLinks getSubObjectProperty() {
        return subProperties;
    }



    // implementations for MORObjectProperty.Super

    @Override  //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORHierarchicalObjectProperty getNewSuperObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new MORHierarchicalObjectProperty( instance, ontology);
    }

    @Override
    public MORAxioms.ObjectLinks getSuperObjectProperty() {
        return superProperties;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground

    public String toString() {
        return "MORFullObjectProperty{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊃ " + subProperties +
                "," + NL + "\t⊂ " + superProperties +
                NL + "}";
    }
}
