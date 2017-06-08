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
 * The implementation of all the semantic features of a class.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a class that is synchronised w.r.t. all interfaces of {@link MORConcept}.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.Concepts}
 *     and {@link MORAxioms.Restrictions} for the
 *     respective descriptions, as well as call all interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link MORConceptBase} in order
 *     to automatically manage a grounding {@link ConceptInstance}.
 *     <br>
 *     In order to optimise the synchronisation efficiency (i.e.: minimize
 *     reasoning calls) use this object only if it is necessary.
 *     Otherwise is recommended to use an had hoc {@link Descriptor}.
 *     You may want to use this class, see also {@link MORDefinitionConcept} and {@link MORHierarchicalConcept}
 *     (generally, all the classes in the {@link it.emarolab.owloop.aMORDescriptor.utility} package)
 *     as templates for doing that.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.concept.MORFullConcept <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORFullConcept
        extends MORConceptBase
        implements MORConcept.Define,
        MORConcept.Disjoint<MORFullConcept>,
        MORConcept.Equivalent<MORFullConcept>,
        MORConcept.Sub<MORFullConcept>,
        MORConcept.Super<MORFullConcept>,
        MORConcept.Classify<MORLinkIndividual> {

    private MORAxioms.Concepts disjointConcept = new MORAxioms.Concepts();
    private MORAxioms.Concepts equivalentConcept = new MORAxioms.Concepts();
    private MORAxioms.Restrictions restrictions = new MORAxioms.Restrictions();
    private MORAxioms.Concepts subConcept = new MORAxioms.Concepts();
    private MORAxioms.Concepts superConcept = new MORAxioms.Concepts();
    private MORAxioms.Individuals classifiedIndividual = new MORAxioms.Individuals();


    // constructors for MORConceptBase

    public MORFullConcept(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORFullConcept(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORFullConcept(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORFullConcept(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORFullConcept(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORFullConcept(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORFullConcept(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORFullConcept(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }





    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORConcept.Disjoint.super.readSemantic();
        r.addAll( MORConcept.Equivalent.super.readSemantic());
        r.addAll( MORConcept.Define.super.readSemantic()); // call this before Sub or Super !!!
        r.addAll( MORConcept.Sub.super.readSemantic());
        r.addAll( MORConcept.Super.super.readSemantic());
        r.addAll( MORConcept.Classify.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORConcept.Disjoint.super.writeSemantic();
        r.addAll( MORConcept.Equivalent.super.writeSemantic());
        r.addAll( MORConcept.Sub.super.writeSemantic());
        r.addAll( MORConcept.Super.super.writeSemantic());
        r.addAll( MORConcept.Define.super.writeSemantic()); // call this before Sub or Super !!!
        r.addAll( MORConcept.Classify.super.writeSemantic());
        return r;
    }


    // implementations for MORConcept.Disjoint

    @Override // called during build...() you can change the returning type to any implementations of MORConcept
    public MORFullConcept getNewDisjointConcept(OWLClass instance, OWLReferences ontology) {
        return new MORFullConcept( instance, ontology);
    }

    @Override
    public MORAxioms.Concepts getDisjointConcept() {
        return disjointConcept;
    }



    // implementations for MORConcept.Equivalent

    @Override // called during build...() you can change the returning type to any implementations of MORConcept
    public MORFullConcept getNewEquivalentConcept(OWLClass instance, OWLReferences ontology) {
        return new MORFullConcept( instance, ontology);
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



    // implementations for MORConcept.Super

    @Override // called during build...() you can change the returning type to any implementations of MORConcept
    public MORFullConcept getNewSubConcept(OWLClass instance, OWLReferences ontology) {
        return new MORFullConcept( instance, ontology);
    }

    @Override
    public MORAxioms.Concepts getSubConcept() {
        return subConcept;
    }



    // implementations for MORConcept.Super

    @Override // called during build...() you can change the returning type to any implementations of MORConcept
    public MORFullConcept getNewSuperConcept(OWLClass instance, OWLReferences ontology) {
        return new MORFullConcept( instance, ontology);
    }

    @Override
    public MORAxioms.Concepts getSuperConcept() {
        return superConcept;
    }


    // implementations for MORConcept.Classifier

    @Override // called during build...() you can change the returning type to any implementations of MORConcept
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
                ":" + NL + "\t≠ " + disjointConcept +
                "," + NL + "\t≡ " + equivalentConcept +
                "," + NL + "\t⇐ " + classifiedIndividual +
                "," + NL + "\t= " + restrictions +
                "," + NL + "\t⊃ " + subConcept +
                "," + NL + "\t⊂ " + superConcept +
                NL + "}";
    }
}
