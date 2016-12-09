INTROSDE 2016 Assignment 03: SOAP Web Services  - CLIENT
===============

--------

The code
-------------

In the /src folder there are the Java classes; the 'client' package contains two clients with all the requests, the PeopleClient sends the request to my server and the PartnerClient to my partner's server. The 'document.ws' package contains all the classes generated by _wsimport_ command with MY wsdl file and the 'assignment.soap' package contains all the classes generated by _wsimport_ command with my PARTNER's wsdl file. In the project root there are _client.log_ and _partner-client.log_, the logs file whith the output of the clients. 


#### My server can be found here:
Link git: 	https://github.com/michelebof/introsde-2016-assignment-3

Link wsdl file: https://introsde2016-assignment3.herokuapp.com/ws/people?wsdl


#### Information of my partner student:
Name:	Sara Gasperetti

Link git:	https://github.com/SaraGasperetti

Link wsdl file:	https://introsde2016-assignment-3.herokuapp.com/soap/people?wsdl 


----------

How run the code 
---------------------
The code can be run simply execute in the terminal ```git clone https://github.com/michelebof/introsde-2016-assignment-3-client```
Then:
 - ```ant execute.client``` : to install all the dependencies, to execute the PARTNER's client and save the requests/responses information into the log file 'partner-client.log'
 - ```ant execute.MYclient``` : to install all the dependencies, to execute MY client and save the requests/responses information into the log file 'client.log'