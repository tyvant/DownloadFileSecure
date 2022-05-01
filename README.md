# DownloadFileSecure
download / upload file 

# How to Run application

After clone or download the project go to the folder binaireToTest. 
That folder contains :

-  jar application 
-  AppConfig.yml
-  downloadPostgres.zip (folder that will use to instanciate postgres via docker using docker compose)

Step 1 : instanciate postgres via docker compose

  Unzip the folder and go inside it execute the command : docker-compose up -d 


Step 2: go to binaireToTest 

**java -jar DownloadFileSecure-1.0-SNAPSHOT.jar server AppConfig.yml**


# How to test 

the for test you can use *Postman*

-  Upload (/file/upload)
  This allow you to save in database the selected file. the byte are encrypted
  
    ![image](https://user-images.githubusercontent.com/22868698/166168997-5b5e7da2-6115-4386-81fd-e046ef1b644c.png)

 
 - Download ( /download/list  ||  /download/list/{id} )
   
   I do the download in two steps : 
    
      - /download/list : list filename to download 
   
        - ![image](https://user-images.githubusercontent.com/22868698/166169087-fcec09be-3a10-4704-8f6a-e541fff6334d.png)

     - /download/list/{id}: id to file we want to download
        
        - ![image](https://user-images.githubusercontent.com/22868698/166169181-8d4739d1-5de4-4a7f-9cdb-54a3dcf55c36.png)


# Encryption Method 
   I use the **AES/GCM mode** because data blocks can be encrypted in parallel, 
   while also maintaining confidentiality by using a different counter value for encrypting each block. 
   I think that technic is performant for large file.
    
   

