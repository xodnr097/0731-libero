package com.libero.service.user.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.libero.service.domain.User;
import com.libero.service.user.UserDAO;

@Repository("userDAOImpl")
public class UserDAOImpl implements UserDAO {

	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public UserDAOImpl() {
		System.out.println(this.getClass());
	}
	//method
	public User getUser(String userId) throws Exception {
		return sqlSession.selectOne("UserMapper.getUser", userId);
	}

	@Override
	public void addUser(User user) throws Exception {
		sqlSession.insert("UserMapper.addNewUser",user);
		
	}

	@Override
	public List<User> getAdminCashList() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("UserMapper.getAdminCashList");
	}
	
	
	
	

}
