# Post Management

https://roadmap.sh/projects/blogging-platform-api

## Indicaciones

1. run docker compose up
2. En el Postman usar la URL http://localhost:3000/api/v1/post

### Funcionalidades

- GET ALL POST
- GET BY CATEGORY
- GET BY CONTENT
- GET BY TAGS
- GET BY ID
- CREATE
- UPDATE
- DELETE

### BODY

- TITLE: String (4 a 100 caracteres)
- CONTENT: String (min 10 caracteres)
- CATEGORY: String (TECHNOLOGY,GAMES,NEWS,SHOPPING)
- TAGS: Array de Strings (min 4 caracteres)