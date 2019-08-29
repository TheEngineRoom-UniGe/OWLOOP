package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.core.Axiom;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link SemanticRestriction}.
 * <p>
 *     It represent the {@link Axiom.EntitySet} which contains OWL-Restrictions.
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