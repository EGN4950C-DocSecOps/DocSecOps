def textFiles = " "
def pdfFiles = " "
def pptxFiles = " "
def docxFiles = " "
def xlsxFiles = " "
def jsonFiles = " "
def uploadSpecJSON = " "
def uploadSpecTXT = " "
def uploadSpecPDF = " "
def uploadSpecDOCX = " "
def uploadSpecPPTX = " "
def uploadSpecXLSX = " "
def server = Artifactory.server 'artifactory'
pipeline {
    agent {
        kubernetes {
        yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: maven
            image: maven:alpine
            command:
            - cat
            tty: true
        '''
        }
    }
    options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    environment {
      CI = true
      ARTIFACTORY_ACCESS_TOKEN = credentials('artifactory-access-token')
    }
    stages {
        stage('Run Java program') {
            steps {
                echo "Running File Detection program..."
                script {
                    echo "Checking the uploaded files..."
                    sh " java -jar FileDetectionValidator.jar"
                    //sh " java -jar FileDetectionValidator.jar -validateURL"
                    echo "Checking if json files were created successfully"
                    echo "Listing the json files with extracted metadata"
                    sh "ls ./src/FileOutput/"
                 }
            }
            
        }

        stage('Build') {
            steps {
                echo "Building.."
                script {
                    echo "doing build stuff.."
                    textFiles= sh(returnStdout: true, script: 'find ./src/FileInput -iname *.txt')
                    pdfFiles= sh(returnStdout: true, script: 'find ./src/FileInput -iname *.pdf')
                    pptxFiles = sh(returnStdout: true, script: 'find ./src/FileInput -iname *.pptx')
                    docxFiles = sh(returnStdout: true, script: 'find ./src/FileInput -iname *.docx')
                    xlsxFiles = sh(returnStdout: true, script: 'find ./src/FileInput -iname *.xlsx')
                    jsonFiles= sh(returnStdout: true, script: 'find ./src/FileOutput/ -iname *.json')
                 }
            }
        }
         stage('Preparing-file(s)-to-upload') {
            steps {
                echo "Uploading successfully checked files to JFrog.."
               
                script {
                    
                def uploadSpecSTART = '{"files": ['
                def uploadSpecPatStart = '{"pattern": "'   
                def uploadSpecPatEnd = '",'                          
                def uploadSpecTarget = '"target": "DocSecOps/"}'
                def uploadSpecEND = ']}'
                    
                echo "Test Step - jsonFiles"   
                uploadSpecJSON = uploadSpecSTART
                 //sh "echo ${uploadSpecJSON}"
                     def jsontexts = jsonFiles.split('\n')
                     for (txt in jsontexts) {
                         //sh "echo ${txt}"
                         //sh "cat ${txt}"
                         uploadSpecJSON = uploadSpecJSON + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecJSON = uploadSpecJSON[0..-2]
                    uploadSpecJSON = uploadSpecJSON + uploadSpecEND
                    //echo "${uploadSpecJSON}"
                    
                echo "Test Step - textFiles"    
                uploadSpecTarget = '"target": "DocSecOps-txt/"}'
                uploadSpecTXT = uploadSpecSTART
                 //sh "echo ${uploadSpecTXT}"
                     def txttexts = textFiles.split('\n')
                     for (txt in txttexts) {
                         sh "echo ${txt}"
                         //sh "cat ${txt}"
                         uploadSpecTXT = uploadSpecTXT + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecTXT = uploadSpecTXT[0..-2]
                    uploadSpecTXT = uploadSpecTXT + uploadSpecEND
                    //echo "${uploadSpecTXT}"    
                    
                echo "Test Step - pdfFiles "  
                uploadSpecTarget = '"target": "DocSecOps-pdf/"}'
                uploadSpecPDF = uploadSpecSTART
                     def pdftexts = pdfFiles.split('\n')
                     for (txt in pdftexts) {
                         sh "echo ${txt}"
                         //sh "cat ${txt}"
                         uploadSpecPDF = uploadSpecPDF + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecPDF = uploadSpecPDF[0..-2]
                    uploadSpecPDF = uploadSpecPDF + uploadSpecEND
                    //echo "${uploadSpecPDF}"  
                    
                echo "Test Step - pptxFiles "  
                uploadSpecTarget = '"target": "DocSecOps-pptx/"}'
                uploadSpecPPTX = uploadSpecSTART
                 //sh "echo ${uploadSpecPPTX}"
                     def pptxtexts = pptxFiles.split('\n')
                     for (txt in pptxtexts) {
                         sh "echo ${txt}"
                         //sh "cat ${txt}"
                         uploadSpecPPTX = uploadSpecPPTX + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecPPTX = uploadSpecPPTX[0..-2]
                    uploadSpecPPTX = uploadSpecPPTX + uploadSpecEND
                    //echo "${uploadSpecPPTX}"
                    
                echo "Test Step - docxFiles "  
                uploadSpecTarget = '"target": "DocSecOps-docx/"}'
                uploadSpecDOCX = uploadSpecSTART
                 //sh "echo ${uploadSpecDOCX}"
                     def docxtexts = docxFiles.split('\n')
                     for (txt in docxtexts) {
                         sh "echo ${txt}"
                         //sh "cat ${txt}"
                         uploadSpecDOCX = uploadSpecDOCX + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecDOCX = uploadSpecDOCX[0..-2]
                    uploadSpecDOCX = uploadSpecDOCX + uploadSpecEND
                    //echo "${uploadSpecDOCX}"  
                    
                echo "Test Step - xlsxFiles "  
                uploadSpecTarget = '"target": "DocSecOps-xlsx/"}'
                uploadSpecXLSX = uploadSpecSTART
                     def xlsxtexts = xlsxFiles.split('\n')
                     for (txt in xlsxtexts) {
                         sh "echo ${txt}"
                         //sh "cat ${txt}"
                         uploadSpecXLSX = uploadSpecDOCX + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecXLSX = uploadSpecXLSX[0..-2]
                    uploadSpecXLSX = uploadSpecXLSX + uploadSpecEND
                }
            }
        }
        stage('Deploying Files to Artifactory') {
            steps {
                script {
                    if(textFiles.length() != 0)
                    {
                        echo 'Deploying *.txt files to JFrog....'
                            rtUpload(
                                serverId: 'artifactory',
                                spec:"""${uploadSpecTXT}"""
                            )
                    }
                    else
                    {
                        echo 'There are no text files to Deploy'
                    }
                    
                     if(pdfFiles.length() != 0)
                    {
                        echo 'Deploying *.pdf files to JFrog....'
                            rtUpload(
                                serverId: 'artifactory',
                                spec:"""${uploadSpecPDF}"""
                            )
                    }
                    else
                    {
                        echo 'There are no PDF files to Deploy'
                    }
                    
                     if(docxFiles.length() != 0)
                    {
                        echo 'Deploying *.docx files to JFrog....'
                            rtUpload(
                                serverId: 'artifactory',
                                spec:"""${uploadSpecDOCX}"""
                            )
                    }
                    else
                    {
                        echo 'There are no Word Document files to Deploy'
                    }
                    
                     if(pptxFiles.length() != 0)
                    {
                        echo 'Deploying *.pptx files to JFrog....'
                            rtUpload(
                                serverId: 'artifactory',
                                spec:"""${uploadSpecPPTX}"""
                            )
                    }
                    else
                    {
                        echo 'There are no PowerPoint files to Deploy'
                    }
                     if(xlsxFiles.length() != 0)
                    {
                        echo 'Deploying *.xlsx files to JFrog....'
                            rtUpload(
                                serverId: 'artifactory',
                                spec:"""${uploadSpecXLSX}"""
                            )
                    }
                    else
                    {
                        echo 'There are no Excel files to Deploy'
                    }
                    
                    if(jsonFiles.length() != 0)
                    {
                        echo 'Deploying *.json files to JFrog....'
                            rtUpload(
                                serverId: 'artifactory',
                                spec:"""${uploadSpecJSON}"""
                            )
                    }
                    else
                    {
                        echo 'There are no Json files to Deploy'
                    }
                }
            }
        }         
    }
    post {  
         always {  
             echo 'Post Build Functions'  
         }  
         success {  
             echo 'The build is successful, document(s) uploaded!'
             emailext attachLog: true,
                subject: "Jenkins Build: ${env.BUILD_NUMBER} Status: SUCCESS!", 
                body: "Project: ${env.JOB_NAME}\r\nBuild Number: ${env.BUILD_NUMBER} \r\n",
                to: 'faugroup22@gmail.com'
         }  
         failure {  
             echo 'The build failed'
             emailext attachLog: true,
                subject: "Jenkins Build: ${env.BUILD_NUMBER} Status: FAILED!",
                body: "Project: ${env.JOB_NAME}\r\nBuild Number: ${env.BUILD_NUMBER} \r\n",
                to: 'faugroup22@gmail.com'  
         }  
         unstable {  
             echo 'The run is marked as unstable'  
         }  
         changed {  
             echo 'The state of the Pipeline has changed'  
         }  
     }  
}
