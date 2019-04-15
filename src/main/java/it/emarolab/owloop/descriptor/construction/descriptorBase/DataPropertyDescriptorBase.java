package it.emarolab.owloop.descriptor.construction.descriptorBase;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;

/**
 * The base {@link DataPropertyExpression} class {@link Descriptor}.
 * <p>
 *     This class is shared between all the {@link Descriptor}s that uses
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a>.
 *     It is just define {@link DescriptorBase} for an {@link DataInstance}
 *     to be used for {@link DataPropertyExpression}s. Please, see those classes
 *     for more documentations.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorBase.DataPropertyDescriptorBase <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
abstract public class DataPropertyDescriptorBase
        extends DescriptorBase<OWLDataProperty>
        implements DataPropertyExpression {

    public DataPropertyDescriptorBase(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DataPropertyDescriptorBase(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DataPropertyDescriptorBase(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public DataPropertyDescriptorBase(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DataPropertyDescriptorBase(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DataPropertyDescriptorBase(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DataPropertyDescriptorBase(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DataPropertyDescriptorBase(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    @Override
    protected GroundBase<OWLDataProperty> getNewGround(OWLReferences ontology, OWLDataProperty instance) {
        return new DataInstance(ontology, instance);
    }
    @Override
    protected GroundBase<OWLDataProperty> getNewGround(OWLReferences ontology, String instance) {
        return new DataInstance( ontology, ontology.getOWLDataProperty( instance));
    }

    @Override
    public DataInstance getGround() {
        return (DataInstance) super.getGround();
    }

}
