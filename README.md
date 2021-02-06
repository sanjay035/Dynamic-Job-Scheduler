# Dynamic-Job-Scheduler
It is an API based job scheduler(with cron-expressions) where we can create, update, delete, activate and deactivate jobs at anytime with REST calls.

* The jobs are persisted in the database MySQL when create and update request is made.
* The sheduler automatically picks the jobs and resumes them, after restarting when the application goes down.

**References**:

[1] https://www.javatpoint.com/spring-tutorial

[2] http://www.quartz-scheduler.org/

[3] https://www.baeldung.com/quartz

# Installation:
- Clone this repo:
```bash
> git clone https://github.com/sanjay235/Dynamic-Job-Scheduler.git
> cd Dynamic-Job-Scheduler
```
- Install Maven, MySQL(any Database of your choice), IntelliJ(any IDE of your choice).
- Create database tables provide in the `resources/sql` folder.
- Update the dependencies given in the `pom.xml` using Maven.
- Run it as spring boot application in the IDE.

# REST APIs:
* I have created a job called `APITriggerJob` for hitting an external REST API at regular intervals for demo purposes.
* The request body for below APIs is specific to `APITriggerJob` that I have created.
* You can customise and add new jobs as per your need in the `main/src/main/java/com/scheduling/app/job` folder.
```
// Create job end point = localhost:8080/api/scheduler/insert-job
// HTTP Method = POST
// JSON body =
{
    "jobName": "Github profile fetch job",
    "description": "This job fetches the Sanjay's Github profile details.",
    "cronExpression": "0/45 * * * * ?",
    "requestUrl": "https://github.com/sanjay235",
    "requestType": "GET",
    "callbackUrl": "https://www.github.com",
    "retryCount": 23,
    "delayBetweenRetries": 2335,
    "param": {},
    "headers": {
        "Content-Type": "application/json"
    },
    "body": {}
}

// Get job details endpoint = localhost:8080/api/scheduler/get-job/235
// HTTP Method = GET

// Update Job endpoint = localhost:8080/api/scheduler/update-job/
// HTTP method = PUT
// For updating a job get the entire request body using get-job details end point and make necessary changes
// Json body =
{
    "jobID": 235,
    "jobName": "Github profile fetch job",
    "description": "This job fetches the Sanjay's Github profile details.",
    "cronExpression": "0/45 * * * * ?",
    "requestUrl": "https://github.com/sanjay235",
    "requestType": "GET",
    "callbackUrl": "https://www.github.com",
    "retryCount": 23,
    "delayBetweenRetries": 2335,
    "param": {},
    "headers": {
        "Content-Type": "application/json"
    },
    "body": {}
}

// Delete job end point = localhost:8080/api/scheduler/delete-job/235
// HTTP Method = DELETE

// Activate job end point = localhost:8080/api/scheduler/activate-job/235
// HTTP Method = PUT

// De-Activate job end point = localhost:8080/api/scheduler/deactivate-job/235
// HTTP Method = PUT
```

# Contribute:
* Feel free to fork to customise and add the jobs as per your need. Add a star and share if you like it.
* Corrections & Contributions are always welcome! 😃
