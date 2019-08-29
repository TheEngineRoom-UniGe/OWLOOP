package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.core.Axiom;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link SemanticRestriction}.
 * <p>
 *     It represent the {@link Axiom.EntitySet} which contains OWL-Restrictions.
 * </p>
 */
public class Restrictions
        extends DescriptorEntitySet.EntitySetBase<SemanticRestriction>
        implements Axiom.EntitySet<SemanticRestriction> {

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