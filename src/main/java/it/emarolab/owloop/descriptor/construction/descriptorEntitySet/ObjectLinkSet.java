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
