package it.emarolab.owloop.descriptor.utility.individualDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * This is an example of a 'compound' Individual Descriptor which implements 2 {@link IndividualExpression}s.
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

    // constructors for IndividualGround

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

    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.readExpressionAxioms();
        r.addAll( IndividualExpression.Disjoint.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.writeExpressionAxioms();
        r.addAll( IndividualExpression.Disjoint.super.writeExpressionAxioms());
        return r;
    }

    // implementations for IndividualExpression.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of IndividualExpression
    public DefinitionIndividualDesc getNewDisjointIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new DefinitionIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Individuals getDisjointIndividuals() {
        return disjointIndividuals;
    }

    // implementations for IndividualExpression.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of IndividualExpression
    public DefinitionIndividualDesc getNewEquivalentIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new DefinitionIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Individuals getEquivalentIndividuals() {
        return equivalentIndividuals;
    }

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointIndividuals +
                "," + NL + "\t≡ " + equivalentIndividuals +
                NL + "}";
    }
}
