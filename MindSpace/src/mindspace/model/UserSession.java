/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mindspace.model;

public class UserSession {

    private static String userId;
    private static String username;

    public static void start(String id, String name) {
        userId = id;
        username = name;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void clear() {
        userId = null;
        username = null;
    }

    public static boolean isLoggedIn() {
        return userId != null;
    }
}
