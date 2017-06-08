package it.emarolab.owloop.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The main interface for ontological individual {@link Semantic.Descriptor}.
 * <p>
 *     This interface contains all the {@link Semantic.Descriptor} that
 *     can be applied to an ontological individual (e.g.: {@link org.semanticweb.owlapi.model.OWLNamedIndividual})
 *     in any arbitrary combination since all of them should rely on the same {@link Semantic.Ground}
 *     type.
 *     <br>
 *     More in particular, for the {@link #getInstance()} entities in the {@link #getOntology()}, those are:
 *     <ul>
 *     <li><b>{@link Type}</b>: for describing the classes of an individual.</li>
 *     <li><b>{@link Disjoint}</b>: for describing disjointed individuals for the one grounded
 *                                         in a specific {@link Individual}.</li>
 *     <li><b>{@link Equivalent}</b>: for describing equivalent individuals for the one grounded
 *                                         in a specific {@link Individual}.</li>
 *     <li><b>{@link DataLink}</b>: for representing the data properties (and related values)
 *                                         applied to an {@link Individual}.</li>
 *     <li><b>{@link ObjectLink}</b>: for representing the object properties (and related individuals value)
 *                                         applied to an {@link Individual}.</li>
 *     </ul>
 *     Nevertheless, they are still generic and not attached to any specific OWL implementation.
 *     Since they implements common feature of OWLLOOP architecture only.
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.core.Individual <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 *
 * @param <O> the type of ontology in which the axioms for classes will be applied.
 * @param <J> the type of instance (i.e.: individual) for the axioms.
 */
public interface Individual<O,J>
        extends Semantic.Descriptor<O,J>{

    /**
     * The {@link Semantic.Descriptor} for classes (e.g.: types) that describes an ontological individual.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the types in which the specific individual
     *     (i.e.: the {@link Ground#getGroundInstance()}) is classified.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Individual <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described individual.
     * @param <Y> the type of classes describing the individual
     *           (it represents the type of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Concept} descriptors instantiated during
     *           {@link #buildTypeIndividual()} through {@link #getNewTypeIndividual(Object, Object)}.
     */
    interface Type<O,J,Y,D extends Concept<O,Y>>
            extends Individual<O,J>{

        @Override // see documentation on Semantic.Descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<Y> from = synchroniseTypeIndividualFromSemantic();
                if ( from != null) {
                    getTypeIndividual().addAll(from.getToAdd());
                    getTypeIndividual().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
         * represents the classes in which {@code this} individual is belonging to.
         * Each of {@link Concept}s are instantiated
         * through the method {@link #getNewTypeIndividual(Object, Object)};
         * this is called for all {@link #getTypeIndividual()}.
         * @return the set of {@link Concept}s that describes the
         * entities in which {@code this} described ontological individual
         * is belonging to.
         */
        default Set<D> buildTypeIndividual(){
            Set<D> out = new HashSet<>();
            for( Y cl : getTypeIndividual()){
                D built = getNewTypeIndividual( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildTypeIndividual()} and
         * its purpose is to instantiate a new {@link Concept} to represent
         * the types of {@code this} {@link Individual} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Concept}.
         * @param ontology the ontology in which ground the new {@link Concept}.
         * @return a new {@link Semantic.Descriptor} for all the classes
         * in which {@code this} individual is belonging to.
         */
        D getNewTypeIndividual(Y instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the classes in which
         * {@code this} {@link Individual} is belonging to, from a no OOP point of view.
         * @return the entities describing the types of {@code this} individual.
         */
        Axioms<Y> getTypeIndividual();

        /**
         * Queries to the OWL representation for the types of {@code this} individual.
         * @return a new {@link Semantic.Axioms} contained the classes in which {@link #getInstance()}
         * is belonging to, into the OWL structure.
         */
        Axioms<Y> queryTypeIndividual();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #queryTypeIndividual()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getTypeIndividual()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the classes in which {@link #getInstance()} is belonging to, in the OWL representation.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseTypeIndividualToSemantic(){
            try {
                return getTypeIndividual().synchroniseTo( queryTypeIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link Semantic.Axioms#synchroniseFrom(Axioms)} with {@link #queryTypeIndividual()}
         * as input parameter. This computes the changes to be performed into the {@link #getTypeIndividual()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the classes in which the {@link #getInstance()}
         * individual is belonging to, from an OWL representation to {@code this} descriptor.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseTypeIndividualFromSemantic(){
            try{
                return getTypeIndividual().synchroniseFrom( queryTypeIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for individuals that are different from the one described.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the different individuals from the described one
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Individual <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described individual.
     *           (it also represents the type of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Individual} descriptors instantiated during
     *           {@link #buildDisjointIndividual()} through {@link #getNewDisjointIndividual(Object, Object)}.
     */
    interface Disjoint<O,J,D extends Individual<O,J>>
            extends Individual<O,J>{

        @Override // see documentation on Semantic.Descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<J> from = synchroniseDisjointIndividualFromSemantic();
                if ( from != null) {
                    getDisjointIndividual().addAll(from.getToAdd());
                    getDisjointIndividual().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
         * represents the different individuals from {@code this} description.
         * Each of {@link Individual}s are instantiated
         * through the method {@link #getNewDisjointIndividual(Object, Object)};
         * this is called for all {@link #getDisjointIndividual()}.
         * @return the set of {@link Individual}s that describes the
         * entities that are different from {@code this} described ontological individual.
         */
        default Set<D> buildDisjointIndividual(){
            Set<D> out = new HashSet<>();
            for( J cl : getDisjointIndividual()){
                D built = getNewDisjointIndividual( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildDisjointIndividual()} and
         * its purpose is to instantiate a new {@link Individual} to represent
         * an equivalent individual from {@code this} {@link Individual} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Individual}.
         * @param ontology the ontology in which ground the new {@link Individual}.
         * @return a new {@link Semantic.Descriptor} for all the individuals
         * that are equivalent from the one described by {@code this} interface.
         */
        D getNewDisjointIndividual(J instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the different individual from
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the different individual of {@code this} individual.
         */
        Axioms<J> getDisjointIndividual();

        /**
         * Queries to the OWL representation for the different individuals from {@code this} {@link Descriptor}.
         * @return a new {@link Semantic.Axioms} contained the individuals different from {@link #getInstance()};
         * into the OWL structure.
         */
        Axioms<J> queryDisjointIndividual();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #queryDisjointIndividual()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointIndividual()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the different individuals from {@link #getInstance()}; to the OWL representation.
         */
        default Axioms.SynchronisationIntent<J> synchroniseDisjointIndividualToSemantic(){
            try {
                return getDisjointIndividual().synchroniseTo( queryDisjointIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link Semantic.Axioms#synchroniseFrom(Axioms)} with {@link #queryDisjointIndividual()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointIndividual()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the different individuals from {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default Axioms.SynchronisationIntent<J> synchroniseDisjointIndividualFromSemantic(){
            try{
                return getDisjointIndividual().synchroniseFrom( queryDisjointIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for individuals that are equivalent from the one described.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises the equivalent individuals from the described one
     *     (i.e.: the {@link Ground#getGroundInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Individual <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described individual.
     *           (it also represents the type of {@link Semantic.Axioms} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Individual} descriptors instantiated during
     *           {@link #buildEquivalentIndividual()} through {@link #getNewEquivalentIndividual(Object, Object)}.
     */
    interface Equivalent<O,J,D extends Individual<O,J>>
            extends Individual<O,J>{

        @Override // see documentation on Semantic.Descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<J> from = synchroniseEquivalentIndividualFromSemantic();
                getEquivalentIndividual().addAll(from.getToAdd());
                getEquivalentIndividual().removeAll(from.getToRemove());
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
         * represents the equivalent individuals from {@code this} description.
         * Each of {@link Individual}s are instantiated
         * through the method {@link #getNewEquivalentIndividual(Object, Object)};
         * this is called for all {@link #getEquivalentIndividual()}.
         * @return the set of {@link Individual}s that describes the
         * entities that are equivalent from {@code this} described ontological individual.
         */
        default Set<D> buildEquivalentIndividual(){
            Set<D> out = new HashSet<>();
            for( J cl : getEquivalentIndividual()){
                D built = getNewEquivalentIndividual( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildEquivalentIndividual()} and
         * its purpose is to instantiate a new {@link Individual} to represent
         * a different individual from {@code this} {@link Individual} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Individual}.
         * @param ontology the ontology in which ground the new {@link Individual}.
         * @return a new {@link Semantic.Descriptor} for all the individuals
         * that are different from the one described by {@code this} interface.
         */
        D getNewEquivalentIndividual(J instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes all the equivalent individual from
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the equivalent individual of {@code this} individual.
         */
        Axioms<J> getEquivalentIndividual();

        /**
         * Queries to the OWL representation for the equivalent individuals of {@code this} {@link Descriptor}.
         * @return a new {@link Semantic.Axioms} contained the individuals equivalent from {@link #getInstance()};
         * into the OWL structure.
         */
        Axioms<J> queryEquivalentIndividual();

        /**
         * It calls {@link Semantic.Axioms#synchroniseTo(Axioms)} with {@link #queryEquivalentIndividual()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentIndividual()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent individuals from {@link #getInstance()}; to the OWL representation.
         */
        default Axioms.SynchronisationIntent<J> synchroniseEquivalentIndividualToSemantic(){
            try {
                return getEquivalentIndividual().synchroniseTo( queryEquivalentIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link Semantic.Axioms#synchroniseFrom(Axioms)} with {@link #queryEquivalentIndividual()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentIndividual()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the equivalent individuals from {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default Axioms.SynchronisationIntent<J> synchroniseEquivalentIndividualFromSemantic(){
            try{
                return getEquivalentIndividual().synchroniseFrom( queryEquivalentIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for the data properties applied to the described individuals.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises only the specified data property and
     *     relative values for an ontological individual (i.e.: the {@link Ground#getGroundInstance()}).
     *     <br>
     *     By default, the synchronisation occurs only for the proprieties which semantics
     *     have been initialised in the {@link SemanticAxioms} ({@link #getDataSemantics()}.
     *     Anyway if the {@link SemanticAxioms} is leaved empty during {@link #readSemantic()}
     *     it maps all the data properties applied to the described individual.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Individual <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described individual.
     * @param <Y> the type of {@link SemanticAxiom} synchronised by this descriptor
     * @param <S> the type of semantic described by this class (i.e.: {@code OWLDataProperty})
     * @param <D> the type of the {@link DataProperty} descriptors instantiated during
     *           {@link #buildDataIndividual()} through {@link #getNewDataIndividual(SemanticAxiom, Object)}.
     */
    interface DataLink<O,J,Y extends SemanticAxiom<S,?>, S,D extends DataProperty<O, S>>
            extends Individual<O,J>{

        @Override // see documentation on Semantic.Descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                Axioms.SynchronisationIntent<Y> from = synchroniseDataIndividualFromSemantic();
                if (from != null) {
                    getDataSemantics().addAll(from.getToAdd());
                    getDataSemantics().removeAll(from.getToRemove());
                }
                return getIntent(from);
            }catch (Exception e){
                e.printStackTrace();
                return getIntent(null);
            }
        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
         * represents the specified data properties applied to this {@code this} description.
         * Each of {@link DataProperty}s are instantiated
         * through the method {@link #getNewDataIndividual(SemanticAxiom, Object)};
         * this is called for all {@link #getDataSemantics()}.
         * @return the set of {@link DataProperty}s that describes the
         * entities that are applied to {@code this} described ontological individual.
         */
        default Set<D> buildDataIndividual(){
            Set<D> out = new HashSet<>();
            for( Y cl : getDataSemantics()){
                D built = getNewDataIndividual( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildDataIndividual()} and
         * its purpose is to instantiate a new {@link DataProperty} to represent
         * a data value applied to {@code this} {@link Individual} {@link Descriptor}.
         * @param instance the instance to ground the new {@link DataLink}.
         * @param ontology the ontology in which ground the new {@link DataProperty}.
         * @return a new {@link Semantic.Descriptor} for all the data properties
         * that are applied to {@code this} interface.
         */
        D getNewDataIndividual(Y instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes the specified data properties applied to
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the data properties of {@code this} individual.
         */
        Axioms<Y> getDataSemantics();

        /**
         * Queries to the OWL representation for the data properties applied to {@code this} {@link Descriptor}.
         * @return a new {@link SemanticAxioms} contained the data properties of {@link #getInstance()};
         * into the OWL structure.
         */
        Axioms<Y> queryDataIndividual();

        /**
         * It calls {@link SemanticAxioms#synchroniseTo(Axioms)} with {@link #queryDataIndividual()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDataSemantics()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the data properties applied on {@link #getInstance()}; to the OWL representation.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseDataIndividualToSemantic(){
            try {
                return getDataSemantics().synchroniseTo( queryDataIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticAxioms#synchroniseFrom(Axioms)} with {@link #queryDataIndividual()}
         * as input parameter. This computes the changes to be performed into the {@link #getDataSemantics()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the data properties applied on {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseDataIndividualFromSemantic(){
            try{
                return getDataSemantics().synchroniseFrom( queryDataIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * The {@link Semantic.Descriptor} for the object properties applied to the described individuals.
     * <p>
     *     This {@link Semantic.Descriptor} synchronises only the specified object property and
     *     relative values for an ontological individual (i.e.: the {@link Ground#getGroundInstance()}).
     *     <br>
     *     By default, the synchronisation occurs only for the proprieties which semantics
     *     have been initialised in the {@link SemanticAxioms} ({@link #getObjectSemantics()}),
     *     not for all relations in the OWL representation.
     *     Anyway if the {@link SemanticAxioms} is leaved empty during {@link #readSemantic()}
     *     it maps all the object properties applied to the described individual.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Individual <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms for classes will be applied.
     * @param <J> the type of the described individual.
     * @param <Y> the type of {@link SemanticAxiom} synchronised by this descriptor
     * @param <S> the type of semantic described by this class (i.e.: {@code OWLObjectProperty})
     * @param <D> the type of the {@link DataProperty} descriptors instantiated during
     *           {@link #buildObjectIndividual()} through {@link #getNewObjectIndividual(SemanticAxiom, Object)}.
     */
    interface ObjectLink<O,J,Y extends SemanticAxiom<S,?>, S,D extends ObjectProperty<O, S>>
            extends Individual<O,J>{

        @Override // see documentation on Semantic.Descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try{
                Axioms.SynchronisationIntent<Y> from = synchroniseObjectIndividualFromSemantic();
                if (from != null) {
                    getObjectSemantics().addAll(from.getToAdd());
                    getObjectSemantics().removeAll(from.getToRemove());
                }
                return getIntent( from);
            }catch (Exception e){
                e.printStackTrace();
                return getIntent(null);
            }
        }

        /**
         * Create an {@link Semantic.Descriptor} set where each element
         * represents the specified object properties applied to this {@code this} description.
         * Each of {@link ObjectProperty}s are instantiated
         * through the method {@link #getNewObjectIndividual(SemanticAxiom, Object)};
         * this is called for all {@link #getObjectSemantics()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * entities that are applied to {@code this} described ontological individual.
         */
        default Set< D> buildObjectIndividual(){
            Set<D> out = new HashSet<>();
            for( Y cl : getObjectSemantics()){
                D built = getNewObjectIndividual( cl, getOntology());
                built.readSemantic();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildObjectIndividual()} and
         * its purpose is to instantiate a new {@link ObjectProperty} to represent
         * an object value applied to {@code this} {@link Individual} {@link Descriptor}.
         * @param instance the instance to ground the new {@link ObjectProperty}.
         * @param ontology the ontology in which ground the new {@link ObjectProperty}.
         * @return a new {@link Semantic.Descriptor} for all the object properties
         * that are applied to {@code this} interface.
         */
        D getNewObjectIndividual(Y instance, O ontology);

        /**
         * Returns the {@link Semantic.Axioms} that describes the specified object properties applied to
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the object properties of {@code this} individual.
         */
        Axioms<Y> getObjectSemantics();

        /**
         * Queries to the OWL representation for the data properties applied to {@code this} {@link Descriptor}.
         * @return a new {@link SemanticAxioms} contained the object properties of {@link #getInstance()};
         * into the OWL structure.
         */
        Axioms<Y> queryObject();

        /**
         * It calls {@link SemanticAxioms#synchroniseTo(Axioms)} with {@link #queryObject()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getObjectSemantics()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the object properties applied on {@link #getInstance()}; to the OWL representation.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseObjectIndividualToSemantic(){
            try {
                return getObjectSemantics().synchroniseTo( queryObject());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link SemanticAxioms#synchroniseFrom(Axioms)} with {@link #queryObject()}
         * as input parameter. This computes the changes to be performed into the {@link #getObjectSemantics()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the object properties applied on {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default Axioms.SynchronisationIntent<Y> synchroniseObjectIndividualFromSemantic(){
            try{
                return getObjectSemantics().synchroniseFrom( queryObject());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

}
