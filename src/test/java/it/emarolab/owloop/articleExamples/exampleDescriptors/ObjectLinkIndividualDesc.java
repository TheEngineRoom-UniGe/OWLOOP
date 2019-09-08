package it.emarolab.owloop.articleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.ObjectLinkSet;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.ObjectLinks;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.FullObjectPropertyDesc;

import java.util.List;

/**
 * A 'simple' Individual Descriptor which implements 1 {@link IndividualExpression} interface:
 * <ul>
 * <li><b>{@link IndividualExpression.ObjectLink}</b>:   to describe an ObjectProperty and Individuals related via that ObjectProperty, for an Individual.</li>
 * </ul>
 * <p>
 *     Doing build() with this Descriptor returns another descriptor of type {@link FullObjectPropertyDesc}.
 * </p>
 * See {@link FullIndividualDesc} for an example of a 'compound' Individual Descriptor that implements all IndividualExpressions.
 *
 * <p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it), Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
 * </small></div>
 */
public class ObjectLinkIndividualDesc
        extends IndividualGround
        implements IndividualExpression.ObjectLink<FullObjectPropertyDesc> {

    private ObjectLinkSet objectLinks = new ObjectLinkSet();

    /* Constructors from class: IndividualGround */

    public ObjectLinkIndividualDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }

    /* Overriding methods in class: IndividualGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ObjectLink.super.readExpressionAxioms();
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ObjectLink.super.writeExpressionAxioms();
        return r;
    }

    /* Overriding methods in classes: Individual and IndividualExpression */


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

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t⊨ " + objectLinks + "\n" +
                "}" + "\n";
    }
}