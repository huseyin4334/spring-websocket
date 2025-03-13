minikube start --driver=docker --memory=8g --cpus=4

# insert socket app image to minikube
eval $(minikube docker-env)

# Windows version
minikube docker-env | Invoke-Expression

minikube image load socket-app:latest

docker build -t socket-app .

kubectl apply -f deployment.yaml

