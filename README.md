Postgresql client that has some improvements (or not) over the pgAdmin version.

[![Build Status](https://travis-ci.org/luksrn/pgconsole.svg?branch=master)](https://travis-ci.org/luksrn/pgconsole)


Installation and Getting Started
================================

```
./gradlew :pgconsole:bootRun
```
If you want use a postgres database, you should override the default H2 profile with the argument " -Dspring.profiles.active=postgres", futhermore you need provide your credentials at src/main/resources/application-postgres.properties
```
./gradlew :pgconsole:bootRun -Dspring.profiles.active=postgres
 ```
