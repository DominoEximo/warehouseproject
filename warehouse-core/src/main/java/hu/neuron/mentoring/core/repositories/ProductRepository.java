package hu.neuron.mentoring.core.repositories;

import hu.neuron.mentoring.clientapi.entity.Category;
import hu.neuron.mentoring.clientapi.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product,Long>,JpaRepository<Product,Long> {

    List<Product> getAllByCategory(Category category, PageRequest pageRequest);
    List<Product> getAllByCategory(Category category);




}
