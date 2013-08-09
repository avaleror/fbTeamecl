/*
Este es mi proyecto de final del curso de programaci??n de aplicaciones en Java,
es un programa con interfaz gr??fica Swing que gestiona una base de datos de los 
jugadores de un equipo de f??tbol americano. Autor: Andr??s Valero
 */
package fbteam;

    //IMPORTACI??N DE PAQUETES UTILIZADOS EN NUESTRA APLICACI??N
    import javax.swing.*;
    import java.awt.event.*;
    import java.awt.*;
    import java.sql.*;
    import java.io.*;
    import java.util.*;

public class FbTeam extends JFrame implements ActionListener{

    //VARIABLES SWING
    private JMenuBar barra;
    private JMenu menu1;
    private JMenuItem mi1, mi2;
    private JTextArea fdbuscar;// Muestra resultador de las b??squedas
    private JScrollPane scroll; //A??adiren??mos un scroll al JTextArea
    private JTextField fddorsal, fdposicion, fdnombre, fdapellido; //El interfaz necesita 4 textfield
    private JLabel titulo, version, nombreautor;//Pantalla de Inicio
    private JLabel lbdorsal,lbposicion, lbnombre, lbapellido; //El interfaz necesita 5 label 
    
    //botones de la interfaz
    JButton alta, baja, modificar, buscar, dbtotxt;
    
    
    Formatter archivo;
    Scanner leer;
  
