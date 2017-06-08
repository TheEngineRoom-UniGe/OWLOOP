package it.emarolab.owloop.aMORDescriptor;

import it.emarolab.amor.owlInterface.DataPropertyRelations;
import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.ObjectPropertyRelations;
import it.emarolab.owloop.core.Individual;
import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@link Individual} {@link Descriptor} implementation for {@link OWLNamedIndividual}.
 * <p>
 *     This interface extends all the interfaces contained in {@link Individual}
 *     in order to fully define {@link Descriptor}s for {@link OWLNamedIndividual} based on the
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a> API.
 *     In particular all of the sub interfaces specify how to
 *     {@code query} and {@link #writeSemantic()} specifically for OWL individuals.
 *     It contains several semantic descriptors that can be combined in any arbitrary combinations, since they
 *     rely on the same ground (i.e.: {@link IndividualInstance}).
 * </p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORIndividual <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public interface MORIndividual
        extends Individual<OWLReferences,OWLNamedIndividual>,
                MORGrounding<OWLNamedIndividual> {

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
    @Override @Deprecated // used internally to simplify syntax use getOntology() instead
    default OWLNamedIndividual getGroundInstance() {
        return getGround().getGroundInstance();
    }



    /**
     * The {@link Individual.Type} {@link Descriptor} implementation for {@link OWLNamedIndividual}.
     * <p>
     *     It specify how to {@link #queryTypeIndividual()} and {@link #writeSemantic()} for the
     *     types (i.e.: {@link OWLClass}) in which the described individual (i.e.: {@link #getInstance()})
     *     in belonging to.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORIndividual <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORConcept} descriptors instantiated during
     *           {@link #buildTypeIndividual()} through {@link #getNewTypeIndividual(Object, Object)}.
     */
    interface Type<D extends MORConcept>
            extends Individual.Type<OWLReferences, OWLNamedIndividual, OWLClass,D>,
                    MORIndividual{

        /**
         * It is an helper that just calls:
         * {@code {@link #getTypeIndividual()}.add( {@link #getOntology()}.getOWLClass( className))}
         * in order to add a new class (given by name) in the {@link Axioms} list.
         * @param className the class name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addTypeIndividual(String className){
            return getTypeIndividual().add( getOntology().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getTypeIndividual()}.add( cl)}
         * in order to add a new class in the {@link Axioms} list.
         * @param cl the class to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addTypeIndividual(OWLClass cl){
            return getTypeIndividual().add( cl);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getTypeIndividual()}.remove( {@link #getOntology()}.getOWLClass( className))}
         * in order to remove a class (given by name) from the {@link Axioms} list.
         * @param className the class name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeTypeIndividual(String className){
            return getTypeIndividual().remove( getOntology().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getTypeIndividual()}.remove( individual)}
         * in order to remove a class in the {@link Axioms} list.
         * @param cl the class to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeTypeIndividual(OWLClass cl){
            return getTypeIndividual().remove( cl);
        }

        @Override
        MORAxioms.Concepts getTypeIndividual();

        @Override // see super classes for documentation
        default MORAxioms.Concepts queryTypeIndividual(){
            MORAxioms.Concepts set = new MORAxioms.Concepts(getOntology().getIndividualClasses(getInstance()));
            set.setSingleton( getTypeIndividual().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<OWLClass> to = synchroniseTypeIndividualToSemantic();
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
     * The {@link Individual.Disjoint} {@link Descriptor} implementation for {@link OWLNamedIndividual}.
     * <p>
     *     It specify how to {@link #queryDisjointIndividual()} and {@link #writeSemantic()} for the
     *     disjoint individuals of {@link #getInstance()}.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORIndividual <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORIndividual} descriptors instantiated during
     *           {@link #buildDisjointIndividual()}  through {@link #getNewDisjointIndividual(Object, Object)}.
     */
    interface Disjoint<D extends MORIndividual>
            extends Individual.Disjoint<OWLReferences, OWLNamedIndividual,D>,
                    MORIndividual {

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointIndividual()}.add( {@link #getOntology()}.getOWLIndividual( individualName))}
         * in order to add a new individual (given by name) in the {@link Axioms} list.
         * @param individualName the individual name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointIndividual( String individualName){
            return getDisjointIndividual().add( getOntology().getOWLIndividual( individualName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointIndividual()}.add( individual)}
         * in order to add a new individual in the {@link Axioms} list.
         * @param individual the individual to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointIndividual( OWLNamedIndividual individual){
            return getDisjointIndividual().add( individual);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointIndividual()}.remove( {@link #getOntology()}.getOWLIndividual( individualName))}
         * in order to remove an individual (given by name) from the {@link Axioms} list.
         * @param individualName the individual name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointIndividual( String individualName){
            return getDisjointIndividual().remove( getOntology().getOWLIndividual( individualName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointIndividual()}.remove( property)}
         * in order to remove a class in the {@link Axioms} list.
         * @param individual the individual to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointIndividual( OWLNamedIndividual individual){
            return getDisjointIndividual().remove( individual);
        }

        @Override
        MORAxioms.Individuals getDisjointIndividual();

        @Override // see super classes for documentation
        default MORAxioms.Individuals queryDisjointIndividual(){
            MORAxioms.Individuals set = new MORAxioms.Individuals(getOntology().getDisjointIndividuals(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getDisjointIndividual().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<OWLNamedIndividual> to = synchroniseDisjointIndividualToSemantic();
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
     * The {@link Individual.Equivalent} {@link Descriptor} implementation for {@link OWLNamedIndividual}.
     * <p>
     *     It specify how to {@link #queryEquivalentIndividual()} and {@link #writeSemantic()} for the
     *     equivalent individuals of {@link #getInstance()}.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORIndividual <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORIndividual} descriptors instantiated during
     *           {@link #buildEquivalentIndividual()} through {@link #getNewEquivalentIndividual(Object, Object)}.
     */
    interface Equivalent<D extends MORIndividual>
            extends Individual.Equivalent<OWLReferences, OWLNamedIndividual,D>,
                    MORIndividual{

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentIndividual()}.add( {@link #getOntology()}.getOWLIndividual( individualName))}
         * in order to add a new individual (given by name) in the {@link Axioms} list.
         * @param individualName the individual name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentIndividual( String individualName){
            return getEquivalentIndividual().add( getOntology().getOWLIndividual( individualName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentIndividual()}.add( individual)}
         * in order to add a new individual in the {@link Axioms} list.
         * @param individual the individual to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentIndividual( OWLNamedIndividual individual){
            return getEquivalentIndividual().add( individual);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentIndividual()}.remove( {@link #getOntology()}.getOWLIndividual( individualName))}
         * in order to remove an individual (given by name) from the {@link Axioms} list.
         * @param individualName the individual name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentIndividual( String individualName){
            return getEquivalentIndividual().remove( getOntology().getOWLIndividual( individualName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentIndividual()}.remove( individual)}
         * in order to remove a class in the {@link Axioms} list.
         * @param individual the individual to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentIndividual( OWLNamedIndividual individual){
            return getEquivalentIndividual().remove( individual);
        }

        @Override
        MORAxioms.Individuals getEquivalentIndividual();

        @Override // see super classes for documentation
        default MORAxioms.Individuals queryEquivalentIndividual(){
            MORAxioms.Individuals set = new MORAxioms.Individuals(getOntology().getEquivalentIndividuals(getInstance()));
            set.remove( getInstance());
            set.setSingleton( getEquivalentIndividual().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<OWLNamedIndividual> to = synchroniseEquivalentIndividualToSemantic();
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
     * The {@link Individual.DataLink} {@link Descriptor} implementation for {@link OWLNamedIndividual}.
     * <p>
     *     It specify how to {@link #queryDataIndividual()} and {@link #writeSemantic()} for the
     *     data properties applied to {@link #getInstance()} (i.e.: {@code this individual}).
     *     It also implements common function to populate the {@link SemanticAxioms}
     *     (of type {@link MORAxioms.DataSemantic}) that specify the data properties of this individual
     *     that are synchronised with this {@link Descriptor}. For efficiency purposes,
     *     this descriptor does not map all the property of an individual but only the one which
     *     {@code semantic} have been initialised in the {@link SemanticAxiom}.
     *     On the other hand, if the set of {@link SemanticAxioms} is leaved empty during
     *     {@link #readSemantic()}, it maps all the object properties applied to the described
     *     individual
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORIndividual <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORDataProperty} descriptors instantiated during
     *           {@link #buildDataIndividual()} through {@link #getNewDataIndividual(SemanticAxiom, Object)}.
     */
    interface DataLink<D extends MORDataProperty>
            extends Individual.DataLink<OWLReferences, OWLNamedIndividual, MORAxioms.DataSemantic, OWLDataProperty, D>,
                    MORIndividual{

        /**
         * Remove the {@link SemanticAxiom} of the given semantic ({@link MORAxioms.DataSemantic#getSemantic()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readSemantic()} and {@link #writeSemantic()}.
         * @param property a data property name contained in an element of {@link #getDataSemantics()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( String property){
            return removeData( getOWLDataProperty( property));
        }
        /**
         * Remove the {@link SemanticAxiom} of the given semantic ({@link MORAxioms.DataSemantic#getSemantic()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readSemantic()} and {@link #writeSemantic()}.
         * @param property a data property contained in an element of {@link #getDataSemantics()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( OWLDataProperty property){
            for (MORAxioms.DataSemantic d : getDataSemantics())
                if( d.getSemantic().equals( property))
                    return getDataSemantics().remove( d);
            return false;
        }

        /**
         * Remove the {@link SemanticAxiom#getSemantic()} of the given property, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readSemantic()}.
         * The class of the specified value represents its data type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param property the name of the data property which value, contained in {@link #getDataSemantics()}, will be removed.
         * @param value the property value to be removed from the {@link MORAxioms.DataSemantic#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( String property, Object value){
            return removeData( getOWLDataProperty( property), getOWLLiteral( value));
        }
        /**
         * Remove the {@link SemanticAxiom#getSemantic()} of the given property, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readSemantic()}.
         * @param property the data property which value, contained in {@link #getDataSemantics()}, will be removed.
         * @param value the specific property literal to be removed from the {@link MORAxioms.DataSemantic#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeData( OWLDataProperty property, OWLLiteral value){
            for (MORAxioms.DataSemantic d : getDataSemantics())
                if ( d.getSemantic().equals( property))
                    return d.getValues().remove(value);
            return false;
        }
        default boolean removeObject( OWLDataProperty property, Set<OWLLiteral> values){
            MORAxioms.DataSemantic objectSemantic = new MORAxioms.DataSemantic(property);
            objectSemantic.getValues().addAll( values);
            return getDataSemantics().remove( objectSemantic);
        }

        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readSemantic()}.
         * @param property the name of the data property to synchronise.
         * @return {@code true} if an element was added as a result of this call 
         * (a change of singleton value is not considered).
         */
        default boolean addData( String property){
            return addData( property, false);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readSemantic()}.
         * @param property the data property to synchronise.
         * @return {@code true} if an element was added as a result of this call 
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty property){
            return addData( property, false);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readSemantic()}.
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
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readSemantic()}.
         * @param property the data property to synchronise.
         * @param singleton the flag to enable the synchronisation of only one element of the set, 
         *                  others will be discarded.
         * @return {@code true} if an element was added as a result of this call 
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty property, boolean singleton){
            for (MORAxioms.DataSemantic d : getDataSemantics()) {
                if (d.getSemantic().equals(property)) {
                    d.getValues().setSingleton( singleton);
                    return false;
                }
            }

            MORAxioms.DataSemantic data = new MORAxioms.DataSemantic(property);
            data.getValues().setSingleton( singleton);
            return getDataSemantics().add(data);
        }

        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to {@code false} anyway.
         * The class of the specified value represents its data type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param property the name of the data property to synchronise.
         * @param value the specific data to be added to the {@link MORAxioms.DataSemantic#getValues()} set.
         * @return {@code true} if an element was added as a result of this call 
         * (a change of singleton value is not considered).
         */
        default boolean addData( String property, Object value){
            return addData( property, value, false);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to {@code false} anyway.
         * @param property the data property to synchronise.
         * @param value the literal to be added to the {@link MORAxioms.DataSemantic#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty property, OWLLiteral value){
            return addData( property, value, false);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to specified boolean anyway.
         * The class of the specified value represents its data type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param property the name of the data property to synchronise.
         * @param value the specific data to be added to the {@link MORAxioms.DataSemantic#getValues()} set.
         * @param singleton the flag specifying if the new {@link MORAxioms.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( String property, Object value, boolean singleton){
            return addData( getOWLDataProperty( property), getOWLLiteral( value), singleton);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getDataSemantics()} represents a singleton set this call clear the
         * previous contents.
         * @param property the data property to synchronise.
         * @param value the specific property literal to be added to the {@link MORAxioms.DataSemantic#getValues()} set.
         * @param singleton the flag specifying if the new {@link MORAxioms.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addData( OWLDataProperty property, OWLLiteral value, boolean singleton){
            for (MORAxioms.DataSemantic d : getDataSemantics())
                if( d.getSemantic().equals( property)){
                    if ( singleton)
                        d.getValues().clear();
                    d.getValues().setSingleton( singleton);
                    return d.getValues().add( value);
                }

            MORAxioms.DataSemantic data = new MORAxioms.DataSemantic(property);
            data.getValues().add(value);
            data.getValues().setSingleton( singleton);
            return getDataSemantics().add(data);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getDataSemantics()} represents a singleton set this call clear the
         * previous contents.
         * @param property the data property to synchronise.
         * @param values the specific set of property literal to be added to the {@link MORAxioms.DataSemantic#getValues()} set.
         * @param singleton the flag specifying if the new {@link MORAxioms.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLDataProperty property, Set<OWLLiteral> values, boolean singleton){
            MORAxioms.DataSemantic objectSemantic = new MORAxioms.DataSemantic(property);
            objectSemantic.getValues().addAll( values);
            objectSemantic.getValues().setSingleton( singleton);
            return getDataSemantics().add( objectSemantic);
        }
        /**
         * Add a new semantic (i.e.: data property) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getDataSemantics()} represents a singleton set this call clear the
         * previous contents.
         * This call, automatically sets the {@code singleton} flag to false.
         * @param property the data property to synchronise.
         * @param values the specific set of property literal to be added to the {@link MORAxioms.DataSemantic#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLDataProperty property, Set<OWLLiteral> values){
            return addObject( property, values, false);
        }


        @Override
        MORAxioms.DataSemantics getDataSemantics();

        /**
         * A shortcut for {@link MORAxioms.DataSemantics#getLink(OWLProperty)}
         * @param semantic the data property to look for its values.
         * @return a value of the given data property. {@code Null} if is not available.
         */
        default OWLLiteral getLiteral( OWLDataProperty semantic){
            return getDataSemantics().getLink( semantic);
        }
        /**
         * A shortcut for {@link MORAxioms.DataSemantics#getLink(OWLProperty)}
         * @param semanticName the name of the data property to look for its values.
         * @return a value of the given data property. {@code Null} if is not available.
         */
        default OWLLiteral getLiteral( String semanticName){
            return getDataSemantics().getLink( getOntology().getOWLDataProperty( semanticName));
        }

        /**
         * A shortcut for {@link MORAxioms.DataSemantics#getLinks(OWLProperty)}
         * @param semantic the data property to look for its values.
         * @return all the values of the given data property. An {@code empty} {@link HashSet} if is not available.
         */
        default Axioms<OWLLiteral> getLiterals( OWLDataProperty semantic){
            return getDataSemantics().getLinks( semantic);
        }
        /**
         * A shortcut for {@link MORAxioms.DataSemantics#getLinks(OWLProperty)}
         * @param semanticName the name of the data property to look for its values.
         * @return all the values of the given data property. An {@code empty} {@link HashSet} if is not available.
         */
        default Axioms<OWLLiteral> getLiterals( String semanticName){
            return getDataSemantics().getLinks( getOntology().getOWLDataProperty( semanticName));
        }



        @Override // see super classes for documentation
        default MORAxioms.DataSemantics queryDataIndividual(){
            MORAxioms.DataSemantics dataSet = new MORAxioms.DataSemantics();
            dataSet.setSingleton( getDataSemantics().isSingleton());
            for (DataPropertyRelations r :  getOntology().getDataPropertyB2Individual(getInstance())){
                MORAxioms.DataSemantic data = new MORAxioms.DataSemantic( r.getProperty());
                data.getValues().addAll( r.getValues());
                for (MORAxioms.DataSemantic w : getDataSemantics())
                    if ( data.equals( w)){
                        data.getValues().setSingleton( w.getValues().isSingleton());
                        break;
                    }
                dataSet.add( data);
            }
            return dataSet;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<MORAxioms.DataSemantic> to = synchroniseDataIndividualToSemantic();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (MORAxioms.DataSemantic a : to.getToAdd())
                    for (OWLLiteral l : a.getValues())
                        changes.add(getOntology().addDataPropertyB2Individual(getInstance(), a.getSemantic(), l));
                for (MORAxioms.DataSemantic r : to.getToRemove())
                    for (OWLLiteral l : r.getValues())
                        changes.add(getOntology().removeDataPropertyB2Individual(getInstance(), r.getSemantic(), l));
                return getChangingIntent(to, changes);
            }catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link Individual.ObjectLink} {@link Descriptor} implementation for {@link OWLNamedIndividual}.
     * <p>
     *     It specify how to {@link #queryObject()} and {@link #writeSemantic()} for the
     *     object properties applied to {@link #getInstance()} (i.e.: {@code this individual}).
     *     It also implements common function to populate the {@link SemanticAxioms}
     *     (of type {@link MORAxioms.ObjectSemantic}) that specify the object properties of this individual
     *     that are synchronised with this {@link Descriptor}. For efficiency purposes,
     *     this descriptor does not map all the property of an individual but only the one which
     *     {@code semantic} have been initialised in the {@link SemanticAxiom}.
     *     On the other hand, if the set of {@link SemanticAxioms} is leaved empty during
     *     {@link #readSemantic()}, it maps all the object properties applied to the described
     *     individual.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.MORIndividual <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     *
     * @param <D> the type of the {@link MORDataProperty} descriptors instantiated during
     *           {@link #buildObjectIndividual()} through {@link #getNewObjectIndividual(SemanticAxiom, Object)}.
     */
    interface ObjectLink<D extends MORObjectProperty>
            extends Individual.ObjectLink<OWLReferences, OWLNamedIndividual, MORAxioms.ObjectSemantic, OWLObjectProperty, D>,
                    MORIndividual{

        /**
         * Remove the {@link SemanticAxiom} of the given semantic ({@link MORAxioms.ObjectSemantic#getSemantic()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readSemantic()} and {@link #writeSemantic()}.
         * @param property an object property name contained in an element of {@link #getObjectSemantics()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( String property){
            return removeObject( getOWLObjectProperty( property));
        }
        /**
         * Remove the {@link SemanticAxiom} of the given semantic ({@link MORAxioms.ObjectSemantic#getSemantic()})
         * from the {@link Descriptor}. This call may remove multiple value attached to that
         * semantic. It will no longer be used during {@link #readSemantic()} and {@link #writeSemantic()}.
         * @param property an object property contained in an element of {@link #getObjectSemantics()} to be removed.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( OWLObjectProperty property){
            return getObjectSemantics().remove( property);
        }

        /**
         * Remove the {@link SemanticAxiom#getSemantic()} of the given property, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readSemantic()}.
         * @param property the name of the object property which value, contained in {@link #getObjectSemantics()}, will be removed.
         * @param value the property value to be removed from the {@link MORAxioms.ObjectSemantic#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( String property, String value){
            return removeObject( getOWLObjectProperty( property), getOWLIndividual( value));
        }
        /**
         * Remove the {@link SemanticAxiom#getSemantic()} of the given property, with a specific value, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readSemantic()}.
         * @param property the object property which value, contained in {@link #getObjectSemantics()}, will be removed.
         * @param value the specific property literal to be removed from the {@link MORAxioms.ObjectSemantic#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( OWLObjectProperty property, OWLNamedIndividual value){
            MORAxioms.ObjectSemantic objectSemantic = new MORAxioms.ObjectSemantic(property);
            objectSemantic.getValues().add( value);
            return getObjectSemantics().remove( objectSemantic);
        }
        /**
         * Remove the {@link SemanticAxiom#getSemantic()} of the given property, with specific values, from {@code this}
         * {@link Descriptor}. This call does not remove the semantic from this object, and it may be
         * repopulate (or completely removed) by calling: {@link #readSemantic()}.
         * @param property the object property which value, contained in {@link #getObjectSemantics()}, will be removed.
         * @param values the specific set of property literal to be removed from the {@link MORAxioms.ObjectSemantic#getValues()} set.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeObject( OWLObjectProperty property, Set<OWLNamedIndividual> values){
            MORAxioms.ObjectSemantic objectSemantic = new MORAxioms.ObjectSemantic(property);
            objectSemantic.getValues().addAll( values);
            return getObjectSemantics().remove( objectSemantic);
        }


        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readSemantic()}.
         * @param property the name of the object property to synchronise.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( String property){
            return addObject( property, false);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to {@code false} anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readSemantic()}.
         * @param property the object property to synchronise.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property){
            return addObject( property, false);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor}.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readSemantic()}.
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
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * to the specified value anyway.
         * On the other hand, it does not assign any value to the properties that are automatically
         * queried during {@link #readSemantic()}.
         * @param property the object property to synchronise.
         * @param singleton the flag to enable the synchronisation of only one element of the set,
         *                  others will be discarded.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property, boolean singleton){
            MORAxioms.ObjectSemantic objectSemantic = new MORAxioms.ObjectSemantic(property);
            objectSemantic.getValues().setSingleton( singleton);
            return getObjectSemantics().add( objectSemantic);
        }

        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to {@code false} anyway.
         * The class of the specified value represents its object type, supported {@link OWLLiteral} are:
         * {@link Integer}, {@link Boolean}, {@link Double}, {@link Float} and {@link Long} (see {@link #getOWLLiteral(Object)}).
         * @param property the name of the object property to synchronise.
         * @param value the specific object to be added to the {@link MORAxioms.ObjectSemantic#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( String property, String value){
            return addObject( property, value, false);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to {@code false} anyway.
         * @param property the object property to synchronise.
         * @param value the literal to be added to the {@link MORAxioms.ObjectSemantic#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property, OWLNamedIndividual value){
            return addObject( property, value, false);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to specified boolean anyway.
         * @param property the name of the object property to synchronise.
         * @param value the specific object to be added to the {@link MORAxioms.ObjectSemantic#getValues()} set.
         * @param singleton the flag specifying if the new {@link MORAxioms.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( String property, String value, boolean singleton){
            return addObject( getOWLObjectProperty( property), getOWLIndividual( value), singleton);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular value.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getObjectSemantics()} represents a singleton set this call clear the
         * previous contents.
         * @param property the object property to synchronise.
         * @param value the specific property literal to be added to the {@link MORAxioms.ObjectSemantic#getValues()} set.
         * @param singleton the flag specifying if the new {@link MORAxioms.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property, OWLNamedIndividual value, boolean singleton){
            MORAxioms.ObjectSemantic objectSemantic = new MORAxioms.ObjectSemantic(property);
            objectSemantic.getValues().add( value);
            objectSemantic.getValues().setSingleton( singleton);
            return getObjectSemantics().add( objectSemantic);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getObjectSemantics()} represents a singleton set this call clear the
         * previous contents.
         * @param property the object property to synchronise.
         * @param values the specific set of property literal to be added to the {@link MORAxioms.ObjectSemantic#getValues()} set.
         * @param singleton the flag specifying if the new {@link MORAxioms.Literals} should contains only one element.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property, Set<OWLNamedIndividual> values, boolean singleton){
            MORAxioms.ObjectSemantic objectSemantic = new MORAxioms.ObjectSemantic(property);
            objectSemantic.getValues().addAll( values);
            objectSemantic.getValues().setSingleton( singleton);
            return getObjectSemantics().add( objectSemantic);
        }
        /**
         * Add a new semantic (i.e.: object property) to this {@link Descriptor} with a particular set of values.
         * In case it already exists, this will set the {@link MORAxioms.Literals#isSingleton()}
         * value to specified boolean anyway.
         * If {@link #getObjectSemantics()} represents a singleton set this call clear the
         * previous contents.
         * This call, automatically sets the {@code singleton} flag to false.
         * @param property the object property to synchronise.
         * @param values the specific set of property literal to be added to the {@link MORAxioms.ObjectSemantic#getValues()} set.
         * @return {@code true} if an element was added as a result of this call
         * (a change of singleton value is not considered).
         */
        default boolean addObject( OWLObjectProperty property, Set<OWLNamedIndividual> values){
            return addObject( property, values, false);
        }

        @Override
        MORAxioms.ObjectSemantics getObjectSemantics();

        /**
         * A shortcut for {@link MORAxioms.ObjectSemantics#getLink(OWLProperty)}
         * @param semantic the object property to look for its values.
         * @return a value of the given object property. {@code Null} if is not available.
         */
        default OWLNamedIndividual getObject( OWLObjectProperty semantic){
            return getObjectSemantics().getLink( semantic);
        }
        /**
         * A shortcut for {@link MORAxioms.ObjectSemantics#getLink(OWLProperty)}
         * @param semanticName the name of the object property to look for its values.
         * @return a value of the given object property. {@code Null} if is not available.
         */
        default OWLNamedIndividual getObject( String semanticName){
            return getObjectSemantics().getLink( getOntology().getOWLObjectProperty( semanticName));
        }

        /**
         * A shortcut for {@link MORAxioms.ObjectSemantics#getLinks(OWLProperty)}
         * @param semantic the object property to look for its values.
         * @return all the values of the given object property. An {@code empty} {@link HashSet} if is not available.
         */
        default Axioms<OWLNamedIndividual> getObjects( OWLObjectProperty semantic){
            return getObjectSemantics().getLinks( semantic);
        }
        /**
         * A shortcut for {@link MORAxioms.ObjectSemantics#getLinks(OWLProperty)}
         * @param semanticName the name of the object property to look for its values.
         * @return all the values of the given object property. {@code Null} if is not available.
         */
        default Axioms<OWLNamedIndividual> getObjects( String semanticName){
            return getObjectSemantics().getLinks( getOntology().getOWLObjectProperty( semanticName));
        }

        @Override // see super classes for documentation
        default MORAxioms.ObjectSemantics queryObject(){
            MORAxioms.ObjectSemantics objectSet = new MORAxioms.ObjectSemantics();
            objectSet.setSingleton( getObjectSemantics().isSingleton());
            for (ObjectPropertyRelations r :  getOntology().getObjectPropertyB2Individual(getInstance())){
                MORAxioms.ObjectSemantic object = new MORAxioms.ObjectSemantic( r.getProperty());
                object.getValues().addAll( r.getValues());
                for (MORAxioms.ObjectSemantic w : getObjectSemantics())
                    if ( object.equals( w)){
                        object.getValues().setSingleton( w.getValues().isSingleton());
                        break;
                    }
                objectSet.add( object);
            }
            return objectSet;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeSemantic(){
            try {
                Axioms.SynchronisationIntent<MORAxioms.ObjectSemantic> to = synchroniseObjectIndividualToSemantic();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (MORAxioms.ObjectSemantic a : to.getToAdd())
                    for (OWLNamedIndividual l : a.getValues())
                        changes.add(getOntology().addObjectPropertyB2Individual(getInstance(), a.getSemantic(), l));
                for (MORAxioms.ObjectSemantic r : to.getToRemove())
                    for (OWLNamedIndividual l : r.getValues())
                        changes.add(getOntology().removeObjectPropertyB2Individual(getInstance(), r.getSemantic(), l));
                return getChangingIntent(to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }
}
