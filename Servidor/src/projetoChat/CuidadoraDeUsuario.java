package projetoChat;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import daos.Salas;
import projetoChat.SalasDisponiveis;
import projetoChat.Usuario;

public class CuidadoraDeUsuario extends Thread
{
    private boolean viva = true;
    protected boolean errou = true;
    private Socket  conexao;
    private Salas   salas;
    private Usuario usu;
    private projetoChat.Sala salaEscolhida = null;
    private ObjectOutputStream transmissor;
    private ObjectInputStream receptor;
    protected String nomeSala = "";
    protected String nomeUsu = "";
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
        while(this.viva)
        {
    	try
    	{
                while(errou)
                {
                    transmissor = new ObjectOutputStream(conexao.getOutputStream());
                    receptor = new ObjectInputStream(conexao.getInputStream());
                    this.nomeSala = (String)receptor.readObject();
                    this.nomeUsu = (String)receptor.readObject();
                    salaEscolhida = salas.procurar(nomeSala);
                    if(salaEscolhida.isCheia())
                    {
                      transmissor.writeObject("Sala escolhida está cheia!");
                    }
                    else if(this.nomeSala == null)
                    {
                        transmissor.writeObject("Nome da Sala não pode ser vazio!");
                    }
                    else if(salaEscolhida.existe(this.nomeUsu))
                    {
                        transmissor.writeObject("Este nome já existe!! Tente novamente.");
                    }
                    else if(this.nomeUsu == null)
                    {
                        transmissor.writeObject("Nome de Usuário não pode ser vazio!");
                    }
                    else
                    {
                        errou = false;
                        transmissor.writeObject("Processo completo");
                    }
                    transmissor.flush();
                }
                usu = new Usuario(conexao, transmissor, receptor, nomeUsu, salaEscolhida);
                salaEscolhida.adicionarUsu(usu);
                for(int i = 0; i < salaEscolhida.getUsuarios().size()-1; i++)
                {
                    AvisosUsuarios nomeUsu = new AvisosUsuarios(salaEscolhida.getNomeUsu((Usuario)(this.salaEscolhida.getUsuarios().get(i))));
                    transmissor.writeObject(nomeUsu);
                    transmissor.flush();
                    (((Usuario)salaEscolhida.getUsuarios().get(i))).envia(new AvisosUsuarios(this.usu.apelido));
                }

                for(int i = 0; i < salaEscolhida.getUsuarios().size()-1; i++)
                {
                    this.usu.envia(new AvisoDeEntradaNaSala(((Usuario)(salaEscolhida.getUsuarios().get(i))).getNome()));
                    ((Usuario)salaEscolhida.getUsuarios().get(i)).envia(new AvisoDeEntradaNaSala(this.usu.apelido));
                }
		Coisa recebido=null;
	        do
	        {
	            recebido = (Coisa)receptor.readObject();
	            if(recebido != null)
	            {
		            if(recebido instanceof Mensagem)
                            {
                                    for(int i = 0; i < salaEscolhida.getUsuarios().size(); i++)
                                    {
                                        ((Usuario)salaEscolhida.getUsuarios().get(i)).envia(recebido);
                                    }
                            }
                    }
                }
                while (!(recebido instanceof PedidoParaSairDaSala));
                this.usu.fechaTudo();
                for(int i = 0; salaEscolhida.getUsuarios().size() > i; i++)
                {
                    try 
                    {
                        ((Usuario)salaEscolhida.getUsuarios().get(i)).envia(new AvisoDeSaidaDaSala(this.usu.apelido));
                    } 
                    catch (Exception ex) 
                    {        
                        System.out.println(ex.getMessage());
                    }
                }
                this.salaEscolhida.excluirUsu(this.usu);
                this.morra();
    	}
    	catch(Exception err)
    	{
            System.out.println(err.getMessage());
    	}
        }
    }
    public void morra()
    {
        this.viva = false;
    }
}
