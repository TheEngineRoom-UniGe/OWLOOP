package it.emarolab.owloop.aMORDescriptor.utility.individual;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORIndividual;
import it.emarolab.owloop.aMORDescriptor.utility.MORIndividualBase;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * A basic implementation for an individual with 'same as' and 'different from' individual description.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     am individual that is synchronised w.r.t. to {@link Equivalent}
 *     and {@link Disjoint} instances.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.Individuals} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link MORIndividualBase} in order
 *     to automatically manage a grounding {@link IndividualInstance}.
 *     <br>
 *     You may want to use this class (see also {@link MORTypeIndividual}
 *     and {@link MORLinkIndividual}, as well as other classes in the
 *     {@link it.emarolab.owloop.aMORDescriptor.utility} package) as templates to build a specific
 *     {@link MORIndividual} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for data properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.individual.MORDefinitionIndividual <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORDefinitionIndividual
        extends MORIndividualBase
        implements MORIndividual.Disjoint<MORDefinitionIndividual>,
        MORIndividual.Equivalent<MORDefinitionIndividual>{

    private MORAxioms.Individuals disjointIndividual = new MORAxioms.Individuals();
    private MORAxioms.Individuals equivalentIndividual = new MORAxioms.Individuals();

    // constructors for MORIndividualBase

    public MORDefinitionIndividual(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORDefinitionIndividual(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORDefinitionIndividual(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORDefinitionIndividual(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORDefinitionIndividual(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORDefinitionIndividual(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORDefinitionIndividual(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORDefinitionIndividual(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORIndividual.Equivalent.super.readSemantic();
        r.addAll( MORIndividual.Disjoint.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORIndividual.Equivalent.super.writeSemantic();
        r.addAll( MORIndividual.Disjoint.super.writeSemantic());
        return r;
    }


    // implementations for MORIndividual.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of MORIndividual
    public MORDefinitionIndividual getNewDisjointIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new MORDefinitionIndividual( instance, ontology);
    }

    @Override
    public MORAxioms.Individuals getDisjointIndividual() {
        return disjointIndividual;
    }



    // implementations for MORIndividual.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of MORIndividual
    public MORDefinitionIndividual getNewEquivalentIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new MORDefinitionIndividual( instance, ontology);
    }

    @Override
    public MORAxioms.Individuals getEquivalentIndividual() {
        return equivalentIndividual;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground

    @Override
    public String toString() {
        return "MORFullObjectProperty{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointIndividual +
                "," + NL + "\t≡ " + equivalentIndividual +
                NL + "}";
    }
}
