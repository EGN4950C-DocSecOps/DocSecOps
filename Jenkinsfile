def textFiles = " "
def pdfFiles = " "
def pptxFiles = " "
def docxFiles = " "
def jsonFiles = " "
def uploadSpecJSON = " "
def uploadSpecTXT = " "
def uploadSpecPDF = " "
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
        stage('Checking if there are differences in GitHub'){
               steps{
                   sh '''
                   ls -ltr ./documents/
                   git diff ./documents/
                   '''
               }
        }
         stage('Compile Java program') {
            steps {
                echo "working on Java program.."
                script {
                    echo "Compiling File Detection program..."
                    //sh " javac ./fileProcessing/src/FileTypeDetection.java"
                 }
            }
        }
        stage('Run Java program') {
            steps {
                echo "Running File Detection program..."
                script {
                    echo "Checking Files Uploaded..."
                    sh " java -jar DocumentTester.jar ./documents/"
                    echo "Checking if json files were created successfully"
                    sh "pwd"
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
                    textFiles= sh(returnStdout: true, script: 'find ./documents -iname *.txt')
                    pdfFiles= sh(returnStdout: true, script: 'find ./documents -iname *.pdf')
                    pptxFiles = sh(returnStdout: true, script: 'find ./documents -iname *.pptx')
                    docxFiles = sh(returnStdout: true, script: 'find ./documents -iname *.docx')
                    jsonFiles= sh(returnStdout: true, script: 'find ./src/FileOutput/ -iname *.json')
                    sh "ls -l ./documents"
                    echo "$textFiles"
                 }
            }
        }
         stage('Prepare-JSON-files-to-upload') {
            steps {
                echo "Uploading successfully checked files to JFrog.."
                echo "Test Step - Value of jsonFiles = $jsonFiles"
               
                script {
                    
                    
                def uploadSpecSTART = '{"files": ['
                def uploadSpecPatStart = '{"pattern": "'   
                def uploadSpecPatEnd = '",'                          
                def uploadSpecTarget = '"target": "DocSecOps/"}'
                def uploadSpecEND = ']}'
                    
                uploadSpecJSON = uploadSpecSTART
                 sh "echo ${uploadSpecJSON}"
                     def texts = jsonFiles.split('\n')
                     for (txt in texts) {
                         sh "echo ${txt}"
                         //sh "cat ${txt}"
                         uploadSpecJSON = uploadSpecJSON + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecJSON = uploadSpecJSON[0..-2]
                    uploadSpecJSON = uploadSpecJSON + uploadSpecEND
                    echo "${uploadSpecJSON}"
                }
            }
        }
        stage('Prepare-txt-files-to-upload') {
            steps {
                echo "Uploading successfully checked files to JFrog.."
                echo "Test Step - Value of textFiles = $textFiles"
               
                script {
                    
                    
                def uploadSpecSTART = '{"files": ['
                def uploadSpecPatStart = '{"pattern": "'   
                def uploadSpecPatEnd = '",'                          
                def uploadSpecTarget = '"target": "DocSecOps-txt/"}'
                def uploadSpecEND = ']}'
                    
                uploadSpecTXT = uploadSpecSTART
                 sh "echo ${uploadSpecTXT}"
                     def texts = textFiles.split('\n')
                     for (txt in texts) {
                         sh "echo ${txt}"
                         //sh "cat ${txt}"
                         uploadSpecTXT = uploadSpecTXT + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecTXT = uploadSpecTXT[0..-2]
                    uploadSpecTXT = uploadSpecTXT + uploadSpecEND
                    echo "${uploadSpecTXT}"
                }
            }
        }
        stage('Prepare-pdf-files-to-upload') {
            steps {
                echo "Uploading successfully checked files to JFrog.."
                echo "Test Step - Value of pdfFiles = $pdfFiles"
               
                script {
                    
                    
                def uploadSpecSTART = '{"files": ['
                def uploadSpecPatStart = '{"pattern": "'   
                def uploadSpecPatEnd = '",'                          
                def uploadSpecTarget = '"target": "DocSecOps-pdf/"}'
                def uploadSpecEND = ']}'
                    
                uploadSpecPDF = uploadSpecSTART
                 sh "echo ${uploadSpecPDF}"
                     def texts = pdfFiles.split('\n')
                     for (txt in texts) {
                         sh "echo ${txt}"
                         //sh "cat ${txt}"
                         uploadSpecPDF = uploadSpecPDF + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecPDF = uploadSpecPDF[0..-2]
                    uploadSpecPDF = uploadSpecPDF + uploadSpecEND
                    echo "${uploadSpecPDF}"
                }
            }
        }
        stage('Prepare-pptx-files-to-upload') {
            steps {
                echo "Uploading successfully checked files to JFrog.."
                echo "Test Step - Value of pptxFiles = $pptxFiles"
               
                script {
                    
                    
                def uploadSpecSTART = '{"files": ['
                def uploadSpecPatStart = '{"pattern": "'   
                def uploadSpecPatEnd = '",'                          
                def uploadSpecTarget = '"target": "DocSecOps-pptx/"}'
                def uploadSpecEND = ']}'
                    
                uploadSpecPPTX = uploadSpecSTART
                 sh "echo ${uploadSpecPPTX}"
                     def texts = pptxFiles.split('\n')
                     for (txt in texts) {
                         sh "echo ${txt}"
                         //sh "cat ${txt}"
                         uploadSpecPPTX = uploadSpecPPTX + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecPPTX = uploadSpecPPTX[0..-2]
                    uploadSpecPPTX = uploadSpecPPTX + uploadSpecEND
                    echo "${uploadSpecPPTX}"
                }
            }
        }
        stage('Prepare-docx-files-to-upload') {
            steps {
                echo "Uploading successfully checked files to JFrog.."
                echo "Test Step - Value of docxFiles = $docxFiles"
               
                script {
                    
                    
                def uploadSpecSTART = '{"files": ['
                def uploadSpecPatStart = '{"pattern": "'   
                def uploadSpecPatEnd = '",'                          
                def uploadSpecTarget = '"target": "DocSecOps-docx/"}'
                def uploadSpecEND = ']}'
                    
                uploadSpecDOCX = uploadSpecSTART
                 sh "echo ${uploadSpecDOCX}"
                     def texts = docxFiles.split('\n')
                     for (txt in texts) {
                         sh "echo ${txt}"
                         //sh "cat ${txt}"
                         uploadSpecDOCX = uploadSpecDOCX + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpecDOCX = uploadSpecDOCX[0..-2]
                    uploadSpecDOCX = uploadSpecDOCX + uploadSpecEND
                    echo "${uploadSpecDOCX}"
                }
            }
        }
        stage('Deploy txt to Artifactory') {
            steps {
                echo 'Uploading....'
                        rtUpload(
                            serverId: 'artifactory',
                            spec:"""${uploadSpecTXT}"""
                        )
            }
        }    
        stage('Deploy pdf to Artifactory') {
            steps {
                script{
                      if(uploadSpecPDF == " ")
                     {
                         echo 'There are no PDF files to deploy'
                     }
                      else
                     {
                         echo 'Uploading....'
                             rtUpload(
                                serverId: 'artifactory',
                                spec:"""${uploadSpecPDF}"""
                            )
                     } 
                }
            }
        }  
         stage('Deploy pptx to Artifactory') {
            steps {
                echo 'Uploading....'
                        rtUpload(
                            serverId: 'artifactory',
                            spec:"""${uploadSpecPPTX}"""
                        )
            }
        }
        stage('Deploy docx to Artifactory') {
            steps {
                echo 'Uploading....'
                        rtUpload(
                            serverId: 'artifactory',
                            spec:"""${uploadSpecDOCX}"""
                        )
            }
        }
         stage('Deploy JSON to Artifactory') {
            steps {
                echo 'Uploading....'
                        rtUpload(
                            serverId: 'artifactory',
                            spec:"""${uploadSpecJSON}"""
                        )
            }
        }     
    }
    post {  
         always {  
             echo 'Post Build Functions'  
         }  
         success {  
             echo 'The build is successful, document uploaded!'
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
