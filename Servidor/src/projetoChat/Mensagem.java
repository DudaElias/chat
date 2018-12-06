package projetoChat;

public class Mensagem implements Coisa
{
	protected static final long serialVersionUID = 1L;
    protected String mensagem;
    protected String desti;
    protected String rem;
    public Mensagem(String msg, String destinatario, String remetente) throws Exception
    {
        if(msg == "" && destinatario == "" && remetente == "")
        {
            throw new Exception("Dados passados por parametro não podem ser vazios!");
        }
        this.mensagem = msg;
        this.desti = destinatario;
        this.rem = remetente;
    }
    public String toString()
    {
        return this.rem + ": " + mensagem;
    }
    public int hashCode()
   {
       int ret = 666;
       ret = ret*2 + Integer.parseInt(mensagem);
       ret = ret*2 + Integer.parseInt(desti);
       ret = ret*2+ Integer.parseInt(rem);
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
       Mensagem msg = (Mensagem)obj;       
       
       if(!msg.desti.equals(this.desti))
           return false;
       
       if(!msg.mensagem.equals(this.mensagem))
           return false;
       
       if(!msg.rem.equals(this.rem))
           return false;
       
       return true;
   }
}