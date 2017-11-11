# Coeus
Es una librería que permite crear redes neuronales utilizando 
aprendizaje automático para su entrenamiento (TD Lambda Learning). 
Provee una implementación de Redes NTuplas y además una API para 
conectarse con librerías que proveen otras implementaciones de 
redes neuronales como por ejemplo Encog, Neuroph, etc.
Para el aprendizaje, utiliza el método de TDLambda learning el cual 
hace uso de las Trazas de Elegibilidad como método de asignación de 
crédito temporal, el cual puede ser ajustado configurando sus 
diferentes parámetros. 

## Instalación
El proyecto esta construido utilizando Gradle (incorporado en el 
repositorio). 

##### Requisitos
- Java JDK 8 o superior.
- Tener configurada la variable de entorno ***JAVA_HOME***. 

##### Instrucciones Recomendadas
- `gradlew clean`: limpia los directorios del proyecto.   
- `gradlew build`: compila el proyecto.
- `gradlew finalFatJar`: crea un jar con la librería lista para 
usar.  
- `gradlew test`:  ejecuta los test de JUnit.
- `gradlew javadoc`:  compila javadoc.
