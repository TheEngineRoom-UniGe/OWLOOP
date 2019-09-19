package it.emarolab.owloop.descriptorDebugging;

import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A Unit Test script for individual descriptor.
 * <p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:        ${FILE} <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: DIBRIS, EMAROLab, University of Genoa. <br>
 * <b>date</b>:        10/07/19 <br>
 * </small></div>
 */
public class FullIndividualDescTest {

    public static String DEBUGGING_PATH = "src/test/resources/debug/";

    private static FullIndividualDesc individual;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        individual = new FullIndividualDesc(
                "individual-A", // the ground instance name
                "ontoName", // ontology reference name
                DEBUGGING_PATH + "ontology4debugging.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/luca-buoncompagni/sit" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        individual.saveOntology( DEBUGGING_PATH + "individualTest.owl");
    }

//    @Test
//    public void disjointTest() throws Exception{
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.addDisjointIndividual( "Disjoint-Individual");
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.writeExpressionAxioms();
//        assertSemantic();
//        individual.addDisjointIndividual( "Disjoint-Individual");
//        individual.writeExpressionAxioms();
//        System.out.println(individual.getDisjointIndividuals() + ", " + individual.queryDisjointIndividuals());
//        assertSemantic();
//        individual.removeDisjointIndividual( "Disjoint-Individual");
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.removeDisjointIndividual( "Disjoint-Individual");
//        individual.writeExpressionAxioms();
//        assertSemantic();
//
//        individual.addDisjointIndividual( "Disjoint-Individual-To-Build");
//        individual.writeExpressionAxioms();
//        assertSemantic();
//    }
//
//    @Test
//    public void equivalentTest() throws Exception{
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.addEquivalentIndividual( "Equivalent-Individual");
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.writeExpressionAxioms();
//        assertSemantic();
//        individual.addEquivalentIndividual( "Equivalent-Individual");
//        individual.writeExpressionAxioms();
//        assertSemantic();
//        individual.removeEquivalentIndividual( "Equivalent-Individual");
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.removeEquivalentIndividual( "Equivalent-Individual");
//        // the reasoner may infers other disjoint individuals
//        individual.writeReadExpressionAxioms();
//        assertSemantic();
//
//        individual.addEquivalentIndividual( "Equivalent-Individual-To-Build");
//        individual.writeExpressionAxioms();
//        assertSemantic();
//        System.out.println( "described individual, equivalent test: " + individual.buildEquivalentIndividuals());
//    }
//
//    @Test
//    public void typeTest() throws Exception{
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.addTypeIndividual( "Sphere");
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        assertSemantic();
//        individual.addTypeIndividual( "Sphere");
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        assertSemantic();
//        individual.removeTypeIndividual( "Sphere");
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.removeTypeIndividual( "Sphere");
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        assertSemantic();
//
//        individual.addTypeIndividual( "Individual-Type-To-Build");
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        assertSemantic();
//        System.out.println( "described individual, type test: " + individual.buildTypes());
//    }
//
    @Test
    public void objectLinkTest() throws Exception{
        individual.readAxioms();
        assertSemantic();
        individual.addObject( "isRightOf");
        individual.readAxioms();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addObject( "isRightOf", "Individual-B");
        individual.readAxioms();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addObject( "isRightOf", "Individual-B");
        individual.addObject( "isRightOf", "Individual-C");
        individual.writeAxiomsReasonReadAxioms(); // reasoner infers shape properties
        System.out.println(individual.getObjectProperties() + ", " + individual.queryObjectProperties());

        assertSemantic();
        individual.removeObject( "isRightOf", "Individual-C");
        individual.writeAxioms();
        assertSemantic();
        individual.removeObject( "isRightOf", "Individual-B");
        individual.writeAxiomsReasonReadAxioms(); // reasoner infers shape properties
        // here is not equal to the ontology since there aro not values but keep listen for that property
        //assertSemantic();

        individual.writeAxiomsReasonReadAxioms(); // reasoner infers shape properties
        System.out.println( "described individual, multi object test: " + individual.buildObjectProperties());

        System.out.println( "synchronised object properties: " + individual.getIndividualsFromObjectProperty( "isRightOf"));
    }
//
//    @Test
//    public void objectLinkSingletonTest() throws Exception{
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.addObject( "isRightOf", true);
//        individual.readExpressionAxioms();
//        // here is not equal to the ontology since there aro not values but it keeps listen for that property
//        //assertSemantic();
//        individual.addObject( "isRightOf", "Individual-B", true);
//        individual.readExpressionAxioms();
//        // here is not equal to the ontology since there aro not values but it keeps listen for that property
//        //assertSemantic();
//        individual.addObject( "isRightOf", "Individual-B", true);
//        individual.addObject( "isRightOf", "Individual-D",true);
//      //  for(DescriptorEntitySet.ObjectLinks i : individual.getObjectProperties())
//      //      assertEquals( i.getValues().size(), 1);
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        assertSemantic();
//        individual.removeObject( "isRightOf", "Individual-C");
//        individual.writeExpressionAxioms();
//        assertSemantic();
//        individual.removeObject( "isRightOf", "Individual-B");
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        // here is not equal to the ontology since there aro not values but keep listen for that property
//        //assertSemantic();
//
//        individual.addObject( "isLeftOf", "Individual-B", true);
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        System.out.println( "described individual, multi object test: " + individual.buildObjectProperties());
//
//        System.out.println( "synchronised object properties: " + individual.getIndividualFromObjectProperty( "isRightOf"));
//
//    }
//
//    @Test
//    public void dataLinkTest() throws Exception{
//
//        individual.addData( "has-geometric_centerX", 0.1);
//        individual.addData( "has-geometric_centerY", 0.2);
//        individual.addData( "has-geometric_centerZ", 0.3);
//        individual.addData( "has-sphere_radius", 0.01);
//        individual.writeExpressionAxioms();
//        //assertSemantic(); // here it fails because the reasoner infer new axiom that should be read. (**)
//        individual.readExpressionAxioms();
//        assertSemantic();
//
//        individual.removeData( "has-sphere_radius", 0.01);
//        // same case as above (**), use shortcut for reading and writing by manually update the reasoning
//        individual.writeReadExpressionAxioms();
//        assertSemantic();
//
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.addData( "hasProp");
//        individual.readExpressionAxioms();
//        // here is not equal to the ontology since there aro not values but it keeps listen for that property
//        //assertSemantic();
//        individual.addData( "hasProp", 2.3);
//        individual.readExpressionAxioms();
//        // here is not equal to the ontology since there aro not values but it keeps listen for that property
//        //assertSemantic();
//        individual.addData( "hasProp", 2.3);
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        assertSemantic();
//        individual.removeData( "hasProp", "string");
//        individual.writeExpressionAxioms();
//        assertSemantic();
//        individual.removeData( "hasProp", 2.3);
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        // here is not equal to the ontology since there aro not values but keep listen for that property
//        //assertSemantic();
//
//        individual.addData( "has-cone_height", 3.6);
//        individual.addData( "has-geometric_height");
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        System.out.println( "described individual, multi object test: " + individual.buildDataProperties());
//
//        System.out.println( "synchronised object properties: " + individual.getLiteralsFromDataProperty( "has-cone_height"));
//    }
//
//    @Test
//    public void dataLinkSingletonTest() throws Exception{
//        individual.readExpressionAxioms();
//        assertSemantic();
//        individual.addData( "hasProp", true);
//        individual.readExpressionAxioms();
//        // here is not equal to the ontology since there aro not values but it keeps listen for that property
//        //assertSemantic();
//        individual.addData( "hasProp", 2.3, true);
//        individual.readExpressionAxioms();
//        // here is not equal to the ontology since there aro not values but it keeps listen for that property
//        //assertSemantic();
//        individual.addData( "hasProp", 2.3, true);
//        individual.addData( "hasProp", 5.7, true);
//        for(DataLinks i : individual.getDataProperties())
//            assertEquals( i.getValues().size(), 1);
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        assertSemantic();
//        individual.removeData( "hasProp", "string");
//        individual.writeExpressionAxioms();
//        assertSemantic();
//        individual.removeData( "hasProp", 5.7);
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        // here is not equal to the ontology since there aro not values but keep listen for that property
//        //assertSemantic();
//
//        individual.addData( "has-cone_height", 3.6, true);
//        individual.addData( "has-geometric_height");
//        individual.writeReadExpressionAxioms(); // reasoner infers shape properties
//        System.out.println( "described individual, multi object test: " + individual.buildDataProperties());
//
//        System.out.println( "synchronised object properties: " + individual.getLiteralFromDataProperty( "has-cone_height"));
//    }

    int cnt = 0;
    public void assertSemantic(){ // asserts that the descriptor's internal state is equal to the state of the ontology
        System.out.println( ++cnt + " ->   " + individual);
        assertEquals( individual.getDisjointIndividuals(), individual.queryDisjointIndividuals());
        assertEquals( individual.getEquivalentIndividuals(), individual.queryEquivalentIndividuals());
        assertEquals( individual.getTypes(), individual.queryTypes());
        assertEquals( individual.getObjectProperties(), individual.queryObjectProperties());
        assertEquals( individual.getDataProperties(), individual.queryDataProperties());
    }
}