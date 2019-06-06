package it.emarolab.owloop.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This interface is a part of the core of OWLOOP architecture.
 * It contains interfaces of the expressions that can be applied to
 * the OWL entity OWLDataProperty (i.e., {@link org.semanticweb.owlapi.model.OWLDataProperty}). <p>
 * The expressions are the following:
 *
 * <ul>
 * <li><b>{@link Equivalent}</b>:   this expression describes that a DataProperty is equivalent to another DataProperty.</li>
 * <li><b>{@link Disjoint}</b>:     this expression describes that a DataProperty is disjoint to another DataProperty.</li>
 * <li><b>{@link Sub}</b>:          this expression describes that a DataProperty is subsumes another DataProperty.</li>
 * <li><b>{@link Super}</b>:        this expression describes that a DataProperty is super-sumes another DataProperty.</li>
 * <li><b>{@link Domain}</b>:       this expression describes the domain restrictions of a DataProperty.</li>
 * <li><b>{@link Range}</b>:        this expression describes the range restrictions of a DataProperty.</li>
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
public interface DataProperty<O,J>
        extends Axiom.Descriptor<O,J>{

    // those could return ontology changes
    /**
     * Make {@link #getInstance()} as a functional property.
     */
    void setFunctional();
    /**
     * Make {@link #getInstance()} to be not a functional property.
     */
    void setNotFunctional();

    /**
     * Implementation of this interface enables a {@link DataProperty} to have the {@link Equivalent} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link DataProperty} descriptor instantiated during
     *            {@link #buildEquivalentDataProperty()} through {@link #getNewEquivalentDataProperty(Object, Object)}.
     */
    interface Equivalent<O,J,D extends DataProperty<O,J>>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseEquivalentDataPropertyFromExpressionAxioms();
                if (from != null) {
                    getEquivalentDataProperties().addAll(from.getToAdd());
                    getEquivalentDataProperties().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the disjointed data property of {@code this} property.
         * Each of {@link DataProperty}s are instantiated
         * through the method {@link #getNewEquivalentDataProperty(Object, Object)};
         * this is called for all {@link #getEquivalentDataProperties()}.
         * @return the set of {@link DataProperty}s that describes the
         * equivalent relations of {@code this} described ontological property.
         */
        default Set<D> buildEquivalentDataProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getEquivalentDataProperties()){
                D built = getNewEquivalentDataProperty( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildEquivalentDataProperty()} and
         * its purpose is to instantiate a new {@link DataProperty} to represent
         * an equivalent property of {@code this} {@link DataProperty} {@link Descriptor}.
         * @param instance the instance to ground the new equivalent {@link DataProperty}.
         * @param ontology the ontology in which ground the new {@link DataProperty}.
         * @return a new {@link Axiom.Descriptor} for all the equivalent properties
         * of the one described by {@code this} interface.
         */
        D getNewEquivalentDataProperty(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the equivalent data properties of
         * {@code this} grounded {@link DataProperty}; from a no OOP point of view.
         * @return the entities describing the equivalent data properties of {@code this} described property.
         */
        EntitySet<J> getEquivalentDataProperties();

        /**
         * Queries to the OWL representation for the equivalent properties of {@code this} data property.
         * @return a new {@link EntitySet} contained the equivalent properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> queryEquivalentDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryEquivalentDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentDataProperties()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentDataPropertyToExpressionAxioms(){
            try {
                return getEquivalentDataProperties().synchroniseTo( queryEquivalentDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryEquivalentDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentDataProperties()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the equivalent data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentDataPropertyFromExpressionAxioms(){
            try{
                return getEquivalentDataProperties().synchroniseFrom( queryEquivalentDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link DataProperty} to have the {@link Disjoint} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link DataProperty} descriptor instantiated during
     *            {@link #buildDisjointDataProperty()} through {@link #getNewDisjointDataProperty(Object, Object)}.
     */
    interface Disjoint<O,J,D extends DataProperty<O,J>>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readExpressionAxioms(){
            EntitySet.SynchronisationIntent<J> from = synchroniseDisjointDataPropertyFromExpressionAxioms();
            getDisjointDataProperties().addAll( from.getToAdd());
            getDisjointDataProperties().removeAll( from.getToRemove());
            return getIntent( from);
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the disjointed data property of {@code this} property.
         * Each of {@link DataProperty}s are instantiated
         * through the method {@link #getNewDisjointDataProperty(Object, Object)};
         * this is called for all {@link #getDisjointDataProperties()}.
         * @return the set of {@link DataProperty}s that describes the
         * disjoint relations of {@code this} described ontological property.
         */
        default Set< D> buildDisjointDataProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getDisjointDataProperties()){
                D built = getNewDisjointDataProperty( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildDisjointDataProperty()} and
         * its purpose is to instantiate a new {@link DataProperty} to represent
         * a disjointed property of {@code this} {@link DataProperty} {@link Descriptor}.
         * @param instance the instance to ground the new disjoint {@link DataProperty}.
         * @param ontology the ontology in which ground the new {@link DataProperty}.
         * @return a new {@link Axiom.Descriptor} for all the disjointed properties
         * of the one described by {@code this} interface.
         */
        D getNewDisjointDataProperty(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the disjoint data properties of
         * {@code this} grounded {@link DataProperty}; from a no OOP point of view.
         * @return the entities describing the disjoint data properties of {@code this} described property.
         */
        EntitySet<J> getDisjointDataProperties();

        /**
         * Queries to the OWL representation for the disjoint properties of {@code this} data property.
         * @return a new {@link EntitySet} contained the disjoint properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> queryDisjointDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDisjointDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointDataProperties()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the disjoint properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointDataPropertyToExpressionAxioms(){
            try {
                return getDisjointDataProperties().synchroniseTo( queryDisjointDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryDisjointDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointDataProperties()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the disjoint data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointDataPropertyFromExpressionAxioms(){
            try{
                return getDisjointDataProperties().synchroniseFrom( queryDisjointDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link DataProperty} to have the {@link Sub} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link DataProperty} descriptor instantiated during
     *            {@link #buildSubDataProperty()} through {@link #getNewSubDataProperty(Object, Object)}.
     */
    interface Sub<O,J,D extends DataProperty<O,J>>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSubDataPropertyFromExpressionAxioms();
                if (from != null) {
                    getSubDataProperties().addAll(from.getToAdd());
                    getSubDataProperties().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the sub data property of {@code this} property.
         * Each of {@link DataProperty}s are instantiated
         * through the method {@link #getNewSubDataProperty(Object, Object)};
         * this is called for all {@link #getSubDataProperties()}.
         * @return the set of {@link DataProperty}s that describes the
         * sub relations of {@code this} described ontological property.
         */
        default Set< D> buildSubDataProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getSubDataProperties()){
                D built = getNewSubDataProperty( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildSubDataProperty()} and
         * its purpose is to instantiate a new {@link DataProperty} to represent
         * a sub property of {@code this} {@link DataProperty} {@link Descriptor}.
         * @param instance the instance to ground the new sub {@link DataProperty}.
         * @param ontology the ontology in which ground the new {@link DataProperty}.
         * @return a new {@link Axiom.Descriptor} for all the sub properties
         * of the one described by {@code this} interface.
         */
        D getNewSubDataProperty( J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the sub data properties of
         * {@code this} grounded {@link DataProperty}; from a no OOP point of view.
         * @return the entities describing the sub data properties of {@code this} described property.
         */
        EntitySet<J> getSubDataProperties();

        /**
         * Queries to the OWL representation for the sub properties of {@code this} data property.
         * @return a new {@link EntitySet} contained the sub properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> querySubDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySubDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSubDataProperties()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the sub properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubDataPropertyToExpressionAxioms(){
            try {
                return getSubDataProperties().synchroniseTo( querySubDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #querySubDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getSubDataProperties()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the sub data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubDataPropertyFromExpressionAxioms(){
            try{
                return getSubDataProperties().synchroniseFrom( querySubDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link DataProperty} to have the {@link Super} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of {@link DataProperty} descriptor instantiated during
     *            {@link #buildSuperDataProperty()} through {@link #getNewSuperDataProperty(Object, Object)}.
     */
    interface Super<O,J,D extends DataProperty<O,J>>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSuperDataPropertyFromExpressionAxioms();
                getSuperDataProperties().addAll(from.getToAdd());
                getSuperDataProperties().removeAll(from.getToRemove());
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the super data property of {@code this} property.
         * Each of {@link DataProperty}s are instantiated
         * through the method {@link #getNewSuperDataProperty(Object, Object)};
         * this is called for all {@link #getSuperDataProperties()}.
         * @return the set of {@link DataProperty}s that describes the
         * super relations of {@code this} described ontological property.
         */
        default Set< D> buildSuperDataProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getSuperDataProperties()){
                D built = getNewSuperDataProperty( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildSuperDataProperty()} and
         * its purpose is to instantiate a new {@link DataProperty} to represent
         * a super property of {@code this} {@link DataProperty} {@link Descriptor}.
         * @param instance the instance to ground the new super {@link DataProperty}.
         * @param ontology the ontology in which ground the new {@link DataProperty}.
         * @return a new {@link Axiom.Descriptor} for all the super properties
         * of the one described by {@code this} interface.
         */
        D getNewSuperDataProperty(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the super data properties of
         * {@code this} grounded {@link DataProperty}; from a no OOP point of view.
         * @return the entities describing the super data properties of {@code this} described property.
         */
        EntitySet<J> getSuperDataProperties();

        /**
         * Queries to the OWL representation for the super properties of {@code this} data property.
         * @return a new {@link EntitySet} contained the super properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> querySuperDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySuperDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSuperDataProperties()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the super properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperDataPropertyToExpressionAxioms(){
            try {
                return getSuperDataProperties().synchroniseTo( querySuperDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #querySuperDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getSuperDataProperties()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the super data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperDataPropertyFromExpressionAxioms(){
            try{
                return getSuperDataProperties().synchroniseFrom( querySuperDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link DataProperty} to have the {@link Domain} expression.<p>
     *
     * This descriptor synchronises the definition of the domain of the grounded data property.
     * Definition is defined as a conjunction of restriction properties that
     * create a super class of the described ontological class.
     * The restriction can be of the following types:
     *
     * <ul>
     * <li><b>class restriction</b>:    a domain is restricted to a class.</li>
     * <li><b>data restriction</b>:     a domain is defined to have data properties into a given range.
     *                                  It is also possible to describe the cardinality of this restriction to be:
     *                                  existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     * <li><b>object restriction</b>:   a domain is defined to have data properties into a given class.
     *                                  It is also possible to describe the cardinality of this restriction to be:
     *                                  existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     * </ul>
     *
     * If this expression is used, it is not possible to use the {@code build()} operation since
     * the restrictions are not simple OWL entities like OWLClass, OWLIndividual, OWLObjectProperty and OWLDataProperty.
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     * @param <Y> the type of restriction for the {@link EntitySet} managed by this {@link Descriptor}.
     */
    interface Domain<O,J,Y>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseDomainDataPropertyFromExpressionAxioms();
                if ( from != null) {
                    getDataPropertyDomainConcepts().addAll(from.getToAdd());
                    getDataPropertyDomainConcepts().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Returns the {@link EntitySet} that describes all the restrictions of the
         * domain of the described property; from a no OOP point of view.
         * @return the restrictions describing the domain of {@code this} grounded the data property.
         */
        EntitySet<Y> getDataPropertyDomainConcepts();

        /**
         * Queries to the OWL representation for the domain restrictions of {@code this} data property.
         * @return a new {@link EntitySet} contained the domain restrictions of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<Y> queryDomainDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDomainDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDataPropertyDomainConcepts()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the domain restriction of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDomainDataPropertyToExpressionAxioms(){
            try {
                return getDataPropertyDomainConcepts().synchroniseTo( queryDomainDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryDomainDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getDataPropertyDomainConcepts()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the domain restrictions of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDomainDataPropertyFromExpressionAxioms(){
            try{
                return getDataPropertyDomainConcepts().synchroniseFrom( queryDomainDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link DataProperty} to have the {@link Range} expression.<p>
     *
     * This descriptor synchronises the definition of the range of the grounded data property.
     * Definition is defined as a conjunction of restriction properties that
     * create a super class of the described ontological class.
     * The restriction can be of the following types:
     *
     * <ul>
     * <li><b>class restriction</b>:    a range is restricted to a class.</li>
     * <li><b>data restriction</b>:     a range is defined to have data properties into a given range.
     *                                  It is also possible to describe the cardinality of this restriction to be:
     *                                  existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     * <li><b>object restriction</b>:   a range is defined to have data properties into a given class.
     *                                  It is also possible to describe the cardinality of this restriction to be:
     *                                  existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     * </ul>
     *
     * If this expression is used, it is not possible to use the {@code build()} operation since
     * the restrictions are not simple OWL entities like OWLClass, OWLIndividual, OWLObjectProperty and OWLDataProperty.
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     * @param <Y> the type of restriction for the {@link EntitySet} managed by this {@link Descriptor}.
     */
    interface Range<O,J,Y>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseRangeDataPropertyFromExpressionAxioms();
                if (from != null) {
                    getDataPropertyRangeConcepts().addAll(from.getToAdd());
                    getDataPropertyRangeConcepts().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Returns the {@link EntitySet} that describes all the restrictions of the
         * range of the described property; from a no OOP point of view.
         * @return the restrictions describing the range of {@code this} grounded the data property.
         */
        EntitySet<Y> getDataPropertyRangeConcepts();

        /**
         * Queries to the OWL representation for the range restrictions of {@code this} data property.
         * @return a new {@link EntitySet} contained the range restrictions of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<Y> queryRangeDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryRangeDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDataPropertyRangeConcepts()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the range restriction of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseRangeDataPropertyToExpressionAxioms(){
            try {
                return getDataPropertyRangeConcepts().synchroniseTo( queryRangeDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryRangeDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getDataPropertyRangeConcepts()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the range restrictions of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseRangeDataPropertyFromExpressionAxioms(){
            try{
                return getDataPropertyRangeConcepts().synchroniseFrom( queryRangeDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
