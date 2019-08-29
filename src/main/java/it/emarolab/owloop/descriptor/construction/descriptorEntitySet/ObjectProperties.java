package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link OWLObjectProperty}.
 * <p>
 *     It represent the {@link Axiom.EntitySet} which contains OWL-ObjectProperties.
 * </p>
 */
public class ObjectProperties
        extends DescriptorEntitySet.OWLEntitySetBase<OWLObjectProperty>
        implements Axiom.EntitySet<OWLObjectProperty> {

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
