# DevSecOps Pipeline

Our group is configuring a DevSecOps pipeline to handle distinct types of documentation. 
The pipeline will support plain text files, Word documents, Excel, PDFs, and PowerPoints. 

* FileProcessing Folder contains the source code 
  * it can be found in main as well as the "DevSecOps-Java-Source-Code" branch
* docDataValidator Folder contains the source code for the plugin
  * it can be found in main as well as the "Jenkins-Plugin" branch

 
# Configure Jenkins to use Pipeline Script from SCM

In our case, we are leveraging a GitHub repository as our workspace for the pipeline. To complete this, in the current pipline you need to click on the configuration in the sidebar.

<img width="1406" alt="Screen Shot 2023-04-20 at 8 41 22 PM" src="https://user-images.githubusercontent.com/89712188/233516166-6e181b6d-d99f-4ec7-b2aa-ea270447912f.png">

In the definition, select the "Pipeline script from SCM" and for the SCM pick "Git." This will open more specific question to ensure that the pipeline has an accurate path. In the Repository URL, go to the desire repo location and <>code and copy the repo URL and insert it here.

<img width="1405" alt="Screen Shot 2023-04-20 at 8 41 47 PM" src="https://user-images.githubusercontent.com/89712188/233516519-7afe8e01-70aa-4996-8ed3-54dd60fb3099.png">

Then you want to specify the desired branch and the name of the file. In standard practice for Jenkins, it is preferred to use the name "Jenkinsfile" as the name of the document where the pipeline script will resided. To ensure it is working, you can build the pipeline with a simple pipeline script to ensure that it is connecting appropriately.

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

# Jenkins-Document-Data-Validator

Original Repository: https://github.com/lcasals/Jenkins-File-Processor-Plugin

Document Data Validator is a Jenkins plugin that extracts metadata from PDF, Word, and PPTX files, validates the content, and saves metadata into JSON files. It detects and lists files in a directory, prints out file information, and report errors such as broken links to the console. 
## Features:  
- Detects and lists files in a directory (optional parameter)
- Extracts metadata from PDF, Word, and PPTX files
- Validates content for broken links (optional parameter)
- Saves metadata and errors into json files in an output directory (optional parameter)


## Usage: 
### Importing the plugin into Jenkins: 
Use jenkins dashboard web UI: 
    Go to Jenkins dashboard
    After logging in, click on Manage Jenkins, then Manage Plugins
    Click on Advanced Settings
    In the Deploy Plugin section, click browse to choose a file and select the .hpi file
    Click Upload
May need to restart the Jenkins instance to complete installation

### To use the Document Data Validator plugin in a declarative Jenkins pipeline, add the following step to your `Jenkinsfile`:
### Windows Systems
```
pipeline {
    agent any

    stages {
        stage('Validation') {
            steps {
                script {
                    validateDocuments(directory: 'C:\\path\\to\\inputFolder\\', enableUrlCheck: true)
                }
            }
        }
    }
}

```
Replace "C:\\path\\to\\inputFolder\\" with the path to the directory containing the documents you want to validate.

### Unix Systems
```
pipeline {
    agent any

    stages {
        stage('Validation') {
            steps {
                script {
                    validateDocuments(directory: '/path/to/inputFolder/', enableUrlCheck: true)
                }
            }
        }
    }
}

```
Replace "/path/to/inputFolder/" with the path to the directory containing the documents you want to validate. 
Make sure to include the forward slash at the end.

## Default Directories: 
### Default Input Directory: 
The plugin scans for documents in the root of the Jenkins workspace. If you want to specify a different directory, you can provide the `directory` parameter in the `validateDocuments` step as shown above under "Usage"
- Note: when you use a pipeline script and check out files from a GitHub repository, Jenkins creates a workspace folder for the job run. The workspace folder will contain the files and directories from the checked-out repository. The files should be available in the workspace folder when the plugin is called.

### Default JSON Output Directory: 
The plugin saves metadata and error information in JSON files, which are stored in a directory named jsonOutput inside the Jenkins workspace. If you want to specify a different output directory, you can provide the outputDirectory parameter in the validateDocuments step as shown above under "Usage"
## Using the Default Input and Output Directories: 
By not providing the directory and outputDirectory parameters, the plugin will use the default input directory (the root of the Jenkins workspace) to scan for documents and the default output directory (jsonOutput inside the Jenkins workspace) to save the JSON files with metadata.

### Using Default Directories in a Jenkins Pipeline
To use the default input and output directories in your Jenkins pipeline, you don't need to provide the `directory` and `outputDirectory` parameters in the `validateDocuments` step. Here's an example of how to use the default directories in a Jenkinsfile. note that the enableUrlCheck parameter is also optional:

```groovy
pipeline {
    agent any

    stages {
        stage('Validation') {
            steps {
                script {
                    validateDocuments()
                }
            }
        }
    }
}
```
## how to test Locally:
1. install maven
2. Open project folder in command line
3. run the following command:   mvn hpi:run
4. jenkins dashboard should be available at http://localhost:8080/jenkins/ , 
5. if port is being used, you can specify the port like this:    mvn hpi:run -Dport=8081  

# DevSecOps Data Processing Source Code

Original Repository: https://github.com/jaroldsabillon/FIleProcessing

A file processing project to be converted to a jenkins plug. Metadata extraction will be done using apache libraries, and will start will docx, pdf, and pptx files. Unrecognized files will undergo generic data extraction, if unsuccesfull it will set it aside and add it to a list of unprocessed files.

Information extracted is written to JSON files

Implemented URLChecker that requires "-validateURL" arugument parameter
