package it.emarolab.owloop.articleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Classes;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Restrictions;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ClassExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ClassGround;
import it.emarolab.owloop.descriptor.utility.classDescriptor.FullClassDesc;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.List;

/**
 * This is an example of a 'compound' Class Descriptor which implements 2 {@link ClassExpression} interfaces:
 * <ul>
 * <li><b>{@link EquivalentRestriction}</b>:  to describe the definition of a Class..</li>
 * <li><b>{@link ClassExpression.Sub}</b>:         to describe that a Class subsumes another Class.</li>
 * </ul>
 * <p>
 *     Doing build() with this Descriptor returns the descriptor of type {@link DefSubClassDesc}.
 * </p>
 * See {@link FullClassDesc} for an example of a 'compound' Class Descriptor that implements all ConceptExpressions.
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
public class DefSubClassDesc
        extends ClassGround
        implements ClassExpression.EquivalentRestriction,
        ClassExpression.Sub<DefSubClassDesc>{

    private Restrictions restrictions = new Restrictions();
    private Classes subClasses = new Classes();

    // constructors for ClassGround
    public DefSubClassDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }

    // implementations for Axiom.descriptor
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = EquivalentRestriction.super.readExpressionAxioms(); // call this before Sub or Super !!!
        r.addAll( Sub.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = Sub.super.writeExpressionAxioms();
        r.addAll( EquivalentRestriction.super.writeExpressionAxioms()); // call this before Sub or Super !!!
        return r;
    }

    // implementations for ClassExpression.Definition
    @Override
    public Restrictions getEquivalentRestrictions() {
        return restrictions;
    }

    // you cannot build Definition (based on Restrictions)


    // implementations for ClassExpression.Super
    @Override // called during build...() you can change the returning type to any implementations of ClassExpression
    public DefSubClassDesc getSubClassDescriptor(OWLClass instance, OWLReferences ontology) {
        return new DefSubClassDesc( instance, ontology);
    }

    @Override
    public Classes getSubClasses() {
        return subClasses;
    }


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t≐ " + restrictions + "\n" +
                "\t\t⊃ " + subClasses + "\n" +
                "}" + "\n";
    }
}