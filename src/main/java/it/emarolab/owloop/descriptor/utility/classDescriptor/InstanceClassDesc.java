package it.emarolab.owloop.descriptor.utility.classDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Individuals;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ClassExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ClassGround;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.LinkIndividualDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * This is an example of a 'simple' Class Descriptor that implements 1 ClassExpression (aka {@link ClassExpression}) interface:
 * <ul>
 * <li><b>{@link ClassExpression.Instance}</b>:    to describe an Individual of a Class.</li>
 * </ul>
 *
 *  See {@link FullClassDesc} for an example of a 'compound' Class Descriptor that implements all ClassExpressions (aka {@link ClassExpression}).
 * <p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
 * </small></div>
 */
public class InstanceClassDesc
        extends ClassGround
        implements ClassExpression.Instance<LinkIndividualDesc> {

    private Individuals individuals = new Individuals();

    /* Constructors from class: ClassGround */

    public InstanceClassDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public InstanceClassDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public InstanceClassDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public InstanceClassDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public InstanceClassDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public InstanceClassDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public InstanceClassDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public InstanceClassDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    /* Overriding methods in class: ClassGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readAxioms() {
        return Instance.super.readAxioms();
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeAxioms() {
        return Instance.super.writeAxioms();
    }

    /* Overriding methods in classes: Class and ClassExpression */


    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public LinkIndividualDesc getIndividualDescriptor(OWLNamedIndividual instance, OWLReferences ontology) {
        return new LinkIndividualDesc( instance, ontology);
    }
    // It returns Individuals from the EntitySet (after being read from the ontology)
    @Override
    public Individuals getIndividuals() {
        return individuals;
    }

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t⇐ " + individuals + "\n" +
                "}" + "\n";
    }
}
