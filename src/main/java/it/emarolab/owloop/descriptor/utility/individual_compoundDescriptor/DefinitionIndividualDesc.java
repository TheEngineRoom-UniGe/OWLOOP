package it.emarolab.owloop.descriptor.utility.individual_compoundDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorAxioms;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.IndividualExpression;
import it.emarolab.owloop.descriptor.construction.descriptorBase.IndividualDescriptorBase;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * A basic implementation for an individual_compoundDescriptor with 'same as' and 'different from' individual_compoundDescriptor description.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     am individual_compoundDescriptor that is synchronised w.r.t. to {@link Equivalent}
 *     and {@link Disjoint} instances.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorAxioms.Individuals} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link IndividualDescriptorBase} in order
 *     to automatically manage a grounding {@link IndividualInstance}.
 *     <br>
 *     You may want to use this class (see also {@link TypeIndividualDesc}
 *     and {@link LinkIndividualDesc}, as well as other classes in the
 *     {@link it.emarolab.owloop.descriptor.utility} package) as templates to build a specific
 *     {@link IndividualExpression} descriptor that fits your needs and maximises the
 *     OWL synchronisation efficiency for data properties.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.individual_compoundDescriptor.DefinitionIndividualDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class DefinitionIndividualDesc
        extends IndividualDescriptorBase
        implements IndividualExpression.Disjoint<DefinitionIndividualDesc>,
        IndividualExpression.Equivalent<DefinitionIndividualDesc>{

    private DescriptorAxioms.Individuals disjointIndividual = new DescriptorAxioms.Individuals();
    private DescriptorAxioms.Individuals equivalentIndividual = new DescriptorAxioms.Individuals();

    // constructors for IndividualDescriptorBase

    public DefinitionIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DefinitionIndividualDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DefinitionIndividualDesc(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public DefinitionIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DefinitionIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DefinitionIndividualDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DefinitionIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DefinitionIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.readSemantic();
        r.addAll( IndividualExpression.Disjoint.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.writeSemantic();
        r.addAll( IndividualExpression.Disjoint.super.writeSemantic());
        return r;
    }


    // implementations for IndividualExpression.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of IndividualExpression
    public DefinitionIndividualDesc getNewDisjointIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new DefinitionIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Individuals getDisjointIndividual() {
        return disjointIndividual;
    }



    // implementations for IndividualExpression.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of IndividualExpression
    public DefinitionIndividualDesc getNewEquivalentIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new DefinitionIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Individuals getEquivalentIndividual() {
        return equivalentIndividual;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorBase<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointIndividual +
                "," + NL + "\t≡ " + equivalentIndividual +
                NL + "}";
    }
}
