package it.emarolab.owloop.descriptorDebugging;

import it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor.FullDataPropertyDesc;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A Unit Test script for data property descriptor.
 * <p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: DIBRIS, EMAROLab, University of Genoa. <br>
 * <b>date</b>:        10/07/19 <br>
 * </small></div>
 *
 */
public class FullDataPropertyDescTest {

    public static String DEBUGGING_PATH = "src/test/resources/debug/";

    private static FullDataPropertyDesc dataProperty;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        dataProperty = new FullDataPropertyDesc(
                "has_time", // the ground instance name
                "ontoName", // ontology reference name
                DEBUGGING_PATH + "debug/ontology4debugging.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/luca-buoncompagni/sit" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        dataProperty.saveOntology( DEBUGGING_PATH + "dataPropertyTest.owl");
    }


    @Test
    public void subTest() throws Exception {
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.addSubDataProperty( "hasSubProperty");
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.writeAxioms();
        assertSemantic();
        dataProperty.addSubDataProperty( "hasSubProperty");
        dataProperty.writeAxioms();
        assertSemantic();
        dataProperty.removeSubDataProperty( "hasSubProperty");
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.removeSubDataProperty( "hasSubProperty");
        dataProperty.writeAxioms();
        assertSemantic();

        dataProperty.addSubDataProperty( "subDataPropertyToBuild");
        dataProperty.writeAxioms();
        assertSemantic();
        System.out.println( "described data property, sub test: " + dataProperty.buildSubDataProperties());
    }


    @Test
    public void superTest() throws Exception {
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.addSuperDataProperty("hasSuperProperty");
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.writeAxioms();
        assertSemantic();
        dataProperty.addSuperDataProperty( "hasSuperProperty");
        dataProperty.writeAxioms();
        assertSemantic();
        dataProperty.removeSuperDataProperty( "hasSuperProperty");
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.removeSuperDataProperty( "hasSuperProperty");
        dataProperty.writeAxioms();
        assertSemantic();

        dataProperty.addSuperDataProperty( "superDataPropertyToBuild");
        dataProperty.writeAxioms();
        assertSemantic();
        System.out.println( "described data property, super test: " + dataProperty.buildSuperDataProperties());
    }

    @Test
    public void disjointTest() throws Exception {
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.addDisjointDataProperty("hasDisjointProperty");
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.writeAxioms();
        assertSemantic();
        dataProperty.addDisjointDataProperty( "hasDisjointProperty");
        dataProperty.writeAxioms();
        assertSemantic();
        dataProperty.removeDisjointDataProperty( "hasDisjointProperty");
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.removeDisjointDataProperty( "hasDisjointProperty");
        dataProperty.writeAxioms();
        assertSemantic();

        dataProperty.addDisjointDataProperty( "disjointDataPropertyToBuild");
        dataProperty.writeAxioms();
        assertSemantic();
        System.out.println( "described data property, disjoint test: " + dataProperty.buildDisjointDataProperties());
    }

    @Test
    public void equivalentTest() throws Exception {
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.addEquivalentDataProperty("hasEquivalentProperty");
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.writeAxioms();
        assertSemantic();
        dataProperty.addEquivalentDataProperty( "hasEquivalentProperty");
        dataProperty.writeAxioms();
        assertSemantic();
        dataProperty.removeEquivalentDataProperty( "hasEquivalentProperty");
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.removeEquivalentDataProperty( "hasEquivalentProperty");
        dataProperty.writeAxioms();
        assertSemantic();

        dataProperty.addEquivalentDataProperty( "equivalentDataPropertyToBuild");
        dataProperty.writeAxioms();
        assertSemantic();
        System.out.println( "described data property, equivalent test: " + dataProperty.buildEquivalentDataProperties());
    }

    @Test
    public void domainTest() throws Exception{

        dataProperty.readAxioms();
        dataProperty.getDomainRestrictions().clear();
        dataProperty.writeAxioms();

        dataProperty.addDomainClassRestriction("Sphere");
        dataProperty.addDomainClassRestriction("Plane");
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();

        dataProperty.removeDomainClassRestriction("Sphere");
        dataProperty.removeDomainExactDataRestriction("has_time", 1, Long.class);
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();

        dataProperty.addDomainSomeDataRestriction("has_time", Long.class);
        dataProperty.addDomainSomeDataRestriction("has_time_stamp", String.class);
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();

        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.addDomainClassRestriction("Sphere");
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();
        dataProperty.addDomainClassRestriction( "Sphere");
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();
        dataProperty.removeDomainClassRestriction( "Sphere");
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.removeDomainClassRestriction( "Sphere");
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();

        dataProperty.addDomainClassRestriction( "ClassRestrictionTest");
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();



        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();
        dataProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();
        dataProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();

        dataProperty.addDomainMinDataRestriction( "hasDomainDataPropertyTest", 3, Double.class);
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();

        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.writeAxiomsReasonReadAxioms();
       assertSemantic();
        dataProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.writeAxiomsReasonReadAxioms(); // the reasoner always infers here
        assertSemantic();
        dataProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.readAxioms();
       assertSemantic();
        dataProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.writeAxiomsReasonReadAxioms(); // the reasoner always infers here
        assertSemantic();

        dataProperty.addDomainMaxObjectRestriction( "hasDomainDataPropertyTest1", 2, "Cone");
        dataProperty.writeAxiomsReasonReadAxioms(); // the reasoner always infers here
       assertSemantic();
        System.out.println( "described data property, domain test: " + dataProperty.getDomainRestrictions());

    }


    @Test
    public void rangeTest() throws Exception{
        dataProperty.setGroundInstance( "testPropRange");

        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.addRangeDataRestriction( Long.class);
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.writeAxioms();
        assertSemantic();
        dataProperty.addRangeDataRestriction( String.class);
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();
        dataProperty.removeRangeDataRestriction( String.class);
        dataProperty.readAxioms();
        assertSemantic();
        dataProperty.removeRangeDataRestriction( Integer.class);
        // the reasoner infer no more disjoint here since there is no more range restriction
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();

        dataProperty.addRangeDataRestriction( Boolean.class);
        // the reasoner infer disjoint here since there is a range restriction
        dataProperty.writeAxiomsReasonReadAxioms();
        assertSemantic();

        System.out.println( "described object property, range test: " + dataProperty.getRangeRestrictions());
    }

    int cnt = 0;
    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        System.out.println( ++cnt + " ->   " + dataProperty);
        assertEquals( dataProperty.getSubDataProperties(), dataProperty.querySubDataProperties());
        assertEquals( dataProperty.getSuperDataProperties(), dataProperty.querySuperDataProperties());
        assertEquals( dataProperty.getDisjointDataProperties(), dataProperty.queryDisjointDataProperties());
        assertEquals( dataProperty.getEquivalentDataProperties(), dataProperty.queryEquivalentDataProperties());
        assertEquals( dataProperty.getDomainRestrictions(), dataProperty.queryDomainRestrictions());
        assertEquals( dataProperty.getRangeRestrictions(), dataProperty.queryRangeRestrictions());
    }
}
