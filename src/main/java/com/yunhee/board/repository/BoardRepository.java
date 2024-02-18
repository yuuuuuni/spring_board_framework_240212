package com.yunhee.board.repository;

import com.yunhee.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final SqlSessionTemplate sql; // SqlSessionTemplate 클래스 주입. Mybatis에서 제공하는 클래스로 여기 자바 클래스와 매퍼 간의 연결을 해주는 역할을 함

    public int save(BoardDTO boardDTO) {
        return sql.insert("Board.save", boardDTO); // Board는 boardMapper.xml의 매퍼 이름인 namespace="Board"인것을 가리킴, save는 boardMapper.xml의 insert id 태그를 가리킴
    }

    public List<BoardDTO> findAll() {
        return sql.selectList("Board.findAll");
    }

    public BoardDTO findById(Long id) {
        return sql.selectOne("Board.findById", id);
    }

    public void updateHits(Long id) {
        sql.update("Board.updateHits", id);
    }

    public void delete(Long id) {
        sql.delete("Board.delete", id);
    }

    public void update(BoardDTO boardDTO) {
        sql.update("Board.update", boardDTO);
    }

    public List<BoardDTO> pagingList(Map<String, Integer> pagingParams) {
        return sql.selectList("Board.pagingList", pagingParams);
    }

    public int boardCount() {
        return sql.selectOne("Board.boardCount");
    }
}
