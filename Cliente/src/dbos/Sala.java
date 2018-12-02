/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbos;
public class Sala {
    private String nome;
    private int qtdMax;
    private int codigo;

    public Sala (int cod, String nome, int qtdMax) throws Exception
    {
        if(nome==""||nome.equals(""))
            throw new Exception ("Nome de Sala ausente");

      this.nome = nome;
      this.qtdMax = qtdMax;
      this.codigo = cod;
      /*
       this.setCodigo (codigo);
	          this.setNome   (nome);
        this.setPreco  (preco);

      */
    }

    public void setNome (String nome) throws Exception
    {
        if (nome==null || nome.equals(""))
            throw new Exception ("Nome nao fornecido");

        this.nome = nome;
    }

    public String getNome ()
    {
        return this.nome;
    }


    public int getMax ()
    {
        return this.qtdMax;
    }


    public String toString ()
    {
        String ret="";

        ret+="Nome..: "+this.nome  +"\n";
        ret+="Quantidade máxima.: "+this.qtdMax;

        return ret;
    }

    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (!(obj instanceof Sala))
            return false;

        Sala sa = (Sala)obj;

        if (!this.nome.equals(sa.nome))
            return false;

        if (this.qtdMax!=sa.qtdMax)
            return false;

        return true;
    }

    public int hashCode ()
    {
        int ret=666;

        ret = 7*ret + this.nome.hashCode();
        ret = 7*ret + new Float(this.qtdMax).hashCode();

        return ret;
    }


    public Sala (Sala modelo) throws Exception
    {
        this.nome   = modelo.nome;   // nao clono, pq nao eh clonavel
        this.qtdMax  = modelo.qtdMax;  // nao clono, pq nao eh objeto
    }

    public Object clone ()
    {
        Sala ret=null;

        try
        {
            ret = new Sala (this);
        }
        catch (Exception erro)
        {} // nao trato, pq this nunca ï¿½ null e construtor de
           // copia da excecao qdo seu parametro for null

        return ret;
    }

}
