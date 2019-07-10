package it.emarolab.owloop.articleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.descriptor.utility.conceptDescriptor.FullConceptDesc;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ConceptGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;

import java.util.List;

/**
 * A 'simple' Concept Descriptor that implements 1 ClassExpression (aka {@link ConceptExpression}) interface:
 * <ul>
 * <li><b>{@link ConceptExpression.Definition}</b>:  to describe the definition of a Class..</li>
 * </ul>
 *
 *  See {@link FullConceptDesc} for an example of a 'compound' Concept Descriptor that implements all ClassExpressions (aka {@link ConceptExpression}).
 */
public abstract class DefConceptDesc
        extends ConceptGround
        implements ConceptExpression.Definition {

    private DescriptorEntitySet.Restrictions conceptRestrictions = new DescriptorEntitySet.Restrictions();

    /* Constructors from class: ConceptGround */

    DefConceptDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);  // grounds the Concept having a 'name', with respect to an 'onto'
    }

    /* Overriding methods in class: ConceptGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = Definition.super.readExpressionAxioms(); // call this before Sub or Super !!!
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = Definition.super.writeExpressionAxioms(); // call this before Sub or Super !!!
        return r;
    }

    // To get a restriction to be added to the definition
    abstract protected SemanticRestriction.ApplyingPropertyRestriction getRestriction();

    /* Overriding methods in classes: Concept and ConceptExpression */


    // It returns conceptRestrictions from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Restrictions getDefinitionConcepts() {
        return conceptRestrictions;
    }

    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t≐ " + conceptRestrictions + "\n" +
                "}" + "\n";
    }
}