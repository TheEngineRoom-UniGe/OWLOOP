<p align="center">
  <img src="https://github.com/EmaroLab/owloop/blob/master/gitRepoResources/images/owloopLogo.png" width="310">
</p>

#
[![code](https://img.shields.io/badge/code-Java-<COLOR>.svg)](https://en.wikipedia.org/wiki/Java_(programming_language))
[![GPLv3 license](https://img.shields.io/badge/license-GPLv3-blue.svg)](https://github.com/EmaroLab/owloop/blob/master/LICENSE)
[![release](https://img.shields.io/badge/release-v2.1-red.svg)](https://github.com/EmaroLab/owloop/releases/tag/2.1)



Enabling a developer to use **O**ntology **W**eb **L**anguage (OWL) along with its reasoning capabilities in an **O**bject **O**riented **P**rogramming (OOP) paradigm, by providing an easy to use API, i.e., OWLOOP. 

Although OWL and OOP paradigms have similar structure, there are some key differences between them; see this [W3C publication](https://www.w3.org/2001/sw/BestPractices/SE/ODSD/) for more details about the differences. Nonetheless, it is possible to use OWL along with its reasoning capabilities within applications developed in an OOP paradigm, by using the classic [OWL-API](https://github.com/owlcs/owlapi).
But, the usage of the classic OWL-API leaves your project with lots of boilerplate code. Therefore, the **OWLOOP-API** (built on top of OWL-API), reduces boilerplate code by enabling interaction with 'OWL entities' (i.e, Concept (also known as Class), Individual, Object property and Data property) as objects within the OOP paradigm. These objects are termed as Descriptors (i.e., ClassDescriptor, IndividualDescriptor, ObjectPropertyDescriptor and DataPropertyDescriptor). By using descriptor(s), OWLOOP synchronizes axioms (OWL2-DL axioms) between the OOP paradigm (your application's code) and the OWL paradigm (OWL ontology XML/RDF file(s)).

## Example of a real-world system that used OWLOOP API:

This video [(link)](https://youtu.be/SEEqSawrQNo) shows a smart home system recognising human activities. The system uses a network of multiple ontologies to recognise specific activities. The network of multiple ontologies was developed using OWLOOP API.  

## Table of Contents
1. [Reference to the publication](https://github.com/TheEngineRoom-UniGe/OWLOOP#1-reference-to-the-publication)
2. [Getting Started with OWLOOP](https://github.com/TheEngineRoom-UniGe/OWLOOP#2-getting-started-with-owloop)
   * 2.1. [Prerequisites for your Operating System](https://github.com/TheEngineRoom-UniGe/OWLOOP#21-prerequisites-for-your-operating-system)
   * 2.2. [Add OWLOOP dependencies to your project](https://github.com/TheEngineRoom-UniGe/OWLOOP#22-add-owloop-dependencies-to-your-project)
   * 2.3. [Use OWLOOP in your project](https://github.com/TheEngineRoom-UniGe/OWLOOP#23-use-owloop-in-your-project) 
3. [Overview of important Java-classes (in OWLOOP) and their methods](https://github.com/TheEngineRoom-UniGe/OWLOOP#3-overview-of-important-java-classes-in-owloop-and-their-methods)
4. [Wiki documentation](https://github.com/TheEngineRoom-UniGe/OWLOOP#4-wiki-documentation)
5. [Some details about OWLOOP dependencies](https://github.com/TheEngineRoom-UniGe/OWLOOP#5-some-details-about-owloop-dependencies)
6. [Developers' message](https://github.com/TheEngineRoom-UniGe/OWLOOP#6-developers-message)
7. [License](https://github.com/TheEngineRoom-UniGe/OWLOOP#7-license)

## 1. Reference to the Publication

OWLOOP API is a peer reviewed software. It has been [published by Elsevier in its journal SoftwareX](https://doi.org/10.1016/j.softx.2021.100952).
The publication presents in detail the motivation for developing OWLOOP. Furthermore, it describes the design of the API and presents the API's usage with illustrative examples. 

## 2. Getting Started with OWLOOP

### 2.1. Prerequisites for your Operating System

* Install an IDE, for example [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).
* Install [Java JRE](https://www.oracle.com/java/technologies/javase-jre8-downloads.html) and [Java JDK](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html).

### 2.2. Add OWLOOP dependencies to your project

* Download OWLOOP related jar files.
  * [owloop-2.1.jar](https://github.com/EmaroLab/owloop/releases/tag/2.1)
  * [amor-2.2.jar](https://github.com/EmaroLab/multi_ontology_reference/releases/tag/v2.2)
  
* Add OWLOOP related jar files to your project: 

<p align="center">
  <img src="https://github.com/TheEngineRoom-UniGe/OWLOOP/blob/master/gitRepoResources/images/owloop_setup.gif">
</p>

**First Step**: Create a new project with `Java` as the programming language and `Gradle` as the build tool.

**Second Step**: Create a directory called `lib` and place the OWLOOP related jar files in it.

**Third Step**: Modify your `build.gradle` file, as follows:

  * Add `flatDir { dirs 'lib' }` within the `repositories{}` section, as shown below:
  
  ```gradle
  repositories {
      mavenCentral()
  
      flatDir {
          dirs 'lib'
      }
  }
  ```
  
  * Add the required dependencies (i.e., owloop, amor and pellet), as shown below :point_down:
  
  ```gradle
  dependencies {
      // testCompile group: 'junit', name: 'junit', version: '4.12'
  
      implementation 'it.emarolab.amor:amor:2.2'
      implementation 'it.emarolab.owloop:owloop:2.1'
      implementation group: 'com.github.galigator.openllet', name: 'openllet-owlapi', version: '2.5.1'
  }
  ```
  It is normal that a warning like `SLF4J: Class path contains multiple SLF4J bindings` occurs.
  
**Final Step**: You are now ready to create/use OWL ontologies in your project/application :fire:, by using OWLOOP descriptors in your code!. 

### 2.3. Use OWLOOP in your project

* This is an example code that shows how to create an OWL file and add axioms to it.

```java
import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.owloop.core.Axiom;
import it.emarolab.owloop.descriptor.utility.classDescriptor.FullClassDesc;
import it.emarolab.owloop.descriptor.utility.individualDescriptor.FullIndividualDesc;
import it.emarolab.owloop.descriptor.utility.objectPropertyDescriptor.FullObjectPropertyDesc;

public class someClassInMyProject {

    public static void main(String[] args) {

        // Disabling 'internal logs' (so that our console is clean)
        Axiom.Descriptor.OntologyReference.activateAMORlogging(false);

        // Creating an object that is 'a reference to an ontology'
        OWLReferences ontoRef = Axiom.Descriptor.OntologyReference.newOWLReferencesCreatedWithPellet(
                "robotAtHomeOntology",
                "src/main/resources/robotAtHomeOntology.owl",
                "http://www.semanticweb.org/robotAtHomeOntology",
                true
        );

        // Creating some 'classes in the ontology'
        FullClassDesc location = new FullClassDesc("LOCATION", ontoRef);
        location.addSubClass("CORRIDOR");
        location.addSubClass("ROOM");
        location.writeAxioms();
        FullClassDesc robot = new FullClassDesc("ROBOT", ontoRef);
        robot.addDisjointClass("LOCATION");
        robot.writeAxioms();

        // Creating some 'object properties in the ontology'
        FullObjectPropertyDesc isIn = new FullObjectPropertyDesc("isIn", ontoRef);
        isIn.addDomainClassRestriction("ROBOT");
        isIn.addRangeClassRestriction("LOCATION");
        isIn.writeAxioms();
        FullObjectPropertyDesc isLinkedTo = new FullObjectPropertyDesc("isLinkedTo", ontoRef);
        isLinkedTo.addDomainClassRestriction("CORRIDOR");
        isLinkedTo.addRangeClassRestriction("ROOM");
        isLinkedTo.writeAxioms();

        // Creating some 'individuals in the ontology'
        FullIndividualDesc corridor1 = new FullIndividualDesc("Corridor1", ontoRef);
        corridor1.addObject("isLinkedTo", "Room1");
        corridor1.addObject("isLinkedTo", "Room2");
        corridor1.writeAxioms();
        FullIndividualDesc robot1 = new FullIndividualDesc("Robot1", ontoRef);
        robot1.addObject("isIn", "Room1");
        robot1.writeAxioms();
        
        // Saving axioms from in-memory ontology to the the OWL file located in 'src/main/resources'
        ontoRef.saveOntology();
    }
}
```

* After running the above code, the OWL file `robotAtHomeOntology` gets saved in `src/main/resources`. We can open the OWL file in [Protege](https://protege.stanford.edu/) and view the ontology.

<p align="center">
  <img src="https://github.com/TheEngineRoom-UniGe/OWLOOP/blob/master/gitRepoResources/images/add_axioms_and_view_in_protege.gif">
</p>

## 3. Overview of important Java-classes (in OWLOOP) and their methods

<p align="center">
  <img src="https://github.com/TheEngineRoom-UniGe/OWLOOP/blob/master/gitRepoResources/images/descriptor_methods.png" width="400">
</p>

| Java-classes                                                                                                                                                                                                                                                                                             | methods                                                                                                                                                                                                             |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Path**: OWLOOP/src/.../owloop/core/<br><br>This path contains, all core Java-classes. Among them, one in particular is immediately useful, i.e., `OntologyReference`. It allows to create/load/save an OWL ontology file.                                                                                  |                                                                                                                                                                                                                     |
|                                                                                                                                                                                                                                                                                                          | The following method allows to enable/disable display of internal logging:<br><br>`activateAMORlogging()`                                                                                                               |
|                                                                                                                                                                                                                                                                                                          | The following methods allow to instantiate an object of the Java-class `OWLReferences`:<br><br>`newOWLReferencesCreatedWithPellet()`<br>`newOWLReferencesFromFileWithPellet()`<br>`newOWLReferencesFromWebWithPellet()` |
|                                                                                                                                                                                                                                                                                                          | The object of Java-class `OWLReferences`, offers the following methods:<br><br>![#0000FF](https://via.placeholder.com/15/0000FF/000000?text=+)`saveOntology()`<br>![#0000FF](https://via.placeholder.com/15/0000FF/000000?text=+)`synchronizeReasoner()`<br>![#0000FF](https://via.placeholder.com/15/0000FF/000000?text=+)`load()` // is hidden and used internally                                                 |
| **Path**: OWLOOP/src/.../owloop/descriptor/utility/<br><br>This path contains the directories that contain all Java-classes that are (as we call them) descriptors. The directories are the following:<br>/classDescriptor<br>/dataPropertyDescriptor<br>/objectPropertyDescriptor<br>/individualDescriptor. |                                                                                                                                                                                                                     |
|                                                                                                                                                                                                                                                                                                          | The object of a Descriptor, offers the following methods:<br><br>![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+)`add...()`<br>![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+)`remove...()`<br>![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+)`build...()`<br>![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+)`get...()`<br>![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+)`query...()`<br>![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+)`writeAxioms()`<br>![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+)`readAxioms()`<br>![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+)`reason()`<br>![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+)`saveOntology()`       |

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

## 5. Some details about OWLOOP dependencies

Please use [Gradle](https://github.com/EmaroLab/owloop/blob/master/build.gradle) as the build tool for your project, and include the following dependencies in your project's `build.gradle` file:

* [aMOR](https://github.com/EmaroLab/multi_ontology_reference) (latest release is **amor-2.2**): **a** 
**M**ulti-**O**ntology **R**eference library is based on OWL-API and it provides helper functions to OWLOOP.
  * [OWL-API](https://github.com/owlcs/owlapi): a Java API for creating, manipulating and serialising OWL Ontologies. We 
  have included **owlapi-distribution-5.0.5** within **amor-2.2**.
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

##

<p align="center">
  <img src="https://github.com/TheEngineRoom-UniGe/OWLOOP/blob/master/gitRepoResources/images/unige_ter_logo.png" width="400">
</p>
