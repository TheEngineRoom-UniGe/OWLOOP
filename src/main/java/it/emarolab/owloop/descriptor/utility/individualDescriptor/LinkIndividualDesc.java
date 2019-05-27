package it.emarolab.owloop.descriptor.utility.individualDescriptor;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.descriptor.construction.descriptorGround.IndividualGround;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.IndividualExpression;
import it.emarolab.owloop.descriptor.utility.dataPropertyDescriptor.FullDataPropertyDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.FullObjectPropertyDesc;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.List;

/**
 * This is an example of a 'compound' Individual Descriptor which implements 2 {@link IndividualExpression}s.
 * <ul>
 * <li><b>{@link IndividualExpression.ObjectLink}</b>:   to describe an ObjectProperty and Individuals related via that ObjectProperty, for an Individual.</li>
 * <li><b>{@link IndividualExpression.DataLink}</b>:     to describe an DataProperty and Individuals related via that DataProperty, for an Individual.</li>
 * </ul>
 * See {@link FullIndividualDesc} for an example of a 'compound' Individual Descriptor that implements all IndividualExpressions.
 */
public class LinkIndividualDesc
        extends IndividualGround
        implements IndividualExpression.ObjectLink<FullObjectPropertyDesc>,
        IndividualExpression.DataLink<FullDataPropertyDesc> {

    private DescriptorEntitySet.ObjectLinksSet objectLinks = new DescriptorEntitySet.ObjectLinksSet();
    private DescriptorEntitySet.DataLinksSet dataLinks = new DescriptorEntitySet.DataLinksSet();

    // constructors for IndividualGround

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
    public FullObjectPropertyDesc getNewObjectIndividual(DescriptorEntitySet.ObjectLinks instance, OWLReferences ontology) {
        return new FullObjectPropertyDesc( instance.getExpression(), ontology);
    }

    @Override
    public DescriptorEntitySet.ObjectLinksSet getObjectExpressionAxioms() {
        return objectLinks;
    }

    // implementations for IndividualExpression.DataLink

    @Override  //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewDataIndividual(DescriptorEntitySet.DataLinks instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance.getExpression(), ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinksSet getDataExpressionAxioms() {
        return dataLinks;
    }

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                "," + NL + "\t⊨ " + objectLinks +
                "," + NL + "\t⊢ " + dataLinks +
                NL + "}";
    }
}