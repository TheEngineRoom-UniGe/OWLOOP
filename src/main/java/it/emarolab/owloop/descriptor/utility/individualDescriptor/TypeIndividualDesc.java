package it.emarolab.owloop.descriptor.utility.individualDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.utility.conceptDescriptor.HierarchicalConceptDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * This is an example of a 'simple' Individual Descriptor which implements 1 {@link IndividualExpression}s.
 * <ul>
 * <li><b>{@link IndividualExpression.Type}</b>: to describe the Type/s (i.e., class/es) of an Individual.</li>
 * </ul>
 * See {@link FullIndividualDescriptor} for an example of a 'compound' Individual Descriptor that implements all IndividualExpressions.
 */
public class TypeIndividualDesc
        extends IndividualGround
        implements IndividualExpression.Type<HierarchicalConceptDesc>{

    private DescriptorEntitySet.Concepts individualTypes = new DescriptorEntitySet.Concepts();

    // constructors for IndividualGround

    public TypeIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public TypeIndividualDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public TypeIndividualDesc(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public TypeIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public TypeIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public TypeIndividualDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public TypeIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public TypeIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        return IndividualExpression.Type.super.readExpressionAxioms();
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        return IndividualExpression.Type.super.writeExpressionAxioms();
    }

    // implementations for IndividualExpression.Type

    @Override //called during build...() you can change the returning type to any implementations of ConceptExpression
    public HierarchicalConceptDesc getNewTypeIndividual(OWLClass instance, OWLReferences ontology) {
        return new HierarchicalConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getTypeIndividual() {
        return individualTypes;
    }

    @Override
    public String toString() {
        return "FullObjectPropertyDescriptor{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t ∈ " + individualTypes +
                NL + "}";
    }
}
