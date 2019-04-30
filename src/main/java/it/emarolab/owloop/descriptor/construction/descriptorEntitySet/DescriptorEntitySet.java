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
 * CONTAINS CLASSES that can instantiate an EntitySet as a for containing a particular type of OWL entity.
 *
 *
 * The main interface for {@link EntitySet} implemented in the <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a> API.
 * <p>
 *     This interface contains all the {@link EntitySet}, {@link ExpressionEntity}
 *     and {@link ExpressionEntitySet} used in the {@link Concept}, {@link Individual},
 *     {@link DataProperty} and {@link ObjectProperty} {@link Descriptor}s for the
 *     OWLOOP architecture implemented with the
 *     <a href="https://github.com/EmaroLab/multi_ontology_reference">aMOR</a> framework.
 *     <br>
 *     All of those manage a collection of ontological entities, eventually attached with
 *     a specific semantic. In particular all are based on {@link EntitySetBase}, which
 *     implements the management of an {@link HashSet} with a specified type. When the type
 *     is an {@link OWLObject}, the {@link OWLEntitySetBase} in used just to extend the
 *     {@link EntitySetBase#toString()} method.
 *     <br>
 *     More in particular, the implemented axioms set are:
 *     <ul>
 *     <li><b>{@link Individuals}</b>: for describing a set of ontological individuals.</li>
 *     <li><b>{@link Concepts}</b>: for describing a set of ontological classes.</li>
 *     <li><b>{@link Literals}</b>: for describing a set of ontological data values
 *                                           (used by the {@link DataExpression} axiom implementation).</li>
 *     <li><b>{@link DataLinks}</b>: for describing a set of ontological data properties.</li>
 *     <li><b>{@link ObjectLinks}</b>: for describing a set of ontological object properties.</li>
 *     <li><b>{@link Restrictions}</b>: for describing a set of restriction
 *                           (i.e.: for conceptCompoundDescriptor definition as wel as data and property range or domain definition).</li>
 *     <li><b>{@link DataExpression}</b>: for describing a set of data values with a semantic
 *                                     (i.e.: by {@link DataSemantics}).</li>
 *     <li><b>{@link ObjectExpression}</b>: for describing a set of data values with a semantic
 *                                     (i.e.: by {@link ObjectSemantics}).</li>
 *     </ul>
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public interface DescriptorEntitySet extends Axiom {

    /**
     * The base interface for all the {@link DescriptorEntitySet} that extends {@link EntitySet} or {@link ExpressionEntitySet}.
     * <p>
     *     This class implements common features for managing an {@link HashSet} and a {@code singleton} value
     *     for {@link EntitySet}.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
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
         * Instanciate this {@link EntitySet} as an empty {@link HashSet}.
         * It is not set to be a {@code singleton}.
         */
        public EntitySetBase() {
           super();
        }
        /**
         * Instanciate this {@link EntitySet} as a {@link HashSet} containing the given value.
         * It is not set to be {@code singleton}.
         * @param c the element with wich setGround the set of {@link EntitySet}.
         */
        public EntitySetBase(Collection<? extends T> c) {
            super(c);
        }
        /**
         * Instanciate this {@link EntitySet} as a {@link HashSet} with a given initial size and load factor.
         * It is not set to be {@code singleton}.
         * @param initialCapacity the initial capacity of the axioms {@link HashSet}.
         * @param loadFactor the load factor of the axioms {@link HashSet}
         */
        public EntitySetBase(int initialCapacity, float loadFactor) {
            super( initialCapacity, loadFactor);
        }
        /**
         * Instanciate this {@link EntitySet} as a {@link HashSet} with a given initial size.
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
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
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
     * An extension of {@link EntitySetBase} for {@link OWLNamedIndividual}.
     * <p>
     *     It represent the {@link EntitySet} describing a set of
     *     ontological individuals.
     *     <br>
     *     This class does not add any other feature with respect to its super class
     *     refer to {@link DescriptorEntitySet} and {@link EntitySetBase}  for further documentation.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
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
     *     It represent the {@link EntitySet} describing a set of
     *     ontological classes.
     *     <br>
     *     This class does not add any other feature with respect to its super class
     *     refer to {@link DescriptorEntitySet} and {@link EntitySetBase} for further documentation.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    class Concepts
            extends OWLEntitySetBase<OWLClass>
            implements EntitySet<OWLClass> {
        public Concepts() {
        }
        public Concepts(Collection<? extends OWLClass> c) {
            super(c);
        }
        public Concepts(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public Concepts(int initialCapacity) {
            super(initialCapacity);
        }
    }

    /**
     * An extension of {@link EntitySetBase} for {@link OWLLiteral}.
     * <p>
     *     It represent the {@link EntitySet} describing a set of
     *     ontological literal values.
     *     <br>
     *     This class does not add any other feature with respect to its super class
     *     refer to {@link DescriptorEntitySet} and {@link EntitySetBase}  for further documentation.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
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
     *     It represent the {@link EntitySet} describing a set of
     *     ontological data properties.
     *     <br>
     *     This class does not add any other feature with respect to its super class
     *     refer to {@link DescriptorEntitySet} and {@link EntitySetBase}  for further documentation.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    class DataLinks
            extends OWLEntitySetBase<OWLDataProperty>
            implements EntitySet<OWLDataProperty> {

        public DataLinks() {
        }
        public DataLinks(Collection<? extends OWLDataProperty> c) {
            super(c);
        }
        public DataLinks(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public DataLinks(int initialCapacity) {
            super(initialCapacity);
        }
    }

    /**
     * An extension of {@link EntitySetBase} for {@link OWLObjectProperty}.
     * <p>
     *     It represent the {@link EntitySet} describing a set of
     *     ontological object properties.
     *     <br>
     *     This class does not add any other feature with respect to its super class
     *     refer to {@link DescriptorEntitySet} and {@link EntitySetBase}  for further documentation.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    class ObjectLinks
            extends OWLEntitySetBase<OWLObjectProperty>
            implements EntitySet<OWLObjectProperty> {

        public ObjectLinks() {
        }
        public ObjectLinks(Collection<? extends OWLObjectProperty> c) {
            super(c);
        }
        public ObjectLinks(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public ObjectLinks(int initialCapacity) {
            super(initialCapacity);
        }
    }


    /**
     * An extension of {@link EntitySetBase} for {@link SemanticRestriction}.
     * <p>
     *     It represent the {@link EntitySet} describing a set of
     *     ontological restrictions.
     *     <br>
     *     This class does not add any other feature with respect to its super class
     *     refer to {@link DescriptorEntitySet} and {@link EntitySetBase}  for further documentation.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
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
     * An implementation of {@link ExpressionEntity} for ontological data properties.
     * <p>
     *     This class is a container for all the same data properties applied to an individualCompoundDescriptor
     *     (with related values). In particular, the {@link #getExpression()} is a specific
     *     property, while {@link #getValues()} represents a set of values linked with the
     *     above property to a {@link Ground}, not specified here.
     *     For this class, the literals ({@link #getValues()}) are of the {@link Literals} type.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    class DataExpression
            implements ExpressionEntity<OWLDataProperty,OWLLiteral> {

        private OWLDataProperty semantic;
        private Literals literals;

        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and empty {@link #getValues()}.
         */
        public DataExpression() {
            this.literals = new Literals();
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and a given set of literal ({@link #getValues()}).
         * @param c the initial set of literals to assign to {@code this} {@link ExpressionEntity}.
         */
        public DataExpression(Collection<? extends OWLLiteral> c) {
            this.literals = new Literals(c);
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and set of literals {@link #getValues()}
         * with specified size and load factory.
         * @param initialCapacity the initial size of the literals {@link HashSet}.
         * @param loadFactor the load factor of the literals {@link HashSet}.
         */
        public DataExpression(int initialCapacity, float loadFactor) {
            this.literals = new Literals(initialCapacity, loadFactor);
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and set of literals {@link #getValues()}
         * with specified size and load factory.
         * @param initialCapacity the initial size of the literals {@link HashSet}.
         */
        public DataExpression(int initialCapacity) {
            this.literals = new Literals(initialCapacity);
        }
        /**
         * Initialise this object to a specific {@link #getExpression()} and empty {@link #getValues()}.
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         */
        public DataExpression(OWLDataProperty semantic) {
            this.literals = new Literals();
            this.semantic = semantic;
        }
        /**
         * Initialise this object to have a specific {@link #getExpression()} and a given set of literal ({@link #getValues()}).
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         * @param c the initial set of literals to assign to {@code this} {@link ExpressionEntity}.
         */
        public DataExpression(OWLDataProperty semantic, Collection<? extends OWLLiteral> c) {
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
        public DataExpression(OWLDataProperty semantic, int initialCapacity, float loadFactor) {
            this.literals = new Literals(initialCapacity, loadFactor);
            this.semantic = semantic;
        }
        /**
         * Initialise this object to have a specific {@link #getExpression()} and set of literals {@link #getValues()}
         * with specified size and load factory.
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         * @param initialCapacity the initial size of the literals {@link HashSet}.
         */
        public DataExpression(OWLDataProperty semantic, int initialCapacity) {
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
        public DataExpression getNewData(Set<OWLLiteral> values) {
            return new DataExpression( semantic, values);
        }

        @Override // see super class for documentation
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DataExpression)) return false;
            DataExpression morSemanticData = (DataExpression) o;
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
     * An implementation of {@link ExpressionEntity} for ontological object properties.
     * <p>
     *     This class is a container for all the same object properties applied to an individualCompoundDescriptor
     *     (with related values). In particular, the {@link #getExpression()} is a specific
     *     property, while {@link #getValues()} represents a set of values linked with the
     *     above property to a {@link Ground}, not specified here.
     *     For this class, the individuals ({@link #getValues()}) are of the {@link Individuals} type.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    class ObjectExpression
            implements ExpressionEntity<OWLObjectProperty,OWLNamedIndividual> {

        private OWLObjectProperty semantic;
        private Individuals objects;

        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and empty {@link #getValues()}.
         */
        public ObjectExpression() {
            this.objects = new Individuals();
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and a given set of individuals ({@link #getValues()}).
         * @param c the initial set of individuals to assign to {@code this} {@link ExpressionEntity}.
         */
        public ObjectExpression(Collection<? extends OWLNamedIndividual> c) {
            this.objects = new Individuals(c);
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and set of individuals {@link #getValues()}
         * with specified size and load factory.
         * @param initialCapacity the initial size of the individuals {@link HashSet}.
         * @param loadFactor the load factor of the individuals {@link HashSet}.
         */
        public ObjectExpression(int initialCapacity, float loadFactor) {
            this.objects = new Individuals(initialCapacity, loadFactor);
        }
        /**
         * Initialise this object to have {@code null} {@link #getExpression()} and set of individuals {@link #getValues()}
         * with specified size and load factory.
         * @param initialCapacity the initial size of the individuals {@link HashSet}.
         */
        public ObjectExpression(int initialCapacity) {
            this.objects = new Individuals(initialCapacity);
        }
        /**
         * Initialise this object to a specific {@link #getExpression()} and empty {@link #getValues()}.
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         */
        public ObjectExpression(OWLObjectProperty semantic) {
            this.objects = new Individuals();
            this.semantic = semantic;
        }
        /**
         * Initialise this object to have a specific {@link #getExpression()} and a given set of individuals ({@link #getValues()}).
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         * @param c the initial set of individuals to assign to {@code this} {@link ExpressionEntity}.
         */
        public ObjectExpression(OWLObjectProperty semantic, Collection<? extends OWLNamedIndividual> c) {
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
        public ObjectExpression(OWLObjectProperty semantic, int initialCapacity, float loadFactor) {
            this.objects = new Individuals(initialCapacity, loadFactor);
            this.semantic = semantic;
        }
        /**
         * Initialise this object to have a specific {@link #getExpression()} and set of individuals {@link #getValues()}
         * with specified size and load factory.
         * @param semantic the initial semantic of this {@link ExpressionEntity}.
         * @param initialCapacity the initial size of the individuals {@link HashSet}.
         */
        public ObjectExpression(OWLObjectProperty semantic, int initialCapacity) {
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
        public ObjectExpression getNewData(Set<OWLNamedIndividual> values) {
            return new ObjectExpression( semantic, values);
        }

        @Override // see super class for documentation
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ObjectExpression)) return false;
            ObjectExpression morData = (ObjectExpression) o;
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
     * The base implementation for the {@link ExpressionEntitySet}.
     * <p>
     *     It implements common methods to be used to manage a set of EntitySet.
     *     In particular, it define the method of adding and removing set of
     *     data or object properties values with the same semantic. Also,
     *     it implements helping way to obtain the actual value of a property.
     *     Constructors, and common way to manage an {@link HashSet} are based
     *     on {@link EntitySetBase}.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.ExpressionEntitySetBase <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
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
                    if ( ! s.getValues().isSingleton())
                        System.out.println( " !! search for only one literal in a not singleton object property: " + s);
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
     * An extension of {@link EntitySetBase} for {@link ExpressionEntitySet} for data properties.
     * <p>
     *     It represent the {@link ExpressionEntitySet} describing a set of
     *     ontological data properties as a collection of {@link DataExpression}.
     *     <br>
     *     This class does not add any other feature with respect to its super class
     *     refer to {@link DescriptorEntitySet} and {@link EntitySetBase}  for further documentation.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    class DataSemantics
            extends ExpressionEntitySetBase<DataExpression,OWLDataProperty,OWLLiteral>
            implements ExpressionEntitySet<DataExpression,OWLLiteral> {

        public DataSemantics() {
        }
        public DataSemantics(Collection<? extends DataExpression> c) {
            super(c);
        }
        public DataSemantics(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public DataSemantics(int initialCapacity) {
            super(initialCapacity);
        }

    }

    /**
     * An extension of {@link EntitySetBase} for {@link ExpressionEntitySet} for object properties.
     * <p>
     *     It represent the {@link ExpressionEntitySet} describing a set of
     *     ontological object properties as a collection of {@link ObjectExpression}.
     *     <br>
     *     This class does not add any other feature with respect to its super class
     *     refer to {@link DescriptorEntitySet} and {@link EntitySetBase}  for further documentation.
     * </p>
     * <div style="text-align:center;"><small>
     * <b>File</b>:        it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet <br>
     * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
     * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
     * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
     * <b>date</b>:        21/05/17 <br>
     * </small></div>
     */
    class ObjectSemantics
            extends ExpressionEntitySetBase<ObjectExpression,OWLObjectProperty,OWLNamedIndividual>
            implements ExpressionEntitySet<ObjectExpression,OWLNamedIndividual> {

        public ObjectSemantics() {
        }
        public ObjectSemantics(Collection<? extends ObjectExpression> c) {
            super(c);
        }
        public ObjectSemantics(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }
        public ObjectSemantics(int initialCapacity) {
            super(initialCapacity);
        }

    }
}
