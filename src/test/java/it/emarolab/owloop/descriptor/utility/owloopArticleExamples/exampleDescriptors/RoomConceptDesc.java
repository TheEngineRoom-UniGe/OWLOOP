package it.emarolab.owloop.descriptor.utility.owloopArticleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;

public class RoomConceptDesc
        extends LocationConceptDesc {

    public RoomConceptDesc(OWLReferences onto) {

        super("ROOM", onto);
        addDefinitionRestriction( new SemanticRestriction.ClassRestrictedOnSomeObject()); // adding with Some-restriction
    }

    // overriding with a MaxCardinality-restriction
    @Override
    protected SemanticRestriction.ApplyingPropertyRestriction getRestriction(){
        SemanticRestriction.ClassRestrictedOnMaxObject definition = new SemanticRestriction.ClassRestrictedOnMaxObject();
        definition.setCardinality( 1);
        return definition;
    }
}
