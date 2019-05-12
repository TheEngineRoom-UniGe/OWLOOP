package it.emarolab.owloop.descriptor.construction.descriptorGround;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * The base {@link ConceptExpression} class {@link Descriptor}.
 * <p>
 *     This class is shared between all the {@link Descriptor}s that uses
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a>.
 *     It is just define {@link DescriptorGround} for an {@link ConceptInstance}
 *     to be used for {@link ConceptExpression}s. Please, see those classes
 *     for more documentations.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorGround.ConceptDescriptorGround <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
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
    protected GroundBase<OWLClass> getNewGround(OWLReferences ontology, OWLClass instance) {
        return new ConceptInstance(ontology, instance);
    }

    @Override
    protected GroundBase<OWLClass> getNewGround(OWLReferences ontology, String instance) {
            return new ConceptInstance( ontology, ontology.getOWLClass( instance));
    }

    @Override
    public ConceptInstance getGround() {
        return (ConceptInstance) super.getGround();
    }

}
