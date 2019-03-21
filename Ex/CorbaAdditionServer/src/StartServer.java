import AdditionApp.*;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NameComponent;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POA;

public class StartServer {
  public static void main(String args[]) {
    try {
      // cria e inicia o ORB
      ORB orb = ORB.init(args, null);
      // obtém uma referência ao RootPOA e ativa o POAManager
      // (inicia o Adaptador de Objetos)
      POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      // cria o servente e registra-o no ORB
      AdditionObj addobj = new AdditionObj();
      addobj.setORB(orb); 

      // obtém uma referência para o servente
      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(addobj);
      Addition href = AdditionHelper.narrow(ref);

      // grava o servente no Serviço de Nomes
      org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
      NameComponent path[] = ncRef.to_name("FIAP");
      ncRef.rebind(path, href);
      System.out.println("Servente pronto e aguardando...");

      // aguarda a invocação de clientes
      for (;;){orb.run();}
    } 
    catch (Exception e) {
        System.out.println("Addition Server ERRO: " + e);
        e.printStackTrace(System.out);
    }
    System.out.println("Addition Server finalizando...");
  }
}
