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
     *            {@link #buildEquivalentConcept()} through {@link #getNewEquivalentConcept(Object, Object)}.
     */
    interface Equivalent<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override  // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseEquivalentConceptFromSemantic();
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
         * Create an {@link Axiom.Descriptor} set where each element
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
        D getNewEquivalentConcept(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the equivalent classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the equivalent classes to {@code this} object
         */
        EntitySet<J> getEquivalentConcept();

        /**
         * Queries to the OWL representation for the equivalent classes of {@code this} class.
         * @return a new {@link EntitySet} contained the equivalent classes to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> queryEquivalentConcept();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryEquivalentConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentConcept()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentConceptToSemantic(){
            try {
                return getEquivalentConcept().synchroniseTo( queryEquivalentConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryEquivalentConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentConcept()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the equivalent classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentConceptFromSemantic(){
            try{
                return getEquivalentConcept().synchroniseFrom( queryEquivalentConcept());
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
     *            {@link #buildDisjointConcept()} ()} through {@link #getNewDisjointConcept(Object, Object)}.
     */
    interface Disjoint<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseDisjointConceptFromSemantic();
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
         * Create an {@link Axiom.Descriptor} set where each element
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
        D getNewDisjointConcept(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the disjointed classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the disjointed classes from {@code this} object
         */
        EntitySet<J> getDisjointConcept();

        /**
         * Queries to the OWL representation of the disjointed classes for {@code this} class.
         * @return a new {@link EntitySet} contained the disjointed classes from
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> queryDisjointConcept();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDisjointConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointConcept()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the disjointed classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointConceptToSemantic(){
            try {
                return getDisjointConcept().synchroniseTo( queryDisjointConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryDisjointConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointConcept()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the disjointed classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointConceptFromSemantic(){
            try{
                return getDisjointConcept().synchroniseFrom( queryDisjointConcept());
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
     *            {@link #buildSubConcept()} ()} through {@link #getNewSubConcept(Object, Object)}.
     */
    interface Sub<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSubConceptFromSemantic();
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
         * Create an {@link Axiom.Descriptor} set where each element
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
        D getNewSubConcept(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the sub classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the sub classes of {@code this} class.
         */
        EntitySet<J> getSubConcept();

        /**
         * Queries to the OWL representation for the sub classes of {@code this} class.
         * @return a new {@link EntitySet} contained the sub classes to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> querySubConcept();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySubConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSubConcept()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the sub classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubConceptToSemantic(){
            try {
                return getSubConcept().synchroniseTo( querySubConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #querySubConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getSubConcept()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the sub classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubConceptFromSemantic(){
            try{
                return getSubConcept().synchroniseFrom( querySubConcept());
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
     *            {@link #buildSuperConcept()} ()} through {@link #getNewSuperConcept(Object, Object)}.
     */
    interface Super<O,J,D extends Concept<O,J>>
            extends Concept<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSuperConceptFromSemantic();
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
         * Create an {@link Axiom.Descriptor} set where each element
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
        D getNewSuperConcept(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the super classes of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the super classes of {@code this} class.
         */
        EntitySet<J> getSuperConcept();

        /**
         * Queries to the OWL representation for the super classes of {@code this} class.
         * @return a new {@link EntitySet} contained the super classes to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> querySuperConcept();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySuperConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSuperConcept()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the super classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperConceptToSemantic(){
            try {
                return getSuperConcept().synchroniseTo( querySuperConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #querySuperConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getSuperConcept()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the super classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperConceptFromSemantic(){
            try{
                return getSuperConcept().synchroniseFrom( querySuperConcept());
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
     *            {@link #buildIndividualInstance()} ()} through {@link #getNewIndividualInstance(Object, Object)}.
     */
    interface Instance<O,J,Y,D extends Individual<O,Y>>
            extends Concept<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseIndividualInstanceFromSemantic();
                if ( from != null) {
                    getIndividualInstance().addAll(from.getToAdd());
                    getIndividualInstance().removeAll(from.getToRemove());
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
         * through the method {@link #getNewIndividualInstance(Object, Object)};
         * this is called for all {@link #getIndividualInstance()}.
         * @return the set of {@link Individual}s that describes the
         * entities belonging to {@code this} described ontological class.
         */
        default Set<D> buildIndividualInstance(){
            Set<D> out = new HashSet<>();
            for( Y cl : getIndividualInstance()){
                D built = getNewIndividualInstance( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildIndividualInstance()} and
         * its purpose is to instantiate a new {@link Individual} to represent
         * an individualDescriptor classified in {@code this} {@link Concept} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Individual}.
         * @param ontology the ontology in which ground the new {@link Individual}.
         * @return a new {@link Axiom.Descriptor} for all the individuals
         * classified by {@code this} descriptor.
         */
        D getNewIndividualInstance(Y instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the individualDescriptor classified
         * in {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing the individuals classified in {@code this} object
         */
        EntitySet<Y> getIndividualInstance();

        /**
         * Queries to the OWL representation of the individualDescriptor that are classified in {@code this} class.
         * @return a new {@link EntitySet} contained the individualDescriptor classified by
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<Y> queryIndividualInstance();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryIndividualInstance()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getIndividualInstance()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the individualDescriptor classified in an OWL class.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseIndividualInstanceToSemantic(){
            try {
                return getIndividualInstance().synchroniseTo( queryIndividualInstance());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryIndividualInstance()}
         * as input parameter. This computes the changes to be performed into the {@link #getIndividualInstance()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the individualDescriptor of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseIndividualInstanceFromSemantic(){
            try{
                return getIndividualInstance().synchroniseFrom( queryIndividualInstance());
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
                EntitySet.SynchronisationIntent<Y> from = synchroniseDefinitionConceptFromSemantic();
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
         * Returns the {@link EntitySet} that describes all the definition restriction of
         * {@code this} {@link Concept} from a no OOP point of view.
         * @return the entities describing {@code this} class.
         */
        EntitySet<Y> getDefinitionConcept();

        /**
         * Queries to the OWL representation for the definition of {@code this} class.
         * @return a new {@link EntitySet} contained the class definition to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<Y> queryDefinitionConcept();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDefinitionConcept()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDefinitionConcept()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the class definition of an OWL class.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDefinitionConceptToSemantic(){
            try {
                return getDefinitionConcept().synchroniseTo( queryDefinitionConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryDefinitionConcept()}
         * as input parameter. This computes the changes to be performed into the {@link #getDefinitionConcept()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the class definition of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDefinitionConceptFromSemantic(){
            try{
                return getDefinitionConcept().synchroniseFrom( queryDefinitionConcept());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
