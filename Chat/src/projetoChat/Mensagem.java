/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoChat;

public class Mensagem implements Coisa
{
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