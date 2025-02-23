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
git clone https://github.com/GreggHwzz/cloud-project.git
cd cloud-project/backend/todo-service
```

### 2️⃣ Compiler et exécuter l’application
```bash
mvn clean package
mvn spring-boot:run
```

### 3️⃣ Tester l'API
Une fois que l'application est en cours d'exécution, tu peux tester l'API en utilisant un navigateur ou un outil comme `curl`.

#### 1. **Lister les tâches**
   Pour récupérer la liste des tâches, utilise la commande suivante :
   ```bash
   curl http://localhost:8080/api/todos
   ```
   **Résultat attendu** :
   ```json
   []
   ```
   (Cela indique qu'il n'y a actuellement aucune tâche.)

#### 2. **Créer une tâche**
   Pour ajouter une nouvelle tâche, envoie une requête `POST` avec les données de la tâche sous format JSON :
   ```bash
   curl -X POST http://localhost:8080/api/todos -H "Content-Type: application/json" -d '{"title":"Acheter du lait","completed":false}'
   ```
   **Résultat attendu** :
   ```json
   {"id":1,"title":"Acheter du lait","completed":false}
   ```
   (Cela confirme que la tâche a été ajoutée avec succès, et elle reçoit un identifiant unique.)

#### 3. **Supprimer une tâche**
   Pour supprimer une tâche, envoie une requête `DELETE` en utilisant l'ID de la tâche à supprimer. Par exemple, pour supprimer la tâche avec l'ID `1` :
   ```bash
   curl -X DELETE http://localhost:8080/api/todos/1
   ```


## 🐳 Exécuter avec Docker
### 1️⃣ Construire l’image Docker
```bash
docker build -t todo-service .
```

### 2️⃣ Lancer le conteneur
```bash
docker run -p 8080:8080 todo-service
```

### 3️⃣ Tester l'API

[Retour à Tester l'API](#3️⃣-tester-lapi)

---

## ☸️ Déployer sur Kubernetes (Minikube)
### 1️⃣ Démarrer Minikube
```bash
minikube start
```

### 2️⃣ Appliquer les configurations Kubernetes
```bash
kubectl apply -f kubernetes/todo-service-deployment.yaml
kubectl apply -f kubernetes/todo-service-service.yaml
```

### 3️⃣ Vérifier que le pod est en cours d'exécution
```bash
kubectl get pods
```

### 4️⃣ Exposer le service avec Minikube
```bash
minikube service todo-service --url
```
Cela retournera une URL que vous pourrez tester dans votre navigateur ou avec `curl`.

