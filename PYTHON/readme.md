# To start this app:
### minikube start
### docker-compose build python
### docker-compose up python
#### now you can check website on localhost:5000
### kubectl apply -f ./kubernetes/deployments/deployment.yaml
#### you can check your deploy with: 
* kubectl get deploy
* kubectl get pods
### kubectl apply -f ./kubernetes/services/service.yaml 
#### you can check your services with:
* kubectl get svc
#### try to open localhost without port 
