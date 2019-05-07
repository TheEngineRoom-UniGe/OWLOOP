package it.emarolab.owloop.descriptor.utility.owloopPaperTests.locationDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;

public class LocationDescriptor
        extends BaseDescriptor<SemanticRestriction.ClassRestrictedOnSomeObject> {

    public LocationDescriptor(OWLReferences onto) {
        super("LOCATION", onto);
    }

    protected SemanticRestriction.ClassRestrictedOnSomeObject getRestriction(){
        return new SemanticRestriction.ClassRestrictedOnSomeObject();
    }

}
