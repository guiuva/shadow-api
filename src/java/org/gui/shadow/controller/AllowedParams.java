package org.gui.shadow.controller;

/**
 * Colección de parámetros permitidos en las peticiones HTTP contra la API de los
 * miembros del Grupo Universitario de Informática de la Universidad de Valladolid.
 * 
 * @author Andrés López
 * @version 0.1
 */

public abstract class AllowedParams {
    
    public final static int PARAM_FILTER = 0;
    public final static int PARAM_FIELD = 1;
    
    public final static String PARAM_ALL = "all";
    public final static String PARAM_YEAR = "year";
    public final static String PARAM_POSITION = "position";
    public final static String PARAM_POSITION_YEAR = "position_year";
    
    public final static String PARAM_LOGIN = "login";
    public final static String PARAM_PASSWORD = "password";
    public final static String PARAM_DNI = "dni";
    public final static String PARAM_NAME = "name";
    public final static String PARAM_SURNAME = "surname";
    public final static String PARAM_EMAIL = "email";
    public final static String PARAM_PHONE = "phone";
    public final static String PARAM_MEMBERSHIP = "membership";
    public final static String PARAM_STUDY = "study";
    
}
