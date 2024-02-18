package com.yunhee.board.controller;

import com.yunhee.board.dto.BoardDTO;
import com.yunhee.board.dto.PageDTO;
import com.yunhee.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor // 컨트롤러->서비스->리포지터리 넘어가기 위한 의존성 주입
@RequestMapping("/board") // "/board"로 들어오는 주소에 대해서 다 받는다
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/save") // 글 작성 폼 페이지
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save") // 글 작성 처리
    public String save(@ModelAttribute BoardDTO boardDTO) {
        int saveResult = boardService.save(boardDTO);
        if (saveResult > 0) { // 글 작성 성공
            return "redirect:/board/paging"; // 글 목록으로 이동
        } else { // 글 작성 실패
            return "save"; // 글 작성 페이지에 그대로 머물고 있음
        }
    }

    @GetMapping("/") // 글 목록(글 여러개) 조회
    public String findAll(Model model) { // DB에서 데이터를 가져와서 화면에 뿌려줘야 하는 경우 Model model을 매개변수로 넘겨줌
        List<BoardDTO> boardDTOList = boardService.findAll(); // 글 여러개를 가져와야 하니 List 형식
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @GetMapping // 글 상세(글 한개) 조회, ?id=2라는 식의 파라미터로 들어오므로 /board 다음 매핑해줄 주소는 없음
    public String findById(@RequestParam("id") Long id,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) { // @RequestParam 뜻 : id라는 파라미터에 담겨온 값을 Long 타입의 id 변수에 담겠다 / 마찬가지로 DB에서 데이터를 가져오는 것이기 때문에 Model 필요
        boardService.updateHits(id); // 조회수 증가 메서드
        BoardDTO boardDTO = boardService.findById(id); // 글 한개를 가져오니 그냥 BoardDTO 타입
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", page);
        return "detail";
    }

    @GetMapping("/delete") // 글 삭제
    public String delete(@RequestParam("id") Long id) { // id의 값이 파라미터로 담겨왔으니까 @RequestParam("id") 써줌
        boardService.delete(id);
        return "redirect:/board/";
    }

    @GetMapping("/update") // 글 수정 폼 페이지
    public String updateForm(@RequestParam("id") Long id, Model model) { // DB에서 저장된 원 내용을 끌고와서 수정전 화면으로 보여줘야 하므로 Model model 필요
        BoardDTO boardDTO = boardService.findById(id); // 수정하려면 먼저 DB에서 데이터를 가져와서 화면에 보여줘야하므로 기존에 만들어놓은 findById 메서드 사용
        model.addAttribute("board", boardDTO);
        return "update";
    }

    @PostMapping("/update") // 글 수정 처리
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        boardService.update(boardDTO); // 1. 글 수정
        BoardDTO dto = boardService.findById(boardDTO.getId()); // 2. 해당 글 가져옴. 수정된 글의 상세를 가져오기 위해 findById 메서드 사용(수정된 boardDTO 객체의 id값에 해당하는 글을 가져오기 때문에 boardDTO.getId()를 매개변수로 넣어줌
        model.addAttribute("board", dto);
        return "detail";
        // return "redirect:/board?id=" + boardDTO.getId(); // 이렇게 redirect로 다시 글 상세 조회 메서드를 실행하면 상세 조회 페이지가 띄워지긴 하지만 조회수가 올라가기 때문에 찝찝함
    }

    // (처음 페이지 요청은 1페이지를 보여줌) /board/paging : page = 1
    // /board/paging?page=5 : page = 5

    // value = "page" : 넘어온 파라미터가 page인것을 뜻함
    // required = false : page 파라미터가 넘어오는 것이 필수가 아니다. page 파라미터가 없어도, 그냥 /board/paging으로만 들어와도 에러 안나고 매핑해주겠다.
    // defaultValue = "1" : page 파라미터가 안넘어와도 상관없기 때문에 대신 기본 값으로 1을 지정해주겠다.(보통 브라우저들 페이징 보면 1이 기본값으로 되어있음)
    // int page : 이렇게 넘어온 파라미터의 값을 page 매개변수 안에 담음
    // 페이징 처리라는 것은 사용자가 어떤 페이지 번호를 클릭했느냐에 따라 화면에 보여지는 데이터가 바뀌어짐
    @GetMapping("/paging")
    public String paging(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        // 해당 페이지에서 보여줄 글 목록
        List<BoardDTO> pagingList = boardService.pagingList(page); // 페이지당 글이 3개씩 끊어서 보이도록 하는 메서드
        System.out.println("pagingList = " + pagingList); // 페이지당 게시물이 3개씩 잘 가져와지는지 print로 확인
        // 하단 페이징 번호들을 나타낼 파라미터들을 구해서 pageDTO에 담음
        PageDTO pageDTO = boardService.pagingParam(page); // 하단의 페이징 번호 나타내기 위해 연산한 메서드(maxPage, startPage, endPage 값 구하기 위해)
        model.addAttribute("boardList", pagingList);
        model.addAttribute("paging", pageDTO);
        return "paging";
    }
}
