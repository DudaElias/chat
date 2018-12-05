/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoChat;

import java.io.ObjectOutputStream;

/**
 *
 * @author Duda Rocha
 */
public class Recebendo extends Thread
{
    protected Chat janela = null;
    protected boolean viva = true;
    protected ObjectOutputStream obj;
    
    public Recebendo(Chat c) throws Exception
    {
        if(c == null)
            throw new Exception("Chat inválido!");
        this.janela = c;
    }
    
    public void run()
    {
        try
        {
            while(viva)
            {
                janela.receber();
                Thread.sleep(100); 
            }
            if(janela.fecharComunicacao)
            {
                morra();
            }
        }
        catch(Exception err)
        {
            
        }
    }
    
    public void morra()
    {
        this.viva = false;
    }
}
