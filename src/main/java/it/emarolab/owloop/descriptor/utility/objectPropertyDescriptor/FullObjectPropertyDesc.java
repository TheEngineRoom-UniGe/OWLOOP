package it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.ObjectProperties;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Restrictions;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ObjectPropertyGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;

/**
 * This is an example of a 'compound' ObjectProperty Descriptor as it implements more than one {@link ObjectPropertyExpression} interfaces.
 * Axioms in this descriptor's internal state (i.e., OWLOOP representation) can be synchronized to/from an OWL ontology.
 * {@link FullObjectPropertyDesc} can synchronize all the axioms, that are based on the following ObjectPropertyExpressions:
 *
 * <ul>
 * <li><b>{@link ObjectPropertyExpression.Equivalent}</b>:   to describe that an ObjectProperty is equivalent to another ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Disjoint}</b>:     to describe that an ObjectProperty is disjoint to another ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Sub}</b>:          to describe that an ObjectProperty subsumes another ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Super}</b>:        to describe that an ObjectProperty super-sumes another ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Domain}</b>:       to describe the domain restrictions of an ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Range}</b>:        to describe the range restrictions of an ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Inverse}</b>:      to describe that an ObjectProperty has another inverse ObjectProperty.</li>
 * </ul>
 *
 * <p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
 * </small></div>
 */
public class FullObjectPropertyDesc
        extends ObjectPropertyGround
        implements ObjectPropertyExpression.Disjoint<FullObjectPropertyDesc>,
        ObjectPropertyExpression.Equivalent<FullObjectPropertyDesc>,
        ObjectPropertyExpression.Inverse<FullObjectPropertyDesc>,
        ObjectPropertyExpression.Sub<FullObjectPropertyDesc>,
        ObjectPropertyExpression.Super<FullObjectPropertyDesc>,
        ObjectPropertyExpression.Domain,
        ObjectPropertyExpression.Range{

    private ObjectProperties disjointObjectProperties = new ObjectProperties();
    private ObjectProperties equivalentObjectProperties = new ObjectProperties();
    private ObjectProperties inverseObjectProperties = new ObjectProperties();
    private ObjectProperties subObjectProperties = new ObjectProperties();
    private ObjectProperties superObjectProperties = new ObjectProperties();
    private Restrictions domainRestrictions = new Restrictions();
    private Restrictions rangeRestrictions = new Restrictions();

    /* Constructors from class: ObjectPropertyGround */

    public FullObjectPropertyDesc(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public FullObjectPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public FullObjectPropertyDesc(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public FullObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public FullObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public FullObjectPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public FullObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public FullObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    /* Overriding methods in class: ObjectPropertyGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.readAxioms();
        r.addAll( ObjectPropertyExpression.Equivalent.super.readAxioms());
        r.addAll( ObjectPropertyExpression.Range.super.readAxioms());
        r.addAll( ObjectPropertyExpression.Domain.super.readAxioms());
        r.addAll( ObjectPropertyExpression.Sub.super.readAxioms());
        r.addAll( ObjectPropertyExpression.Super.super.readAxioms());
        r.addAll( ObjectPropertyExpression.Inverse.super.readAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Disjoint.super.writeAxioms();
        r.addAll( ObjectPropertyExpression.Equivalent.super.writeAxioms());
        r.addAll( ObjectPropertyExpression.Range.super.writeAxioms());
        r.addAll( ObjectPropertyExpression.Domain.super.writeAxioms());
        r.addAll( ObjectPropertyExpression.Sub.super.writeAxioms());
        r.addAll( ObjectPropertyExpression.Super.super.writeAxioms());
        r.addAll( ObjectPropertyExpression.Inverse.super.writeAxioms());
        return r;
    }

    /* Overriding methods in classes: ObjectProperty and ObjectPropertyExpression */


    // It returns domainRestrictions from the EntitySet (after being read from the ontology)
    @Override
    public Restrictions getDomainRestrictions() {
        return domainRestrictions;
    }

    // It returns rangeRestrictions from the EntitySet (after being read from the ontology)
    @Override
    public Restrictions getRangeRestrictions() {
        return rangeRestrictions;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullObjectPropertyDesc getNewDisjointObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance, ontology);
    }
    // It returns disjointObjectProperties from the EntitySet (after being read from the ontology)
    @Override
    public ObjectProperties getDisjointObjectProperties() {
        return disjointObjectProperties;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullObjectPropertyDesc getNewEquivalentObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance, ontology);
    }
    // It returns equivalentObjectProperties from the EntitySet (after being read from the ontology)
    @Override
    public ObjectProperties getEquivalentObjectProperties() {
        return equivalentObjectProperties;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullObjectPropertyDesc getNewSubObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance, ontology);
    }
    // It returns subObjectProperties from the EntitySet (after being read from the ontology)
    @Override
    public ObjectProperties getSubObjectProperties() {
        return subObjectProperties;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullObjectPropertyDesc getNewSuperObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance, ontology);
    }
    // It returns superObjectProperties from the EntitySet (after being read from the ontology)
    @Override
    public ObjectProperties getSuperObjectProperties() {
        return superObjectProperties;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullObjectPropertyDesc getNewInverseObjectProperty(OWLObjectProperty instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance, ontology);
    }
    // It returns inverseObjectProperties from the EntitySet (after being read from the ontology)
    @Override
    public ObjectProperties getInverseObjectProperties() {
        return inverseObjectProperties;
    }

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t≠ " +      disjointObjectProperties + "\n" +
                "\t\t≡ " +      equivalentObjectProperties + "\n" +
                "\t\t[≐,--] " + domainRestrictions + "\n" +
                "\t\t[--,≐] " + rangeRestrictions + "\n" +
                "\t\t⊃ " +      subObjectProperties + "\n" +
                "\t\t⊂ " +      superObjectProperties + "\n" +
                "\t\t↔ " +      inverseObjectProperties + "\n" +
                "}" + "\n";
    }
}