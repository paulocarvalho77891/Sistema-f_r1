import AdditionApp.*;
import org.omg.CORBA.ORB;
	class AdditionObj extends AdditionPOA {
	  private ORB orb;

	  public void setORB(ORB orb_val) {
	    orb = orb_val; 
	  }

	  // implementação do método add()
	  public int add(int a, int b) {
	    int soma = a + b;
	    return soma;
	  }

	  public void shutdown() {
	    orb.shutdown(false);
	  }
	}