package it.emarolab.owloop.aMORDescriptor.utility.individual;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORIndividual;
import it.emarolab.owloop.aMORDescriptor.utility.MORIndividualBase;
import it.emarolab.owloop.aMORDescriptor.utility.dataProperty.MORFullDataProperty;
import it.emarolab.owloop.aMORDescriptor.utility.objectProperty.MORFullObjectProperty;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * A basic implementation for an individual that has data and object property values.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     an individual that is synchronised w.r.t. its {@link DataLink}s
 *     and {@link ObjectLink}s.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.ObjectSemantics} for the
 *     respective descriptions, as well as call the derived interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link MORIndividualBase} in order
 *     to automatically manage a grounding {@link ConceptInstance}.
 *     From an OOP prospective it returns an {@link MORFullObjectProperty}
 *     and {@link MORFullDataProperty}, be aware that this may be not efficient and
 *     perhaps a less complete descriptor is enough for your case.
 *     <br>
 *     You may want to use this class (see also {@link MORDefinitionIndividual} and {@link MORTypeIndividual},
 *     as well as other classes in the {@link it.emarolab.owloop.aMORDescriptor.utility} package)
 *     as templates to build a specific {@link MORIndividual} descriptor that fits your needs
 *     and maximises the OWL synchronisation efficiency.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.concept.MORLinkIndividual <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORLinkIndividual
        extends MORIndividualBase
        implements MORIndividual.ObjectLink<MORFullObjectProperty>,
        MORIndividual.DataLink<MORFullDataProperty> {

    private MORAxioms.ObjectSemantics objectLinks = new MORAxioms.ObjectSemantics();
    private MORAxioms.DataSemantics dataLinks = new MORAxioms.DataSemantics();

    // constructors for MORIndividualBase

    public MORLinkIndividual(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORLinkIndividual(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORLinkIndividual(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORLinkIndividual(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORLinkIndividual(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORLinkIndividual(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORLinkIndividual(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORLinkIndividual(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORIndividual.ObjectLink.super.readSemantic();
        r.addAll( MORIndividual.DataLink.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = ObjectLink.super.writeSemantic();
        r.addAll( DataLink.super.writeSemantic());
        return r;
    }


    // implementations for MORIndividual.ObjectLink

    @Override  //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORFullObjectProperty getNewObjectIndividual(MORAxioms.ObjectSemantic instance, OWLReferences ontology) {
        return new MORFullObjectProperty( instance.getSemantic(), ontology);
    }

    @Override
    public MORAxioms.ObjectSemantics getObjectSemantics() {
        return objectLinks;
    }



    // implementations for MORIndividual.DataLink

    @Override  //called during build...() you can change the returning type to any implementations of MORDataProperty
    public MORFullDataProperty getNewDataIndividual(MORAxioms.DataSemantic instance, OWLReferences ontology) {
        return new MORFullDataProperty( instance.getSemantic(), ontology);
    }

    @Override
    public MORAxioms.DataSemantics getDataSemantics() {
        return dataLinks;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on MORBase<?> which considers only the ground

    @Override
    public String toString() {
        return "MORFullObjectProperty{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊨ " + objectLinks +
                "," + NL + "\t⊢ " + dataLinks +
                NL + "}";
    }
}