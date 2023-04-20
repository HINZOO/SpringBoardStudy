package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.UserDto;
import org.springframework.stereotype.Service;

//@Component//Spring Container(==IoC(Inversion of Controller;제어의 역전) container 라고도 부른다.) 가 관리하는 bean 객체
//IoC(제어의역전): 객체를 내부에서 생성하는 것이 정상적인 제어고 컨테이너에서 객체를 주입하는 형태로 변경하는것이 제어의 역전.
//IoC 를 이용해서 관심사를 분리할 수 있고 이를 통해 관점지향언어(AOP)를 구현.
//DI(의존성주입): private 필드인 @Autowired로 주입 또는 private 필드 생성자로 주입(POJO에 해당) 또는 private 필드 setter함수를 정의하고 호출할때 주입.
//IoC에 있는 객체가 @Autowired 와 생성자로 주입(POJO)
//DIP (Dependency Inversion Principle) 의존성 역전 법칙: 주입받는 객체의 타입은 꼭 인터페이스로 정의하라 (모듈을 유연하게 확장하기 위함)
@Service// @Component 의 자식 어노테이션 ; 서비스를 관리한다(관심사 분리)는 명시적의미와 @Transactional (롤백)을 정의 가능
public interface UserService {
    //?? 유저는 어떤 서비스를 받을 수 있을까?
    // 로그인   // 유저상세    // 유저정보 수정    // 회원가입    // 회원탈퇴
    UserDto login(UserDto user);
    UserDto detail(String uId,String loginUserId);
    int modify(UserDto user);
    int signup(UserDto user);
    int dropout(UserDto user);


}
