package it.emarolab.owloop.aMORDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.amor.owlInterface.SemanticRestriction.*;
import it.emarolab.owloop.core.ObjectProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyChange;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@link ObjectProperty} {@link Descriptor} implementation for {@link OWLObjectProperty}.
 * <p>
 *     This interface extends all the interfaces contained in {@link ObjectProperty}
 *     in order to fully define {@link Descriptor}s for {@link OWLObjectProperty} based on the
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a> API.
 *     In particular all of the sub interfaces specify how to
 *     {@code query} and {@link #writeSemantic()} specifically for OWL data properties.
 *     It contains several semantic descriptors that can be combined in any arbitrary combinations, since they
 *     rely on the same ground (i.e.: {@link ObjectInstance}).
 * </p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORObjectProperty <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public interface MORObjectProperty
        extends ObjectProperty<OWLReferences,OWLObjectProperty>,
                MORGrounding<OWLObjectProperty> {

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
     * The {@link ObjectProperty.Inverse} {@link Descriptor} implementation for {@link OWLObjectProperty}.
     * <p>
     *     It specify how to {@link #queryInverseObjectProperty()} and {@link #writeSemantic()} for the
     *     disjointed object properties (i.e.: {@link OWLObjectProperty}) from the one described
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORObjectProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORObjectProperty} descriptor instantiated during
     *           {@link #buildInverseObjectProperty()} through {@link #getNewInverseObjectProperty(Object, Object)}.
     */
    interface Inverse<D extends MORObjectProperty>
            extends ObjectProperty.Inverse<OWLReferences, OWLObjectProperty,D>,
            MORObjectProperty {

        /**
         * It is an helper that just calls:
         * {@code {@link #getInverseObjectProperty()}.add( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to add a new object property (given by name) in the {@link Axioms} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addInverseObjectProperty( String propertyName){
            return getInverseObjectProperty().add( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getInverseObjectProperty()}.add( property)}
         * in order to add a new object property in the {@link Axioms} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addInverseObjectProperty( OWLObjectProperty property){
            return getInverseObjectProperty().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getInverseObjectProperty()}.remove( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to remove an object property (given by name) from the {@link Axioms} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeInverseObjectProperty( String propertyName){
            return getInverseObjectProperty().remove( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getInverseObjectProperty()}.remove( property)}
         * in order to remove an object property in the {@link Axioms} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeInverseObjectProperty( OWLObjectProperty property){
            return getInverseObjectProperty().remove( property);
        }

        @Override
        MORAxioms.ObjectLinks getInverseObjectProperty();

        @Override // see super class for documentation
        default MORAxioms.ObjectLinks queryInverseObjectProperty(){
            MORAxioms.ObjectLinks set = new MORAxioms.ObjectLinks(getOntology().getInverseProperty(getInstance()));
            set.setSingleton( getInverseObjectProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<OWLObjectProperty> to = synchroniseInverseObjectPropertyToSemantic();
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
     * The {@link ObjectProperty.Disjoint} {@link Descriptor} implementation for {@link OWLObjectProperty}.
     * <p>
     *     It specify how to {@link #queryDisjointObjectProperty()} and {@link #writeSemantic()} for the
     *     disjointed object properties (i.e.: {@link OWLObjectProperty}) from the one described
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORObjectProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORObjectProperty} descriptor instantiated during
     *           {@link #buildDisjointObjectProperty()} through {@link #getNewDisjointObjectProperty(Object, Object)}.
     */
    interface Disjoint<D extends MORObjectProperty>
            extends ObjectProperty.Disjoint<OWLReferences, OWLObjectProperty,D>,
            MORObjectProperty {

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointObjectProperty()}.add( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to add a new object property (given by name) in the {@link Axioms} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointObjectProperty( String propertyName){
            return getDisjointObjectProperty().add( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointObjectProperty()}.add( property)}
         * in order to add a new object property in the {@link Axioms} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointObjectProperty( OWLObjectProperty property){
            return getDisjointObjectProperty().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointObjectProperty()}.remove( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to remove an object property (given by name) from the {@link Axioms} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointObjectProperty( String propertyName){
            return getDisjointObjectProperty().remove( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointObjectProperty()}.remove( property)}
         * in order to remove an object property in the {@link Axioms} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointObjectProperty( OWLObjectProperty property){
            return getDisjointObjectProperty().remove( property);
        }

        @Override
        MORAxioms.ObjectLinks getDisjointObjectProperty();

        @Override // see super class for documentation
        default MORAxioms.ObjectLinks queryDisjointObjectProperty(){
            MORAxioms.ObjectLinks set = new MORAxioms.ObjectLinks(getOntology().getDisjointObjectProperty(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getDisjointObjectProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try{
                Axioms.SynchronisationIntent<OWLObjectProperty> to = synchroniseDisjointObjectPropertyToSemantic();
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
     * The {@link ObjectProperty.Equivalent} {@link Descriptor} implementation for {@link OWLObjectProperty}.
     * <p>
     *     It specify how to {@link #queryEquivalentObjectProperty()} ()} and {@link #writeSemantic()} for the
     *     equivalent object properties (i.e.: {@link OWLObjectProperty}) from the one described
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORObjectProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORObjectProperty} descriptor instantiated during
     *           {@link #buildEquivalentObjectProperty()} through {@link #getNewEquivalentObjectProperty(Object, Object)}.
     */
    interface Equivalent<D extends MORObjectProperty>
            extends ObjectProperty.Equivalent<OWLReferences, OWLObjectProperty,D>,
            MORObjectProperty {

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentObjectProperty()}.add( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to add a new object property (given by name) in the {@link Axioms} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentObjectProperty( String propertyName){
            return getEquivalentObjectProperty().add( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentObjectProperty()}.add( property)}
         * in order to add a new object property in the {@link Axioms} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentObjectProperty( OWLObjectProperty property){
            return getEquivalentObjectProperty().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentObjectProperty()}.remove( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to remove an object property (given by name) from the {@link Axioms} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentObjectProperty( String propertyName){
            return getEquivalentObjectProperty().remove( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentObjectProperty()}.remove( property)}
         * in order to remove an object property in the {@link Axioms} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentObjectProperty( OWLObjectProperty property){
            return getEquivalentObjectProperty().remove( property);
        }

        @Override
        MORAxioms.ObjectLinks getEquivalentObjectProperty();

        @Override // see super class for documentation
        default MORAxioms.ObjectLinks queryEquivalentObjectProperty(){
            MORAxioms.ObjectLinks set = new MORAxioms.ObjectLinks(getOntology().getEquivalentObjectProperty(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getEquivalentObjectProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<OWLObjectProperty> to = synchroniseEquivalentObjectPropertyToSemantic();
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
     * The {@link ObjectProperty.Sub} {@link Descriptor} implementation for {@link OWLObjectProperty}.
     * <p>
     *     It specify how to {@link #querySubObjectProperty()} and {@link #writeSemantic()} for the
     *     sub object properties (i.e.: {@link OWLObjectProperty}) from the one described
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORObjectProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORObjectProperty} descriptor instantiated during
     *           {@link #buildSubObjectProperty()}  through {@link #getSubObjectProperty()}.
     */
    interface Sub<D extends MORObjectProperty>
            extends ObjectProperty.Sub<OWLReferences, OWLObjectProperty,D>,
            MORObjectProperty {

        /**
         * It is an helper that just calls:
         * {@code {@link #getSubObjectProperty()}.add( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to add a new object property (given by name) in the {@link Axioms} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSubObjectProperty( String propertyName){
            return getSubObjectProperty().add( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSubObjectProperty()}.add( property)}
         * in order to add a new object property in the {@link Axioms} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSubObjectProperty( OWLObjectProperty property){
            return getSubObjectProperty().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getSubObjectProperty()}.remove( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to remove an object property (given by name) from the {@link Axioms} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSubObjectProperty( String propertyName){
            return getSubObjectProperty().remove( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSubObjectProperty()}.remove( property)}
         * in order to remove an object property in the {@link Axioms} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSubObjectProperty( OWLObjectProperty property){
            return getSubObjectProperty().remove( property);
        }

        @Override
        MORAxioms.ObjectLinks getSubObjectProperty();

        @Override // see super class for documentation
        default MORAxioms.ObjectLinks querySubObjectProperty(){
            MORAxioms.ObjectLinks set = new MORAxioms.ObjectLinks(getOntology().getSubObjectPropertyOf(getInstance()));
            set.setSingleton( getSubObjectProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try{
                Axioms.SynchronisationIntent<OWLObjectProperty> to = synchroniseSubObjectPropertyToSemantic();
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
     * The {@link ObjectProperty.Super} {@link Descriptor} implementation for {@link OWLObjectProperty}.
     * <p>
     *     It specify how to {@link #querySuperObjectProperty()} and {@link #writeSemantic()} for the
     *     super object properties (i.e.: {@link OWLObjectProperty}) from the one described
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORObjectProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORObjectProperty} descriptor instantiated during
     *           {@link #buildSuperObjectProperty()}  through {@link #getSuperObjectProperty()}.
     */
    interface Super<D extends MORObjectProperty>
            extends ObjectProperty.Super<OWLReferences, OWLObjectProperty, D>,
            MORObjectProperty {


        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperObjectProperty()}.add( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to add a new object property (given by name) in the {@link Axioms} list.
         * @param propertyName the property name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSuperObjectProperty( String propertyName){
            return getSuperObjectProperty().add( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperObjectProperty()}.add( property)}
         * in order to add a new object property in the {@link Axioms} list.
         * @param property the property to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSuperObjectProperty( OWLObjectProperty property){
            return getSuperObjectProperty().add( property);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperObjectProperty()}.remove( {@link #getOntology()}.getOWLObjectProperty( propertyName))}
         * in order to remove an object property (given by name) from the {@link Axioms} list.
         * @param propertyName the property name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSuperObjectProperty( String propertyName){
            return getSuperObjectProperty().remove( getOntology().getOWLObjectProperty( propertyName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperObjectProperty()}.remove( property)}
         * in order to remove an object property in the {@link Axioms} list.
         * @param property the property to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSuperObjectProperty( OWLObjectProperty property){
            return getSuperObjectProperty().remove( property);
        }

        @Override
        MORAxioms.ObjectLinks getSuperObjectProperty();

        @Override // see super class for documentation
        default MORAxioms.ObjectLinks querySuperObjectProperty(){
            MORAxioms.ObjectLinks set = new MORAxioms.ObjectLinks(getOntology().getSuperObjectPropertyOf(getInstance()));
            set.setSingleton( getSuperObjectProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try{
                Axioms.SynchronisationIntent<OWLObjectProperty> to = synchroniseSuperObjectPropertyToSemantic();
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
     * The {@link ObjectProperty.Domain} {@link Descriptor} implementation for {@link OWLObjectProperty}.
     * <p>
     *     It specify how to {@link #queryDomainObjectProperty()} and {@link #writeSemantic()} for the
     *     domain restriction (i.e.: {@link SemanticRestriction}) of the one described
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORObjectProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    interface Domain
            extends ObjectProperty.Domain<OWLReferences, OWLObjectProperty, SemanticRestriction>,
            MORObjectProperty{

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMinDataRestriction(String property, int cardinality, Class dataType){
            return getDomainObjectProperty().add( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()}
         * based on {@link #domainMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainObjectProperty().add( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMinDataRestriction(String property, int cardinality, Class dataType){
            return getDomainObjectProperty().remove( domainMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainObjectProperty().remove( domainMinDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMaxDataRestriction(String property, int cardinality, Class dataType){
            return getDomainObjectProperty().add( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()}
         * based on {@link #domainMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainObjectProperty().add( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMaxDataRestriction(String property, int cardinality, Class dataType){
            return getDomainObjectProperty().remove( domainMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainObjectProperty().remove( domainMaxDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainExactDataRestriction(String property, int cardinality, Class dataType){
            return getDomainObjectProperty().add( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()}
         * based on {@link #domainExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainObjectProperty().add( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainExactDataRestriction(String property, int cardinality, Class dataType){
            return getDomainObjectProperty().remove( domainExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getDomainObjectProperty().remove( domainExactDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainSomeDataRestriction(String property, Class dataType){
            return getDomainObjectProperty().add( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()}
         * based on {@link #domainSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainObjectProperty().add( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainSomeDataRestriction(String property, Class dataType){
            return getDomainObjectProperty().remove( domainSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainObjectProperty().remove( domainSomeDataRestriction( property, dataType));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainOnlyDataRestriction(String property, Class dataType){
            return getDomainObjectProperty().add( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()}
         * based on {@link #domainOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainObjectProperty().add( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainOnlyDataRestriction(String property, Class dataType){
            return getDomainObjectProperty().remove( domainOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getDomainObjectProperty().remove( domainOnlyDataRestriction( property, dataType));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainObjectProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMinObjectRestriction(String property, int cardinality, String cl){
            return getDomainObjectProperty().add( domainMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()}
         * based on {@link #domainMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainObjectProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainObjectProperty().add( domainMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainObjectProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMinObjectRestriction(String property, int cardinality, String cl){
            return getDomainObjectProperty().remove( domainMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainObjectProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainObjectProperty().remove( domainMinObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMaxObjectRestriction(String, int, String)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMaxObjectRestriction(String property, int cardinality, String cl){
            return getDomainObjectProperty().add( domainMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()}
         * based on {@link #domainMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainObjectProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainObjectProperty().add( domainMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMaxObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainObjectProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMaxObjectRestriction(String property, int cardinality, String cl){
            return getDomainObjectProperty().remove( domainMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainObjectProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainObjectProperty().remove( domainMaxObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainObjectProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainExactObjectRestriction(String property, int cardinality, String cl){
            return getDomainObjectProperty().add( domainExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()}
         * based on {@link #domainExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainObjectProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainObjectProperty().add( domainExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainObjectProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainExactObjectRestriction(String property, int cardinality, String cl){
            return getDomainObjectProperty().remove( domainExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryDomainObjectProperty()},
         * since the reasoner infers also an {@link #domainClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getDomainObjectProperty().remove( domainExactObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainSomeObjectRestriction(String property, String cl){
            return getDomainObjectProperty().add( domainSomeObjectRestriction( property, cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()}
         * based on {@link #domainSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainObjectProperty().add( domainSomeObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainSomeObjectRestriction(String property, String cl){
            return getDomainObjectProperty().remove( domainSomeObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainObjectProperty().remove( domainSomeObjectRestriction( property, cl));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainOnlyObjectRestriction(String property, String cl){
            return getDomainObjectProperty().add( domainOnlyObjectRestriction( property, cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()}
         * based on {@link #domainOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Domain}.
         */
        default boolean addDomainOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainObjectProperty().add( domainOnlyObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainOnlyObjectRestriction(String property, String cl){
            return getDomainObjectProperty().remove( domainOnlyObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getDomainObjectProperty().remove( domainOnlyObjectRestriction( property, cl));
        }

        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
         * which is represented by a class.
         * @param className the name of the range class of the restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainClassRestriction(String className){
            return domainClassRestriction( getOWLClass( className));
        }
        /**
         * Creates a new object property domain restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
         * which is represented by a class.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for object property domain.
         */
        default SemanticRestriction domainClassRestriction( OWLClass cl){
            return new ObjectDomainRestrictedOnClass( getInstance(), cl);
        }
        /**
         * Adds a new restriction to the described {@link Axioms}
         * (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Domain}.
         */
        default boolean addDomainClassRestriction(String className){
            return getDomainObjectProperty().add( domainClassRestriction( className));
        }
        /**
         * Adds a new restriction to the described {@link Axioms}
         * (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainClassRestriction(String)}.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Domain}.
         */
        default boolean addDomainClassRestriction(OWLClass cl){
            return getDomainObjectProperty().add( domainClassRestriction( cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms}
         * (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainClassRestriction(String className){
            return getDomainObjectProperty().remove( domainClassRestriction( className));
        }
        /**
         * Removes a restriction to the described {@link Axioms}
         * (i.e.: {@link #getDomainObjectProperty()})
         * based on {@link #domainClassRestriction(String)}.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Domain}.
         */
        default boolean removeDomainClassRestriction(OWLClass cl){
            return getDomainObjectProperty().remove( domainClassRestriction( cl));
        }


        @Override
        MORAxioms.Restrictions getDomainObjectProperty();

        @Override // see super class for documentation
        default MORAxioms.Restrictions queryDomainObjectProperty(){
            MORAxioms.Restrictions set = new MORAxioms.Restrictions(getOntology().getDomainRestriction(getInstance()));
            set.setSingleton( getDomainObjectProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try{
                Axioms.SynchronisationIntent<SemanticRestriction> to = synchroniseDomainObjectPropertyToSemantic();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( SemanticRestriction a : to.getToAdd())
                    changes.add( getOntology().addRestriction( a));
                for( SemanticRestriction r : to.getToRemove())
                    changes.add( getOntology().removeRestriction( r));
                return getChangingIntent( to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link ObjectProperty.Range} {@link Descriptor} implementation for {@link OWLObjectProperty}.
     * <p>
     *     It specify how to {@link #queryRangeObjectProperty()} and {@link #writeSemantic()} for the
     *     range restriction (i.e.: {@link SemanticRestriction}) of the one described
     *     by this class (i.e.: {@link #getInstance()}).
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORObjectProperty <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    interface Range
            extends ObjectProperty.Range<OWLReferences, OWLObjectProperty, SemanticRestriction>,
            MORObjectProperty{

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMinDataRestriction(String property, int cardinality, Class dataType){
            return getRangeObjectProperty().add( rangeMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()}
         * based on {@link #rangeMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getRangeObjectProperty().add( rangeMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMinDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMinDataRestriction(String property, int cardinality, Class dataType){
            return getRangeObjectProperty().remove( rangeMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMinDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMinDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getRangeObjectProperty().remove( rangeMinDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMaxDataRestriction(String property, int cardinality, Class dataType){
            return getRangeObjectProperty().add( rangeMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()}
         * based on {@link #rangeMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getRangeObjectProperty().add( rangeMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMaxDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMaxDataRestriction(String property, int cardinality, Class dataType){
            return getRangeObjectProperty().remove( rangeMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMaxDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMaxDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getRangeObjectProperty().remove( rangeMaxDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeExactDataRestriction(String property, int cardinality, Class dataType){
            return getRangeObjectProperty().add( rangeExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()}
         * based on {@link #rangeExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getRangeObjectProperty().add( rangeExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeExactDataRestriction(String, int, Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeExactDataRestriction(String property, int cardinality, Class dataType){
            return getRangeObjectProperty().remove( rangeExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeExactDataRestriction(OWLDataProperty, int, Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeExactDataRestriction(OWLDataProperty property, int cardinality, Class dataType){
            return getRangeObjectProperty().remove( rangeExactDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeSomeDataRestriction(String property, Class dataType){
            return getRangeObjectProperty().add( rangeSomeDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()}
         * based on {@link #rangeSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getRangeObjectProperty().add( rangeSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeSomeDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeSomeDataRestriction(String property, Class dataType){
            return getRangeObjectProperty().remove( rangeSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeSomeDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeSomeDataRestriction(OWLDataProperty property, Class dataType){
            return getRangeObjectProperty().remove( rangeSomeDataRestriction( property, dataType));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeOnlyDataRestriction(String property, Class dataType){
            return getRangeObjectProperty().add( rangeOnlyDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()}
         * based on {@link #rangeOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getRangeObjectProperty().add( rangeOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeOnlyDataRestriction(String, Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeOnlyDataRestriction(String property, Class dataType){
            return getRangeObjectProperty().remove( rangeOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeOnlyDataRestriction(OWLDataProperty, Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeOnlyDataRestriction(OWLDataProperty property, Class dataType){
            return getRangeObjectProperty().remove( rangeOnlyDataRestriction( property, dataType));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMinObjectRestriction(String property, int cardinality, String cl){
            return getRangeObjectProperty().add( rangeMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()}
         * based on {@link #rangeMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getRangeObjectProperty().add( rangeMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMinObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMinObjectRestriction(String property, int cardinality, String cl){
            return getRangeObjectProperty().remove( rangeMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getRangeObjectProperty().remove( rangeMinObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMaxObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMaxObjectRestriction(String property, int cardinality, String cl){
            return getRangeObjectProperty().add( rangeMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()}
         * based on {@link #rangeMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getRangeObjectProperty().add( rangeMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMaxObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMaxObjectRestriction(String property, int cardinality, String cl){
            return getRangeObjectProperty().remove( rangeMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeMaxObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getRangeObjectProperty().remove( rangeMaxObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeExactObjectRestriction(String property, int cardinality, String cl){
            return getRangeObjectProperty().add( rangeExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()}
         * based on {@link #rangeExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getRangeObjectProperty().add( rangeExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeExactObjectRestriction(String, int, String)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeExactObjectRestriction(String property, int cardinality, String cl){
            return getRangeObjectProperty().remove( rangeExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * This method should be always synchronised with {@link #writeSemanticInconsistencySafe()}
         * to be perfectly aligned with the {@link #queryRangeObjectProperty()},
         * since the reasoner infers also an {@link #rangeClassRestriction(OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeExactObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return getRangeObjectProperty().remove( rangeExactObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeSomeObjectRestriction(String property, String cl){
            return getRangeObjectProperty().add( rangeSomeObjectRestriction( property, cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()}
         * based on {@link #rangeSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getRangeObjectProperty().add( rangeSomeObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeSomeObjectRestriction(String property, String cl){
            return getRangeObjectProperty().remove( rangeSomeObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeSomeObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getRangeObjectProperty().remove( rangeSomeObjectRestriction( property, cl));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * (to be in conjunction with the others in the specific {@link Axioms})
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
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeOnlyObjectRestriction(String property, String cl){
            return getRangeObjectProperty().add( rangeOnlyObjectRestriction( property, cl));
        }
        /**
         * Adds a new restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()}
         * based on {@link #rangeOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a new restriction has been added to the {@link ObjectProperty.Range}.
         */
        default boolean addRangeOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getRangeObjectProperty().add( rangeOnlyObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeOnlyObjectRestriction(String property, String cl){
            return getRangeObjectProperty().remove( rangeOnlyObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link Axioms} (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the class range restriction.
         * @return {@code true} if a restriction has been removed from the {@link ObjectProperty.Range}.
         */
        default boolean removeRangeOnlyObjectRestriction(OWLObjectProperty property, OWLClass cl){
            return getRangeObjectProperty().remove( rangeOnlyObjectRestriction( property, cl));
        }

        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
         * which is represented by a class.
         * @param className the name of the range class of the restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeClassRestriction(String className){
            return rangeClassRestriction( getOWLClass( className));
        }
        /**
         * Creates a new object property range restriction
         * (to be in conjunction with the others in the specific {@link Axioms})
         * which is represented by a class.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for object property range.
         */
        default SemanticRestriction rangeClassRestriction( OWLClass cl){
            return new ObjectRangeRestrictedOnClass( getInstance(), cl);
        }
        /**
         * Adds a new restriction to the described {@link Axioms}
         * (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Range}.
         */
        default boolean addRangeClassRestriction(String className){
            return getRangeObjectProperty().add( rangeClassRestriction( className));
        }
        /**
         * Adds a new restriction to the described {@link Axioms}
         * (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeClassRestriction(String)}.
         * @param className the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Range}.
         */
        default boolean addRangeClassRestriction(OWLClass className){
            return getRangeObjectProperty().add( rangeClassRestriction( className));
        }
        /**
         * Removes a restriction to the described {@link Axioms}
         * (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Range}.
         */
        default boolean removeRangeClassRestriction(String className){
            return getRangeObjectProperty().remove( rangeClassRestriction( className));
        }
        /**
         * Removes a restriction to the described {@link Axioms}
         * (i.e.: {@link #getRangeObjectProperty()})
         * based on {@link #rangeClassRestriction(String)}.
         * @param className the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the
         * {@link ObjectProperty.Range}.
         */
        default boolean removeRangeClassRestriction(OWLClass className){
            return getRangeObjectProperty().remove( rangeClassRestriction( className));
        }

        @Override
        MORAxioms.Restrictions getRangeObjectProperty();

        @Override // see super class for documentation
        default MORAxioms.Restrictions queryRangeObjectProperty(){
            MORAxioms.Restrictions set = new MORAxioms.Restrictions(getOntology().getRangeRestriction(getInstance()));
            set.setSingleton( getRangeObjectProperty().isSingleton());
            return set;
        }

        @Override // see super class for documentation
        default List<MappingIntent> writeSemantic(){
            try{
                Axioms.SynchronisationIntent<SemanticRestriction> to = synchroniseRangeObjectPropertyToSemantic();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( SemanticRestriction a : to.getToAdd())
                    changes.add( getOntology().addRestriction( a));
                for( SemanticRestriction r : to.getToRemove())
                    changes.add( getOntology().removeRestriction( r));
                return getChangingIntent( to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

}
