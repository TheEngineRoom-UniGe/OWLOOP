package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link OWLNamedIndividual}.
 * <p>
 *     It represents the {@link Axiom.EntitySet} which contains OWL-Individuals.
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
public class Individuals
        extends DescriptorEntitySet.OWLEntitySetBase<OWLNamedIndividual>
        implements Axiom.EntitySet<OWLNamedIndividual> {
    public Individuals() {
    }
    public Individuals(Collection<? extends OWLNamedIndividual> c) {
        super(c);
    }
    public Individuals(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    public Individuals(int initialCapacity) {
        super(initialCapacity);
    }
}