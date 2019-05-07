package it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MORFullDataPropertyTest {

    private static FullDataPropertyDesc dataProperty;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        dataProperty = new FullDataPropertyDesc(
                "has_time", // the ground instance name
                "ontoName", // ontology reference name
                "src/test/resources/tboxTest.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/luca-buoncompagni/sit" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        dataProperty.saveOntology( "src/test/resources/dataPropertyTest.owl");
    }


    @Test
    public void subTest() throws Exception {
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.addSubDataProperty( "hasSubProperty");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.addSubDataProperty( "hasSubProperty");
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.removeSubDataProperty( "hasSubProperty");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.removeSubDataProperty( "hasSubProperty");
        dataProperty.writeExpressionAxioms();
        assertSemantic();

        dataProperty.addSubDataProperty( "subDataPropertyToBuild");
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described data property, sub test: " + dataProperty.buildSubDataProperty());
    }


    @Test
    public void superTest() throws Exception {
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.addSuperDataProperty("hasSuperProperty");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.addSuperDataProperty( "hasSuperProperty");
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.removeSuperDataProperty( "hasSuperProperty");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.removeSuperDataProperty( "hasSuperProperty");
        dataProperty.writeExpressionAxioms();
        assertSemantic();

        dataProperty.addSuperDataProperty( "superDataPropertyToBuild");
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described data property, super test: " + dataProperty.buildSuperDataProperty());
    }

    @Test
    public void disjointTest() throws Exception {
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.addDisjointDataProperty("hasDisjointProperty");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.addDisjointDataProperty( "hasDisjointProperty");
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.removeDisjointDataProperty( "hasDisjointProperty");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.removeDisjointDataProperty( "hasDisjointProperty");
        dataProperty.writeExpressionAxioms();
        assertSemantic();

        dataProperty.addDisjointDataProperty( "disjointDataPropertyToBuild");
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described data property, disjoint test: " + dataProperty.buildDisjointDataProperty());
    }

    @Test
    public void equivalentTest() throws Exception {
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.addEquivalentDataProperty("hasEquivalentProperty");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.addEquivalentDataProperty( "hasEquivalentProperty");
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.removeEquivalentDataProperty( "hasEquivalentProperty");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.removeEquivalentDataProperty( "hasEquivalentProperty");
        dataProperty.writeExpressionAxioms();
        assertSemantic();

        dataProperty.addEquivalentDataProperty( "equivalentDataPropertyToBuild");
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        System.out.println( "described data property, equivalent test: " + dataProperty.buildEquivalentDataProperty());
    }

    @Test
    public void domainTest() throws Exception{
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.addDomainClassRestriction("Sphere");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.addDomainClassRestriction( "Sphere");
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.removeDomainClassRestriction( "Sphere");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.removeDomainClassRestriction( "Sphere");
        dataProperty.writeExpressionAxioms();
        assertSemantic();

        dataProperty.addDomainClassRestriction( "ClassRestrictionTest");
        dataProperty.writeExpressionAxioms();
        assertSemantic();



        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.writeExpressionAxioms();
        assertSemantic();

        dataProperty.addDomainMinDataRestriction( "hasDomainDataPropertyTest", 3, Double.class);
        dataProperty.writeExpressionAxioms();
        assertSemantic();



        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
        assertSemantic();
        dataProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        dataProperty.addDomainMaxObjectRestriction( "hasDomainDataPropertyTest", 2, "Cone");
        dataProperty.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        System.out.println( "described data property, domain test: " + dataProperty.getDomainDataProperty());
    }


    @Test
    public void rangeTest() throws Exception{
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.addRangeDataRestriction( Long.class);
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.addRangeDataRestriction( Long.class);
        dataProperty.writeExpressionAxioms();
        assertSemantic();
        dataProperty.removeRangeDataRestriction( Long.class);
        dataProperty.readExpressionAxioms();
        assertSemantic();
        dataProperty.removeRangeDataRestriction( Long.class);
        // the reasoner infer no more disjoint here since there is no more range restriction
        dataProperty.writeExpressionAxiomsInconsistencySafe();
        assertSemantic();

        dataProperty.addRangeDataRestriction( Long.class);
        // the reasoner infer disjoint here since there is a range restriction
        dataProperty.writeExpressionAxiomsInconsistencySafe();
        assertSemantic();

        System.out.println( "described object property, range test: " + dataProperty.getRangeDataProperty());
    }




    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        assertEquals( dataProperty.getSubDataProperty(), dataProperty.querySubDataProperty());
        assertEquals( dataProperty.getSuperDataProperty(), dataProperty.querySuperDataProperty());
        assertEquals( dataProperty.getDisjointDataProperty(), dataProperty.queryDisjointDataProperty());
        assertEquals( dataProperty.getEquivalentDataProperty(), dataProperty.queryEquivalentDataProperty());
        assertEquals( dataProperty.getDomainDataProperty(), dataProperty.queryDomainDataProperty());
        assertEquals( dataProperty.getRangeDataProperty(), dataProperty.queryRangeDataProperty());
    }
}