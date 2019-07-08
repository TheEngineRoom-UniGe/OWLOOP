# OWLOOP

Enabling a developer to use **O**ntology **W**eb **L**anguage (OWL) along with its reasoning capabilities in an **O**bject **O**riented **P**rogramming (OOP) paradigm, by providing an easy to use interface.

Although OWL and OOP paradigms have similar structure, there are some key differences between them; see this [W3C publication](https://www.w3.org/2001/sw/BestPractices/SE/ODSD/) for more details about the differences. Nevertheless, it is possible to use OWL along with its reasoning capabilities within applications developed in an OOP paradigm, by using the classic [OWL-API](https://github.com/owlcs/owlapi).
But, the usage of classic OWL-API leaves your application filled with lots of boilerplate code. Therefore, the **OWLOOP-API** (built on top of OWL-API), not only reduces boilerplate code but enables interaction with 'OWL entities', i.e, Concept (also known as, Class), Individual, Object property and Data property as objects within the OOP paradigm. These objects are termed as Descriptors (i.e., ConceptDescriptor, IndividualDescriptor, ObjectPropertyDescriptor and DataPropertyDescriptor).  
Furthermore, with the help of a descriptor, OWLOOP enables synchronization of axioms between the OWL paradigm and the OOP paradigm.

## Table of Contents
1. Reference to the publication
2. Installation
3. Usage
4. License
5. Dependencies
6. Known Issues to be Resolved
7. Author's message

#

## 1. Reference to the Publication

This repository has been published in the journal ... (**todo**: add the link here once the repo gets published)

## 2. Installation

- Clone or download the repository.
- Open the project by opening the file `owloop/build.gradle` in IntelliJ, with the following parameters ... (**todo**: show screen shot of the correct parameters)

    **OR**

- Add the following dependency in your project's `build.gradle` file ... (**todo**: the developer should be able to add a single line and thus have acess to the OWLOOP API)
```gradle
compile group: 'com.github.owloop', name: 'owloop-api', version: '1.0'
```

## 3. Usage

To explain the usage of OWLOOP, we present in this repository's [wiki](https://github.com/EmaroLab/owloop/wiki):

- The overall [structure of the OWLOOP project](https://github.com/EmaroLab/owloop/wiki/1.-OWLOOP:-Project-Structure-&-JavaDoc#project-structure) and its [JavaDoc](https://github.com/EmaroLab/owloop/wiki/1.-OWLOOP:-Project-Structure-&-JavaDoc#javadoc).

- The [Descriptor](https://github.com/EmaroLab/owloop/wiki/2.-The-OWLOOP-Descriptor#what-is-a-descriptor), its [types](https://github.com/EmaroLab/owloop/wiki/2.-The-OWLOOP-Descriptor#what-are-the-types-of-descriptors), the possible [expressions](https://github.com/EmaroLab/owloop/wiki/2.-The-OWLOOP-Descriptor#descriptor-expressions) that each type can implement and the useful [methods](https://github.com/EmaroLab/owloop/wiki/2.-The-OWLOOP-Descriptor#descriptor-methods) it provides.

- [Examples](https://github.com/EmaroLab/owloop/wiki/3.-Example:-Creating-a-Simple-or-a-Compound-Descriptor) that show construction of a [simple descriptor](https://github.com/EmaroLab/owloop/wiki/3.-Example:-Creating-a-Simple-or-a-Compound-Descriptor#a-simple-concept-descriptor) vs a [compound descriptor](https://github.com/EmaroLab/owloop/wiki/3.-Example:-Creating-a-Simple-or-a-Compound-Descriptor#a-compound-concept-descriptor).

- An [example](https://github.com/EmaroLab/owloop/wiki/4.-Example:-Adding-Axioms-to-an-Ontology) that shows how to add axioms to an ontology, using descriptors.

- An [example](https://github.com/EmaroLab/owloop/wiki/5.-Example:-Inferring-Axioms-from-an-Ontology) that shows how to infer some knowledge (i.e., axioms) from axioms already present in an ontology, and the particular usefulness of the [descriptor build method](https://github.com/EmaroLab/owloop/wiki/5.-Example:-Inferring-Axioms-from-an-Ontology#descriptor-build-method).

- An [example](https://github.com/EmaroLab/owloop/wiki/6.-Example:-Removing-Axioms-from-an-Ontology) that shows how to remove axioms from an ontology, using descriptors.

## 4. License

OWLOOP is under the license: [GNU General Public License v3.0](owloop/LICENSE)


## 5. Dependencies

[Gradle](https://gradle.org/) is used for building OWLOOP and it has the following dependencies; can be found in `owloop/build.gradle`:

- [aMOR](https://github.com/EmaroLab/multi_ontology_reference): **a** **M**ulti-**O**ntology **R**eference library (based on OWL-API) that provides helper functions for OWL Ontologies.
- [OWL-API](https://github.com/owlcs/owlapi): a Java API for creating, manipulating and serialising OWL Ontologies.
- [Pellet](https://github.com/stardog-union/pellet): an open source OWL 2 DL reasoner.
- [JUnit](https://github.com/junit-team): a programmer-oriented testing framework for Java.

## 6. Issues to be Resolved

We are currently working on a known issue, i.e., during manipulation of a class definition, especially while removing restrictions, due to an unknown (for now) reason, the restrictions do not get removed. 
To circumvent the problem (for now), we clear the class hierarchy and regenerate it from scratch, instead of updating it. 

## 7. Author's message
Feel free to contribute to OWLOOP by sharing your thoughts and ideas, raising issues (if found) and providing bug-fixes. 
For any information or support, please do not hesitate to contact us through this Github repository or by email.

[luca.buoncompagni@edu.unige.it](mailto:luca.buoncompagni@edu.unige.it),
[kareem.syed.yusha@dibris.unige.it](mailto:kareem.syed.yusha@dibris.unige.it).

# Notes to self

## 8. Check List Before Publishing Repo on Maven Central

- [ ] Add the UML in /documentation directory
- [ ] Create a fresh JavaDoc in /documentation directory
- [x] Fix the bug of removing axioms. (*The problem was in /core Axiom*)
- [ ] Finalize a plan for AMOR dependency. (*Luca gives green signal for pushing AMOR to Maven Central*)
- [ ] Make OWLOOP as a release 2.0
- [ ] Add disjoint to the Classes (ROBOT disjoint with DOOR AND LOCATION) and Individuals (all disjoint to each other)
- [ ] Merge with the master branch
