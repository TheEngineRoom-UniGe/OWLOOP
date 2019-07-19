package it.emarolab.owloop.descriptor.construction.descriptorExpression;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.amor.owlInterface.SemanticRestriction.*;
import it.emarolab.owloop.core.ObjectProperty;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DescriptorGroundInterface;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This interface extends all the interfaces in {@link ObjectProperty}.
 * It allows to {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()}
 * specifically for OWL DataProperties.
 * It contains several expressions that can be combined in any arbitrary way as they
 * rely on the same ground ({@link ObjectGroundInstance}).
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
public interface ObjectPropertyExpression
        extends ObjectProperty<OWLReferences,OWLObjectProperty>,
        DescriptorGroundInterface<OWLObjectProperty> {

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
    @Override @Deprecated
    default OWLObjectProperty getGroundInstance() {
        return getGround().getGroundInstance();
    }


    @Override // see super class for documentation
    default void setFunctional(){
        getOntology().addFunctionalObjectProperty( getInstance());
    }
    @Override // see super class for documentation
    default void setNotFunctional(){
        getOntology().removeFunctionalObjectProperty( getInstance());
    }

    @Override // see super class for documentation
    default void setInverseFunctional(){
        getOntology().addInverseFunctionalObjectProperty( getInstance());
    }
    @Override // see super class for documentation
    default void setNotInverseFunctional(){
        getOntology().removeInverseFunctionalObjectProperty( getInstance());
    }

    @Override // see super class for documentation
    default void setTransitive(){
        getOntology().addTransitiveObjectProperty( getInstance());
    }
    @Override // see super class for documentation
    default void setNotTransitive(){
        getOntology().removeTransitiveObjectProperty( getInstance());
    }

    @Override // see super class for documentation
    default void setSymmetric(){
        getOntology().addSymmetricObjectProperty( getInstance());
    }
    @Override // see super class for documentation
    default void setNotSymmetric(){
        getOntology().removeSymmetricObjectProperty( getInstance());
    }

    @Override // see super class for documentation
    default void setAsymmetric(){
        getOntology().addAsymmetricObjectProperty( getInstance());
    }
    @Override // see super class for documentation
    default void setNotAsymmetric(){
        getOntology().removeAsymmetricObjectProperty( getInstance());
    }

    @Override // see super class for documentation
    default void setReflexive(){
        getOntology().addReflexiveObjectProperty( getInstance());
    }
    @Override // see super class for documentation
    default void setNotReflexive(){
        getOntology().removeReflexiveObjectProperty( getInstance());
    }

    @Override // see super class for documentation
    default void setIrreflexive(){
        getOntology().addIrreflexiveObjectProperty( getInstance());
    }
    @Override // see super class for documentation
    default void setNotIrreflexive(){
        getOntology().removeIrreflexiveObjectProperty( getInstance());
    }


    /**
     * The {@link ObjectProperty.Inverse} expression for a {@link Descriptor} whose ground is {@link OWLObjectProperty}.
     * <p>
     *     It specifies how to {@link #queryInverseObjectProperties()} and {@link #writeExpressionAxioms()} for the
     *     ObjectProperties inverse to the ground ObjectProperty (i.e.: {@link #getInstance()}).
     * </p>
     *
     * @param <D> the type of the {@link ObjectPropertyExpression} descriptor instantiated during
     *           {@link #buildInverseObjectProperty()} through {@link #getNewInverseObjectProperty(Object, Object)}.
     */
    interface Inverse<D extends ObjectPropertyExpression>
            extends ObjectProperty.Inverse<OWLReferences, OWLObjectProperty,D>,
            ObjectPropertyExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getInverseObjectProperties()}.add( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to add a new object property (given by name) in the {@link EntitySet} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addInverseObjectProperty( String propertyName){
            return getInverseObjectProperties().add( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getInverseObjectProperties()}.add( property)}
         * in order to add a new object property in the {@link EntitySet} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addInverseObjectProperty( OWLObjectProperty property){
            return getInverseObjectProperties().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getInverseObjectProperties()}.remove( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to remove an object property (given by name) from the {@link EntitySet} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeInverseObjectProperty( String propertyName){
            return getInverseObjectProperties().remove( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getInverseObjectProperties()}.remove( property)}
         * in order to remove an object property in the {@link EntitySet} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeInverseObjectProperty( OWLObjectProperty property){
            return getInverseObjectProperties().remove( property);
        }

        @Override
        DescriptorEntitySet.ObjectProperties getInverseObjectProperties();

        @Override // see super class for documentation
        default DescriptorEntitySet.ObjectProperties queryInverseObjectProperties(){
            DescriptorEntitySet.ObjectProperties set = new DescriptorEntitySet.ObjectProperties(getOntology().getInverseProperty(getInstance()));
            set.setSingleton( getInverseObjectProperties().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLObjectProperty> to = synchroniseInverseObjectPropertyToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (OWLObjectProperty a : to.getToAdd())
                    changes.add(getOntology().addObjectPropertyInverseOf(getInstance(), a));
                for (OWLObjectProperty r : to.getToRemove())
                    changes.add(getOntology().removeObjectPropertyInverseOf(getInstance(), r));
                return getChangingIntent(to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link ObjectProperty.Disjoint} expression for a {@link Descriptor} whose ground is {@link OWLObjectProperty}.
     * <p>
     *     It specifies how to {@link #queryDisjointObjectProperties()} and {@link #writeExpressionAxioms()} for the
     *     ObjectProperties disjoint to the ground ObjectProperty (i.e.: {@link OWLObjectProperty}).
     * </p>
     *
     * @param <D> the type of the {@link ObjectPropertyExpression} descriptor instantiated during
     *           {@link #buildDisjointObjectProperty()} through {@link #getNewDisjointObjectProperty(Object, Object)}.
     */
    interface Disjoint<D extends ObjectPropertyExpression>
            extends ObjectProperty.Disjoint<OWLReferences, OWLObjectProperty,D>,
            ObjectPropertyExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointObjectProperties()}.add( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to add a new object property (given by name) in the {@link EntitySet} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointObjectProperty( String propertyName){
            return getDisjointObjectProperties().add( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointObjectProperties()}.add( property)}
         * in order to add a new object property in the {@link EntitySet} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointObjectProperty( OWLObjectProperty property){
            return getDisjointObjectProperties().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointObjectProperties()}.remove( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to remove an object property (given by name) from the {@link EntitySet} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointObjectProperty( String propertyName){
            return getDisjointObjectProperties().remove( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointObjectProperties()}.remove( property)}
         * in order to remove an object property in the {@link EntitySet} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointObjectProperty( OWLObjectProperty property){
            return getDisjointObjectProperties().remove( property);
        }

        @Override
        DescriptorEntitySet.ObjectProperties getDisjointObjectProperties();

        @Override // see super class for documentation
        default DescriptorEntitySet.ObjectProperties queryDisjointObjectProperties(){
            DescriptorEntitySet.ObjectProperties set = new DescriptorEntitySet.ObjectProperties(getOntology().getDisjointObjectProperty(getInstance()));
            set.remove( getInstance());
            set.remove( getOntology().getOWLFactory().getOWLBottomObjectProperty());
            set.setSingleton( getDisjointObjectProperties().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try{
                EntitySet.SynchronisationIntent<OWLObjectProperty> to = synchroniseDisjointObjectPropertyToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLObjectProperty a : to.getToAdd()){
                    Set<OWLObjectProperty> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( a);
                    changes.add( getOntology().makeDisjointObjectProperties( s));
                }
                for( OWLObjectProperty r : to.getToRemove()){
                    Set<OWLObjectProperty> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( r);
                    changes.add( getOntology().removeDisjointObjectProperties( s));
                }
                return getChangingIntent( to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link ObjectProperty.Equivalent} expression for a {@link Descriptor} whose ground is {@link OWLObjectProperty}.
     * <p>
     *     It specifies how to {@link #queryEquivalentObjectProperties()} ()} and {@link #writeExpressionAxioms()} for the
     *     ObjectProperties equivalent to the ground ObjectProperty (i.e.: {@link OWLObjectProperty}).
     * </p>
     *
     * @param <D> the type of the {@link ObjectPropertyExpression} descriptor instantiated during
     *           {@link #buildEquivalentObjectProperty()} through {@link #getNewEquivalentObjectProperty(Object, Object)}.
     */
    interface Equivalent<D extends ObjectPropertyExpression>
            extends ObjectProperty.Equivalent<OWLReferences, OWLObjectProperty,D>,
            ObjectPropertyExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentObjectProperties()}.add( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to add a new object property (given by name) in the {@link EntitySet} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentObjectProperty( String propertyName){
            return getEquivalentObjectProperties().add( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentObjectProperties()}.add( property)}
         * in order to add a new object property in the {@link EntitySet} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentObjectProperty( OWLObjectProperty property){
            return getEquivalentObjectProperties().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentObjectProperties()}.remove( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to remove an object property (given by name) from the {@link EntitySet} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentObjectProperty( String propertyName){
            return getEquivalentObjectProperties().remove( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentObjectProperties()}.remove( property)}
         * in order to remove an object property in the {@link EntitySet} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentObjectProperty( OWLObjectProperty property){
            return getEquivalentObjectProperties().remove( property);
        }

        @Override
        DescriptorEntitySet.ObjectProperties getEquivalentObjectProperties();

        @Override // see super class for documentation
        default DescriptorEntitySet.ObjectProperties queryEquivalentObjectProperties(){
            DescriptorEntitySet.ObjectProperties set = new DescriptorEntitySet.ObjectProperties(getOntology().getEquivalentObjectProperty(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getEquivalentObjectProperties().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLObjectProperty> to = synchroniseEquivalentObjectPropertyToExpressionAxioms();
                if( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLObjectProperty a : to.getToAdd()){
                    Set<OWLObjectProperty> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( a);
                    changes.add( getOntology().makeEquivalentObjectProperties( s));
                }
                for( OWLObjectProperty r : to.getToRemove()){
                    Set<OWLObjectProperty> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( r);
                    changes.add( getOntology().removeEquivalentObjectProperties( s));
                }
                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link ObjectProperty.Sub} expression for a {@link Descriptor} whose ground is {@link OWLObjectProperty}.
     * <p>
     *     It specifies how to {@link #querySubObjectProperties()} and {@link #writeExpressionAxioms()} for the
     *     ObjectProperties subsumed by the ground ObjectProperty(i.e.: {@link OWLObjectProperty}).
     * </p>
     *
     * @param <D> the type of the {@link ObjectPropertyExpression} descriptor instantiated during
     *           {@link #buildSubObjectProperty()}  through {@link #getSubObjectProperties()}.
     */
    interface Sub<D extends ObjectPropertyExpression>
            extends ObjectProperty.Sub<OWLReferences, OWLObjectProperty,D>,
            ObjectPropertyExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getSubObjectProperties()}.add( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to add a new object property (given by name) in the {@link EntitySet} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSubObjectProperty( String propertyName){
            return getSubObjectProperties().add( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSubObjectProperties()}.add( property)}
         * in order to add a new object property in the {@link EntitySet} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSubObjectProperty( OWLObjectProperty property){
            return getSubObjectProperties().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getSubObjectProperties()}.remove( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to remove an object property (given by name) from the {@link EntitySet} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSubObjectProperty( String propertyName){
            return getSubObjectProperties().remove( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSubObjectProperties()}.remove( property)}
         * in order to remove an object property in the {@link EntitySet} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSubObjectProperty( OWLObjectProperty property){
            return getSubObjectProperties().remove( property);
        }

        @Override
        DescriptorEntitySet.ObjectProperties getSubObjectProperties();

        @Override // see super class for documentation
        default DescriptorEntitySet.ObjectProperties querySubObjectProperties(){
            DescriptorEntitySet.ObjectProperties set = new DescriptorEntitySet.ObjectProperties(getOntology().getSubObjectPropertyOf(getInstance()));
            set.setSingleton( getSubObjectProperties().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try{
                EntitySet.SynchronisationIntent<OWLObjectProperty> to = synchroniseSubObjectPropertyToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLObjectProperty a : to.getToAdd())
                    changes.add( getOntology().addSubObjectPropertyOf( getInstance(), a));
                for( OWLObjectProperty r : to.getToRemove())
                    changes.add( getOntology().removeSubObjectPropertyOf( getInstance(), r));
                return getChangingIntent( to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link ObjectProperty.Super} expression for a {@link Descriptor} whose ground is {@link OWLObjectProperty}.
     * <p>
     *     It specifies how to {@link #querySuperObjectProperties()} and {@link #writeExpressionAxioms()} for the
     *     ObjectProperties super over the ground ObjectProperty (i.e.: {@link OWLObjectProperty}).
     * </p>
     *
     * @param <D> the type of the {@link ObjectPropertyExpression} descriptor instantiated during
     *           {@link #buildSuperObjectProperty()}  through {@link #getSuperObjectProperties()}.
     */
    interface Super<D extends ObjectPropertyExpression>
            extends ObjectProperty.Super<OWLReferences, OWLObjectProperty, D>,
            ObjectPropertyExpression {


        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperObjectProperties()}.add( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to add a new object property (given by name) in the {@link EntitySet} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSuperObjectProperty( String propertyName){
            return getSuperObjectProperties().add( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperObjectProperties()}.add( property)}
         * in order to add a new object property in the {@link EntitySet} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSuperObjectProperty( OWLObjectProperty property){
            return getSuperObjectProperties().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperObjectProperties()}.remove( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to remove an object property (given by name) from the {@link EntitySet} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSuperObjectProperty( String propertyName){
            return getSuperObjectProperties().remove( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperObjectProperties()}.remove( property)}
         * in order to remove an object property in the {@link EntitySet} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSuperObjectProperty( OWLObjectProperty property){
            return getSuperObjectProperties().remove( property);
        }

        @Override
        DescriptorEntitySet.ObjectProperties getSuperObjectProperties();

        @Override // see super class for documentation
        default DescriptorEntitySet.ObjectProperties querySuperObjectProperties(){
            DescriptorEntitySet.ObjectProperties set = new DescriptorEntitySet.ObjectProperties(getOntology().getSuperObjectPropertyOf(getInstance()));
            set.setSingleton( getSuperObjectProperties().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try{
                EntitySet.SynchronisationIntent<OWLObjectProperty> to = synchroniseSuperObjectPropertyToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLObjectProperty a : to.getToAdd())
                    changes.add( getOntology().addSubObjectPropertyOf( a, getInstance()));
                for( OWLObjectProperty r : to.getToRemove())
                    changes.add( getOntology().removeSubObjectPropertyOf( r, getInstance()));
                return getChangingIntent( to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link ObjectProperty.Domain} expression for a {@link Descriptor} whose ground is {@link OWLObjectProperty}.
     * <p>
     *     It specifies how to {@link #queryObjectPropertyDomainConcepts()} and {@link #writeExpressionAxioms()} for the
     *     domain restriction of the ground ObjectProperty (i.e.: {@link SemanticRestriction}).
     * </p>
     */
    interface Domain
            extends ObjectProperty.Domain<OWLReferences, OWLObjectProperty, SemanticRestriction>,
            ObjectPropertyExpression {

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property domain.
         */
        default SemanticRestriction domainMinDataRestriction(String property, int cardinality, Class dataType){
            return domainMinDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property domain.
         */
        default SemanticRestriction domainMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return new ObjectDomainRestrictedOnMinData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMinDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().add( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()}
         * based on {@link #domainMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().add( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMinDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().remove( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().remove( domainMinDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property domain.
         */
        default SemanticRestriction domainMaxDataRestriction(String property, int cardinality, Class dataType){
            return domainMaxDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property domain.
         */
        default SemanticRestriction domainMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return new ObjectDomainRestrictedOnMaxData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMaxDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().add( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()}
         * based on {@link #domainMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().add( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMaxDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().remove( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().remove( domainMaxDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property domain.
         */
        default SemanticRestriction domainExactDataRestriction(String property, int cardinality, Class dataType){
            return domainExactDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property domain.
         */
        default SemanticRestriction domainExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return new ObjectDomainRestrictedOnExactData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainExactDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().add( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()}
         * based on {@link #domainExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().add( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainExactDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().remove( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyDomainConcepts().remove( domainExactDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property domain.
         */
        default SemanticRestriction domainSomeDataRestriction(String property, Class dataType){
            return domainSomeDataRestriction( getOWLDataProperty( property), dataType);
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property domain.
         */
        default SemanticRestriction domainSomeDataRestriction(OWLDataProperty property, Class dataType){
            return new ObjectDomainRestrictedOnSomeData( getInstance(), getOWLDataType( dataType), property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainSomeDataRestriction(String property, Class dataType){
            return getObjectPropertyDomainConcepts().add( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()}
         * based on {@link #domainSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getObjectPropertyDomainConcepts().add( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainSomeDataRestriction(String property, Class dataType){
            return getObjectPropertyDomainConcepts().remove( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getObjectPropertyDomainConcepts().remove( domainSomeDataRestriction( property, dataType));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property domain.
         */
        default SemanticRestriction domainOnlyDataRestriction(String property, Class dataType){
            return domainOnlyDataRestriction( getOWLDataProperty( property), dataType);
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property domain.
         */
        default SemanticRestriction domainOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return new ObjectDomainRestrictedOnAllData( getInstance(), getOWLDataType( dataType), property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainOnlyDataRestriction(String property, Class dataType){
            return getObjectPropertyDomainConcepts().add( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()}
         * based on {@link #domainOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getObjectPropertyDomainConcepts().add( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainOnlyDataRestriction(String property, Class dataType){
            return getObjectPropertyDomainConcepts().remove( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getObjectPropertyDomainConcepts().remove( domainOnlyDataRestriction( property, dataType));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainMinObjectRestriction(String property, int cardinality, String cl){
            return domainMinObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( cl));
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return new ObjectDomainRestrictedOnMinObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyDomainConcepts()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMinObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyDomainConcepts().add( domainMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()}
         * based on {@link #domainMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyDomainConcepts()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyDomainConcepts().add( domainMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyDomainConcepts()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMinObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyDomainConcepts().remove( domainMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyDomainConcepts()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyDomainConcepts().remove( domainMinObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainMaxObjectRestriction(String property, int cardinality, String cl){
            return domainMaxObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( cl));
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return new ObjectDomainRestrictedOnMaxObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMaxObjectRestriction(String, int, String)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMaxObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyDomainConcepts().add( domainMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()}
         * based on {@link #domainMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyDomainConcepts()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyDomainConcepts().add( domainMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMaxObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyDomainConcepts()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMaxObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyDomainConcepts().remove( domainMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyDomainConcepts()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyDomainConcepts().remove( domainMaxObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainExactObjectRestriction(String property, int cardinality, String cl){
            return domainExactObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( cl));
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return new ObjectDomainRestrictedOnExactObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyDomainConcepts()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainExactObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyDomainConcepts().add( domainExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()}
         * based on {@link #domainExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyDomainConcepts()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyDomainConcepts().add( domainExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyDomainConcepts()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainExactObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyDomainConcepts().remove( domainExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyDomainConcepts()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyDomainConcepts().remove( domainExactObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainSomeObjectRestriction(String property, String cl){
            return domainSomeObjectRestriction( getOWLObjectProperty( property), getOWLClass( cl));
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return new ObjectDomainRestrictedOnSomeObject( getInstance(), cl, property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainSomeObjectRestriction(String property, String cl){
            return getObjectPropertyDomainConcepts().add( domainSomeObjectRestriction( property, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()}
         * based on {@link #domainSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getObjectPropertyDomainConcepts().add( domainSomeObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainSomeObjectRestriction(String property, String cl){
            return getObjectPropertyDomainConcepts().remove( domainSomeObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getObjectPropertyDomainConcepts().remove( domainSomeObjectRestriction( property, cl));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainOnlyObjectRestriction(String property, String cl){
            return domainOnlyObjectRestriction( getOWLObjectProperty( property), getOWLClass( cl));
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return new ObjectDomainRestrictedOnAllObject( getInstance(), cl, property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainOnlyObjectRestriction(String property, String cl){
            return getObjectPropertyDomainConcepts().add( domainOnlyObjectRestriction( property, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()}
         * based on {@link #domainOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getObjectPropertyDomainConcepts().add( domainOnlyObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainOnlyObjectRestriction(String property, String cl){
            return getObjectPropertyDomainConcepts().remove( domainOnlyObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getObjectPropertyDomainConcepts().remove( domainOnlyObjectRestriction( property, cl));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which is represented by a class.
         * @param className the name of the range class of the restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainClassRestriction(String className){
            return domainClassRestriction( getOWLClass( className));
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which is represented by a class.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainClassRestriction( OWLClass cl){
            return new ObjectDomainRestrictedOnClass( getInstance(), cl);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet}
         * (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Domain}.
         */
        default boolean addDomainClassRestriction(String className){
            return getObjectPropertyDomainConcepts().add( domainClassRestriction( className));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet}
         * (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainClassRestriction(String)}.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Domain}.
         */
        default boolean addDomainClassRestriction(OWLClass cl){
            return getObjectPropertyDomainConcepts().add( domainClassRestriction( cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet}
         * (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainClassRestriction(String className){
            return getObjectPropertyDomainConcepts().remove( domainClassRestriction( className));
        }
        /**
         * Removes a restriction to the described {@link EntitySet}
         * (i.e.: {@link #getObjectPropertyDomainConcepts()})
         * based on {@link #domainClassRestriction(String)}.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainClassRestriction(OWLClass cl){
            return getObjectPropertyDomainConcepts().remove( domainClassRestriction( cl));
        }


        @Override
        DescriptorEntitySet.Restrictions getObjectPropertyDomainConcepts();

        @Override // see super class for documentation
        default DescriptorEntitySet.Restrictions queryObjectPropertyDomainConcepts(){
            Set< Set<ApplyingRestriction>> restrictionsSet = getOntology().getObjectDomainRestrictions(getInstance());
            Set<ApplyingRestriction> restrictions = new HashSet<>();
            for ( Set<ApplyingRestriction> r : restrictionsSet){
                restrictions = r;
                break;
            }
            if ( restrictionsSet.size() > 1)
                System.err.println( "WARNING: all the restriction that define an object domain should be contained in a single axiom." +
                        "Only axiom \'" + restrictions + "\' is considered in \'" + restrictionsSet + "\'");
            // remove self
            for ( ApplyingRestriction a : restrictions)
                if ( a.getRestrictionType().isRestrictionOnClass())
                    if ( a.getValue().equals( getInstance())){
                        restrictions.remove( a);
                        break;
                    }
            DescriptorEntitySet.Restrictions set = new DescriptorEntitySet.Restrictions( restrictions);
            set.setSingleton( getObjectPropertyDomainConcepts().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<SemanticRestriction> to = synchroniseDomainObjectPropertyToExpressionAxioms();
                List<OWLOntologyChange> changes = new ArrayList<>();
                // not optimised: it does not sync only the changes but it removes and re-add the axiom
                if ( ! to.getToAdd().isEmpty() || ! to.getToRemove().isEmpty()) {
                    for( Set<ApplyingRestriction> r : getOntology().getObjectDomainRestrictions( getInstance()))
                        changes.add(getOntology().removeRestrictionAxiom ( r)); // remove all
                    HashSet<SemanticRestriction> copy = new HashSet<>(to.getToAdd());
                    copy.addAll( to.getUnchanged());
                    changes.add(getOntology().addRestrictionAxiom( copy));
                }

                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }

        }
    }

    /**
     * The {@link ObjectProperty.Range} expression for a {@link Descriptor} whose ground is {@link OWLObjectProperty}.
     * <p>
     *     It specifies how to {@link #queryObjectPropertyRangeConcepts()} and {@link #writeExpressionAxioms()} for the
     *     range restriction of the ground ObjectProperty (i.e.: {@link SemanticRestriction}).
     */
    interface Range
            extends ObjectProperty.Range<OWLReferences, OWLObjectProperty, SemanticRestriction>,
            ObjectPropertyExpression {

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property range.
         */
        default SemanticRestriction rangeMinDataRestriction(String property, int cardinality, Class dataType){
            return rangeMinDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property range.
         */
        default SemanticRestriction rangeMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return new ObjectRangeRestrictedOnMinData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMinDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().add( rangeMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()}
         * based on {@link #rangeMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().add( rangeMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMinDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().remove( rangeMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().remove( rangeMinDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property range.
         */
        default SemanticRestriction rangeMaxDataRestriction(String property, int cardinality, Class dataType){
            return rangeMaxDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property range.
         */
        default SemanticRestriction rangeMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return new ObjectRangeRestrictedOnMaxData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMaxDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().add( rangeMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()}
         * based on {@link #rangeMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().add( rangeMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMaxDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().remove( rangeMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().remove( rangeMaxDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property range.
         */
        default SemanticRestriction rangeExactDataRestriction(String property, int cardinality, Class dataType){
            return rangeExactDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property range.
         */
        default SemanticRestriction rangeExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return new ObjectRangeRestrictedOnExactData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeExactDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().add( rangeExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()}
         * based on {@link #rangeExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().add( rangeExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeExactDataRestriction(String property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().remove( rangeExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getObjectPropertyRangeConcepts().remove( rangeExactDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property range.
         */
        default SemanticRestriction rangeSomeDataRestriction(String property, Class dataType){
            return rangeSomeDataRestriction( getOWLDataProperty( property), dataType);
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property range.
         */
        default SemanticRestriction rangeSomeDataRestriction(OWLDataProperty property, Class dataType){
            return new ObjectRangeRestrictedOnSomeData( getInstance(), getOWLDataType( dataType), property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeSomeDataRestriction(String property, Class dataType){
            return getObjectPropertyRangeConcepts().add( rangeSomeDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()}
         * based on {@link #rangeSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getObjectPropertyRangeConcepts().add( rangeSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeSomeDataRestriction(String property, Class dataType){
            return getObjectPropertyRangeConcepts().remove( rangeSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getObjectPropertyRangeConcepts().remove( rangeSomeDataRestriction( property, dataType));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property range.
         */
        default SemanticRestriction rangeOnlyDataRestriction(String property, Class dataType){
            return rangeOnlyDataRestriction( getOWLDataProperty( property), dataType);
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for object property range.
         */
        default SemanticRestriction rangeOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return new ObjectRangeRestrictedOnAllData( getInstance(), getOWLDataType( dataType), property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeOnlyDataRestriction(String property, Class dataType){
            return getObjectPropertyRangeConcepts().add( rangeOnlyDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()}
         * based on {@link #rangeOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getObjectPropertyRangeConcepts().add( rangeOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeOnlyDataRestriction(String property, Class dataType){
            return getObjectPropertyRangeConcepts().remove( rangeOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getObjectPropertyRangeConcepts().remove( rangeOnlyDataRestriction( property, dataType));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeMinObjectRestriction(String property, int cardinality, String cl){
            return rangeMinObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( cl));
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return new ObjectRangeRestrictedOnMinObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMinObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyRangeConcepts().add( rangeMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()}
         * based on {@link #rangeMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyRangeConcepts().add( rangeMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMinObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyRangeConcepts().remove( rangeMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyRangeConcepts().remove( rangeMinObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeMaxObjectRestriction(String property, int cardinality, String cl){
            return rangeMaxObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( cl));
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return new ObjectRangeRestrictedOnMaxObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMaxObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMaxObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyRangeConcepts().add( rangeMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()}
         * based on {@link #rangeMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyRangeConcepts().add( rangeMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMaxObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMaxObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyRangeConcepts().remove( rangeMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyRangeConcepts().remove( rangeMaxObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeExactObjectRestriction(String property, int cardinality, String cl){
            return rangeExactObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( cl));
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return new ObjectRangeRestrictedOnExactObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeExactObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyRangeConcepts().add( rangeExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()}
         * based on {@link #rangeExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyRangeConcepts().add( rangeExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeExactObjectRestriction(String property, int cardinality, String cl){
            return getObjectPropertyRangeConcepts().remove( rangeExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryObjectPropertyRangeConcepts()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getObjectPropertyRangeConcepts().remove( rangeExactObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeSomeObjectRestriction(String property, String cl){
            return rangeSomeObjectRestriction( getOWLObjectProperty( property), getOWLClass( cl));
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return new ObjectRangeRestrictedOnSomeObject( getInstance(), cl, property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeSomeObjectRestriction(String property, String cl){
            return getObjectPropertyRangeConcepts().add( rangeSomeObjectRestriction( property, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()}
         * based on {@link #rangeSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getObjectPropertyRangeConcepts().add( rangeSomeObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeSomeObjectRestriction(String property, String cl){
            return getObjectPropertyRangeConcepts().remove( rangeSomeObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getObjectPropertyRangeConcepts().remove( rangeSomeObjectRestriction( property, cl));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeOnlyObjectRestriction(String property, String cl){
            return rangeOnlyObjectRestriction( getOWLObjectProperty( property), getOWLClass( cl));
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return new ObjectRangeRestrictedOnAllObject( getInstance(), cl, property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeOnlyObjectRestriction(String property, String cl){
            return getObjectPropertyRangeConcepts().add( rangeOnlyObjectRestriction( property, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()}
         * based on {@link #rangeOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getObjectPropertyRangeConcepts().add( rangeOnlyObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeOnlyObjectRestriction(String property, String cl){
            return getObjectPropertyRangeConcepts().remove( rangeOnlyObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getObjectPropertyRangeConcepts().remove( rangeOnlyObjectRestriction( property, cl));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which is represented by a class.
         * @param className the name of the range class of the restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeClassRestriction(String className){
            return rangeClassRestriction( getOWLClass( className));
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which is represented by a class.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeClassRestriction( OWLClass cl){
            return new ObjectRangeRestrictedOnClass( getInstance(), cl);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet}
         * (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Range}.
         */
        default boolean addRangeClassRestriction(String className){
            return getObjectPropertyRangeConcepts().add( rangeClassRestriction( className));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet}
         * (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeClassRestriction(String)}.
         * @param className the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Range}.
         */
        default boolean addRangeClassRestriction(OWLClass className){
            return getObjectPropertyRangeConcepts().add( rangeClassRestriction( className));
        }
        /**
         * Removes a restriction to the described {@link EntitySet}
         * (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Range}.
         */
        default boolean removeRangeClassRestriction(String className){
            return getObjectPropertyRangeConcepts().remove( rangeClassRestriction( className));
        }
        /**
         * Removes a restriction to the described {@link EntitySet}
         * (i.e.: {@link #getObjectPropertyRangeConcepts()})
         * based on {@link #rangeClassRestriction(String)}.
         * @param className the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Range}.
         */
        default boolean removeRangeClassRestriction(OWLClass className){
            return getObjectPropertyRangeConcepts().remove( rangeClassRestriction( className));
        }

        @Override
        DescriptorEntitySet.Restrictions getObjectPropertyRangeConcepts();

        @Override // see super class for documentation
        default DescriptorEntitySet.Restrictions queryObjectPropertyRangeConcepts(){
            Set< Set<ApplyingRestriction>> restrictionsSet =  getOntology().getObjectRangeRestrictions(getInstance());
            Set<ApplyingRestriction> restrictions = new HashSet<>();
            for ( Set<ApplyingRestriction> r : restrictionsSet){
                restrictions = r;
                break;
            }
            if ( restrictionsSet.size() > 1)
                System.err.println( "WARNING: all the restriction that define an object range should be contained in a single axiom." +
                        "Only axiom \'" + restrictions + "\' is considered in \'" + restrictionsSet + "\'");
            // remove self
            for ( ApplyingRestriction a : restrictions)
                if ( a.getRestrictionType().isRestrictionOnClass())
                    if ( a.getValue().equals( getInstance())){
                        restrictions.remove( a);
                        break;
                    }
            DescriptorEntitySet.Restrictions set = new DescriptorEntitySet.Restrictions( restrictions);
            set.setSingleton( getObjectPropertyRangeConcepts().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<SemanticRestriction> to = synchroniseRangeObjectPropertyToExpressionAxioms();
                List<OWLOntologyChange> changes = new ArrayList<>();
                // not optimised: it does not sync only the changes but it removes and re-add the axiom
                if ( ! to.getToAdd().isEmpty() || ! to.getToRemove().isEmpty()) {
                    for( Set<ApplyingRestriction> r : getOntology().getObjectRangeRestrictions( getInstance()))
                        changes.add(getOntology().removeRestrictionAxiom ( r)); // remove all
                    HashSet<SemanticRestriction> copy = new HashSet<>(to.getToAdd());
                    copy.addAll( to.getUnchanged());
                    changes.add(getOntology().addRestrictionAxiom( copy));
                }

                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }





    }

}
