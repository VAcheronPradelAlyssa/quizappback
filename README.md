# QuizAppBack

API backend Java/Spring Boot pour la gestion de quiz interactifs.

## Présentation

QuizAppBack est une API REST permettant de créer, consulter, modifier et supprimer des quiz et leurs questions. Elle propose également un jeu de quiz de base pour la découverte immédiate de l’application côté front.

- **Stack** : Java 21, Spring Boot 4, JPA/H2, Maven
- **API RESTful** : endpoints CRUD pour quiz et questions
- **Sécurité** : endpoints publics (configurable)
- **CORS** : ouvert sur http://localhost:4200 pour développement front
- **Conteneurisation** : Dockerfile multi-stage optimisé
- **Tests** : unitaires sur la couche service

## Fonctionnalités principales

- Création, édition, suppression de quiz
- Ajout, édition, suppression de questions dans un quiz
- Récupération de tous les quiz ou d’un quiz par ID
- Quiz de base injectés au démarrage (Java, Spring Boot, Web)

## Lancer le projet en local

### Prérequis
- Java 21
- Maven 3.9+
- (Optionnel) Docker

### Démarrage classique
```sh
mvn spring-boot:run
```
L’API sera disponible sur http://localhost:8080

### Démarrage via Docker
```sh
docker build -t quizappback .
docker run -p 8080:8080 quizappback
```

## Endpoints principaux

- `GET    /api/quizzes` : liste tous les quiz
- `GET    /api/quizzes/{id}` : quiz par ID
- `POST   /api/quizzes` : créer un quiz
- `PUT    /api/quizzes/{id}` : modifier un quiz
- `DELETE /api/quizzes/{id}` : supprimer un quiz
- `GET    /api/quizzes/{quizId}/questions` : questions d’un quiz
- `POST   /api/quizzes/{quizId}/questions` : ajouter une question
- `DELETE /api/quizzes/{quizId}/questions/{questionId}` : supprimer une question

### Exemple de payload Quiz
```json
{
  "title": "Java Basics",
  "questions": [
    {
      "text": "What is Java?",
      "options": ["Programming language", "Coffee", "Island", "Framework"],
      "answer": "Programming language"
    }
  ]
}
```

## Configuration
- **CORS** : autorisé pour http://localhost:4200 (Angular par défaut)
- **Base de données** : H2 en mémoire (aucune config à faire pour tester)
- **Sécurité** : tout est public par défaut, facilement configurable via `SecurityConfig.java`

## Tests
```sh
mvn test
```

## Conteneurisation
- Dockerfile multi-stage (build + runtime)
- `.dockerignore` pour un build rapide et propre

## Pour aller plus loin
- Ajout d’une vraie base de données (PostgreSQL, MySQL…)
- Authentification JWT ou OAuth2
- Pagination, recherche, filtrage
- Documentation Swagger (springdoc déjà inclus)
- Déploiement cloud (Azure, AWS, GCP…)

---

### Auteur
Alyssa Vacheron
