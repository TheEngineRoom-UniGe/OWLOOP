package it.emarolab.owloop.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This interface is a part of the core of OWLOOP architecture.
 * It contains interfaces of the expressions that can be applied to
 * the OWL entity OWLDataProperty (i.e., {@link org.semanticweb.owlapi.model.OWLObjectProperty}). <p>
 * The expressions are the following:
 *
 * <ul>
 * <li><b>{@link Equivalent}</b>:   this expression describes that an ObjectProperty is equivalent to another ObjectProperty.</li>
 * <li><b>{@link Disjoint}</b>:     this expression describes that an ObjectProperty is disjoint to another ObjectProperty.</li>
 * <li><b>{@link Sub}</b>:          this expression describes that an ObjectProperty subsumes another ObjectProperty.</li>
 * <li><b>{@link Super}</b>:        this expression describes that an ObjectProperty super-sumes another ObjectProperty.</li>
 * <li><b>{@link Domain}</b>:       this expression describes the domain restrictions of an ObjectProperty.</li>
 * <li><b>{@link Range}</b>:        this expression describes the range restrictions of an ObjectProperty.</li>
 * <li><b>{@link Inverse}</b>:      this expression describes that an ObjectProperty has another inverse ObjectProperty.</li>
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
public interface ObjectProperty<O,J>
        extends Axiom.Descriptor<O,J>{

    // those can return ontology changes
    /**
     * Make {@link #getInstance()} as a functional property.
     */
    void setFunctional();
    /**
     * Make {@link #getInstance()} to be not a functional property anymore.
     */
    void setNotFunctional();

    /**
     * Make {@link #getInstance()} as an inverse functional property.
     */
    void setInverseFunctional();
    /**
     * Make {@link #getInstance()} to be not an inverse functional property anymore.
     */
    void setNotInverseFunctional();

    /**
     * Make {@link #getInstance()} as a transitive property.
     */
    void setTransitive();
    /**
     * Make {@link #getInstance()} to be not a transitive property anymore.
     */
    void setNotTransitive();

    /**
     * Make {@link #getInstance()} as a symmetric property.
     */
    void setSymmetric();
    /**
     * Make {@link #getInstance()} to be no a symmetric property anymore.
     */
    void setNotSymmetric();

    /**
     * Make {@link #getInstance()} as a asymmetric property.
     */
    void setAsymmetric();
    /**
     * Make {@link #getInstance()} to be not an asymmetric property anymore.
     */
    void setNotAsymmetric();

    /**
     * Make {@link #getInstance()} as a reflexive property.
     */
    void setReflexive();
    /**
     * Make {@link #getInstance()} to be no a reflexive property anymore.
     */
    void setNotReflexive();

    /**
     * Make {@link #getInstance()} as an irreflexive property.
     */
    void setIrreflexive();
    /**
     * Make {@link #getInstance()} to be not an irreflexive property anymore.
     */
    void setNotIrreflexive();

    /**
     * Implementation of this interface enables a {@link ObjectProperty} to have the {@link Inverse} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link ObjectProperty} descriptor instantiated during
     *           {@link #buildInverseObjectProperties()} through {@link #getNewInverseObjectProperty(Object, Object)}.
     */
    interface Inverse<O,J,D extends ObjectProperty<O,J>>
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseInverseObjectPropertiesFromExpressionAxioms();
                if( from != null) {
                    getInverseObjectProperties().addAll(from.getToAdd());
                    getInverseObjectProperties().removeAll(from.getToRemove());
                }
                return getIntent(from);
            }catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the inverse object property of {@code this} property.
         * Each of {@link ObjectProperty}s are instantiated
         * through the method {@link #getNewInverseObjectProperty(Object, Object)};
         * this is called for all {@link #getInverseObjectProperties()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * inverse relations of {@code this} described ontological property.
         */
        default Set<D> buildInverseObjectProperties(){
            Set<D> out = new HashSet<>();
            for( J cl : getInverseObjectProperties()){
                D built = getNewInverseObjectProperty( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildInverseObjectProperties()} and
         * its purpose is to instantiate a new {@link ObjectProperty} to represent
         * an inverse property of {@code this} {@link ObjectProperty} {@link Descriptor}.
         * @param instance the instance to ground the new inverse {@link ObjectProperty}.
         * @param ontology the ontology in which ground the new {@link ObjectProperty}.
         * @return a new {@link Axiom.Descriptor} for all the inverse properties
         * of the one described by {@code this} interface.
         */
        D getNewInverseObjectProperty(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the inverse object properties of
         * {@code this} grounded {@link ObjectProperty}; from a no OOP point of view.
         * @return the entities describing the inverse object properties of {@code this} described property.
         */
        EntitySet<J> getInverseObjectProperties();

        /**
         * Queries to the OWL representation for the inverse properties of {@code this} object property.
         * @return a new {@link EntitySet} contained the inverse properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> queryInverseObjectProperties();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryInverseObjectProperties()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getInverseObjectProperties()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the inverse properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseInverseObjectPropertiesToExpressionAxioms(){
            try {
                return getInverseObjectProperties().synchroniseTo( queryInverseObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryInverseObjectProperties()}
         * as input parameter. This computes the changes to be performed into the {@link #getInverseObjectProperties()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the inverse object properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseInverseObjectPropertiesFromExpressionAxioms(){
            try{
                return getInverseObjectProperties().synchroniseFrom( queryInverseObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link ObjectProperty} to have the {@link Disjoint} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link ObjectProperty} descriptor instantiated during
     *           {@link #buildDisjointObjectProperties()}  through {@link #getNewDisjointObjectProperty(Object, Object)}.
     */
    interface Disjoint<O,J,D extends ObjectProperty<O,J>>
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseDisjointObjectPropertiesFromExpressionAxioms();
                if ( from != null) {
                    getDisjointObjectProperties().addAll(from.getToAdd());
                    getDisjointObjectProperties().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the disjointed object property of {@code this} property.
         * Each of {@link ObjectProperty}s are instantiated
         * through the method {@link #getNewDisjointObjectProperty(Object, Object)};
         * this is called for all {@link #getDisjointObjectProperties()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * disjoint relations of {@code this} described ontological property.
         */
        default Set< D> buildDisjointObjectProperties(){
            Set<D> out = new HashSet<>();
            for( J cl : getDisjointObjectProperties()){
                D built = getNewDisjointObjectProperty( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildDisjointObjectProperties()} and
         * its purpose is to instantiate a new {@link ObjectProperty} to represent
         * a disjointed property of {@code this} {@link ObjectProperty} {@link Descriptor}.
         * @param instance the instance to ground the new disjoint {@link ObjectProperty}.
         * @param ontology the ontology in which ground the new {@link ObjectProperty}.
         * @return a new {@link Axiom.Descriptor} for all the disjointed properties
         * of the one described by {@code this} interface.
         */
        D getNewDisjointObjectProperty(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the disjoint object properties of
         * {@code this} grounded {@link ObjectProperty}; from a no OOP point of view.
         * @return the entities describing the disjoint object properties of {@code this} described property.
         */
        EntitySet<J> getDisjointObjectProperties();

        /**
         * Queries to the OWL representation for the disjoint properties of {@code this} object property.
         * @return a new {@link EntitySet} contained the disjoint properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> queryDisjointObjectProperties();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDisjointObjectProperties()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointObjectProperties()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the disjoint properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointObjectPropertiesToExpressionAxioms(){
            try {
                return getDisjointObjectProperties().synchroniseTo( queryDisjointObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryDisjointObjectProperties()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointObjectProperties()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the disjoint object properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointObjectPropertiesFromExpressionAxioms(){
            try{
                return getDisjointObjectProperties().synchroniseFrom( queryDisjointObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link ObjectProperty} to have the {@link Equivalent} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link ObjectProperty} descriptor instantiated during
     *           {@link #buildEquivalentObjectProperties()}   through {@link #getNewEquivalentObjectProperty(Object, Object)}.
     */
    interface Equivalent<O,J,D extends ObjectProperty<O,J>>
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseEquivalentObjectPropertiesFromExpressionAxioms();
                if (from != null) {
                    getEquivalentObjectProperties().addAll(from.getToAdd());
                    getEquivalentObjectProperties().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the disjointed object property of {@code this} property.
         * Each of {@link ObjectProperty}s are instantiated
         * through the method {@link #getNewEquivalentObjectProperty(Object, Object)};
         * this is called for all {@link #getEquivalentObjectProperties()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * equivalent relations of {@code this} described ontological property.
         */
        default Set<D> buildEquivalentObjectProperties(){
            Set<D> out = new HashSet<>();
            for( J cl : getEquivalentObjectProperties()){
                D built = getNewEquivalentObjectProperty( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildEquivalentObjectProperties()} and
         * its purpose is to instantiate a new {@link ObjectProperty} to represent
         * an equivalent property of {@code this} {@link ObjectProperty} {@link Descriptor}.
         * @param instance the instance to ground the new equivalent {@link ObjectProperty}.
         * @param ontology the ontology in which ground the new {@link ObjectProperty}.
         * @return a new {@link Axiom.Descriptor} for all the equivalent properties
         * of the one described by {@code this} interface.
         */
        D getNewEquivalentObjectProperty(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the equivalent object properties of
         * {@code this} grounded {@link ObjectProperty}; from a no OOP point of view.
         * @return the entities describing the equivalent object properties of {@code this} described property.
         */
        EntitySet<J> getEquivalentObjectProperties();

        /**
         * Queries to the OWL representation for the equivalent properties of {@code this} object property.
         * @return a new {@link EntitySet} contained the equivalent properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> queryEquivalentObjectProperties();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryEquivalentObjectProperties()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentObjectProperties()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentObjectPropertiesToExpressionAxioms(){
            try {
                return getEquivalentObjectProperties().synchroniseTo( queryEquivalentObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryEquivalentObjectProperties()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentObjectProperties()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the equivalent object properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentObjectPropertiesFromExpressionAxioms(){
            try{
                return getEquivalentObjectProperties().synchroniseFrom( queryEquivalentObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link ObjectProperty} to have the {@link Sub} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link ObjectProperty} descriptor instantiated during
     *           {@link #buildSubObjectProperties()} through {@link #getNewSubObjectProperty(Object, Object)}.
     */
    interface Sub<O,J,D extends ObjectProperty<O,J>>
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSubObjectPropertiesFromExpressionAxioms();
                if (from != null) {
                    getSubObjectProperties().addAll(from.getToAdd());
                    getSubObjectProperties().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the sub object property of {@code this} property.
         * Each of {@link ObjectProperty}s are instantiated
         * through the method {@link #getNewSubObjectProperty(Object, Object)};
         * this is called for all {@link #getSubObjectProperties()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * sub relations of {@code this} described ontological property.
         */
        default Set<D> buildSubObjectProperties(){
            Set<D> out = new HashSet<>();
            for( J cl : getSubObjectProperties()){
                D built = getNewSubObjectProperty( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildSubObjectProperties()} and
         * its purpose is to instantiate a new {@link ObjectProperty} to represent
         * a sub property of {@code this} {@link ObjectProperty} {@link Descriptor}.
         * @param instance the instance to ground the new sub {@link ObjectProperty}.
         * @param ontology the ontology in which ground the new {@link ObjectProperty}.
         * @return a new {@link Axiom.Descriptor} for all the sub properties
         * of the one described by {@code this} interface.
         */
        D getNewSubObjectProperty( J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the sub object properties of
         * {@code this} grounded {@link ObjectProperty}; from a no OOP point of view.
         * @return the entities describing the sub object properties of {@code this} described property.
         */
        EntitySet<J> getSubObjectProperties();

        /**
         * Queries to the OWL representation for the sub properties of {@code this} object property.
         * @return a new {@link EntitySet} contained the sub properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> querySubObjectProperties();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySubObjectProperties()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSubObjectProperties()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the sub properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubObjectPropertiesToExpressionAxioms(){
            try {
                return getSubObjectProperties().synchroniseTo( querySubObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #querySubObjectProperties()}
         * as input parameter. This computes the changes to be performed into the {@link #getSubObjectProperties()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the sub object properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubObjectPropertiesFromExpressionAxioms(){
            try{
                return getSubObjectProperties().synchroniseFrom( querySubObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link ObjectProperty} to have the {@link Super} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link ObjectProperty} descriptor instantiated during
     *           {@link #buildSuperObjectProperties()} through {@link #getNewSuperObjectProperty(Object, Object)}.
     */
    interface Super<O,J,D extends ObjectProperty<O,J>>
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSuperObjectPropertiesFromExpressionAxioms();
                if ( from != null) {
                    getSuperObjectProperties().addAll(from.getToAdd());
                    getSuperObjectProperties().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the super object property of {@code this} property.
         * Each of {@link ObjectProperty}s are instantiated
         * through the method {@link #getNewSuperObjectProperty(Object, Object)};
         * this is called for all {@link #getSuperObjectProperties()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * super relations of {@code this} described ontological property.
         */
        default Set<D> buildSuperObjectProperties(){
            Set<D> out = new HashSet<>();
            for( J cl : getSuperObjectProperties()){
                D built = getNewSuperObjectProperty( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildSuperObjectProperties()} and
         * its purpose is to instantiate a new {@link ObjectProperty} to represent
         * a super property of {@code this} {@link ObjectProperty} {@link Descriptor}.
         * @param instance the instance to ground the new super {@link ObjectProperty}.
         * @param ontology the ontology in which ground the new {@link ObjectProperty}.
         * @return a new {@link Axiom.Descriptor} for all the super properties
         * of the one described by {@code this} interface.
         */
        D getNewSuperObjectProperty(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the super object properties of
         * {@code this} grounded {@link ObjectProperty}; from a no OOP point of view.
         * @return the entities describing the super object properties of {@code this} described property.
         */
        EntitySet<J> getSuperObjectProperties();

        /**
         * Queries to the OWL representation for the super properties of {@code this} object property.
         * @return a new {@link EntitySet} contained the super properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> querySuperObjectProperties();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySuperObjectProperties()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSuperObjectProperties()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the super properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperObjectPropertiesToExpressionAxioms(){
            try {
                return getSuperObjectProperties().synchroniseTo( querySuperObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #querySuperObjectProperties()}
         * as input parameter. This computes the changes to be performed into the {@link #getSuperObjectProperties()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the super object properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperObjectPropertiesFromExpressionAxioms(){
            try{
                return getSuperObjectProperties().synchroniseFrom( querySuperObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link ObjectProperty} to have the {@link Domain} expression.<p>
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
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseDomainObjectPropertyFromExpressionAxioms();
                getDomainRestrictions().addAll(from.getToAdd());
                getDomainRestrictions().removeAll(from.getToRemove());
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Returns the {@link EntitySet} that describes all the restrictions of the
         * domain of the described property; from a no OOP point of view.
         * @return the restrictions describing the domain of {@code this} grounded the object property.
         */
        EntitySet<Y> getDomainRestrictions();

        /**
         * Queries to the OWL representation for the domain restrictions of {@code this} object property.
         * @return a new {@link EntitySet} contained the domain restrictions of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<Y> queryDomainRestrictions();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDomainRestrictions()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDomainRestrictions()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the domain restriction of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDomainObjectPropertyToExpressionAxioms(){
            try {
                return getDomainRestrictions().synchroniseTo( queryDomainRestrictions());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryDomainRestrictions()}
         * as input parameter. This computes the changes to be performed into the {@link #getDomainRestrictions()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the domain restrictions of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDomainObjectPropertyFromExpressionAxioms(){
            try{
                return getDomainRestrictions().synchroniseFrom( queryDomainRestrictions());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link ObjectProperty} to have the {@link Range} expression.<p>
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
            extends ObjectProperty<O,J>{

        @Override
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseRangeObjectPropertyFromExpressionAxioms();
                if (from != null) {
                    getRangeRestrictions().addAll(from.getToAdd());
                    getRangeRestrictions().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent(null);
            }
        }

        /**
         * Returns the {@link EntitySet} that describes all the restrictions of the
         * range of the described property; from a no OOP point of view.
         * @return the restrictions describing the range of {@code this} grounded the object property.
         */
        EntitySet<Y> getRangeRestrictions();

        /**
         * Queries to the OWL representation for the range restrictions of {@code this} object property.
         * @return a new {@link EntitySet} contained the range restrictions of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<Y> queryRangeRestrictions();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryRangeRestrictions()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getRangeRestrictions()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the range restriction of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseRangeObjectPropertyToExpressionAxioms(){
            try {
                return getRangeRestrictions().synchroniseTo( queryRangeRestrictions());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryRangeRestrictions()}
         * as input parameter. This computes the changes to be performed into the {@link #getRangeRestrictions()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the range restrictions of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseRangeObjectPropertyFromExpressionAxioms(){
            try{
                return getRangeRestrictions().synchroniseFrom( queryRangeRestrictions());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
