package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.service.model.Cat;

public interface CatService {

    /**
     * save new cat or update existing cat
     *
     * @param cat - model with cat parameters
     * @return - id of saved cat
     */
    String addCat(Cat cat);

    /**
     * find exist cat by provided id
     *
     * @param id - of cat to look for
     * @return {@link Cat} by id
     * @throws EntityNotFoundException - if cat with provided id not found
     */
    Cat findCat(String id) throws EntityNotFoundException;
}
