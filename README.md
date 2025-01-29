# Medication Service

The HealthSmart Medication Service provides functionality for patients to view medications, search medication by name or NDC, retrieve complete medication history, or find a label of a medication by name and dosage.
Similarly, it provides that functionality for clinicians on all the patients that are attached to them. In doing this, patients can manage their medications in a central place. Clinicians can search for a patient's medication
for reasons such as resolving medication concerns, checking for interactions, and others. 

# Design

The user (a person), which is a patient or clinician belonging to a partner organization of HealthSmart, interacts with a front-end client appropriately named HealthSmart Medication Management UI.  Before a request from a user is sent to the API of the Medication Service, it is first routed to the system's Gateway Service, which acts as a proxy for services and handles cross-cutting concerns.
Then, it is routed to the appropriate API endpoint of the Medication Service. Figure 1-1 shows a context diagram of the Medication Service.

![image](https://github.com/user-attachments/assets/f06d59f0-b35a-406e-9cba-0c3b9a6a2db8)


As mentioned previously, the user will interact with a front-end client called HealthSmart Medication Mangement UI. It is an Angular Single Page Application 
responsible for providing all of the medication management functionality to users via their web browser. It makes API calls using HTTPS to the system's
Gateway Service. The Gateway Service is a Spring Cloud Gateway container. It is responsible for routing API calls to the appropriate service. In this case, after determining the route
was for the /medications endpoint, it routes using HTTPS to the system's Medication Service. The Medication Service is a Spring Boot container. Since it's functionality has been discussed, focus will be shifted to the Medication Service Database, which is a PostgreSQL container. It stores medication data that is used by the operations of the Medication Service. 

![image](https://github.com/user-attachments/assets/e0fb9966-f705-41d7-9a03-8ed4a21f5454)

