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

##### Known Problem

We are currently working in a known issue about the manipulation of a class definition (especially on removing restrictions). 
This step depends on the super and sub classes of the class to be manipulated. In order to go around it,
for the moment you may want to be very careful about the hierarchy of the class to manipulate (this strongly depends on the shape of your ontology) or clear its
definition and regenerate it from scratch, instead of updating. 


# 

### Contacts

For any information, support, discussion or comments do not hesitate to contact me through this Github repository or at: 
[luca.buoncompagni@edu.unige.it](mailto:luca.buoncompagni@edu.unige.it), 
