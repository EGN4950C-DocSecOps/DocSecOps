# DevSecOps Pipeline

Our group is configuring a DevSecOps pipeline to handle distinct types of documentation. 
The pipeline will support plain text files, Word documents, Excel, PDFs, and PowerPoints. 

 
# Configure Jenkins to use Pipeline Script from SCM

In our case, we are leveraging a GitHub repository as our workspace for the pipeline. To complete this, in the current pipline you need to click on the configuration in the sidebar.

<img width="1406" alt="Screen Shot 2023-04-20 at 8 41 22 PM" src="https://user-images.githubusercontent.com/89712188/233516166-6e181b6d-d99f-4ec7-b2aa-ea270447912f.png">

In the definition, select the "Pipeline script from SCM" and for the SCM pick "Git." This will open more specific question to ensure that the pipeline has an accurate path. In the Repository URL, go to the desire repo location and <>code and copy the repo URL and insert it here.

<img width="1405" alt="Screen Shot 2023-04-20 at 8 41 47 PM" src="https://user-images.githubusercontent.com/89712188/233516519-7afe8e01-70aa-4996-8ed3-54dd60fb3099.png">

Then you want to specify the desired branch and the name of the file. In standard practice for Jenkins, it is preferred to use the name "Jenkinsfile" as the name of the document where the pipeline script will resided. To ensure it is working, you can build the pipeline with a simple pipeline script to ensure that it is connecting appropriately.

Example of pipeline script:

<img width="820" alt="Screen Shot 2023-04-20 at 9 06 31 PM" src="https://user-images.githubusercontent.com/89712188/233517142-40e4c35a-ee30-44ff-b198-e1dc2e44169e.png">

Quick Tip: If you need help writing pipeline script, you can click on "pipeline syntax" and it will show you various types on common questions and how to write them in the script.

# Configuring JFrog Artifactory

To add plugins in Jenkins, you need to "Manage Jenkins" then to "Manage Plugins," here you can download a wide range of plugins to fit your needs. Here, we are going to search for the "JFrog" plugin and download it. The back to "Manage Jenkins" you need to go to "Configure System" then scrool untill you reach the JFrog section.

<img width="1356" alt="Screen Shot 2023-04-20 at 8 43 01 PM" src="https://user-images.githubusercontent.com/89712188/233517839-04fb3326-41d7-4a61-b766-bcaaed04e238.png">

Here you will need to provide the URL of the Artifactory instance as well as a username and passsword so it gains access. You can also create an access token and define the environment directly in the Jenkins file.

<img width="555" alt="Screen Shot 2023-04-20 at 9 17 52 PM" src="https://user-images.githubusercontent.com/89712188/233518272-aa96df61-3ce9-4d1a-8be2-612815724e14.png">

# How to use URL checker

In the "Run Java program" stage, if you want to run the URL validator you have to add the argumant parameter "-validateURL"
If the parameter is not included it will not check for URls.

<img width="575" alt="Screen Shot 2023-04-27 at 9 37 04 PM" src="https://user-images.githubusercontent.com/89712188/235034427-0a8aa768-f09a-4b27-951c-cceb1792ebb2.png">

Lastly, for the Jar file to work appropriately there needs to exist a src/FileInput and src/FileOutput directory in the GitHub Repository. Due to the nature of GitHub this requires to upload a "dummy" file to maintain the,. If a file doesnt exists here, the folder will be deleted and the Jar file will fail.

# How to Build the Pipeline

Once you connected to Github and Artifactory, in Jenkins you can trigger the Build manual by clicking "Build" or you can configure a GitHub webhook everytime a push occurs in the repository. 


When the pipeline begeins the first thing it will do is build a Kubernetes agent that will work on all the task in pipeline.

<img width="244" alt="Screen Shot 2023-04-20 at 9 24 32 PM" src="https://user-images.githubusercontent.com/89712188/233518960-190fae20-5575-4bf1-8951-8ad4650fffde.png">

Everything a build is started this agent will be gernated and is completely customizable and a preferred method since it is destoryed when the build is finished and will only take up memeory while it is executing.

<img width="903" alt="Screen Shot 2023-04-20 at 9 27 16 PM" src="https://user-images.githubusercontent.com/89712188/233519255-e3056589-2db5-4506-aa40-34887ec7ea1b.png">

In the pipeline script, you have stages. Here you can define certain task that need to be accomplish and label them accordingly. The first stage that occurs is to run the JAR file that contains all of the code to process the different file types entering the pipeline. Once this is completely, the following stage located what kind of files its locating. This will begin the set-up to deploy the files the artifact repository. 

<img width="942" alt="Screen Shot 2023-04-20 at 9 30 53 PM" src="https://user-images.githubusercontent.com/89712188/233519612-180041e9-81a6-4ab5-9fb6-fe517b85514d.png">

In this stage, it will be building the upload specifications required by artifory inorder to deploy successfuly, which looks like {"files": [{"pattern": "<file directory + file name","target": "Artifact repo name/"}

Once the upload specs for each file is complete the next stage will begin.

<img width="458" alt="Screen Shot 2023-04-20 at 9 36 38 PM" src="https://user-images.githubusercontent.com/89712188/233520176-a6ad4919-fd7b-4637-a21e-fbf97a64a906.png">

To ensure the pipeline does not fail, there are IF conditions in place to verifiy that there are files to deploy, otherwise to do nothing. We can see that earlier when we configure JFrog, we called it "artifactory" you put this as the serverID then pass all the files. 

Lastly, you can add post builds. We are doing this, so that for every build an email gets sent out that provides the build name, build number, the status of the build (Success or Failure), then provides a copy of the console log.

<img width="695" alt="Screen Shot 2023-04-20 at 9 42 02 PM" src="https://user-images.githubusercontent.com/89712188/233520828-ae651b08-2e24-42e4-b081-6c313a0ffe3b.png">

An example of what the email looks like: 

<img width="496" alt="Screen Shot 2023-04-20 at 9 42 41 PM" src="https://user-images.githubusercontent.com/89712188/233520944-b96dce5e-fd54-4db3-9f1f-2ddc1bd675ad.png">
