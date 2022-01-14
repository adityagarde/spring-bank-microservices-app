### Principles of Microservices -

- Microservices should not share code or data.
- Avoid unnecessary coupling between services and software components.
- Independence and Autonomy are more important than code re-usability.
- Each microservice should be responsible for single system or process.
    - Because if it fails - we will more all those functions.
- Microservices should not communicate directly with each other, they should make use of an event / message bus to communicate with one another.

Summary - "Each microservice should believe that is the only service in the world."

### CQRS and Event Sourcing -

- CQRS - **Command Query Responsibility Segregation.**
- It is a design pattern which suggests that applications should be divided in command and query part.
- Commands alter the state of an object & queries return the state of an object / entity.
    - Read and Write operations are separate concerns.
- Beneficial when the reads out number the writes or vice-versa.

- Data is often more frequently queried than altered, or vice-versa.
- Segregating commands and queries enables us to optimise each other for high performance.
- Read and write representations of data often differ substantially.
- Executing command and query operations on the same model can cause data contention.
- Segregating read and write concerns enables you to manage read and write security separately.

### Event Sourcing -

- Event Sourcing defines an approach where all the changes made to an object or entity are stored as a sequence of immutable events to an event source as opposed to just saving the current state of the object or entity.

### Benefits of Event Sourcing -

- The event store provides a complete log of every state change.
- The state of an object / aggregate can be recreated by replaying the event store.
- Improves write performance - All event types are simply appended to the event store. There are no update or delete operations.
- In case of failure, the event store can be used to restore read database.

### Apache Kafka

- Apache Kafka is an open-source distributed event streaming platform that enables the development of real-time, event-driven applications.

#### CQRS Topics -

- **Command** - Command is a combination of expressed intent. It describes something that you want to be done. It also contains the information required to undertake action based on that intent. Commands are named with a verb in the imperative mood.

- **Events** - Events are objects that describe something that has occurred in the application. A typical source of events is the aggregate. When something important has occurred within the aggregate, it will raise an event. Events are typically named in past participal verb.

