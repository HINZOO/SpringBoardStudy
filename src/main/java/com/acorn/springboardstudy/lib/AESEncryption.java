package com.acorn.springboardstudy.lib;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.Key;
import java.util.Base64;

public class AESEncryption {
    //AES : 대칭키(암호화와 복호화 방식) 블록+패딩(블록화하여 블록단위로 암호화 ECB/개별블럭암호,CBC/앞블럭다시암호,GCM/CBC에 인증코드추가)
    private static final String ALGORITHM="AES";//알고리즘 유형
    private static final String CIPHER_ALGORITHM="AES/ECB/PKCS5Padding";
    private static final int BLOCK_SIZE=128;//AES 모드시 블럭 사이즈.
    private static Key secretKey;//암호화와 복구화에 사용될 대칭키
    private static final String KEY_FILE_NAME="secretKey.ser";//톰캣 서버가 배포되는 위치에 저장됨.

    //📁
    //메모리 : heap(new=>인스턴스객체 / GC에 의해 정리됨) ->C 에서는 pointer 를 이용해 일일히 삭제.
    //        stack( method 에 정의된 실행된 객체들)
    //        method(class 정보 + static)
    //static : JVM 이 class 를 로드할 때 static 이라고 선언된 필드는  method 메모리 영역에 저장된다.
    static{//JVM 이 클래스를 로드할 때 static 필드를 초기화 한다. //try/catch 를 쓰기 위해 사용.
        try {
            File secretKeyFile = new File(KEY_FILE_NAME);
            if(secretKeyFile.exists()){//🎃 파일이 있으면 기존파일을 불러와서 오브젝트로 변환
                ObjectInputStream ois=new ObjectInputStream(new FileInputStream(KEY_FILE_NAME));
                secretKey=(Key)ois.readObject();// Object 파일상태인 key를 읽어줌.
            }else{//🎃 파일이 없으면 생성 오브젝트를 파일로 변환
            //🍒 KeyGenerator :  간단한 암호화 알고리즘으로 대칭키를 생성
            KeyGenerator kg=KeyGenerator.getInstance(ALGORITHM);
            kg.init(BLOCK_SIZE);//블럭사이즈 지정.
            secretKey=kg.generateKey();//톰캣서버 시작시, key가 생성됨.
            ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(KEY_FILE_NAME));//Object를 파일로 반환
            oos.writeObject(secretKey);//파일로 작성
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String encryptValue(String value) throws Exception{
        //Cipher : 암호화와 복호화 하는 클래스
        Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);//해당키로 암호화를 한다.
        byte[] encryptBytes=cipher.doFinal(value.getBytes());//doFinal:: 암호화든 복호화든 하겠다.->바이트로 반환
            //"AB"=={"A","B"} char[] == {65,66} byte[]
            //"안녕"=={'안','녕'} char[] == {-20,-80,-107,-21,-113,-117} byte[]
        //🍒 암호화된 해시코드는 바이트로 인코딩이 된다.

        return Base64.getEncoder().encodeToString(encryptBytes);//인코딩된 바이트배열을 문자열로 반환
    }
    public static String decryptValue(String encryptValue)throws Exception{
        byte[] encryptBytes=Base64.getDecoder().decode(encryptValue);// 문자열->바이트 인코딩으로 변환
        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,secretKey);//대칭키로 복호화 준비
        byte[] decryptBytes=cipher.doFinal(encryptBytes);//복호화 진행 //유니코드인 바이트배열를 반환
        return new String(decryptBytes);//유니코드인 바이트배열을  문자열로 반환
    }
}
