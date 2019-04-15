package it.emarolab.owloop.descriptor.utility.concept_compoundDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorBase.ConceptDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorAxioms;
import it.emarolab.owloop.descriptor.utility.individual_compoundDescriptor.LinkIndividualDesc;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * The implementation of all the semantic features of a class.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a class that is synchronised w.r.t. all interfaces of {@link ConceptExpression}.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorAxioms.Concepts}
 *     and {@link DescriptorAxioms.Restrictions} for the
 *     respective descriptions, as well as call all interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link ConceptDescriptorBase} in order
 *     to automatically manage a grounding {@link ConceptInstance}.
 *     <br>
 *     In order to optimise the synchronisation efficiency (i.e.: minimize
 *     reasoning calls) use this object only if it is necessary.
 *     Otherwise is recommended to use an had hoc {@link Descriptor}.
 *     You may want to use this class, see also {@link DefinitionConceptDesc} and {@link HierarchicalConceptDesc}
 *     (generally, all the classes in the {@link it.emarolab.owloop.descriptor.utility} package)
 *     as templates for doing that.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.concept_compoundDescriptor.FullConceptDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class FullConceptDesc
        extends ConceptDescriptorBase
        implements ConceptExpression.Define,
        ConceptExpression.Disjoint<FullConceptDesc>,
        ConceptExpression.Equivalent<FullConceptDesc>,
        ConceptExpression.Sub<FullConceptDesc>,
        ConceptExpression.Super<FullConceptDesc>,
        ConceptExpression.Classify<LinkIndividualDesc> {

    private DescriptorAxioms.Concepts disjointConcept = new DescriptorAxioms.Concepts();
    private DescriptorAxioms.Concepts equivalentConcept = new DescriptorAxioms.Concepts();
    private DescriptorAxioms.Restrictions restrictions = new DescriptorAxioms.Restrictions();
    private DescriptorAxioms.Concepts subConcept = new DescriptorAxioms.Concepts();
    private DescriptorAxioms.Concepts superConcept = new DescriptorAxioms.Concepts();
    private DescriptorAxioms.Individuals classifiedIndividual = new DescriptorAxioms.Individuals();


    // constructors for ConceptDescriptorBase

    public FullConceptDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public FullConceptDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public FullConceptDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public FullConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public FullConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public FullConceptDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public FullConceptDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public FullConceptDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }





    // implementations for Semantic.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = ConceptExpression.Disjoint.super.readSemantic();
        r.addAll( ConceptExpression.Equivalent.super.readSemantic());
        r.addAll( ConceptExpression.Define.super.readSemantic()); // call this before Sub or Super !!!
        r.addAll( ConceptExpression.Sub.super.readSemantic());
        r.addAll( ConceptExpression.Super.super.readSemantic());
        r.addAll( ConceptExpression.Classify.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = ConceptExpression.Disjoint.super.writeSemantic();
        r.addAll( ConceptExpression.Equivalent.super.writeSemantic());
        r.addAll( ConceptExpression.Sub.super.writeSemantic());
        r.addAll( ConceptExpression.Super.super.writeSemantic());
        r.addAll( ConceptExpression.Define.super.writeSemantic()); // call this before Sub or Super !!!
        r.addAll( ConceptExpression.Classify.super.writeSemantic());
        return r;
    }


    // implementations for ConceptExpression.Disjoint

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewDisjointConcept(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Concepts getDisjointConcept() {
        return disjointConcept;
    }



    // implementations for ConceptExpression.Equivalent

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewEquivalentConcept(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Concepts getEquivalentConcept() {
        return equivalentConcept;
    }



    // implementations for ConceptExpression.Define

    @Override
    public DescriptorAxioms.Restrictions getDefinitionConcept() {
        return restrictions;
    }



    // implementations for ConceptExpression.Super

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewSubConcept(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Concepts getSubConcept() {
        return subConcept;
    }



    // implementations for ConceptExpression.Super

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewSuperConcept(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Concepts getSuperConcept() {
        return superConcept;
    }


    // implementations for ConceptExpression.Classifier

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public LinkIndividualDesc getNewIndividualClassified(OWLNamedIndividual instance, OWLReferences ontology) {
        return new LinkIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Individuals getIndividualClassified() {
        return classifiedIndividual;
    }


    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorBase<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
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
