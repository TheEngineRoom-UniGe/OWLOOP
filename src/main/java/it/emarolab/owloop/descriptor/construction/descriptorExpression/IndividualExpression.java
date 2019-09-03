package it.emarolab.owloop.descriptor.construction.descriptorExpression;

import it.emarolab.amor.owlInterface.DataPropertyRelations;
import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.ObjectPropertyRelations;
import it.emarolab.owloop.core.Individual;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.*;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DescriptorGroundInterface;
import org.semanticweb.owlapi.model.*;

import java.util.*;

/**
 * This interface extends all the interfaces in {@link Individual}.
 * It allows to {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()}
 * specifically for OWL Individuals.
 * It contains several expressions that can be combined in any arbitrary way as they
 * rely on the same ground ({@link IndividualGroundInstance}).
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
public interface IndividualExpression
        extends Individual<OWLReferences,OWLNamedIndividual>,
        DescriptorGroundInterface<OWLNamedIndividual> {

    /**
     * To access the {@link DescriptorGroundInterface} facilities.
     * @return the ontology on which {@code this} descriptor is working.
     * @deprecated use {@link #getGround()} instead.
     */
    @Override @Deprecated
    default OWLReferences getGroundOntology() {
        return getGround().getGroundOntology();
    }
    /**
     * To access the {@link DescriptorGroundInterface} facilities.
     * @return the ground instance of {@code this} descriptor.
     * @deprecated use {@link #getInstance()} instead.
     */
    @Override @Deprecated // used internally to simplify syntax use getOntology() instead
    default OWLNamedIndividual getGroundInstance() {
        return getGround().getGroundInstance();
    }



    /**
     * The {@link Individual.Type} expression for a {@link Descriptor} whose ground is {@link OWLNamedIndividual}.
     * <p>
     *     It specifies how to {@link #queryTypes()} and {@link #writeExpressionAxioms()} for the
     *     types (i.e.: {@link OWLClass}) of the ground Individual ({@link #getInstance()}).
     * </p>
     *
     * @param <D> the type of the {@link ClassExpression} descriptors instantiated during
     *           {@link #buildTypes()} through {@link #getNewType(Object, Object)}.
     */
    interface Type<D extends ClassExpression>
            extends Individual.Type<OWLReferences, OWLNamedIndividual, OWLClass,D>,
            IndividualExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getTypes()}.add( {@link #getOntologyReference()}.getOWLClass( className))}
         * in order to add a new class (given by name) in the {@link EntitySet} list.
         * @param className the class name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addTypeIndividual(String className){
            return getTypes().add( getOntologyReference().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getTypes()}.add( cl)}
         * in order to add a new class in the {@link EntitySet} list.
         * @param instance the class to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addTypeIndividual(OWLClass instance){
            return getTypes().add( instance);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getTypes()}.remove( {@link #getOntologyReference()}.getOWLClass( className))}
         * in order to remove a class (given by name) from the {@link EntitySet} list.
         * @param className the class name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeTypeIndividual(String className){
            return getTypes().remove( getOntologyReference().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getTypes()}.remove( individualDescriptor)}
         * in order to remove a class in the {@link EntitySet} list.
         * @param instance the class to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeTypeIndividual(OWLClass instance){
            return getTypes().remove( instance);
        }

        @Override
        Classes getTypes();

        @Override // see super classes for documentation
        default Classes queryTypes(){
            Classes set = new Classes(getOntologyReference().getIndividualClasses(getInstance()));
            set.setSingleton( getTypes().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLClass> to = synchroniseTypesToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (OWLClass a : to.getToAdd())
                    changes.add(getOntologyReference().addIndividualB2Class(getInstance(), a));
                for (OWLClass b : to.getToRemove())
                    changes.add(getOntologyReference().removeIndividualB2Class(getInstance(), b));
                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link Individual.Disjoint} expression for a {@link Descriptor} whose ground is {@link OWLNamedIndividual}.
     * <p>
     *     It specifies how to {@link #queryDisjointIndividuals()} and {@link #writeExpressionAxioms()} for the
     *     individuals disjoint with the ground Individual ({@link #getInstance()}).
     * </p>
     *
     * @param <D> the type of the {@link IndividualExpression} descriptors instantiated during
     *           {@link #buildDisjointIndividuals()}  through {@link #getNewDisjointIndividual(Object, Object)}.
     */
    interface Disjoint<D extends IndividualExpression>
            extends Individual.Disjoint<OWLReferences, OWLNamedIndividual,D>,
            IndividualExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointIndividuals()}.add( {@link #getOntologyReference()}.getOWLIndividual( individualName))}
         * in order to add a new individualDescriptor (given by name) in the {@link EntitySet} list.
         * @param individualName the individualDescriptor name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointIndividual( String individualName){
            return getDisjointIndividuals().add( getOntologyReference().getOWLIndividual( individualName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointIndividuals()}.add( individualDescriptor)}
         * in order to add a new individualDescriptor in the {@link EntitySet} list.
         * @param individual the individualDescriptor to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointIndividual( OWLNamedIndividual individual){
            return getDisjointIndividuals().add( individual);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointIndividuals()}.remove( {@link #getOntologyReference()}.getOWLIndividual( individualName))}
         * in order to remove an individualDescriptor (given by name) from the {@link EntitySet} list.
         * @param individualName the individualDescriptor name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointIndividual( String individualName){
            return getDisjointIndividuals().remove( getOntologyReference().getOWLIndividual( individualName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointIndividuals()}.remove( dataProperty)}
         * in order to remove a class in the {@link EntitySet} list.
         * @param individual the individualDescriptor to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointIndividual( OWLNamedIndividual individual){
            return getDisjointIndividuals().remove( individual);
        }

        @Override
        Individuals getDisjointIndividuals();

        @Override // see super classes for documentation
        default Individuals queryDisjointIndividuals(){
            Individuals set = new Individuals(getOntologyReference().getDisjointIndividuals(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getDisjointIndividuals().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLNamedIndividual> to = synchroniseDisjointIndividualsToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLNamedIndividual a : to.getToAdd()){
                    Set<OWLNamedIndividual> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( a);
                    changes.add( getOntologyReference().makeDisjointIndividuals( s));
                }
                for( OWLNamedIndividual r : to.getToRemove()){
                    Set<OWLNamedIndividual> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( r);
                    changes.add( getOntologyReference().removeDisjointIndividuals( s));
                }
                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link Individual.Equivalent} expression for a {@link Descriptor} whose ground is {@link OWLNamedIndividual}.
     * <p>
     *     It specifies how to {@link #queryEquivalentIndividuals()} and {@link #writeExpressionAxioms()} for the
     *     individuals equivalent to the ground Individual ({@link #getInstance()}).
     * </p>
     *
     * @param <D> the type of the {@link IndividualExpression} descriptors instantiated during
     *           {@link #buildEquivalentIndividuals()} through {@link #getNewEquivalentIndividual(Object, Object)}.
     */
    interface Equivalent<D extends IndividualExpression>
            extends Individual.Equivalent<OWLReferences, OWLNamedIndividual,D>,
            IndividualExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentIndividuals()}.add( {@link #getOntologyReference()}.getOWLIndividual( individualName))}
         * in order to add a new individualDescriptor (given by name) in the {@link EntitySet} list.
         * @param individualName the individualDescriptor name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentIndividual( String individualName){
            return getEquivalentIndividuals().add( getOntologyReference().getOWLIndividual( individualName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentIndividuals()}.add( individualDescriptor)}
         * in order to add a new individualDescriptor in the {@link EntitySet} list.
         * @param individual the individualDescriptor to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentIndividual( OWLNamedIndividual individual){
            return getEquivalentIndividuals().add( individual);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentIndividuals()}.remove( {@link #getOntologyReference()}.getOWLIndividual( individualName))}
         * in order to remove an individualDescriptor (given by name) from the {@link EntitySet} list.
         * @param individualName the individualDescriptor name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentIndividual( String individualName){
            return getEquivalentIndividuals().remove( getOntologyReference().getOWLIndividual( individualName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentIndividuals()}.remove( individualDescriptor)}
         * in order to remove a class in the {@link EntitySet} list.
         * @param individual the individualDescriptor to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentIndividual( OWLNamedIndividual individual){
            return getEquivalentIndividuals().remove( individual);
        }

        @Override
        Individuals getEquivalentIndividuals();

        @Override // see super classes for documentation
        default Individuals queryEquivalentIndividuals(){
            Individuals set = new Individuals(getOntologyReference().getEquivalentIndividuals(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getEquivalentIndividuals().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLNamedIndividual> to = synchroniseEquivalentIndividualsToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLNamedIndividual a : to.getToAdd()){
                    Set<OWLNamedIndividual> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( a);
                    changes.add( getOntologyReference().makeEquivalentIndividuals( s));
                }
                for( OWLNamedIndividual r : to.getToRemove()){
                    Set<OWLNamedIndividual> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( r);
                    changes.add( getOntologyReference().removeEquivalentIndividuals( s));
                }
                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link Individual.DataLink} expression for a {@link Descriptor} whose ground is {@link OWLNamedIndividual}.
     * <p>
     *     It specifies how to {@link #queryDataProperties()} and {@link #writeExpressionAxioms()} for the
     *     data properties associated to the ground Individual {@link #getInstance()}.
     *     It also implements common function to populate the {@link ExpressionEntitySet}
     *     (of type {@link DataLinks}) that specify the data properties of this individualDescriptor
     *     that are synchronised with this {@link Descriptor}. For efficiency,
     *     this descriptor does not map all the properties of an individualDescriptor but only for the
     *     {@code Expressions} that have been initialised in the {@link ExpressionEntity}.
     *     On the other hand, if the set of {@link ExpressionEntitySet} is left empty during
     *     {@link #readExpressionAxioms()}, it maps all the object properties applied to the described
     *     individualDescriptor
     * </p>
     *
     * @param <D> the type of the {@link DataPropertyExpression} descriptors instantiated during
     *           {@link #buildDataProperties()} through {@link #getNewDataProperty(ExpressionEntity, Object)}.
     */
    interface DataLink<D extends DataPropertyExpression>
            extends Individual.DataLink<OWLReferences, OWLNamedIndividual, DataLinks, OWLDataProperty, D>,
            IndividualExpression {

        /**
         * Remove the {@link ExpressionEntity} of the given semantic ({@link DataLinks#getExpression()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()}.
         * @param dataProperty a dataProperty name contained in an element of {@link #getDataProperties()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( String dataProperty){
            return removeData( getOWLDataProperty( dataProperty));
        }
        /**
         * Remove the {@link ExpressionEntity} of the given semantic ({@link DataLinks#getExpression()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()}.
         * @param dataProperty a dataProperty contained in an element of {@link #getDataProperties()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( OWLDataProperty dataProperty){
            for (DataLinks d : getDataProperties())
                if( d.getExpression().equals( dataProperty))
                    return getDataProperties().remove( d);
            return false;
        }

        /**
         * Remove the {@link ExpressionEntity#getExpression()} of the given dataProperty, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readExpressionAxioms()}.
         * The class of the specified value represents its data type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param dataProperty the name of the dataProperty which value, contained in {@link #getDataProperties()}, will be removed.
         * @param value the dataProperty value to be removed from the {@link DataLinks#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( String dataProperty, Object value){
            return removeData( getOWLDataProperty( dataProperty), getOWLLiteral( value));
        }
        /**
         * Remove the {@link ExpressionEntity#getExpression()} of the given dataProperty, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readExpressionAxioms()}.
         * @param dataProperty the dataProperty which value, contained in {@link #getDataProperties()}, will be removed.
         * @param value the specific dataProperty literal to be removed from the {@link DataLinks#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( OWLDataProperty dataProperty, OWLLiteral value){
            boolean out = false;
            HashSet<OWLDataProperty> toRemove = new HashSet<>();
            for (DataLinks d : getDataProperties()) {
                if (d.getExpression().equals(dataProperty))
                    out = d.getValues().remove(value);
                if ( d.getValues().isEmpty())
                    toRemove.add( d.getExpression());
            }
            getDataProperties().removeAll( toRemove);
            return out;
        }
        default boolean removeData( OWLDataProperty dataProperty, Set<OWLLiteral> values){
            DataLinks dataSemantic = new DataLinks(dataProperty);
            dataSemantic.getValues().addAll( values);
            return getDataProperties().remove( dataSemantic);
        }

        /**
         * Add a new semantic (i.e.: data dataProperty) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param dataProperty the name of the dataProperty to synchronise.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( String dataProperty){
            return addData( dataProperty, false);
        }
        /**
         * Add a new semantic (i.e.: data dataProperty) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param dataProperty the dataProperty to synchronise.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty dataProperty){
            return addData( dataProperty, false);
        }
        /**
         * Add a new semantic (i.e.: data dataProperty) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param dataProperty the name of the dataProperty to synchronise.
         * @param singleton the flag to enable the synchronisation of only one element of the set,
         *                  others will be discarded.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( String dataProperty, boolean singleton){
            return addData( getOWLDataProperty( dataProperty), singleton);
        }
        /**
         * Add a new semantic (i.e.: data dataProperty) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param dataProperty the dataProperty to synchronise.
         * @param singleton the flag to enable the synchronisation of only one element of the set,
         *                  others will be discarded.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty dataProperty, boolean singleton){
            for (DataLinks d : getDataProperties()) {
                if (d.getExpression().equals(dataProperty)) {
                    d.getValues().setSingleton( singleton);
                    return false;
                }
            }

            DataLinks data = new DataLinks(dataProperty);
            data.getValues().setSingleton( singleton);
            return getDataProperties().add(data);
        }

        /**
         * Add a new semantic (i.e.: data dataProperty) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to {@code false} anyway.
         * The class of the specified value represents its data type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param dataProperty the name of the dataProperty to synchronise.
         * @param value the specific data to be added to the {@link DataLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( String dataProperty, Object value){
            return addData( dataProperty, value, false);
        }
        /**
         * Add a new semantic (i.e.: data dataProperty) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to {@code false} anyway.
         * @param dataProperty the dataProperty to synchronise.
         * @param value the literal to be added to the {@link DataLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty dataProperty, OWLLiteral value){
            return addData( dataProperty, value, false);
        }
        /**
         * Add a new semantic (i.e.: data dataProperty) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to specified boolean anyway.
         * The class of the specified value represents its data type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param dataProperty the name of the dataProperty to synchronise.
         * @param value the specific data to be added to the {@link DataLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( String dataProperty, Object value, boolean singleton){
            return addData( getOWLDataProperty( dataProperty), getOWLLiteral( value), singleton);
        }
        /**
         * Add a new semantic (i.e.: data dataProperty) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getDataProperties()} represents a singleton set this call clear the
         * previous contents.
         * @param dataProperty the dataProperty to synchronise.
         * @param value the specific dataProperty literal to be added to the {@link DataLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty dataProperty, OWLLiteral value, boolean singleton){
            for (DataLinks d : getDataProperties())
                if( d.getExpression().equals( dataProperty)){
                    if ( singleton)
                        d.getValues().clear();
                    d.getValues().setSingleton( singleton);
                    return d.getValues().add( value);
                }

            DataLinks data = new DataLinks(dataProperty);
            data.getValues().add(value);
            data.getValues().setSingleton( singleton);
            return getDataProperties().add(data);
        }
        /**
         * Add a new semantic (i.e.: data dataProperty) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getDataProperties()} represents a singleton set this call clear the
         * previous contents.
         * @param dataProperty the dataProperty to synchronise.
         * @param values the specific set of dataProperty literal to be added to the {@link DataLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLDataProperty dataProperty, Set<OWLLiteral> values, boolean singleton){
            DataLinks objectSemantic = new DataLinks(dataProperty);
            objectSemantic.getValues().addAll( values);
            objectSemantic.getValues().setSingleton( singleton);
            return getDataProperties().add( objectSemantic);
        }
        /**
         * Add a new semantic (i.e.: data dataProperty) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getDataProperties()} represents a singleton set this call clear the
         * previous contents.
         * This call, automatically sets the {@code singleton} flag to false.
         * @param dataProperty the dataProperty to synchronise.
         * @param values the specific set of dataProperty literal to be added to the {@link DataLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLDataProperty dataProperty, Set<OWLLiteral> values){
            return addObject( dataProperty, values, false);
        }


        @Override
        DataLinkSet getDataProperties();

        /**
         * A shortcut for {@code getDataProperties().getLink( semantic)}
         * @param instance the data dataProperty to look for its values.
         * @return a value of the given data dataProperty. {@code Null} if is not available.
         */
        default OWLLiteral getLiteralFromDataProperty(OWLDataProperty instance){
            return getDataProperties().getLink( instance);
        }
        /**
         * A shortcut for {@code getDataProperties().getLink( getOntology().getOWLDataProperty( semanticName)}
         * @param dataPropertyName the name of the data dataProperty to look for its values.
         * @return a value of the given data dataProperty. {@code Null} if is not available.
         */
        default OWLLiteral getLiteralFromDataProperty(String dataPropertyName){
            return getDataProperties().getLink( getOntologyReference().getOWLDataProperty( dataPropertyName));
        }

        /**
         * A shortcut for {@code getDataProperties().getLinks( semantic)}
         * @param instance the data dataProperty to look for its values.
         * @return all the values of the given data dataProperty. An {@code empty} {@link HashSet} if is not available.
         */
        default EntitySet<OWLLiteral> getLiteralsFromDataProperty(OWLDataProperty instance){
            return getDataProperties().getLinks( instance);
        }
        /**
         * A shortcut for {@code getDataProperties().getLinks( getOntology().getOWLDataProperty( semanticName))}
         * @param dataPropertyName the name of the data dataProperty to look for its values.
         * @return all the values of the given data dataProperty. An {@code empty} {@link HashSet} if is not available.
         */
        default EntitySet<OWLLiteral> getLiteralsFromDataProperty(String dataPropertyName){
            return getDataProperties().getLinks( getOntologyReference().getOWLDataProperty( dataPropertyName));
        }



        @Override // see super classes for documentation
        default DataLinkSet queryDataProperties(){
            DataLinkSet dataSet = new DataLinkSet();
            dataSet.setSingleton( getDataProperties().isSingleton());
            for (DataPropertyRelations r :  getOntologyReference().getDataPropertyB2Individual(getInstance())){
                DataLinks data = new DataLinks( r.getProperty());
                data.getValues().addAll( r.getValues());
                for (DataLinks w : getDataProperties())
                    if ( data.equals( w)){
                        data.getValues().setSingleton( w.getValues().isSingleton());
                        break;
                    }
                dataSet.add( data);
            }
            return dataSet;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<DataLinks> to = synchroniseDataPropertiesToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (DataLinks a : to.getToAdd())
                    for (OWLLiteral l : a.getValues())
                        changes.add(getOntologyReference().addDataPropertyB2Individual(getInstance(), a.getExpression(), l));
                for (DataLinks r : to.getToRemove())
                    for (OWLLiteral l : r.getValues())
                        changes.add(getOntologyReference().removeDataPropertyB2Individual(getInstance(), r.getExpression(), l));
                return getChangingIntent(to, changes);
            }catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link Individual.ObjectLink} expression for a {@link Descriptor} whose ground is {@link OWLNamedIndividual}.
     * <p>
     *     It specifies how to {@link #queryObjectProperties()} and {@link #writeExpressionAxioms()} for the
     *     object properties associated to the ground Individual {@link #getInstance()}.
     *     It also implements common function to populate the {@link ExpressionEntitySet}
     *     (of type {@link ObjectLinks}) that specify the object properties of this individualDescriptor
     *     that are synchronised with this {@link Descriptor}. For efficiency,
     *     this descriptor does not map all the objectProperty of an individualDescriptor but only for the
     *     {@code Expressions} that have been initialised in the {@link ExpressionEntity}.
     *     On the other hand, if the set of {@link ExpressionEntitySet} is leaved empty during
     *     {@link #readExpressionAxioms()}, it maps all the object properties applied to the described
     *     individualDescriptor.
     * </p>
     *
     * @param <D> the type of the {@link DataPropertyExpression} descriptors instantiated during
     *           {@link #buildObjectProperties()} through {@link #getNewObjectProperty(ExpressionEntity, Object)}.
     */
    interface ObjectLink<D extends ObjectPropertyExpression>
            extends Individual.ObjectLink<OWLReferences, OWLNamedIndividual, ObjectLinks, OWLObjectProperty, D>,
            IndividualExpression {

        /**
         * Remove the {@link ExpressionEntity} of the given semantic ({@link ObjectLinks#getExpression()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()}.
         * @param objectProperty an objectProperty name contained in an element of {@link #getObjectProperties()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( String objectProperty){
            return removeObject( getOWLObjectProperty( objectProperty));
        }
        /**
         * Remove the {@link ExpressionEntity} of the given semantic ({@link ObjectLinks#getExpression()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()}.
         * @param objectProperty an objectProperty contained in an element of {@link #getObjectProperties()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( OWLObjectProperty objectProperty){
            return getObjectProperties().remove( objectProperty);
        }

        /**
         * Remove the {@link ExpressionEntity#getExpression()} of the given objectProperty, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readExpressionAxioms()}.
         * @param objectProperty the name of the objectProperty which value, contained in {@link #getObjectProperties()}, will be removed.
         * @param value the objectProperty value to be removed from the {@link ObjectLinks#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( String objectProperty, String value){
            return removeObject( getOWLObjectProperty( objectProperty), getOWLIndividual( value));
        }
        /**
         * Remove the {@link ExpressionEntity#getExpression()} of the given objectProperty, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readExpressionAxioms()}.
         * @param objectProperty the objectProperty which value, contained in {@link #getObjectProperties()}, will be removed.
         * @param value the specific objectProperty literal to be removed from the {@link ObjectLinks#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( OWLObjectProperty objectProperty, OWLNamedIndividual value){
            ObjectLinks objectSemantic = new ObjectLinks(objectProperty);
            objectSemantic.getValues().add( value);
            boolean out = getObjectProperties().remove( objectSemantic);
            removeEmptyDataProprtySet();
            return out;
        }
        /**
         * Remove the {@link ExpressionEntity#getExpression()} of the given objectProperty, with specific values, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readExpressionAxioms()}.
         * @param objectProperty the objectProperty which value, contained in {@link #getObjectProperties()}, will be removed.
         * @param values the specific set of objectProperty literal to be removed from the {@link ObjectLinks#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( OWLObjectProperty objectProperty, Set<OWLNamedIndividual> values){
            ObjectLinks objectSemantic = new ObjectLinks(objectProperty);
            objectSemantic.getValues().addAll( values);
            boolean out = getObjectProperties().remove( objectSemantic);
            removeEmptyDataProprtySet();
            return out;
        }
        default void removeEmptyDataProprtySet(){
            HashSet<OWLObjectProperty> toRemove = new HashSet<>();
            for (ObjectLinks d : getObjectProperties()) {
                if ( d.getValues().isEmpty())
                    toRemove.add( d.getExpression());
            }
            getObjectProperties().removeAll( toRemove);
        }


        /**
         * Add a new semantic (i.e.: object objectProperty) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param objectProperty the name of the objectProperty to synchronise.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( String objectProperty){
            return addObject( objectProperty, false);
        }
        /**
         * Add a new semantic (i.e.: object objectProperty) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param objectProperty the objectProperty to synchronise.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty objectProperty){
            return addObject( objectProperty, false);
        }
        /**
         * Add a new semantic (i.e.: object objectProperty) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param objectProperty the name of the objectProperty to synchronise.
         * @param singleton the flag to enable the synchronisation of only one element of the set,
         *                  others will be discarded.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( String objectProperty, boolean singleton){
            return addObject( getOWLObjectProperty( objectProperty), singleton);
        }
        /**
         * Add a new semantic (i.e.: object objectProperty) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param objectProperty the objectProperty to synchronise.
         * @param singleton the flag to enable the synchronisation of only one element of the set,
         *                  others will be discarded.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty objectProperty, boolean singleton){
            ObjectLinks objectSemantic = new ObjectLinks(objectProperty);
            objectSemantic.getValues().setSingleton( singleton);
            return getObjectProperties().add( objectSemantic);
        }

        /**
         * Add a new semantic (i.e.: object objectProperty) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to {@code false} anyway.
         * The class of the specified value represents its object type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param objectProperty the name of the objectProperty to synchronise.
         * @param value the specific object to be added to the {@link ObjectLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( String objectProperty, String value){
            return addObject( objectProperty, value, false);
        }
        /**
         * Add a new semantic (i.e.: object objectProperty) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to {@code false} anyway.
         * @param objectProperty the objectProperty to synchronise.
         * @param value the literal to be added to the {@link ObjectLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty objectProperty, OWLNamedIndividual value){
            return addObject( objectProperty, value, false);
        }
        /**
         * Add a new semantic (i.e.: object objectProperty) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to specified boolean anyway.
         * @param objectProperty the name of the objectProperty to synchronise.
         * @param value the specific object to be added to the {@link ObjectLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( String objectProperty, String value, boolean singleton){
            return addObject( getOWLObjectProperty( objectProperty), getOWLIndividual( value), singleton);
        }
        /**
         * Add a new semantic (i.e.: object objectProperty) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getObjectProperties()} represents a singleton set this call clear the
         * previous contents.
         * @param objectProperty the objectProperty to synchronise.
         * @param value the specific objectProperty literal to be added to the {@link ObjectLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty objectProperty, OWLNamedIndividual value, boolean singleton){
            ObjectLinks objectSemantic = new ObjectLinks(objectProperty);
            objectSemantic.getValues().add( value);
            objectSemantic.getValues().setSingleton( singleton);
            return getObjectProperties().add( objectSemantic);
        }
        /**
         * Add a new semantic (i.e.: object objectProperty) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getObjectProperties()} represents a singleton set this call clear the
         * previous contents.
         * @param objectProperty the objectProperty to synchronise.
         * @param values the specific set of objectProperty literal to be added to the {@link ObjectLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty objectProperty, Set<OWLNamedIndividual> values, boolean singleton){
            ObjectLinks objectSemantic = new ObjectLinks(objectProperty);
            objectSemantic.getValues().addAll( values);
            objectSemantic.getValues().setSingleton( singleton);
            return getObjectProperties().add( objectSemantic);
        }
        /**
         * Add a new semantic (i.e.: object objectProperty) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getObjectProperties()} represents a singleton set this call clear the
         * previous contents.
         * This call, automatically sets the {@code singleton} flag to false.
         * @param objectProperty the objectProperty to synchronise.
         * @param values the specific set of objectProperty literal to be added to the {@link ObjectLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty objectProperty, Set<OWLNamedIndividual> values){
            return addObject( objectProperty, values, false);
        }

        @Override
        ObjectLinkSet getObjectProperties();

        /**
         * A shortcut for {@code getObjectProperties().getLink( semantic)}
         * @param instance the object objectProperty to look for its values.
         * @return a value of the given object objectProperty. {@code Null} if is not available.
         */
        default OWLNamedIndividual getIndividualFromObjectProperty(OWLObjectProperty instance){
            return getObjectProperties().getLink( instance);
        }
        /**
         * A shortcut for {@code getObjectProperties().getLink( getOntology().getOWLObjectProperty( semanticName)}
         * @param objectPropertyName the name of the objectProperty to look for its values.
         * @return a value of the given object objectProperty. {@code Null} if is not available.
         */
        default OWLNamedIndividual getIndividualFromObjectProperty(String objectPropertyName){
            return getObjectProperties().getLink( getOntologyReference().getOWLObjectProperty( objectPropertyName));
        }

        /**
         * A shortcut for {@code getObjectProperties().getLinks( semantic)}
         * @param instance the object objectProperty to look for its values.
         * @return all the values of the given object objectProperty. An {@code empty} {@link HashSet} if is not available.
         */
        default EntitySet<OWLNamedIndividual> getIndividualsFromObjectProperty(OWLObjectProperty instance){
            return getObjectProperties().getLinks( instance);
        }
        /**
         * A shortcut for {@code getObjectProperties().getLinks( getOntology().getOWLObjectProperty( semanticName))}
         * @param objectPropertyName the name of the objectProperty to look for its values.
         * @return all the values of the given object objectProperty. {@code Null} if is not available.
         */
        default EntitySet<OWLNamedIndividual> getIndividualsFromObjectProperty(String objectPropertyName){
            return getObjectProperties().getLinks( getOntologyReference().getOWLObjectProperty( objectPropertyName));
        }

        @Override // see super classes for documentation
        default ObjectLinkSet queryObjectProperties(){
            ObjectLinkSet objectSet = new ObjectLinkSet();
            objectSet.setSingleton( getObjectProperties().isSingleton());
            for (ObjectPropertyRelations r :  getOntologyReference().getObjectPropertyB2Individual(getInstance())){
                ObjectLinks object = new ObjectLinks( r.getProperty());
                object.getValues().addAll( r.getValues());
                for (ObjectLinks w : getObjectProperties())
                    if ( object.equals( w)){
                        object.getValues().setSingleton( w.getValues().isSingleton());
                        break;
                    }
                objectSet.add( object);
            }
            return objectSet;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<ObjectLinks> to = synchroniseObjectPropertiesToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (ObjectLinks a : to.getToAdd())
                    for (OWLNamedIndividual l : a.getValues())
                        changes.add(getOntologyReference().addObjectPropertyB2Individual(getInstance(), a.getExpression(), l));
                for (ObjectLinks r : to.getToRemove())
                    for (OWLNamedIndividual l : r.getValues())
                        changes.add(getOntologyReference().removeObjectPropertyB2Individual(getInstance(), r.getExpression(), l));
                return getChangingIntent(to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }
}
