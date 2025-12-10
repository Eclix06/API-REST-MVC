# TP1 – API de gestion des auteurs et des livres

API REST réalisée avec Spring Boot 3.5, Spring Data JPA, Bean Validation et Springdoc OpenAPI.



# 1. Lancer l’application

## Prérequis

* Java 17
* Maven
* MySQL ou MariaDB
* Port 8080 disponible

## Configuration MySQL

Dans `application.properties` :

```
spring.datasource.url=jdbc:mysql://localhost:3306/tp1?createDatabaseIfNotExist=true 
spring.datasource.username=root
spring.datasource.password=XXXX

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

app.api-key=123456789abcdef
springdoc.api-docs.version=OPENAPI_3_0
```

## Commandes

```
mvn clean install
mvn spring-boot:run
```

Application disponible sur :
[http://localhost:8080](http://localhost:8080)

Documentation Swagger :
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)



# 2. Sécurité (API Key)

Les routes GET sont publiques.
Toutes les routes POST, PUT, PATCH et DELETE nécessitent ce header :


X-API-KEY: 123456789abcdef


En cas d’absence, l’API renvoie 401 Unauthorized.



# 3. Endpoints disponibles

## Auteurs (`/authors`)

| Méthode | Endpoint        | Description                           |
| ------- | --------------- | ------------------------------------- |
| GET     | `/authors`      | Liste des auteurs                     |
| GET     | `/authors/{id}` | Récupérer un auteur                   |
| POST    | `/authors`      | Créer un auteur (clé API requise)     |
| PUT     | `/authors/{id}` | Modifier un auteur (clé API requise)  |
| DELETE  | `/authors/{id}` | Supprimer un auteur (clé API requise) |



## Livres (`/books`)

| Méthode | Endpoint      | Description                          |
| ------- | ------------- | ------------------------------------ |
| GET     | `/books`      | Liste paginée et filtrée             |
| GET     | `/books/{id}` | Récupérer un livre                   |
| POST    | `/books`      | Créer un livre (clé API requise)     |
| PUT     | `/books/{id}` | Modifier un livre (clé API requise)  |
| DELETE  | `/books/{id}` | Supprimer un livre (clé API requise) |



# 4. Filtres disponibles sur `/books`

Tous les filtres sont optionnels et combinables.

| Paramètre  | Exemple           | Description          |
| ---------- | ----------------- | -------------------- |
| `title`    | `?title=harry`    | Filtre sur le titre  |
| `authorId` | `?authorId=1`     | Filtre par auteur    |
| `category` | `?category=NOVEL` | Filtre par catégorie |
| `yearFrom` | `?yearFrom=2000`  | Année minimale       |
| `yearTo`   | `?yearTo=2010`    | Année maximale       |
| `page`     | `?page=0`         | Numéro de page       |
| `size`     | `?size=5`         | Taille de page       |
| `sort`     | `?sort=year,desc` | Tri selon un champ   |


## Exemples de requêtes

Rechercher tous les romans publiés entre 1940 et 1950 :

GET /books?category=NOVEL&yearFrom=1940&yearTo=1950



Trier par titre :

GET /books?sort=title,asc




# 5. Endpoints Statistiques

| Endpoint                         | Description                    |
| -------------------------------- | ------------------------------ |
| `GET /stats/books-per-category`  | Nombre de livres par catégorie |
| `GET /stats/top-authors?limit=3` | Auteurs avec le plus de livres |



# 6. Exemples de requêtes JSON

## Créer un auteur

```
{
  "firstName": "George",
  "lastName": "Orwell",
  "birthDate": "1903-06-25"
}
```

## Créer un livre

```
{
  "title": "1984",
  "isbn": "9782070368228",
  "year": 1949,
  "category": "NOVEL",
  "authorId": 1
}
```

## Exemple de requête invalide (ISBN incorrect)

```
{
  "title": "Bad book",
  "isbn": "123",
  "year": 2020,
  "category": "NOVEL",
  "authorId": 1
}
```

Résultat attendu : 400 Bad Request.



# 7. Export Postman / Insomnia

Le rendu doit inclure un export Postman ou Insomnia contenant :

* Toutes les routes `/authors`
* Toutes les routes `/books`
* Les routes `/stats`
* Les tests avec et sans API key

Si besoin, je peux te générer le fichier directement.



# 8. Architecture du projet


```
src/
 ├─ controller/
 ├─ service/
 ├─ repository/
 ├─ domain/
 ├─ dto/
 ├─ exception/
 ├─ config/
```


* DTO : validation et transfert
* Services : logique métier
* Repositories : accès base de données
* Entities : mapping JPA
* Exceptions : gestion des erreurs JSON
* ApiKeyFilter : filtre de sécurité
* Springdoc : documentation OpenAPI