# DB Exporter - Choripan Solutions

Proyecto semestral para la materia de Ingeniería de Software, desarrollado por Choripan Solutions. Esta es una aplicación backend construida con Spring Boot que expone una API REST para interactuar con una base de datos MySQL. Incluye funcionalidades de seguridad basadas en JSON Web Tokens (JWT) para la autenticación y autorización de usuarios.

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Web**: Para crear la API REST.
- **Spring Data JPA**: Para la persistencia de datos.
- **Spring Security**: Para la gestión de seguridad y autenticación.
- **MySQL**: Como motor de base de datos.
- **Maven**: Como gestor de dependencias y construcción del proyecto.
- **JWT (JSON Web Tokens)**: Para la autenticación stateless.
- **Lombok**: Para reducir el código boilerplate en las entidades y otros modelos.

## Prerrequisitos

Antes de empezar, asegúrate de tener instalado lo siguiente:
- **JDK 21** (Java Development Kit)
- **Maven**
- **MySQL Server**
- **Postman** (o cualquier otro cliente API de tu elección)

## Configuración del Proyecto

1.  **Clonar el repositorio:**
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd ProyectoSemestralGrupo2
    ```

2.  **Configurar la Base de Datos:**
    - Asegúrate de que tu servidor MySQL esté en ejecución.
    - Crea una base de datos llamada `choripan_solutions`.
      ```sql
      CREATE DATABASE choripan_solutions;
      ```
    - La aplicación utiliza `spring.jpa.hibernate.ddl-auto=update`, por lo que las tablas se crearán o actualizarán automáticamente al iniciar la aplicación.

3.  **Configurar las propiedades de la aplicación:**
    - Abre el archivo `src/main/resources/application.properties`.
    - Modifica las siguientes propiedades si tus credenciales de MySQL son diferentes:
      ```properties
      spring.datasource.username=root
      spring.datasource.password=
      ```

## Cómo Ejecutar la Aplicación

Puedes ejecutar la aplicación de dos maneras:

1.  **Usando Maven (desde la terminal):**
    Navega al directorio raíz del proyecto (`db_exporter`) y ejecuta:
    ```bash
    mvn spring-boot:run
    ```

2.  **Desde tu IDE (IntelliJ, Eclipse, etc.):**
    - Importa el proyecto como un proyecto Maven.
    - Busca la clase `DbExporterApplication.java` y ejecútala como una aplicación Java.

> **Nota Importante:** La propiedad `server.port=0` en `application.properties` hace que la aplicación se inicie en un puerto disponible aleatorio. Revisa la consola al iniciar para ver en qué puerto se está ejecutando. Verás un mensaje como: `Tomcat started on port(s): 51234 (http)`.

---

## Guía de API para Postman

A continuación se muestran ejemplos de cómo interactuar con la API. Reemplaza `{{PORT}}` con el puerto en el que se está ejecutando tu aplicación.

### Autenticación

La API está protegida y requiere un token JWT para acceder a la mayoría de los endpoints. El flujo es: **Registrar un usuario -> Iniciar sesión para obtener un token -> Usar el token para otras peticiones.**

#### 1. Registrar un Nuevo Usuario

- **Endpoint:** `POST http://localhost:{{PORT}}/auth/register`
- **Descripción:** Crea un nuevo usuario en el sistema.
- **Body:** `raw (JSON)`
  ```json
  {
      "username": "nuevo_usuario",
      "password": "una_contraseña_segura",
      "email": "usuario@example.com",
      "nombre": "Nombre",
      "apellido": "Apellido"
  }
  ```
- **Ejemplo con cURL:**
  ```bash
  curl --location 'http://localhost:{{PORT}}/auth/register' \
  --header 'Content-Type: application/json' \
  --data '{
      "username": "nuevo_usuario",
      "password": "una_contraseña_segura",
      "email": "usuario@example.com",
      "nombre": "Nombre",
      "apellido": "Apellido"
  }'
  ```

#### 2. Iniciar Sesión (Login)

- **Endpoint:** `POST http://localhost:{{PORT}}/auth/login`
- **Descripción:** Autentica a un usuario y devuelve un token JWT si las credenciales son correctas.
- **Body:** `raw (JSON)`
  ```json
  {
      "username": "nuevo_usuario",
      "password": "una_contraseña_segura"
  }
  ```
- **Respuesta Exitosa (200 OK):**
  ```json
  {
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJudWV2b191c3VhcmlvIiwiaWF0IjoxNzE2NDk..."
  }
  ```
- **Ejemplo con cURL:**
  ```bash
  curl --location 'http://localhost:{{PORT}}/auth/login' \
  --header 'Content-Type: application/json' \
  --data '{
      "username": "nuevo_usuario",
      "password": "una_contraseña_segura"
  }'
  ```

### Endpoints Protegidos

Para acceder a los siguientes endpoints, debes incluir el token JWT en la cabecera `Authorization`.

#### Cómo configurar la autorización en Postman:

- **Headers:**
  - `Authorization`: `Bearer <TU_TOKEN_JWT>`

**Cómo configurar en Postman:**
1.  Copia el token que recibiste en la respuesta del login.
2.  Ve a la pestaña **Authorization** de tu nueva petición.
3.  Selecciona el tipo **Bearer Token**.
4.  Pega el token en el campo "Token".

---

### Gestión de Participantes

#### 1. Listar todos los participantes

- **Endpoint:** `GET http://localhost:{{PORT}}/api/participantes`
- **Descripción:** Devuelve una lista de todos los participantes.
- **Headers:** `Authorization: Bearer <TU_TOKEN_JWT>`
- **Ejemplo con cURL:**
  ```bash
  curl --location 'http://localhost:{{PORT}}/api/participantes' \
  --header 'Authorization: Bearer <TU_TOKEN_JWT>'
  ```
### Gestión de Reportes
#### 1. Exportar Participantes a Excel

- **Endpoint:** ` GET http://localhost:{{PORT}}/api/reportes/participantes/export/excel`
- **Descripción:** Genera y descarga un archivo .xlsx que contiene la lista completa de todos los participantes registrados en la base de datos.
- **Headers:** `Authorization: Bearer <TU_TOKEN_JWT> +- Respuesta Exitosa: La API devolverá un archivo para descargar. En Postman, verás un botón "Save Response" para guardarlo en tu disco.` 

- **Ejemplo con cURL:**
    ```bash
    curl --location 'http://localhost:{{PORT}}/api/reportes/participantes/export/excel' \
    --header 'Authorization: Bearer <TU_TOKEN_JWT>' \
    --output participantes.xlsx
    plaintext
  ```

#### ¿Cómo personalizar el archivo Excel? 
Toda la lógica para la creación del archivo Excel se encuentra en la clase ExcelService.java (src/main/java/choripan_solutions/db_exporter/Servicio/ExcelService.java). +> +> Si necesitas cambiar las columnas, añadir más datos, aplicar estilos (como colores o negritas) o modificar el contenido del archivo, simplemente debes editar el método crearExcelDeParticipantes() en esa clase. La librería utilizada es Apache POI, que ofrece un control total sobre la estructura y el estilo del documento.


  

> **Nota:** Los endpoints como `/auth/register` y `/auth/login` son ejemplos comunes. La ruta exacta (`/auth`, `/api/auth`, etc.) y los campos requeridos en el body pueden variar según la implementación final en los controladores. Adapta los ejemplos a tu código.
