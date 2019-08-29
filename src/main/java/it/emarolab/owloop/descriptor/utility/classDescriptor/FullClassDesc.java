package it.emarolab.owloop.descriptor.utility.classDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Classes;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Individuals;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Restrictions;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ClassExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ClassGround;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.LinkIndividualDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * This is an example of a 'compound' Class Descriptor as it implements more than one ClassExpression (aka {@link ClassExpression}) interfaces.
 * Axioms in this descriptor's internal state (i.e., OWLOOP representation) can be synchronized to/from an OWL ontology.
 * {@link FullClassDesc} can synchronize all the axioms, that are based on the following ClassExpressions:
 *
 * <ul>
 * <li><b>{@link ClassExpression.Equivalent}</b>:  to describe that a Class is equivalent to another Class.</li>
 * <li><b>{@link ClassExpression.Disjoint}</b>:    to describe that a Class is disjoint to another Class.</li>
 * <li><b>{@link ClassExpression.Sub}</b>:         to describe that a Class subsumes another Class.</li>
 * <li><b>{@link ClassExpression.Super}</b>:       to describe that a Class is a super-class of another Class.</li>
 * <li><b>{@link ClassExpression.Instance}</b>:    to describe an Individual of a Class.</li>
 * <li><b>{@link ClassExpression.EquivalentRestriction}</b>:  to describe the definition of a Class..</li>
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
public class FullClassDesc
        extends ClassGround
        implements ClassExpression.EquivalentRestriction,
        ClassExpression.Disjoint<FullClassDesc>,
        ClassExpression.Equivalent<FullClassDesc>,
        ClassExpression.Sub<FullClassDesc>,
        ClassExpression.Super<FullClassDesc>,
        ClassExpression.Instance<LinkIndividualDesc> {

    private Restrictions restrictions = new Restrictions();
    private Classes disjointClasses = new Classes();
    private Classes equivalentClasses = new Classes();
    private Classes subClasses = new Classes();
    private Classes superClasses = new Classes();
    private Individuals individuals = new Individuals();

    /* Constructors from class: ClassGround */

    public FullClassDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public FullClassDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public FullClassDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public FullClassDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public FullClassDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public FullClassDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public FullClassDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public FullClassDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    /* Overriding methods in class: ClassGround */


    public void clearAll() { // clear all internal state
        restrictions.clear();
        disjointClasses.clear();
        equivalentClasses.clear();
        subClasses.clear();
        superClasses.clear();
        individuals.clear();
    }

    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = EquivalentRestriction.super.readExpressionAxioms(); // call this before all
        r.addAll( ClassExpression.Equivalent.super.readExpressionAxioms());
        r.addAll( ClassExpression.Disjoint.super.readExpressionAxioms());
        r.addAll( ClassExpression.Sub.super.readExpressionAxioms());
        r.addAll( ClassExpression.Super.super.readExpressionAxioms());
        r.addAll( Instance.super.readExpressionAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ClassExpression.Super.super.writeExpressionAxioms();
        r.addAll( EquivalentRestriction.super.writeExpressionAxioms());// call this before all and after super
        r.addAll( ClassExpression.Equivalent.super.writeExpressionAxioms());
        r.addAll( ClassExpression.Disjoint.super.writeExpressionAxioms());
        r.addAll( ClassExpression.Sub.super.writeExpressionAxioms());
        r.addAll( Instance.super.writeExpressionAxioms());
        return r;
    }

    /* Overriding methods in classes: Class and ClassExpression */


    // It returns restrictions from the EntitySet (after being read from the ontology)
    @Override
    public Restrictions getEquivalentRestrictions() {
        return restrictions;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullClassDesc getDisjointClassDescriptor(OWLClass instance, OWLReferences ontology) {
        return new FullClassDesc( instance, ontology);
    }
    // It returns disjointClasses from the EntitySet (after being read from the ontology)
    @Override
    public Classes getDisjointClasses() {
        return disjointClasses;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullClassDesc getEquivalentClassDescriptor(OWLClass instance, OWLReferences ontology) {
        return new FullClassDesc( instance, ontology);
    }
    // It returns equivalentClasses from the EntitySet (after being read from the ontology)
    @Override
    public Classes getEquivalentClasses() {
        return equivalentClasses;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullClassDesc getSubClassDescriptor(OWLClass instance, OWLReferences ontology) {
        return new FullClassDesc( instance, ontology);
    }
    // It returns subClasses from the EntitySet (after being read from the ontology)
    @Override
    public Classes getSubClasses() {
        return subClasses;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public FullClassDesc getSuperClassDescriptor(OWLClass instance, OWLReferences ontology) {
        return new FullClassDesc( instance, ontology);
    }
    // It returns superClasses from the EntitySet (after being read from the ontology)
    @Override
    public Classes getSuperClasses() {
        return superClasses;
    }

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
                "\t\t≠ " + disjointClasses + "\n" +
                "\t\t≡ " + equivalentClasses + "\n" +
                "\t\t⇐ " + individuals + "\n" +
                "\t\t≐ " + restrictions + "\n" +
                "\t\t⊃ " + subClasses + "\n" +
                "\t\t⊂ " + superClasses + "\n" +
                "}" + "\n";
    }

}
