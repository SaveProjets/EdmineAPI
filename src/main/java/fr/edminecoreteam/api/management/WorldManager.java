package fr.edminecoreteam.api.management;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WorldManager {

    /**
     * Get un nom de dossier aléatoire dans le dossier parent spécifié.
     *
     * @param parentFolderPath le chemin du dossier parent
     * @return un nom de dossier aléatoire dans le dossier parent spécifié
     */
    public String getRandomSubfolderName(String parentFolderPath) {
        File parentFolder = new File(parentFolderPath);
        if (!parentFolder.exists() || !parentFolder.isDirectory()) {
            return null;
        }

        File[] subFolders = parentFolder.listFiles(File::isDirectory);
        if (subFolders == null || subFolders.length == 0) {
            return null;
        }

        int randomIndex = new Random().nextInt(subFolders.length);
        return subFolders[randomIndex].getName();
    }

    /**
     * Crée un monde de jeu à partir d'un modèle.
     *
     * @param world le nom du monde
     */
    public void createGameWorld(String world) {
        File srcDir = new File("gameTemplate/" + world);
        File destDir = new File("game");
        try {
            FileUtils.copyDirectory(srcDir, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("game/uid.dat");
        file.delete();
        Bukkit.getServer().createWorld(new WorldCreator("game"));
    }

    /**
     * Décharge un monde.
     *
     * @param world Le monde à décharger
     */
    public void unloadWorld(World world) {
        if (world != null) {
            Bukkit.getServer().unloadWorld(world, true);
        }
    }

    /**
     * Supprime un monde.
     *
     * @param path Le chemin du monde à supprimer
     * @return
     */
    public boolean deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    /**
     * Copie un monde.
     *
     * @param source Le monde source
     * @param target Le monde cible
     */
    public void copyWorld(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists())
                        target.mkdirs();
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
