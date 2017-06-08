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
 * A basic implementation for a concept with equivalent, disjoint classes as well as defining restrictions.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a concept that is synchronised w.r.t. its {@link Disjoint} and {@link Equivalent} classes
 *     as wel as with its {@link Define} restrictions and {@link Classify}ed individuals.
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
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.concept.MORDefinitionConcept <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORDefinitionConcept 
        extends MORConceptBase
        implements MORConcept.Define,
        MORConcept.Disjoint<MORDefinitionConcept>,
        MORConcept.Equivalent<MORDefinitionConcept>,
        MORConcept.Classify<MORLinkIndividual>{


    private MORAxioms.Concepts disjointConcept = new MORAxioms.Concepts();
    private MORAxioms.Concepts equivalentConcept = new MORAxioms.Concepts();
    private MORAxioms.Restrictions restrictions = new MORAxioms.Restrictions();
    private MORAxioms.Individuals classifiedIndividual = new MORAxioms.Individuals();



    // constructors for MORConceptBase
    
    public MORDefinitionConcept(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORDefinitionConcept(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORDefinitionConcept(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORDefinitionConcept(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORDefinitionConcept(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORDefinitionConcept(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORDefinitionConcept(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORDefinitionConcept(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORConcept.Disjoint.super.readSemantic();
        r.addAll( MORConcept.Equivalent.super.readSemantic());
        r.addAll( MORConcept.Define.super.readSemantic());
        r.addAll( MORConcept.Classify.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORConcept.Disjoint.super.writeSemantic();
        r.addAll( MORConcept.Equivalent.super.writeSemantic());
        r.addAll( MORConcept.Define.super.writeSemantic());
        r.addAll( MORConcept.Classify.super.writeSemantic());
        return r;
    }


    // implementations for MORConcept.Disjoint

    @Override  // called during build...() you can change the returning type to any implementations of MORConcept
    public MORDefinitionConcept getNewDisjointConcept(OWLClass instance, OWLReferences ontology) {
        return new MORDefinitionConcept( instance, ontology);
    }

    @Override
    public MORAxioms.Concepts getDisjointConcept() {
        return disjointConcept;
    }



    // implementations for MORConcept.Equivalent

    @Override // called during build...() you can change the returning type to any implementations of MORConcept
    public MORDefinitionConcept getNewEquivalentConcept(OWLClass instance, OWLReferences ontology) {
        return new MORDefinitionConcept( instance, ontology);
    }

    @Override
    public MORAxioms.Concepts getEquivalentConcept() {
        return equivalentConcept;
    }



    // implementations for MORConcept.Define

    @Override
    public MORAxioms.Restrictions getDefinitionConcept() {
        return restrictions;
    }


    // implementations for MORConcept.Classifier

    @Override  // called during build...() you can change the returning type to any implementations of MORConcept
    public MORLinkIndividual getNewIndividualClassified(OWLNamedIndividual instance, OWLReferences ontology) {
        return new MORLinkIndividual( instance, ontology);
    }

    @Override
    public MORAxioms.Individuals getIndividualClassified() {
        return classifiedIndividual;
    }


    @Override
    public String toString() {
        return "MORFullObjectProperty{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointConcept +
                "," + NL + "\t≡ " + equivalentConcept +
                "," + NL + "\t⇐ " + classifiedIndividual +
                "," + NL + "\t= " + restrictions +
                NL + "}";
    }
}
