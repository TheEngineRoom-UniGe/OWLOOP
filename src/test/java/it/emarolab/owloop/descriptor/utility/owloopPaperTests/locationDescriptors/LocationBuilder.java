package it.emarolab.owloop.descriptor.utility.owloopPaperTests.locationDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
import org.junit.Before;
import org.junit.Test;

public class LocationBuilder {

    private OWLReferences ontoref;

    @Before // called a before every @Test
    public void setUp() {

        it.emarolab.amor.owlDebugger.Logger.setPrintOnConsole( false);

        //OWLReferences
        ontoref = OWLReferencesInterface.OWLReferencesContainer.newOWLReferencesCreatedWithPellet(
                "owloopTest", // ontology reference name
                "/home/luca-bnc/textLocationBuilder.owl", // the ontology file path
                "http://www.semanticweb.org/yusha_temporary/owloopTestOntology", // the ontology IRI path
                true
        );
    }

    @Test
    public void LocationBuilder() {
        LocationDescriptor locationDescriptor = new LocationDescriptor( ontoref);
        CorridorDescriptor corridorDescriptor = new CorridorDescriptor( ontoref);
        RoomDescriptor roomDescriptor = new RoomDescriptor( ontoref);

        System.out.println( locationDescriptor);
        System.out.println( corridorDescriptor);
        System.out.println( roomDescriptor);

        ontoref.saveOntology();
    }

}
