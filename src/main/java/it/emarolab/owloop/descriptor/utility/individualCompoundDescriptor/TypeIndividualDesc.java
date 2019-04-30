package it.emarolab.owloop.descriptor.utility.individualCompoundDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorBase.IndividualDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorBaseInterface.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.utility.conceptCompoundDescriptor.HierarchicalConceptDesc;
import it.emarolab.owloop.core.Axiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;


/**
 * A basic implementation for an individualCompoundDescriptor that belongs to classes.
 * <p>
 *     This is an example of how use the {@link Axiom.Descriptor}s for implement
 *     an individualCompoundDescriptor that is synchronised w.r.t. its {@link Type}s.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorEntitySet.Concepts} for the
 *     respective descriptions, as well as call the derived interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     From an OOP prospective it returns the classified types as instances
 *     of {@link HierarchicalConceptDesc}s.
 *     All its constructions are based on {@link IndividualDescriptorBase} in order
 *     to automatically manage a grounding {@link IndividualInstance}.
 *     <br>
 *     You may want to use this class (see also {@link DefinitionIndividualDesc} and {@link LinkIndividualDesc},
 *     as well as other classes in the {@link it.emarolab.owloop.descriptor.utility} package)
 *     as templates to build a specific {@link IndividualExpression} descriptor that fits your needs
 *     and maximises the OWL synchronisation efficiency.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.conceptCompoundDescriptor.MORTypeIndinvidual <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class TypeIndividualDesc
        extends IndividualDescriptorBase
        implements IndividualExpression.Type<HierarchicalConceptDesc>{

    private DescriptorEntitySet.Concepts individualTypes = new DescriptorEntitySet.Concepts();

    // constructors for IndividualDescriptorBase

    public TypeIndividualDesc(OWLNamedIndividual instance, OWLReferences onto) {
        super(instance, onto);
    }
    public TypeIndividualDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public TypeIndividualDesc(OWLNamedIndividual instance, String ontoName) {
        super(instance, ontoName);
    }
    public TypeIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public TypeIndividualDesc(OWLNamedIndividual instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public TypeIndividualDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public TypeIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public TypeIndividualDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }



    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        return IndividualExpression.Type.super.readSemantic();
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        return IndividualExpression.Type.super.writeSemantic();
    }


    // implementations for IndividualExpression.Type

    @Override //called during build...() you can change the returning type to any implementations of ConceptExpression
    public HierarchicalConceptDesc getNewTypeIndividual(OWLClass instance, OWLReferences ontology) {
        return new HierarchicalConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getTypeIndividual() {
        return individualTypes;
    }



    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorBase<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t ∈ " + individualTypes +
                NL + "}";
    }
}
