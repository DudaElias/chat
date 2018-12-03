package servidor;

import java.net.*;
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