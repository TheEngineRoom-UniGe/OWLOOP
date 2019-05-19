package it.emarolab.owloop.descriptor.utility.owloopPaperTests.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;

public class CorridorConceptDesc extends DefConceptDesc {

    public CorridorConceptDesc(OWLReferences onto) {

        super("CORRIDOR", onto);
    }

    // implementing with a MinCardinality-restriction
    @Override
    protected SemanticRestriction.ClassRestrictedOnMinObject getRestriction(){
        SemanticRestriction.ClassRestrictedOnMinObject definition = new SemanticRestriction.ClassRestrictedOnMinObject();
        definition.setCardinality( 2);
        return definition;
    }

}
