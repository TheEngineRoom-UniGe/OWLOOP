package it.emarolab.owloop.descriptor.utility.individualCompoundDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.core.Axiom;
import it.emarolab.owloop.descriptor.construction.descriptorBase.IndividualDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.IndividualExpression;
import it.emarolab.owloop.descriptor.utility.conceptCompoundDescriptor.FullConceptDesc;
import it.emarolab.owloop.descriptor.utility.dataPropertyCompoundDescriptor.FullDataPropertyDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyCompoundDescriptor.FullObjectPropertyDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * The implementation of all the semantic features of an individualCompoundDescriptor.
 * <p>
 *     This is an example of how use the {@link Axiom.Descriptor}s for implement
 *     a individualCompoundDescriptor that is synchronised w.r.t. all interfaces of {@link IndividualExpression}.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorEntitySet.Individuals},
 *     {@link DescriptorEntitySet.ObjectSemantics} and {@link DescriptorEntitySet.DataSemantics} for the
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
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.individualCompoundDescriptor.FullIndividualDesc <br>
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

    private DescriptorEntitySet.Individuals disjointIndividual = new DescriptorEntitySet.Individuals();
    private DescriptorEntitySet.Individuals equivalentIndividual = new DescriptorEntitySet.Individuals();
    private DescriptorEntitySet.Concepts individualTypes = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.ObjectSemantics objectLinks = new DescriptorEntitySet.ObjectSemantics();
    private DescriptorEntitySet.DataSemantics dataLinks = new DescriptorEntitySet.DataSemantics();

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

    // implementations for Axiom.descriptor

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
    public DescriptorEntitySet.Individuals getDisjointIndividual() {
        return disjointIndividual;
    }

    // implementations for IndividualExpression.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of IndividualExpression
    public FullIndividualDesc getNewEquivalentIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new FullIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Individuals getEquivalentIndividual() {
        return equivalentIndividual;
    }

    // implementations for IndividualExpression.Type

    @Override //called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewTypeIndividual(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getTypeIndividual() {
        return individualTypes;
    }

    // implementations for IndividualExpression.ObjectLink

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDesc getNewObjectIndividual(DescriptorEntitySet.ObjectExpression instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance.getExpression(), ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectSemantics getObjectSemantics() {
        return objectLinks;
    }

    // implementations for IndividualExpression.DataLink

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewDataIndividual(DescriptorEntitySet.DataExpression instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance.getExpression(), ontology);
    }

    @Override
    public DescriptorEntitySet.DataSemantics getDataSemantics() {
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
