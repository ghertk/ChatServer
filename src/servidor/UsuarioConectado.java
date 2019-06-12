package servidor;

import chat.MensagemInterface;
import chat.Usuario;
import java.rmi.RemoteException;
import java.util.Objects;

public class UsuarioConectado {
    private Usuario usuario;
    private final MensagemInterface objetoRemoto;

    public UsuarioConectado(Usuario usuario, MensagemInterface objetoRemoto) {
        this.usuario = usuario;
        this.objetoRemoto = objetoRemoto;
    }
    
    public void enviarMensagem(String mensagem) throws RemoteException {
        this.objetoRemoto.enviarMensagem(mensagem, usuario.getIpPessoal());
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioConectado other = (UsuarioConectado) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }
}
