package it.emarolab.owloop.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This interface is a part of the core of OWLOOP architecture.
 * It contains interfaces of the expressions that can be applied to
 * the OWL entity OWLDataProperty (i.e., {@link org.semanticweb.owlapi.model.OWLNamedIndividual}). <p>
 * The expressions are the following:
 *
 * <ul>
 * <li><b>{@link Equivalent}</b>:   this expression describes an Individual same-as another Individual.</li>
 * <li><b>{@link Disjoint}</b>:     this expression describes an Individual different from another Individual.</li>
 * <li><b>{@link Type}</b>:         this expression describes the Type/s (i.e., class/es) of an Individual.</li>
 * <li><b>{@link ObjectLink}</b>:   this expression describes an ObjectProperty and Individuals related via that ObjectProperty, for an Individual.</li>
 * <li><b>{@link DataLink}</b>:     this expression describes an DataProperty and Individuals related via that DataProperty, for an Individual.</li>
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
public interface Individual<O,J>
        extends Axiom.Descriptor<O,J>{

    /**
     * Implementation of this interface enables a {@link Individual} to have the {@link Type} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <Y> the type of restriction for the {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Concept} descriptors instantiated during
     *           {@link #buildTypeIndividual()} through {@link #getNewTypeIndividual(Object, Object)}.
     */
    interface Type<O,J,Y,D extends Concept<O,Y>>
            extends Individual<O,J>{

        @Override // see documentation on Axiom.descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseTypeIndividualFromSemantic();
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
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the classes in which {@code this} individualCompoundDescriptor is belonging to.
         * Each of {@link Concept}s are instantiated
         * through the method {@link #getNewTypeIndividual(Object, Object)};
         * this is called for all {@link #getTypeIndividual()}.
         * @return the set of {@link Concept}s that describes the
         * entities in which {@code this} described ontological individualCompoundDescriptor
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
         * @return a new {@link Axiom.Descriptor} for all the classes
         * in which {@code this} individualCompoundDescriptor is belonging to.
         */
        D getNewTypeIndividual(Y instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the classes in which
         * {@code this} {@link Individual} is belonging to, from a no OOP point of view.
         * @return the entities describing the types of {@code this} individualCompoundDescriptor.
         */
        EntitySet<Y> getTypeIndividual();

        /**
         * Queries to the OWL representation for the types of {@code this} individualCompoundDescriptor.
         * @return a new {@link EntitySet} contained the classes in which {@link #getInstance()}
         * is belonging to, into the OWL structure.
         */
        EntitySet<Y> queryTypeIndividual();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryTypeIndividual()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getTypeIndividual()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the classes in which {@link #getInstance()} is belonging to, in the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseTypeIndividualToSemantic(){
            try {
                return getTypeIndividual().synchroniseTo( queryTypeIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryTypeIndividual()}
         * as input parameter. This computes the changes to be performed into the {@link #getTypeIndividual()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the classes in which the {@link #getInstance()}
         * individualCompoundDescriptor is belonging to, from an OWL representation to {@code this} descriptor.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseTypeIndividualFromSemantic(){
            try{
                return getTypeIndividual().synchroniseFrom( queryTypeIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Individual} to have the {@link Disjoint} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Individual} descriptors instantiated during
     *           {@link #buildDisjointIndividual()} through {@link #getNewDisjointIndividual(Object, Object)}.
     */
    interface Disjoint<O,J,D extends Individual<O,J>>
            extends Individual<O,J>{

        @Override // see documentation on Axiom.descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseDisjointIndividualFromSemantic();
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
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the different individuals from {@code this} description.
         * Each of {@link Individual}s are instantiated
         * through the method {@link #getNewDisjointIndividual(Object, Object)};
         * this is called for all {@link #getDisjointIndividual()}.
         * @return the set of {@link Individual}s that describes the
         * entities that are different from {@code this} described ontological individualCompoundDescriptor.
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
         * an equivalent individualCompoundDescriptor from {@code this} {@link Individual} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Individual}.
         * @param ontology the ontology in which ground the new {@link Individual}.
         * @return a new {@link Axiom.Descriptor} for all the individuals
         * that are equivalent from the one described by {@code this} interface.
         */
        D getNewDisjointIndividual(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the different individualCompoundDescriptor from
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the different individualCompoundDescriptor of {@code this} individualCompoundDescriptor.
         */
        EntitySet<J> getDisjointIndividual();

        /**
         * Queries to the OWL representation for the different individuals from {@code this} {@link Descriptor}.
         * @return a new {@link EntitySet} contained the individuals different from {@link #getInstance()};
         * into the OWL structure.
         */
        EntitySet<J> queryDisjointIndividual();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDisjointIndividual()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointIndividual()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the different individuals from {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointIndividualToSemantic(){
            try {
                return getDisjointIndividual().synchroniseTo( queryDisjointIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryDisjointIndividual()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointIndividual()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the different individuals from {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointIndividualFromSemantic(){
            try{
                return getDisjointIndividual().synchroniseFrom( queryDisjointIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Individual} to have the {@link Equivalent} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Individual} descriptors instantiated during
     *           {@link #buildEquivalentIndividual()} through {@link #getNewEquivalentIndividual(Object, Object)}.
     */
    interface Equivalent<O,J,D extends Individual<O,J>>
            extends Individual<O,J>{

        @Override // see documentation on Axiom.descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseEquivalentIndividualFromSemantic();
                getEquivalentIndividual().addAll(from.getToAdd());
                getEquivalentIndividual().removeAll(from.getToRemove());
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the equivalent individuals from {@code this} description.
         * Each of {@link Individual}s are instantiated
         * through the method {@link #getNewEquivalentIndividual(Object, Object)};
         * this is called for all {@link #getEquivalentIndividual()}.
         * @return the set of {@link Individual}s that describes the
         * entities that are equivalent from {@code this} described ontological individualCompoundDescriptor.
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
         * a different individualCompoundDescriptor from {@code this} {@link Individual} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Individual}.
         * @param ontology the ontology in which ground the new {@link Individual}.
         * @return a new {@link Axiom.Descriptor} for all the individuals
         * that are different from the one described by {@code this} interface.
         */
        D getNewEquivalentIndividual(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the equivalent individualCompoundDescriptor from
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the equivalent individualCompoundDescriptor of {@code this} individualCompoundDescriptor.
         */
        EntitySet<J> getEquivalentIndividual();

        /**
         * Queries to the OWL representation for the equivalent individuals of {@code this} {@link Descriptor}.
         * @return a new {@link EntitySet} contained the individuals equivalent from {@link #getInstance()};
         * into the OWL structure.
         */
        EntitySet<J> queryEquivalentIndividual();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryEquivalentIndividual()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentIndividual()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent individuals from {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentIndividualToSemantic(){
            try {
                return getEquivalentIndividual().synchroniseTo( queryEquivalentIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryEquivalentIndividual()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentIndividual()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the equivalent individuals from {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentIndividualFromSemantic(){
            try{
                return getEquivalentIndividual().synchroniseFrom( queryEquivalentIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Individual} to have the {@link DataLink} expression.
     * <p>
     *     {@link Individual} synchronises only specified data property and its
     *     relative values for an individualCompoundDescriptor (i.e.: the {@link Ground#getGroundInstance()}).
     *     <br>
     *     By default, the synchronisation occurs only for the proprieties whose expression
     *     has been initialised in the {@link ExpressionEntitySet} ({@link #getDataExpressions()}.
     *     If the {@link ExpressionEntitySet} is left empty during {@link #readSemantic()}
     *     it maps all the data properties applied to the described individualCompoundDescriptor.
     * </p>
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <Y> the type of {@link ExpressionEntity} synchronised by this descriptor
     * @param <S> the type of expression described by this class (i.e.: {@code OWLDataProperty})
     * @param <D> the type of the {@link DataProperty} descriptors instantiated during
     *           {@link #buildDataIndividual()} through {@link #getNewDataIndividual(ExpressionEntity, Object)}.
     */
    interface DataLink<O,J,Y extends ExpressionEntity<S,?>, S,D extends DataProperty<O, S>>
            extends Individual<O,J>{

        @Override // see documentation on Axiom.descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseDataIndividualFromSemantic();
                if (from != null) {
                    getDataExpressions().addAll(from.getToAdd());
                    getDataExpressions().removeAll(from.getToRemove());
                }
                return getIntent(from);
            }catch (Exception e){
                e.printStackTrace();
                return getIntent(null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the specified data properties applied to this {@code this} description.
         * Each of {@link DataProperty}s are instantiated
         * through the method {@link #getNewDataIndividual(ExpressionEntity, Object)};
         * this is called for all {@link #getDataExpressions()}.
         * @return the set of {@link DataProperty}s that describes the
         * entities that are applied to {@code this} described ontological individualCompoundDescriptor.
         */
        default Set<D> buildDataIndividual(){
            Set<D> out = new HashSet<>();
            for( Y cl : getDataExpressions()){
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
         * @return a new {@link Axiom.Descriptor} for all the data properties
         * that are applied to {@code this} interface.
         */
        D getNewDataIndividual(Y instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes the specified data properties applied to
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the data properties of {@code this} individualCompoundDescriptor.
         */
        EntitySet<Y> getDataExpressions();

        /**
         * Queries to the OWL representation for the data properties applied to {@code this} {@link Descriptor}.
         * @return a new {@link ExpressionEntitySet} contained the data properties of {@link #getInstance()};
         * into the OWL structure.
         */
        EntitySet<Y> queryDataIndividual();

        /**
         * It calls {@link ExpressionEntitySet#synchroniseTo(EntitySet)} with {@link #queryDataIndividual()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDataExpressions()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the data properties applied on {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDataIndividualToSemantic(){
            try {
                return getDataExpressions().synchroniseTo( queryDataIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryDataIndividual()}
         * as input parameter. This computes the changes to be performed into the {@link #getDataExpressions()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the data properties applied on {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDataIndividualFromSemantic(){
            try{
                return getDataExpressions().synchroniseFrom( queryDataIndividual());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Implementation of this interface enables a {@link Individual} to have the {@link ObjectLink} expression.
     * <p>
     *     {@link Individual} synchronises only the specified object property and its
     *     relative values for an individualCompoundDescriptor (i.e.: the {@link Ground#getGroundInstance()}).
     *     <br>
     *     By default, the synchronisation occurs only for the proprieties whose expression
     *     has been initialised in the {@link ExpressionEntitySet} ({@link #getObjectSemantics()}).
     *     If the {@link ExpressionEntitySet} is left empty during {@link #readSemantic()}
     *     it maps all the object properties applied to the described individualCompoundDescriptor.
     * </p>
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <Y> the type of {@link ExpressionEntity} synchronised by this descriptor
     * @param <S> the type of expression described by this class (i.e.: {@code OWLObjectProperty})
     * @param <D> the type of the {@link DataProperty} descriptors instantiated during
     *           {@link #buildObjectIndividual()} through {@link #getNewObjectIndividual(ExpressionEntity, Object)}.
     */
    interface ObjectLink<O,J,Y extends ExpressionEntity<S,?>, S,D extends ObjectProperty<O, S>>
            extends Individual<O,J>{

        @Override // see documentation on Axiom.descriptor.readSemantic
        default List<MappingIntent> readSemantic(){
            try{
                EntitySet.SynchronisationIntent<Y> from = synchroniseObjectIndividualFromSemantic();
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
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the specified object properties applied to this {@code this} description.
         * Each of {@link ObjectProperty}s are instantiated
         * through the method {@link #getNewObjectIndividual(ExpressionEntity, Object)};
         * this is called for all {@link #getObjectSemantics()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * entities that are applied to {@code this} described ontological individualCompoundDescriptor.
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
         * @return a new {@link Axiom.Descriptor} for all the object properties
         * that are applied to {@code this} interface.
         */
        D getNewObjectIndividual(Y instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes the specified object properties applied to
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the object properties of {@code this} individualCompoundDescriptor.
         */
        EntitySet<Y> getObjectSemantics();

        /**
         * Queries to the OWL representation for the data properties applied to {@code this} {@link Descriptor}.
         * @return a new {@link ExpressionEntitySet} contained the object properties of {@link #getInstance()};
         * into the OWL structure.
         */
        EntitySet<Y> queryObject();

        /**
         * It calls {@link ExpressionEntitySet#synchroniseTo(EntitySet)} with {@link #queryObject()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getObjectSemantics()}. This should
         * be done by {@link #writeSemantic()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the object properties applied on {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseObjectIndividualToSemantic(){
            try {
                return getObjectSemantics().synchroniseTo( queryObject());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryObject()}
         * as input parameter. This computes the changes to be performed into the {@link #getObjectSemantics()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readSemantic()}.
         * @return the changes to be done to synchronise the object properties applied on {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseObjectIndividualFromSemantic(){
            try{
                return getObjectSemantics().synchroniseFrom( queryObject());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

}
