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
 * A basic implementation for a concept_compoundDescriptor that classify individuals.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a concept_compoundDescriptor that is synchronised only w.r.t. {@link Classify}ed individuals.
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
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.concept_compoundDescriptor.ClassifyConceptDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class ClassifyConceptDesc
        extends ConceptDescriptorBase
        implements ConceptExpression.Classify<LinkIndividualDesc> {

    private DescriptorAxioms.Individuals classifiedIndividual = new DescriptorAxioms.Individuals();


    // constructors for ConceptDescriptorBase

    public ClassifyConceptDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public ClassifyConceptDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public ClassifyConceptDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public ClassifyConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public ClassifyConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public ClassifyConceptDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public ClassifyConceptDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public ClassifyConceptDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }





    // implementations for Semantic.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        return ConceptExpression.Classify.super.readSemantic();
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        return ConceptExpression.Classify.super.writeSemantic();
    }

    // implementations for ConceptExpression.Classifier


    @Override // you can change the returning type to any implementations of ConceptExpression
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
                "," + NL + "\t⇐ " + classifiedIndividual +
                NL + "}";
    }
}
