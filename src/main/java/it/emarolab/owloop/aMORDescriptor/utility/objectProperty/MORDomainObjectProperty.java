package it.emarolab.owloop.aMORDescriptor.utility.objectProperty;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORObjectProperty;
import it.emarolab.owloop.aMORDescriptor.utility.MORObjectPropertyBase;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;

/**
 * A basic implementation for an object property with domain and range restrictions.
 * <p>
 *     This is an example of how use the {@link Descriptor}s for implement
 *     an object property that is synchronised w.r.t. its {@link Domain} and {@link Range} restrictions.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.Restrictions} for the
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
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.objectProperty.MORDomainObjectProperty <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORDomainObjectProperty
        extends MORObjectPropertyBase
        implements MORObjectProperty.Domain, MORObjectProperty.Range {

    private MORAxioms.Restrictions domainRestriction = new MORAxioms.Restrictions();
    private MORAxioms.Restrictions rangeRestriction = new MORAxioms.Restrictions();


    // constructors for MORObjectPropertyBase

    public MORDomainObjectProperty(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORDomainObjectProperty(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORDomainObjectProperty(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORDomainObjectProperty(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORDomainObjectProperty(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORDomainObjectProperty(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORDomainObjectProperty(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORDomainObjectProperty(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORObjectProperty.Domain.super.readSemantic();
        r.addAll( MORObjectProperty.Range.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORObjectProperty.Domain.super.writeSemantic();
        r.addAll( MORObjectProperty.Range.super.writeSemantic());
        return r;
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



    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground

    public String toString() {
        return "MORFullObjectProperty{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t→ " + domainRestriction +
                "," + NL + "\t← " + rangeRestriction +
                NL + "}";
    }
}

