# 🎬 MovieDataService

Este proyecto es un **microservicio de gestión de datos de películas** construido con Spring Boot que utiliza una arquitectura hexagonal. Demuestra la integración con APIs externas, mapeo de datos y almacenamiento en Elasticsearch.

## 📋 **Análisis de la Aplicación**

La aplicación está construida con:
- **Spring Boot 3.3.2** con Java 17
- **Arquitectura Hexagonal** (Clean Architecture)
- **Elasticsearch 8.10.0** para almacenamiento
- **Kibana 8.10.0** para visualización
- **Feign Client** para consumir APIs externas
- **MapStruct** para mapeo de objetos

---

## 🚀 **Paso a Paso para Ejecutar la Aplicación**

### **1. Prerrequisitos**
```bash
# Verificar que tienes instalado:
- Docker Desktop
- Docker Compose
- Java 17 (para desarrollo local)
- Maven 3.6+ (para desarrollo local)
```

### **2. Levantar la Infraestructura (Elasticsearch + Kibana)**
```bash
# Navegar al directorio del proyecto
cd MovieDataService

# Levantar Elasticsearch y Kibana
docker-compose up -d
```

### **3. Verificar que los Servicios Estén Funcionando**
```bash
# Verificar contenedores activos
docker-compose ps

# Verificar logs de Elasticsearch
docker-compose logs elasticsearch

# Verificar logs de Kibana
docker-compose logs kibana
```

### **4. Acceder a Kibana**
- **URL**: http://localhost:5601
- **Estado**: Kibana se conectará automáticamente a Elasticsearch
- **Índice**: `movies` (se creará automáticamente cuando se indexen películas)

### **5. Compilar y Ejecutar la Aplicación Spring Boot**
```bash
# Compilar la aplicación
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run
```

**O alternativamente:**
```bash
# Compilar y crear JAR
mvn clean package

# Ejecutar JAR
java -jar target/movie-data-service-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en: **http://localhost:8080**

---

## 🔌 **Endpoints y Métodos Expuestos**

La aplicación expone **2 endpoints principales** a través del `MovieController`:

### **1. Indexar Películas**
```http
POST /movies/index
```

**Descripción**: Indexa todas las películas disponibles desde la API externa de Hackerrank hacia Elasticsearch.

**Ejemplo de uso**:
```bash
curl -X POST http://localhost:8080/movies/index
```

**Respuesta**:
```json
"Movies indexed successfully."
```

### **2. Buscar Películas**
```http
GET /movies/search?title={title}&year={year}
```

**Parámetros**:
- `title` (opcional): Título de la película (búsqueda parcial)
- `year` (opcional): Año de lanzamiento
- **Nota**: Al menos uno de los parámetros debe ser proporcionado

**Ejemplos de uso**:

```bash
# Buscar por título
curl "http://localhost:8080/movies/search?title=Avengers"

# Buscar por año
curl "http://localhost:8080/movies/search?year=2020"

# Buscar por título y año
curl "http://localhost:8080/movies/search?title=Avengers&year=2020"

# Buscar solo por título parcial
curl "http://localhost:8080/movies/search?title=Av"
```

**Respuesta**:
```json
[
  {
    "Title": "Avengers: Endgame",
    "Year": 2019,
    "imdbID": "tt4154796"
  },
  {
    "Title": "Avengers: Infinity War",
    "Year": 2018,
    "imdbID": "tt4154756"
  }
]
```

---

## 📊 **Configuración y Uso de Kibana**

### **Acceso a Kibana**
1. **URL**: http://localhost:5601
2. **Tiempo de inicio**: ~2-3 minutos después de levantar Docker Compose

### **Configurar el Índice en Kibana**

1. **Crear Index Pattern**:
   - Ve a **Stack Management** → **Index Patterns**
   - Haz clic en **Create index pattern**
   - Ingresa: `movies*`
   - Selecciona **@timestamp** como time field (si está disponible)
   - Haz clic en **Create index pattern**

2. **Explorar Datos**:
   - Ve a **Discover**
   - Selecciona el index pattern `movies*`
   - Verás todos los documentos indexados

### **Consultas Útiles en Kibana**

```json
# Buscar películas por título
{
  "query": {
    "match": {
      "title": "Avengers"
    }
  }
}

# Buscar películas por año
{
  "query": {
    "term": {
      "year": 2020
    }
  }
}

