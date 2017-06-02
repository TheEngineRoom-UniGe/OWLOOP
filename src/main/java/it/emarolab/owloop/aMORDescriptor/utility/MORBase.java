package it.emarolab.owloop.aMORDescriptor.utility;

import com.clarkparsia.pellet.owlapi.PelletReasoner;
import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.owloop.aMORDescriptor.*;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * The base class for {@link Semantic.Descriptor} implemented in the <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a> API.
 * <p>
 *     This class is shared between all the {@link Descriptor}s that uses
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a>.
 *     (i.e.: the once which are based on: {@link MORConcept}, {@link MORIndividual},
 *     {@link MORDataProperty} and {@link MORObjectProperty}).
 *     It only describes common constructors and initialisation
 *     for specific {@link MORGrounding}.
 *     Note that all constructors fully initialise a new {@link Semantic.Ground}
 *     (i.e.: set both an ontology and an instance).
 *     <br>
 *     By default this takes an ontology from file and initialises the {@link PelletReasoner}.
 *     Otherwise, if another {@link OWLReferences} is instanciate with the same {@code name}
 *     in the {@link OWLReferences.OWLReferencesContainer}, this ontology will be used.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.MORBase <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
abstract public class MORBase<J extends OWLObject>
        implements Semantic.Descriptor<OWLReferences,J>, MORGrounding<J>{

    protected static final String NL = System.getProperty("line.separator");

    private static final boolean DEFAULT_BUFFERING_CHANGES = false;
    private static final boolean DEFAULT_AMOR_LOG = false;

    private GroundBase<J> ground;

    public MORBase(J instance, OWLReferences onto) {
        setGround( getNewGround( onto, instance));
    }
    public MORBase(String instanceName, OWLReferences onto) {
        setGround( getNewGround( onto, instanceName));
    }
    public MORBase(J instance, String ontoName) {
        OWLReferences ontology = initialiseOntology( ontoName);
        setGround( getNewGround( ontology, instance));
    }
    public MORBase( J instance, String ontoName, String filePath, String iriPath) {
        OWLReferences ontology = initialiseOntology( ontoName, filePath, iriPath, DEFAULT_BUFFERING_CHANGES);
        setGround( getNewGround( ontology, instance));
    }
    public MORBase( J instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        OWLReferences ontology = initialiseOntology( ontoName, filePath, iriPath, bufferingChanges);
        setGround( getNewGround( ontology, instance));
    }
    public MORBase(String instanceName, String ontoName) {
        OWLReferences ontology = initialiseOntology( ontoName);
        setGround( getNewGround( ontology, instanceName));
    }
    public MORBase( String instanceName, String ontoName, String filePath, String iriPath) {
        OWLReferences ontology = initialiseOntology( ontoName, filePath, iriPath, DEFAULT_BUFFERING_CHANGES);
        setGround( getNewGround( ontology, instanceName));
    }
    public MORBase( String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        OWLReferences ontology = initialiseOntology( ontoName, filePath, iriPath, bufferingChanges);
        setGround( getNewGround( ontology, instanceName));
    }
    // common internal constructors.
    private OWLReferences initialiseOntology( String ontoName){
        if (OWLReferencesInterface.OWLReferencesContainer.getOWLReferencesKeys().contains(ontoName))
            return (OWLReferences) OWLReferencesInterface.OWLReferencesContainer.getOWLReferences(ontoName);
        System.err.println( "\tOntology " + ontoName + " not found!");
        return null;
    }
    private OWLReferences initialiseOntology( String ontoName, String filePath, String iriPath, Boolean bufferingChanges) {
        aMORlogging( DEFAULT_AMOR_LOG);
        OWLReferences ontology;
        if (OWLReferencesInterface.OWLReferencesContainer.getOWLReferencesKeys().contains(ontoName)) {
            ontology = (OWLReferences) OWLReferencesInterface.OWLReferencesContainer.getOWLReferences(ontoName);
            if ( ontology.getFilePath().equals(filePath) & ontology.getOntologyPath().equals(iriPath)) {
                System.out.println("\tontological representation loaded from container with name: " + ontoName);
                if (bufferingChanges != null)
                    ontology.setOWLManipulatorBuffering(bufferingChanges);
            } else{
                System.out.println("!!!!\tontological representation cannot have a duplicated name: " + ontoName
                        + ". Loaded from container with different file path or iri path!!");
                System.out.println("!!!!\tgiven file path: " + filePath);
                System.out.println("!!!!\tloaded file path: " + ontology.getFilePath());
                System.out.println("!!!!\tgiven IRI path: " + iriPath);
                System.out.println("!!!!\tloaded IRI path: " + ontology.getOntologyPath());
            }
        } else {
            ontology = OWLReferencesInterface.OWLReferencesContainer.
                    newOWLReferenceFromFileWithPellet(ontoName, filePath, iriPath, bufferingChanges);
            System.out.println("\tontological representation loaded from: " + filePath);
        }
        return ontology;
    }

    /**
     * This function has to be called in any constructor.
     * This set should be done with a fully constructed input parameter.
     * @param ground the ground to set as field of this class.
     */
    public void setGround(GroundBase<J> ground){ // call this in all constructor
        this.ground = ground;
    }

    /**
     * It sets a new instance in the same {@link #getOntology()}.
     * @param instance the new instance to be ground.
     */
    public void setInstance(J instance){
        this.ground.setInstance( instance);
    }

    /**
     * It sets a new instance in the same {@link #getOntology()}.
     * @param instanceName the new instance name to be ground.
     */
    public void setInstance(String instanceName){
        this.ground.setInstance( instanceName);
    }

    /**
     * This method should return a new instanciate {@link MORGrounding} with the given values.
     * It is automatically called on all default constructors in order to {@link #setGround(GroundBase)}.
     * @param ontology the grounding ontology.
     * @param instance the grounding instance.
     * @return a new instance of the {@link MORGrounding}.
     */
    abstract protected GroundBase<J> getNewGround(OWLReferences ontology, J instance);
    /**
     * This method should return a new instanciate {@link MORGrounding} with the given values.
     * It is automatically called on all default constructors in order to {@link #setGround(GroundBase)}.
     * @param ontology the grounding ontology.
     * @param instance the ane of the grounding instance.
     * @return a new instance of the {@link MORGrounding}.
     */
    abstract protected GroundBase<J> getNewGround(OWLReferences ontology, String instance);

    @Override // see super class for documentation
    public GroundBase<J> getGround() {
        return ground;
    }

    @Override // see super class for documentation
    public void reason() {
        getGround().reason();
    }

    @Override // see super class for documentation
    public Ground<OWLReferences, J> copyGround() {
        return getGround().copyGround();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MORBase)) return false;

        MORBase<?> morBase = (MORBase<?>) o;

        return getGround() != null ? getGround().equals(morBase.getGround()) : morBase.getGround() == null;
    }

    @Override
    public int hashCode() {
        return getGround() != null ? getGround().hashCode() : 0;
    }
}
