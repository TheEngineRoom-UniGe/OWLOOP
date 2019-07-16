package it.emarolab.owloop.descriptorDebugging;

import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.FullObjectPropertyDesc;
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
public class FullObjectPropertyDescTest {

    public static String DEBUGGING_PATH = "src/test/resources/debug/";

    private static FullObjectPropertyDesc objectProperty;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        objectProperty = new FullObjectPropertyDesc(
                "isAboveOf", // the ground instance name
                "ontoName", // ontology reference name
                DEBUGGING_PATH + "ontology4debugging.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/luca-buoncompagni/sit" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        objectProperty.saveOntology( DEBUGGING_PATH + "objectPropertyTest.owl");
    }


    @Test
    public void subTest() throws Exception {
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.addSubObjectProperty( "hasSubProperty");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.addSubObjectProperty( "hasSubProperty");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.removeSubObjectProperty( "hasSubProperty");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeSubObjectProperty( "hasSubProperty");
        objectProperty.writeExpressionAxioms();
        assertSemantic();

        objectProperty.addSubObjectProperty( "subPropertyToBuild");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described object property, sub test: " + objectProperty.buildSubObjectProperty());
    }


    @Test
    public void superTest() throws Exception {
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.addSuperObjectProperty("hasSuperProperty");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.addSuperObjectProperty( "hasSuperProperty");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.removeSuperObjectProperty( "hasSuperProperty");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeSuperObjectProperty( "hasSuperProperty");
        objectProperty.writeReadExpressionAxioms(); // this is necessary when only test runs in series
        assertSemantic();

        objectProperty.addSuperObjectProperty( "superPropertyToBuild");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described object property, super test: " + objectProperty.buildSuperObjectProperty());
    }

    @Test
    public void disjointTest() throws Exception {
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.addDisjointObjectProperty("hasDisjointProperty");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.addDisjointObjectProperty( "hasDisjointProperty");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.removeDisjointObjectProperty( "hasDisjointProperty");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeDisjointObjectProperty( "hasDisjointProperty");
        objectProperty.writeExpressionAxioms();
        assertSemantic();

        objectProperty.addDisjointObjectProperty( "disjointPropertyToBuild");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described object property, disjoint test: " + objectProperty.buildDisjointObjectProperty());
    }

    @Test
    public void equivalentTest() throws Exception {
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.addEquivalentObjectProperty("hasEquivalentProperty");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.addEquivalentObjectProperty( "hasEquivalentProperty");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.removeEquivalentObjectProperty( "hasEquivalentProperty");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeEquivalentObjectProperty( "hasEquivalentProperty");
        objectProperty.writeExpressionAxioms();
        assertSemantic();

        objectProperty.addEquivalentObjectProperty( "equivalentPropertyToBuild");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described object property, equivalent test: " + objectProperty.buildEquivalentObjectProperty());
    }

    @Test
    public void inverseTest() throws Exception {
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.addInverseObjectProperty("hasInverseProperty");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.addInverseObjectProperty( "hasInverseProperty");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.removeInverseObjectProperty( "hasInverseProperty");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeInverseObjectProperty( "hasInverseProperty");
        objectProperty.writeExpressionAxioms();
        assertSemantic();

        objectProperty.addInverseObjectProperty( "inversePropertyToBuild");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described object property, inverse test: " + objectProperty.buildInverseObjectProperty());
    }

    @Test
    public void domainTest() throws Exception{
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.addDomainClassRestriction("Sphere");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.addDomainClassRestriction( "Sphere");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.removeDomainClassRestriction( "Sphere");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeDomainClassRestriction( "Sphere");
        objectProperty.writeExpressionAxioms();
        assertSemantic();

        objectProperty.addDomainClassRestriction( "ClassRestrictionTest");
        objectProperty.writeExpressionAxioms();
        assertSemantic();



        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.writeExpressionAxioms();
        assertSemantic();

        objectProperty.addDomainMinDataRestriction( "hasDomainPropertyTest", 3, Double.class);
        objectProperty.writeExpressionAxioms();
        assertSemantic();



        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.writeReadExpressionAxioms(); // the reasoner always infers here
        assertSemantic();
        objectProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.writeReadExpressionAxioms(); // the reasoner always infers here
        assertSemantic();

        objectProperty.addDomainMaxObjectRestriction( "hasDomainPropertyTest", 2, "Cone");
        objectProperty.writeReadExpressionAxioms(); // the reasoner always infers here
        assertSemantic();

        System.out.println( "described object property, domain test: " + objectProperty.getObjectPropertyDomainConcepts());
    }


    @Test
    public void rangeTest() throws Exception{
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.addRangeClassRestriction("Sphere");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.addRangeClassRestriction( "Sphere");
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.removeRangeClassRestriction( "Sphere");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeRangeClassRestriction( "Sphere");
        objectProperty.writeExpressionAxioms();
        assertSemantic();

        objectProperty.addRangeClassRestriction( "ClassRestrictionTest");
        objectProperty.writeExpressionAxioms();
        assertSemantic();



        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.addRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.addRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.removeRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.writeExpressionAxioms();
        assertSemantic();

        objectProperty.addRangeMinDataRestriction( "hasRangePropertyTest", 3, Double.class);
        objectProperty.writeExpressionAxioms();
        assertSemantic();



        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.addRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.writeExpressionAxioms();
        assertSemantic();
        objectProperty.addRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.addRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.writeReadExpressionAxioms(); // the reasoner always infers here
        assertSemantic();
        objectProperty.removeRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.writeReadExpressionAxioms(); // the reasoner always infers here
        assertSemantic();

        objectProperty.addRangeMaxObjectRestriction( "hasRangePropertyTest", 2, "Cone");
        objectProperty.writeReadExpressionAxioms(); // the reasoner always infers here
        assertSemantic();

        System.out.println( "described object property, range test: " + objectProperty.getObjectPropertyRangeConcepts());
    }



    int cnt = 0;
    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        System.out.println( ++cnt + " -> " + objectProperty);

        assertEquals( objectProperty.getSubObjectProperties(), objectProperty.querySubObjectProperties());
        assertEquals( objectProperty.getSuperObjectProperties(), objectProperty.querySuperObjectProperties());
        assertEquals( objectProperty.getDisjointObjectProperties(), objectProperty.queryDisjointObjectProperties());
        assertEquals( objectProperty.getEquivalentObjectProperties(), objectProperty.queryEquivalentObjectProperties());
        assertEquals( objectProperty.getObjectPropertyDomainConcepts(), objectProperty.queryObjectPropertyDomainConcepts());
        assertEquals( objectProperty.getObjectPropertyRangeConcepts(), objectProperty.queryObjectPropertyRangeConcepts());
        assertEquals( objectProperty.getInverseObjectProperties(), objectProperty.queryInverseObjectProperties());
    }
}