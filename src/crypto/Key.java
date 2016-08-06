/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crypto;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

/**
 *
 * @author Fareez
 */
public class Key {
    int length;
    byte[] key;
    int idkey;

    public Key()
    {
        length=-1;
    }

    public Key(int l)
    {
        length = l;
        key = new byte[l];
    }

    public Key(int l,byte[] b)
    {
        length=l;
        key=b;
    }

    public Key(int l,byte[] b,int id)
    {
        length=l;
        key=b;
        idkey=id;
    }

    public Key(String k)
    {
        length = k.length();
        key = k.getBytes();
    }

    public static Key decodeKeyFromFile(String f)
    {
        try{
            FileInputStream fin = new FileInputStream(new File(f));
            DataInputStream in = new DataInputStream(fin);
            DataInputStream bin;
            RandomAccessFile raf = new RandomAccessFile(f,"r");
            int idno;
            byte[] k = new byte[2];
            byte[] buf = new byte[100+2];
            byte[] keyval;
            short len;
            int i,j;
            in.read(k, 0, 2);
            in.read(buf,0,18);
            for(i=0,j=0;i<buf.length;i++)
            {
                buf[i]=(byte)(buf[i]^k[j]);
                if(j==0) j=1;
                else j=0;
            }
            bin = new DataInputStream(new ByteArrayInputStream(buf));
            len = bin.readShort();
            keyval = new byte[len];
            bin.read(keyval,0,len);
            raf.seek(len+4);
            idno = raf.readInt();
//            System.out.println("Available : "+bin.available());
            return new Key(len,keyval,idno);
        }
        catch(Exception e)
        {
            System.out.println("Error : "+e.getStackTrace());
        }
        return new Key();
    }

    public byte[] getCodedKey()
    {
        byte[] b;
        int i,j;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(2+4+length);
        DataOutputStream out = new DataOutputStream(buf);
        byte k[] = new byte[2];
        k[0]=(byte)(Math.random()*255);
        k[1]=(byte)(Math.random()*255);
        try
        {
            out.write(k);
            out.writeShort((short)length);
            out.write(key);
        }
        catch(Exception e)
        {
            System.out.println("Error :"+e);
        }
        b = buf.toByteArray();
        for(i=2,j=0;i<b.length;i++)
        {
            b[i]=(byte)(b[i]^k[j]);
            if(j==0) j=1;
            else j=0;
        }
        return b;
    }

    public void writeKey(String f)
    {
        try{
            FileOutputStream out = new FileOutputStream(new File(f));
            DataOutputStream dout = new DataOutputStream(out);
            out.write(getCodedKey());
            dout.writeInt(idkey);
            int n = 1024 - (getCodedKey().length) - 4;
            for(int i=0;i<n;i++)
                out.write((byte)(Math.random()*255));
        }
        catch(Exception e)
        {
            System.out.println("Error : "+e);
        }
    }

    public void generateRandomKey()
    {
        int i=0;
        while(i<length)
        {
            key[i]=(byte)(Math.random()*255);
            i++;
        }
    }

    public void setIDKey(int i)
    {
        idkey=i;
    }

    public int getIDKey()
    {
        return idkey;
    }

    public void print()
    {
        int i=0;
        for(i=0;i<length;i++)
        {
            System.out.println(key[i]);
        }
    }
    
    public String toString()
    {
        String s;
        s = "Length : "+ Integer.toString(length)+" Key : ";
        for(int i=0;i<length;i++)
            s=s+Integer.toString(key[i])+" ";
        s=s+" ID : "+Integer.toHexString(idkey);
        return s;
    }

}
