package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLLiteral;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link OWLLiteral}.
 * <p>
 *     It represent the {@link Axiom.EntitySet} which contains OWL-literals.
 * </p>
 */
public class Literals
        extends DescriptorEntitySet.OWLEntitySetBase<OWLLiteral>
        implements Axiom.EntitySet<OWLLiteral> {

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
