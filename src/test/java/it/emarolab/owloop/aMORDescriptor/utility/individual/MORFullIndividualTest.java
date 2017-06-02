package it.emarolab.owloop.aMORDescriptor.utility.individual;

import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MORFullIndividualTest {

    private static MORFullIndividual individual;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        individual = new MORFullIndividual(
                "individual-A", // the ground instance name
                "ontoName", // ontology reference name
                "src/test/resources/tboxTest.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/luca-buoncompagni/sit" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        individual.saveOntology( "src/test/resources/individualTest.owl");
    }

    @Test
    public void disjointTest() throws Exception{
        individual.readSemantic();
        assertSemantic();
        individual.addDisjointIndividual( "Disjoint-Individual");
        individual.readSemantic();
        assertSemantic();
        individual.writeSemantic();
        assertSemantic();
        individual.addDisjointIndividual( "Disjoint-Individual");
        individual.writeSemantic();
        assertSemantic();
        individual.removeDisjointIndividual( "Disjoint-Individual");
        individual.readSemantic();
        assertSemantic();
        individual.removeDisjointIndividual( "Disjoint-Individual");
        individual.writeSemantic();
        assertSemantic();

        individual.addDisjointIndividual( "Disjoint-Individual-To-Build");
        individual.writeSemantic();
        assertSemantic();
        System.out.println( "described individual, disjoint test: " + individual.buildDisjointIndividual());
    }

    @Test
    public void equivalentTest() throws Exception{
        individual.readSemantic();
        assertSemantic();
        individual.addEquivalentIndividual( "Equivalent-Individual");
        individual.readSemantic();
        assertSemantic();
        individual.writeSemantic();
        assertSemantic();
        individual.addEquivalentIndividual( "Equivalent-Individual");
        individual.writeSemantic();
        assertSemantic();
        individual.removeEquivalentIndividual( "Equivalent-Individual");
        individual.readSemantic();
        assertSemantic();
        individual.removeEquivalentIndividual( "Equivalent-Individual");
        // the reasoner may infers other disjoint individuals
        individual.writeSemanticInconsistencySafe();
        assertSemantic();

        individual.addEquivalentIndividual( "Equivalent-Individual-To-Build");
        individual.writeSemantic();
        assertSemantic();
        System.out.println( "described individual, equivalent test: " + individual.buildEquivalentIndividual());
    }

    @Test
    public void typeTest() throws Exception{
        individual.readSemantic();
        assertSemantic();
        individual.addTypeIndividual( "Sphere");
        individual.readSemantic();
        assertSemantic();
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.addTypeIndividual( "Sphere");
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.removeTypeIndividual( "Sphere");
        individual.readSemantic();
        assertSemantic();
        individual.removeTypeIndividual( "Sphere");
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();

        individual.addTypeIndividual( "Individual-Type-To-Build");
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        System.out.println( "described individual, type test: " + individual.buildTypeIndividual());
    }

    @Test
    public void objectLinkTest() throws Exception{
        individual.readSemantic();
        assertSemantic();
        individual.addObject( "isRightOf");
        individual.readSemantic();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addObject( "isRightOf", "Individual-B");
        individual.readSemantic();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addObject( "isRightOf", "Individual-B");
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.removeObject( "isRightOf", "Individual-C");
        individual.writeSemantic();
        assertSemantic();
        individual.removeObject( "isRightOf", "Individual-B");
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        // here is not equal to the ontology since there aro not values but keep listen for that property
        //assertSemantic();

        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        System.out.println( "described individual, multi object test: " + individual.buildObjectIndividual());

        System.out.println( "synchronised object properties: " + individual.getObjects( "isRightOf"));
    }

    @Test
    public void objectLinkSingletonTest() throws Exception{
        individual.readSemantic();
        assertSemantic();
        individual.addObject( "isRightOf", true);
        individual.readSemantic();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addObject( "isRightOf", "Individual-B", true);
        individual.readSemantic();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addObject( "isRightOf", "Individual-B", true);
        individual.addObject( "isRightOf", "Individual-D",true);
        for(MORAxioms.ObjectSemantic i : individual.getObjectSemantics())
            assertEquals( i.getValues().size(), 1);
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.removeObject( "isRightOf", "Individual-C");
        individual.writeSemantic();
        assertSemantic();
        individual.removeObject( "isRightOf", "Individual-B");
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        // here is not equal to the ontology since there aro not values but keep listen for that property
        //assertSemantic();

        individual.addObject( "isLeftOf", "Individual-B", true);
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        System.out.println( "described individual, multi object test: " + individual.buildObjectIndividual());

        System.out.println( "synchronised object properties: " + individual.getObject( "isRightOf"));

    }

    @Test
    public void dataLinkTest() throws Exception{
        individual.readSemantic();
        assertSemantic();
        individual.addData( "hasProp");
        individual.readSemantic();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addData( "hasProp", 2.3);
        individual.readSemantic();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addData( "hasProp", 2.3);
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.removeData( "hasProp", "string");
        individual.writeSemantic();
        assertSemantic();
        individual.removeData( "hasProp", 2.3);
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        // here is not equal to the ontology since there aro not values but keep listen for that property
        //assertSemantic();

        individual.addData( "has-cone_height", 3.6);
        individual.addData( "has-geometric_height");
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        System.out.println( "described individual, multi object test: " + individual.buildDataIndividual());

        System.out.println( "synchronised object properties: " + individual.getLiterals( "has-cone_height"));
    }

    @Test
    public void dataLinkSingletonTest() throws Exception{
        individual.readSemantic();
        assertSemantic();
        individual.addData( "hasProp", true);
        individual.readSemantic();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addData( "hasProp", 2.3, true);
        individual.readSemantic();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addData( "hasProp", 2.3, true);
        individual.addData( "hasProp", 5.7, true);
        for(MORAxioms.DataSemantic i : individual.getDataSemantics())
            assertEquals( i.getValues().size(), 1);
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.removeData( "hasProp", "string");
        individual.writeSemantic();
        assertSemantic();
        individual.removeData( "hasProp", 5.7);
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        // here is not equal to the ontology since there aro not values but keep listen for that property
        //assertSemantic();

        individual.addData( "has-cone_height", 3.6, true);
        individual.addData( "has-geometric_height");
        individual.writeSemanticInconsistencySafe(); // reasoner infers shape properties
        System.out.println( "described individual, multi object test: " + individual.buildDataIndividual());

        System.out.println( "synchronised object properties: " + individual.getLiteral( "has-cone_height"));
    }


    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        assertEquals( individual.getDisjointIndividual(), individual.queryDisjointIndividual());
        assertEquals( individual.getEquivalentIndividual(), individual.queryEquivalentIndividual());
        assertEquals( individual.getTypeIndividual(), individual.queryTypeIndividual());
        assertEquals( individual.getObjectSemantics(), individual.queryObject());
        assertEquals( individual.getDataSemantics(), individual.queryDataIndividual());
    }
}