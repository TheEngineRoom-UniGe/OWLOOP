package it.emarolab.owloop.descriptor.utility.individual_compoundDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorBase.IndividualDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorAxioms;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.IndividualExpression;
import it.emarolab.owloop.descriptor.utility.concept_compoundDescriptor.FullConceptDesc;
import it.emarolab.owloop.descriptor.utility.dataProperty_compoundDescriptor.FullDataPropertyDesc;
import it.emarolab.owloop.descriptor.utility.objectProperty_compoundDescriptor.FullObjectPropertyDesc;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * The implementation of all the semantic features of an individual_compoundDescriptor.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a individual_compoundDescriptor that is synchronised w.r.t. all interfaces of {@link IndividualExpression}.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorAxioms.Individuals},
 *     {@link DescriptorAxioms.ObjectSemantics} and {@link DescriptorAxioms.DataSemantics} for the
 *     respective descriptions, as well as call all interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link IndividualDescriptorBase} in order
 *     to automatically manage a grounding {@link ConceptInstance}.
 *     From an OOP prospective it returns an {@link FullObjectPropertyDesc}
 *     and {@link FullDataPropertyDesc}, be aware that this may be not efficient and
 *     perhaps a less complete descriptor is enough for your case.
 *     <br>
 *     In order to optimise the synchronisation efficiency (i.e.: minimize
 *     reasoning calls) use this object only if it is necessary.
 *     Otherwise is recommended to use an had hoc {@link Descriptor}.
 *     You may want to use this class, see also {@link DefinitionIndividualDesc},
 *     {@link LinkIndividualDesc} and {@link TypeIndividualDesc}
 *     (generally, all the classes in the {@link it.emarolab.owloop.descriptor.utility} package)
 *     as templates for doing that.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.individual_compoundDescriptor.FullIndividualDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class FullIndividualDesc
        extends IndividualDescriptorBase
        implements IndividualExpression.Disjoint<FullIndividualDesc>,
        IndividualExpression.Equivalent<FullIndividualDesc>,
        IndividualExpression.Type<FullConceptDesc>,
        IndividualExpression.ObjectLink<FullObjectPropertyDesc>,
        IndividualExpression.DataLink<FullDataPropertyDesc> {

    private DescriptorAxioms.Individuals disjointIndividual = new DescriptorAxioms.Individuals();
    private DescriptorAxioms.Individuals equivalentIndividual = new DescriptorAxioms.Individuals();
    private DescriptorAxioms.Concepts individualTypes = new DescriptorAxioms.Concepts();
    private DescriptorAxioms.ObjectSemantics objectLinks = new DescriptorAxioms.ObjectSemantics();
    private DescriptorAxioms.DataSemantics dataLinks = new DescriptorAxioms.DataSemantics();

    // constructors for IndividualDescriptorBase

    public FullIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public FullIndividualDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public FullIndividualDesc(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public FullIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public FullIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public FullIndividualDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public FullIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public FullIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }


    // implementations for Semantic.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.readSemantic();
        r.addAll( IndividualExpression.Disjoint.super.readSemantic());
        r.addAll( IndividualExpression.Type.super.readSemantic());
        r.addAll( IndividualExpression.ObjectLink.super.readSemantic());
        r.addAll( IndividualExpression.DataLink.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.writeSemantic();
        r.addAll( IndividualExpression.Disjoint.super.writeSemantic());
        r.addAll( IndividualExpression.Type.super.writeSemantic());
        r.addAll( IndividualExpression.ObjectLink.super.writeSemantic());
        r.addAll( IndividualExpression.DataLink.super.writeSemantic());
        return r;
    }




    // implementations for IndividualExpression.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of IndividualExpression
    public FullIndividualDesc getNewDisjointIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new FullIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Individuals getDisjointIndividual() {
        return disjointIndividual;
    }



    // implementations for IndividualExpression.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of IndividualExpression
    public FullIndividualDesc getNewEquivalentIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new FullIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Individuals getEquivalentIndividual() {
        return equivalentIndividual;
    }




    // implementations for IndividualExpression.Type

    @Override //called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewTypeIndividual(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Concepts getTypeIndividual() {
        return individualTypes;
    }




    // implementations for IndividualExpression.ObjectLink

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDesc getNewObjectIndividual(DescriptorAxioms.ObjectSemantic instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance.getSemantic(), ontology);
    }

    @Override
    public DescriptorAxioms.ObjectSemantics getObjectSemantics() {
        return objectLinks;
    }



    // implementations for IndividualExpression.DataLink

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewDataIndividual(DescriptorAxioms.DataSemantic instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance.getSemantic(), ontology);
    }

    @Override
    public DescriptorAxioms.DataSemantics getDataSemantics() {
        return dataLinks;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorBase<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointIndividual +
                "," + NL + "\t≡ " + equivalentIndividual +
                "," + NL + "\t∈ " + individualTypes +
                "," + NL + "\t⊨ " + objectLinks +
                "," + NL + "\t⊢ " + dataLinks +
                NL + "}";
    }
}
