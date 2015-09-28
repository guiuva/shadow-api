package es.uva.gui.shadow.api.servlet;

import es.uva.gui.shadow.api.controller.AllowedParams;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import es.uva.gui.shadow.api.controller.MemberController;
import es.uva.gui.shadow.auth.MemberAuthentification;
import es.uva.gui.shadow.persistence.dto.UserDTO;
import java.util.Date;
import java.util.Map;

/**
 * Servlet manejador de las peticiones HTTP contra la API de los miebros del Grupo
 * Universitario de Informática de la Universidad de Valladolid.
 * 
 * @author Andrés López
 * @version 0.2
 */

@WebServlet(name = "MemberServlet", urlPatterns = {"/member/*"})
public class MemberServlet extends HttpServlet {

    private MemberController mc;
    
    /**
     * Procesa una petición GET en función a la ruta introducida. Existen dos tipos:
     *  
     * 1. si la ruta llega hasta '/member' se aplica un filtro con los parámetros
     * de la petición y se devuelven los miembros que concuerden con dicho
     * filtro. Los posibles parámetros son: all, year, position y position_year.
     * 
     * 2. si la ruta llega hasta '/member/:id' se devolverá el miembro con el
     * id introducido.
     * 
     * En el resto de casos se devolverá un error HTTP 404 (NOT FOUND).
     * 
     * Si los argumentos de la petición no coinciden con los permitidos se devolverá
     * un error HTTP 400 (BAD REQUEST).
     * 
     * Esta operación necesita una autenticación previa en el servidor y unos privilegios
     * especiales para llevarse a cabo. En caso de no disponer de ellos, la petición
     * devolverá un error HTTP 401 (UNAUTHORIZED).
     * 
     * Si existe un error al establecer la conexión con la base de datos se devolverá
     * un error HTTP 500 (INTERNAL SERVER ERROR).
     * 
     * Si la petición es procesada correctamente se devolverá el estado HTTP 200
     * (OK).
     *
     * @param request petición HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error específico del Servlet
     * @throws IOException si ocurre un error específico al escribir una respuesta
     * a la petición recibida
     */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        // Hay que definir el parámetro de auth y el código de acciones, o plantearlo
        // de otra forma
        if (MemberAuthentification.checkAuth(null, 0) == HttpServletResponse.SC_OK){
            
            String args [] = getArgs(request.getPathInfo());
            mc = new MemberController();

            if (args == null){

                int res = mc.checkFilterParam(request.getParameterNames());

                if (res == HttpServletResponse.SC_BAD_REQUEST)
                    response.sendError(res);

                else {

                    String message = mc.getMemberFiltered(request.getParameterMap(),
                        request.getParameterNames());
                    
                    if (!message.equals("")){
                        
                        response.getWriter().print(message);
                        response.setStatus(HttpServletResponse.SC_OK);
                    
                    } else response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            } else if (args.length == 1){

                int res = mc.checkId(args[0]);

                if (res == HttpServletResponse.SC_BAD_REQUEST)
                    response.sendError(res);

                else {

                    String message = mc.getMemberById(args[0]);
                    
                    if (!message.equals("")){
                    
                        response.getWriter().print(message);
                        response.setStatus(HttpServletResponse.SC_OK);

                    } else response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            } else response.sendError(HttpServletResponse.SC_NOT_FOUND);
        
        } else response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        
    }

    /**
     * Procesa una petición POST en función a la ruta introducida. Si la ruta llega
     * hasta '/member' se procederá al registro de un nuevo miembro de la asociación
     * con los datos introducidos como parámetros en la petición.
     * 
     * Los parámetros obligatorios son: login, password, dni, name, surname y email.
     * 
     * Los parámetros opcionales son: phone, membership, study, <otros-campos>.
     * 
     * Los tipos de los campos se corresponden con los tipos de los objetos UserDTO
     * de la librería org.gui.shadow.dto, mientras que <otros-campos> conformarán
     * un JSON de campos adicionales.
     * 
     * En el resto de casos se devolverá un error HTTP 404 (NOT FOUND).
     * 
     * Si los parámetros de la petición no coinciden con los permitidos se devolverá
     * un error HTTP 400 (BAD REQUEST).
     * 
     * Sin embargo, si al añadir el nuevo miembro existe algún tipo de
     * coincidencia entre campos primarios, se devolverá un error HTTP 409 (CONFLICT).
     * 
     * Esta operación necesita una autenticación previa en el servidor y unos privilegios
     * especiales para llevarse a cabo. En caso de no disponer de ellos, la petición
     * devolverá un error HTTP 401 (UNAUTHORIZED).
     * 
     * Si existe un error al establecer la conexión con la base de datos se devolverá
     * un error HTTP 500 (INTERNAL SERVER ERROR).
     * 
     * Si la petición es procesada correctamente se devolverá el estado HTTP 201
     * (CREATED).
     *
     * @param request petición HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error específico del Servlet
     * @throws IOException si ocurre un error específico al escribir una respuesta
     * a la petición recibida
     */
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        // Hay que definir el parámetro de auth y el código de acciones, o plantearlo
        // de otra forma
        if (MemberAuthentification.checkAuth(null, 0) == HttpServletResponse.SC_OK){
            
            String args [] = getArgs(request.getPathInfo());
            mc = new MemberController();

            if (args == null) {

                if (mc.checkFieldParam(request.getParameterMap())
                    != HttpServletResponse.SC_BAD_REQUEST){

                    UserDTO newUser = paramsToUserDTO(request.getParameterMap());
                    int res = mc.postMember(newUser);

                    if (res == HttpServletResponse.SC_CONFLICT ||
                        res == HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                        response.sendError(res);
                    else response.setStatus(res);

                }else response.sendError(HttpServletResponse.SC_BAD_REQUEST);

            } else response.sendError(HttpServletResponse.SC_NOT_FOUND);
        
        } else response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        
    }
    
    /**
     * Procesa una petición DELETE en función a la ruta introducida. Si la ruta
     * llega hasta '/member/:id' se procederá al borrado del miembro de la asociación
     * cuyo id se corresponda con el introducido como argumento en la ruta.
     * 
     * En el resto de casos se devolverá un error HTTP 404 (NOT FOUND).
     * 
     * Si no se encuentra un miembro con dicho identificador se devolverá un error
     * HTTP 400 (BAD REQUEST).
     * 
     * Esta operación necesita una autenticación previa en el servidor y unos privilegios
     * especiales para llevarse a cabo. En caso de no disponer de ellos, la petición
     * devolverá un error HTTP 401 (UNAUTHORIZED).
     * 
     * Si existe un error al establecer la conexión con la base de datos se devolverá
     * un error HTTP 500 (INTERNAL SERVER ERROR).
     * 
     * Si la petición es procesada correctamente se devolverá el estado HTTP 200
     * (OK).
     * 
     * @param request petición HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error específico del Servlet
     * @throws IOException si ocurre un error específico al escribir una respuesta
     * a la petición recibida
     */
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        // Hay que definir el parámetro de auth y el código de acciones, o plantearlo
        // de otra forma
        if (MemberAuthentification.checkAuth(null, 0) == HttpServletResponse.SC_OK){
        
            String args [] = getArgs(request.getPathInfo());
            mc = new MemberController();

            if (args != null && args.length == 1) {

                int res = mc.deleteMember(args[0]);

                if (res == HttpServletResponse.SC_BAD_REQUEST ||
                    res == HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    response.sendError(res);
                else response.setStatus(res);

            } else response.sendError(HttpServletResponse.SC_NOT_FOUND);
            
        } else response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        
    }
    
    /**
     * Procesa una petición PUT en función a la ruta introducida. Si la ruta llega
     * hasta '/member/:id' se procederá a la actualización del miembro de la asociación
     * cuyo identificador coincida con el introducido en la ruta.
     * 
     * En el resto de casos se devolverá un error HTTP 404 (NOT FOUND).
     * 
     * Si no se encuentra un miembro con dicho identificador o los parámetros no
     * coinciden con los permitidos se devolverá un error HTTP 400 (BAD REQUEST).
     * 
     * Esta operación necesita una autenticación previa en el servidor y unos privilegios
     * especiales para llevarse a cabo. En caso de no disponer de ellos, la petición
     * devolverá un error HTTP 401 (UNAUTHORIZED).
     * 
     * Si existe un error al establecer la conexión con la base de datos se devolverá
     * un error HTTP 500 (INTERNAL SERVER ERROR).
     * 
     * Si la petición es procesada correctamente se devolverá el estado HTTP 201
     * (CREATED).
     * 
     * @param request petición HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error específico del Servlet
     * @throws IOException si ocurre un error específico al escribir una respuesta
     * a la petición recibida 
     */
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        // Hay que definir el parámetro de auth y el código de acciones, o plantearlo
        // de otra forma
        if (MemberAuthentification.checkAuth(null, 0) == HttpServletResponse.SC_OK){
        
            String args [] = getArgs(request.getPathInfo());
            mc = new MemberController();

            if (args != null && args.length == 1) {

                if (mc.checkId(args[0]) != HttpServletResponse.SC_BAD_REQUEST){

                    int res = mc.putMember(args[0],
                        request.getParameterMap(),
                        request.getParameterNames());

                    if (res == HttpServletResponse.SC_CONFLICT ||
                        res == HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                        response.sendError(res);
                    else response.setStatus(res);

                } else response.sendError(HttpServletResponse.SC_BAD_REQUEST);

            } else response.sendError(HttpServletResponse.SC_NOT_FOUND);
            
        } else response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        
    }
    
    /**
     * Procesa una ruta de una petición HTTP y obtiene los argumentos de la misma.
     * Si no existen argumentos a mayores el valor devuelto es null, en caso contrario
     * es un array de String con los argumentos de la ruta.
     * 
     * @param path la ruta introducida por el cliente que recibe el servlet
     * @return un array con los argumentos de la ruta introducida
     */
    
    private String [] getArgs (String path){
        
        if (path != null) {
            
            String res [] = path.substring(1, path.length()).split("/");
            return (res.length == 1 && res[0].isEmpty()) ? null : res;
            
        } return null;
    }
    
    private UserDTO paramsToUserDTO (Map <String, String[]> values){
        
        UserDTO user = new UserDTO(values.get(AllowedParams.PARAM_LOGIN)[0]);
        String aux [];
        
        user.setPassword(values.get(AllowedParams.PARAM_PASSWORD)[0]);
        user.setDni(values.get(AllowedParams.PARAM_DNI)[0]);
        user.setName(values.get(AllowedParams.PARAM_NAME)[0]);
        user.setSurname(values.get(AllowedParams.PARAM_SURNAME)[0]);
        user.setEmail(values.get(AllowedParams.PARAM_EMAIL)[0]);
        
        aux = values.get(AllowedParams.PARAM_PHONE);
        if (aux != null) user.setPhone(aux[0]);
        
        aux = values.get(AllowedParams.PARAM_STUDY);
        if (aux != null) user.setStudy(aux[0]);
        
        aux = values.get(AllowedParams.PARAM_MEMBERSHIP);
        if (aux != null) user.setMembership(new Date(aux[0]));
        
        return user;
        
    }
}
