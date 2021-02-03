package com.bitacademy.mysite.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.mysite.repository.GuestbookRepository;
import com.bitacademy.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	private static final Log LOGGER = LogFactory.getLog(GuestbookService.class);
	
	@Autowired
	private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getMessageList() {
		return guestbookRepository.findAll();
	}
	
	public List<GuestbookVo> getMessageList(Long startNo) {
		return guestbookRepository.findAll(startNo);
	}
	
	public void writeMessage(GuestbookVo vo) {
		guestbookRepository.insert(vo);
	}

	public void deleteMessage(GuestbookVo vo) {
		guestbookRepository.delete(vo);
	}
}