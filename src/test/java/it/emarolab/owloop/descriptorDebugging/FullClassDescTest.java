package it.emarolab.owloop.descriptorDebugging;

import it.emarolab.owloop.descriptor.utility.classDescriptor.FullClassDesc;
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
public class FullClassDescTest {

    public static String DEBUGGING_PATH = "src/test/resources/debug/";

    private static FullClassDesc concept;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        concept = new FullClassDesc(
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
        concept.getOntologyReference();
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addSubClass( "SubClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.addSubClass( "SubClass");
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.removeSubClass( "SubClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeSubClass( "SubClass");
        concept.writeExpressionAxioms();
        assertSemantic();

        concept.addSubClass( "Plane");
        concept.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described concept, sub test: " + concept.buildSubClasses());
    }

    @Test
    public void superTest() throws Exception{
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addSuperClass( "SuperClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.addSuperClass( "SuperClass");
        // super class affect class definition during reasoning
        concept.writeReadExpressionAxioms();
        assertSemantic();
        concept.removeSuperClass( "SuperClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeSuperClass( "SuperClass");
        // super class affect class definition during reasoning
        concept.writeReadExpressionAxioms();
        assertSemantic();

        concept.addSuperClass( "Object");
        // super class affect class definition during reasoning
        concept.writeReadExpressionAxioms();
        assertSemantic();
        System.out.println( "described concept, super test: " + concept.buildSuperClasses());
    }

    @Test
    public void disjointTest() throws Exception{
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addDisjointClass( "DisjointClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.addDisjointClass( "DisjointClass");
        concept.writeReadExpressionAxioms();
        assertSemantic();
        concept.removeDisjointClass( "DisjointClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeDisjointClass( "DisjointClass");
        concept.writeReadExpressionAxioms();
        assertSemantic();

        concept.addDisjointClass( "Scene");
        // disjoint class affect sub classes during reasoning
        concept.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described concept, disjoint test: " + concept.buildDisjointClasses());
    }

    @Test
    public void equivalentTest() throws Exception{
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addEquivalentClass( "EquivalentClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.addEquivalentClass( "EquivalentClass");
        // equivalent class affect sub classes during reasoning
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.removeEquivalentClass( "EquivalentClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeEquivalentClass( "EquivalentClass");
        // equivalent class affect sub classes during reasoning
        concept.writeExpressionAxioms();
        assertSemantic();

        concept.addEquivalentClass( "EquivalentClass");
        // equivalent class affect sub classes during reasoning
        concept.writeReadExpressionAxioms();
        assertSemantic();
        System.out.println( "described concept, equivalent test: " + concept.buildEquivalentClasses());
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

        concept.addEquivalentClass( "EE");
        concept.addClassRestriction( "Scene1");
        concept.addOnlyDataRestriction( "hasPropTest2", Boolean.class);
        concept.writeReadExpressionAxioms(false);
        assertSemantic();

        for( FullClassDesc d : concept.buildEquivalentClasses())
            // you can use also: d = concept.buildEquivalentClasses().toArray()[0]
            if ( d.getGroundInstanceName().equals( "EE")) {
                d.addDisjointClass(concept.getInstance());
                d.getEquivalentClasses().clear();
                d.writeExpressionAxioms();
            }

        concept.removeClassRestriction( "Scene1");
        concept.removeClassRestriction( "Scene");// TODO remove scene from sub class
        concept.removeEquivalentClass( "EE");
        concept.writeReadExpressionAxioms(false);
        assertSemantic();

    }

    @Test
    public void classifyTest() throws Exception{
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addIndividual( "Individual-A");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.addIndividual( "Individual-A");
        // equivalent class affect sub classes during reasoning
        concept.writeExpressionAxioms();
        assertSemantic();
        concept.removeIndividual( "Individual-A");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeIndividual( "Individual-A");
        // equivalent class affect sub classes during reasoning
        concept.writeExpressionAxioms();
        assertSemantic();

        concept.setGroundInstance( "Parameter");
        concept.readExpressionAxioms();
        System.out.println( "described concept, equivalent test: " + concept.buildIndividuals());

    }

    int cnt = 0;
    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        System.out.println( ++cnt + " ->   " + concept);
        assertEquals( concept.getSubClasses(), concept.querySubClasses());
        assertEquals( concept.getSuperClasses(), concept.querySuperClasses());
        assertEquals( concept.getDisjointClasses(), concept.queryDisjointClasses());
        assertEquals( concept.getEquivalentClasses(), concept.queryEquivalentClasses());
        assertEquals( concept.getIndividuals(), concept.queryIndividuals());
        assertEquals( concept.getEquivalentRestrictions(), concept.queryEquivalentRestrictions());
    }
}