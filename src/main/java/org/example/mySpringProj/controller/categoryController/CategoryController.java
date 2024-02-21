package org.example.mySpringProj.controller.categoryController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.dto.categoryDto.CategoryDTO;
import org.example.mySpringProj.service.categoryService.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping("/create")
    public ResponseDTO create(@RequestBody CategoryDTO categoryDTO){
        categoryService.save(categoryDTO);
        return ResponseDTO.success(HttpStatus.CREATED,"해당 게시판 명이 생성되었습니다.",categoryDTO);
    }

    @GetMapping("/{categoryId}")
    public CategoryDTO getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @PatchMapping("/modify/{categoryId}")
    public ResponseDTO modify(@PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO, Authentication authentication){
        categoryService.modify(categoryDTO,categoryId,authentication.getName());
        return ResponseDTO.success(HttpStatus.OK,"해당 게시판 명이 수정되었습니다. : " + categoryDTO.getName(),categoryDTO);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseDTO delete(@PathVariable Long categoryId,Authentication authentication) {
        categoryService.delete(categoryId,authentication.getName());
        return ResponseDTO.success(HttpStatus.OK,"해당 게시판의 게시물 및 댓글이 모두 삭제되었습니다.",null);
    }



}
