package it.emarolab.owloop.descriptorDebugging;

import it.emarolab.owloop.descriptor.utility.conceptDescriptor.FullConceptDesc;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A Unit Test script for object property descriptor.
 * <p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:        ${FILE} <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: DIBRIS, EMAROLab, University of Genoa. <br>
 * <b>date</b>:        10/07/19 <br>
 * </small></div>
 */
public class FullConceptDescTest {

    public static String DEBUGGING_PATH = "src/test/resources/debug/";

    private static FullConceptDesc concept;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        concept = new FullConceptDesc(
                "Sphere", // the ground instance name
                "ontoName", // ontology reference name
                DEBUGGING_PATH + "ontology4debugging.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/luca-buoncompagni/sit" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        concept.saveOntology( DEBUGGING_PATH + "conceptTest.owl");
    }

    @Test
    public void subTest() throws Exception{
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addSubConcept( "SubClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.addSubConcept( "SubClass");
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.removeSubConcept( "SubClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeSubConcept( "SubClass");
        concept.writeExpressionAxioms();
        assertSemantic();

        concept.addSubConcept( "Plane");
        concept.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described concept, sub test: " + concept.buildSubConcept());
    }

    @Test
    public void superTest() throws Exception{
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addSuperConcept( "SuperClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.addSuperConcept( "SuperClass");
        // super class affect class definition during reasoning
        concept.writeReadExpressionAxioms();
        assertSemantic();
        concept.removeSuperConcept( "SuperClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeSuperConcept( "SuperClass");
        // super class affect class definition during reasoning
        concept.writeReadExpressionAxioms();
        assertSemantic();

        concept.addSuperConcept( "Object");
        // super class affect class definition during reasoning
        concept.writeReadExpressionAxioms();
        assertSemantic();
        System.out.println( "described concept, super test: " + concept.buildSuperConcept());
    }

    @Test
    public void disjointTest() throws Exception{
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addDisjointConcept( "DisjointClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.addDisjointConcept( "DisjointClass");
        concept.writeReadExpressionAxioms();
        assertSemantic();
        concept.removeDisjointConcept( "DisjointClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeDisjointConcept( "DisjointClass");
        concept.writeReadExpressionAxioms();
        assertSemantic();

        concept.addDisjointConcept( "Scene");
        // disjoint class affect sub classes during reasoning
        concept.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described concept, disjoint test: " + concept.buildDisjointConcept());
    }

    @Test
    public void equivalentTest() throws Exception{
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addEquivalentConcept( "EquivalentClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.addEquivalentConcept( "EquivalentClass");
        // equivalent class affect sub classes during reasoning
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.removeEquivalentConcept( "EquivalentClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeEquivalentConcept( "EquivalentClass");
        // equivalent class affect sub classes during reasoning
        concept.writeExpressionAxioms();
        assertSemantic();

        concept.addEquivalentConcept( "EquivalentClass");
        // equivalent class affect sub classes during reasoning
        concept.writeReadExpressionAxioms();
        assertSemantic();
        System.out.println( "described concept, equivalent test: " + concept.buildEquivalentConcept());
    }

    @Test
    public void defineTest() throws Exception {

        concept.setGroundInstance( "ToRestrict");

        concept.addExactDataRestriction( "defines-th_parallelism", 1, Float.class);
        concept.writeReadExpressionAxioms();
        assertSemantic();

        concept.clearAll();
        concept.writeExpressionAxioms();
        assertSemantic();

        concept.addSomeObjectRestrcition( "isAboveOf", "Cone");
        concept.writeReadExpressionAxioms(false); // bug when saving on file but fine at runtime
        assertSemantic();

        concept.addClassRestriction( "Parameter");
        concept.writeReadExpressionAxioms( false);
        assertSemantic();

        concept.removeClassRestriction( "Parameter");
        concept.writeReadExpressionAxioms( false);
        assertSemantic();

        concept.addExactDataRestriction( "defines-th_parallelism", 1, Float.class);
        concept.addClassRestriction( "AA");
        concept.writeReadExpressionAxioms( false);
        assertSemantic();

        concept.removeSomeObjectRestrcition( "isAboveOf", "Cone");
        concept.writeReadExpressionAxioms(false); // bug when saving on file but fine at runtime
        assertSemantic();

        concept.addMaxDataRestriction( "hasPropTest", 5, Integer.class);
        concept.addClassRestriction( "AA");
        concept.writeReadExpressionAxioms( false);
        assertSemantic();

        concept.clearAll();
        concept.addClassRestriction( "Scene");
        concept.addMinDataRestriction( "hasPropTest", 5, String.class);
        concept.writeReadExpressionAxioms(false);
        assertSemantic();

        concept.addEquivalentConcept( "EE");
        concept.addClassRestriction( "Scene1");
        concept.addOnlyDataRestriction( "hasPropTest2", Boolean.class);
        concept.writeReadExpressionAxioms(false);
        assertSemantic();

        for( FullConceptDesc d : concept.buildEquivalentConcept())
            // you can use also: d = concept.buildEquivalentConcept().toArray()[0]
            if ( d.getGroundInstanceName().equals( "EE")) {
                d.addDisjointConcept(concept.getInstance());
                d.getEquivalentConcepts().clear();
                d.writeExpressionAxioms();
            }

        concept.removeClassRestriction( "Scene1");
        concept.removeClassRestriction( "Scene");// TODO remove scene from sub class
        concept.removeEquivalentConcept( "EE");
        concept.writeReadExpressionAxioms(false);
        assertSemantic();

    }

    @Test
    public void classifyTest() throws Exception{
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addIndividualClassified( "Individual-A");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.addIndividualClassified( "Individual-A");
        // equivalent class affect sub classes during reasoning
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.removeIndividualClassified( "Individual-A");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeIndividualClassified( "Individual-A");
        // equivalent class affect sub classes during reasoning
        concept.writeExpressionAxioms();
        assertSemantic();

        concept.setGroundInstance( "Parameter");
        concept.readExpressionAxioms();
        System.out.println( "described concept, equivalent test: " + concept.buildIndividualInstances());

    }

    int cnt = 0;
    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        System.out.println( ++cnt + " ->   " + concept);
        assertEquals( concept.getSubConcepts(), concept.querySubConcepts());
        assertEquals( concept.getSuperConcepts(), concept.querySuperConcepts());
        assertEquals( concept.getDisjointConcepts(), concept.queryDisjointConcepts());
        assertEquals( concept.getEquivalentConcepts(), concept.queryEquivalentConcepts());
        assertEquals( concept.getIndividualInstances(), concept.queryIndividualInstances());
        assertEquals( concept.getDefinitionConcepts(), concept.queryDefinitionConcepts());
    }
}