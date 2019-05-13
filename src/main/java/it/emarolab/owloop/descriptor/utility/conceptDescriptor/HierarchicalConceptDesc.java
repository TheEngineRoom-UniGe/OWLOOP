package it.emarolab.owloop.descriptor.utility.conceptDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.core.Axiom;
import it.emarolab.owloop.descriptor.construction.descriptorGround.ConceptGround;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import org.semanticweb.owlapi.model.OWLClass;

import java.util.List;


/**
 * A basic implementation for a conceptDescriptor with sub and super concepts.
 * <p>
 *     This is an example of how use the {@link Axiom.Descriptor}s for implement
 *     a conceptDescriptor that is synchronised w.r.t. its {@link Sub} and {@link Super} concepts.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorEntitySet.Concepts} for the
 *     respective descriptions, as well as call both interfaces in the
 *     {@link #readExpressionAxioms()} and {@link #writeExpressionAxioms()} methods.
 *     All its constructions are based on {@link ConceptGround} in order
 *     to automatically manage a grounding {@link ConceptGroundInstance}.
 *     <br>
 *     You may want to use this class (see also {@link HierarchicalConceptDesc},
 *     as well as other classes in the {@link it.emarolab.owloop.descriptor.utility} package)
 *     as templates to build a specific {@link ConceptExpression} descriptor that fits your needs
 *     and maximises the OWL synchronisation efficiency.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.conceptDescriptor.HierarchicalConceptDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class HierarchicalConceptDesc
        extends ConceptGround
        implements ConceptExpression.Sub<HierarchicalConceptDesc>,
        ConceptExpression.Super<HierarchicalConceptDesc>{

    private DescriptorEntitySet.Concepts subConcept = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.Concepts superConcept = new DescriptorEntitySet.Concepts();

    // constructors for ConceptGround

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



    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = ConceptExpression.Sub.super.readExpressionAxioms();
        r.addAll( ConceptExpression.Super.super.readExpressionAxioms());
        return r;
    }

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = ConceptExpression.Sub.super.writeExpressionAxioms();
        r.addAll( ConceptExpression.Super.super.writeExpressionAxioms());
        return r;
    }



    // implementations for ConceptExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of ConceptExpression
    public HierarchicalConceptDesc getNewSubConcept(OWLClass instance, OWLReferences ontology) {
        return new HierarchicalConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getSubConcept() {
        return subConcept;
    }



    // implementations for ConceptExpression.Sub

    @Override // called during build...() you can change the returning type to any implementations of ConceptExpression
    public HierarchicalConceptDesc getNewSuperConcept(OWLClass instance, OWLReferences ontology) {
        return new HierarchicalConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getSuperConcept() {
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
