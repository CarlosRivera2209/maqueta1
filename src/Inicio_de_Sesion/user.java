package Inicio_de_Sesion;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public class user {
    private String username;
    private String password;
    private File userDirectory;
    private File userInfoFile;

    public user(String username, String password) {
        this.password = password;
        this.username = username;
        this.userDirectory = new File("D:\\admin\\" + username);
        this.userInfoFile = new File(userDirectory, "user_info.txt");
        initializeUserDirectory();
    }

    // Método que se usa solo cuando se crea un nuevo usuario
    public user(String username, String password, boolean isNewUser) {
        this.username = username;
        this.password = password;
        this.userDirectory = new File("D:\\admin\\" + username);
        this.userInfoFile = new File(userDirectory, "user_info.txt");
        initializeUserDirectory();

        // Crear archivo de usuario si es un usuario nuevo
        if (isNewUser) {
            createUserInfoFile();
        }
    }

    private void initializeUserDirectory() {
        // Crear la carpeta del usuario dentro de admin si no existe
        if (!userDirectory.exists()) {
            userDirectory.mkdirs(); // Crear la carpeta del usuario y sus carpetas padre si no existen
            new File(userDirectory, "Mis Documentos").mkdir();
            new File(userDirectory, "Música").mkdir();
            new File(userDirectory, "Mis Imágenes").mkdir();
        }
    }

    private void createUserInfoFile() {
        // Crear el archivo user_info.txt con la información del usuario
        try (FileWriter writer = new FileWriter(userInfoFile)) {
            writer.write("Usuario: " + username + "\n");
            writer.write("Contraseña: " + password + "\n");
            writer.flush();
            System.out.println("Archivo de información de usuario creado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al crear el archivo de usuario: " + e.getMessage());
        }
    }

    // Método para autenticar leyendo los datos desde el archivo user_info.txt
    public boolean authenticate(String username, String password) {
        if (userInfoFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(userInfoFile))) {
                String line;
                String fileUsername = null;
                String filePassword = null;

                // Leer el archivo línea por línea
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Usuario: ")) {
                        fileUsername = line.substring(9).trim();
                    } else if (line.startsWith("Contraseña: ")) {
                        filePassword = line.substring(12).trim();
                    }
                }

                // Verificar las credenciales
                return username.equals(fileUsername) && password.equals(filePassword);
            } catch (IOException e) {
                System.out.println("Error al leer el archivo de usuario: " + e.getMessage());
            }
        }
        return false;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public File getUserDirectory() {
        return userDirectory;
    }
}
