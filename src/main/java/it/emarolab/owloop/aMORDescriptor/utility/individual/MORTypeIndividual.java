package it.emarolab.owloop.aMORDescriptor.utility.individual;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORIndividual;
import it.emarolab.owloop.aMORDescriptor.utility.MORIndividualBase;
import it.emarolab.owloop.aMORDescriptor.utility.concept.MORHierarchicalConcept;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * A basic implementation for an individual that belongs to classes.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     an individual that is synchronised w.r.t. its {@link Type}s.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.Concepts} for the
 *     respective descriptions, as well as call the derived interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     From an OOP prospective it returns the classified types as instances
 *     of {@link MORHierarchicalConcept}s.
 *     All its constructions are based on {@link MORIndividualBase} in order
 *     to automatically manage a grounding {@link IndividualInstance}.
 *     <br>
 *     You may want to use this class (see also {@link MORDefinitionIndividual} and {@link MORLinkIndividual},
 *     as well as other classes in the {@link it.emarolab.owloop.aMORDescriptor.utility} package)
 *     as templates to build a specific {@link MORIndividual} descriptor that fits your needs
 *     and maximises the OWL synchronisation efficiency.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.concept.MORTypeIndinvidual <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORTypeIndividual
        extends MORIndividualBase
        implements MORIndividual.Type<MORHierarchicalConcept>{

    private MORAxioms.Concepts individualTypes = new MORAxioms.Concepts();

    // constructors for MORIndividualBase

    public MORTypeIndividual(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORTypeIndividual(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORTypeIndividual(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORTypeIndividual(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORTypeIndividual(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORTypeIndividual(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORTypeIndividual(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORTypeIndividual(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        return MORIndividual.Type.super.readSemantic();
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        return MORIndividual.Type.super.writeSemantic();
    }


    // implementations for MORIndividual.Type

    @Override //called during build...() you can change the returning type to any implementations of MORConcept
    public MORHierarchicalConcept  getNewTypeIndividual(OWLClass instance, OWLReferences ontology) {
        return new MORHierarchicalConcept( instance, ontology);
    }

    @Override
    public MORAxioms.Concepts getTypeIndividual() {
        return individualTypes;
    }



    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground

    @Override
    public String toString() {
        return "MORFullObjectProperty{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t ∈ " + individualTypes +
                NL + "}";
    }
}
