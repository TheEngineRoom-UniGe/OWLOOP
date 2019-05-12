package it.emarolab.owloop.descriptor.utility.individualDescriptor;

import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MORFullIndividualTest {

    private static FullIndividualDesc individual;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        individual = new FullIndividualDesc(
                "individualDescriptor-A", // the ground instance name
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
        individual.readExpressionAxioms();
        assertSemantic();
        individual.addDisjointIndividual( "Disjoint-Individual");
        individual.readExpressionAxioms();
        assertSemantic();
        individual.writeExpressionAxioms();
        assertSemantic();
        individual.addDisjointIndividual( "Disjoint-Individual");
        individual.writeExpressionAxioms();
        assertSemantic();
        individual.removeDisjointIndividual( "Disjoint-Individual");
        individual.readExpressionAxioms();
        assertSemantic();
        individual.removeDisjointIndividual( "Disjoint-Individual");
        individual.writeExpressionAxioms();
        assertSemantic();

        individual.addDisjointIndividual( "Disjoint-Individual-To-Build");
        individual.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described individualDescriptor, disjoint test: " + individual.buildDisjointIndividual());
    }

    @Test
    public void equivalentTest() throws Exception{
        individual.readExpressionAxioms();
        assertSemantic();
        individual.addEquivalentIndividual( "Equivalent-Individual");
        individual.readExpressionAxioms();
        assertSemantic();
        individual.writeExpressionAxioms();
        assertSemantic();
        individual.addEquivalentIndividual( "Equivalent-Individual");
        individual.writeExpressionAxioms();
        assertSemantic();
        individual.removeEquivalentIndividual( "Equivalent-Individual");
        individual.readExpressionAxioms();
        assertSemantic();
        individual.removeEquivalentIndividual( "Equivalent-Individual");
        // the reasoner may infers other disjoint individuals
        individual.writeExpressionAxiomsInconsistencySafe();
        assertSemantic();

        individual.addEquivalentIndividual( "Equivalent-Individual-To-Build");
        individual.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described individualDescriptor, equivalent test: " + individual.buildEquivalentIndividual());
    }

    @Test
    public void typeTest() throws Exception{
        individual.readExpressionAxioms();
        assertSemantic();
        individual.addTypeIndividual( "Sphere");
        individual.readExpressionAxioms();
        assertSemantic();
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.addTypeIndividual( "Sphere");
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.removeTypeIndividual( "Sphere");
        individual.readExpressionAxioms();
        assertSemantic();
        individual.removeTypeIndividual( "Sphere");
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();

        individual.addTypeIndividual( "Individual-Type-To-Build");
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        System.out.println( "described individualDescriptor, type test: " + individual.buildTypeIndividual());
    }

    @Test
    public void objectLinkTest() throws Exception{
        individual.readExpressionAxioms();
        assertSemantic();
        individual.addObject( "isRightOf");
        individual.readExpressionAxioms();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addObject( "isRightOf", "Individual-B");
        individual.readExpressionAxioms();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addObject( "isRightOf", "Individual-B");
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.removeObject( "isRightOf", "Individual-C");
        individual.writeExpressionAxioms();
        assertSemantic();
        individual.removeObject( "isRightOf", "Individual-B");
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        // here is not equal to the ontology since there aro not values but keep listen for that property
        //assertSemantic();

        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        System.out.println( "described individualDescriptor, multi object test: " + individual.buildObjectIndividual());

        System.out.println( "synchronised object properties: " + individual.getObjects( "isRightOf"));
    }

    @Test
    public void objectLinkSingletonTest() throws Exception{
        individual.readExpressionAxioms();
        assertSemantic();
        individual.addObject( "isRightOf", true);
        individual.readExpressionAxioms();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addObject( "isRightOf", "Individual-B", true);
        individual.readExpressionAxioms();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addObject( "isRightOf", "Individual-B", true);
        individual.addObject( "isRightOf", "Individual-D",true);
        for(DescriptorEntitySet.ObjectExpression i : individual.getObjectSemantics())
            assertEquals( i.getValues().size(), 1);
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.removeObject( "isRightOf", "Individual-C");
        individual.writeExpressionAxioms();
        assertSemantic();
        individual.removeObject( "isRightOf", "Individual-B");
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        // here is not equal to the ontology since there aro not values but keep listen for that property
        //assertSemantic();

        individual.addObject( "isLeftOf", "Individual-B", true);
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        System.out.println( "described individualDescriptor, multi object test: " + individual.buildObjectIndividual());

        System.out.println( "synchronised object properties: " + individual.getObject( "isRightOf"));

    }

    @Test
    public void dataLinkTest() throws Exception{
        individual.readExpressionAxioms();
        assertSemantic();
        individual.addData( "hasProp");
        individual.readExpressionAxioms();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addData( "hasProp", 2.3);
        individual.readExpressionAxioms();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addData( "hasProp", 2.3);
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.removeData( "hasProp", "string");
        individual.writeExpressionAxioms();
        assertSemantic();
        individual.removeData( "hasProp", 2.3);
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        // here is not equal to the ontology since there aro not values but keep listen for that property
        //assertSemantic();

        individual.addData( "has-cone_height", 3.6);
        individual.addData( "has-geometric_height");
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        System.out.println( "described individualDescriptor, multi object test: " + individual.buildDataIndividual());

        System.out.println( "synchronised object properties: " + individual.getLiterals( "has-cone_height"));
    }

    @Test
    public void dataLinkSingletonTest() throws Exception{
        individual.readExpressionAxioms();
        assertSemantic();
        individual.addData( "hasProp", true);
        individual.readExpressionAxioms();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addData( "hasProp", 2.3, true);
        individual.readExpressionAxioms();
        // here is not equal to the ontology since there aro not values but it keeps listen for that property
        //assertSemantic();
        individual.addData( "hasProp", 2.3, true);
        individual.addData( "hasProp", 5.7, true);
        for(DescriptorEntitySet.DataExpression i : individual.getDataExpressions())
            assertEquals( i.getValues().size(), 1);
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        assertSemantic();
        individual.removeData( "hasProp", "string");
        individual.writeExpressionAxioms();
        assertSemantic();
        individual.removeData( "hasProp", 5.7);
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        // here is not equal to the ontology since there aro not values but keep listen for that property
        //assertSemantic();

        individual.addData( "has-cone_height", 3.6, true);
        individual.addData( "has-geometric_height");
        individual.writeExpressionAxiomsInconsistencySafe(); // reasoner infers shape properties
        System.out.println( "described individualDescriptor, multi object test: " + individual.buildDataIndividual());

        System.out.println( "synchronised object properties: " + individual.getLiteral( "has-cone_height"));
    }


    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        assertEquals( individual.getDisjointIndividual(), individual.queryDisjointIndividual());
        assertEquals( individual.getEquivalentIndividual(), individual.queryEquivalentIndividual());
        assertEquals( individual.getTypeIndividual(), individual.queryTypeIndividual());
        assertEquals( individual.getObjectSemantics(), individual.queryObject());
        assertEquals( individual.getDataExpressions(), individual.queryDataIndividual());
    }
}