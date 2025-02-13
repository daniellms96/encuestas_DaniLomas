- ¿Qué sucede si intentas borrar una encuesta que no existe? ¿Cómo lo has controlado?.
- Si intentas borrar una encuesta que no existe mediante la URL(/encuestas/eliminar/{idNoExistente}) salta la página de error. La solución a esto es la modificación
del método eliminarEncuesta para que tenga una verificación de tal modo que si no se encuentra esta encuesta no se sigue con la ejecución del resto de  código
y se redirige a la página de inicio (/encuestas/listar).


- Si introduces en un texto del tipo <style>body background-color:red</style> en uno de los campos ¿Qué sucede al ver la encuesta?
¿el navegador ejecuta ese código o no? ¿porqué? ¿cómo podrías hacer que se ejecute ese código o que se deje de ejecutar?.
- Este caso trata la inyección de código SQL. La diferencia de que ocurra una cosa u otra tiene que ver con si dejamos escapar los carateres especiales o no.
Para SI ejecutar el código -> `<p th:utext="${encuesta.comentarios}"></p>`
Para NO ejecutar el código -> `<p th:text="${encuesta.comentarios}"></p>`

- ¿Qué has tenido que modificar en el repositorio para filtrar por motivo de búsqueda?
¿has tenido que escribir el código específico o como lo has realizado?.
- Modificar el repositorio con un método findByMotivoVisita(), modificación del controller y agregación de la vista.

