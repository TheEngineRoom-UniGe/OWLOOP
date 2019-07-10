package it.emarolab.owloop.descriptor.utility.conceptDescriptor;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
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

    public static String DEBUGGING_PATH = "src/test/resources/tests/";

    // todo solve problem on concept definition

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
        concept.writeExpressionAxiomsInconsistencySafe();
        assertSemantic();
        concept.removeSuperConcept( "SuperClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeSuperConcept( "SuperClass");
        // super class affect class definition during reasoning
        concept.writeExpressionAxiomsInconsistencySafe();
        assertSemantic();

        concept.addSuperConcept( "Object");
        // super class affect class definition during reasoning
        concept.writeExpressionAxiomsInconsistencySafe();
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
        concept.writeExpressionAxiomsInconsistencySafe();
        assertSemantic();
        concept.removeDisjointConcept( "DisjointClass");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.removeDisjointConcept( "DisjointClass");
        concept.writeExpressionAxiomsInconsistencySafe();
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
        concept.writeExpressionAxiomsInconsistencySafe();
        assertSemantic();
        System.out.println( "described concept, equivalent test: " + concept.buildEquivalentConcept());
    }

    @Test @Ignore
    public void defineTest() throws Exception{

        concept.setInstance( "ToRestrict");

        concept.readExpressionAxioms();
        assertSemantic();
        concept.addClassRestriction( "ClassRestriction");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner considers also all super class as equivalent classes
        assertSemantic();
        concept.addClassRestriction( "ClassRestriction");
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner considers also all super class as equivalent classes
        assertSemantic();
        concept.addClassRestriction( "ClassRestriction");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addClassRestriction( "ClassRestriction");
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner considers also all super class as equivalent classes
        assertSemantic();

        concept.addClassRestriction( "ClassRestriction");
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner considers also all super class as equivalent classes
        assertSemantic();



        concept.readExpressionAxioms();
        assertSemantic();
        concept.addExactDataRestriction( "hasRestrictionProperty", 3, Long.class);
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner considers also all super class as equivalent classes
        assertSemantic();
        concept.addExactDataRestriction( "hasRestrictionProperty", 3, Long.class);
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner considers also all super class as equivalent classes
        assertSemantic();
        concept.addExactDataRestriction( "hasRestrictionProperty", 3, Long.class);
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addExactDataRestriction( "hasRestrictionProperty", 3, Long.class);
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner considers also all super class as equivalent classes
        concept.removeExactDataRestriction( "hasRestrictionProperty", 3, Long.class);
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner considers also all super class as equivalent classes
        assertSemantic();

        concept.addExactDataRestriction( "hasRestrictionPropertyTest", 3, Double.class);
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner considers also all super class as equivalent classes
        assertSemantic();



        concept.readExpressionAxioms();
        assertSemantic();
        concept.addMinObjectRestriction( "hasRestrictionProperty", 3, "Restricting");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner considers also all super class as equivalent classes
        assertSemantic();
        concept.addMinObjectRestriction( "hasRestrictionProperty", 3, "Restricting");
        concept.addMinObjectRestriction( "hasRestrictionProperty", 3, "Restricting");
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
        assertSemantic();
        concept.addMinObjectRestriction( "hasRestrictionProperty", 3, "Restricting");
        concept.readExpressionAxioms();
        assertSemantic();
        concept.addMinObjectRestriction( "hasRestrictionProperty", 3, "Restricting");
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        concept.addMaxObjectRestriction( "hasRestrictionPropertyTest", 2, "Restricting");
        concept.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
        assertSemantic();
        System.out.println( "described concept, definition test: " + concept.getDefinitionConcepts());
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

        concept.setInstance( "Parameter");
        concept.readExpressionAxioms();
        System.out.println( "described concept, equivalent test: " + concept.buildIndividualInstances());

    }

    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        assertEquals( concept.getSubConcepts(), concept.querySubConcepts());
        assertEquals( concept.getSuperConcepts(), concept.querySuperConcepts());
        assertEquals( concept.getDisjointConcepts(), concept.queryDisjointConcepts());
        assertEquals( concept.getEquivalentConcepts(), concept.queryEquivalentConcepts());
        assertEquals( concept.getIndividualInstances(), concept.queryIndividualInstances());
        assertEquals( concept.getDefinitionConcepts(), concept.queryDefinitionConcepts());
    }
}