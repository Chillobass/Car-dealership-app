package data.RepositoriesImpl;

import BusinessLogic.details.Detail;
import BusinessLogic.details.DetailCategory;
import data.Persistence.Repositories.DetailRepository;

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

    public boolean contains(Detail detail){
        return storage.containsKey(detail);
    }

//    @Override
//    public List<Detail> Query() {
//    }

    public List<Detail> findByCategoty(DetailCategory detailCategory){
        List<Detail> res = new ArrayList<Detail>();
        for (var i : storage.keySet()){
            if (i.getCategory() == detailCategory){
                res.add(i);
            }
        }
        return res;
    }
}
