package it.emarolab.owloop.descriptor.utility.conceptDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ConceptGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.LinkIndividualDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * This is an example of a 'simple' Concept Descriptor that implements 1 ClassExpressions (aka {@link ConceptExpression}):
 * <ul>
 * <li><b>{@link ConceptExpression.Instance}</b>:    to describe an Individual of a Class.</li>
 * </ul>
 *
 *  See {@link FullConceptDesc} for an example of a 'compound' Concept Descriptor that implements all ClassExpressions (aka {@link ConceptExpression}).
 */
public class InstanceConceptDesc
        extends ConceptGround
        implements ConceptExpression.Instance<LinkIndividualDesc> {

    private DescriptorEntitySet.Individuals classAssertedIndividuals = new DescriptorEntitySet.Individuals();

    // Constructors from class: ConceptGround

    public InstanceConceptDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public InstanceConceptDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public InstanceConceptDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public InstanceConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public InstanceConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public InstanceConceptDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public InstanceConceptDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public InstanceConceptDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // Overriding methods in classes: Concept and ConceptExpression


    // To read axioms from an ontology
    @Override
    public List<MappingIntent> readExpressionAxioms() {
        return Instance.super.readExpressionAxioms();
    }

    // To write axioms to an ontology
    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        return Instance.super.writeExpressionAxioms();
    }

    // Is used by the descriptors's build() method. It's possible to change the return type based on need.
    @Override
    public LinkIndividualDesc getIndividualDescriptor(OWLNamedIndividual instance, OWLReferences ontology) {
        return new LinkIndividualDesc( instance, ontology);
    }
    // It returns the Individuals from the EntitySet (after being read from the ontology)
    @Override
    public DescriptorEntitySet.Individuals getIndividualInstances() {
        return classAssertedIndividuals;
    }

    // Overriding method in class: Object


    // To print particularly useful info., if the object of this class is printed.
    @Override
    public String toString() {
        return "InstanceConceptDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⇐ " + classAssertedIndividuals +
                NL + "}";
    }
}
