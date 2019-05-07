package it.emarolab.owloop.descriptor.utility.owloopPaperTests.locationDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;

public class CorridorDescriptor extends BaseDescriptor<SemanticRestriction.ClassRestrictedOnMinObject> {

    public CorridorDescriptor( OWLReferences onto) {
        super("CORRIDOR", onto);
    }

    @Override
    protected SemanticRestriction.ClassRestrictedOnMinObject getRestriction(){
        SemanticRestriction.ClassRestrictedOnMinObject definition = new SemanticRestriction.ClassRestrictedOnMinObject();
        definition.setCardinality( 2);
        return definition;
    }

}
