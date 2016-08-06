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
public class Test2 {

    public static void main(String args[])throws Exception
    {
        Crypter c = new Crypter();
        System.out.println(c.decrypt("C:\\Documents and Settings\\Shinaz\\My Documents\\fareez.mdb.enc", "C:\\Documents and Settings\\Shinaz\\My Documents\\fareez.mdb.eky"));
    }
}
