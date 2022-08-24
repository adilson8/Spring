package org.zerock.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.exception.ServiceException;
import org.zerock.myapp.mapper.BoardMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
public class BoardServiceImpl implements BoardService {
	
	@Setter(onMethod_=@Autowired)
	private BoardMapper mapper;

	@Override
	public List<BoardVO> getList() throws ServiceException {
		log.trace("getList() invoked.");
		
		// 핵심 비지니스 로직 구현
		try {
//			List<BoardVO> list = this.mapper.selectAllList();			
//			return list;
			
			return this.mapper.selectAllList();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
		
	} // getList

	@Override
	public boolean register(BoardDTO dto) throws ServiceException {
		log.trace("register({}) invoked.", dto);
		
		// 핵심 비지니스 로직 구현
		try {
			return this.mapper.insert(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
		
	} // register

	@Override
	public boolean modify(BoardDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.", dto);
		
		// 핵심 비지니스 로직 구현
		try {
			return this.mapper.update(dto) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
		
	} // modify

	@Override
	public boolean remove(BoardDTO dto) throws ServiceException {
		log.trace("remove({}) invoked.", dto);
		
		// 핵심 비지니스 로직 구현
		try {
			return this.mapper.delete(dto.getBno()) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
		
	} // remove

	@Override
	public BoardVO get(BoardDTO dto) throws ServiceException {
		log.trace("get({}) invoked.", dto);
		
		// 핵심 비지니스 로직 구현
		
		try {
			return this.mapper.select(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
		
	} // get

} // end class
