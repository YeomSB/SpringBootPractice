package org.example.mySpringProj.repository.categoryRepository;

import org.example.mySpringProj.domain.categoryDomain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
