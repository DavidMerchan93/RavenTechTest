
# RavenTechTest

**RavenTechTest** es una aplicación Android diseñada para demostrar la implementación de arquitecturas modernas, herramientas avanzadas y mejores prácticas en desarrollo móvil. Este proyecto utiliza **Kotlin**, **Jetpack Compose** y sigue los principios de **Clean Architecture** para garantizar un código escalable, mantenible y extensible.

---

## **Arquitecturas Usadas**
### **Clean Architecture**
El proyecto implementa la arquitectura **Clean Architecture**, separando responsabilidades en capas bien definidas:
1. **Capa de Presentación (Presentation):**
   - Maneja la interfaz de usuario (UI) utilizando **Jetpack Compose**.
   - Implementa el patrón **MVVM (Model-View-ViewModel)** para gestionar la lógica de presentación y estados.
2. **Capa de Dominio (Domain):**
   - Contiene la lógica de negocio y los casos de uso.
   - Define interfaces para repositorios que son implementadas por la capa de datos.
3. **Capa de Datos (Data):**
   - Implementa las fuentes de datos locales y remotas.
   - Define repositorios que interactúan con la API y la base de datos.

---

## **Herramientas Usadas**
1. **Jetpack Compose:** Para la creación de la interfaz de usuario.
2. **Hilt:** Para la inyección de dependencias.
3. **Retrofit:** Para la comunicación con APIs RESTful.
4. **Room:** Para el manejo de la base de datos local.
5. **MockK:** Para pruebas unitarias y de integración.

---

## **Módulos y Responsabilidades**
El proyecto está modularizado para mantener una separación clara de responsabilidades:

### **1. Módulo `presentation`:**
   - Contiene las pantallas de la aplicación.
   - Define los ViewModels para gestionar la lógica de presentación y estados de la UI.

### **2. Módulo `domain`:**
   - Contiene los modelos de dominio y los casos de uso.
   - Define las interfaces de repositorios para una abstracción limpia.

### **3. Módulo `data`:**
   - Implementa los repositorios y las fuentes de datos.
   - Maneja la interacción con **Room** para la base de datos local y **Retrofit** para las llamadas a la API.

### **4. Módulo `network`:**
   - Centraliza las configuraciones de red, como interceptores y creación de clientes de Retrofit.

### **5. Módulo `core`:**
   - Proporciona utilidades comunes y clases compartidas entre los módulos, como validadores o manejadores de excepciones.

---

## **Cómo se Creó la UI**
La interfaz de usuario se desarrolló utilizando **Jetpack Compose** y siguiendo los principios de diseño de **Material Design 3**:
- **Pantalla Principal (HomeScreen):** Muestra una lista de artículos cargados desde la API o la base de datos local.
- **Pantalla de Detalle:** Muestra información detallada de un artículo seleccionado.
- **Interacciones:** Incluyen eliminación, restauración y manejo de estados como error o carga.

---

## **Cómo Instalar la Aplicación**
1. **Clona el Repositorio:**
   ```bash
   git clone https://github.com/usuario/RavenTechTest.git
   cd RavenTechTest
   ```

2. **Configura las Claves de API (opcional):**
   - Crea un archivo `apikey.properties` en la raíz del proyecto.
   - Agrega las claves necesarias para el funcionamiento de la API.

3. **Abre el Proyecto en Android Studio:**
   - Usa **Android Studio Flamingo** o una versión superior.

4. **Limpia y Construye el Proyecto:**
   ```bash
   ./gradlew clean build
   ```

5. **Ejecuta la Aplicación:**
   - Conecta un dispositivo físico o usa un emulador.
   - Ejecuta el proyecto desde Android Studio.

---

## **Pruebas**
1. **Ejecutar Pruebas Unitarias:**
   ```bash
   ./gradlew test
   ```

2. **Ejecutar Pruebas de UI:**
   ```bash
   ./gradlew connectedAndroidTest
   ```

---

Este proyecto es un ejemplo robusto de cómo implementar una arquitectura moderna y herramientas avanzadas en Android. 🚀

--- 

### **Licencia**
Este proyecto está bajo la licencia MIT.
