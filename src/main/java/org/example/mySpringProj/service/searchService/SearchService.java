package org.example.mySpringProj.service.searchService;


import lombok.RequiredArgsConstructor;
import org.example.mySpringProj.dto.boardDto.BoardResponseDTO;
import org.example.mySpringProj.dto.commentDto.CommentResponseDTO;
import org.example.mySpringProj.dto.searchDto.SearchDTO;
import org.example.mySpringProj.repository.searchRepository.SearchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final SearchRepository searchRepository;

    public List<CommentResponseDTO> searchComments(SearchDTO dto) {
            return CommentResponseDTO.addDtoList
                    (searchRepository.findComments(dto.getUserId(), dto.getBoardId(),dto.getContent()));
    }
    public List<BoardResponseDTO> searchBoards(SearchDTO dto) {
        return BoardResponseDTO.getDtoList
                (searchRepository.findBoards(dto.getUserId(), dto.getCategoryId(), dto.getTitle(), dto.getContent()));
    }



}