# Buscar películas por título y año
{
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "title": "Avengers"
          }
        },
        {
          "term": {
            "year": 2019
          }
        }
      ]
    }
  }
}
```

---

## 🧪 **Ejemplos Prácticos de Ejecución**

### **Flujo Completo de Prueba**

#### **Paso 1: Verificar que Elasticsearch esté funcionando**
```bash
curl http://localhost:9200/_cluster/health
```

#### **Paso 2: Indexar películas**
```bash
curl -X POST http://localhost:8080/movies/index
```

#### **Paso 3: Verificar en Elasticsearch que se indexaron**
```bash
# Ver el índice movies
curl http://localhost:9200/movies/_count

# Ver algunos documentos
curl http://localhost:9200/movies/_search?size=5
```

#### **Paso 4: Buscar películas**
```bash
# Buscar películas de Avengers
curl "http://localhost:8080/movies/search?title=Avengers"

# Buscar películas del 2020
curl "http://localhost:8080/movies/search?year=2020"

# Buscar películas específicas
curl "http://localhost:8080/movies/search?title=Spider&year=2019"
```

### **Usando Postman o Insomnia**

**Colección de requests**:

1. **Index Movies**:
   - Method: `POST`
   - URL: `http://localhost:8080/movies/index`
   - Headers: `Content-Type: application/json`

2. **Search by Title**:
   - Method: `GET`
   - URL: `http://localhost:8080/movies/search?title=Avengers`

3. **Search by Year**:
   - Method: `GET`
   - URL: `http://localhost:8080/movies/search?year=2020`

4. **Search by Title and Year**:
   - Method: `GET`
   - URL: `http://localhost:8080/movies/search?title=Spider&year=2019`

### **Verificación en Kibana**

1. **Acceder a Kibana**: http://localhost:5601
2. **Crear Index Pattern**: `movies*`
3. **Ir a Discover** y ver los documentos indexados
4. **Usar Dev Tools** para ejecutar consultas Elasticsearch

---

## 🔧 **Configuración de la Aplicación**

### **Variables de Entorno Importantes**

```properties
# Elasticsearch
spring.elasticsearch.uris=http://localhost:9200
spring.elasticsearch.index.name=movies
spring.elasticsearch.read-timeout=3000

# API Externa
movies.client.url=https://jsonmock.hackerrank.com/api/moviesdata
```

### **Puertos Utilizados**

- **Aplicación Spring Boot**: `8080`
- **Elasticsearch**: `9200` (HTTP), `9300` (Transport)
- **Kibana**: `5601`

---

## 📋 **Resumen de Comandos Rápidos**

```bash
# 1. Levantar infraestructura
docker-compose up -d

# 2. Verificar servicios
docker-compose ps

# 3. Compilar y ejecutar aplicación
mvn clean spring-boot:run

# 4. Indexar películas
curl -X POST http://localhost:8080/movies/index

# 5. Buscar películas
curl "http://localhost:8080/movies/search?title=Avengers"

# 6. Acceder a Kibana
# http://localhost:5601

# 7. Parar servicios
docker-compose down
```

---

## 🎯 **Arquitectura de la Aplicación**

La aplicación sigue **Clean Architecture** con:

- **Domain**: `Movie` (modelo de dominio)
- **Application**: Casos de uso (`IndexMoviesUseCase`, `SearchMoviesUseCase`)
- **Infrastructure**: 
  - **Inbound**: REST Controller
  - **Outbound**: Elasticsearch Repository, Feign Client
  - **Gateways**: `MoviesClient` (API externa)

**Flujo de datos**:
1. **Index**: API Externa → Feign Client → Use Case → Elasticsearch
2. **Search**: REST Controller → Use Case → Elasticsearch → Response

---

## 🏗️ **Estructura del Proyecto**

```
src/
├── main/java/com/evaluation/movies/
│   ├── application/
│   │   ├── ports/outbound/          # Puertos de salida
│   │   └── usecases/                # Casos de uso
│   ├── domain/
│   │   ├── model/                   # Modelos de dominio
│   │   └── repository/              # Interfaces de repositorio
│   └── infrastructure/
│       ├── adapters/
│       │   ├── inbound/rest/        # Controladores REST
│       │   └── outbound/            # Adaptadores de salida
│       ├── gateways/                # Clientes externos
│       └── mappers/                 # Mappers de objetos
└── test/
    └── java/                        # Tests unitarios e integración
```

