/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crypto;

import java.io.*;
/**
 *
 * @author Fareez
 */
public class Crypter {

    long i=0;
    long max=0;

    static int KEY_MISMATCH=2;

    public Crypter()
    {

    }

    public boolean encrypt(String f,Key key)throws IOException
    {
        int j;
        int bufSize=32*1024;
        File file = new File(f);
        if(file.length()>50*1024*1024)
            bufSize = 256*1024;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file),bufSize);
        BufferedOutputStream out;
        DataInputStream idIn;
        String outFile=file.getName();
        out =new BufferedOutputStream(
                new FileOutputStream(file.getParentFile().getPath()
                + File.separator
                + outFile + ".enc"),bufSize);
        i=0;
        j=0;
        max = file.length();
        while(i<max)
        {
            out.write(in.read()^key.key[j]);
            i++;
            j++;
            if(j==key.length)
                j=0;
        }
        idIn = new DataInputStream(new FileInputStream(file.getParentFile().getPath()
                + File.separator
                + outFile + ".enc"));
        key.setIDKey(idIn.readInt());
        key.writeKey(file.getParentFile().getPath()
                + File.separator
                + outFile + ".eky");
        out.flush();
        out.close();
        idIn.close();
        in.close();
        return true;
    }

    public int decrypt(String f,String kf)throws Exception
    {
        File encf = new File(f);
        RandomAccessFile raf = new RandomAccessFile(f,"r");
        Key k = Key.decodeKeyFromFile(kf);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(encf),32*1024);
        String outFile = f.substring(0,f.lastIndexOf("."));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile),32*1024);
        int j,temp;
        //check match of key n encryption file
        temp=raf.readInt();
        if(temp!=k.getIDKey())
            return KEY_MISMATCH;
        /////////////////////////////////////
        i=0;
        max=encf.length();
        j=0;
        while(i<max)
        {
            out.write(in.read()^k.key[j]);
            j++;
            i++;
            if(j==k.length) j=0;
        }
        out.flush();
        raf.close();
        in.close();
        out.close();
        return 1;
    }

    public int decrypt(String f,Key k)throws IOException
    {
        File encf = new File(f);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(encf),32*1024);
        String outFile = f.substring(0,f.lastIndexOf("."));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile),32*1024);
        int j;
        //check match of key n encryption file
        ///////////////////////////////////////////
        i=0;
        max=encf.length();
        j=0;
        while(i<max)
        {
            out.write(in.read()^k.key[j]);
            j++;
            i++;
            if(j==k.length) j=0;
        }
        out.flush();
        in.close();
        out.close();
        return 1;
    }

    public int getProgress()
    {
        if(max!=0)
            return (int)(((float)i/(float)max)*100);
        else
            return 0;
    }
}
