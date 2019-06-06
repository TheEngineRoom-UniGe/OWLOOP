package it.emarolab.owloop.descriptor.utility.individualDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.utility.conceptDescriptor.FullConceptDesc;
import it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor.FullDataPropertyDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.FullObjectPropertyDesc;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * This is an example of a 'compound' Individual Descriptor as it implements more than one {@link IndividualExpression}.
 * Axioms in this descriptor's internal state (i.e., OWLOOP representation) can be synchronized to/from an OWL ontology.
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
 * <p>
 * <div style="text-align:center;"><small>
 * <b>File</b>:         it.emarolab.owloop.core.Axiom <br>
 * <b>Licence</b>:      GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Authors</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it), Syed Yusha Kareem (kareem.syed.yusha@dibris.unige.it) <br>
 * <b>affiliation</b>:  EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:         01/05/19 <br>
 * </small></div>
 */
public class FullIndividualDesc
        extends IndividualGround
        implements IndividualExpression.Disjoint<FullIndividualDesc>,
        IndividualExpression.Equivalent<FullIndividualDesc>,
        IndividualExpression.Type<FullConceptDesc>,
        IndividualExpression.ObjectLink<FullObjectPropertyDesc>,
        IndividualExpression.DataLink<FullDataPropertyDesc> {

    private DescriptorEntitySet.Individuals disjointIndividual = new DescriptorEntitySet.Individuals();
    private DescriptorEntitySet.Individuals equivalentIndividual = new DescriptorEntitySet.Individuals();
    private DescriptorEntitySet.Concepts concepts = new DescriptorEntitySet.Concepts();
    private DescriptorEntitySet.ObjectLinksSet objectLinks = new DescriptorEntitySet.ObjectLinksSet();
    private DescriptorEntitySet.DataLinksSet dataLinks = new DescriptorEntitySet.DataLinksSet();

    // Constructors from the abstract class: IndividualGround

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

    // Implementation of readExpressionAxioms()

    @Override
    public List<MappingIntent> readExpressionAxioms() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.readExpressionAxioms();
        r.addAll( IndividualExpression.Disjoint.super.readExpressionAxioms());
        r.addAll( IndividualExpression.Type.super.readExpressionAxioms());
        r.addAll( IndividualExpression.ObjectLink.super.readExpressionAxioms());
        r.addAll( IndividualExpression.DataLink.super.readExpressionAxioms());
        return r;
    }

    // Implementation of writeExpressionAxioms()

    @Override
    public List<MappingIntent> writeExpressionAxioms() {
        List<MappingIntent> r = IndividualExpression.Equivalent.super.writeExpressionAxioms();
        r.addAll( IndividualExpression.Disjoint.super.writeExpressionAxioms());
        r.addAll( IndividualExpression.Type.super.writeExpressionAxioms());
        r.addAll( IndividualExpression.ObjectLink.super.writeExpressionAxioms());
        r.addAll( IndividualExpression.DataLink.super.writeExpressionAxioms());
        return r;
    }

    // implementations for: IndividualExpression.Disjoint

    @Override //called during build...() you can change the returning type to any implementations of IndividualExpression
    public FullIndividualDesc getNewDisjointIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new FullIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Individuals getDisjointIndividuals() {
        return disjointIndividual;
    }

    // Implementations for: IndividualExpression.Equivalent

    @Override //called during build...() you can change the returning type to any implementations of IndividualExpression
    public FullIndividualDesc getNewEquivalentIndividual(OWLNamedIndividual instance, OWLReferences ontology) {
        return new FullIndividualDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Individuals getEquivalentIndividuals() {
        return equivalentIndividual;
    }

    // Implementations for: IndividualExpression.Type

    @Override //called during build...() you can change the returning type to any implementations of ConceptExpression
    public FullConceptDesc getNewIndividualType(OWLClass instance, OWLReferences ontology) {
        return new FullConceptDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.Concepts getIndividualTypes() {
        return concepts;
    }

    // Implementations for: IndividualExpression.ObjectLink

    @Override //called during build...() you can change the returning type to any implementations of ObjectPropertyExpression
    public FullObjectPropertyDesc getNewIndividualObjectProperty(DescriptorEntitySet.ObjectLinks instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance.getExpression(), ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectLinksSet getIndividualObjectProperties() {
        return objectLinks;
    }

    // Implementations for: IndividualExpression.DataLink

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewIndividualDataProperty(DescriptorEntitySet.DataLinks instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance.getExpression(), ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinksSet getIndividualDataProperties() {
        return dataLinks;
    }

    // Implementations for: standard object interface

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "\n" +
                "\n" +
                "\t" + getGround() + ":" + "\n" +
                "\n" +
                "\t\t≠ " + disjointIndividual + "\n" +
                "\t\t≡ " + equivalentIndividual + "\n" +
                "\t\t∈ " + concepts + "\n" +
                "\t\t⊨ " + objectLinks + "\n" +
                "\t\t⊢ " + dataLinks + "\n" +
                "}" + "\n";
    }
}

// todo: (i) rename entitySet objects properly (ii) rename the methods related to those variables properly (iii) modification in toString() (iv) fix spaces and comments.