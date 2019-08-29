package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link OWLDataProperty}.
 * <p>
 *     It represent the {@link Axiom.EntitySet} which contains OWL-DataProperties.
 * </p>
 */
public class DataProperties
        extends DescriptorEntitySet.OWLEntitySetBase<OWLDataProperty>
        implements Axiom.EntitySet<OWLDataProperty> {

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
