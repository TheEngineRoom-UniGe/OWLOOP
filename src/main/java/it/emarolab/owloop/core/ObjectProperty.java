package it.emarolab.owloop.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The main interface for ontological object property {@link Axiom.Descriptor}.
 * <p>
 *     This interface contains all the {@link Axiom.Descriptor} that
 *     can be applied to an ontological object property (e.g.: {@link org.semanticweb.owlapi.model.OWLObjectProperty})
 *     in any arbitrary combination since all of them should rely on the same {@link Axiom.Ground}
 *     type.
 *     <br>
 *     More in particular, for the {@link #getInstance()} entities in the {@link #getOntology()}, those are:
 *     <ul>
 *     <li><b>{@link Inverse}</b>: for describing the inverse properties.</li>
 *     <li><b>{@link Disjoint}</b>: for describing disjointed properties from the specific
 *                                         grounded {@link ObjectProperty}.</li>
 *     <li><b>{@link Equivalent}</b>: for describing equivalent properties from the specific
 *                                         grounded {@link ObjectProperty}.</li>
 *     <li><b>{@link Sub}</b>: for representing the sub object properties of
 *                                         this grounded {@link ObjectProperty}.</li>
 *     <li><b>{@link Super}</b>: for representing the super object properties of
 *                                         this grounded {@link ObjectProperty}.</li>
 *     <li><b>{@link Domain}</b>: for representing the domain restrictions of the
 *                                         this grounded {@link ObjectProperty}.</li>
 *     <li><b>{@link Range}</b>: for representing the range restrictions of the
 *                                         this grounded {@link ObjectProperty}.</li>
 *     </ul>
 *     Nevertheless, they are still generic and not attached to any specific OWL implementation.
 *     Since they implements common feature of OWLLOOP architecture only.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
 * </small></div>
 *
 * @param <O> the type of ontology in which the axioms for classes will be applied.
 * @param <J> the type of instance (i.e.: object properties) for the axioms.
 */
public interface ObjectProperty<O,J>
        extends Axiom.Descriptor<O,J>{

    // those could return ontology changes
    /**
     * Make {@link #getInstance()} as a functional property.
     */
    void setFunctional();
    /**
     * Make {@link #getInstance()} to be no a functional property anymore.
     */
    void setNotFunctional();

    /**
     * Make {@link #getInstance()} as an inverse functional property.
     */
    void setInverseFunctional();
    /**
     * Make {@link #getInstance()} to be no an inverse functional property anymore.
     */
    void setNotInverseFunctional();

    /**
     * Make {@link #getInstance()} as a transitive property.
     */
    void setTransitive();
    /**
     * Make {@link #getInstance()} to be no a transitive property anymore.
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
     * Make {@link #getInstance()} to be no an asymmetric property anymore.
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
     * Make {@link #getInstance()} to be no an irreflexive property anymore.
     */
    void setNotIrreflexive();



    /**
     * The {@link Axiom.Descriptor} for inverse object properties.
     * <p>
     *     This {@link Axiom.Descriptor} synchronises the inverse object properties of {@code this}
     *     property (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described object property
     *           (it also represents the type of {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link ObjectProperty} descriptor instantiated during
     *           {@link #buildInverseObjectProperty()} through {@link #getNewInverseObjectProperty(Object, Object)}.
     */
    interface Inverse<O,J,D extends ObjectProperty<O,J>>
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseInverseObjectPropertyFromSemantic();
                if( from != null) {
                    getInverseObjectProperty().addAll(from.getToAdd());
                    getInverseObjectProperty().removeAll(from.getToRemove());
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
         * this is called for all {@link #getInverseObjectProperty()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * inverse relations of {@code this} described ontological property.
         */
        default Set<D> buildInverseObjectProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getInverseObjectProperty()){
                D built = getNewInverseObjectProperty( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildInverseObjectProperty()} and
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
        EntitySet<J> getInverseObjectProperty();

        /**
         * Queries to the OWL representation for the inverse properties of {@code this} object property.
         * @return a new {@link EntitySet} contained the inverse properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> queryInverseObjectProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryInverseObjectProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getInverseObjectProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the inverse properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseInverseObjectPropertyToSemantic(){
            try {
                return getInverseObjectProperty().synchroniseTo( queryInverseObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticEntitySet#synchroniseFrom(EntitySet)} with {@link #queryInverseObjectProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getInverseObjectProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the inverse object properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseInverseObjectPropertyFromSemantic(){
            try{
                return getInverseObjectProperty().synchroniseFrom( queryInverseObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Axiom.Descriptor} for disjointed object properties.
     * <p>
     *     This {@link Axiom.Descriptor} synchronises the disjointed object properties of {@code this}
     *     property (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described object property
     *           (it also represents the type of {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link ObjectProperty} descriptor instantiated during
     *           {@link #buildDisjointObjectProperty()}  through {@link #getNewDisjointObjectProperty(Object, Object)}.
     */
    interface Disjoint<O,J,D extends ObjectProperty<O,J>>
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseDisjointObjectPropertyFromSemantic();
                if ( from != null) {
                    getDisjointObjectProperty().addAll(from.getToAdd());
                    getDisjointObjectProperty().removeAll(from.getToRemove());
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
         * this is called for all {@link #getDisjointObjectProperty()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * disjoint relations of {@code this} described ontological property.
         */
        default Set< D> buildDisjointObjectProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getDisjointObjectProperty()){
                D built = getNewDisjointObjectProperty( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildDisjointObjectProperty()} and
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
        EntitySet<J> getDisjointObjectProperty();

        /**
         * Queries to the OWL representation for the disjoint properties of {@code this} object property.
         * @return a new {@link EntitySet} contained the disjoint properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> queryDisjointObjectProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDisjointObjectProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointObjectProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the disjoint properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointObjectPropertyToSemantic(){
            try {
                return getDisjointObjectProperty().synchroniseTo( queryDisjointObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticEntitySet#synchroniseFrom(EntitySet)} with {@link #queryDisjointObjectProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointObjectProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the disjoint object properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointObjectPropertyFromSemantic(){
            try{
                return getDisjointObjectProperty().synchroniseFrom( queryDisjointObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Axiom.Descriptor} for equivalent object properties.
     * <p>
     *     This {@link Axiom.Descriptor} synchronises the equivalent object properties of {@code this}
     *     property (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described object property
     *           (it also represents the type of {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link ObjectProperty} descriptor instantiated during
     *           {@link #buildEquivalentObjectProperty()}   through {@link #getNewEquivalentObjectProperty(Object, Object)}.
     */
    interface Equivalent<O,J,D extends ObjectProperty<O,J>>
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseEquivalentObjectPropertyFromSemantic();
                if (from != null) {
                    getEquivalentObjectProperty().addAll(from.getToAdd());
                    getEquivalentObjectProperty().removeAll(from.getToRemove());
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
         * this is called for all {@link #getEquivalentObjectProperty()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * equivalent relations of {@code this} described ontological property.
         */
        default Set<D> buildEquivalentObjectProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getEquivalentObjectProperty()){
                D built = getNewEquivalentObjectProperty( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildEquivalentObjectProperty()} and
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
        EntitySet<J> getEquivalentObjectProperty();

        /**
         * Queries to the OWL representation for the equivalent properties of {@code this} object property.
         * @return a new {@link EntitySet} contained the equivalent properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> queryEquivalentObjectProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryEquivalentObjectProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentObjectProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentObjectPropertyToSemantic(){
            try {
                return getEquivalentObjectProperty().synchroniseTo( queryEquivalentObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticEntitySet#synchroniseFrom(EntitySet)} with {@link #queryEquivalentObjectProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentObjectProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the equivalent object properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentObjectPropertyFromSemantic(){
            try{
                return getEquivalentObjectProperty().synchroniseFrom( queryEquivalentObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Axiom.Descriptor} for sub object properties.
     * <p>
     *     This {@link Axiom.Descriptor} synchronises the sub object properties of {@code this}
     *     property (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described object property
     *           (it also represents the type of {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link ObjectProperty} descriptor instantiated during
     *           {@link #buildSubObjectProperty()} through {@link #getNewSubObjectProperty(Object, Object)}.
     */
    interface Sub<O,J,D extends ObjectProperty<O,J>>
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSubObjectPropertyFromSemantic();
                if (from != null) {
                    getSubObjectProperty().addAll(from.getToAdd());
                    getSubObjectProperty().removeAll(from.getToRemove());
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
         * this is called for all {@link #getSubObjectProperty()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * sub relations of {@code this} described ontological property.
         */
        default Set<D> buildSubObjectProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getSubObjectProperty()){
                D built = getNewSubObjectProperty( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildSubObjectProperty()} and
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
        EntitySet<J> getSubObjectProperty();

        /**
         * Queries to the OWL representation for the sub properties of {@code this} object property.
         * @return a new {@link EntitySet} contained the sub properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> querySubObjectProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySubObjectProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSubObjectProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the sub properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubObjectPropertyToSemantic(){
            try {
                return getSubObjectProperty().synchroniseTo( querySubObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticEntitySet#synchroniseFrom(EntitySet)} with {@link #querySubObjectProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getSubObjectProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the sub object properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSubObjectPropertyFromSemantic(){
            try{
                return getSubObjectProperty().synchroniseFrom( querySubObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Axiom.Descriptor} for super object properties.
     * <p>
     *     This {@link Axiom.Descriptor} synchronises the super object properties of {@code this}
     *     property (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described object property
     *           (it also represents the type of {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link ObjectProperty} descriptor instantiated during
     *           {@link #buildSuperObjectProperty()} through {@link #getNewSuperObjectProperty(Object, Object)}.
     */
    interface Super<O,J,D extends ObjectProperty<O,J>>
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseSuperObjectPropertyFromSemantic();
                if ( from != null) {
                    getSuperObjectProperty().addAll(from.getToAdd());
                    getSuperObjectProperty().removeAll(from.getToRemove());
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
         * this is called for all {@link #getSuperObjectProperty()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * super relations of {@code this} described ontological property.
         */
        default Set<D> buildSuperObjectProperty(){
            Set<D> out = new HashSet<>();
            for( J cl : getSuperObjectProperty()){
                D built = getNewSuperObjectProperty( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildSuperObjectProperty()} and
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
        EntitySet<J> getSuperObjectProperty();

        /**
         * Queries to the OWL representation for the super properties of {@code this} object property.
         * @return a new {@link EntitySet} contained the super properties of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<J> querySuperObjectProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #querySuperObjectProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSuperObjectProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the super properties of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperObjectPropertyToSemantic(){
            try {
                return getSuperObjectProperty().synchroniseTo( querySuperObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticEntitySet#synchroniseFrom(EntitySet)} with {@link #querySuperObjectProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getSuperObjectProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the super object properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseSuperObjectPropertyFromSemantic(){
            try{
                return getSuperObjectProperty().synchroniseFrom( querySuperObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Axiom.Descriptor} for the definition of an ontological object property domain.
     * <p>
     *     This {@link Axiom.Descriptor} synchronises the definition of a specific object property domain
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     *     Definition is defined as conjunction of restriction properties that
     *     creates classes in the domain of the described property.
     *     More in details, such a restriction can be of the type:
     *     <ul>
     *     <li><b>class restriction</b>: a domain is restricted to a class.</li>
     *     <li><b>data restriction</b>: a domain is defined to have data properties into a given range.
     *                       It is also possible to describe the cardinality of this restriction to be:
     *                       existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     *     <li><b>object restriction</b>: a domain is defined to have object properties into a given class.
     *                       It is also possible to describe the cardinality of this restriction to be:
     *                       existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     *     </ul>
     *     This description is not considered from an OOP point of view (it is not possible
     *     to {@code build} them) since their are not entities that fall back in the {@link Axiom}
     *     representations.
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     * @param <Y> the type of restriction of the domain of the defined property.
     *           (it represents the of {@link EntitySet} managed by this {@link Descriptor}.
     */
    interface Domain<O,J,Y>
            extends ObjectProperty<O,J>{

        @Override // see documentation on Axiom.descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseDomainObjectPropertyFromSemantic();
                getDomainObjectProperty().addAll(from.getToAdd());
                getDomainObjectProperty().removeAll(from.getToRemove());
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
        EntitySet<Y> getDomainObjectProperty();

        /**
         * Queries to the OWL representation for the domain restrictions of {@code this} object property.
         * @return a new {@link EntitySet} contained the domain restrictions of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<Y> queryDomainObjectProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDomainObjectProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDomainObjectProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the domain restriction of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDomainObjectPropertyToSemantic(){
            try {
                return getDomainObjectProperty().synchroniseTo( queryDomainObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticEntitySet#synchroniseFrom(EntitySet)} with {@link #queryDomainObjectProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getDomainObjectProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the domain restrictions of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDomainObjectPropertyFromSemantic(){
            try{
                return getDomainObjectProperty().synchroniseFrom( queryDomainObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Axiom.Descriptor} for the definition of an ontological object property range.
     * <p>
     *     This {@link Axiom.Descriptor} synchronises the definition of a specific object property range
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     *     Definition is defined as conjunction of restriction properties that
     *     creates classes in the range of the described property.
     *     More in details, such a restriction can be of the type:
     *     <ul>
     *     <li><b>class restriction</b>: a range is restricted to a class.</li>
     *     <li><b>data restriction</b>: a range is defined to have data properties into a given range.
     *                       It is also possible to describe the cardinality of this restriction to be:
     *                       existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     *     <li><b>object restriction</b>: a range is defined to have object properties into a given class.
     *                       It is also possible to describe the cardinality of this restriction to be:
     *                       existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     *     </ul>
     *     This description is not considered from an OOP point of view (it is not possible
     *     to {@code build} them) since their are not entities that fall back in the {@link Axiom}
     *     representations.
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     * @param <Y> the type of restriction of the range of the defined property.
     *           (it represents the of {@link EntitySet} managed by this {@link Descriptor}.
     *
     */
    interface Range<O,J,Y>
            extends ObjectProperty<O,J>{

        @Override
        default List<MappingIntent> readSemantic(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseRangeObjectPropertyFromSemantic();
                if (from != null) {
                    getRangeObjectProperty().addAll(from.getToAdd());
                    getRangeObjectProperty().removeAll(from.getToRemove());
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
        EntitySet<Y> getRangeObjectProperty();

        /**
         * Queries to the OWL representation for the range restrictions of {@code this} object property.
         * @return a new {@link EntitySet} contained the range restrictions of {@link #getInstance()},
         * into the OWL structure.
         */
        EntitySet<Y> queryRangeObjectProperty();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryRangeObjectProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getRangeObjectProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the range restriction of {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseRangeObjectPropertyToSemantic(){
            try {
                return getRangeObjectProperty().synchroniseTo( queryRangeObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticEntitySet#synchroniseFrom(EntitySet)} with {@link #queryRangeObjectProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getRangeObjectProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the range restrictions of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseRangeObjectPropertyFromSemantic(){
            try{
                return getRangeObjectProperty().synchroniseFrom( queryRangeObjectProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
