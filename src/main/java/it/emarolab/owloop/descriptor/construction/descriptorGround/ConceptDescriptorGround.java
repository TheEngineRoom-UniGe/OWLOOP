package it.emarolab.owloop.descriptor.construction.descriptorGround;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * This abstract class is used to instantiate a {@link DescriptorGround} for a {@link ConceptGroundInstance}
 * linked to {@link ConceptExpression}s.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
 * </small></div>
 */
abstract public class ConceptDescriptorGround
        extends DescriptorGround<OWLClass>
        implements ConceptExpression {

    public ConceptDescriptorGround(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public ConceptDescriptorGround(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public ConceptDescriptorGround(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public ConceptDescriptorGround(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public ConceptDescriptorGround(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public ConceptDescriptorGround(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public ConceptDescriptorGround(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public ConceptDescriptorGround(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    @Override
    protected GroundInstance<OWLClass> getNewGround(OWLReferences ontology, OWLClass instance) {
        return new ConceptGroundInstance(ontology, instance);
    }

    @Override
    protected GroundInstance<OWLClass> getNewGround(OWLReferences ontology, String instance) {
            return new ConceptGroundInstance( ontology, ontology.getOWLClass( instance));
    }

    @Override
    public ConceptGroundInstance getGround() {
        return (ConceptGroundInstance) super.getGround();
    }

}
