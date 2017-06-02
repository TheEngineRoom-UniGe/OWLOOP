package it.emarolab.owloop.aMORDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.amor.owlInterface.SemanticRestriction.*;
import it.emarolab.owloop.core.DataProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@link DataProperty} {@link Descriptor} implementation for {@link OWLDataProperty}.
 * <p>
 *     This interface extends all the interfaces contained in {@link DataProperty}
 *     in order to fully define {@link Descriptor}s for {@link OWLDataProperty} based on the
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a> API.
 *     In particular all of the sub interfaces specify how to
 *     {@code query} and {@link #writeSemantic()} specifically for OWL data properties.
 *     It contains several semantic descriptors that can be combined in any arbitrary combinations, since they
 *     rely on the same ground (i.e.: {@link DataInstance}).
 * </p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORDataProperty <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public interface MORDataProperty
        extends DataProperty<OWLReferences,OWLDataProperty>,
                MORGrounding<OWLDataProperty> {

    /**
     * It is used to easily access to the {@link MORGrounding} facilities.
     * @return the ontology in which {@code this} description is working on.
     * @deprecated use {@link #getGround()} instead.
     */
    @Override @Deprecated
    default OWLReferences getGroundOntology() {
        return getGround().getGroundOntology();
    }
    /**
     * It is used to easily access to the {@link MORGrounding} facilities.
     * @return the instance described by {@code this} implementation.
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
     * The {@link DataProperty.Disjoint} {@link Descriptor} implementation for {@link OWLDataProperty}.
     * <p>
     *     It specify how to {@link #queryDisjointDataProperty()} and {@link #writeSemantic()} for the
     *     disjointed data properties (i.e.: {@link OWLDataProperty}) from the one described 
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORDataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORDataProperty} descriptor instantiated during
     *           {@link #buildDisjointDataProperty()} through {@link #getNewDisjointDataProperty(Object, Object)}.
     */
    interface Disjoint<D extends MORDataProperty>
            extends DataProperty.Disjoint<OWLReferences, OWLDataProperty,D>,
                    MORDataProperty {

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointDataProperty()}.add( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to add a new data property (given by name) in the {@link Axioms} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointDataProperty( String propertyName){
            return getDisjointDataProperty().add( getOntology().getOWLDataProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointDataProperty()}.add( property)}
         * in order to add a new data property in the {@link Axioms} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointDataProperty( OWLDataProperty property){
            return getDisjointDataProperty().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointDataProperty()}.remove( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to remove a data property (given by name) from the {@link Axioms} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointDataProperty( String propertyName){
            return getDisjointDataProperty().remove( getOntology().getOWLDataProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointDataProperty()}.remove( property)}
         * in order to remove a data property in the {@link Axioms} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointDataProperty( OWLDataProperty property){
            return getDisjointDataProperty().remove( property);
        }

        @Override
        MORAxioms.DataLinks getDisjointDataProperty();

        @Override // see super class for documentation
        default MORAxioms.DataLinks queryDisjointDataProperty(){
            MORAxioms.DataLinks set = new MORAxioms.DataLinks(getOntology().getDisjointDataProperty(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getDisjointDataProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<OWLDataProperty> to = synchroniseDisjointDataPropertyToSemantic();
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
     * The {@link DataProperty.Equivalent} {@link Descriptor} implementation for {@link OWLDataProperty}.
     * <p>
     *     It specify how to {@link #queryEquivalentDataProperty()} and {@link #writeSemantic()} for the
     *     equivalent data properties (i.e.: {@link OWLDataProperty}) from the one described 
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORDataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORDataProperty} descriptor instantiated during
     *           {@link #buildEquivalentDataProperty()} through {@link #getNewEquivalentDataProperty(Object, Object)}.
     */
    interface Equivalent<D extends MORDataProperty>
            extends DataProperty.Equivalent<OWLReferences, OWLDataProperty,D>,
                    MORDataProperty{

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentDataProperty()}.add( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to add a new data property (given by name) in the {@link Axioms} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentDataProperty( String propertyName){
            return getEquivalentDataProperty().add( getOntology().getOWLDataProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentDataProperty()}.add( property)}
         * in order to add a new data property in the {@link Axioms} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentDataProperty( OWLDataProperty property){
            return getEquivalentDataProperty().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentDataProperty()}.remove( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to remove a data property (given by name) from the {@link Axioms} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentDataProperty( String propertyName){
            return getEquivalentDataProperty().remove( getOntology().getOWLDataProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentDataProperty()}.remove( property)}
         * in order to remove a data property in the {@link Axioms} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentDataProperty( OWLDataProperty property){
            return getEquivalentDataProperty().remove( property);
        }

        @Override
        MORAxioms.DataLinks getEquivalentDataProperty();

        @Override // see super class for documentation
        default MORAxioms.DataLinks queryEquivalentDataProperty(){
            MORAxioms.DataLinks set = new MORAxioms.DataLinks(getOntology().getEquivalentDataProperty(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getEquivalentDataProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<OWLDataProperty> to = synchroniseEquivalentDataPropertyToSemantic();
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
     * The {@link DataProperty.Sub} {@link Descriptor} implementation for {@link OWLDataProperty}.
     * <p>
     *     It specify how to {@link #querySubDataProperty()} and {@link #writeSemantic()} for the
     *     sub data properties (i.e.: {@link OWLDataProperty}) from the one described 
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORDataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORDataProperty} descriptor instantiated during
     *           {@link #buildSubDataProperty()}  through {@link #getNewSubDataProperty(Object, Object)}.
     */
    interface Sub<D extends MORDataProperty>
            extends DataProperty.Sub<OWLReferences, OWLDataProperty,D>,
                    MORDataProperty{

        /**
         * It is an helper that just calls:
         * {@code {@link #getSubDataProperty()}.add( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to add a new data property (given by name) in the {@link Axioms} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSubDataProperty( String propertyName){
            return getSubDataProperty().add( getOntology().getOWLDataProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSubDataProperty()}.add( property)}
         * in order to add a new data property in the {@link Axioms} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSubDataProperty( OWLDataProperty property){
            return getSubDataProperty().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getSubDataProperty()}.remove( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to remove a data property (given by name) from the {@link Axioms} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSubDataProperty( String propertyName){
            return getSubDataProperty().remove( getOntology().getOWLDataProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSubDataProperty()}.remove( property)}
         * in order to remove a data property in the {@link Axioms} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSubDataProperty( OWLDataProperty property){
            return getSubDataProperty().remove( property);
        }

        @Override
        MORAxioms.DataLinks getSubDataProperty();

        @Override // see super class for documentation
        default MORAxioms.DataLinks querySubDataProperty(){
            MORAxioms.DataLinks set = new MORAxioms.DataLinks(getOntology().getSubDataPropertyOf(getInstance()));
            set.setSingleton( getSubDataProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<OWLDataProperty> to = synchroniseSubDataPropertyToSemantic();
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
     * The {@link DataProperty.Super} {@link Descriptor} implementation for {@link OWLDataProperty}.
     * <p>
     *     It specify how to {@link #querySuperDataProperty()} and {@link #writeSemantic()} for the
     *     super data properties (i.e.: {@link OWLDataProperty}) from the one described 
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORDataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORDataProperty} descriptor instantiated during
     *           {@link #buildSuperDataProperty()} through {@link #getNewSuperDataProperty(Object, Object)}.
     */
    interface Super<D extends MORDataProperty>
            extends DataProperty.Super<OWLReferences, OWLDataProperty,D>,
                    MORDataProperty{

        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperDataProperty()}.add( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to add a new data property (given by name) in the {@link Axioms} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSuperDataProperty( String propertyName){
            return getSuperDataProperty().add( getOntology().getOWLDataProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperDataProperty()}.add( property)}
         * in order to add a new data property in the {@link Axioms} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSuperDataProperty( OWLDataProperty property){
            return getSuperDataProperty().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperDataProperty()}.remove( {@link #getOntology()}.getOWLDataProperty( propertyName))}
         * in order to remove a data property (given by name) from the {@link Axioms} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSuperDataProperty( String propertyName){
            return getSuperDataProperty().remove( getOntology().getOWLDataProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperDataProperty()}.remove( property)}
         * in order to remove a data property in the {@link Axioms} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSuperDataProperty( OWLDataProperty property){
            return getSuperDataProperty().remove( property);
        }

        @Override
        MORAxioms.DataLinks getSuperDataProperty();

        @Override // see super class for documentation
        default MORAxioms.DataLinks querySuperDataProperty(){
            MORAxioms.DataLinks set = new MORAxioms.DataLinks(getOntology().getSuperDataPropertyOf(getInstance()));
            set.setSingleton( getSuperDataProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try{
                Axioms.SynchronisationIntent<OWLDataProperty> to = synchroniseSuperDataPropertyToSemantic();
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
     * The {@link DataProperty.Domain} {@link Descriptor} implementation for {@link OWLDataProperty}.
     * <p>
     *     It specify how to {@link #queryDomainDataProperty()} and {@link #writeSemantic()} for the
     *     domain restriction (i.e.: {@link SemanticRestriction}) of the one described 
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORDataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    interface Domain
            extends DataProperty.Domain<OWLReferences, OWLDataProperty, SemanticRestriction>,
            MORDataProperty{
        
        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMinDataRestriction(String property, int cardinality, Class dataType){
            return getDomainDataProperty().add( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()}
         * based on {@link #domainMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainDataProperty().add( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMinDataRestriction(String property, int cardinality, Class dataType){
            return getDomainDataProperty().remove( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainDataProperty().remove( domainMinDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMaxDataRestriction(String property, int cardinality, Class dataType){
            return getDomainDataProperty().add( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()}
         * based on {@link #domainMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainDataProperty().add( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMaxDataRestriction(String property, int cardinality, Class dataType){
            return getDomainDataProperty().remove( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainDataProperty().remove( domainMaxDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainExactDataRestriction(String property, int cardinality, Class dataType){
            return getDomainDataProperty().add( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()}
         * based on {@link #domainExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainDataProperty().add( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainExactDataRestriction(String property, int cardinality, Class dataType){
            return getDomainDataProperty().remove( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainDataProperty().remove( domainExactDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainSomeDataRestriction(String property, Class dataType){
            return getDomainDataProperty().add( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()}
         * based on {@link #domainSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainDataProperty().add( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainSomeDataRestriction(String property, Class dataType){
            return getDomainDataProperty().remove( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainDataProperty().remove( domainSomeDataRestriction( property, dataType));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainOnlyDataRestriction(String property, Class dataType){
            return getDomainDataProperty().add( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()}
         * based on {@link #domainOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainDataProperty().add( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainOnlyDataRestriction(String property, Class dataType){
            return getDomainDataProperty().remove( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainDataProperty().remove( domainOnlyDataRestriction( property, dataType));
        }


        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMinObjectRestriction(String property, int cardinality, String className){
            return getDomainDataProperty().add( domainMinObjectRestriction( property, cardinality, className));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()}
         * based on {@link #domainMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainDataProperty().add( domainMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMinObjectRestriction(String property, int cardinality, String className){
            return getDomainDataProperty().remove( domainMinObjectRestriction( property, cardinality, className));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainDataProperty().remove( domainMinObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMaxObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMaxObjectRestriction(String property, int cardinality, String className){
            return getDomainDataProperty().add( domainMaxObjectRestriction( property, cardinality, className));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()}
         * based on {@link #domainMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainDataProperty().add( domainMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMaxObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMaxObjectRestriction(String property, int cardinality, String className){
            return getDomainDataProperty().remove( domainMaxObjectRestriction( property, cardinality, className));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainDataProperty().remove( domainMaxObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainExactObjectRestriction(String property, int cardinality, String className){
            return getDomainDataProperty().add( domainExactObjectRestriction( property, cardinality, className));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()}
         * based on {@link #domainExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainDataProperty().add( domainExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param className the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainExactObjectRestriction(String property, int cardinality, String className){
            return getDomainDataProperty().remove( domainExactObjectRestriction( property, cardinality, className));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainDataProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainDataProperty().remove( domainExactObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainSomeObjectRestriction(String property, String cl){
            return getDomainDataProperty().add( domainSomeObjectRestriction(property,cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()}
         * based on {@link #domainSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainDataProperty().add( domainSomeObjectRestriction(property,cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainSomeObjectRestriction(String property, String cl){
            return getDomainDataProperty().remove( domainSomeObjectRestriction(property,cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainDataProperty().remove( domainSomeObjectRestriction(property,cl));
        }

        /**
         * Creates a new data property domain restriction 
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainOnlyObjectRestriction(String property, String cl){
            return getDomainDataProperty().add( domainOnlyObjectRestriction(property,cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()}
         * based on {@link #domainOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link DataProperty.Domain}.
         */
        default boolean addDomainOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainDataProperty().add( domainOnlyObjectRestriction(property,cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainOnlyObjectRestriction(String property, String cl){
            return getDomainDataProperty().remove( domainOnlyObjectRestriction(property,cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link DataProperty.Domain}.
         */
        default boolean removeDomainOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainDataProperty().remove( domainOnlyObjectRestriction(property,cl));
        }

        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
         * which is represented by a class.
         * @param className the name of the range class of the restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainClassRestriction(String className){
            return domainClassRestriction( getOWLClass( className));
        }
        /**
         * Creates a new data property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
         * which is represented by a class.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for data property domain.
         */
        default SemanticRestriction domainClassRestriction( OWLClass cl){
            return new DataDomainRestrictedOnClass( getInstance(), cl);
        }
        /**
         * Adds a new restriction to the described {@link Axioms} 
         * (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the 
         * {@link DataProperty.Domain}.
         */
        default boolean addDomainClassRestriction(String className){
            return getDomainDataProperty().add( domainClassRestriction( className));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} 
         * (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainClassRestriction(String)}.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the 
         * {@link DataProperty.Domain}.
         */
        default boolean addDomainClassRestriction(OWLClass cl){
            return getDomainDataProperty().add( domainClassRestriction( cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} 
         * (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the 
         * {@link DataProperty.Domain}.
         */
        default boolean removeDomainClassRestriction(String className){
            return getDomainDataProperty().remove( domainClassRestriction( className));
        }
        /**
         * Removes a restriction to the described {@link Axioms} 
         * (i.e.: {@link #getDomainDataProperty()})
         * based on {@link #domainClassRestriction(String)}.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the 
         * {@link DataProperty.Domain}.
         */
        default boolean removeDomainClassRestriction(OWLClass cl){
            return getDomainDataProperty().remove( domainClassRestriction( cl));
        }

        @Override
        MORAxioms.Restrictions getDomainDataProperty();

        @Override // see super class for documentation
        default MORAxioms.Restrictions queryDomainDataProperty(){
            MORAxioms.Restrictions set = new MORAxioms.Restrictions(getOntology().getDomainRestriction(getInstance()));
            set.setSingleton( getDomainDataProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<SemanticRestriction> to = synchroniseDomainDataPropertyToSemantic();
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (SemanticRestriction a : to.getToAdd())
                    changes.add(getOntology().addRestriction(a));
                for (SemanticRestriction r : to.getToRemove())
                    changes.add(getOntology().removeRestriction(r));
                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link DataProperty.Range} {@link Descriptor} implementation for {@link OWLDataProperty}.
     * <p>
     *     It specify how to {@link #queryRangeDataProperty()} and {@link #writeSemantic()} for the
     *     range restriction (i.e.: {@link SemanticRestriction}) of the one described 
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORDataProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    interface Range
            extends DataProperty.Range<OWLReferences, OWLDataProperty, SemanticRestriction>,
            MORDataProperty{

        /**
         * Creates a new data property range restriction 
         * (to be in conjunction with the others in the specific {@link Axioms})
         * which restricts over an unique data type (see {@link #getOWLDataType(Class)}).
         * @param dataType the value of the restriction.
         * @return a new data type restriction for data property range.
         */
        default SemanticRestriction rangeDataRestriction(Class dataType){
            return new DataRangeRestricted( getInstance(), getOWLDataType( dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} 
         * (i.e.: {@link #getRangeDataProperty()})
         * based on {@link #rangeDataRestriction(Class)}.
         * @param dataType the value of the restriction.
         * @return {@code true} if a new restriction has been added to the 
         * {@link DataProperty.Range}.
         */
        default boolean addRangeDataRestriction(Class dataType){
            return getRangeDataProperty().add( rangeDataRestriction( dataType));
        }
        /**
         * Removes a new restriction to the described {@link Axioms}
         * (i.e.: {@link #getRangeDataProperty()})
         * based on {@link #rangeDataRestriction(Class)}.
         * @param dataType the value of the restriction.
         * @return {@code true} if a new restriction has been removed from the 
         * {@link DataProperty.Range}.
         */
        default boolean removeRangeDataRestriction(Class dataType){
            return getRangeDataProperty().remove( rangeDataRestriction( dataType));
        }

        @Override
        MORAxioms.Restrictions getRangeDataProperty();

        @Override // see super class for documentation
        default MORAxioms.Restrictions queryRangeDataProperty(){
            MORAxioms.Restrictions set = new MORAxioms.Restrictions(getOntology().getRangeRestriction(getInstance()));
            set.setSingleton( getRangeDataProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<SemanticRestriction> to = synchroniseRangeDataPropertyToSemantic();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (SemanticRestriction a : to.getToAdd())
                    changes.add(getOntology().addRestriction(a));
                for (SemanticRestriction r : to.getToRemove())
                    changes.add(getOntology().removeRestriction(r));
                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

}
