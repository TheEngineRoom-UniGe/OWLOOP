package it.emarolab.owloop.descriptor.utility.individualDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
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

    private DescriptorEntitySet.Individuals disjointIndividuals = new DescriptorEntitySet.Individuals();
    private DescriptorEntitySet.Individuals equivalentIndividuals = new DescriptorEntitySet.Individuals();
    private DescriptorEntitySet.Classes classes = new DescriptorEntitySet.Classes();
    private DescriptorEntitySet.ObjectLinkSet objectLinks = new DescriptorEntitySet.ObjectLinkSet();
    private DescriptorEntitySet.DataLinkSet dataLinks = new DescriptorEntitySet.DataLinkSet();

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
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.readExpressionAxioms();
        r.addAll( IndividualExpression.Disjoint.super.readExpressionAxioms());
        r.addAll( IndividualExpression.Type.super.readExpressionAxioms());
        r.addAll( IndividualExpression.ObjectLink.super.readExpressionAxioms());
        r.addAll( IndividualExpression.DataLink.super.readExpressionAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.writeExpressionAxioms();
        r.addAll( IndividualExpression.Disjoint.super.writeExpressionAxioms());
        r.addAll( IndividualExpression.Type.super.writeExpressionAxioms());
        r.addAll( IndividualExpression.ObjectLink.super.writeExpressionAxioms());
        r.addAll( IndividualExpression.DataLink.super.writeExpressionAxioms());
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
    public DescriptorEntitySet.Individuals getDisjointIndividuals() {
        return disjointIndividuals;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullIndividualDesc getNewEquivalentIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new FullIndividualDesc( instance, ontology);
    }
    // It returns equivalentIndividuals from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Individuals getEquivalentIndividuals() {
        return equivalentIndividuals;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullClassDesc getNewType(OWLClass instance, OWLReferences ontology) {
        return new FullClassDesc( instance, ontology);
    }
    // It returns classes from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Classes getTypes() {
        return classes;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullObjectPropertyDesc getNewObjectProperty(DescriptorEntitySet.ObjectLinks instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance.getExpression(), ontology);
    }
    // It returns objectLinks from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.ObjectLinkSet getObjectProperties() {
        return objectLinks;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullDataPropertyDesc getNewDataProperty(DescriptorEntitySet.DataLinks instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance.getExpression(), ontology);
    }
    // It returns dataLinks from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.DataLinkSet getDataProperties() {
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