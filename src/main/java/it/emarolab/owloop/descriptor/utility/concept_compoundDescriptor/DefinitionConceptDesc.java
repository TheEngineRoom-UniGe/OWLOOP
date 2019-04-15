package it.emarolab.owloop.descriptor.utility.concept_compoundDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorAxioms;
import it.emarolab.owloop.descriptor.construction.descriptorBase.ConceptDescriptorBase;
import it.emarolab.owloop.descriptor.utility.individual_compoundDescriptor.LinkIndividualDesc;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * A basic implementation for a concept_compoundDescriptor with equivalent, disjoint classes as well as defining restrictions.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a concept_compoundDescriptor that is synchronised w.r.t. its {@link Disjoint} and {@link Equivalent} classes
 *     as wel as with its {@link Define} restrictions and {@link Classify}ed individuals.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorAxioms.Concepts} for the
 *     respective descriptions, as well as call the derived interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link ConceptDescriptorBase} in order
 *     to automatically manage a grounding {@link ConceptInstance}.
 *     <br>
 *     You may want to use this class (see also {@link HierarchicalConceptDesc},
 *     as well as other classes in the {@link it.emarolab.owloop.descriptor.utility} package)
 *     as templates to build a specific {@link ConceptExpression} descriptor that fits your needs
 *     and maximises the OWL synchronisation efficiency.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.concept_compoundDescriptor.DefinitionConceptDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class DefinitionConceptDesc
        extends ConceptDescriptorBase
        implements ConceptExpression.Define,
        ConceptExpression.Disjoint<DefinitionConceptDesc>,
        ConceptExpression.Equivalent<DefinitionConceptDesc>,
        ConceptExpression.Classify<LinkIndividualDesc>{


    private DescriptorAxioms.Concepts disjointConcept = new DescriptorAxioms.Concepts();
    private DescriptorAxioms.Concepts equivalentConcept = new DescriptorAxioms.Concepts();
    private DescriptorAxioms.Restrictions restrictions = new DescriptorAxioms.Restrictions();
    private DescriptorAxioms.Individuals classifiedIndividual = new DescriptorAxioms.Individuals();



    // constructors for ConceptDescriptorBase
    
    public DefinitionConceptDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public DefinitionConceptDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public DefinitionConceptDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public DefinitionConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public DefinitionConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public DefinitionConceptDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public DefinitionConceptDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public DefinitionConceptDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = ConceptExpression.Disjoint.super.readSemantic();
        r.addAll( ConceptExpression.Equivalent.super.readSemantic());
        r.addAll( ConceptExpression.Define.super.readSemantic());
        r.addAll( ConceptExpression.Classify.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = ConceptExpression.Disjoint.super.writeSemantic();
        r.addAll( ConceptExpression.Equivalent.super.writeSemantic());
        r.addAll( ConceptExpression.Define.super.writeSemantic());
        r.addAll( ConceptExpression.Classify.super.writeSemantic());
        return r;
    }


    // implementations for ConceptExpression.Disjoint

    @Override  // called during build...() you can change the returning type to any implementations of ConceptExpression
    public DefinitionConceptDesc getNewDisjointConcept(OWLClass instance, OWLReferences ontology) {
        return new DefinitionConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Concepts getDisjointConcept() {
        return disjointConcept;
    }



    // implementations for ConceptExpression.Equivalent

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public DefinitionConceptDesc getNewEquivalentConcept(OWLClass instance, OWLReferences ontology) {
        return new DefinitionConceptDesc( instance, ontology);
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


    // implementations for ConceptExpression.Classifier

    @Override  // called during build...() you can change the returning type to any implementations of ConceptExpression
    public LinkIndividualDesc getNewIndividualClassified(OWLNamedIndividual instance, OWLReferences ontology) {
        return new LinkIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Individuals getIndividualClassified() {
        return classifiedIndividual;
    }


    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointConcept +
                "," + NL + "\t≡ " + equivalentConcept +
                "," + NL + "\t⇐ " + classifiedIndividual +
                "," + NL + "\t= " + restrictions +
                NL + "}";
    }
}
