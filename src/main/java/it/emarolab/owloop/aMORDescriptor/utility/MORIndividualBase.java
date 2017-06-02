package it.emarolab.owloop.aMORDescriptor.utility;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

abstract public class MORIndividualBase
        extends MORBase<OWLNamedIndividual>
        implements MORIndividual{

    public MORIndividualBase(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }

    public MORIndividualBase(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }

    public MORIndividualBase(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }

    public MORIndividualBase(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }

    public MORIndividualBase(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }

    public MORIndividualBase(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }

    public MORIndividualBase(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }

    public MORIndividualBase(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
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
