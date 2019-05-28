package it.emarolab.owloopArticleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.descriptor.utility.conceptDescriptor.FullConceptDesc;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ConceptGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;

/**
 * A 'simple' Concept Descriptor that implements 1 ClassExpression (aka {@link ConceptExpression}):
 * <ul>
 * <li><b>{@link ConceptExpression.Definition}</b>:  to describe the definition of a Class..</li>
 * </ul>
 *
 *  See {@link FullConceptDesc} for an example of a 'compound' Concept Descriptor that implements all ClassExpressions (aka {@link ConceptExpression}).
 */
public abstract class DefConceptDesc
        extends ConceptGround
        implements ConceptExpression.Definition {

    private DescriptorEntitySet.Restrictions newDefRestrictions = new DescriptorEntitySet.Restrictions();

    protected DefConceptDesc(String instanceName, OWLReferences onto) { // constructor called from derived classes
        super(instanceName, onto);                                      // grounds the Concept having a 'name', with respect to an 'onto'
        addDefinitionRestriction( getRestriction());                    // adds definition with a restriction
    }

    // To make the warning go away, add the types explicitly:
    void addDefinitionRestriction( SemanticRestriction.ApplyingPropertyRestriction defRestrictions){
        defRestrictions.setSubject( getGround().getGroundInstance());
        defRestrictions.setProperty( getOWLObjectProperty( "hasDoor"));
        defRestrictions.setValue( getOWLClass( "DOOR"));
        getDefinitionConcepts().add( defRestrictions);
        writeExpressionAxioms();
    }

    abstract protected SemanticRestriction.ApplyingPropertyRestriction getRestriction();

    // inherit read/write Expression Axioms from the only simple descriptor `ConceptExpression.Definition`

    @Override
    public DescriptorEntitySet.Restrictions getDefinitionConcepts() {
        return newDefRestrictions;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()  + "{" + NL + "\t" + getGround() + NL + "\t= " + newDefRestrictions + NL + "}";
    }
}

// todo: (i) rename entitySet objects properly (ii) rename the methods related to those variables properly (iii) modification in toString() (iv) fix spaces and comments.