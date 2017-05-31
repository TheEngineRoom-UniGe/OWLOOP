package it.emarolab.owloop.aMORDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import java.util.Set;

/**
 * The main interface for {@link Semantic.Ground} implemented in the <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a> API.
 * <p>
 *     This interface contains all the {@link Ground}s used by the
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a>
 *     {@link Descriptor} (i.e.: {@link MORConcept}, {@link MORIndividual},
 *     {@link MORDataProperty} and {@link MORObjectProperty}).
 *     As well as, It implements common facility to interface with an OWL ontology
 *     though a {@link OWLReferences}, which are implemented also by the
 *     above {@link Descriptor}s; just for simple usage.
 *     <br>
 *     By using this class each {@link Descriptor} is actually link to
 *     a specific OWL entity to be described as well external users can
 *     access to any further feature of the standard OWL API.
 *     <br>
 *     Each {@link GroundBase} represents an reference to an OWL ontology (i.e.: {@link #getGroundOntology()}),
 *     which is shared between more {@link GroundBase}s, without reinstating
 *     nether the ontology nor the reasoner. As well as link each {@link Semantic.Descriptor}
 *     to a particular instance in such ontology (i.e.: {@link #getGroundInstance()})
 *     to be synchronised with respect to a particular feature with respect to the
 *     ontological OWL representation (e.g.: disjoint, equivalent, sup/super etc.).
 *     More in particular, the class defined in this interface are:
 *     <ul>
 *     <li><b>{@link GroundBase}</b>: which is a base implementation for all the classes below.</li>
 *     <li><b>{@link IndividualInstance}</b>: used to ground descriptions about {@link MORIndividual}.</li>
 *     <li><b>{@link ConceptInstance}</b>: used to ground descriptions about {@link MORConcept}.</li>
 *     <li><b>{@link DataInstance}</b>: used to ground descriptions about {@link MORDataProperty}.</li>
 *     <li><b>{@link ObjectInstance}</b>: used to ground descriptions about {@link MORObjectProperty}.</li>
 *     </ul>
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORGrounding <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public interface MORGrounding<J extends OWLObject>
        extends Semantic.Ground<OWLReferences,J>{

    /**
     * The base class for each {@link MORGrounding}s.
     * <p>
     *     It describes common implementation for the: {@link IndividualInstance},
     *     {@link ConceptInstance}, {@link DataInstance} and
     *     {@link ObjectInstance}. It poses the instance to be an
     *     {@link OWLObject} while the ontology to be an {@link OWLReferences},
     *     following the <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a> API.
     *
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORGrounding <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <J> the type of ontological entity to be manipulated in the ontology by a Descriptor.
     */
    abstract class GroundBase<J extends OWLObject>
            implements MORGrounding<J>{

        private OWLReferences ontology;
        protected J instance;

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instance the ontological entities to be described.
         */
        public GroundBase(OWLReferences ontology, J instance) {
            this.ontology = ontology;
            this.instance = instance;
        }
        /**
         * Copy constructor. It should be used by {@link #copyGround()}.
         * @param copy the ground to copyGround in a {@code new} instance
         */
        protected GroundBase(GroundBase<J> copy){
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
        abstract public GroundBase<J> copyGround();

        @Override // see super class for documentation
        public void reason() {
            ontology.synchronizeReasoner();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MORGrounding.GroundBase)) return false;

            GroundBase<?> morGround = (GroundBase<?>) o;

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
     * The {@link Semantic.Ground} for {@link MORIndividual}.
     * <p>
     *     It just implements super class constructors and set the
     *     entity parameter of {@link MORGrounding} (i.e.: {@code <Y>})
     *     to be an {@link OWLNamedIndividual}.
     *     See super class and related interface for more documentation.
     *
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORGrounding <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    class IndividualInstance
            extends GroundBase<OWLNamedIndividual> {
        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instance the ontological individual to be described.
         */
        public IndividualInstance(OWLReferences ontology, OWLNamedIndividual instance) {
            super(ontology, instance);
        }

        /**
         * Copy constructor
         * @param copy the ground to copy
         */
        public IndividualInstance( IndividualInstance copy) {
            super(copy);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instanceName the name of the ontological individual to be described.
         */
        public IndividualInstance(OWLReferences ontology, String instanceName) {
            super(ontology, ontology.getOWLIndividual(instanceName));
        }

        @Override
        public void setInstance(String instanceName) {
            this.instance = getOWLIndividual( instanceName);
        }

        @Override
        public IndividualInstance copyGround() {
            return new IndividualInstance( this);
        }
    }

    /**
     * The {@link Semantic.Ground} for {@link MORConcept}.
     * <p>
     *     It just implements super class constructors and set the
     *     entity parameter of {@link MORGrounding} (i.e.: {@code <Y>})
     *     to be an {@link OWLClass}.
     *     See super class and related interface for more documentation.
     *
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORGrounding <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    class ConceptInstance
            extends GroundBase<OWLClass> {

        /**
         * Copy constructor
         * @param copy the ground to copy
         */
        public ConceptInstance( ConceptInstance copy) {
            super(copy);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instance the ontological class to be described.
         */
        public ConceptInstance(OWLReferences ontology, OWLClass instance) {
            super(ontology, instance);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instanceName the name of the ontological class to be described.
         */
        public ConceptInstance(OWLReferences ontology, String instanceName) {
            super(ontology, ontology.getOWLClass( instanceName));
        }

        @Override
        public void setInstance(String instanceName) {
            this.instance = getOWLClass( instanceName);
        }

        @Override
        public ConceptInstance copyGround() {
            return new ConceptInstance( this);
        }
    }

    /**
     * The {@link Semantic.Ground} for {@link DataInstance}.
     * <p>
     *     It just implements super class constructors and set the
     *     entity parameter of {@link MORGrounding} (i.e.: {@code <Y>})
     *     to be an {@link OWLDataProperty}.
     *     See super class and related interface for more documentation.
     *
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORGrounding <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    class DataInstance
            extends GroundBase<OWLDataProperty> {

        /**
         * Copy constructor
         * @param copy the ground to copy
         */
        public DataInstance( DataInstance copy) {
            super(copy);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instance the ontological data property to be described.
         */
        public DataInstance(OWLReferences ontology, OWLDataProperty instance) {
            super(ontology, instance);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instanceName the name of the ontological data property to be described.
         */
        public DataInstance(OWLReferences ontology, String instanceName) {
            super(ontology, ontology.getOWLDataProperty( instanceName));
        }

        @Override
        public void setInstance(String instanceName) {
            this.instance = getOWLDataProperty( instanceName);
        }

        @Override
        public DataInstance copyGround() {
            return new DataInstance( this);
        }
    }

    /**
     * The {@link Semantic.Ground} for {@link ObjectInstance}.
     * <p>
     *     It just implements super class constructors and set the
     *     entity parameter of {@link MORGrounding} (i.e.: {@code <Y>})
     *     to be an {@link OWLObjectProperty}.
     *     See super class and related interface for more documentation.
     *
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORGrounding <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    class ObjectInstance
            extends GroundBase<OWLObjectProperty> {

        /**
         * Copy constructor
         * @param copy the ground to copy
         */
        public ObjectInstance( ObjectInstance copy) {
            super(copy);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instance the ontological object property to be described.
         */
        public ObjectInstance(OWLReferences ontology, OWLObjectProperty instance) {
            super(ontology, instance);
        }

        /**
         * Fully instanciate this class
         * @param ontology the ontology in which the related {@link Descriptor} will operate.
         * @param instanceName the name of the ontological object property to be described.
         */
        public ObjectInstance(OWLReferences ontology, String instanceName) {
            super(ontology, ontology.getOWLObjectProperty( instanceName));
        }

        @Override
        public void setInstance(String instanceName) {
            this.instance = getOWLObjectProperty( instanceName);
        }

        @Override
        public ObjectInstance copyGround() {
            return new ObjectInstance( this);
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
     * Given an ontological individual with a specified name
     * in the in the {@link #getOWLOntology()} IRI.
     * @param individualName the name of the {@link OWLNamedIndividual}.
     * @return a new OWL individual with the specified name in the grounded ontology.
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
     * @param value the data value and type of the {@link OWLLiteral}.
     * @return a new OWL data literal with the specified value and type, in the grounded ontology.
     */
    default OWLLiteral getOWLLiteral( Object value){
        return getGroundOntology().getOWLLiteral( value);
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
    default OWLReasoner getReasoner(){
        return getGroundOntology().getOWLReasoner();
    }
    /**
     * Returns the ontology initialised by aMOR API during {@link OWLReferences}
     * loading. It is generically used to access asserted entities.
     * @return the base ontology (i.e.: OWL API) interface of {@link #getGroundInstance()}.
     */
    default OWLOntology getOWLOntology(){
        return getGroundOntology().getOWLOntology();
    }
    /**
     * Returns the data factory initialised by aMOR API during {@link OWLReferences}
     * loading. This is generically used to create ontological entities to be asserted
     * in the ontology.
     * @return the base data factory (i.e.: OWL API) interface of {@link #getGroundInstance()}.
     */
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
     * This method calls: {@link it.emarolab.amor.owlDebugger.Logger#setPrintOnConsole(Boolean)}
     * with a given flag parameter.
     * @param enable the flag for enable/disable aMOR logging.
     */
    default void aMORlogging( boolean enable){
        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( enable);
    }

    /**
     * Return a compact name of an {@link OWLObject}, by removing IRI for instance.
     * @param obj the object to describe with a short name
     * @return the short name of the given object.
     */
    default String getOWLName( OWLObject obj){
        return getGroundOntology().getOWLObjectName( obj);
    }
    /**
     * Return a compact name of a {@code Set<{@link OWLObject}>}, by removing IRI for instance.
     * @param set the set of objects to describe with related short names
     * @return the set short names of the given object set.
     */
    default Set<String> getOWLName( Set<?> set){
        return getGroundOntology().getOWLObjectName( set);
    }

    /**
     * Givens the unique name of the {@link OWLReferences} given during ontology loading.
     * @return the ID of the ontology reference.
     */
    default String getOntologyName(){
        return getGroundOntology().getReferenceName();
    }
}
