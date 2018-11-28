package projetoChat;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import projetoChat.SalasDisponiveis;
public class Servidor
{
    public static void main (String[] args) throws Exception
    {
        SalasDisponiveis salas = new SalasDisponiveis();
        ServerSocket pedido = null;
        try 
        {
            pedido = new ServerSocket (12345);
        } 
        catch (Exception ex) 
        {
           
        }
        for(;;)
        {
            try 
            {
                Socket conexao = pedido.accept();
                CuidadoraDeUsuario cuidadora=new CuidadoraDeUsuario(conexao,salas);
                cuidadora.start();
            } 
            catch (Exception ex) 
            {
            }
        }     
    }
}