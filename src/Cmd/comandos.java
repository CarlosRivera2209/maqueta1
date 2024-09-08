package Cmd;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class comandos {

    private File dirActual;

    public comandos(String dirInicial) {
        dirActual = new File(dirInicial);
    }

    public String Mkdir(String direccion) {
        File carpeta = new File(dirActual, direccion);
        if (carpeta.exists()) {
            return "Error: Esta carpeta ya existe.";
        } else {
            if (carpeta.mkdir()) {
                return "Carpeta creada existosamente.";
            } else {
                return "Error: No se pudo crear la carpeta.";
            }
        }
    }

    public String Mfile(String path) {
        File archivo = new File(dirActual, path);
        if (archivo.exists()) {
            return "Error: Este archivo ya existe.";
        } else {
            try {
                if (archivo.createNewFile()) {
                    return "Archivo creado con éxito.";
                } else {
                    return "Error: No se pudo crear el archivo.";
                }
            } catch (IOException e) {
                return "Error: Ocurrió un problema al crear el archivo.";
            }
        }
    }

    private void vaciarCarpeta(File aVaciar) {
        if (aVaciar.isDirectory()) {
            for (File vaciando : aVaciar.listFiles()) {
                vaciando.delete();
            }
        }
    }

    public String Rm(File aBorrar) {
        if (aBorrar.isDirectory()) {
            for (File borrando : aBorrar.listFiles()) {
                if (borrando.isDirectory()) {
                    vaciarCarpeta(borrando);
                    borrando.delete();
                } else {
                    borrando.delete();
                }
            }
            aBorrar.delete();
            return "Carpeta eliminada.";
        } else if (aBorrar.isFile()) {
            aBorrar.delete();
            return "Archivo eliminado.";
        }
        return "Error: La carpeta o archivo que desea eliminar no existe.";
    }

    public String Cd(String direccion) {
        if ("...".equals(direccion)) {
            dirActual = dirActual.getParentFile();
            if (dirActual != null) {
                return "Directorio cambiado a: " + dirActual.getAbsolutePath();
            } else {
                return "Error: No se puede regresar más atrás.";
            }
        } else {
            File nuevaDir = new File(dirActual, direccion);
            if (!nuevaDir.exists() || !nuevaDir.isDirectory()) {
                return "Error: La dirección ingresada debe ser una carpeta válida.";
            }
            dirActual = nuevaDir;
            return "Directorio cambiado a: " + nuevaDir.getAbsolutePath();
        }
    }


    public String getCurrentPath() {
        try {
            return dirActual.getCanonicalPath();
        } catch (IOException e) {
            return "Error al obtener la ruta: " + e.getMessage();
        }
    }

    public String wr(String mensaje, String direccion) {
        File archivo = new File(dirActual, direccion);
        if (archivo.exists()) {
            if (archivo.isFile()) {
                try (FileWriter escribir = new FileWriter(archivo, true); BufferedWriter bw = new BufferedWriter(escribir)) {
                    bw.write(mensaje);
                    bw.newLine();
                    return "Se escribió correctamente.";
                } catch (IOException e) {
                    return "Error: No se pudo escribir en el archivo.";
                }
            } else {
                return "Error: La ruta especificada no es un archivo.";
            }
        } else {
            return "Error: Este archivo no existe.";
        }
    }

    public String rd(String direccion) {
        File archivo = new File(dirActual, direccion);
        if (archivo.exists()) {
            if (archivo.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                    StringBuilder contenido = new StringBuilder();
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        contenido.append(linea).append("\n");
                    }
                    return contenido.toString();
                } catch (IOException e) {
                    return "Error: No se puede leer el archivo.";
                }
            } else {
                return "Error: Debe seleccionar un archivo para leerlo.";
            }
        } else {
            return "Error: Este archivo no existe.";
        }
    }

    public String Dir() {
        if (dirActual.isDirectory()) {
            StringBuilder lista = new StringBuilder("Contenido del directorio actual:\n");
            for (File archivo : dirActual.listFiles()) {
                if (archivo.isDirectory()) {
                    lista.append("[Carpeta] ");
                } else {
                    lista.append("[Archivo] ");
                }
                lista.append(archivo.getName()).append("\n");
            }
            return lista.toString();
        } else {
            return "Error: El directorio actual no es válido.";
        }
    }

    public String date() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        return "Fecha actual: " + formatoFecha.format(new Date());
    }

    public String time() {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        return "Hora actual: " + formatoHora.format(new Date());
    }

    public void escribir(String contenido) {
        if (dirActual == null || !dirActual.exists()) {
            JOptionPane.showMessageDialog(null, "Error: El archivo no existe.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dirActual, true))) {
            bw.write(contenido);
            bw.newLine();
            JOptionPane.showMessageDialog(null, "Se ha escrito en el archivo.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public void leer() {
        if (dirActual == null || !dirActual.exists()) {
            JOptionPane.showMessageDialog(null, "Error: El archivo no existe.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(dirActual))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
        }
    }
}
