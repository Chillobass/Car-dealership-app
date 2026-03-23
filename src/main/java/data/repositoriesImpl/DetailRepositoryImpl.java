package data.repositoriesImpl;

import businessLogic.details.Detail;
import businessLogic.details.replaceable.Replaceable;
import data.persistence.repositories.DetailRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailRepositoryImpl implements DetailRepository {
    private final HashMap<Detail, Integer> storage = new HashMap<>();

    @Override
    public void add(Detail detail) {
        if (storage.containsKey(detail)){
            storage.put(detail, storage.get(detail)+1);
        }
        storage.put(detail, 1);
    }

    @Override
    public boolean contains(Detail detail){
        return storage.containsKey(detail);
    }

    @Override
    public List<Detail> findByCategory(Replaceable detailCategory){
        List<Detail> res = new ArrayList<Detail>();
        for (var i : storage.keySet()){
            if (i.getCategory() == detailCategory){
                res.add(i);
            }
        }
        return res;
    }
}
