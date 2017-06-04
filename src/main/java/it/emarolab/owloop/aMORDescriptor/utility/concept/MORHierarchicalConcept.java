package it.emarolab.owloop.aMORDescriptor.utility.concept;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORConcept;
import it.emarolab.owloop.aMORDescriptor.utility.MORConceptBase;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.List;


/**
 * A basic implementation for a concept with sub and super concepts.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a concept that is synchronised w.r.t. its {@link Sub} and {@link Super} concepts.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.Concepts} for the
 *     respective descriptions, as well as call both interfaces in the
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
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.concept.MORHierarchicalConcept <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORHierarchicalConcept
        extends MORConceptBase
        implements MORConcept.Sub<MORHierarchicalConcept>,
        MORConcept.Super<MORHierarchicalConcept>{

    private MORAxioms.Concepts subConcept = new MORAxioms.Concepts();
    private MORAxioms.Concepts superConcept = new MORAxioms.Concepts();

    // constructors for MORConceptBase

    public MORHierarchicalConcept(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORHierarchicalConcept(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORHierarchicalConcept(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORHierarchicalConcept(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORHierarchicalConcept(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORHierarchicalConcept(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORHierarchicalConcept(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORHierarchicalConcept(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORConcept.Sub.super.readSemantic();
        r.addAll( MORConcept.Super.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORConcept.Sub.super.writeSemantic();
        r.addAll( MORConcept.Super.super.writeSemantic());
        return r;
    }



    // implementations for MORConcept.Super

    @Override //called during build...() you can change the returning type to any implementations of MORConcept
    public MORHierarchicalConcept getNewSubConcept(OWLClass instance, OWLReferences ontology) {
        return new MORHierarchicalConcept( instance, ontology);
    }

    @Override
    public MORAxioms.Concepts getSubConcept() {
        return subConcept;
    }



    // implementations for MORConcept.Sub

    @Override // called during build...() you can change the returning type to any implementations of MORConcept
    public MORHierarchicalConcept getNewSuperConcept(OWLClass instance, OWLReferences ontology) {
        return new MORHierarchicalConcept( instance, ontology);
    }

    @Override
    public MORAxioms.Concepts getSuperConcept() {
        return superConcept;
    }


    @Override
    public String toString() {
        return "MORFullObjectProperty{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊃ " + subConcept +
                "," + NL + "\t⊂ " + superConcept +
                NL + "}";
    }
}
