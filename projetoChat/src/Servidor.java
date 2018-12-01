public class Servidor
{
    public static void main (String[] args)
    {
        Salas salas = new Salas ();
        ServerSocket pedido = new ServerSocket ("123.45.67.89", 12321);
        ...

        for(;;)
        {
            Socket conexao = pedido.accept();
            CuidadoraDeUsuario cuidadora=new CuidadoraDeUsuario(conexao,salas);
            cuidadora.start();

            ...
        }     
    }
}