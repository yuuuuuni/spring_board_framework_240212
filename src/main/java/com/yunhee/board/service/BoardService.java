package com.yunhee.board.service;

import com.yunhee.board.dto.BoardDTO;
import com.yunhee.board.dto.PageDTO;
import com.yunhee.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public int save(BoardDTO boardDTO) {
        return boardRepository.save(boardDTO);
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    int pageLimit = 3; // 한 페이지당 보여줄 글 개수
    int blockLimit = 3; // 하단에 보여줄 페이지 번호 개수
    public List<BoardDTO> pagingList(int page) {
        /*
        1페이지당 보여지는 글 개수 3개
            1page => 0
            2page => 3
            3page => 6
            4page => 9
         */
        int pagingStart = (page - 1) * pageLimit; // 페이징 시작 인덱스
        Map<String, Integer> pagingParams = new HashMap<>(); // 쿼리의 limit을 쓰려면 (페이징시작인덱스, 갯수) 이렇게 2개를 넘겨줘야 하므로 Map 이용
        pagingParams.put("start", pagingStart);
        pagingParams.put("limit", pageLimit);
        List<BoardDTO> pagingList = boardRepository.pagingList(pagingParams);
        return pagingList;
    }

    public PageDTO pagingParam(int page) {
        // 전체 글 개수 조회
        int boardCount = boardRepository.boardCount(); // DB에 있는 게시글의 개수에 따라 전체 몇 페이지가 필요한지 달라지기 때문에 전체 글 개수를 알아야함
        // 전체 페이지 개수 계산 (10/3 = 3.3333 => 4)
        int maxPage = (int)(Math.ceil((double) boardCount / pageLimit)); // Math.ceil는 자바에서 제공하는 소수점 자리의 숫자를 무조건 올리는 함수
        // 시작 페이지 값 계산(1, 4, 7, 10, ~~~~)
        int startPage = (((int)(Math.ceil((double) page / blockLimit))) -1) * blockLimit + 1;
        // 끝 페이지 값 계산(3, 6, 9, 12, ~~~~)
        int endPage = startPage + blockLimit - 1;
        if (endPage > maxPage) { // 끝 페이지 값이 전체 페이지 값보다 크면
            endPage = maxPage; // 끝 페이지 값을 전체 페이지 값으로 나타내라
        }

        PageDTO pageDTO = new PageDTO(); // 위에서 구한 maxPage, startPage, endPage 값들을 pageDTO 객체에 넣음
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setStartPage(startPage);
        pageDTO.setEndPage(endPage);
        return pageDTO;
    }
}
