# Backend - Sistema de Reservas de Alojamiento

**Grupo E:** Inés Cabrera Martín
* Sofía Alexandra Grigore Barfa
* Paula Seva García
* Valeria Seva García

## Descripción del Proyecto
Este repositorio contiene el código fuente del backend para el sistema de gestión de alojamientos. La implementación cubre los 4 casos de uso principales, basándose en la arquitectura orientada a microservicios y los diagramas de secuencia diseñados en la fase previa.

## Decisiones de Diseño y Notas de Implementación

De cara a la evaluación del proyecto, hemos tomado las siguientes decisiones técnicas para cumplir con los requisitos establecidos:

### 1. Componentes Simulados (Fakes) y Asincronía
Siguiendo las indicaciones de la práctica, los servicios periféricos (`PagosService`, `NotificacionesService` y `MessageBroker`) se han implementado como componentes simulados. 
* Para comprobar que la comunicación asíncrona funciona correctamente (por ejemplo, en el flujo de cancelación de reservas), se debe observar la consola de Spring Boot. Se han incluido trazas (`System.out.println()`) que muestran cómo el Message Broker publica los eventos y notifica al resto de servicios de forma desacoplada.

### 2. Implementación de Arquitectura Lambda (Analíticas)
En el Caso de Uso de Analíticas, hemos simulado la integración de datos provenientes de distintas fuentes. El `AnaliticasService` actúa como orquestador, combinando dinámicamente los datos de la Capa Batch (históricos simulados), la Capa de Velocidad (eventos recientes simulados) y el conteo real de reseñas de la base de datos transaccional.

### 3. Persistencia en Memoria (Thread-Safe)
Para simular la base de datos sin dependencias externas, los repositorios se han implementado utilizando `ConcurrentHashMap`, garantizando así que las operaciones sean *Thread-Safe*. Además, el sistema se inicializa con 5 alojamientos y 5 arrendatarios por defecto para facilitar las pruebas desde la API.

### 4. Control de Reglas de Negocio (Edge Cases)
Hemos añadido validaciones en la capa de servicios para asegurar la consistencia de los datos en casos límite:
* **Validación de fechas:** El sistema bloquea reservas donde la fecha de salida sea anterior a la de entrada, o si la fecha de entrada está en el pasado.
* **Política de 48h y Cancelaciones:** Se impide cancelar reservas con menos de 48 horas de antelación al *check-in*, y se bloquea la posibilidad de un "doble reembolso" si la reserva ya estaba cancelada.
* **Verificación de Reseñas:** Se valida contra `LocalDate.now()` que el arrendatario no pueda publicar una reseña si su estancia en el alojamiento aún no ha finalizado.

### 5. Convenciones de Nomenclatura
Para evitar problemas de codificación al importar o compilar el proyecto en distintos entornos, hemos optado por omitir caracteres especiales. Por ello, las clases relacionadas con las valoraciones se han nombrado como `Resena` y `ResenasService` (sin la ñ).

## Cómo ejecutar y probar el proyecto
1. Clonar el repositorio.
2. Arrancar la aplicación Spring Boot (`BackendApplication.java`).
3. El servidor se levantará en el puerto `8080`.
4. Se pueden probar todos los flujos utilizando **Swagger UI** (accediendo a la ruta correspondiente) o enviando peticiones mediante herramientas como Postman a `http://localhost:8080/...`
