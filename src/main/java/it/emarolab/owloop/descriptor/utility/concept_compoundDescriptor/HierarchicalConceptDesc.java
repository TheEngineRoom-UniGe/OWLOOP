package it.emarolab.owloop.descriptor.utility.concept_compoundDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorInterface.DescriptorAxioms;
import it.emarolab.owloop.descriptor.construction.descriptorBase.ConceptDescriptorBase;
import it.emarolab.owloop.core.Semantic;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.List;


/**
 * A basic implementation for a concept_compoundDescriptor with sub and super concepts.
 * <p>
 *     This is an example of how use the {@link Semantic.Descriptor}s for implement
 *     a concept_compoundDescriptor that is synchronised w.r.t. its {@link Sub} and {@link Super} concepts.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorAxioms.Concepts} for the
 *     respective descriptions, as well as call both interfaces in the
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
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.concept_compoundDescriptor.HierarchicalConceptDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class HierarchicalConceptDesc
        extends ConceptDescriptorBase
        implements ConceptExpression.Sub<HierarchicalConceptDesc>,
        ConceptExpression.Super<HierarchicalConceptDesc>{

    private DescriptorAxioms.Concepts subConcept = new DescriptorAxioms.Concepts();
    private DescriptorAxioms.Concepts superConcept = new DescriptorAxioms.Concepts();

    // constructors for ConceptDescriptorBase

    public HierarchicalConceptDesc(OWLClass instance, OWLReferences onto) {
        super(instance, onto);
    }
    public HierarchicalConceptDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public HierarchicalConceptDesc(OWLClass instance, String ontoName) {
        super(instance, ontoName);
    }
    public HierarchicalConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public HierarchicalConceptDesc(OWLClass instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public HierarchicalConceptDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public HierarchicalConceptDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public HierarchicalConceptDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Semantic.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = ConceptExpression.Sub.super.readSemantic();
        r.addAll( ConceptExpression.Super.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = ConceptExpression.Sub.super.writeSemantic();
        r.addAll( ConceptExpression.Super.super.writeSemantic());
        return r;
    }



    // implementations for ConceptExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of ConceptExpression
    public HierarchicalConceptDesc getNewSubConcept(OWLClass instance, OWLReferences ontology) {
        return new HierarchicalConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Concepts getSubConcept() {
        return subConcept;
    }



    // implementations for ConceptExpression.Sub

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public HierarchicalConceptDesc getNewSuperConcept(OWLClass instance, OWLReferences ontology) {
        return new HierarchicalConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorAxioms.Concepts getSuperConcept() {
        return superConcept;
    }


    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊃ " + subConcept +
                "," + NL + "\t⊂ " + superConcept +
                NL + "}";
    }
}
