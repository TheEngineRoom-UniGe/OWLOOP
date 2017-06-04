package it.emarolab.owloop.aMORDescriptor.utility.individual;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import it.emarolab.owloop.aMORDescriptor.MORIndividual;
import it.emarolab.owloop.aMORDescriptor.utility.MORIndividualBase;
import it.emarolab.owloop.aMORDescriptor.utility.concept.MORFullConcept;
import it.emarolab.owloop.aMORDescriptor.utility.dataProperty.MORFullDataProperty;
import it.emarolab.owloop.aMORDescriptor.utility.objectProperty.MORFullObjectProperty;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * The implementation of all the semantic features of an individual.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a individual that is synchronised w.r.t. all interfaces of {@link MORIndividual}.
 *     <br>
 *     Its purpose is only to instanciate the {@link MORAxioms.Individuals},
 *     {@link MORAxioms.ObjectSemantics} and {@link MORAxioms.DataSemantics} for the
 *     respective descriptions, as well as call all interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link MORIndividualBase} in order
 *     to automatically manage a grounding {@link ConceptInstance}.
 *     From an OOP prospective it returns an {@link MORFullObjectProperty}
 *     and {@link MORFullDataProperty}, be aware that this may be not efficient and
 *     perhaps a less complete descriptor is enough for your case.
 *     <br>
 *     In order to optimise the synchronisation efficiency (i.e.: minimize
 *     reasoning calls) use this object only if it is necessary.
 *     Otherwise is recommended to use an had hoc {@link Descriptor}.
 *     You may want to use this class, see also {@link MORDefinitionIndividual},
 *     {@link MORLinkIndividual} and {@link MORTypeIndividual}
 *     (generally, all the classes in the {@link it.emarolab.owloop.aMORDescriptor.utility} package)
 *     as templates for doing that.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.aMORDescriptor.utility.individual.MORFullIndividual <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class MORFullIndividual
        extends MORIndividualBase
        implements MORIndividual.Disjoint<MORFullIndividual>,
        MORIndividual.Equivalent<MORFullIndividual>,
        MORIndividual.Type<MORFullConcept>,
        MORIndividual.ObjectLink<MORFullObjectProperty>,
        MORIndividual.DataLink<MORFullDataProperty> {

    private MORAxioms.Individuals disjointIndividual = new MORAxioms.Individuals();
    private MORAxioms.Individuals equivalentIndividual = new MORAxioms.Individuals();
    private MORAxioms.Concepts individualTypes = new MORAxioms.Concepts();
    private MORAxioms.ObjectSemantics objectLinks = new MORAxioms.ObjectSemantics();
    private MORAxioms.DataSemantics dataLinks = new MORAxioms.DataSemantics();

    // constructors for MORIndividualBase

    public MORFullIndividual(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public MORFullIndividual(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public MORFullIndividual(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public MORFullIndividual(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public MORFullIndividual(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public MORFullIndividual(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public MORFullIndividual(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public MORFullIndividual(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }


    // implementations for Semantic.Descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = MORIndividual.Equivalent.super.readSemantic();
        r.addAll( MORIndividual.Disjoint.super.readSemantic());
        r.addAll( MORIndividual.Type.super.readSemantic());
        r.addAll( MORIndividual.ObjectLink.super.readSemantic());
        r.addAll( MORIndividual.DataLink.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = MORIndividual.Equivalent.super.writeSemantic();
        r.addAll( MORIndividual.Disjoint.super.writeSemantic());
        r.addAll( MORIndividual.Type.super.writeSemantic());
        r.addAll( MORIndividual.ObjectLink.super.writeSemantic());
        r.addAll( MORIndividual.DataLink.super.writeSemantic());
        return r;
    }




    // implementations for MORIndividual.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of MORIndividual
    public MORFullIndividual getNewDisjointIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new MORFullIndividual( instance, ontology);
    }

    @Override
    public MORAxioms.Individuals getDisjointIndividual() {
        return disjointIndividual;
    }



    // implementations for MORIndividual.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of MORIndividual
    public MORFullIndividual getNewEquivalentIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new MORFullIndividual( instance, ontology);
    }

    @Override
    public MORAxioms.Individuals getEquivalentIndividual() {
        return equivalentIndividual;
    }




    // implementations for MORIndividual.Type

    @Override //called during build...() you can change the returning type to any implementations of MORConcept
    public MORFullConcept getNewTypeIndividual(OWLClass instance, OWLReferences ontology) {
        return new MORFullConcept( instance, ontology);
    }

    @Override
    public MORAxioms.Concepts getTypeIndividual() {
        return individualTypes;
    }




    // implementations for MORIndividual.ObjectLink

    @Override //called during build...() you can change the returning type to any implementations of MORObjectProperty
    public MORFullObjectProperty getNewObjectIndividual(MORAxioms.ObjectSemantic instance, OWLReferences ontology) {
        return new MORFullObjectProperty( instance.getSemantic(), ontology);
    }

    @Override
    public MORAxioms.ObjectSemantics getObjectSemantics() {
        return objectLinks;
    }



    // implementations for MORIndividual.DataLink

    @Override //called during build...() you can change the returning type to any implementations of MORDataProperty
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
                ":" + NL + "\t≠ " + disjointIndividual +
                "," + NL + "\t≡ " + equivalentIndividual +
                "," + NL + "\t∈ " + individualTypes +
                "," + NL + "\t⊨ " + objectLinks +
                "," + NL + "\t⊢ " + dataLinks +
                NL + "}";
    }
}
