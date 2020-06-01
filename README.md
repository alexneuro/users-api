# users-api
REST API with JWT

## users-api
END-POINTS
## Authorization: 
### {serverName}/api/auth  
Return authorization login with JWToken
#### Method:
    POST
#### Headers:
    Content-type: application/json
#### Body:
    {"login":"", "password":""}
    


 ## User: 
 ### {serverName}/api/user
 Return user
 #### Method:
     GET
     
### {serverName}/api/user
 Update user info
#### Method:
     POST
#### Headers:
    Content-type: application/json
### Body:
   UserDTO in JSON

 ## Admin: 
  ### {serverName}/api/admin/users
  Return users
  #### Method:
      GET
      
 ### {serverName}/api/user/{id}
  Update, Delete, Create user 
  #### Method:
      POST, DELETE, PUT
 #### Headers:
     Content-type: application/json
 #### Body:
    UserDTO in JSON


