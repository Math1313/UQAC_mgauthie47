@echo off

echo Stopping any existing mongo-container
docker stop mongo-container

echo Waiting for the container to stop
timeout /t 5

echo Removing any existing mongo-container
docker rm mongo-container

echo Waiting for the container to be removed
timeout /t 10

echo Building the Docker image with the tag 'mongo'
docker build -t mongo .

echo Running the container in detached mode, mapping port 27017
docker run -d -p 27017:27017 --name mongo-container mongo

echo Waiting for MongoDB to start
timeout /t 10

echo Executing the mongosh command inside the running container
docker exec -it mongo-container mongosh