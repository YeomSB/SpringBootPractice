package org.example.mySpringProj.service.categoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mySpringProj.domain.boardDomain.Board;
import org.example.mySpringProj.domain.categoryDomain.Category;
import org.example.mySpringProj.domain.boardDomain.Likes;
import org.example.mySpringProj.domain.categoryDomain.Tag;
import org.example.mySpringProj.domain.commentDomain.Comment;
import org.example.mySpringProj.domain.commentDomain.ReComment;
import org.example.mySpringProj.dto.categoryDto.CategoryDTO;
import org.example.mySpringProj.exception.AppException;
import org.example.mySpringProj.exception.ErrorCode;
import org.example.mySpringProj.repository.boardRepository.BoardRepository;
import org.example.mySpringProj.repository.boardRepository.LikeRepository;
import org.example.mySpringProj.repository.categoryRepository.CategoryRepository;
import org.example.mySpringProj.repository.categoryRepository.TagRepository;
import org.example.mySpringProj.repository.commentRepository.CommentRepository;
import org.example.mySpringProj.repository.commentRepository.ReCommentRepository;
import org.example.mySpringProj.repository.userRepository.UserRepository;
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
    private final LikeRepository likeRepository;
    private final ReCommentRepository reCommentRepository;
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
    public void modify(CategoryDTO categoryDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND,"카테고리를 찾을 수 없습니다.",categoryId));

        category.setName(categoryDTO.getName());
    }

    @Transactional
    public void delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND,"카테고리를 찾을 수 없습니다.",categoryId));
        List<Board> boards = category.getBoards();
        for (Board board : boards) {
            List<Likes> likes = board.getLikes();
            likeRepository.deleteAll(likes);

            List<Comment> comments = board.getComments();
            for (Comment comment : comments) {
                List<ReComment> reComments = comment.getReComments();
                reCommentRepository.deleteAll(reComments);
            }
            commentRepository.deleteAll(comments);
        }
        boardRepository.deleteAllByCategory(category);
        tagRepository.deleteAllByCategory(category);
        categoryRepository.delete(category);
    }

    public CategoryDTO getCategory(Long categoryId) {
         Category category = categoryRepository.findFetchById(categoryId);
        return CategoryDTO.builder()
                .name(category.getName())
                .tags(Tag.convertTagNames(category.getTags()))
                .userId(category.getUser().getId())
                .build();
    }
}
