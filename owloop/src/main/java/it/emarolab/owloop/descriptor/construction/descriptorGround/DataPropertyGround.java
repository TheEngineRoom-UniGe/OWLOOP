package it.emarolab.owloop.descriptor.construction.descriptorGround;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;

/**
 * This abstract class is used to instantiate a {@link DescriptorGround} for a {@link DataGroundInstance}
 * linked to {@link DataPropertyExpression}s.
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
abstract public class DataPropertyGround
        extends DescriptorGround<OWLDataProperty>
        implements DataPropertyExpression {

    public DataPropertyGround(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DataPropertyGround(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DataPropertyGround(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public DataPropertyGround(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DataPropertyGround(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DataPropertyGround(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DataPropertyGround(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DataPropertyGround(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    @Override
    protected GroundInstance<OWLDataProperty> getNewGround(OWLReferences ontology, OWLDataProperty instance) {
        return new DataGroundInstance(ontology, instance);
    }
    @Override
    protected GroundInstance<OWLDataProperty> getNewGround(OWLReferences ontology, String instance) {
        return new DataGroundInstance( ontology, ontology.getOWLDataProperty( instance));
    }

    @Override
    public DataGroundInstance getGround() {
        return (DataGroundInstance) super.getGround();
    }

}
