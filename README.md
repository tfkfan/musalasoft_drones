## Drones

[[_TOC_]]

---

:scroll: **START**

### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

---

### Task description

We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:

- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has:

- name (allowed only letters, numbers, ‘-‘, ‘\_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task.

The service should allow:

- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone;
- checking available drones for loading;
- check drone battery level for a given drone;

> Feel free to make assumptions for the design approach.

---

### Requirements

While implementing your solution **please take care of the following requirements**:

#### Functional requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.

---

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Any data required by the application to run (e.g. reference tables, dummy data) must be preloaded in the database;
- Unit tests;
- Use a framework of your choice, but popular, up-to-date, and long-term support versions are recommended.

---

:scroll: **END**

# DroneFactory

This application was generated using JHipster 8.0.0-beta.2, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v8.0.0-beta.2](https://www.jhipster.tech/documentation-archive/v8.0.0-beta.2).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.
This application is configured for Service Discovery and Configuration with Consul. On launch, it will refuse to start if it is not able to connect to Consul at [http://localhost:8500](http://localhost:8500). For more information, read our documentation on [Service Discovery and Configuration with Consul][].

## Requirements

Project uses java v 18 and maven >= 3.2.5 (see in enforcer logs during build)
Make sure docker is installed (required for testcontainers)

## Project Structure

Node is required for generation and recommended for development. `package.json` is always generated for a better development experience with prettier, commit hooks, scripts and so on.

In the project root, JHipster generates configuration files for tools like git, prettier, eslint, husky, and others that are well known and you can find references in the web.

`/src/*` structure follows default Java structure.

- `.yo-rc.json` - Yeoman configuration file
  JHipster configuration is stored in this file at `generator-jhipster` key. You may find `generator-jhipster-*` for specific blueprints configuration.
- `.yo-resolve` (optional) - Yeoman conflict resolver
  Allows to use a specific action when conflicts are found skipping prompts for files that matches a pattern. Each line should match `[pattern] [action]` with pattern been a [Minimatch](https://github.com/isaacs/minimatch#minimatch) pattern and action been one of skip (default if ommited) or force. Lines starting with `#` are considered comments and are ignored.
- `.jhipster/*.json` - JHipster entity configuration files
- `/src/main/docker` - Docker configurations for the application and services that the application depends on

## Quickstart

To start your application in the dev profile, run:

```
docker-compose  -f src/main/docker/postgresql.yml up -d
./mvnw clean spring-boot:run
```

or for windows:

```
docker-compose  -f src/main/docker/postgresql.yml up -d
./mvnw.cmd clean spring-boot:run
```

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

For more information, refer to the [Running tests page][].

[JHipster Homepage and latest documentation]: https://www.jhipster.tech
[JHipster 8.0.0-beta.2 archive]: https://www.jhipster.tech/documentation-archive/v8.0.0-beta.2
[Doing microservices with JHipster]: https://www.jhipster.tech/documentation-archive/v8.0.0-beta.2/microservices-architecture/
[Using JHipster in development]: https://www.jhipster.tech/documentation-archive/v8.0.0-beta.2/development/
[Service Discovery and Configuration with Consul]: https://www.jhipster.tech/documentation-archive/v8.0.0-beta.2/microservices-architecture/#consul
[Using Docker and Docker-Compose]: https://www.jhipster.tech/documentation-archive/v8.0.0-beta.2/docker-compose
[Using JHipster in production]: https://www.jhipster.tech/documentation-archive/v8.0.0-beta.2/production/
[Running tests page]: https://www.jhipster.tech/documentation-archive/v8.0.0-beta.2/running-tests/
[Code quality page]: https://www.jhipster.tech/documentation-archive/v8.0.0-beta.2/code-quality/
[Setting up Continuous Integration]: https://www.jhipster.tech/documentation-archive/v8.0.0-beta.2/setting-up-ci/
[Node.js]: https://nodejs.org/
[NPM]: https://www.npmjs.com/
