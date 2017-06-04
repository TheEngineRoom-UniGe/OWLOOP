package it.emarolab.owloop.aMORDescriptor.utility.concept;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORConcept;
import it.emarolab.owloop.aMORDescriptor.utility.MORConceptBase;
import it.emarolab.owloop.aMORDescriptor.utility.individual.MORLinkIndividual;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * A basic implementation for a concept that classify individuals.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a concept that is synchronised only w.r.t. {@link Classify}ed individuals.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.Concepts} for the
 *     respective descriptions, as well as call the derived interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link MORConceptBase} in order
 *     to automatically manage a grounding {@link ConceptInstance}.
 *     <br>
 *     You may want to use this class (see also {@link MORHierarchicalConcept},
 *     as well as other classes in the {@link it.emarolab.owloop.aMORDescriptor.utility} package)
 *     as templates to build a specific {@link MORConcept} descriptor that fits your needs
 *     and maximises the OWL synchronisation efficiency.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.concept.MORClassifyConcept <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORClassifyConcept
        extends MORConceptBase
        implements MORConcept.Classify<MORLinkIndividual> {

    private MORAxioms.Individuals classifiedIndividual = new MORAxioms.Individuals();


    // constructors for MORConceptBase

    public MORClassifyConcept(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORClassifyConcept(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORClassifyConcept(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORClassifyConcept(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORClassifyConcept(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORClassifyConcept(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORClassifyConcept(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORClassifyConcept(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }





    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        return MORConcept.Classify.super.readSemantic();
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        return MORConcept.Classify.super.writeSemantic();
    }

    // implementations for MORConcept.Classifier


    @Override // you can change the returning type to any implementations of MORConcept
    public MORLinkIndividual getNewIndividualClassified(OWLNamedIndividual instance, OWLReferences ontology) {
        return new MORLinkIndividual( instance, ontology);
    }

    @Override
    public MORAxioms.Individuals getIndividualClassified() {
        return classifiedIndividual;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground

    @Override
    public String toString() {
        return "MORFullObjectProperty{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⇐ " + classifiedIndividual +
                NL + "}";
    }
}
