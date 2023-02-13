def textFiles = " "
def pdfFiles = " "
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
         stage('Setting up Java script') {
            steps {
                echo "working on Java script.."
                script {
                    echo "Compiling File Detection Script..."
                    sh " javac ./fileProcessing/src/FileTypeDetection.java"
                 }
            }
        }
        stage('Run Java script') {
            steps {
                echo "Running File Detection Script..."
                script {
                    echo "Checking Files Uploaded..."
                    sh " java ./fileProcessing/src/FileTypeDetection.java"
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
                    jsonFiles= sh(returnStdout: true, script: 'find ./documents -iname *.json')
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
                echo 'Uploading....'
                        rtUpload(
                            serverId: 'artifactory',
                            spec:"""${uploadSpecPDF}"""
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
                body: "Project: ${env.JOB_NAME}\r\nBuild Number: ${env.BUILD_NUMBER} \r\nBuild URL: ${env.BUILD_URL}",
                to: 'faugroup22@gmail.com'
         }  
         failure {  
             echo 'The build failed'
             emailext attachLog: true,
                subject: "Jenkins Build: ${env.BUILD_NUMBER} Status: FAILED!",
                body: "Project: ${env.JOB_NAME}\r\nBuild Number: ${env.BUILD_NUMBER} \r\nBuild URL: ${env.BUILD_URL}",
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
