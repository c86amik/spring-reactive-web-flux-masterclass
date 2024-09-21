# spring-reactive-web-flux-masterclass

## A good understanding of the usage of Reactive Programming using Spring Web FLux and MySQL as the underlying database

### Software Required
* [Java 17](https://www.openlogic.com/openjdk-downloads?page=0)
* [IntelliJ](https://www.jetbrains.com/idea/download/?section=windows) or [Spring tool Suite](https://spring.io/tools) or [Eclipse](https://www.eclipse.org/downloads/packages/)
* [Apache Maven](https://maven.apache.org/download.cgi)
* [Git Bash](https://git-scm.com/downloads)
* [MySQL](https://dev.mysql.com/downloads/mysql/) - Download the Community Edition. If it is a problem then you can download another one as provided below
* [DBeaver](https://dbeaver.io/download/) - DBeaver. A MySQL Client
* [Postman](https://www.postman.com/downloads/)

### Steps to execute the DB Scripts
Run the below scripts in any of the software either in MySQL Workbench or in DBeaver

* create_database_flux.sql - It will create the database named <strong>flux</strong>
* drop_database_flux.sql - It will delete the database <strong>flux</strong>

But don't run it before the create_database_flux.sql, otherwise it will throw the error stating `No database is present with this name`

* create-report-process-tracker-table-ddl.sql - It will create the table named as <strong>report_process_tracker</strong>
* create-report-file-tracker-table-ddl.sql - It will create the table named as <strong>report_file_tracker</strong>

But though it is not required to execute, because when you run the application as Spring Boot and try to call the <strong>POST</strong> method it will automatically create the table <strong>report_process_tracker</strong>, <strong>report_file_tracker</strong> under <strong>flux</strong> database

* drop-report-file-tracker-table-ddl.sql - It will delete the table <strong>report_file_tracker</strong>
* drop-report-process-tracker-table-ddl.sql - It will delete the table <strong>report_process_tracker</strong>

### Steps to clone and run the application
* Install Java 17.
* Install MySQL. Complete installation steps of [MySQL Workbench](https://www.sqlshack.com/how-to-install-mysql-database-server-8-0-19-on-windows-10/) are provided
* If you face any problem while installing MySQL Workbench, then you can install SQLYog as mentioned in the Software Required steps
* Open Git Bash or even you can open Command Prompt (if you are using Windows) or Terminal (if you are using MAC) in your machine
* Clone the application from github.com as   
<code>git clone https://github.com/c86amik/spring-reactive-web-flux-masterclass.git</code>
* Open either <strong>STS</strong> or <strong>Eclipse</strong> or <strong>IntelliJ</strong> and import the application as <strong>Maven</strong> project
* After the application got successfully imported in either <strong>STS</strong> or <strong>Eclipse</strong>
* Right Click on the application, select the <strong>Run As</strong> option, and then select <strong>Spring Boot App</strong>
* The application will start in the port <strong>7124</strong>
* Open the Postman and test the REST Endpoints
* But in case of <strong>IntelliJ</strong> you click on File -> Open option and import the project.
* Once imported click the Maven Perspective available at the top right corner of IntelliJ.
* Run the <code>mvn clean install</code> command by setting the JDK to Java 17 and Maven as installed locally in your machine.
* After that click on the New/Edit Configurations at the top right corner of IntelliJ.
* It will open a pop-up box select the main class of your application, provide the proper JDK as Java 17 and click on Apply button and then run it. It will run as the Spring Boot Application. 

### Testing using Postman
<ol>
<li><strong>Create Report Process Trackers</strong> - localhost:7124/flux/processTrackers</li>
<li><strong>Get all Report Process Trackers</strong> - localhost:7124/flux/processTrackers</li>
<li><strong>Create Report File Tracker</strong> - localhost:7124/flux/fileTrackers</li>
<li><strong>Get All Report File Trackers</strong> - localhost:7124/flux/fileTrackers</li>
<li><strong>Get Report Process Tracker by Trace Id</strong> - localhost:7124/flux/processTracker/{traceId}</li>
<li><strong>Get Report File Tracker by Trace Id</strong> - localhost:7124/flux/fileTracker/{traceId}</li>
<li><strong>Get Report File Tracker by Report Process Id</strong> - localhost:7124/flux/fileTrackers/{reportProcessId}</li>
<li><strong>Get Report File Tracker associated with Report Process Tracker by Trace Id</strong> - localhost:7124/flux/processFileTrackers/{traceId}</li>
<li><strong>Get Report Process Trackers with associated Report File Tracker History</strong> - localhost:7124/flux/history</li>
<li><strong>Get PDF Report for Report Process Trackers with associated Report File Trackers History</strong> - localhost:7124/flux/generatePDF</li>
<li><strong>Update Report Process Tracker by TraceId</strong> - localhost:7124/flux/updateProcessTracker/{traceId}</li>
<li><strong>Update Report File Tracker by TraceId</strong> - localhost:7124/flux/updateFileTracker/{traceId}</li>
<li><strong>Delete Report File Tracker by Id</strong> - localhost:7124/flux/deleteFileTracker/{id}</li>
<li><strong>Delete Report Process Tracker by Id</strong> - localhost:7124/flux/deleteProcessTracker/{id}</li>
</ol>

#### Dummy JSON object
* Body for the <strong>POST</strong> method for creating Report Process Tracker  
<code>{
    "serviceName": "spring-reactive-web-flux-masterclass",
    "processName": "Reactive Programming with Web Flux",
    "path": "DEMO_PROJECTS",
    "createdBy": "amik.blogger"
}</code>
* Body for the <strong>PUT</strong> method to update Report Process Tracker  
<code>{
    "id": 5,
    "traceId": "ba0b7ae1-4e82-4aeb-bee7-608d952675bc",
    "serviceName": "spring-reactive-web-flux-masterclass",
    "processName": "Web Flux using Reactive Programming",
    "path": "DEMO_PROJECTS12",
    "createdBy": "amik.blogger",
    "createdTime": "2024-09-10T20:59:21"
}</code>
* Body for the <strong>POST</strong> method to create Report File Tracker  
<code>{
    "reportProcessId": "bc750fcf-4ca3-4b96-8a56-03b4864372a8",
    "fileName": "Spring.docx",
    "fileType": "docx",
    "createdBy": "amik.blogger"
}</code>
* Body for the <strong>PUT</strong> method to update Report File Tracker  
<code>{
    "id": 9,
    "reportProcessId": "bc750fcf-4ca3-4b96-8a56-03b4864372a8",
    "traceId": "2ff5ece4-8404-4e6b-ac3a-a0141be307b7",
    "fileName": "Reactive Programming using Spring-WebFlux.pdf",
    "fileType": "pdf",
    "createdBy": "amik.blogger"
}</code>

	