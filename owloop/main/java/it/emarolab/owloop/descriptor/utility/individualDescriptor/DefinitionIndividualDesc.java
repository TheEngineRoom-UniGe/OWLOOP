package it.emarolab.owloop.descriptor.utility.individualDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * This is an example of a 'compound' Individual Descriptor which implements 2 {@link IndividualExpression} interfaces:
 * <ul>
 * <li><b>{@link IndividualExpression.Equivalent}</b>:   to describe an Individual same-as another Individual.</li>
 * <li><b>{@link IndividualExpression.Disjoint}</b>:     to describe an Individual different from another Individual.</li>
 * </ul>
 * See {@link FullIndividualDesc} for an example of a 'compound' Individual Descriptor that implements all IndividualExpressions.
 */
public class DefinitionIndividualDesc
        extends IndividualGround
        implements IndividualExpression.Disjoint<DefinitionIndividualDesc>,
        IndividualExpression.Equivalent<DefinitionIndividualDesc>{

    private DescriptorEntitySet.Individuals disjointIndividuals = new DescriptorEntitySet.Individuals();
    private DescriptorEntitySet.Individuals equivalentIndividuals = new DescriptorEntitySet.Individuals();

    /* Constructors from class: IndividualGround */

    public DefinitionIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DefinitionIndividualDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DefinitionIndividualDesc(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public DefinitionIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DefinitionIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DefinitionIndividualDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DefinitionIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DefinitionIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    /* Overriding methods in class: IndividualGround */


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.readExpressionAxioms();
        r.addAll( IndividualExpression.Disjoint.super.readExpressionAxioms());
        return r;
    }
    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.writeExpressionAxioms();
        r.addAll( IndividualExpression.Disjoint.super.writeExpressionAxioms());
        return r;
    }

    /* Overriding methods in classes: Individual and IndividualExpression */


    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public DefinitionIndividualDesc getNewDisjointIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new DefinitionIndividualDesc( instance, ontology);
    }
    // It returns disjointIndividuals from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Individuals getDisjointIndividuals() {
        return disjointIndividuals;
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public DefinitionIndividualDesc getNewEquivalentIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new DefinitionIndividualDesc( instance, ontology);
    }
    // It returns equivalentIndividuals from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Individuals getEquivalentIndividuals() {
        return equivalentIndividuals;
    }

    /* Overriding method in class: Object */


    // To show internal state of the Descriptor
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t≠ " + disjointIndividuals + "\n" +
                "\t\t≡ " + equivalentIndividuals + "\n" +
                "}" + "\n";
    }
}
