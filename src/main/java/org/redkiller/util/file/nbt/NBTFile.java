package org.redkiller.util.file.nbt;

import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class NBTFile {
    private final File file;
    private NBTTagCompound tagCompound;

    public NBTFile(String path) {
        this(new File(path), new NBTTagCompound());
    }

    public NBTFile(String path, NBTTagCompound tagCompound) {
        this(new File(path), tagCompound);
    }

    public NBTFile(File file) {
        this(file, new NBTTagCompound());
    }

    public NBTFile(File file, NBTTagCompound tagCompound) {
        this.file = file;
        this.tagCompound = tagCompound;
    }

    public File getFile() {
        return this.file;
    }

    public NBTTagCompound getCompound() {
        return this.tagCompound;
    }

    public void read() {
        try {
            // if the file exists we read it
            if(file.exists()) {
                FileInputStream fileinputstream = new FileInputStream(file);
                tagCompound = NBTCompressedStreamTools.a(fileinputstream);
                fileinputstream.close();

            } else {
                // else we create an empty TagCompound
                clear();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void clear() {
        tagCompound = new NBTTagCompound();
    }

    public void write() {
        try {

            if(!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fileoutputstream = new FileOutputStream(file);

            NBTCompressedStreamTools.a(tagCompound, fileoutputstream);
            fileoutputstream.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
