def JSONFiles = " "
def uploadSpecJSON = " "
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
        stage('Build') {
            steps {
                echo "Building.."
                script {
                    echo "doing build stuff.."
                    JSONFiles= sh(returnStdout: true, script: 'find ./documents -iname *.json')
                    sh "ls -l ./documents"
                    echo "$JSONFiles"
                 }
            }
        }
         stage('Prepare-JSON-files-to-upload') {
            steps {
                echo "Uploading successfully checked files to JFrog.."
                echo "Test Step - Value of textFiles = $JSONFiles"
               
                script {
                    
                    
                def uploadSpecSTART = '{"files": ['
                def uploadSpecPatStart = '{"pattern": "'   
                def uploadSpecPatEnd = '",'                          
                def uploadSpecTarget = '"target": "DocSecOps/"}'
                def uploadSpecEND = ']}'
                    
                uploadSpecJSON = uploadSpecSTART
                 sh "echo ${uploadSpecJSON}"
                     def texts = JSONFiles.split('\n')
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
