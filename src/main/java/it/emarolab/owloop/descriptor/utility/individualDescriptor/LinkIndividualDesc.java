package it.emarolab.owloop.descriptor.utility.individualDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.core.Axiom;
import it.emarolab.owloop.descriptor.construction.descriptorBase.IndividualDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorBaseInterface.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor.FullDataPropertyDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.FullObjectPropertyDesc;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * A basic implementation for an individualDescriptor that has data and object property values.
 * <p>
 *     This is an example of how use the {@link Axiom.Descriptor}s for implement
 *     an individualDescriptor that is synchronised w.r.t. its {@link DataLink}s
 *     and {@link ObjectLink}s.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorEntitySet.ObjectSemantics} for the
 *     respective descriptions, as well as call the derived interfaces in the
 *     {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()} methods.
 *     All its constructions are based on {@link IndividualDescriptorBase} in order
 *     to automatically manage a grounding {@link ConceptInstance}.
 *     From an OOP prospective it returns an {@link FullObjectPropertyDesc}
 *     and {@link FullDataPropertyDesc}, be aware that this may be not efficient and
 *     perhaps a less complete descriptor is enough for your case.
 *     <br>
 *     You may want to use this class (see also {@link DefinitionIndividualDesc} and {@link TypeIndividualDesc},
 *     as well as other classes in the {@link it.emarolab.owloop.descriptor.utility} package)
 *     as templates to build a specific {@link IndividualExpression} descriptor that fits your needs
 *     and maximises the OWL synchronisation efficiency.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.conceptDescriptor.LinkIndividualDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class LinkIndividualDesc
        extends IndividualDescriptorBase
        implements IndividualExpression.ObjectLink<FullObjectPropertyDesc>,
        IndividualExpression.DataLink<FullDataPropertyDesc> {

    private DescriptorEntitySet.ObjectSemantics objectLinks = new DescriptorEntitySet.ObjectSemantics();
    private DescriptorEntitySet.DataSemantics dataLinks = new DescriptorEntitySet.DataSemantics();

    // constructors for IndividualDescriptorBase

    public LinkIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public LinkIndividualDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public LinkIndividualDesc(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public LinkIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public LinkIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public LinkIndividualDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public LinkIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public LinkIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = IndividualExpression.ObjectLink.super.readExpressionAxioms();
        r.addAll( IndividualExpression.DataLink.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ObjectLink.super.writeExpressionAxioms();
        r.addAll( DataLink.super.writeExpressionAxioms());
        return r;
    }


    // implementations for IndividualExpression.ObjectLink

    @Override  //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDesc getNewObjectIndividual(DescriptorEntitySet.ObjectExpression instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance.getExpression(), ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectSemantics getObjectSemantics() {
        return objectLinks;
    }



    // implementations for IndividualExpression.DataLink

    @Override  //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewDataIndividual(DescriptorEntitySet.DataExpression instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance.getExpression(), ontology);
    }

    @Override
    public DescriptorEntitySet.DataSemantics getDataExpressions() {
        return dataLinks;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorBase<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊨ " + objectLinks +
                "," + NL + "\t⊢ " + dataLinks +
                NL + "}";
    }
}