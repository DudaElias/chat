/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoChat;

/**
 *
 * @author u18190
 */
public class AvisosUsuarios implements Coisa
{
    protected static final long serialVersionUID = 1L;
    protected String mensagem;
    public AvisosUsuarios(String msg) throws Exception
    {
        if(msg.equals(""))
        {
            throw new Exception("Dados passados por parametro não podem ser vazios!");
        }
        this.mensagem = msg;

    }
    public String toString()
    {
        return mensagem;
    }
    public int hashCode()
   {
       int ret = 666;
       ret = ret*2 + Integer.parseInt(this.mensagem);
       return ret;
   }
   
   public boolean equals(Object obj)
   {
       if(this == obj)
           return true;
       if(obj == null)
           return false;
       if (this.getClass()!=obj.getClass())
		return false;
       AvisosUsuarios aviso = (AvisosUsuarios)obj;       
       
       if(!aviso.mensagem.equals(this.mensagem))
           return false;
       return true;
   }
}
