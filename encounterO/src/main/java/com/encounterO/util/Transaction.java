package com.encounterO.util;

import java.util.List;

import com.encounterO.board.db.BoardDTO;
import com.encounterO.board.db.RepleDTO;
import com.encounterO.member.db.MemberDTO;

public class Transaction {
	List<BoardDTO> bdtoList;
	List<RepleDTO> rdtoList;
	List<MemberDTO> mdtoList;
	BoardDTO bdto;
	RepleDTO rdto;
	MemberDTO mdto;
	public List<BoardDTO> getBdtoList() {
		return bdtoList;
	}
	public void setBdtoList(List<BoardDTO> bdtoList) {
		this.bdtoList = bdtoList;
	}
	public List<RepleDTO> getRdtoList() {
		return rdtoList;
	}
	public void setRdtoList(List<RepleDTO> rdtoList) {
		this.rdtoList = rdtoList;
	}
	public List<MemberDTO> getMdtoList() {
		return mdtoList;
	}
	public void setMdtoList(List<MemberDTO> mdtoList) {
		this.mdtoList = mdtoList;
	}
	public BoardDTO getBdto() {
		return bdto;
	}
	public void setBdto(BoardDTO bdto) {
		this.bdto = bdto;
	}
	public RepleDTO getRdto() {
		return rdto;
	}
	public void setRdto(RepleDTO rdto) {
		this.rdto = rdto;
	}
	public MemberDTO getMdto() {
		return mdto;
	}
	public void setMdto(MemberDTO mdto) {
		this.mdto = mdto;
	}
	@Override
	public String toString() {
		return "Transaction [bdtoList=" + bdtoList + ", rdtoList=" + rdtoList + ", mdtoList=" + mdtoList + ", bdto="
				+ bdto + ", rdto=" + rdto + ", mdto=" + mdto + "]";
	}

	
	
	
}
