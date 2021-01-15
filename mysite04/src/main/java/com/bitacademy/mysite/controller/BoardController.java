package com.bitacademy.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitacademy.mysite.service.BoardService;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.security.Auth;
import com.bitacademy.security.AuthUser;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(@RequestParam(value="page", defaultValue="1") Integer page, Model model, @AuthUser UserVo authUser) {
		List<BoardVo> list = boardService.getBoardList(page);
		model.addAttribute("list",list);
		model.addAttribute("count",boardService.count());
		model.addAttribute("page", page);
		model.addAttribute("authUser", authUser);
		return "board/list";
	}

	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(Long no, int page, Model model, @AuthUser UserVo authUser) {
		BoardVo vo = boardService.view(no);
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		model.addAttribute("authUser", authUser);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(Long no, Model model, int page) {
		model.addAttribute("boardNo", no);
		model.addAttribute("page", page);
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(Long boardNo, BoardVo vo,@AuthUser UserVo authUser, int page) {
		boardService.writeBoard(boardNo, vo, authUser);
		return "redirect:/board?page="+page;
	}
	
	@Auth
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(Long no, int page) {
		boardService.delete(no);
		return "redirect:/board?page="+page;
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(Long boardNo, int page, Model model) {
		BoardVo vo = boardService.view(boardNo);
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		model.addAttribute("boardNo", boardNo);
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(BoardVo vo, int page) {
		boardService.modify(vo);
		return "redirect:/board/view?no="+vo.getNo()+"&page="+page;
	}
}
