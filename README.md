This is Behavior Capture and Test (BCT), a dynamic analysis toolset to support program understanding and debugging.
  
This repository contains the sources of the BCT user interfaces: a standalone library and an Eclipse plugin.

To compile the library:

cd to BCT_EclipseLibraryStubs

ant eclipseStandaloneAll

The project BCT is required in a sister folder. For example:

/parent/BCT

/parent/BCT_TOOLSET



We suggest to import all the projects in an Eclipse workspace.



BCT has been described in detail in the following publications:

* Leonardo Mariani, Fabrizio Pastore, Mauro Pezzè:
Dynamic Analysis for Diagnosing Integration Faults. IEEE Trans. Software Eng. 37(4): 486-508 (2011)

* Leonardo Mariani, Fabrizio Pastore, Mauro Pezzè: A toolset for automated failure analysis. ICSE 2009: 563-566

* Leonardo Mariani, Mauro Pezzè:
Dynamic Detection of COTS Component Incompatibility. IEEE Software 24(5): 76-85 (2007)

* L. Mariani and M. Pezze`, “Behavior capture and test: automated analysis
of component integration,” in proceedings on the 10th International Con- ference on Engineering Complex Computer Systems. IEEE Computer Society, 2005, pp. 292–301.




