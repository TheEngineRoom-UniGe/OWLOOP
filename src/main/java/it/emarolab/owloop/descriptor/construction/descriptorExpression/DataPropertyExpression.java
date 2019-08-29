package it.emarolab.owloop.descriptor.construction.descriptorExpression;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.amor.owlInterface.SemanticRestriction.*;
import it.emarolab.owloop.core.DataProperty;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DataProperties;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Restrictions;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DescriptorGroundInterface;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This interface extends all the interfaces in {@link DataProperty}.
 * It allows to {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()}
 * specifically for OWL data properties.
 * It contains several expressions that can be combined in any arbitrary way as they
 * rely on the same ground ({@link DataGroundInstance}).
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
public interface DataPropertyExpression
        extends DataProperty<OWLReferences,OWLDataProperty>,
        DescriptorGroundInterface<OWLDataProperty> {

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
    default OWLDataProperty getGroundInstance() {
        return getGround().getGroundInstance();
    }


    @Override // see super class for documentation
    default void setFunctional(){
        getOntology().addFunctionalDataProperty( getInstance());
    }

    @Override // see super class for documentation
    default void setNotFunctional(){
        getOntology().removeFunctionalDataProperty( getInstance());
    }

    /**
     * The {@link DataProperty.Disjoint} expression for a {@link Descriptor} whose ground is {@link OWLDataProperty}.
     * <p>
     *     It specifies how to {@link #queryDisjointDataProperties()} and {@link #writeExpressionAxioms()} for the
     *     DataProperties disjoint to the ground DataProperty (i.e.: {@link OWLDataProperty}).
     * </p>
     *
     * @param <D> the type of the {@link DataPropertyExpression} descriptor instantiated during
     *           {@link #buildDisjointDataProperties()} through {@link #getNewDisjointDataProperty(Object, Object)}.
     */
    interface Disjoint<D extends DataPropertyExpression>
            extends DataProperty.Disjoint<OWLReferences, OWLDataProperty,D>,
            DataPropertyExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointDataProperties()}.add( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to add a new data property (given by name) in the {@link EntitySet} list.
         * @param dataPropertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointDataProperty( String dataPropertyName){
            return getDisjointDataProperties().add( getOntology().getOWLDataProperty( dataPropertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointDataProperties()}.add( property)}
         * in order to add a new data property in the {@link EntitySet} list.
         * @param instance the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointDataProperty( OWLDataProperty instance){
            return getDisjointDataProperties().add( instance);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointDataProperties()}.remove( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to remove a data property (given by name) from the {@link EntitySet} list.
         * @param dataPropertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointDataProperty( String dataPropertyName){
            return getDisjointDataProperties().remove( getOntology().getOWLDataProperty( dataPropertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointDataProperties()}.remove( property)}
         * in order to remove a data property in the {@link EntitySet} list.
         * @param instance the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointDataProperty( OWLDataProperty instance){
            return getDisjointDataProperties().remove( instance);
        }

        @Override
        DataProperties getDisjointDataProperties();

        @Override // see super class for documentation
        default DataProperties queryDisjointDataProperties(){
            DataProperties set = new DataProperties(getOntology().getDisjointDataProperty(getInstance()));
            set.remove( getInstance());
            set.remove( getOntology().getOWLFactory().getOWLBottomDataProperty());
            set.setSingleton( getDisjointDataProperties().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLDataProperty> to = synchroniseDisjointDataPropertiesToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLDataProperty a : to.getToAdd()){
                    Set<OWLDataProperty> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( a);
                    changes.add( getOntology().makeDisjointDataProperties( s));
                }
                for( OWLDataProperty r : to.getToRemove()){
                    Set<OWLDataProperty> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( r);
                    changes.add( getOntology().removeDisjointDataProperties( s));
                }
                return getChangingIntent(to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link DataProperty.Equivalent} expression for a {@link Descriptor} whose ground is {@link OWLDataProperty}.
     * <p>
     *     It specifies how to {@link #queryEquivalentDataProperties()} and {@link #writeExpressionAxioms()} for the
     *     DataProperties equivalent to the ground DataProperty (i.e.: {@link OWLDataProperty}).
     * </p>
     *
     * @param <D> the type of the {@link DataPropertyExpression} descriptor instantiated during
     *           {@link #buildEquivalentDataProperties()} through {@link #getNewEquivalentDataProperty(Object, Object)}.
     */
    interface Equivalent<D extends DataPropertyExpression>
            extends DataProperty.Equivalent<OWLReferences, OWLDataProperty,D>,
            DataPropertyExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentDataProperties()}.add( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to add a new data property (given by name) in the {@link EntitySet} list.
         * @param dataPropertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentDataProperty( String dataPropertyName){
            return getEquivalentDataProperties().add( getOntology().getOWLDataProperty( dataPropertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentDataProperties()}.add( property)}
         * in order to add a new data property in the {@link EntitySet} list.
         * @param instance the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentDataProperty( OWLDataProperty instance){
            return getEquivalentDataProperties().add( instance);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentDataProperties()}.remove( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to remove a data property (given by name) from the {@link EntitySet} list.
         * @param dataPropertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentDataProperty( String dataPropertyName){
            return getEquivalentDataProperties().remove( getOntology().getOWLDataProperty( dataPropertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentDataProperties()}.remove( property)}
         * in order to remove a data property in the {@link EntitySet} list.
         * @param instance the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentDataProperty( OWLDataProperty instance){
            return getEquivalentDataProperties().remove( instance);
        }

        @Override
        DataProperties getEquivalentDataProperties();

        @Override // see super class for documentation
        default DataProperties queryEquivalentDataProperties(){
            DataProperties set = new DataProperties(getOntology().getEquivalentDataProperty(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getEquivalentDataProperties().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLDataProperty> to = synchroniseEquivalentDataPropertiesToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLDataProperty a : to.getToAdd()){
                    Set<OWLDataProperty> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( a);
                    changes.add( getOntology().makeEquivalentDataProperties( s));
                }
                for( OWLDataProperty r : to.getToRemove()){
                    Set<OWLDataProperty> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( r);
                    changes.add( getOntology().removeEquivalentDataProperties( s));
                }
                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link DataProperty.Sub} expression for a {@link Descriptor} whose ground is {@link OWLDataProperty}.
     * <p>
     *     It specifies how to {@link #querySubDataProperties()} and {@link #writeExpressionAxioms()} for the
     *     DataProperties subsumed by the ground DataProperty (i.e.: {@link OWLDataProperty}).
     * </p>
     *
     * @param <D> the type of the {@link DataPropertyExpression} descriptor instantiated during
     *           {@link #buildSubDataProperties()}  through {@link #getNewSubDataProperty(Object, Object)}.
     */
    interface Sub<D extends DataPropertyExpression>
            extends DataProperty.Sub<OWLReferences, OWLDataProperty,D>,
            DataPropertyExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getSubDataProperties()}.add( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to add a new data property (given by name) in the {@link EntitySet} list.
         * @param dataPropertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSubDataProperty( String dataPropertyName){
            return getSubDataProperties().add( getOntology().getOWLDataProperty( dataPropertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSubDataProperties()}.add( property)}
         * in order to add a new data property in the {@link EntitySet} list.
         * @param instance the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSubDataProperty( OWLDataProperty instance){
            return getSubDataProperties().add( instance);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getSubDataProperties()}.remove( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to remove a data property (given by name) from the {@link EntitySet} list.
         * @param dataPropertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSubDataProperty( String dataPropertyName){
            return getSubDataProperties().remove( getOntology().getOWLDataProperty( dataPropertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSubDataProperties()}.remove( property)}
         * in order to remove a data property in the {@link EntitySet} list.
         * @param instance the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSubDataProperty( OWLDataProperty instance){
            return getSubDataProperties().remove( instance);
        }

        @Override // see super class for documentation
        default DataProperties querySubDataProperties(){
            DataProperties set = new DataProperties(getOntology().getSubDataPropertyOf(getInstance()));
            set.setSingleton( getSubDataProperties().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLDataProperty> to = synchroniseSubDataPropertiesToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (OWLDataProperty a : to.getToAdd())
                    changes.add(getOntology().addSubDataPropertyOf(getInstance(), a));
                for (OWLDataProperty r : to.getToRemove())
                    changes.add(getOntology().removeSubDataPropertyOf(getInstance(), r));
                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link DataProperty.Super} expression for a {@link Descriptor} whose ground is {@link OWLDataProperty}.
     * <p>
     *     It specifies how to {@link #querySuperDataProperties()} and {@link #writeExpressionAxioms()} for the
     *     DataProperties super over by the ground DataProperty (i.e.: {@link OWLDataProperty}).
     * </p>
     *
     * @param <D> the type of the {@link DataPropertyExpression} descriptor instantiated during
     *           {@link #buildSuperDataProperties()} through {@link #getNewSuperDataProperty(Object, Object)}.
     */
    interface Super<D extends DataPropertyExpression>
            extends DataProperty.Super<OWLReferences, OWLDataProperty,D>,
            DataPropertyExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperDataProperties()}.add( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to add a new data property (given by name) in the {@link EntitySet} list.
         * @param dataPropertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSuperDataProperty( String dataPropertyName){
            return getSuperDataProperties().add( getOntology().getOWLDataProperty( dataPropertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperDataProperties()}.add( property)}
         * in order to add a new data property in the {@link EntitySet} list.
         * @param instance the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSuperDataProperty( OWLDataProperty instance){
            return getSuperDataProperties().add( instance);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperDataProperties()}.remove( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to remove a data property (given by name) from the {@link EntitySet} list.
         * @param dataPropertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSuperDataProperty( String dataPropertyName){
            return getSuperDataProperties().remove( getOntology().getOWLDataProperty( dataPropertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperDataProperties()}.remove( property)}
         * in order to remove a data property in the {@link EntitySet} list.
         * @param instance the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSuperDataProperty( OWLDataProperty instance){
            return getSuperDataProperties().remove( instance);
        }

        @Override
        DataProperties getSuperDataProperties();

        @Override // see super class for documentation
        default DataProperties querySuperDataProperties(){
            DataProperties set = new DataProperties(getOntology().getSuperDataPropertyOf(getInstance()));
            set.setSingleton( getSuperDataProperties().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try{
                EntitySet.SynchronisationIntent<OWLDataProperty> to = synchroniseSuperDataPropertiesToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLDataProperty a : to.getToAdd())
                    changes.add( getOntology().addSubDataPropertyOf( a, getInstance()));
                for( OWLDataProperty r : to.getToRemove())
                    changes.add( getOntology().removeSubDataPropertyOf( r, getInstance()));
                return getChangingIntent( to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link DataProperty.Domain} expression for a {@link Descriptor} whose ground is {@link OWLDataProperty}.
     * <p>
     *     It specifies how to {@link #queryDomainRestrictions()} and {@link #writeExpressionAxioms()} for the
     *     domain restriction of the ground DataProperty (i.e.: {@link SemanticRestriction}).
     * </p>
     */
    interface Domain
            extends DataProperty.Domain<OWLReferences, OWLDataProperty, SemanticRestriction>,
            DataPropertyExpression {
        
        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for data property domain.
         */
        default SemanticRestriction domainMinDataRestriction(String property, int cardinality, Class dataType){
            return domainMinDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for data property domain.
         */
        default SemanticRestriction domainMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return new DataDomainRestrictedOnMinData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMinDataRestriction(String property, int cardinality, Class dataType){
            return getDomainRestrictions().add( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()}
         * based on {@link #domainMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainRestrictions().add( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMinDataRestriction(String property, int cardinality, Class dataType){
            return getDomainRestrictions().remove( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainRestrictions().remove( domainMinDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for data property domain.
         */
        default SemanticRestriction domainMaxDataRestriction(String property, int cardinality, Class dataType){
            return domainMaxDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for data property domain.
         */
        default SemanticRestriction domainMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return new DataDomainRestrictedOnMaxData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMaxDataRestriction(String property, int cardinality, Class dataType){
            return getDomainRestrictions().add( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()}
         * based on {@link #domainMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainRestrictions().add( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMaxDataRestriction(String property, int cardinality, Class dataType){
            return getDomainRestrictions().remove( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainRestrictions().remove( domainMaxDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for data property domain.
         */
        default SemanticRestriction domainExactDataRestriction(String property, int cardinality, Class dataType){
            return domainExactDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for data property domain.
         */
        default SemanticRestriction domainExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return new DataDomainRestrictedOnExactData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainExactDataRestriction(String property, int cardinality, Class dataType){
            return getDomainRestrictions().add( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()}
         * based on {@link #domainExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainRestrictions().add( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainExactDataRestriction(String property, int cardinality, Class dataType){
            return getDomainRestrictions().remove( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainRestrictions().remove( domainExactDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for data property domain.
         */
        default SemanticRestriction domainSomeDataRestriction(String property, Class dataType){
            return domainSomeDataRestriction( getOWLDataProperty( property), dataType);
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for data property domain.
         */
        default SemanticRestriction domainSomeDataRestriction(OWLDataProperty property, Class dataType){
            return new DataDomainRestrictedOnSomeData( getInstance(), getOWLDataType( dataType), property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainSomeDataRestriction(String property, Class dataType){
            return getDomainRestrictions().add( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()}
         * based on {@link #domainSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainRestrictions().add( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainSomeDataRestriction(String property, Class dataType){
            return getDomainRestrictions().remove( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainRestrictions().remove( domainSomeDataRestriction( property, dataType));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for data property domain.
         */
        default SemanticRestriction domainOnlyDataRestriction(String property, Class dataType){
            return domainOnlyDataRestriction( getOWLDataProperty( property), dataType);
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for data property domain.
         */
        default SemanticRestriction domainOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return new DataDomainRestrictedOnAllData( getInstance(), getOWLDataType( dataType), property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainOnlyDataRestriction(String property, Class dataType){
            return getDomainRestrictions().add( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()}
         * based on {@link #domainOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainRestrictions().add( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainOnlyDataRestriction(String property, Class dataType){
            return getDomainRestrictions().remove( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainRestrictions().remove( domainOnlyDataRestriction( property, dataType));
        }


        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param className the name of the class range restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainMinObjectRestriction(String property, int cardinality, String className){
            return domainMinObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( className));
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return new DataDomainRestrictedOnMinObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMinObjectRestriction(String property, int cardinality, String className){
            return getDomainRestrictions().add( domainMinObjectRestriction( property, cardinality, className));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()}
         * based on {@link #domainMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainRestrictions().add( domainMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMinObjectRestriction(String property, int cardinality, String className){
            return getDomainRestrictions().remove( domainMinObjectRestriction( property, cardinality, className));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainRestrictions().remove( domainMinObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param className the name of the class range restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainMaxObjectRestriction(String property, int cardinality, String className){
            return domainMaxObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( className));
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return new DataDomainRestrictedOnMaxObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMaxObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMaxObjectRestriction(String property, int cardinality, String className){
            return getDomainRestrictions().add( domainMaxObjectRestriction( property, cardinality, className));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()}
         * based on {@link #domainMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainRestrictions().add( domainMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMaxObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMaxObjectRestriction(String property, int cardinality, String className){
            return getDomainRestrictions().remove( domainMaxObjectRestriction( property, cardinality, className));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainRestrictions().remove( domainMaxObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainExactObjectRestriction(String property, int cardinality, String cl){
            return domainExactObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( cl));
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return new DataDomainRestrictedOnExactObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainExactObjectRestriction(String property, int cardinality, String className){
            return getDomainRestrictions().add( domainExactObjectRestriction( property, cardinality, className));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()}
         * based on {@link #domainExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainRestrictions().add( domainExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainExactObjectRestriction(String property, int cardinality, String className){
            return getDomainRestrictions().remove( domainExactObjectRestriction( property, cardinality, className));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeReadExpressionAxioms()}
         * to be perfectly aligned with the {@link #queryDomainRestrictions()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainRestrictions().remove( domainExactObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainSomeObjectRestriction(String property, String cl){
            return domainSomeObjectRestriction( getOWLObjectProperty( property), getOWLClass( cl));
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return new DataDomainRestrictedOnSomeObject( getInstance(), cl, property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainSomeObjectRestriction(String property, String cl){
            return getDomainRestrictions().add( domainSomeObjectRestriction(property,cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()}
         * based on {@link #domainSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainRestrictions().add( domainSomeObjectRestriction(property,cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainSomeObjectRestriction(String property, String cl){
            return getDomainRestrictions().remove( domainSomeObjectRestriction(property,cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainRestrictions().remove( domainSomeObjectRestriction(property,cl));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of object properties
         * in a specific ontological class range
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainOnlyObjectRestriction(String property, String cl){
            return domainOnlyObjectRestriction( getOWLObjectProperty( property), getOWLClass( cl));
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of object properties
         * in a specific ontological class range
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return a new object type restriction for data property domain.
         */        
        default SemanticRestriction domainOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return new DataDomainRestrictedOnAllObject( getInstance(), cl, property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainOnlyObjectRestriction(String property, String cl){
            return getDomainRestrictions().add( domainOnlyObjectRestriction(property,cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()}
         * based on {@link #domainOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainRestrictions().add( domainOnlyObjectRestriction(property,cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainOnlyObjectRestriction(String property, String cl){
            return getDomainRestrictions().remove( domainOnlyObjectRestriction(property,cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainRestrictions().remove( domainOnlyObjectRestriction(property,cl));
        }

        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which is represented by a class.
         * @param className the name of the range class of the restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainClassRestriction(String className){
            return domainClassRestriction( getOWLClass( className));
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which is represented by a class.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainClassRestriction( OWLClass cl){
            return new DataDomainRestrictedOnClass( getInstance(), cl);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet}
         * (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the 
         * {@link DataProperty.Domain}.
         */
        default boolean addDomainClassRestriction(String className){
            return getDomainRestrictions().add( domainClassRestriction( className));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet}
         * (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainClassRestriction(String)}.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the 
         * {@link DataProperty.Domain}.
         */
        default boolean addDomainClassRestriction(OWLClass cl){
            return getDomainRestrictions().add( domainClassRestriction( cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet}
         * (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the 
         * {@link DataProperty.Domain}.
         */
        default boolean removeDomainClassRestriction(String className){
            return getDomainRestrictions().remove( domainClassRestriction( className));
        }
        /**
         * Removes a restriction to the described {@link EntitySet}
         * (i.e.: {@link #getDomainRestrictions()})
         * based on {@link #domainClassRestriction(String)}.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the 
         * {@link DataProperty.Domain}.
         */
        default boolean removeDomainClassRestriction(OWLClass cl){
            return getDomainRestrictions().remove( domainClassRestriction( cl));
        }

        @Override
        Restrictions getDomainRestrictions();

        @Override // see super class for documentation
        default Restrictions queryDomainRestrictions(){
            Set< Set<ApplyingRestriction>> restrictionsSet = getOntology().getDataDomainRestrictions(getInstance());
            Set<ApplyingRestriction> restrictions = new HashSet<>();
            for ( Set<ApplyingRestriction> r : restrictionsSet){
                restrictions = r;
                break;
            }
            if ( restrictionsSet.size() > 1)
                System.err.println( "WARNING: all the restriction that define a data property domain should be contained in a single axiom." +
                        " Only axiom \'" + restrictions + "\' is considered in \'" + restrictionsSet + "\'");
            // remove self
            for ( ApplyingRestriction a : restrictions)
                if ( a.getRestrictionType().isRestrictionOnClass())
                    if ( a.getValue().equals( getInstance())){
                        restrictions.remove( a);
                        break;
                    }
            Restrictions set = new Restrictions( restrictions);
            set.setSingleton( getDomainRestrictions().isSingleton());
            return set;

        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<SemanticRestriction> to = synchroniseDomainDataPropertyToExpressionAxioms();
                List<OWLOntologyChange> changes = new ArrayList<>();
                // not optimised: it does not sync only the changes but it removes and re-add the axiom
                if ( ! to.getToAdd().isEmpty() || ! to.getToRemove().isEmpty()) {
                    for( Set<ApplyingRestriction> r : getOntology().getDataDomainRestrictions( getInstance()))
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
     * The {@link DataProperty.Range} expression for a {@link Descriptor} whose ground is {@link OWLDataProperty}.
     * <p>
     *     It specifies how to {@link #queryRangeRestrictions()} and {@link #writeExpressionAxioms()} for the
     *     range restriction of the ground DataProperty (i.e.: {@link SemanticRestriction}).
     * </p>
     */
    interface Range
            extends DataProperty.Range<OWLReferences, OWLDataProperty, SemanticRestriction>,
            DataPropertyExpression {

        /**
         * Creates a new data property range restriction 
         * (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an unique data type (see {@link #getOWLDataType(Class)}).
         * @param dataType the value of the restriction.
         * @return a new data type restriction for data property range.
         */
        default SemanticRestriction rangeDataRestriction(Class dataType){
            return new DataRangeRestricted( getInstance(), getOWLDataType( dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet}
         * (i.e.: {@link #getRangeRestrictions()})
         * based on {@link #rangeDataRestriction(Class)}.
         * @param dataType the value of the restriction.
         * @return {@code true} if a new restriction has been added to the 
         * {@link DataProperty.Range}.
         */
        default boolean addRangeDataRestriction(Class dataType){
            return getRangeRestrictions().add( rangeDataRestriction( dataType));
        }
        /**
         * Removes a new restriction to the described {@link EntitySet}
         * (i.e.: {@link #getRangeRestrictions()})
         * based on {@link #rangeDataRestriction(Class)}.
         * @param dataType the value of the restriction.
         * @return {@code true} if a new restriction has been removed from the 
         * {@link DataProperty.Range}.
         */
        default boolean removeRangeDataRestriction(Class dataType){
            return getRangeRestrictions().remove( rangeDataRestriction( dataType));
        }

        @Override
        Restrictions getRangeRestrictions();

        @Override // see super class for documentation
        default Restrictions queryRangeRestrictions(){
            Restrictions set = new Restrictions(getOntology().getDataRangeRestrictions(getInstance()));
            set.setSingleton( getRangeRestrictions().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeExpressionAxioms(){
            try {
                EntitySet.SynchronisationIntent<SemanticRestriction> to = synchroniseRangeDataPropertyToExpressionAxioms();
                List<OWLOntologyChange> changes = new ArrayList<>();
                // not optimised: it does not sync only the changes but it removes and re-add the axiom
                if ( ! to.getToAdd().isEmpty() || ! to.getToRemove().isEmpty()) {
                    changes.remove(getOntology().removeRestrictionAxiom(queryRangeRestrictions()));
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
