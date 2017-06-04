package it.emarolab.owloop.aMORDescriptor.utility.dataProperty;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORDataProperty;
import it.emarolab.owloop.aMORDescriptor.utility.MORDataPropertyBase;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;
import java.util.Set;

/**
 * The implementation of all the semantic features of a data property.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a data property that is synchronised w.r.t. all interfaces of {@link MORDataProperty}.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.DataLinks}
 *     and {@link MORAxioms.Restrictions} for the
 *     respective descriptions, as well as call all interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link MORDataPropertyBase} in order
 *     to automatically manage a grounding {@link DataInstance}.
 *     <br>
 *     In order to optimise the synchronisation efficiency (i.e.: minimize
 *     reasoning calls) use this object only if it is necessary.
 *     Otherwise is recommended to use an had hoc {@link Descriptor}.
 *     You may want to use this class, see also {@link MORHierarchicalDataProperty},
 *     {@link MORDefinitionDataProperty} and {@link MORDomainDataProperty}
 *     (generally, all the classes in the {@link it.emarolab.owloop.aMORDescriptor.utility} package)
 *     as templates for doing that.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.dataProperty.MORFullDataProperty <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORFullDataProperty
        extends MORDataPropertyBase
        implements MORDataProperty.Disjoint<MORFullDataProperty>,
        MORDataProperty.Equivalent<MORFullDataProperty>,
        MORDataProperty.Sub<MORFullDataProperty>,
        MORDataProperty.Super<MORFullDataProperty>,
        MORDataProperty.Domain,
        MORDataProperty.Range{

    private MORAxioms.DataLinks disjointProperties = new MORAxioms.DataLinks();
    private MORAxioms.DataLinks equivalentProperties = new MORAxioms.DataLinks();
    private MORAxioms.Restrictions domainRestriction = new MORAxioms.Restrictions();
    private MORAxioms.Restrictions rangeRestriction = new MORAxioms.Restrictions();
    private MORAxioms.DataLinks subProperties = new MORAxioms.DataLinks();
    private MORAxioms.DataLinks superProperties = new MORAxioms.DataLinks();


    // constructors for MORDataPropertyBase

    public MORFullDataProperty(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORFullDataProperty(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORFullDataProperty(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORFullDataProperty(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORFullDataProperty(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORFullDataProperty(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORFullDataProperty(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORFullDataProperty(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }




    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORDataProperty.Disjoint.super.readSemantic();
        r.addAll( MORDataProperty.Equivalent.super.readSemantic());
        r.addAll( MORDataProperty.Range.super.readSemantic());
        r.addAll( MORDataProperty.Domain.super.readSemantic());
        r.addAll( MORDataProperty.Sub.super.readSemantic());
        r.addAll( MORDataProperty.Super.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORDataProperty.Disjoint.super.writeSemantic();
        r.addAll( MORDataProperty.Equivalent.super.writeSemantic());
        r.addAll( MORDataProperty.Range.super.writeSemantic());
        r.addAll( MORDataProperty.Domain.super.writeSemantic());
        r.addAll( MORDataProperty.Sub.super.writeSemantic());
        r.addAll( MORDataProperty.Super.super.writeSemantic());
        return r;
    }


    // implementations for MORDataProperty.Disjoint


    @Override // you can change the returning type to any implementations of MORDataProperty
    public MORFullDataProperty getNewDisjointDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new MORFullDataProperty( instance, ontology);
    }

    @Override
    public MORAxioms.DataLinks getDisjointDataProperty() {
        return disjointProperties;
    }



    // implementations for MORDataProperty.Equivalent

    @Override // returns a set with elements of the same type of getNewDisjointDataProperty()
    public Set<MORFullDataProperty> buildEquivalentDataProperty() {
        return MORDataProperty.Equivalent.super.buildEquivalentDataProperty();
    }

    @Override // you can change the returning type to any implementations of MORDataProperty
    public MORFullDataProperty getNewEquivalentDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new MORFullDataProperty( instance, ontology);
    }

    @Override
    public MORAxioms.DataLinks getEquivalentDataProperty() {
        return equivalentProperties;
    }




    // implementations for MORDataProperty.Domain
    @Override
    public MORAxioms.Restrictions getDomainDataProperty() {
        return domainRestriction;
    }



    // implementations for MORDataProperty.Range
    @Override
    public MORAxioms.Restrictions getRangeDataProperty() {
        return rangeRestriction;
    }




    // implementations for MORDataProperty.Super

    @Override //called during build...() you can change the returning type to any implementations of MORDataProperty
    public MORFullDataProperty getNewSubDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new MORFullDataProperty( instance, ontology);
    }

    @Override
    public MORAxioms.DataLinks getSubDataProperty() {
        return subProperties;
    }



    // implementations for MORDataProperty.Super

    @Override //called during build...() you can change the returning type to any implementations of MORDataProperty
    public MORFullDataProperty getNewSuperDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new MORFullDataProperty( instance, ontology);
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
                ":" + NL + "\t≠ " + disjointProperties +
                "," + NL + "\t≡ " + equivalentProperties +
                "," + NL + "\t→ " + domainRestriction +
                "," + NL + "\t← " + rangeRestriction +
                "," + NL + "\t⊃ " + subProperties +
                "," + NL + "\t⊂ " + superProperties +
                NL + "}";
    }
}
