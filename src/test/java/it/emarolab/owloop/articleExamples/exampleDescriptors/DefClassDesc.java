package it.emarolab.owloop.articleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Restrictions;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ClassExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ClassGround;
import it.emarolab.owloop.descriptor.utility.classDescriptor.FullClassDesc;

import java.util.List;

/**
 * A 'simple' Class Descriptor that implements 1 ClassExpression (aka {@link ClassExpression}) interface:
 * <ul>
 * <li><b>{@link EquivalentRestriction}</b>:  to describe the definition of a Class..</li>
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
public abstract class DefClassDesc
        extends ClassGround
        implements ClassExpression.EquivalentRestriction {

    private Restrictions restrictions = new Restrictions();

    /* Constructors from class: ClassGround */

    DefClassDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);  // grounds the Class having a 'name', with respect to an 'onto'
    }

    /* Overriding methods in class: ClassGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readAxioms() {
        List<MappingIntent> r = EquivalentRestriction.super.readAxioms(); // call this before Sub or Super !!!
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeAxioms() {
        List<MappingIntent> r = EquivalentRestriction.super.writeAxioms(); // call this before Sub or Super !!!
        return r;
    }

    // To get a restriction to be added to the definition
    abstract protected SemanticRestriction.ApplyingPropertyRestriction getRestriction();

    /* Overriding methods in classes: Class and ClassExpression */


    // It returns restrictions from the EntitySet (after being read from the ontology)
    @Override
    public Restrictions getEquivalentRestrictions() {
        return restrictions;
    }

    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t≐ " + restrictions + "\n" +
                "}" + "\n";
    }
}