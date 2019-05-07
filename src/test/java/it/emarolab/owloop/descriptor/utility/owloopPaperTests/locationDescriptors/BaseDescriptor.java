package it.emarolab.owloop.descriptor.utility.owloopPaperTests.locationDescriptors;

import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.SemanticRestriction;
import it.emarolab.owloop.descriptor.construction.descriptorBase.ConceptDescriptorBase;
import it.emarolab.owloop.descriptor.construction.descriptorBaseInterface.DescriptorEntitySet;
import it.emarolab.owloop.descriptor.construction.descriptorExpression.ConceptExpression;

public abstract class BaseDescriptor<T extends SemanticRestriction.ApplyingPropertyRestriction>
        extends ConceptDescriptorBase
        implements ConceptExpression.Definition {

    private DescriptorEntitySet.Restrictions restrictions = new DescriptorEntitySet.Restrictions();

    protected BaseDescriptor(String instanceName, OWLReferences onto) { // called from only derived classes
        super(instanceName, onto);
        initialize();
    }

    private void initialize(){
        T definition = getRestriction();
        definition.setSubject( getGround().getGroundInstance());
        definition.setProperty( getOWLObjectProperty( "hasDoor"));
        definition.setValue( getOWLClass( "DOOR"));
        getDefinitionConcept().add( definition);
        writeExpressionAxioms();
    }

    abstract protected T getRestriction();

    // inherit read/write Expression Axioms from the only simple descriptor `ConceptExpression.Definition`

    @Override
    public DescriptorEntitySet.Restrictions getDefinitionConcept() {
        return restrictions;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + NL + "\t\t\t" + getGround() + "," + NL + "\t= " + restrictions + NL + "}";
    }
}

