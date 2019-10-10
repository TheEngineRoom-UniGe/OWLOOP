package it.emarolab.owloop.descriptor.construction.descriptorGround;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.core.Axiom;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ClassExpression;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ObjectPropertyExpression;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import java.util.Set;

/**
 * This interface implements {@link Axiom.Ground} and allows grounding of Descriptors of the type
 * {@link ClassExpression}, {@link IndividualExpression}, {@link DataPropertyExpression} and
 * {@link ObjectPropertyExpression}).
 * A Ground associates to an Ontology using {@link OWLReferences}.
 * The following classes implement {@link DescriptorGroundInterface}:
 *     <ul>
 *     <li><b>{@link GroundInstance}</b>:       an abstract class of all the classes below.</li>
 *     <li><b>{@link IndividualGroundInstance}</b>:   class to ground axioms of type {@link IndividualExpression}.</li>
 *     <li><b>{@link ConceptGroundInstance}</b>:      class to ground axioms of type {@link ClassExpression}.</li>
 *     <li><b>{@link DataGroundInstance}</b>:         class to ground axioms of type {@link DataPropertyExpression}.</li>
 *     <li><b>{@link ObjectGroundInstance}</b>:       class to ground axioms of type {@link ObjectPropertyExpression}.</li>
 *     </ul>
 * Using these classes each {@link Descriptor}, (i) links to a specific OWL entity in an Ontology and (ii) allows access
 * to features in the standard OWL-API.
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
public interface DescriptorGroundInterface<J extends OWLObject>
        extends Axiom.Ground<OWLReferences,J>{

    /**
     * A {@link GroundInstance} has the reference to an Ontology (i.e., using {@link #getGroundOntology()}). The reference
     * is shared between all {@link GroundInstance}s. This way ontology and reasoner are not instantiated for all.
     *
     * The base class for each {@link DescriptorGroundInterface}s.
     * Is used for implementing: {@link IndividualGroundInstance}, {@link ConceptGroundInstance}, {@link DataGroundInstance} and
     * {@link ObjectGroundInstance}. It makes the instance to be an {@link OWLObject} and the ontology to be an {@link OWLReferences}.
     *
     * @param <J> the type of ontological entity to be manipulated in the ontology by a descriptor.
     */
    abstract class GroundInstance<J extends OWLObject>
            implements DescriptorGroundInterface<J> {

        private OWLReferences ontology;
        protected J instance;

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instance the ontological entities to be described.
         */
        public GroundInstance(OWLReferences ontology, J instance) {
            this.ontology = ontology;
            this.instance = instance;
        }
        /**
         * Copy constructor. It should be used by {@link #copyGround()}.
         * @param copy the ground to copyGround in a {@code new} instance
         */
        protected GroundInstance(GroundInstance<J> copy){
            this.ontology = copy.ontology;
            this.instance = copy.instance;
        }

        @Override // see super class for documentation
        public OWLReferences getGroundOntology() {
            return ontology;
        }

        @Override // see super class for documentation
        public J getGroundInstance() {
            return instance;
        }

        @Override // see super class for documentation
        abstract public GroundInstance<J> copyGround();

        @Override // see super class for documentation
        public void reason() {
            ontology.synchronizeReasoner();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DescriptorGroundInterface.GroundInstance)) return false;

            GroundInstance<?> morGround = (GroundInstance<?>) o;

            if (ontology != null ? !ontology.equals(morGround.ontology) : morGround.ontology != null) return false;
            return instance != null ? instance.equals(morGround.instance) : morGround.instance == null;
        }

        @Override
        public int hashCode() {
            int result = ontology != null ? ontology.hashCode() : 0;
            result = 31 * result + (instance != null ? instance.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return ontology.getReferenceName() + "@" + getOWLName( instance);
        }

        /**
         * It change the instance to be grounded in the same {@link #getOWLOntology()},
         * @param instance the new grounding instance.
         */
        public void setInstance(J instance) {
            this.instance = instance;
        }
        /**
         * It change the instance to be grounded in the same {@link #getOWLOntology()},
         * @param instanceName the new grounding instance name.
         */
        abstract public void setInstance(String instanceName);
    }

    /**
     * The {@link Axiom.Ground} for an {@link IndividualExpression}.
     * It sets the entity parameter of {@link DescriptorGroundInterface} to be an {@link OWLNamedIndividual}.
     */
    class IndividualGroundInstance
            extends GroundInstance<OWLNamedIndividual> {
        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instance the ontological individualDescriptor to be described.
         */
        public IndividualGroundInstance(OWLReferences ontology, OWLNamedIndividual instance) {
            super(ontology, instance);
        }

        /**
         * Copy constructor
         * @param copy the ground to copy
         */
        public IndividualGroundInstance(IndividualGroundInstance copy) {
            super(copy);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instanceName the name of the ontological individualDescriptor to be described.
         */
        public IndividualGroundInstance(OWLReferences ontology, String instanceName) {
            super(ontology, ontology.getOWLIndividual(instanceName));
        }

        @Override
        public void setInstance(String instanceName) {
            this.instance = getOWLIndividual( instanceName);
        }

        @Override
        public IndividualGroundInstance copyGround() {
            return new IndividualGroundInstance( this);
        }
    }

    /**
     * The {@link Axiom.Ground} for a {@link ClassExpression}.
     * It sets the entity parameter of {@link DescriptorGroundInterface} to be an {@link OWLClass}.
     */
    class ConceptGroundInstance
            extends GroundInstance<OWLClass> {

        /**
         * Copy constructor
         * @param copy the ground to copy
         */
        public ConceptGroundInstance(ConceptGroundInstance copy) {
            super(copy);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instance the ontological class to be described.
         */
        public ConceptGroundInstance(OWLReferences ontology, OWLClass instance) {
            super(ontology, instance);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instanceName the name of the ontological class to be described.
         */
        public ConceptGroundInstance(OWLReferences ontology, String instanceName) {
            super(ontology, ontology.getOWLClass( instanceName));
        }

        @Override
        public void setInstance(String instanceName) {
            this.instance = getOWLClass( instanceName);
        }

        @Override
        public ConceptGroundInstance copyGround() {
            return new ConceptGroundInstance( this);
        }
    }

    /**
     * The {@link Axiom.Ground} for a {@link DataGroundInstance}.
     * It sets the entity parameter of {@link DescriptorGroundInterface} to be an {@link OWLDataProperty}.
     */
    class DataGroundInstance
            extends GroundInstance<OWLDataProperty> {

        /**
         * Copy constructor
         * @param copy the ground to copy
         */
        public DataGroundInstance(DataGroundInstance copy) {
            super(copy);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instance the ontological data property to be described.
         */
        public DataGroundInstance(OWLReferences ontology, OWLDataProperty instance) {
            super(ontology, instance);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instanceName the name of the ontological data property to be described.
         */
        public DataGroundInstance(OWLReferences ontology, String instanceName) {
            super(ontology, ontology.getOWLDataProperty( instanceName));
        }

        @Override
        public void setInstance(String instanceName) {
            this.instance = getOWLDataProperty( instanceName);
        }

        @Override
        public DataGroundInstance copyGround() {
            return new DataGroundInstance( this);
        }
    }

    /**
     * The {@link Axiom.Ground} for an {@link ObjectGroundInstance}.
     * It sets the entity parameter of {@link DescriptorGroundInterface} to be an {@link OWLObjectProperty}.
     */
    class ObjectGroundInstance
            extends GroundInstance<OWLObjectProperty> {

        /**
         * Copy constructor
         * @param copy the ground to copy
         */
        public ObjectGroundInstance(ObjectGroundInstance copy) {
            super(copy);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instance the ontological object property to be described.
         */
        public ObjectGroundInstance(OWLReferences ontology, OWLObjectProperty instance) {
            super(ontology, instance);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instanceName the name of the ontological object property to be described.
         */
        public ObjectGroundInstance(OWLReferences ontology, String instanceName) {
            super(ontology, ontology.getOWLObjectProperty( instanceName));
        }

        @Override
        public void setInstance(String instanceName) {
            this.instance = getOWLObjectProperty( instanceName);
        }

        @Override
        public ObjectGroundInstance copyGround() {
            return new ObjectGroundInstance( this);
        }
    }

    /**
     * Given an ontological class with a specified name
     * in the in the {@link #getOWLOntology()} IRI.
     * @param className the name of the {@link OWLClass}.
     * @return a new OWL class with the specified name in the grounded ontology.
     */
    default OWLClass getOWLClass(String className){
        return getGroundOntology().getOWLClass( className);
    }

    /**
     * Given an ontological individualDescriptor with a specified name
     * in the in the {@link #getOWLOntology()} IRI.
     * @param individualName the name of the {@link OWLNamedIndividual}.
     * @return a new OWL individualDescriptor with the specified name in the grounded ontology.
     */
    default OWLNamedIndividual getOWLIndividual(String individualName){
        return getGroundOntology().getOWLIndividual( individualName);
    }

    /**
     * Given an ontological data property with a specified name
     * in the in the {@link #getOWLOntology()} IRI.
     * @param propertyName the name of the {@link OWLDataProperty}.
     * @return a new OWL data property with the specified name in the grounded ontology.
     */
    default OWLDataProperty getOWLDataProperty(String propertyName){
        return getGroundOntology().getOWLDataProperty( propertyName);
    }

    /**
     * Given an ontological object property with a specified name
     * in the in the {@link #getOWLOntology()} IRI.
     * @param propertyName the name of the {@link OWLDataProperty}.
     * @return a new OWL data property with the specified name in the grounded ontology.
     */
    default OWLObjectProperty getOWLObjectProperty(String propertyName){
        return getGroundOntology().getOWLObjectProperty( propertyName);
    }

    /**
     * Given an ontological literal within the {@link #getOWLOntology()} IRI.
     * The supported type of the input values are: {@link String}, {@link Integer},
     * {@link Float}, {@link Double} and {@link Long}.
     * @param instance the data value and type of the {@link OWLLiteral}.
     * @return a new OWL data literal with the specified value and type, in the grounded ontology.
     */
    default OWLLiteral getOWLLiteral( Object instance){
        return getGroundOntology().getOWLLiteral( instance);
    }

    /**
     * Given an ontological literal within the {@link #getOWLOntology()} IRI.
     * The supported type of the input values are: {@link String}, {@link Integer},
     * {@link Float}, {@link Double} and {@link Long}.
     * @param c the data type of the {@link OWLDatatype}.
     * @return a new OWL data type (based on the given {@code Class}), in the grounded ontology.
     */
    default OWLDatatype getOWLDataType( Class c){ // data ranges????
        if( c.equals( Double.class))
            return getGroundOntology().getOWLFactory().getDoubleOWLDatatype();
        if( c.equals( Float.class))
            return getGroundOntology().getOWLFactory().getFloatOWLDatatype();
        if( c.equals( String.class))
           return getGroundOntology().getOWLFactory().getStringOWLDatatype();
        if( c.equals( Boolean.class))
            return getGroundOntology().getOWLFactory().getBooleanOWLDatatype();
        if( c.equals( Long.class))
            return getGroundOntology().getOWLFactory().getOWLDatatype( OWL2Datatype.XSD_LONG.getIRI());
        if( c.equals( Integer.class))
            return getGroundOntology().getOWLFactory().getIntegerOWLDatatype();

        System.err.println( "OWL data type " + c.getSimpleName() + " not supported!");
        return null;
    }

    /**
     * Returns the reasoner initialised by aMOR API during {@link OWLReferences}
     * loading. It is generically used to query inferred entities in the ontology.
     * @return the reasoner of the {@link #getGroundInstance()}.
     */
    @Deprecated
    default OWLReasoner getOWLReasoner(){
        return getGroundOntology().getOWLReasoner();
    }
    /**
     * Returns the ontology initialised by aMOR API during {@link OWLReferences}
     * loading. It is generically used to access asserted entities.
     * @return the base ontology (i.e.: OWL API) interface of {@link #getGroundInstance()}.
     */
    @Deprecated
    default OWLOntology getOWLOntology(){
        return getGroundOntology().getOWLOntology();
    }
    /**
     * Returns the data factory initialised by aMOR API during {@link OWLReferences}
     * loading. This is generically used to create ontological entities to be asserted
     * in the ontology.
     * @return the base data factory (i.e.: OWL API) interface of {@link #getGroundInstance()}.
     */
    @Deprecated
    default OWLDataFactory getOWLDataFactory(){
        return getGroundOntology().getOWLFactory();
    }

    /**
     * It calls {@link OWLReferences#synchronizeReasoner()} in order to performs
     * reasoning over the actual state of the ontology.
     */
    default void reason(){
        getGroundOntology().synchronizeReasoner();
    }

    /**
     * Save the ontology in a specified file.
     * @param filePath the path to the file to create (or overwrite).
     */
    default void saveOntology(String filePath){
        getGroundOntology().saveOntology( filePath);
    }
    /**
     * Save the ontology in a specified file by making all the inferred axioms of the {@link OWLReasoner}
     * as facts (i.e.: asserted).
     * @param filePath the path to the file to create (or overwrite).
     * @param saveReasoned {@code true} to make inferred axiom as asserted, {@code false} to store
     *                                 only the assertions.
     */
    default void saveOntology(String filePath, boolean saveReasoned){
        getGroundOntology().saveOntology( saveReasoned, filePath);
    }

    /**
     * It enable/disable the use of the reasoner during aMOR querying (i.e.: {@code {@link OWLReferences}.get...(..)})
     * @param reason {@code true} to enable reasoning during aMOR query. {@code False}
     *                           to query only for asserted axioms in the OWL ontology.
     */
    default void setReasonedQuery( boolean reason){
        getGroundOntology().setOWLEnquirerIncludesInferences( reason);
    }
    /**
     * It returns if the aMOR querying (i.e.: {@code {@link OWLReferences}.get...(..)})
     * returns also reasoned axioms or only asserted facts.
     * @return {@code false} if the aMOR querying returns only asserted facts. {@code True}
     * if also reasoning inferring is considered.
     */
    default boolean isReasonedQuery(){
        return getGroundOntology().getOWLEnquirerReasoningFlag();
    }

    /**
     * It enable/disable exhaustive description of the reasoner during aMOR querying.
     * @param complete {@code true} if the query returns all the results (except for {@code OWLThing}).
     *                             {@code False} if only the representative element should be given.
     */
    default void setCompleteQuery( boolean complete){
        getGroundOntology().setOWLEnquirerCompletenessFlag( complete);
    }
    /**
     * It returns if aMOR querying give an exhaustive descriptions.
     * @return  {@code true} if the query returns all the results (except for {@code OWLThing}).
     *                             {@code False} if only the representative element is given.
     */
    default boolean isCompleteQuery(){
        return getGroundOntology().getOWLEnquirerCompletenessFlag();
    }

    /**
     * Return a compact name of an {@link OWLObject}, by removing IRI for instance.
     * @param instance the object to describe with a short name
     * @return the short name of the given object.
     */
    default String getOWLName( OWLObject instance){
        return getGroundOntology().getOWLObjectName( instance);
    }
    /**
     * Return a compact name of a {@code Set<{@link OWLObject}>}, by removing IRI for instance.
     * @param instance the set of objects to describe with related short names
     * @return the set short names of the given object set.
     */
    default Set<String> getOWLName( Set<?> instance){
        return getGroundOntology().getOWLObjectName( instance);
    }

    /**
     * Givens the unique name of the {@link OWLReferences} given during ontology loading.
     * @return the ID of the ontology reference.
     */
    default String getOntologyName(){
        return getGroundOntology().getReferenceName();
    }
}
