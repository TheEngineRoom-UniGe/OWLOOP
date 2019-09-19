package it.emarolab.owloop.articleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;

/**
 *  <p>
 *      Extends DefClassDesc.
 *  </p>
 *  Adds some-restriction on the concept "LOCATION", i.e, LOCATION hasDoor some DOOR
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
public class LocationClassDesc
        extends DefClassDesc {

    public LocationClassDesc(OWLReferences onto) {

        super("LOCATION", onto);
        addEquivalentRestriction( getRestriction());                    // adds definition with a restriction
    }
    LocationClassDesc(String instanceName, OWLReferences onto) {

        super(instanceName, onto);
        addEquivalentRestriction( new SemanticRestriction.ClassRestrictedOnAllObject()); // adding with Some-restriction
        addEquivalentRestriction( getRestriction());                    // adds definition with a restriction
    }

    // To make the warning go away, add the types explicitly:
    private void addEquivalentRestriction(SemanticRestriction.ApplyingPropertyRestriction defRestrictions){
        defRestrictions.setSubject( getGround().getGroundInstance());
        defRestrictions.setProperty( getOWLObjectProperty( "hasDoor"));
        defRestrictions.setValue( getOWLClass( "DOOR"));
        getEquivalentRestrictions().add( defRestrictions);
        writeAxioms();
    }

    // implementing with a Some-restriction
    @Override
    protected SemanticRestriction.ApplyingPropertyRestriction getRestriction(){
        return new SemanticRestriction.ClassRestrictedOnAllObject();
    }

}
