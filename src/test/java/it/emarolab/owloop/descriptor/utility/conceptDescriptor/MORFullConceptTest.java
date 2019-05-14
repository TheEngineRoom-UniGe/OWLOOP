package it.emarolab.owloop.descriptor.utility.conceptDescriptor;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MORFullConceptTest {

    // todo solve problem on conceptDescriptor definition

    private static FullConceptDescriptor concept;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        concept = new FullConceptDescriptor(
                "Sphere", // the ground instance name
                "ontoName", // ontology reference name
                "src/test/resources/tboxTest.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/luca-buoncompagni/sit" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        concept.saveOntology( "src/test/resources/conceptTest.owl");
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
        System.out.println( "described conceptDescriptor, sub test: " + concept.buildSubConcept());
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
        System.out.println( "described conceptDescriptor, super test: " + concept.buildSuperConcept());
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
        System.out.println( "described conceptDescriptor, disjoint test: " + concept.buildDisjointConcept());
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
        System.out.println( "described conceptDescriptor, equivalent test: " + concept.buildEquivalentConcept());
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
        System.out.println( "described conceptDescriptor, definition test: " + concept.getDefinitionConcept());
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
        System.out.println( "described conceptDescriptor, equivalent test: " + concept.buildIndividualInstance());

    }

    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        assertEquals( concept.getSubConcept(), concept.querySubConcept());
        assertEquals( concept.getSuperConcept(), concept.querySuperConcept());
        assertEquals( concept.getDisjointConcept(), concept.queryDisjointConcept());
        assertEquals( concept.getEquivalentConcept(), concept.queryEquivalentConcept());
        assertEquals( concept.getIndividualInstance(), concept.queryIndividualInstance());
        assertEquals( concept.getDefinitionConcept(), concept.queryDefinitionConcept());
    }
}