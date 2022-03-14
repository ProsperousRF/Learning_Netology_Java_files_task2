import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Stanislav Rakitov in 2022
 */
public class Main {
  private static final String FILE_PATH_PREFIX = "H:\\Games\\savegames\\";

  public static void main(String[] args) {
    // Создать три экземпляра класса GameProgress.
    GameProgress gp1 = new GameProgress(1, 1, 1, 1.1);
    GameProgress gp2 = new GameProgress(2, 2, 3, 2.2);
    GameProgress gp3 = new GameProgress(3, 3, 3, 3.3);

    // Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.


    var gp1file = "gp1.dat";
    var gp2file = "gp2.dat";
    var gp3file = "gp3.dat";

    saveProgress(gp1, FILE_PATH_PREFIX + gp1file);
    saveProgress(gp2, FILE_PATH_PREFIX + gp2file);
    saveProgress(gp3, FILE_PATH_PREFIX + gp3file);

    // Созданные файлы сохранений из папки savegames запаковать в архив zip.
    zipFiles(FILE_PATH_PREFIX + "gameprogress.zip");
  }

  private static void zipFiles(String zipFile) {
    File path = new File(zipFile);
    // Get folder path
    File folder = new File(path.getParent());
    // Get all files in folder before any zipping
    File[] files = folder.listFiles();

    try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
      for (File file : files) {
        if (file.isFile() && !file.getName().endsWith(".zip")) {
          FileInputStream fis = new FileInputStream(file);
          ZipEntry entry = new ZipEntry(file.getName());
          zipOut.putNextEntry(entry);
          byte[] buffer = new byte[fis.available()];
          fis.read(buffer);
          zipOut.write(buffer);
          zipOut.closeEntry();
          fis.close();

          // Удалить файлы сохранений, лежащие вне архива.
          file.delete();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void saveProgress(GameProgress gameProgress, String filePath) {
    try (FileOutputStream fos = new FileOutputStream(filePath);
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(gameProgress);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
