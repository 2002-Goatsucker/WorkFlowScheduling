import controller.PopulationController;
import controller.impl.NSGAIIPopulationController;
import service.io.Input;
import service.io.impl.ConsoleInputImpl;

public class Application {
    public static void main(String[] args) {
        Input input=new ConsoleInputImpl();
        PopulationController controller=new NSGAIIPopulationController();
        controller.iterate();
    }
}
