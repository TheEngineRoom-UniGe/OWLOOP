package it.emarolab.owloop.core;

import java.util.*;

/**
 *      This interface is a part of the core of OWLOOP architecture.
 *      It contains interfaces of the basic components within OWLOOP. <p>
 *      The components are the following:
 *
 *      <ul>
 *      <li><b>{@link Ground}</b>:  is an OWL entity associated to an ontology. </li>
 *      <li><b>{@link EntitySet}</b>: is a set of OWL entities associated to the {@link Ground} via an expression. </li>
 *      <li><b>{@link SemanticEntity}</b>: associates an expression to the {@link EntitySet}. </li>
 *      <li><b>{@link SemanticEntitySet}</b>: enables association of complex expressions to the {@link EntitySet}. </li>
 *      <li><b>{@link Descriptor}</b>: is in charge of mapping the ({@link Ground}) and the ({@link EntitySet}) via an (expression).
 *                                      A descriptor is also in charge of synchronizing them all, i.e, the axioms, between it's
 *                                      internal state and the OWL representation.
 *      <li><b>{@link MappingIntent}</b>: it keeps a record of the manipulations made by the {@link Descriptor}
 *                                during the synchronisation. While 'reading()' it records the changes made in the
 *                                internal state of the descriptors, and while 'writing()' it records also the changes
 *                                applied to the OWL representation. </li>
 *      </ul>
 *
 *      <div style="text-align:center;"><small>
 *      <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 *      <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 *      <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 *      <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 *      <b>date</b>:         01/05/19 <br>
 *      </small></div>
 */
public interface Axiom {

    /**
     * Is an OWL entity associated to an ontology.
     *
     * @param <O> is the ontology in which the axioms will be applied.
     * @param <J> is the grounded OWL entity.
     */
    interface Ground<O,J>
            extends Axiom {

        /**
         * @return ontology with which a {@link Descriptor} synchronizes its internal state.
         */
        O getGroundOntology();

        /**
         * @return the the grounded OWL entity.
         */
        J getGroundInstance();

        /**
         * Perform reasoning in the ontology described by {@link Ground} object.
         */
        void reason();

        /**
         * Make a copy of the {@link Ground} object.
         * Used to make each {@link MappingIntent} independent
         * for the evolution of the system.
         * This method should call a specific copyGround constructor.
         * @return a new grounded copyGround.
         */
        Ground<O,J> copyGround();
    }

