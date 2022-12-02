# ExploreWithMe

[PR Link for review](https://github.com/anatolykalugin/java-explore-with-me/pull/2)

### Technologies used:
* Spring Boot
* REST API
* WebClient
* PostgreSql
* Hibernate
* Docker
* Maven
* Lombok
* Jackson

### Services description:
* User service: creating, updating and getting users;
* Event service: basic CRUD-operations + advanced
DB-connected functionality, including getting custom
filtered lists, moderating events, etc.;
* Request service: applying for participation in events,
cancelling requests and getting author's requests;
* Category service: operations with event's categories
for a more friendly UX;
* Compilation service: possibility to compile several 
events into one compilation with the ability to add or 
remove events at any time and pin (or unpin) 
different compilations;
* Stats service: distinct service with its own DB for
saving and getting view statistics for events.
### Docker launch command:
Using a terminal, go into the project's folder 
"java-explore-with-me", enter `docker-compose up`.
The project should be launched.