package es.uva.gui.shadow.api.controller;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import es.uva.gui.shadow.persistence.dto.UserDTO;
import es.uva.gui.shadow.persistence.persistence.ConnectionFail;
import es.uva.gui.shadow.persistence.persistence.PersistenceFacade;
import es.uva.gui.shadow.persistence.persistence.IPersistenceFacade;

/**
 * Controlador de la API correspondiente a las peticiones HTTP acerca de los
 * miembros del Grupo Universitario de Informática de la Universidad de Valladolid.
 * 
 * @author Andrés López
 * @version 0.2
 */

public class MemberController {
    
    private IPersistenceFacade pf;
    
    /**
     * Comprobación de los parámetros de una petición GET para obtener un listado
     * de los miembros que pasen ciertos filtros.
     * 
     * Si los parámetros coinciden con 'all', 'year', 'position' o 'position_year'
     * el valor devuelto es el estado HTTP 200 (OK).
     * 
     * En el caso del tipo parámetro, existen todos los parámetros obligatorios
     * de UserDTO de la librería org.gui.shadow.dto el valor devuelto es el estado
     * HTTP 200 (OK).
     * 
     * Si, al menos, uno de los parámetros no coincide con los permitidos el valor
     * devuelto es el estado HTTP 400 (BAD REQUEST).
     * 
     * @param en parámetros de una petición HTTP
     * @return el estado HTTP 200 (OK) si los parámetros son correctos; el estado
     * HTTP 400 (BAD REQUEST) en caso contrario.
     */
    
    public int checkFilterParam (Enumeration <String> en){
        
        int res = HttpServletResponse.SC_OK;
        String param;
        
        while (en.hasMoreElements()){

            param = en.nextElement();

            if (!param.equals(AllowedParams.PARAM_ALL) ||
                !param.equals(AllowedParams.PARAM_YEAR) ||
                !param.equals(AllowedParams.PARAM_POSITION) ||
                !param.equals(AllowedParams.PARAM_POSITION_YEAR)){

                res = HttpServletResponse.SC_BAD_REQUEST;
                break;

            }
        }
        
        return res;
        
    }
    
    /**
     * Comprobación de los parámetros de una petición POST para añadir un miembro
     * en la asociación.
     * 
     * Si existen todos los parámetros obligatorios de UserDTO de la librería
     * es.uva.gui.shadow.dto (login, contraseña, dni, nombre, apellido y correo
     * electrónico) el valor devuelto es el estado HTTP 200 (OK).
     * 
     * Si, al menos, uno de los parámetros no coincide con los permitidos el valor
     * devuelto es el estado HTTP 400 (BAD REQUEST).
     * 
     * @param values valores de los parámetros de la petición
     * @return el estado HTTP 200 (OK) si los parámetros son correctos; el estado
     * HTTP 400 (BAD REQUEST) en caso contrario.
     */
    
    public int checkFieldParam (Map <String, String[]> values){
    
        return (values.get(AllowedParams.PARAM_LOGIN) == null ||
            values.get(AllowedParams.PARAM_PASSWORD) == null ||
            values.get(AllowedParams.PARAM_DNI) == null ||
            values.get(AllowedParams.PARAM_NAME) == null ||
            values.get(AllowedParams.PARAM_SURNAME) == null ||
            values.get(AllowedParams.PARAM_EMAIL) == null)
            ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST;
        
    }
    
    /**
     * Comprobación de la existencia de un miembro de la asociación por identificador.
     * 
     * @param id identificador de un miembro de la asociación
     * @return se devolverá el estado HTTP 500 (INTERNAL SERVER ERROR) si existe
     * algún fallo con la conexión a la base de datos, el estado HTTP 200 (OK) si
     * el miembro existe o el estado HTTP 400 (BAD REQUEST) en caso contrario.
     */
    
    public int checkId (String id){
        
        pf = getConnection();
        UserDTO member;
        
        try {
            
            member = pf.getUser(id);
            return (member == null) ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST;
        
        } catch (ConnectionFail cf){
        
            System.err.println(cf.getMessage());
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        
        }
    }
    
    /**
     * Obtención de los miembros de la asociación que cumplan los filtros indicados.
     * 
     * @param param filtros para la búsqueda de miembros de la asociación
     * @param en nombres de los filtros para la búsqueda de miembros de la asociación
     * @return un JSONObject convertido a String con los identificadores de los
     * miembros de la asociación que cumplan con los filtros indicados o,
     * en caso de que la conexión a la base de datos falle, el valor devuelto
     * será un String vacío
     */
    
