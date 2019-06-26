package it.emarolab.owloop.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This interface is a part of the core of OWLOOP architecture.
 * It contains interfaces of the expressions that can be applied to
 * the OWL entity OWLClass (i.e., {@link org.semanticweb.owlapi.model.OWLClass}). <p>
 * The expressions are the following:
 *
 * <ul>
 * <li><b>{@link Equivalent}</b>:  this expression describes that a Class is equivalent to another Class.</li>
 * <li><b>{@link Disjoint}</b>:    this expression describes that a Class is disjoint to another Class.</li>
 * <li><b>{@link Sub}</b>:         this expression describes that a Class subsumes another Class.</li>
 * <li><b>{@link Super}</b>:       this expression describes that a Class is a super-class of another Class.</li>
 * <li><b>{@link Instance}</b>:    this expression describes an Individual of a Class.</li>
 * <li><b>{@link Definition}</b>:  this expression describes the definition of a Class..</li>
 * </ul>
 *
 * <p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
 * </small></div>
 *
 * @param <O> is the ontology.
 * @param <J> is the ground.
 */
public interface Concept<O,J>
        extends Axiom.Descriptor<O,J>{

    /**
     * Implementation of this interface enables a {@link Concept} to have the {@link Equivalent} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link Concept} descriptor instantiated during
     *            {@link #buildEquivalentConcept()} through {@link #getEquivalentConceptDescriptor(Object, Object)}.
     */
    interface Equivalent<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override  // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseEquivalentConceptFromExpressionAxioms();
                if ( from != null) {
                    getEquivalentConcepts().addAll(from.getToAdd());
                    getEquivalentConcepts().removeAll(from.getToRemove());
                }
                return getIntent( from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the classes equivalent to this {@link Concept}.
         * Each of those {@link Concept}s are instantiated
         * through the method {@link #getEquivalentConceptDescriptor(Object, Object)};
         * this is called for all {@link #getEquivalentConcepts()}.
         * @return the set of {@link Concept}s that describes the
         * entities equivalent to {@code this} described ontological class.
         */
        default Set<D> buildEquivalentConcept(){
            Set<D> out = new HashSet<>();
            for( J cl : getEquivalentConcepts()){
                D built = getEquivalentConceptDescriptor( cl, getOntology());
                built.readExpressionAxioms();
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
         * @return a new {@link Axiom.Descriptor} for all the classes
         * equivalent to {@code this} descriptor.
         */
        D getEquivalentConceptDescriptor(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the equivalent classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the equivalent classes to {@code this} object
         */
        EntitySet<J> getEquivalentConcepts();

        /**
         * Queries to the OWL representation for the equivalent classes of {@code this} class.
         * @return a new {@link EntitySet} contained the equivalent classes to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> queryEquivalentConcept();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryEquivalentConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentConcepts()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentConceptToExpressionAxioms(){
            try {
                return getEquivalentConcepts().synchroniseTo( queryEquivalentConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryEquivalentConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentConcepts()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the equivalent classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentConceptFromExpressionAxioms(){
            try{
                return getEquivalentConcepts().synchroniseFrom( queryEquivalentConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Concept} to have the {@link Disjoint} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link Concept} descriptor instantiated during
     *            {@link #buildDisjointConcept()} ()} through {@link #getDisjointConceptDescriptor(Object, Object)}.
     */
    interface Disjoint<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseDisjointConceptFromExpressionAxioms();
                if ( from != null) {
                    getDisjointConcepts().addAll(from.getToAdd());
                    getDisjointConcepts().removeAll(from.getToRemove());
                }
                return getIntent( from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }

        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the classes disjointed to this {@link Concept}.
         * Each of those {@link Concept}s are instantiated
         * through the method {@link #getDisjointConceptDescriptor(Object, Object)};
         * this is called for all {@link #getDisjointConcepts()}.
         * @return the set of {@link Concept}s that describes the
         * entities disjointed to {@code this} described ontological class.
         */
        default Set<D> buildDisjointConcept(){
            Set<D> out = new HashSet<>();
            for( J cl : getDisjointConcepts()){
                D built = getDisjointConceptDescriptor( cl, getOntology());
                built.readExpressionAxioms();
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
         * @return a new {@link Axiom.Descriptor} for all the classes
         * disjointed to {@code this} descriptor.
         */
        D getDisjointConceptDescriptor(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the disjointed classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the disjointed classes from {@code this} object
         */
        EntitySet<J> getDisjointConcepts();

        /**
         * Queries to the OWL representation of the disjointed classes for {@code this} class.
         * @return a new {@link EntitySet} contained the disjointed classes from
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> queryDisjointConcept();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDisjointConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointConcepts()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the disjointed classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointConceptToExpressionAxioms(){
            try {
                return getDisjointConcepts().synchroniseTo( queryDisjointConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryDisjointConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointConcepts()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the disjointed classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointConceptFromExpressionAxioms(){
            try{
                return getDisjointConcepts().synchroniseFrom( queryDisjointConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Concept} to have the {@link Sub} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link Concept} descriptor instantiated during
     *            {@link #buildSubConcept()} ()} through {@link #getSubConceptDescriptor(Object, Object)}.
     */
    interface Sub<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSubConceptFromExpressionAxioms();
                if ( from != null) {
                    getSubConcepts().addAll(from.getToAdd());
                    getSubConcepts().removeAll(from.getToRemove());
                }
                return getIntent(from);
            }catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the sub classes of this {@link Concept}.
         * Each of those {@link Concept}s are instantiated
         * through the method {@link #getSubConceptDescriptor(Object, Object)};
         * this is called for all {@link #getSubConcepts()}.
         * @return the set of {@link Concept}s that describes the
         * sub entities of {@code this} described ontological class.
         */
        default Set<D> buildSubConcept(){
            Set<D> out = new HashSet<>();
            for( J cl : getSubConcepts()){
                D built = getSubConceptDescriptor( cl, getOntology());
                built.readExpressionAxioms();
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
         * @return a new {@link Axiom.Descriptor} for all the sub classes
         * of {@code this} descriptor.
         */
        D getSubConceptDescriptor(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the sub classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the sub classes of {@code this} class.
         */
        EntitySet<J> getSubConcepts();

        /**
         * Queries to the OWL representation for the sub classes of {@code this} class.
         * @return a new {@link EntitySet} contained the sub classes to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> querySubConcept();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySubConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSubConcepts()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the sub classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubConceptToExpressionAxioms(){
            try {
                return getSubConcepts().synchroniseTo( querySubConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #querySubConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getSubConcepts()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the sub classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubConceptFromExpressionAxioms(){
            try{
                return getSubConcepts().synchroniseFrom( querySubConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Concept} to have the {@link Super} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link Concept} descriptor instantiated during
     *            {@link #buildSuperConcept()} ()} through {@link #getSuperConceptDescriptor(Object, Object)}.
     */
    interface Super<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSuperConceptFromExpressionAxioms();
                if ( from != null) {
                    getSuperConcepts().addAll(from.getToAdd());
                    getSuperConcepts().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the super classes of this {@link Concept}.
         * Each of those {@link Concept}s are instantiated
         * through the method {@link #getSuperConceptDescriptor(Object, Object)};
         * this is called for all {@link #getSuperConcepts()}.
         * @return the set of {@link Concept}s that describes the
         * super entities of {@code this} described ontological class.
         */
        default Set<D> buildSuperConcept(){
            Set<D> out = new HashSet<>();
            for( J cl : getSuperConcepts()){
                D built = getSuperConceptDescriptor( cl, getOntology());
                built.readExpressionAxioms();
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
         * @return a new {@link Axiom.Descriptor} for all the super classes
         * of {@code this} descriptor.
         */
        D getSuperConceptDescriptor(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the super classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the super classes of {@code this} class.
         */
        EntitySet<J> getSuperConcepts();

        /**
         * Queries to the OWL representation for the super classes of {@code this} class.
         * @return a new {@link EntitySet} contained the super classes to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> querySuperConcept();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySuperConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSuperConcepts()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the super classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperConceptToExpressionAxioms(){
            try {
                return getSuperConcepts().synchroniseTo( querySuperConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #querySuperConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getSuperConcepts()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the super classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperConceptFromExpressionAxioms(){
            try{
                return getSuperConcepts().synchroniseFrom( querySuperConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Concept} to have the {@link Instance} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <Y> the type of restriction for the {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link Concept} descriptor instantiated during
     *            {@link #buildIndividualInstances()} ()} through {@link #getIndividualDescriptor(Object, Object)}.
     */
    interface Instance<O,J,Y,D extends Individual<O,Y>>
            extends Concept<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseIndividualInstanceFromExpressionAxioms();
                if ( from != null) {
                    getIndividualInstances().addAll(from.getToAdd());
                    getIndividualInstances().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent(null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the individualDescriptor classified to belonging to this {@link Concept}.
         * Each of {@link Individual}s are instantiated
         * through the method {@link #getIndividualDescriptor(Object, Object)};
         * this is called for all {@link #getIndividualInstances()}.
         * @return the set of {@link Individual}s that describes the
         * entities belonging to {@code this} described ontological class.
         */
        default Set<D> buildIndividualInstances(){
            Set<D> out = new HashSet<>();
            for( Y cl : getIndividualInstances()){
                D built = getIndividualDescriptor( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildIndividualInstances()} and
         * its purpose is to instantiate a new {@link Individual} to represent
         * an individualDescriptor classified in {@code this} {@link Concept} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Individual}.
         * @param ontology the ontology in which ground the new {@link Individual}.
         * @return a new {@link Axiom.Descriptor} for all the individuals
         * classified by {@code this} descriptor.
         */
        D getIndividualDescriptor(Y instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the individualDescriptor classified
         * in {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the individuals classified in {@code this} object
         */
        EntitySet<Y> getIndividualInstances();

        /**
         * Queries to the OWL representation of the individualDescriptor that are classified in {@code this} class.
         * @return a new {@link EntitySet} contained the individualDescriptor classified by
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<Y> queryIndividualInstances();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryIndividualInstances()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getIndividualInstances()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the individualDescriptor classified in an OWL class.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseIndividualInstanceToExpressionAxioms(){
            try {
                return getIndividualInstances().synchroniseTo( queryIndividualInstances());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryIndividualInstances()}
         * as input parameter. This computes the changes to be performed into the {@link #getIndividualInstances()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the individualDescriptor of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseIndividualInstanceFromExpressionAxioms(){
            try{
                return getIndividualInstances().synchroniseFrom( queryIndividualInstances());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Concept} to have the {@link Definition} expression.<p>
     * Definition is defined as a conjunction of restriction properties that
     * create a super class of the described ontological class.
     * The restriction can be of the following types:
     *
     * <ul>
     * <li><b>equivalent restriction</b>:   a class is defined as another.</li>
     * <li><b>data restriction</b>:         a class is defined to have data properties into a given range.
     *                                      It is also possible to describe the cardinality of this restriction to be:
     *                                      existential ({@code some}), universal ({@code all}), minimum, maximum and exact.</li>
     * <li><b>object restriction</b>:       a class is defined to have object properties into a given class.
     *                                      It is also possible to describe the cardinality of this restriction to be:
     *                                      existential ({@code some}), universal ({@code all}), minimum, maximum and exact.</li>
     * </ul>
     *
     * If this expression is used, it is not possible to use the {@code build()} operation since
     * the restrictions are not simple OWL entities like OWLClass, OWLIndividual, OWLObjectProperty and OWLDataProperty.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <Y> the type of restriction for the {@link EntitySet} managed by this {@link Descriptor}.
     */
    interface Definition<O,J,Y>
            extends Concept<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseDefinitionConceptFromExpressionAxioms();
                if ( from != null) {
                    getDefinitionConcepts().addAll(from.getToAdd());
                    getDefinitionConcepts().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Returns the {@link EntitySet} that describes all the definition restriction of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing {@code this} class.
         */
        EntitySet<Y> getDefinitionConcepts();

        /**
         * Queries to the OWL representation for the definition of {@code this} class.
         * @return a new {@link EntitySet} contained the class definition to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<Y> queryDefinitionConcepts();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDefinitionConcepts()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDefinitionConcepts()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the class definition of an OWL class.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDefinitionConceptToExpressionAxioms(){
            try {
                return getDefinitionConcepts().synchroniseTo( queryDefinitionConcepts());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryDefinitionConcepts()}
         * as input parameter. This computes the changes to be performed into the {@link #getDefinitionConcepts()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the class definition of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDefinitionConceptFromExpressionAxioms(){
            try{
                return getDefinitionConcepts().synchroniseFrom( queryDefinitionConcepts());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
