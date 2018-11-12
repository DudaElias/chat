public class Sala
{
	protected ArrayList <Usuario> usuarios = new ArrayList();
	protected String nome;

	public Sala (String nome)
	{
		if(nome.equals("")
		throw new Exception ("Nome da Sala ausente");

		this.nome = nome;
	}

	public String getNome()
	{
		return this.nome;
	}

	public ArrayList getUsuarios()
	{
		return this.usuarios;
	}

	public void adicionarUsu(Usuario usu)
	{
		this.usuarios.add(usu);
	}

	public excluirUsu (Usuario usu)
	{
		int n = this.usuarios.size();

		for(int i = 0; i<n ; i++)
		{
			if(this.usuarios.get(i) == usu)
			usuario.remove(i);
		}
	}

	public boolean existe(Usuario usu)
	{
		int n = this.usuarios.size();

		for(int i = 0; i<n ; i++)
		{
			if(this.usuarios.get(i) == usu)
			return true;
		}
		return false;
	}

	public String toString()
	{
		return "Nome :" + this.nome +"\n" + "Possui" +this.usuarios.size()+"usuários" ;
	}

	public int hashCode ()
	{
		 int ret = 666;

		        ret = 13*ret + this.nome;

		        for (int pos=0; pos<this.qtdContatos; pos++)
		        {
		            ret = 13*ret + this.nome    [pos].hashCode();
		            ret = 13*ret + this.telefone[pos].hashCode();
		        }

        return ret;
	}


}