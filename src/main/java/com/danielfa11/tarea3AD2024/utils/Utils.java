package com.danielfa11.tarea3AD2024.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utils {

	private static TreeMap<String, String> diccionarioPaises;
	
	/*
	 * Metodo para extraer las nacionalidades disponibles
	 */
	
	public static TreeMap<String, String> getDiccionarioPaises()
	{
		
		if(diccionarioPaises == null)
		{
			//Creando: Fábrica de Constructores de Documento y Constructor de Documento
	        DocumentBuilderFactory fabricaConstructorDocumento = DocumentBuilderFactory.newInstance();
	        DocumentBuilder constructorDocumento;
	        try {
	        	diccionarioPaises = new TreeMap<>();
                constructorDocumento = fabricaConstructorDocumento.newDocumentBuilder();
                //Abriendo y parseando el documento
		        File fichero = new File("src/main/resources/paises.xml");
		        Document documento = constructorDocumento.parse(fichero);
		        
		        NodeList listaPaises, listaPais;
		        Element paises, pais, id, nombre;
		        int indicePaises, indicePais;
		        
		        listaPaises = documento.getElementsByTagName("paises");
		        indicePaises = 0;
		        
	            while (indicePaises<listaPaises.getLength()) 
	            {
	                paises = (Element) listaPaises.item(indicePaises);
	                
	                // Leyendo iterativamente todos los elementos pais
	                listaPais = paises.getElementsByTagName("pais");
	                indicePais = 0;
	                
	                while (indicePais<listaPais.getLength()) 
	                {
	                    pais = (Element) listaPais.item(indicePais);
	
	                    // Leyendo los elementos descendientes de un elemento pais y agregandolos al diccionario
	                    id = (Element) pais.getElementsByTagName("id").item(0);             
	                    nombre = (Element) pais.getElementsByTagName("nombre").item(0);
	                    
	                    diccionarioPaises.put(id.getTextContent(),nombre.getTextContent());
	                    
	                    indicePais++;
	                
	                }
	                indicePaises++;
	            }
	            
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
		}
        return diccionarioPaises;
	}
	
	
	// Validaciones
	
	/*
	 * Validacion de nombre
	 */
	
	public static boolean validarNombre(String nombre){
		String regex = "^[A-Za-záéíóúÁÉÍÓÚüÜ]+(\\s[A-Za-záéíóúÁÉÍÓÚüÜ]+)*$";
		
		Pattern patron = Pattern.compile(regex);
		Matcher matcher = patron.matcher(nombre);
		
		if(nombre.isBlank())
		{
			alertaNombreVacio();
			return false;
		}
		
		if(matcher.matches())
		{
			return true;
		}
		else
		{
			alertaNombre();
			return false;
		}
	}
	
	/*
	 * Validacion de contraseña
	 */
	
	public static boolean validarContraseña(String contraseña) {
		
		if(contraseña.isBlank()) {
			alertaContraseñaVacia();
			return false;
		}
        else if(contraseña.contains(" ")){
        	alertaContraseña();
        	return false;
        }
        return true;
	}
	
	/*
	 * Validacion de email
	 */
	
	public static boolean validarEmail(String email) {
		
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        
        if(email.isBlank()) {
        	alertaEmailVacio();
        	return false;
        }        
        else if(matcher.matches()) {
        	return true;
        }
        else {
        	alertaEmail();
        	return false;
        }

    }
	
	/*
	 * Validacion de usuario
	 */
	
	public static boolean validarUsuario(String usuario) {
		        
        if(usuario.isBlank()) {
        	alertaUsuarioVacio();
        	return false;
        }        
        else if(usuario.contains(" ")){
        	alertaUsuario();
        	return false;
        }
        return true;
    }
	
	/*
	 * Metodo para conseguir los ids de las nacionalidades por su nombre
	 */
	
	public static String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
	
	// Alertas
	
	/*
	 * Alerta de usuario vacio
	 */
	
	public static void alertaUsuarioVacio() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Usuario vacio");
		alerta.setContentText("No dejes el usuario vacio");
		alerta.show();
	}
	
	/*
	 * Alerta de usuario incorrecto
	 */
	
	public static void alertaUsuario() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Usuario incorrecto");
		alerta.setContentText("No dejes espacios en blanco");
		alerta.show();
	}
	
	
	/*
	 * Alerta de confirmacion de datos
	 */
	
	public static Alert confirmarDatos() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar datos");
        alert.setContentText("¿Has introducido todos los datos correctamente?");
        alert.showAndWait();
        return alert;
	}
	
	/*
	 * Alerta de nombre vacio
	 */
	
	public static void alertaNombreVacio() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Nombre vacio");
		alerta.setContentText("No dejes el nombre vacio");
		alerta.show();
	}
	
	/*
	 * Alerta de nombre incorrecto
	 */
	
	public static void alertaNombre() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Nombre incorrecto");
		alerta.setContentText("No introduzcas numeros ni caracteres especiales");
		alerta.show();
	}
	
	/*
	 * Alerta de contraseña vacia
	 */
	
	public static void alertaContraseñaVacia() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Contraseña vacia");
		alerta.setContentText("No dejes la contraseña vacia");
		alerta.show();
	}
	
	/*
	 * Alerta de contraseña incorrecta
	 */
	
	public static void alertaContraseña() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Contraseña incorrecta");
		alerta.setContentText("No dejes espacios en blanco");
		alerta.show();
	}
	
	/*
	 * Alerta de email vacio
	 */
	
	public static void alertaEmailVacio() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Email vacio");
		alerta.setContentText("No dejes el email vacio");
		alerta.show();
	}
	
	/*
	 * Alerta de email incorrecto
	 */
	
	public static void alertaEmail() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Email invalido");
		alerta.setContentText("Introduzca un email valido");
		alerta.show();
	}
	
	

}
