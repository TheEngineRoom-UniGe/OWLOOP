package it.emarolab.owloop.core;

import java.util.*;

/**
 * This interface is a part of the core of OWLOOP architecture.
 * It contains interfaces of the basic components within OWLOOP. These components are used to define and give structure to a descriptor. <p>
 * The components are the following:
 *
 * <ul>
 * <li><b>{@link Ground}</b>:              is an OWL entity associated to an ontology. </li>
 * <li><b>{@link EntitySet}</b>:           is a set of OWL entities associated to the {@link Ground} via an expression. </li>
 * <li><b>{@link ExpressionEntity}</b>:    associates an expression to the {@link EntitySet}. </li>
 * <li><b>{@link ExpressionEntitySet}</b>: enables association of complex expressions to the {@link EntitySet}. </li>
 * <li><b>{@link Descriptor}</b>:          is in charge of mapping the ({@link Ground}) and the ({@link EntitySet}) via an (expression).
 *                                         A descriptor is also in charge of synchronizing them all, i.e, the axioms, between it's
 *                                         internal state and the OWL representation.
 * <li><b>{@link MappingIntent}</b>:       it keeps a record of the manipulations made by the {@link Descriptor}
 *                                         during the synchronisation. While 'reading()' it records the changes made in the
 *                                         internal state of the descriptors, and while 'writing()' it records also the changes
 *                                         applied to the OWL representation. </li>
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
 */
public interface Axiom {

    /**
     * Is an OWL entity associated to an ontology.
     *
     * @param <O> is the ontology.
     * @param <J> is the ground.
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
         * This method is used during {@link Descriptor#writeExpressionAxioms()} and finds
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
         * This method is used during {@link Descriptor#readExpressionAxioms()} and finds
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
         *     set or in an OWL ontology during: {@link Descriptor#readExpressionAxioms()} or
         *     {@link Descriptor#writeExpressionAxioms()}.
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
             * During {@link Descriptor#writeExpressionAxioms()} those are the axioms
             * to add in the OWL representation.
             * During {@link Descriptor#readExpressionAxioms()} those are the axioms
             * to add to the OWLOOP representation.
             * @return the element to add for synchronise the axioms sets.
             */
            public Set<E> getToAdd() {
                return toAdd;
            }

            /**
             * Returns the element to remove to the set in order to synchronise them.
             * During {@link Descriptor#writeExpressionAxioms()} those are the axioms
             * to remove from the OWL representation.
             * During {@link Descriptor#readExpressionAxioms()} those are the axioms
             * to remove from the OWLOOP representation.
             * @return the element to add for synchronise the axioms sets.
             */
            public Set<E> getToRemove() {
                return toRemove;
            }

            /**
             * Returns the elements that are in both sets.
             * During {@link Descriptor#writeExpressionAxioms()} and {@link Descriptor#readExpressionAxioms()},
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
     *It describes an {@link EntitySet} associated to a given expression.
     *
     * @param <S> the type of expression.
     * @param <Y> the {@link EntitySet}.
     */
    interface ExpressionEntity<S,Y>{
        /**
         * Gets the expression.
         * @return the expression associated to the {@link EntitySet}.
         */
        S getExpression();

        /**
         * Gets the values (i.e., elements) in the {@link EntitySet}.
         * @return the values in the {@link EntitySet}.
         */
        EntitySet<Y> getValues();

        /**
         * Creates a new instance of {@link ExpressionEntity} that preserves
         * the expression but assigns new values to it. <p>
         * It is used during synchronisation by {@link ExpressionEntitySet.SynchronisationMultiIntent}.
         *
         * @param values the new values to be assigned to the expression.
         *
         * @return a new {@link ExpressionEntity} with the same expression but new values.
         */
        ExpressionEntity<S,Y> getNewData(Set<Y> values);
    }