    public String getMemberFiltered (Map <String, String[]> param, Enumeration <String> en){
        
        pf = getConnection();
        
        //try {
            
            // Completar la función
            return null;
            
        /*} catch (ConnectionFail cf){
            
            System.err.println(cf.getMessage());
            return "";
            
        }*/
    }
    
    /**
     * Obtención de un mimebro de la asociación a través de un identificador.
     * 
     * @param id identificador un miembro de la asociación
     * @return un JSONObject convertido a String con los datos correspondientes
     * al miembro de la asociación que coincida con el identificador indicado o,
     * en caso de que la conexión a la base de datos falle, el valor devuelto
     * será un String vacío
     */
    
    public String getMemberById (String id){
        
        pf = getConnection();
        
        try {
            
            UserDTO user = pf.getUser(id);
            return (user != null) ? pf.getUser(id).toJson().toString() : "";
            
        } catch (ConnectionFail cf){
            
            System.err.println(cf.getMessage());
            return "";
            
        }
    }
    
    /**
     * Adición de un nuevo miembro a la asociación.
     * 
     * @param user nuevo usuario
     * @return se delvolverá el estado HTTP 500 (INTERNAL SERVER ERROR) si existe
     * algún fallo con la conexión a la base de datos, el estado HTTP 201 (CREATED)
     * si la adición del miembro transcurre sin problemas o, en caso de que ocurriera
     * un conflicto con los campos del nuevo miebro, se devolverá el estado HTTP
     * 409 (CONFLICT)
     */
    
    public int postMember (UserDTO user){
        
        pf = getConnection();
        
        //if (pf.checkUnique(user.getLogin(), user.getDni(), user.getEmail())){
            
            try {
                
                pf.createUser(user);
                return HttpServletResponse.SC_CREATED;
                
            } catch (ConnectionFail cf){
                
                System.err.println(cf.getMessage());
                return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                
            }
            
        //} else return HttpServletResponse.SC_CONFLICT;
        
    }
    
    /**
     * Borrado de un miembro de la asociación a través de un identificador.
     * 
     * @param id identificador del miembro
     * @return se delvolverá el estado HTTP 500 (INTERNAL SERVER ERROR) si existe
     * algún fallo con la conexión a la base de datos, el estado HTTP 400 (BAD
     * REQUEST) si no existe un miembro en la asociación con dicho identificador
     * o, en caso contrario, se devolverá el estado HTTP 200 (OK)
     */
    
    public int deleteMember (String id){
        
        if (checkId(id) == HttpServletResponse.SC_OK){
            
            pf = getConnection();
            
            try {
                
                pf.deleteUser(id);
                return HttpServletResponse.SC_OK;
                
            } catch (ConnectionFail cf){
                
                System.err.println(cf.getMessage());
                return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                
            }
            
        } else return HttpServletResponse.SC_BAD_REQUEST;
        
    }
    
    /**
     * Actualización de un miembro de la asociación a través de un identificador.
     * 
     * @param id identificador del miembro de la asociación
     * @param param valor de los campos a actualizar
     * @param en nombre de los campos a actualizar
     * @return se delvolverá el estado HTTP 500 (INTERNAL SERVER ERROR) si existe
     * algún fallo con la conexión a la base de datos, el estado HTTP 201 (CREATED)
     * si la actualización del miembro transcurre sin problemas o, en caso de que
     * ocurriera un conflicto con los campos del nuevo miebro, se devolverá el
     * estado HTTP 409 (CONFLICT)
     */
    
    public int putMember (String id, Map <String, String[]> param, Enumeration <String> en){
        
        // Completar la función
        return 0;
        
    }
    
    /**
     * Devuelve la conexión a la base de datos. Sigue el patrón singleton, por lo
     * que si el recurso ya está creado devuelve la conexíon y no crea una nueva.
     * 
     * @return la conexión a la base de datos
     */
    
    private PersistenceFacade getConnection (){
        
        if (pf == null){
            
            pf = new PersistenceFacade ("database.db");
            pf.open();
            
        }
        
        return (PersistenceFacade) pf;
        
    }
    
}