    public FbTeam() {
    
        setLayout(null);
        // VARIABLES QUE DAN LA INFORMACI??N IMPRESA EN LA PANTALLA INICIAL
        titulo = new JLabel("Gesti??n de Jugadores");
        titulo.setBounds(50, 50, 180, 50);
        add(titulo);
        
        nombreautor = new JLabel("Andr??s Valero");
        nombreautor.setBounds(200, 100, 180, 50);
        add(nombreautor);
        
        version = new JLabel("Versi??n 1.0");
        version.setBounds(300, 150, 180, 50);
        add(version);
        
        // CREACI??N DEL MEN?? DE LA PANTALLA DE INICIO
        barra = new JMenuBar();
        setJMenuBar(barra);
        menu1 = new JMenu("Men??");
        barra.add(menu1);
        
        
        mi1 = new JMenuItem("Base de Datos");
        mi1.addActionListener(this);
        menu1.add(mi1);
        
        mi2 = new JMenuItem("Salir");
        mi2.addActionListener(this);
        menu1.add(mi2);
      
    }
    
       
    public void actionPerformed(ActionEvent ev){
        
        Container f = this.getContentPane();
        
        //OPCI??N 1 DEL MEN??, GESTI??N DE LA BASE DE DATOS
        if(ev.getSource()==mi1){
         
            // VARIABLES SWING DE LA PANTALLA INICIAL QUITADAS DE PANTALLA
        titulo.setVisible(false);
        nombreautor.setVisible(false);
        version.setVisible(false);
        
        
            //Text ??rea
        fdbuscar = new JTextArea();
        fdbuscar.setBounds(350, 50, 300, 200);
        fdbuscar.setEditable(false);//El usuario no puede escribir en el JTextArea
        add(fdbuscar);
        fdbuscar.setVisible(true);
          
        //A??adimos un scroll a JTextArea
        scroll = new JScrollPane(fdbuscar);//CRAMOS UN NUEVO OBJETO SCROLL
        scroll.setBounds(350, 50, 300, 200);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//HACEMOS EL SCROLL VERTICAL SIEMPRE VISIBLE
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//BLOQUEAMOS SCROLL HORIZONTAL
        add(scroll);
        
            //Labels
        lbdorsal = new JLabel("DORSAL DEL JUGADOR");
        lbdorsal.setBounds(50, 50, 200, 30);
        add(lbdorsal);
        lbdorsal.setVisible(true);
        
        lbposicion = new JLabel("POSICI??N DEL JUGADOR");
        lbposicion.setBounds(50, 100, 200, 30);
        add(lbposicion);
        lbposicion.setVisible(true);
        
        lbnombre = new JLabel("NOMBRE DEL JUGADOR");
        lbnombre.setBounds(50, 150, 200, 30);
        add(lbnombre);
        lbnombre.setVisible(true);
        
        lbapellido = new JLabel("APELLIDO DEL JUGADOR");
        lbapellido.setBounds(50, 200, 200, 30);
        add(lbapellido);
        lbapellido.setVisible(true);

        //Text Fields
        fddorsal = new JTextField();
        fddorsal.setBounds(220, 50, 100, 30);
        add(fddorsal);
        fddorsal.setVisible(true);
        
        fdposicion = new JTextField();
        fdposicion.setBounds(220, 100, 100, 30);
        add(fdposicion);
        fdposicion.setVisible(true);
        
        
        fdnombre = new JTextField();
        fdnombre.setBounds(220, 150, 100, 30);
        add(fdnombre);
        fdnombre.setVisible(true);
        
        fdapellido = new JTextField();
        fdapellido.setBounds(220, 200, 100, 30);
        add(fdapellido);
        fdapellido.setVisible(true);     
           
            //BOTONES
            alta = new JButton("ALTA");
            alta.setBounds(50, 300, 110, 30);
            add(alta);
            alta.addActionListener(this);
            alta.setVisible(true);
            
            
            baja = new JButton("BAJA");
            baja.setBounds(170, 300, 110, 30);
            add(baja);
            baja.addActionListener(this);
            baja.setVisible(true);
            
            modificar = new JButton("MODIFICAR");
            modificar.setBounds(290, 300, 110, 30);
            add(modificar);
            modificar.addActionListener(this);
            modificar.setVisible(true);   
            
            buscar = new JButton("BUSCAR");
            buscar.setBounds(410, 300, 110, 30);
            add(buscar);
            buscar.addActionListener(this);
            buscar.setVisible(true);
            
            dbtotxt = new JButton("PASAR BASE DE DATOS A TXT"); 
            dbtotxt.setBounds(50, 350, 300, 40);
            add(dbtotxt);
            dbtotxt.addActionListener(this);
            dbtotxt.setVisible(true);
          
                
        }
        //OPCI??N 2 DEL MEN??, SALIR
        if(ev.getSource()==mi2){
            System.exit(0);
        }
        
        //BOTONES Y SUS ACCIONES
        
        if(ev.getSource()==buscar){
                fdbuscar.setText("");   
                int evalua=0;
                    //B??SQUEDA SOLO POR NOMBRE
          if((fdnombre.getText().equals(""))==false && fdapellido.getText().equals("")== true)  {    
           
                 try {
                  
                  Class.forName("com.mysql.jdbc.Driver");
                  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/football","root","y3d5862AZ");
                  Statement estado = con.createStatement();
                  ResultSet resultado = estado.executeQuery("SELECT * FROM jugadores WHERE nombre = '"+fdnombre.getText()+"'");
                               
                   //Imprimir resultado de la b??squeda en los campos pertinentes
                    while(resultado.next()){
                         
                         fdbuscar.append(resultado.getString("dorsal")+ " " + resultado.getString("posicion") + " " + resultado.getString("nombre") + " " + resultado.getString("apellido") + "\n");
                         evalua = 1;   
                    }      
                    if(evalua==0){
                        fdbuscar.append("No hay jugadores con este nombre\n");
                    }
                      } catch(SQLException ex){
                        System.out.println("Error de MySQL");
                      } catch(ClassNotFoundException err){
                        err.printStackTrace();
                      } catch(Exception err){
                        System.out.println("Error Inesperado" + err);
                     }
               
                 }
           // B??SQUEDA SOLO POR APELLIDO
        if((fdnombre.getText().equals(""))==true && (fdapellido.getText().equals(""))==false ){
             
            try {
                  Class.forName("com.mysql.jdbc.Driver");
                  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/football","root","y3d5862AZ");
                  Statement estado = con.createStatement();
                  ResultSet resultado1 = estado.executeQuery("SELECT * FROM jugadores WHERE apellido = '"+fdapellido.getText()+"'");
                 
                   //Imprimir resultado de la b??squeda en los campos pertinentes
                    while(resultado1.next()){
                                           
                        fdbuscar.append( resultado1.getString("dorsal")+ " " + resultado1.getString("posicion") + " " + resultado1.getString("nombre") + " " + resultado1.getString("apellido") + "\n");
                        evalua=1;
                     }
                      if(evalua==0){
                        fdbuscar.append("No hay jugadores con este apellido\n");
                    }  
                    
                      } catch(SQLException ex){
                        System.out.println("Error de MySQL");
                      } catch(ClassNotFoundException err){
                        err.printStackTrace();
                      } catch(Exception err){
                        System.out.println("Error Inesperado" + err);
                     }
            
        }
         //B??SQUEDA POR NOMBRE Y APELLIDO
        if((fdnombre.getText().equals(""))==false && (fdapellido.getText().equals(""))==false)  {      
                
             try {
                  Class.forName("com.mysql.jdbc.Driver");
                  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/football","root","y3d5862AZ");
                  Statement estado = con.createStatement();
                  ResultSet resultado2 = estado.executeQuery("SELECT * FROM  jugadores WHERE  nombre LIKE  '"+fdnombre.getText()+"' AND  apellido LIKE  '"+fdapellido.getText()+"'");
               

                   //Imprimir resultado de la b??squeda en los campos pertinentes
                    while(resultado2.next()){
                                               
                   fdbuscar.append(resultado2.getString("dorsal")+ " " + resultado2.getString("posicion") + " " + resultado2.getString("nombre") + " " + resultado2.getString("apellido") + "\n");
                           evalua=1; 
                     }
                    
                    if(evalua==0){
                        fdbuscar.append("No hay jugadores llamados as??\n");
                    }
                           
                      } catch(SQLException ex){
                        System.out.println("Error de MySQL");
                      } catch(ClassNotFoundException err){
                        err.printStackTrace();
                      } catch(Exception err){
                        System.out.println("Error Inesperado" + err);
                     }
               
                 }
        
         if((fddorsal.getText().equals(""))==false && (fdposicion.getText().equals(""))==true && (fdapellido.getText().equals(""))==true && (fdnombre.getText().equals(""))==true ){
             
            try {
                  Class.forName("com.mysql.jdbc.Driver");
                  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/football","root","y3d5862AZ");
                  Statement estado = con.createStatement();
                  ResultSet resultado3 = estado.executeQuery("SELECT * FROM jugadores WHERE dorsal = '"+fddorsal.getText()+"'");
                 
                   //Imprimir resultado de la b??squeda en los campos pertinentes
                    while(resultado3.next()){
                                           
                        fdbuscar.append( resultado3.getString("dorsal")+ " " + resultado3.getString("posicion") + " " + resultado3.getString("nombre") + " " + resultado3.getString("apellido") + "\n");
                        evalua=1;
                     }
                           
                    if(evalua==0){
                        fdbuscar.append("No hay jugadores con ese dorsal\n");
                    }
                    
                      } catch(SQLException ex){
                        System.out.println("Error de MySQL");
                      } catch(ClassNotFoundException err){
                        err.printStackTrace();
                      } catch(Exception err){
                        System.out.println("Error Inesperado" + err);
                     }
            
           }
              //PODEMOS IMPRIMIR UN LISTADO DE LOS JUGADORES QUE OCUPAN UNA DETERMINADA DEMARCACI??N EN EL CAMPO
         if((fddorsal.getText().equals(""))==true && (fdposicion.getText().equals(""))==false && (fdapellido.getText().equals(""))==true && (fdnombre.getText().equals(""))==true ){
           
            try {
                  Class.forName("com.mysql.jdbc.Driver");
                  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/football","root","y3d5862AZ");
                  Statement estado = con.createStatement();
                  ResultSet resultado3 = estado.executeQuery("SELECT * FROM jugadores WHERE posicion = '"+fdposicion.getText()+"'");
                 
                   //Imprimir resultado de la b??squeda en los campos pertinentes
                    while(resultado3.next()){
                                           
                        fdbuscar.append( resultado3.getString("dorsal")+ " " + resultado3.getString("nombre") + " " + resultado3.getString("apellido") + "\n");
                        evalua=1;
                     }
                    
                    if(evalua==0){
                        fdbuscar.append("No hay jugadores en esa demarcaci??n\n");
                    }
                           
                      } catch(SQLException ex){
                        System.out.println("Error de MySQL");
                      } catch(ClassNotFoundException err){
                        err.printStackTrace();
                      } catch(Exception err){
                        System.out.println("Error Inesperado" + err);
                     }
            
           }
         
        }
    
        
        
        //PASAMOS TODA LA BASE DE DATOS A UN ARCHIVO .TXT .
        if(ev.getSource()==dbtotxt){
                 
                 try {
                  Class.forName("com.mysql.jdbc.Driver");
                  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/football","root","y3d5862AZ");
                  Statement estado = con.createStatement();                   
                  ResultSet resultado = estado.executeQuery("SELECT dorsal, posicion, nombre, apellido FROM jugadores WHERE 1" );
                  PrintWriter archivo = new PrintWriter(new FileWriter("//Users//andres//Documents//pruebasjava//exportacion2.txt"));
                   //Imprimir resultado de la b??squeda en los campos pertinentes
                    while(resultado.next()){
                        
                         archivo.println( resultado.getString("dorsal")+ " " + resultado.getString("posicion")+ " " + resultado.getString("nombre")+ " " + resultado.getString("apellido"));                      
                                     
                    }
                    
                      archivo.close();
                      
                  
                     
                      } catch(SQLException ex){
                        System.out.println("Error de MySQL");
                      } catch(ClassNotFoundException err){
                        err.printStackTrace();
                      } catch(Exception err){
                        System.out.println("Error Inesperado" + err);
                     }
                 
                 }
        
                    //BOT??N ALTA: PODEMOS DAR DE ALTA UN JUGADOR CON TODOS LOS CAMPOS O DAR DE ALTA SOLO NOMBRE Y APELLIDO A LA ESPERA DE QUE SE LE ASIGNE DORSAL Y POSICI??N
                 if((ev.getSource()==alta) && ((fdnombre.getText().equals(""))==false) && ((fdapellido.getText().equals(""))==false) ){
                   
                   try {
                   Class.forName("com.mysql.jdbc.Driver");
                   Connection con = DriverManager.getConnection("jdbc:mysql://localhost/football","root","y3d5862AZ");
                   Statement estado = con.createStatement();
                   estado.executeUpdate("INSERT INTO jugadores (dorsal, posicion, nombre, apellido) VALUES ('"+fddorsal.getText()+"' , '"+fdposicion.getText()+"' , '"+fdnombre.getText()+"' , '"+fdapellido.getText()+"') ");
                   
                 // INSERT INTO  `football`.`jugadores` (`dorsal` , `posicion` , `nombre` , `apellido`) VALUES ('00',  'WR',  'RASHEED',  'WALLACE');
                           
                      } catch(SQLException ex){
                        System.out.println("Error de MySQL");
                      } catch(ClassNotFoundException err){
                        err.printStackTrace();
                      } catch(Exception err){
                        System.out.println("Error Inesperado" + err);
                     }
                    
                }
                 
                           //BOT??N MODIFICAR: PODEMOS MODIFICAR EL DORSAL Y LA POSICI??N DE UN JUGADOR
                   if((ev.getSource()==modificar) && ((fdnombre.getText().equals(""))==false) && ((fdapellido.getText().equals(""))==false) && ((fddorsal.getText().equals(""))==false) && ((fdposicion.getText().equals(""))==false)){
                   
                   try {
                   Class.forName("com.mysql.jdbc.Driver");
                   Connection con = DriverManager.getConnection("jdbc:mysql://localhost/football","root","y3d5862AZ");
                   Statement estado = con.createStatement();
                   estado.executeUpdate("UPDATE football.jugadores SET dorsal = '"+fddorsal.getText()+"' , posicion = '"+fdposicion.getText()+"' WHERE nombre = '"+fdnombre.getText()+"' AND apellido = '"+fdapellido.getText()+"'");
                   
                    //estado.executeUpdate("UPDATE  `football`.`jugadores` SET  `nombre` =  'PAOLO' WHERE  `jugadores`.`dorsal` =80 AND  `jugadores`.`posicion` =  'CB' AND  `jugadores`.`nombre` =  'SERGIO' AND `jugadores`.`apellido` =  'CONTI' LIMIT 1");
                 
                           
                      } catch(SQLException ex){
                        System.out.println("Error de MySQL" + ex);
                      } catch(ClassNotFoundException err){
                        err.printStackTrace();
                      } catch(Exception err){
                        System.out.println("Error Inesperado" + err);
                     }
                    
                }
                        //BOT??N BAJA: DAMOS DE BAJA JUGADORES DE LA BASE DATOS A PARTIR DEL NOMBRE Y EL APELLIDO
                   if((ev.getSource()==baja) && ((fdnombre.getText().equals(""))==false) && ((fdapellido.getText().equals(""))==false) ){
                   
                   try {
                   Class.forName("com.mysql.jdbc.Driver");
                   Connection con = DriverManager.getConnection("jdbc:mysql://localhost/football","root","y3d5862AZ");
                   Statement estado = con.createStatement();
                   estado.executeUpdate("DELETE FROM football.jugadores WHERE nombre = '"+fdnombre.getText()+"' AND apellido = '"+fdapellido.getText()+"'");
                   
                    //estado.executeUpdate("UPDATE  `football`.`jugadores` SET  `nombre` =  'PAOLO' WHERE  `jugadores`.`dorsal` =80 AND  `jugadores`.`posicion` =  'CB' AND  `jugadores`.`nombre` =  'SERGIO' AND `jugadores`.`apellido` =  'CONTI' LIMIT 1");
                 
                          
                      } catch(SQLException ex){
                        System.out.println("Error de MySQL" + ex);
                      } catch(ClassNotFoundException err){
                        err.printStackTrace();
                      } catch(Exception err){
                        System.out.println("Error Inesperado" + err);
                     }
                    
                }
        
    
    }
    
    public static void main(String[] args) {
        
        FbTeam ventana = new FbTeam();
        ventana.setBounds(100, 200, 800, 600);
        ventana.setVisible(true);
                
    }
}
