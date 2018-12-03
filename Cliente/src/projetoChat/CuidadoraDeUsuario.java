package projetoChat;
import java.io.*;
import java.net.*;
import daos.Salas;
import projetoChat.Usuario;

public class CuidadoraDeUsuario extends Thread
{
    private Socket  conexao;
    private Salas   salas;
    private Usuario usu;
    private projetoChat.Sala salaEscolhida = null;
    public CuidadoraDeUsuario (Socket conexao, Salas salas) throws Exception
    {
        if(conexao == null || salas == null)
        {
            throw new Exception("Conexao ou salas não podem ser vazias");
        }
        
        this.conexao = conexao;
        this.salas = salas;
    }
    
    public void run ()
    {
        try 
        {
            // procurar em salas a sala com o nome desejado
            ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
            ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
            String nomeUsu = (String) receptor.readObject();
            String nomeSala = (String) receptor.readObject();
            salaEscolhida = salas.procurar(nomeSala);
            usu = new Usuario(conexao, transmissor, receptor, nomeUsu, salaEscolhida);
            for(int i = 0; i < salaEscolhida.getUsuarios().size(); i++)
            {
                this.usu.envia(new AvisoDeEntradaNaSala(((Usuario)(salaEscolhida.getUsuarios().get(i))).getNome()));
                ((Usuario)salaEscolhida.getUsuarios().get(i)).envia(new AvisoDeEntradaNaSala(this.usu.apelido));
            }
            salaEscolhida.adicionarUsu(usu);
        } 
        catch (Exception ex) 
        {
        }
        
        Coisa recebido=null;
        do
        {
            recebido = usu.receber();
            if(recebido instanceof Mensagem)
            {
                /*for(int i = 0; salaEscolhida.getUsuarios().size() > i; i++)
                {*/
                    try 
                    {
                        ((Usuario)this.salaEscolhida.getUsuario(usu.getNome())).envia(recebido);
                    }
                    catch (Exception ex) {}
               /* }*/
            }
        }
        while (!(recebido instanceof PedidoParaSairDaSala));
        this.salaEscolhida.excluirUsu(this.usu);
        for(int i = 0; salaEscolhida.getUsuarios().size() > i; i++)
        {
            try 
            {
                this.usu.envia(new AvisoDeSaidaDaSala(((Usuario)(salaEscolhida.getUsuarios().get(i))).getNome()));
                ((Usuario)salaEscolhida.getUsuarios().get(i)).envia(new AvisoDeSaidaDaSala(this.usu.apelido));
            } 
            catch (Exception ex) 
            {        
            }
        }
        this.usu.fechaTudo();
    }
}
