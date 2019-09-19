package it.emarolab.owloop.descriptor.utility.classDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Classes;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Individuals;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Restrictions;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ClassExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ClassGround;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.LinkIndividualDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * This is an example of a 'compound' Class Descriptor that implements 4 ClassExpression (aka {@link ClassExpression}) interfaces:
 * <ul>
 * <li><b>{@link ClassExpression.Equivalent}</b>:  to describe that a Class is equivalent to another Class.</li>
 * <li><b>{@link ClassExpression.Disjoint}</b>:    to describe that a Class is disjoint to another Class.</li>
 * <li><b>{@link ClassExpression.Instance}</b>:    to describe an Individual of a Class.</li>
 * <li><b>{@link EquivalentRestriction}</b>:  to describe the EquivalentTo restrictions of a Class..</li>
 * </ul>
 *
 *  See {@link FullClassDesc} for an example of a 'compound' Class Descriptor that implements all ClassExpressions (aka {@link ClassExpression}).
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
public class RestrictionClassDesc
        extends ClassGround
        implements ClassExpression.EquivalentRestriction,
        ClassExpression.Disjoint<RestrictionClassDesc>,
        ClassExpression.Equivalent<RestrictionClassDesc>,
        ClassExpression.Instance<LinkIndividualDesc> {

    private Classes disjointClasses = new Classes();
    private Classes equivalentClasses = new Classes();
    private Restrictions restrictions = new Restrictions();
    private Individuals individuals = new Individuals();

    /* Constructors from class: ClassGround */
    
    public RestrictionClassDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public RestrictionClassDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public RestrictionClassDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public RestrictionClassDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public RestrictionClassDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public RestrictionClassDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public RestrictionClassDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public RestrictionClassDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    /* Overriding methods in class: ClassGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readAxioms() {
        List<MappingIntent> r = ClassExpression.Disjoint.super.readAxioms();
        r.addAll( ClassExpression.Equivalent.super.readAxioms());
        r.addAll( EquivalentRestriction.super.readAxioms());
        r.addAll( Instance.super.readAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeAxioms() {
        List<MappingIntent> r = ClassExpression.Disjoint.super.writeAxioms();
        r.addAll( ClassExpression.Equivalent.super.writeAxioms());
        r.addAll( EquivalentRestriction.super.writeAxioms());
        r.addAll( Instance.super.writeAxioms());
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
    public RestrictionClassDesc getDisjointClassDescriptor(OWLClass instance, OWLReferences ontology) {
        return new RestrictionClassDesc( instance, ontology);
    }
    // It returns disjointClasses from the EntitySet (after being read from the ontology)
    @Override
    public Classes getDisjointClasses() {
        return disjointClasses;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public RestrictionClassDesc getEquivalentClassDescriptor(OWLClass instance, OWLReferences ontology) {
        return new RestrictionClassDesc( instance, ontology);
    }
    // It returns equivalentClasses from the EntitySet (after being read from the ontology)
    @Override
    public Classes getEquivalentClasses() {
        return equivalentClasses;
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
                "}" + "\n";
    }
}