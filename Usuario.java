import java.io.*;
import java.net.*;

public class Usuario
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


	public void envia(Enviavel x) throws Exception
	{
		transmissor.write(x.toString());
	}

	public Enviavel receber()
	{

	}



}
