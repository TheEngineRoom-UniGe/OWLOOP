package it.emarolab.owloop.aMORDescriptor.utility;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORConcept;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * The base {@link MORConcept} class {@link Descriptor}.
 * <p>
 *     This class is shared between all the {@link Descriptor}s that uses
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a>.
 *     It is just define {@link MORBase} for an {@link ConceptInstance}
 *     to be used for {@link MORConcept}s. Please, see those classes
 *     for more documentations.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.MORConceptBase <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
abstract public class MORConceptBase
        extends MORBase<OWLClass>
        implements MORConcept{

    public MORConceptBase(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORConceptBase(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORConceptBase(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORConceptBase(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORConceptBase(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORConceptBase(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORConceptBase(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORConceptBase(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
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
