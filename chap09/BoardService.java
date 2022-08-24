package org.zerock.myapp.service;

import java.util.List;

import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.exception.ServiceException;

public interface BoardService {
	
	// 각 웹 3계층의 성격에 맞게 추상메소드의 이름을 결정하라
	
	// 1. 게시글 전체목록 획득 (게시글 번호의 역순으로)
	public abstract List<BoardVO> getList() throws ServiceException;
	
	// 2. 새로운 게시글 등록
	public abstract boolean register(BoardDTO dto) throws ServiceException;
	
	// 3. 기존 게시글 수정
	public abstract boolean modify(BoardDTO dto) throws ServiceException;
	
	// 4. 기존 게시글 삭제
	public abstract boolean remove(BoardDTO dto) throws ServiceException;
	
	// 5. 기존 게시글 상세조회
	public abstract BoardVO get(BoardDTO dto) throws ServiceException;

} // end interface
