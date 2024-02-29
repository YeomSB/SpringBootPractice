package org.example.mySpringProj.service.categoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.categoryDomain.Category;
import org.example.mySpringProj.domain.categoryDomain.Tag;
import org.example.mySpringProj.domain.commentDomain.Comment;
import org.example.mySpringProj.dto.categoryDto.CategoryDTO;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.boardRepository.BoardRepository;
import org.example.mySpringProj.repository.categoryRepository.CategoryRepository;
import org.example.mySpringProj.repository.categoryRepository.TagRepository;
import org.example.mySpringProj.repository.commentRepository.CommentRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
import org.example.mySpringProj.service.UtilFunc;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;


    @Transactional
    public void save(CategoryDTO categoryDTO) {
        List<Tag> tags = Tag.convertTags(categoryDTO.getTags());

        Category category = categoryRepository.save(Category.builder()
                .name(categoryDTO.getName())
                .user(userRepository.findById(categoryDTO.getUserId()).orElseThrow())
                .tags(tags)
                .build()
        );

        for(Tag tag : tags)
            tag.setCategory(category);

        tagRepository.saveAll(tags);
    }

    @Transactional
    public void modify(CategoryDTO categoryDTO, Long categoryId, String reqName) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"카테고리를 찾을 수 없습니다.",categoryId));

        UtilFunc.hasPermission(category.getUser().getName(),reqName);

        category.setName(categoryDTO.getName());
    }

    @Transactional
    public void delete(Long categoryId,String reqName) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"카테고리를 찾을 수 없습니다.",categoryId));

        UtilFunc.hasPermission(category.getUser().getName(),reqName);

        List<Board> boards = category.getBoards();
        for (Board board : boards) {
            List<Comment> comments = board.getComments();
            commentRepository.deleteAll(comments);
        }
        boardRepository.deleteAllByCategory(category);
        tagRepository.deleteAllByCategory(category);
        categoryRepository.delete(category);
    }

    public CategoryDTO getCategory(Long categoryId) {
         Category category = categoryRepository.findById(categoryId).orElseThrow();
        return CategoryDTO.builder()
                .name(category.getName())
                .tags(Tag.convertTagNames(category.getTags()))
                .userId(category.getUser().getId())
                .build();
    }
}
