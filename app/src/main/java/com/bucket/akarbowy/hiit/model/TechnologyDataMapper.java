package com.bucket.akarbowy.hiit.model;

import com.bucket.akarbowy.hiit.adomain.Technology;
import com.bucket.akarbowy.hiit.di.PerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by akarbowy on 20.12.2015.
 */
@PerActivity
public class TechnologyDataMapper {

    @Inject
    public TechnologyDataMapper() {
    }

    public TechnologyModel transform(Technology technology) {
        if (technology == null) throw new IllegalArgumentException("Cannot transform a null value");
        TechnologyModel model = new TechnologyModel(technology.getObjectId());
        model.setTitle(technology.getTitle());
        return model;
    }

    public List<TechnologyModel> transform(List<Technology> technologies) {
        List<TechnologyModel> technologyModelList;

        if (technologies == null || technologies.isEmpty()) {
            technologyModelList = Collections.emptyList();
        } else {
            technologyModelList = new ArrayList<>();
            for (Technology technology : technologies) {
                technologyModelList.add(transform(technology));
            }
        }

        return technologyModelList;
    }
}
