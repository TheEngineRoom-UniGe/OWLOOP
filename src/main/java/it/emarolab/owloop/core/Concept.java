package it.emarolab.owloop.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The main interface for ontological Class {@link Semantic.Descriptor}.
 * <p>
 *     This interface contains all the {@link Semantic.Descriptor} that
 *     can be applied to an ontological class (e.g.: {@link org.semanticweb.owlapi.model.OWLClass})
 *     in any arbitrary combination since all of them should rely on the same {@link Semantic.Ground}
 *     type.
 *     <br>
 *     More in particular, for the {@link #getInstance()} entities in the {@link #getOntology()}, those are:
 *     <ul>
 *     <li><b>{@link Classify}</b>: for describing the instances of an ontological class.</li>
 *     <li><b>{@link Disjoint}</b>: for describing disjointed class for the one grounded
 *                                         in a specific {@link Concept}.</li>
 *     <li><b>{@link Equivalent}</b>: for describing equivalent class for the one grounded
 *                                         in a specific {@link Concept}.</li>
 *     <li><b>{@link Sub}</b>: for representing classes that are implied by the one grounded
 *                                         in a specific {@link Concept}.</li>
 *     <li><b>{@link Super}</b>: for representing a grounded {@link Concept} that implies
 *                                         specific classes.</li>
 *     <li><b>{@link Define}</b>: for describing the axioms that builds the
 *                                         definition of a grounded {@link Concept}.</li>
 *     </ul>
 *     Nevertheless, they are still generic and not attached to any specific OWL implementation.
 *     Since they implements common feature of OWLLOOP architecture only.
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.core.Concept <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 *
 * @param <O> the type of ontology in which the axioms for classes will be applied.
 * @param <J> the type of instance (i.e.: class) for the axioms.
 */
public interface Concept<O,J>
        extends Semantic.Descriptor<O,J>{

    /**
     * The {@link Semantic.Descriptor} for individuals of an ontological class.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the individuals classified in a specific class
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Concept <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     * @param <Y> the type of individuals belonging to the class
     *           (it represents the of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Individual} descriptor instantiated during
     *           {@link #buildIndividualClassified()}  through {@link #getNewIndividualClassified(Object, Object)}.
     */
    interface Classify<O,J,Y,D extends Individual<O,Y>>
            extends Concept<O,J> {

        @Override // see documentation on Semantic.Descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<Y> from = synchroniseIndividualClassifiedFromSemantic();
                if ( from != null) {
                    getIndividualClassified().addAll(from.getToAdd());
                    getIndividualClassified().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent(null);
            }
        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
         * represents the individual classified to belonging to this {@link Concept}.
         * Each of {@link Individual}s are instantiated
         * through the method {@link #getNewIndividualClassified(Object, Object)};
         * this is called for all {@link #getIndividualClassified()}.
         * @return the set of {@link Individual}s that describes the
         * entities belonging to {@code this} described ontological class.
         */
        default Set<D> buildIndividualClassified(){
            Set<D> out = new HashSet<>();
            for( Y cl : getIndividualClassified()){
                D built = getNewIndividualClassified( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildIndividualClassified()} and
         * its purpose is to instantiate a new {@link Individual} to represent
         * an individual classified in {@code this} {@link Concept} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Individual}.
         * @param ontology the ontology in which ground the new {@link Individual}.
         * @return a new {@link Semantic.Descriptor} for all the individuals
         * classified by {@code this} descriptor.
         */
        D getNewIndividualClassified(Y instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the individual classified
         * in {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the individuals classified in {@code this} object
         */
        Axioms<Y> getIndividualClassified();

        /**
         * Queries to the OWL representation of the individual that are classified in {@code this} class.
         * @return a new {@link Semantic.Axioms} contained the individual classified by
         * the OWL structure of {@link #getInstance()}.
         */
        Axioms<Y> queryIndividualClassified();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #queryIndividualClassified()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getIndividualClassified()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the individual classified in an OWL class.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseIndividualClassifiedToSemantic(){
            try {
                return getIndividualClassified().synchroniseTo( queryIndividualClassified());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link Semantic.Axioms#synchroniseFrom(Axioms)} with {@link #queryIndividualClassified()}
         * as input parameter. This computes the changes to be performed into the {@link #getIndividualClassified()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the individual of an OWL class
         * with {@code this} structure.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseIndividualClassifiedFromSemantic(){
            try{
                return getIndividualClassified().synchroniseFrom( queryIndividualClassified());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for disjointed classes of an ontological class.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the disjointed classes of a specific class
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Concept <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     *           (it represents also the of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Concept} descriptor instantiated during
     *           {@link #buildDisjointConcept()} through {@link #getNewDisjointConcept(Object, Object)}.
     */
    interface Disjoint<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override // see documentation on Semantic.Descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<J> from = synchroniseDisjointConceptFromSemantic();
                if ( from != null) {
                    getDisjointConcept().addAll(from.getToAdd());
                    getDisjointConcept().removeAll(from.getToRemove());
                }
                return getIntent( from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }

        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
         * represents the classes disjointed to this {@link Concept}.
         * Each of those {@link Concept}s are instantiated
         * through the method {@link #getNewDisjointConcept(Object, Object)};
         * this is called for all {@link #getDisjointConcept()}.
         * @return the set of {@link Concept}s that describes the
         * entities disjointed to {@code this} described ontological class.
         */
        default Set<D> buildDisjointConcept(){
            Set<D> out = new HashSet<>();
            for( J cl : getDisjointConcept()){
                D built = getNewDisjointConcept( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildDisjointConcept()} and
         * its purpose is to instantiate a new {@link Concept} to represent
         * an class disjointed from {@code this} {@link Concept} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Concept}.
         * @param ontology the ontology in which ground the new {@link Concept}.
         * @return a new {@link Semantic.Descriptor} for all the classes
         * disjointed to {@code this} descriptor.
         */
        D getNewDisjointConcept(J instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the disjointed classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the disjointed classes from {@code this} object
         */
        Axioms<J> getDisjointConcept();

        /**
         * Queries to the OWL representation of the disjointed classes for {@code this} class.
         * @return a new {@link Semantic.Axioms} contained the disjointed classes from
         * the OWL structure of {@link #getInstance()}.
         */
        Axioms<J> queryDisjointConcept();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #queryDisjointConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointConcept()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the disjointed classes of an OWL class.
         */
        default Axioms.SynchronisationIntent<J> synchroniseDisjointConceptToSemantic(){
            try {
                return getDisjointConcept().synchroniseTo( queryDisjointConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link Semantic.Axioms#synchroniseFrom(Axioms)} with {@link #queryDisjointConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointConcept()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the disjointed classes of an OWL class
         * with {@code this} structure.
         */
        default Axioms.SynchronisationIntent<J> synchroniseDisjointConceptFromSemantic(){
            try{
                return getDisjointConcept().synchroniseFrom( queryDisjointConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for equivalent classes of an ontological class.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the equivalent classes of a specific class
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Concept <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     *           (it represents also the of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Concept} descriptor instantiated during
     *           {@link #buildEquivalentConcept()} through {@link #getNewEquivalentConcept(Object, Object)}.
     */
    interface Equivalent<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override  // see documentation on Semantic.Descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<J> from = synchroniseEquivalentConceptFromSemantic();
                if ( from != null) {
                    getEquivalentConcept().addAll(from.getToAdd());
                    getEquivalentConcept().removeAll(from.getToRemove());
                }
                return getIntent( from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
         * represents the classes equivalent to this {@link Concept}.
         * Each of those {@link Concept}s are instantiated
         * through the method {@link #getNewEquivalentConcept(Object, Object)};
         * this is called for all {@link #getEquivalentConcept()}.
         * @return the set of {@link Concept}s that describes the
         * entities equivalent to {@code this} described ontological class.
         */
        default Set<D> buildEquivalentConcept(){
            Set<D> out = new HashSet<>();
            for( J cl : getEquivalentConcept()){
                D built = getNewEquivalentConcept( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildEquivalentConcept()} and
         * its purpose is to instantiate a new {@link Concept} to represent
         * an class equivalent from {@code this} {@link Concept} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Concept}.
         * @param ontology the ontology in which ground the new {@link Concept}.
         * @return a new {@link Semantic.Descriptor} for all the classes
         * equivalent to {@code this} descriptor.
         */
        D getNewEquivalentConcept(J instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the equivalent classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the equivalent classes to {@code this} object
         */
        Axioms<J> getEquivalentConcept();

        /**
         * Queries to the OWL representation for the equivalent classes of {@code this} class.
         * @return a new {@link Semantic.Axioms} contained the equivalent classes to
         * the OWL structure of {@link #getInstance()}.
         */
        Axioms<J> queryEquivalentConcept();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #queryEquivalentConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentConcept()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent classes of an OWL class.
         */
        default Axioms.SynchronisationIntent<J> synchroniseEquivalentConceptToSemantic(){
            try {
                return getEquivalentConcept().synchroniseTo( queryEquivalentConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link Semantic.Axioms#synchroniseFrom(Axioms)} with {@link #queryEquivalentConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentConcept()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the equivalent classes of an OWL class
         * with {@code this} structure.
         */
        default Axioms.SynchronisationIntent<J> synchroniseEquivalentConceptFromSemantic(){
            try{
                return getEquivalentConcept().synchroniseFrom( queryEquivalentConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for sub classes of an ontological class.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the sub classes of a specific class
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Concept <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     *           (it represents also the of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Concept} descriptor instantiated during
     *           {@link #buildSubConcept()}  through {@link #getNewSubConcept(Object, Object)}.
     */
    interface Sub<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override // see documentation on Semantic.Descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<J> from = synchroniseSubConceptFromSemantic();
                if ( from != null) {
                    getSubConcept().addAll(from.getToAdd());
                    getSubConcept().removeAll(from.getToRemove());
                }
                return getIntent(from);
            }catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
         * represents the sub classes of this {@link Concept}.
         * Each of those {@link Concept}s are instantiated
         * through the method {@link #getNewSubConcept(Object, Object)};
         * this is called for all {@link #getSubConcept()}.
         * @return the set of {@link Concept}s that describes the
         * sub entities of {@code this} described ontological class.
         */
        default Set<D> buildSubConcept(){
            Set<D> out = new HashSet<>();
            for( J cl : getSubConcept()){
                D built = getNewSubConcept( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildSubConcept()} and
         * its purpose is to instantiate a new {@link Concept} to represent
         * an sub class of {@code this} {@link Concept} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Concept}.
         * @param ontology the ontology in which ground the new {@link Concept}.
         * @return a new {@link Semantic.Descriptor} for all the sub classes
         * of {@code this} descriptor.
         */
        D getNewSubConcept(J instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the sub classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the sub classes of {@code this} class.
         */
        Axioms<J> getSubConcept();

        /**
         * Queries to the OWL representation for the sub classes of {@code this} class.
         * @return a new {@link Semantic.Axioms} contained the sub classes to
         * the OWL structure of {@link #getInstance()}.
         */
        Axioms<J> querySubConcept();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #querySubConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSubConcept()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the sub classes of an OWL class.
         */
        default Axioms.SynchronisationIntent<J> synchroniseSubConceptToSemantic(){
            try {
                return getSubConcept().synchroniseTo( querySubConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link Semantic.Axioms#synchroniseFrom(Axioms)} with {@link #querySubConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getSubConcept()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the sub classes of an OWL class
         * with {@code this} structure.
         */
        default Axioms.SynchronisationIntent<J> synchroniseSubConceptFromSemantic(){
            try{
                return getSubConcept().synchroniseFrom( querySubConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for super classes of an ontological class.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the super classes of a specific class
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Concept <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     *           (it represents also the of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Concept} descriptor instantiated during
     *           {@link #buildSuperConcept()} through {@link #getNewSuperConcept(Object, Object)}.
     */
    interface Super<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override // see documentation on Semantic.Descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<J> from = synchroniseSuperConceptFromSemantic();
                if ( from != null) {
                    getSuperConcept().addAll(from.getToAdd());
                    getSuperConcept().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
         * represents the super classes of this {@link Concept}.
         * Each of those {@link Concept}s are instantiated
         * through the method {@link #getNewSuperConcept(Object, Object)};
         * this is called for all {@link #getSuperConcept()}.
         * @return the set of {@link Concept}s that describes the
         * super entities of {@code this} described ontological class.
         */
        default Set<D> buildSuperConcept(){
            Set<D> out = new HashSet<>();
            for( J cl : getSuperConcept()){
                D built = getNewSuperConcept( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildSuperConcept()} and
         * its purpose is to instantiate a new {@link Concept} to represent
         * an super class of {@code this} {@link Concept} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Concept}.
         * @param ontology the ontology in which ground the new {@link Concept}.
         * @return a new {@link Semantic.Descriptor} for all the super classes
         * of {@code this} descriptor.
         */
        D getNewSuperConcept(J instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the super classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the super classes of {@code this} class.
         */
        Axioms<J> getSuperConcept();

        /**
         * Queries to the OWL representation for the super classes of {@code this} class.
         * @return a new {@link Semantic.Axioms} contained the super classes to
         * the OWL structure of {@link #getInstance()}.
         */
        Axioms<J> querySuperConcept();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #querySuperConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSuperConcept()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the super classes of an OWL class.
         */
        default Axioms.SynchronisationIntent<J> synchroniseSuperConceptToSemantic(){
            try {
                return getSuperConcept().synchroniseTo( querySuperConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link Semantic.Axioms#synchroniseFrom(Axioms)} with {@link #querySuperConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getSuperConcept()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the super classes of an OWL class
         * with {@code this} structure.
         */
        default Axioms.SynchronisationIntent<J> synchroniseSuperConceptFromSemantic(){
            try{
                return getSuperConcept().synchroniseFrom( querySuperConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for the definition of an ontological class.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the definition of a specific class
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     *     Definition is defined as conjunction of restriction properties that
     *     creates super class (i.e.: sub class of) of the described ontological class.
     *     More in details, such a restriction can be of the type:
     *     <ul>
     *     <li><b>equivalent restriction</b>: a class is defined as another.</li>
     *     <li><b>data restriction</b>: a class is defined to have data properties into a given range.
     *                       It is also possible to describe the cardinality of this restriction to be:
     *                       existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     *     <li><b>object restriction</b>: a class is defined to have object properties into a given class.
     *                       It is also possible to describe the cardinality of this restriction to be:
     *                       existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     *     </ul>
     *     This description is not considered from an OOP point of view (it is not possible
     *     to {@code build} them) since their are not entities that fall back in the {@link Semantic}
     *     representations.
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Concept <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     * @param <Y> the type of restriction that define the class.
     *           (it represents the of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     */
    interface Define<O,J,Y>
            extends Concept<O,J> {

        @Override // see documentation on Semantic.Descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<Y> from = synchroniseDefinitionConceptFromSemantic();
                if ( from != null) {
                    getDefinitionConcept().addAll(from.getToAdd());
                    getDefinitionConcept().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Returns the {@link Semantic.Axioms} that describes all the definition restriction of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing {@code this} class.
         */
        Axioms<Y> getDefinitionConcept();

        /**
         * Queries to the OWL representation for the definition of {@code this} class.
         * @return a new {@link Semantic.Axioms} contained the class definition to
         * the OWL structure of {@link #getInstance()}.
         */
        Axioms<Y> queryDefinitionConcept();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #queryDefinitionConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDefinitionConcept()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the class definition of an OWL class.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseDefinitionConceptToSemantic(){
            try {
                return getDefinitionConcept().synchroniseTo( queryDefinitionConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link Semantic.Axioms#synchroniseFrom(Axioms)} with {@link #queryDefinitionConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getDefinitionConcept()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the class definition of an OWL class
         * with {@code this} structure.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseDefinitionConceptFromSemantic(){
            try{
                return getDefinitionConcept().synchroniseFrom( queryDefinitionConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

}
