package it.emarolab.owloop.descriptor.construction.descriptorGround;

import com.clarkparsia.pellet.owlapi.PelletReasoner;
import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * This abstract class describes common constructors and initialisations for
 * a specific {@link GroundInstance}.
 * All constructors fully initialise a new {@link Axiom.Ground} (i.e., set both an ontology and an instance).
 * By default, this class takes an ontology from a file and initialises the {@link PelletReasoner}
 * (If another {@link OWLReferences} is instantiated with the same ontoName then this ontology will be used.).
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
abstract public class DescriptorGround<J extends OWLObject>
        implements Axiom.Descriptor<OWLReferences,J>, DescriptorGroundInterface<J> {

    protected static final String NL = System.getProperty("line.separator");

    private static final boolean DEFAULT_BUFFERING_CHANGES = false;
    private static final boolean DEFAULT_AMOR_LOG = false;

    private GroundInstance<J> ground;

    public DescriptorGround(J instance, OWLReferences onto) {
        setGround( getNewGround( onto, instance));
    }
    public DescriptorGround(String instanceName, OWLReferences onto) {
        setGround( getNewGround( onto, instanceName));
    }
    public DescriptorGround(J instance, String ontoName) {
        OWLReferences ontology = initialiseOntology( ontoName);
        setGround( getNewGround( ontology, instance));
    }
    public DescriptorGround(J instance, String ontoName, String filePath, String iriPath) {
        OWLReferences ontology = initialiseOntology( ontoName, filePath, iriPath, DEFAULT_BUFFERING_CHANGES);
        setGround( getNewGround( ontology, instance));
    }
    public DescriptorGround(J instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        OWLReferences ontology = initialiseOntology( ontoName, filePath, iriPath, bufferingChanges);
        setGround( getNewGround( ontology, instance));
    }
    public DescriptorGround(String instanceName, String ontoName) {
        OWLReferences ontology = initialiseOntology( ontoName);
        setGround( getNewGround( ontology, instanceName));
    }
    public DescriptorGround(String instanceName, String ontoName, String filePath, String iriPath) {
        OWLReferences ontology = initialiseOntology( ontoName, filePath, iriPath, DEFAULT_BUFFERING_CHANGES);
        setGround( getNewGround( ontology, instanceName));
    }
    public DescriptorGround(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
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
    public void setGround(GroundInstance<J> ground){ // call this in all constructor
        this.ground = ground;
    }

    /**
     * It sets a new instance in the same {@link #getOntologyReference()}.
     * @param instance the new instance to be ground.
     */
    public void setGroundInstance(J instance){
        this.ground.setInstance( instance);
    }

    /**
     * It sets a new instance in the same {@link #getOntologyReference()}.
     * @param instanceName the new instance name to be ground.
     */
    public void setGroundInstance(String instanceName){
        this.ground.setInstance( instanceName);
    }

    /**
     * This method should return a new instanciate {@link DescriptorGroundInterface} with the given values.
     * It is automatically called on all default constructors in order to {@link #setGround(GroundInstance)}.
     * @param ontology the grounding ontology.
     * @param instance the grounding instance.
     * @return a new instance of the {@link DescriptorGroundInterface}.
     */
    abstract protected GroundInstance<J> getNewGround(OWLReferences ontology, J instance);
    /**
     * This method should return a new instanciate {@link DescriptorGroundInterface} with the given values.
     * It is automatically called on all default constructors in order to {@link #setGround(GroundInstance)}.
     * @param ontology the grounding ontology.
     * @param instance the ane of the grounding instance.
     * @return a new instance of the {@link DescriptorGroundInterface}.
     */
    abstract protected GroundInstance<J> getNewGround(OWLReferences ontology, String instance);

    /** Returns the Ground instance's name as a String*/
    public String getGroundInstanceName() {
        return getOntologyReference().getOWLObjectName(getGroundInstance());
    }

    @Override // see super class for documentation
    public GroundInstance<J> getGround() {
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
        if (!(o instanceof DescriptorGround)) return false;

        DescriptorGround<?> descriptorGround = (DescriptorGround<?>) o;

        return getGround() != null ? getGround().equals(descriptorGround.getGround()) : descriptorGround.getGround() == null;
    }

    @Override
    public int hashCode() {
        return getGround() != null ? getGround().hashCode() : 0;
    }
}
