package it.emarolab.owloop.descriptor.construction.descriptorGround;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * This abstract class is used to instantiate a {@link DescriptorGround} for a {@link ObjectGroundInstance}
 * linked to {@link ObjectPropertyExpression}s.
 *
 * <p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
 * </small></div>
 */
abstract public class ObjectPropertyGround
        extends DescriptorGround<OWLObjectProperty>
        implements ObjectPropertyExpression {

    public ObjectPropertyGround(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public ObjectPropertyGround(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public ObjectPropertyGround(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public ObjectPropertyGround(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public ObjectPropertyGround(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public ObjectPropertyGround(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public ObjectPropertyGround(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public ObjectPropertyGround(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    @Override
    protected GroundInstance<OWLObjectProperty> getNewGround(OWLReferences ontology, OWLObjectProperty instance) {
        return new ObjectGroundInstance(ontology, instance);
    }
    @Override
    protected GroundInstance<OWLObjectProperty> getNewGround(OWLReferences ontology, String instance) {
        return new ObjectGroundInstance( ontology, ontology.getOWLObjectProperty( instance));
    }

    @Override
    public ObjectGroundInstance getGround() {
        return (ObjectGroundInstance) super.getGround();
    }

}
