package it.emarolab.owloop.aMORDescriptor.utility.objectProperty;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORObjectProperty;
import it.emarolab.owloop.aMORDescriptor.utility.MORObjectPropertyBase;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;


/**
 * A basic implementation for an object property with equivalent, disjoint and inverse properties.
 * <p>
 *     This is an example of how use the {@link Descriptor}s for implement
 *     an object property that is synchronised w.r.t. its {@link Disjoint},
 *     {@link Equivalent} and {@link Inverse} properties.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.ObjectLinks} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link MORObjectPropertyBase} in order
 *     to automatically manage an {@link ObjectInstance} ground.
 *     <br>
 *     You may want to use this class (see also {@link MORDefinitionObjectProperty}
 *     and {@link MORHierarchicalObjectProperty},as well as other classes in the
 *     {@link it.emarolab.owloop.aMORDescriptor.utility} package) as templates to build a specific
 *     {@link MORObjectProperty} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for object properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.objectProperty.MORDefinitionObjectProperty <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORDefinitionObjectProperty
        extends MORObjectPropertyBase
        implements MORObjectProperty.Disjoint<MORDefinitionObjectProperty>,
        MORObjectProperty.Equivalent<MORDefinitionObjectProperty>,
        MORObjectProperty.Inverse<MORDefinitionObjectProperty> {

    private MORAxioms.ObjectLinks disjointProperties = new MORAxioms.ObjectLinks();
    private MORAxioms.ObjectLinks equivalentProperties = new MORAxioms.ObjectLinks();
    private MORAxioms.ObjectLinks inverseProperties = new MORAxioms.ObjectLinks();


    // constructors for MORObjectPropertyBase

    public MORDefinitionObjectProperty(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORDefinitionObjectProperty(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORDefinitionObjectProperty(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORDefinitionObjectProperty(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORDefinitionObjectProperty(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORDefinitionObjectProperty(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORDefinitionObjectProperty(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORDefinitionObjectProperty(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORObjectProperty.Disjoint.super.readSemantic();
        r.addAll( MORObjectProperty.Equivalent.super.readSemantic());
        r.addAll( MORObjectProperty.Inverse.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORObjectProperty.Disjoint.super.writeSemantic();
        r.addAll( MORObjectProperty.Equivalent.super.writeSemantic());
        r.addAll( MORObjectProperty.Inverse.super.writeSemantic());
        return r;
    }


    // implementations for MORObjectProperty.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORDefinitionObjectProperty getNewDisjointObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new MORDefinitionObjectProperty( instance, ontology);
    }

    @Override
    public MORAxioms.ObjectLinks getDisjointObjectProperty() {
        return disjointProperties;
    }



    // implementations for MORObjectProperty.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORDefinitionObjectProperty getNewEquivalentObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new MORDefinitionObjectProperty( instance, ontology);
    }

    @Override
    public MORAxioms.ObjectLinks getEquivalentObjectProperty() {
        return equivalentProperties;
    }



    // implementations for MORObjectProperty.Inverse

    @Override //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORDefinitionObjectProperty getNewInverseObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new MORDefinitionObjectProperty( instance, ontology);
    }

    @Override
    public MORAxioms.ObjectLinks getInverseObjectProperty() {
        return inverseProperties;
    }



    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground

    public String toString() {
        return "MORFullObjectProperty{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointProperties +
                "," + NL + "\t≡ " + equivalentProperties +
                "," + NL + "\t↔ " + inverseProperties +
                NL + "}";
    }

}
