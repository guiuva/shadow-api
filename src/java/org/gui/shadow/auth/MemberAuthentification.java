package org.gui.shadow.auth;

/**
 * Autentificador de las peticiones HTTP contra la API del Grupo Universitario
 * de Informática de la Universidad de Valladolid.
 * 
 * Es necesario completar el protocolo de autenticación o sustituirlo por las
 * librerías pertinentes.
 * 
 * @author Andrés López
 * @version 0.1
 */

public class MemberAuthentification {
    
    /**
     * Autentifica un cliente en el servidor para realizar un tipo de accion en
     * concreto.
     * 
     * @param token código del cliente que desea realizar la acción
     * @param action código de la acción que el cliente desea realizar
     * @return si el cliente puede realizar la acción se devolverá el valor HTTP
     * 200 (OK); en caso contrario se devolverá el valor HTTP 401 (UNAuTHORIZED)
     */
    
    public static int checkAuth (String token, int action){
        
        // Completar la función o utilizar las librerías pertinentes
        return 0;
        
    }
    
}
