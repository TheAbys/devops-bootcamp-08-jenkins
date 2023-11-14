# jenkins introduction

## 2 - Install Jenkins

added a new droplet with a new firewall which allows connection through :8080
connected to the server and install docker

    apt update
    apt install docker.io

installing application through docker
port 50000 is not used in the current case, but is necessary for a cluster setup, multiple worker nodes communicate through that port

    docker run -p 8080:8080 -p 50000:50000 -d \
        -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts

application is now reachable through http://138.68.111.144:8080/

for installation we require the admin password which is stored within the docker container in path /var/jenkins_home/secrets/initialAdminPassword

    docker ps
    docker exec -it <container-hash> bash
    cat /var/jenkins_home/secrets/initialAdminPassword

password can obviously be found on the host if you access the right docker volume folder

1. we install suggested plugins
2. added a new admin user basti
3. started with jenkins

## 3 - Introduktion to Jenkins UI

You basically can divide it into to parts. An administrator part and a DevOps part.
Through Manage Jenkins you can administer the jenkins application.
Through New Item you can build pipelines for all your applications.

## 4 - Install Build Tools in Jenkins

There are two ways.
Installing a build tool through Manage Jenkins => Tools (Maven for example).
Or installing it from the command line on the machine (the container, not the host) itself

    docker exec -u 0 -it <container-hash> bash

Get the current version of os (this helps to find the correct installation guide for the build tools we require)

    cat /etc/issue
    apt update


The script is not up-to-date anymore
    curl -sL https://deb.nodesource.com/setup_20.x -o nodesource_setup.sh

Correct solution
    apt update
    apt install -y ca-certificates curl gnupg
    mkdir -p /etc/apt/keyrings
    curl -fsSL https://deb.nodesource.com/gpgkey/nodesource-repo.gpg.key | gpg --dearmor -o /etc/apt/keyrings/nodesource.gpg

    NODE_MAJOR=20 // change version to whatever version must be installed
    echo "deb [signed-by=/etc/apt/keyrings/nodesource.gpg] https://deb.nodesource.com/node_$NODE_MAJOR.x nodistro main" | tee /etc/apt/sources.list.d/nodesource.list
    apt update
    apt install nodejs -y


