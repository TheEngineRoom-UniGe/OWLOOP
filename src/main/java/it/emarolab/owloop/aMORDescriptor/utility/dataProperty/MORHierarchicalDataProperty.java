package it.emarolab.owloop.aMORDescriptor.utility.dataProperty;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORDataProperty;
import it.emarolab.owloop.aMORDescriptor.utility.MORDataPropertyBase;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;

/**
 * A basic implementation for a data property with sub and super properties.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a data property that is synchronised w.r.t. its {@link Super} and {@link Sub} properties.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.DataLinks} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link MORDataPropertyBase} in order
 *     to automatically manage a grounding {@link DataInstance}.
 *     <br>
 *     You may want to use this class (see also {@link MORDefinitionDataProperty}
 *     and {@link MORDomainDataProperty} as well as other classes in the
 *     {@link it.emarolab.owloop.aMORDescriptor.utility} package) as templates to build a specific
 *     {@link MORDataProperty} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for data properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.dataProperty.MORHierarchicalDataProperty <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORHierarchicalDataProperty
        extends MORDataPropertyBase
        implements MORDataProperty.Sub<MORHierarchicalDataProperty>,
        MORDataProperty.Super<MORHierarchicalDataProperty>{

    private MORAxioms.DataLinks subProperties = new MORAxioms.DataLinks();
    private MORAxioms.DataLinks superProperties = new MORAxioms.DataLinks();

    // constructors for MORDataPropertyBase

    public MORHierarchicalDataProperty(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORHierarchicalDataProperty(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORHierarchicalDataProperty(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORHierarchicalDataProperty(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORHierarchicalDataProperty(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORHierarchicalDataProperty(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORHierarchicalDataProperty(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORHierarchicalDataProperty(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORDataProperty.Sub.super.readSemantic();
        r.addAll( MORDataProperty.Super.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORDataProperty.Sub.super.writeSemantic();
        r.addAll( MORDataProperty.Super.super.writeSemantic());
        return r;
    }



    // implementations for MORDataProperty.Super

    @Override //called during build...() you can change the returning type to any implementations of MORDataProperty
    public MORHierarchicalDataProperty getNewSubDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new MORHierarchicalDataProperty( instance, ontology);
    }

    @Override
    public MORAxioms.DataLinks getSubDataProperty() {
        return subProperties;
    }



    // implementations for MORDataProperty.Super

    @Override //called during build...() you can change the returning type to any implementations of MORDataProperty
    public MORHierarchicalDataProperty getNewSuperDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new MORHierarchicalDataProperty( instance, ontology);
    }

    @Override
    public MORAxioms.DataLinks getSuperDataProperty() {
        return superProperties;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground

    @Override
    public String toString() {
        return "MORFullObjectProperty{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊃ " + subProperties +
                "," + NL + "\t⊂ " + superProperties +
                NL + "}";
    }
}
