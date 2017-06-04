package it.emarolab.owloop.aMORDescriptor.utility.dataProperty;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORDataProperty;
import it.emarolab.owloop.aMORDescriptor.utility.MORDataPropertyBase;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;


/**
 * A basic implementation for a data property with equivalent and disjoint properties.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a data property that is synchronised w.r.t. its {@link Disjoint} and {@link Equivalent} properties.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.DataLinks} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link MORDataPropertyBase} in order
 *     to automatically manage a grounding {@link DataInstance}.
 *     <br>
 *     You may want to use this class (see also {@link MORHierarchicalDataProperty}
 *     and {@link MORDomainDataProperty}, as well as other classes in the
 *     {@link it.emarolab.owloop.aMORDescriptor.utility} package) as templates to build a specific
 *     {@link MORDataProperty} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for data properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.dataProperty.MORDefinitionDataProperty <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORDefinitionDataProperty
        extends MORDataPropertyBase
        implements MORDataProperty.Disjoint<MORDefinitionDataProperty>,
        MORDataProperty.Equivalent<MORDefinitionDataProperty> {

    private MORAxioms.DataLinks disjointProperties = new MORAxioms.DataLinks();
    private MORAxioms.DataLinks equivalentProperties = new MORAxioms.DataLinks();


    // constructors for MORDataPropertyBase

    public MORDefinitionDataProperty(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORDefinitionDataProperty(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORDefinitionDataProperty(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORDefinitionDataProperty(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORDefinitionDataProperty(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORDefinitionDataProperty(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORDefinitionDataProperty(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORDefinitionDataProperty(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORDataProperty.Disjoint.super.readSemantic();
        r.addAll( MORDataProperty.Equivalent.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORDataProperty.Disjoint.super.writeSemantic();
        r.addAll( MORDataProperty.Equivalent.super.writeSemantic());
        return r;
    }


    // implementations for MORDataProperty.Disjoint

    @Override  // called during build...()you can change the returning type to any implementations of MORDataProperty
    public MORDefinitionDataProperty getNewDisjointDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new MORDefinitionDataProperty( instance, ontology);
    }

    @Override
    public MORAxioms.DataLinks getDisjointDataProperty() {
        return disjointProperties;
    }



    // implementations for MORDataProperty.Equivalent

    @Override // called during build...() you can change the returning type to any implementations of MORDataProperty
    public MORDefinitionDataProperty getNewEquivalentDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new MORDefinitionDataProperty( instance, ontology);
    }

    @Override
    public MORAxioms.DataLinks getEquivalentDataProperty() {
        return equivalentProperties;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground

    @Override
    public String toString() {
        return "MORFullObjectProperty{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointProperties +
                "," + NL + "\t≡ " + equivalentProperties +
                NL + "}";
    }
}
