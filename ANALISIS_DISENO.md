# Análisis de Diseño - Sistema de Caja Fuerte

## Introducción

El presente documento describe las decisiones de diseño adoptadas en la implementación del sistema de caja fuerte mediante la metodología de Test-Driven Development (TDD). Se detalla la justificación de cada componente de la solución propuesta.

---

## 1. Identificación de las Clases del Dominio

### Clase `CajaFuerte`

Se identificó una única clase en el dominio del problema. La razón principal es que la caja fuerte constituye una entidad conceptual única y autónoma que encapsula toda la lógica requerida.

**Justificación:** 
- El problema no requiere descomponer la caja fuerte en sub-entidades (como cerraduras, códigos, etc.) de forma independiente.
- La responsabilidad única de la clase es gestionar el comportamiento de una caja fuerte individual.
- No existen dependencias externas que requieran de clases adicionales.
- La solución sigue el principio KISS (Keep It Simple, Stupid), evitando sobre-ingeniería innecesaria.

---

## 2. Definición de Atributos

### `codigoSeguridad: String`

**Propósito:** Almacenar el código actual de apertura de la caja fuerte.

**Justificación:**
- Se utiliza `String` como tipo de dato para permitir códigos alfanuméricos, más allá de solo números.
- Es modificable, ya que el código debe actualizarse cada vez que se cierra la caja con un nuevo código.
- Se inicializa en el constructor con el código inicial proporcionado por el usuario.

### `abierta: boolean`

**Propósito:** Mantener el estado actual de la caja fuerte.

**Justificación:**
- Es un estado binario (abierto o cerrado), por lo que `boolean` es la opción más eficiente.
- Es fundamental para validar operaciones (por ejemplo, no se puede cerrar una caja que ya está cerrada).
- Su uso evita cálculos o consultas adicionales para determinar el estado.

---

## 3. Definición de Métodos

### `abrir(String codigo): void`

**Responsabilidad:** Verificar el código de acceso y abrir la caja si es correcto.

**Lógica:**
1. Verifica que el código proporcionado coincida con el código de seguridad almacenado.
2. Si coincide, marca la caja como abierta (`abierta = true`).
3. Si no coincide, lanza una excepción con el mensaje "Código incorrecto".

**Justificación:**
- El nombre es un verbo directo en español, claramente expresa la acción.
- La excepción es apropiada porque un código incorrecto es una condición anómala de seguridad.
- No permite estados inconsistentes (validación antes de cambiar estado).

### `cerrar(String codigoNuevo): void`

**Responsabilidad:** Cerrar la caja y actualizar el código de seguridad.

**Lógica:**
1. Verifica que la caja esté abierta.
2. Si no está abierta, lanza una excepción con el mensaje "La caja fuerte no está abierta".
3. Si está abierta, actualiza el código de seguridad y marca la caja como cerrada.

**Justificación:**
- Combina dos operaciones (cerrar y actualizar código) en un único método porque ocurren en el mismo contexto lógico.
- En cajas de seguridad reales, establecer un nuevo código es parte del acto de cerrar.
- Validar que la caja esté abierta previene operaciones ilógicas.

### `estaAbierta(): boolean`

**Responsabilidad:** Consultar el estado actual de la caja.

**Retorno:** Devuelve `true` si la caja está abierta, `false` si está cerrada.

**Justificación:**
- Es un getter simple que expone el estado sin permitir su modificación directa.
- Permite que los clientes de la clase consulten el estado sin manipularlo directamente.
- Mantiene el encapsulamiento de datos.

---

## 4. Estrategia de Testing con TDD

Se adoptó un enfoque de Test-Driven Development donde se escribieron los tests antes de la implementación. Esto aseguró:

1. **Claridad en los requisitos:** Cada test documenta un comportamiento esperado.
2. **Cobertura completa:** Se logró 100% de cobertura del código.
3. **Validación de diseño:** Los tests validaron que el diseño fuera correcto y completo.

### Tests Implementados

#### Test 1: Estado inicial cerrado
```java
void alCrearseUnaCajaFuerteEstaCerrada()
```
Valida que una caja recién creada esté en estado cerrado.

#### Test 2: Abrir con código correcto
```java
void abriendoLaCajaConElCodigoCorrectoLaabre()
```
Verifica que proporcionando el código correcto, la caja se abre.

#### Test 3: Abrir con código incorrecto
```java
void abriendoLaCajaConUnCodigoIncorrectoLanzaExcepcion()
```
Valida que un código incorrecto lanza una excepción con el mensaje apropiado.

#### Test 4: Estado persistente
```java
void unaCajaAbriertaPermanececeAbiertaAntesDeSerCerrada()
```
Verifica que el estado se mantiene entre operaciones.

#### Test 5: Cerrar la caja
```java
void cerrandoUnaCajaAbriertaLaCierra()
```
Valida que cerrar una caja abierta la marca como cerrada.

#### Test 6: Actualizar código
```java
void cerrandoUnaCajaAbriertaActualizaElCodigo()
```
Verifica que el código se actualiza correctamente al cerrar.

#### Test 7: No cerrar caja cerrada
```java
void cerrandoUnaCajaCerradaLanzaExcepcion()
```
Valida que intentar cerrar una caja cerrada lanza una excepción.

#### Test 8: Código antiguo no funciona
```java
void noSePuedeAbrirUnaCajaConUnCodigoAntiguo()
```
Verifica que después de cambiar el código, el antiguo no funciona.

#### Test 9: Independencia de instancias
```java
void dosCajasDistintasTienenCodigosIndependientes()
```
Valida que cada instancia de `CajaFuerte` mantiene su propio estado.

#### Test 10: Cambios múltiples
```java
void seCanviarElCodigoVariasveces()
```
Verifica que el código se puede cambiar múltiples veces sin inconsistencias.

---

## 5. Cobertura de Código

Se logró **100% de cobertura** de código en la clase `CajaFuerte` mediante los 10 tests implementados. Esto implica:

- **100% de cobertura de líneas:** Todas las líneas de código fueron ejecutadas en al menos un test.
- **100% de cobertura de ramas:** Todas las ramificaciones condicionales (if/else) fueron probadas.
- **Validación de casos normales y excepcionales:** Se probaron tanto escenarios exitosos como casos de error.

---

## 6. Decisiones de Diseño

### Uso de excepciones
Se decidió utilizar excepciones (`RuntimeException`) para casos de error en lugar de códigos de retorno. Esto es apropiado porque:
- Los errores son condiciones excepcionales de seguridad.
- Las excepciones fuerzan al cliente a manejar explícitamente los errores.
- Es más idiomático en Java.

### Encapsulamiento
Los atributos son privados y solo se accede a través de métodos públicos, garantizando:
- Que el estado interno no sea manipulado directamente desde el exterior.
- Que las invariantes de la clase se mantengan (por ejemplo, no se puede cambiar un código sin cerrar antes).

### Responsabilidad única
La clase tiene una única responsabilidad: gestionar el comportamiento de una caja fuerte. No maneja persistencia, autenticación multifactor, ni otros aspectos que podrían separarse en futuras extensiones.

---

## 7. Conclusión

El diseño propuesto es simple, completo y maintainable. La clase `CajaFuerte` encapsula correctamente la lógica requerida, y la batería de tests asegura su corrección y robustez. El enfoque TDD permitió validar el diseño antes de completar la implementación, resultando en una solución de alta calidad.

---

**Autor:** Luca Baello  
**Fecha:** 19 de abril de 2026  
**Asignatura:** Objetos II  
**Universidad:** UNAHUR
