package servidor;

import chat.Mensagem;
import chat.MensagemInterface;
import chat.Usuario;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ObjetoRemoto implements MensagemInterface {

    private List<Mensagem> mensagens = new ArrayList<>();
    private List<UsuarioConectado> usuariosConectados = new ArrayList<>();

    @Override
    public void enviarMensagem(String mensagem, String ipPessoal) throws RemoteException {
        UsuarioConectado usuarioConectado = this.usuariosConectados.get(this.usuariosConectados.indexOf(new UsuarioConectado(new Usuario("", ipPessoal), null)));
        Mensagem m = new Mensagem(usuarioConectado.getUsuario(), mensagem);
        this.mensagens.add(m);
        System.out.println("Mensagem: " + mensagem);
        try {
            this.propagarMensagem(m);
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<String> conectarUsuario(String ipCliente, String nome) throws RemoteException {
        Usuario usuario = new Usuario(nome, ipCliente);
        try {
//            System.out.println(usuario.getIpPessoal());
            UsuarioConectado usuarioConectado = new UsuarioConectado(usuario, (MensagemInterface) Naming.lookup("//" + usuario.getIpPessoal() + ":2001/Chat"));
            if (this.usuariosConectados.contains(usuario)) {
                System.out.println("Usuário já conectado: " + usuario.getIpPessoal());
            } else {
                this.usuariosConectados.add(usuarioConectado);
            }
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        System.out.println("Usuario conectado: " + usuario.getNome());
        List<String> mensagens = new ArrayList();
        for (Mensagem mensagem : this.mensagens) {
            mensagens.add(mensagem.toString());
        }
        return mensagens;
    }

    private void propagarMensagem(Mensagem mensagem) throws RemoteException, NotBoundException, MalformedURLException {
        String msg = mensagem.toString();
        for (UsuarioConectado usuarioConectado : this.usuariosConectados) {
            usuarioConectado.enviarMensagem(msg);
        }
    }
}
