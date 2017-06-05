package it.emarolab.owloop.aMORDescriptor.utility.objectProperty;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORObjectProperty;
import it.emarolab.owloop.aMORDescriptor.utility.MORObjectPropertyBase;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;

/**
 * The implementation of all the semantic features of an object property.
 * <p>
 *     This is an example of how use the {@link Descriptor}s for implement
 *     an object property that is synchronised w.r.t. all interfaces of {@link MORObjectProperty}.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.ObjectLinks}
 *     and {@link MORAxioms.Restrictions} for the
 *     respective descriptions, as well as call all interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link MORObjectPropertyBase} in order
 *     to automatically manage an {@link ObjectInstance} ground.
 *     <br>
 *     In order to optimise the synchronissation efficiency (i.e.: minimize
 *     reasoning calls) use this object only if it is necessary.
 *     Otherwise is recommended to use an had hoc {@link Descriptor}.
 *     You may want to use this class, see also {@link MORHierarchicalObjectProperty},
 *     {@link MORDefinitionObjectProperty} and {@link MORDomainObjectProperty} 
 *     (generally, all the classes in the {@link it.emarolab.owloop.aMORDescriptor.utility} package) 
 *     as templates for doing that.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.objectProperty.MORFullObjectProperty <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORFullObjectProperty
        extends MORObjectPropertyBase
        implements MORObjectProperty.Disjoint<MORFullObjectProperty>,
        MORObjectProperty.Equivalent<MORFullObjectProperty>,
        MORObjectProperty.Inverse<MORFullObjectProperty>,
        MORObjectProperty.Sub<MORFullObjectProperty>,
        MORObjectProperty.Super<MORFullObjectProperty>,
        MORObjectProperty.Domain,
        MORObjectProperty.Range{

    private MORAxioms.ObjectLinks disjointProperties = new MORAxioms.ObjectLinks();
    private MORAxioms.ObjectLinks equivalentProperties = new MORAxioms.ObjectLinks();
    private MORAxioms.Restrictions domainRestriction = new MORAxioms.Restrictions();
    private MORAxioms.Restrictions rangeRestriction = new MORAxioms.Restrictions();
    private MORAxioms.ObjectLinks subProperties = new MORAxioms.ObjectLinks();
    private MORAxioms.ObjectLinks superProperties = new MORAxioms.ObjectLinks();
    private MORAxioms.ObjectLinks inverseProperties = new MORAxioms.ObjectLinks();


    // constructors for MORDataPropertyBase

    public MORFullObjectProperty(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORFullObjectProperty(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORFullObjectProperty(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORFullObjectProperty(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORFullObjectProperty(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORFullObjectProperty(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORFullObjectProperty(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORFullObjectProperty(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }




    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORObjectProperty.Disjoint.super.readSemantic();
        r.addAll( MORObjectProperty.Equivalent.super.readSemantic());
        r.addAll( MORObjectProperty.Range.super.readSemantic());
        r.addAll( MORObjectProperty.Domain.super.readSemantic());
        r.addAll( MORObjectProperty.Sub.super.readSemantic());
        r.addAll( MORObjectProperty.Super.super.readSemantic());
        r.addAll( MORObjectProperty.Inverse.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORObjectProperty.Disjoint.super.writeSemantic();
        r.addAll( MORObjectProperty.Equivalent.super.writeSemantic());
        r.addAll( MORObjectProperty.Range.super.writeSemantic());
        r.addAll( MORObjectProperty.Domain.super.writeSemantic());
        r.addAll( MORObjectProperty.Sub.super.writeSemantic());
        r.addAll( MORObjectProperty.Super.super.writeSemantic());
        r.addAll( MORObjectProperty.Inverse.super.writeSemantic());
        return r;
    }


    // implementations for MORObjectProperty.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORFullObjectProperty getNewDisjointObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new MORFullObjectProperty( instance, ontology);
    }

    @Override
    public MORAxioms.ObjectLinks getDisjointObjectProperty() {
        return disjointProperties;
    }



    // implementations for MORObjectProperty.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORFullObjectProperty getNewEquivalentObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new MORFullObjectProperty( instance, ontology);
    }

    @Override
    public MORAxioms.ObjectLinks getEquivalentObjectProperty() {
        return equivalentProperties;
    }




    // implementations for MORObjectProperty.Domain
    @Override
    public MORAxioms.Restrictions getDomainObjectProperty() {
        return domainRestriction;
    }



    // implementations for MORObjectProperty.Range
    @Override
    public MORAxioms.Restrictions getRangeObjectProperty() {
        return rangeRestriction;
    }




    // implementations for MORObjectProperty.Sub

    @Override //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORFullObjectProperty getNewSubObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new MORFullObjectProperty( instance, ontology);
    }

    @Override
    public MORAxioms.ObjectLinks getSubObjectProperty() {
        return subProperties;
    }



    // implementations for MORObjectProperty.Super

    @Override //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORFullObjectProperty getNewSuperObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new MORFullObjectProperty( instance, ontology);
    }

    @Override
    public MORAxioms.ObjectLinks getSuperObjectProperty() {
        return superProperties;
    }




    // implementations for MORObjectProperty.Inverse

    @Override //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORFullObjectProperty getNewInverseObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new MORFullObjectProperty( instance, ontology);
    }

    @Override
    public MORAxioms.ObjectLinks getInverseObjectProperty() {
        return inverseProperties;
    }



    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground

    @Override
    public String toString() {
        return "MORFullObjectProperty{" +
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
