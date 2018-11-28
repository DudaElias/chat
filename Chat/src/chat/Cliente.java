/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;
import daos.Salas;
import java.io.*;
import java.net.*;

public class Cliente {
    
    public static void Main (String[]args) throws Exception
    {
        String msg = "";
        Salas salas = new Salas();
        daos.Salas salasBd;
        salasBd = new daos.Salas();
    
        for(int i = 0; i <salasBd.getQtd(); i++)
        salas.adicionarSala((Sala)salasBd.getSalas().get(i));
    
        do
        {
            ServerSocket servidor = new ServerSocket(12345);
            Socket conexao = servidor.accept();
            ObjectOutputStream obj = new ObjectOutputStream(conexao.getOutputStream());
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            CuidadoraDeUsuario tratar = new CuidadoraDeUsuario(conexao, salas);
            tratar.start();
            msg = teclado.readLine();
            obj.writeChars(msg);
        
        }
    while(!msg.toUpperCase().equals("FIM"));
    }
}

//sincornized (X)  - COMANDO    //MINIMO POSSIVEL //POSSO FAZER VARIOS
//{
//    X.-------
//    X.-------
//}

//hardData

//CUIDADORA => recebe coisas do usuario que ela cuida, se for uma coisa que precisa ser enviada para outro usuario, ela manda para o outro usuario.
// |_ ela recebe do usuario que cuida e manda para usuarios que ela n cuida

//instanceof para descobrir o tipo da coisa
