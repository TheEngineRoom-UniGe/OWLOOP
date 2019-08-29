package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import com.google.common.base.Objects;
import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * An implementation of {@link Axiom.ExpressionEntity} for ObjectLinks.
 * <p>
 *     This class is a container for all the same object properties applied to an individualDescriptor
 *     (with related values). In particular, the {@link #getExpression()} is a specific
 *     property, while {@link #getValues()} represents a set of values linked with the
 *     above property to a {@link Axiom.Ground}, not specified here.
 *     For this class, ({@link #getValues()}) returns elements of {@link Individuals} type.
 * </p>
 */
public class ObjectLinks
        implements Axiom.ExpressionEntity<OWLObjectProperty, OWLNamedIndividual> {

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
     * @param c the initial set of individuals to assign to {@code this} {@link Axiom.ExpressionEntity}.
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
     * @param semantic the initial semantic of this {@link Axiom.ExpressionEntity}.
     */
    public ObjectLinks(OWLObjectProperty semantic) {
        this.objects = new Individuals();
        this.semantic = semantic;
    }
    /**
     * Initialise this object to have a specific {@link #getExpression()} and a given set of individuals ({@link #getValues()}).
     * @param semantic the initial semantic of this {@link Axiom.ExpressionEntity}.
     * @param c the initial set of individuals to assign to {@code this} {@link Axiom.ExpressionEntity}.
     */
    public ObjectLinks(OWLObjectProperty semantic, Collection<? extends OWLNamedIndividual> c) {
        this.objects = new Individuals( c);
        this.semantic = semantic;
    }
    /**
     * Initialise this object to have a specific {@link #getExpression()} and set of individuals {@link #getValues()}
     * with specified size and load factory.
     * @param semantic the initial semantic of this {@link Axiom.ExpressionEntity}.
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
     * @param semantic the initial semantic of this {@link Axiom.ExpressionEntity}.
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