package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import com.google.common.base.Objects;
import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * An implementation of {@link Axiom.ExpressionEntity} for DataLinks.
 * <p>
 *     This class is a container for all the same data properties applied to an individualDescriptor
 *     (with related values). In particular, the {@link #getExpression()} is a specific
 *     property, while {@link #getValues()} represents a set of values linked with the
 *     above property to a {@link Axiom.Ground}, not specified here.
 *     For this class, ({@link #getValues()}) returns elements of {@link Literals} type.
 * </p>
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
public class DataLinks
        implements Axiom.ExpressionEntity<OWLDataProperty, OWLLiteral> {

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
     * @param c the initial set of literals to assign to {@code this} {@link Axiom.ExpressionEntity}.
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
     * @param semantic the initial semantic of this {@link Axiom.ExpressionEntity}.
     */
    public DataLinks(OWLDataProperty semantic) {
        this.literals = new Literals();
        this.semantic = semantic;
    }
    /**
     * Initialise this object to have a specific {@link #getExpression()} and a given set of literal ({@link #getValues()}).
     * @param semantic the initial semantic of this {@link Axiom.ExpressionEntity}.
     * @param c the initial set of literals to assign to {@code this} {@link Axiom.ExpressionEntity}.
     */
    public DataLinks(OWLDataProperty semantic, Collection<? extends OWLLiteral> c) {
        this.literals = new Literals(c);
        this.semantic = semantic;
    }
    /**
     * Initialise this object to have a specific {@link #getExpression()} and set of literals {@link #getValues()}
     * with specified size and load factory.
     * @param semantic the initial semantic of this {@link Axiom.ExpressionEntity}.
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
     * @param semantic the initial semantic of this {@link Axiom.ExpressionEntity}.
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