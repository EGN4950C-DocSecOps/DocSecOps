# DevSecOps Pipeline

Our group is configuring a DevSecOps pipeline to handle distinct types of documentation. 
The pipeline will support plain text files, Word documents, PDFs, and PowerPoints. 
 
Currently, we configured a pipeline that uses a web hook to grab *.txt files from this Github repository anytime a push occurs. If the file(s) successfully passes through the pipeline they will be uploaded to an artifact repository to be stored.
