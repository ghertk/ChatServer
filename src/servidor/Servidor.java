package servidor;

import chat.MensagemInterface;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(2001);
            
            ObjetoRemoto objetoRemoto = new ObjetoRemoto();
            
            MensagemInterface skeletonObj = (MensagemInterface)UnicastRemoteObject.exportObject(objetoRemoto, 0);
            
            registry.bind("Chat", skeletonObj);
            
            System.out.println("Servidor iniciado ip: " + InetAddress.getLocalHost().getHostAddress());
        } catch (RemoteException ex) {
            System.out.println("Erro ao iniciar o servidor.");
        } catch (AlreadyBoundException ex) {
            System.out.println("Já ha outro servidor sendo executado.");
        } catch (UnknownHostException ex) {
            System.out.println("Erro ao recuperar o endereço de ip.");
        }
    }
}
