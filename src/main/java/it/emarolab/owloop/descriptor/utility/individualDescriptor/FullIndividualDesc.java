package it.emarolab.owloop.descriptor.utility.individualDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.*;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.utility.classDescriptor.FullClassDesc;
import it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor.FullDataPropertyDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.FullObjectPropertyDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * This is an example of a 'compound' Individual Descriptor as it implements more than one {@link IndividualExpression} interfaces.
 * Axioms in this descriptor's internal state (i.e., OWLOOP representation) can be synchronized to/from an OWL ontology.
 * {@link FullIndividualDesc} can synchronize all the axioms, that are based on the following IndividualExpressions:
 *
 * <ul>
 * <li><b>{@link IndividualExpression.Equivalent}</b>:   to describe an Individual same-as another Individual.</li>
 * <li><b>{@link IndividualExpression.Disjoint}</b>:     to describe an Individual different from another Individual.</li>
 * <li><b>{@link IndividualExpression.Type}</b>:         to describe the Type/s (i.e., class/es) of an Individual.</li>
 * <li><b>{@link IndividualExpression.ObjectLink}</b>:   to describe an ObjectProperty and Individuals related via that ObjectProperty, for an Individual.</li>
 * <li><b>{@link IndividualExpression.DataLink}</b>:     to describe an DataProperty and Individuals related via that DataProperty, for an Individual.</li>
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
public class FullIndividualDesc
        extends IndividualGround
        implements IndividualExpression.Disjoint<FullIndividualDesc>,
        IndividualExpression.Equivalent<FullIndividualDesc>,
        IndividualExpression.Type<FullClassDesc>,
        IndividualExpression.ObjectLink<FullObjectPropertyDesc>,
        IndividualExpression.DataLink<FullDataPropertyDesc> {

    private Individuals disjointIndividuals = new Individuals();
    private Individuals equivalentIndividuals = new Individuals();
    private Classes classes = new Classes();
    private ObjectLinkSet objectLinks = new ObjectLinkSet();
    private DataLinkSet dataLinks = new DataLinkSet();

    /* Constructors from class: IndividualGround */

    public FullIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public FullIndividualDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public FullIndividualDesc(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public FullIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public FullIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public FullIndividualDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public FullIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public FullIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    /* Overriding methods in class: IndividualGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readAxioms() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.readAxioms();
        r.addAll( IndividualExpression.Disjoint.super.readAxioms());
        r.addAll( IndividualExpression.Type.super.readAxioms());
        r.addAll( IndividualExpression.ObjectLink.super.readAxioms());
        r.addAll( IndividualExpression.DataLink.super.readAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeAxioms() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.writeAxioms();
        r.addAll( IndividualExpression.Disjoint.super.writeAxioms());
        r.addAll( IndividualExpression.Type.super.writeAxioms());
        r.addAll( IndividualExpression.ObjectLink.super.writeAxioms());
        r.addAll( IndividualExpression.DataLink.super.writeAxioms());
        return r;
    }

    /* Overriding methods in classes: Individual and IndividualExpression */


    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullIndividualDesc getNewDisjointIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new FullIndividualDesc( instance, ontology);
    }
    // It returns disjointIndividuals from the EntitySet (after being read from the ontology)
    @Override
    public Individuals getDisjointIndividuals() {
        return disjointIndividuals;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullIndividualDesc getNewEquivalentIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new FullIndividualDesc( instance, ontology);
    }
    // It returns equivalentIndividuals from the EntitySet (after being read from the ontology)
    @Override
    public Individuals getEquivalentIndividuals() {
        return equivalentIndividuals;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullClassDesc getNewType(OWLClass instance, OWLReferences ontology) {
        return new FullClassDesc( instance, ontology);
    }
    // It returns classes from the EntitySet (after being read from the ontology)
    @Override
    public Classes getTypes() {
        return classes;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullObjectPropertyDesc getNewObjectProperty(ObjectLinks instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance.getExpression(), ontology);
    }
    // It returns objectLinks from the EntitySet (after being read from the ontology)
    @Override
    public ObjectLinkSet getObjectProperties() {
        return objectLinks;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullDataPropertyDesc getNewDataProperty(DataLinks instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance.getExpression(), ontology);
    }
    // It returns dataLinks from the EntitySet (after being read from the ontology)
    @Override
    public DataLinkSet getDataProperties() {
        return dataLinks;
    }

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t≠ " + disjointIndividuals + "\n" +
                "\t\t≡ " + equivalentIndividuals + "\n" +
                "\t\t∈ " + classes + "\n" +
                "\t\t⊨ " + objectLinks + "\n" +
                "\t\t⊢ " + dataLinks + "\n" +
                "}" + "\n";
    }
}