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
