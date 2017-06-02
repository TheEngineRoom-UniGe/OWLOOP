package it.emarolab.owloop.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The main interface for ontological data property {@link Semantic.Descriptor}.
 * <p>
 *     This interface contains all the {@link Semantic.Descriptor} that
 *     can be applied to an ontological data property (e.g.: {@link org.semanticweb.owlapi.model.OWLDataProperty})
 *     in any arbitrary combination since all of them should rely on the same {@link Semantic.Ground}
 *     type.
 *     <br>
 *     More in particular, for the {@link #getInstance()} entities in the {@link #getOntology()}, those are:
 *     <ul>
 *     <li><b>{@link Disjoint}</b>: for describing disjointed properties from the specific
 *                                         grounded {@link DataProperty}.</li>
 *     <li><b>{@link Equivalent}</b>: for describing equivalent properties from the specific
 *                                         grounded {@link DataProperty}.</li>
 *     <li><b>{@link Sub}</b>: for representing the sub data properties of
 *                                         this grounded {@link DataProperty}.</li>
 *     <li><b>{@link Super}</b>: for representing the super data properties of
 *                                         this grounded {@link DataProperty}.</li>
 *     <li><b>{@link Domain}</b>: for representing the domain restrictions of the
 *                                         this grounded {@link DataProperty}.</li>
 *     <li><b>{@link Range}</b>: for representing the range restrictions of the
 *                                         this grounded {@link DataProperty}.</li>
 *     </ul>
 *     Nevertheless, they are still generic and not attached to any specific OWL implementation.
 *     Since they implements common feature of OWLLOOP architecture only.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.core.DataProperty <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 *
 * @param <O> the type of ontology in which the axioms for classes will be applied.
 * @param <J> the type of instance (i.e.: data properties) for the axioms.
 */
public interface DataProperty<O,J>
        extends Semantic.Descriptor<O,J>{

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
     * The {@link Semantic.Descriptor} for disjointed data properties.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the disjointed data properties of {@code this}
     *     property (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.DataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described data property
     *           (it also represents the type of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link DataProperty} descriptor instantiated during
     *           {@link #buildDisjointDataProperty()} through {@link #getNewDisjointDataProperty(Object, Object)}.
     */
    interface Disjoint<O,J,D extends DataProperty<O,J>>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readSemantic(){
            Axioms.SynchronisationIntent<J> from = synchroniseDisjointDataPropertyFromSemantic();
            getDisjointDataProperty().addAll( from.getToAdd());
            getDisjointDataProperty().removeAll( from.getToRemove());
            return getIntent( from);
        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
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
                built.readSemantic();
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
         * @return a new {@link Semantic.Descriptor} for all the disjointed properties
         * of the one described by {@code this} interface.
         */
        D getNewDisjointDataProperty(J instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the disjoint data properties of
         * {@code this} grounded {@link DataProperty}; from a no OOP point of view.
         * @return the entities describing the disjoint data properties of {@code this} described property.
         */
        Axioms<J> getDisjointDataProperty();

        /**
         * Queries to the OWL representation for the disjoint properties of {@code this} data property.
         * @return a new {@link Semantic.Axioms} contained the disjoint properties of {@link #getInstance()},
         * into the OWL structure.
         */
        Axioms<J> queryDisjointDataProperty();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #queryDisjointDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointDataProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the disjoint properties of {@link #getInstance()}; to the OWL representation.
         */
        default Axioms.SynchronisationIntent<J> synchroniseDisjointDataPropertyToSemantic(){
            try {
                return getDisjointDataProperty().synchroniseTo( queryDisjointDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticAxioms#synchroniseFrom(Axioms)} with {@link #queryDisjointDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the disjoint data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default Axioms.SynchronisationIntent<J> synchroniseDisjointDataPropertyFromSemantic(){
            try{
                return getDisjointDataProperty().synchroniseFrom( queryDisjointDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for equivalent data properties.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the equivalent data properties of {@code this}
     *     property (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.DataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described data property
     *           (it also represents the type of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link DataProperty} descriptor instantiated during
     *           {@link #buildEquivalentDataProperty()} through {@link #getNewEquivalentDataProperty(Object, Object)}.
     */
    interface Equivalent<O,J,D extends DataProperty<O,J>>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<J> from = synchroniseEquivalentDataPropertyFromSemantic();
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
         * Create an {@link Semantic.Descriptor} set where each element
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
                built.readSemantic();
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
         * @return a new {@link Semantic.Descriptor} for all the equivalent properties
         * of the one described by {@code this} interface.
         */
        D getNewEquivalentDataProperty(J instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the equivalent data properties of
         * {@code this} grounded {@link DataProperty}; from a no OOP point of view.
         * @return the entities describing the equivalent data properties of {@code this} described property.
         */
        Axioms<J> getEquivalentDataProperty();

        /**
         * Queries to the OWL representation for the equivalent properties of {@code this} data property.
         * @return a new {@link Semantic.Axioms} contained the equivalent properties of {@link #getInstance()},
         * into the OWL structure.
         */
        Axioms<J> queryEquivalentDataProperty();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #queryEquivalentDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentDataProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent properties of {@link #getInstance()}; to the OWL representation.
         */
        default Axioms.SynchronisationIntent<J> synchroniseEquivalentDataPropertyToSemantic(){
            try {
                return getEquivalentDataProperty().synchroniseTo( queryEquivalentDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticAxioms#synchroniseFrom(Axioms)} with {@link #queryEquivalentDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the equivalent data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default Axioms.SynchronisationIntent<J> synchroniseEquivalentDataPropertyFromSemantic(){
            try{
                return getEquivalentDataProperty().synchroniseFrom( queryEquivalentDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for sub data properties.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the sub data properties of {@code this}
     *     property (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.DataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described data property
     *           (it also represents the type of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link DataProperty} descriptor instantiated during
     *           {@link #buildSubDataProperty()} through {@link #getNewSubDataProperty(Object, Object)}.
     */
    interface Sub<O,J,D extends DataProperty<O,J>>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<J> from = synchroniseSubDataPropertyFromSemantic();
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
         * Create an {@link Semantic.Descriptor} set where each element
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
                built.readSemantic();
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
         * @return a new {@link Semantic.Descriptor} for all the sub properties
         * of the one described by {@code this} interface.
         */
        D getNewSubDataProperty( J instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the sub data properties of
         * {@code this} grounded {@link DataProperty}; from a no OOP point of view.
         * @return the entities describing the sub data properties of {@code this} described property.
         */
        Axioms<J> getSubDataProperty();

        /**
         * Queries to the OWL representation for the sub properties of {@code this} data property.
         * @return a new {@link Semantic.Axioms} contained the sub properties of {@link #getInstance()},
         * into the OWL structure.
         */
        Axioms<J> querySubDataProperty();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #querySubDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSubDataProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the sub properties of {@link #getInstance()}; to the OWL representation.
         */
        default Axioms.SynchronisationIntent<J> synchroniseSubDataPropertyToSemantic(){
            try {
                return getSubDataProperty().synchroniseTo( querySubDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticAxioms#synchroniseFrom(Axioms)} with {@link #querySubDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getSubDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the sub data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default Axioms.SynchronisationIntent<J> synchroniseSubDataPropertyFromSemantic(){
            try{
                return getSubDataProperty().synchroniseFrom( querySubDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for super data properties.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the super data properties of {@code this}
     *     property (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.DataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described data property
     *           (it also represents the type of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link DataProperty} descriptor instantiated during
     *           {@link #buildSuperDataProperty()} through {@link #getNewSuperDataProperty(Object, Object)}.
     */
    interface Super<O,J,D extends DataProperty<O,J>>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<J> from = synchroniseSuperDataPropertyFromSemantic();
                getSuperDataProperty().addAll(from.getToAdd());
                getSuperDataProperty().removeAll(from.getToRemove());
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
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
                built.readSemantic();
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
         * @return a new {@link Semantic.Descriptor} for all the super properties
         * of the one described by {@code this} interface.
         */
        D getNewSuperDataProperty(J instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the super data properties of
         * {@code this} grounded {@link DataProperty}; from a no OOP point of view.
         * @return the entities describing the super data properties of {@code this} described property.
         */
        Axioms<J> getSuperDataProperty();

        /**
         * Queries to the OWL representation for the super properties of {@code this} data property.
         * @return a new {@link Semantic.Axioms} contained the super properties of {@link #getInstance()},
         * into the OWL structure.
         */
        Axioms<J> querySuperDataProperty();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #querySuperDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getSuperDataProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the super properties of {@link #getInstance()}; to the OWL representation.
         */
        default Axioms.SynchronisationIntent<J> synchroniseSuperDataPropertyToSemantic(){
            try {
                return getSuperDataProperty().synchroniseTo( querySuperDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticAxioms#synchroniseFrom(Axioms)} with {@link #querySuperDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getSuperDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the super data properties of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default Axioms.SynchronisationIntent<J> synchroniseSuperDataPropertyFromSemantic(){
            try{
                return getSuperDataProperty().synchroniseFrom( querySuperDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for the definition of an ontological data property domain.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the definition of a specific data property domain
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     *     Definition is defined as conjunction of restriction properties that
     *     creates classes in the domain of the described property.
     *     More in details, such a restriction can be of the type:
     *     <ul>
     *     <li><b>class restriction</b>: a domain is restricted to a class.</li>
     *     <li><b>data restriction</b>: a domain is defined to have data properties into a given range.
     *                       It is also possible to describe the cardinality of this restriction to be:
     *                       existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     *     <li><b>object restriction</b>: a domain is defined to have data properties into a given class.
     *                       It is also possible to describe the cardinality of this restriction to be:
     *                       existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     *     </ul>
     *     This description is not considered from an OOP point of view (it is not possible
     *     to {@code build} them) since their are not entities that fall back in the {@link Semantic}
     *     representations.
     *
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.DataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     * @param <Y> the type of restriction of the domain of the defined property.
     *           (it represents the of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     */
    interface Domain<O,J,Y>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<Y> from = synchroniseDomainDataPropertyFromSemantic();
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
         * Returns the {@link Semantic.Axioms} that describes all the restrictions of the
         * domain of the described property; from a no OOP point of view.
         * @return the restrictions describing the domain of {@code this} grounded the data property.
         */
        Axioms<Y> getDomainDataProperty();

        /**
         * Queries to the OWL representation for the domain restrictions of {@code this} data property.
         * @return a new {@link Semantic.Axioms} contained the domain restrictions of {@link #getInstance()},
         * into the OWL structure.
         */
        Axioms<Y> queryDomainDataProperty();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #queryDomainDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDomainDataProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the domain restriction of {@link #getInstance()}; to the OWL representation.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseDomainDataPropertyToSemantic(){
            try {
                return getDomainDataProperty().synchroniseTo( queryDomainDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticAxioms#synchroniseFrom(Axioms)} with {@link #queryDomainDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getDomainDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the domain restrictions of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseDomainDataPropertyFromSemantic(){
            try{
                return getDomainDataProperty().synchroniseFrom( queryDomainDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for the definition of an ontological data property range.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the definition of a specific data property range
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     *     Definition is defined as conjunction of restriction properties that
     *     creates classes in the range of the described property.
     *     More in details, such a restriction can be of the type:
     *     <ul>
     *     <li><b>class restriction</b>: a range is restricted to a class.</li>
     *     <li><b>data restriction</b>: a range is defined to have data properties into a given range.
     *                       It is also possible to describe the cardinality of this restriction to be:
     *                       existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     *     <li><b>object restriction</b>: a range is defined to have data properties into a given class.
     *                       It is also possible to describe the cardinality of this restriction to be:
     *                       existential ({@code some}), universal ({@code all}), minimal, maximal and exact.</li>
     *     </ul>
     *     This description is not considered from an OOP point of view (it is not possible
     *     to {@code build} them) since their are not entities that fall back in the {@link Semantic}
     *     representations.
     *
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.DataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described class.
     * @param <Y> the type of restriction of the range of the defined property.
     *           (it represents the of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     *
     */
    interface Range<O,J,Y>
            extends DataProperty<O,J>{

        @Override
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<Y> from = synchroniseRangeDataPropertyFromSemantic();
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
         * Returns the {@link Semantic.Axioms} that describes all the restrictions of the
         * range of the described property; from a no OOP point of view.
         * @return the restrictions describing the range of {@code this} grounded the data property.
         */
        Axioms<Y> getRangeDataProperty();

        /**
         * Queries to the OWL representation for the range restrictions of {@code this} data property.
         * @return a new {@link Semantic.Axioms} contained the range restrictions of {@link #getInstance()},
         * into the OWL structure.
         */
        Axioms<Y> queryRangeDataProperty();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #queryRangeDataProperty()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getRangeDataProperty()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the range restriction of {@link #getInstance()}; to the OWL representation.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseRangeDataPropertyToSemantic(){
            try {
                return getRangeDataProperty().synchroniseTo( queryRangeDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticAxioms#synchroniseFrom(Axioms)} with {@link #queryRangeDataProperty()}
         * as input parameter. This computes the changes to be performed into the {@link #getRangeDataProperty()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the range restrictions of {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseRangeDataPropertyFromSemantic(){
            try{
                return getRangeDataProperty().synchroniseFrom( queryRangeDataProperty());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
