package it.emarolab.owloopArticleExamples.exampleDescriptors;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * A 'simple' Individual Descriptor which implements 1 {@link IndividualExpression}s.
 * <ul>
 * <li><b>{@link IndividualExpression.Type}</b>:         to describe the Type/s (i.e., class/es) of an Individual.</li>
 * </ul>
 * <p>
 *     Doing build() with this Descriptor returns another descriptor of type {@link DefSubConceptDesc}.
 * </p>
 *
 * See {@link FullIndividualDesc} for an example of a 'compound' Individual Descriptor that implements all IndividualExpressions.
 */
public class TypeIndividualDesc
        extends IndividualGround
        implements IndividualExpression.Type<DefSubConceptDesc> {

    private DescriptorEntitySet.Concepts concepts = new DescriptorEntitySet.Concepts();

    // constructors for IndividualGround
    public TypeIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }

    // implementations for Axiom.descriptor
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = Type.super.readExpressionAxioms();
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = Type.super.writeExpressionAxioms();
        return r;
    }


    // implementations for IndividualExpression.Type
    @Override //called during build...() you can change the returning type to any implementations of ConceptExpression
    public DefSubConceptDesc getNewIndividualType(OWLClass instance, OWLReferences ontology) {
        return new DefSubConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getIndividualTypes() {
        return concepts;
    }


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t∈ " + concepts + "\n" +
                "}" + "\n";
    }
}

// todo: (i) rename entitySet objects properly (ii) rename the methods related to those variables properly (iii) modification in toString() (iv) fix spaces and comments.