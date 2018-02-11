/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

/**
 *
 * @author Geovanny Cudco
 */
public class InterfazMenu {

    public static String obtenerHomeInicio(int idRol) {
        String url;
        System.out.println("Rol a buscar: " + idRol);
        switch (idRol) {
            case 1:
                url = "privado/master/home.jsf";
                break;
            case 2:
                url = "privado/socialGroup/home.jsf";
                break;
            case 4:
                url = "privado/socialGroup/home.jsf";
                break;

            case 6:
                url = "privado/invitado/home.jsf";
                break;
            case 7:
                url = "privado/lider/home.jsf";
                break;
            case 9:
                url = "privado/diseniador/home.jsf";
                break;
            default:
                url = "privado/home.jsf";
                break;
        }

        System.out.println("URL obtenida " + url);
        return url;
    }

    public static String obtenerInicioRol(int idRol) {
        String url;
        System.out.println("Rol a buscar: " + idRol);
        switch (idRol) {
            case 1:
                url = "master/home.jsf";
                break;
            case 2:
                url = "socialGroup/home.jsf";
                break;
            case 4:
                url = "socialGroup/home.jsf";
                break;

            case 6:
                url = "invitado/home.jsf";
                break;
            case 7:
                url = "lider/home.jsf";
                break;
            case 9:
                url = "diseniador/home.jsf";
                break;
            default:
                url = "home.jsf";
                break;
        }

        System.out.println("URL obtenida " + url);
        return url;
    }
}
