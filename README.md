# Auto Ahorro API - Solución Hacking México

Esta API expone distintas funcionalidades para el análisis, validación y proyección de estrategias de ahorro personal. La solución fue desarrollada en Java con Spring Boot y se encuentra totalmente contenida mediante Docker.

---

## Seguridad

Para acceder a cualquiera de los endpoints protegidos, es obligatorio incluir el siguiente header de autorización:
``Authorization: Bearer blackrock-autoahorro-api``


---

## Documentación Swagger

Puedes explorar la documentación y probar los endpoints disponibles accediendo a:

``http://localhost:5477/swagger-ui/index.html``


Incluye descripciones, estructuras de entrada/salida, ejemplos y validaciones por cada endpoint.

---

## Requisitos

- Docker instalado
- Puerto `5477` libre en el host
- Linux (si deseas hacer build directamente fuera de contenedores)

---

## Ejecución usando Docker Compose (Recomendado)

```bash
docker compose -f compose.yaml up --build
```

Esto levantará el contenedor usando:
- Puerto 5477 en tu máquina (host)
- Puerto 80 dentro del contenedor

Una vez desplegado, puedes acceder a la API en:
``http://localhost:5477``

---
## Ejecución manual usando el Dockerfile

Si deseas correr directamente el Dockerfile sin Compose:
1. Construye la imagen:
```bash
docker build -t blk-hacking-mx-gael-alburo .
```
2. Ejecuta el contenedor:
```bash
docker run -p 5477:80 blk-hacking-mx-gael-alburo
```

---

## Detalles técnicos del contenedor
- Basado en una distribución Linux (openjdk:17-jdk-slim) por su ligereza y compatibilidad
- Expone el puerto 80 dentro del contenedor
- Se expone el puerto 5477 en el host (configurado vía compose.yaml)
- Incluye protección mediante token

---

## 👤 Autor

**Gael Alburo** - 
gael.a24@outlook.com

Desarrollador participante en Hacking México 2025
Solución desarrollada como parte del reto técnico de BlackRock
