package chat;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CuidadoraDeUsuario extends Thread
{
    private Usuario usu;
    protected Sala salaEscolhida;
    public CuidadoraDeUsuario (Socket conexao, SalasDisponiveis salas) throws Exception
    {
        // declarar e instanciar OOS e OIS
        ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
        ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
        // interagir com o usr via OOS e OIS ate descobrir o nome da sala em que ele deseja entrar, eventualmente, informando sala cheia
        transmissor.writeChars("Qual sala deseja entrar? Suas possibilidades serão listadas a seguir:");
        for(int i = 0; i < salas.getSalas().size();i++ )
        {
            transmissor.writeChars(((Sala)salas.getSalas().get(i)).getNome());
        }
        String escolhaSala = receptor.readUTF();
        // procurar em salas a sala com o nome desejado
        salaEscolhida = salas.procurar(escolhaSala);
        // interagir com o usr via OOS e OIS ate descobrir o nome que ele deseja usar, eventualmente, informando nome invalido ou ja usado
        transmissor.writeChars("Qual nome você deseja usar para entrar nessa sala?");
        String apelido = receptor.readUTF();
        if(apelido == null)
        {
            transmissor.writeChars("Nome de usuário não pode ser vazio!!");
        }
        else
        {
            boolean existe  = false;
            for(int i = 0; i < salaEscolhida.getUsuarios().size(); i++)
            {
                if(apelido == ((Sala)(salaEscolhida.getUsuarios().get(i))).getNome())
                {
                     transmissor.writeChars("Nome de usuário já escolhido por outro usuário!!");
                     existe = true;
                }
            }
            if(existe == false)
            {
                usu = new Usuario(conexao, transmissor, receptor, apelido, salaEscolhida);
                for(int i = 0; i < salaEscolhida.getUsuarios().size(); i++)
                {
                    this.usu.envia(new AvisoDeEntradaNaSala(((Usuario)(salaEscolhida.getUsuarios().get(i))).getNome()));
                    ((Usuario)salaEscolhida.getUsuarios().get(i)).envia(new AvisoDeEntradaNaSala(this.usu.apelido));
                }
                salaEscolhida.adicionarUsu(usu);
            }
        }
        // instanciar o Usuario, fornecendo, conexao, OOS, OIS, nome e sala
        // fazer varias vezes this.usuario.envia(new AvisoDeEntradaDaSala(i)), onde i � o nome de algum usuario que ja estava na sala
        
        // fazer varias vezes i.envia(new AvisoDeEntradaDaSala(usuario.getNome()), onde i � o nome de algum usuario que ja estava na sala
        // incluir o usuario na sala
    }

    public void run ()
    {
        Coisa recebido=null;

        do
        {
            // receber mensagens, avisos de entrada na e de saida da sala
            recebido = usu.receber();
            if(recebido instanceof Mensagem)
            {
                for(int i = 0; salaEscolhida.getUsuarios().size() > i; i++)
                {
                    try 
                    {
                        ((Usuario)this.salaEscolhida.getUsuario(usu.getNome())).envia(recebido);
                    }
                    catch (Exception ex) {}
                }
            }
            // se for mensagem, pega nela o destinatario, acha o destinatario na sala e manda para ele a mensagem
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
            catch (Exception ex) {        }
        }
        // remover this.usuario da sala
        // mandar new AvisoDeSaidaDaSala(this.usuario.getNome()) para todos da sala
        this.usu.fechaTudo();
    }
}