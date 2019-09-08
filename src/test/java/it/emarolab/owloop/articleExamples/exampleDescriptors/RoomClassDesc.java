package it.emarolab.owloop.articleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;

/**
 *  <p>
 *      Extends LocationClassDesc.
 *  </p>
 *  Adds some-restriction on the concept "ROOM", i.e, ROOM hasDoor some DOOR.
 *  Furthermore, adds max-cardinality-restriction, i.e., ROOM hasDoor max 1 DOOR.
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
public class RoomClassDesc
        extends LocationClassDesc {

    public RoomClassDesc(OWLReferences onto) {

        super("ROOM", onto);
    }

    // overriding with a MaxCardinality-restriction
    @Override
    protected SemanticRestriction.ApplyingPropertyRestriction getRestriction(){
        SemanticRestriction.ClassRestrictedOnMaxObject definition = new SemanticRestriction.ClassRestrictedOnMaxObject();
        definition.setCardinality( 1);
        return definition;
    }
}
