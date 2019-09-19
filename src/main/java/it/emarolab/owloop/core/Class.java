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
 * <li><b>{@link Restriction}</b>:  this expression describes the definition of a Class..</li>
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
public interface Class<O,J>
        extends Axiom.Descriptor<O,J>{

    /**
     * Implementation of this interface enables a {@link Class} to have the {@link Equivalent} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link Class} descriptor instantiated during
     *            {@link #buildEquivalentClasses()} through {@link #getEquivalentClassDescriptor(Object, Object)}.
     */
    interface Equivalent<O,J,D extends Class<O,J>>
            extends Class<O,J> {

        @Override  // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseEquivalentClassesFromExpressionAxioms();
                if ( from != null) {
                    getEquivalentClasses().addAll(from.getToAdd());
                    getEquivalentClasses().removeAll(from.getToRemove());
                }
                return getIntent( from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the classes equivalent to this {@link Class}.
         * Each of those {@link Class}s are instantiated
         * through the method {@link #getEquivalentClassDescriptor(Object, Object)};
         * this is called for all {@link #getEquivalentClasses()}.
         * @return the set of {@link Class}s that describes the
         * entities equivalent to {@code this} described ontological class.
         */
        default Set<D> buildEquivalentClasses(){
            Set<D> out = new HashSet<>();
            for( J cl : getEquivalentClasses()){
                D built = getEquivalentClassDescriptor( cl, getOntologyReference());
                built.readAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildEquivalentClasses()} and
         * its purpose is to instantiate a new {@link Class} to represent
         * an class equivalent from {@code this} {@link Class} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Class}.
         * @param ontology the ontology in which ground the new {@link Class}.
         * @return a new {@link Axiom.Descriptor} for all the classes
         * equivalent to {@code this} descriptor.
         */
        D getEquivalentClassDescriptor(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the equivalent classes of
         * {@code this} {@link Class} from a no OOP point of view.
         * @return the entities describing the equivalent classes to {@code this} object
         */
        EntitySet<J> getEquivalentClasses();

        /**
         * Queries to the OWL representation for the equivalent classes of {@code this} class.
         * @return a new {@link EntitySet} contained the equivalent classes to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> queryEquivalentClasses();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryEquivalentClasses()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentClasses()}. This should
         * be done by {@link #writeAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentClassesToExpressionAxioms(){
            try {
                return getEquivalentClasses().synchroniseTo( queryEquivalentClasses());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryEquivalentClasses()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentClasses()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readAxioms()}.
         * @return the changes to be done to synchronise the equivalent classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentClassesFromExpressionAxioms(){
            try{
                return getEquivalentClasses().synchroniseFrom( queryEquivalentClasses());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Class} to have the {@link Disjoint} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link Class} descriptor instantiated during
     *            {@link #buildDisjointClasses()} ()} through {@link #getDisjointClassDescriptor(Object, Object)}.
     */
    interface Disjoint<O,J,D extends Class<O,J>>
            extends Class<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseDisjointClassesFromExpressionAxioms();
                if ( from != null) {
                    getDisjointClasses().addAll(from.getToAdd());
                    getDisjointClasses().removeAll(from.getToRemove());
                }
                return getIntent( from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }

        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the classes disjointed to this {@link Class}.
         * Each of those {@link Class}s are instantiated
         * through the method {@link #getDisjointClassDescriptor(Object, Object)};
         * this is called for all {@link #getDisjointClasses()}.
         * @return the set of {@link Class}s that describes the
         * entities disjointed to {@code this} described ontological class.
         */
        default Set<D> buildDisjointClasses(){
            Set<D> out = new HashSet<>();
            for( J cl : getDisjointClasses()){
                D built = getDisjointClassDescriptor( cl, getOntologyReference());
                built.readAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildDisjointClasses()} and
         * its purpose is to instantiate a new {@link Class} to represent
         * an class disjointed from {@code this} {@link Class} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Class}.
         * @param ontology the ontology in which ground the new {@link Class}.
         * @return a new {@link Axiom.Descriptor} for all the classes
         * disjointed to {@code this} descriptor.
         */
        D getDisjointClassDescriptor(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the disjointed classes of
         * {@code this} {@link Class} from a no OOP point of view.
         * @return the entities describing the disjointed classes from {@code this} object
         */
        EntitySet<J> getDisjointClasses();

        /**
         * Queries to the OWL representation of the disjointed classes for {@code this} class.
         * @return a new {@link EntitySet} contained the disjointed classes from
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> queryDisjointClasses();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDisjointClasses()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointClasses()}. This should
         * be done by {@link #writeAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the disjointed classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointClassesToExpressionAxioms(){
            try {
                return getDisjointClasses().synchroniseTo( queryDisjointClasses());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryDisjointClasses()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointClasses()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readAxioms()}.
         * @return the changes to be done to synchronise the disjointed classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointClassesFromExpressionAxioms(){
            try{
                return getDisjointClasses().synchroniseFrom( queryDisjointClasses());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Class} to have the {@link Sub} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link Class} descriptor instantiated during
     *            {@link #buildSubClasses()} ()} through {@link #getSubClassDescriptor(Object, Object)}.
     */
    interface Sub<O,J,D extends Class<O,J>>
            extends Class<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSubClassesFromExpressionAxioms();
                if ( from != null) {
                    getSubClasses().addAll(from.getToAdd());
                    getSubClasses().removeAll(from.getToRemove());
                }
                return getIntent(from);
            }catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the sub classes of this {@link Class}.
         * Each of those {@link Class}s are instantiated
         * through the method {@link #getSubClassDescriptor(Object, Object)};
         * this is called for all {@link #getSubClasses()}.
         * @return the set of {@link Class}s that describes the
         * sub entities of {@code this} described ontological class.
         */
        default Set<D> buildSubClasses(){
            Set<D> out = new HashSet<>();
            for( J cl : getSubClasses()){
                D built = getSubClassDescriptor( cl, getOntologyReference());
                built.readAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildSubClasses()} and
         * its purpose is to instantiate a new {@link Class} to represent
         * an sub class of {@code this} {@link Class} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Class}.
         * @param ontology the ontology in which ground the new {@link Class}.
         * @return a new {@link Axiom.Descriptor} for all the sub classes
         * of {@code this} descriptor.
         */
        D getSubClassDescriptor(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the sub classes of
         * {@code this} {@link Class} from a no OOP point of view.
         * @return the entities describing the sub classes of {@code this} class.
         */
        EntitySet<J> getSubClasses();

        /**
         * Queries to the OWL representation for the sub classes of {@code this} class.
         * @return a new {@link EntitySet} contained the sub classes to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> querySubClasses();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySubClasses()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSubClasses()}. This should
         * be done by {@link #writeAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the sub classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubClassesToExpressionAxioms(){
            try {
                return getSubClasses().synchroniseTo( querySubClasses());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #querySubClasses()}
         * as input parameter. This computes the changes to be performed into the {@link #getSubClasses()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readAxioms()}.
         * @return the changes to be done to synchronise the sub classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubClassesFromExpressionAxioms(){
            try{
                return getSubClasses().synchroniseFrom( querySubClasses());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Class} to have the {@link Super} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link Class} descriptor instantiated during
     *            {@link #buildSuperClasses()} ()} through {@link #getSuperClassDescriptor(Object, Object)}.
     */
    interface Super<O,J,D extends Class<O,J>>
            extends Class<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSuperClassesFromExpressionAxioms();
                if ( from != null) {
                    getSuperClasses().addAll(from.getToAdd());
                    getSuperClasses().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the super classes of this {@link Class}.
         * Each of those {@link Class}s are instantiated
         * through the method {@link #getSuperClassDescriptor(Object, Object)};
         * this is called for all {@link #getSuperClasses()}.
         * @return the set of {@link Class}s that describes the
         * super entities of {@code this} described ontological class.
         */
        default Set<D> buildSuperClasses(){
            Set<D> out = new HashSet<>();
            for( J cl : getSuperClasses()){
                D built = getSuperClassDescriptor( cl, getOntologyReference());
                built.readAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildSuperClasses()} and
         * its purpose is to instantiate a new {@link Class} to represent
         * an super class of {@code this} {@link Class} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Class}.
         * @param ontology the ontology in which ground the new {@link Class}.
         * @return a new {@link Axiom.Descriptor} for all the super classes
         * of {@code this} descriptor.
         */
        D getSuperClassDescriptor(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the super classes of
         * {@code this} {@link Class} from a no OOP point of view.
         * @return the entities describing the super classes of {@code this} class.
         */
        EntitySet<J> getSuperClasses();

        /**
         * Queries to the OWL representation for the super classes of {@code this} class.
         * @return a new {@link EntitySet} contained the super classes to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<J> querySuperClasses();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySuperClasses()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSuperClasses()}. This should
         * be done by {@link #writeAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the super classes of an OWL class.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperClassesToExpressionAxioms(){
            try {
                return getSuperClasses().synchroniseTo( querySuperClasses());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #querySuperClasses()}
         * as input parameter. This computes the changes to be performed into the {@link #getSuperClasses()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readAxioms()}.
         * @return the changes to be done to synchronise the super classes of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperClassesFromExpressionAxioms(){
            try{
                return getSuperClasses().synchroniseFrom( querySuperClasses());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Class} to have the {@link Instance} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <Y> the type of restriction for the {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link Class} descriptor instantiated during
     *            {@link #buildIndividuals()} ()} through {@link #getIndividualDescriptor(Object, Object)}.
     */
    interface Instance<O,J,Y,D extends Individual<O,Y>>
            extends Class<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readAxioms(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseIndividualsFromExpressionAxioms();
                if ( from != null) {
                    getIndividuals().addAll(from.getToAdd());
                    getIndividuals().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent(null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the individualDescriptor classified to belonging to this {@link Class}.
         * Each of {@link Individual}s are instantiated
         * through the method {@link #getIndividualDescriptor(Object, Object)};
         * this is called for all {@link #getIndividuals()}.
         * @return the set of {@link Individual}s that describes the
         * entities belonging to {@code this} described ontological class.
         */
        default Set<D> buildIndividuals(){
            Set<D> out = new HashSet<>();
            for( Y cl : getIndividuals()){
                D built = getIndividualDescriptor( cl, getOntologyReference());
                built.readAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildIndividuals()} and
         * its purpose is to instantiate a new {@link Individual} to represent
         * an individualDescriptor classified in {@code this} {@link Class} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Individual}.
         * @param ontology the ontology in which ground the new {@link Individual}.
         * @return a new {@link Axiom.Descriptor} for all the individuals
         * classified by {@code this} descriptor.
         */
        D getIndividualDescriptor(Y instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the individualDescriptor classified
         * in {@code this} {@link Class} from a no OOP point of view.
         * @return the entities describing the individuals classified in {@code this} object
         */
        EntitySet<Y> getIndividuals();

        /**
         * Queries to the OWL representation of the individualDescriptor that are classified in {@code this} class.
         * @return a new {@link EntitySet} contained the individualDescriptor classified by
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<Y> queryIndividuals();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryIndividuals()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getIndividuals()}. This should
         * be done by {@link #writeAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the individualDescriptor classified in an OWL class.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseIndividualsToExpressionAxioms(){
            try {
                return getIndividuals().synchroniseTo( queryIndividuals());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryIndividuals()}
         * as input parameter. This computes the changes to be performed into the {@link #getIndividuals()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readAxioms()}.
         * @return the changes to be done to synchronise the individualDescriptor of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseIndividualsFromExpressionAxioms(){
            try{
                return getIndividuals().synchroniseFrom( queryIndividuals());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Class} to have the {@link Restriction} expression.<p>
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
     * Also, the current implementation support only one restriction axiom for defining each class.
     * The axiom can be made of a union of `class` expressions, as well as `some`, `all`, `min`, `max`,
     * and `exact` data or object property expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <Y> the type of restriction for the {@link EntitySet} managed by this {@link Descriptor}.
     */
    interface Restriction<O,J,Y>
            extends Class<O,J> {

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readAxioms(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseEquivalentRestrictionsFromExpressionAxioms();
                if ( from != null) {
                    getEquivalentRestrictions().addAll(from.getToAdd());
                    getEquivalentRestrictions().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Returns the {@link EntitySet} that describes all the definition restriction of
         * {@code this} {@link Class} from a no OOP point of view.
         * @return the entities describing {@code this} class.
         */
        EntitySet<Y> getEquivalentRestrictions();

        /**
         * Queries to the OWL representation for the definition of {@code this} class.
         * @return a new {@link EntitySet} contained the class definition to
         * the OWL structure of {@link #getInstance()}.
         */
        EntitySet<Y> queryEquivalentRestrictions();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryEquivalentRestrictions()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentRestrictions()}. This should
         * be done by {@link #writeAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the class definition of an OWL class.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseEquivalentRestrictionsToExpressionAxioms(){
            try {
                return getEquivalentRestrictions().synchroniseTo( queryEquivalentRestrictions());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryEquivalentRestrictions()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentRestrictions()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readAxioms()}.
         * @return the changes to be done to synchronise the class definition of an OWL class
         * with {@code this} structure.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseEquivalentRestrictionsFromExpressionAxioms(){
            try{
                return getEquivalentRestrictions().synchroniseFrom( queryEquivalentRestrictions());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
