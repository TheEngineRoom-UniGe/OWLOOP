package it.emarolab.owloop.descriptor.construction.descriptorBase;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

abstract public class IndividualDescriptorBase
        extends DescriptorBase<OWLNamedIndividual>
        implements IndividualExpression {

    public IndividualDescriptorBase(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }

    public IndividualDescriptorBase(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }

    public IndividualDescriptorBase(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }

    public IndividualDescriptorBase(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }

    public IndividualDescriptorBase(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }

    public IndividualDescriptorBase(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }

    public IndividualDescriptorBase(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }

    public IndividualDescriptorBase(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    @Override
    protected GroundBase<OWLNamedIndividual> getNewGround(OWLReferences ontology, OWLNamedIndividual instance) {
        return new IndividualInstance(ontology, instance);
    }

    @Override
    protected GroundBase<OWLNamedIndividual> getNewGround(OWLReferences ontology, String instance) {
            return new IndividualInstance( ontology, ontology.getOWLIndividual( instance));
    }

    @Override
    public IndividualInstance getGround() {
        return (IndividualInstance) super.getGround();
    }

}
