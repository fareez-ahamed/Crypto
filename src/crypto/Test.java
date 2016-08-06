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
public class Test {

    public static void main(String args[])throws Exception
    {
        DataInputStream in = new DataInputStream(new FileInputStream("C:/java/gpl.enc"));
        Key k2,k1 = new Key(8);
        k1.generateRandomKey();
        Crypter c = new Crypter();
        System.out.println("K1 : "+k1);
        c.encrypt("C:/java/gpl.txt", k1);
        k2 = Key.decodeKeyFromFile("C:/java/gpl.eky");
        System.out.println("K2 : "+k2);
        System.out.println(Integer.toHexString(in.readInt()));
    }
}
