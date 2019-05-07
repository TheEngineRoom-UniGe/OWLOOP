package it.emarolab.owloop.descriptor.utility.owloopPaperTests.locationDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;

public class RoomDescriptor extends BaseDescriptor<SemanticRestriction.ClassRestrictedOnMaxObject> {

    public RoomDescriptor( OWLReferences onto) {
        super("ROOM", onto);
    }

    @Override
    protected SemanticRestriction.ClassRestrictedOnMaxObject getRestriction(){
        SemanticRestriction.ClassRestrictedOnMaxObject definition = new SemanticRestriction.ClassRestrictedOnMaxObject();
        definition.setCardinality( 1);
        return definition;
    }
}
