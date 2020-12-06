package es.ucm.gdv.offtheline;

//--------------------------------------------------------------------
//                         Clase MySurfaceView
//--------------------------------------------------------------------

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import es.ucm.gdv.engine.android.Engine;

/**
 * Clase con la vista principal de la actividad, que se incluye en ella
 * ocupando todo el espacio disponible.
 *
 * Implementa también el interfaz Runnable para proporcionar
 * active rendering. Para ello es necesario lanzar la ejecución de una
 * hebra cuyo ciclo de vida se gestionará aquí. Para eso, se proporcionan
 * métodos que deben ser llamados externamente para notificar cambios
 * en el ciclo de vida de la actividad.
 *
 * En condiciones normales (una aplicación más compleja) estas dos
 * funcionalidades estarían separadas.
 */
class MySurfaceView extends SurfaceView implements Runnable {

    /**
     * Constructor.
     *
     * @param context Contexto en el que se integrará la vista
     *                (normalmente una actividad).
     */
    public MySurfaceView(Context context) {

        super(context);
        _holder = getHolder();
        _stateMachine = new StateMachine();
        _engine = new Engine(_stateMachine, this);
    } // MySurfaceView

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Método llamado para solicitar que se continue con el
     * active rendering. El "juego" se vuelve a poner en marcha
     * (o se pone en marcha por primera vez).
     */
    public void resume() {

        if (!_running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva, nunca se sabe quién va a
            // usarnos...)
            _running = true;
            _engine.setRunning(_running);
            // Lanzamos la ejecución de nuestro método run()
            // en una hebra nueva.
            _renderThread = new Thread(this);
            _renderThread.start();
        } // if (!_running)

    } // resume

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Método llamado cuando el active rendering debe ser detenido.
     * Puede tardar un pequeño instante en volver, porque espera a que
     * se termine de generar el frame en curso.
     *
     * Se hace así intencionadamente, para bloquear la hebra de UI
     * temporalmente y evitar potenciales situaciones de carrera (como
     * por ejemplo que Android llame a resume() antes de que el último
     * frame haya terminado de generarse).
     */
    public void pause() {

        if (_running) {
            _running = false;
            _engine.setRunning(_running);
            while (true) {
                try {
                    _renderThread.join();
                    _renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            } // while(true)
        } // if (_running)

    } // pause

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Método que implementa el bucle principal del "juego" y que será
     * ejecutado en otra hebra. Aunque sea público, NO debe ser llamado
     * desde el exterior.
     */
    @Override
    public void run() {
        if (_renderThread != Thread.currentThread()) {
            // ¿¿Quién es el tuercebotas que está llamando al
            // run() directamente?? Programación defensiva
            // otra vez, con excepción, por merluzo.
            throw new RuntimeException("run() should not be called directly");
        }

        // Antes de saltar a la simulación, confirmamos que tenemos
        // un tamaño mayor que 0. Si la hebra se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while(_running && getWidth() == 0)
            // Espera activa. Sería más elegante al menos dormir un poco.
            ;

        _engine.run();
    } // run

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Realiza la actualización de "la lógica" de la aplicación. En particular,
     * desplaza el rótulo a su nueva posición en su deambular de izquierda
     * a derecha.
     *
     * @param deltaTime Tiempo transcurrido (en segundos) desde la invocación
     * anterior (frame anterior).
     */
    protected void update(double deltaTime) {


    } // update

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Dibuja en pantalla el estado actual de la aplicación.
     *
     * @param c Objeto usado para enviar los comandos de dibujado.
     */
    protected void render(Canvas c) {



    } // render

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //        Atributos protegidos/privados (de MySurfaceView)
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


    /**
     * Objeto Thread que está ejecutando el método run() en una hebra
     * diferente. Cuando se pide a la vista que se detenga el active
     * rendering, se espera a que la hebra termine.
     */
    Thread _renderThread;

    /**
     * Manejador de la superficie para poder acceder a su contenido.
     */
    SurfaceHolder _holder;

    /**
     * Bandera que indica si está o no en marcha la hebra de
     * active rendering, y que se utiliza para sincronización.
     * Es importante que el campo sea volatile.
     *
     * Java proporciona un mecanismo integrado para solicitar la
     * detencción de una hebra, aunque por simplicidad nosotros
     * motamos el nuestro propio.
     */
    volatile boolean _running = false;

    /**
     * Logica
     */
    StateMachine _stateMachine;

    Engine _engine;

} // class MySurfaceView
