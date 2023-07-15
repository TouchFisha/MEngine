package touchfish.unit.object;

import touchfish.unit.system.GameEngine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class BaseObject {
    public String name;
    public String uuid;
    public int instanceID = -1;
    public BaseObject() {
        name = "New "+getClass().getSimpleName();
        uuid = UUID.randomUUID().toString();
    }
    public BaseObject(String name) {
        this.name = name;
        uuid = UUID.randomUUID().toString();
    }

}
