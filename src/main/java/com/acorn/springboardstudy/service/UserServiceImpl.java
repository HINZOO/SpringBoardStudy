package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.UserDto;
import com.acorn.springboardstudy.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    //😎 @Component로 정의된 클래스만 DI 할 수 있다.=>지금 이 클래스는 @Component의 자식인 @Service로 정의해서 컴포넌트한것과 같다.
    private UserMapper userMapper;
        //DIP 에 의해서 인터페이스로 정의
        //인터페이스로 모듈을 유연하게 작성했기 때문에 Mybatis가 객체로 구현할 수 있다.(DIP 덕분에 Mybatis 사용)
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    //POJO 로 DI 정의 =================================


  @Override
    public UserDto login(UserDto user) {
        return userMapper.findByUIdAndPw(user);
    }

    @Override
    public UserDto detail(String uId) {
        return userMapper.findByUId(uId);
    }

    @Override
    public int modify(UserDto user) {
        return userMapper.updateOne(user);
    }

    @Override
    public int signup(UserDto user) {
        return userMapper.insertOne(user);
    }
    @Override
    public int dropout(UserDto user) {
        return userMapper.deleteByUIdAndPw(user);
    }
}
//https://www.webjars.org/
//