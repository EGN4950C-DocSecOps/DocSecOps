# DevSecOps Data Processing Source Code

Original Repository: https://github.com/jaroldsabillon/FIleProcessing

A file processing project to be converted to a jenkins plug. Metadata extraction will be done using apache libraries, and will start will docx, pdf, and pptx files. Unrecognized files will undergo generic data extraction, if unsuccesfull it will set it aside and add it to a list of unprocessed files.

Information extracted is written to JSON files

Implemented URLChecker that requires "-validateURL" arugument parameter
