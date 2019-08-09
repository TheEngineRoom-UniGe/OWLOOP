package it.emarolab.owloop.descriptor.utility.conceptDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ClassExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ClassGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.List;


/**
 * This is an example of a 'compound' Class Descriptor that implements 2 ClassExpression (aka {@link ClassExpression}) interfaces:
 * <ul>
 * <li><b>{@link ClassExpression.Sub}</b>:         to describe that a Class subsumes another Class.</li>
 * <li><b>{@link ClassExpression.Super}</b>:       to describe that a Class is a super-class of another Class.</li>
 * </ul>
 *
 * See {@link FullClassDesc} for an example of a 'compound' Class Descriptor that implements all ClassExpressions (aka {@link ClassExpression}).
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
public class HierarchicalClassDesc
        extends ClassGround
        implements ClassExpression.Sub<HierarchicalClassDesc>,
        ClassExpression.Super<HierarchicalClassDesc>{

    private DescriptorEntitySet.Classes subClasses = new DescriptorEntitySet.Classes();
    private DescriptorEntitySet.Classes superClasses = new DescriptorEntitySet.Classes();

    /* Constructors from class: ClassGround */

    public HierarchicalClassDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public HierarchicalClassDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public HierarchicalClassDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public HierarchicalClassDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public HierarchicalClassDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public HierarchicalClassDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public HierarchicalClassDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public HierarchicalClassDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    /* Overriding methods in class: ClassGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ClassExpression.Sub.super.readExpressionAxioms();
        r.addAll( ClassExpression.Super.super.readExpressionAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ClassExpression.Sub.super.writeExpressionAxioms();
        r.addAll( ClassExpression.Super.super.writeExpressionAxioms());
        return r;
    }

    /* Overriding methods in classes: Class and ClassExpression */


    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public HierarchicalClassDesc getSubClassDescriptor(OWLClass instance, OWLReferences ontology) {
        return new HierarchicalClassDesc( instance, ontology);
    }
    // It returns subClasses from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Classes getSubClasses() {
        return subClasses;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public HierarchicalClassDesc getSuperClassDescriptor(OWLClass instance, OWLReferences ontology) {
        return new HierarchicalClassDesc( instance, ontology);
    }
    // It returns superClasses from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Classes getSuperClasses() {
        return superClasses;
    }

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t⊃ " + subClasses + "\n" +
                "\t\t⊂ " + superClasses + "\n" +
                "}" + "\n";
    }
}