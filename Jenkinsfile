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
    
        stage('Build') {
            steps {
                echo "Building.."
                script {
                    echo "doing build stuff.."
                    textFiles= sh(returnStdout: true, script: 'find ./documents -iname *.txt')
                    sh "ls -l ./documents"
                    echo "$textFiles"
                    echo "$pdfFiles"
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
        stage('Deploy txt to Artifactory') {
            steps {
                if(textFiles != " ")
                {
                    echo 'Deploying *.txt files to JFrog....'
                        rtUpload(
                            serverId: 'artifactory',
                            spec:"""${uploadSpecTXT}"""
                        )
                }
                else
                {
                    echo 'There are no files to Deploy'
                }
                
            }
        }     
    }
}
