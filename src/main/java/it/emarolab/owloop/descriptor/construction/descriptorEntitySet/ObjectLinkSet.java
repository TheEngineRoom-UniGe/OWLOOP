package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link Axiom.ExpressionEntitySet} for ObjectLinks.
 * <p>
 *     It represents a set of OWL-ObjectProperties and related OWL-Individuals, as a collection of {@link ObjectLinks}.
 * </p>
 */
public class ObjectLinkSet
        extends DescriptorEntitySet.ExpressionEntitySetBase<ObjectLinks, OWLObjectProperty, OWLNamedIndividual>
        implements Axiom.ExpressionEntitySet<ObjectLinks,OWLNamedIndividual> {

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
