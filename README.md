# Aplicativo de Gestión de Clientes con Spring Boot

Este repositorio contiene una aplicación de Gestión de Clientes desarrollada con **Spring Boot** y configurada para implementarse en una instancia **AWS EC2**, con el archivo JAR almacenado en un bucket de **S3** y una base de datos MySQL en **AWS RDS**.

---

## **Requisitos previos**

Antes de compilar o ejecutar el proyecto, asegúrate de tener lo siguiente:

- **Java 17** instalado.
- **Maven** instalado.
- Una cuenta activa en **AWS**.
- AWS CLI configurada en tu máquina local:
  ```bash
  aws configure
  ```
- Una instancia EC2 configurada y accesible.

---

## **Instalación y configuración**

### **1. Clonar el repositorio**
```bash
git clone https://github.com/l3nny548/seek.git
cd cliente
```

### **2. Configurar las propiedades del proyecto**
Edita el archivo `src/main/resources/application.properties` para incluir las credenciales de tu base de datos y configuraciones JWT:

```properties
spring.datasource.url=jdbc:mysql://<RDS-ENDPOINT>:3306/gestion_clientes
spring.datasource.username=<DB-USERNAME>
spring.datasource.password=<DB-PASSWORD>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

jwt.secret=YourSecretKey
jwt.expiration=3600000
```

Si estás usando AWS Secrets Manager para las credenciales de la base de datos, asegúrate de configurar el acceso correspondiente en tu código.

### **3. Instalar dependencias**
Ejecuta el siguiente comando para descargar todas las dependencias requeridas:
```bash
mvn clean install
```

---

## **Ejecución local**

Para ejecutar el proyecto en tu máquina local:

1. **Iniciar MySQL localmente o en Docker:**
   Si deseas usar MySQL local, asegúrate de que esté corriendo en el puerto 3306.

   O puedes usar Docker Compose:
   ```bash
   docker-compose up -d
   ```

2. **Ejecutar la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

3. Accede a la aplicación desde tu navegador o cliente REST en:
   ```
   http://localhost:8080
   ```

4. **Documentación Swagger:**
   La documentación de la API está disponible en:
   ```
   http://localhost:8080/api/swagger-ui/index.html
   ```

---

## **Despliegue en AWS EC2**

### **1. Crear la base de datos en AWS RDS**
1. Ve a la consola de **AWS RDS** y crea una instancia de MySQL.
2. Configura:
   - Nombre de la base de datos: `gestion_clientes`.
   - Usuario y contraseña.
3. Copia el endpoint de la base de datos y actualiza el archivo `application.properties` con esta información.

### **2. Empaquetar la aplicación**
Compila la aplicación en un archivo JAR:
```bash
mvn clean package
```
Esto generará un archivo JAR en el directorio `target/`.

### **3. Subir el archivo JAR a S3**
1. Ve a la consola de S3 y crea un bucket (si no tienes uno).
2. Sube el archivo JAR generado en el paso anterior al bucket.
3. Copia el enlace del archivo JAR en S3.

### **4. Configurar una instancia EC2**
1. Ve a la consola de AWS EC2 y lanza una nueva instancia:
   - Selecciona una imagen **Amazon Linux 2** o similar.
   - Escoge un tipo de instancia (por ejemplo, `t2.micro` si estás en el nivel gratuito).
   - Configura un grupo de seguridad que permita acceso a los puertos:
     - **22** para SSH.
     - **8080** para la aplicación.

2. Conéctate a la instancia mediante SSH:
   ```bash
   ssh -i <tu-archivo.pem> ec2-user@<ip-publica-de-la-instancia>
   ```

3. Actualiza el sistema e instala Java:
   ```bash
   sudo yum update -y
   sudo amazon-linux-extras enable corretto17
   sudo yum install java-17-amazon-corretto -y
   ```

### **5. Descargar y ejecutar el JAR en EC2**
1. Descarga el archivo JAR desde S3:
   ```bash
   aws s3 cp s3://<nombre-del-bucket>/<nombre-del-archivo>.jar app.jar
   ```

2. Ejecuta la aplicación:
   ```bash
   java -jar app.jar
   ```

3. La aplicación estará disponible en la IP pública de la instancia:
   ```
   http://<ip-publica-de-la-instancia>:8080
   ```

---

## **Buenas prácticas aplicadas**

1. **Autenticación JWT**:
   - Los endpoints están protegidos mediante un token JWT generado en el endpoint `/auth/login`.

2. **Seguridad de secretos**:
   - Se recomienda usar **AWS Secrets Manager** para gestionar credenciales sensibles.

3. **Configuración segura**:
   - Claves sensibles y credenciales están separadas del código fuente mediante variables de entorno o Secrets Manager.

4. **Escalabilidad**:
   - Diseñado para ejecutarse en instancias EC2 con posibilidad de escalar horizontalmente.

5. **Monitoreo y acceso**:
   - Se recomienda usar **CloudWatch Logs** para rastrear eventos de la aplicación.

---

## **Contribuciones**
Si deseas contribuir a este proyecto, realiza un fork y envía un pull request con tus cambios.

---

## **Licencia**
Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más información.

