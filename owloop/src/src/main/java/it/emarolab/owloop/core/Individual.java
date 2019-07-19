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
public interface Individual<O,J>
        extends Axiom.Descriptor<O,J>{

    /**
     * Implementation of this interface enables a {@link Individual} to have the {@link Type} expression.
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <Y> the type of restriction for the {@link EntitySet} managed by this {@link Descriptor}.
     * @param <D> the type of the {@link Concept} descriptors instantiated during
     *           {@link #buildTypeIndividual()} through {@link #getNewIndividualType(Object, Object)}.
     */
    interface Type<O,J,Y,D extends Concept<O,Y>>
            extends Individual<O,J>{

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseTypeIndividualFromExpressionAxioms();
                if ( from != null) {
                    getIndividualTypes().addAll(from.getToAdd());
                    getIndividualTypes().removeAll(from.getToRemove());
                }
                return getIntent(from);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }

        /**
         * Create an {@link Axiom.Descriptor} set where each element
         * represents the classes in which {@code this} individualDescriptor is belonging to.
         * Each of {@link Concept}s are instantiated
         * through the method {@link #getNewIndividualType(Object, Object)};
         * this is called for all {@link #getIndividualTypes()}.
         * @return the set of {@link Concept}s that describes the
         * entities in which {@code this} described ontological individualDescriptor
         * is belonging to.
         */
        default Set<D> buildTypeIndividual(){
            Set<D> out = new HashSet<>();
            for( Y cl : getIndividualTypes()){
                D built = getNewIndividualType( cl, getOntology());
                built.readExpressionAxioms();
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
         * in which {@code this} individualDescriptor is belonging to.
         */
        D getNewIndividualType(Y instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the classes in which
         * {@code this} {@link Individual} is belonging to, from a no OOP point of view.
         * @return the entities describing the types of {@code this} individualDescriptor.
         */
        EntitySet<Y> getIndividualTypes();

        /**
         * Queries to the OWL representation for the types of {@code this} individualDescriptor.
         * @return a new {@link EntitySet} contained the classes in which {@link #getInstance()}
         * is belonging to, into the OWL structure.
         */
        EntitySet<Y> queryIndividualTypes();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryIndividualTypes()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getIndividualTypes()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the classes in which {@link #getInstance()} is belonging to, in the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseTypeIndividualToExpressionAxioms(){
            try {
                return getIndividualTypes().synchroniseTo( queryIndividualTypes());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryIndividualTypes()}
         * as input parameter. This computes the changes to be performed into the {@link #getIndividualTypes()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the classes in which the {@link #getInstance()}
         * individualDescriptor is belonging to, from an OWL representation to {@code this} descriptor.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseTypeIndividualFromExpressionAxioms(){
            try{
                return getIndividualTypes().synchroniseFrom( queryIndividualTypes());
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

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseDisjointIndividualFromExpressionAxioms();
                if ( from != null) {
                    getDisjointIndividuals().addAll(from.getToAdd());
                    getDisjointIndividuals().removeAll(from.getToRemove());
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
         * this is called for all {@link #getDisjointIndividuals()}.
         * @return the set of {@link Individual}s that describes the
         * entities that are different from {@code this} described ontological individualDescriptor.
         */
        default Set<D> buildDisjointIndividual(){
            Set<D> out = new HashSet<>();
            for( J cl : getDisjointIndividuals()){
                D built = getNewDisjointIndividual( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildDisjointIndividual()} and
         * its purpose is to instantiate a new {@link Individual} to represent
         * an equivalent individualDescriptor from {@code this} {@link Individual} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Individual}.
         * @param ontology the ontology in which ground the new {@link Individual}.
         * @return a new {@link Axiom.Descriptor} for all the individuals
         * that are equivalent from the one described by {@code this} interface.
         */
        D getNewDisjointIndividual(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the different individualDescriptor from
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the different individualDescriptor of {@code this} individualDescriptor.
         */
        EntitySet<J> getDisjointIndividuals();

        /**
         * Queries to the OWL representation for the different individuals from {@code this} {@link Descriptor}.
         * @return a new {@link EntitySet} contained the individuals different from {@link #getInstance()};
         * into the OWL structure.
         */
        EntitySet<J> queryDisjointIndividuals();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryDisjointIndividuals()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getDisjointIndividuals()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the different individuals from {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointIndividualToExpressionAxioms(){
            try {
                return getDisjointIndividuals().synchroniseTo( queryDisjointIndividuals());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryDisjointIndividuals()}
         * as input parameter. This computes the changes to be performed into the {@link #getDisjointIndividuals()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the different individuals from {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseDisjointIndividualFromExpressionAxioms(){
            try{
                return getDisjointIndividuals().synchroniseFrom( queryDisjointIndividuals());
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

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<J> from = synchroniseEquivalentIndividualFromExpressionAxioms();
                getEquivalentIndividuals().addAll(from.getToAdd());
                getEquivalentIndividuals().removeAll(from.getToRemove());
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
         * this is called for all {@link #getEquivalentIndividuals()}.
         * @return the set of {@link Individual}s that describes the
         * entities that are equivalent from {@code this} described ontological individualDescriptor.
         */
        default Set<D> buildEquivalentIndividual(){
            Set<D> out = new HashSet<>();
            for( J cl : getEquivalentIndividuals()){
                D built = getNewEquivalentIndividual( cl, getOntology());
                built.readExpressionAxioms();
                out.add( built);
            }
            return out;
        }

        /**
         * This method is called by {@link #buildEquivalentIndividual()} and
         * its purpose is to instantiate a new {@link Individual} to represent
         * a different individualDescriptor from {@code this} {@link Individual} {@link Descriptor}.
         * @param instance the instance to ground the new {@link Individual}.
         * @param ontology the ontology in which ground the new {@link Individual}.
         * @return a new {@link Axiom.Descriptor} for all the individuals
         * that are different from the one described by {@code this} interface.
         */
        D getNewEquivalentIndividual(J instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes all the equivalent individualDescriptor from
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the equivalent individualDescriptor of {@code this} individualDescriptor.
         */
        EntitySet<J> getEquivalentIndividuals();

        /**
         * Queries to the OWL representation for the equivalent individuals of {@code this} {@link Descriptor}.
         * @return a new {@link EntitySet} contained the individuals equivalent from {@link #getInstance()};
         * into the OWL structure.
         */
        EntitySet<J> queryEquivalentIndividuals();

        /**
         * It calls {@link EntitySet#synchroniseTo(EntitySet)} with {@link #queryEquivalentIndividuals()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getEquivalentIndividuals()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the equivalent individuals from {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentIndividualToExpressionAxioms(){
            try {
                return getEquivalentIndividuals().synchroniseTo( queryEquivalentIndividuals());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link EntitySet#synchroniseFrom(EntitySet)} with {@link #queryEquivalentIndividuals()}
         * as input parameter. This computes the changes to be performed into the {@link #getEquivalentIndividuals()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the equivalent individuals from {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<J> synchroniseEquivalentIndividualFromExpressionAxioms(){
            try{
                return getEquivalentIndividuals().synchroniseFrom( queryEquivalentIndividuals());
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
     *     relative values for an individualDescriptor (i.e.: the {@link Ground#getGroundInstance()}).
     *     <br>
     *     By default, the synchronisation occurs only for the proprieties whose expression
     *     has been initialised in the {@link ExpressionEntitySet} ({@link #getIndividualDataProperties()}.
     *     If the {@link ExpressionEntitySet} is left empty during {@link #readExpressionAxioms()}
     *     it maps all the data properties applied to the described individualDescriptor.
     * </p>
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <Y> the type of {@link ExpressionEntity} synchronised by this descriptor
     * @param <S> the type of expression described by this class (i.e.: {@code OWLDataProperty})
     * @param <D> the type of the {@link DataProperty} descriptors instantiated during
     *           {@link #buildDataIndividual()} through {@link #getNewIndividualDataProperty(ExpressionEntity, Object)}.
     */
    interface DataLink<O,J,Y extends ExpressionEntity<S,?>, S,D extends DataProperty<O, S>>
            extends Individual<O,J>{

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<Y> from = synchroniseDataIndividualFromExpressionAxioms();
                if (from != null) {
                    getIndividualDataProperties().addAll(from.getToAdd());
                    getIndividualDataProperties().removeAll(from.getToRemove());
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
         * through the method {@link #getNewIndividualDataProperty(ExpressionEntity, Object)};
         * this is called for all {@link #getIndividualDataProperties()}.
         * @return the set of {@link DataProperty}s that describes the
         * entities that are applied to {@code this} described ontological individualDescriptor.
         */
        default Set<D> buildDataIndividual(){
            Set<D> out = new HashSet<>();
            for( Y cl : getIndividualDataProperties()){
                D built = getNewIndividualDataProperty( cl, getOntology());
                built.readExpressionAxioms();
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
        D getNewIndividualDataProperty(Y instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes the specified data properties applied to
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the data properties of {@code this} individualDescriptor.
         */
        EntitySet<Y> getIndividualDataProperties();

        /**
         * Queries to the OWL representation for the data properties applied to {@code this} {@link Descriptor}.
         * @return a new {@link ExpressionEntitySet} contained the data properties of {@link #getInstance()};
         * into the OWL structure.
         */
        EntitySet<Y> queryIndividualDataProperties();

        /**
         * It calls {@link ExpressionEntitySet#synchroniseTo(EntitySet)} with {@link #queryIndividualDataProperties()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getIndividualDataProperties()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the data properties applied on {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDataIndividualToExpressionAxioms(){
            try {
                return getIndividualDataProperties().synchroniseTo( queryIndividualDataProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryIndividualDataProperties()}
         * as input parameter. This computes the changes to be performed into the {@link #getIndividualDataProperties()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the data properties applied on {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseDataIndividualFromExpressionAxioms(){
            try{
                return getIndividualDataProperties().synchroniseFrom( queryIndividualDataProperties());
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
     *     relative values for an individualDescriptor (i.e.: the {@link Ground#getGroundInstance()}).
     *     <br>
     *     By default, the synchronisation occurs only for the proprieties whose expression
     *     has been initialised in the {@link ExpressionEntitySet} ({@link #getIndividualObjectProperties()}).
     *     If the {@link ExpressionEntitySet} is left empty during {@link #readExpressionAxioms()}
     *     it maps all the object properties applied to the described individualDescriptor.
     * </p>
     *
     * @param <O> the ontology.
     * @param <J> the type of {@link Ground} and {@link EntitySet} managed by this {@link Descriptor}.
     * @param <Y> the type of {@link ExpressionEntity} synchronised by this descriptor
     * @param <S> the type of expression described by this class (i.e.: {@code OWLObjectProperty})
     * @param <D> the type of the {@link DataProperty} descriptors instantiated during
     *           {@link #buildObjectIndividual()} through {@link #getNewIndividualObjectProperty(ExpressionEntity, Object)}.
     */
    interface ObjectLink<O,J,Y extends ExpressionEntity<S,?>, S,D extends ObjectProperty<O, S>>
            extends Individual<O,J>{

        @Override // see documentation on Axiom.descriptor.readExpressionAxioms
        default List<MappingIntent> readExpressionAxioms(){
            try{
                EntitySet.SynchronisationIntent<Y> from = synchroniseObjectIndividualFromExpressionAxioms();
                if (from != null) {
                    getIndividualObjectProperties().addAll(from.getToAdd());
                    getIndividualObjectProperties().removeAll(from.getToRemove());
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
         * through the method {@link #getNewIndividualObjectProperty(ExpressionEntity, Object)};
         * this is called for all {@link #getIndividualObjectProperties()}.
         * @return the set of {@link ObjectProperty}s that describes the
         * entities that are applied to {@code this} described ontological individualDescriptor.
         */
        default Set< D> buildObjectIndividual(){
            Set<D> out = new HashSet<>();
            for( Y cl : getIndividualObjectProperties()){
                D built = getNewIndividualObjectProperty( cl, getOntology());
                built.readExpressionAxioms();
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
        D getNewIndividualObjectProperty(Y instance, O ontology);

        /**
         * Returns the {@link EntitySet} that describes the specified object properties applied to
         * {@code this} {@link Individual}; from a no OOP point of view.
         * @return the entities describing the object properties of {@code this} individualDescriptor.
         */
        EntitySet<Y> getIndividualObjectProperties();

        /**
         * Queries to the OWL representation for the data properties applied to {@code this} {@link Descriptor}.
         * @return a new {@link ExpressionEntitySet} contained the object properties of {@link #getInstance()};
         * into the OWL structure.
         */
        EntitySet<Y> queryIndividualObjectProperties();

        /**
         * It calls {@link ExpressionEntitySet#synchroniseTo(EntitySet)} with {@link #queryIndividualObjectProperties()}
         * as input parameter. This computes the changes to be performed in the OWL representation
         * for synchronise it with respect to {@link #getIndividualObjectProperties()}. This should
         * be done by {@link #writeExpressionAxioms()}.
         * @return the changes to be done to synchronise {@code this} structure with
         * the object properties applied on {@link #getInstance()}; to the OWL representation.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseObjectIndividualToExpressionAxioms(){
            try {
                return getIndividualObjectProperties().synchroniseTo( queryIndividualObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }

        /**
         * It calls {@link ExpressionEntitySet#synchroniseFrom(EntitySet)} with {@link #queryIndividualObjectProperties()}
         * as input parameter. This computes the changes to be performed into the {@link #getIndividualObjectProperties()}
         * in order to synchronise it with respect to an OWL representation. This is
         * be done by {@link #readExpressionAxioms()}.
         * @return the changes to be done to synchronise the object properties applied on {@link #getInstance()};
         * from an OWL representation to {@code this} {@link Descriptor}.
         */
        default EntitySet.SynchronisationIntent<Y> synchroniseObjectIndividualFromExpressionAxioms(){
            try{
                return getIndividualObjectProperties().synchroniseFrom( queryIndividualObjectProperties());
            } catch ( Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

}
