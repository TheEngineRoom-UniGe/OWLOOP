package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import com.google.common.base.Objects;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.core.*;
import org.semanticweb.owlapi.model.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This interface contains classes that can be used to instantiate EntitySets containing
 * entities of a particular type.
 * The entity types within the OWLOOP architecture are among the following:
 * <ul>
 * <li><b>{@link Individuals}</b>:      type to describe a set of: [OWL-Individual].</li>
 * <li><b>{@link Classes}</b>:         type to describe a set of: [OWL-Class].</li>
 * <li><b>{@link Literals}</b>:         type to describe a set of: [OWL-Literal] (i.e., data values).</li>
 * <li><b>{@link DataProperties}</b>:   type to describe a set of: [OWL-DataProperty].</li>
 * <li><b>{@link ObjectProperties}</b>: type to describe a set of: [OWL-ObjectProperty].</li>
 * <li><b>{@link Restrictions}</b>:     type to describe a set of: [OWL-EquivalentRestriction].</li>
 * <li><b>{@link DataLinks}</b>:        type to describe a set of: [An Expression along with an OWL-Literal] .</li>
 * <li><b>{@link ObjectLinks}</b>:      type to describe a set of: [An Expression along with an OWL Individual].</li>
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
 */
public interface DescriptorEntitySet extends Axiom {

    /**
     * Base interface for all the {@link DescriptorEntitySet} that extends {@link EntitySet}.
     * <p>
     *      This class implements common features for managing an {@link HashSet} and a {@code singleton} value
     *      for {@link EntitySet}.
     * </p>
     *
     * @param <T> the type of the axiom to collect.
     */
    class EntitySetBase<T>
            extends HashSet<T>
            implements EntitySet<T> {

        /**
         * the actual singleton flagging value. Constructing value is set to {@code false}.
         */
        protected boolean singleton = false;

        /**
         * Instantiate this {@link EntitySet} as an empty {@link HashSet}.
         * It is not set to be a {@code singleton}.
         */
        public EntitySetBase() {
           super();
        }
        /**
         * Instantiate this {@link EntitySet} as a {@link HashSet} containing the given value.
         * It is not set to be {@code singleton}.
         * @param c the element with wich setGround the set of {@link EntitySet}.
         */
        public EntitySetBase(Collection<? extends T> c) {
            super(c);
        }
        /**
         * Instantiate this {@link EntitySet} as a {@link HashSet} with a given initial size and load factor.
         * It is not set to be {@code singleton}.
         * @param initialCapacity the initial capacity of the axioms {@link HashSet}.
         * @param loadFactor the load factor of the axioms {@link HashSet}
         */
        public EntitySetBase(int initialCapacity, float loadFactor) {
            super( initialCapacity, loadFactor);
        }
        /**
         * Instantiate this {@link EntitySet} as a {@link HashSet} with a given initial size.
         * It is not set to be {@code singleton}.
         * @param initialCapacity the initial capacity of the axioms {@link HashSet}.
         */
        public EntitySetBase(int initialCapacity) {
            super( initialCapacity);
        }

        @Override // see Axiom.EntitySet for documentation
        public boolean isSingleton() {
            return singleton;
        }

        @Override // see Axiom.EntitySet for documentation
        public void setSingleton(boolean singleton) {
            this.singleton = singleton;
        }


        @Override
        public String toString() {
            String out = "{";
            int cnt = size();
            for ( T l : this) {
                out += l;
                if ( cnt-- > 1)
                    out += ", ";
            }
            out += "}";
            if ( isSingleton())
                out += "(singleton)";
            return out;
        }
    }

