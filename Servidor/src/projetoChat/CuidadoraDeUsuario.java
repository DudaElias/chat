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
    
    public void setNome(String nome) throws Exception 
    {
        if(nome == null)
            throw new Exception("Nome de Usuário não pode ser vazio!");
        
        this.usu.apelido = nome;
    }
    public void setSala(String sala) throws Exception 
    {
        if(sala == null)
            throw new Exception("Nome de Usuário não pode ser vazio!");
        
        this.salaEscolhida.nome= sala;
    }
    
    
    public void run ()
    {
    	try
    	{
    		ObjectOutputStream transmissor;
    		ObjectInputStream receptor;
	        // procurar em salas a sala com o nome desejado
	        	transmissor = new ObjectOutputStream(conexao.getOutputStream());
	        	receptor = new ObjectInputStream(conexao.getInputStream());
		        String nomeUsu = (String) receptor.readObject();
		        String nomeSala = (String) receptor.readObject();
		        salaEscolhida = salas.procurar(nomeSala);
		        usu = new Usuario(conexao, transmissor, receptor, nomeUsu, salaEscolhida);
                        salaEscolhida.adicionarUsu(usu);
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
		                try 
                                {
                                    for(int i = 0; i < salaEscolhida.getUsuarios().size(); i++)
                                    {
                                        ((Usuario)salaEscolhida.getUsuarios().get(i)).envia(recebido);
                                    }
                                }
                                catch (Exception ex) {}
		            }
	            }
	        }
		        while (!(recebido instanceof PedidoParaSairDaSala));
		        this.salaEscolhida.excluirUsu(this.usu);
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
		        this.usu.fechaTudo();
    	}
    	catch(Exception err)
    	{
            System.out.println(err.getMessage());
    	}
    }

    		
}
