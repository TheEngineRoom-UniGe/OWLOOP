package it.emarolab.owloopArticleExamples.exampleDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;

public class CorridorConceptDesc
        extends LocationConceptDesc {

    public CorridorConceptDesc(OWLReferences onto) {

        super("CORRIDOR", onto);
    }

    // overriding with a MinCardinality-restriction
    @Override
    protected SemanticRestriction.ClassRestrictedOnMinObject getRestriction(){
        SemanticRestriction.ClassRestrictedOnMinObject definition = new SemanticRestriction.ClassRestrictedOnMinObject();
        definition.setCardinality( 2);
        return definition;
    }

}
