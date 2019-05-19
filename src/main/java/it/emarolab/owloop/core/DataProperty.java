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
                    getEquivalentDataProperty().addAll(from.getToAdd());
                    getEquivalentDataProperty().removeAll(from.getToRemove());
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
         * this is called for all {@link #getEquivalentDataProperty()}.
         * @return the set of {@link DataProperty}s that describes the
         * equivalent relations of {@code this} described ontological property.
         */
        default Set<D> buildEquivalentDataProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getEquivalentDataProperty()){
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
        EntitySet<J> getEquivalentDataProperty();

        /**
         * Queries to the OWL representation for the equivalent properties of {@code this} data property.
         * @return a new {@link EntitySet} contained the equivalent properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> queryEquivalentDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryEquivalentDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentDataProperty()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentDataPropertyToExpressionAxioms(){
            try {
                return getEquivalentDataProperty().synchroniseTo( queryEquivalentDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryEquivalentDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the equivalent data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentDataPropertyFromExpressionAxioms(){
            try{
                return getEquivalentDataProperty().synchroniseFrom( queryEquivalentDataProperty());
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
            getDisjointDataProperty().addAll( from.getToAdd());
            getDisjointDataProperty().removeAll( from.getToRemove());
            return getIntent( from);
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the disjointed data property of {@code this} property.
         * Each of {@link DataProperty}s are instantiated
         * through the method {@link #getNewDisjointDataProperty(Object, Object)};
         * this is called for all {@link #getDisjointDataProperty()}.
         * @return the set of {@link DataProperty}s that describes the
         * disjoint relations of {@code this} described ontological property.
         */
        default Set< D> buildDisjointDataProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getDisjointDataProperty()){
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
        EntitySet<J> getDisjointDataProperty();

        /**
         * Queries to the OWL representation for the disjoint properties of {@code this} data property.
         * @return a new {@link EntitySet} contained the disjoint properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> queryDisjointDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDisjointDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointDataProperty()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the disjoint properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointDataPropertyToExpressionAxioms(){
            try {
                return getDisjointDataProperty().synchroniseTo( queryDisjointDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryDisjointDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the disjoint data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointDataPropertyFromExpressionAxioms(){
            try{
                return getDisjointDataProperty().synchroniseFrom( queryDisjointDataProperty());
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
                    getSubDataProperty().addAll(from.getToAdd());
                    getSubDataProperty().removeAll(from.getToRemove());
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
         * this is called for all {@link #getSubDataProperty()}.
         * @return the set of {@link DataProperty}s that describes the
         * sub relations of {@code this} described ontological property.
         */
        default Set< D> buildSubDataProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getSubDataProperty()){
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
        EntitySet<J> getSubDataProperty();

        /**
         * Queries to the OWL representation for the sub properties of {@code this} data property.
         * @return a new {@link EntitySet} contained the sub properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> querySubDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySubDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSubDataProperty()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the sub properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubDataPropertyToExpressionAxioms(){
            try {
                return getSubDataProperty().synchroniseTo( querySubDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #querySubDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getSubDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the sub data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubDataPropertyFromExpressionAxioms(){
            try{
                return getSubDataProperty().synchroniseFrom( querySubDataProperty());
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
                getSuperDataProperty().addAll(from.getToAdd());
                getSuperDataProperty().removeAll(from.getToRemove());
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
         * this is called for all {@link #getSuperDataProperty()}.
         * @return the set of {@link DataProperty}s that describes the
         * super relations of {@code this} described ontological property.
         */
        default Set< D> buildSuperDataProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getSuperDataProperty()){
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
        EntitySet<J> getSuperDataProperty();

        /**
         * Queries to the OWL representation for the super properties of {@code this} data property.
         * @return a new {@link EntitySet} contained the super properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> querySuperDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySuperDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSuperDataProperty()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the super properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperDataPropertyToExpressionAxioms(){
            try {
                return getSuperDataProperty().synchroniseTo( querySuperDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #querySuperDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getSuperDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the super data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperDataPropertyFromExpressionAxioms(){
            try{
                return getSuperDataProperty().synchroniseFrom( querySuperDataProperty());
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
                    getDomainDataProperty().addAll(from.getToAdd());
                    getDomainDataProperty().removeAll(from.getToRemove());
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
        EntitySet<Y> getDomainDataProperty();

        /**
         * Queries to the OWL representation for the domain restrictions of {@code this} data property.
         * @return a new {@link EntitySet} contained the domain restrictions of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<Y> queryDomainDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDomainDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDomainDataProperty()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the domain restriction of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDomainDataPropertyToExpressionAxioms(){
            try {
                return getDomainDataProperty().synchroniseTo( queryDomainDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryDomainDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getDomainDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the domain restrictions of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDomainDataPropertyFromExpressionAxioms(){
            try{
                return getDomainDataProperty().synchroniseFrom( queryDomainDataProperty());
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
                    getRangeDataProperty().addAll(from.getToAdd());
                    getRangeDataProperty().removeAll(from.getToRemove());
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
        EntitySet<Y> getRangeDataProperty();

        /**
         * Queries to the OWL representation for the range restrictions of {@code this} data property.
         * @return a new {@link EntitySet} contained the range restrictions of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<Y> queryRangeDataProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryRangeDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getRangeDataProperty()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the range restriction of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseRangeDataPropertyToExpressionAxioms(){
            try {
                return getRangeDataProperty().synchroniseTo( queryRangeDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryRangeDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getRangeDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the range restrictions of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseRangeDataPropertyFromExpressionAxioms(){
            try{
                return getRangeDataProperty().synchroniseFrom( queryRangeDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
