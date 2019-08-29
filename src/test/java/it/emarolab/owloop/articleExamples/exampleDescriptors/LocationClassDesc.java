package it.emarolab.owloop.articleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;

/**
 *  <p>
 *      Extends DefClassDesc.
 *  </p>
 *  Adds some-restriction on the concept "LOCATION", i.e, LOCATION hasDoor some DOOR
 */
public class LocationClassDesc
        extends DefClassDesc {

    public LocationClassDesc(OWLReferences onto) {

        super("LOCATION", onto);
        addDefinitionRestriction( getRestriction());                    // adds definition with a restriction
    }
    LocationClassDesc(String instanceName, OWLReferences onto) {

        super(instanceName, onto);
        addDefinitionRestriction( new SemanticRestriction.ClassRestrictedOnAllObject()); // adding with Some-restriction
        addDefinitionRestriction( getRestriction());                    // adds definition with a restriction
    }

    // To make the warning go away, add the types explicitly:
    private void addDefinitionRestriction(SemanticRestriction.ApplyingPropertyRestriction defRestrictions){
        defRestrictions.setSubject( getGround().getGroundInstance());
        defRestrictions.setProperty( getOWLObjectProperty( "hasDoor"));
        defRestrictions.setValue( getOWLClass( "DOOR"));
        getEquivalentRestrictions().add( defRestrictions);
        writeExpressionAxioms();
    }

    // implementing with a Some-restriction
    @Override
    protected SemanticRestriction.ApplyingPropertyRestriction getRestriction(){
        return new SemanticRestriction.ClassRestrictedOnAllObject();
    }

}
