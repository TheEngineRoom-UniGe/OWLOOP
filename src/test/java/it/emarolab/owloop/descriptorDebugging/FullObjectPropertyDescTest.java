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
                DEBUGGING_PATH + "debug/ontology4debugging.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/luca-buoncompagni/sit" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        objectProperty.saveOntology( DEBUGGING_PATH + "objectPropertyTest.owl");
    }


    @Test
    public void subTest() throws Exception {
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.addSubObjectProperty( "hasSubProperty");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addSubObjectProperty( "hasSubProperty");
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.removeSubObjectProperty( "hasSubProperty");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.removeSubObjectProperty( "hasSubProperty");
        objectProperty.writeAxioms();
        assertSemantic();

        objectProperty.addSubObjectProperty( "subPropertyToBuild");
        objectProperty.writeAxioms();
        assertSemantic();
        System.out.println( "described object property, sub test: " + objectProperty.buildSubObjectProperties());
    }


    @Test
    public void superTest() throws Exception {
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.addSuperObjectProperty("hasSuperProperty");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addSuperObjectProperty( "hasSuperProperty");
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.removeSuperObjectProperty( "hasSuperProperty");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.removeSuperObjectProperty( "hasSuperProperty");
        objectProperty.writeAxiomsReasonReadAxioms(); // this is necessary when only test runs in series
        assertSemantic();

        objectProperty.addSuperObjectProperty( "superPropertyToBuild");
        objectProperty.writeAxioms();
        assertSemantic();
        System.out.println( "described object property, super test: " + objectProperty.buildSuperObjectProperties());
    }

    @Test
    public void disjointTest() throws Exception {
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.addDisjointObjectProperty("hasDisjointProperty");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addDisjointObjectProperty( "hasDisjointProperty");
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.removeDisjointObjectProperty( "hasDisjointProperty");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.removeDisjointObjectProperty( "hasDisjointProperty");
        objectProperty.writeAxioms();
        assertSemantic();

        objectProperty.addDisjointObjectProperty( "disjointPropertyToBuild");
        objectProperty.writeAxioms();
        assertSemantic();
        System.out.println( "described object property, disjoint test: " + objectProperty.buildDisjointObjectProperties());
    }

    @Test
    public void equivalentTest() throws Exception {
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.addEquivalentObjectProperty("hasEquivalentProperty");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addEquivalentObjectProperty( "hasEquivalentProperty");
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.removeEquivalentObjectProperty( "hasEquivalentProperty");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.removeEquivalentObjectProperty( "hasEquivalentProperty");
        objectProperty.writeAxioms();
        assertSemantic();

        objectProperty.addEquivalentObjectProperty( "equivalentPropertyToBuild");
        objectProperty.writeAxioms();
        assertSemantic();
        System.out.println( "described object property, equivalent test: " + objectProperty.buildEquivalentObjectProperties());
    }

    @Test
    public void inverseTest() throws Exception {
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.addInverseObjectProperty("hasInverseProperty");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addInverseObjectProperty( "hasInverseProperty");
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.removeInverseObjectProperty( "hasInverseProperty");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.removeInverseObjectProperty( "hasInverseProperty");
        objectProperty.writeAxioms();
        assertSemantic();

        objectProperty.addInverseObjectProperty( "inversePropertyToBuild");
        objectProperty.writeAxioms();
        assertSemantic();
        System.out.println( "described object property, inverse test: " + objectProperty.buildInverseObjectProperties());
    }

    @Test
    public void domainTest() throws Exception{
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.addDomainClassRestriction("Sphere");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addDomainClassRestriction( "Sphere");
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.removeDomainClassRestriction( "Sphere");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.removeDomainClassRestriction( "Sphere");
        objectProperty.writeAxioms();
        assertSemantic();

        objectProperty.addDomainClassRestriction( "ClassRestrictionTest");
        objectProperty.writeAxioms();
        assertSemantic();



        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.writeAxioms();
        assertSemantic();

        objectProperty.addDomainMinDataRestriction( "hasDomainPropertyTest2", 3, Double.class);
        objectProperty.writeAxioms();
        assertSemantic();



        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.writeAxiomsReasonReadAxioms(); // the reasoner always infers here
        assertSemantic();
        objectProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.writeAxiomsReasonReadAxioms(); // the reasoner always infers here
        assertSemantic();

        objectProperty.addDomainMaxObjectRestriction( "hasDomainPropertyTest", 2, "Cone");
        objectProperty.writeAxiomsReasonReadAxioms(); // the reasoner always infers here
        assertSemantic();

        System.out.println( "described object property, domain test: " + objectProperty.getDomainRestrictions());
    }


    @Test
    public void rangeTest() throws Exception{
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.addRangeClassRestriction("Sphere");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addRangeClassRestriction( "Sphere");
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.removeRangeClassRestriction( "Sphere");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.removeRangeClassRestriction( "Sphere");
        objectProperty.writeAxioms();
        assertSemantic();

        objectProperty.addRangeClassRestriction( "ClassRestrictionTest");
        objectProperty.writeAxioms();
        assertSemantic();

        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.addRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.removeRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.removeRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.writeAxioms();
        assertSemantic();

        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.addRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");

        objectProperty.removeRangeClassRestriction( "GeometricPrimitive");
        objectProperty.writeAxioms();
        assertSemantic();
        objectProperty.addRangeExactObjectRestriction( "hasRangeProperty", 4, "Plane");
        objectProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();

        objectProperty.removeRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.readAxioms();
        assertSemantic();
        objectProperty.removeRangeExactObjectRestriction( "hasRangeProperty", 4, "Plane");
        objectProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();


        objectProperty.addRangeMaxObjectRestriction( "hasRangePropertyTest", 2, "Cone");
        objectProperty.writeAxiomsReasonReadAxioms(); // the reasoner always infers here
        assertSemantic();

        System.out.println( "described object property, range test: " + objectProperty.getRangeRestrictions());
    }

    int cnt = 0;
    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        System.out.println( ++cnt + " ->   " + objectProperty);
        assertEquals( objectProperty.getSubObjectProperties(), objectProperty.querySubObjectProperties());
        assertEquals( objectProperty.getSuperObjectProperties(), objectProperty.querySuperObjectProperties());
        assertEquals( objectProperty.getDisjointObjectProperties(), objectProperty.queryDisjointObjectProperties());
        assertEquals( objectProperty.getEquivalentObjectProperties(), objectProperty.queryEquivalentObjectProperties());
        assertEquals( objectProperty.getDomainRestrictions(), objectProperty.queryDomainRestrictions());
        assertEquals( objectProperty.getRangeRestrictions(), objectProperty.queryRangeRestrictions());
        assertEquals( objectProperty.getInverseObjectProperties(), objectProperty.queryInverseObjectProperties());
    }
}