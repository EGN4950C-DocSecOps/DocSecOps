def textFiles = " "
def uploadSpec = " "
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
        stage('Find files') {
            steps {
                echo "Finding the files..."

                script {
                    echo "Getting files from Github repository..."
                    textFiles= sh(returnStdout: true, script: 'find ./documents -iname *.txt')
                    sh "ls -l ./documents"
                    echo "$textFiles"
                 }
            }
        }
        stage('Test') {
            steps {
                echo "Testing..."
                echo "Test Step - Value of textFiles = $textFiles"
               
                script {
                    
                    
                def uploadSpecSTART = '{"files": ['
                def uploadSpecPatStart = '{"pattern": "'   
                def uploadSpecPatEnd = '",'                          
                def uploadSpecTarget = '"target": "DocSecOps/"}'
                def uploadSpecEND = ']}'
                
                uploadSpec = uploadSpecSTART
                sh "echo ${uploadSpec}"
                    def texts = textFiles.split('\n')
                    for (txt in texts) {
                        sh "echo ${txt}"
                        sh "cat ${txt}"
                        uploadSpec = uploadSpec + uploadSpecPatStart + "${txt}" + uploadSpecPatEnd + uploadSpecTarget + ','
                    }
                    uploadSpec = uploadSpec[0..-2]
                    uploadSpec = uploadSpec + uploadSpecEND
                    echo "${uploadSpec}"
                }
            }
        }
         stage('Deploy to Artifactory') {
            steps {
                echo "Deploying files...."
                        rtUpload(
                            serverId: 'artifactory',
                            spec:"""${uploadSpec}"""
                        )
                echo "File(s) successully deployed to artifact repository"
            }
        }
    }
}
