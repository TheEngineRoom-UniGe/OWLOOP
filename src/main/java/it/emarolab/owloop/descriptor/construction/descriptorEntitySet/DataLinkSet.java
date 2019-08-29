package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link Axiom.ExpressionEntitySet} for DataLinks.
 * <p>
 *     It represents a set of OWL-DataProperties and related OWL-Literals, as a collection of {@link DataLinks}.
 * </p>
 */
public class DataLinkSet
        extends DescriptorEntitySet.ExpressionEntitySetBase<DataLinks, OWLDataProperty, OWLLiteral>
        implements Axiom.ExpressionEntitySet<DataLinks,OWLLiteral> {

    public DataLinkSet() {
    }
    public DataLinkSet(Collection<? extends DataLinks> c) {
        super(c);
    }
    public DataLinkSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    public DataLinkSet(int initialCapacity) {
        super(initialCapacity);
    }

}
