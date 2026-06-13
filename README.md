# ms-pacientes

Microservicio de gestión de **Pacientes** del sistema **Servicio de Salud RedNorte**.
Administra el registro y la información de los pacientes que ingresan al sistema de listas de espera (CRUD completo), aplicando el patrón **Repository**.

Forma parte de una arquitectura de microservicios:

```
ms-front (React) → bff-gateway → [ ms-pacientes | ms-citas | ms-listaEspera | ms-reasignacion ]
```

---

## Stack tecnológico

| Tecnología | Versión / Detalle |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.6 |
| Build | Maven (con wrapper `mvnw`) |
| Persistencia | Spring Data JPA |
| Base de datos | MySQL (H2 en memoria para los tests) |
| Utilidades | Lombok |
| Documentación API | springdoc / Swagger UI |
| Cobertura de pruebas | JaCoCo |

---

## Requisitos previos

- **JDK 21** instalado.
- **MySQL** corriendo en `localhost:3306` (ej. XAMPP), con usuario `root` y sin contraseña.
  - La base de datos `db_pacientes` se crea automáticamente al iniciar (`createDatabaseIfNotExist=true`).
- No es necesario instalar Maven: el proyecto incluye el wrapper `mvnw`.

---

##  Configuración

La configuración está en `src/main/resources/application.properties`:

- Puerto: **8081**
- Base de datos: `jdbc:mysql://localhost:3306/db_pacientes`
- Usuario / contraseña: `root` / *(vacía)* — ajustar según tu MySQL.

Para los **tests** se usa un perfil separado (`src/test/resources/application-test.properties`) con **H2 en memoria**, por lo que las pruebas **no requieren MySQL**.

---

## ️ Cómo ejecutar

```bash
# Construir
./mvnw.cmd clean install

# Ejecutar
./mvnw.cmd spring-boot:run
```

El microservicio queda disponible en `http://localhost:8081`.

---

##  API REST

Ruta base: `/api/pacientes`

| Método | Endpoint | Descripción | Respuesta |
|---|---|---|---|
| `POST` | `/api/pacientes` | Crea un paciente | 201 Created |
| `GET` | `/api/pacientes` | Lista todos los pacientes | 200 OK |
| `GET` | `/api/pacientes/{id}` | Obtiene un paciente por id | 200 OK / 404 |
| `PUT` | `/api/pacientes/{id}` | Actualiza un paciente | 200 OK / 404 |
| `DELETE` | `/api/pacientes/{id}` | Elimina un paciente | 204 No Content / 404 |

### Documentación interactiva (Swagger)
Con el servicio corriendo: **http://localhost:8081/swagger-ui.html**

### Ejemplo de cuerpo (POST / PUT)
```json
{
  "rut": "12345678-9",
  "nombre": "Juan Perez",
  "contacto": "juan@mail.com",
  "historial": "Sin antecedentes"
}
```

### Manejo de errores
| Código | Situación |
|---|---|
| `400` | Datos inválidos (validación `@Valid`) |
| `404` | Paciente no encontrado |
| `409` | RUT duplicado |

---

##  Pruebas y cobertura

```bash
# Ejecutar todas las pruebas unitarias
./mvnw.cmd test
```

Las pruebas usan **JUnit 5 + Mockito** (sin base de datos real, gracias a H2 + perfil `test`).

El reporte de **cobertura (JaCoCo)** se genera en:
```
target/site/jacoco/index.html
```
Cobertura actual: **~90% de líneas** (sobre el mínimo exigido del 60%).

---

##  Estructura del proyecto

```
src/main/java/com/rednorte/ms_pacientes/
├── model/         → Entidad JPA Paciente
├── repository/    → PacienteRepository (Repository Pattern)
├── dto/           → PacienteRequest / PacienteResponse
├── service/       → PacienteService (interfaz) + PacienteServiceImpl
├── controller/    → PacienteController (endpoints REST)
├── exception/     → Excepciones propias + GlobalExceptionHandler + ErrorResponseDTO
└── config/        → OpenApiConfig (Swagger)
```

##  Patrón de diseño
**Repository Pattern** — la interfaz `PacienteRepository` extiende `JpaRepository`, desacoplando la lógica de negocio del acceso a datos. Spring Data genera la implementación en tiempo de ejecución.

---

##  Equipo
Proyecto académico — Desarrollo Fullstack III (DSY1106), Duoc UC.
Responsable del microservicio: **Cristian T.**