    /**
     * It describe a generic set of entities managed by a specific {@link Descriptor}.
     * Given the descriptor's internal state (having the {@link EntitySet} within it) and the OWL representation (i.e., queried description),
     * it is possible to synchronise the two by using the {@link SynchronisationIntent}.
     *
     * @param <Y> the OWL entities.
     */
    interface EntitySet<Y>
            extends Collection<Y>{

        /**
         * Describes whether this EntitySet contains a single element.
         * If yes, the synchronisation procedure will replace always such an element.
         * @return (true) if this is a singleton set, (false) otherwise.
         */
        boolean isSingleton();
        /**
         * Set this EntitySet to contain a single element.
         * If yes, the synchronisation procedure will replace always such an element.
         * @param singleton (true) if this is a singleton set, (false) otherwise.
         */
        void setSingleton(boolean singleton);

        /**
         * This method is used during {@link Descriptor#writeSemantic()} and finds
         * the differences between the actual state of EntitySet and the one
         * queried to the OWL representation. Such changes represent the intent to
         * delete and add axioms to the OWL structure, to make it equal to this EntitySet.
         *
         * @param queried the set to check for writing differences from OWL to this EntitySet.
         *
         * @return the changes to be applied in the ontology to make this EntitySet
         * equal to the one queried to the OWL representation.
         */
        default SynchronisationIntent<Y> synchroniseTo(EntitySet<Y> queried){
            // synchronise to ontology (write)
            return new SynchronisationIntent<>( this, queried);
        }
        /**
         * This method is used during {@link Descriptor#readSemantic()} and finds
         * the differences between the actual state of EntitySet and the one
         * queried to the OWL representation. Such changes represents the intent to
         * delete and add elements to this EntitySet, to make it equal to the OWL representation.
         *
         * @param queried the set to check for reading differences from OWL to this EntitySet.
         *
         * @return the changes to be applied in this EntitySet to make it
         * equal to the one queried to the OWL representation.
         */
        default SynchronisationIntent<Y> synchroniseFrom(EntitySet<Y> queried){
            // synchronise from ontology (read)
            return new SynchronisationIntent<>( queried, this);
        }

        /**
         * The synchronising intent during reading or writing of {@link EntitySet}.
         * <p>
         *     It describes the changes that should be performed in a {@link EntitySet}
         *     set or in an OWL ontology during: {@link Descriptor#readSemantic()} or
         *     {@link Descriptor#writeSemantic()}.
         *     This implementation considers sets of {@link EntitySet} as a
         *     {@link HashSet} E.
         *     <br>
         *     This class is not directly instantiable but it can be assessed through:
         *     {@link EntitySet#synchroniseFrom(EntitySet)} or {@link EntitySet#synchroniseTo(EntitySet)}.
         * </p>
         *
         * @param <E> the type of entities described in an {@link EntitySet} set.
         *           It should be the same parameter (or an extension) used for
         *           a specific {@link EntitySet} implementation.
         */
        class SynchronisationIntent<E> {
            // the output of this class
            private Set<E> toAdd, toRemove, unchanged;

            /*
             * non externally instantiable (see EntitySet class).
             * It setGround th output of the class and call
             * sync(..) (or call it in the derived class).
             */
            private SynchronisationIntent(){
                initialise();
            } // called by extending class
            private SynchronisationIntent(EntitySet<E> a1, EntitySet<E> a2) {
                initialise();
                // empty set if parameter are null
                sync( copySet( a1), copySet( a2));
            }
            // copyGround this instance, used in: MappingIntent.
            private SynchronisationIntent(SynchronisationIntent<E> copy){
                this.toAdd = new HashSet<>( copy.toAdd);
                this.toRemove = new HashSet<>( copy.toRemove);
                this.unchanged = new HashSet<>( copy.unchanged);
            }
            private void initialise(){
                toAdd = new HashSet<>();
                unchanged = new HashSet<>();
                toRemove = new HashSet<>();
            }

            /*
             * make the actual synchronisation and populates the output  of the class
             * it should be called on constructor.
             * It usage and input parameters are:
             *     - read  ->  a1:queried, a2:atom.
             *     - write ->  a1:atoms,   a2:queried.
             */
            private void sync( Set<E> a1, Set<E> a2) {
                // it could be faster ....
                if ( a1.isEmpty() & a2.isEmpty())
                    return;
                if ( a1.isEmpty())
                    toRemove.addAll( a2);
                else if ( a2.isEmpty())
                    toAdd.addAll( a1);
                else {
                    unchanged = new HashSet<>(a1);
                    unchanged.retainAll( a2);
                    toAdd = new HashSet<>(a1);
                    toAdd.removeAll( a2);
                    toRemove = new HashSet<>(a2);
                    toRemove.removeAll( a1);
                }
            }

            /*
             * makes a null EntitySet as an empty set or copyGround the given
             * set for further manipulation (in the sync(..) method).
             * It manages also the singleton by copyGround only
             * the first element if necessary
             * (the others are discarded and a warning msg is produced).
             */
            private Set<E> copySet( EntitySet<E> a){
                if ( a == null)
                    return new HashSet<>();
                Set<E> copy = new HashSet<>();
                for ( E e : a){
                    copy.add( e);
                    if ( a.isSingleton()) {
                        if ( a.size() > 1) {
                            System.out.println("\t!! a singleton with size: " + a.size() + " found.");
                            System.out.println("\t!! Only element " + e + " has been considered on set: " + a);
                        }
                        break;
                    }
                }
                return copy;
            }

            /**
             * Returns the element to add to the set in order to synchronise them.
             * During {@link Descriptor#writeSemantic()} those are the axioms
             * to add in the OWL representation.
             * During {@link Descriptor#readSemantic()} those are the axioms
             * to add to the OWLOOP representation.
             * @return the element to add for synchronise the axioms sets.
             */
            public Set<E> getToAdd() {
                return toAdd;
            }

            /**
             * Returns the element to remove to the set in order to synchronise them.
             * During {@link Descriptor#writeSemantic()} those are the axioms
             * to remove from the OWL representation.
             * During {@link Descriptor#readSemantic()} those are the axioms
             * to remove from the OWLOOP representation.
             * @return the element to add for synchronise the axioms sets.
             */
            public Set<E> getToRemove() {
                return toRemove;
            }

            /**
             * Returns the elements that are in both sets.
             * During {@link Descriptor#writeSemantic()} and {@link Descriptor#readSemantic()},
             * those elements can be not considered.
             * @return the elements in both axioms sets.
             */
            public Set<E> getUnchanged() {
                return unchanged;
            }

            // equals if all output sets are equals
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof EntitySet.SynchronisationIntent)) return false;

