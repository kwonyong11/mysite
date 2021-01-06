package com.bitacademy.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitacademy.mysite.service.BoardService;
import com.bitacademy.mysite.service.GuestbookService;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.GuestbookVo;
import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.security.Auth;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(@RequestParam(value="n", defaultValue="1") Integer n, Model model, HttpSession session) {
		List<BoardVo> list = boardService.getBoardList(n);
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		model.addAttribute("list",list);
		model.addAttribute("authUser", authUser);
		return "board/list";
	}

	
	@Auth
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(Long no, Model model) {
		model.addAttribute("boardNo", no);
		return "board/write";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(Long boardNo, BoardVo vo,HttpSession session) {
		boardService.writeBoard(boardNo, vo, session);
		return "redirect:/board";
	}
	
//	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
//	public String delete(@PathVariable("no") Long no, Model model) {
//		model.addAttribute("no", no);
//		return "guestbook/delete";
//	}
	
//	@RequestMapping(value="/delete", method=RequestMethod.POST)
//	public String delete(GuestbookVo vo) {
//		guestbookService.deleteMessage(vo);
//		return "redirect:/guestbook";
//	}
}
