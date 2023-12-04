/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

// Classe Util  para gerencicar caracteres especiais.
public abstract class Util {
    public static String decode(String text){
        try {
            return java.net.URLDecoder.decode(java.net.URLEncoder.encode(text,"iso-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    public static Date stringToDate(String dataString){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // Define o formato da data esperado
        Date data = null;

        try {
            data = df.parse(dataString); // Converte a string para um objeto Date
        } catch (ParseException e) {
            // Lida com a exceção caso a conversão falhe
            e.printStackTrace(); // Ou faça algo mais apropriado, como retornar um erro para o usuário
        }
        return data;
    }
    
    public static String dateToString(Date pdata){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // Define o formato da data esperado
        String data = "";
        data = df.format(pdata); // Converte a string para um objeto Date
        return data;
    }
}