    /**
     * An extension of {@link EntitySetBase} for type extending {@link OWLObject}.
     * <p>
     *     This class only override the {@link EntitySetBase#toString()} method.
     *     Refer to the super class for documentation
     *     (constructors only call {@code super(..)}).
     * </p>
     *
     * @param <T> the type of the axiom to collect.
     */
    class OWLEntitySetBase<T extends OWLObject>
            extends EntitySetBase<T> {

        public OWLEntitySetBase() {
        }
        public OWLEntitySetBase(Collection<? extends T> c) {
            super(c);
        }
        public OWLEntitySetBase(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public OWLEntitySetBase(int initialCapacity) {
            super(initialCapacity);
        }

        @Override
        public String toString() {
            String out = "{";
            int cnt = size();
            for ( T l : this) {
                out += OWLReferencesInterface.getOWLName( l);
                if ( cnt-- > 1)
                    out += ", ";
            }
            out += "}";
            if ( isSingleton())
                out += "(singleton)";
            return out;
        }
    }

    /**
     * The base implementation for the {@link ExpressionEntitySet}.
     * <p>
     *     It implements common methods to be used to manage a set of EntitySet.
     *     In particular, it define the method of adding and removing set of
     *     data or object properties values with the same expression. Also,
     *     it enables obtaining the actual value of a property.
     *     Constructors and {@link HashSet} management are based
     *     on {@link EntitySetBase}.
     * </p>
     */
    abstract class ExpressionEntitySetBase<X extends ExpressionEntity<S,A>,S extends OWLProperty,A>
            extends EntitySetBase<X>
            implements ExpressionEntitySet<X,A> {

        public ExpressionEntitySetBase() {
        }
        public ExpressionEntitySetBase(Collection<? extends X> c) {
            super(c);
        }
        public ExpressionEntitySetBase(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public ExpressionEntitySetBase(int initialCapacity) {
            super(initialCapacity);
        }

        /**
         * Searches in the {@link ExpressionEntity} for the given property and
         * returns all its values.
         * @param semantic the properties to look for.
         * @return all the synchronised values of the given property.
         * An {@code empty} {@link HashSet} if the values is not available.
         */
        public EntitySet<A> getLinks(S semantic){
            for ( X s : this){
                if ( semantic.equals( s.getExpression()))
                    return s.getValues();
            }
            return new EntitySetBase<>();
        }

        /**
         * Searches in the {@link ExpressionEntity} for the given property and
         * returns one of its values. It should be used with a {@link EntitySet#isSingleton()}
         * axioms, since other values are ignored.
         * @param semantic the properties to look for.
         * @return one of the synchronised values of the given property.
         * An {@code null} if the values is not available.
         */
        public A getLink(S semantic){
            for ( X s : this){
                if ( semantic.equals( s.getExpression())) {
                    if ( ! s.getValues().isSingleton() & s.getValues().size() > 1)
                        System.out.println( " !![WARNING] Uncertainty in the element that is returned, if there is more than 1 element in the EntitySet: " + s);
                    for (A l : s.getValues())
                        return l;
                }
            }
            return null;
        }


        /**
         * This method modifies the standard adding procedure to a set
         * by looking if the {@link ExpressionEntitySet} already contains
         * the semantic (i.e.: data or object property) specified in the
         * input parameter. If this is true the given value are added
         * to the related {@link  ExpressionEntity#getValues()}. Otherwise
         * the given object is added as a new element.
         * Note that if the input parameter describes a {@code singleton}
         * object all the previous contents related to that semantic
         * are deleted.
         * @param dataSemantic the new semantic to add.
         * @return {@code true} if this collection changed as a result of the call.
         */
        @Override
        public boolean add(X dataSemantic) {
            for ( X d : this)//.set)
                if( d.getExpression().equals( dataSemantic.getExpression())){
                    if ( dataSemantic.getValues().isSingleton())
                        d.getValues().clear();
                    d.getValues().setSingleton( dataSemantic.getValues().isSingleton());
                    return d.getValues().addAll( dataSemantic.getValues());
                }

            return super.add( dataSemantic);
        }

        /**
         * It removes an entri from the {@link ExpressionEntitySet}.
         * The input parameter cna be ether the object describing the
         * {@link ExpressionEntity} with a specific semantic and values
         * to be removed. Or it can a property. For the latter,
         * all the values are deleted.
         * @param o the object to be removed.
         * @return {@code true} if the set contained the specified element.
         */
        @Override
        public boolean remove(Object o) {
            for (X d : this){//.set) {
                if (d.equals(o))
                    return super.remove(d);
                if (d.getExpression().equals(o))
                    return super.remove(d);
                if( o instanceof  ObjectLinks) {
                    ObjectLinks objLink = (ObjectLinks) o;
                    if (d.getExpression().equals( objLink.getExpression())) {
                        return d.getValues().removeAll( objLink.getValues());
                    }
                }
            }
            return false;
        }

        @Override
        public String toString() {
            String out = super.toString();
            if ( isSingleton())
                out += "(singleton)";
            return out;
        }
    }

    /**
     * An extension of {@link EntitySetBase} for {@link OWLNamedIndividual}.
     * <p>
     *     It represents the {@link EntitySet} which contains OWL-Individuals.
     * </p>
     */
    class Individuals
            extends OWLEntitySetBase<OWLNamedIndividual>
            implements EntitySet<OWLNamedIndividual> {
        public Individuals() {
        }
        public Individuals(Collection<? extends OWLNamedIndividual> c) {
            super(c);
        }
        public Individuals(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public Individuals(int initialCapacity) {
            super(initialCapacity);
        }
    }

    /**
     * An extension of {@link EntitySetBase} for {@link OWLClass}.
     * <p>
     *     It represent the {@link EntitySet} which contains OWL-Classes.
     * </p>
     */
    class Classes
            extends OWLEntitySetBase<OWLClass>
            implements EntitySet<OWLClass> {
        public Classes() {
        }
        public Classes(Collection<? extends OWLClass> c) {
            super(c);
        }
        public Classes(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public Classes(int initialCapacity) {
            super(initialCapacity);
        }
    }

    /**
     * An extension of {@link EntitySetBase} for {@link OWLLiteral}.
     * <p>
     *     It represent the {@link EntitySet} which contains OWL-literals.
     * </p>
     */
    class Literals
            extends OWLEntitySetBase<OWLLiteral>
            implements EntitySet<OWLLiteral> {

        public Literals() {
        }
        public Literals(Collection<? extends OWLLiteral> c) {
            super(c);
        }
        public Literals(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public Literals(int initialCapacity) {
            super(initialCapacity);
        }
    }

    /**
     * An extension of {@link EntitySetBase} for {@link OWLDataProperty}.
     * <p>
     *     It represent the {@link EntitySet} which contains OWL-DataProperties.
     * </p>
     */
    class DataProperties
            extends OWLEntitySetBase<OWLDataProperty>
            implements EntitySet<OWLDataProperty> {

        public DataProperties() {
        }
        public DataProperties(Collection<? extends OWLDataProperty> c) {
            super(c);
        }
        public DataProperties(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public DataProperties(int initialCapacity) {
            super(initialCapacity);
        }
    }

    /**
     * An extension of {@link EntitySetBase} for {@link OWLObjectProperty}.
     * <p>
     *     It represent the {@link EntitySet} which contains OWL-ObjectProperties.
     * </p>
     */
    class ObjectProperties
            extends OWLEntitySetBase<OWLObjectProperty>
            implements EntitySet<OWLObjectProperty> {

        public ObjectProperties() {
        }
        public ObjectProperties(Collection<? extends OWLObjectProperty> c) {
            super(c);
        }
        public ObjectProperties(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public ObjectProperties(int initialCapacity) {
            super(initialCapacity);
        }
    }


    /**
     * An extension of {@link EntitySetBase} for {@link SemanticRestriction}.
     * <p>
     *     It represent the {@link EntitySet} which contains OWL-Restrictions.
     * </p>
     */
    class Restrictions
            extends EntitySetBase<SemanticRestriction>
            implements EntitySet<SemanticRestriction> {

        public Restrictions() {
        }
        public Restrictions(Collection<? extends SemanticRestriction> c) {
            super(c);
        }
        public Restrictions(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public Restrictions(int initialCapacity) {
            super(initialCapacity);
        }
    }

    /**
     * An implementation of {@link ExpressionEntity} for DataLinks.
     * <p>
     *     This class is a container for all the same data properties applied to an individualDescriptor
     *     (with related values). In particular, the {@link #getExpression()} is a specific
     *     property, while {@link #getValues()} represents a set of values linked with the
     *     above property to a {@link Ground}, not specified here.
     *     For this class, ({@link #getValues()}) returns elements of {@link Literals} type.
     * </p>
     */
    class DataLinks
            implements ExpressionEntity<OWLDataProperty,OWLLiteral> {

        private OWLDataProperty semantic;
        private Literals literals;

        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and empty {@link #getValues()}.
         */
        public DataLinks() {
            this.literals = new Literals();
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and a given set of literal ({@link #getValues()}).
         * @param c the initial set of literals to assign to {@code this} {@link ExpressionEntity}.
         */
        public DataLinks(Collection<? extends OWLLiteral> c) {
            this.literals = new Literals(c);
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and set of literals {@link #getValues()}
         * with specified size and load factory.
         * @param initialCapacity the initial size of the literals {@link HashSet}.
         * @param loadFactor the load factor of the literals {@link HashSet}.
         */
        public DataLinks(int initialCapacity, float loadFactor) {
            this.literals = new Literals(initialCapacity, loadFactor);
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and set of literals {@link #getValues()}
         * with specified size and load factory.
         * @param initialCapacity the initial size of the literals {@link HashSet}.
         */
        public DataLinks(int initialCapacity) {
            this.literals = new Literals(initialCapacity);
        }
        /**
         * Initialise this object to a specific {@link #getExpression()} and empty {@link #getValues()}.
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         */
        public DataLinks(OWLDataProperty semantic) {
            this.literals = new Literals();
            this.semantic = semantic;
        }
        /**
         * Initialise this object to have a specific {@link #getExpression()} and a given set of literal ({@link #getValues()}).
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         * @param c the initial set of literals to assign to {@code this} {@link ExpressionEntity}.
         */
        public DataLinks(OWLDataProperty semantic, Collection<? extends OWLLiteral> c) {
            this.literals = new Literals(c);
            this.semantic = semantic;
        }
        /**
         * Initialise this object to have a specific {@link #getExpression()} and set of literals {@link #getValues()}
         * with specified size and load factory.
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         * @param initialCapacity the initial size of the literals {@link HashSet}.
         * @param loadFactor the load factor of the literals {@link HashSet}.
         */
        public DataLinks(OWLDataProperty semantic, int initialCapacity, float loadFactor) {
            this.literals = new Literals(initialCapacity, loadFactor);
            this.semantic = semantic;
        }
        /**
         * Initialise this object to have a specific {@link #getExpression()} and set of literals {@link #getValues()}
         * with specified size and load factory.
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         * @param initialCapacity the initial size of the literals {@link HashSet}.
         */
        public DataLinks(OWLDataProperty semantic, int initialCapacity) {
            this.literals = new Literals(initialCapacity);
            this.semantic = semantic;
        }

        @Override // see super class for documentation
        public OWLDataProperty getExpression() {
            return semantic;
        }

        /**
         * Set the semantic (i.e.: data property) for all the {@link #getValues()}
         * @param semantic the semantic described by {@code this} container.
         */
        public void setSemantic(OWLDataProperty semantic) {
            this.semantic = semantic;
        }

        @Override // see super class for documentation
        public Literals getValues() {
            return literals;
        }

        @Override // see super class for documentation
        public DataLinks getNewData(Set<OWLLiteral> values) {
            return new DataLinks( semantic, values);
        }

        @Override // see super class for documentation
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DataLinks)) return false;
            DataLinks morSemanticData = (DataLinks) o;
            return Objects.equal(getExpression(), morSemanticData.getExpression()) &&
                    Objects.equal(literals, morSemanticData.literals);
        }

        @Override // see super class for documentation
        public int hashCode() {
            return Objects.hashCode(getExpression());//, objects);
        }

        @Override // see super class for documentation
        public String toString() {
            return semantic.getIRI().getRemainder().get() + "." + getValues();
        }
    }

    /**
     * An implementation of {@link ExpressionEntity} for ObjectLinks.
     * <p>
     *     This class is a container for all the same object properties applied to an individualDescriptor
     *     (with related values). In particular, the {@link #getExpression()} is a specific
     *     property, while {@link #getValues()} represents a set of values linked with the
     *     above property to a {@link Ground}, not specified here.
     *     For this class, ({@link #getValues()}) returns elements of {@link Individuals} type.
     * </p>
     */
    class ObjectLinks
            implements ExpressionEntity<OWLObjectProperty,OWLNamedIndividual> {

        private OWLObjectProperty semantic;
        private Individuals objects;

        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and empty {@link #getValues()}.
         */
        public ObjectLinks() {
            this.objects = new Individuals();
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and a given set of individuals ({@link #getValues()}).
         * @param c the initial set of individuals to assign to {@code this} {@link ExpressionEntity}.
         */
        public ObjectLinks(Collection<? extends OWLNamedIndividual> c) {
            this.objects = new Individuals(c);
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and set of individuals {@link #getValues()}
         * with specified size and load factory.
         * @param initialCapacity the initial size of the individuals {@link HashSet}.
         * @param loadFactor the load factor of the individuals {@link HashSet}.
         */
        public ObjectLinks(int initialCapacity, float loadFactor) {
            this.objects = new Individuals(initialCapacity, loadFactor);
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and set of individuals {@link #getValues()}
         * with specified size and load factory.
         * @param initialCapacity the initial size of the individuals {@link HashSet}.
         */
        public ObjectLinks(int initialCapacity) {
            this.objects = new Individuals(initialCapacity);
        }
        /**
         * Initialise this object to a specific {@link #getExpression()} and empty {@link #getValues()}.
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         */
        public ObjectLinks(OWLObjectProperty semantic) {
            this.objects = new Individuals();
            this.semantic = semantic;
        }
        /**
         * Initialise this object to have a specific {@link #getExpression()} and a given set of individuals ({@link #getValues()}).
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         * @param c the initial set of individuals to assign to {@code this} {@link ExpressionEntity}.
         */
        public ObjectLinks(OWLObjectProperty semantic, Collection<? extends OWLNamedIndividual> c) {
            this.objects = new Individuals( c);
            this.semantic = semantic;
        }
        /**
         * Initialise this object to have a specific {@link #getExpression()} and set of individuals {@link #getValues()}
         * with specified size and load factory.
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         * @param initialCapacity the initial size of the individuals {@link HashSet}.
         * @param loadFactor the load factor of the individuals {@link HashSet}.
         */
        public ObjectLinks(OWLObjectProperty semantic, int initialCapacity, float loadFactor) {
            this.objects = new Individuals(initialCapacity, loadFactor);
            this.semantic = semantic;
        }
        /**
         * Initialise this object to have a specific {@link #getExpression()} and set of individuals {@link #getValues()}
         * with specified size and load factory.
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         * @param initialCapacity the initial size of the individuals {@link HashSet}.
         */
        public ObjectLinks(OWLObjectProperty semantic, int initialCapacity) {
            this.objects = new Individuals(initialCapacity);
            this.semantic = semantic;
        }

        @Override // see super class for documentation
        public OWLObjectProperty getExpression() {
            return semantic;
        }

        /**
         * Set the semantic (i.e.: object property) for all the {@link #getValues()}
         * @param semantic the semantic described by {@code this} container.
         */
        public void setSemantic(OWLObjectProperty semantic) {
            this.semantic = semantic;
        }

        @Override // see super class for documentation
        public Individuals getValues() {
            return objects;
        }

        @Override // see super class for documentation
        public ObjectLinks getNewData(Set<OWLNamedIndividual> values) {
            return new ObjectLinks( semantic, values);
        }

        @Override // see super class for documentation
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ObjectLinks)) return false;
            ObjectLinks morData = (ObjectLinks) o;
            return Objects.equal(getExpression(), morData.getExpression()) &&
                    Objects.equal(objects, morData.objects);
        }

        @Override // see super class for documentation
        public int hashCode() {
            return Objects.hashCode(getExpression());//, objects);
        }

        @Override // see super class for documentation
        public String toString() {
            return semantic.getIRI().getRemainder().get() + "." + getValues();
        }
    }

    /**
     * An extension of {@link EntitySetBase} for {@link ExpressionEntitySet} for DataLinks.
     * <p>
     *     It represents a set of OWL-DataProperties and related OWL-Literals, as a collection of {@link DataLinks}.
     * </p>
     */
    class DataLinkSet
            extends ExpressionEntitySetBase<DataLinks,OWLDataProperty,OWLLiteral>
            implements ExpressionEntitySet<DataLinks,OWLLiteral> {

        public DataLinkSet() {
        }
        public DataLinkSet(Collection<? extends DataLinks> c) {
            super(c);
        }
        public DataLinkSet(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public DataLinkSet(int initialCapacity) {
            super(initialCapacity);
        }

    }

    /**
     * An extension of {@link EntitySetBase} for {@link ExpressionEntitySet} for ObjectLinks.
     * <p>
     *     It represents a set of OWL-ObjectProperties and related OWL-Individuals, as a collection of {@link ObjectLinks}.
     * </p>
     */
    class ObjectLinkSet
            extends ExpressionEntitySetBase<ObjectLinks,OWLObjectProperty,OWLNamedIndividual>
            implements ExpressionEntitySet<ObjectLinks,OWLNamedIndividual> {

        public ObjectLinkSet() {
        }
        public ObjectLinkSet(Collection<? extends ObjectLinks> c) {
            super(c);
        }
        public ObjectLinkSet(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public ObjectLinkSet(int initialCapacity) {
            super(initialCapacity);
        }

    }
}
