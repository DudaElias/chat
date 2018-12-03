package projetoChat;

import java.io.*;
import java.net.*;

public class Usuario implements Serializable
{
	protected String apelido;
	protected Socket conexao;
	protected ObjectOutputStream transmissor;
	protected ObjectInputStream receptor;
	protected Sala sala;

	public Usuario(Socket con ,ObjectOutputStream trs,ObjectInputStream rec,String nome, Sala s) throws Exception
	{
		if(nome == null || con == null || trs == null || s == null)
		{
			throw new Exception("Dados digitados não podem estar nulos!");
		}
		this.conexao = con;
		this.transmissor = trs;
		this.apelido = nome;
		this.sala = s;
	}
	
	public String getNome()
	{
		return this.apelido;
	}

	public void envia(Coisa x) throws Exception
	{
		try
		{
                    this.transmissor.writeObject(x.toString());
                    this.transmissor.flush();
		}
		catch(Exception err)
		{
        	System.out.println(err.getStackTrace().toString());

		}

	}

	public Coisa receber()
	{
            try
            {
            	System.out.println(this.receptor.readObject());
            	return (Coisa) this.receptor.readObject();
            }
            
            catch(Exception erro)
            {
            	System.out.println(erro.getStackTrace().toString());
            }
            
            return null;
	}
	
	public void fechaTudo()
	{
            try
            {
		this.transmissor.close();
        	this.receptor.close();
       		this.conexao.close();
            }
            catch(Exception erro)
            {
                erro.getMessage();
            }
	}

	public String toString()
	{
		String s = "Nome de usuário: " + this.apelido + " e sua sala é: " + this.sala.nome;
                return s;
	}

	public int hashCode()
	{
		int ret = 5;
		ret = ret * 2 + Integer.parseInt(this.apelido);
		ret = ret * 2 + Integer.parseInt(this.sala.toString());
                return ret;
		/*ret = ret * 2 + Integer.parseInt(this.conexao);
		ret = ret * 2 + Integer.parse(this.transmissor);
		ret = ret * 2 + this.receptor;*/
	}

	public boolean equals(Usuario usu)
	{

		if(usu == null)
			return false;
		if(usu == this)
			return true;	
		if(usu.getClass() != this.getClass())
			return false;

		Usuario u = (Usuario) usu;
		
		if(this.apelido != u.apelido)
			return false;
		if(this.sala != u.sala)
			return false;
		if(this.conexao != u.conexao)
			return false;
		if(this.transmissor != u.transmissor)
			return false;
		if(this.receptor != u.receptor)
			return false;
		return true;
	}

}
