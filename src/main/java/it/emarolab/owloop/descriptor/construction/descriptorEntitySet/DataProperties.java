package it.emarolab.owloop.descriptor.construction.descriptorEntitySet;

import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.Collection;

/**
 * An extension of {@link DescriptorEntitySet.EntitySetBase} for {@link OWLDataProperty}.
 * <p>
 *     It represent the {@link Axiom.EntitySet} which contains OWL-DataProperties.
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
public class DataProperties
        extends DescriptorEntitySet.OWLEntitySetBase<OWLDataProperty>
        implements Axiom.EntitySet<OWLDataProperty> {

    public DataProperties() {
    }
    public DataProperties(Collection<? extends OWLDataProperty> c) {
        super(c);
    }
    public DataProperties(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    public DataProperties(int initialCapacity) {
        super(initialCapacity);
    }
}
