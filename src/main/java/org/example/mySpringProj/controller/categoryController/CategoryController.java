package org.example.mySpringProj.controller.categoryController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mySpringProj.domain.boardDomain.Category;
import org.example.mySpringProj.dto.ResponseDTO;
import org.example.mySpringProj.dto.categoryDto.CategoryDTO;
import org.example.mySpringProj.service.categoryService.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody CategoryDTO categoryDTO){

        categoryService.save(categoryDTO);

        return ResponseEntity.ok(ResponseDTO.builder()
                .successStatus(HttpStatus.CREATED)
                .successContent("해당 게시판 생성되었습니다.")
                .Data(categoryDTO)
                .build());
    }


    @PatchMapping("/modify/{categoryId}")
    public ResponseEntity<ResponseDTO> modify(@PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO){
        categoryService.modify(categoryDTO,categoryId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .successStatus(HttpStatus.CREATED)
                .successContent("해당 게시판 명이 수정되었습니다. : " + categoryDTO.getName())
                .Data(categoryDTO)
                .build());
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ResponseDTO> modify(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);

        return ResponseEntity.ok(ResponseDTO.builder()
                .successStatus(HttpStatus.CREATED)
                .successContent("해당 게시판의 게시물 및 댓글이 모두 삭제되었습니다.")
                .Data(null)
                .build());
    }

    }
