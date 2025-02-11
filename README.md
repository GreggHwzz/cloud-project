# cloud-project

# Microservice Service1 - Spring Boot

## 📌 Prérequis
Avant de tester ce projet, assurez-vous d'avoir installé :
- [Java 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/)
- [Kubernetes (Minikube)](https://minikube.sigs.k8s.io/docs/start/)

---

## 🚀 Lancer le projet en local (sans Docker)
### 1️⃣ Cloner le dépôt
```bash
git clone https://github.com/ton-user/nom-du-projet.git
cd nom-du-projet/backend/service1
```

### 2️⃣ Compiler et exécuter l’application
```bash
mvn clean package
mvn spring-boot:run
```

### 3️⃣ Tester l'API
Ouvrir un navigateur ou utiliser `curl` :
```bash
curl http://localhost:8080/api/hello
```
Vous devriez obtenir :
```json
Hello World from Service 1!
```

---

## 🐳 Exécuter avec Docker
### 1️⃣ Construire l’image Docker
```bash
docker build -t mon-projet-service1 .
```

### 2️⃣ Lancer le conteneur
```bash
docker run -p 8080:8080 mon-projet-service1
```

### 3️⃣ Tester l'API
```bash
curl http://localhost:8080/api/hello
```

---

## ☸️ Déployer sur Kubernetes (Minikube)
### 1️⃣ Démarrer Minikube
```bash
minikube start
```

### 2️⃣ Appliquer les configurations Kubernetes
```bash
kubectl apply -f kubernetes/service1-deployment.yaml
kubectl apply -f kubernetes/service1-service.yaml
```

### 3️⃣ Vérifier que le pod est en cours d'exécution
```bash
kubectl get pods
```

### 4️⃣ Exposer le service avec Minikube
```bash
minikube service service1 --url
```
Cela retournera une URL que vous pourrez tester dans votre navigateur ou avec `curl`.

---

## 🛠️ Prochaines étapes
- Ajouter un deuxième microservice et les connecter
- Intégrer une base de données (PostgreSQL / MySQL)
- Ajouter une API Gateway

---

Si vous avez des questions, n'hésitez pas à me contacter ! 🚀

