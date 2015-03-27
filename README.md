Postgresql client that has some improvements (or not) over the pgAdmin version.


Installation and Getting Started
================================


./gradlew bootRun

If you want use a postgres database, you should override the default H2 profile with the argument " -Dspring.profiles.active=postgres", futhermore you need provide your credentials at src/main/resources/application-postgres.properties

./gradlew bootRun -Dspring.profiles.active=postgres
 