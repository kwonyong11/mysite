package com.bitacademy.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.mysite.repository.BoardRepository;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	public List<BoardVo> getBoardList(int n) {
		return boardRepository.findAll(n);
	}
	
	public int count() {
		return boardRepository.count();
	}
	
	public BoardVo view(Long no) {
		
		BoardVo vo = boardRepository.view(no);
		
		boardRepository.hit(no);
		
		return vo;
	}
	
	public void writeBoard(Long no, BoardVo vo, UserVo authUser) {
		Long authNo = authUser.getNo();
		Long groupNo=0L;
		int orderNo=0;
		int depth=0;
		if(no!=null) {
			BoardVo BoardVo = boardRepository.view(no);
			depth=BoardVo.getDepth()+1;
			groupNo=BoardVo.getGroupNo();
			orderNo=BoardVo.getOrderNo()+1;
		}
		
		vo.setGroupNo(groupNo);
		vo.setNo(no);
		vo.setOrderNo(orderNo);
		vo.setDepth(depth);
		vo.setUserNo(authNo);
		boardRepository.insert(vo);
	}
	
	public void delete(Long no) {
		BoardVo vo = boardRepository.view(no);
		List<BoardVo> list = boardRepository.deletefind(vo);
		
		boardRepository.delete(list);
	}
	
	public void modify(BoardVo vo) {
		boardRepository.update(vo);
	}
}