package it.emarolab.owloop.descriptor.construction.descriptorExpression;

import it.emarolab.amor.owlInterface.DataPropertyRelations;
import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.ObjectPropertyRelations;
import it.emarolab.owloop.core.Individual;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
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
     *     It specifies how to {@link #queryIndividualTypes()} and {@link #writeExpressionAxioms()} for the
     *     types (i.e.: {@link OWLClass}) of the ground Individual ({@link #getInstance()}).
     * </p>
     *
     * @param <D> the type of the {@link ConceptExpression} descriptors instantiated during
     *           {@link #buildTypeIndividual()} through {@link #getNewIndividualType(Object, Object)}.
     */
    interface Type<D extends ConceptExpression>
            extends Individual.Type<OWLReferences, OWLNamedIndividual, OWLClass,D>,
            IndividualExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getIndividualTypes()}.add( {@link #getOntology()}.getOWLClass( className))}
         * in order to add a new class (given by name) in the {@link EntitySet} list.
         * @param className the class name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addTypeIndividual(String className){
            return getIndividualTypes().add( getOntology().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getIndividualTypes()}.add( cl)}
         * in order to add a new class in the {@link EntitySet} list.
         * @param cl the class to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addTypeIndividual(OWLClass cl){
            return getIndividualTypes().add( cl);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getIndividualTypes()}.remove( {@link #getOntology()}.getOWLClass( className))}
         * in order to remove a class (given by name) from the {@link EntitySet} list.
         * @param className the class name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeTypeIndividual(String className){
            return getIndividualTypes().remove( getOntology().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getIndividualTypes()}.remove( individualDescriptor)}
         * in order to remove a class in the {@link EntitySet} list.
         * @param cl the class to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeTypeIndividual(OWLClass cl){
            return getIndividualTypes().remove( cl);
        }

        @Override
        DescriptorEntitySet.Concepts getIndividualTypes();

        @Override // see super classes for documentation
        default DescriptorEntitySet.Concepts queryIndividualTypes(){
            DescriptorEntitySet.Concepts set = new DescriptorEntitySet.Concepts(getOntology().getIndividualClasses(getInstance()));
            set.setSingleton( getIndividualTypes().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLClass> to = synchroniseTypeIndividualToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (OWLClass a : to.getToAdd())
                    changes.add(getOntology().addIndividualB2Class(getInstance(), a));
                for (OWLClass b : to.getToRemove())
                    changes.add(getOntology().removeIndividualB2Class(getInstance(), b));
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
     *           {@link #buildDisjointIndividual()}  through {@link #getNewDisjointIndividual(Object, Object)}.
     */
    interface Disjoint<D extends IndividualExpression>
            extends Individual.Disjoint<OWLReferences, OWLNamedIndividual,D>,
            IndividualExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointIndividuals()}.add( {@link #getOntology()}.getOWLIndividual( individualName))}
         * in order to add a new individualDescriptor (given by name) in the {@link EntitySet} list.
         * @param individualName the individualDescriptor name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointIndividual( String individualName){
            return getDisjointIndividuals().add( getOntology().getOWLIndividual( individualName));
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
         * {@code {@link #getDisjointIndividuals()}.remove( {@link #getOntology()}.getOWLIndividual( individualName))}
         * in order to remove an individualDescriptor (given by name) from the {@link EntitySet} list.
         * @param individualName the individualDescriptor name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointIndividual( String individualName){
            return getDisjointIndividuals().remove( getOntology().getOWLIndividual( individualName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointIndividuals()}.remove( property)}
         * in order to remove a class in the {@link EntitySet} list.
         * @param individual the individualDescriptor to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointIndividual( OWLNamedIndividual individual){
            return getDisjointIndividuals().remove( individual);
        }

        @Override
        DescriptorEntitySet.Individuals getDisjointIndividuals();

        @Override // see super classes for documentation
        default DescriptorEntitySet.Individuals queryDisjointIndividuals(){
            DescriptorEntitySet.Individuals set = new DescriptorEntitySet.Individuals(getOntology().getDisjointIndividuals(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getDisjointIndividuals().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLNamedIndividual> to = synchroniseDisjointIndividualToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLNamedIndividual a : to.getToAdd()){
                    Set<OWLNamedIndividual> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( a);
                    changes.add( getOntology().makeDisjointIndividuals( s));
                }
                for( OWLNamedIndividual r : to.getToRemove()){
                    Set<OWLNamedIndividual> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( r);
                    changes.add( getOntology().removeDisjointIndividuals( s));
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
     *           {@link #buildEquivalentIndividual()} through {@link #getNewEquivalentIndividual(Object, Object)}.
     */
    interface Equivalent<D extends IndividualExpression>
            extends Individual.Equivalent<OWLReferences, OWLNamedIndividual,D>,
            IndividualExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentIndividuals()}.add( {@link #getOntology()}.getOWLIndividual( individualName))}
         * in order to add a new individualDescriptor (given by name) in the {@link EntitySet} list.
         * @param individualName the individualDescriptor name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentIndividual( String individualName){
            return getEquivalentIndividuals().add( getOntology().getOWLIndividual( individualName));
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
         * {@code {@link #getEquivalentIndividuals()}.remove( {@link #getOntology()}.getOWLIndividual( individualName))}
         * in order to remove an individualDescriptor (given by name) from the {@link EntitySet} list.
         * @param individualName the individualDescriptor name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentIndividual( String individualName){
            return getEquivalentIndividuals().remove( getOntology().getOWLIndividual( individualName));
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
        DescriptorEntitySet.Individuals getEquivalentIndividuals();

        @Override // see super classes for documentation
        default DescriptorEntitySet.Individuals queryEquivalentIndividuals(){
            DescriptorEntitySet.Individuals set = new DescriptorEntitySet.Individuals(getOntology().getEquivalentIndividuals(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getEquivalentIndividuals().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLNamedIndividual> to = synchroniseEquivalentIndividualToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLNamedIndividual a : to.getToAdd()){
                    Set<OWLNamedIndividual> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( a);
                    changes.add( getOntology().makeEquivalentIndividuals( s));
                }
                for( OWLNamedIndividual r : to.getToRemove()){
                    Set<OWLNamedIndividual> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( r);
                    changes.add( getOntology().removeEquivalentIndividuals( s));
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
     *     It specifies how to {@link #queryIndividualDataProperties()} and {@link #writeExpressionAxioms()} for the
     *     data properties associated to the ground Individual {@link #getInstance()}.
     *     It also implements common function to populate the {@link ExpressionEntitySet}
     *     (of type {@link DescriptorEntitySet.DataLinks}) that specify the data properties of this individualDescriptor
     *     that are synchronised with this {@link Descriptor}. For efficiency,
     *     this descriptor does not map all the properties of an individualDescriptor but only for the
     *     {@code Expressions} that have been initialised in the {@link ExpressionEntity}.
     *     On the other hand, if the set of {@link ExpressionEntitySet} is left empty during
     *     {@link #readExpressionAxioms()}, it maps all the object properties applied to the described
     *     individualDescriptor
     * </p>
     *
     * @param <D> the type of the {@link DataPropertyExpression} descriptors instantiated during
     *           {@link #buildDataIndividual()} through {@link #getNewIndividualDataProperty(ExpressionEntity, Object)}.
     */
    interface DataLink<D extends DataPropertyExpression>
            extends Individual.DataLink<OWLReferences, OWLNamedIndividual, DescriptorEntitySet.DataLinks, OWLDataProperty, D>,
            IndividualExpression {

        /**
         * Remove the {@link ExpressionEntity} of the given semantic ({@link DescriptorEntitySet.DataLinks#getExpression()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()}.
         * @param property a data property name contained in an element of {@link #getIndividualDataProperties()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( String property){
            return removeData( getOWLDataProperty( property));
        }
        /**
         * Remove the {@link ExpressionEntity} of the given semantic ({@link DescriptorEntitySet.DataLinks#getExpression()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()}.
         * @param property a data property contained in an element of {@link #getIndividualDataProperties()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( OWLDataProperty property){
            for (DescriptorEntitySet.DataLinks d : getIndividualDataProperties())
                if( d.getExpression().equals( property))
                    return getIndividualDataProperties().remove( d);
            return false;
        }

        /**
         * Remove the {@link ExpressionEntity#getExpression()} of the given property, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readExpressionAxioms()}.
         * The class of the specified value represents its data type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param property the name of the data property which value, contained in {@link #getIndividualDataProperties()}, will be removed.
         * @param value the property value to be removed from the {@link DescriptorEntitySet.DataLinks#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( String property, Object value){
            return removeData( getOWLDataProperty( property), getOWLLiteral( value));
        }
        /**
         * Remove the {@link ExpressionEntity#getExpression()} of the given property, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readExpressionAxioms()}.
         * @param property the data property which value, contained in {@link #getIndividualDataProperties()}, will be removed.
         * @param value the specific property literal to be removed from the {@link DescriptorEntitySet.DataLinks#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( OWLDataProperty property, OWLLiteral value){
            boolean out = false;
            HashSet<OWLDataProperty> toRemove = new HashSet<>();
            for (DescriptorEntitySet.DataLinks d : getIndividualDataProperties()) {
                if (d.getExpression().equals(property))
                    out = d.getValues().remove(value);
                if ( d.getValues().isEmpty())
                    toRemove.add( d.getExpression());
            }
            getIndividualDataProperties().removeAll( toRemove);
            return out;
        }
        default boolean removeData( OWLDataProperty property, Set<OWLLiteral> values){
            DescriptorEntitySet.DataLinks dataSemantic = new DescriptorEntitySet.DataLinks(property);
            dataSemantic.getValues().addAll( values);
            return getIndividualDataProperties().remove( dataSemantic);
        }

        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param property the name of the data property to synchronise.
         * @return {@code true} if an element was added as a result of this call 
         * (a change of singleton value is not considered).
         */
        default boolean addData( String property){
            return addData( property, false);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param property the data property to synchronise.
         * @return {@code true} if an element was added as a result of this call 
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty property){
            return addData( property, false);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param property the name of the data property to synchronise.
         * @param singleton the flag to enable the synchronisation of only one element of the set, 
         *                  others will be discarded.
         * @return {@code true} if an element was added as a result of this call 
         * (a change of singleton value is not considered).
         */
        default boolean addData( String property, boolean singleton){
            return addData( getOWLDataProperty( property), singleton);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param property the data property to synchronise.
         * @param singleton the flag to enable the synchronisation of only one element of the set, 
         *                  others will be discarded.
         * @return {@code true} if an element was added as a result of this call 
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty property, boolean singleton){
            for (DescriptorEntitySet.DataLinks d : getIndividualDataProperties()) {
                if (d.getExpression().equals(property)) {
                    d.getValues().setSingleton( singleton);
                    return false;
                }
            }

            DescriptorEntitySet.DataLinks data = new DescriptorEntitySet.DataLinks(property);
            data.getValues().setSingleton( singleton);
            return getIndividualDataProperties().add(data);
        }

        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to {@code false} anyway.
         * The class of the specified value represents its data type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param property the name of the data property to synchronise.
         * @param value the specific data to be added to the {@link DescriptorEntitySet.DataLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call 
         * (a change of singleton value is not considered).
         */
        default boolean addData( String property, Object value){
            return addData( property, value, false);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to {@code false} anyway.
         * @param property the data property to synchronise.
         * @param value the literal to be added to the {@link DescriptorEntitySet.DataLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty property, OWLLiteral value){
            return addData( property, value, false);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to specified boolean anyway.
         * The class of the specified value represents its data type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param property the name of the data property to synchronise.
         * @param value the specific data to be added to the {@link DescriptorEntitySet.DataLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link DescriptorEntitySet.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( String property, Object value, boolean singleton){
            return addData( getOWLDataProperty( property), getOWLLiteral( value), singleton);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getIndividualDataProperties()} represents a singleton set this call clear the
         * previous contents.
         * @param property the data property to synchronise.
         * @param value the specific property literal to be added to the {@link DescriptorEntitySet.DataLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link DescriptorEntitySet.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty property, OWLLiteral value, boolean singleton){
            for (DescriptorEntitySet.DataLinks d : getIndividualDataProperties())
                if( d.getExpression().equals( property)){
                    if ( singleton)
                        d.getValues().clear();
                    d.getValues().setSingleton( singleton);
                    return d.getValues().add( value);
                }

            DescriptorEntitySet.DataLinks data = new DescriptorEntitySet.DataLinks(property);
            data.getValues().add(value);
            data.getValues().setSingleton( singleton);
            return getIndividualDataProperties().add(data);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getIndividualDataProperties()} represents a singleton set this call clear the
         * previous contents.
         * @param property the data property to synchronise.
         * @param values the specific set of property literal to be added to the {@link DescriptorEntitySet.DataLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link DescriptorEntitySet.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLDataProperty property, Set<OWLLiteral> values, boolean singleton){
            DescriptorEntitySet.DataLinks objectSemantic = new DescriptorEntitySet.DataLinks(property);
            objectSemantic.getValues().addAll( values);
            objectSemantic.getValues().setSingleton( singleton);
            return getIndividualDataProperties().add( objectSemantic);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getIndividualDataProperties()} represents a singleton set this call clear the
         * previous contents.
         * This call, automatically sets the {@code singleton} flag to false.
         * @param property the data property to synchronise.
         * @param values the specific set of property literal to be added to the {@link DescriptorEntitySet.DataLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLDataProperty property, Set<OWLLiteral> values){
            return addObject( property, values, false);
        }


        @Override
        DescriptorEntitySet.DataLinksSet getIndividualDataProperties();

        /**
         * A shortcut for {@code getIndividualDataProperties().getLink( semantic)}
         * @param semantic the data property to look for its values.
         * @return a value of the given data property. {@code Null} if is not available.
         */
        default OWLLiteral getLiteral( OWLDataProperty semantic){
            return getIndividualDataProperties().getLink( semantic);
        }
        /**
         * A shortcut for {@code getIndividualDataProperties().getLink( getOntology().getOWLDataProperty( semanticName)}
         * @param semanticName the name of the data property to look for its values.
         * @return a value of the given data property. {@code Null} if is not available.
         */
        default OWLLiteral getLiteral( String semanticName){
            return getIndividualDataProperties().getLink( getOntology().getOWLDataProperty( semanticName));
        }

        /**
         * A shortcut for {@code getIndividualDataProperties().getLinks( semantic)}
         * @param semantic the data property to look for its values.
         * @return all the values of the given data property. An {@code empty} {@link HashSet} if is not available.
         */
        default EntitySet<OWLLiteral> getLiterals(OWLDataProperty semantic){
            return getIndividualDataProperties().getLinks( semantic);
        }
        /**
         * A shortcut for {@code getIndividualDataProperties().getLinks( getOntology().getOWLDataProperty( semanticName))}
         * @param semanticName the name of the data property to look for its values.
         * @return all the values of the given data property. An {@code empty} {@link HashSet} if is not available.
         */
        default EntitySet<OWLLiteral> getLiterals(String semanticName){
            return getIndividualDataProperties().getLinks( getOntology().getOWLDataProperty( semanticName));
        }



        @Override // see super classes for documentation
        default DescriptorEntitySet.DataLinksSet queryIndividualDataProperties(){
            DescriptorEntitySet.DataLinksSet dataSet = new DescriptorEntitySet.DataLinksSet();
            dataSet.setSingleton( getIndividualDataProperties().isSingleton());
            for (DataPropertyRelations r :  getOntology().getDataPropertyB2Individual(getInstance())){
                DescriptorEntitySet.DataLinks data = new DescriptorEntitySet.DataLinks( r.getProperty());
                data.getValues().addAll( r.getValues());
                for (DescriptorEntitySet.DataLinks w : getIndividualDataProperties())
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
                EntitySet.SynchronisationIntent<DescriptorEntitySet.DataLinks> to = synchroniseDataIndividualToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (DescriptorEntitySet.DataLinks a : to.getToAdd())
                    for (OWLLiteral l : a.getValues())
                        changes.add(getOntology().addDataPropertyB2Individual(getInstance(), a.getExpression(), l));
                for (DescriptorEntitySet.DataLinks r : to.getToRemove())
                    for (OWLLiteral l : r.getValues())
                        changes.add(getOntology().removeDataPropertyB2Individual(getInstance(), r.getExpression(), l));
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
     *     It specifies how to {@link #queryIndividualObjectProperties()} and {@link #writeExpressionAxioms()} for the
     *     object properties associated to the ground Individual {@link #getInstance()}.
     *     It also implements common function to populate the {@link ExpressionEntitySet}
     *     (of type {@link DescriptorEntitySet.ObjectLinks}) that specify the object properties of this individualDescriptor
     *     that are synchronised with this {@link Descriptor}. For efficiency,
     *     this descriptor does not map all the property of an individualDescriptor but only for the
     *     {@code Expressions} that have been initialised in the {@link ExpressionEntity}.
     *     On the other hand, if the set of {@link ExpressionEntitySet} is leaved empty during
     *     {@link #readExpressionAxioms()}, it maps all the object properties applied to the described
     *     individualDescriptor.
     * </p>
     *
     * @param <D> the type of the {@link DataPropertyExpression} descriptors instantiated during
     *           {@link #buildObjectIndividual()} through {@link #getNewIndividualObjectProperty(ExpressionEntity, Object)}.
     */
    interface ObjectLink<D extends ObjectPropertyExpression>
            extends Individual.ObjectLink<OWLReferences, OWLNamedIndividual, DescriptorEntitySet.ObjectLinks, OWLObjectProperty, D>,
            IndividualExpression {

        /**
         * Remove the {@link ExpressionEntity} of the given semantic ({@link DescriptorEntitySet.ObjectLinks#getExpression()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()}.
         * @param property an object property name contained in an element of {@link #getIndividualObjectProperties()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( String property){
            return removeObject( getOWLObjectProperty( property));
        }
        /**
         * Remove the {@link ExpressionEntity} of the given semantic ({@link DescriptorEntitySet.ObjectLinks#getExpression()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()}.
         * @param property an object property contained in an element of {@link #getIndividualObjectProperties()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( OWLObjectProperty property){
            return getIndividualObjectProperties().remove( property);
        }

        /**
         * Remove the {@link ExpressionEntity#getExpression()} of the given property, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readExpressionAxioms()}.
         * @param property the name of the object property which value, contained in {@link #getIndividualObjectProperties()}, will be removed.
         * @param value the property value to be removed from the {@link DescriptorEntitySet.ObjectLinks#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( String property, String value){
            return removeObject( getOWLObjectProperty( property), getOWLIndividual( value));
        }
        /**
         * Remove the {@link ExpressionEntity#getExpression()} of the given property, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readExpressionAxioms()}.
         * @param property the object property which value, contained in {@link #getIndividualObjectProperties()}, will be removed.
         * @param value the specific property literal to be removed from the {@link DescriptorEntitySet.ObjectLinks#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( OWLObjectProperty property, OWLNamedIndividual value){
            DescriptorEntitySet.ObjectLinks objectSemantic = new DescriptorEntitySet.ObjectLinks(property);
            objectSemantic.getValues().add( value);
            boolean out = getIndividualObjectProperties().remove( objectSemantic);
            removeEmptyDataProprtySet();
            return out;
        }
        /**
         * Remove the {@link ExpressionEntity#getExpression()} of the given property, with specific values, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readExpressionAxioms()}.
         * @param property the object property which value, contained in {@link #getIndividualObjectProperties()}, will be removed.
         * @param values the specific set of property literal to be removed from the {@link DescriptorEntitySet.ObjectLinks#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( OWLObjectProperty property, Set<OWLNamedIndividual> values){
            DescriptorEntitySet.ObjectLinks objectSemantic = new DescriptorEntitySet.ObjectLinks(property);
            objectSemantic.getValues().addAll( values);
            boolean out = getIndividualObjectProperties().remove( objectSemantic);
            removeEmptyDataProprtySet();
            return out;
        }
        default void removeEmptyDataProprtySet(){
            HashSet<OWLObjectProperty> toRemove = new HashSet<>();
            for (DescriptorEntitySet.ObjectLinks d : getIndividualObjectProperties()) {
                if ( d.getValues().isEmpty())
                    toRemove.add( d.getExpression());
            }
            getIndividualObjectProperties().removeAll( toRemove);
        }


        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param property the name of the object property to synchronise.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( String property){
            return addObject( property, false);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param property the object property to synchronise.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property){
            return addObject( property, false);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param property the name of the object property to synchronise.
         * @param singleton the flag to enable the synchronisation of only one element of the set,
         *                  others will be discarded.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( String property, boolean singleton){
            return addObject( getOWLObjectProperty( property), singleton);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readExpressionAxioms()}.
         * @param property the object property to synchronise.
         * @param singleton the flag to enable the synchronisation of only one element of the set,
         *                  others will be discarded.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property, boolean singleton){
            DescriptorEntitySet.ObjectLinks objectSemantic = new DescriptorEntitySet.ObjectLinks(property);
            objectSemantic.getValues().setSingleton( singleton);
            return getIndividualObjectProperties().add( objectSemantic);
        }

        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to {@code false} anyway.
         * The class of the specified value represents its object type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param property the name of the object property to synchronise.
         * @param value the specific object to be added to the {@link DescriptorEntitySet.ObjectLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( String property, String value){
            return addObject( property, value, false);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to {@code false} anyway.
         * @param property the object property to synchronise.
         * @param value the literal to be added to the {@link DescriptorEntitySet.ObjectLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property, OWLNamedIndividual value){
            return addObject( property, value, false);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to specified boolean anyway.
         * @param property the name of the object property to synchronise.
         * @param value the specific object to be added to the {@link DescriptorEntitySet.ObjectLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link DescriptorEntitySet.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( String property, String value, boolean singleton){
            return addObject( getOWLObjectProperty( property), getOWLIndividual( value), singleton);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getIndividualObjectProperties()} represents a singleton set this call clear the
         * previous contents.
         * @param property the object property to synchronise.
         * @param value the specific property literal to be added to the {@link DescriptorEntitySet.ObjectLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link DescriptorEntitySet.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property, OWLNamedIndividual value, boolean singleton){
            DescriptorEntitySet.ObjectLinks objectSemantic = new DescriptorEntitySet.ObjectLinks(property);
            objectSemantic.getValues().add( value);
            objectSemantic.getValues().setSingleton( singleton);
            return getIndividualObjectProperties().add( objectSemantic);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getIndividualObjectProperties()} represents a singleton set this call clear the
         * previous contents.
         * @param property the object property to synchronise.
         * @param values the specific set of property literal to be added to the {@link DescriptorEntitySet.ObjectLinks#getValues()} set.
         * @param singleton the flag specifying if the new {@link DescriptorEntitySet.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property, Set<OWLNamedIndividual> values, boolean singleton){
            DescriptorEntitySet.ObjectLinks objectSemantic = new DescriptorEntitySet.ObjectLinks(property);
            objectSemantic.getValues().addAll( values);
            objectSemantic.getValues().setSingleton( singleton);
            return getIndividualObjectProperties().add( objectSemantic);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link DescriptorEntitySet.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getIndividualObjectProperties()} represents a singleton set this call clear the
         * previous contents.
         * This call, automatically sets the {@code singleton} flag to false.
         * @param property the object property to synchronise.
         * @param values the specific set of property literal to be added to the {@link DescriptorEntitySet.ObjectLinks#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property, Set<OWLNamedIndividual> values){
            return addObject( property, values, false);
        }

        @Override
        DescriptorEntitySet.ObjectLinksSet getIndividualObjectProperties();

        /**
         * A shortcut for {@code getIndividualObjectProperties().getLink( semantic)}
         * @param semantic the object property to look for its values.
         * @return a value of the given object property. {@code Null} if is not available.
         */
        default OWLNamedIndividual getObject( OWLObjectProperty semantic){
            return getIndividualObjectProperties().getLink( semantic);
        }
        /**
         * A shortcut for {@code getIndividualObjectProperties().getLink( getOntology().getOWLObjectProperty( semanticName)}
         * @param semanticName the name of the object property to look for its values.
         * @return a value of the given object property. {@code Null} if is not available.
         */
        default OWLNamedIndividual getObject( String semanticName){
            return getIndividualObjectProperties().getLink( getOntology().getOWLObjectProperty( semanticName));
        }

        /**
         * A shortcut for {@code getIndividualObjectProperties().getLinks( semantic)}
         * @param semantic the object property to look for its values.
         * @return all the values of the given object property. An {@code empty} {@link HashSet} if is not available.
         */
        default EntitySet<OWLNamedIndividual> getObjects(OWLObjectProperty semantic){
            return getIndividualObjectProperties().getLinks( semantic);
        }
        /**
         * A shortcut for {@code getIndividualObjectProperties().getLinks( getOntology().getOWLObjectProperty( semanticName))}
         * @param semanticName the name of the object property to look for its values.
         * @return all the values of the given object property. {@code Null} if is not available.
         */
        default EntitySet<OWLNamedIndividual> getObjects(String semanticName){
            return getIndividualObjectProperties().getLinks( getOntology().getOWLObjectProperty( semanticName));
        }

        @Override // see super classes for documentation
        default DescriptorEntitySet.ObjectLinksSet queryIndividualObjectProperties(){
            DescriptorEntitySet.ObjectLinksSet objectSet = new DescriptorEntitySet.ObjectLinksSet();
            objectSet.setSingleton( getIndividualObjectProperties().isSingleton());
            for (ObjectPropertyRelations r :  getOntology().getObjectPropertyB2Individual(getInstance())){
                DescriptorEntitySet.ObjectLinks object = new DescriptorEntitySet.ObjectLinks( r.getProperty());
                object.getValues().addAll( r.getValues());
                for (DescriptorEntitySet.ObjectLinks w : getIndividualObjectProperties())
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
                EntitySet.SynchronisationIntent<DescriptorEntitySet.ObjectLinks> to = synchroniseObjectIndividualToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (DescriptorEntitySet.ObjectLinks a : to.getToAdd())
                    for (OWLNamedIndividual l : a.getValues())
                        changes.add(getOntology().addObjectPropertyB2Individual(getInstance(), a.getExpression(), l));
                for (DescriptorEntitySet.ObjectLinks r : to.getToRemove())
                    for (OWLNamedIndividual l : r.getValues())
                        changes.add(getOntology().removeObjectPropertyB2Individual(getInstance(), r.getExpression(), l));
                return getChangingIntent(to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }
}
