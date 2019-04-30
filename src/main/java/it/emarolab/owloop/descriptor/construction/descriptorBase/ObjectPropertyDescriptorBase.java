package it.emarolab.owloop.descriptor.construction.descriptorBase;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * The base {@link ObjectPropertyExpression} class {@link Descriptor}.
 * <p>
 *     This class is shared between all the {@link Descriptor}s that uses
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a>.
 *     It is just define {@link DescriptorBase} for an {@link ObjectInstance}
 *     to be used for {@link ObjectPropertyExpression}s. Please, see those classes
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
abstract public class ObjectPropertyDescriptorBase
        extends DescriptorBase<OWLObjectProperty>
        implements ObjectPropertyExpression {

    public ObjectPropertyDescriptorBase(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public ObjectPropertyDescriptorBase(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public ObjectPropertyDescriptorBase(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public ObjectPropertyDescriptorBase(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public ObjectPropertyDescriptorBase(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public ObjectPropertyDescriptorBase(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public ObjectPropertyDescriptorBase(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public ObjectPropertyDescriptorBase(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    @Override
    protected GroundBase<OWLObjectProperty> getNewGround(OWLReferences ontology, OWLObjectProperty instance) {
        return new ObjectInstance(ontology, instance);
    }
    @Override
    protected GroundBase<OWLObjectProperty> getNewGround(OWLReferences ontology, String instance) {
        return new ObjectInstance( ontology, ontology.getOWLObjectProperty( instance));
    }

    @Override
    public ObjectInstance getGround() {
        return (ObjectInstance) super.getGround();
    }

}
