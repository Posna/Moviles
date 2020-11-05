import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image; //Para usar imagenes

public class Paint extends JFrame{
    /**
     * Const...
     * @param titulo
     */
    public Paint(String titulo){
        super(titulo);
    }

    public void init() {
        setSize(400, 400); //Mejor por encima de setVisible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            _logo = javax.imageio.ImageIO.read(new java.io.File("ElArrebato.jpg"));
            _imageWidth = _logo.getWidth(null);
        }
        catch (Exception e){
            System.out.println(e);
        }
        _x = 0;

        _lastFrameTime =System.nanoTime();//Tiempo en nanosegundos desde que se lanzo la aplicacion
    }

    public void paint(Graphics g){
        //Graphics2D g2d = (Graphics2D) g; //Esto se puede hacer si necesito el G2D
        //System.out.println("Repintando" + ++_repintado);
        /*g.setColor(new Color(255, 0, 0, 128)); //Se puede hacer new pero es poco eficiente
        g.fillRect(0, 0, 200, 200);
        g.fillRect(100, 100, 200, 200);*/
        //super.paint(g);


        g.setColor(Color.blue);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(_logo, (int)_x, 100, null);
        long _currentTime = System.nanoTime();
        long nanoDelta = _currentTime - _lastFrameTime;
        _lastFrameTime = _currentTime;
        _x += ((double)_incX) * nanoDelta/1.0E9;
        if(_x < 0){
            _x = -_x;
            _incX *= -1;
        }
        else if (_x >= (getWidth() - _imageWidth)){ 
            _x = 2*(getWidth() - _imageWidth) - _x; //Lado derecho - cuanto me he pasado
            _incX *= -1;
        }
        try{
            Thread.sleep(15); //Duerme 15 milisegundos
        } catch (Exception e){

        }

        repaint();
    }


    //Lo suyo es tener el main en otra clase
    public static void main(String[] args){
        Paint ventana = new Paint("Paint");
        ventana.init();
        ventana.setVisible(true);
    }

    //private int _repintado = 0;

    Image _logo;

    private double _x = 0;
    int _incX = 50; //Pîxeles por segundo
    int _imageWidth;

    long _lastFrameTime;
}