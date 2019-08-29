package it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Restrictions;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DataPropertyGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;

/**
 * This is an example of a 'compound' DataProperty Descriptor which implements 2 {@link DataPropertyExpression} interfaces:
 *
 * <ul>
 * <li><b>{@link DataPropertyExpression.Domain}</b>:       to describe the domain restrictions of a DataProperty.</li>
 * <li><b>{@link DataPropertyExpression.Range}</b>:        to describe the range restrictions of a DataProperty.</li>
 * </ul>
 *
 * See {@link FullDataPropertyDesc} for an example of a 'compound' DataProperty Descriptor that implements all DataPropertyExpressions.
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
public class DomainRangeDataPropertyDesc
        extends DataPropertyGround
        implements DataPropertyExpression.Domain,
        DataPropertyExpression.Range {

    private Restrictions domainRestrictions = new Restrictions();
    private Restrictions rangeRestrictions = new Restrictions();

    /* Constructors from class: DataPropertyGround */

    public DomainRangeDataPropertyDesc(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DomainRangeDataPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DomainRangeDataPropertyDesc(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public DomainRangeDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DomainRangeDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DomainRangeDataPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DomainRangeDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DomainRangeDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    /* Overriding methods in class: DataPropertyGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Domain.super.readExpressionAxioms();
        r.addAll( DataPropertyExpression.Range.super.readExpressionAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = DataPropertyExpression.Range.super.writeExpressionAxioms();
        r.addAll( DataPropertyExpression.Domain.super.writeExpressionAxioms());
        return r;
    }

    /* Overriding methods in classes: DataProperty and DataPropertyExpression */


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

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t[≐,--] " + domainRestrictions + "\n" +
                "\t\t[--,≐] " + rangeRestrictions + "\n" +
                "}" + "\n";
    }
}

