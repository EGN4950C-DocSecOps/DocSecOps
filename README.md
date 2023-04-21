# DevSecOps Pipeline

Our group is configuring a DevSecOps pipeline to handle distinct types of documentation. 
The pipeline will support plain text files, Word documents, PDFs, and PowerPoints. 
 
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