    /**
     *Enables to associate a set of expressions, within which each is associated to an EntitySet.
     *Therefore each EntitySet is associated to a specific expression and
     *grounded with a specific ({@link Ground}).
     *<br>
     *Given the OWLOOP state of this set and the OWL representation (i.e.: queried description)
     *it is possible to synchronise (read or write) the two set by using the
     *{@link SynchroniseContainedIntent} class, from which the {@link SynchronisationMultiIntent}
     *can be retrieved.
     *
     * @param <F> the ExpressionEntities.
     * @param <Y> the Entities.
     */
    interface ExpressionEntitySet<F extends ExpressionEntity<?,Y>,Y>
            extends EntitySet<F> {

        @Override // see documentation in the super method
        default SynchronisationIntent<F> synchroniseFrom(EntitySet<F> queried) {
            // synchronise from ontology (read)
            return new SynchroniseContainedIntent<F,Y>().expressionAxiomsSync( queried, this); // reading
        }

        @Override // see documentation in the super method
        default SynchronisationIntent<F> synchroniseTo(EntitySet<F> queried) {
            // synchronise to ontology (write)
            return new SynchroniseContainedIntent<F,Y>().expressionAxiomsSync( this, queried); // writing
        }


        /* this class in private and never expose outside this interface.
         * Its purpose is to synchronise two expressionAxioms and populate an
         * opportune SynchroniseMultiIntent object.
         * Its implementation is based on SynchroniseIntent.syn().
         * This class synchronises only the expressionAxioms that have been specied in the
         * OWLOOP architecture, not for all the proprieties !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */
        class SynchroniseContainedIntent<F extends ExpressionEntity<?,Y>,Y> {

            private SynchroniseContainedIntent(){} // not instantiable outside of this file

            // hp: not null inputs
            // write -> a1: internal state,  a2: queried.
            // read  -> a1: queried,         a2: internal state.
            private SynchronisationMultiIntent<F,?> expressionAxiomsSync(EntitySet<F> a1, EntitySet<F> a2){
                SynchronisationMultiIntent<F,Y> sync = new SynchronisationMultiIntent<>();

                // it could be faster ....
                if ( a1.isEmpty() & a2.isEmpty())
                    return sync;
                if ( a1.isEmpty()) {
                    for (F a : a2)
                        sync.addSynchronised(new SynchronisationIntent<>(null, a.getValues()), a);
                    return sync;
                } 
                if ( a2.isEmpty()) {
                    for (F a : a1)
                        sync.addSynchronised(new SynchronisationIntent<>( a.getValues(), null), a);
                    return sync;
                }

                for (F b1 : a1) {
                    boolean found = false;
                    for (F b2 : a2) {
                        // you may want to add here something to sync all object/data properties (now it sync only the value of the property described in the internal state of a description )
                        if (b1.getExpression().equals(b2.getExpression())) {
                            // sync common values between a1 and a2
                            sync.addSynchronised(new SynchronisationIntent<>(b1.getValues(), b2.getValues()), b1);
                            found = true;
                            break;
                        }
                        if ( checkSingletton( a2, b2))
                            break;
                    }

                    // add in case of writing (remove in case of reading) where a1 contains elements not in a2
                    if ( ! found)
                        sync.addSynchronised(new SynchronisationIntent<>(b1.getValues(), null), b1);
                    if ( checkSingletton( a1, b1))
                        break;
                }

                for (F b2 : a2) {
                    boolean found = false;
                    for (F b1 : a1) {
                        if (b1.getExpression().equals(b2.getExpression())) {
                            found = true;
                            break;
                        }
                    }
                    // add in case of reading (remove in case of writing) where a2 contains element not in a1
                    if ( !found)
                        sync.addSynchronised(new SynchronisationIntent<>(null, b2.getValues()), b2);
                }
                return sync;
            }


            private boolean checkSingletton( EntitySet<F> a, F b){
                if (a.isSingleton()) {
                    if (a.size() > 1) {
                        System.out.println("\t!a singleton with size: " + a.size() + " found.");
                        System.out.println("\t!Only element " + b + " has been considered on set: " + a);
                    }
                    return true;
                }
                return false;
            }
        }

        /**
         * The synchronising intent used during {@link ExpressionEntity} reading or writing.
         * <p>
         *     It describes the changes that should be performed in a {@link ExpressionEntity}
         *     set or in an OWL ontology during: {@link Descriptor#readExpressionAxioms()} or
         *     {@link Descriptor#writeExpressionAxioms()}.
         *     This implementation considers sets of {@link EntitySet} as
         *     {@code {@link HashSet} E}
         *     <br>
         *     This class is not directly instantiable but it can be assessed through:
         *     {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} or {@link ExpressionEntitySet#synchroniseTo(EntitySet)}.
         * </p>
         *
         * @param <E> the type of {@link ExpressionEntitySet} to synchronise.
         *           It has to describe {@link EntitySet} of type Y.
         *           It should be the same parameter (or an extension) used for
         *           a specific {@link ExpressionEntitySet} implementation.
         * @param <Y> the type of elements described in an {@link EntitySet} set
         *           contained in E.
         */
        class SynchronisationMultiIntent<E extends ExpressionEntity<?,Y>,Y>
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
     * Descriptors are OWLOOP representation of axioms.
     * A {@link Descriptor} deals with axioms by having the structure [Ground,Expression,EntitySet].
     * It enables reading-from and writing-to an ontology, specific {@link EntitySet} or {@link ExpressionEntitySet}.
     * A {@link Descriptor} is in charge of maintaining its {@link EntitySet} and {@link ExpressionEntitySet}
     * synchronised with respect to axioms within an OWL representation.
     *
     * @param <O> the ontology in which the axioms will be applied.
     * @param <J> the {@link Ground} for this descriptor.
     */
    interface Descriptor<O,J>
            extends Axiom {

        /**
         * @return the ground.
         */
        Ground<O,J> getGround();

        /**
         * @return the ontology.
         */
        default O getOntologyReference(){
            return getGround().getGroundOntology();
        }

        /**
         * @return the instance of the ground within the ontology.
         */
        default J getInstance(){
            return getGround().getGroundInstance();
        }

        /**
         * @return the instance of the ground within the ontology, as a String.
         */
        String getGroundInstanceName();

        /**
         * This method synchronise the reasoner of the ontology (with which the descriptor is interacting). <p>
         * It calls: {{@link #getGround()}.groundReason()}
         */
        default void groundReason(){
            getGround().reason();
        }

        /**
         * This method is used to update specific {@link EntitySet} (or {@link ExpressionEntitySet}) by synchronizing
         * the internal state of the descriptor with the queried OWL structure, such that they are equal.
         * It is based on {@link EntitySet#synchroniseFrom(EntitySet)}.
         *
         * @return the changes made in the {@link EntitySet} during the reading.
         * Note that the elements of the returned list, may not have the {@link MappingIntent#getOntologyChanges()} field
         * initialised (i.e.: {@code Void}).
         */
        List<MappingIntent> readExpressionAxioms();

        /**
         * This method is used to update the ontology with a specific {@link EntitySet} (or {@link ExpressionEntitySet})
         * by synchronizing the OWL representation with the internal state of the descriptor, such that they are equal.
         * It is based on {@link EntitySet#synchroniseTo(EntitySet)}.
         *
         * @return the changes made in the OWL ontology during the writing.
         * Note that the elements of the returned list, may have the {@link MappingIntent#getOntologyChanges()} field
         * initialised (i.e.: {@link org.semanticweb.owlapi.model.OWLOntologyChange}).
         */
        List<MappingIntent> writeExpressionAxioms();

        /**
         * This method assure that after {@link #writeExpressionAxioms()} the internal state of the descriptor is consistent
         * with the OWL ontology.
         * The new entities written into the ontology may trigger the reasoner, thus inferring new
         * elements. Therefore, this method, after calling {@link #writeExpressionAxioms()}, it also calls
         * {@link #groundReason()} and then {@link #readExpressionAxioms()} in order to add the newly inferred elements into the
         * {@link EntitySet}.
         *
         * @param reason set to (false) to disable reasoning. This will add only asserted elements.
         *
         * @return the changes made by the {@link #writeExpressionAxioms()} and {@link #readExpressionAxioms()} operations.
         */
        default List< MappingIntent> writeReadExpressionAxioms(boolean reason){
            List<MappingIntent> intent = writeExpressionAxioms();
            if(reason)
                groundReason();
            intent.addAll( readExpressionAxioms());
            return intent;
        }

        /**
         * This method assure that after {@link #writeExpressionAxioms()} the internal state of the descriptor is consistent
         * with the OWL ontology.
         * The new entities written into the ontology may trigger the reasoner, thus inferring new
         * elements. Therefore, this method, after calling {@link #writeExpressionAxioms()}, it also calls
         * {@link #groundReason()} and then {@link #readExpressionAxioms()} in order to add the newly inferred elements into the
         * {@link EntitySet}.
         *
         * @return the changes made by the {@link #writeExpressionAxioms()} and {@link #readExpressionAxioms()} operations.
         */
        default List< MappingIntent> writeReadExpressionAxioms(){return writeReadExpressionAxioms(true);}

        /**
         * It instantiates a lists of {@link MappingIntent} with the
         * parameters as {@link MappingIntent#getOntologyChanges()}.
         * Its a helper method that can be used by the {@link #readExpressionAxioms()}, in order to record the results of
         * {@link EntitySet#synchroniseFrom(EntitySet)}.
         *
         * @param sync the results of a call to {@link EntitySet#synchroniseFrom(EntitySet)}.
         *
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
         * It instantiates a lists of {@link MappingIntent} with the
         * parameters as {@link MappingIntent#getOntologyChanges()} and
         * (e.g.: {@link org.semanticweb.owlapi.model.OWLOntologyChange}).
         * Its a helper that can be used by the implementation of the {@link #writeExpressionAxioms()},
         * in order to obtain the value to be returned, based on the results of
         * {@link EntitySet#synchroniseTo(EntitySet)} and aMOR manipulations.
         *
         * @param sync the results of a call to {@link EntitySet#synchroniseTo(EntitySet)}.
         * @param changes the ontology changes made during writing.
         *
         * @param <C> The `Class` representing the changes
         *
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
    }

    /**
     * The class to track synchronisation changes.
     * <p>
     *     This class is used to keep a track of the modification in the
     *     descriptors or OWL structures. The modifications made during {@link Descriptor#readExpressionAxioms()}
     *     or {@link Descriptor#writeExpressionAxioms()}.
     *     For instance it can be used to monitor the evolution of the system
     *     or role back the changes in case of inconsistency.
     *     The data in this container should be copied in order ot be
     *     independent from the evolution of the architecture.
     *     <br>
     *     In the current implementation this class is only instantiated by
     *     the helpers in the {@link Descriptor} object in such a way that we have
     *     (void) element for {@link #getOntologyChanges()} during
     *     reading(). Whereas while writing(), it contains instances
     *     of {@link org.semanticweb.owlapi.model.OWLOntologyChange}.
     *     In case in which an {@link Exception} occurs during synchronisation,
     *     the value {@link #getIntent()} would be {@code null}.
     * </p>
     *
     * @param <I> the {@link Ground} used during synchronisation.
     * @param <C> the changes in the EntitySet of the ontology.
     */
    class MappingIntent<I extends Axiom.Ground,C> {

        private long time;
        private EntitySet.SynchronisationIntent intent; // null if error occurs
        private I ground;
        private boolean writing;
        private C ontologyChanges = null;

        /**
         * Initialise this object without specifying any {@link #getOntologyChanges()}.
         *
         * @param ground the ontology and instance used during synchronisation.
         * @param intent the differences between the OWL structure and internal state of the descriptor.
         * @param writing {@code true} if this is generated during {@link Descriptor#writeExpressionAxioms()}.
         *                {@code false} if this is generated during {@link Descriptor#readExpressionAxioms()}.
         */
        public MappingIntent(I ground, EntitySet.SynchronisationIntent intent, boolean writing){
            initialise( ground, intent, writing);
        }
        /**
         * Initialise this object during {@link Descriptor#readExpressionAxioms()} without specifying any {@link #getOntologyChanges()}.
         *
         * @param ground the ontology and instance used during synchronisation.
         * @param intent the differences between the OWL structure and internal state of the descriptor.
         */
        public MappingIntent(I ground, EntitySet.SynchronisationIntent intent) {
            initialise( ground, intent, false);
        }
        /**
         * Fully setGround this object.
         *
         * @param ground the ontology and instance used during synchronisation.
         * @param intent the differences between the OWL structure and internal state of the descriptor.
         * @param changes the ontological changes applied during writing.
         * @param writing {@code true} if this is generated during {@link Descriptor#writeExpressionAxioms()}.
         *                {@code false} if this is generated during {@link Descriptor#readExpressionAxioms()}.
         */
        public MappingIntent(I ground, EntitySet.SynchronisationIntent intent, C changes, boolean writing) {
            initialise( ground, intent, writing);
            this.ontologyChanges = changes;
        }
        /**
         * Initialise this object during {@link Descriptor#writeExpressionAxioms()}.
         *
         * @param ground the ontology and instance used during synchronisation.
         * @param intent the differences between the OWL structure and internal state of the descriptor.
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
         * Gives a flag ro know if the changes have been made during
         * {@link Descriptor#writeExpressionAxioms()} or {@link Descriptor#readExpressionAxioms()}.
         * @return {@code true} if it has been generated during {@link Descriptor#writeExpressionAxioms()}.
         * {@code false} otherwise.
         */
        public boolean wasWriting() {
            return writing;
        }
        /**
         * Gives a flag ro know if this changes have been made during
         * {@link Descriptor#writeExpressionAxioms()} or {@link Descriptor#readExpressionAxioms()}.
         * @return {@code true} if it has been generated during {@link Descriptor#readExpressionAxioms()}.
         * {@code false} otherwise.
         */
        public boolean wasReading() {
            return !writing;
        }

        /**
         * Returns the time in which the changes were made.
         *
         * @return the construction time.
         */
        public long getTime() {
            return time;
        }

        /**
         * Returns the changes within the OWl structure and internal state of the descriptor.
         * If {@link #wasReading()} is (true) those are the changes made
         * on the internal state of the descriptor to become equal to the OWL ontology ({@link EntitySet#synchroniseFrom(EntitySet)}).
         * If {@link #wasWriting()} is (true) those are the changes made
         * on the OWL ontology to make it equal to the internal state of the descriptor ({@link EntitySet#synchroniseTo(EntitySet)}).
         *
         * @return the differences between the OWL structure and internal state of the descriptor.
         */
        public final EntitySet.SynchronisationIntent getIntent() {
            return intent;
        }

        /**
         * @return a copyGround of the {@link Ground}.
         */
        public final I getGround() {
            return ground;
        }

        /**
         * Gets the changes made in the OWL ontology.
         * It is {@code null} if {@link #wasReading()} is {@code true}
         *
         * @return the changes in the ontology.
         */
        public final C getOntologyChanges() {
            return ontologyChanges;
        }

        /**
         * Returns {@code true} if {@link #getIntent()} returns {@code null}.
         *
         * @return {@code true} if an error occurs during reading or writing.
         * {@code false} otherwise.
         */
        public boolean errorOccur(){
            return intent == null;
        }

        /**
         * Return {@code true} if {@code {@link #getOntologyChanges()} = null}.
         *
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
