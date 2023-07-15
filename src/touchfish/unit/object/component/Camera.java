package touchfish.unit.object.component;

import touchfish.unit.action.ActionUtility;
import touchfish.unit.inter.IDelegate;
import touchfish.unit.object.game.GameObject;
import touchfish.unit.system.GameEngine;

import java.util.List;

public class Camera extends Component {

    public static Camera mainCamera;

    public static Camera getMainCamera() {
        List<Camera> cameras = FindObjectsByType(Camera.class);
        if (cameras.size() > 0) {
            mainCamera = cameras.get(0);
            return cameras.get(0);
        }
        return null;
    }

    public Camera() {
        super();
    }

    @Override
    public void Update() {

    }
}