                SynchronisationIntent<?> that = (SynchronisationIntent<?>) o;

                if (getToAdd() != null ? !getToAdd().equals(that.getToAdd()) : that.getToAdd() != null) return false;
                if (getToRemove() != null ? !getToRemove().equals(that.getToRemove()) : that.getToRemove() != null)
                    return false;
                return getUnchanged() != null ? getUnchanged().equals(that.getUnchanged()) : that.getUnchanged() == null;
            }

            @Override
            public int hashCode() {
                int result = getToAdd() != null ? getToAdd().hashCode() : 0;
                result = 31 * result + (getToRemove() != null ? getToRemove().hashCode() : 0);
                result = 31 * result + (getUnchanged() != null ? getUnchanged().hashCode() : 0);
                return result;
            }

            @Override
            public String toString() {
                return  "toAdd=" + toAdd +
                        ", toRemove=" + toRemove +
                        ", unchanged=" + unchanged;
            }

            public SynchronisationIntent<E> copy(){
                return new SynchronisationIntent<E>( this);
            }
        }

    }

    /**
     * The container for {@link EntitySet} with a specific {@code Axiom}.
     * <p>
     *     It describes an {@link EntitySet} set that are all referring to a given
     *     semantic. It is mainly used to describes the values of data or object
     *     properties (i.e.: {@link EntitySet}), when the {@code Axiom} is such a property.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Axiom <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <S> the type of the semantic of all the element in the axiom set.
     * @param <Y> the type of {@link EntitySet} described by this container.
     */
    interface SemanticEntity<S,Y>{
        /**
         * Represents the semantic (i.e.: data or object property)
         * that aggregates all {@link #getValues()} (i.e.: {@link EntitySet}).
         * @return the semantic of the {@link EntitySet} described in {@code this} container.
         */
        S getSemantic();

        /**
         * Represents the {@link EntitySet} of this container.
         * All those values are described by the same {@link #getSemantic()}.
         * @return the axioms contained in {@code this} container.
         */
        EntitySet<Y> getValues();

        /**
         * It should create a new instance of this object that preserve
         * the {@code semantic} but assign new values to it.
         * It is used during synchronisation by {@link SemanticEntitySet.SynchronisationMultiIntent}.
         * @param values the new data to be assigned to the same semantic.
         * @return a new object with the same {@link #getSemantic()} but new values.
         */
        SemanticEntity<S,Y> getNewData(Set<Y> values); // set the semantic it has
    }

    /**
     * The object that describe a set of axioms with a given semantic, managed by a specific {@link Descriptor}.
     * <p>
     *     It describes a collection of axioms that have a specific semantic and that
     *     can be grounded in an ontology with respect an instance ({@link Ground}), from a {@link Descriptor}.
     *     <br>
     *     Given the OWLOOP state of this set and the OWL representation (i.e.: queried description)
     *     it is possible to synchronise the two set (read or write) by using the
     *     {@link SynchroniseContainedIntent} class, from which the {@link SynchronisationMultiIntent}
     *     can be retrieved.
     *     <br>
     *     This class extends {@link EntitySet} by managing a set of {@link SemanticEntity},
     *     where each element contains a {@code Axiom} and a set of {@link EntitySet}.
     *     <br>
     *     By default, the synchronisation occurs only for the proprieties which semantics
     *     have been initialised in the {@link SemanticEntity}, not for all relations in the OWL representation.
     *     Note that a {@link Descriptor#readSemantic()} may remove this value if there is no such entities in the ontology.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Axiom <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <F> the type of semantic axioms assigned to this set
     * @param <Y> the type of axioms described by this set.
     */
    interface SemanticEntitySet<F extends SemanticEntity<?,Y>,Y>
            extends EntitySet<F> {

        @Override // see documentation in the super method
        default SynchronisationIntent<F> synchroniseFrom(EntitySet<F> queried) {
            // synchronise from ontology (read)
            return new SynchroniseContainedIntent<F,Y>().semanticSync( queried, this, true);
        }

        @Override // see documentation in the super method
        default SynchronisationIntent<F> synchroniseTo(EntitySet<F> queried) {
            // synchronise to ontology (write)
            return new SynchroniseContainedIntent<F,Y>().semanticSync( this, queried, false);
        }


        /* this class in private and never expose outside this interface.
         * Its purpose is to synchronise two SemanticSet and populate an
         * opportune SynchroniseMultiIntent object.
         * Its implementation is based on SynchroniseIntent.syn().
         * This class synchronises only the Semantics that have been specied in the
         * OWLOOP architecture, not for all the proprieties !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */
        class SynchroniseContainedIntent<F extends SemanticEntity<?,Y>,Y> {

            private SynchroniseContainedIntent(){} // not instantiable outside of this file

            // hp: not null inputs
            // write -> a1: atom,    a2: queried
            // read  -> a1: queried, a2: atom
            private SynchronisationMultiIntent<F,?> semanticSync(EntitySet<F> a1, EntitySet<F> a2, boolean reading){
                SynchronisationMultiIntent<F,Y> sync = new SynchronisationMultiIntent<>();
                // it could be faster ....
                if ( a1.isEmpty() & a2.isEmpty())
                    return sync;
                if ( a1.isEmpty()) {
                    for (F a : a2)
                        sync.addSynchronised(new SynchronisationIntent<>(null, a.getValues()), a);
                    return sync;
                } else if ( a2.isEmpty()) {
                    for (F a : a1)
                        sync.addSynchronised(new SynchronisationIntent<>( a.getValues(), null), a);
                    return sync;
                } else {
                    for (F b1 : a1) {
                        boolean matched = false;
                        for (F b2 : a2) {
                            // you may want to add here something to sync all properties
                            if (b1.getSemantic().equals(b2.getSemantic())) {
                                sync.addSynchronised(new SynchronisationIntent<>(b1.getValues(), b2.getValues()), b1);
                                matched = true;
                            }
                            if (a2.isSingleton()) {
                                if (a2.size() > 1) {
                                    System.out.println("\t!a singleton with size: " + a2.size() + " found.");
                                    System.out.println("\t!Only element " + b2 + " has been considered on set: " + a2);
                                }
                                break;
                            }
                        }
                        if ( !matched & !reading)
                            sync.addSynchronised(new SynchronisationIntent<>(b1.getValues(), null), b1);
                        if (a1.isSingleton()) {
                            if (a1.size() > 1) {
                                System.out.println("\t!a singleton with size: " + a1.size() + " found.");
                                System.out.println("\t!Only element " + b1 + " has been considered on set: " + a1);
                            }
                            break;
                        }
                    }
                }
                return sync;
            }
        }

        /**
         * The synchronising intent during {@link SemanticEntity} reading or writing.
         * <p>
         *     It describes the changes that should be performed in a {@link SemanticEntity}
         *     set or in an OWL ontology during: {@link Descriptor#readSemantic()} or
         *     {@link Descriptor#writeSemantic()}.
         *     This implementation considers sets of {@link EntitySet} as
         *     {@code {@link HashSet}<E>}
         *     <br>
         *     This class is not directly instantiable but it can be assessed through:
         *     {@link SemanticEntitySet#synchroniseFrom(EntitySet)} or {@link SemanticEntitySet#synchroniseTo(EntitySet)}.
         * </p>
         * <div style="text-align:center;"><small>
         * <b>File</b>:        it.emarolab.owloop.core.Axiom <br>
         * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
         * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
         * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
         * <b>date</b>:        21/05/17 <br>
         * </small></div>
         *
         * @param <E> the type of {@link SemanticEntitySet} to synchronise.
         *           It has to describe {@link EntitySet} of type Y.
         *           It should be the same parameter (or an extension) used for
         *           a specific {@link SemanticEntitySet} implementation.
         * @param <Y> the type of element described in an {@link EntitySet} set
         *           contained in E.
         */
        class SynchronisationMultiIntent<E extends SemanticEntity<?,Y>,Y>
                extends SynchronisationIntent<E> {

            /* this class is created by the SynchroniseContainedIntent class.
             * The public interface are equals to the SynchroniseIntent class
             */
            private SynchronisationMultiIntent() {
                super();
            }

            @SuppressWarnings("unchecked")
            private void addSynchronised(SynchronisationIntent<Y> intent, E t) {
                if ( ! intent.getToAdd().isEmpty())
                    getToAdd().add((E) t.getNewData(intent.getToAdd()));
                if ( ! intent.getUnchanged().isEmpty())
                    getUnchanged().add((E) t.getNewData(intent.getUnchanged()));
                if ( ! intent.getToRemove().isEmpty())
                    getToRemove().add( (E) t.getNewData(intent.getToRemove()));
            }
        }
    }


    /**
     * The interface to read and write from the ontology specific {@link EntitySet} or {@link SemanticEntitySet}.
     * <p>
     *     This class is the core of the OWLOOP implementation and is in charge to maintain
     *     the {@link EntitySet} and {@link SemanticEntitySet} synchronised with respect to an OWL
     *     representation (i.e.: {@link Ground}).
     *     <br>
     *     The paradigm through which this implement OWL accessibility through an OOP approach is
     *     given by the fact that a generic OWLLOOP class can implement several descriptors
     *     (all related to the same {@link Ground}). Doing so it enhance its feature to
     *     represent different OWL description of the same entities and gains the possibility to
     *     access them through OOP paradigms. This is true, given a correct reading and
     *     writing policy that the developer should manage.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Axiom <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <O> the type of ontology in which the axioms will be applied.
     *           It defines the related {@link Ground} parameter for {@code this} descriptor.
     * @param <J> the type of instance (i.e.: subject) for the axioms.
     *           It defines the related {@link Ground} parameter for {@code this} descriptor.
     */
    interface Descriptor<O,J>
            extends Axiom {

        /**
         * Returns the grounding element for {@code this} descriptor.
         * @return the instances using during {@link #writeSemantic()} and
         * {@link #readSemantic()} to synchronise the described {@link EntitySet}
         * (or {@link SemanticEntitySet}).
         */
        Ground<O,J> getGround();

        /**
         * Returns the ontology in which the description is operating
         * its synchronisations. It just calls: {@code {@link #getGround()}.getGroundOntology()}.
         * @return the ontology in which this description is working on.
         */
        default O getOntology(){
            return getGround().getGroundOntology();
        }

        /**
         * Returns the instance in the ontology in which the description is
         * applying the relative axioms (i.e.: subject).
         * It just calls: {@code {@link #getGround()}.getGroundInstance()}.
         * @return the instance in the ontology with which this description is working on.
         */
        default J getInstance(){
            return getGround().getGroundInstance();
        }

        /** Added by Yusha*/
        String getInstanceName();

        /**
         * This method synchronise the reasoner among all the ontology of {@code this}
         * description.
         * It calls: {@code {@link #getGround()}.groundReason()}
         */
        default void groundReason(){
            getGround().reason();
        }

        /**
         * This method is used to synchronise specific {@link EntitySet} (or {@link SemanticEntitySet})
         * from the ontology. It manipulates the OWLLOOP representation to be
         * equal to the queried OWL structure. It is based on {@code EntitySet#synchroniseFrom(EntitySet)}.
         * @return the changes made in the {@link EntitySet} during the reading.
         * Those objects may not have the {@link MappingIntent#getOntologyChanges()} field
         * initialised (i.e.: {@code Void}).
         */
        List< MappingIntent> readSemantic();

        /**
         * This method is used to synchronise specific {@link EntitySet} (or {@link SemanticEntitySet})
         * to the ontology. It manipulates the OWL representation to be
         * equal to the OWLOOP structure. It is based on {@code EntitySet#synchroniseTo(EntitySet)}.
         * @return the changes made in the OWL ontology during the writing.
         * Those objects may have the {@link MappingIntent#getOntologyChanges()} field
         * initialised (i.e.: {@link org.semanticweb.owlapi.model.OWLOntologyChange}).
         */
        List< MappingIntent> writeSemantic();

        /**
         * This method assure that after {@link #writeSemantic()} the actual OWLLOOP
         * representation is consistent (only for this descriptor) with the OWL ontology.
         * In fact the new written entities may trigger the reasoner in inferring new
         * elements in the described {@link EntitySet} set. This method, after calling
         * {@link #writeSemantic()} it calls also {@link #groundReason()} and than,
         * {@link #readSemantic()}, in order to add the new inferred elements in the
         * described set.
         * @param reason set to {@code false} to disable reasoning. This will
         *               add only asserted elements;
         * @return the changes made by the {@link #writeSemantic()} and
         * {@link #readSemantic()} operations.
         */
        default List< MappingIntent> writeSemanticInconsistencySafe( boolean reason){
            //r.addAll( readSemantic()); read also before????
            List<MappingIntent> intent = writeSemantic();
            if( reason)
                groundReason();
            intent.addAll( readSemantic());
            return intent;
        }

        /**
         * This method assure that after {@link #writeSemantic()} the actual OWLLOOP
         * representation is consistent (only for this descriptor) with the OWL ontology.
         * In fact the new written entities may trigger the reasoner in inferring new
         * elements in the described {@link EntitySet} set. This method class a
         * {@link #writeSemantic()} and than {@link #readSemantic()},
         * in order to add the new inferred elements in the
         * described set.
         * @return the changes made by the {@link #writeSemantic()} and
         * {@link #readSemantic()} operations.
         */
        default List< MappingIntent> writeSemanticInconsistencySafe(){
            return writeSemanticInconsistencySafe( false);
        }

        /**
         * It instanciate a lists of {@link MappingIntent} with the
         * given parameters and {@code Void} as {@link MappingIntent#getOntologyChanges()},
         * which will be null. This is just an helper that can be used by
         * the implementation of the {@link #readSemantic()}, in order to obtain the value
         * to be returned, based from the results of {@link EntitySet#synchroniseFrom(EntitySet)}.
         * @param sync the results of a specific call to {@link EntitySet#synchroniseFrom(EntitySet)}.
         * @return the changes based on the input parameters.
         */
        default List<MappingIntent> getIntent(EntitySet.SynchronisationIntent sync){ // read
            List<MappingIntent> intents = new ArrayList<>();
            EntitySet.SynchronisationIntent synchronisationIntent = null;
            if( sync != null)
                synchronisationIntent = sync.copy();
            intents.add( new MappingIntent<Ground<O,J>,Void>( getGround().copyGround(), synchronisationIntent));
            return intents;
        }
        /**
         * It instanciate a lists of {@link MappingIntent} with the
         * given parameters and {@code C} as {@link MappingIntent#getOntologyChanges()},
         * (e.g.: {@link org.semanticweb.owlapi.model.OWLOntologyChange}).
         * This is just an helper that can be used by
         * the implementation of the {@link #writeSemantic()}, in order to obtain the value
         * to be returned, based from the results of {@link EntitySet#synchroniseTo(EntitySet)}
         * and aMOR manipulations.
         * @param sync the results of a specific call to {@link EntitySet#synchroniseTo(EntitySet)}.
         * @param changes the ontology changes made during writing.
         * @param <C> the types of ontology changes attached to this intent.
         * @return the changes based on the input parameters.
         */
        default <C> List<MappingIntent> getChangingIntent(EntitySet.SynchronisationIntent sync, C changes){ // write
            List<MappingIntent> intents = new ArrayList<>();
            EntitySet.SynchronisationIntent synchronisationIntent = null;
            if( sync != null)
                synchronisationIntent = sync.copy();
            intents.add( new MappingIntent<>( getGround().copyGround(), synchronisationIntent, changes));
            return intents;
        }
        /*default List<MappingIntent> getIntent(EntitySet.SynchroniseIntent sync, boolean isReading){
            List<MappingIntent> intents = new ArrayList<>();
            intents.add( new MappingIntent<Ground<O,J>,Void>( getGround().copyGround(),
                    new EntitySet.SynchroniseIntent( sync), isReading));
            return intents;
        }
        default <C> List<MappingIntent> getChangingIntent(EntitySet.SynchroniseIntent sync, C changes, boolean isReading){
            List<MappingIntent> intents = new ArrayList<>();
            intents.add( new MappingIntent<>( getGround().copyGround(),
                    new EntitySet.SynchroniseIntent( sync), changes, isReading));
            return intents;
        }*/
    }

    /**
     * The class to track synchronisation changes.
     * <p>
     *     This class is used to take track of the modification in the
     *     OWLLOOP or OWL semantics done during {@link Descriptor#readSemantic()}
     *     or {@link Descriptor#writeSemantic()}.
     *     For instance it can be used to monitor the evolution of the system
     *     or role back the changes in case of inconsistency.
     *     The data in this container should be copied in order ot be
     *     independent from the evolution of the architecture.
     *     <br>
     *     In the current implementation this class is only instanciated by
     *     the helpers in the {@link Descriptor} object in such a way to have
     *     {@code Void} element for {@link #getOntologyChanges()} during
     *     reading. On the other hand, during writing, it contains instances
     *     of {@link org.semanticweb.owlapi.model.OWLOntologyChange}.
     *     In case in which an {@link Exception} occurs during synchronisation,
     *     the value {@link #getIntent()} would be {@code null}.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.core.Axiom <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <I> the {@link Ground} used during synchronisation.
     * @param <C> the type of changed done in the axioms set of the ontology.
     */
    class MappingIntent<I extends Axiom.Ground,C> {

        private long time;
        private EntitySet.SynchronisationIntent intent; // null if error occurs
        private I ground;
        private boolean writing;
        private C ontologyChanges = null;

        /**
         * Initialise this object without specifying any {@link #getOntologyChanges()}.
         * @param ground the ontology and instance used during synchronisation.
         * @param intent the differences between the OWL and OWLOOP representation.
         * @param writing {@code true} if this is generated during {@link Descriptor#writeSemantic()}.
         *                {@code false} if this is generated during {@link Descriptor#readSemantic()}.
         */
        public MappingIntent(I ground, EntitySet.SynchronisationIntent intent, boolean writing){
            initialise( ground, intent, writing);
        }
        /**
         * Initialise this object during {@link Descriptor#readSemantic()} without specifying any {@link #getOntologyChanges()}.
         * @param ground the ontology and instance used during synchronisation.
         * @param intent the differences between the OWL and OWLOOP representation.
         */
        public MappingIntent(I ground, EntitySet.SynchronisationIntent intent) {
            initialise( ground, intent, false);
        }
        /**
         * Fully setGround this object.
         * @param ground the ontology and instance used during synchronisation.
         * @param intent the differences between the OWL and OWLOOP representation.
         * @param changes the ontological changes applied during writing.
         * @param writing {@code true} if this is generated during {@link Descriptor#writeSemantic()}.
         *                {@code false} if this is generated during {@link Descriptor#readSemantic()}.
         */
        public MappingIntent(I ground, EntitySet.SynchronisationIntent intent, C changes, boolean writing) {
            initialise( ground, intent, writing);
            this.ontologyChanges = changes;
        }
        /**
         * Initialise this object during {@link Descriptor#writeSemantic()}.
         * @param ground the ontology and instance used during synchronisation.
         * @param intent the differences between the OWL and OWLOOP representation.
         * @param changes the ontological changes applied during writing.
         */
        public MappingIntent(I ground, EntitySet.SynchronisationIntent intent, C changes) {
            initialise( ground, intent, true);
            this.ontologyChanges = changes;
        }
        private void initialise(I ground, EntitySet.SynchronisationIntent intent, boolean writing){
            this.time = System.currentTimeMillis();
            this.writing = writing;
            this.ground = ground;
            this.intent = intent;
        }

        /**
         * Gives a flag ro know if this changes have been made during
         * {@link Descriptor#writeSemantic()} or {@link Descriptor#readSemantic()}.
         * @return {@code true} if it has been generated during {@link Descriptor#writeSemantic()}.
         * {@code false} otherwise.
         */
        public boolean wasWriting() {
            return writing;
        }
        /**
         * Gives a flag ro know if this changes have been made during
         * {@link Descriptor#writeSemantic()} or {@link Descriptor#readSemantic()}.
         * @return {@code true} if it has been generated during {@link Descriptor#readSemantic()}.
         * {@code false} otherwise.
         */
        public boolean wasReading() {
            return !writing;
        }

        /**
         * Returns the time in which {@code this} changes have been done.
         * @return the construction time.
         */
        public long getTime() {
            return time;
        }

        /**
         * Returns the changes within the OWl and the OWLOOP
         * representation given on construction.
         * If {@link #wasReading()} is {@code true} those are the changes made
         * on the OWLLOOP representation to become equal to the OWL ontology ({@link EntitySet#synchroniseFrom(EntitySet)}).
         * If {@link #wasWriting()} is {@code true} those are the changes made
         * on the OWL ontology to make it equal to the OWLLOOP representation ({@link EntitySet#synchroniseTo(EntitySet)}).
         * @return the differences between the OWL ontology and the OWLLOOP given during constructors.
         */
        public final EntitySet.SynchronisationIntent getIntent() {
            return intent;
        }

        /**
         * Returns the ontology and instance references over which the
         * changes have been performed during synchronisation.
         * @return a copyGround of the {@link Ground} given on constructor.
         */
        public final I getGround() {
            return ground;
        }

        /**
         * The changes made in the OWL ontology.
         * It is {@code null} if {@link #wasReading()} is {@code true}
         * @return the changes in the ontology.
         */
        public final C getOntologyChanges() {
            return ontologyChanges;
        }

        /**
         * Returns {@code true} of {@link #getIntent()} returns {@code null}.
         * @return {@code true} if an error occurs during reading or writing.
         * {@code false} otherwise.
         */
        public boolean errorOccur(){
            return intent == null;
        }

        /**
         * Return {@code true} if {@code {@link #getOntologyChanges()} = null}.
         * @return {@code true} if changes have been given during constructor.
         * {@code false} otherwise.
         */
        public boolean describesChanges(){
            return ontologyChanges != null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MappingIntent)) return false;

            MappingIntent<?, ?> that = (MappingIntent<?, ?>) o;

            if (writing != that.writing) return false;
            if (getIntent() != null ? !getIntent().equals(that.getIntent()) : that.getIntent() != null) return false;
            if (getGround() != null ? !getGround().equals(that.getGround()) : that.getGround() != null) return false;
            return getOntologyChanges() != null ? getOntologyChanges().equals(that.getOntologyChanges()) : that.getOntologyChanges() == null;
        }

        @Override
        public int hashCode() {
            int result = getIntent() != null ? getIntent().hashCode() : 0;
            result = 31 * result + (getGround() != null ? getGround().hashCode() : 0);
            result = 31 * result + (writing ? 1 : 0);
            result = 31 * result + (getOntologyChanges() != null ? getOntologyChanges().hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return  "{time=" + time +
                    ", intent=" + intent +
                    ", ground=" + ground +
                    ", writing=" + writing +
                    ", ontologyChanges=" + ontologyChanges +
                    '}';
        }
    }
}