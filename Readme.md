# LibrarySearch API

## Usage guide
##### Clone the repo
```bash 
git clone https://github.com/arani87/librarysearcherapi.git
```
### One command to build and run the API
##### On Linux/Mac/Git Bash execute the following command
```bash 
./run.sh
```
##### On Windows command line
```cmd
run.bat
```

### Test the API using swagger endpoints
[Swagger UI](http://localhost:8080/swagger-ui.html)  
[Main API endpoint](http://localhost:8080/swagger-ui.html#!/search45controller/searchUsingGET)  
[Metrics endpoint](http://localhost:8080/swagger-ui.html#!/operation45handler/handleUsingGET_1)

### (Alternatively) Run as a Docker container
```bash
docker pull arani87/librarysearchapp:1.0
docker run -p 8080:8080 -t arani87/librarysearchapp:1.0
```