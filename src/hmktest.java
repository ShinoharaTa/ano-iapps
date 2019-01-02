import com.docomostar.*;
import com.docomostar.ui.*;

public class hmktest extends StarApplication {
    public void started(int launchType) {
        MainCanvas can = new MainCanvas();
        Display.setCurrent(can);
        new Thread(can).start();
    }
}
