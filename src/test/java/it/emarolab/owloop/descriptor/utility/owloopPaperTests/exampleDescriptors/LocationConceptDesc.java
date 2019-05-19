package it.emarolab.owloop.descriptor.utility.owloopPaperTests.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;

public class LocationConceptDesc
        extends DefConceptDesc {

    public LocationConceptDesc(OWLReferences onto) {

        super("LOCATION", onto);
    }
    LocationConceptDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }

    // implementing with a Some-restriction
    @Override
    protected SemanticRestriction.ApplyingPropertyRestriction getRestriction(){
        return new SemanticRestriction.ClassRestrictedOnSomeObject();
    }

}
