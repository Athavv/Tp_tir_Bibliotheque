# API REST - Gestion de Bibliothèque

Projet réalisé dans le cadre du TP Spring Boot - Architecture MVC  
**Auteur** : Aathavan Thevakumar  
**Technologies** : Java 25, Spring Boot 3, mysql, Lombok

---

## Lancement de l'application

### Prérequis
- Java 17 ou supérieur
- Maven
- IntelliJ IDEA (recommandé)

### Étapes
1. Cloner ou extraire le projet
2. Ouvrir le projet avec IntelliJ IDEA
3. Lancer la classe principale `Tp1Application.java`
4. L'API sera disponible sur : **http://localhost:8080**

---

## 📋 Liste complète des endpoints

### AUTEURS

| Méthode | Endpoint | Description | Body |
|---------|----------|-------------|------|
| **GET** | `/authors` | Récupère tous les auteurs | - |
| **GET** | `/authors/{id}` | Récupère un auteur par son ID | - |
| **POST** | `/authors` | Crée un nouvel auteur | AuthorDTO |
| **PUT** | `/authors/{id}` | Met à jour un auteur existant | AuthorDTO |
| **DELETE** | `/authors/{id}` | Supprime un auteur | - |

**Exemple de création d'auteur :**
```json
POST http://localhost:8080/authors
Content-Type: application/json

{
  "firstName": "Victor",
  "lastName": "Hugo",
  "birthYear": 1802
}
```

**Réponse (201 Created) :**
```json
{
  "id": 1,
  "firstName": "Victor",
  "lastName": "Hugo",
  "birthYear": 1802
}
```

---

### LIVRES

| Méthode | Endpoint | Description | Body |
|---------|----------|-------------|------|
| **GET** | `/books` | Récupère tous les livres | - |
| **GET** | `/books/{id}` | Récupère un livre par son ID | - |
| **POST** | `/books` | Crée un nouveau livre | BookDTO |
| **PUT** | `/books/{id}` | Met à jour un livre existant | BookDTO |
| **DELETE** | `/books/{id}` | Supprime un livre | - |

**Exemple de création de livre :**
```json
POST http://localhost:8080/books
Content-Type: application/json

{
  "title": "Les Misérables",
  "isbn": "978-2-07-036012-3",
  "year": 1862,
  "category": "NOVEL",
  "authorId": 1
}
```

**Réponse (201 Created) :**
```json
{
  "id": 1,
  "title": "Les Misérables",
  "isbn": "978-2-07-036012-3",
  "year": 1862,
  "category": "NOVEL",
  "authorId": 1,
  "authorFirstName": "Victor",
  "authorLastName": "Hugo"
}
```

**Catégories disponibles :**
- `NOVEL` - Roman
- `ESSAY` - Essai
- `POETRY` - Poésie
- `OTHER` - Autre

---

### STATISTIQUES

| Méthode | Endpoint | Description | Paramètres |
|---------|----------|-------------|------------|
| **GET** | `/stats/books-per-category` | Nombre de livres par catégorie | - |
| **GET** | `/stats/top-authors` | Top auteurs avec le plus de livres | `limit` (défaut: 3) |

**Exemple 1 - Livres par catégorie :**
```bash
GET http://localhost:8080/stats/books-per-category
```

**Réponse :**
```json
{
  "NOVEL": 5,
  "ESSAY": 2,
  "POETRY": 3,
  "OTHER": 1
}
```

**Exemple 2 - Top 5 auteurs :**
```bash
GET http://localhost:8080/stats/top-authors?limit=5
```

**Réponse :**
```json
[
  {
    "firstName": "Victor",
    "lastName": "Hugo",
    "bookCount": 8
  },
  {
    "firstName": "Albert",
    "lastName": "Camus",
    "bookCount": 5
  },
  {
    "firstName": "Émile",
    "lastName": "Zola",
    "bookCount": 3
  }
]
```

---

## Gestion des erreurs

L'API retourne des erreurs au format JSON structuré grâce au **GlobalExceptionHandler** :

### Exemple 1 - Ressource non trouvée (404)
```json
GET /authors/999

{
  "error": "Author not found with id: 999"
}
```

### Exemple 2 - Validation échouée (400)
```json
POST /books
{
  "title": "",
  "isbn": "12345",
  "year": 2030,
  "authorId": null
}

{
  "title": "Title is required",
  "year": "Year cannot be in the future",
  "authorId": "Author ID is required"
}
```



## Fonctionnalités implémentées
```
- CRUD complet pour les auteurs
- CRUD complet pour les livres
- Validation des données avec `@Valid` et annotations Jakarta
- ISBN unique (contrainte en base de données)
- Relations ManyToOne (Book → Author)
- Gestion centralisée des erreurs avec `GlobalExceptionHandler`
- Statistiques par catégorie
- Top auteurs avec limite paramétrable
- Utilisation de JPQL pour les statistiques (Conseil Mr Laroussi)
- DTOs
```
## Fonctionnalités NON implémentées (et pourquoi)
```
### 1. **Pagination et filtres avancés pour `/books`**
**Demandé dans le TP** : `GET /books?title=...&authorId=...&category=...&yearFrom=...&yearTo=...&sort=year,desc`

**Pourquoi non fait** :
- J'ai préféré faire ce que je comprenais
- Pour un premier TP, le CRUD de base est plus important


### 2. **Clé API (X-API-KEY)**

**Demandé dans le TP** : Protection des endpoints POST/PUT/DELETE

**Pourquoi non fait** :
- Après avoir vu avec vous, vous m'avez dis de pas ajouter un dossier `config/`