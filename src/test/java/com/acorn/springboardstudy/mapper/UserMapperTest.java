package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.UserDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//MethodOrderer.OrderAnnotation : @Order(0~) 로 순서를 정하겠다.(함수이름 등으로 순서를 정할수 있다.)
class UserMapperTest {
//    @BeforeAll//모든 테스트 실행전 실행됨.(초기화)//static 필드
//    static void init(){}
//    @AfterAll//모든 테스트가 끝나고 실행 (정리)
//    static void destory(){}
//    //@Test : 정의된 함수들은 순서 없이 실행
//    //등록-> 수정-> 삭제 순 테스트를 기대하나 실제로는 순서를 알 수 없다.
//    //따라서 초기화 하거나 정리를 해야한다.

    //순서가 있는 TEST -> @TestMethodOrder : test의 순서를 지정할 수 있음 (Junit 5)
    @Autowired
    private UserMapper userMapper;
    private static UserDto user;
    @Test
    @Order(1)
    void insertOne() {
        user = new UserDto();
        user.setUId("test00");
        user.setPw("1234");
        user.setBirth("1990-11-28");
        user.setPhone("test-1111-test");
        user.setEmail("Test00@test.or.com.net");
        user.setName("테스트");
        user.setAddress("서울시");
        user.setDetailAddress("압구정로");
        user.setGender("NONE");
        user.setImgPath("/public/imgs/user/test00.jpeg");
        int insertOne= userMapper.insertOne(user);
        assertEquals(insertOne,1);
    }
    @Test
    @Order(2)
    void findByUId() {
        UserDto finduser=userMapper.findByUId(user.getUId());
        assertNotNull(finduser);
    }
    @Test
    @Order(3)
    void findByUIdAndPw() {
        UserDto findUser=userMapper.findByUIdAndPw(user);
        assertNotNull(findUser);
    }

    @Test
    @Order(4)
    void findUIdByEmailAndPhoneAndName() {
        String uId= userMapper.findUIdByEmailAndPhoneAndName(user);
        assertNotNull(uId);
    }

    @Test
    @Order(5)
    void updateOne() {
       UserDto user = new UserDto();
        user.setUId("test00");
        user.setPw("12345678");
        user.setBirth("1990-12-27");
        user.setPhone("test-8888-test");
        user.setEmail("Test00@test.or.com");
        user.setName("테스트유저수정");
        user.setAddress("서울시 강남구");
        user.setDetailAddress("논현로");
        user.setGender("MALE");
        user.setImgPath("/public/imgs/user/test88.jpeg");
        int updateOne = userMapper.updateOne(user);
        UserMapperTest.user=userMapper.findByUId(user.getUId());
        System.out.println("UserMapperTest.user = " + UserMapperTest.user);
        assertEquals(updateOne,1);
    }


    @Test
    @Order(6)
    void updatePwByUId() {
        UserDto user = new UserDto();
        user.setUId("test00");
        user.setPw("비밀번호1234");
        int updatePwByUId= userMapper.updatePwByUId(user);
        UserMapperTest.user=userMapper.findByUId(user.getUId());
        System.out.println("UserMapperTest.user = " + UserMapperTest.user);
        assertEquals(updatePwByUId,1);
    }
    @Order(7)// 빨리 테스트하기 위해서는 동시에 진행하는것이 가장 좋다 (방법이 없으면 순서를 정한다)
    @Test
    void deleteByUIdAndPw() {
        int deleteByUIdAndPw= userMapper.deleteByUIdAndPw(user);
        assertEquals(deleteByUIdAndPw,1);

    }
}