package it.emarolab.owloop.descriptor.utility.dataPropertyCompoundDescriptor;


import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.core.Axiom;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.DataPropertyExpression;
import it.emarolab.owloop.descriptor.construction.descriptorEntitySet.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorGround.DataPropertyDescriptorGround;
import org.semanticweb.owlapi.model.OWLDataProperty;

import java.util.List;
import java.util.Set;

/**
 * The implementation of all the semantic features of a data property.
 * <p>
 *     This is an example of how use the {@link Axiom.Descriptor}s for implement
 *     a data property that is synchronised w.r.t. all interfaces of {@link DataPropertyExpression}.
 *     <br>
 *     Its purpose is only to instanciate the {@link DescriptorEntitySet.DataLinks}
 *     and {@link DescriptorEntitySet.Restrictions} for the
 *     respective descriptions, as well as call all interfaces in the
 *     {@link #readSemantic()} and {@link #writeSemantic()} methods.
 *     All its constructions are based on {@link DataPropertyDescriptorGround} in order
 *     to automatically manage a grounding {@link DataInstance}.
 *     <br>
 *     In order to optimise the synchronisation efficiency (i.e.: minimize
 *     reasoning calls) use this object only if it is necessary.
 *     Otherwise is recommended to use an had hoc {@link Descriptor}.
 *     You may want to use this class, see also {@link HierarchicalDataPropertyDesc},
 *     {@link DefinitionDataPropertyDesc} and {@link DomainDataPropertyDesc}
 *     (generally, all the classes in the {@link it.emarolab.owloop.descriptor.utility} package)
 *     as templates for doing that.
 *
 * <div style="text-align:center;"><small>
 * <b>File</b>:        it.emarolab.owloop.descriptor.utility.dataPropertyCompoundDescriptor.FullDataPropertyDesc <br>
 * <b>Licence</b>:     GNU GENERAL PUBLIC LICENSE. Version 3, 29 June 2007 <br>
 * <b>Author</b>:      Buoncompagni Luca (luca.buoncompagni@edu.unige.it) <br>
 * <b>affiliation</b>: EMAROLab, DIBRIS, University of Genoa. <br>
 * <b>date</b>:        21/05/17 <br>
 * </small></div>
 */
public class FullDataPropertyDesc
        extends DataPropertyDescriptorGround
        implements DataPropertyExpression.Disjoint<FullDataPropertyDesc>,
        DataPropertyExpression.Equivalent<FullDataPropertyDesc>,
        DataPropertyExpression.Sub<FullDataPropertyDesc>,
        DataPropertyExpression.Super<FullDataPropertyDesc>,
        DataPropertyExpression.Domain,
        DataPropertyExpression.Range{

    private DescriptorEntitySet.DataLinks disjointProperties = new DescriptorEntitySet.DataLinks();
    private DescriptorEntitySet.DataLinks equivalentProperties = new DescriptorEntitySet.DataLinks();
    private DescriptorEntitySet.DataLinks subProperties = new DescriptorEntitySet.DataLinks();
    private DescriptorEntitySet.DataLinks superProperties = new DescriptorEntitySet.DataLinks();
    private DescriptorEntitySet.Restrictions domainRestriction = new DescriptorEntitySet.Restrictions();
    private DescriptorEntitySet.Restrictions rangeRestriction = new DescriptorEntitySet.Restrictions();

    // constructors for DataPropertyDescriptorGround

    public FullDataPropertyDesc(OWLDataProperty instance, OWLReferences onto) {
        super(instance, onto);
    }
    public FullDataPropertyDesc(String instanceName, OWLReferences onto) {
        super(instanceName, onto);
    }
    public FullDataPropertyDesc(OWLDataProperty instance, String ontoName) {
        super(instance, ontoName);
    }
    public FullDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath) {
        super(instance, ontoName, filePath, iriPath);
    }
    public FullDataPropertyDesc(OWLDataProperty instance, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instance, ontoName, filePath, iriPath, bufferingChanges);
    }
    public FullDataPropertyDesc(String instanceName, String ontoName) {
        super(instanceName, ontoName);
    }
    public FullDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath) {
        super(instanceName, ontoName, filePath, iriPath);
    }
    public FullDataPropertyDesc(String instanceName, String ontoName, String filePath, String iriPath, boolean bufferingChanges) {
        super(instanceName, ontoName, filePath, iriPath, bufferingChanges);
    }

    // implementations for Axiom.descriptor

    @Override
    public List<MappingIntent> readSemantic() {
        List<MappingIntent> r = DataPropertyExpression.Disjoint.super.readSemantic();
        r.addAll( DataPropertyExpression.Equivalent.super.readSemantic());
        r.addAll( DataPropertyExpression.Range.super.readSemantic());
        r.addAll( DataPropertyExpression.Domain.super.readSemantic());
        r.addAll( DataPropertyExpression.Sub.super.readSemantic());
        r.addAll( DataPropertyExpression.Super.super.readSemantic());
        return r;
    }

    @Override
    public List<MappingIntent> writeSemantic() {
        List<MappingIntent> r = DataPropertyExpression.Disjoint.super.writeSemantic();
        r.addAll( DataPropertyExpression.Equivalent.super.writeSemantic());
        r.addAll( DataPropertyExpression.Range.super.writeSemantic());
        r.addAll( DataPropertyExpression.Domain.super.writeSemantic());
        r.addAll( DataPropertyExpression.Sub.super.writeSemantic());
        r.addAll( DataPropertyExpression.Super.super.writeSemantic());
        return r;
    }

    // implementations for DataPropertyExpression.Disjoint


    @Override // you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewDisjointDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinks getDisjointDataProperty() {
        return disjointProperties;
    }

    // implementations for DataPropertyExpression.Equivalent

    @Override // returns a set with elements of the same type of getNewDisjointDataProperty()
    public Set<FullDataPropertyDesc> buildEquivalentDataProperty() {
        return DataPropertyExpression.Equivalent.super.buildEquivalentDataProperty();
    }

    @Override // you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewEquivalentDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinks getEquivalentDataProperty() {
        return equivalentProperties;
    }

    // implementations for DataPropertyExpression.Domain
    @Override
    public DescriptorEntitySet.Restrictions getDomainDataProperty() {
        return domainRestriction;
    }

    // implementations for DataPropertyExpression.Range
    @Override
    public DescriptorEntitySet.Restrictions getRangeDataProperty() {
        return rangeRestriction;
    }

    // implementations for DataPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewSubDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinks getSubDataProperty() {
        return subProperties;
    }

    // implementations for DataPropertyExpression.Super

    @Override //called during build...() you can change the returning type to any implementations of DataPropertyExpression
    public FullDataPropertyDesc getNewSuperDataProperty(OWLDataProperty instance, OWLReferences ontology) {
        return new FullDataPropertyDesc( instance, ontology);
    }

    @Override
    public DescriptorEntitySet.DataLinks getSuperDataProperty() {
        return superProperties;
    }

    // implementation for standard object interface
    // equals() and hashCode() is based on DescriptorGround<?> which considers only the ground

    @Override
    public String toString() {
        return "FullObjectPropertyDesc{" +
                NL + "\t\t\t" + getGround() +
                ":" + NL + "\t≠ " + disjointProperties +
                "," + NL + "\t≡ " + equivalentProperties +
                "," + NL + "\t→ " + domainRestriction +
                "," + NL + "\t← " + rangeRestriction +
                "," + NL + "\t⊃ " + subProperties +
                "," + NL + "\t⊂ " + superProperties +
                NL + "}";
    }
}
