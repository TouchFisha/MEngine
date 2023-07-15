package touchfish.unit.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGroup<T>
{
    public class Value
    {
        public T data;
        public int percentage;

        public Value(T data)
        {
            this.data = data;
            this.percentage = 1;
        }
        public Value(T data, int per)
        {
            this.data = data;
            this.percentage = per;
        }
    }
    public T Value(Random random)
    {
        int per = perBase, pos = random.nextInt(percentageAll);
        for (Value v : pack) {
            if ((per += v.percentage) >= pos)
                return v.data;
        }
        return null;
    }
    public Random random;
    public List<Value> pack;
    public int percentageAll = 0;
    public int perBase = -1;
    public RandomGroup()
    {
        ReloadPer();
        pack = new ArrayList<>();
        random = new Random();
    }
    public RandomGroup(int seed)
    {
        ReloadPer();
        pack = new ArrayList<>();
        random = new Random();
        random.setSeed(seed);
    }
    public void Add(T data, int percentage)
    {
        this.pack.add(new Value(data, percentage));
        percentageAll += percentage;
    }
    public void Add(Value pack)
    {
        this.pack.add(pack);
        percentageAll += pack.percentage;
    }
    public void AddRange(List<Value> packs)
    {
        this.pack.addAll(packs);
        for (Value v : packs) {
            percentageAll += v.percentage;
        }
    }
    public void Remove(T data)
    {
        boolean removed = false;
        int percentage = 0;
        for (Value value : pack) {
            if (value.data.equals(data))
            {
                removed = pack.remove(value);
                percentage = value.percentage;
                break;
            }
        }
        if (removed) percentageAll -= percentage;
    }
    public void Clear()
    {
        pack.clear();
        ReloadPer();
    }
    public void ReloadPer()
    {
        percentageAll = perBase + 1;
    }
}
