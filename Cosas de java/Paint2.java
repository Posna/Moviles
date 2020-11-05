import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image; //Para usar imagenes
import java.awt.image.BufferStrategy;

public class Paint2 extends JFrame{
    /**
     * Const...
     * @param titulo
     */
    public Paint2(String titulo){
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
    }
    
    public void update(double deltaTime){
        if(_logo != null){
            _x += _incX * deltaTime;
            if(_x < 0){
                _x = -_x;
                _incX *= -1;
            }
            else if (_x >= (getWidth() - _imageWidth)){ 
                _x = 2*(getWidth() - _imageWidth) - _x; //Lado derecho - cuanto me he pasado
                _incX *= -1;
            }
        }
    }
    
    public void render(Graphics g){
        
        g.setColor(Color.blue);
        g.fillRect(0, 0, getWidth(), getHeight());
        if(_logo!=null){
            g.drawImage(_logo, (int)_x, 100, null);
        }
    }
    
    public void paint(Graphics g){
        //Graphics2D g2d = (Graphics2D) g; //Esto se puede hacer si necesito el G2D
        //System.out.println("Repintando" + ++_repintado);
        /*g.setColor(new Color(255, 0, 0, 128)); //Se puede hacer new pero es poco eficiente
        g.fillRect(0, 0, 200, 200);
        g.fillRect(100, 100, 200, 200);*/
        //super.paint(g);
        
        
        /*try{
            Thread.sleep(15); //Duerme 15 milisegundos
        } catch (Exception e){
            
        }
        
        repaint();*/
    }
    
    
    //Lo suyo es tener el main en otra clase
    public static void main(String[] args){
        Paint2 ventana = new Paint2("Paint");
        ventana.init(); 
        ventana.setIgnoreRepaint(false);
        ventana.setVisible(true);
        
        
        long lastFrameTime =System.nanoTime();//Tiempo en nanosegundos desde que se lanzo la aplicacion

        //Forzar el action rendering
        ventana.createBufferStrategy(2);
        BufferStrategy strategy = ventana.getBufferStrategy();

        while(true) {
            long currentTime = System.nanoTime();
            long nanoDelta = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            ventana.update(((double)nanoDelta)/1.0E9); 
            Graphics g = strategy.getDrawGraphics();
            try{
                ventana.render(g);
            }finally{
                g.dispose();
            }
            strategy.show();
        }
    }
    
    //private int _repintado = 0;
    
    Image _logo;

    private double _x = 0;
    int _incX = 6000; //Pîxeles por segundo
    int _imageWidth;

}