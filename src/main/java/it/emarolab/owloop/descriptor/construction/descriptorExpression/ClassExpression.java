package it.emarolab.owloop.descriptor.construction.descriptorExpression;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.amor.owlInterface.SemanticRestriction.*;
import it.emarolab.owloop.core.Class;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Classes;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Individuals;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.Restrictions;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DescriptorGroundInterface;
import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This interface extends all the interfaces in {@link Class}.
 * It allows to {@link #readAxioms()} and {@link #writeAxioms()}
 * specifically for OWL Classes.
 * It contains several expressions that can be combined in any arbitrary way as they
 * rely on the same ground ({@link ConceptGroundInstance}).
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
public interface ClassExpression
        extends Class<OWLReferences,OWLClass>,
        DescriptorGroundInterface<OWLClass> {

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
    default OWLClass getGroundInstance() {
        return getGround().getGroundInstance();
    }

    /**
     * The {@link Class.Instance} expression for a {@link Descriptor} whose ground is {@link OWLClass}.
     * <p>
     *     It specifies how to {@link #queryIndividuals()} and {@link #writeAxioms()} for the
     *     individuals of the ground Class ({@link #getGroundInstance()}).
     * </p>
     *
     * @param <D> the type of the {@link IndividualExpression} descriptor instantiated during
     *           {@link #buildIndividuals()} through {@link #getIndividualDescriptor(Object, Object)}.
     */
    interface Instance<D extends IndividualExpression>
            extends Class.Instance<OWLReferences, OWLClass, OWLNamedIndividual,D>,
            ClassExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getIndividuals()}.add( {@link #getOntologyReference()}.getOWLIndividual( propertyName))}
         * in order to add a new individualDescriptor (given by name) in the {@link EntitySet} list.
         * @param individualName the individualDescriptor name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addIndividual(String individualName){
            return getIndividuals().add( getOntologyReference().getOWLIndividual( individualName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getIndividuals()}.add( individualDescriptor)}
         * in order to add a new individualDescriptor in the {@link EntitySet} list.
         * @param instance the individualDescriptor to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addIndividual(OWLNamedIndividual instance){
            return getIndividuals().add( instance);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getIndividuals()}.remove( {@link #getOntologyReference()}.getOWLIndividual( propertyName))}
         * in order to remove an individualDescriptor (given by name) from the {@link EntitySet} list.
         * @param individualName the individualDescriptor name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeIndividual(String individualName){
            return getIndividuals().remove( getOntologyReference().getOWLIndividual( individualName));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getIndividuals()}.remove( individualDescriptor)}
         * in order to remove an individualDescriptor in the {@link EntitySet} list.
         * @param instance the individualDescriptor to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeIndividual(OWLNamedIndividual instance){
            return getIndividuals().remove( instance);
        }

        @Override // see super classes for documentation
        default Individuals queryIndividuals(){
            Individuals set = new Individuals(getOntologyReference().getIndividualB2Class(getInstance()));
            set.setSingleton( getIndividuals().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLNamedIndividual> to = synchroniseIndividualsToExpressionAxioms();
                if (to == null)
                    return getIntent(null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (OWLNamedIndividual a : to.getToAdd())
                    changes.add(getOntologyReference().addIndividualB2Class(a, getInstance()));
                for (OWLNamedIndividual b : to.getToRemove())
                    changes.add(getOntologyReference().removeIndividualB2Class(b, getInstance()));
                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link Class.Disjoint} expression for a {@link Descriptor} whose ground is {@link OWLClass}.
     * <p>
     *     It specifies how to {@link #queryDisjointClasses()} and {@link #writeAxioms()} for the
     *     classes disjoint to the ground Class ({@link #getGroundInstance()}).
     * </p>
     *
     * @param <D> the type of the {@link ClassExpression} descriptor instantiated during
     *           {@link #buildDisjointClasses()} through {@link #getDisjointClassDescriptor(Object, Object)}.
     */
    interface Disjoint<D extends ClassExpression>
            extends Class.Disjoint<OWLReferences, OWLClass, D>,
            ClassExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointClasses()}.add( {@link #getOntologyReference()}.getOWLClass( propertyName))}
         * in order to add a new class (given by name) in the {@link EntitySet} list.
         * This method should be always synchronised with {@link #writeAxiomsReasonReadAxioms()}
         * to be perfectly aligned with the ontology, since it affects {@link ClassExpression.Sub} {@link EntitySet}.
         * @param className the class name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointClass(String className){
            return getDisjointClasses().add( getOntologyReference().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointClasses()}.add( cl)}
         * in order to add a new class in the {@link EntitySet} list.
         * This method should be always synchronised with {@link #writeAxiomsReasonReadAxioms()}
         * to be perfectly aligned with the ontology, since it affects {@link ClassExpression.Sub} {@link EntitySet}.
         * @param instance the class to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addDisjointClass(OWLClass instance){
            return getDisjointClasses().add( instance);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointClasses()}.remove( {@link #getOntologyReference()}.getOWLClass( propertyName))}
         * in order to remove a class (given by name) from the {@link EntitySet} list.
         * This method should be always synchronised with {@link #writeAxiomsReasonReadAxioms()}
         * to be perfectly aligned with the ontology, since it affects {@link ClassExpression.Sub} {@link EntitySet}.
         * @param className the class name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointClass(String className){
            return getDisjointClasses().remove( getOntologyReference().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getDisjointClasses()}.remove( cl)}
         * in order to remove a class in the {@link EntitySet} list.
         * This method should be always synchronised with {@link #writeAxiomsReasonReadAxioms()}
         * to be perfectly aligned with the ontology, since it affects {@link ClassExpression.Sub} {@link EntitySet}.
         * @param instance the class to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeDisjointClass(OWLClass instance){
            return getDisjointClasses().remove( instance);
        }


        @Override // see super classes for documentation
        default Classes queryDisjointClasses(){
            Classes set = new Classes(getOntologyReference().getDisjointClasses(getInstance()));
            set.remove( getInstance());
            set.remove( getOntologyReference().getOWLFactory().getOWLNothing());
            set.setSingleton( getDisjointClasses().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLClass> to = synchroniseDisjointClassesToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLClass a : to.getToAdd())
                    if ( ! a.isOWLNothing()){
                        Set<OWLClass> s = new HashSet<>();
                        s.add( getInstance());
                        s.add( a);
                        changes.add( getOntologyReference().makeDisjointClasses( s));
                    }
                for( OWLClass r : to.getToRemove()){
                    Set<OWLClass> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( r);
                    changes.add( getOntologyReference().removeDisjointClasses( s));
                }
                return getChangingIntent(to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link Class.Equivalent} expression for a {@link Descriptor} whose ground is {@link OWLClass}.
     * <p>
     *     It specifies how to {@link #queryEquivalentClasses()} and {@link #writeAxioms()} for the
     *     classes equivalent to the ground Class ({@link #getGroundInstance()}) .
     * </p>
     *
     * @param <D> the type of the {@link ClassExpression} descriptor instantiated during
     *           {@link #buildEquivalentClasses()} through {@link #getEquivalentClassDescriptor(Object, Object)}.
     */
    interface Equivalent<D extends ClassExpression>
            extends Class.Equivalent<OWLReferences, OWLClass, D>,
            ClassExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentClasses()}.add( {@link #getOntologyReference()}.getOWLClass( propertyName))}
         * in order to add a new class (given by name) in the {@link EntitySet} list.
         * @param className the class name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentClass(String className){
            return getEquivalentClasses().add( getOntologyReference().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentClasses()}.add( cl)}
         * in order to add a new class in the {@link EntitySet} list.
         * @param instance the class to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addEquivalentClass(OWLClass instance){
            return getEquivalentClasses().add( instance);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentClasses()}.remove( {@link #getOntologyReference()}.getOWLClass( propertyName))}
         * in order to remove a class (given by name) from the {@link EntitySet} list.
         * @param className the class name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentClass(String className){
            return getEquivalentClasses().remove( getOntologyReference().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getEquivalentClasses()}.remove( cl)}
         * in order to remove a class in the {@link EntitySet} list.
         * @param instance the class to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeEquivalentClass(OWLClass instance){
            return getEquivalentClasses().remove( instance);
        }

        @Override // see super classes for documentation
        default Classes queryEquivalentClasses(){
            Classes set = new Classes(getOntologyReference().getEquivalentClasses(getInstance()));
            set.remove( getInstance());
            //set.remove( getOntology().getOWLFactory().getOWLNothing());
            set.setSingleton( getEquivalentClasses().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLClass> to = synchroniseEquivalentClassesToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for( OWLClass a : to.getToAdd())
                    if ( ! a.isOWLNothing()){
                        Set<OWLClass> s = new HashSet<>();
                        s.add( getInstance());
                        s.add( a);
                        changes.add( getOntologyReference().makeEquivalentClasses( s));
                    }
                for( OWLClass r : to.getToRemove()){
                    Set<OWLClass> s = new HashSet<>();
                    s.add( getInstance());
                    s.add( r);
                    changes.add( getOntologyReference().removeEquivalentClasses( s));
                }
                return getChangingIntent(to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link Class.Sub} expression for a {@link Descriptor} whose ground is {@link OWLClass}.
     * <p>
     *     It specifies how to {@link #querySubClasses()} and {@link #writeAxioms()} for the
     *     sub-classes of the ground Class ({@link #getGroundInstanceName()}).
     * </p>
     *
     * @param <D> the type of the {@link ClassExpression} descriptor instantiated during
     *           {@link #buildSubClasses()} through {@link #getSubClassDescriptor(Object, Object)}.
     */
    interface Sub<D extends ClassExpression>
            extends Class.Sub<OWLReferences, OWLClass, D>,
            ClassExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getSubClasses()}.add( {@link #getOntologyReference()}.getOWLClass( propertyName))}
         * in order to add a new class (given by name) in the {@link EntitySet} list.
         * @param className the class name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSubClass(String className){
            return getSubClasses().add( getOntologyReference().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSubClasses()}.add( cl)}
         * in order to add a new class in the {@link EntitySet} list.
         * @param instance the class to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSubClass(OWLClass instance){
            return getSubClasses().add( instance);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getSubClasses()}.remove( {@link #getOntologyReference()}.getOWLClass( propertyName))}
         * in order to remove a class (given by name) from the {@link EntitySet} list.
         * @param className the class name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSubClass(String className){
            return getSubClasses().remove( getOntologyReference().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSubClasses()}.remove( cl)}
         * in order to remove a class in the {@link EntitySet} list.
         * @param instance the class to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSubClass(OWLClass instance){
            return getSubClasses().remove( instance);
        }

        @Override // see super classes for documentation
        default Classes querySubClasses(){
            Classes set = new Classes(getOntologyReference().getSubClassOf(getInstance()));
            //set.remove( getOntology().getOWLFactory().getOWLNothing());
            set.setSingleton( getSubClasses().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLClass> to = synchroniseSubClassesToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (OWLClass a : to.getToAdd())
                    if ( ! a.isOWLNothing())
                        changes.add(getOntologyReference().addSubClassOf(getInstance(), a));
                for (OWLClass r : to.getToRemove())
                    changes.add(getOntologyReference().removeSubClassOf(getInstance(), r));
                return getChangingIntent(to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link Class.Super} expression for a {@link Descriptor} whose ground is {@link OWLClass}.
     * <p>
     *     It specifies how to {@link #querySuperClasses()} and {@link #writeAxioms()} for the
     *     super-classes of the ground Class ({@link #getGroundInstance()}).
     * </p>
     *
     * @param <D> the type of the {@link ClassExpression} descriptor instantiated during
     *           {@link #buildSuperClasses()} through {@link #getSuperClassDescriptor(Object, Object)}.
     */
    interface Super<D extends ClassExpression>
            extends Class.Super<OWLReferences, OWLClass, D>,
            ClassExpression {

        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperClasses()}.add( {@link #getOntologyReference()}.getOWLClass( propertyName))}
         * in order to add a new class (given by name) in the {@link EntitySet} list.
         * This method should be always synchronised with {@link #writeAxiomsReasonReadAxioms()}
         * to be perfectly aligned with the ontology, since it affects {@link EquivalentRestriction} {@link EntitySet}.
         * @param className the class name to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSuperClass(String className){
            return getSuperClasses().add( getOntologyReference().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperClasses()}.add( cl)}
         * in order to add a new class in the {@link EntitySet} list.
         * This method should be always synchronised with {@link #writeAxiomsReasonReadAxioms()}
         * to be perfectly aligned with the ontology, since it affects {@link EquivalentRestriction} {@link EntitySet}.
         * @param instance the class to add for synchronisation.
         * @return {@code true} if the axioms changed as a result of the call.
         */
        default boolean addSuperClass(OWLClass instance){
            return getSuperClasses().add( instance);
        }

        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperClasses()}.remove( {@link #getOntologyReference()}.getOWLClass( propertyName))}
         * in order to remove a class (given by name) from the {@link EntitySet} list.
         * This method should be always synchronised with {@link #writeAxiomsReasonReadAxioms()}
         * to be perfectly aligned with the ontology, since it affects {@link EquivalentRestriction} {@link EntitySet}.
         * @param className the class name to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSuperClass(String className){
            return getSuperClasses().remove( getOntologyReference().getOWLClass( className));
        }
        /**
         * It is an helper that just calls:
         * {@code {@link #getSuperClasses()}.remove( cl)}
         * in order to remove a class in the {@link EntitySet} list.
         * This method should be always synchronised with {@link #writeAxiomsReasonReadAxioms()}
         * to be perfectly aligned with the ontology, since it affects {@link EquivalentRestriction} {@link EntitySet}.
         * @param instance the class to remove for synchronisation.
         * @return {@code true} if an element was removed as a result of this call.
         */
        default boolean removeSuperClass(OWLClass instance){
            return getSuperClasses().remove( instance);
        }

        @Override // see super classes for documentation
        default Classes querySuperClasses(){
            Classes set = new Classes( getOntologyReference().getSuperClassOf(getInstance()));
            set.setSingleton( getSuperClasses().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeAxioms(){
            try {
                EntitySet.SynchronisationIntent<OWLClass> to = synchroniseSuperClassesToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();
                for (OWLClass a : to.getToAdd())
                    if( ! a.isOWLNothing())
                        changes.add(getOntologyReference().addSubClassOf(a, getInstance()));
                for (OWLClass r : to.getToRemove())
                    changes.add(getOntologyReference().removeSubClassOf(r, getInstance()));
                return getChangingIntent(to, changes);
            } catch ( Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }

    /**
     * The {@link Class.Restriction} expression for a {@link Descriptor} whose ground is {@link OWLClass}.
     * <p>
     *     It specifies how to {@link #queryEquivalentRestrictions()} and {@link #writeAxioms()} for
     *     definition (i.e.: {@link SemanticRestriction}) of the ground Class ({@link #getGroundInstance()}).
     *     All the restrictions managed by this Class are considered to be a unique class definition
     *     made by their intersection.
     *     <br>
     *     <b>ATTENTION</b>: the {@link #writeAxioms()} method implemented by
     *     this constructor uses {@link org.semanticweb.owlapi.change.ConvertSuperClassesToEquivalentClass}
     *     and {@link org.semanticweb.owlapi.change.ConvertEquivalentClassesToSuperClasses}.
     *     It may affect {@link ClassExpression.Super} or {@link ClassExpression.Sub} descriptors.
     *     Call always this first!!
     *     <b>REMARK</b>: the actual implementation allow the definition of a signe restriction axiom.
     *     This can be the union of `class` expression, and `min`, `max`, `exactly`, `some`, `any`
     *     data or property restriction expression
     * </p>
     */
    interface EquivalentRestriction
            extends Class.Restriction<OWLReferences, OWLClass, SemanticRestriction>,
            ClassExpression {

        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for class definition.
         */
        default SemanticRestriction createMinDataRestriction(String property, int cardinality, java.lang.Class dataType){
            return createMinDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(java.lang.Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for class definition.
         */
        default SemanticRestriction createMinDataRestriction(OWLDataProperty property, int cardinality, java.lang.Class dataType){
            return new ClassRestrictedOnMinData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMinDataRestriction(OWLDataProperty, int, java.lang.Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addMinDataRestriction(OWLDataProperty property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().add( createMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMinDataRestriction(String, int, java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addMinDataRestriction(String property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().add( createMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMinDataRestriction(OWLDataProperty, int, java.lang.Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeMinDataRestriction(OWLDataProperty property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().remove( createMinDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMinDataRestriction(String, int, java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeMinDataRestriction(String property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().remove( createMinDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for class definition.
         */
        default SemanticRestriction createMaxDataRestriction(String property, int cardinality, java.lang.Class dataType){
            return createMaxDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(java.lang.Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for class definition.
         */
        default SemanticRestriction createMaxDataRestriction(OWLDataProperty property, int cardinality, java.lang.Class dataType){
            return new ClassRestrictedOnMaxData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMaxDataRestriction(OWLDataProperty, int, java.lang.Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addMaxDataRestriction(OWLDataProperty property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().add( createMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMaxDataRestriction(String, int, java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addMaxDataRestriction(String property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().add( createMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMaxDataRestriction(OWLDataProperty, int, java.lang.Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeMaxDataRestriction(OWLDataProperty property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().remove( createMaxDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMaxDataRestriction(String, int, java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeMaxDataRestriction(String property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().remove( createMaxDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for class definition.
         */
        default SemanticRestriction createExactDataRestriction(String property, int cardinality, java.lang.Class dataType){
            return createExactDataRestriction( getOWLDataProperty( property), cardinality, dataType);
        }
        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(java.lang.Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for class definition.
         */
        default SemanticRestriction createExactDataRestriction(OWLDataProperty property, int cardinality, java.lang.Class dataType){
            return new ClassRestrictedOnExactData( getInstance(), getOWLDataType( dataType), property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createExactDataRestriction(OWLDataProperty, int, java.lang.Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addExactDataRestriction(OWLDataProperty property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().add( createExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createExactDataRestriction(String, int, java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addExactDataRestriction(String property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().add( createExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createExactDataRestriction(OWLDataProperty, int, java.lang.Class)}.
         * @param property the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeExactDataRestriction(OWLDataProperty property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().remove( createExactDataRestriction( property, cardinality, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createExactDataRestriction(String, int, java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeExactDataRestriction(String property, int cardinality, java.lang.Class dataType){
            return getEquivalentRestrictions().remove( createExactDataRestriction( property, cardinality, dataType));
        }

        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for class definition.
         */
        default SemanticRestriction createSomeDataRestriction(String property, java.lang.Class dataType){
            return createSomeDataRestriction( getOWLDataProperty( property), dataType);
        }
        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(java.lang.Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for class definition.
         */
        default SemanticRestriction createSomeDataRestriction(OWLDataProperty property, java.lang.Class dataType){
            return new ClassRestrictedOnSomeData( getInstance(), getOWLDataType( dataType), property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createSomeDataRestriction(OWLDataProperty, java.lang.Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addSomeDataRestriction(String property, java.lang.Class dataType){
            return getEquivalentRestrictions().add( createSomeDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createSomeDataRestriction(String, java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addSomeDataRestriction(OWLDataProperty property, java.lang.Class dataType){
            return getEquivalentRestrictions().add( createSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createSomeDataRestriction(OWLDataProperty, java.lang.Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeSomeDataRestriction(String property, java.lang.Class dataType){
            return getEquivalentRestrictions().remove( createSomeDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createSomeDataRestriction(String, java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeSomeDataRestriction(OWLDataProperty property, java.lang.Class dataType){
            return getEquivalentRestrictions().remove( createSomeDataRestriction( property, dataType));
        }

        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for class definition.
         */
        default SemanticRestriction createOnlyDataRestriction(String property, java.lang.Class dataType){
            return createOnlyDataRestriction( getOWLDataProperty( property), dataType);
        }
        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of data properties
         * in a specific range of data type based on: {@link #getOWLDataType(java.lang.Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return a new data type restriction for class definition.
         */
        default SemanticRestriction createOnlyDataRestriction(OWLDataProperty property, java.lang.Class dataType){
            return new ClassRestrictedOnAllData( getInstance(), getOWLDataType( dataType), property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createOnlyDataRestriction(OWLDataProperty, java.lang.Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addOnlyDataRestriction(String property, java.lang.Class dataType){
            return getEquivalentRestrictions().add( createOnlyDataRestriction( property, dataType));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createOnlyDataRestriction(String, java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addOnlyDataRestriction(OWLDataProperty property, java.lang.Class dataType){
            return getEquivalentRestrictions().add( createOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createOnlyDataRestriction(OWLDataProperty, java.lang.Class)}.
         * @param property the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeOnlyDataRestriction(String property, java.lang.Class dataType){
            return getEquivalentRestrictions().remove( createOnlyDataRestriction( property, dataType));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createOnlyDataRestriction(String, java.lang.Class)}.
         * @param property the name of the restricting data property.
         * @param dataType the data type of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeOnlyDataRestriction(OWLDataProperty property, java.lang.Class dataType){
            return getEquivalentRestrictions().remove( createOnlyDataRestriction( property, dataType));
        }

        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of object properties
         * within in a specific ontological class.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createMinObjectRestriction(String property, int cardinality, String cl){
            return createMinObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( cl));
        }
        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a minimal cardinality of object properties
         * within in a specific ontological class.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createMinObjectRestriction(OWLObjectProperty property, int cardinality, OWLClass cl){
            return new ClassRestrictedOnMinObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addMinObjectRestriction( OWLObjectProperty property, int cardinality, OWLClass cl){
            return getEquivalentRestrictions().add( createMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMinObjectRestriction(String, int, String)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addMinObjectRestriction( String property, int cardinality, String cl){
            return getEquivalentRestrictions().add( createMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMinObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl of the range class of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeMinObjectRestriction( OWLObjectProperty property, int cardinality, OWLClass cl){
            return getEquivalentRestrictions().remove( createMinObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMinObjectRestriction(String, int, String)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the minimal property restriction.
         * @param cl the name of the range class of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeMinObjectRestriction( String property, int cardinality, String cl){
            return getEquivalentRestrictions().remove( createMinObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of object properties
         * within in a specific ontological class.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createMaxObjectRestriction( String property, int cardinality, String cl){
            return createMaxObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( cl));
        }
        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over a maximal cardinality of object properties
         * within in a specific ontological class.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createMaxObjectRestriction( OWLObjectProperty property, int cardinality, OWLClass cl){
            return new ClassRestrictedOnMaxObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addMaxObjectRestriction( OWLObjectProperty property, int cardinality, OWLClass cl){
            return getEquivalentRestrictions().add( createMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMaxObjectRestriction(String, int, String)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addMaxObjectRestriction( String property, int cardinality, String cl){
            return getEquivalentRestrictions().add( createMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMaxObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl of the range class of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeMaxObjectRestriction( OWLObjectProperty property, int cardinality, OWLClass cl){
            return getEquivalentRestrictions().remove( createMaxObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createMaxObjectRestriction(String, int, String)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the maximal property restriction.
         * @param cl the name of the range class of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeMaxObjectRestriction( String property, int cardinality, String cl){
            return getEquivalentRestrictions().remove( createMaxObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of object properties
         * within in a specific ontological class.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createExactObjectRestriction( String property, int cardinality, String cl){
            return createMaxObjectRestriction( getOWLObjectProperty( property), cardinality, getOWLClass( cl));
        }
        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an exact cardinality of object properties
         * within in a specific ontological class.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createExactObjectRestriction( OWLObjectProperty property, int cardinality, OWLClass cl){
            return new ClassRestrictedOnMaxObject( getInstance(), cl, property, cardinality);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addExactObjectRestriction( OWLObjectProperty property, int cardinality, OWLClass cl){
            return getEquivalentRestrictions().add( createExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createExactObjectRestriction(String, int, String)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addExactObjectRestriction( String property, int cardinality, String cl){
            return getEquivalentRestrictions().add( createExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createExactObjectRestriction(OWLObjectProperty, int, OWLClass)}.
         * @param property the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl of the range class of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeExactObjectRestriction( OWLObjectProperty property, int cardinality, OWLClass cl){
            return getEquivalentRestrictions().remove( createExactObjectRestriction( property, cardinality, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createExactObjectRestriction(String, int, String)}.
         * @param property the name of the restricting object property.
         * @param cardinality the cardinality for the exact property restriction.
         * @param cl the name of the range class of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeExactObjectRestriction( String property, int cardinality, String cl){
            return getEquivalentRestrictions().remove( createExactObjectRestriction( property, cardinality, cl));
        }

        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of object properties
         * within in a specific ontological class.
         * @param property the name of the restricting object property.
         * @param cl the name of the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createSomeObjectRestriction( String property, String cl){
            return createSomeObjectRestriction( getOWLObjectProperty( property), getOWLClass( cl));
        }
        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an existential cardinality of object properties
         * within in a specific ontological class.
         * @param property the restricting object property.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createSomeObjectRestriction( OWLObjectProperty property, OWLClass cl){
            return new ClassRestrictedOnSomeObject( getInstance(), cl, property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addSomeObjectRestrcition( String property, String cl){
            return getEquivalentRestrictions().add( createSomeObjectRestriction( property, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addSomeObjectRestrcition( OWLObjectProperty property, OWLClass cl){
            return getEquivalentRestrictions().add( createSomeObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createSomeObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl of the range class of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeSomeObjectRestrcition( String property, String cl){
            return getEquivalentRestrictions().remove( createSomeObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createSomeObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the range class of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeSomeObjectRestrcition( OWLObjectProperty property, OWLClass cl){
            return getEquivalentRestrictions().remove( createSomeObjectRestriction( property, cl));
        }

        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of object properties
         * within in a specific ontological class.
         * @param property the name of the restricting object property.
         * @param cl the name of the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createOnlyObjectRestriction( String property, String cl){
            return createOnlyObjectRestriction( getOWLObjectProperty( property), getOWLClass( cl));
        }
        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which restricts over an universal cardinality of object properties
         * within in a specific ontological class.
         * @param property the restricting object property.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createOnlyObjectRestriction( OWLObjectProperty property, OWLClass cl){
            return new ClassRestrictedOnAllObject( getInstance(), cl, property);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addOnlyObjectRestrcition( String property, String cl){
            return getEquivalentRestrictions().add( createOnlyObjectRestriction( property, cl));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addOnlyObjectRestrcition( OWLObjectProperty property, OWLClass cl){
            return getEquivalentRestrictions().add( createOnlyObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createOnlyObjectRestriction(OWLObjectProperty, OWLClass)}.
         * @param property the restricting object property.
         * @param cl of the range class of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeOnlyObjectRestrcition( String property, String cl){
            return getEquivalentRestrictions().remove( createOnlyObjectRestriction( property, cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createOnlyObjectRestriction(String, String)}.
         * @param property the name of the restricting object property.
         * @param cl the name of the range class of the restriction.
         * @return {@code true} if a restriction has been removed from the {@link Class.Restriction}.
         */
        default boolean removeOnlyObjectRestrcition( OWLObjectProperty property, OWLClass cl){
            return getEquivalentRestrictions().remove( createOnlyObjectRestriction( property, cl));
        }

        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which represents an equivalent class.
         * @param className the name of the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createClassRestriction(String className){
            return createClassRestriction( getOWLClass( className));
        }
        /**
         * Creates a new class equivalence restriction (to be in conjunction with the others in the specific {@link EntitySet})
         * which represents an equivalent class.
         * @param cl the range class of the restriction.
         * @return a new object type restriction for class definition.
         */
        default SemanticRestriction createClassRestriction( OWLClass cl){
            return new ClassRestrictedOnClass( getInstance(), cl);
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createClassRestriction(String)}.
         * @param className the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addClassRestriction(String className){
            return getEquivalentRestrictions().add( createClassRestriction( className));
        }
        /**
         * Adds a new restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createClassRestriction(String)}.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean addClassRestriction(OWLClass cl){
            return getEquivalentRestrictions().add( createClassRestriction( cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createClassRestriction(String)}.
         * @param cl the name of the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean removeClassRestriction(String cl){
            return getEquivalentRestrictions().remove( createClassRestriction( cl));
        }
        /**
         * Removes a restriction to the described {@link EntitySet} (i.e.: {@link #getEquivalentRestrictions()})
         * based on {@link #createClassRestriction(String)}.
         * @param cl the range class of the restriction.
         * @return {@code true} if a new restriction has been added to the {@link Class.Restriction}.
         */
        default boolean remvoveClassRestriction(OWLClass cl){
            return getEquivalentRestrictions().remove( createClassRestriction( cl));
        }

        @Override // did not name it as getEquivalentRestrictions because this interface implements from a more generic interface Class.Restriction
        Restrictions getEquivalentRestrictions();

        @Override // did not name it as getEquivalentRestrictions because this interface implements from a more generic interface Class.Restriction
        default Restrictions queryEquivalentRestrictions(){
            Set< Set<ApplyingRestriction>> restrictionsSet = getOntologyReference().getClassRestrictions( getInstance());
            Set<ApplyingRestriction> restrictions = new HashSet<>();
            for ( Set<ApplyingRestriction> r : restrictionsSet){
                restrictions = r;
                break;
            }
            if ( restrictionsSet.size() > 1)
                System.err.println( "WARNING: all the restrictions that define a class should be contained in a single axiom." +
                        " Only axiom \'" + restrictions + "\' is considered in \'" + restrictionsSet + "\'");
            // remove self
            for ( ApplyingRestriction a : restrictions)
                if ( a.getRestrictionType().isRestrictionOnClass())
                    if ( a.getValue().equals( getInstance())){
                        restrictions.remove( a);
                        break;
                    }
            Restrictions set = new Restrictions( restrictions);
            set.setSingleton( getEquivalentRestrictions().isSingleton());
            return set;
        }

        @Override // see super classes for documentation
        default List<MappingIntent> writeAxioms(){
            try {
                EntitySet.SynchronisationIntent<SemanticRestriction> to = synchroniseEquivalentRestrictionsToExpressionAxioms();
                if ( to == null)
                    return getIntent( null);
                List<OWLOntologyChange> changes = new ArrayList<>();

                if ( to.getToAdd().size() > 0 | to.getToRemove().size() > 0){
                    //noinspection unchecked
                    changes.addAll( getOntologyReference().convertEquivalentClassesToSuperClasses( getInstance()));
                    for (SemanticRestriction r : to.getToRemove()) {
                        changes.add( getOntologyReference().removeRestriction(r));
                        if( r instanceof ClassRestrictedOnClass)
                            changes.add( getOntologyReference().removeSubClassOf( (OWLClass) r.getSubject(), (OWLClass) r.getValue()));
                    }

                    for (SemanticRestriction a : to.getToAdd())
                        changes.add(getOntologyReference().addRestriction(a));

                    if ( ! getEquivalentRestrictions().isEmpty())
                        changes.addAll(getOntologyReference().convertSuperClassesToEquivalentClass(getInstance()));//getEquivalentRestrictions()));
                }

                return getChangingIntent(to, changes);
            } catch (Exception e){
                e.printStackTrace();
                return getIntent( null);
            }
        }
    }
}
