Camel Java Router Project
=========================

General Info
------------
This is a Camel + OSGi + CXF implementation of an imaginary battery power plant controller.

Setup
-----

To build this project use

    mvn compile

To run this project from within Maven use

    mvn exec:java

For more help see the Apache Camel documentation

    http://camel.apache.org/
    
Karaf
-----
    
To run this project from within Karaf (3+) build the project using 

	mvn install

and then run the Karaf commands

	feature:repo-add file:///<path-to-feature-xml>/features.xml
	
and

	feature:install powerplant-controller-tinypart
	
You will have to adjust the path in features.xml manually. Also note that support for
Karaf 4 is not up to speed yet.
