# Taskboard

## Project Management Made Simple
Taskboard is perfect for large or small scale teams alike. 
- Administartors will find that it provides an easy way to create and manage projects as well as the users assigned to a projects.
- Developers can easily see and update issues that have been created for projects that they have been assigned to.
- Testers can raise issues and ensure that issues have been fixed on assigned projects. 

All users will have access to comments on issues that allows for convienent communication between team members.

## Running the Application
Taskboard is divided into two pieces: a Spring Boot backend to manage data and an Angular frontend for user interaction.

### Launching the Spring Backend
Spring requires NodeJS and the npm installer to run. 

After you have installed Node from the official website, we recommend using a IDE of choice that is compatable with Gradle (We highly recommend using IntelliJ). The IDE will handle the installation of dependencies for you.

After dependencies have been installed, you will need to create an `.env` file in the root of the Spring-Taskboard directory. The `.env.example` file shows the formatting needed and commands required to set up this file.

Finally, you can run the Spring application by compiling and running the TaskboardBoardApplication class in your IDE of choice.

### Launching the Angular Frontend
Since Node has already been installed, you can install the necessary dependencies by entering the Angular-Taskboard directory and running:

```
npm install
```

Afterwards, the Angular application can be run by running:
```
npm run start
```

You can then use the application by going to [http://localhost:4200](http://localhost:4200) in your browser of choice