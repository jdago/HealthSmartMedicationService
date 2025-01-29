# Medication Service

The HealthSmart Medication Service provides functionality for patients to view medications, search medication by name or NDC, retrieve complete medication history, or find a label of a medication by name and dosage.
Similarly, it provides that functionality for clinicians on all the patients that are attached to them. In doing this, patients can manage their medications in a central place. Clinicians can search for a patient's medication
for reasons such as resolving medication concerns, checking for interactions, and others. 

# Design

The user (a person), which is a patient or clinician belonging to a partner organization of HealthSmart, interacts with a front-end client appropriately named HealthSmart Medication Management UI.  Before a request from a user is sent to the API of the Medication Service, it is first routed to the system's Gateway Service, which acts as a proxy for services and handles cross-cutting concerns.
Then, it is routed to the appropriate API endpoint of the Medication Service. Figure 1-1 shows a context diagram of the Medication Service.


As mentioned previously, the user will interact with a front-end client called HealthSmart Medication Mangement UI. It is an Angular Single Page Application 
responsible for providing all of the medication management functionality to users via their web browser. It makes API calls using HTTPS to the system's
Gateway Service. The Gateway Service is a Spring Cloud Gateway container. It is responsible for routing API calls to the appropriate service. In this case, after determining the route
was for the /medications endpoint, it routes using HTTPS to the system's Medication Service. The Medication Service is a Spring Boot container. Since it's functionality has been discussed, it's important
to now explore a container diagram of the Medication Service, as shown in Figure 1-2.





The HealthSmart Medication Service was designed using a service-oriented architecture approach. Like other systems in the Medication Management System, it has a single responsibility, adhering to the Single Responsiblity Principle
and it allows clients to interact with it through its RESTful API.
RESTful API interface. 
