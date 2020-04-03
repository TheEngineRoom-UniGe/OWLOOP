<img src="https://github.com/EmaroLab/owloop/blob/master/gitRepoResources/images/owloopLogo.png" width="310">

#
[![code](https://img.shields.io/badge/code-Java-<COLOR>.svg)](https://en.wikipedia.org/wiki/Java_(programming_language))
[![GPLv3 license](https://img.shields.io/badge/license-GPLv3-blue.svg)](https://github.com/EmaroLab/owloop/blob/master/LICENSE)
[![release](https://img.shields.io/badge/release-v2.1-red.svg)](https://github.com/EmaroLab/owloop/releases/tag/2.1)



Enabling a developer to use **O**ntology **W**eb **L**anguage (OWL) along with its reasoning capabilities in an **O**bject **O**riented **P**rogramming (OOP) paradigm, by providing an easy to use API, i.e., OWLOOP. 

Although OWL and OOP paradigms have similar structure, there are some key differences between them; see this [W3C publication](https://www.w3.org/2001/sw/BestPractices/SE/ODSD/) for more details about the differences. Nonetheless, it is possible to use OWL along with its reasoning capabilities within applications developed in an OOP paradigm, by using the classic [OWL-API](https://github.com/owlcs/owlapi).
But, the usage of the classic OWL-API leaves your project with lots of boilerplate code. Therefore, the **OWLOOP-API** (built on top of OWL-API), reduces boilerplate code by enabling interaction with 'OWL entities' (i.e, Concept (also known as Class), Individual, Object property and Data property) as objects within the OOP paradigm. These objects are termed as Descriptors (i.e., ClassDescriptor, IndividualDescriptor, ObjectPropertyDescriptor and DataPropertyDescriptor). By using descriptor(s), OWLOOP synchronizes axioms (OWL2-DL axioms) between the OOP paradigm (your application's code) and the OWL paradigm (OWL ontology XML/RDF file(s)).

## Table of Contents
1. Reference to the publication
2. Installation
3. Quick overview
4. Wiki documentation
5. Dependencies
6. Developers' message
7. License

## 1. Reference to the Publication

... *will be updated soon* ...

## 2. Installation

Given that you have a Java project with Gradle as your build tool.

* **Step 1**: Create a directory called `lib` in your project's main directory wherein `build.gradle` file is also located.
* **Step 2**: Download the files [owloop-2.1.jar](https://github.com/EmaroLab/owloop/releases/tag/2.1) and [amor-2.2.jar](https://github.com/EmaroLab/multi_ontology_reference/releases/tag/v2.2), and place them within the `lib` directory created in Step 1.
* **Step 3**: In your project's `build.gradle` file,

  * add `flatDir { dirs 'lib' }` within the `repositories{}` section, as shown below:
  
  ```gradle
  repositories {
      mavenCentral()
  
      flatDir {
          dirs 'lib'
      }
  }
  ```
  
  * add **amor-2.1** and **owloop-2.2** as your project's dependencies by including them within the `dependencies{}` section, as shown below:
  
  ```gradle
  dependencies {
      compile 'it.emarolab.amor:amor:2.2'
      compile 'it.emarolab.owloop:owloop:2.1'
  }
  ```
  
OWLOOP features should now be accessible to you within your code.

## 3. Quick overview

OWLOOP API enables a developer to construct and/or utilize preconstructed descriptors (that are Java-classes with helpful methods) 
for manipulating ontologies with reduced boilerplate code. Thus, the API intends to ease the integration of ontologies 
within software applications in varied domains, i.e., biomedical, information retrieval, robotics etc.

Consider an object **D** of a descriptor (i.e., a Java-class). It has an internal structure composed of three parts, 
i.e., the ground **G**, the expression(s) **E** and the entity-set **E-S**, which conceptually looks like as follows: 
<img src="https://github.com/EmaroLab/owloop/blob/master/gitRepoResources/images/g_e_es.png" width="90">. 
Consider an ontology **O** that can be instantiated in association to a (.owl) ontology file. This instantiation is enabled by the use of the Java-class `OntologyReference`. If the ontology file already exists then it gets loaded into the *program memory* and if it does not exist then it first gets created and then is loaded into the *program memory*.
A descriptor object **D** provides methods that allow manipulation of an *in-memory* ontology **O**. The ontology (or 
ontologies) that can be manipulated by **D** depend upon the `OntologyReference` object that is associated to **D** during its instantiation.

The following diagram shows the methods of a descriptor object **D**. Each method is described in the wiki 
documentation.

<p align="center">
  <img src="https://github.com/EmaroLab/owloop/blob/master/gitRepoResources/images/d_methods.png" width="400">
</p>

**Note that** `load()` is a method of an `OntologyReference` object and `save()` is a method of both, an 
`OntologyReference` object and a descriptor object **D**.

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

[Gradle](https://github.com/EmaroLab/owloop/blob/master/build.gradle) is used for building OWLOOP. The following are 
the minimum dependencies that your `build.gradle` file should include in order to use OWLOOP features:

* [aMOR](https://github.com/EmaroLab/multi_ontology_reference) (latest release is **amor-2.1**): **a** 
**M**ulti-**O**ntology **R**eference library is based on OWL-API and it provides helper functions to OWLOOP.
  * [OWL-API](https://github.com/owlcs/owlapi): a Java API for creating, manipulating and serialising OWL Ontologies. We 
  have included **owlapi-distribution-5.0.5** within **amor-2.1**.
* [OWLOOP](https://github.com/EmaroLab/owloop) (latest release is **owloop-2.2**): an API that enables easy manipulation 
of OWL (Ontology Web Language) ontologies from within an OOP (Object Oriented Programming) paradigm. 
  * [Pellet](https://github.com/stardog-union/pellet): an open source OWL 2 DL reasoner. We have included 
  **openllet-owlapi-2.5.1** within **owloop-2.2**.

## 6. Developers' message
Feel free to contribute to OWLOOP by sharing your thoughts and ideas, raising issues (if found) and providing bug-fixes. 
For any information or support, please do not hesitate to contact us through this Github repository or by email.

Developed by [luca.buoncompagni@edu.unige.it](mailto:luca.buoncompagni@edu.unige.it) and 
[kareem.syed.yusha@dibris.unige.it](mailto:kareem.syed.yusha@dibris.unige.it) under the supervision of 
[fulvio.mastrogiovanni@unige.it](mailto:fulvio.mastrogiovanni@unige.it).

## 7. License

OWLOOP is under the license: [GNU General Public License v3.0](https://github.com/EmaroLab/owloop/blob/master/LICENSE)
