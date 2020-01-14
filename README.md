<img src="https://github.com/EmaroLab/owloop/blob/master/gitRepoResources/images/owloopLogo.png" width="310">
 
#

Enabling a developer to use **O**ntology **W**eb **L**anguage (OWL) along with its reasoning capabilities in an **O**bject **O**riented **P**rogramming (OOP) paradigm, by providing an easy to use interface.

Although OWL and OOP paradigms have similar structure, there are some key differences between them; see this [W3C publication](https://www.w3.org/2001/sw/BestPractices/SE/ODSD/) for more details about the differences. Nevertheless, it is possible to use OWL along with its reasoning capabilities within applications developed in an OOP paradigm, by using the classic [OWL-API](https://github.com/owlcs/owlapi).
But, the usage of the classic OWL-API leaves your application code with lots of boilerplate. Therefore, the **OWLOOP-API** (built on top of OWL-API), not only reduces boilerplate code but enables interaction with 'OWL entities', i.e, Concept (also known as, Class), Individual, Object property and Data property as objects within the OOP paradigm. These objects are termed as Descriptors (i.e., ConceptDescriptor, IndividualDescriptor, ObjectPropertyDescriptor and DataPropertyDescriptor).  
Furthermore, with the help of descriptor/s, OWLOOP enables synchronization of axioms (OWL2-DL axioms) between the OWL paradigm (ontology file/s) and the OOP paradigm (application code).

## Table of Contents
1. Reference to the publication
2. Installation
3. Quick overview
4. Wiki documentation
5. Dependencies
6. Developers' message
7. License

## 1. Reference to the Publication

This repository has been published in the journal ... *will be updated soon* ...

## 2. Installation

- Clone or download the repository.
- Open the project by opening the file `owloop/build.gradle` in IntelliJ, with the following parameters ... (**todo**: show screen shot of the correct parameters)

    **OR**

- Add the following dependency in your project's `build.gradle` file ... (**todo**: the developer should be able to add a single line and thus have acess to the OWLOOP API)
```gradle
compile group: 'com.github.owloop', name: 'owloop-api', version: '1.0'
```

## 3. Quick overview

OWLOOP API enables a developer to construct and/or utilize descriptors (that are Java-classes with helpful methods) 
for manipulating ontologies with reduced boilerplate code. Thus, the API intends to ease the integration of ontologies 
within software applications in varied domains, i.e., biomedical, information retrieval, robotics etc.

Consider an object **D** of a descriptor (i.e., a Java-class). It has an internal structure composed of three parts, 
i.e., the ground **G**, the expression(s) **E** and the entity-set **E-S**, which conceptually looks like as follows: 
<img src="https://github.com/EmaroLab/owloop/blob/master/gitRepoResources/images/g_e_es.png" width="90">. 
An ontology **O** can be instantiated using the `OntologyReference` (i.e., a Java-class). If an ontology file (.owl) 
already exists then it gets loaded into the *program memory*, else if an ontology file does not exist then it gets created 
and then is loaded into the *program memory*.
The descriptor object **D** provides methods that allow manipulation of an *in-memory* ontology **O**. Which ontology (or 
ontologies) get manipulated by **D** depends upon the `OntologyReference` object associated to **D**.

The following diagram shows the methods of a descriptor object. Each method is described in the wiki 
documentation.

<p align="center">
  <img src="https://github.com/EmaroLab/owloop/blob/master/gitRepoResources/images/d_methods.png" width="400">
</p>

**Note that** `load()` is a method of an `OntologyReference` object and `save()` is a method of both, an 
`OntologyReference` object and a descriptor object.

## 4. Wiki documentation

The OWLOOP API's core aspects are described in this repository's [wiki](https://github.com/EmaroLab/owloop/wiki):

- [Structure](https://github.com/EmaroLab/owloop/wiki/1.-Project-Structure-&-JavaDoc#project-structure) of the 
OWLOOP API project.

- [JavaDoc](https://github.com/EmaroLab/owloop/wiki/1.-Project-Structure-&-JavaDoc#javadoc) of the OWLOOP API project.

- What is a [Descriptor](https://github.com/EmaroLab/owloop/wiki/2.-What-is-a-Descriptor%3F) in OWLOOP?
    - [Types](https://github.com/EmaroLab/owloop/wiki/2.1.-Types-of-Descriptors) of 
    descriptors.
    - [Expressions](https://github.com/EmaroLab/owloop/wiki/2.2.-Possible-Expressions-for-each-Type-of-Descriptor) that can 
    be implemented for each type of descriptor. 
    - [Methods](https://github.com/EmaroLab/owloop/wiki/2.3.-Methods-of-a-Descriptor-object) of a descriptor.
    - [Preconstructed Descriptors](https://github.com/EmaroLab/owloop/wiki/2.4.-Preconstructed-Descriptors) provided by 
    OWLOOP.

- Code examples that show how to: 

    - [Construct](https://github.com/EmaroLab/owloop/wiki/3.-Example:-Constructing-a-Descriptor) a type of descriptor.

    - [Add](https://github.com/EmaroLab/owloop/wiki/4.-Example:-Adding-Axioms-to-an-Ontology) axioms to an ontology by 
    using descriptors.

    - [Infer](https://github.com/EmaroLab/owloop/wiki/5.-Example:-Inferring-Axioms-from-an-Ontology) some knowledge 
    (i.e., axioms) from the axioms already present within an ontology by using descriptors. 
    This example also highlights the use of the `build()` method.

    - [Remove](https://github.com/EmaroLab/owloop/wiki/6.-Example:-Removing-Axioms-from-an-Ontology) axioms from an 
    ontology by using descriptors.

## 5. Dependencies

[Gradle](https://github.com/EmaroLab/owloop/blob/master/build.gradle) is used for building OWLOOP and it has the following dependencies in it:

- [aMOR](https://github.com/EmaroLab/multi_ontology_reference): **a** **M**ulti-**O**ntology **R**eference library 
(based on OWL-API) that provides helper functions to OWLOOP.
- [OWL-API](https://github.com/owlcs/owlapi): a Java API for creating, manipulating and serialising OWL Ontologies.
- [Pellet](https://github.com/stardog-union/pellet): an open source OWL 2 DL reasoner.
- [JUnit](https://github.com/junit-team): a programmer-oriented testing framework for Java.

## 6. Developers' message
Feel free to contribute to OWLOOP by sharing your thoughts and ideas, raising issues (if found) and providing bug-fixes. 
For any information or support, please do not hesitate to contact us through this Github repository or by email.

Developed by [luca.buoncompagni@edu.unige.it](mailto:luca.buoncompagni@edu.unige.it) and 
[kareem.syed.yusha@dibris.unige.it](mailto:kareem.syed.yusha@dibris.unige.it) under the supervision of 
[fulvio.mastrogiovanni@unige.it](mailto:fulvio.mastrogiovanni@unige.it).

## 7. License

OWLOOP is under the license: [GNU General Public License v3.0](https://github.com/EmaroLab/owloop/blob/master/LICENSE)