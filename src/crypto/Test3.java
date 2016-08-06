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
public class Test3 {

    public static void main(String args[])throws Exception
    {
        char a[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        DataInputStream in = new DataInputStream(new FileInputStream("C:/java/gpl.eky"));
        int i=0,j,k,temp;
        while(i<128)
        {
            temp=in.read();
            System.out.print(a[temp>>4]);
            System.out.print(a[temp&0x000F]);
            System.out.print(" ");
            i++;
            if(i%16==0)
                System.out.println();
        }
    }
}
