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

    private DescriptorEntitySet.Restrictions conceptRestrictions = new DescriptorEntitySet.Restrictions();

    DefConceptDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);  // grounds the Concept having a 'name', with respect to an 'onto'
    }

    abstract protected SemanticRestriction.ApplyingPropertyRestriction getRestriction();

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

// todo: (i) rename entitySet objects properly (ii) rename the methods related to those variables properly (iii) modification in toString() (iv) fix spaces and comments.
// todo: Replace NL with \n everywhere
// todo: Replace other stuff with this.getClass().getSimpleName() everywhere