# 🍔 Sistema de Gestión - Hamburguesería
**Aplicación de escritorio para toma de pedidos y control de stock**
> Materia: Programación II

---

## 👥 Integrantes
| Nombre | Módulo asignado |
|--------|----------------|
| (Tu Nombre) | Arquitectura / Base de Datos |
| (Compañero 2) | Interfaz de Pedidos |
| (Compañero 3) | Gestión de Menú (CRUD) |
| (Compañero 4) | (Completar) |

---

## 🏗️ Arquitectura
El proyecto sigue el patrón **MVC (Modelo-Vista-Controlador)** con **DAO**:
```text
src/
├── modelo/       → Entidades puras de Java (Producto, Pedido, DetallePedido)
├── dao/          → Lógica de Base de Datos y consultas SQL (ProductoDAO, etc.)
├── vista/        → Interfaces gráficas Swing (VentanaMenu, VentanaPedidos)
├── controlador/  → Cerebro que conecta la vista con el DAO
└── config/       → Configuración general (ConexionDB.java)
```

## ⚙️ Requisitos previos
Instalar en tu PC antes de clonar el proyecto:
|Herramienta | Uso|
|------------|---------------|
|Java JDK 17 o superior | Entorno de ejecución de Java|
|Apache NetBeans | IDE oficial del proyecto (Java with Ant)|
|MySQL / XAMPP | Motor de base de datos| 
|Git | Control de versiones|


## 🚀 Instalación paso a paso (Hacer UNA sola vez)
1. Clonar el repositorio
Abrir la terminal donde guardan sus proyectos y ejecutar:

```git clone [https://github.com/TU_USUARIO/TU_REPOSITORIO.git](https://github.com/TU_USUARIO/sistema-hamburgueseria-java.git)```

2. Preparar la Base de Datos
Encender tu servidor MySQL (desde Linux nativo o abriendo XAMPP).

Abrir MySQL Workbench.

Ejecutar el script hamburgueseria_setup.sql que se encuentra en la carpeta del repositorio para crear las tablas y cargar los productos de prueba.

3. Abrir y configurar el proyecto en NetBeans
Abrir NetBeans.

Ir a File -> Open Project y seleccionar la carpeta clonada.

Paso crítico (Conector MySQL): - En la pestaña Projects (izquierda), desplegar el proyecto.

Click derecho en la carpeta Libraries -> Add JAR/Folder.

Buscar dentro de la carpeta del proyecto la carpeta /lib/ y seleccionar el archivo mysql-connector-j-8.x.x.jar.
(No usar archivos de la carpeta Descargas).

4. Correr la aplicación
Abrir la clase principal (la que contiene el public static void main).

Click derecho -> Run File (o Shift + F6).

## 🔄 Flujo de trabajo con Git (¡LEER OBLIGATORIO!)
# ⚠️ REGLA DE ORO PARA JAVA SWING (Archivos .form)
NetBeans genera un archivo oculto .form por cada ventana gráfica. NUNCA dos personas deben editar el diseño de la misma ventana al mismo tiempo. Si lo hacen y suben los cambios, la ventana se romperá.

# Convención de Commits
Usen mensajes claros para saber qué hizo cada uno:

# Cómo trabajar en una tarea (Uso de Ramas)
NUNCA programar directamente en la rama main.

# 1. Traer los últimos cambios
git checkout main
git pull origin main

# 2. Crear tu rama para trabajar
git checkout -b nombre-de-tu-rama (ej: feature/ventana-pedidos)

# 3. Guardar tus cambios
git add .
git commit -m "feat: diseño inicial ventana de pedidos"

# 4. Subir tu rama a GitHub
git push origin nombre-de-tu-rama

## 🗄️ Base de Datos
# Tablas principales:
|Tabla |Descripción|
|----------|---------------|
|productos | Carta de la hamburguesería y control de stock actual |
|pedidos | Registro general de la comanda y estado del pedido | 
|detalle_pedido | Relación de qué productos exactos tiene cada pedido |

# Credenciales Locales Acordadas:
Para que el código funcione en la PC de todos sin modificar ConexionDB.java, todos deben configurar su MySQL con:

Usuario: root

Contraseña: root

Puerto: 3306
