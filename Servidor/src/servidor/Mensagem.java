package servidor;

import java.io.Serializable;

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
}