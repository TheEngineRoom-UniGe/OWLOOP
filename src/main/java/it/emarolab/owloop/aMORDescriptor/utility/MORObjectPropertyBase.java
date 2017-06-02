package it.emarolab.owloop.aMORDescriptor.utility;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * The base {@link MORObjectProperty} class {@link Descriptor}.
 * <p>
 *     This class is shared between all the {@link Descriptor}s that uses
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a>.
 *     It is just define {@link MORBase} for an {@link ObjectInstance}
 *     to be used for {@link MORObjectProperty}s. Please, see those classes
 *     for more documentations.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.MORDataPropertyBase <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
abstract public class MORObjectPropertyBase
        extends MORBase<OWLObjectProperty>
        implements MORObjectProperty{

    public MORObjectPropertyBase(OWLObjectProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORObjectPropertyBase(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORObjectPropertyBase(OWLObjectProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORObjectPropertyBase(OWLObjectProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORObjectPropertyBase(OWLObjectProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORObjectPropertyBase(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORObjectPropertyBase(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORObjectPropertyBase(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
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
