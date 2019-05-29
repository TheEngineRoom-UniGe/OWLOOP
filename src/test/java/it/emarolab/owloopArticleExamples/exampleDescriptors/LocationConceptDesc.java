package it.emarolab.owloopArticleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;

public class LocationConceptDesc
        extends DefConceptDesc {

    public LocationConceptDesc(OWLReferences onto) {

        super("LOCATION", onto);
        addDefinitionRestriction( getRestriction());                    // adds definition with a restriction
    }
    LocationConceptDesc(String instanceName, OWLReferences onto) {

        super(instanceName, onto);
        addDefinitionRestriction( new SemanticRestriction.ClassRestrictedOnSomeObject()); // adding with Some-restriction
        addDefinitionRestriction( getRestriction());                    // adds definition with a restriction
    }

    // To make the warning go away, add the types explicitly:
    private void addDefinitionRestriction(SemanticRestriction.ApplyingPropertyRestriction defRestrictions){
        defRestrictions.setSubject( getGround().getGroundInstance());
        defRestrictions.setProperty( getOWLObjectProperty( "hasDoor"));
        defRestrictions.setValue( getOWLClass( "DOOR"));
        getDefinitionConcepts().add( defRestrictions);
        writeExpressionAxioms();
    }

    // implementing with a Some-restriction
    @Override
    protected SemanticRestriction.ApplyingPropertyRestriction getRestriction(){
        return new SemanticRestriction.ClassRestrictedOnSomeObject();
    }

}
