package org.example.mySpringProj.repository.categoryRepository;

import org.example.mySpringProj.domain.categoryDomain.Category;
import org.example.mySpringProj.domain.categoryDomain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    void deleteAllByCategory(Category category);
}
