package org.example.mySpringProj.repository.categoryRepository;

import org.example.mySpringProj.domain.categoryDomain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select b from Category b join fetch b.tags")
    Category findFetchById(Long categoryId);

//    @Query("select b from Board b join fetch b.likes")
//    Board findFetchLikesById(Long boardId);
}
