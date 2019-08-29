package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link OWLClass}.
 * <p>
 *     It represent the {@link Axiom.EntitySet} which contains OWL-Classes.
 * </p>
 */
public class Classes
        extends DescriptorEntitySet.OWLEntitySetBase<OWLClass>
        implements Axiom.EntitySet<OWLClass> {
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
