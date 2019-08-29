package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLLiteral;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link OWLLiteral}.
 * <p>
 *     It represent the {@link Axiom.EntitySet} which contains OWL-literals.
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
