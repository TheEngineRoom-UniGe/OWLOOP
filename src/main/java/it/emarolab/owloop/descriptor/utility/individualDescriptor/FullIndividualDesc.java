package it.emarolab.owloop.descriptor.utility.individualDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.core.Axiom;
import it.emarolab.owloop.core.Individual;
import it.emarolab.owloop.descriptor.construction.descriptorBase.IndividualDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorBaseInterface.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.utility.conceptDescriptor.FullConceptDesc;
import it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor.FullDataPropertyDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.FullObjectPropertyDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * This is an example of a 'compound' Individual Descriptor as it implements more than one {@link IndividualExpression}.
 * Axioms in this descriptor's internal state (OWLOOP representation) gets synchronized wrt the axioms in the OWL representation.
 * {@link FullIndividualDesc} can synchronize all the axioms, that are based on the following IndividualExpressions:
 *
 * <ul>
 * <li><b>{@link IndividualExpression.Equivalent}</b>:   to describe an Individual same-as another Individual.</li>
 * <li><b>{@link IndividualExpression.Disjoint}</b>:     to describe an Individual different from another Individual.</li>
 * <li><b>{@link IndividualExpression.Type}</b>:         to describe the Type/s (i.e., class/es) of an Individual.</li>
 * <li><b>{@link IndividualExpression.ObjectLink}</b>:   to describe an ObjectProperty and Individuals related via that ObjectProperty, for an Individual.</li>
 * <li><b>{@link IndividualExpression.DataLink}</b>:     to describe an DataProperty and Individuals related via that DataProperty, for an Individual.</li>
 * </ul>
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
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

    // Constructors from the abstract class: IndividualDescriptorBase

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

    // Implementation of readSemantic()

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.readSemantic();
        r.addAll( IndividualExpression.Disjoint.super.readSemantic());
        r.addAll( IndividualExpression.Type.super.readSemantic());
        r.addAll( IndividualExpression.ObjectLink.super.readSemantic());
        r.addAll( IndividualExpression.DataLink.super.readSemantic());
        return r;
    }

    // Implementation of writeSemantic()

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.writeSemantic();
        r.addAll( IndividualExpression.Disjoint.super.writeSemantic());
        r.addAll( IndividualExpression.Type.super.writeSemantic());
        r.addAll( IndividualExpression.ObjectLink.super.writeSemantic());
        r.addAll( IndividualExpression.DataLink.super.writeSemantic());
        return r;
    }

    // implementations for: IndividualExpression.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of IndividualExpression
    public FullIndividualDesc getNewDisjointIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new FullIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Individuals getDisjointIndividual() {
        return disjointIndividual;
    }

    // Implementations for: IndividualExpression.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of IndividualExpression
    public FullIndividualDesc getNewEquivalentIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new FullIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Individuals getEquivalentIndividual() {
        return equivalentIndividual;
    }

    // Implementations for: IndividualExpression.Type

    @Override //called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewTypeIndividual(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getTypeIndividual() {
        return individualTypes;
    }

    // Implementations for: IndividualExpression.ObjectLink

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDesc getNewObjectIndividual(DescriptorEntitySet.ObjectExpression instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance.getExpression(), ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectSemantics getObjectSemantics() {
        return objectLinks;
    }

    // Implementations for: IndividualExpression.DataLink

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewDataIndividual(DescriptorEntitySet.DataExpression instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance.getExpression(), ontology);
    }

    @Override
    public DescriptorEntitySet.DataSemantics getDataExpressions() {
        return dataLinks;
    }

    // Implementations for: standard object interface

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
