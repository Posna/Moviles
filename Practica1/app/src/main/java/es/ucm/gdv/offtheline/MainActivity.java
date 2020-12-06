package es.ucm.gdv.offtheline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Prueba de concepto de renderizado activo en Android.
 *
 * Esta clase implementa la actividad principal de la aplicación.
 * En condiciones normales (una aplicación más compleja) la
 * implementación se distribuiría en más clases y se haría más
 * versátil.
 *
 * Para que funcione, en el módulo se debe incluir un directorio
 * de Assets y guardar en él el fichero "Bangers-Regular.ttf" con
 * la fuente de letra, que será cargada en ejecución para pintar un
 * rótulo en pantalla.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Método llamado por Android como parte del ciclo de vida de
     * la actividad. Se llama en el momento de lanzarla.
     *
     * @param savedInstanceState Información de estado de la actividad
     *                           previamente serializada por ella misma
     *                           para reconstruirse en el mismo estado
     *                           tras un reinicio. Será null la primera
     *                           vez.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Preparamos el contenido de la actividad.
        _renderView = new MySurfaceView(this);
        System.out.println("OnCreate llamado");
        setContentView(_renderView);

    } // onCreate

    //--------------------------------------------------------------------

    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad va a pasar a primer plano,
     * estando en la cima de la pila de actividades y completamente
     * visible.
     *
     * Es llamado durante la puesta en marcha de la actividad (algo después
     * de onCreate()) y también después de un periodo de pausa (notificado
     * a través de onPause()).
     */
    @Override
    protected void onResume() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onResume();
        _renderView.resume();

    } // onResume

    //--------------------------------------------------------------------

    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad ha dejado de ser la de
     * primer plano. Es un indicador de que el usuario está, de alguna
     * forma, abandonando la actividad.
     */
    @Override
    protected void onPause() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onPause();
        _renderView.pause();

    } // onPause

    //--------------------------------------------------------------------
    //                    Atributos protegidos/privados
    //--------------------------------------------------------------------

    /**
     * Vista principal de la actividad que gestiona, además, el active
     * rendering.
     */
    protected MySurfaceView _renderView;


} // class MainActivity
