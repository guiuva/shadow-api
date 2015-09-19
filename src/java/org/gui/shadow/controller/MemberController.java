package org.gui.shadow.controller;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.gui.shadow.dto.UserDTO;

/**
 * Controlador de la API correspondiente a las peticiones HTTP acerca de los
 * miembros del Grupo Universitario de Informática de la Universidad de Valladolid.
 * 
 * @author Andrés López
 * @version 0.1
 */

public abstract class MemberController {
    
    /**
     * Comprobación de los parámetros de una petición GET. Existen dos tipos de
     * comprobaciones: tipo filtro o tipo parámetro.
     * 
     * En el caso del tipo filtro, si los parámetros coinciden con 'all', 'year',
     * 'position' o 'position_year' el valor devuelto es el estado HTTP 200 (OK).
     * 
     * En el caso del tipo parámetro, existen todos los parámetros obligatorios
     * de UserDTO de la librería org.gui.shadow.dto el valor devuelto es el estado
     * HTTP 200 (OK).
     * 
     * En cambos casos, si, al menos, uno de los parámetros no coincide con los
     * permitidos el valor devuelto es el estado HTTP 400 (BAD REQUEST).
     * 
     * @param type tipo de parámetro a comprobar
     * @param en parámetros de una petición HTTP
     * @return el estado HTTP 200 (OK) si los parámetros son correctos; el estado
     * HTTP 400 (BAD REQUEST) en caso contrario.
     */
    
    public static int checkParam (int type, Enumeration <String> en){
        
        int res = HttpServletResponse.SC_OK;
        String param;
        
        if (type == AllowedParams.PARAM_FILTER){
        
            while (en.hasMoreElements()){

                param = en.nextElement();

                if (param.equals(AllowedParams.PARAM_ALL) ||
                    param.equals(AllowedParams.PARAM_YEAR) ||
                    param.equals(AllowedParams.PARAM_POSITION) ||
                    param.equals(AllowedParams.PARAM_POSITION_YEAR)){

                    res = HttpServletResponse.SC_BAD_REQUEST;
                    break;

                }
            }
        } else {
            
            // Completar la función
            
        }
        
        return res;
        
    }
    
    /**
     * Comprobación de la existencia de un miembro de la asociación por identificador.
     * 
     * Si el miembro existe, el valor devuelto es el estado HTTP 200 (OK); en caso
     * contrario el valor devuelto el estado HTTP 400 (BAD REQUEST).
     * 
     * @param id identificador de un miembro de la asociación
     * @return el estado HTTP 200 (OK) si el miembro existe; el estado HTTP 400 
     * (BAD REQUEST) en caso contrario.
     */
    
    public static int checkId (String id){
        
        // Completar la función
        return 0;
        
    }
    
    /**
     * Obtención de los miembros de la asociación que cumplan los filtros indicados.
     * 
     * @param param filtros para la búsqueda de miembros de la asociación
     * @param en nombres de los filtros para la búsqueda de miembros de la asociación
     * @return un JSONObject convertido a String con los identificadores de los
     * miembros de la asociación que cumplan con los filtros indicados
     */
    
    public static String getMemberFiltered (Map <String, String[]> param, Enumeration <String> en){
        
        // Completar la función.
        return null;
        
    }
    
    /**
     * Obtención de un mimebro de la asociación a través de un identificador.
     * 
     * @param id identificador un miembro de la asociación
     * @return un JSONObject convertido a String con los datos correspondientes
     * al miembro de la asociación que coincida con el identificador indicado
     */
    
    public static String getMemberById (String id){
        
        // Completar la función
        return null;
        
    }
    
    /**
     * Adición de un nuevo miembro a la asociación.
     * 
     * @param user nuevo usuario
     * @return se delvolverá el estado HTTP 200 (OK) si la adición del miembro
     * transcurre sin problemas; en caso de que ocurriera un conflicto con los campos del
     * nuevo miebro se devolverá el estado HTTP 409 (CONFLICT)
     */
    
    public static int postMember (UserDTO user){
        
        // Completar la función
        return 0;
        
    }
    
    /**
     * Borrado de un miembro de la asociación a través de un identificador.
     * 
     * @param id identificador del miembro
     * @return se delvolverá el estado HTTP 400 (BAD REQUEST) si no existe un
     * miembro en la asociación con dicho identificador; en caso contrario se
     * devolverá el estado HTTP 200 (OK)
     */
    
    public static int deleteMember (String id){
        
        if (MemberController.checkId(id) == HttpServletResponse.SC_OK){
            
            // Completar la función
            return HttpServletResponse.SC_OK;
            
        } else return HttpServletResponse.SC_BAD_REQUEST;
        
    }
    
    /**
     * Actualización de un miembro de la asociación a través de un identificador.
     * 
     * @param id identificador del miembro de la asociación
     * @param param valor de los campos a actualizar
     * @param en nombre de los campos a actualizar
     * @return se delvolverá el estado HTTP 200 (OK) si la actualización del miembro
     * transcurre sin problemas; en caso de que ocurriera un conflicto con los campos del
     * nuevo miebro se devolverá el estado HTTP 409 (CONFLICT)
     */
    
    public static int putMember (String id, Map <String, String[]> param, Enumeration <String> en){
        
        // Completar la función
        return 0;
        
    }
    
}
