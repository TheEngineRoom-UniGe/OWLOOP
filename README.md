# OWLOOP

An **O**bject **O**riented **P**rogramming interface for using the **O**ntology **W**eb **L**anguage within real architecting paradigms.


>The design of ontologies does not follow OOP paradigms since properties and hierarchy are dynamically inferred by the reasoner; see [this W3C](https://www.w3.org/2001/sw/BestPractices/SE/ODSD/) publication for more details about their differences. 
>Nevertheless, this API implements an efficient, customizable and general (but jet simple) synchronization interface that allows developers for an easier designing of flexible systems. While OWLOOP cares at run time to map the reasoned ontological representation in their objects. 
> 
>From now on you do not have to deal with `String` or `IRI` anymore, instead, let's directly rely and extend your `Objects` with real semantics !!!


# 



### Dependences

OWLOOP as been developed on top of the [aMOR](https://github.com/EmaroLab/multi_ontology_reference), which is a collection of helpers methods based on [OWL API](https://github.com/owlcs/owlapi). Those are still completely accessible through OWLOOP and allow to work with many reasoners, we test this software with [Pellet](https://github.com/Galigator/openllet). 
Finally, the runnable examples are based on [JUnit](http://junit.org/junit4/) within the [Intellij IDEA](https://www.jetbrains.com/idea/) IDE. The compilation is based on [Gradle](https://gradle.org/) and you can find a detailed list of the dependences versions in the [build.gradle](https://github.com/EmaroLab/owloop/blob/master/build.gradle) file. 

In the [lib](https://github.com/EmaroLab/owloop/tree/master/lib) folder you can find OWLOOP as a jar archive with (and without) its dependencies, as well as the compatible aMOR archive.



### Documentation 

In the [doc](https://github.com/EmaroLab/owloop/tree/master/doc) folder, you can find the Java documentation as well as UML graphs for visualizing the library structure.

I am currently improving this section with more examples and formalizations of the Java components.... work in progress!



### API package structure

OWLOOP is organized (in the [src](https://github.com/EmaroLab/owloop/tree/master/src) folder) as follow:

 - `test/` : contains the code used to check for bugs within a testing driven implementation style. Beside, even if those implementations does not do meaningful semantic manipulations, those are good examples to get started with features of this API.
  - `main/java/it/emarolab/owloop/` : contains the actual library components, dived in:
    - `core/` : contains the interfaces of the synchronization procedures, which has been generalized and they do not depend on OWL API. Those are:
      - `Semantic` : defines all the basic OWLOOP interfaces (i.e: `Ground`, `Axioms` and `Descriptor`).
      - `Concept`, `Individual`, `Data` and `Object` properties : define the particular *Semantics* for different ontological entities based on *Semantic*.
    - `aMORDescriptor/` : contains the implementation of the above interfaces based on OWL API. In particular:
      - `MORAxioms` : is an interface containing all the different *Axioms* that can be synchronized between the ontology and the OOP representation.
      - `MORGrounding` : contains the definition of the ontological entities on which such *Axioms* are synchronized.
      - `MORConcept`, `MORIndividual`, `MORData` and `MORObject` property : extend the respective *Semantic* interfaces (above). Those describe how to synchronize a specific *Axioms* set on a *Ground* in/from the ontology.
    - `utility/` : contains some of the classes that implements the above interfaces. Those are the classes that an user of this API want to manipulate for considering OWL entities as `Objects` and no more as `Strings`. Those are meant to be examples and you may want to take them as templates to define an efficient synchronization policy that fits for your needs. More deeply in the directory tree:
      - `MORBase` is a base for all the classes within this package. Some useful extensions are also provided to deal with the interfaces above (i.e.: `MORConceptBase`,  `MORIndividualBase`, `MORData` and `MORObject` property `Base`). All the other classes within this package extend those bases and implements the relative *aMORDescriptors* set of interfaces.
      - `cocept/` : contains some implementations of `MORConcept` to describe the semantics of an `OWLClass`.
      - `dataProperty/` : contains some implementations of `MORDataProperty` to describe the semantics of an `OWLDataProperty`.
      - `objectProperty/` : contains some implementations of `MORObjectProperty` to describe the semantics of an `OWLObjectProperty`.
      - `individual/` : contains some implementations of `MORIndividual` to describe the semantics of an `OWLNamedIndividual`.



# 



### OWLOOP features

#### Main components

As briefly introduced above, OWLOOP is based on three main components: **Ground**, **Axioms** and **Descriptor**. We can say that:

> a *Descriptor* is aimed in maintaining an `Object` synchronized with a specific semantic, represented as a set of *Axioms* that have the same common *Ground*.
> For instance, a *Descriptor* may take care of the fact that an individual `I` (the *Ground*) is disjoint (the semantic) with `X`, `J` and `K` (the *Axioms* set).

So, a *Descriptor* is characterized by: the type of *Ground* and a semantic (disjoint, in the example above, which implies also the type of *Axioms* to be individuals). More descriptors that are based on the same type of *Ground* can be combined together in any combination. For instance, a *Descriptor* that is grounded in an object property can synchronize its *(i)* domain and *(ii)* range by implementing the two "base" descriptors interfaces in a single one class. This is done for efficiency purposes, since synchronizing requires reasoning and ontology search. So, it is a good practice to optimize the composition of "base" descriptors in such a way that they listen only the interesting knowledge for a given application (see the [utility](https://github.com/EmaroLab/owloop/tree/master/src/main/java/it/emarolab/owloop/aMORDescriptor/utility) sub directories for some examples).

More in details, the main ability of a *Descriptor* (or combination of those) are:
 - **read** : it modify its state in order to be equal to the described semantics in the ontological representation. It also returns the changes of such a manipulation. Note, that this does not automatically call the reasoning task but check in the reasoner state (it can be disabled).
 - **write** : it modify the state of the ontology to be equal to the its described state (due to the reasoner conclusions this may require a further reading state to be perfectly synchronized). Also this method returns the changes and do not call the reasoning tasks (as *read*).
 - **getting** : it gets the described semantic, updated to the last read or write operation (it does not query the ontology).
 - **query** : it gets the ontological state of the described semantic, without modify any states.
 - **build** : it returns all the `Descriptor` based on *get* and *read* operations, which have a *Ground* equal to the type of *Axioms*. This indeed is how you can have an OOP paradigms from OWLOOP. For instance, a *Descriptor* that cares about a the individuals in an `OWLClass` (the *Ground*) can *build* a set of other *Descriptors*, all grounding individuals, from which you may want to *get* the data properties values.

#### Developer Point of view

Form the OWLOOP usage point of view, all the interesting *Axioms* and *Ground* have been implemented and their classes are ready to be used. On the other hand, the *Descriptors* have been encapsulated as interfaces almost integrally, but final classes should be implemented in order to use them efficiently, via the combining them. Noteworthy, all the *Descriptor* are always based on the same patter of basic Java operations, as you can see from the [utility](https://github.com/EmaroLab/owloop/tree/master/src/main/java/it/emarolab/owloop/aMORDescriptor/utility) sub directories. Basically you can use the "full" descriptor as template and remove the semantics that you do not want to synchronize, or that you want to synchronize only on constructors, for instance. Noteworthy, you can decide the type of `Descriptor` returned by each *build* operators freely, only limitation is the consistency of *Ground*.

When the most efficient descriptors for an application have been implemented you can just use them by instantiation with specific grounds and ontologies, as it is possible to see from the [testing](https://github.com/EmaroLab/owloop/tree/master/src/test/java/it/emarolab/owloop/aMORDescriptor/utility) examples.

#### OWL extension

Noteworthy, each `Descriptor` has methods to interact with the instantiated ontology through standard [aMOR](https://github.com/EmaroLab/multi_ontology_reference) helping functions, as well as lower layer [OWL API](https://github.com/owlcs/owlapi). From here you can not only call the reasoning task, but also get the `OWLManager`, `OWLDataFactory`, `OWLOntology` and `OWLReferences`. So, other manipulations and queries are possible without further dependencies or bridges.

OWLOOP maps most of the features of OWL but not all. We try to implement the most useful once, as well as the part of the representation that are more lucky to be changed at run time. Nevertheless, this architecture is designed to accommodate all the OWL abilities, it is only matter of time for adding them. As an hint, you may want to use [Protege](http://protege.stanford.edu/) for more sophysticated manipulation that do not change at run time and load such ontology as a starting point for your systems.  

#### API Capability

Follow a list of "base" implemented *Descriptor* interfaces, divided for the type of *Ground* that they can manipulate. So, within the same table, they can be aggregated in any combinations. 

Remember that the type of *built descriptors* have always *Ground* consistent to the type of *Axioms*. Note that OWLOOP cannot *build* `MORRestriction`.

###### The `MORCocept` interface collects all the semantic descriptions grounded on ontological classes (i.e.: `MORGrounding<OWLClass>`). Those are divided in:
| Descriptor   | Axioms of            |                                          Semantic |
|-------------:|:---------------------|--------------------------------------------------:|
|   `Disjoint` | `OWLClass`           |          all the classes disjoint from the ground |
| `Equivalent` | `OWLClass`           |          all the classes equivalent to the ground |
|        `Sub` | `OWLClass`           |                 all the sub classes of the ground |
|      `Super` | `OWLClass`           |               all the super classes of the ground |
|   `Classify` | `OWLNamedIndividual` |       all the individuals belonging to the ground |
| `Definition` | `MORRestriction`     | all the classes and the data/object properties cardinality restrictions (`min`, `max`, `exact`, `some`, `any`) that define the ground. Considered as a unique intersection |

###### The `MORIndividual` interface collects all the semantic descriptions grounded on ontological individuals (i.e.: `MORGrounding<OWLNamedIndividual>`). Those are divided in:
| Descriptor   | Axioms of                                        |                                                           Semantic |
|-------------:|:-------------------------------------------------|-------------------------------------------------------------------:|
|   `Disjoint` | `OWLNamedIndividual`                             |                      all the different individuals from the ground |
| `Equivalent` | `OWLNamedIndividual`                             |                             all the same individuals as the ground |
|       `Type` | `OWLClass`                                       |                        all the classes in which the ground belongs |
| `ObjectLink` | `OWLObjectProperty` sets of `OWLNamedIndividual` | all (or the specified) object properties (and all their values) applied to the ground |
|   `DataLink` | `OWLDataProperty` sets of `OWLLiteral`           |   all (or the specified) data properties (and all their values) applied to the ground |

###### The `MORObjectProperty` interface collects all the semantic descriptions grounded on ontological object property (i.e.: `MORGrounding<OWLObjectProperty>`). Those are divided in:
| Descriptor   | Axioms of           |                                                Semantic |
|-------------:|:--------------------|--------------------------------------------------------:|
|   `Disjoint` | `OWLObjectProperty` |      all the disjoint object properties from the ground |
| `Equivalent` | `OWLObjectProperty` |      all the equivalent object properties to the ground |
|        `Sub` | `OWLObjectProperty` |             all the sub object properties of the ground |
|      `Super` | `OWLObjectProperty` |            all the object data properties of the ground |
|      `Range` | `MORRestriction`    | all the classes and the data/object properties cardinality restrictions (`min`, `max`, `exact`, `some`, `any`) of the ground range. Considered as separate restrictions. |
|     `Domain` | `MORRestriction`    | all the classes and the data/object properties cardinality restrictions (`min`, `max`, `exact`, `some`, `any`) of the ground domain. Considered as separate restrictions. |

###### The `MORDataProperty` interface collects all the semantic descriptions grounded on ontological data property (i.e.: `MORGrounding<OWLDataProperty>`). Those are divided in:
| Descriptor   | Axioms of         |                                               Semantic |
|-------------:|:------------------|-------------------------------------------------------:|
|   `Disjoint` | `OWLDataProperty` |       all the disjoint data properties from the ground |
| `Equivalent` | `OWLDataProperty` |       all the equivalent data properties to the ground |
|        `Sub` | `OWLDataProperty` |              all the sub data properties of the ground |
|      `Super` | `OWLDataProperty` |            all the super data properties of the ground |
|      `Range` | `MORRestriction`  | all the data type (`Integer`, `Boolean`, `String`, `Double`, `Float` and `Long`) restrictions of the ground range |
|     `Domain` | `MORRestriction`  | all the classes and the data/object properties cardinality restrictions (`min`, `max`, `exact`, `some`, `any`) of the ground domain. Considered as separate restrictions |


##### Known Problem

We are currently working in a known issue about the manipulation of a class definition (especially on removing restrictions). 
This step depends on the super and sub classes of the class to be manipulated. In order to go around it,
for the moment you may want to be very careful about the hierarchy of the class to manipulate (this strongly depends on the shape of your ontology) or clear its
definition and regenerate it from scratch, instead of updating. 


# 



### Contacts

For any information, support, discussion or comments do not hesitate to contact me through this Github repository or at: 
[luca.buoncompagni@edu.unige.it](mailto:luca.buoncompagni@edu.unige.it), 
