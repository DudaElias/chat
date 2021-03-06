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
    
    
    synchronized public void run ()
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
                                String destinatario = ((Mensagem) recebido).desti;
                                if(salaEscolhida.getNome().equals(destinatario))
                                {
                                    for(int i = 0; i < salaEscolhida.getUsuarios().size(); i++)
                                    {
                                        ((Usuario)salaEscolhida.getUsuarios().get(i)).envia(recebido);
                                    }
                                }
                                else
                                {
                                    ((Usuario)salaEscolhida.getUsuario(this.usu.apelido)).envia(recebido);
                                    ((Usuario)salaEscolhida.getUsuario(destinatario)).envia(recebido);
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
    public String toString()
    {
        return this.nomeUsu + " na sala " + this.nomeSala;
    }
    public boolean equals(CuidadoraDeUsuario obj)
    {
       if(this == obj)
           return true;
       if(obj == null)
           return false;
       if (this.getClass()!=obj.getClass())
		return false;
       CuidadoraDeUsuario aviso = (CuidadoraDeUsuario)obj;       
       if(aviso.viva!=this.viva) 
       {
           return false;
       } 
       if(this.conexao != obj.conexao)
           return false;
       if(this.errou != obj.errou)
           return false;
       if(!this.nomeSala.equals(obj.nomeSala))
           return false;
       if(!this.nomeUsu.equals(obj.nomeUsu))
           return false;
       if(this.receptor != obj.receptor)
           return false;
       if(!this.salaEscolhida.equals(obj.salaEscolhida))
           return false;
       if(!this.salas.equals(obj.salas))
           return false;
       if(this.transmissor != obj.transmissor)
           return false;
       if(!this.usu.equals(obj.usu))
           return false;
           
       return true;
        
    }
    public int hashCode()
    {
        int ret = 666;
       ret = this.viva?0:1 + 2*ret;
       ret = Integer.parseInt(this.conexao.toString()) + 2*ret;
       ret = Integer.parseInt(this.nomeSala) + 2*ret;
       ret = Integer.parseInt(this.nomeUsu) + 2*ret;
       ret = this.viva?0:1 + 2*ret;
       ret = Integer.parseInt(this.receptor.toString() + 2*ret);
       ret = Integer.parseInt(this.salaEscolhida.toString()) + 2*ret;
       ret = Integer.parseInt(this.transmissor.toString()) + 2*ret;
       ret = Integer.parseInt(this.usu.toString()) + ret*2;
       return ret;
    }
}
