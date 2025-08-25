# Control de Ventas

Aplicación Android para la gestión de compras, proveedores y cuentas.

## Descripción

appMom es una app diseñada para facilitar la administración de compras y proveedores, permitiendo llevar un control eficiente de gastos y balances. Ideal para pequeños negocios, familias o cualquier usuario que requiera organizar sus compras y relaciones con proveedores.

## Funcionalidades principales

- **Registro de usuarios:** Permite crear y gestionar cuentas de usuario.
- **Gestión de proveedores:** Visualiza, agrega y edita información de proveedores.
- **Detalle de proveedores:** Consulta información específica de cada proveedor.
- **Gestión de compras:** Registra y administra compras realizadas.
- **Agregar compra:** Añade nuevas compras al sistema de forma sencilla.
- **Cálculo de cuentas:** Calcula balances y totales para mantener el control financiero.

## Estructura del Proyecto

- `app/`: Módulo principal de la aplicación.
  - `src/main/java/`: Código fuente principal.
  - `src/main/res/`: Recursos gráficos y de interfaz.
  - `src/main/AndroidManifest.xml`: Manifest de la aplicación.
  - `src/test/java/`: Pruebas unitarias.
  - `src/androidTest/java/`: Pruebas instrumentadas.
- `build.gradle.kts`: Configuración de Gradle.
- `settings.gradle.kts`: Configuración de módulos.
- `gradle/`: Configuración de dependencias y wrapper.

## Requisitos

- Android Studio (última versión recomendada)
- JDK 17+
- Gradle (incluido en el wrapper)
- Dispositivo o emulador Android

## Instalación

1. Clona el repositorio:
   ```sh
   git clone <URL-del-repositorio>
   ```
2. Abre el proyecto en Android Studio.
3. Sincroniza el proyecto con Gradle.
4. Ejecuta la aplicación en un emulador o dispositivo físico.

## Compilación

```sh
./gradlew build
```

## Ejecución de Pruebas

```sh
./gradlew test
./gradlew connectedAndroidTest
```

## Contribución

Las contribuciones son bienvenidas. Por favor, abre un issue o envía un pull request.

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

