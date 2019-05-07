package it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MORFullObjectPropertyTest {

    private static FullObjectPropertyDesc objectProperty;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        objectProperty = new FullObjectPropertyDesc(
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
        objectProperty.writeExpressionAxiomsInconsistencySafe(); // this is necessary when only test runs in series
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
        objectProperty.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
        assertSemantic();
        objectProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeDomainExactObjectRestriction( "hasDomainProperty", 3, "Plane");
        objectProperty.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        objectProperty.addDomainMaxObjectRestriction( "hasDomainPropertyTest", 2, "Cone");
        objectProperty.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        System.out.println( "described object property, domain test: " + objectProperty.getDomainObjectProperty());
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
        objectProperty.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
        assertSemantic();
        objectProperty.removeRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.readExpressionAxioms();
        assertSemantic();
        objectProperty.removeRangeExactObjectRestriction( "hasRangeProperty", 3, "Plane");
        objectProperty.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        objectProperty.addRangeMaxObjectRestriction( "hasRangePropertyTest", 2, "Cone");
        objectProperty.writeExpressionAxiomsInconsistencySafe(); // the reasoner always infers here
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