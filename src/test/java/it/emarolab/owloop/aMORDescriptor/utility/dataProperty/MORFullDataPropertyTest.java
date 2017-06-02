package it.emarolab.owloop.aMORDescriptor.utility.dataProperty;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MORFullDataPropertyTest {

    private static MORFullDataProperty dataProperty;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        dataProperty = new MORFullDataProperty(
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
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.addSubDataProperty( "hasSubProperty");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.addSubDataProperty( "hasSubProperty");
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.removeSubDataProperty( "hasSubProperty");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.removeSubDataProperty( "hasSubProperty");
        dataProperty.writeSemantic();
        assertSemantic();

        dataProperty.addSubDataProperty( "subDataPropertyToBuild");
        dataProperty.writeSemantic();
        assertSemantic();
        System.out.println( "described data property, sub test: " + dataProperty.buildSubDataProperty());
    }


    @Test
    public void superTest() throws Exception {
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.addSuperDataProperty("hasSuperProperty");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.addSuperDataProperty( "hasSuperProperty");
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.removeSuperDataProperty( "hasSuperProperty");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.removeSuperDataProperty( "hasSuperProperty");
        dataProperty.writeSemantic();
        assertSemantic();

        dataProperty.addSuperDataProperty( "superDataPropertyToBuild");
        dataProperty.writeSemantic();
        assertSemantic();
        System.out.println( "described data property, super test: " + dataProperty.buildSuperDataProperty());
    }

    @Test
    public void disjointTest() throws Exception {
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.addDisjointDataProperty("hasDisjointProperty");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.addDisjointDataProperty( "hasDisjointProperty");
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.removeDisjointDataProperty( "hasDisjointProperty");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.removeDisjointDataProperty( "hasDisjointProperty");
        dataProperty.writeSemantic();
        assertSemantic();

        dataProperty.addDisjointDataProperty( "disjointDataPropertyToBuild");
        dataProperty.writeSemantic();
        assertSemantic();
        System.out.println( "described data property, disjoint test: " + dataProperty.buildDisjointDataProperty());
    }

    @Test
    public void equivalentTest() throws Exception {
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.addEquivalentDataProperty("hasEquivalentProperty");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.addEquivalentDataProperty( "hasEquivalentProperty");
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.removeEquivalentDataProperty( "hasEquivalentProperty");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.removeEquivalentDataProperty( "hasEquivalentProperty");
        dataProperty.writeSemantic();
        assertSemantic();

        dataProperty.addEquivalentDataProperty( "equivalentDataPropertyToBuild");
        dataProperty.writeSemantic();
        assertSemantic();
        System.out.println( "described data property, equivalent test: " + dataProperty.buildEquivalentDataProperty());
    }

    @Test
    public void domainTest() throws Exception{
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.addDomainClassRestriction("Sphere");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.addDomainClassRestriction( "Sphere");
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.removeDomainClassRestriction( "Sphere");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.removeDomainClassRestriction( "Sphere");
        dataProperty.writeSemantic();
        assertSemantic();

        dataProperty.addDomainClassRestriction( "ClassRestrictionTest");
        dataProperty.writeSemantic();
        assertSemantic();



        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        dataProperty.writeSemantic();
        assertSemantic();

        dataProperty.addDomainMinDataRestriction( "hasDomainDataPropertyTest", 3, Double.class);
        dataProperty.writeSemantic();
        assertSemantic();



        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();
        dataProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        dataProperty.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        dataProperty.addDomainMaxObjectRestriction( "hasDomainDataPropertyTest", 2, "Cone");
        dataProperty.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        System.out.println( "described data property, domain test: " + dataProperty.getDomainDataProperty());
    }


    @Test
    public void rangeTest() throws Exception{
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.addRangeDataRestriction( Long.class);
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.addRangeDataRestriction( Long.class);
        dataProperty.writeSemantic();
        assertSemantic();
        dataProperty.removeRangeDataRestriction( Long.class);
        dataProperty.readSemantic();
        assertSemantic();
        dataProperty.removeRangeDataRestriction( Long.class);
        // the reasoner infer no more disjoint here since there is no more range restriction
        dataProperty.writeSemanticInconsistencySafe();
        assertSemantic();

        dataProperty.addRangeDataRestriction( Long.class);
        // the reasoner infer disjoint here since there is a range restriction
        dataProperty.writeSemanticInconsistencySafe();
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