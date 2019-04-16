package it.emarolab.owloop.descriptor.construction.descriptorBase;

import com.clarkparsia.pellet.owlapi.PelletReasoner;
import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.owloop.core.Axiom;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.*;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * The base class for {@link Axiom.Descriptor} implemented in the <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a> API.
 * <p>
 *     This class is shared between all the {@link Descriptor}s that uses
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a>.
 *     (i.e.: the once which are based on: {@link ConceptExpression}, {@link IndividualExpression},
 *     {@link DataPropertyExpression} and {@link ObjectPropertyExpression}).
 *     It only describes common constructors and initialisation
 *     for specific {@link DescriptorGrounding}.
 *     Note that all constructors fully initialise a new {@link Axiom.Ground}
 *     (i.e.: set both an ontology and an instance).
 *     <br>
 *     By default this takes an ontology from file and initialises the {@link PelletReasoner}.
 *     Otherwise, if another {@link OWLReferences} is instanciate with the same {@code name}
 *     in the {@link OWLReferences.OWLReferencesContainer}, this ontology will be used.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorBase.DescriptorBase <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
abstract public class DescriptorBase<J extends OWLObject>
        implements Axiom.Descriptor<OWLReferences,J>, DescriptorGrounding<J> {

    protected static final String NL = System.getProperty("line.separator");

    private static final boolean DEFAULT_BUFFERING_CHANGES = false;
    private static final boolean DEFAULT_AMOR_LOG = false;

    private GroundBase<J> ground;

    public DescriptorBase(J instance, OWLReferences onto) {
        setGround( getNewGround( onto, instance));
    }
    public DescriptorBase(String instanceName, OWLReferences onto) {
        setGround( getNewGround( onto, instanceName));
    }
    public DescriptorBase(J instance, String ontoName) {
        OWLReferences ontology = initialiseOntology( ontoName);
        setGround( getNewGround( ontology, instance));
    }
    public DescriptorBase(J instance, String ontoName, String filePath, String iriPath) {
        OWLReferences ontology = initialiseOntology( ontoName, filePath, iriPath, DEFAULT_BUFFERING_CHANGES);
        setGround( getNewGround( ontology, instance));
    }
    public DescriptorBase(J instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        OWLReferences ontology = initialiseOntology( ontoName, filePath, iriPath, bufferingChanges);
        setGround( getNewGround( ontology, instance));
    }
    public DescriptorBase(String instanceName, String ontoName) {
        OWLReferences ontology = initialiseOntology( ontoName);
        setGround( getNewGround( ontology, instanceName));
    }
    public DescriptorBase(String instanceName, String ontoName, String filePath, String iriPath) {
        OWLReferences ontology = initialiseOntology( ontoName, filePath, iriPath, DEFAULT_BUFFERING_CHANGES);
        setGround( getNewGround( ontology, instanceName));
    }
    public DescriptorBase(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
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
     * This method should return a new instanciate {@link DescriptorGrounding} with the given values.
     * It is automatically called on all default constructors in order to {@link #setGround(GroundBase)}.
     * @param ontology the grounding ontology.
     * @param instance the grounding instance.
     * @return a new instance of the {@link DescriptorGrounding}.
     */
    abstract protected GroundBase<J> getNewGround(OWLReferences ontology, J instance);
    /**
     * This method should return a new instanciate {@link DescriptorGrounding} with the given values.
     * It is automatically called on all default constructors in order to {@link #setGround(GroundBase)}.
     * @param ontology the grounding ontology.
     * @param instance the ane of the grounding instance.
     * @return a new instance of the {@link DescriptorGrounding}.
     */
    abstract protected GroundBase<J> getNewGround(OWLReferences ontology, String instance);

    /** Returns the Ground instance's name as a String*/
    public String getInstanceName() {
        return getOntology().getOWLObjectName(getGroundInstance());
    }

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
        if (!(o instanceof DescriptorBase)) return false;

        DescriptorBase<?> descriptorBase = (DescriptorBase<?>) o;

        return getGround() != null ? getGround().equals(descriptorBase.getGround()) : descriptorBase.getGround() == null;
    }

    @Override
    public int hashCode() {
        return getGround() != null ? getGround().hashCode() : 0;
    }
}
