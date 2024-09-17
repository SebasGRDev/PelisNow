# "Tu guía personal al cine." Encuentra rápidamente las películas que te interesan, ya sean éxitos de taquilla o joyas ocultas. ¡Con solo unos clics!


## Definición del proyecto:
### La aplicación utiliza **Retrofit** para consumir una API y obtener las películas mejor calificadas, así como las que están en cartelera. Se mostrarán en su respectivo **RecyclerView** de forma horizontal, implementados dentro de un **Fragment**. Al seleccionar uno, se redirige a otro Fragment con detalles de la película: título, póster, resumen y calificación, esta última mostrada dinámicamente con estrellas según su puntuación. Incluye una **barra de búsqueda** que opera en **tiempo real** para buscar películas por nombre. Los datos se guardan localmente con **Room** (SQLite), permitiendo el acceso y filtrado de películas incluso sin conexión a internet.

![Screens PelisNow](https://github.com/user-attachments/assets/1de9b183-2c27-4d60-8c28-d300de2a7220)

# ¡Todo lo que buscas para elegir tu próxima película!
## Tecnologías usadas:
- Lenguaje de programación **Kotlin**.
- **Retrofit** para el consumo de APIs.
- **Room** para guardar los datos localmente.
- **Fragments** para mostrar las vistas.
- **RecyclerView** para las listas.
- Arquitectura **MVVM**
- **LiveData**
- **Corrutinas**

![image](https://github.com/user-attachments/assets/22313bb1-3b3c-4d62-92e9-13825cd0ea48)

# ¡Nunca más te quedarás sin saber qué ver!
## Se consume el api de las películas "TopRated" (título, poster y calificación) y se pintan en una Card mediante RecyclerView de manera horizontal.
![image](https://github.com/user-attachments/assets/633c9ef5-c3f4-4906-9658-8e613dce7de6)

# ¡Estrenos imperdibles te esperan!
## Se consume el api de las películas "Movies In Theater" (título, poster y calificación) y se pintan en una Card mediante RecyclerView de manera horizontal.
![image](https://github.com/user-attachments/assets/d99e5495-a0de-4764-b5a7-94a8d901af56)

# ¡Ya sabes qué ver! Conoce la sinopsis y la nota.
![image](https://github.com/user-attachments/assets/f3d3f64a-85eb-414f-bb9a-7d04df0f0311)

# Encuentra tu película al instante.
![image](https://github.com/user-attachments/assets/071718c8-4f41-451b-99fc-dc4461eae566)
