package ccm.deathTimer.utils;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;

import java.io.File;

/**
 * Use this class to save per-server data.
 *
 * @author Dries007
 */
public final class DataHelper
{
    private static File root;

    /**
     * Remove a data file.
     *
     * @param modID    The mod in "control" of the file
     * @param fileName Do not add an extension
     * @return true if
     */
    public static boolean deleteFile(String modID, final String fileName)
    {
        try
        {
            final File folder = DataHelper.getModFolder(modID);
            final File file = new File(folder, fileName.trim() + ".dat");

            return file.delete();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Use to get a (new) folder for your mod only.
     *
     * @param modID
     * @return File instance of the folder. It exists.
     */
    public static File getModFolder(String modID)
    {
        final File folder = new File(DataHelper.root, modID);
        folder.mkdirs();
        return folder;
    }

    /**
     * To be called on server start.
     */
    public static void init()
    {
        DataHelper.root = new File(DimensionManager.getCurrentSaveRootDirectory(), "CCM-Modding");
        DataHelper.root.mkdirs();
    }

    /**
     * Use this before you save data...
     *
     * @param modID    for the folder
     * @param fileName , don't add an extension.
     * @return data stored or new NBTTagCompound if file didn't exist.
     */
    public static NBTTagCompound readData(String modID, final String fileName)
    {
        try
        {
            final File folder = DataHelper.getModFolder(modID);

            final File file = new File(folder, fileName.trim() + ".dat");

            if (!file.exists())
            {
                return new NBTTagCompound();
            }
            else
            {
                return CompressedStreamTools.read(file);
            }

        }
        catch (final Exception e)
        {
            e.printStackTrace();
            return new NBTTagCompound();
        }
    }

    /**
     * Make sure you don't just override the old file. Read it, then manipulate, then save.
     *
     * @param modID    for the folder.
     * @param fileName , don't add an extension.
     * @param data     in NBT format.
     * @return true if success.
     */
    public static boolean saveData(String modID, final String fileName, final NBTTagCompound data)
    {
        try
        {
            final File folder = DataHelper.getModFolder(modID);

            final File tempFile = new File(folder, fileName.trim() + "_tmp.dat");
            final File realFile = new File(folder, fileName.trim() + ".dat");

            CompressedStreamTools.write(data, tempFile);

            if (realFile.exists())
            {
                realFile.delete();
            }

            tempFile.renameTo(realFile);

            return true;
        }
        catch (final Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}