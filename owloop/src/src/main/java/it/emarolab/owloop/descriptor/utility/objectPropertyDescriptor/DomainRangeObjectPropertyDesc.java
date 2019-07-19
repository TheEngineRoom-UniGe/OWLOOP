package it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ObjectPropertyGround;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.List;

/**
 * This is an example of a 'compound' ObjectProperty Descriptor which implements 2 {@link ObjectPropertyExpression} interfaces:
 * <ul>
 * <li><b>{@link ObjectPropertyExpression.Domain}</b>:       to describe the domain restrictions of an ObjectProperty.</li>
 * <li><b>{@link ObjectPropertyExpression.Range}</b>:        to describe the range restrictions of an ObjectProperty.</li>
 * </ul>
 * See {@link FullObjectPropertyDesc} for an example of a 'compound' Individual Descriptor that implements all ObjectPropertyExpressions.
 */
public class DomainRangeObjectPropertyDesc
        extends ObjectPropertyGround
        implements ObjectPropertyExpression.Domain,
        ObjectPropertyExpression.Range {

    private DescriptorEntitySet.Restrictions domainConceptRestrictions = new DescriptorEntitySet.Restrictions();
    private DescriptorEntitySet.Restrictions rangeConceptRestrictions = new DescriptorEntitySet.Restrictions();

    /* Constructors from class: ObjectPropertyGround */

    public DomainRangeObjectPropertyDesc(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DomainRangeObjectPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DomainRangeObjectPropertyDesc(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public DomainRangeObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DomainRangeObjectPropertyDesc(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DomainRangeObjectPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DomainRangeObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DomainRangeObjectPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    /* Overriding methods in class: ObjectPropertyGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Domain.super.readExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Range.super.readExpressionAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ObjectPropertyExpression.Domain.super.writeExpressionAxioms();
        r.addAll( ObjectPropertyExpression.Range.super.writeExpressionAxioms());
        return r;
    }

    /* Overriding methods in classes: ObjectProperty and ObjectPropertyExpression */


    // It returns domainConceptRestrictions from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Restrictions getObjectPropertyDomainConcepts() {
        return domainConceptRestrictions;
    }

    // It returns rangeConceptRestrictions from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Restrictions getObjectPropertyRangeConcepts() {
        return rangeConceptRestrictions;
    }

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t[≐,--] " + domainConceptRestrictions + "\n" +
                "\t\t[--,≐] " + rangeConceptRestrictions + "\n" +
                "}" + "\n";
    }
}

