package com.bitacademy.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int count(){
		return sqlSession.selectOne("board.count");
	}
	
	public List<BoardVo> findAll(int n){
		int start = (n-1)*10;
		return sqlSession.selectList("board.findAll",start);
	}
	
	public BoardVo view(Long no){
		return sqlSession.selectOne("board.view",no);
	}
	
	public void insert(BoardVo vo) {
		sqlSession.insert("board.insert",vo);
	}
	
	public void delete(List<BoardVo> list) {
		Map<String, Object> map=new HashMap<>();
		map.put("group", list.get(0).getGroupNo());
		map.put("order", list.get(0).getOrderNo());
		map.put("depth", list.get(0).getDepth());
		map.put("size", list.size());
		if(list.size()>1) {
			map.put("orderNext", list.get(1).getOrderNo());
		}
		sqlSession.delete("board.delete",map);
	}
	
	public List<BoardVo> deletefind(BoardVo vo) {
		System.out.println(sqlSession.selectList("board.deletefind",vo));
		return sqlSession.selectList("board.deletefind",vo);
	}
	
	public void update(BoardVo vo) {
		sqlSession.update("board.update",vo);
	}
	
	public void commendUpdate(BoardVo vo) {
		sqlSession.update("board.commendUpdate",vo);
	}
	
	public void hit(Long no) {
		sqlSession.update("board.hit",no);
	}
}
