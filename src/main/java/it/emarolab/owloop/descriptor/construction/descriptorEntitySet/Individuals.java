package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link OWLNamedIndividual}.
 * <p>
 *     It represents the {@link Axiom.EntitySet} which contains OWL-Individuals.
 * </p>
 */
public class Individuals
        extends DescriptorEntitySet.OWLEntitySetBase<OWLNamedIndividual>
        implements Axiom.EntitySet<OWLNamedIndividual> {
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