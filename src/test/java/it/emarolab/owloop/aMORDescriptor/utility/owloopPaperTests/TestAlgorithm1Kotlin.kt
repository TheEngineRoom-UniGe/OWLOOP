package it.emarolab.owloop.aMORDescriptor.utility.owloopPaperTests

import io.kotlintest.shouldBe
import it.emarolab.owloop.aMORDescriptor.utility.individual.MORFullIndividual
import org.junit.After
import org.junit.Before
import org.junit.Test

class TestAlgorithm1Kotlin {


    private lateinit var d: MORFullIndividual

    @Before // called a before every @Test
    fun setUp() {

        d = MORFullIndividual(

                "Robot1", // the ground instance name
                "owloopTest", // ontology reference name
                "src/test/resources/owloopTestOntology.owl", // the ontology file path
                "http://www.semanticweb.org/yusha_temporary/owloopTestOntology" // the ontology IRI path
        )
    }

    @After // called after all @Test-s
    fun save() {

        d.saveOntology("src/test/resources/owloopTestOntology.owl")
    }

    @Test
    fun algorithm1() {

        val pose = "Corridor1"

        d.readSemantic()
        d.addTypeIndividual("Robot")
        d.addObject("isIn", pose)
        d.writeSemantic()

        assertSemantic()
    }

    private fun assertSemantic() {// asserts that the state of the java representation is equal to the state of the ontology

        d.let{
            it.typeIndividual.shouldBe(it.queryTypeIndividual())
            it.objectSemantics.shouldBe(it.queryObject())
        }
    }
}