package it.emarolab.owloop.descriptor.construction.descriptorGround;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * This abstract class is used to instantiate a {@link DescriptorGround} for a {@link IndividualGroundInstance}
 * linked to {@link IndividualExpression}s.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
 * </small></div>
 */
abstract public class IndividualDescriptorGround
        extends DescriptorGround<OWLNamedIndividual>
        implements IndividualExpression {

    public IndividualDescriptorGround(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }

    public IndividualDescriptorGround(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }

    public IndividualDescriptorGround(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }

    public IndividualDescriptorGround(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }

    public IndividualDescriptorGround(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }

    public IndividualDescriptorGround(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }

    public IndividualDescriptorGround(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }

    public IndividualDescriptorGround(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    @Override
    protected GroundInstance<OWLNamedIndividual> getNewGround(OWLReferences ontology, OWLNamedIndividual instance) {
        return new IndividualGroundInstance(ontology, instance);
    }

    @Override
    protected GroundInstance<OWLNamedIndividual> getNewGround(OWLReferences ontology, String instance) {
            return new IndividualGroundInstance( ontology, ontology.getOWLIndividual( instance));
    }

    @Override
    public IndividualGroundInstance getGround() {
        return (IndividualGroundInstance) super.getGround();
    }

}
