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
 * This is an example of a 'compound' Concept Descriptor that implements 1 ClassExpressions (aka {@link ConceptExpression}):
 * <ul>
 * <li><b>{@link ConceptExpression.Instance}</b>:    to describe an Individual of a Class.</li>
 * </ul>
 *
 *  See {@link FullConceptDescriptor} for an example of a 'compound' Concept Descriptor that implements all ClassExpressions (aka {@link ConceptExpression}).
 */
public class InstanceConceptDesc
        extends ConceptGround
        implements ConceptExpression.Instance<LinkIndividualDesc> {

    private DescriptorEntitySet.Individuals classifiedIndividual = new DescriptorEntitySet.Individuals();

    // constructors for ConceptGround

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





    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        return Instance.super.readExpressionAxioms();
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        return Instance.super.writeExpressionAxioms();
    }

    // implementations for ConceptExpression.Classifier


    @Override // you can change the returning type to any implementations of ConceptExpression
    public LinkIndividualDesc getNewIndividualInstance(OWLNamedIndividual instance, OWLReferences ontology) {
        return new LinkIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Individuals getIndividualInstance() {
        return classifiedIndividual;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorGround<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDescriptor{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⇐ " + classifiedIndividual +
                NL + "}";
    }
}
