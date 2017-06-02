package it.emarolab.owloop.aMORDescriptor.utility.objectProperty;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MORFullObjectPropertyTest {

    private static MORFullObjectProperty objectProperty;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        objectProperty = new MORFullObjectProperty(
                "isAboveOf", // the ground instance name
                "ontoName", // ontology reference name
                "src/test/resources/tboxTest.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/luca-buoncompagni/sit" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        objectProperty.saveOntology( "src/test/resources/objectPropertyTest.owl");
    }


    @Test
    public void subTest() throws Exception {
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.addSubObjectProperty( "hasSubProperty");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.addSubObjectProperty( "hasSubProperty");
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.removeSubObjectProperty( "hasSubProperty");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.removeSubObjectProperty( "hasSubProperty");
        objectProperty.writeSemantic();
        assertSemantic();

        objectProperty.addSubObjectProperty( "subPropertyToBuild");
        objectProperty.writeSemantic();
        assertSemantic();
        System.out.println( "described object property, sub test: " + objectProperty.buildSubObjectProperty());
    }


    @Test
    public void superTest() throws Exception {
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.addSuperObjectProperty("hasSuperProperty");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.addSuperObjectProperty( "hasSuperProperty");
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.removeSuperObjectProperty( "hasSuperProperty");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.removeSuperObjectProperty( "hasSuperProperty");
        objectProperty.writeSemanticInconsistencySafe(); // this is necessary when only test runs in series
        assertSemantic();

        objectProperty.addSuperObjectProperty( "superPropertyToBuild");
        objectProperty.writeSemantic();
        assertSemantic();
        System.out.println( "described object property, super test: " + objectProperty.buildSuperObjectProperty());
    }

    @Test
    public void disjointTest() throws Exception {
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.addDisjointObjectProperty("hasDisjointProperty");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.addDisjointObjectProperty( "hasDisjointProperty");
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.removeDisjointObjectProperty( "hasDisjointProperty");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.removeDisjointObjectProperty( "hasDisjointProperty");
        objectProperty.writeSemantic();
        assertSemantic();

        objectProperty.addDisjointObjectProperty( "disjointPropertyToBuild");
        objectProperty.writeSemantic();
        assertSemantic();
        System.out.println( "described object property, disjoint test: " + objectProperty.buildDisjointObjectProperty());
    }

    @Test
    public void equivalentTest() throws Exception {
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.addEquivalentObjectProperty("hasEquivalentProperty");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.addEquivalentObjectProperty( "hasEquivalentProperty");
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.removeEquivalentObjectProperty( "hasEquivalentProperty");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.removeEquivalentObjectProperty( "hasEquivalentProperty");
        objectProperty.writeSemantic();
        assertSemantic();

        objectProperty.addEquivalentObjectProperty( "equivalentPropertyToBuild");
        objectProperty.writeSemantic();
        assertSemantic();
        System.out.println( "described object property, equivalent test: " + objectProperty.buildEquivalentObjectProperty());
    }

    @Test
    public void inverseTest() throws Exception {
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.addInverseObjectProperty("hasInverseProperty");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.addInverseObjectProperty( "hasInverseProperty");
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.removeInverseObjectProperty( "hasInverseProperty");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.removeInverseObjectProperty( "hasInverseProperty");
        objectProperty.writeSemantic();
        assertSemantic();

        objectProperty.addInverseObjectProperty( "inversePropertyToBuild");
        objectProperty.writeSemantic();
        assertSemantic();
        System.out.println( "described object property, inverse test: " + objectProperty.buildInverseObjectProperty());
    }

    @Test
    public void domainTest() throws Exception{
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.addDomainClassRestriction("Sphere");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.addDomainClassRestriction( "Sphere");
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.removeDomainClassRestriction( "Sphere");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.removeDomainClassRestriction( "Sphere");
        objectProperty.writeSemantic();
        assertSemantic();

        objectProperty.addDomainClassRestriction( "ClassRestrictionTest");
        objectProperty.writeSemantic();
        assertSemantic();



        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.addDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.removeDomainExactDataRestriction( "hasDomainProperty", 3, Long.class);
        objectProperty.writeSemantic();
        assertSemantic();

        objectProperty.addDomainMinDataRestriction( "hasDomainPropertyTest", 3, Double.class);
        objectProperty.writeSemantic();
        assertSemantic();



        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.addDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();
        objectProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        objectProperty.addDomainMaxObjectRestriction( "hasDomainPropertyTest", 2, "Cone");
        objectProperty.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        System.out.println( "described object property, domain test: " + objectProperty.getDomainObjectProperty());
    }


    @Test
    public void rangeTest() throws Exception{
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.addRangeClassRestriction("Sphere");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.addRangeClassRestriction( "Sphere");
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.removeRangeClassRestriction( "Sphere");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.removeRangeClassRestriction( "Sphere");
        objectProperty.writeSemantic();
        assertSemantic();

        objectProperty.addRangeClassRestriction( "ClassRestrictionTest");
        objectProperty.writeSemantic();
        assertSemantic();



        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.addRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.addRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.removeRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.removeRangeExactDataRestriction( "hasRangeProperty", 3, Long.class);
        objectProperty.writeSemantic();
        assertSemantic();

        objectProperty.addRangeMinDataRestriction( "hasRangePropertyTest", 3, Double.class);
        objectProperty.writeSemantic();
        assertSemantic();



        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.addRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.writeSemantic();
        assertSemantic();
        objectProperty.addRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.addRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();
        objectProperty.removeRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.readSemantic();
        assertSemantic();
        objectProperty.removeRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        objectProperty.addRangeMaxObjectRestriction( "hasRangePropertyTest", 2, "Cone");
        objectProperty.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        System.out.println( "described object property, range test: " + objectProperty.getRangeObjectProperty());
    }




    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        assertEquals( objectProperty.getSubObjectProperty(), objectProperty.querySubObjectProperty());
        assertEquals( objectProperty.getSuperObjectProperty(), objectProperty.querySuperObjectProperty());
        assertEquals( objectProperty.getDisjointObjectProperty(), objectProperty.queryDisjointObjectProperty());
        assertEquals( objectProperty.getEquivalentObjectProperty(), objectProperty.queryEquivalentObjectProperty());
        assertEquals( objectProperty.getDomainObjectProperty(), objectProperty.queryDomainObjectProperty());
        assertEquals( objectProperty.getRangeObjectProperty(), objectProperty.queryRangeObjectProperty());
        assertEquals( objectProperty.getInverseObjectProperty(), objectProperty.queryInverseObjectProperty());
    }
}