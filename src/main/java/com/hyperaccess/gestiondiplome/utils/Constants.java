package com.hyperaccess.gestiondiplome.utils;

public class Constants {
    public static final String APP_ROOT = "gestion-diplome/v1";
    public final static  String CREATE_UTILISATEUR_ENDPOINT = APP_ROOT + "/utilisateurs/save";
    public static final String CREATE_UTILISATEUR_PHOTO_ENDPOINT = APP_ROOT + "/utilisateurs/create";
    public static final String UPDATE_UTILISATEUR_ENDPOINT = APP_ROOT+ "/utilisateurs/update/{id}";
    public static final String ACTIVE_UTILISATEUR_ENDPOINT =  APP_ROOT+ "/utilisateurs/active/{id}";
    public static final String DESACTIVE_UTILISATEUR_ENDPOINT =  APP_ROOT+ "/utilisateurs/desactive/{id}";
    public final static  String FIND_BY_ID_UTILISATEUR_ENDPOINT = APP_ROOT+ "/utilisateurs/by-id/{id}";
    public final static  String FIND_BY_NOM_UTILISATEUR_ENDPOINT = APP_ROOT+ "/utilisateurs/by-nom/{nom}";
    public final static  String FIND_ALL_UTILISATEUR_ENDPOINT = APP_ROOT+ "/utilisateurs/all";
    public static final String OBTENIR_PHOTO_BY_ID_UTILISATEUR_ENDPOINT = APP_ROOT+ "/utilisateurs/photo/{id}";
    public final static  String DELETE_BY_ID_UTILISATEUR_ENDPOINT = APP_ROOT+ "/utilisateurs/delete/{id}";

    public static final String CREATE_ROLE_ENDPOINT = APP_ROOT + "/roles/create";
    public static final String UPDATE_ROLE_ENDPOINT = APP_ROOT + "/roles/update/{id}";
    public final static  String FIND_BY_ID_ROLE_ENDPOINT = APP_ROOT+ "/roles/by-id/{id}";
    public final static  String FIND_BY_ROLENAME_ROLE_ENDPOINT = APP_ROOT+ "/roles/by-nom/{role}";
    public final static  String FIND_ALL_ROLE_ENDPOINT = APP_ROOT+ "/roles/all";
    public final static  String DELETE_BY_ID_ROLE_ENDPOINT = APP_ROOT+ "/roles/delete/{id}";

    public static final String CREATE_MINISTRE_ENDPOINT = APP_ROOT + "/ministres/create";
    public static final String UPDATE_MINISTRE_ENDPOINT = APP_ROOT + "/ministres/update/{id}";
    public static final String EXPORT_MINISTRE_ENDPOINT = APP_ROOT + "/ministres/export";
    public final static  String FIND_BY_ID_MINISTRE_ENDPOINT = APP_ROOT+ "/ministres/{id}";
    public final static  String FIND_ALL_MINISTRE_ENDPOINT = APP_ROOT+ "/ministres/all";
    public final static  String DELETE_BY_ID_MINISTRE_ENDPOINT = APP_ROOT+ "/ministres/delete/{id}";



    public static final String CREATE_DIPLOME_ENDPOINT = APP_ROOT + "/diplomes/create";
    public static final String UPDATE_DIPLOME_ENDPOINT = APP_ROOT + "/diplomes/update/{id}";
    public static final String FIND_BY_NUMERO_ENREG_DIPLOME_ENDPOINT = APP_ROOT + "/diplomes/by-numero/{numeroEnreg}";
    public static final String EXPORT_EXCEL_DIPLOME_ENDPOINT = APP_ROOT + "/diplomes/export/excel";
    public static final String EXPORT_PDF_DIPLOME_ENDPOINT = APP_ROOT + "/diplomes/export/pdf/{numeroEnreg}";
    public static final String FIND_BY_ID_DIPLOME_ENDPOINT =APP_ROOT+ "/diplomes/{id}";
    public static final String FIND_ALL_DIPLOME_ENDPOINT = APP_ROOT+ "/diplomes/all";
    public static final String DELETE_BY_ID_DIPLOME_ENDPOINT = APP_ROOT+ "/diplomes/delete/{id}";


}
