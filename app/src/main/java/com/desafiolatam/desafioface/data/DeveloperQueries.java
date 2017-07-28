package com.desafiolatam.desafioface.data;

import com.desafiolatam.desafioface.models.Developer;

import java.util.Collection;
import java.util.List;

/**
 * Created by Pedro on 20-07-2017.
 */

public class DeveloperQueries {
    public List<Developer> all()
    {
        return Developer.listAll(Developer.class);
    }

    public List<Developer> findByName(String name) {
        String query = "name LIKE ?";
        String arg = "%"+name+"%";
        return Developer.find(Developer.class, query, arg);
    }
}
