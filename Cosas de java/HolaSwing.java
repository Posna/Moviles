import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener; //Para el listener-observer
import java.awt.event.ActionEvent;


public class HolaSwing extends JFrame{
    
    //Se puede poner dentro para que quede mas limpito
    /*class MiActionListener implements ActionListener{ //Esta clase se puede mover dentro del metodo y puede acceder a los parametros del metodo
    
        public void actionPerformed(ActionEvent e){
            //System.out.println("AAAAAAAAAAAAAAAAAAA!");
            System.out.println(_title);
        }
    }*/

    public HolaSwing(String titulo){
        super(titulo); //Metodo de la super clase
        //setVisible(true); Hacer esto esta MAL =(
        _title = titulo;    
    }
    public void init(){
        setSize(400, 400); //Mejor por encima de setVisible
        setLayout(new java.awt.GridLayout(1,1)); //2 filas 1 columnas
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Añadimos un boton
        JButton boton;
        boton = new JButton("Pulsame!! UwU");

        //Ahora lo añadimos a la ventana
        add(boton);
        //add(new JButton("♦♦"));

        //boton.addActionListener(new MiActionListener()); //Solo tiene sentido hacer este tipo de news asi
        /*boton.addActionListener(new ActionListener(){ //Tambien se puede hacer esta barbaridad LUL
            public void actionPerformed(ActionEvent e){
                //System.out.println("AAAAAAAAAAAAAAAAAAA!");
                //System.out.println(_title);
                onClick();
            }
        }
        );*/
        boton .addActionListener((ActionEvent e) -> System.out.println("Pulsado !^^!"));
    }

    private void onClick() {
        System.out.println("Pulsado!");
    }

    //Lo suyo es tener el main en otra clase
    public static void main(String[] args){
        HolaSwing ventana = new HolaSwing("Hola swing");
        ventana.init();
        ventana.setVisible(true);
        System.out.println("Ventana visible");
    }
    private String _title;
}