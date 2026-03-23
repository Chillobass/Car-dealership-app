package data.persistence.repositories;

import businessLogic.details.Detail;
import businessLogic.details.replaceable.Replaceable;

import java.util.List;

public interface DetailRepository {
    void add(Detail detail);

    boolean contains(Detail detail);

    List<Detail> findByCategory(Replaceable detailCategory);
}