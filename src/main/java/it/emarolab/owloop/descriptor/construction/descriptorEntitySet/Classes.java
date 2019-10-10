package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link OWLClass}.
 * <p>
 *     It represent the {@link Axiom.EntitySet} which contains OWL-Classes.
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
